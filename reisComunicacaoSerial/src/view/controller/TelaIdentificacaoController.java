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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Aplicacao;
import application.Main;
import business.RecuperarLogin;
import conexaohttp.ConexaoHTTP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.LoginDomain;

public class TelaIdentificacaoController implements Initializable{
	@FXML
	private TextField txtLogin;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	@FXML
	private PasswordField psSenha;

	@FXML
	void btnConfirmarClick(ActionEvent event) {
		boolean dadosValidos = true;
		if (txtLogin.getText().isEmpty() || psSenha.getText().isEmpty()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Todos os campos devem ser preenchidos");
			alert.setTitle("REIS");
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		Aplicacao.login = txtLogin.getText();
		Aplicacao.senha = psSenha.getText();
		if(new ConexaoHTTP().enviar(Aplicacao.login, Aplicacao.senha, Aplicacao.hora, Aplicacao.porcentagem,
				Aplicacao.saturacao)){
			new Main().openStage("/arquivosFXML/telaconfirmacao.fxml");
		}

		

	}

	@FXML
	void btnCancelarClick(ActionEvent event) {
		new Main().openStage("/arquivosFXML/telaprincipal.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		RecuperarLogin recuperarLogin = new RecuperarLogin();
		LoginDomain loginDomain = recuperarLogin.getSave();
		txtLogin.setText(loginDomain.getLogin());
		psSenha.setText(loginDomain.getSenha());
		
	}
}
