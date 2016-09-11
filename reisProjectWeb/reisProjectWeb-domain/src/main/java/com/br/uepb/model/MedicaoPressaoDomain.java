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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.br.uepb.validacao.FormataCampos;

/**
 * Classe de domínio que define as informações coletadas do dispositivo pessoal medidor de pressão arterial 
 */
@Entity
@Table(name = "medicao_pressao")
public class MedicaoPressaoDomain {

	/** Id da medição */
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	/** Representação do objeto Paciente */
	@ManyToOne
	@JoinColumn(name = "paciente_id", referencedColumnName="id")
	@Fetch(FetchMode.JOIN)	
	private PacienteDomain paciente;
	
	/** Data e hora da medicao */
	@Column(name="data_hora")
	private Date dataHora;
	
	/** Data e hora da medição formatada */
	@Transient
	private String dataHoraFormatada;
	
	/** Medida referente a pressão sistólica */
	@Column(name = "pressao_sistolica")
	private double pressaoSistolica;
	
	/** Unidade de medida referente a pressão sistólica */
	@Column(name="pressao_sistolica_unidade")
	private String pressaoSistolicaUnidade;
	
	/** Medida referente a pressão diastólica */
	@Column(name = "pressao_diastolica")
	private double pressaoDiastolica;
	
	/** Unidade de medida referente a pressão diastólica */
	@Column(name="pressao_diastolica_unidade")
	private String pressaoDiastolicaUnidade;
		
	/** Medida referente a pressão media arterial */
	@Column(name = "pressao_media")
	private double pressaoMedia;
	
	/** Unidade de medida referente a pressão media arterial */
	@Column(name="pressao_media_unidade")
	private String pressaoMediaUnidade;
	
	/** Medida referente a taxa de pulso */
	@Column(name = "taxa_pulso")
	private double taxaPulso;
	
	/** Unidade de medida referente a taxa de pulso */
	@Column(name="taxa_pulso_unidade")
	private String taxaPulsoUnidade;
	
	/**
	 * Método para retornar o id da medição 
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Método para informar o id da medição
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
	 * Método para informar o objeto referente ao paciente
	 * @param paciente Objeto de representação do paciente (PacienteDomain)
	 */
	public void setPaciente(PacienteDomain paciente) {
		this.paciente = paciente;
	}
	
	/**
	 * Método para informar a data e hora da medição
	 * @return Date
	 */
	public Date getDataHora() {
		return dataHora;
	}
	
	/**
	 * Método para retornar a data e hora da medição
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
	 * Método para retornar o valor da pressão sistólica
	 * @return double
	 */	
	public double getPressaoSistolica() {
		return pressaoSistolica;
	}
	
	/**
	 * Método para informar o valor da pressão sistólica 
	 * @param pressaoSistolica double
	 */
	public void setPressaoSistolica(double pressaoSistolica) {
		this.pressaoSistolica = pressaoSistolica;
	}
	
	/**
	 * Método para retornar a unidade de medida referente a pressão sistólica
	 * @return String
	 */
	public String getPressaoSistolicaUnidade() {
		return pressaoSistolicaUnidade;
	}
	
	/**
	 * Método para informar a unidade de medida referente a pressão sistólica
	 * @param pressaoSistolicaUnidade Unidade de medida referente a pressão sistólica
	 */
	public void setPressaoSistolicaUnidade(String pressaoSistolicaUnidade) {
		this.pressaoSistolicaUnidade = pressaoSistolicaUnidade;
	}
	
	/**
	 * Método para retornar o valor da pressão diastólica
	 * @return double
	 */
	public double getPressaoDiastolica() {
		return pressaoDiastolica;
	}
	
	/**
	 * Método para informar o valor da pressão diastólica
	 * @param pressaoDiastolica Valor da pressão diastólica
	 */
	public void setPressaoDiastolica(double pressaoDiastolica) {
		this.pressaoDiastolica = pressaoDiastolica;
	}
	
	/**
	 * Método para retornar a unidade de medida referente a pressão diastólica
	 * @return String
	 */
	public String getPressaoDiastolicaUnidade() {
		return pressaoDiastolicaUnidade;
	}
	
	/**
	 * Método para informar a unidade de medida referente a pressão diastólica
	 * @param pressaoDiastolicaUnidade Unidade de medida referente a pressao diastólica
	 */
	public void setPressaoDiastolicaUnidade(String pressaoDiastolicaUnidade) {
		this.pressaoDiastolicaUnidade = pressaoDiastolicaUnidade;
	}
		
	/**
	 * Método para informar o valor da pressão média arterial
	 * @return double
	 */
	public double getPressaoMedia() {
		return pressaoMedia;
	}
	
	/**
	 * Método para informar o valor da pressão média arterial
	 * @param pressaoMedia Valor da pressão média arterial
	 */
	public void setPressaoMedia(double pressaoMedia) {
		this.pressaoMedia = pressaoMedia;
	}
	
	/**
	 * Método para retornar a unidade de medida referente a pressao arterial média
	 * @return String
	 */
	public String getPressaoMediaUnidade() {
		return pressaoMediaUnidade;
	}
	
	/**
	 * Método para informar a unidade de medida referente a pressão arterial média
	 * @param pressaoMediaUnidade Unidade de medida referente a pressão arterial média
	 */
	public void setPressaoMediaUnidade(String pressaoMediaUnidade) {
		this.pressaoMediaUnidade = pressaoMediaUnidade;
	}

	/**
	 * Método para retornar o valor da taxa de pulso
	 * @return double
	 */
	public double getTaxaPulso() {
		return taxaPulso;
	}
	
	/**
	 * Método para informar o valor da taxa de pulso
	 * @param taxaPulso Valor da taxa de pulso
	 */
	public void setTaxaPulso(double taxaPulso) {
		this.taxaPulso = taxaPulso;
	}
	
	/**
	 * Método para retornar a unidade referente a taxa de pulso
	 * @return String
	 */
	public String getTaxaPulsoUnidade() {
		return taxaPulsoUnidade;
	}
	
	/**
	 * Método para informar a unidade referente a taxa de pulso
	 * @param taxaPulsoUnidade Unidade de medida refernte a taxa de pulso
	 */
	public void setTaxaPulsoUnidade(String taxaPulsoUnidade) {
		this.taxaPulsoUnidade = taxaPulsoUnidade;
	}
	
}
