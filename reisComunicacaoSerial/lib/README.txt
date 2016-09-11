Extensão do REIS para leitura de dados do Oxímetro através da porta serial

Para o funcionamento adequado desta extensão do REIS o computador deve estar configurado de forma adequada.
As restrições para o devido funcionamento são:

O computador deve estar com a M�quina virtual java instalada.
A versão Java utilizada foi 1.8.0_101
A máquina virtual deve ser a versão de 32bits devido a limitação da API de comunicação Serial da Oracle Javacomm, 
visto que uma DLL está disponível apenas para a referida versão.

A API Javacomm deve estar corretamente configurada, para configurar siga os passos:
 - Copie o arquivo comm.jar para C:\Program Files (x86)\Java\jre1.8.0_101\lib\ext
 - Configure o classpath para que reconheça o arquivo comm.jar 
 - Copie o arquivo javax.comm.properties para C:\Program Files (x86)\Java\jre1.8.0_101\lib
 - Copie o arquivo win32com.dll para C:\Program Files (x86)\Java\jre1.8.0_101\bin
 - Caso necessário, configure o projeto para reconhecer a biblioteca comm.jar

Verifique se o projeto executa normalmente no eclipse