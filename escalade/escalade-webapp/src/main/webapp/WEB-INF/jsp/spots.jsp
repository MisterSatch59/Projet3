<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>

	<h1>
		<s:text name="spots.titre" />
	</h1>

	<s:a action="creerSpot">Créer un nouveau spot</s:a>

	<s:form action="rechercheSpot">
		<legend>
			<s:text name="spots.recherche.title" />
		</legend>
		<s:select name="departement"
			key="spots.recherche.departement" list="listDepartements"
			emptyOption="true" requiredLabel="false" />

		<s:textfield name="ville" key="spots.recherche.ville"
			requiredLabel="false" />

		<s:select name="difficulteMin" key="spots.recherche.diffMin"
			list="listDifficultes"
			emptyOption="true" requiredLabel="false" />

		<s:select name="difficulteMax" key="spots.recherche.diffMax"
			list="listDifficultes" emptyOption="true" requiredLabel="false" />

		<s:submit key="spots.rechercheButton" />

	</s:form>

	<ul id="rechercheListe">
		<s:iterator value="resultatRecherche">
			<li><s:property value="nom" /> - <s:property
					value="ville.departement.nom" /> - <s:property value="ville.nom" />
				<ul>
					<li><s:iterator value="types" var="type">
							<s:property value="type" /> - 
						</s:iterator></li>
					<li><s:iterator value="profils" var="profil">
							<s:property value="profil" /> - 
						</s:iterator></li>
					<li><s:iterator value="orientations" var="orientation">
							<s:property value="orientation" /> - 
						</s:iterator></li>
					<li>Difficulté : De <s:property value="difficulteMin" /> à <s:property
							value="difficulteMax" />
					</li>
					<li>Hauteur Min : <s:if test="hauteurMin==0">?</s:if> <s:else>
							<s:property value="hauteurMin" />
						</s:else> Hauteur Max : <s:property value="hauteurMax" />
					</li>
					<li><s:property value="AdapteEnfants" /> Accessible aux
						enfants : <s:if test="AdapteEnfants">oui</s:if> <s:elseif
							test="AdapteEnfants==false">non</s:elseif> <s:else>?</s:else></li>
					<li><s:if test="nbSecteur>0">
							Nombre de secteur : <s:property value="nbSecteur" /> - 
						</s:if> Nombre de voies : <s:property value="nbVoie" /></li>
					<li><s:a action="spotInfo">
							<s:param name="spot" value="id" />Page descriptive du Topo</s:a></li>
				</ul></li>
		</s:iterator>
	</ul>
</body>

<footer>
	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</footer>
</html>