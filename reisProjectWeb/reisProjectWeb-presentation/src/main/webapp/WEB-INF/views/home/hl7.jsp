<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<%@ include file="/WEB-INF/views/importsDashboard.jsp"%>

<div class="mdl-grid">
<div class="row">
	<div class="mdl-cell mdl-cell--12-col" style="position: absolute;">
	
		<div class="col-md-12">			
			<div class="col-md-12">
				<div class="h6"> Mensagem HL7 </div> <hr>
				<c:if test="${status == null}">
					<div class="alert alert-warning" role="alert">
						<span class="close" data-dismiss="alert" aria-label="close">&times;</span>
						<span class="fa fa-exclamation-circle" aria-hidden="true"></span> 
						<span class="sr-only">Aviso:</span> Selecione os tipos de medição para gerar uma mensagem HL7.
					</div>
				</c:if>
				
				<c:if test="${status == 1}">
					<div class="alert alert-danger" role="alert">
						<span class="close" data-dismiss="alert" aria-label="close">&times;</span>
						<span class="fa fa-exclamation-circle" aria-hidden="true"></span> 
						<span class="sr-only">Erro:</span> ${mensagemErro}
					</div>
				</c:if>
			</div>
			
			<form:form id="formMensagemHL7" modelAttribute="mensagemHL7" method="post">					
				<div class="col-md-12">
					<label class="mdl-checkbox__label h4-small">Tipo de Medição</label>
					<div class="panel panel-default">	  				
				  		<div class="panel-body h4-small">
				  			<div class="col-md-4">
							   	<label class="mdl-checkbox mdl-js-checkbox" for="checkbox_balanca">
									<input type="checkbox" id="checkbox_balanca" class="mdl-checkbox__input">
							        <span class="mdl-checkbox__label">Balança</span>
							    </label>
						    </div>
						    
						    <div class="col-md-4">     
							    <label class="mdl-checkbox mdl-js-checkbox" for="checkbox_oximetro">
							    	<input type="checkbox" id="checkbox_oximetro" class="mdl-checkbox__input">
							    	<span class="mdl-checkbox__label">Oximetro de Pulso</span>
							    </label>
							</div>
							
							<div class="col-md-4">
							   	<label class="mdl-checkbox mdl-js-checkbox" for="checkbox_pressao">
									<input type="checkbox" id="checkbox_pressao" class="mdl-checkbox__input">
							        <span class="mdl-checkbox__label">Medidor de Pressão Arterial</span>
							    </label>
							</div>
					  	</div> 
					  	
					  	
				  	</div>
				</div>
									
				<div class="col-md-12">
					<button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect btn-azul"
							onclick="verificar_checkboxes()"> Gerar Mensagem HL7 </button>
				</div>
				
				<div class="col-md-12">
					<c:if test="${status == 0}">	
						<br>
						<div class="form-group">
						  	<label for="mdl-checkbox__label h4-small">Tipo de Medição</label>
						  	<textarea class="form-control" rows="12" id="comment">${mensagemHL7.mensagem}</textarea>
						</div>
					</c:if>
				</div>
				
				
				<!-- O conteúdo desta div estará invisível ao usuário.
				 	 Ele serve apenas para armazenar os retornos true/false nos campos
					 referentes aos três dispositivos da classe mensagemHL7 -->
				<div class="col-md-4" style="display: none">
					<div class="form-group">
						<form:input path="habilita_balanca" type="text" value="" placeholder="balanca" class="form-control" />
						<form:input path="habilita_oximetro" type="text" value="" placeholder="oximetro" class="form-control" />
						<form:input path="habilita_pressao" type="text" value="" placeholder="pressao" class="form-control" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
</div>

<script>
	function verificar_checkboxes() {		
		var check_balanca = document.getElementById("checkbox_balanca");
		var check_oximetro = document.getElementById("checkbox_oximetro");
		var check_pressao = document.getElementById("checkbox_pressao");
		
		//teste para a balança
		if (check_balanca.checked == true) {
			document.getElementById("habilita_balanca").value = true;
		} else {
			document.getElementById("habilita_balanca").value = false;
		}
		
		//teste para o oximetro
		if (check_oximetro.checked == true) {
			document.getElementById("habilita_oximetro").value = true;
		} else {
			document.getElementById("habilita_oximetro").value = false;
		}
		
		//teste para o medidor de pressao
		if (check_pressao.checked == true) {
			document.getElementById("habilita_pressao").value = true;
		} else {
			document.getElementById("habilita_pressao").value = false;
		}
	}	
</script>

<script src="../js/flat-ui-reis/flat-ui-reis.js"></script>