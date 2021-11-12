package br.com.cotiinformatica.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import br.com.cotiinformatica.requests.LoginPostRequest;
import br.com.cotiinformatica.responses.LoginResponse;
import br.com.cotiinformatica.security.TokenSecurity;

@Transactional
@Controller
public class LoginController {

	// endereço de serviço deste controlador
	private static final String ENDPOINT = "/api/login";

	@Autowired // inicialização automática (injeção de dependência)
	private IClienteRepository clienteRepository;

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<LoginResponse> post(@RequestBody LoginPostRequest request) {
		
		LoginResponse response = new LoginResponse();
		
		HttpStatus status = null;
		String message = null;
		
		try {
			
			//procurar o cliente na base de dados atraves do email e senha
			Cliente cliente = clienteRepository.findByEmailAndSenha
					(request.getEmail(), MD5Cryptography.encrypt(request.getSenha()));
			
			//verificar se o cliente foi encontrado
			if(cliente != null) {
				
				status = HttpStatus.OK;
				message = "Cliente autenticado com sucesso.";
				
				response.setNomeCliente(cliente.getNome());
				response.setEmailCliente(cliente.getEmail());
				
				//gerando o token de autenticação
				response.setAccessToken(TokenSecurity.generateToken(cliente.getEmail()));
				response.setDataExpiracao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
						.format(new Date(System.currentTimeMillis() + 6000000)));
			}
			else {
				
				status = HttpStatus.UNAUTHORIZED; //não autorizado
				message = "Acesso negado.";
			}
		}
		catch(Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		}		
		
		response.setStatusCode(status.value());
		response.setMessage(message);
		
		return ResponseEntity.status(status).body(response);
	}
}













