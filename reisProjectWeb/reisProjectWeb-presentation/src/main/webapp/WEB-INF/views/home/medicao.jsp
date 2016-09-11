<%@ include file="/WEB-INF/views/includeTags.jsp"%>

<div class="mdl-grid">
<div class="row">
	<div class="mdl-cell mdl-cell--12-col"  style="position: absolute;">			
		<div class="col-md-12">
			<form:form modelAttribute="uploadItem" method="post" id="formMedicao" enctype="multipart/form-data">
				<div class="col-md-12">				
				<c:if test="${status == 0}">
					<div class="alert alert-success" role="alert">
						<span class="close" data-dismiss="alert" aria-label="close">&times;</span>
						<span class="fa fa-exclamation-circle" aria-hidden="true"></span> 
						<span class="sr-only">Sucesso:</span> ${mensagem}
					</div>
				</c:if>
							
				<c:if test="${status == 1}">
					<div class="alert alert-danger" role="alert">
						<span class="close" data-dismiss="alert" aria-label="close">&times;</span>
						<span class="fa fa-exclamation-circle" aria-hidden="true"></span> 
						<span class="sr-only">Erro:</span> ${mensagem}
					</div>
				</c:if>
				</div>
				
				<div class="mdl-grid">
					<div class="mdl-cell mdl-cell--4-col">
						<label class="h4-small">Tipo de Medição</label>
						<form:input path="tipo_dispositivo" type="text" class="form-control"
							id="tipoDispositivo" cssStyle="display: none;" />
						<div class="form-group">
							<select data-toggle="select" id="tipo"
								class="form-control select select-primary select-lg">						
								<option value="0">Oximetro</option>
								<option value="1">Balança</option>
								<option value="2">Pressão</option>
							</select>
						</div>
					</div>
					
					<div class="mdl-cell mdl-cell--8-col">
						<form:label for="fileData" path="fileData">Arquivo XML</form:label>
						<form:input path="fileData" type="file" cssClass="form-control" />
					</div>
						
					<div class="mdl-cell mdl-cell--12-col">
						<input type="button" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect btn-verde" onclick="enviar()" value="Enviar" />				
					</div>
				</div>
			</form:form>
		</div>
		
		<br>
		
		<div class="col-md-6">
			<c:if test="${tipoDispositivo != null}">
				<div class="col-md-12">	 
					<div class="h6"> Medição Cadastrada</div> <hr>
				</div>
			</c:if>
			
			<c:if test="${tipoDispositivo == 1}"> <!--  Balança -->
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
	  		
	  		<c:if test="${tipoDispositivo == 0}"> <!-- Oximetro -->
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
	  		
	  		<c:if test="${tipoDispositivo == 2}"> <!-- pressão -->
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

<script>
	function enviar() {
		var tipo = document.getElementById("tipo").value;
		document.getElementById("tipoDispositivo").value = tipo;
		document.getElementById("formMedicao").submit();
	}
</script>
