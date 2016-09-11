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

import com.br.uepb.model.MedicaoOximetroDomain;

import conexaoBD.HibernateUtil;

/**
 * Classe DAO para o objeto MedicaoOximetroDomain
 * Esta classe é responsavel por pesquisar, inserir, excluir e atualizar informações 
 * na tabela mapeada com o objeto MedicaoOximetroDomain
 */
public class MedicaoOximetroDAO {

	/** Idenfiticação da sessão atual */
	private Session sessaoAtual;
	
	/**
	 * Método para salvar ou atualizar a medição do oximetro
	 * Se o parametro medição ja existir, as informações serão atualizadas,
	 * caso contrário, será armazenada uma nova medição
	 * @param medicao Objeto de representação da medição do Oxímetro(MedicaoOximetroDomain)
	 */
	public void salvaMedicaoOximetro(MedicaoOximetroDomain medicao){
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
	 * Método para exlcuir a medição do oximetro
	 * @param medicao Objeto de representação da medição do Oxímetro(MedicaoOximetroDomain)	 
	 * @return Boolean - Retorna true se conseguir excluir a medição e false caso contrário
	 */
	public Boolean excluiMedicaoOximetro(MedicaoOximetroDomain medicao){
		try {
			Session novaSessao = SessaoAtual();
			Transaction tx = SessaoAtual().beginTransaction();
			novaSessao.delete(medicao);
			novaSessao.flush();
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * para excluir a medição do oximetro de acordo com o id do paciente
	 * @param idPaciente Id do Paciente
	 * @return Retorna true se conseguir excluir a medicao e false caso contrário
	 */
	public Boolean excuiMedicaoOximetroPorPaciente(int idPaciente) {
		try {
			Session novaSessao = SessaoAtual();
			Transaction tx = SessaoAtual().beginTransaction();
			novaSessao.createQuery("delete from MedicaoOximetroDomain where paciente.id = :id").
								   setParameter("id", idPaciente).executeUpdate();
			novaSessao.flush();
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	/**
	 * Método para obter a medição do oxímetro de acordo com o id da medição
	 * @param idOximetro Id do Oxímetro
	 * @return MedicaoOximetroDomain
	 */
	public MedicaoOximetroDomain obtemMedicaoOximetro(int idOximetro){
		MedicaoOximetroDomain medicao = (MedicaoOximetroDomain)SessaoAtual().get(MedicaoOximetroDomain.class, idOximetro);
		SessaoAtual().close();
		return medicao;
	}	
	
	/**
	 * Método para obter a ultima medição cadastrada de acordo com o id do paciente
	 * @param idPaciente Id do paciente
	 * @return MedicaoOximetroDomain
	 */
	public MedicaoOximetroDomain obtemUltimaMedicao(int idPaciente){
		String comando = "select mo.* from Medicao_oximetro mo " +
						"where mo.paciente_id = :idPaciente " +
						"order by data_hora desc " +
						"limit 1";
		SQLQuery query = SessaoAtual().createSQLQuery(comando);
		query.setParameter("idPaciente", idPaciente);
		query.addEntity(MedicaoOximetroDomain.class);
		
		MedicaoOximetroDomain medicao = (MedicaoOximetroDomain) query.uniqueResult();
		return medicao;
	}
	
	/**
	 * Método para listar todas as medições cadastradas
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoOximetroDomain> listaMedicoes(){
		
		List<MedicaoOximetroDomain> medicao = 
				(List<MedicaoOximetroDomain>)SessaoAtual().createQuery("from MedicaoOximetroDomain").list();
		
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Método para listar todas as medições cadastradas de acordo com o id do paciente
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoOximetroDomain> listaMedicoesDoPaciente(int idPaciente){
		List<MedicaoOximetroDomain> medicao =
				(List<MedicaoOximetroDomain>)SessaoAtual().createQuery(
						"from MedicaoOximetroDomain where  paciente.id =" + idPaciente).list();
		
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Método para listar a ultima medição do paciente cadastrada de acordo com o id do paciente
	 * @param idPaciente Id do paciente
	 * @return MedicaoOximetroDomain
	 */
	@SuppressWarnings("unchecked")
	public MedicaoOximetroDomain listaUltimaMedicaoDoPaciente(int idPaciente){
		List<MedicaoOximetroDomain> listamedicoes =
				(List<MedicaoOximetroDomain>)SessaoAtual().createQuery(
						"from MedicaoOximetroDomain where  paciente.id = " + idPaciente+"order by dataHora").list();
		
		SessaoAtual().close();
		return listamedicoes.get(0);
	}
	
	/**
	 * Método para verificar se a medição já foi cadastrada
	 * Se o id da medição já existir será retornado false,
	 * caso contrário será retornado true
	 * @param medicao Objeto de representação da medição do Oxímetro(MedicaoOximetroDomain)
	 * @return boolean
	 */
	private boolean ehNovaMedicao(MedicaoOximetroDomain medicao){
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
