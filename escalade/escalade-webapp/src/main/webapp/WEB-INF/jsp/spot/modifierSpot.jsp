<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<s:actionerror/>
	
	<s:form action="modifierSpot">
		<legend>
			<s:text name="modifierSpot.title" />
		</legend>
		<s:textfield id = "nom" name="nom" key="nom" requiredLabel="true" maxlength="40" value="%{spot.nom}" />
		<s:select id = "departement" name="departement" key="departement" list="listDepartements" emptyOption="true" requiredLabel="true" value="%{spot.ville.departement}" />
		
		<s:textfield id = "villeNom" name="villeNom" key="modifierSpot.ville.nom" requiredLabel="true" maxlength="100" value="%{spot.ville.nom}" />
		<s:textfield id = "villeCP" name="villeCP" key="cp" requiredLabel="true" maxlength="5" value="%{spot.ville.cp}" />
		
		<s:checkbox key="ouvert" name="ouvert" value="spot.ouvert"/>
		
		<s:checkboxlist key="types" list="listTypes" name="types" value="spot.types" />
		
		<s:checkboxlist key="profils" list="listProfils" name="profils" value="spot.profils" />
		
		<s:select id = "difficulteMin" name="difficulteMin" key="diffMin" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{spot.difficulteMin}" />
		<s:select id = "difficulteMax" name="difficulteMax" key="diffMax" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{spot.difficulteMax}" />
		
		<s:select id = "accessibleEnfants" name="accessibleEnfants" key="accesEnfants" list="#{'true':'oui', 'false':'non'}" emptyOption="true" requiredLabel="false" value="%{spot.adapteEnfants}" />
		
		<s:if test="%{spot.hauteurMin==0}">
			<s:textfield id = "hauteurMin" name="hauteurMin" key="hauteurMin" requiredLabel="false" type="number" />
		</s:if>
		<s:else>
			<s:textfield id = "hauteurMin" name="hauteurMin" key="hauteurMin" requiredLabel="false" type="number" value="%{spot.hauteurMin}" />
		</s:else>
		
		<s:textfield id = "hauteurMax" name="hauteurMax" key="hauteurMax" requiredLabel="false" type="number" value="%{spot.hauteurMax}" />
		
		<s:checkboxlist key="orientations" list="listOrientations" name="orientations" value="spot.orientations" />
		
		
		<s:if test="%{spot.nbSecteur==0}">
			<s:textfield id = "nbSecteur" name="nbSecteur" key="nbSecteur" requiredLabel="false" type="number" />
		</s:if>
		<s:else>
			<s:textfield id = "nbSecteur" name="nbSecteur" key="nbSecteur" requiredLabel="false" type="number" value="%{spot.nbSecteur}" />
		</s:else>
		
		<s:textfield id = "nbVoie" name="nbVoie" key="nbVoie" requiredLabel="true" maxlength="50" value="%{spot.nbVoie}" />
		
		<s:textfield id = "latitude" name="latitude" key="latitude" requiredLabel="false" maxlength="15" value="%{spot.latitude}" />
		<s:textfield id = "longitude" name="longitude" key="longitude" requiredLabel="false" maxlength="15" value="%{spot.longitude}" />
		
		<s:textfield id = "descriptionTitre" name="descriptionTitre" key="modifierSpot.presentation" requiredLabel="false" maxlength="100" value="%{spot.presentation.titre}" />
		
		<s:set var="texte" value=""/>
		<s:iterator value="%{spot.presentation.listParagraphes}" var="parag">
			<s:if test="%{#texte==null}">
				<s:set var="texte" value="%{parag}"/>
			</s:if>
			<s:else>
				<s:set var="texte" value="%{#texte + #parag}"/>
			</s:else>
			<s:set var="texte" value="%{#texte + '\n'}"/>
		</s:iterator>
		<s:textarea id = "descriptionTexte" name="descriptionTexte" requiredLabel="false" value="%{texte}" />
	
		<s:hidden name="spotId" value="%{spot.id}" />
		<s:submit key="valider" name=""/>
	</s:form>
	
</body>
</html>