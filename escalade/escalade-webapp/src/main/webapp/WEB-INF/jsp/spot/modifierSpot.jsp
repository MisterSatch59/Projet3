<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<s:form action="modifierSpot">
		<legend>
			<s:text name="modifierSpot.title" />
		</legend>
		<s:textfield id = "nom" name="nom" key="modifierSpot.nom" requiredLabel="true" value="%{spot.nom}" />
		<s:select id = "departement" name="departement" key="modifierSpot.departement" list="listDepartements" emptyOption="true" requiredLabel="true" value="%{spot.ville.departement}" />
		
		<s:textfield id = "villeNom" name="villeNom" key="modifierSpot.ville.nom" requiredLabel="true" value="%{spot.ville.nom}" />
		<s:textfield id = "villeCP" name="villeCP" key="modifierSpot.ville.cp" requiredLabel="true" value="%{spot.ville.cp}" />
		
		<s:iterator value="%{listTypes}" var="type">
			<s:set var="isInSpot" value="%{false}"/>
			<s:iterator value="%{spot.types}" var="spotType">
				<s:if test="%{#spotType==#type}">
					<s:set var="isInSpot" value="%{true}"/>
				</s:if>
			</s:iterator>
			<s:checkbox label="%{type}" name="%{type}" value="%{isInSpot}"/>
		</s:iterator>
		
		<s:iterator value="%{listProfils}" var="profil">
			<s:set var="isInSpot" value="%{false}"/>
			<s:iterator value="%{spot.profils}" var="spotProfil">
				<s:if test="%{#spotProfil==#profil}">
					<s:set var="isInSpot" value="%{true}"/>
				</s:if>
			</s:iterator>
			<s:checkbox label="%{profil}" name="%{profil}" value="%{isInSpot}"/>
		</s:iterator>
		
		<s:select id = "difficulteMin" name="difficulteMin" key="modifierSpot.diffMin" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{spot.difficulteMin}" />
		<s:select id = "difficulteMax" name="difficulteMax" key="modifierSpot.diffMax" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{spot.difficulteMax}" />
		
		<s:select id = "accessibleEnfants" name="accessibleEnfants" key="modifierSpot.accessibleEnfants" list="#{'true':'oui', 'false':'non'}" emptyOption="true" requiredLabel="false" value="%{spot.adapteEnfants}" />
		
		<s:textfield id = "hauteurMin" name="hauteurMin" key="modifierSpot.hauteurMin" requiredLabel="false" value="%{spot.hauteurMin}" />
		<s:textfield id = "hauteurMax" name="hauteurMax" key="modifierSpot.hauteurMax" requiredLabel="false" value="%{spot.hauteurMax}" />
		
		<s:iterator value="%{listOrientations}" var="orientation">
			<s:set var="isInSpot" value="%{false}"/>
			<s:iterator value="%{spot.orientations}" var="spotOrientation">
				<s:if test="%{#spotOrientation==#orientation}">
					<s:set var="isInSpot" value="%{true}"/>
				</s:if>
			</s:iterator>
			<s:checkbox label="%{orientation}" name="%{orientation}" value="%{isInSpot}"/>
		</s:iterator>
		
		<s:textfield id = "nbSecteur" name="nbSecteur" key="modifierSpot.nbSecteur" requiredLabel="false" value="%{spot.nbSecteur}" />
		<s:textfield id = "nbVoie" name="nbVoie" key="modifierSpot.nbVoie" requiredLabel="true" value="%{spot.nbVoie}" />
		
		<s:textfield id = "latitude" name="latitude" key="modifierSpot.position" requiredLabel="false" value="%{spot.latitude}" />
		<s:textfield id = "longitude" name="longitude" requiredLabel="false" value="%{spot.longitude}" />
		
		<s:textfield id = "decriptionTitre" name="decriptionTitre" key="modifierSpot.presentation" requiredLabel="false" value="%{spot.presentation.titre}" />
		
		<s:set var="texte" value=""/>
		<s:iterator value="%{spot.presentation.listParagraphes}" var="parag">
			<s:if test="%{#texte==null}">
				<s:set var="texte" value="%{parag}"/>
			</s:if>
			<s:else>
				<s:set var="texte" value="%{#texte + #parag}"/>
			</s:else>
			<s:set var="texte" value="%{#texte + '\r\n'}"/>
		</s:iterator>
		<s:textarea id = "decriptionTexte" name="decriptionTexte" requiredLabel="false" value="%{texte}" />
		
		<s:submit key="valider" name="" />
	</s:form>
	

	
</body>
</html>