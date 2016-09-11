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
 * Classe de domínio que define as informações coletadas do dispositivo pessoal oxímetro de pulso 
 */
@Entity
@Table(name="medicao_oximetro")
public class MedicaoOximetroDomain {
	
	/** Id da medição */
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
	
	/** Data e hora da medição */
	@Column(name="data_hora")
	private Date dataHora;
	
	/** Data e hora da medição formatada */
	@Transient
	private String dataHoraFormatada;
	
	/** Medida referente a Saturação de oxigênio no sangue */
	@Column(name = "spo2")
	private double spo2;
	
	/** Unidade de medida referente a saturação de oxigênio no sangue */
	@Column(name="spo2_unidade")
	private String spo2Unidade;
	
	/** Medida referente a taxa de pulso */
	@Column(name = "taxa_pulso")
	private double taxaPulso;
	
	/** Unidade de medida referente a taxa de pulso */	
	@Column(name="taxa_pulso_Unidade")
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
	 * Método para retornar a data e hora da medição
	 * @return Date Data e hora da medição
	 */
	public Date getDataHora() {
		return dataHora;
	}
	
	/**
	 * Método para informar a data e hora da medição
	 * @param dataHora Date
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
	 * Método para retornar o valor da saturação de oxigênio no sangue
	 * @return double
	 */
	public double getSpo2() {
		return spo2;
	}
	
	/**
	 * Método para informar o valor da saturação de oxigênio no sangue
	 * @param spo2 Saturação de oxigênio no sangue
	 */
	public void setSpo2(double spo2) {
		this.spo2 = spo2;
	}
	
	/**
	 * Método para retornar a unidade de medida referente a saturação de oxigênio
	 * @return String
	 */
	public String getSpo2Unidade() {
		return spo2Unidade;
	}
	
	/**
	 * Método para informar a unidade de medida referente a saturação de oxigênio
	 * @param spo2Unidade Unidade de medida referente a saturação de oxigênio
	 */
	public void setSpo2Unidade(String spo2Unidade) {
		this.spo2Unidade = spo2Unidade;
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
	 * Método para retornar a unidade de medida referente a taxa de pulso
	 * @return String
	 */
	public String getTaxaPulsoUnidade() {
		return taxaPulsoUnidade;
	}
	
	/**
	 * Método para informar a unidade de medida referente a taxa de pulso
	 * @param taxaPulsoUnidade Unidade de medida referente a taxa de pulso
	 */
	public void setTaxaPulsoUnidade(String taxaPulsoUnidade) {
		this.taxaPulsoUnidade = taxaPulsoUnidade;
	}
	
	
}
