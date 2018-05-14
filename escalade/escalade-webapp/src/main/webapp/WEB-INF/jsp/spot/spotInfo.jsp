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

	<s:if test="#session.utilisateur">
		<p>
			<s:a action="versModifierSpot"><s:param name="spotId" value="spot.id" /><s:text name="spotInfo.modifier" /></s:a>
			<s:if test="#session.utilisateur.pseudo==spot.auteur.pseudo||#session.utilisateur.admin==true">
					 - 
					<s:a action="supprimerSpot"><s:param name="spotId" value="spot.id" /><s:text name="spotInfo.supprimer" /></s:a>
					<br/><br/>
			</s:if>
		</p>
	</s:if>
	
	<ul>
		<li>
			<s:text name="ouvert" /> ? : <s:property value="spot.ouvert" />
		</li>
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
			<s:text name="spots.resultat.difficulte.de" /><s:property value="spot.difficulteMin" /> <s:text name="spots.resultat.a" /> <s:property value="spot.difficulteMax" />
		</li>
		<li>
			<s:text name="hauteurMin" /> : 
			<s:if test="spot.hauteurMin==0">?</s:if>
			<s:else><s:property value="spot.hauteurMin" /></s:else> - 
			<s:text name="hauteurMax" /> : <s:property value="spot.hauteurMax" />
		</li>
		<li>
			<s:text name="accesEnfants" /> : 
			<s:if test="spot.adapteEnfants"><s:text name="oui" /></s:if>
			<s:elseif test="spot.adapteEnfants==false"><s:text name="non" /></s:elseif>
			<s:else>?</s:else>
		</li>
		<li>
			<s:if test="spot.nbSecteur!=0"><s:text name="nbSecteurs" /> : <s:property value="spot.nbSecteur" /> - </s:if>
			<s:text name="nbVoies" /> : <s:property value="spot.nbVoie" />
		</li>
		
		<s:if test="spot.presentation.titre!=''">
			<li>
				<s:property value="spot.presentation.titre" />
				<ul>
					<s:iterator value="spot.presentation.listParagraphes" var="parag">
						<li><s:property value="parag" /></li>
					</s:iterator>
				</ul>
			</li>
		</s:if>
		
	</ul>
	<h3><s:text name="spotsInfo.listTopos" /> : </h3>
	<ul>
		<s:iterator value="listTopo" var="nom">
			<s:property value="nom" /> - <s:a action="topoInfo"><s:param name="nomTopo" value="nom" /><s:text name="spotInfo.voirTopo" /></s:a><br/>
		</s:iterator>
	</ul>
	
	<h3><s:text name="alertes" /> : </h3>
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
					<s:if test="#session.utilisateur.pseudo==auteur.pseudo||#session.utilisateur.admin==true">
						<button onclick="supprimerCommentaire(this)" id="${id}">
								<s:text name="spotInfo.supprCommentaire" />
						</button>
					</s:if>
				</li>
			</s:if>
		</s:iterator>
	</ul>
	
	<h3><s:text name="commentaires" /> : </h3>
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
					<s:if test="#session.utilisateur.pseudo==auteur.pseudo||#session.utilisateur.admin==true">
						<button onclick="supprimerCommentaire(this)" id="${id}">
								<s:text name="spotInfo.supprCommentaire" />
						</button>
					</s:if>
				</li>
			</s:if>
		</s:iterator>
	</ul>
	<s:if test="#session.utilisateur">
		<s:form>
			<legend >
				<s:text name="spotInfo.titreNouveauCommentaire" />
			</legend>
			<s:textfield id = "titre" name="titre" key="spotInfo.message.commentaire" requiredLabel="true" />		
			<s:textarea id = "texte" name="texte" requiredLabel="true" />
			
			<s:checkbox id = "alerte" key="spotInfo.message.alerte" name="alerte" />
			
		</s:form>
		<button onclick="envoyerCommentaire()">
				<s:text name="valider" />
		</button>
	</s:if>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script>
		function envoyerCommentaire() {
			var titre = $("#titre").val()
			var texteCommentaire = $("#texte").val()
			var alerte =  $('#alerte').is(':checked')
			

			if(titre != "" && texteCommentaire != ""){
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
						AfficherComm(data);
					})
				.fail(function (data) {
					alert("Une erreur s'est produite.");
				});
			}
		}
		
		function supprimerCommentaire(that) {
			// URL de l'action AJAX
			var url = "<s:url action="ajax_supprimerCommentaire"/>";
			
			var id = that.id;
			
			// Paramètres de la requête AJAX
			var params = {
				commentaireId : id,
				spotId : "${spot.id}"
			};

			// Action AJAX en POST
		    jQuery.post(
		        url,
		        params,
		        function (data) {
		        	AfficherComm(data);
		        })
		        .fail(function (data) {
		            alert("Une erreur s'est produite.");
		        });
			
		}
		
		function AfficherComm(data) {
			 var $affCommentaires = $("#affCommentaires");
	            $affCommentaires.empty();
	            var $affAlertes = $("#affAlertes");
	            $affAlertes.empty();
	            $("#titre").val('');
	            $("#texte").val('');
	            $("#alerte").prop("checked", false);
	            jQuery.each(data, function (key, val) {
	            	var infoComm = val.auteur.pseudo + "<br/>" + val.date + "<br/>" + val.titre + "<br/>";
	            	$.each(val.listParagraphes, function( index, value ) {
	            		  infoComm+=value + "<br/>";
	            	});
            		infoComm+= '<button onclick="supprimerCommentaire(this)" id="'+val.id+'">' + '<s:text name="spotInfo.supprCommentaire" />';
	            	
	            	if (val.alerte == true) {
	            		$affAlertes.append($('<li>').append(infoComm));
	            	} else { 
	            		$affCommentaires.append($('<li>').append(infoComm));
	            	}
	            });
		 }
		
	</script>

</body>
</html>