package com.diary.inn.InnDiray.user.entity;

import com.diary.inn.InnDiray.user.domain.ProviderType;
import com.diary.inn.InnDiray.user.domain.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "inn_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonIgnore
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @JsonIgnore
    @Column(length = 128)
    @NotNull
    @Size(max = 128)
    private String password;

    @Column(length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    public User(@NotNull @Size(max = 64) String userId, @NotNull @Size(max = 512) String email, @NotNull ProviderType providerType, @NotNull RoleType roleType) {
        this.userId = userId;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}
