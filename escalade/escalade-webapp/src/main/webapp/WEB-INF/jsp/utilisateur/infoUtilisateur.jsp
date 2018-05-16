<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<h1><s:text name="profilUtilisateur" /> - <s:property value="#session.utilisateur.pseudo" /></h1>


	<s:form action="modifierUtilisateur">
		<legend>
			<s:text name="modifierUtilisateur.title" />
		</legend>
		
		<s:password id = "mdp" name="mdp" key="mdp" requiredLabel="true" />
		<s:password id = "mdp2" name="mdp2" key="confirmation" requiredLabel="true" />
		
		<s:textfield id = "email" name="email" key="email" requiredLabel="true" type="email" value="%{#session.utilisateur.mail}"/>
	
		<s:submit key="valider" name=""/>
	</s:form>
	
	<br/><br/>
	<h3><s:text name="listEmprunt.title" /></h3>
	<s:iterator value="listEmprunt">
		<s:a action="infoTopo"><s:param name="titreTopo" value="exemplaire.topo.titre"/><s:property value="exemplaire.topo.titre" /></s:a> - <s:property value="dateDebut" /> - <s:property value="dateFin" /> - <s:text name="proprio" /> : <s:property value="exemplaire.proprietaire.pseudo" /> - 
		<s:text name="email" /> : <s:property value="exemplaire.proprietaire.mail" /> - <s:a action="supprimerEmprunt"><s:param name="empruntId" value="id"/><s:text name="supprimerEmprunt" /></s:a>
		<br/>
	</s:iterator>
	
	<br/><br/>
	<h3><s:text name="listExemplaireTopo.title" /></h3>
	<s:iterator value="listExemplaireTopo">
		<s:a action="infoTopo"><s:param name="titreTopo" value="topo.titre"/><s:property value="topo.titre" /></s:a> - 
		<s:a action="infoExemplaireTopo"><s:param name="exemplaireId" value="id"/><s:text name="voirPeriodeEmprunt" /></s:a>
		<br/>
	</s:iterator>
	<br/>
	<s:a action="ajouterExemplaireTopo"><s:text name="ajouterExemplaire" /></s:a>
</body>
</html>