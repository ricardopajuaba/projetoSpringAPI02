package br.com.cotiinformatica.controllers;

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
import br.com.cotiinformatica.entities.Endereco;
import br.com.cotiinformatica.repositories.IClienteRepository;
import br.com.cotiinformatica.repositories.IEnderecoRepository;
import br.com.cotiinformatica.requests.EnderecoPostRequest;
import br.com.cotiinformatica.responses.EnderecoResponse;

@Transactional
@Controller
public class EnderecosController {

	// definir o endpoint do serviço
	private static final String ENDPOINT = "/api/enderecos";

	@Autowired // inicialização automática (injeção de dependência)
	private IEnderecoRepository enderecoRepository;

	@Autowired // inicialização automática (injeção de dependência)
	private IClienteRepository clienteRepository;
	
	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<EnderecoResponse> post(@RequestBody EnderecoPostRequest request) {

		HttpStatus status = null;
		String message = null;
		
		try {
			
			//buscar o cliente atraves do email
			Cliente cliente = clienteRepository.findByEmail(request.getEmailCliente());
			
			//verificar se o cliente foi encontrado
			if(cliente != null) {
				
				//verificar se o cliente não possui um endereço cadastrado
				if(cliente.getEndereco() == null) {
										
					Endereco endereco = new Endereco();
					
					endereco.setLogradouro(request.getLogradouro());
					endereco.setNumero(request.getNumero());
					endereco.setComplemento(request.getComplemento());
					endereco.setBairro(request.getBairro());
					endereco.setCidade(request.getCidade());
					endereco.setEstado(request.getEstado());
					endereco.setCep(request.getCep());
					endereco.setCliente(cliente);
					
					enderecoRepository.save(endereco);
					
					status = HttpStatus.CREATED;
					message = "Endereço cadastrado com sucesso.";				
				}
				else {
				
					status = HttpStatus.BAD_REQUEST;
					message = "Já existe um endereço cadastrado para o cliente informado.";	
				}
			}
			else {
				status = HttpStatus.BAD_REQUEST;
				message = "Não foi encontrado um cliente com o email: " + request.getEmailCliente();
			}
		}
		catch(Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Erro: " + e.getMessage();
		}
		
		EnderecoResponse response = new EnderecoResponse();
		response.setStatusCode(status.value());
		response.setMessage(message);
		
		return ResponseEntity.status(status).body(response);
	}

}
