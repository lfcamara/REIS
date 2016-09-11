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
package com.br.uepb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Classe de domínio que define o login do Paciente 
 */

@Entity
@Table(name = "login")
public class LoginDomain {

	/** Id do login */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	/** Login do paciente */
	@Column(name = "login", unique=true)
	@NotNull
	private String login;
	
	/** Senha do paciente */
	@Column(name = "senha")
	@NotNull
	private String senha;
	
	/** Representação do objeto Paciente */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paciente_id", referencedColumnName="id")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private PacienteDomain paciente;
	
	/**
	 * Método para retornar o id do login
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Método para informar o id do login
	 * @param id Id do login
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método para retornar o login do paciente
	 * @return String
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Método para informar o login do paciente
	 * @param login Login do paciente
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Método para retornar a senha do login do paciente 
	 * @return String
	 */
	public String getSenha() {
		return senha;
	}
	
	/**
	 * Método para informar a senha do login do paciente
	 * @param senha Senha do paciente
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/**
	 * Método para retornar o paciente pertencente ao login
	 * @return PacienteDomain
	 */
	public PacienteDomain getPaciente() {
		return paciente;
	}
	
	/**
	 * Método para informar o paciente pertencente ao login
	 * @param paciente Objeto de representação do paciente (PacienteDomain)
	 */
	public void setPaciente(PacienteDomain paciente) {
		this.paciente = paciente;
	}
	
}
