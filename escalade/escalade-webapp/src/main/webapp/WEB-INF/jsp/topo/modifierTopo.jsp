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
		<s:form action="modifierTopo" class="formClassiq" method="POST" enctype="multipart/form-data">
		
			<s:textfield id="descriptionTitre" name="descriptionTitre" key="modifierSpot.presentation" requiredLabel="true" maxlength="100" value="%{topo.description.titre}" class="form-control marge" />

			<s:set var="texte" value="" />
			<s:iterator value="%{topo.description.listParagraphes}" var="parag">
				<s:if test="%{#texte==null}">
					<s:set var="texte" value="%{parag}" />
				</s:if>
				<s:else>
					<s:set var="texte" value="%{#texte + #parag}" />
				</s:else>
				<s:set var="texte" value="%{#texte + '\n'}" />
			</s:iterator>
			<s:textarea id="descriptionTexte" name="descriptionTexte" requiredLabel="false" value="%{texte}" class="form-control miniMarge" cols="150" rows="6" />
			
			<s:optiontransferselect id = "listSpot" key="modifiertopo.listSpot" name="listSpotId" requiredLabel="true" doubleName=""  list="listSpot" doubleList="listTTSpot" value="" doubleValue="" size="15" doubleSize="15" class="form-control  marge" doubleCssClass="form-control  marge"/>

			
			<s:hidden name="titreTopo" value="%{topo.titre}" />
			
			<s:file name="myFile" key="photosPresentationTopo" accept="image/*" />

			<s:submit class="col-sm-offset-4 col-sm-4 col-xs-12 marge btn btn-default btn-custom" key="valider" name="" />

		</s:form>
	</div>

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>