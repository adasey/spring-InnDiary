package com.diary.inn.InnDiary.work.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Long dSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SlotEntity slot;

    @Column
    private Long diarySeq;

    @Column
    private String title;

    @Column
    private int weather;

    @Column
    private int status;

    @Column
    private String content;

    @Column
    private LocalDate date;
}
