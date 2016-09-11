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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Classe de domínio que define o modelo para Paciente 
 */

@Entity
@Table(name = "paciente")
public class PacienteDomain {
	
	/** Id do paciente */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	/** Nome do paciente  */
	@Column(name = "nome")
	@NotNull
	private String nome;
	
	/** Sobrenome do paciente  */
	@Column(name = "sobrenome")
	private String sobrenome;
	
	/** Cidade do paciente  */
	@Column(name = "cidade")
	private String cidade;
	
	/** Estado do paciente  */
	@Column(name = "estado")
	private String estado;
	
	/** Sexo do paciente  */
	@Column(name = "sexo")
	private String sexo;
	
	/** Sexo do paciente  */	
	@Column(name = "data_nascimento")
	private String dataNascimento;
	
	/** Idade do paciente */
	@Transient
	private int idade;
	
	/** Endereço do paciente  */
	@Column(name = "endereco")
	private String endereco;
	
	/** Telefone de casa do paciente  */
	@Column(name = "telefone_casa")
	private String telefoneCasa;
	
	/**
	 * Método para retornar o id do paciente
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Método para informar o id do paciente
	 * @param id Id do paciente
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Método para retornar o nome do paciente
	 * @return String
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método para informar o nome do paciente 
	 * @param nome Nome do paciente
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método para retornar o sobrenome do paciente
	 * @return String
	 */
	public String getSobrenome() {
		return sobrenome;
	}

	/**
	 * Método para informar o sobrenome do paciente 
	 * @param sobrenome Sobrenome do paciente
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	/**
	 * Método para retornar a cidade do paciente
	 * @return String
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * Método para informar a cidade do paciente
	 * @param cidade Cidade do paciente
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	/**
	 * Método para retornar o estado do paciente 
	 * @return string
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Método para informar o estado do paciente
	 * @param estado Estado do paciente
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Método para retornar o sexo do paciente
	 * @return String
	 */
	public String getSexo() {
		return sexo;
	}
	
	/**
	 * Método para informar o sexo do paciente
	 * @param sexo Sexo do paciente
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * Método para retornar a data de nascimento
	 * @return String
	 */
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	/**
	 * Método para informar a data de nascimento
	 * @param dataNascimento Data de nascimento do paciente
	 */
	public void setDataNascimento(String dataNascimento) {
		//FormataCampos formataCampos = new FormataCampos();
		//String data = formataCampos.formataData(dataNascimento).toString(); 				
		this.dataNascimento = dataNascimento;	
	}
	
	/**
	 * Método para retornar a idade do paciente
	 * @return int
	 */
	public int getIdade() {
		//FormataCampos formataCampos = new FormataCampos();
		//idade = formataCampos.obtemIdade(dataNascimento);		
		return idade;
	}
	
	/**
	 * Método para retornar o endereço do paciente
	 * @return String
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * Método para informar o endereço do paciente
	 * @param endereco Endereço do paciente
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * Método para retornar o telefone(casa) do paciente 
	 * @return String
	 */
	public String getTelefoneCasa() {
		return telefoneCasa;
	}

	/**
	 * Método para informar o telefone(casa) do paciente
	 * @param telefoneCasa Telefone primário (casa/celular)
	 */
	public void setTelefoneCasa(String telefoneCasa) {
		this.telefoneCasa = telefoneCasa;
	}
		
}
