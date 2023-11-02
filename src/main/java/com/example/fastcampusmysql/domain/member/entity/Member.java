package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    final private Long id; // 변경되면 안되니 final 선언
    private String nickname; // 변경이 가능하므로
    final private String email;
    final private LocalDate birthDay;
    final private LocalDateTime createdAt;
    final private static Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthDay, LocalDateTime createAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email); // 널 체크
        this.birthDay = Objects.requireNonNull(birthDay); // 널 체크

        validateNickName(nickname);
        this.nickname = Objects.requireNonNull(nickname); // 널 체크

        this.createdAt = createAt == null ? LocalDateTime.now() : createAt;
    }

    public void changeNickname(String to) {
        Objects.requireNonNull(to);
        validateNickName(to);
        nickname = to;
    }

    private void validateNickName(String nickname) {
        Assert.isTrue(nickname.length()<=NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}
