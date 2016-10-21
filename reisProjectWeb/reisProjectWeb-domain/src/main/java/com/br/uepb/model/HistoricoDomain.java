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

/**
 * Classe de domínio que define o histórico de medições do paciente 
 */
public class HistoricoDomain {

	/** Id do histórico */
	private int id;
	/** Data da medição */	
	private String data;
	/** Horário da medição */
	private String hora;
	/** Objeto referente aos dados de medição da balança */
	private MedicaoBalancaDomain balanca;
	/** Objeto referente aos dados de medição do oxímetro */
	private MedicaoOximetroDomain oximetro;
	/** Objeto referente aos dados de medição do medidor de pressão arterial */
	private MedicaoPressaoDomain pressao;
	private MedicaoIcgDomain icg;

	/**
	 * Método construtor da classe HistoricoDomain
	 * @param id Id do Histórico
	 * @param data Data da medição
	 * @param hora Hora da medição
	 * @param balanca Objeto referente a medição da balança
	 * @param oximetro Objeto referente a medição do oxímetro
	 * @param pressao Objeto referente a medição do medidor de pressão arterial
	 */
	public HistoricoDomain(int id, String data, String hora, MedicaoBalancaDomain balanca,
			MedicaoOximetroDomain oximetro, MedicaoPressaoDomain pressao, MedicaoIcgDomain icg) {		
		this.id = id;
		this.data = data;
		this.balanca = balanca;
		this.oximetro = oximetro;
		this.pressao = pressao;
		this.icg = icg;
	}

	/**
	 * Método para retornar o id do histórico
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método para retornar a data da medição
	 * @return String
	 */
	public String getData() {
		return data;
	}

	/**
	 * Método para informar a data da medição
	 * @param data Data da medição
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Método para retornar o horário da medição
	 * @return String
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Método para informar o horário da medição
	 * @param hora Hora da medição
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * Método para retornar o objeto referente aos dados de medição da balança
	 * @return MedicaoBalancaDomain
	 */
	public MedicaoBalancaDomain getBalanca() {
		return balanca;
	}

	/**
	 * Método para informar o objeto referente aos dados de medição da balança
	 * @param balanca Objeto referente a medição da balança
	 */
	public void setBalanca(MedicaoBalancaDomain balanca) {
		this.balanca = balanca;
	}

	/**
	 * Método para retornar o objeto referente aos dados de medição do oxímetro
	 * @return MedicaoOximetroDomain
	 */
	public MedicaoOximetroDomain getOximetro() {
		return oximetro;
	}

	/**
	 * Método para informar o objeto referente aos dados de medição do oxímetro
	 * @param oximetro Objeto referente a medição do oxímetro
	 */
	public void setOximetro(MedicaoOximetroDomain oximetro) {
		this.oximetro = oximetro;
	}

	/**
	 * Método para retornar o objeto referente aos dados de medição do medidor de pressão arterial
	 * @return MedicaoPressaoDomain
	 */
	public MedicaoPressaoDomain getPressao() {
		return pressao;
	}

	/**
	 * Método para informar o objeto referente aos dados de medição do medidor de pressão arterial
	 * @param pressao Objeto referente a medição do medidor de pressão arterial
	 */
	public void setPressao(MedicaoPressaoDomain pressao) {
		this.pressao = pressao;
	}
	
	/**
	 * Método para retornar o objeto referente aos dados de medição do medidor de pressão arterial
	 * @return MedicaoPressaoDomain
	 */
	public MedicaoIcgDomain getIcg() {
		return icg;
	}

	/**
	 * Método para informar o objeto referente aos dados de medição do medidor de pressão arterial
	 * @param pressao Objeto referente a medição do medidor de pressão arterial
	 */
	public void setIcg(MedicaoIcgDomain icg) {
		this.icg = icg;
	}

}
