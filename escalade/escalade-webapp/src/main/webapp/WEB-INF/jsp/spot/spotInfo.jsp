<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<!-- En tête de la page (titre et bouton si connecté -->
	<div class="row aligneCentre">
		<div  class="col-sm-6">
			<h1><s:property value="spot.nom" /> - <s:property value="spot.ville.departement.nom" /> - <s:property value="spot.ville.nom" /></h1>
		</div>
		
		<div class="col-sm-6">
			<div class="row">
				<s:if test="#session.utilisateur">
					<s:if test="#session.utilisateur.pseudo==spot.auteur.pseudo||#session.utilisateur.admin==true">
					<div class="col-sm-6 ">
						<s:a action="supprimerSpot" class="btn btn-default btn-custom-rouge"><s:param name="spotId" value="spot.id" /><s:text name="supprimer"/></s:a>
					</div>
					</s:if>
					<div class="col-sm-6">
						<s:a action="versModifierSpot" class="btn btn-default btn-custom"><s:param name="spotId" value="spot.id" /><s:text name="modifier"/></s:a>
					</div>
				</s:if>
			</div>
		</div>
	</div>
	
	<!-- Présentation du spot -->
	<div class="jumbotron marge container">
		<div class="row alignCentre">
			<div class="col-sm-8">
				<h2><s:text name="presentation"/></h2>
				<h4><s:text name="spotInfo.ceSpotEst"/> : 
				<s:if test="spot.ouvert"><strong><s:text name="ouvert" /></strong></s:if>
				<s:else><strong><s:text name="ferme" /></strong></s:else></h4>
			</div>
			<div class="col-sm-4">
				<s:text name="auteur" /> : <img class="avatar" src="img/avatar/<s:property value="spot.auteur.avatar" />" alt= "avatar" /> - <strong><s:property value="spot.auteur.pseudo" /></strong>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4">
				<ul class="list-unstyled">
					<li>
						<s:text name="types"/> : 
						<s:iterator value="spot.types" var="type"  status="incr">
							<s:property value="type" /> 
							<s:if test="%{#incr.index + 1!=spot.types.size()}"> - </s:if>
						</s:iterator>
					</li>
					
					<li>
						<s:text name="profils"/> : 
						<s:iterator value="spot.profils" var="profil" status="incr">
							<s:property value="profil" />
							<s:if test="%{#incr.index + 1!=spot.profils.size()}"> - </s:if>
						</s:iterator>
					</li>
					<li>
						<s:text name="orientations"/> : 
						<s:iterator value="spot.orientations" var="orientation" status="incr">
							<s:property value="orientation" />
							<s:if test="%{#incr.index + 1!=spot.orientations.size()}"> - </s:if>
						</s:iterator>
					</li>
					<li>
						<s:text name="spots.resultat.difficulte.de" /><s:property value="spot.difficulteMin" /> <s:text name="spots.resultat.a" /> <s:property value="spot.difficulteMax" />
					</li>
					<li>
						<s:if test="spot.hauteurMin!=0">
							<s:text name="hauteurMin" /> : <s:property value="spot.hauteurMin" /> - 
						</s:if>
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
							<h3><s:property value="spot.presentation.titre" /></h3>
								<s:iterator value="spot.presentation.listParagraphes" var="parag">
									<h4><s:property value="parag" /></h4>
								</s:iterator>
						</li>
					</s:if>
				</ul>
			</div>
			
			<s:if test="%{spot.listPhotos.size()!=0}">
				<div class="col-sm-8">
					<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
						<!-- Indicators -->
						<ol class="carousel-indicators">
							<s:iterator value="spot.listPhotos" status="stat">
								<s:if test="#stat.index==0">
									<li data-target="#carousel-example-generic" data-slide-to="#stat.index" class="active"></li>
								</s:if>
								<s:else>
									<li data-target="#carousel-example-generic" data-slide-to="#stat.index"></li>
								</s:else>
							</s:iterator>
						</ol>
		
						<!-- Wrapper for slides -->
						<div  class="carousel-inner" role="listbox">
							<s:iterator value="spot.listPhotos" var="photo" status="stat">
								<s:if test="#stat.index==0">
									<div class="item active ">
										<img src="img/spot/<s:property value="photo" />" alt="photo" class="imageCarousel"/>
									</div>
								</s:if>
								<s:else>
									<div class="item">
										<img src="img/spot/<s:property value="photo" />" alt="photo" class="imageCarousel"/>
									</div>
								</s:else>
							</s:iterator>
						</div>
						<!-- Controls -->
						<a class="left carousel-control" href="#carousel-example-generic"
							role="button" data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control" href="#carousel-example-generic"
							role="button" data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>
				</div>
			</s:if>
		</div>
		
		<div class="row">
			<h3><s:text name="spotsInfo.listTopos" /> : </h3>
			<ul>
				<s:iterator value="listTopo" var="topo">
					<li><s:property value="topo" /> - <s:a action="infoTopo"><s:param name="titreTopo" value="topo" /><s:text name="spotInfo.voirTopo" /></s:a></li>
				</s:iterator>
			</ul>
		</div>
	</div>
	
	
	<!-- Alertes -->
	<div class="jumbotron marge container">
		<h2><s:text name="alertes" /> : </h2>
		<ul id="affAlertes">
			<s:iterator value="spot.listCommentaires">
				<s:if test="alerte==true">
					<li>
						<h4>
							<img class="avatar" src="img/avatar/<s:property value="auteur.avatar" />" alt= "avatar" /> - <strong><s:property value="auteur.pseudo" /></strong> - 
							<s:property value="date" /> - <s:property value="titre" />
						</h4>
						<div class="cadrePerso">
							<s:iterator value="listParagraphes" var="paragraphe">
								<s:property value="paragraphe" /><br/>
							</s:iterator>
						</div>
						<s:if test="#session.utilisateur.pseudo==auteur.pseudo||#session.utilisateur.admin==true">
							<div class="col-sm-offset-8 col-sm-4 marge">
								<button onclick="supprimerCommentaire(this)" id="${id}" class="btn btn-default btn-custom">
										<s:text name="spotInfo.supprCommentaire" />
								</button>
							</div>
						</s:if>
					</li>
				</s:if>
			</s:iterator>
		</ul>
	</div>
	
	<!-- Commentaires -->
	<div class="jumbotron marge container">
		<h2><s:text name="commentaires" /> : </h2>
		<ul id="affCommentaires">
			<s:iterator value="spot.listCommentaires">
				<s:if test="alerte==false">
					<li>
						<h4>
							<img class="avatar" src="img/avatar/<s:property value="auteur.avatar" />" alt= "avatar" /> - <strong><s:property value="auteur.pseudo" /></strong> - 
							<s:property value="date" /> - <s:property value="titre" />
						</h4>
						<div class="cadrePerso">
							<s:iterator value="listParagraphes" var="paragraphe">
								<s:property value="paragraphe" /><br/>
							</s:iterator>
						</div>
						<s:if test="#session.utilisateur.pseudo==auteur.pseudo||#session.utilisateur.admin==true">
							<div class="col-sm-offset-8 col-sm-4 marge">
								<button onclick="supprimerCommentaire(this)" id="${id}" class="btn btn-default btn-custom">
										<s:text name="spotInfo.supprCommentaire" />
								</button>
							</div>
						</s:if>
					</li>
				</s:if>
			</s:iterator>
		</ul>
	</div>
	
	<!-- Nouveau commentaire (ou alerte) -->
	<s:if test="#session.utilisateur">
		<div class="jumbotron marge container">
			<s:form class="formClassiq">
				<legend>
					<s:text name="spotInfo.titreNouveauCommentaire" />
				</legend>
				<s:textfield id = "titre" name="titre" key="spotInfo.titre.commentaire" requiredLabel="true" class="form-control" />		
				<s:textarea id = "texte" name="texte" key="spotInfo.message.commentaire" requiredLabel="true" class="form-control" cols="150" rows="6" />
				
				<s:checkbox id = "alerte" key="spotInfo.message.alerte" name="alerte" labelposition="left"/>
				
			</s:form>
			<div class="col-sm-offset-8 col-sm-4">
				<button onclick="envoyerCommentaire()" class="btn btn-default btn-custom">
						<s:text name="valider" />
				</button>
			</div>
		</div>
	</s:if>
	

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
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
	            	
	            	var d = new Date(val.date);
	            	var curr_date = d.getDate();
	            	if (curr_date < 10) {
	            		var currDate = '0' + curr_date;
	            	}else{
	            		var currDate = curr_date;
	            	}
	            	var curr_month = d.getMonth()+1;
	            	if (curr_month < 10) {
	            		var currMonth = '0' + curr_month;
	            	}else{
	            		var currMonth = curr_month;
	            	}
	            	curr_month++;
	            	var curr_year = d.getFullYear();
	            	
	            	var infoComm ='<h4><img class="avatar" src="img/avatar/' + val.auteur.avatar + '" alt= "avatar" /> - <strong>'+ val.auteur.pseudo +'</strong> - ' + currDate + '/' + currMonth + '/' + curr_year + ' - ' + val.titre +'</h4>';
	            	infoComm +='<div class="cadrePerso">';
	            	$.each(val.listParagraphes, function( index, value ) {
	            		infoComm+= value + "<br/>";
	            	});
	            	infoComm +='</div>';
	            	
	            	var pseudo="<s:property value="#session.utilisateur.pseudo" />";
	            	var admin="<s:property value="#session.utilisateur.admin" />"
	            	if(val.auteur.pseudo==pseudo || admin=="true" ){
		            	infoComm +='<div class="col-sm-offset-8 col-sm-4 marge">';
	            		infoComm+= '<button onclick="supprimerCommentaire(this)" id="'+val.id+'" class="btn btn-default btn-custom">' + '<s:text name="spotInfo.supprCommentaire" />';
	            		infoComm +='</div>';
	            	}

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