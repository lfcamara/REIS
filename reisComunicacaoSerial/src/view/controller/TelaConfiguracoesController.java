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
package view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import business.RecuperarLogin;
import business.SalvarLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.LoginDomain;

public class TelaConfiguracoesController implements Initializable{

    @FXML
    private TextField txtLogin;

    @FXML
    private Button btnSalvar;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtLink;

    @FXML
    private Button btnCancelar;

    @FXML
    void btnSalvarClick(ActionEvent event) {
    	
    	LoginDomain loginDomain = new LoginDomain();
    	loginDomain.setLogin(txtLogin.getText());
    	loginDomain.setSenha(txtSenha.getText());
    	loginDomain.setLink(txtLink.getText());
    	SalvarLogin salvarLogin = new SalvarLogin();
    	salvarLogin.salvar(loginDomain);
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Alterações realizadas com sucesso");
		alert.setTitle("REIS");
		alert.setHeaderText(null);
		alert.showAndWait();
    }

    @FXML
    void btnCancelarClick(ActionEvent event) {
    	new Main().openStage("/arquivosFXML/telainicio.fxml");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoginDomain loginDomain = new RecuperarLogin().getSave();
		txtLink.setText(loginDomain.getLink());
		txtLogin.setText(loginDomain.getLogin());
		txtSenha.setText(loginDomain.getSenha());
		
	}

}
