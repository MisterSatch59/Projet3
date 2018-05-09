<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<header>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
			<!--  
				<s:a action="index" class="navbar-brand"><img src="img/logo.png" alt= <s:text name="header.accueil"/> title= <s:text name="header.accueil"/> /></s:a>
			-->
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li><s:a action="index">Accueil</s:a></li>
					<li><s:a action="infos"><s:text name="infos" /></s:a></li>
					<li><s:a action="spots"><s:text name="spots" /></s:a></li>
					<li><s:a action="topos"><s:text name="topos" /></s:a></li>
					
					<s:if test="#session.user">
						<li><s:a action="logout"><s:text name="deconnection" /></s:a></li>
					</s:if>
					<s:else>
						<li><s:a action="login"><s:text name="connection" /></s:a></li>
						<li><s:a action="creerCompte"><s:text name="creer.compte" /></s:a></li>
					</s:else>
					
					<li>
						<s:a action="index">
							<s:param name="request_locale">en</s:param>
							<img alt="[English]" src="img/en.png">
						</s:a>
					</li>
					<li>
						<s:a action="index">
							<s:param name="request_locale">fr</s:param>
							<img alt="[Français]" src="img/fr.png">
						</s:a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

</header>

<s:actionerror />
<s:actionmessage />
