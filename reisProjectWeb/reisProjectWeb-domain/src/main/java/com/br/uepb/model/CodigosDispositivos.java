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
 * Classe que define os códigos utilizados para geração da mensagem HL7
 * Verifique o documento de especificação do HL7 ORU^R01 para compreender 
 * a estrutura de uma mensagem HL7
 */

public class CodigosDispositivos {

	/** Nomenclaura referente a definição do SNOMED */
	public final String SNOMED_NOMENCLATURA = "SNOMED-CT";
	/**  Nomenclaura referente a definição de sinais vitais */
	public final String SNOMED_SINAIS_VITAIS = "Vital Signs";
	/**  Codificação da nomenclatura sinais vitais de acordo com o SNOMED */
	public final String SNOMED_SINAIS_VITAIS_COD = "46680005";
	/**  Enumerator para definição dos códigos dos sinais vitais */
	public SinaisVitaisEnum sinaisVitaisEnum;
	
	/**
	 * Enumerator para definição dos códigos dos sinais vitais 
	 * A ordem do enum segue o seguinte padrão:
	 * SinaisVitaisEnum_Nome ( Nome, codXML, codSNOMED, nomeSNOMED, intervalo);
	 * nome - Nome do sinal vital 
	 * codXML - Codificação do sinal vital de acordo com o XML
	 * codSNOMED - Codificação do sinal vital de acordo com a definição do SNOMED
	 * nomeSNOMED - Nome do sinal vital definido pelo do SNOMED
	 * intervalo - Interlvalo indicado para a medição
	 */
	public enum SinaisVitaisEnum {
		SPO2_OX("SpO2", "19384", "431314004", "SpO2", "95-100"),
		TAXA_PULSO_OX("Taxa de Pulso", "18458", "78564009", "Pulse rate", "50-100"),		
		PRESSAO_MED("Pressao Arterial", "18948", "75367002", "Blood Pressure", ""),
		PRESSAO_SISTOLICA_MED("Pressão Arterial Sistólica","18949", "271649006", "Systolic blood pressure", "<120"),
		PRESSAO_DIASTOLICA_MED("Pressão Arterial Diastólica", "18950", "271650006", "Diastolic blood pressure", "<80"),
		PRESSAO_MEDIA_MED("Pressão Arterial Media", "18951", "6797001", "Mean blood pressure", ""),
		TAXA_PULSO_MED("Taxa de Pulso", "18474", "78564009", "Pulse rate", "50-100"),		
		PESO_BAL("Peso", "57664", "27113001", "Body Weight", ""),
		ALTURA_BAL("Altura", "57668", "50373000", "Body Height", ""),
		IMC_BAL("IMC", "57680", "60621009", "Body mass index", "");
		
		/** Nome do sinal vital */
        private String nome;
        /** Codificação do sinal vital de acordo com o XML */
		private String codXML;
		/** codSNOMED - Codificação do sinal vital de acordo com a definição do SNOMED */
        private String codSNOMED;
        /** Nome do sinal vital definido pelo do SNOMED */
        private String nomeSNOMED;
        /** Indicado para a medição */
        private String intervalo;
        
        /**
         * Método construtor do enum SinaisVitaisEnum
         * @param nome Nome do sinal vital
         * @param codXML Codificação do sinal vital de acordo com o XML
         * @param codSNOMED Codificação do sinal vital de acordo com a definição do SNOMED
         * @param nomeSNOMED Nome do sinal vital definido pelo do SNOMED
         * @param intervalo Interlvalo indicado para a medição
         */
        SinaisVitaisEnum(String nome, String codXML, String codSNOMED, String nomeSNOMED, String intervalo){
        	this.nome = nome;
        	this.codXML = codXML;
        	this.codSNOMED = codSNOMED;
        	this.nomeSNOMED = nomeSNOMED;
        	this.intervalo = intervalo;
        }
		
        /**
         * Método para obter o nome do sinal vital
         * @return String Nome do sinal vital
         */
        public String getNome() {
			return nome;
		}

        /**
         * Método para obter a codificação do sinal vital de acordo com o XML
         * @return String Codigo referente sinal vital de acordo com o XML
         */
		public String getCodXML() {
			return codXML;
		}

		/**
		 * Método para obter a codificação do sinal vital de acordo com o SNOMED
		 * @return String Codigo referente sinal vital de acordo com o SNOMED
		 */
		public String getCodSNOMED() {
			return codSNOMED;
		}

		/**
		 * Método para obter a nomenclatura do sinal vital de acordo com o SNOMED
		 * @return String Nomenclatura referente sinal vital de acordo com o SNOMED
		 */
		public String getNomeSNOMED() {
			return nomeSNOMED;
		}
		
		/**
		 * Método para obter a faixa de valor padrão para o sinal vital
		 * @return String Faixa de valor padrão para o sinal vital
		 */
		public String getIntervalo() {
			return intervalo;
		}
		
	}	
	
}
