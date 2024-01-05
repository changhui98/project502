package org.choongang.file.service;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.choongang.commons.Utils;
import org.choongang.configs.FileProperties;
import org.choongang.file.entitys.FileInfo;
import org.choongang.file.repositorys.FileInfoRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {

    private final FileProperties fileProperties;
    private final FileInfoRepository repository;
    private final FileInfoService infoService;
    private final Utils utils;

    public List<FileInfo> upload(MultipartFile[] files, String gid, String location){
        /**
         *  1. 파일 정보 저장
         *  2. 서버쪽에 파일 업로드 처리
         */

        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();

        String uploadPath = fileProperties.getPath(); // 파일 업로드 기본 경로
        String thumbPath = uploadPath + "thumbs/"; // 썸네일 업로드 기본 경로

        List<int[]> thumbsSize = utils.getThumbSize(); // 썸네일 사이즈

        List<FileInfo> uploaderFiles = new ArrayList<>(); // 업로드 성공 파일 정보 목록

        for(MultipartFile file : files){

            /* 파일 정보 저장  */

            String fileName = file.getOriginalFilename();

            String extension = fileName.substring(fileName.lastIndexOf(".")); // 확장자

            String fileType = file.getContentType(); // 파일 종류 - 예) image/...

            FileInfo fileInfo = FileInfo.builder()
                    .gid(gid)
                    .location(location)
                    .fileName(fileName)
                    .extension(extension)
                    .fileType(fileType)
                    .build();

            repository.saveAndFlush(fileInfo);


            /* 파일 업로드 처리 Start  */

            long seq = fileInfo.getSeq();
            File dir = new File(uploadPath + (seq % 10));
            if (!dir.exists()) { // 디렉터리가 없으면 -> 생성
                dir.mkdir();
            }

            File uploadFile = new File(dir, seq+ extension);
            try {
                file.transferTo(uploadFile);

                /* 썸네일 이미지 처리 Start  */
                if(fileType.indexOf("image/") != -1 && thumbsSize != null){
                    File thumbDir = new File(thumbPath + dir);
                    if(!thumbDir.exists()){
                        thumbDir.mkdirs();
                    }
                    for(int[] sizes : thumbsSize){
                        String thumbFileName = sizes[0] + "_" + sizes[1] + "_" + fileName;

                        File thumb = new File(thumbDir, thumbFileName);
                        Thumbnails.of(uploadFile)
                                .size(sizes[0], sizes[1])
                                .toFile(thumb);
                    }


                }


                /* 썸네일 이미지 처리 End   */

                infoService.addFileInfo(fileInfo); // 파일 추가 정보 처리

                uploaderFiles.add(fileInfo); // 업로드 성공시 파일 정보 추가

            } catch (IOException e) { // 업로드 실패시에는 파일 정보 제거
                e.printStackTrace();
                repository.delete(fileInfo);
                repository.flush();
            }


            /* 파일 업로드 처리 End  */
        }
        return uploaderFiles;
    }

    /**
     * 업로드 완료 처리
     * @param gid
     */
    public void processDone(String gid){
        List<FileInfo> files = repository.findByGid(gid);
        if(files == null){
            return;
        }

        files.forEach(file -> file.setDone(true));
        repository.flush();
    }


}