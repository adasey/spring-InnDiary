package com.diary.inn.InnDiary.work.entity.info;

import com.diary.inn.InnDiary.work.domain.info.Member;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "web_diary")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryEntity {

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
