package br.com.cotiinformatica.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecosRequest {

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private String emailCliente;
	
}
