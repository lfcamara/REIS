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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.GerenciarSessaoBusiness;
import com.br.uepb.business.MedicoesBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.model.HistoricoDomain;
import com.br.uepb.model.MedicaoBalancaDomain;
import com.br.uepb.model.MedicaoIcgDomain;
import com.br.uepb.model.MedicaoOximetroDomain;
import com.br.uepb.model.MedicaoPressaoDomain;

@Controller
public class HistoricoController {

	public List<HistoricoDomain> historico;
	
	@RequestMapping(value = "/home/historico.html", method = RequestMethod.GET)
	public ModelAndView historicoGet(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MedicoesBusiness medicoesBusiness = new MedicoesBusiness();
		
		String login = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(login);
		if(sessao == null){
			modelAndView.setViewName("redirect:/index/login.html");
			return modelAndView;
		}
				
		List<MedicaoOximetroDomain> historicoOximetro = medicoesBusiness.listaMedicoesOximetroPaciente(sessao.getLoginDomain().getPaciente().getId());
		List<MedicaoBalancaDomain> historicoBalanca = medicoesBusiness.listaMedicoesBalancaPaciente(sessao.getLoginDomain().getPaciente().getId());
		List<MedicaoPressaoDomain> historicoPressao = medicoesBusiness.listaMedicoesPressaoPaciente(sessao.getLoginDomain().getPaciente().getId());
		List<MedicaoIcgDomain> historicoIcg = medicoesBusiness.listaMedicoesIcgPaciente(sessao.getLoginDomain().getPaciente().getId());
		
		modelAndView.setViewName("home/historico");
		modelAndView.addObject("usuario", sessao.getLoginDomain().getPaciente().getNome());
		modelAndView.addObject("historicoOximetro", historicoOximetro);
		modelAndView.addObject("historicoBalanca", historicoBalanca);
		modelAndView.addObject("historicoPressao", historicoPressao);
		modelAndView.addObject("historicoICG", historicoIcg);
		modelAndView.addObject("status", null);
		modelAndView.addObject("mensagemErro", null);
		
		return modelAndView;
	}	
}
