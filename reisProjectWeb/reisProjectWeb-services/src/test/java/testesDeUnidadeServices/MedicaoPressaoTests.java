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
package testesDeUnidadeServices;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.br.uepb.dao.MedicaoPressaoDAO;
import com.br.uepb.dao.PacienteDAO;
import com.br.uepb.model.MedicaoPressaoDomain;
import com.br.uepb.model.PacienteDomain;

public class MedicaoPressaoTests {
	
	private PacienteDAO pacienteDAO;
	private PacienteDomain paciente;
	
	@Before
	public void criarPaciente () {
		pacienteDAO = new PacienteDAO();
		paciente = new PacienteDomain();		
		paciente.setNome("Jorge Miranda");
		paciente.setEndereco("Rua Calvario Azul");			
		paciente.setSexo("M");
		paciente.setCidade("Campina Grande");
		paciente.setEstado("PB");
		paciente.setTelefoneCasa("99999999");
		pacienteDAO.salvaPaciente(paciente);					
	}
	
	@Test
	public void criarMedicaoBalancaPressaoTest() {		
		MedicaoPressaoDAO medicaoDAO = new MedicaoPressaoDAO();
		MedicaoPressaoDomain medicao = new MedicaoPressaoDomain();				
		medicao.setPaciente(paciente);
		medicao.setPressaoDiastolica(80);
		medicao.setPressaoSistolica(120);
		medicao.setPressaoMedia(112);
		medicao.setTaxaPulso(90);
		medicao.setDataHora(Calendar.getInstance().getTime());
		medicao.setPressaoSistolicaUnidade("mmHg");		
		medicaoDAO.salvaMedicaoPressao(medicao);
		
		assertTrue(medicao.getId() > 0);
		assertEquals(paciente.getId(), medicao.getPaciente().getId());
		
		List<MedicaoPressaoDomain> medicoes = medicaoDAO.listaMedicoesDoPaciente(paciente.getId());
		assertTrue(medicoes.size() > 0);
		 
		
		MedicaoPressaoDomain ultimaMedicao = medicaoDAO.obtemUltimaMedicao(paciente.getId());
		assertNotNull(ultimaMedicao);
		assertEquals(medicoes.get(0).getId(), ultimaMedicao.getId());
	}
	
	@Test
	public void obterMedicaoOximetroTest(){		
		PacienteDAO newPacienteDAO = new PacienteDAO();
		PacienteDomain newPaciente = new PacienteDomain();
		
		newPaciente.setNome("Algusta");
		newPaciente.setEndereco("Rua Calvario Azul");			
		newPaciente.setSexo("F");
		newPaciente.setCidade("Campina Grande");
		newPaciente.setEstado("PB");
		newPaciente.setTelefoneCasa("99999999");
		newPacienteDAO.salvaPaciente(newPaciente);
		
		MedicaoPressaoDAO medicaoDAO = new MedicaoPressaoDAO();
		List<MedicaoPressaoDomain> medicoes = medicaoDAO.listaMedicoesDoPaciente(newPaciente.getId());
		assertTrue(medicoes.size() < 1);
	}
	
	@After
	public void limparDados(){		
		MedicaoPressaoDAO medicaoDAO = new MedicaoPressaoDAO();
		List<MedicaoPressaoDomain> listaMedicao = medicaoDAO.listaMedicoesPressao();
		for (MedicaoPressaoDomain medicao : listaMedicao) {
			medicaoDAO.excluiMedicaoPressao(medicao);
		}
		
		List<PacienteDomain> listaPacientes = pacienteDAO.listaPacientes();
		for (PacienteDomain pacienteDomain : listaPacientes) {
			pacienteDAO.excluiPaciente(pacienteDomain);
		}
	}

}
