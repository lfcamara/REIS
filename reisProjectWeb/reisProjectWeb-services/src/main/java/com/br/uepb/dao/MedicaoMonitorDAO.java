package com.br.uepb.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.br.uepb.model.MedicaoMonitorDomain;
import com.br.uepb.model.MedicaoOximetroDomain;

import conexaoBD.HibernateUtil;


/**
 * Classe DAO para o objeto MedicaoMonitorDomain
 * Esta classe é responsavel por pesquisar, inserir, excluir e atualizar informações 
 * na tabela mapeada com o objeto MedicaoMonitorDomain
 */

public class MedicaoMonitorDAO {
	
	/** Idenfiticação da sessão atual */
	private Session sessaoAtual;
	
	/**
	 * Método para salvar ou atualizar a medição do oximetro
	 * Se o parametro medição ja existir, as informações serão atualizadas,
	 * caso contrário, será armazenada uma nova medição
	 * @param medicao Objeto de representação da medição do Monitor(MedicaoMonitorDomain)
	 */
	public void salvaMedicaoMonitor(MedicaoMonitorDomain medicao){
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
	 * @param medicao Objeto de representação da medição do Monitor(MedicaoMonitorDomain)	 
	 * @return Boolean - Retorna true se conseguir excluir a medição e false caso contrário
	 */
	public Boolean excluiMedicaoMonitor(MedicaoMonitorDomain medicao){
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
	 * para excluir a medição do monitor de acordo com o id do paciente
	 * @param idPaciente Id do Paciente
	 * @return Retorna true se conseguir excluir a medicao e false caso contrário
	 */
	public Boolean excuiMedicaoMonitorPorPaciente(int idPaciente) {
		try {
			Session novaSessao = SessaoAtual();
			Transaction tx = SessaoAtual().beginTransaction();
			novaSessao.createQuery("delete from MedicaoMonitorDomain where paciente.id = :id").
								   setParameter("id", idPaciente).executeUpdate();
			novaSessao.flush();
			tx.commit();
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	/**
	 * Método para obter a medição do monitor de acordo com o id da medição
	 * @param idMonitor Id do Monitor
	 * @return MedicaoMonitorDomain
	 */
	public MedicaoMonitorDomain obtemMedicaoMonitor(int idMonitor){
		MedicaoMonitorDomain medicao = (MedicaoMonitorDomain)SessaoAtual().get(MedicaoMonitorDomain.class, idMonitor);
		SessaoAtual().close();
		return medicao;
	}	
	
	/**
	 * Método para obter a ultima medição cadastrada de acordo com o id do paciente
	 * @param idMonitor Id do paciente
	 * @return MedicaoMonitorDomain
	 */
	public MedicaoMonitorDomain obtemUltimaMedicao(int idPaciente){
		String comando = "select mm.* from Medicao_monitor mm " +
						"where mm.paciente_id = :idPaciente " +
						"order by data_hora desc " +
						"limit 1";
		SQLQuery query = SessaoAtual().createSQLQuery(comando);
		query.setParameter("idPaciente", idPaciente);
		query.addEntity(MedicaoMonitorDomain.class);
		
		MedicaoMonitorDomain medicao = (MedicaoMonitorDomain) query.uniqueResult();
		return medicao;
	}
	
	/**
	 * Método para listar todas as medições cadastradas
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoMonitorDomain> listaMedicoes(){
		
		List<MedicaoMonitorDomain> medicao = 
				(List<MedicaoMonitorDomain>)SessaoAtual().createQuery("from MedicaoMonitorDomain").list();
		
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Método para listar todas as medições cadastradas de acordo com o id do paciente
	 * @param idPaciente Id do paciente
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<MedicaoMonitorDomain> listaMedicoesDoPaciente(int idPaciente){
		List<MedicaoMonitorDomain> medicao =
				(List<MedicaoMonitorDomain>)SessaoAtual().createQuery(
						"from MedicaoMonitorDomain where  paciente.id =" + idPaciente).list();
		
		SessaoAtual().close();
		return medicao;
	}
	
	/**
	 * Método para listar a ultima medição do paciente cadastrada de acordo com o id do paciente
	 * @param idPaciente Id do paciente
	 * @return MedicaoMonitorDomain
	 */
	@SuppressWarnings("unchecked")
	public MedicaoMonitorDomain listaUltimaMedicaoDoPaciente(int idPaciente){
		List<MedicaoMonitorDomain> listamedicoes =
				(List<MedicaoMonitorDomain>)SessaoAtual().createQuery(
						"from MedicaoMonitorDomain where  paciente.id = " + idPaciente+"order by dataHora").list();
		
		SessaoAtual().close();
		return listamedicoes.get(0);
	}
	
	/**
	 * Método para verificar se a medição já foi cadastrada
	 * Se o id da medição já existir será retornado false,
	 * caso contrário será retornado true
	 * @param medicao Objeto de representação da medição do Monitor(MedicaoMonitorDomain)
	 * @return boolean
	 */
	private boolean ehNovaMedicao(MedicaoMonitorDomain medicao){
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
