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
package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Classe principal da aplicação
 */
public class Main extends Application {

	public static Scene SCENE;
	public static Stage STAGE;

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(
				"/arquivosFXML/TelaProgresso.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("REIS");
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.getIcons().add(
				   new Image(
				      this.getClass().getResourceAsStream( "/arquivosFXML/reis_logo.png" ))); 

		stage.show();

		STAGE = stage;
		SCENE = scene;
		STAGE.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
				
			}
		});

	}
	
	public static void main(String[] args) throws IOException {
		try {
			launch(args);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

			System.exit(0);
			
		}
	}
	
	public void openStage(String arquivofxml){
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource(arquivofxml));
			
			SCENE = new Scene(parent);
			
			STAGE.setScene(SCENE);
			STAGE.setTitle("Sistema REIS");
			STAGE.sizeToScene();
			STAGE.centerOnScreen();
			STAGE.getIcons().add(
					   new Image(
					      this.getClass().getResourceAsStream( "/arquivosFXML/reis_logo.png" ))); 
			
			
			
			STAGE.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void openScene(String arquivofxml){
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource(arquivofxml));
			if(arquivofxml.equals("/arquivosFXML/TelaProgressoAbrirPorta.fxml")){
				STAGE = new Stage();
				STAGE.initStyle(StageStyle.UNDECORATED);
			}
			else {
				STAGE = new Stage();
				STAGE.initStyle(StageStyle.DECORATED);
			}
			SCENE = new Scene(parent);
			
			STAGE.setScene(SCENE);
			STAGE.setTitle("Sistema REIS");
			STAGE.sizeToScene();
			STAGE.centerOnScreen();
			STAGE.getIcons().add(
					   new Image(
					      this.getClass().getResourceAsStream( "/arquivosFXML/reis_logo.png" ))); 
			
			
			
			STAGE.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
