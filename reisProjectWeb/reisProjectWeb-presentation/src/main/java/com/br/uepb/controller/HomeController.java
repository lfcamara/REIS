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

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.GerenciarSessaoBusiness;
import com.br.uepb.business.LoginBusiness;
import com.br.uepb.business.MedicoesBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.model.LoginDomain;
import com.br.uepb.model.MedicaoBalancaDomain;
import com.br.uepb.model.MedicaoOximetroDomain;
import com.br.uepb.model.MedicaoPressaoDomain;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/home/home.html", method = RequestMethod.GET)
	public ModelAndView homeGet(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		String login = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(login);
		if(sessao == null){
			modelAndView.setViewName("redirect:/index/login.html");
			return modelAndView;
		}
		MedicoesBusiness medicoesBusiness = new MedicoesBusiness();
		LoginBusiness loginBusiness = new LoginBusiness();
		LoginDomain loginDomain = sessao.getLoginDomain();
		loginDomain.setPaciente(loginBusiness.getPaciente(loginDomain.getLogin(), loginDomain.getSenha()));
		
		MedicaoOximetroDomain oximetro = medicoesBusiness.listaUltimaMedicaoOximetro(loginDomain.getPaciente().getId());
		MedicaoBalancaDomain balanca = medicoesBusiness.listaUltimaMedicaoBalanca(loginDomain.getPaciente().getId());
		MedicaoPressaoDomain pressao = medicoesBusiness.listaUltimaMedicaoPressao(loginDomain.getPaciente().getId());
		
		boolean verificaMedicao = false;
		if ((oximetro != null) || (balanca != null) || (pressao != null)) {
			verificaMedicao= true;
		}
		
		modelAndView.setViewName("home/home");
		modelAndView.addObject("usuario", loginDomain.getPaciente().getNome());
		modelAndView.addObject("paciente", loginDomain.getPaciente());
		modelAndView.addObject("oximetro", oximetro);
		modelAndView.addObject("balanca", balanca);
		modelAndView.addObject("pressao", pressao);
		modelAndView.addObject("verificaMedicao", verificaMedicao);
		
		return modelAndView;
	}
	
	
	

}
