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
package com.br.uepb.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.br.uepb.model.MedicaoBalancaDomain;
import com.br.uepb.model.MedicaoIcgDomain;
import com.br.uepb.model.MedicaoOximetroDomain;
import com.br.uepb.model.MedicaoPressaoDomain;

/**
 * Classe para interpretação das medicoes dos arquivos xml
 * Consultar o documento pelo link: http://oss.signove.com/images/c/c7/AntidoteProgramGuide.pdf
 * Código próprio
 */
public class Medicoes {
	/** Representação do objeto dataList */
	private DataList _dataList = null;
	/** ArrayList para armazenar o conjunto das medicoes organizado por (nome, conteudo) */
	private ArrayList<Pair<String,String>> medicoes;
	/** Formatação da data */
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
	
	/**
	 * Método construtor da classe
	 * @param dataList Representação do objeto DataList
	 */
	public Medicoes(DataList dataList) {
		_dataList = dataList;
	}
	
	/**
	 * Método que obtém a lista de medições organizando a lista de elementos em pares(pair).
	 * Cada par de elemento é dividido em dois valores. O primeiro valor representa o nome
	 * e o segundo, o conteudo do elemento. Por exemplo: (type, numeric)  
	 * @return ArrayList - ArrayList de pares (nome_elemento, conteudo_elemento) 
	 */
	public ArrayList<Pair<String,String>> getMedicoes() {
		medicoes = new ArrayList<Pair<String,String>>();
				
		for (Entry entry : _dataList.getEntries()) {
			//Imprime a lista de meta de cada entidade
			setMeta(entry.getMeta());
			
			for(Entry sub : entry.getEntries()){
				setMeta(sub.getMeta());
				
				if(sub.getName().equals("Basic-Nu-Observed-Value")) {
					basic_nu(sub);
				}
				
				if(sub.getName().equals("Simple-Nu-Observed-Value")) {
					simple_nu(sub);
				}
				
				if ((sub.getName().equals("Compound-Basic-Nu-Observed-Value")) || (sub.getName().equals("Compound-Simple-Nu-Observed-Value"))) {
					compound_nu(sub);
				}
				
				if (sub.getName().equals("Absolute-Time-Stamp")) {
					absolute_time_stamp(sub);
				}
			}			
		}

		return medicoes;
	}
	
	/**
	 * Método para informar os valores do objeto do tipo "meta" para o array de medicoes 
	 * @param meta Representação do objeto "meta".
	 */
	private void setMeta(Map<String, String> meta) {		
		for (Map.Entry<String, String> e : meta.entrySet()) {
			Pair<String,String> node = new Pair<String, String>(null, null);
			node.setFirst(e.getKey());
			node.setSecond(e.getValue());
			medicoes.add(node);			
		}
	}
	
	/**
	 * Método para informar o valor de uma entidade, quando a entidade é classificada como "Basic-Nu-Observed-Value". 
	 * Exemplo (value, 20) 
	 * @param sub Representação do objeto "entry"
	 */
	private void basic_nu(Entry sub) {
		Pair<String,String> node = new Pair<String, String>(null, null);
		node.setFirst("value");
		node.setSecond(sub.getValue());
		medicoes.add(node);		
	}

	/**
	 * Método para informar o valor de uma entidade, quando a entidade é classificada como "simple". 
	 * Exemplo (value, 96.500)
	 * @param sub Representação do objeto "entry"
	 */
	private void simple_nu(Entry sub) {
		Pair<String,String> node = new Pair<String, String>(null, null);
		node.setFirst("value");
		node.setSecond(sub.getValue());
		medicoes.add(node);		
	}
	
	/**
	 * Método para informar o valor de uma entidade, quando a entidade é classificada como "Compound-Basic-Nu-Observed-Value". 
	 * @param sub Representação do objeto "entry"
	 */
	private void compound_nu(Entry sub) {
		Pair<String,String> node = new Pair<String, String>(null, null);
		String values = "";
		String metricId = "";
		
		for(Entry sub_entries : sub.getEntries()){
			//getMeta(sub_entries.getMeta());
			if (values.length() > 1) {
				values += ":";
			}
			//Obtem os valores tipo "meta-data"
			metricId = sub_entries.getMeta().get("metric-id"); 
			values += "metric-id=" + metricId;
			
			if (values.length() > 1) {
				values += ":";
			}
			
			values += "value=" + sub_entries.getValue();
	    }
				
		node.setFirst("compound");
		node.setSecond(values);
		//System.out.println(node.getFirst()+" "+node.getSecond());
		medicoes.add(node);			   
	}
	
