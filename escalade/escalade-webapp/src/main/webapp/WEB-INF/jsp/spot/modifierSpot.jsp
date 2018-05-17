<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<h1><s:text name="modifierSpot.title" /></h1>
	<div class="jumbotron marge container">
		<s:form action="modifierSpot">
			<div class="row">
		
				<s:textfield id = "nom" name="nom" key="nom" requiredLabel="true" maxlength="40" value="%{spot.nom}" class="form-control" />
				<s:select id = "departement" name="departement" key="departement" list="listDepartements" emptyOption="true" requiredLabel="true" value="%{spot.ville.departement}" class="form-control miniMarge" />
				
				<s:textfield id = "villeNom" name="villeNom" key="modifierSpot.ville.nom" requiredLabel="true" maxlength="100" value="%{spot.ville.nom}" class="form-control miniMarge" />
				<s:textfield id = "villeCP" name="villeCP" key="cp" requiredLabel="true" maxlength="5" value="%{spot.ville.cp}" class="form-control miniMarge" />
				
				<s:checkbox key="ouvert" name="ouvert" value="spot.ouvert" labelposition="left" class="miniMarge"/>
				
				<s:checkboxlist key="types" list="listTypes" name="types" value="spot.types" class="miniMarge"/>
				
				<s:checkboxlist key="profils" list="listProfils" name="profils" value="spot.profils" class="miniMarge"/>
				
				<s:select id = "difficulteMin" name="difficulteMin" key="diffMin" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{spot.difficulteMin}" class="form-control miniMarge" />
				<s:select id = "difficulteMax" name="difficulteMax" key="diffMax" list="listDifficultes" emptyOption="true" requiredLabel="true" value="%{spot.difficulteMax}" class="form-control miniMarge" />
				
				<s:select id = "accessibleEnfants" name="accessibleEnfants" key="accesEnfants" list="#{'true':'oui', 'false':'non'}" emptyOption="true" requiredLabel="false" value="%{spot.adapteEnfants}" class="form-control miniMarge" />
				
				<s:if test="%{spot.hauteurMin==0}">
					<s:textfield id = "hauteurMin" name="hauteurMin" key="hauteurMin" requiredLabel="false" type="number" class="form-control miniMarge" />
				</s:if>
				<s:else>
					<s:textfield id = "hauteurMin" name="hauteurMin" key="hauteurMin" requiredLabel="false" type="number" value="%{spot.hauteurMin}" class="form-control miniMarge" />
				</s:else>
				
				<s:textfield id = "hauteurMax" name="hauteurMax" key="hauteurMax" requiredLabel="false" type="number" value="%{spot.hauteurMax}" class="form-control miniMarge" />
				
				<s:checkboxlist key="orientations" list="listOrientations" name="orientations" value="spot.orientations" class="miniMarge" />
				
				<s:if test="%{spot.nbSecteur==0}">
					<s:textfield id = "nbSecteur" name="nbSecteur" key="nbSecteur" requiredLabel="false" type="number" class="form-control miniMarge" />
				</s:if>
				<s:else>
					<s:textfield id = "nbSecteur" name="nbSecteur" key="nbSecteur" requiredLabel="false" type="number" value="%{spot.nbSecteur}" class="form-control miniMarge" />
				</s:else>
				
				<s:textfield id = "nbVoie" name="nbVoie" key="nbVoie" requiredLabel="true" maxlength="50" value="%{spot.nbVoie}" class="form-control miniMarge" />
				
				<s:textfield id = "latitude" name="latitude" key="latitude" requiredLabel="false" maxlength="15" value="%{spot.latitude}" class="form-control miniMarge" />
				<s:textfield id = "longitude" name="longitude" key="longitude" requiredLabel="false" maxlength="15" value="%{spot.longitude}" class="form-control miniMarge" />
				
				<s:textfield id = "descriptionTitre" name="descriptionTitre" key="modifierSpot.presentation" requiredLabel="false" maxlength="100" value="%{spot.presentation.titre}" class="form-control marge" />
				
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
				<s:textarea id = "descriptionTexte" name="descriptionTexte" requiredLabel="false" value="%{texte}" class="form-control miniMarge" cols="150" rows="6" />
			
				<s:hidden name="spotId" value="%{spot.id}" />
				
				<s:submit class="col-sm-offset-4 col-sm-4 col-xs-12 marge btn btn-default btn-custom" key="valider" name=""/>
	
			</div>
		</s:form>
	</div>
	
	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>