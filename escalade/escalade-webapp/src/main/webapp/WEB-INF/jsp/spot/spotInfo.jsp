<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
<style type="text/css">
.avatar {
	max-width : 30px;
	max-height : 30px;
}
</style>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<h1><s:property value="spot.nom" /> - <s:property value="spot.ville.departement.nom" /> - <s:property value="spot.ville.nom" /></h1>

	<p>
		<s:a action="versModifierSpot"><s:param name="spotId" value="spot.id" /><s:text name="spotInfo.modifier" /></s:a>
		 - 
		<s:a action="supprimerSpot"><s:param name="spotId" value="spot.id" /><s:text name="spotInfo.supprimer" /></s:a>
		<br/><br/>
	</p>
	
	<ul>
		<li>
			<img class="avatar" src="img/<s:property value="spot.auteur.avatar" />" alt= "avatar" /> -  <s:property value="spot.auteur.pseudo" />
		</li>
		<li>
			<s:iterator value="spot.types" var="type">
				<s:property value="type" /> - 
			</s:iterator>
		</li>
		
		<li>
			<s:iterator value="spot.profils" var="profil">
				<s:property value="profil" /> - 
			</s:iterator>
		</li>
		<li>
			<s:iterator value="spot.orientations" var="orientation">
				<s:property value="orientation" /> - 
			</s:iterator>
		</li>
		<li>
			<s:text name="spots.resultat.diff" /><s:property value="spot.difficulteMin" /> <s:text name="spots.resultat.a" /> <s:property value="spot.difficulteMax" />
		</li>
		<li>
			<s:text name="spots.resultat.hauteur.min" /> : 
			<s:if test="spot.hauteurMin==0">?</s:if>
			<s:else><s:property value="spot.hauteurMin" /></s:else> - 
			<s:text name="spots.resultat.hauteur.max" /> : <s:property value="spot.hauteurMax" />
		</li>
		<li>
			<s:text name="spots.resultat.enfants" />
			<s:if test="spot.adapteEnfants"><s:text name="oui" /></s:if>
			<s:elseif test="spot.adapteEnfants==false"><s:text name="non" /></s:elseif>
			<s:else>?</s:else>
		</li>
		<li>
			<s:if test="spot.nbSecteur!=0"><s:text name="spots.resultat.secteurs" /> : <s:property value="spot.nbSecteur" /> - </s:if>
			<s:text name="spots.resultat.voies" /> : <s:property value="spot.nbVoie" />
		</li>
		
		<li>
			<s:property value="%{spot.presentation.titre}" />
			<ul>
				<s:iterator value="%{spot.presentation.listParagraphes}" var="parag">
					<li><s:property value="%{parag}" /></li>
				</s:iterator>
			</ul>
		</li>
		
	</ul>
	<h3>Topos contenants ce spot: </h3>
	<ul>
		<s:iterator value="listTopo" var="nom">
			<s:property value="nom" /> - <s:a action="topoInfo"><s:param name="nomTopo" value="nom" /><s:text name="spotInfo.voirTopo" /></s:a><br/>
		</s:iterator>
	</ul>
	
	<h3>Alertes : </h3>
	<ul id="affAlertes">
		<s:iterator value="spot.listCommentaires">

			<s:if test="alerte==true">
				<li>
					<s:property value="auteur.pseudo" /><br/>
					<s:property value="date" /><br/>
					<s:property value="titre" /><br/>
					<s:iterator value="listParagraphes" var="paragraphe">
						<s:property value="paragraphe" /><br/>
					</s:iterator>
					<s:a action="supprimerCommentaire"><s:param name="commentaireId" value="id" /><s:text name="spotInfo.supprAlerte" /></s:a>
				</li>
			</s:if>
		</s:iterator>
	</ul>
	
	<h3>Commentaires : </h3>
	<ul id="affCommentaires">
		<s:iterator value="spot.listCommentaires">
			<s:if test="alerte==false">
				<li>
					<s:property value="auteur.pseudo" /><br/>
					<s:property value="date" /><br/>
					<s:property value="titre" /><br/>
					<s:iterator value="listParagraphes" var="paragraphe">
						<s:property value="paragraphe" /><br/>
					</s:iterator>
					<s:a action="supprimerCommentaire"><s:param name="commentaireId" value="id" /><s:param name="spotId" value="spot.id" /><s:text name="spotInfo.supprCommentaire" /></s:a>
				</li>
			</s:if>
		</s:iterator>
	</ul>
	
	<s:form action="nouveauCommentaire" id="formulaire">
		<legend>
			<s:text name="spotInfo.titreNouveauCommentaire" />
		</legend>
		<s:textfield id = "titre" name="titre" key="spotInfo.message.commentaire" requiredLabel="true" />		
		<s:textarea id = "texte" name="texte" requiredLabel="true" />
		
		<s:checkbox id = "alerte" key="spotInfo.message.alerte" name="alerte" />
		
	</s:form>
	<button onclick="envoyerCommentaire()">
			<s:text name="valider" />
	</button>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script>
		function envoyerCommentaire() {
			var titre = $("#titre").val()
			var texteCommentaire = $("#texte").val()
			var alerte =  $('#alerte').is(':checked')

			// URL de l'action AJAX
			var url = "<s:url action="ajax_nouveauCommentaire"/>";

			// Paramètres de la requête AJAX
			var params = {
				titre : titre,
				texteCommentaire : texteCommentaire,
				alerte : alerte,
				spotId : "${spot.id}"
			};

			// Action AJAX en POST
		    jQuery.post(
		        url,
		        params,
		        function (data) {
		            var $affCommentaires = $("#affCommentaires");
		            $affCommentaires.empty();
		            var $affAlertes = $("#affAlertes");
		            $affAlertes.empty();
		            $("#titre").val('');
		            $("#texte").val('');
		            $("#alerte").prop("checked", false);
		            
		            //A FAIRE : peupler les listes commentaires et alertes
		           
		            
		        })
		        .fail(function (data) {
		            alert("Une erreur s'est produite.");
		        });
			
		}
		
	</script>

</body>
</html>