package br.com.cotiinformatica.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idCliente;

	@Column(length = 150, nullable = false)
	private String nome;

	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@Column(length = 50, nullable = false)
	private String senha;

	@Column(length = 15, nullable = false, unique = true)
	private String cpf;

	@Column(length = 20, nullable = false, unique = true)
	private String telefone;

	// mapeando um vínculo 1 para 1
	// como a foreign key ja esta definida na classe Endereco, apenas
	// precisamos mapear o nome do atributo na classe Endereco onde
	// está mapeado a foreign key (joinColumn)
	@OneToOne(mappedBy = "cliente")
	private Endereco endereco;

	// mapeando um vínculo 1 para muitos
	// como a foreign key ja esta definida na classe Venda, apenas
	// precisamos mapear o nome do atributo na classe Venda onde
	// está mapeado a foregin key (joinColumn)
	@OneToMany(mappedBy = "cliente")
	private List<Venda> venda;
}
