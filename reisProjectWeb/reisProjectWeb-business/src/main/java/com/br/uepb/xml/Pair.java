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

/**
 * Classe para armazenar de pares de elementos.
 * Esta classe é responsável por organizar a informação em pares de elementos.
 * Onde o primeiro valor geralmente representa o nome do elemento e o segundo valor, o conteúdo. 
 * Código adaptado do projeto HAM - NUTES/UEPB
 */
public class Pair <T, U>  {	
	/** Representação do primeiro elemento */
	private T first;
	/** Representação do segundo elemento */
	private U second;
	/** Código hash do elemento */
	private transient final int hash;

	/**
	 * Método construtor da classe que recebe os parâmetros para armazenar o primeiro e segundo valor
	 * @param _first Representação do primeiro elemento.
	 * @param _second Representação do segundo elemento.
	 */
	public Pair( T _first, U _second )
	{
		this.first = _first;
		this.second = _second;
		hash = (first == null? 0 : first.hashCode() * 31)
				+(second == null? 0 : second.hashCode());
	}

	/**
	 * Método para obter o primeiro elemento do par(pair)
	 * @return T - Representação do primeiro elemento
	 */
	public T getFirst()
	{
		return first;
	}
	
	/**
	 * Método para informar o primeiro elemento do par(pair)
	 * @param _first Representação do primeiro elemento
	 */
	public void setFirst(T _first) {
		first = _first;
	}
	
	/**
	 * Método para informar o segundo elemento do par(pair)
	 * @param _second - Representação do segundo elemento
	 */
	public void setSecond(U _second) {
		second = _second;
	}
	
	/**
	 * Método para obter o segundo elemento do par(pair) 
	 * @return U - Representação do segundo elemento
	 */
	public U getSecond()
	{
		return second;
	}
	
	/**
	 * Método para retornar o código hash do elemento
	 */
	@Override
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Método para verificar se dois objetos são iguais 
	 */
	@Override
	public boolean equals( Object oth )
	{
		if ( this == oth )
		{
			return true;
		}
		if ( oth == null || !(getClass().isInstance( oth )) )
		{
			return false;
		}
		Pair<T, U> other = getClass().cast( oth );
			return (first == null? other.first == null : first.equals( other.first ))
			 && (second == null? other.second == null : second.equals( other.second ));
	}
}
