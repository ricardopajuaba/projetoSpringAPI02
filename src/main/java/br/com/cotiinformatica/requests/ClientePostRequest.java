package br.com.cotiinformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientePostRequest {

	private String nome;
	private String email;
	private String senha;
	private String senhaConfirmacao;
	private String cpf;
	private String telefone;

}
