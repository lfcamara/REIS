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
import application.PortaSerial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TelaPrincipalController extends Thread implements Initializable{

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnReceberDados;
    
    @FXML
    private Button btnVoltar;

    @FXML
    private TextArea txtAreaInformacoesDoDispositivo;
    
    @FXML
    private TextField txtDadosParaEnvio;

    boolean deveAtualizar = true;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			if(txtDadosParaEnvio.getText().isEmpty()){
    			txtDadosParaEnvio.setText(Aplicacao.ultimosDadosRecebidos);
    			Aplicacao.dados = Aplicacao.ultimosDadosRecebidos;
    		}

			txtAreaInformacoesDoDispositivo.setText(Aplicacao.historicoDeLeitura + "\n");

			Aplicacao.portaSerial.lendoDados = "";
			start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

    
    @FXML
    void btnEnviarClick(ActionEvent event) {
    	if(Aplicacao.dados.isEmpty()){
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Voc� deve clicar para capiturar os dados do dispositivo");
			alert.setTitle("REIS");
			alert.setHeaderText(null);
			alert.showAndWait();
    	}else{

    		deveAtualizar = false;
    		String leitura = Aplicacao.dados;
    		String aux = "";
    		boolean ehHora = true;
    		for (int i = 0; i < leitura.length(); i++) {
    			try {
    				if(ehHora && leitura.charAt(i)==':'){
        				aux+=leitura.charAt(i);
        				ehHora=false;
    				}
    				if(ehHora || !(i<leitura.length()-1 && leitura.charAt(i+1)==':') ){
    					Integer.parseInt(leitura.charAt(i)+"");
        				aux+=leitura.charAt(i);
    				}
    				
    			} catch (Exception e) {
    				if(leitura.charAt(i)==' '){
    					aux+=leitura.charAt(i);
    				}
    			}
    		}
    		
    		Aplicacao.dados = aux;
    		String[] valores = Aplicacao.dados.split("   ");
    		try {
    			Aplicacao.hora = valores[0];
        		Aplicacao.porcentagem = valores[1];
        		Aplicacao.saturacao = valores[2];
			} catch (Exception e) {
			}
    		new Main().openStage("/arquivosFXML/telaidentificacao.fxml");
    	}
    	
    }

    @FXML
    void btnReceberDadosClick(ActionEvent event) {
    	try {
    		txtDadosParaEnvio.setText(Aplicacao.ultimosDadosRecebidos);
    		Aplicacao.dados = Aplicacao.ultimosDadosRecebidos;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
     @Override
    public void run() {
    	 
    	while(deveAtualizar){
    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    		}
    		System.out.println(deveAtualizar);
    		if(!Aplicacao.portaSerial.lendoDados.isEmpty()){
				Aplicacao.ultimosDadosRecebidos = Aplicacao.portaSerial.lendoDados;
				Aplicacao.historicoDeLeitura=Aplicacao.portaSerial.lendoDados+Aplicacao.historicoDeLeitura;
			}

    		if(txtDadosParaEnvio.getText().isEmpty()){
    			txtDadosParaEnvio.setText(Aplicacao.ultimosDadosRecebidos);
    			Aplicacao.dados = Aplicacao.ultimosDadosRecebidos;
    		}
    		
			txtAreaInformacoesDoDispositivo.setText(Aplicacao.historicoDeLeitura + "\n");

			Aplicacao.portaSerial.lendoDados = "";
    		
    		
    	}
    }
     @FXML
     void btnVoltarClick(ActionEvent event) {
    	 try {

        	 deveAtualizar = false;
    	 Aplicacao.portaSerial.fecharPorta();
		} catch (Exception e) {
		}
    	 new Main().openStage("/arquivosFXML/telainicio.fxml");
     }
}
