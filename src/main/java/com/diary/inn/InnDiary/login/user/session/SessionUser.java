package com.diary.inn.InnDiary.login.user.session;

import com.diary.inn.InnDiary.login.user.entity.UserEntity;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(UserEntity user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

//    @Entity User 클래스를 SessionUser로 사용안하는 이유
//
//    세션에 저장하기 위해 User클래스를 세션에 저장하려고 하니 User 클래스에 직렬화를 구현하지 않았다는
//    에러가 난다.
//
//    Entity 클래스는 직렬화 코드를 넣지 않는게 좋다
//    엔티티 클래스에는 언제 다른 엔티티와 관계가 형성될지 모른다.
//    @OneToMany, @ManyToMany등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높다
//    그래서 직렬화 기능을 가진 세션 Dto를 하나 추가로 만든 것이 더 좋은 방법이다.
// 출처 https://velog.io/@swchoi0329/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EC%99%80-OAuth-2.0%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84
}
