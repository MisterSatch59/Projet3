<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
	
	<p>
		<s:a action="creerSpot">
			<s:text name="spots.creer" />
		</s:a>
	</p>

	<s:form action="rechercheSpot">
		<legend>
			<s:text name="spots.recherche.title" />
		</legend>
		<s:select id = "departement" name="departement" key="spots.recherche.departement" list="listDepartements" emptyOption="true" requiredLabel="false" onchange="onSelectDepartementChange()" />
		<s:select id = "ville" name="ville" key="spots.recherche.ville" list="listVille" emptyOption="true" requiredLabel="false" />
		<s:select id = "difficulteMin" name="difficulteMin" key="spots.recherche.diffMin" list="listDifficultes" emptyOption="true" requiredLabel="false" />
		<s:select id = "difficulteMax" name="difficulteMax" key="spots.recherche.diffMax" list="listDifficultes" emptyOption="true" requiredLabel="false" />

	</s:form>
		<button onclick="rechercheSpots()">
			<s:text name="spots.rechercheButton" />
		</button>

	<ul id="rechercheListe">
		
	</ul>
	
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
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
						var detail = '<ul><li>' + val.types;
						detail += '<li>' + val.profils;
						detail += '<li>' + val.orientations;
						detail += '<li>' + '<s:text name="spots.resultat.diff" /> ' + val.difficulteMin + ' <s:text name="spots.resultat.a" /> ' + val.difficulteMax;
						//Hauteurs Min et Max
						detail += '<li>' ;
						if (val.hauteurMin!=0){
							detail += '<s:text name="spots.resultat.hauteur.min" /> : ' + val.hauteurMin +" - ";
						}
						detail+='<s:text name="spots.resultat.hauteur.max" /> : ';
						detail+= val.hauteurMax;
						//Adapte aux enfants
						detail+='<li><s:text name="spots.resultat.enfants" /> : '
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
							detail+= '<s:text name="spots.resultat.secteurs" /> : ' + val.nbSecteur +" - ";
						}
						detail+= '<s:text name="spots.resultat.voies" /> : ' + val.nbVoie;
						
						//lien vers fiche
						var url2 = "spotInfo.action?spotId="+ val.id ; 
						detail+= '<li>' + '<a href = "' + url2 + '">' + '<s:text name="spots.resultat.description" />';
						
						
						$listSpots.append($('<li>').append(val.nom + " - " + val.ville.departement.nom + " - " + val.ville.nom).append(detail));
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
	
</body>

<footer>
	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</footer>
</html>