package br.com.cotiinformatica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idEndereco;

	@Column(length = 250, nullable = false)
	private String logradouro;

	@Column(length = 10, nullable = false)
	private String numero;

	@Column(length = 100, nullable = false)
	private String complemento;

	@Column(length = 100, nullable = false)
	private String bairro;

	@Column(length = 100, nullable = false)
	private String cidade;

	@Column(length = 50, nullable = false)
	private String estado;

	@Column(length = 10, nullable = false)
	private String cep;
	
	@OneToOne //relacionamento 1 para 1
	@JoinColumn(name = "idCliente", nullable = false, unique = true) //chave estrangeira
	private Cliente cliente;

}
