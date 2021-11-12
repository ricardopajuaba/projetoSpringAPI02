package br.com.cotiinformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginPostRequest {

	private String email;
	private String senha;
	
}
