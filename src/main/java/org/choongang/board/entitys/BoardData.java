package org.choongang.board.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.commons.entitys.BaseMember;

@Data
@Entity
public class BoardData extends BaseMember {

    @Id @GeneratedValue
    private Long seq;

    private String subject;

    private String content;


}
