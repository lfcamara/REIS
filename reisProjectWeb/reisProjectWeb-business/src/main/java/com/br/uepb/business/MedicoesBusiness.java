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

import java.util.ArrayList;
import java.util.List;

import com.br.uepb.dao.MedicaoBalancaDAO;
import com.br.uepb.dao.MedicaoIcgDAO;
import com.br.uepb.dao.MedicaoOximetroDAO;
import com.br.uepb.dao.MedicaoPressaoDAO;
import com.br.uepb.model.LoginDomain;
import com.br.uepb.model.MedicaoBalancaDomain;
import com.br.uepb.model.MedicaoIcgDomain;
import com.br.uepb.model.MedicaoOximetroDomain;
import com.br.uepb.model.MedicaoPressaoDomain;
import com.br.uepb.model.PacienteDomain;
import com.br.uepb.xml.DataList;
import com.br.uepb.xml.Medicoes;
import com.br.uepb.xml.Pair;

/**
 * Classe para as regras de negócio das medições dos dispositivos médicos:
 * Oxímetro de pulso, balança e medidor de pressão arterial
 */
public class MedicoesBusiness {

	/** Representação do objeto MedicaoBalancaDAO */
	private MedicaoBalancaDAO medicaoBalancaDAO = new MedicaoBalancaDAO();
	/** Representação do objeto MedicaoOximetroDAO */
	private MedicaoOximetroDAO medicaoOximetroDAO = new MedicaoOximetroDAO();
	/** Representação do objeto MedicaoPressaoDAO */
	private MedicaoPressaoDAO medicaoPressaoDAO = new MedicaoPressaoDAO();
	/** Representação do objeto MedicaoMonitorDAO */
	private MedicaoIcgDAO medicaoIcgDAO = new MedicaoIcgDAO();
	/** Representação do objeto LoginDomain */
	private LoginDomain loginDomain;
	
	////////////////////////////////////ICG///////////////////////////////////////////
	/**
	 * Método para obter a medicao do monitor da base de dados
	 * @param idIcg Id da Medicao
	 * @return MedicaoIcgDomain - Representação do objeto Monitor (MedicaoMonitorDomain) 
	 */
	public MedicaoIcgDomain obtemMedicaoMonitor(int idIcg) {
		MedicaoIcgDomain medicaoIcgDomain =  medicaoIcgDAO.obtemMedicaoIcg(idIcg);
		return medicaoIcgDomain;
	}
	
