package org.choongang.board.entitys;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BoardView.class)
public class BoardView {

    @Id
    private Long seq; // 게시글 번호

    @Id
    @Column(name="_uid")
    private int uid;

}
