package com.diary.inn.InnDiary.work.entity.fro;

import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
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

    @Column(name = "diary_id")
    private Long diaryId;

    @Column
    private String title;

    @Column
    private int weather;

    @Column
    private int status;

    @Column
    private String content;

    @Column
    private LocalDateTime DiaryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private DiaryJsonEntity diary;
}
