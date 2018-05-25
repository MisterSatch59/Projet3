<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<h1><s:text name="creerSpot.title" /></h1>
	
	<div class="jumbotron marge container">
		<s:form action="creerSpot"  class="formClassiq" method="POST" enctype="multipart/form-data">
			
			<s:textfield id = "nom" name="nom" key="nom" requiredLabel="true" maxlength="40" value="%{nom}" class="form-control"/>
			<s:select id = "departement" name="departement" key="departement" list="listDepartements" emptyOption="true" requiredLabel="true" value="%{departement}" class="form-control miniMarge"/>
			
			<s:textfield id = "villeNom" name="villeNom" key="modifierSpot.ville.nom" requiredLabel="true" maxlength="100" value="%{villeNom}" class="form-control miniMarge"/>
			<s:textfield id = "villeCP" name="villeCP" key="cp" requiredLabel="true" maxlength="5" value="%{villeCP}" class="form-control miniMarge"/>
			
			<s:checkbox key="ouvert" name="ouvert" value="ouvert" labelposition="left" class="miniMarge"/>
			
			<s:checkboxlist key="types" list="listTypes" name="types" requiredLabel="true" value="types" class="miniMarge"/>
			
			<s:checkboxlist key="profils" list="listProfils" name="profils" requiredLabel="true" value="profils" class="miniMarge"/>
			
			<s:select id = "difficulteMin" name="difficulteMin" key="diffMin" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{difficulteMin}" class="form-control miniMarge"/>
			<s:select id = "difficulteMax" name="difficulteMax" key="diffMax" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{difficulteMax}" class="form-control miniMarge" />
			
			<s:select id = "accessibleEnfants" name="accessibleEnfants" key="accesEnfants" list="#{'true':'oui', 'false':'non'}" emptyOption="true" value="%{accessibleEnfants}" class="form-control miniMarge"/>
			
			<s:textfield id = "hauteurMin" name="hauteurMin" key="hauteurMin" type="number" value="%{hauteurMin}" class="form-control miniMarge"/>
			
			<s:textfield id = "hauteurMax" name="hauteurMax" key="hauteurMax" requiredLabel="true" type="number" value="%{hauteurMax}" class="form-control miniMarge"/>
			
			<s:checkboxlist key="orientations" list="listOrientations" name="orientations" requiredLabel="true" value="orientations" class="miniMarge"/>
			
			<s:textfield id = "nbSecteur" name="nbSecteur" key="nbSecteur" type="number" value="%{nbSecteur}" class="form-control miniMarge"/>
		
			<s:textfield id = "nbVoie" name="nbVoie" key="nbVoie" requiredLabel="true" maxlength="50" value="%{nbVoie}" class="form-control miniMarge"/>
			
			<s:textfield id = "latitude" name="latitude" key="latitude" maxlength="15" value="%{latitude}" class="form-control miniMarge"/>
			<s:textfield id = "longitude" name="longitude" key="longitude" maxlength="15" value="%{longitude}" class="form-control miniMarge"/>
				
			<s:textfield id = "descriptionTitre" name="descriptionTitre" key="modifierSpot.presentation" maxlength="100" value="%{descriptionTitre}" class="form-control marge"/>
				
			<s:textarea id = "descriptionTexte" name="descriptionTexte" value="%{descriptionTexte}" class="form-control miniMarge" cols="150" rows="6"/>
				
			<s:file name="myFile" key="photosPresentation" accept="image/*" />
				
			<s:submit class="col-sm-offset-4 col-sm-4 col-xs-12 marge btn btn-default btn-custom" key="valider" name=""/>
		</s:form>
	</div>
	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>