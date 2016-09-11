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
package model;

import java.io.Serializable;

//Objeto que contÃ©m as informaÃ§Ãµes do arquivo de configuraÃ§Ã£o.
/**
 * Classe de domínio que contém as informações do arquivo de login do paciente
 */
public class LoginDomain implements Serializable{
	
	/** Versão do atributo serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** Login do paciente */
	private String login;
	/** Senha do paciente*/
	private String senha;
	/** Link de envio das informações para o servidor REIS */
	private String link;
	
	/**
	 * Método para obter o login do paciente
	 * @return String Login do paciente
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Método para informar o login do paciente
	 * @param login  Login do paciente
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Método para obter a senha do paciente
	 * @return String Senha do paciente
	 */
	public String getSenha() {
		return senha;
	}
	
	/**
	 * Método para informar a senha do paciente
	 * @param senha Senha do paciente
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/**
	 * Método para obter o link de envio das informações para o servidor REIS
	 * @return String link de envio das informações para o servidor REIS
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Método para informar o link de envio das informações para o servidor REIS
	 * @param link Link de envio das informações para o servidor REIS
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	

}
