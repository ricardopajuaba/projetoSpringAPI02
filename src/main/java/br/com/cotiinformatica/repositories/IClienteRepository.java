package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Cliente;

public interface IClienteRepository extends CrudRepository<Cliente, Integer> {

	@Query("from Cliente c where c.email = :pEmail")
	Cliente findByEmail(@Param("pEmail") String email) throws Exception;

	@Query("from Cliente c where c.email = :pEmail and c.senha = :pSenha")
	Cliente findByEmailAndSenha(@Param("pEmail") String email, @Param("pSenha") String senha) throws Exception;

	@Query("from Cliente c where c.cpf = :pCpf")
	Cliente findByCpf(@Param("pCpf") String cpf) throws Exception;

	@Query("from Cliente c where c.telefone = :pTelefone")
	Cliente findByTelefone(@Param("pTelefone") String telefone) throws Exception;
}
