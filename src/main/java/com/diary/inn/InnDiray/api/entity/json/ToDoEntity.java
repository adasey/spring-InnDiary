package com.diary.inn.InnDiray.api.entity.json;

import com.diary.inn.InnDiray.api.entity.BaseEntity;
import com.diary.inn.InnDiray.user.entity.MemberEntity;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "inn_todo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private MemberEntity user;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private JsonNode Json;
}
