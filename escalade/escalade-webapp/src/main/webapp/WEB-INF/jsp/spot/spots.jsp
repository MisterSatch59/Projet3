<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>

	<div class="row aligneCentre">
	
		<div class="col-sm-6 col-xs-12">
			<h1><s:text name="spots" /></h1>
		</div>
	

		<s:if test="#session.utilisateur">
			<div class="col-sm-offset-3 col-sm-3 col-xs-12">
				<s:a action="versCreerSpot" class="btn btn-default btn-custom">
					<s:text name="spots.creer" />
				</s:a>
			</div>
		</s:if>
	</div>

	<div class="row marge">
		<s:form>
			<div class="formInLinePerso">
				<s:select id = "departement" name="departement" key="departement" list="listDepartements" emptyOption="true" requiredLabel="false" onchange="onSelectDepartementChange()" cssClass="form-control" />
				<s:select id = "ville" name="ville" key="ville" list="listVille" emptyOption="true" requiredLabel="false" cssClass="form-control" />
				<s:select id = "difficulteMin" name="difficulteMin" key="diffMin" list="listDifficultes" emptyOption="true" requiredLabel="false" cssClass="form-control" />
				<s:select id = "difficulteMax" name="difficulteMax" key="diffMax" list="listDifficultes" emptyOption="true" requiredLabel="false" cssClass="form-control" />
			</div>
		</s:form>
	</div>
	<div class="row marge">
		<button onclick="rechercheSpots()"  class="btn btn-default btn-custom col-sm-offset-4 col-sm-4 col-xs-12">
			<s:text name="spots.rechercheButton" />
		</button>
	</div>

	<div class="row marge">
		<div id="rechercheListe" class="col-xs-12">
			
		</div>
	</div>
	
	<!-- jQuery -->
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<!-- Javascript de Bootstrap -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous">
		</script>
	<script>
		function rechercheSpots() {
			// URL de l'action AJAX
			var url = "<s:url action="ajax_rechercheSpot"/>";
	
			// Paramètres de la requête AJAX
			var params = {
				ville: jQuery("#ville").val(), departement: jQuery("#departement").val(), difficulteMin: jQuery("#difficulteMin").val(), difficulteMax: jQuery("#difficulteMax").val()
			};
	
			// Action AJAX en POST
			jQuery.post(
				url,
				params,
				function (data) {
					var $listSpots = jQuery("#rechercheListe");
					$listSpots.empty();
					jQuery.each(data, function (key, val) {
						//Types, profils et orientations
						var detail = '<ul class="list-unstyled"><li>' + '<s:text name="types"/> : ' + val.types;
						detail += '<li>' + '<s:text name="profils"/> : ' + val.profils;
						detail += '<li>' + '<s:text name="orientations"/> : ' + val.orientations;
						detail += '<li>' + '<s:text name="spots.resultat.difficulte.de" /> ' + val.difficulteMin + ' <s:text name="spots.resultat.a" /> ' + val.difficulteMax;
						//Hauteurs Min et Max
						detail += '<li>' ;
						if (val.hauteurMin!=0){
							detail += '<s:text name="hauteurMin" /> : ' + val.hauteurMin +" - ";
						}
						detail+='<s:text name="hauteurMax" /> : ';
						detail+= val.hauteurMax;
						//Adapte aux enfants
						detail+='<li><s:text name="accesEnfants" /> : '
						if (val.adapteEnfants==true){
							detail += '<s:text name="oui" /> ';
						}else{
							if (val.adapteEnfants==false){
								detail += '<s:text name="non" /> ';
							}else{
								detail +="? ";
							}
						}
						//Nb secteurs et voies
						detail+='<li>';
						if(val.nbSecteur>0){
							detail+= '<s:text name="nbSecteurs" /> : ' + val.nbSecteur +" - ";
						}
						detail+= '<s:text name="nbVoies" /> : ' + val.nbVoie;
						
						//lien vers fiche
						var url2 = "spotInfo.action?spotId="+ val.id ; 
						detail+= '<li><div class="col-xs-offset-5 col-sm-7">' + '<a href = "' + url2 + '" class="btn btn-default btn-custom" >' + '<s:text name="accesDescriptionSpot" />';
						
						
						$listSpots.append($('<div class="col-md-6">').append($('<div class="jumbotron">').append('<h2>' +val.nom + " - " + val.ville.nom + " - " + val.ville.departement.nom).append(detail)));
					});
					
					
				}
			)
			.fail(function () {
				alert("Une erreur s'est produite.");
			});
		}
		
		function onSelectDepartementChange() {
		    // URL de l'action AJAX
		    var url = "<s:url action="ajax_getVilles"/>";
		    // Paramètres de la requête AJAX
		    var params = {
		    	departement: jQuery("#departement").val()
		    };
		    
		    // Action AJAX en POST
		    jQuery.post(
		        url,
		        params,
		        function (data) {
		            var $selectVille = jQuery("#ville");
		            $selectVille.empty();
		            $selectVille.append(
		                    jQuery("<option>")
		                        .text("")
		                        .val("")
		                );
		            jQuery.each(data, function (key, val) {
		                $selectVille.append(
		                    jQuery("<option>")
		                        .text(val.nom)
		                        .val(val.nom)
		                );
		            });
		        })
		        .fail(function (data) {
		            alert("Une erreur s'est produite.");
		        });
		}
	</script>
	
	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>

</html>