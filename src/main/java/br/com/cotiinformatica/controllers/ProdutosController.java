package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.IProdutoRepository;
import br.com.cotiinformatica.responses.ProdutoResponse;

@Transactional
@Controller
public class ProdutosController {

	private static final String ENDPOINT = "/api/produtos";
	
	@Autowired //inicialização automática (injeção de dependência)
	private IProdutoRepository produtoRepository;

	/*
	 * Serviço para consultar todos os produtos da base de dados
	 */
	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ProdutoResponse>> getAll() {
		
		List<ProdutoResponse> lista = new ArrayList<ProdutoResponse>();
		HttpStatus status = null;
		
		try {
			
			//consultar os produtos no banco de dados
			for(Produto produto : produtoRepository.findAll()) {
				
				ProdutoResponse response = new ProdutoResponse();
				
				response.setIdProduto(produto.getIdProduto());
				response.setNome(produto.getNome());
				response.setPreco(produto.getPreco());
				response.setQuantidade(produto.getQuantidade());
				response.setDescricao(produto.getDescricao());
				response.setFoto(produto.getFoto());
				
				lista.add(response);
			}
			
			status = HttpStatus.OK; //200 (sucesso!)
		}
		catch(Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR; //500 (erro interno de servidor)
		}
		
		return ResponseEntity.status(status).body(lista);
	}
	
	/*
	 * Serviço para consulta 1 produto baseado no ID
	 */
	@CrossOrigin
	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ProdutoResponse> getById(@PathVariable("id") Integer id) {

		ProdutoResponse response = null;
		HttpStatus status = null;
		
		try {
			
			//consultar 1 produto no banco de dados atraves do ID..
			Optional<Produto> registro = produtoRepository.findById(id);
			
			//verificar se o produto foi encontrado
			if(registro.isPresent()) {
				
				//capturar os dados do produto
				Produto produto = registro.get();
				
				//transferir os dados do produto para o objeto 'response'
				response = new ProdutoResponse();
				
				response.setIdProduto(produto.getIdProduto());
				response.setNome(produto.getNome());
				response.setPreco(produto.getPreco());
				response.setQuantidade(produto.getQuantidade());
				response.setDescricao(produto.getDescricao());
				response.setFoto(produto.getFoto());
			}
		}
		catch(Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR; //500 (erro interno de servidor)
		}
		
		return ResponseEntity.status(status).body(response);
	}
	
}









