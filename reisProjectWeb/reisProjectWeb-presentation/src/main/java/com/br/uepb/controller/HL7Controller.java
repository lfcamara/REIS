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
package com.br.uepb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.GerenciarSessaoBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.hl7.GerarMensagemHL7;
import com.br.uepb.model.HistoricoDomain;
import com.br.uepb.model.MensagemHL7;

import ca.uhn.hl7v2.HL7Exception;

@Controller
public class HL7Controller {

	public List<HistoricoDomain> historico;
	
	@RequestMapping(value = "/home/hl7.html", method = RequestMethod.GET)
	public ModelAndView hl7Get(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();		
		String login = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(login);
		if(sessao == null){
			modelAndView.setViewName("redirect:/index/login.html");
			return modelAndView;
		}
		
		MensagemHL7 mensagemHL7 = new MensagemHL7();
		modelAndView.addObject("mensagemHL7", mensagemHL7);
		modelAndView.addObject("usuario", sessao.getLoginDomain().getPaciente().getNome());
		modelAndView.addObject("status", null);
		modelAndView.addObject("mensagemErro", "");
		
		modelAndView.setViewName("home/hl7");
		
		return modelAndView;
	}

	@RequestMapping(value="/home/hl7.html", method = RequestMethod.POST)
	public ModelAndView enviarMensagemPost(@ModelAttribute("mensagemHL7") MensagemHL7 mensagemHL7, HttpServletRequest request,
		   Model model) {	
		ModelAndView modelAndView = new ModelAndView();
		String login = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(login);
		String mensagemErro = "";
		String status = "";
		GerarMensagemHL7 geraMensagemHL7 = new GerarMensagemHL7(sessao.getLoginDomain().getPaciente().getId());
		
		
		try {
			String mensagem = geraMensagemHL7.criarMensagem(mensagemHL7.getHabilita_oximetro(),
															mensagemHL7.getHabilita_balanca(),
															mensagemHL7.getHabilita_pressao());
			mensagemHL7.setMensagem(mensagem);
			status = "0"; //Status OK
		} catch (HL7Exception e) {
			mensagemErro = "Erro ao cadastrar Oximetro arquivo XML!";
			status = "1";
		} catch (IOException e) {
			mensagemErro = "Erro ao cadastrar Oximetro arquivo XML!";
			status = "1";
		}

		model.addAttribute("mensagemErro", mensagemErro);
		model.addAttribute("status", status);
		modelAndView.addObject("mensagemHL7", mensagemHL7);
		
		return modelAndView;		
	}

}
