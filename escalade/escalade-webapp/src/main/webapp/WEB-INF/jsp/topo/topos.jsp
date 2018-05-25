<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<!-- Titre  et bouton création-->
	<div class="row aligneCentre">
		<div class="col-sm-6 col-xs-12">
			<h1><s:text name="topos" /></h1>
		</div>
		<s:if test="#session.utilisateur">
			<div class="col-sm-offset-3 col-sm-3 col-xs-12">
				<s:a action="creerTopo" class="btn btn-default btn-custom">
					<s:text name="topo.creer" />
				</s:a>
			</div>
		</s:if>
	</div>
	
	<!-- Liste des topo -->
	<div class="row marge">
		<div class="col-xs-12">
			<s:iterator value="listTopos">
				<div class="col-md-6 topo">
					<div class="jumbotron">
						<h3><s:property value="titre" /></h3>
						<s:if test="description.titre!=''">
							<ul class="list-unstyled">
								<li><s:text name="descriptionTopo" /> :
									<ul class="list-unstyled">
										<li><strong><s:property value="description.titre" /></strong></li>
										<s:iterator	value="description.listParagraphes" var="parag">
											<li><s:property value="parag" /></li>
										</s:iterator>
									</ul>
								</li>
							</ul>
						</s:if>
						<div class="col-xs-offset-3 col-sm-9">
							<s:a action="infoTopo" class="btn btn-default btn-custom">
								<s:param name="titreTopo"><s:property value="titre" /></s:param>
								<s:text name="accesInfoTopo" />
							</s:a>
						</div>
					</div>
				</div>
			</s:iterator>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>