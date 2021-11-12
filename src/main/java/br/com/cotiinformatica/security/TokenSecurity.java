package br.com.cotiinformatica.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenSecurity {

	public static final String SECRET = "5eebb082-4046-4d7f-a638-3c16d9dec4f8";

	public static String generateToken(String email) {

		// chave secreta para geração do TOKEN (Evitar falsificações)
		String secretKey = SECRET;

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		// COTI_JWT -> nome da aplicação que gerou o token!
		String token = Jwts.builder().setId("COTI_JWT").setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 6000000)) // 10horas
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return token;
	}

}
