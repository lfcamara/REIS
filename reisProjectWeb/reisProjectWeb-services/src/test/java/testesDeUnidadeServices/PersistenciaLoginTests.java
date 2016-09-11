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

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.br.uepb.dao.LoginDAO;
import com.br.uepb.dao.PacienteDAO;
import com.br.uepb.model.LoginDomain;
import com.br.uepb.model.PacienteDomain;


public class PersistenciaLoginTests {

	private PacienteDAO pacienteDAO;
	private PacienteDomain paciente;
	
	@Before
	public void criarPaciente () {
		pacienteDAO = new PacienteDAO();	
		paciente = new PacienteDomain();

		List<PacienteDomain> listaPacientes = pacienteDAO.listaPacientes();
		for (PacienteDomain pacienteDomain : listaPacientes) {
			pacienteDAO.excluiPaciente(pacienteDomain);
		}

		LoginDAO loginDAO = new LoginDAO();
		List<LoginDomain> listaLogin = loginDAO.listaLogins();
		for (LoginDomain loginDomain : listaLogin) {
			loginDAO.excluiLogin(loginDomain);
		}
		
		paciente.setNome("Jorge Miranda");
		paciente.setEndereco("Rua Calvario Azul");			
		paciente.setSexo("M");
		paciente.setCidade("Campina Grande");
		paciente.setEstado("PB");
		paciente.setTelefoneCasa("99999999");
		pacienteDAO.salvaPaciente(paciente);					
	}
		
	@Test
	public void criarLoginTest() {
		LoginDAO loginDAO = new LoginDAO();
		LoginDomain novoLogin = new LoginDomain();		
		novoLogin.setLogin("chico");
		novoLogin.setSenha("senha123");
		novoLogin.setPaciente(paciente);		
		loginDAO.salvaLogin(novoLogin);

		LoginDomain login = loginDAO.obtemLoginPorPaciente(paciente.getId());
		assertTrue(login.getId() > 0);
		assertEquals(login.getPaciente().getId(), paciente.getId());
		
		//Verifica usuario cadastrado
		assertTrue(loginDAO.jaExisteUsuario("chico"));
		//Verifica usuario nao cadastrado
		assertFalse(loginDAO.jaExisteUsuario("maria"));
				
	}

	@After
	public void limpaDados () {
		LoginDAO loginDAO = new LoginDAO();
		List<LoginDomain> listaLogin = loginDAO.listaLogins();
		for (LoginDomain loginDomain : listaLogin) {
			loginDAO.excluiLogin(loginDomain);
		}						
	}
	
}
