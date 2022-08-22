package com.diary.inn.InnDiary.work.entity.info;

import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "web_Todo")
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

    @Column(name = "todo_id")
    private Long todoId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime todoDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private ToDoJsonEntity todo;
}
