package br.com.cotiinformatica.requests;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendaPostRequest {

	private Date dataVenda;
	private String observacoes;
	private List<Integer> produtos;
	private String emailCliente;
}
