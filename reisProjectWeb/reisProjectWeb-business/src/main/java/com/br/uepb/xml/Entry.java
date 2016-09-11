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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

/**
 * Classe para interpretação dos arquivos xml seguindo o documento de especificação
 * do antidote 11073 para desenvolvedores, desenvolvido pela empresa Signove. 
 * Esta classe é responsável por organizar os elementos do aquivo xml em componentes
 * de acordo com suas responsabilidades.
 * Consultar o documento pelo link: http://oss.signove.com/images/c/c7/AntidoteProgramGuide.pdf
 * Código adaptado do projeto HAM - NUTES/UEPB
 */
public class Entry {
	
	/** Lista de entries(entidade), que armazena os demais elementos do xml */
	private List<Entry> entries =  new ArrayList<Entry>();
	/** Mapa de entidades */
	private Map<String, Entry> entries_map = new HashMap<String, Entry>();
	/** componente meta do xml */
	private Map<String, String> meta;
	/** Par de elementos, o primeiro valor representa o valor booleano
	 * Se for true, significa que o elemento é do tipo compound,
	 * se for false, o elemento é do tipo basic */
	private Pair<Boolean, Element> node;
	/** Elementos do xml */
	private String name, dtype, value;
	/** Variável booleana para informar se a entry é do tipo compound ou simple
	 * Se a variável for true, significa que o elemento é compound, 
	 * caso contrário o elemento é simple */
	private Boolean compound;
	
	/**
	 * Este método separa os componentes da uma entry (entidade)
	 * @param entry Componente "entry" do xml
	 * @throws Exception Gera exceção de não conseguir interpretar os componentes do elemento "entry" 
	 */
	public Entry(Element entry) throws Exception {
		 meta = parse_meta(entry);		 				 
		 node = detect_type(entry);
		 name = parse_name(node.getSecond());
		 
		 //Se a tag for do tipo "compound"		 
		 if(node.getFirst()){ 
			 Pair<List<Entry>,Map<String, Entry> > sub_entries = parse_children(node.getSecond());
		     entries = sub_entries.getFirst();
		     entries_map = sub_entries.getSecond();
		     compound = true;
		 }
		 else { //Se a tag for do tipo "simple"
			 Pair<String,String> simple = parse_simple(node.getSecond());
		     dtype = simple.getFirst();
		     value = simple.getSecond();		     
		     compound = false;
		 }
	}
	
	/**
	 * Método para separar o componente "meta-data" dentro de uma "entry" 
	 * @param node Elemento "entry" atual
	 * @return Map - Mapa de Strings, onde o primeiro valor é o nome do elemento
	 * e o segundo é o conteudo do elemento.
	 * @throws Exception Retorna exceção caso exista mais de um elemento do tipo "meta-data".
	 * Segundo a documentação do antidote só existe um elemento do tipo meta-data para cada entry
	 */
	private Map<String, String> parse_meta(Element node) throws Exception {		
		Map<String,String> m = new HashMap<String, String>();		
		
		if(node.getChildren("meta-data").size() > 1) {
			throw new Exception("Existe mais de um meta-data");
		}
			
		if (node.getChildren("meta-data").isEmpty()) {
			return m;
		}
		
		//Como só tem 1 filho "meta-data" ele recupera o primeiro filho do tipo "meta-data"
		Element meta = node.getChild("meta-data");
		List<Element> metas = meta.getChildren("meta");
		for (Element element : metas) {
			Pair<String, String> nameValue = parse_metadata(element);
			m.put(nameValue.getFirst(), nameValue.getSecond());			
		}
		
		return m;
	}
	
	/**
	 * Método para separar os componentes "meta" dentro de um "meta-data"
	 * @param meta Elemento "meta-data"
	 * @return Pair - Par de strings, onde o primeiro valor é o nome do elemento
	 * e o segundo é o conteúdo. Por exemplo: (HANDLE, 1).
	 * @throws Exception Retorna exceção caso não exista nenhum elemento dentro do "meta-data"
	 */
	private Pair<String, String> parse_metadata(Element meta) throws Exception {
		Pair<String, String> nameValue = new Pair<String, String>(null, null);	
		
		if(meta.getAttributes().isEmpty()) {
			throw new Exception("Nenhum atributo no meta data");
		}

		nameValue.setFirst(meta.getAttributeValue("name"));
		nameValue.setSecond(meta.getText());
		return nameValue;		
	}
	
