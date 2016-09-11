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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.br.uepb.validacao.FormataCampos;

/**
 * Classe de domínio que define as informações coletadas do dispositivo pessoal balança 
 */
@Entity
@Table(name="medicao_balanca")
public class MedicaoBalancaDomain {
	
	/** Id da medicao */
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	/** Representação do objeto Paciente */
	@ManyToOne
	@JoinColumn(name = "paciente_id", referencedColumnName="id")
	@Fetch(FetchMode.JOIN)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private PacienteDomain paciente;

	/** Data e Hora da medição */
	@Column(name="data_hora")
	private Date dataHora;
	
	/** Data e hora da medição formatada */
	@Transient
	private String dataHoraFormatada;
	
	/** Peso do paciente */
	@Column(name="peso")
	private double peso;

	/** Unidade de medida referente ao peso do paciente */
	@Column(name="peso_unidade")
	private String pesoUnidade;
	
	/** Altura do paciente */
	@Column(name="altura")
	private double altura;
	
	/** Unidade de medida referente a altura do paciente */
	@Column(name="altura_unidade")
	private String alturaUnidade;
	
	/** Índice de massa corporal(IMC) do paciente */
	@Column(name="massa")
	private double massa;

	/** Unidade de medida referente ao índice de massa corporal(IMC) do paciente */
	@Column(name="massa_unidade")
	private String massaUnidade;
	
	/**
	 * Método para retornar o id da medição da balança 
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Método para informar o id da medição da balança
	 * @param id Id da medição
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Método para retornar o objeto referente ao paciente
	 * @return PacienteDomain
	 */
	public PacienteDomain getPaciente() {
		return paciente;
	}
	
	/**
	 * Método para informar o paciente relacionado com as informações da medição da balança
	 * @param paciente Objeto de representação do paciente (PacienteDomain)
	 */
	public void setPaciente(PacienteDomain paciente) {
		this.paciente = paciente;
	}
	
	/**
	 * Método para retornar a data e hora da medição da balança
	 * @return Date
	 */
	public Date getDataHora() {
		return dataHora;
	}
	
	/**
	 * Método para informar a data e hora da medição da balança
	 * @param dataHora Data e hora da medição
	 */
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	/** Método para informar a data e hora da medição formatada 
	 *  no padrão dd/MM/yyyy HH:mm:ss
	 * @return String Data e hora formatada
	 */
	public String getDataHoraFormatada() {
		FormataCampos formataCampos = new FormataCampos();
		dataHoraFormatada = formataCampos.formataDataHora(dataHora);		
		return dataHoraFormatada;
	}
	
	/**
	 * Método para retornar o peso do paciente
	 * @return double
	 */
	public double getPeso() {
		return peso;
	}
	
	/**
	 * Método para informar o peso do paciente
	 * @param peso Peso do paciente
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	/**
	 * Método para retornar a unidade referente ao peso do paciente
	 * @return String
	 */
	public String getPesoUnidade() {
		return pesoUnidade;
	}
	
	/**
	 * Método para informar a unidade referente ao peso do paciente
	 * @param pesoUnidade Unidade de medida referente ao peso
	 */
	public void setPesoUnidade(String pesoUnidade) {
		this.pesoUnidade = pesoUnidade;
	}
	
	/**
	 * Método para retornar a altura do paciente
	 * @return double
	 */
	public double getAltura() {
		return altura;
	}
	
	/**
	 * Método para informar a altura do paciente
	 * @param altura Altura do paciente
	 */
	public void setAltura(double altura) {
		this.altura = altura;
	}

	/**
	 * Método para retornar a unidade referente a altura do paciente
	 * @return String
	 */
	public String getAlturaUnidade() {
		return alturaUnidade;
	}
	
	/**
	 * Método para informar a unidade referente a altura do paciente
	 * @param alturaUnidade Unidade de medida referente a altura do paciente
	 */
	public void setuAlturaUnidade(String alturaUnidade) {
		this.alturaUnidade = alturaUnidade;
	}
	
	/**
	 * Método para retornar o índice de massa corporal(IMC) do paciente 
	 * @return double
	 */
	public double getMassa() {
		return massa;
	}
	
	/**
	 * Método para informar o índice de massa corporal(IMC) do paciente
	 * @param massa Índice de massa corporal(IMC) do paciente
	 */
	public void setMassa(double massa) {
		this.massa = massa;
	}
	
	/**
	 * Método para retornar a unidade referente ao índice de massa corporal(IMC) do paciente
	 * @return String
	 */
	public String getMassaUnidade() {
		return massaUnidade;
	}
	
	/**
	 * Método para informar a unidade referente ao índice de massa corporal(IMC) do paciente
	 * @param massaUnidade Unidade referente ao índice de massa corporal(IMC) do paciente
	 */
	public void setMassaUnidade(String massaUnidade) {
		this.massaUnidade = massaUnidade;
	}
	
}
