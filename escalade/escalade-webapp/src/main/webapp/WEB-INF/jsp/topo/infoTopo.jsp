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
		<s:property value="topo.titre" />
	</h1>

	<s:if test="#session.utilisateur">
		<p>
			<s:a action="modifierTopo">
				<s:param name="titreTopo" value="topo.titre" />
				<s:text name="modifier" />
			</s:a>
			<s:if test="#session.utilisateur.admin==true">
					 - 
					<s:a action="supprimerTopo">
					<s:param name="titreTopo" value="topo.titre" />
					<s:text name="supprimer" />
				</s:a>
				<br />
				<br />
			</s:if>
		</p>
	</s:if>

	<s:if test="topo.description.titre!=''">
		<s:property value="topo.description.titre" />
		<br />
		<s:iterator value="topo.description.listParagraphes" var="parag">
			<s:property value="parag" />
			<br />
		</s:iterator>
	</s:if>

	<h2>
		<s:text name="infoTopo.listSpot" />
	</h2>

	<s:iterator value="topo.listSpot">
		<s:property value="nom" /> - 
		<s:a action="spotInfo">
			<s:param name="spotId" value="id" />
			<s:text name="accesDescriptionSpot" />
		</s:a>
		<br />
	</s:iterator>

	<br />

	<s:if test="#session.utilisateur">
		<s:form>
			<legend>
				<s:text name="recherche" />
			</legend>
	
			<s:textfield id="dateDebut" type="date" format="dd-MM-yyyy" key="dateDebut" onchange="rechercheDispo()" />
	
			<s:textfield id="dateFin" type="date" format="dd-MM-yyyy" key="dateFin" onchange="rechercheDispo()"/>
	
		</s:form>
	
	
		<h2>
			<s:text name="topoDispo" />
		</h2>
	
		<ul id="listTopoDispo">
		</ul>
	</s:if>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

	<script>
		function rechercheDispo() {

			var debut = $("#dateDebut").val()
			var fin = $('#dateFin').val()

			var $listTopoDispo = $("#listTopoDispo");
			$listTopoDispo.empty();
			
			if(debut!='' && fin != ''){
				if(fin>debut){
					if(new Date(debut)>new Date()){
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
							jQuery.each(data, function(key, val) {
								var info = val.proprietaire.pseudo + ' - <a href="mailto:' + val.proprietaire.mail + '">' + val.proprietaire.mail + '</a><br/>'
										+ val.condition.titre + '<br/>';
								$.each(val.condition.listParagraphes, function(index, value) {
									info += value + '<br/>';
								});

								var url2 = 'emprunterTopo.action?exemplaireId='+ val.id + '&debut=' + debut + '&fin=' + fin ; 
								info+= '<a href = "' + url2 + '">' + '<s:text name="emprunterTopo" />';
								$listTopoDispo.append($('<li>').append(info));
							});
							
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