<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>

	<h1>
		<s:text name="exemplaireTitre" /> : <s:property value="exemplaire.topo.titre" />
	</h1>
	
	<div class="row">
		<div class="jumbotron col-sm-offset-1 col-sm-10 col-xs-12 marge">
					
			<h2><s:text name="periodePretPrevues" /> :</h2>
			
			<ul class="list-unstyled">
				<s:iterator value="listEmprunt">
					<li class="cadrePerso"><s:property value="dateDebut" /> - <s:property value="dateFin" /> :
						<ul class="list-unstyled">
							<li><s:text name="emprunteur" /> : <s:property value="emprunteur.pseudo" /> - <s:text name="email" /> : <a href="mailto:<s:property value="emprunteur.mail" />"><s:property value="emprunteur.mail" /></a></li>
							<li><s:a action="supprimerEmpruntPreteur" class="marge btn btn-default btn-custom-rouge"><s:param name="empruntId" value="id"/><s:param name="exemplaireId" value="%{exemplaire.id}"/><s:text name="supprimerEmprunt" /></s:a></li>
						</ul>
					</li>
					<br/>
				</s:iterator>
			</ul>
			<s:if test="%{listEmprunt.size()==0}"><s:text name="aucuneResaEnr" /></s:if>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>