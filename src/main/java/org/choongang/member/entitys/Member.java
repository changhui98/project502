package org.choongang.member.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.commons.entitys.Base;

@Data
@Entity
public class Member extends Base {


    @Id
    private Long seq;

    @Column(length=80 , nullable = false, unique = true)
    private String email;

    @Column(length = 40, nullable = false, unique = true)
    private String userId;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 40, nullable = false)
    private String name;

}
