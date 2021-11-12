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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.entities.Venda;
import br.com.cotiinformatica.repositories.IClienteRepository;
import br.com.cotiinformatica.repositories.IProdutoRepository;
import br.com.cotiinformatica.repositories.IVendaRepository;
import br.com.cotiinformatica.requests.VendaPostRequest;
import br.com.cotiinformatica.responses.ProdutoResponse;
import br.com.cotiinformatica.responses.VendaResponse;

@Transactional
@Controller
public class VendasController {

	private static final String ENDPOINT = "/api/vendas";
	
	@Autowired //injeção de dependencia
	private IClienteRepository clienteRepository;
	
	@Autowired //injeção de dependencia
	private IProdutoRepository produtoRepository;
	
	@Autowired //injeção de dependencia
	private IVendaRepository vendaRepository;

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<VendaResponse> post(@RequestBody VendaPostRequest request) {

		VendaResponse response = new VendaResponse();

		HttpStatus status = null;
		String message = null;
		
		try {
			
			//capturar os produtos enviados
			List<Produto> produtos = new ArrayList<Produto>();
			Double valorTotal = 0.;
			
			//percorrer os produtos enviados (capturando os ids dos produtos)
			for(Integer idProduto : request.getProdutos()) {
				
				//buscando o produto no banco de dados atraves do id
				Optional<Produto> registro = produtoRepository.findById(idProduto); 
				
				//verificando se o produto foi encontrado
				if(registro.isPresent()) {
					produtos.add(registro.get()); //adicionar na lista
					valorTotal += registro.get().getPreco(); //acumulando o total
				}
				else {
					throw new IllegalArgumentException("Produto não encontado. ID: " + idProduto);
				}
			}
			
			//capturar o cliente, atraves do email
			Cliente cliente = clienteRepository.findByEmail(request.getEmailCliente());
			
			//verificando se o cliente não foi encontrado
			if(cliente == null) {
				throw new IllegalArgumentException("Cliente não encontado. Email: " + request.getEmailCliente());
			}
			
			//gravar os dados da venda
			Venda venda = new Venda();
			
			venda.setData(request.getDataVenda());
			venda.setValor(valorTotal);
			venda.setObservacoes(request.getObservacoes());
			venda.setCliente(cliente);
			venda.setProdutos(produtos);
			
			vendaRepository.save(venda); //cadastrando..
			
			status = HttpStatus.CREATED;
			message = "Venda cadastrada com sucesso";

			//response do serviço (retorno)
			response.setIdVenda(venda.getIdVenda());
			response.setValorTotal(venda.getValor());
			response.setEmailCliente(cliente.getEmail());
			
			List<ProdutoResponse> produtosResponse = new ArrayList<ProdutoResponse>();
			for(Produto produto : produtos) {
				
				ProdutoResponse pResponse = new ProdutoResponse();
				
				pResponse.setIdProduto(produto.getIdProduto());
				pResponse.setNome(produto.getNome());
				pResponse.setPreco(produto.getPreco());
				pResponse.setQuantidade(produto.getQuantidade());
				pResponse.setDescricao(produto.getDescricao());
				pResponse.setFoto(produto.getFoto());
				
				produtosResponse.add(pResponse);
			}
			
			response.setProdutos(produtosResponse);			
		}
		catch(IllegalArgumentException e) {
			status = HttpStatus.BAD_REQUEST; //erro 400
			message = e.getMessage();
		}
		catch(Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR; //erro 500
			message = e.getMessage();
		}

		response.setStatusCode(status.value());
		response.setMessage(message);
		
		return ResponseEntity.status(status).body(response);
	}
}
