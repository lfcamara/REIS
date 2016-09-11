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
package com.br.uepb.hl7;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.br.uepb.dao.MedicaoBalancaDAO;
import com.br.uepb.dao.MedicaoOximetroDAO;
import com.br.uepb.dao.MedicaoPressaoDAO;
import com.br.uepb.dao.PacienteDAO;
import com.br.uepb.model.CodigosDispositivos;
import com.br.uepb.model.MedicaoBalancaDomain;
import com.br.uepb.model.MedicaoOximetroDomain;
import com.br.uepb.model.MedicaoPressaoDomain;
import com.br.uepb.model.PacienteDomain;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v25.message.ORU_R01;
import ca.uhn.hl7v2.model.v25.segment.OBR;
import ca.uhn.hl7v2.model.v25.segment.OBX;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.parser.Parser;

/**
 * Classe para a geração da mensagem no padrão HL7 V2 utilizando 
 * o padrão de mensagem HL7 do tipo HL7 ORU^R01
 */
public class GerarMensagemHL7 {
	
	/** Representação do objeto PacienteDomain */
	private PacienteDomain paciente;
	/** Representação do objeto CódigosDispositvos.
	 * Este objeto contém a codificação de todos os sinais vitais */
	private CodigosDispositivos codigos;
	/** Representação do objeto MedicaoOximetroDAO */
	private MedicaoOximetroDAO medicaoOximetroDAO;
	/** Representação do objeto MedicaoBalancaDAO */
	private MedicaoBalancaDAO medicaoBalancaDAO;
	/** Representação do objeto MedicaoPressaoDAO */
	private MedicaoPressaoDAO medicaoPressaoDAO;
	
	/**
	 * Método construtor da classe GerarMensagemHL7
	 * @param idPaciente Id do paciente atual
	 */
	public GerarMensagemHL7(int idPaciente) {
		paciente = informacoesDoPaciente(idPaciente);
		codigos = new CodigosDispositivos();
		medicaoOximetroDAO = new MedicaoOximetroDAO();
		medicaoBalancaDAO = new MedicaoBalancaDAO();
		medicaoPressaoDAO = new MedicaoPressaoDAO();			
	}

	/**
	 * Método para gerar a mensagem HL7
	 * @param gerarOximetro Opção booleana para incluir a medicao do oximetro durante a geração da mensagem HL7
	 * @param gerarBalanca Opção booleana para incluir a medicao da balença durante a geração da mensagem HL7
	 * @param gerarPressao Opção booleana para incluir a medicao do medidor de pressão durante a geração da mensagem HL7
	 * @return String Retorna uma string contendo a mensagem no padrão HL7
	 * @throws HL7Exception Gera exceção se não conseguir gerar a mensagem HL7
	 * @throws IOException Gera exceção se não conseguir gerar a mensagem HL7
	 */
	public String criarMensagem(boolean gerarOximetro, boolean gerarBalanca, boolean gerarPressao) throws HL7Exception, IOException {		  
		
		int indexOBR = 1;
		
		ORU_R01 mensagem = new ORU_R01();
		mensagem.initQuickstart("ORU", "R01", "P");
		
		//SEGMENTO INICIAL PID
		gerarSegmentoPID(mensagem);
		
		ORU_R01_ORDER_OBSERVATION orderOBR; 
		
		//MEDICOES DO OXIMETRO DE PULSO
		if (gerarOximetro == true) {
			List<MedicaoOximetroDomain> medicaoOximetroList = medicaoOximetroDAO.listaMedicoesDoPaciente(paciente.getId());
			for (MedicaoOximetroDomain oximetro : medicaoOximetroList) {
				orderOBR = mensagem.getPATIENT_RESULT().getORDER_OBSERVATION(indexOBR-1);
				
				gerarSegmentoOBR(mensagem, orderOBR, indexOBR+"", oximetro.getDataHora());
				gerarSegmentoOBX_Oximetro(mensagem, orderOBR, oximetro);
				indexOBR++;
			}
		}
		
		//MEDICOES DA BALANÇA
		if (gerarBalanca == true) {
			List<MedicaoBalancaDomain> medicaoBalancaList = medicaoBalancaDAO.listaMedicoesDoPaciente(paciente.getId());
			for (MedicaoBalancaDomain balanca : medicaoBalancaList) {
				orderOBR = mensagem.getPATIENT_RESULT().getORDER_OBSERVATION(indexOBR-1);
				gerarSegmentoOBR(mensagem, orderOBR, indexOBR+"", balanca.getDataHora());
				gerarSegmentoOBX_Balanca(mensagem, orderOBR, balanca);
				indexOBR++;
			}
		}
		
		//MEDICOES DO MEDIDOR DE PRESSAO ARTERIAL
		if (gerarPressao == true) {
			List<MedicaoPressaoDomain> medicaoPressaoList = medicaoPressaoDAO.listaMedicoesDoPaciente(paciente.getId());
			for (MedicaoPressaoDomain pressao : medicaoPressaoList) {
				orderOBR = mensagem.getPATIENT_RESULT().getORDER_OBSERVATION(indexOBR-1);
				gerarSegmentoOBR(mensagem, orderOBR, indexOBR+"", pressao.getDataHora());
				gerarSegmentoOBX_Pressao(mensagem, orderOBR, pressao);
				indexOBR++;
			}
		}
		
		// Transforma os objetos no arquivo HL7.
		HapiContext context = new DefaultHapiContext();
		Parser parser = context.getPipeParser();
		String encodedMessage = parser.encode(mensagem);
		System.out.println(encodedMessage.trim());
		return encodedMessage;		
	}

