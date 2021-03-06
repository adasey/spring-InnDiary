package com.diary.inn.InnDiray.api.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@TypeDef(name = "json", typeClass = JsonType.class)
public abstract class BaseEntity {
    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;
}
