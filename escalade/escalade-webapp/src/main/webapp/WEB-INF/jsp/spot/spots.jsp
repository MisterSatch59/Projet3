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
		<s:text name="spots" />
	</h1>
	
	<p>
		<s:a action="versCreerSpot">
			<s:text name="spots.creer" />
		</s:a>
	</p>

	<s:form>
		<legend>
			<s:text name="recherche" />
		</legend>
		<s:select id = "departement" name="departement" key="departement" list="listDepartements" emptyOption="true" requiredLabel="false" onchange="onSelectDepartementChange()" />
		<s:select id = "ville" name="ville" key="ville" list="listVille" emptyOption="true" requiredLabel="false" />
		<s:select id = "difficulteMin" name="difficulteMin" key="diffMin" list="listDifficultes" emptyOption="true" requiredLabel="false" />
		<s:select id = "difficulteMax" name="difficulteMax" key="diffMax" list="listDifficultes" emptyOption="true" requiredLabel="false" />

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
						detail+= '<li>' + '<a href = "' + url2 + '">' + '<s:text name="accesDescriptionSpot" />';
						
						
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