	/**
	 * Método para gerar o segmento PID
	 * @param mensagem Objeto do tipo ORU_R01 onde serão adicionadas as informações do paciente
	 * @throws HL7Exception Gera exceção se não conseguir adicionar as informações do paciente
	 * @throws IOException Gera exceção se não conseguir adicionar as informações do paciente
	 */
	private void gerarSegmentoPID(ORU_R01 mensagem) throws HL7Exception, IOException {
		//PID - Informações do Paciente
		PID pid = mensagem.getPATIENT_RESULT().getPATIENT().getPID();
		pid.getSetIDPID().setValue(String.valueOf(1)); //PID-1
		pid.insertPatientIdentifierList(0).getCx1_IDNumber().setValue(paciente.getId()+""); //PID-3
		pid.getPatientName(0).getFamilyName().getSurname().setValue(paciente.getSobrenome()); //PIS-5
		pid.getPatientName(0).getGivenName().setValue(paciente.getNome()); //PID-5
		pid.getDateTimeOfBirth().getTime().setValue(paciente.getDataNascimento()); //PID-7
		pid.getAdministrativeSex().setValue(paciente.getSexo()); //PID-8
		pid.getPatientAddress(0).getStreetAddress().getStreetName().setValue(paciente.getEndereco()); //PID-11
		pid.getPatientAddress(0).getCity().setValue(paciente.getCidade()); //PID-11
		pid.getPhoneNumberHome(0).getTelephoneNumber().setValue(paciente.getTelefoneCasa()); //PID-13
	}
	
	/**
	 * Método para gerar o segmento OBR
	 * Este segmento contem a apresentação dos sinais vitais
	 * @param mensagem Objeto do tipo ORU_R01 onde serão adicionadas as informações do segmento OBR
	 * @param orderOBR Representação do objeto ORU_R01_ORDER_OBSERVATION 
	 * @param idOBR Id da observação
	 * @param dataOBX Data da observação
	 * @throws HL7Exception Gera exceção se não conseguir adicionar as informações no segmento OBR
	 * @throws IOException Gera exceção se não conseguir adicionar  as informações no segmento OBR
	 */
	private void gerarSegmentoOBR(ORU_R01 mensagem, ORU_R01_ORDER_OBSERVATION orderOBR, String idOBR, Date dataOBX) throws HL7Exception, IOException {
		//OBR - Pedido de Observação
		OBR obr = orderOBR.getOBR();		
		obr.getSetIDOBR().setValue(idOBR); //OBR-1
		obr.getUniversalServiceIdentifier().getIdentifier().setValue(codigos.SNOMED_SINAIS_VITAIS_COD); //OBR-4
		obr.getUniversalServiceIdentifier().getText().setValue(codigos.SNOMED_SINAIS_VITAIS); //OBR-4
		obr.getUniversalServiceIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBR-4
		obr.getObservationDateTime().getTime().setValue(formatarData(dataOBX)); //OBR-7
	}
	
