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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.GerenciarSessaoBusiness;
import com.br.uepb.business.MedicoesBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.model.LoginDomain;
import com.br.uepb.model.MedicaoBalancaDomain;
import com.br.uepb.model.MedicaoOximetroDomain;
import com.br.uepb.model.MedicaoPressaoDomain;
import com.br.uepb.model.UploadItem;

@Controller
public class MedicaoController {

	MedicoesBusiness medicoesBusiness = new MedicoesBusiness();

	@RequestMapping(value = "/home/medicao.html", method = RequestMethod.GET)
	public ModelAndView medicaoGet(Model model, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		
		String login = request.getSession().getAttribute("login").toString();
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(login);
		if(sessao == null){
			modelAndView.setViewName("redirect:/index/login.html");
			return modelAndView;
		}
		UploadItem uploadItem = new UploadItem();
		
		model.addAttribute("uploadItem", uploadItem);
		modelAndView.addObject("usuario", sessao.getLoginDomain().getPaciente().getNome());
		modelAndView.setViewName("home/medicao");
		
		modelAndView.addObject("oximetro", null);
		modelAndView.addObject("balanca", null);
		modelAndView.addObject("pressao", null);
		modelAndView.addObject("tipoDispositivo", null);
		
		return modelAndView;
	}

	// POST: Do Upload
	@RequestMapping(value = "/home/medicao.html", method = RequestMethod.POST)
	public ModelAndView medicaoPost(HttpServletRequest request, 
			Model model, 
			@ModelAttribute("uploadItem") UploadItem uploadItem) {

		String login = request.getSession().getAttribute("login").toString();
		
		SessaoBusiness sessao = GerenciarSessaoBusiness.getSessaoBusiness(login);
		MedicoesBusiness medicoesBusiness = new MedicoesBusiness();
		LoginDomain loginDomain = sessao.getLoginDomain();
		
		ModelAndView modelAndView = new ModelAndView();
		String mensagem = "";
		String status = "";
		
		@SuppressWarnings("deprecation")
		String uploadRootPath = request.getRealPath(File.separator);
		
		File uploadRootDir = new File(uploadRootPath);

		// Cria o diretório se não existir
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		CommonsMultipartFile fileData = uploadItem.getFileData();

		// Nome do arquivo cliente
		String name = fileData.getOriginalFilename();

		if (name != null && name.length() > 0) {
			try {
				// Create the file on server
				File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(fileData.getBytes());
				stream.close();
				// Salvou o arquivo xml
				
				String tipo_dispositivo = uploadItem.getTipo_dispositivo();
				String arquivo = serverFile.toString();
				
				if (tipo_dispositivo.equals("0")) { // oximetro
					modelAndView.addObject("tipoDispositivo", 0);
					if (medicoesBusiness.medicaoOximetro(arquivo, login)) {
						//medicoesBusiness.medicaoOximetro(arquivo);
						mensagem = "Medição Oximetro cadastrada com sucesso!";
						status = "0";

						MedicaoOximetroDomain oximetro = medicoesBusiness.listaUltimaMedicaoOximetro(loginDomain.getPaciente().getId());						
						modelAndView.addObject("oximetro", oximetro);
						modelAndView.addObject("balanca", null);
						modelAndView.addObject("pressao", null);
						
					} else {
						mensagem = "Erro ao cadastrar Oximetro arquivo XML!";
						status = "1";
					}

				} else if (tipo_dispositivo.equals("1")) { // balanca
					modelAndView.addObject("tipoDispositivo", 1);
					if (medicoesBusiness.medicaoBalanca(arquivo, login)) {
						mensagem = "Medição Balança cadastrada com sucesso!";
						status = "0";
						
						MedicaoBalancaDomain balanca = medicoesBusiness.listaUltimaMedicaoBalanca(loginDomain.getPaciente().getId());						
						modelAndView.addObject("oximetro", null);
						modelAndView.addObject("balanca", balanca);
						modelAndView.addObject("pressao", null);
					} else {
						mensagem = "Erro ao cadastrar Balança arquivo XML!";
						status = "1";						
					}

				} else if (tipo_dispositivo.equals("2")) { // pressao
					modelAndView.addObject("tipoDispositivo", 2);
					if (medicoesBusiness.medicaoPressao(arquivo, login)) {
						mensagem = "Medição Pressão cadastrada com sucesso!";
						status = "0";

						MedicaoPressaoDomain pressao = medicoesBusiness.listaUltimaMedicaoPressao(loginDomain.getPaciente().getId());						
						modelAndView.addObject("oximetro", null);
						modelAndView.addObject("balanca", null);
						modelAndView.addObject("pressao", pressao);
					} else {
						mensagem = "Erro ao cadastrar Pressão arquivo XML!";
						status = "1";
					}

				} else {
					mensagem = "Erro ao cadastrar arquivo XML!";
					status = "1";
				}

			} catch (Exception e) {
				mensagem = "Erro ao cadastrar arquivo XML!";
				status = "1";
			}
		}	
		else {
			mensagem = "O arquivo não foi informado ou está vazio";
			status = "1";
		}
		
		if (status == "1") {
			modelAndView.addObject("tipoDispositivo", null);
			modelAndView.addObject("oximetro", null);
			modelAndView.addObject("balanca", null);
			modelAndView.addObject("pressao", null);
		}
		
		model.addAttribute("mensagem", mensagem);
		model.addAttribute("status", status);
		modelAndView.setViewName("home/medicao");
		return modelAndView;
	}

}
