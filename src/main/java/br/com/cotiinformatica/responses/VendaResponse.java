package br.com.cotiinformatica.responses;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendaResponse {

	private Integer statusCode;
	private String message;

	private Integer idVenda;
	private Double valorTotal;
	private String emailCliente;

	private List<ProdutoResponse> produtos;
}
