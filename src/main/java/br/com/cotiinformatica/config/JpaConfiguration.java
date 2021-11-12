package br.com.cotiinformatica.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // classe de configuração do Spring
@EnableJpaRepositories(basePackages = "br.com.cotiinformatica")
@EnableTransactionManagement // permitir a realização de transações no banco de dados
public class JpaConfiguration {

	/*
	 * Método para acessar o arquivo /META-INF/persistence.xml e ler os parametros
	 * de configuração definidos para o hibernate (conexao_mysql)
	 */
	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactory() {
		LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
		factoryBean.setPersistenceUnitName("conexao_mysql");

		return factoryBean;
	}

	/*
	 * Método para permitir a realização de transações na base de dados
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}
}
