<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>

	<h1>
		<s:text name="ajouterExemplaire.titre" /> "<s:property value="topo.titre" />"
	</h1>

	<div class="row">
		<div class="jumbotron col-sm-offset-1 col-sm-10 col-xs-12 marge">
			<h3><s:text name="commentaireOuConditions" /></h3>
			<s:form action="ajouterExemplaireTopo" class="formClassiq" method="POST">
				<div class="container">
					<s:textfield id="titre" name="titre" key="titre" requiredLabel="true" class=" form-control"/>
					<s:textarea id="texte" name="texte" cols="150" rows="6" requiredLabel="true" class="form-control miniMarge"/>
					
					<s:hidden name="titreTopo" value="%{topo.titre}" />
					
					<s:submit class="col-sm-offset-3 col-sm-6 col-xs-12 marge btn btn-default btn-custom" key="valider" name=""/>
				</div>
			</s:form>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>