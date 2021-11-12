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

import br.com.cotiinformatica.cryptography.MD5Cryptography;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.IClienteRepository;
import br.com.cotiinformatica.requests.ClientePostRequest;
import br.com.cotiinformatica.responses.ClienteResponse;

@Transactional
@Controller
public class ClientesController {

	// definir o endpoint do serviço
	private static final String ENDPOINT = "/api/clientes";
	
	@Autowired // inicialização automática (injeção de dependência)
	private IClienteRepository clienteRepository;

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ClienteResponse> post(@RequestBody ClientePostRequest request) {

		HttpStatus status = null;
		String message = null;
		
		try {
			
			//verificar se as senhas informadas não são iguais..
			if( ! request.getSenha().equals(request.getSenhaConfirmacao())) {
				status = HttpStatus.BAD_REQUEST;
				message = "Senhas não conferem. Por favor informe as senhas corretamente.";
			}			
			//verificar se o email informado já está cadastrado
			else if(clienteRepository.findByEmail(request.getEmail()) != null) {
				status = HttpStatus.BAD_REQUEST;
				message = "Já existe um cliente cadastrado com o email: "+ request.getEmail();
			}			
			//verificar se o cpf informado já está cadastrado
			else if(clienteRepository.findByCpf(request.getCpf()) != null) {
				status = HttpStatus.BAD_REQUEST;
				message = "Já existe um cliente cadastrado com o cpf: "+ request.getCpf();
			}			
			//verificar se o telefone informado já está cadastrado
			else if(clienteRepository.findByTelefone(request.getTelefone()) != null) {
				status = HttpStatus.BAD_REQUEST;
				message = "Já existe um cliente cadastrado com o telefone: "+ request.getTelefone();
			}
			else {
				
				Cliente cliente = new Cliente();
				
				cliente.setNome(request.getNome());
				cliente.setEmail(request.getEmail());
				cliente.setSenha(MD5Cryptography.encrypt(request.getSenha()));
				cliente.setTelefone(request.getTelefone());
				cliente.setCpf(request.getCpf());
				
				clienteRepository.save(cliente);
				
				status = HttpStatus.CREATED;
				message = "Cliente cadastrado com sucesso.";
				
			}
		}
		catch(Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Erro: " + e.getMessage();
		}
		
		ClienteResponse response = new ClienteResponse();
		response.setStatusCode(status.value());
		response.setMessage(message);
		
		return ResponseEntity.status(status).body(response);
	}
}
