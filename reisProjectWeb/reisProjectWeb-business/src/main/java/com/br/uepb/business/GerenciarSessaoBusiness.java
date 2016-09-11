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
package com.br.uepb.business;

import java.util.HashMap;
import java.util.Map;

import com.br.uepb.model.LoginDomain;

/**
 * Classe para gerenciar as sessões
 */
public class GerenciarSessaoBusiness {
	
	/** Lista de todas as sessões abertas. 
	 * Neste mapa o primeiro atributo é o login do usuário e o segundo a sessão */
	private static Map<String, SessaoBusiness> sessoes = new HashMap<>();
	
	/**
	 * Método para retornar a sessão de acordo com o login informado
	 * @param login Nome de login do usuário
	 * @return SessaoBusiness
	 */
	public static SessaoBusiness getSessaoBusiness(String login){
		return sessoes.get(login);
	}
	
	/**
	 * Método para adicionar uma nova sessão	 
	 * @param login Representação do objeto LoginDomain - Informações de login do usuario
	 * @param sessao Representação do objeto SessaoBusiness - Informações da sessão
	 */
	public static void addSessaoBusiness(LoginDomain login, SessaoBusiness sessao){
		if(!sessoes.containsKey(login.getLogin())){
			sessao.setLoginDomain(login);
			sessoes.put(login.getLogin(), sessao);
		}
	}
	
	/**
	 * Método para remover uma sessão
	 * @param login Nome de login do usuário
	 */
	public static void removeSessao(String login){
		try {
			sessoes.remove(login);
		} catch (Exception e) {
		}
	}

}
