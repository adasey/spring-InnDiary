package com.diary.inn.InnDiray.user.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "inn_member")
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long seq;

    @Column(length = 50, nullable = false)
    private String loginId;

    @Column(length = 4, nullable = false)
    private int state;

    @Column(length = 4, nullable = false)
    private int company;
}
