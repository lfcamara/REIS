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
package servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.uepb.business.LoginBusiness;
import com.br.uepb.dao.MedicaoOximetroDAO;
import com.br.uepb.model.MedicaoOximetroDomain;

/**
 * Servlet implementation class ReceberPortaSerial
 */
public class ReceberPortaSerial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceberPortaSerial() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = "";
		String senha = "";
		String hora = "";
		String porcentagem = "";
		String saturacao = "";
		login = request.getParameter("login");
		senha = request.getParameter("senha");
		hora = request.getParameter("hora");
		porcentagem = request.getParameter("porcentagem");
		saturacao = request.getParameter("saturacao");
		PrintWriter out = response.getWriter();
		LoginBusiness loginBusiness = new LoginBusiness();
		if(loginBusiness.loginValido(login, senha)){
			MedicaoOximetroDomain oximetroDomain = new MedicaoOximetroDomain();
			MedicaoOximetroDAO oximetroDAO = new MedicaoOximetroDAO();
			oximetroDomain.setDataHora(new Date());
			oximetroDomain.setPaciente(loginBusiness.getPaciente(login, senha));
			oximetroDomain.setSpo2(Double.parseDouble(porcentagem));
			oximetroDomain.setSpo2Unidade("%");
			oximetroDomain.setTaxaPulso(Double.parseDouble(saturacao));
			oximetroDomain.setTaxaPulsoUnidade("bpm");
			oximetroDAO.salvaMedicaoOximetro(oximetroDomain);
			 out.print("sucesso");//É usuário cadastrado
		 }else{
			 out.print("erro");//Não é usuário cadastrado
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
