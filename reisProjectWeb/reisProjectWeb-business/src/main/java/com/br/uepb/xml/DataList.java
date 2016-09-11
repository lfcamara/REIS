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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Classe para interpretação dos arquivos xml seguindo o documento de especificação
 * do antidote 11073 para desenvolvedores, desenvolvido pela empresa Signove. 
 * Esta classe é responsável por interpretar os elementos  data-list do aquivo xml.
 * Consultar o documento pelo link: http://oss.signove.com/images/c/c7/AntidoteProgramGuide.pdf
 * Código adaptado do projeto HAM - NUTES/UEPB
 */
public class DataList {
	
	/** Lista de entidades(entry) */
	private List<Entry> entries =  new ArrayList<Entry>();
	/** Mapa(map) de elementos */
	private Map<String, Entry> entries_map = new HashMap<String, Entry>();
	
	/**
	 * Método construtor da classe
	 * @param pathXML Caminho do arquivo xml
	 * @throws Exception Retorna exceção se não conseguir interpretar o documento xml 
	 */
	public DataList(String pathXML) throws Exception {
		File docXML = new File(pathXML);
		SAXBuilder sBuilder = new SAXBuilder();
		
		Document document = null;
		try {
			document = sBuilder.build(docXML);
		} catch (JDOMException e) {
			//TODO tratar este erro
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}
		
		//recupera o primeiro elemento "root"  
		Element rootElement = document.getRootElement();
		
		//lê todos os filhos do XML
		List<Element> filhos = rootElement.getChildren();		
		for (Element element : filhos) {
			//Cria um novo Elemento aqui 
			Entry entry = new Entry(element);
			entries.add(entry); //Adiciona as entidades
			entries_map.put(element.getName(), entry); //adiciona um mapa de entidades
		}
	}
	
	/**
	 * Método para retornar a lista de entidades (Entry)
	 * @return List - Lista de entidades
	 */
	public List<Entry> getEntries() 
	{			
	    return entries;
	}
	
	/**
	 * Método para retornar o mapa(map) de elementos
	 * @return Map - Mapa de elementos. Onde o primeiro valor é o nome representativo 
	 * da entidade (compound ou simple). E o segundo é o objeto entidade(entry)  
	 */
	public Map<String, Entry> getEntries_Map() 
	{			
	    return entries_map;
	}	
}