	/**
	 * Método para informar se o elemento é do tipo compound ou simple
	 * @param node Elemento do tipo "entry"
	 * @return Pair - Par de elementos, onde o primeiro valor é um booleano. 
	 * Se a informação do booleano for true é porque o elemento é do tipo compound,
	 * caso contrário o elemento é do tipo simple. 
	 * Já o segundo valor é o conteúdo do elemento respectivo.  	 
	 * @throws Exception Retorna exceção se existir mais de um elemento do mesmo tipo
	 */
	private Pair<Boolean, Element> detect_type(Element node) throws Exception {
		Pair<Boolean, Element> fragment = new Pair<Boolean, Element>(null, null);
	    
	    if(( node.getChildren("simple").size() > 1) || (node.getChildren("compound").size() > 1) ){
	    	throw new Exception("Existe mais de um elemento simple ou compound");
	    }

	    Element simpleElement = node.getChild("simple");
	    Element compoundElement = node.getChild("compound");
	    
	    //metodo para verificar se tem elementos compound ou simple
	    fragment.setFirst(!(compoundElement == null));
	    if(fragment.getFirst())
	        fragment.setSecond(compoundElement);
	    else
	    	fragment.setSecond(simpleElement);

	    return fragment;	
	}
	
	/**
	 * Método para obter o elemento do tipo "name"
	 * @param node Elemento do tipo "entry"
	 * @return String - Retorna o conteúdo do elemento "name"
	 * @throws Exception Retorna exceção se não existir nenhum elemento "name" dentro de um componente "meta-data"
	 */
	private String parse_name(Element node) throws Exception
	{
	    Element name = node.getChild("name");
	    if(name == null){
	    	throw new Exception("Não existe nenhum nome");
	    }
	    return name.getText();
	}
	
	/**
	 * Método para obter os elementos dentro do componente entry 	
	 * @param node Elemento do tipo "entry"
	 * @return Pair - Par de elementos, onde o primeiro valor é uma lista das entidades (Entry)
	 * e o segundo é um mapa contendo o nome e o conteudo da entidade respectiva.
	 * @throws Exception Retorna uma exceção caso exista mais de um elemento do tipo "entries"
	 */
	private Pair<List<Entry>,Map<String,Entry> > parse_children(Element node) throws Exception
	{
		//Apenas cria estes objetos para instanciar
		List<Entry> entries_empty =  new ArrayList<Entry>();
		Map<String, Entry> entries_map_empty = new HashMap<String, Entry>();	
		
	    Pair<List<Entry>,Map<String,Entry> > sub_entries = new Pair<List<Entry>, Map<String,Entry>>(entries_empty, entries_map_empty);

	    if( node.getChildren("entries").size() > 1 ){
	    	throw new Exception("Compound com numero de entradas de tag errada");
	    }

	    Element entry_compound = node.getChild("entries");
	    
	    List<Element> entryList = entry_compound.getChildren();
	    for (Element element : entryList) {
	    	Entry currentEntry = new Entry(element);	    	
	    	sub_entries.getFirst().add(currentEntry);
	    	sub_entries.getSecond().put(currentEntry.name, currentEntry);
	    }
	    return sub_entries;
	}
	
	/**
	 * Método para separar o componente do tipo "simple"
	 * @param node Elemento do tipo "entry"
	 * @return Pair - Par de Strings. Onde o primeiro valor é o tipo do elemento
	 * e o segundo o conteúdo. Por exemplo: (type, float)
	 * @throws Exception Retorna exceção caso não encontre os elementos "type" e "value"
	 */
	private Pair<String,String> parse_simple(Element node) throws Exception
	{
	    Element type = node.getChild("type");
	    Element value = node.getChild("value");

	    if (type == null){
	    	throw new Exception("Erro: type");
	    }
	    if (value == null){
	    	throw new Exception("Erro: value");
	    }
	    
	    Pair<String,String> simple = new Pair<String, String>(null, null);
	    simple.setFirst(type.getText());
	    simple.setSecond(value.getText());
	    
	    return simple;
	}
	
	/**
	 * Método para obter o elemento do tipo "name"
	 * @return String
	 */
	public String getName()
	{
	    return name;
	}

	/**
	 * Método para obter a lista de entidades(Entries) armazenadas
	 * @return List - Lista de entidades (Entry)
	 */
	public List<Entry> getEntries()
	{
	    return entries;
	}

	/**
	 * Método para obter o elemento do tipo "value"
	 * @return String
	 */
	public String getValue() 
	{
	    return value;
	}
	
	/**
	 * Método para obter o mapa(map) de elementos do tipo "meta" 
	 * @return Map - Mapa de Strings. Onde o primeiro valor é o nome do elemento 
	 * e o segundo o conteúdo. Por exemplo: (metric-id, 57668)
	 */
	public Map<String, String> getMeta()
	{
	    return meta;
	}

	/**
	 * Método para obter o mapa(map) de entidades(Entry).
	 * @return Map - Mapa de Elementos. Onde o primeiro valor é o tipo de entry,
	 * que pode ser simple ou compound. E o segundo é o conteúdo da entidade.
	 */
	public Map<String, Entry> getEntries_map()
	{
	    return entries_map;
	}
}
