<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		
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
	<div class="mdl-layout__container">
		<div class="demo-layout mdl-layout mdl-js-layout  mdl-layout--fixed-header">
			<!-- Topo - Menu lateral -->
			<header class="demo-header mdl-layout__header reis-cor-branco reis-bg-cor-azulClaro is-casting-shadow">
				<div class="mdl-layout__header-row">
					<div class=" mdl-layout-title">
						<a href="../index/index.html">
							<img class='img-responsive' width="90px" height="45px"
								 src='../images/reis_icone_topo_branco.png' style="margin: auto;"/>
						</a>
					</div>
					
					<div class="mdl-layout-spacer"></div>
					
					<button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
						<i class="material-icons">more_vert</i>
					</button>
					<ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
						<a class="mdl-menu__item" href="../index/index.html">Início</a>
						<a class="mdl-menu__item" href="../index/cadastrar.html">Cadastre-se</a>
						<a class="mdl-menu__item" href="../index/login.html">Entrar</a>
					</ul>									
				</div>	
			</header>
			
			<!-- Conteúdo da página -->
			<main class="mdl-layout__content">
				<div class="page-content" style="background-color: #fff;">
					<tiles:insertAttribute name="body" />
				</div>
			</main>			
		</div>
	</div>	
</body>
</html>