	/**
	 * Método para adicionar as informações do oximetro no segmento OBX
	 * @param mensagem Objeto do tipo ORU_R01 onde serão adicionadas as informações do segmento OBX
	 * @param orderOBR  Representação do objeto ORU_R01_ORDER_OBSERVATION
	 * @param oximetro  Representação do objeto MedicaoOximetroDomain
	 * @throws HL7Exception Gera exceção se não conseguir adicionar as informações no segmento OBX
	 * @throws IOException Gera exceção se não conseguir adicionar as informações no segmento OBX
	 */
	private void gerarSegmentoOBX_Oximetro(ORU_R01 mensagem, ORU_R01_ORDER_OBSERVATION orderOBR, MedicaoOximetroDomain oximetro) throws HL7Exception, IOException {
	
		//***** SPO2 - OBX - Resultado da Observação ***********
		OBX obx = orderOBR.getOBSERVATION().getOBX();
		obx.getSetIDOBX().setValue("1"); //OBX-1
		obx.getValueType().setValue("NM"); //OBX-2
		obx.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.SPO2_OX.getCodSNOMED()); //OBX-3
		obx.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.SPO2_OX.getNomeSNOMED()); //OBX-3
		obx.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues = new CE(mensagem);
		ceValues.getText().setValue(oximetro.getSpo2()+"");		
		Varies varieOBX = obx.getObservationValue(0);
		varieOBX.setData(ceValues);
		
		obx.getUnits().getText().setValue(oximetro.getSpo2Unidade()); //OBX-6
		obx.getReferencesRange().setValue(codigos.sinaisVitaisEnum.SPO2_OX.getIntervalo()); //OBX-7
		obx.getObservationResultStatus().setValue("F"); //OBX-11
		obx.getDateTimeOfTheObservation().getTime().setValue(formatarData(oximetro.getDataHora())); //OBX-14
		//******************************************************
		
		//***** TAXA DE PULSO - OBX - Resultado da Observação *****
		OBX obx2 = orderOBR.getOBSERVATION(1).getOBX();
		obx2.getSetIDOBX().setValue("2"); //OBX-1
		obx2.getValueType().setValue("NM"); //OBX-2
		obx2.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.TAXA_PULSO_OX.getCodSNOMED()); //OBX-3
		obx2.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.TAXA_PULSO_OX.getNomeSNOMED()); //OBX-3
		obx2.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
		
		CE ceValues2 = new CE(mensagem);
		ceValues2.getText().setValue(oximetro.getTaxaPulso()+"");		
		Varies varieOBX2 = obx2.getObservationValue(0);
		varieOBX2.setData(ceValues2);
		
		obx2.getUnits().getText().setValue(oximetro.getTaxaPulsoUnidade()); //OBX-6
		obx2.getReferencesRange().setValue(codigos.sinaisVitaisEnum.TAXA_PULSO_OX.getIntervalo()); //OBX-7
		obx2.getObservationResultStatus().setValue("F"); //OBX-11
		obx2.getDateTimeOfTheObservation().getTime().setValue(formatarData(oximetro.getDataHora())); //OBX-14
		//******************************************************
	}
	
	/**
	 * Método para adicionar as informações da balança no segmento OBX
	 * @param mensagem Objeto do tipo ORU_R01 onde serão adicionadas as informações do segmento OBX
	 * @param orderOBR  Representação do objeto ORU_R01_ORDER_OBSERVATION
	 * @param balanca Representação do objeto MedicaoBalancaDomain
	 * @throws HL7Exception Gera exceção se não conseguir adicionar as informações no segmento OBX
	 * @throws IOException Gera exceção se não conseguir adicionar as informações no segmento OBX
	 */
	private void gerarSegmentoOBX_Balanca(ORU_R01 mensagem, ORU_R01_ORDER_OBSERVATION orderOBR, MedicaoBalancaDomain balanca) throws HL7Exception, IOException {
		
		//***** PESO - OBX - Resultado da Observação ***********
		OBX obx = orderOBR.getOBSERVATION().getOBX();
		obx.getSetIDOBX().setValue("1"); //OBX-1
		obx.getValueType().setValue("NM"); //OBX-2
		obx.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.PESO_BAL.getCodSNOMED()); //OBX-3
		obx.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.PESO_BAL.getNomeSNOMED()); //OBX-3
		obx.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues = new CE(mensagem);
		ceValues.getText().setValue(balanca.getPeso()+"");		
		Varies varieOBX = obx.getObservationValue(0);
		varieOBX.setData(ceValues);
		
		obx.getUnits().getText().setValue(balanca.getPesoUnidade()); //OBX-6
		//obx.getReferencesRange().setValue(codigos.sinaisVitaisEnum.PESO_BAL.getIntervalo()); //OBX-7
		obx.getObservationResultStatus().setValue("F"); //OBX-11
		obx.getDateTimeOfTheObservation().getTime().setValue(formatarData(balanca.getDataHora())); //OBX-14
		//******************************************************
		
		//***** ALTURA - OBX - Resultado da Observação ***********
		OBX obx2 = orderOBR.getOBSERVATION(1).getOBX();
		obx2.getSetIDOBX().setValue("2"); //OBX-1
		obx2.getValueType().setValue("NM"); //OBX-2
		obx2.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.ALTURA_BAL.getCodSNOMED()); //OBX-3
		obx2.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.ALTURA_BAL.getNomeSNOMED()); //OBX-3
		obx2.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues2 = new CE(mensagem);
		ceValues2.getText().setValue(balanca.getAltura()+"");		
		Varies varieOBX2 = obx2.getObservationValue(0);
		varieOBX2.setData(ceValues2);
		
		obx2.getUnits().getText().setValue(balanca.getAlturaUnidade()); //OBX-6
		//obx2.getReferencesRange().setValue(codigos.sinaisVitaisEnum.ALTURA_BAL.getIntervalo()); //OBX-7
		obx2.getObservationResultStatus().setValue("F"); //OBX-11
		obx2.getDateTimeOfTheObservation().getTime().setValue(formatarData(balanca.getDataHora())); //OBX-14
		//******************************************************		

		//***** IMC - OBX - Resultado da Observação ***********
		OBX obx3 = orderOBR.getOBSERVATION(2).getOBX();
		obx3.getSetIDOBX().setValue("3"); //OBX-1
		obx3.getValueType().setValue("NM"); //OBX-2
		obx3.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.IMC_BAL.getCodSNOMED()); //OBX-3
		obx3.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.IMC_BAL.getNomeSNOMED()); //OBX-3
		obx3.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues3 = new CE(mensagem);
		ceValues3.getText().setValue(balanca.getMassa()+"");		
		Varies varieOBX3 = obx3.getObservationValue(0);
		varieOBX3.setData(ceValues3);
		
		obx3.getUnits().getText().setValue(balanca.getMassaUnidade()); //OBX-6
		//obx3.getReferencesRange().setValue(codigos.sinaisVitaisEnum.IMC_BAL.getIntervalo()); //OBX-7
		obx3.getObservationResultStatus().setValue("F"); //OBX-11
		obx3.getDateTimeOfTheObservation().getTime().setValue(formatarData(balanca.getDataHora())); //OBX-14
		//******************************************************	
	}

	/**
	 * Método para adicionar as informações do medidor de pressão no segmento OBX
	 * @param mensagem Objeto do tipo ORU_R01 onde serão adicionadas as informações do segmento OBX
	 * @param orderOBR  Representação do objeto ORU_R01_ORDER_OBSERVATION
	 * @param pressao Representação do objeto MedicaoPressaoDomain
	 * @throws HL7Exception Gera exceção se não conseguir adicionar as informações no segmento OBX
	 * @throws IOException Gera exceção se não conseguir adicionar as informações no segmento OBX
	 */
	private void gerarSegmentoOBX_Pressao(ORU_R01 mensagem, ORU_R01_ORDER_OBSERVATION orderOBR, MedicaoPressaoDomain pressao) throws HL7Exception, IOException {
		
		//***** PRESSAO SISTOLICA - OBX - Resultado da Observação ***********
		OBX obx = orderOBR.getOBSERVATION().getOBX();
		obx.getSetIDOBX().setValue("1"); //OBX-1
		obx.getValueType().setValue("NM"); //OBX-2
		obx.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.PRESSAO_SISTOLICA_MED.getCodSNOMED()); //OBX-3
		obx.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.PRESSAO_SISTOLICA_MED.getNomeSNOMED()); //OBX-3
		obx.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues = new CE(mensagem);
		ceValues.getText().setValue(pressao.getPressaoSistolica()+"");		
		Varies varieOBX = obx.getObservationValue(0);
		varieOBX.setData(ceValues);
		
		obx.getUnits().getText().setValue(pressao.getPressaoSistolicaUnidade()); //OBX-6
		obx.getReferencesRange().setValue(codigos.sinaisVitaisEnum.PRESSAO_SISTOLICA_MED.getIntervalo()); //OBX-7
		obx.getObservationResultStatus().setValue("F"); //OBX-11
		obx.getDateTimeOfTheObservation().getTime().setValue(formatarData(pressao.getDataHora())); //OBX-14
		//******************************************************
		
		//***** PRESSAO DIASTOLICA - OBX - Resultado da Observação ***********
		OBX obx2 = orderOBR.getOBSERVATION(1).getOBX();
		obx2.getSetIDOBX().setValue("2"); //OBX-1
		obx2.getValueType().setValue("NM"); //OBX-2
		obx2.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.PRESSAO_DIASTOLICA_MED.getCodSNOMED()); //OBX-3
		obx2.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.PRESSAO_DIASTOLICA_MED.getNomeSNOMED()); //OBX-3
		obx2.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues2 = new CE(mensagem);
		ceValues2.getText().setValue(pressao.getPressaoDiastolica()+"");		
		Varies varieOBX2 = obx2.getObservationValue(0);
		varieOBX2.setData(ceValues2);
		
		obx2.getUnits().getText().setValue(pressao.getPressaoDiastolicaUnidade()); //OBX-6
		obx2.getReferencesRange().setValue(codigos.sinaisVitaisEnum.PRESSAO_DIASTOLICA_MED.getIntervalo()); //OBX-7
		obx2.getObservationResultStatus().setValue("F"); //OBX-11
		obx2.getDateTimeOfTheObservation().getTime().setValue(formatarData(pressao.getDataHora())); //OBX-14
		//******************************************************		

		//***** PRESSAO MEDIA - OBX - Resultado da Observação ***********
		OBX obx3 = orderOBR.getOBSERVATION(2).getOBX();
		obx3.getSetIDOBX().setValue("3"); //OBX-1
		obx3.getValueType().setValue("NM"); //OBX-2
		obx3.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.PRESSAO_MEDIA_MED.getCodSNOMED()); //OBX-3
		obx3.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.PRESSAO_MEDIA_MED.getNomeSNOMED()); //OBX-3
		obx3.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues3 = new CE(mensagem);
		ceValues3.getText().setValue(pressao.getPressaoMedia()+"");		
		Varies varieOBX3 = obx3.getObservationValue(0);
		varieOBX3.setData(ceValues3);
		
		obx3.getUnits().getText().setValue(pressao.getPressaoMediaUnidade()); //OBX-6
		obx3.getReferencesRange().setValue(codigos.sinaisVitaisEnum.PRESSAO_MEDIA_MED.getIntervalo()); //OBX-7
		obx3.getObservationResultStatus().setValue("F"); //OBX-11
		obx3.getDateTimeOfTheObservation().getTime().setValue(formatarData(pressao.getDataHora())); //OBX-14
		//******************************************************	

		//***** TAXA DE PULSO - OBX - Resultado da Observação ***********
		OBX obx4 = orderOBR.getOBSERVATION(3).getOBX();
		obx4.getSetIDOBX().setValue("4"); //OBX-1
		obx4.getValueType().setValue("NM"); //OBX-2
		obx4.getObservationIdentifier().getIdentifier().setValue(codigos.sinaisVitaisEnum.TAXA_PULSO_MED.getCodSNOMED()); //OBX-3
		obx4.getObservationIdentifier().getText().setValue(codigos.sinaisVitaisEnum.TAXA_PULSO_MED.getNomeSNOMED()); //OBX-3
		obx4.getObservationIdentifier().getNameOfCodingSystem().setValue(codigos.SNOMED_NOMENCLATURA); //OBX-3
			
		CE ceValues4 = new CE(mensagem);
		ceValues4.getText().setValue(pressao.getTaxaPulso()+"");		
		Varies varieOBX4 = obx4.getObservationValue(0);
		varieOBX4.setData(ceValues4);
		
		obx4.getUnits().getText().setValue(pressao.getTaxaPulsoUnidade()); //OBX-6
		obx4.getReferencesRange().setValue(codigos.sinaisVitaisEnum.TAXA_PULSO_MED.getIntervalo()); //OBX-7
		obx4.getObservationResultStatus().setValue("F"); //OBX-11
		obx4.getDateTimeOfTheObservation().getTime().setValue(formatarData(pressao.getDataHora())); //OBX-14
		//******************************************************	

	}
	
	/**
	 * Método para obter o objeto PacienteDomain de acordo com o id do paciente
	 * @param id Id do paciente
	 * @return PacienteDomain 
	 */
	private PacienteDomain informacoesDoPaciente(int id) {
		PacienteDAO dao = new PacienteDAO();		
		return dao.obtemPaciente(id);
	}	

	/**
	 * Método para formatar a data no padrão HL7 "yyyyMMddhhmmss"
	 * @param data Data a ser formatada
	 * @return String
	 */
	private String formatarData(Date data) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String novaData = dateFormat.format(data); 
		return novaData;
	}
	
}