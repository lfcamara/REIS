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
package com.br.uepb.validacao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe para definir os estilos de formatação dos objetos 
 */
public class FormataCampos {
	
	/**
	 * Método para formatar uma data no padrão dd/MM/yyyy HH:mm:ss
	 * @param data Data a ser formatada
	 * @return String Data formatada
	 */
	public String formataDataHora(Date data) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
		String novaData = simpleDateFormat.format(data);
		return novaData;
	}
	
	/**
	 * Método para formatar data no padrão dd/MM/yyyy
	 * @param data Data a ser formatada
	 * @return Date data formatada
	 */
	public Date formataData(String data) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date dataNascimento = null;
		
		try {			
			dataNascimento = (Date)formatter.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dataNascimento;
	}
	
	/**
	 * Método para obter a idade de acordo com a data de nascimento
	 * @param data Data de nascimento
	 * @return int Idade atual
	 */
	public int obtemIdade(String data) {
		int idade = 0;			
		Date dataNascimento = formataData(data);		
		Calendar calendarData = Calendar.getInstance();
		calendarData.setTime(dataNascimento);
		
		Date dataAtual = new Date(System.currentTimeMillis());
		Calendar calendarDataAtual = Calendar.getInstance();
		calendarDataAtual.setTime(dataAtual);

		idade = calendarDataAtual.get(Calendar.YEAR) - calendarData.get(Calendar.YEAR);		
		if (calendarDataAtual.get(Calendar.MONTH) < calendarData.get(Calendar.MONTH)) {
	        idade--;
	    }
		else {
			if ( (calendarDataAtual.get(Calendar.MONTH) == calendarData.get(Calendar.MONTH)) && 
			     (calendarDataAtual.get(Calendar.DAY_OF_MONTH) < calendarData.get(Calendar.DAY_OF_MONTH)) ) {
				idade --;
			}
		}
		return idade;
	}
}