	/**
	 * Método para informar o valor de uma entidade, quando a entidade é classificada como "Absolute-Time-Stamp". 
	 * @param sub Representação do objeto "entry"
	 */
	private void absolute_time_stamp(Entry sub) {
		String[] names = new String[] {"century", "year", "month", "day", "hour", "minute", "second", "sec_fractions"};
		String[] times = new String[names.length];
		
	    for(int i = 0; i < names.length; i++) {
	        times[i] = sub.getEntries_map().get(names[i]).getValue();
	    }
	    
	    //formatação da data
	    String ano = format(times[0], 2)+format(times[1], 2);
	    String dia = format(times[3], 2);
	    String mes = format(times[2], 2);
	    String hora = format(times[4], 2);
	    String min = format(times[5], 2);
	    String seg = format(times[6], 2);
	    String milseg = format(times[7], 3);
		String data = dia+"/"+mes+"/"+ano+" "+hora+":"+min+":"+seg+":"+milseg;
				
		Pair<String,String> node = new Pair<String, String>(null, null);
		node.setFirst("dateTime");
		node.setSecond(data);
		//System.out.println(node.getFirst()+" "+node.getSecond());
		medicoes.add(node);		
	}	
	
	/**
	 * Método para formatar um valor de acordo com a quantidade de casas decimais
	 * @param value Valor 
	 * @param tamanho Quantidade de casas decimais necessárias
	 * @return String 0 Valor formatado
	 */
	private String format(String value, int tamanho) {
		String name = value.trim();
		
		if (value.length() < tamanho) {
			name = "";
			for (int i=0; i< (tamanho-value.length()); i++) {
				name += "0";
			}
			name += value;
		}
		return name;
	}	

