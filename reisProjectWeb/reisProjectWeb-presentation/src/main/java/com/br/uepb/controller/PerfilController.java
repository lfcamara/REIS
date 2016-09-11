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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.GerenciarSessaoBusiness;
import com.br.uepb.business.LoginBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.model.LoginDomain;

@Controller
public class PerfilController {

	@RequestMapping(value = "/home/perfil.html", method = RequestMethod.GET)
	public ModelAndView acessarGet(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String login = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(login);
		if(sessao == null){
			modelAndView.setViewName("redirect:/index/login.html");
			return modelAndView;
		}

		modelAndView.setViewName("home/perfil");
		modelAndView.addObject("loginDomain", sessao.getLoginDomain());
		modelAndView.addObject("usuario", sessao.getLoginDomain().getPaciente().getNome());
		
		return modelAndView;
	}

	@RequestMapping(value = "/home/perfil.html", method = RequestMethod.POST)
	public ModelAndView alterarPost(@ModelAttribute("loginDomain") LoginDomain login, Model model, HttpServletRequest request) {
		LoginBusiness loginBusiness = new LoginBusiness();
		String l = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(l);
		
		ModelAndView modelAndView = new ModelAndView();

		login.setLogin(sessao.getLoginDomain().getLogin());		
		login.setId(sessao.getLoginDomain().getId());
		login.getPaciente().setId(sessao.getLoginDomain().getPaciente().getId());
		
		if(loginBusiness.atualizar(login)){
			sessao.setLoginDomain(login);
			modelAndView.setViewName("home/perfil");
			String mensagem = "Alterações realizadas com sucesso";
			modelAndView.addObject("mensagem", mensagem);
			modelAndView.addObject("status", "0");
		}else{
			modelAndView.setViewName("home/perfil");
			String mensagem = "Erro ao fazer alterações";
			modelAndView.addObject("mensagem", mensagem);
			modelAndView.addObject("status", "1");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/excluir.html", method = RequestMethod.POST)
	public ModelAndView excluirGet(@ModelAttribute("loginExcluir") LoginDomain login, Model model, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		LoginBusiness loginBusiness = new LoginBusiness();
		String l = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(l);
		
		if(loginBusiness.excluir(sessao.getLoginDomain())){
			sessao.setLoginDomain(null);
			modelAndView.setViewName("index/index");
			String mensagem = "Todas as suas informações foram removidas";
			modelAndView.addObject("mensagem", mensagem);
			modelAndView.addObject("statusExcluir", "0");
		}else{
			sessao.setLoginDomain(null);
			modelAndView.setViewName("home/perfil");
			String mensagem = "Não foi possível excluir suas informações";
			modelAndView.addObject("mensagem", mensagem);
			modelAndView.addObject("statusExcluir", "1");
		}
		
		return modelAndView;
	}

}