	/**
	 * Método para obter a medição do icg. 
	 * Se a leitura da medição ocorrer sem erros o método retorna true,
	 * caso contrário será retornado false
	 * @param pathXML Caminho do arquivo XML
	 * @param login Login do usuário
	 * @return boolean
	 */
	public Boolean medicaoIcg(String pathXML, String login) {		
		try {
			DataList dataList = new DataList(pathXML);
			Medicoes medicoes = new Medicoes(dataList);
			//Método para gerar um ArrayList de Par;
			ArrayList<Pair<String,String>> med = medicoes.getMedicoes();
			
			MedicaoIcgDomain medicaoIcgDomain =  medicoes.medicaoIcg(med);
			loginDomain = GerenciarSessaoBusiness.getSessaoBusiness(login).getLoginDomain();//SessaoBusiness.getLoginDomain();//loginDAO.obtemLogin(SessaoBusiness.getLoginDomain().getLogin(), SessaoBusiness.getLoginDomain().getSenha());			
			PacienteDomain paciente = loginDomain.getPaciente();			
			medicaoIcgDomain.setPaciente(paciente);			
			medicaoIcgDAO.salvaMedicaoIcg(medicaoIcgDomain);
			return true;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Método para obter a lista de todas as medições do oxímetro de um paciente 
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	public List<MedicaoIcgDomain> listaMedicoesIcgPaciente(int idPaciente){
		return  medicaoIcgDAO.listaMedicoesDoPaciente(idPaciente);
	}
	
	/**
	 * Método para obter a ultima medição do oxímetro
	 * @param idPaciente Id do paciente
	 * @return MedicaoOximetroDomain
	 */
	public MedicaoIcgDomain listaUltimaMedicaoIcg(int idPaciente){
		return medicaoIcgDAO.obtemUltimaMedicao(idPaciente);
	}
	
	////////////////////////////////////OXIMETRO///////////////////////////////////////////
	/**
	 * Método para obter a medicao do oximetro da base de dados
	 * @param idOximetro Id da Medicao
	 * @return MedicaoOximetroDomain - Representação do objeto Oximetro (MedicaoOximetroDomain) 
	 */
	public MedicaoOximetroDomain obtemMedicaoOximetro(int idOximetro) {
		MedicaoOximetroDomain medicaoOximetroDomain =  medicaoOximetroDAO.obtemMedicaoOximetro(idOximetro);
		return medicaoOximetroDomain;
	}
	
	/**
	 * Método para obter a medição do oxímetro. 
	 * Se a leitura da medição ocorrer sem erros o método retorna true,
	 * caso contrário será retornado false
	 * @param pathXML Caminho do arquivo XML
	 * @param login Login do usuário
	 * @return boolean
	 */
	public Boolean medicaoOximetro(String pathXML, String login) {		
		try {
			DataList dataList = new DataList(pathXML);
			Medicoes medicoes = new Medicoes(dataList);
			//Método para gerar um ArrayList de Par;
			ArrayList<Pair<String,String>> med = medicoes.getMedicoes();
			
			MedicaoOximetroDomain medicaoOximetroDomain =  medicoes.medicaoOximetro(med);
			loginDomain = GerenciarSessaoBusiness.getSessaoBusiness(login).getLoginDomain();//SessaoBusiness.getLoginDomain();//loginDAO.obtemLogin(SessaoBusiness.getLoginDomain().getLogin(), SessaoBusiness.getLoginDomain().getSenha());			
			PacienteDomain paciente = loginDomain.getPaciente();			
			medicaoOximetroDomain.setPaciente(paciente);			
			medicaoOximetroDAO.salvaMedicaoOximetro(medicaoOximetroDomain);
			return true;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Método para obter a lista de todas as medições do oxímetro de um paciente 
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	public List<MedicaoOximetroDomain> listaMedicoesOximetroPaciente(int idPaciente){
		return  medicaoOximetroDAO.listaMedicoesDoPaciente(idPaciente);
	}
	
	/**
	 * Método para obter a ultima medição do oxímetro
	 * @param idPaciente Id do paciente
	 * @return MedicaoOximetroDomain
	 */
	public MedicaoOximetroDomain listaUltimaMedicaoOximetro(int idPaciente){
		return medicaoOximetroDAO.obtemUltimaMedicao(idPaciente);
	}
	
	////////////////////////////////////BALANCA///////////////////////////////////////////
	/**
	 * Método para obter a medição da balança.
	 * Se a leitura da medição ocorrer sem erros o método retorna true,
	 * caso contrário será retornado false
	 * @param pathXML Caminho do arquivo XML
	 * @param login Nome de login do usuário
	 * @return boolean
	 */
	public Boolean medicaoBalanca(String pathXML, String login) {		
		try {
			DataList dataList = new DataList(pathXML);
			Medicoes medicoes = new Medicoes(dataList);
			//Método para gerar um ArrayList de Par;
			ArrayList<Pair<String,String>> med = medicoes.getMedicoes();
			
			MedicaoBalancaDomain medicaoBalancaDomain =  medicoes.medicaoBalanca(med);
			loginDomain = GerenciarSessaoBusiness.getSessaoBusiness(login).getLoginDomain();//SessaoBusiness.getLoginDomain();//loginDAO.obtemLogin(SessaoBusiness.getLoginDomain().getLogin(), SessaoBusiness.getLoginDomain().getSenha());				
			PacienteDomain paciente = loginDomain.getPaciente();			
			medicaoBalancaDomain.setPaciente(paciente);								
			medicaoBalancaDAO.salvaMedicaoBalanca(medicaoBalancaDomain);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	/**
	 * Método para obter a lista de todas as medições da balança de um paciente
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	public List<MedicaoBalancaDomain> listaMedicoesBalancaPaciente(int idPaciente){
		return  medicaoBalancaDAO.listaMedicoesDoPaciente(idPaciente);
	}
	
	/**
	 * Método para obter a lista da utima medição da balança 
	 * @param idPaciente Id do paciente
	 * @return MedicaoBalancaDomain
	 */
	public MedicaoBalancaDomain listaUltimaMedicaoBalanca(int idPaciente){
		return medicaoBalancaDAO.obtemUltimaMedicao(idPaciente);
	}
	
////////////////////////////////////MEDIDOR DE PRESSAO///////////////////////////////////////////
	/**
	 * Método para obter a medição do medidor de pressão arterial.
	 * Se a leitura da medição ocorrer sem erros o método retorna true,
	 * caso contrário será retornado false
	 * @param pathXML Caminho do arquivo XML
	 * @param login Nome de login do usuário
	 * @return boolean
	 */
	public Boolean medicaoPressao(String pathXML, String login) {
		
		try {
			DataList dataList = new DataList(pathXML);
			Medicoes medicoes = new Medicoes(dataList);
			//Método para gerar um ArrayList de Par;
			ArrayList<Pair<String,String>> med = medicoes.getMedicoes();
			
			MedicaoPressaoDomain medicaoPressaoDomain =  medicoes.medicaoPressao(med);
			loginDomain = GerenciarSessaoBusiness.getSessaoBusiness(login).getLoginDomain();//SessaoBusiness.getLoginDomain();//loginDAO.obtemLogin(SessaoBusiness.getLoginDomain().getLogin(), SessaoBusiness.getLoginDomain().getSenha());				
			PacienteDomain paciente = loginDomain.getPaciente();			
			medicaoPressaoDomain.setPaciente(paciente);								
			medicaoPressaoDAO.salvaMedicaoPressao(medicaoPressaoDomain);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Método para obter a lista de todas as medições de pressão arterial de um paciente
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	public List<MedicaoPressaoDomain> listaMedicoesPressaoPaciente(int idPaciente){
		return  medicaoPressaoDAO.listaMedicoesDoPaciente(idPaciente);
	}
	
	/**
	 * Método para obter a lista da ultima medição de pressão arterial de um paciente
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	public MedicaoPressaoDomain listaUltimaMedicaoPressao(int idPaciente){
		return medicaoPressaoDAO.obtemUltimaMedicao(idPaciente);
	}	
	
}
