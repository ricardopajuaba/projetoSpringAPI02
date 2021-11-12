package br.com.cotiinformatica.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.cotiinformatica.entities.Venda;

public interface IVendaRepository extends CrudRepository<Venda, Integer> {

}
