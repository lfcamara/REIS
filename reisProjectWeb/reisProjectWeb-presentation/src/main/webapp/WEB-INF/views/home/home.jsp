<%@ include file="/WEB-INF/views/includeTags.jsp"%>

<div class="mdl-grid">
<div class="row">
	<div class="mdl-cell mdl-cell--12-col" style="position: absolute;">
		<div class="col-md-6">			
			<div class="col-md-12">
				<div class="h6"> Dados do Paciente </div> <hr>
				
	  			<div class="panel panel-default">	  				
		  			<div class="panel-body h4-small">
		  				
		  				<div class="col-md-2 reis-verde-NUTES"> Nome: </div>
		  				<div class="col-md-10 reis-cor-preto"> &nbsp;${paciente.nome} ${paciente.sobrenome} </div> 
		  				
		  				<div class="col-md-5 reis-verde-NUTES"> Data de Nascimento: </div>
		  				<div class="col-md-7 reis-cor-preto"> ${paciente.dataNascimento} </div>
		  				
						<div class="col-md-2 reis-verde-NUTES"> Sexo: </div>
		  				<div class="col-md-10 reis-cor-preto"> &nbsp;${paciente.sexo} </div>
		  				
		  				<!-- 
		  				<div class="col-md-2 reis-verde-NUTES"> Idade: </div>
		  				<div class="col-md-4 reis-cor-preto"> ${paciente.idade} anos </div>
		  				 -->
		  				<div class="col-md-2 reis-verde-NUTES"> Endereço: </div>
		  				<div class="col-md-10 reis-cor-preto"> &nbsp;${paciente.endereco} </div>
						
		  				<div class="col-md-2 reis-verde-NUTES"> </div>
		  				<div class="col-md-10 reis-cor-preto"> &nbsp;${paciente.cidade}, ${paciente.estado} </div>
						
						<div class="col-md-2 reis-verde-NUTES"> Telefone: </div>
		  				<div class="col-md-10 reis-cor-preto"> &nbsp;${paciente.telefoneCasa} </div>
						
		  			</div>
		  			
	  				<div class="panel-minifooter text-right">
						<a class="reis-a" href="../home/perfil.html"> EDITAR </a>
	  				</div>
		  		</div>
	  		</div>
	  		<!-- 
	  		<c:if test="${verificaMedicao == true}">
		  		<div class="col-md-12">
					<div class="h6"> Histórico do Paciente </div> <hr>			  			

					
		  			<div class="panel panel-default">
			  			<div class="panel-body">
			  				
				  			<div class="col-md-12">
				  				<div class="col-md-2 reis-verde-NUTES"> </div>
				  				<div class="col-md-12 reis-cor-preto"> &nbsp;${paciente.cidade}, ${paciente.estado} </div>
				  				<div class="col-md-12 reis-cor-preto"> &nbsp;${paciente.cidade}, ${paciente.estado} </div>
				  			</div>
			  				
			  			</div>
			  		</div>			  		 
		  		</div>	  
		  	</c:if>	
		  	-->	 
	  	</div>
	  	
	  	<div class="col-md-6">
	  		<div class="col-md-12">
	  			<div class="h6"> Ultimas Medições </div> <hr>
	  		</div>
	  		
	  		<c:if test="${verificaMedicao == false}">
	  			<div class="col-md-12 text-center">
		  			<div class="h3-small reis-cor-cinzaClaro"> Nenhuma medição foi adicionada </div>
		  			<img class='img-responsive' src='../images/dispositivos_bgCinza.png' style="margin: auto;"/>		  			
		  			<a class="h3-small reis-a reis-cor-cinzaClaro" href="../home/medicao.html"> Adicionar medições </a>		  			
		  		</div>
	  		
	  		</c:if>
	  		
	  		<c:if test="${balanca != null}">
		  		<div class="col-md-12">	
		  			<div class="panel panel-default">
			  			<div class="panel-body h4-small">
				  			<div class="col-md-4">
				  				<div class="reis-verde-NUTES"> Peso <div class="reis-hr"></div> </div>
				  				<div class="reis-cor-preto text-center"> <span class="h6">${balanca.peso}</span> ${balanca.pesoUnidade}</div>
				  			</div>
				  				
				  			<div class="col-md-4">
				  				<div class="reis-verde-NUTES"> Altura <div class="reis-hr"></div> </div>
				  				<div class="reis-cor-preto text-center"> <span class="h6">${balanca.altura}</span> ${balanca.alturaUnidade}</div>
				  			</div>
				  				
				  			<div class="col-md-4">
				  				<div class="reis-verde-NUTES"> IMC <div class="reis-hr"></div> </div>
				  				<div class="reis-cor-preto text-center"> <span class="h6">${balanca.massa}</span> ${balanca.massaUnidade}</div>
				  			</div>
			  			</div>
			  			<div class="panel-minifooter text-right"> 
			  				${balanca.dataHoraFormatada}
			  			</div>	  			
		  			</div>
		  		</div>
	  		</c:if>
	  		
	  		<c:if test="${oximetro != null}">
		  		<div class="col-md-12">
		  			<div class="panel panel-default">  				
			  			<div class="panel-body h4-small">
			  				<div class="col-md-6">
			  					<div class="reis-verde-NUTES"> SpO2 <div class="reis-hr"></div> </div>
			  					<div class="reis-cor-preto text-center"> <span class="h6">${oximetro.spo2}</span> ${oximetro.spo2Unidade}</div>
			  				</div>
			  				
			  				<div class="col-md-6">
			  					<div class="reis-verde-NUTES"> Taxa de Pulso <div class="reis-hr"></div> </div>
			  					<div class="reis-cor-preto text-center"> <span class="h6">${oximetro.taxaPulso}</span> ${oximetro.taxaPulsoUnidade}</div>
			  				</div>		  			
			  			</div>
			  			<div class="panel-minifooter text-right">
							${oximetro.dataHoraFormatada}
		  				</div>
		  			</div>
		  		</div>
	  		</c:if>
	  		
	  		<c:if test="${pressao != null}">
		  		<div class="col-md-12">
		  			<div class="panel panel-default">
			  			<div class="panel-body h4-small">
			  				<div class="col-md-6">
			  					<div class="reis-verde-NUTES"> Pressão Arterial <div class="reis-hr"></div> </div>
			  					<div class="reis-cor-preto text-center"> 
			  						<span class="h6">${pressao.pressaoSistolica}/${pressao.pressaoDiastolica}</span> ${pressao.pressaoSistolicaUnidade}
			  					</div>
			  				</div>
			  				
			  				<div class="col-md-6">
			  					<div class="reis-verde-NUTES"> Taxa de Pulso <div class="reis-hr"></div> </div>
			  					<div class="reis-cor-preto text-center"> <span class="h6">94.23</span> bpm</div>
			  				</div>		
			  			</div>
			  			<div class="panel-minifooter text-right">
							${pressao.dataHoraFormatada}
		  				</div>
		  			</div>
		  		</div>	  		
	  		</c:if>
	  	</div>
	  	
	</div>
</div>
</div>
