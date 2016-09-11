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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.br.uepb.dao.LoginDAO;
import com.br.uepb.dao.PacienteDAO;
import com.br.uepb.model.LoginDomain;
import com.br.uepb.model.PacienteDomain;

public class PersistenciaPacienteTests {

	@Test
	public void associarPacienteLogin(){
		PacienteDAO pacienteDAO = new PacienteDAO();
		LoginDAO loginDAO = new LoginDAO();
		
		PacienteDomain novoPaciente = new PacienteDomain();		
		novoPaciente.setNome("João Lopes");
		novoPaciente.setEndereco("Rua Coronel Falamansa");
		novoPaciente.setCidade("Arco Verde");
		novoPaciente.setSexo("M");
		novoPaciente.setTelefoneCasa("8335640298");
		
		pacienteDAO.salvaPaciente(novoPaciente);
		assertTrue(novoPaciente.getId() > 0);
		
		LoginDomain login = new LoginDomain();
		login.setLogin("jlopes");
		login.setSenha("senha123");
		login.setPaciente(novoPaciente);
		pacienteDAO.salvaPaciente(novoPaciente);
		assertEquals(novoPaciente.getId(), login.getPaciente().getId());
		
		List<PacienteDomain> listaPacientes2 = pacienteDAO.listaPacientes();
		for (PacienteDomain pacienteDomain : listaPacientes2) {
			pacienteDAO.excluiPaciente(pacienteDomain);
		}

		List<LoginDomain> listaLogin2 = loginDAO.listaLogins();
		for (LoginDomain loginDomain : listaLogin2) {
			loginDAO.excluiLogin(loginDomain);
		}

	}


}
