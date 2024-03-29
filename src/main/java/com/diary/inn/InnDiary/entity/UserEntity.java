package com.diary.inn.InnDiary.entity;

import com.diary.inn.InnDiary.utils.attr.Role;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "inn_user")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String uid;

    @Column
    private int company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public UserEntity(String name, String email, String uid, int company, Role role) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.company = company;
        this.role = role;
    }

    public UserEntity(Long id, String name, String email, String uid) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public UserEntity update(String name) {
        this.name = name;
        return this;
    }

    public String getUid() {
        return this.uid;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public int getCompany() {
        return this.company;
    }
}
