package br.com.casadocodigo.loja.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builder.ProdutoBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPAConfiguration.class,ProdutoDAO.class,DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDAOTest {
	
	@Autowired
	private ProdutoDAO produtoDAO;

	@Test
	@Transactional
	public void deveSomarTodosOsPrecosPorTipoLivro() {
		
		List<Produto> livrosImpressos = ProdutoBuilder.create(TipoPreco.IMPRESSO, BigDecimal.TEN)
				.more(3).buildAll();
		
		List<Produto> livrosEbook = ProdutoBuilder.create(TipoPreco.EBOOK, BigDecimal.TEN)
				.more(3).buildAll();
		
		livrosImpressos.stream().forEach(produtoDAO::store);
		livrosEbook.stream().forEach(produtoDAO::store);
		
		BigDecimal valor = produtoDAO.somaPrecosPorTipo(TipoPreco.EBOOK);
		assertEquals(new BigDecimal(40).setScale(2), valor);
	}

}
