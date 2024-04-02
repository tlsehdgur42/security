package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;




	// 스프링 시큐리티가 로그인 시점에서 사용할 UserSecurityService 는 스프링 시큐리티가 제공하는 UserDetailsService 인터페이스를 구현해야 한다.
	// 스프링 시큐리티의 UserDetailsService 는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스이다.
	// loadUserByUsername 메서드는 사용자명(username)으로 스프링 시큐리티의 사용자(User) 객체를 조회하여 리턴하는 메서드이다.
	// loadUserByUsername 는 사용자명으로 SiteUser 객체를 조회하고 만약 사용자명에 해당하는 자료가 없으면 UsernameNotFoundException 을 발생시킨다.
	// 사용자명이 "admin"인 경우에는 "ADMIN" 권한을 부여하고 그 이외에는 "USER"를 부여한다.
	// User 객체를 생성에 반환하는데 이 객체는 스프링 시큐리티에서 사용하는데 User 생성자에는 사용자명, 비밀번호, 권한 리스트가 전달된다.
	// loadUserByUsername 에 의하여 반환된 User 객체의 비밀번호가 사용자로부터 입력받은 비밀번호와 일치하는 지를 검사하는 기능이 내장되어 있다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}
		SiteUser siteUser = _siteUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
}
