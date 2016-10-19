package com.br.uepb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.br.uepb.validacao.FormataCampos;

/**
 * Classe de domínio que define as informações coletadas da Cardiografia pro Impedância 
 */

@Entity
@Table(name="medicao_monitor")
public class MedicaoMonitorDomain {

	/** Id da medicao */
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	/** Representação do objeto Paciente */
	@ManyToOne
	@JoinColumn(name = "paciente_id", referencedColumnName="id")
	@Fetch(FetchMode.JOIN)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private PacienteDomain paciente;

	/** Data e Hora da medição */
	@Column(name="data_hora")
	private Date dataHora;
	
	/** Data e hora da medição formatada */
	@Transient
	private String dataHoraFormatada;
	
	/** Frequencia Cardiaca do paciente */
	@Column(name = "frequencia_cardiaca")
	private double frequenciaCardiaca;
	
	/** Unidade de medida referente a frequencia cardiaca */
	@Column(name="frequencia_cardiaca_unidade")
	private String frequenciaCardiacaUnidade;
	
	/** Frequencia Respiratoria do paciente */
	@Column(name="frequencia_respiratoria")
	private double frequenciaRespiratoria;
	
	/** Unidade de medida referente a frequencia respiratoria do paciente */
	@Column(name="frequencia_respiratoria_unidade")
	private String frequenciaRespiratoriaUnidade;
	
	/** Debito Cardiaco do paciente */
	@Column(name="debito_cardiaco")
	private double debitoCardiaco;

	/** Unidade de medida referente ao debito cardiaco do paciente */
	@Column(name="debito_cardiaco_unidade")
	private String debitoCardiacoUnidade;
	
	/** Indice Cardiaco do paciente */
	@Column(name="indice_cardiaco")
	private double indiceCardiaco;

	/** Unidade de medida referente ao indice cardiaco do paciente */
	@Column(name="indice_cardiaco_unidade")
	private String indiceCardiacoUnidade;
	
	/**
	 * Método para retornar o id da medição do monitor 
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Método para informar o id da medição da balança
	 * @param id Id da medição
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Método para retornar o objeto referente ao paciente
	 * @return PacienteDomain
	 */
	public PacienteDomain getPaciente() {
		return paciente;
	}
	
	/**
	 * Método para informar o paciente relacionado com as informações da medição da balança
	 * @param paciente Objeto de representação do paciente (PacienteDomain)
	 */
	public void setPaciente(PacienteDomain paciente) {
		this.paciente = paciente;
	}
	
	/**
	 * Método para retornar a data e hora da medição da balança
	 * @return Date
	 */
	public Date getDataHora() {
		return dataHora;
	}
	
	/**
	 * Método para informar a data e hora da medição da balança
	 * @param dataHora Data e hora da medição
	 */
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	/** Método para informar a data e hora da medição formatada 
	 *  no padrão dd/MM/yyyy HH:mm:ss
	 * @return String Data e hora formatada
	 */
	public String getDataHoraFormatada() {
		FormataCampos formataCampos = new FormataCampos();
		dataHoraFormatada = formataCampos.formataDataHora(dataHora);		
		return dataHoraFormatada;
	}
	
	/**
	 * Método para retornar a frequencia cardiaca do paciente
	 * @return double
	 */
	public double getFrequenciaCardiaca() {
		return frequenciaCardiaca;
	}
	
	/**
	 * Método para informar a frequencia cardiaca do paciente
	 * @param frequenciaCardiaca Frequencia Cardiaca do paciente
	 */
	public void setFrequenciaCardiaca(double frequenciaCardiaca) {
		this.frequenciaCardiaca = frequenciaCardiaca;
	}
	
	/**
	 * Método para retornar a unidade referente a frequencia cardiaca do paciente
	 * @return String
	 */
	public String getFrequenciaCardiacaUnidade() {
		return frequenciaCardiacaUnidade;
	}
	
	/**
	 * Método para informar a unidade referente a frequencia cardiaca do paciente
	 * @param frequenciaCardiacaUnidade Unidade de medida referente a frequencia cardiaca
	 */
	public void setFrequenciaCardiacaUnidade(String frequenciaCardiacaUnidade) {
		this.frequenciaCardiacaUnidade = frequenciaCardiacaUnidade;
	}
	
	/**
	 * Método para retornar a frequencia respiratoria do paciente
	 * @return double
	 */
	public double getFrequenciaRespiratoria() {
		return frequenciaRespiratoria;
	}
	
	/**
	 * Método para informar a frequencia respiratoria do paciente
	 * @param frequenciaRespiratoria Altura do paciente
	 */
	public void setFrequenciaRespiratoria(double frequenciaRespiratoria) {
		this.frequenciaRespiratoria = frequenciaRespiratoria;
	}

	/**
	 * Método para retornar a unidade referente a frequencia respiratoria do paciente
	 * @return String
	 */
	public String getFrequenciaRespiratoriaUnidade() {
		return frequenciaRespiratoriaUnidade;
	}
	
	/**
	 * Método para informar a unidade referente a frequencia respiratoria do paciente
	 * @param frequenciaRespiratoriaUnidade Unidade de medida referente a frequencia respiratoria do paciente
	 */
	public void setFrequenciaRespiratoriaUnidade(String frequenciaRespiratoriaUnidade) {
		this.frequenciaRespiratoriaUnidade = frequenciaRespiratoriaUnidade;
	}
	
	/**
	 * Método para retornar o debito cardiaco do paciente 
	 * @return double
	 */
	public double getDebitoCardiaco() {
		return debitoCardiaco;
	}
	
	/**
	 * Método para informar o debito cardiaco do paciente
	 * @param debitoCardiaco Debito Cardiaco do paciente
	 */
	public void setDebitoCardiaco(double debitoCardiaco) {
		this.debitoCardiaco = debitoCardiaco;
	}
	
	/**
	 * Método para retornar a unidade referente ao debito cardiaco do paciente
	 * @return String
	 */
	public String getDebitoCardiacoUnidade() {
		return debitoCardiacoUnidade;
	}
	
	/**
	 * Método para informar a unidade referente ao debito cardiaco do paciente
	 * @param debitoCardiacoUnidade Unidade referente ao debito cardiaco do paciente
	 */
	public void setDebitoCardiacoUnidade(String debitoCardiacoUnidade) {
		this.debitoCardiacoUnidade = debitoCardiacoUnidade;
	}
	
	/**
	 * Método para retornar o indice cardiaco do paciente 
	 * @return double
	 */
	public double getIndiceCardiaco() {
		return indiceCardiaco;
	}
	
	/**
	 * Método para informar o indice cardiaco do paciente
	 * @param indiceCardiaco Indice Cardiaco do paciente
	 */
	public void setIndiceCardiaco(double indiceCardiaco) {
		this.indiceCardiaco = indiceCardiaco;
	}
	
	/**
	 * Método para retornar a unidade referente ao indice cardiaco do paciente
	 * @return String
	 */
	public String getIndiceCardiacoUnidade() {
		return indiceCardiacoUnidade;
	}
	
	/**
	 * Método para informar a unidade referente ao indice cardiaco do paciente
	 * @param indiceCardiacoUnidade Unidade referente ao indice cardiaco do paciente
	 */
	public void setIndiceCardiacoUnidade(String indiceCardiacoUnidade) {
		this.indiceCardiacoUnidade = indiceCardiacoUnidade;
	}	
}
