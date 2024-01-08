package org.choongang.file.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entitys.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void download(Long seq){
        FileInfo data = infoService.get(seq);
        String filePath = data.getFilePath();

        // 파일명 -> 2바이트 인코딩으로 변경(윈도우즈 시스템에서 한글 깨짐 방지)
        String fileName = null;
        try {
            fileName = new String(data.getFileName().getBytes(), "ISO8859_1");
        }catch (UnsupportedEncodingException e){

        }


        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)){
            OutputStream out = response.getOutputStream(); // 응답 Body에 출력

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Type", "application/octet-stream");
            response.setIntHeader("Expires",0);
            response.setHeader("Cache-Control", "must-revalidate");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Length",String.valueOf(fileName.length()));

            while(bis.available() > 0){
                out.write(bis.read());
            }
            out.flush();



        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
