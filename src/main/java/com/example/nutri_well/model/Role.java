package com.example.nutri_well.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 시스템상 사용자의 역할을 부여하는 엔티티.
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}