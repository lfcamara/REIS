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
package com.br.uepb.business;

import com.br.uepb.dao.LoginDAO;
import com.br.uepb.model.LoginDomain;
import com.br.uepb.model.PacienteDomain;

/**
 * Classe para as regras de negócio de Login
 */
public class LoginBusiness {
	
	/** Instância de LoginDAO */
	private LoginDAO loginDAO = new LoginDAO();
	
	/**
	 * Método para salvar um login. 
	 * O método retorna true se conseguir salvar o login na base de dados, 
	 * caso contrário retorna false
	 * @param loginDomain Objeto de representação do login(LoginDomain)
	 * @return boolean
	 */
	public boolean salvar(LoginDomain loginDomain){		
		if(!loginDAO.jaExisteUsuario(loginDomain.getLogin())){
			loginDAO.salvaLogin(loginDomain);
			return true;
		}
		return false;
	}
	
	/**
	 * Método para obter o objeto LoginDomain de acordo com a identificação de usuário e senha
	 * @param usuario Nome de usuário para realizar o login
	 * @param senha Senha do usuário
	 * @return LoginDomain
	 */
	public LoginDomain obtemLogin(String usuario, String senha){
		return loginDAO.obtemLogin(usuario, senha);
	}
	
	/**
	 * Método para atualizar um login.
	 * O Método retorna true se conseguir atualizar o login na base de dados,
	 * caso contrário retorna false
	 * @param loginDomain Objeto de representação do login(LoginDomain)
	 * @return boolean
	 */
	public boolean atualizar(LoginDomain loginDomain) {
		try {
			loginDAO.salvaLogin(loginDomain);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Método para verificar se o login está correto.
	 * O Método retorna true se o login estiver correto,
	 * caso contrário retorna false.  
	 * @param login Nome de login do usuário
	 * @param senha Senha do usuário
	 * @return boolean
	 */
	public boolean loginValido(String login, String senha){
		LoginDomain loginDomain = loginDAO.obtemLogin(login, senha);
		if(loginDomain!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * Método para excluir um login.
	 * O Método retorna true se conseguir realizar a exclusão,
	 * caso contrário retorna false.
	 * @param loginDomain Objeto de representação do login(LoginDomain)
	 * @return boolean
	 */
	public boolean excluir(LoginDomain loginDomain){
		try {
			return loginDAO.excluiLogin(loginDomain);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Método para obter um paciente de acordo com o login e senha informados
	 * @param login Nome de login do usuário
	 * @param senha Senha do usuário
	 * @return PacienteDomain
	 */
	public PacienteDomain getPaciente(String login, String senha) {
		return loginDAO.obtemLogin(login, senha).getPaciente();		
	}
}
