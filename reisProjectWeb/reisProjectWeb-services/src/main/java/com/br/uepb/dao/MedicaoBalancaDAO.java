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

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.br.uepb.model.MedicaoBalancaDomain;

import conexaoBD.HibernateUtil;

/**
 * Classe DAO para o objeto MedicaoBalancaDomain
 * Esta classe é responsavel por pesquisar, inserir, excluir e atualizar informacoes 
 * na tabela mapeada com o objeto MedicaoBalancaDomain
 */
public class MedicaoBalancaDAO {
	
	/** Idenfiticacao da sessao atual */
	private Session sessaoAtual;
	
	/**
	 * Metodo para salvar ou atualizar a medição da balança
	 * Se o parâmetro medição ja existir, as informações serão atualizadas,
	 * caso contrário, será armazenada uma nova medição
	 * @param medicao Objeto de representação da medição da balança(MedicaoBalancaDomain)
	 */
	public void salvaMedicaoBalanca(MedicaoBalancaDomain medicao){
		SessaoAtual().beginTransaction();
		if(ehNovaMedicao(medicao)){
			SessaoAtual().save(medicao);
		}
		else{
			SessaoAtual().update(medicao);
		}
		SessaoAtual().getTransaction().commit();
		SessaoAtual().close();
	}
	
	/**
	 * Metodo para excluir a medição da balança
	 * @param medicao Objeto de representação da medição da balança(MedicaoBalancaDomain)
	 */
	public void excluiMedicaoBalanca(MedicaoBalancaDomain medicao){
		Session novaSessao = SessaoAtual();
		Transaction tx = SessaoAtual().beginTransaction();
		novaSessao.delete(medicao);
		novaSessao.flush();
		tx.commit();
	}
	
	/**
	 * para excluir a medição da balança de acordo com o id do paciente
	 * @param idPaciente Id do Paciente
	 * @return Retorna true se conseguir excluir a medicao e false caso contrário
	 */
	public Boolean excuiMedicaoBalancaPorPaciente(int idPaciente) {
		try {
			Session novaSessao = SessaoAtual();
			Transaction tx = SessaoAtual().beginTransaction();
			
			novaSessao.createQuery("delete from MedicaoBalancaDomain where paciente.id = :id").
								   setParameter("id", idPaciente).executeUpdate();
			novaSessao.flush();
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
				
	}
	
	/**
	 * Metodo para obter a medição da balança de acordo com o id da medição
	 * @param idBalanca int
	 * @return MedicaoBalancaDomain
	 */
	public MedicaoBalancaDomain obtemMedicaoBalanca(int idBalanca){
		MedicaoBalancaDomain medicao = (MedicaoBalancaDomain)SessaoAtual().get(MedicaoBalancaDomain.class, idBalanca);
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Metodo para listar todas as medições
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoBalancaDomain> listaMedicoes(){
		
		List<MedicaoBalancaDomain> medicao = 
				(List<MedicaoBalancaDomain>)SessaoAtual().createQuery("from MedicaoBalancaDomain").list();
		
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Metodo para listar todas as medições de um paciente
	 * @param idPaciente int
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoBalancaDomain> listaMedicoesDoPaciente(int idPaciente){
		List<MedicaoBalancaDomain> medicao =
				(List<MedicaoBalancaDomain>)SessaoAtual().createQuery(
						"from MedicaoBalancaDomain where  paciente.id =" + idPaciente).list();
		
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Metodo para listar a ultima medição do paciente cadastrada
	 * @param idPaciente int
	 * @return MedicaoBalancaDomain
	 */	
	public MedicaoBalancaDomain obtemUltimaMedicao(int idPaciente){
		String comando = "select mb.* from medicao_balanca mb " +
				"where mb.paciente_id = :idPaciente " +
				"order by data_hora desc " +
				"limit 1";
		SQLQuery query = SessaoAtual().createSQLQuery(comando);
		query.setParameter("idPaciente", idPaciente);
		query.addEntity(MedicaoBalancaDomain.class);
		
		MedicaoBalancaDomain medicao = (MedicaoBalancaDomain) query.uniqueResult();
		return medicao;
	}
	
	/**
	 * Metodo para verificar se a medição ja foi cadastrada
	 * Se o id da medição já existir (for > 0) será retornado false,
	 * caso contrário será retornado true
	 * @param medicao Objeto de representação da medição da balança(MedicaoBalancaDomain)
	 * @return boolean
	 */
	private boolean ehNovaMedicao(MedicaoBalancaDomain medicao){
		if(medicao.getId() > 0){
			return false;
		}
		else{
			return true;
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
