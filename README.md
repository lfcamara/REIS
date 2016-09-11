# REIS
Registro Eletrônico para Interoperabilidade em Saúde - Electronic Health Records for Interoperability (REIS)

### O que é o REIS? 
O REIS é um projeto desenvolvido por um grupo de estudantes da UEPB/NUTES com o intuito de tornar o acompanhamento de dispositivos de saúde de uso pessoal interoperável. Nossa proposta é armazenar em um perfil o acompanhamento das medições de alguns dispositivos de uso pessoal voltados para a saúde.

### Quais são estes dispositivos?
Oxímetro de Pulso, Balança e Medidor de Pressão Arterial

### Organização deste projeto
O projeto está organizado da seguinte forma:
  - [documentacao](https://github.com/Project-HAM/REIS/tree/master/documentacao) - <b>Documentação do projeto</b>
    - [javadocs](https://github.com/Project-HAM/REIS/tree/master/documentacao/Javadocs) - Arquivos do javadoc do projeto
    - [executaveis](https://github.com/Project-HAM/REIS/tree/master/documentacao/executaveis) - Arquivos compilados para implantacao do sistema reis. Existem 2 arquivos: 
      - O sistema web (reis.rar) para o acompanhamento das medições do paciente
      - O sistema local (reisComunicacaoSerial.rar) para obter a medição de dispositivos que utilizam comunicação com a porta serial (neste sistema foi implementado o módulo de comunicação com o dispositivo pessoal oximetro de pulso).
    - [exemplos de arquivos XML] (https://github.com/Project-HAM/REIS/tree/master/documentacao/exemplos%20de%20arquivos%20XML) - Modelos de arquivos de medições XML
    - [manuais] (https://github.com/Project-HAM/REIS/tree/master/documentacao/manuais) - Manuais de instalação do projeto
      - reis_manual_desenvolvedores.pdf - Manual de instalação do reis para a equipe de desenvolvimento
      - reis_manual_instalacao.pdf - Manual de instalação do sistema reis para ambiente de produção
  
  
  - [reisComunicacaoSerial](https://github.com/Project-HAM/REIS/tree/master/reisComunicacaoSerial) - 
    <b>codigo fonte</b>  
    Um sistema local realizar a leitura do oxímetro de pulso através da porta serial e enviar para o sistema reis web
 
  - [reisProjectWeb](https://github.com/Project-HAM/REIS/tree/master/reisProjectWeb) - 
    <b>codigo fonte</b>  
    Um sistema web para acompanhar de informações de saúde do paciente, obtidas através da leitura de medições de três dispositivos de uso pessoal: o oxímetro de pulso, a balança e o medidor de pressão arterial.
