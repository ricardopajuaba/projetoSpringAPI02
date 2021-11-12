package br.com.cotiinformatica.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idProduto;

	@Column(length = 150, nullable = false)
	private String nome;

	@Column(nullable = false)
	private Double preco;

	@Column(nullable = false)
	private Integer quantidade;

	@Column(length = 150, nullable = false)
	private String foto;

	@Column(length = 250, nullable = false)
	private String descricao;

	// relacionamento de muitos para muitos
	// como a entidade associativa ja esta definida na classe Venda, apenas
	// precisamos mapear o nome do atributo na classe Venda onde
	// está mapeado a tabela associativa (joinTable)
	@ManyToMany(mappedBy = "produtos")
	private List<Venda> vendas;
}
