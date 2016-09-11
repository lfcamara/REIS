<%@ include file="/WEB-INF/views/includeTags.jsp"%>
 
	<c:if test="${statusExcluir == 0}">
		<div class="alert alert-success" role="alert" >
			<span class="close" data-dismiss="alert" aria-label="close">&times;</span>
			<span class="fa fa-exclamation-circle" aria-hidden="true"></span> 
			<span class="sr-only">Sucesso:</span> ${mensagem}
		</div>
	</c:if>

<div class="mdl-grid">
	<!-- Div lateral - Logo REIS -->		
	<div class="mdl-cell mdl-cell--4-col text-center">
		<img class="img-responsive" width="450px" height="450px"
			src='../images/reis_logo.png' style="margin: auto;" />
		<div class='row'>
			<div class="col-lg-12">
				<p>Registro Eletrônico para Interoperabilidade em Saúde</p>
			</div>
		</div>
	</div>
	
	<!-- Div lateral - Apresentação do REIS -->
	<div class="mdl-cell mdl-cell--8-col">
		<div class="col-lg-10">
			<h3 class="negrito">O que é o REIS?</h3>
			
			<p>Registro Eletrônico para Interoperabilidade em Saúde - REIS -
				é um projeto desenvolvido por um grupo de estudantes da Universidade
				Estadual da Paraíba com o intuito de tornar o acompanhamento de
				dispositivos de saúde de uso pessoal interoperável.</p>
		</div>
		
		<div class="col-lg-10">
			<h3 class="negrito">Qual a nossa proposta?</h3>
			
			<p>Nossa proposta é armazenar em um perfil o acompanhamento a
				cada medição de alguns dispositivos de uso pessoal voltado para a
				saúde.</p>
		</div>
		
		<div class="col-lg-10">
			<h3 class="negrito">
				Quais são esses dispositívos? <span class='fa fa-heartbeat'></span>
			</h3>
			
			<div class="col-md-4">
				<div class="col-md-3">
					<i class='fa fa-user-md' aria-hidden='true'></i> 
				</div>
				<div class="col-md-9"> <p>Balança</p>
				</div>
			</div>
			
			<div class="col-md-4">
				<div class="col-md-3">
					<i class='fa fa-user-md' aria-hidden='true'></i> 
				</div>
				<div class="col-md-9"> <p>Oxímetro de Pulso</p>
				</div>
			</div>

			<div class="col-md-4">
				<div class="col-md-3">
					<i class='fa fa-user-md' aria-hidden='true'></i> 
				</div>
				<div class="col-md-9"> <p>Medidor de Pressão</p>
				</div>
			</div>				
		</div>
	</div>
</div>
