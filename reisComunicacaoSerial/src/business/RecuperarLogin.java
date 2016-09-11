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
package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.LoginDomain;

/**
 * Classe que recupera os dados de configuração do reisComunicacaoSerial. 
 * O login, a senha e a URL da página do REIS(web) para onde os dados serão enviados.
 */
public class RecuperarLogin {
	/** Caminho onde serão armazenadas(localmente) as informações do REIS para envio */
	String caminhoSave = "";
	
	/**
	 * Método para obter o caminho onde serão armazenadas(localmente) as informações do REIS para envio 
	 * @return LoginDomain - Representação do objeto LoginDomain
	 */
	public LoginDomain getSave(){
		File file2 = new File(".");
		try {
			caminhoSave = file2.getCanonicalPath()+"/save/loginDomain.reis";
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoginDomain loginDomain = (LoginDomain) restaurar(caminhoSave);
		return loginDomain;
	}

	/**
	 * Método para recuperar as informaçções do login do paciente
	 * @param caminho Caminho onde estão armazenadas(localmente) as informações do REIS para envio
	 * @return Object Objeto LoginDomain 
	 */
	private Object restaurar(String caminho) {
		  
        Object objeto = null;
       
        try {
               FileInputStream restFile = new FileInputStream(caminho);
               ObjectInputStream stream = new ObjectInputStream(restFile);

               // recupera o objeto
               objeto = stream.readObject();

               stream.close();
        } catch (Exception e) {
               e.printStackTrace();
        }
        
		/* Caso nenhum arquivo de configuração exista, então será criado com a url do REIS. 
           pode ser alterado pelo usuário em configurações */
        if(objeto==null){
        	LoginDomain loginDomain = new LoginDomain();
        	loginDomain.setLink("http://localhost:8080/reis/ReceberPortaSerial");
        	loginDomain.setLogin("");
        	loginDomain.setSenha("");
        	new SalvarLogin().salvar(loginDomain);
        	return loginDomain;
        }
        return objeto;
	}
	
	
	

}