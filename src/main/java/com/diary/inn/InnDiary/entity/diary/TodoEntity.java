package com.diary.inn.InnDiary.entity.diary;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "web_Todo")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long tSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private SlotEntity slot;

    @Column
    private Long todoSeq;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDate date;
}
