package com.example.book.springboot.config.auth.dto;

import com.example.book.springboot.domain.user.Role;
import com.example.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, // OAuth2User에서 반홚는 사용자 정보는 Map이기 때문에 값 하나하나를 반환해야함
                                     String userNmaeAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNmaeAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttibuteName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttibuteName)
                .build();
    }

    public User toEntity() { // User 엔티티를 생성
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
