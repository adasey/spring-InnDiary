package com.diary.inn.InnDiary.work.entity.api;

import com.diary.inn.InnDiary.work.entity.BaseEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "inn_todo")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonType.class)
public class ToDoJsonEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long seq;

    @Column(length = 50)
    private String saveTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private MemberEntity member;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String todo;

    public void setUser(MemberEntity member) {
        this.member = member;
    }
}
