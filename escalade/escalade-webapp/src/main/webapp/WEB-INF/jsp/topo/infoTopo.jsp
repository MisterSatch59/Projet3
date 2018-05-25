<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<!-- Titre  et boutons modif et suppr-->
	<div class="row aligneCentre">
		<div  class="col-sm-6">
			<h1><s:property value="topo.titre" /></h1>
		</div>
		<div class="col-sm-6">
			<div class="row">
				<s:if test="#session.utilisateur">
					<div class="col-sm-6 ">
						<s:if test="#session.utilisateur.admin==true">
							<s:a action="supprimerTopo" class="btn btn-default btn-custom-rouge"><s:param name="titreTopo" value="topo.titre" /><s:text name="supprimer" /></s:a>
						</s:if>
					</div>
					<div class="col-sm-6">
						<s:a action="modifierTopo" class="btn btn-default btn-custom"><s:param name="titreTopo" value="topo.titre" /><s:text name="modifier" /></s:a>
					</div>
				</s:if>
			</div>
		</div>
	</div>

	<div class="row">
		<s:if test="#session.utilisateur">
			<s:a action="ajouterExemplaireTopo" class="btn btn-default btn-custom"><s:param name="titreTopo" value="topo.titre" /><s:text name="ajouterExemplaire" /></s:a>
		</s:if>
	</div>

	<div class="row marge">
		<s:if test="topo.description.titre!=''">
			<ul class="list-unstyled">
				<li><s:text name="descriptionTopo" /> :
					<ul class="list-unstyled">
						<li><strong><s:property
									value="topo.description.titre" /></strong></li>
						<s:iterator value="topo.description.listParagraphes" var="parag">
							<li><s:property value="parag" /></li>
						</s:iterator>
					</ul></li>
			</ul>
		</s:if>
	</div>
	<div class="jumbotron marge container">
		<h2><s:text name="infoTopo.listSpot" /> :</h2>
		<div class="row ">
			<div class="col-sm-4">
				<s:iterator value="topo.listSpot">
					<s:property value="nom" /> - 
					<s:a action="spotInfo">
						<s:param name="spotId" value="id" />
						<s:text name="accesDescriptionSpot" />
					</s:a>
					<br />
				</s:iterator>
			</div>
			<s:if test="%{topo.listPhotos.size()!=0}">
				<div class="col-sm-8">
					<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
						<!-- Wrapper for slides -->
						<div  class="carousel-inner" role="listbox">
							<s:iterator value="topo.listPhotos" var="photo" status="stat">
								<s:if test="#stat.index==0">
									<div class="item active ">
										<img src="img/topo/<s:property value="photo" />" alt="photo" class="imageCarousel"/>
									</div>
								</s:if>
								<s:else>
									<div class="item">
										<img src="img/topo/<s:property value="photo" />" alt="photo" class="imageCarousel"/>
										<s:set var="plusieursImages" value="%{true}"/>
									</div>
								</s:else>
							</s:iterator>
							<s:iterator value="topo.listSpot" var="spot">
								<s:iterator value="#spot.listPhotos" var="photo">
									<div class="item">
										<img src="img/spot/<s:property value="photo" />" alt="photo" class="imageCarousel"/>
										<s:set var="plusieursImages" value="%{true}"/>
									</div>
								</s:iterator>
							</s:iterator>
						</div>
						<!-- Controls -->
						<s:if test="#plusieursImages">
							<a class="left carousel-control" href="#carousel-example-generic"
								role="button" data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							</a> <a class="right carousel-control" href="#carousel-example-generic"
								role="button" data-slide="next"> <span
								class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							</a>
						</s:if>
					</div>
				</div>
			</s:if>
		</div>
	</div>

	<s:if test="#session.utilisateur">
	<div class="row marge">
		<h2><s:text name="emprunterTopo" /></h2>
		<div class="jumbotron marge">
			<s:form class="formClassiq">
				<div class="container">
					<s:textfield id="dateDebut" type="date" format="dd-MM-yyyy" key="dateDebut" requiredLabel="true" onchange="rechercheDispo()" class=" form-control" />
			
					<s:textfield id="dateFin" type="date" format="dd-MM-yyyy" key="dateFin" requiredLabel="true" onchange="rechercheDispo()" class="form-control miniMarge"/>
				</div>
			</s:form>
			
			<h2><s:text name="topoDispo" /></h2>
			
			<ul id="aucunResultat" class="list-unstyled">
				<li><s:text name="aucunEx"  /></li>
			</ul>
			<ul id="listTopoDispo" class="list-unstyled">
				
			</ul>
		</div>
	</div>
		
	</s:if>

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
	<script>
		function rechercheDispo() {

			var debut = $("#dateDebut").val()
			var fin = $('#dateFin').val()
			

			var $listTopoDispo = $("#listTopoDispo");
			$listTopoDispo.empty();
			
			if(debut!='' && fin != ''){
				if(fin>=debut){
					if(new Date(debut) >= new Date()){
						var aucunResult = $("#aucunResultat");
						aucunResult.hide();
						
						// URL de l'action AJAX
						var url = "<s:url action="ajax_topoDispo"/>";

						// Paramètres de la requête AJAX
						var params = {
							titreTopo : "${topo.titre}",
							debut : debut,
							fin : fin
						};

						// Action AJAX en POST
						jQuery.post(url, params, function(data) {
							var info='';
							
							jQuery.each(data, function(key, val) {
								info = '<div class="container"><s:text name="proprio" /> : ' + val.proprietaire.pseudo + '  - <s:text name="email" /> : <a href="mailto:' + val.proprietaire.mail + '">' + val.proprietaire.mail + '</a><br/><strong>'
										+ val.condition.titre + '</strong><br/>';
								$.each(val.condition.listParagraphes, function(index, value) {
									info += value + '<br/>';
								});

								var url2 = 'emprunterTopo.action?exemplaireId='+ val.id + '&debut=' + debut + '&fin=' + fin ; 
								info+= '<div class="col-sm-offset-8 col-sm-4 marge">';
								info+= '<a href = "' + url2 + '" class="btn btn-default btn-custom">' + '<s:text name="emprunterTopo" />';
								info+= '</a></div></div>'
								$listTopoDispo.append($('<li class="cadrePerso">').append(info));
							});
							
							if(info==''){
								aucunResult.show();
							}
							
						}).fail(function(data) {
							alert("Une erreur s'est produite.");
						});
					}
				}
			}
		}
	</script>

</body>
</html>