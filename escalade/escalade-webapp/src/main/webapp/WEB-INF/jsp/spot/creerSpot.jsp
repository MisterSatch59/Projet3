<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<s:form action="creerSpot">
		<legend>
			<s:text name="modifierSpot.title" />
		</legend>
		<s:textfield id = "nom" name="nom" key="nom" requiredLabel="true" maxlength="40" value="%{nom}" />
		<s:select id = "departement" name="departement" key="departement" list="listDepartements" emptyOption="true" requiredLabel="true" value="%{departement}" />
		
		<s:textfield id = "villeNom" name="villeNom" key="modifierSpot.ville.nom" requiredLabel="true" maxlength="100" value="%{villeNom}" />
		<s:textfield id = "villeCP" name="villeCP" key="cp" requiredLabel="true" maxlength="5" value="%{villeCP}" />
		
		<s:checkbox key="ouvert" name="ouvert" value="ouvert"/>
		
		<s:checkboxlist key="types" list="listTypes" name="types" value="types" />
		
		<s:checkboxlist key="profils" list="listProfils" name="profils" value="profils" />
		
		<s:select id = "difficulteMin" name="difficulteMin" key="diffMin" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{difficulteMin}" />
		<s:select id = "difficulteMax" name="difficulteMax" key="diffMax" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{difficulteMax}" />
		
		<s:select id = "accessibleEnfants" name="accessibleEnfants" key="accesEnfants" list="#{'true':'oui', 'false':'non'}" emptyOption="true" requiredLabel="false" value="%{accessibleEnfants}" />
		
		<s:textfield id = "hauteurMin" name="hauteurMin" key="hauteurMin" requiredLabel="false" type="number" value="%{hauteurMin}" />
		
		<s:textfield id = "hauteurMax" name="hauteurMax" key="hauteurMax" requiredLabel="true" type="number" value="%{hauteurMax}" />
		
		<s:checkboxlist key="orientations" list="listOrientations" name="orientations" value="orientations" />
		
		<s:textfield id = "nbSecteur" name="nbSecteur" key="nbSecteur" requiredLabel="false" type="number" value="%{nbSecteur}" />

		<s:textfield id = "nbVoie" name="nbVoie" key="nbVoie" requiredLabel="true" maxlength="50" value="%{nbVoie}" />
		
		<s:textfield id = "latitude" name="latitude" key="latitude" requiredLabel="false" maxlength="15" value="%{latitude}" />
		<s:textfield id = "longitude" name="longitude" key="longitude" requiredLabel="false" maxlength="15" value="%{longitude}" />
		
		<s:textfield id = "descriptionTitre" name="descriptionTitre" key="modifierSpot.presentation" requiredLabel="false" maxlength="100" value="%{descriptionTitre}" />
		
		<s:textarea id = "descriptionTexte" name="descriptionTexte" requiredLabel="false" value="%{descriptionTexte}" />
	
		<s:submit key="valider" name=""/>
	</s:form>
	
</body>
</html>