<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<h1><s:text name="profilUtilisateur" /> - <s:property value="#session.utilisateur.pseudo" /></h1>

	<div class="row">
		<!-- Liste des emprunts -->
		<div class="col-md-6">
			<div class="jumbotron">
				<h2><s:text name="listEmprunt.title" /></h2>
				<ul>
					<s:iterator value="listEmprunt">
						<li><s:property value="dateDebut" /> - <s:property value="dateFin" /> :
							<ul class="list-unstyled">
								<li><s:text name="topo" /> :<s:a action="infoTopo"><s:param name="titreTopo" value="exemplaire.topo.titre"/><s:property value="exemplaire.topo.titre" /></s:a></li>
								<li><s:text name="proprio" /> :</li>
								<li><s:property value="exemplaire.proprietaire.pseudo" /> - <s:text name="email" /> : <a href="mailto:<s:property value="exemplaire.proprietaire.mail" />"><s:property value="exemplaire.proprietaire.mail" /></a></li>
								<li><s:a action="supprimerEmpruntEmprunteur" class="marge btn btn-default btn-custom"><s:param name="empruntId" value="id"/><s:text name="supprimerEmprunt" /></s:a></li>
							</ul>
						</li>
						<br/>
					</s:iterator>
				</ul>
				<s:if test="%{listEmprunt.size()==0}"><s:text name="aucunEmpruntEnr" /></s:if>
			</div>
		</div>
		
		<!-- Liste des exemplaires -->
		<div class="col-md-6">
			<div class="jumbotron">
				<h2><s:text name="listExemplaireTopo.title" /></h2>
				<s:iterator value="listExemplaireTopo">
					<s:property value="topo.titre" /> - <s:a action="infoTopo"><s:param name="titreTopo" value="topo.titre" /><s:text name="spotInfo.voirTopo" /></s:a> - 
					<s:a action="infoExemplaireTopo"><s:param name="exemplaireId" value="id"/><s:text name="voirPeriodeEmprunt" /></s:a>
					<br/>
				</s:iterator>
				<s:if test="%{listExemplaireTopo.size()==0}"><s:text name="aucunExemplaireEnr" /></s:if>
			</div>
		</div>
	</div>
	
	<!-- Modifier -->
	<div class="row">
		<div class="jumbotron col-sm-offset-3 col-sm-6 col-xs-12 marge">
			<h2><s:text name="modifierUtilisateur.title" /></h2>
			<s:form action="modifierUtilisateur">
				<s:password id = "mdp" name="mdp" key="mdp" class="form-control miniMarge"/>
				<s:password id = "mdp2" name="mdp2" key="confirmation" class="form-control miniMarge"/>
				
				<s:textfield id = "email" name="email" key="email" type="email" value="%{#session.utilisateur.mail}" class="form-control miniMarge"/>
				
				<s:submit class="col-sm-offset-3 col-sm-6 col-xs-12 marge btn btn-default btn-custom" key="valider" name=""/>
			</s:form>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>