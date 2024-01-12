package org.choongang.board.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entitys.BaseMember;
import org.choongang.file.entitys.FileInfo;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseMember {

    @Id
    @Column(length = 30)
    private String bid; // 게시판 아이디

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    @Column(length = 65, nullable = false)
    private String bName; // 게시판 이름

    private boolean active; // 사용 여부

    private int rowsPerPage = 20; // 1페이지 게시글 수
    private int pageCountPc = 10; // PC 페이지 구간 갯수
    private int pageCountMobile = 5; // Mobile 페이지 구간 갯수

    private boolean useReply; // 답글 사용 여부
    private boolean useComment; // 댓글 사용 여부

    private boolean useEditor; // 에디터 사용여부

    private boolean useUploadImage; // 이미지 첨부 사용 여부
    private boolean useUploadFile; // 파일 첨부 사용 여부

    @Column(length = 10, nullable = false)
    private String locationAfterWriting = "list"; // 글 작성 후 이동 위치

    @Transient
    private FileInfo logo1; // 로고 이미지1

    @Transient
    private FileInfo logo2; // 로고 이미미지2

    @Transient
    private FileInfo logo3; // 로고 이미지3


}