	/**
	 * Método para obter os valores de medição do oxímetro de pulso
	 * @param arrayMedicao Lista de array com os valores organizados em pares
	 * @return MedicaoOximetroDomain 
	 */
	public MedicaoOximetroDomain medicaoOximetro(ArrayList<Pair<String,String>> arrayMedicao) {
		MedicaoOximetroDomain oximetro = new MedicaoOximetroDomain();				
		Pair<String, String> medicao;
		int j = 0;
		
		Calendar c = Calendar.getInstance();
		Date data = c.getTime();			
		
		for (int i = 0; i < arrayMedicao.size(); i++) {
			medicao = arrayMedicao.get(i);
			
			//lê o primeiro elemento do Oximeto
			if (medicao.getFirst().equals("HANDLE")) {
				i++;
				medicao = arrayMedicao.get(i);				
				String metricId = "";
				String unit_cod = ""; 		
				String unit = "";
				float value = 0;

				j = i;
				while ((!medicao.getFirst().equals("HANDLE")) && (j < arrayMedicao.size())) {
					System.out.println(medicao.getFirst() + " " + medicao.getSecond());
					
					if (medicao.getFirst().equals("metric-id")) {
						metricId = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit-code")) {
						unit_cod = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit")) {
						unit = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("value")) {
						value = Float.parseFloat(medicao.getSecond());
					}
					
					if (medicao.getFirst().equals("dateTime")) {		
						try {
							data = (Date) formatter.parse(medicao.getSecond());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						//data = medicao.getSecond();
					}
					j++;
					if (j < arrayMedicao.size()) {
						medicao = arrayMedicao.get(j);
					}
				}
				j--;
				i=j;
				
				if (metricId.equals("19384")) { //spo2
					oximetro.setSpo2(value);
					oximetro.setSpo2Unidade(unit);
				}
				
				if (metricId.equals("18458")) { //pulse rate
					oximetro.setTaxaPulso(value);
					oximetro.setTaxaPulsoUnidade(unit);
				}
			}
		}
		
		oximetro.setDataHora(data);	
		return oximetro;
	}

	/**
	 * Método para obter os valores de medição da balança
	 * @param arrayMedicao Lista de array com os valores organizados em pares
	 * @return MedicaoBalancaDomain
	 */
	public MedicaoBalancaDomain medicaoBalanca(ArrayList<Pair<String,String>> arrayMedicao) {
		MedicaoBalancaDomain balanca = new MedicaoBalancaDomain();				
		Pair<String, String> medicao;
		int j = 0;

		Calendar c = Calendar.getInstance();
		Date data = c.getTime();
		
		for (int i = 0; i < arrayMedicao.size(); i++) {
			medicao = arrayMedicao.get(i);
			
			//lê o primeiro elemento da Balanca
			if (medicao.getFirst().equals("HANDLE")) {
				i++;
				medicao = arrayMedicao.get(i);				
				String metricId = "";
				String unit_cod = ""; 		
				String unit = "";
				float value = 0;
				
				j = i;
				while ((!medicao.getFirst().equals("HANDLE")) && (j < arrayMedicao.size())) {
					System.out.println(medicao.getFirst() + " " + medicao.getSecond());
					
					if (medicao.getFirst().equals("metric-id")) {
						metricId = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit-code")) {
						unit_cod = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit")) {
						unit = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("value")) {
						value = Float.parseFloat(medicao.getSecond());
					}
					
					if (medicao.getFirst().equals("dateTime")) {		
						try {
							data = (Date) formatter.parse(medicao.getSecond());
							System.out.println("Data formatada: "+data.toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						//data = medicao.getSecond();
					}
					
					j++;
					if (j < arrayMedicao.size()) {
						medicao = arrayMedicao.get(j);
					}
				}
				j--;
				i=j;
				
				if (metricId.equals("57664")) { //Body Weight
					balanca.setPeso(value);
					balanca.setPesoUnidade(unit);
				}
				
				if (metricId.equals("57668")) { //Body height 
					balanca.setAltura(value);
					balanca.setuAlturaUnidade(unit);
				}
				
				if (metricId.equals("57680")) { //Body Mass
					balanca.setMassa(value);
					balanca.setMassaUnidade(unit);
				}
				
			}
		}
		balanca.setDataHora(data);
		return balanca;
	}

	/**
	 * Método para obter os valores de medição do medidor de pressão arterial
	 * @param arrayMedicao Lista de array com os valores organizados em pares
	 * @return MedicaoPressaoDomain
	 */
	public MedicaoPressaoDomain medicaoPressao(ArrayList<Pair<String, String>> arrayMedicao) {
		MedicaoPressaoDomain pressao = new MedicaoPressaoDomain();				
		Pair<String, String> medicao;
		int j = 0;

		Calendar c = Calendar.getInstance();
		Date data = c.getTime();
		
		for (int i = 0; i < arrayMedicao.size(); i++) {
			medicao = arrayMedicao.get(i);
			
			//lê o primeiro elemento do Oximeto
			if (medicao.getFirst().equals("HANDLE")) {
				i++;
				medicao = arrayMedicao.get(i);				
				String metricId = "";
				String unit_cod = ""; 		
				String unit = "";
				float value = 0;
				String compound = "";
				
				j = i;
				while ((!medicao.getFirst().equals("HANDLE")) && (j < arrayMedicao.size())) {
					System.out.println(medicao.getFirst() + " " + medicao.getSecond());
					
					if (medicao.getFirst().equals("metric-id")) {
						metricId = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit-code")) {
						unit_cod = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit")) {
						unit = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("value")) {
						value = Float.parseFloat(medicao.getSecond());
					}
					
					if (medicao.getFirst().equals("compound")) {
						compound = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("dateTime")) {		
						try {
							data = (Date) formatter.parse(medicao.getSecond());
							System.out.println("Data formatada: "+data.toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						//data = medicao.getSecond();
					}
					
					j++;
					if (j < arrayMedicao.size()) {
						medicao = arrayMedicao.get(j);
					}
				}
				j--;
				i=j;
				
				if (metricId.equals("18474")) { //Pulse Rate
					pressao.setTaxaPulso(value);
					pressao.setTaxaPulsoUnidade(unit);
				}
				
				if (metricId.equals("18948")) { //Blood Pressure
					pressao.setPressaoDiastolicaUnidade(unit);
					pressao.setPressaoSistolicaUnidade(unit);
					pressao.setPressaoMediaUnidade(unit);
					
					adicionaValores(pressao, compound);	
				}
			}
		}
		pressao.setDataHora(data);
		
		return pressao;
	}

	/**
	 * Método para organizar os valores de pressão arterial de acordo com a codificação definida para ISO 11073
	 * @param pressao Representação do objeto MedicaoPressaoDomain
	 * @param compound Conteúdo referente ao valor da medição. Obs.: Os valores de pressão arterial são separados por ":"
	 */
	private void adicionaValores(MedicaoPressaoDomain pressao, String compound) {
		String aux = "";
		String[] valores = compound.split(":");
		
		String metric_id="";
		float value = 0;
		String[] expressao1;
		String[] expressao2;
		for(int i=0; i < valores.length; i=i+2) {
			expressao1 = valores[i].split("=");		//metric_id			
			expressao2 = valores[i+1].split("="); 	//value
			if(expressao1[1].equals("18949")){	//pressao sistolica
				pressao.setPressaoSistolica(Float.parseFloat(expressao2[1]));
			}
			
			if(expressao1[1].equals("18950")){	//pressao sistolica
				pressao.setPressaoDiastolica(Float.parseFloat(expressao2[1]));
			}
			
			if(expressao1[1].equals("18951")){	//pressao sistolica
				pressao.setPressaoMedia(Float.parseFloat(expressao2[1]));
			}
		}		
	}

	public MedicaoIcgDomain medicaoIcg(ArrayList<Pair<String, String>> arrayMedicao) {
		MedicaoIcgDomain icg = new MedicaoIcgDomain();				
		Pair<String, String> medicao;
		int j = 0;

		Calendar c = Calendar.getInstance();
		Date data = c.getTime();
		
		for (int i = 0; i < arrayMedicao.size(); i++) {
			medicao = arrayMedicao.get(i);
			
			//lê o primeiro elemento do Icg
			if (medicao.getFirst().equals("HANDLE")) {
				i++;
				medicao = arrayMedicao.get(i);				
				String metricId = "";
				String unit_cod = ""; 		
				String unit = "";
				float value = 0;
				
				j = i;
				while ((!medicao.getFirst().equals("HANDLE")) && (j < arrayMedicao.size())) {
					System.out.println(medicao.getFirst() + " " + medicao.getSecond());
					
					if (medicao.getFirst().equals("metric-id")) {
						metricId = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit-code")) {
						unit_cod = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("unit")) {
						unit = medicao.getSecond();
					}
					
					if (medicao.getFirst().equals("value")) {
						value = Float.parseFloat(medicao.getSecond());
					}
					
					if (medicao.getFirst().equals("dateTime")) {		
						try {
							data = (Date) formatter.parse(medicao.getSecond());
							System.out.println("Data formatada: "+data.toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						//data = medicao.getSecond();
					}
					
					j++;
					if (j < arrayMedicao.size()) {
						medicao = arrayMedicao.get(j);
					}
				}
				j--;
				i=j;
				
				if (metricId.equals("18700")) { //Cardiac Index
					icg.setIndiceCardiaco(value);
					icg.setIndiceCardiacoUnidade(unit);
				}
				
				if (metricId.equals("19204")) { //Cardiac Output 
					icg.setDebitoCardiaco(value);
					icg.setDebitoCardiacoUnidade(unit);
				}
				
				if (metricId.equals("16770")) { //Heart Rate
					icg.setFrequenciaCardiaca(value);
					icg.setFrequenciaCardiacaUnidade(unit);
				}
				
				if (metricId.equals("20490")) { //Respiration Rate
					icg.setFrequenciaRespiratoria(value);
					icg.setFrequenciaRespiratoriaUnidade(unit);
				}
			}
		}
		icg.setDataHora(data);
		return icg;	
	}
}
