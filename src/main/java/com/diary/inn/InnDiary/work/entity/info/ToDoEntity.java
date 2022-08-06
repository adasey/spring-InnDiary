package com.diary.inn.InnDiary.work.entity.info;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "web_ToDo")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long seq;

    @Column
    private String title;

    @Column
    private int weather;

    @Column
    private int status;

    @Column
    private String content;

    @Column
    private LocalDateTime reg_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private MemberEntity member;
}
