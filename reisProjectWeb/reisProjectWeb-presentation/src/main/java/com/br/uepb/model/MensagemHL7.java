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

public class MensagemHL7 {
	private String endereco;
	private String porta;
	private String mensagem;
	
	private boolean habilita_balanca = false;
	private boolean habilita_oximetro = false;
	private boolean habilita_pressao = false;
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getPorta() {
		return porta;
	}
	public void setPorta(String porta) {
		this.porta = porta;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public boolean getHabilita_balanca() {
		return habilita_balanca;
	}
	public void setHabilita_balanca(boolean habilita_balanca) {
		this.habilita_balanca = habilita_balanca;
	}
	public boolean getHabilita_oximetro() {
		return habilita_oximetro;
	}
	public void setHabilita_oximetro(boolean habilita_oximetro) {
		this.habilita_oximetro = habilita_oximetro;
	}
	public boolean getHabilita_pressao() {
		return habilita_pressao;
	}
	public void setHabilita_pressao(boolean habilita_pressao) {
		this.habilita_pressao = habilita_pressao;
	}
}
