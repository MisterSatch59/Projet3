<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>

	<h1>
		<s:text name="ajouterExemplaire.titre" /> "<s:property value="topo.titre" />"
	</h1>

	<s:form action="ajouterExemplaireTopo">
		<legend>
			<s:text name="commentaireOuConditions" />
		</legend>
		<s:textfield id="titre" name="titre" key="titre" />
		<s:textarea id="texte" name="texte" requiredLabel="true" />
		
		<s:hidden name="titreTopo" value="%{topo.titre}" />
		
		<s:submit key="valider" name=""/>

	</s:form>

</body>
</html>