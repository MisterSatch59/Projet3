<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<s:a action="index"> <img src="img/logoBleu.png" class="miniLogo" /></s:a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><s:a action="index"><s:text name="accueil" /></s:a></li>
				<li><s:a action="infos"><s:text name="infos" /></s:a></li>
				<li><s:a action="spots"><s:text name="spots" /></s:a></li>
				<li><s:a action="topos"><s:text name="topos" /></s:a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<s:if test="#session.utilisateur">
					<li><s:a action="infoUtilisateur"><s:property value="#session.utilisateur.pseudo" /> - <s:text name="infoUtilisateur" /></s:a></li>
					<li><s:a action="logout"><s:text name="deconnexion" /></s:a></li>
				</s:if>
				<s:else>
					<li><s:a action="login"><s:text name="connexion" /></s:a></li>
					<li><s:a action="creerUtilisateur"><s:text name="creer.compte" /></s:a></li>
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

<s:actionerror class="alert alert-danger list-unstyled"/><!-- Bandeau rouge pour les messages d'erreur -->
<s:actionmessage class="alert alert-info list-unstyled"/><!-- Bandeau bleu pour les message d'action -->

