package br.com.cotiinformatica.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.cotiinformatica.entities.Produto;

public interface IProdutoRepository extends CrudRepository<Produto, Integer> {

}
