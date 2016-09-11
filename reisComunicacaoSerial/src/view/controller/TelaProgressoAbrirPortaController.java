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

import application.Aplicacao;
import application.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;

public class TelaProgressoAbrirPortaController implements Initializable {
	
	@FXML
	private ProgressBar progressBar;
	
	boolean abriuPorta = false;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		doInBackground();
	}

	private void doInBackground() {

		Task<Void> task = new Task<Void>() {

			@Override
			public Void call() {
				abriuPorta = Aplicacao.portaSerial.abrirPorta();
				return null;
			}
			
			@Override
			protected void done() {
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						if(abriuPorta){
							Main.STAGE.close();
							new Main().openScene("/arquivosFXML/telaprincipal.fxml");
						}
						else{
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("Não foi possível identificar o dispositivo");
							alert.setTitle("REIS");
							alert.setHeaderText(null);
							alert.showAndWait();
							Aplicacao.portaSerial.fecharPorta();
							Main.STAGE.close();
							new Main().openScene("/arquivosFXML/telainicio.fxml");
						}
						
					}
				});
			}
		};
		
		progressBar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
	}

}
