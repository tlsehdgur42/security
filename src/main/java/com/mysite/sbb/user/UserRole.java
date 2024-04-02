package com.mysite.sbb.user;

import lombok.Getter;

// 권한에 대한 것을 세팅
// enum을 사용하는 이유는 연관된 상수들을 그룹화 할 수 있다.
// 잘못된 값이나 타입이 사용될 가능성을 배제한다.
@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

	UserRole(String value) {
		this.value = value;
	}

	private String value;
}
