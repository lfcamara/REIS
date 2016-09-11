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

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.br.uepb.model.MedicaoPressaoDomain;

import conexaoBD.HibernateUtil;

/**
 * Classe DAO para o objeto MedicaoPressaoDomain
 * Esta classe é responsavel por pesquisar, inserir, excluir e atualizar informações 
 * na tabela mapeada com o objeto MedicaoPressaoDomain
 */
public class MedicaoPressaoDAO {

	/** Idenfiticação da sessão atual */
	private Session sessaoAtual;
	
	/**
	 * Método para salvar ou atualizar a medição da pressão arterial
	 * Se o parametro medição já existir, as informações serão atualizadas,
	 * caso contrário, será armazenada uma nova medição
	 * @param medicaoPressao Objeto de representação da medição da pressão arterial(MedicaoPressaoDomain)
	 */
	public void salvaMedicaoPressao(MedicaoPressaoDomain medicaoPressao){
		SessaoAtual().beginTransaction();
		if(ehNovaMedicao(medicaoPressao)){
			SessaoAtual().save(medicaoPressao);
		}
		else{
			SessaoAtual().update(medicaoPressao);
		}
		SessaoAtual().getTransaction().commit();
		SessaoAtual().close();
	}
	
	/**
	 * Método para excluir a medição da pressão arterial
	 * @param medicao Objeto de representação da medição da pressão arterial(MedicaoPressaoDomain)
	 */
	public void excluiMedicaoPressao(MedicaoPressaoDomain medicao){
		Session novaSessao = SessaoAtual();
		Transaction tx = SessaoAtual().beginTransaction();
		novaSessao.delete(medicao);
		novaSessao.flush();
		tx.commit();
	}
	
	/**
	 * para excluir a medição da pressão de acordo com o id do paciente
	 * @param idPaciente Id do Paciente
	 * @return Retorna true se conseguir excluir a medicao e false caso contrário
	 */
	public Boolean excuiMedicaoPressaoPorPaciente(int idPaciente) {
		try {
			Session novaSessao = SessaoAtual();
			Transaction tx = SessaoAtual().beginTransaction();
			novaSessao.createQuery("delete from MedicaoPressaoDomain where paciente.id = :id").
								   setParameter("id", idPaciente).executeUpdate();
			novaSessao.flush();
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	/**
	 * Método para obter a medição da pressão arterial de acordo com o id da medição
	 * @param idMedicao Id da medição
	 * @return MedicaoPressaoDomain
	 */
	public MedicaoPressaoDomain obtemMedicaoPressao(int idMedicao){
		MedicaoPressaoDomain medicao = (MedicaoPressaoDomain)SessaoAtual().get(MedicaoPressaoDomain.class, idMedicao);
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Método para listar todas as medições cadastradas
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoPressaoDomain> listaMedicoesPressao(){
		
		List<MedicaoPressaoDomain> medicoes = 
				(List<MedicaoPressaoDomain>)SessaoAtual().createQuery("from MedicaoPressaoDomain").list();
		SessaoAtual().close();
		return medicoes;
	}
	
	/**
	 * Método para listar todas as medições de acordo com o id do paciente
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoPressaoDomain> listaMedicoesDoPaciente(int idPaciente){
		List<MedicaoPressaoDomain> medicao =
				(List<MedicaoPressaoDomain>)SessaoAtual().createQuery(
						"from MedicaoPressaoDomain where  paciente.id =" + idPaciente).list();
		
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Método para obter a ultima medição cadastrada
	 * @param idPaciente Id do paciente
	 * @return MedicaoPressaoDomain
	 */	
	public MedicaoPressaoDomain obtemUltimaMedicao(int idPaciente){
		String comando = "select mp.* from medicao_pressao mp " +
				"where mp.paciente_id = :idPaciente " +
				"order by data_hora desc " +
				"limit 1";
		SQLQuery query = SessaoAtual().createSQLQuery(comando);
		query.setParameter("idPaciente", idPaciente);
		query.addEntity(MedicaoPressaoDomain.class);
		
		MedicaoPressaoDomain medicao = (MedicaoPressaoDomain) query.uniqueResult();
		return medicao;
	}
	
	/**
	 * Método para verificar se a medição ja foi cadastrada
	 * Se o id da medição ja existir (for > 0) será retornado false,
	 * caso contrário sera retornado true
	 * @param medicao Objeto de representação da medição da pressão arterial(MedicaoPressaoDomain)s
	 * @return boolean
	 */
	private boolean ehNovaMedicao(MedicaoPressaoDomain medicao){
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
