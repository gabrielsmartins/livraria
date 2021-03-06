package br.com.casadocodigo.loja.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.model.Carrinho;
import br.com.casadocodigo.loja.model.DadosPagamento;
import br.com.casadocodigo.loja.model.Usuario;

@Controller
@RequestMapping(value="/pagamento", method=RequestMethod.POST)
public class PagamentoController {
	
	@Autowired
	private Carrinho carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender sender;
	
	@RequestMapping("/finalizar")
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model) {
		return () -> {
			String uri = "http://book-payment.herokuapp.com/payment";
			try {
				
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				System.out.println(response);
				//enviaEmailCompraProduto(usuario);
				model.addFlashAttribute("message", response);
				return new ModelAndView("redirect:/produtos");
			}catch(HttpClientErrorException e) {
				System.out.println(e.getMessage());
				model.addFlashAttribute("message", "Valor Maior Que o Permitido");
				return new ModelAndView("redirect:/produtos");
			}
		};

	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra Realizada com Sucesso !!!");
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("naorespondalivraria@gmail.com");
		email.setTo("ga.smartins94@gmail.com");
		sender.send(email);
	}

}
