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
package com.br.uepb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.br.uepb.model.LoginDomain;
import com.br.uepb.model.MedicaoBalancaDomain;
import com.br.uepb.model.MedicaoIcgDomain;
import com.br.uepb.model.MedicaoOximetroDomain;
import com.br.uepb.model.MedicaoPressaoDomain;
import com.br.uepb.model.PacienteDomain;

import conexaoBD.HibernateUtil;

/**
 * Classe DAO para o objeto PacienteDomain
 * Esta classe é responsavel por pesquisar, inserir, excluir e atualizar informações 
 * na tabela mapeada com o objeto PacienteDomain
 */
public class PacienteDAO {
	
	/** Idenfiticacao da sessão atual */
	private Session sessaoAtual;
	
	/** Construtor da classe PacienteDAO */
	public PacienteDAO(){ }	

	/**
	 * Metodo para salvar ou atualizar o paciente
	 * Se o paciente ja existir, suas informações serão atualizadas,
	 * caso contrário será criado um novo paciente
	 * @param paciente Objeto de representação do paciente(PacienteDomain)
	 */
	public void salvaPaciente(PacienteDomain paciente){
		SessaoAtual().beginTransaction();
		if(ehNovoPaciente(paciente)){
			SessaoAtual().save(paciente);
		}
		else{
			SessaoAtual().update(paciente);
		}
		SessaoAtual().getTransaction().commit();
		SessaoAtual().close();
	}
	
	/**
	 * Metodo para excluir o paciente
	 * @param paciente Objeto de representação do paciente(PacienteDomain)
	 */
	public void excluiPaciente(PacienteDomain paciente){
		Session novaSessao = SessaoAtual();
		LoginDAO loginDAO = new LoginDAO();
		MedicaoOximetroDAO medicaoOxDAO = new MedicaoOximetroDAO();
		MedicaoPressaoDAO medicaoPres = new MedicaoPressaoDAO();
		MedicaoBalancaDAO medicaoB = new MedicaoBalancaDAO();
		MedicaoIcgDAO medicaoI = new MedicaoIcgDAO();
		LoginDomain login = loginDAO.obtemLoginPorPaciente(paciente.getId());		
		
		if(login != null){
			deletaLogin(login);
		}
		
		Transaction tx = SessaoAtual().beginTransaction();

		for (MedicaoOximetroDomain medicao  : medicaoOxDAO.listaMedicoesDoPaciente(paciente.getId())) {
			novaSessao.delete(medicao);
		}
		for(MedicaoPressaoDomain medicao : medicaoPres.listaMedicoesDoPaciente(paciente.getId())){
			novaSessao.delete(medicao);
		}
		for(MedicaoBalancaDomain medicao : medicaoB.listaMedicoesDoPaciente(paciente.getId())){
			novaSessao.delete(medicao);
		}
		for(MedicaoIcgDomain medicao : medicaoI.listaMedicoesDoPaciente(paciente.getId())){
			novaSessao.delete(medicao);
		}
		novaSessao.delete(paciente);
		novaSessao.flush();
		tx.commit();
	}
	
	/**
	 * Metodo para obter o objeto PacienteDomain de acordo com o id do paciente
	 * @param idPaciente Id do paciente
	 * @return PacienteDomain
	 */
	public PacienteDomain obtemPaciente(int idPaciente){
		PacienteDomain paciente = (PacienteDomain)SessaoAtual().get(PacienteDomain.class, idPaciente);
		SessaoAtual().close();
		return paciente;
	}
	
	/**
	 * Metodo para obter o ultimo paciente cadastrado
	 * @return PacienteDomain
	 */
	public PacienteDomain obtemUltimoPacienteCadastrado(){
		Query query = SessaoAtual().createQuery("FROM PacienteDomain ORDER BY id DESC").setMaxResults(1);
		PacienteDomain paciente = (PacienteDomain)query.uniqueResult();
		SessaoAtual().close();
		return paciente;
	}
	
	/**
	 * Metodo para listar todos os pacientes cadastrados
	 * @return List - Lista de Pacientes
	 */
	@SuppressWarnings("unchecked")
	public List<PacienteDomain> listaPacientes(){
		
		List<PacienteDomain> pacientes = 
				(List<PacienteDomain>)SessaoAtual().createQuery("from PacienteDomain").list();
		SessaoAtual().close();
		return pacientes;
	}
	
	/**
	 * Metodo para verificar se o paciente ja foi cadastrado
	 * Se o id do paciente já existir (for > 0) será retornado false,
	 * caso contrário será retornado true
	 * @param paciente Objeto de representação do paciente(PacienteDomain)
	 * @return boolean
	 */
	private boolean ehNovoPaciente(PacienteDomain paciente){
		if(paciente.getId() > 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Metodo para apagar o login do usuário
	 * @param login Objeto de representação do login(LoginDomain)
	 */
	private void deletaLogin(LoginDomain login){
		Transaction tx = SessaoAtual().beginTransaction();
		if(login != null){
		SessaoAtual().delete(login);
		SessaoAtual().flush();
		tx.commit();
		}
	}
	
	/**
	 * Método para retornar a sessão atual.
	 * Se não existir, será criada uma nova sessão
	 * @return Session
	 */
	private Session SessaoAtual(){
		if (sessaoAtual == null || !sessaoAtual.isOpen()){
			sessaoAtual = HibernateUtil.getSessionFactory().openSession();
		}
		return sessaoAtual;
	}
}
