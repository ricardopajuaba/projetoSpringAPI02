package br.com.cotiinformatica.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idVenda;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date data;

	@Column(nullable = false)
	private Double valor;

	@Column(length = 250, nullable = false)
	private String observacoes;

	@ManyToOne // muitas Vendas para 1 Cliente
	@JoinColumn(name = "idCliente", nullable = false) // chave estrangeira
	private Cliente cliente;

	@ManyToMany // muitos para muitos
	@JoinTable( // mapeamento da tabela associativa no banco de dados
			// nome da tabela associativa
			name = "VendaProduto",
			// mapear a chave estrangeira para a entidade Venda
			joinColumns = @JoinColumn(name = "idVenda", nullable = false),
			// mapear a chave estrangeira para a entidade Produto
			inverseJoinColumns = @JoinColumn(name = "idProduto", nullable = false))
	private List<Produto> produtos;
}
