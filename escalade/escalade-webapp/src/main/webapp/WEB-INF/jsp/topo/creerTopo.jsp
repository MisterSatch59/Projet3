<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<h1><s:text name="modifierTopo.title" /> : <s:property value="topo.titre" /></h1>

	<div class="jumbotron marge container">
		<s:form action="creerTopo" class="formClassiq" method="POST" enctype="multipart/form-data">

			<s:textfield id="titre" name="titre" key="titre" requiredLabel="true" maxlength="100" value="%{titre}" class="form-control" />
		
			<s:textfield id="descriptionTitre" name="descriptionTitre" key="modifierSpot.presentation" requiredLabel="true" maxlength="100" value="%{descriptionTitre}" class="form-control marge" />

			<s:textarea id="descriptionTexte" name="descriptionTexte" requiredLabel="false" value="%{descriptionTexte}" class="form-control miniMarge" cols="150" rows="6" />
			
			<s:optiontransferselect id = "listSpot" key="modifiertopo.listSpot" name="listSpotId" requiredLabel="true" doubleName=""  list="{}" doubleList="listTTSpot" value="" doubleValue="" size="15" doubleSize="15" class="form-control  marge" doubleCssClass="form-control  marge"/>
			
			<s:file name="myFile" key="photosPresentationTopo" accept="image/*" />

			<s:submit class="col-sm-offset-4 col-sm-4 col-xs-12 marge btn btn-default btn-custom" key="valider" name="" />

		</s:form>
	</div>

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>