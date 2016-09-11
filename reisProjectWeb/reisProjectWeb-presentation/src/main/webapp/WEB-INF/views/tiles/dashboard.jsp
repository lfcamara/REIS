<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="../images/favicon/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">

	<title><tiles:insertAttribute name="title" ignore="true" /></title>

	<%@ include file="/WEB-INF/views/importsDashboard.jsp"%>
	<%@ include file="/WEB-INF/views/includeTags.jsp"%>
	<%@ include file="/WEB-INF/views/includeLinks.jsp"%>
</head>
<body>
	<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
		<!-- Topo -->
		<header class="demo-header mdl-layout__header reis-bg-cor-branco reis-cor-azulEscuro">
			<div class="mdl-layout__header-row">
				<span class="mdl-layout-title"><tiles:insertAttribute name="title" ignore="true" /></span>
				<div class="mdl-layout-spacer"></div>

				<button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
					<i class="material-icons">more_vert</i>
				</button>
				<ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
					<a class="mdl-menu__item" href="../home/perfil.html">Perfil</a>
					<a class="mdl-menu__item" href="../index/sair.html">Sair</a>
				</ul>
			</div>
			<div class="linhaVerde"></div>
		</header>
		
		<!-- Menu lateral -->
		<div class="demo-drawer mdl-layout__drawer">
			<header class="demo-header mdl-layout__header reis-bg-cor-branco reis-cor-azulEscuro">
				<img class='img-responsive' width="90px" height="45px"
					src='../images/reis_icone_topo_vermelho.png' style="margin: auto;"/>
			</header>
			
			<!-- Links laterais -->
			<nav class="demo-navigation mdl-navigation reis-bg-cor-azulEscuro">				
				<a class="mdl-navigation__link" href="../home/home.html"> 
					<i class="fa fa-home"></i><span class="">Início</span>
				</a>  
				<a class="mdl-navigation__link" href="../home/perfil.html"> 
					<i class="fa fa-user"></i>Perfil
				</a> 
				<a class="mdl-navigation__link " href="../home/medicao.html"> 
					<i class="fa fa-user-md"></i>Medição
				</a> 
				<a class="mdl-navigation__link" href="../home/historico.html"> 
					<i class="fa fa-bar-chart"></i>Histórico
				</a>
				<a class="mdl-navigation__link" href="../home/hl7.html"> 
					<i class="fa fa-file-text-o"></i>HL7
				</a>
				
				<div class="mdl-layout-spacer"></div>
				
				<a class="mdl-navigation__link" href="http://nutes.uepb.edu.br/">
					<i>NUTES - UEPB</i> 
					<span class="visuallyhidden">Ajuda</span>
				</a>
			</nav>
		</div>
		
		<!-- Conteúdo da página -->
		<main class="mdl-layout__content mdl-color--white">
			<div class="mdl-grid ">
				<section>
					<tiles:insertAttribute name="body" />
				</section>
			</div>
		</main>
	</div>

</body>
</html>