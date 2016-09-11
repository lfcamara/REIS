/*
The MIT License (MIT)
Copyright (c) 2016 Núcleo de Tecnologias Estratégicas em Saúde (NUTES)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions 
of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE. 
*/
package com.br.uepb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import com.br.uepb.model.LoginDomain;

import conexaoBD.HibernateUtil;

/**
 * Classe DAO para o objeto LoginDomain
 * Esta classe é responsavel por pesquisar, inserir, excluir e atualizar informações 
 * na tabela mapeada com o objeto LoginDomain
 */
public class LoginDAO {
	
	/** Idenfiticação da sessão atual */
	private Session sessaoAtual;
	
	/** Construtor da classe LoginDAO */
	public LoginDAO(){ }
	
	/**
	 * Método para salvar ou atualizar o login do usuário
	 * Se o parâmetro login for informado, o método irá atualizar as informações do usuário
	 * caso contrário irá criar um novo login
	 * @param login Objeto de representação do login(LoginDomain)
	 */
	public void salvaLogin(LoginDomain login){
		Transaction tx = SessaoAtual().beginTransaction();
		if (jaExisteUsuario(login.getLogin())) {
			SessaoAtual().clear();
			SessaoAtual().update(login);
		} 
		else {
			SessaoAtual().save(login);
		}

		SessaoAtual().flush();
		tx.commit();
		SessaoAtual().close();
	}
	
	/**
	 * Método para excluir o login do usuário
	 * @param login Objeto de representação do login(LoginDomain)
	 * @return Retorna true se conseguir ecluir o login e false caso contrário
	 */
	public boolean excluiLogin(LoginDomain login){		
		
		Transaction tx = SessaoAtual().beginTransaction();
		
		MedicaoBalancaDAO medicaoBalanca = new MedicaoBalancaDAO();
		if (!medicaoBalanca.excuiMedicaoBalancaPorPaciente(login.getPaciente().getId())) {
			return false;
		}
		
		MedicaoOximetroDAO medicaoOximetro = new MedicaoOximetroDAO();
		if (!medicaoOximetro.excuiMedicaoOximetroPorPaciente(login.getPaciente().getId())) {
			return false;
		}
		
		MedicaoPressaoDAO medicaoPressao = new MedicaoPressaoDAO();
		if (!medicaoPressao.excuiMedicaoPressaoPorPaciente(login.getPaciente().getId())) {
			return false;
		}
		try {
			SessaoAtual().delete(login);
			SessaoAtual().flush();
			tx.commit();
			SessaoAtual().close();
			
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	/**
	 * Método para obter o objeto LoginDomain de acordo com o id de login do usuário 
	 * @param idLogin Id do login
	 * @return LoginDomain
	 */
	public LoginDomain obtemLogin(int idLogin){
		LoginDomain login = (LoginDomain)SessaoAtual().get(LoginDomain.class, idLogin);
		SessaoAtual().close();
		return login;
	}
	
	/**
	 * Método para obter o objeto LoginDomain de acordo com a identificação de usuário e senha
	 * @param usuario Nome de usuário para realizar o login
	 * @param senha Senha do usuário
	 * @return LoginDomain
	 */
	public LoginDomain obtemLogin(String usuario, String senha){
		Query query = SessaoAtual().createQuery("FROM LoginDomain WHERE login = :usuario AND senha = :senha");
		query.setString("usuario", usuario);
		query.setString("senha", senha);
		LoginDomain login = (LoginDomain)query.uniqueResult();
		
		return login;
	}
	
	/**
	 * Método para obter o objeto LoginDomain de acordo com a identificação de id do paciente
	 * @param idPaciente Id do paciente
	 * @return LoginDomain
	 */
	public LoginDomain obtemLoginPorPaciente(int idPaciente){
		Query query = SessaoAtual().createQuery("FROM LoginDomain WHERE paciente.id = :idPaciente");
		query.setParameter("idPaciente", idPaciente);
		LoginDomain login = (LoginDomain)query.uniqueResult();
		return login;
	}
	
	/**
	 * Método para verificar se um usuário ja existe, 
	 * se o usuário existir o retorno será true, caso contrário será false
	 * @param usuario Nome do usuário
	 * @return Boolean
	 */
	public boolean jaExisteUsuario(String usuario){
		Query query = SessaoAtual().createQuery("FROM LoginDomain WHERE login = :usuario");
		query.setString("usuario", usuario);
		return (query.uniqueResult() != null);
	}
	
	/**
	 * Método para listar todos os logins cadastrados
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<LoginDomain> listaLogins(){
		
		List<LoginDomain> logins = 
				(List<LoginDomain>)SessaoAtual().createQuery("from LoginDomain").list();
		
		SessaoAtual().close();
		return logins;
	}
	
	/**
	 * Método para retornar a sessão atual.
	 * Se não existir, será criada uma nova sessão
	 * @return Session
	 */
	private Session SessaoAtual(){
		if (sessaoAtual == null || !sessaoAtual.isOpen()){
			sessaoAtual = HibernateUtil.getSessionFactory().openSession();
		}
		return sessaoAtual;
	}	
}
