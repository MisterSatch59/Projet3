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
	
	<div class="row aligneCentre">
	
		<div class="col-sm-6 col-xs-12">
			<h1><s:text name="topos" /></h1>
		</div>
	
	</div>

	<div class="jumbotron">
		<div class="row">
			<s:iterator value="listTopos">
				<h4><s:property value="titre" /></h4>
	
				<s:if test="description.titre!=''">
					<ul>
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

				<s:a action="infoTopo" class="btn btn-default btn-custom">
					<s:param name="titreTopo"><s:property value="titre" /></s:param>
					<s:text name="accesInfoTopo" />
				</s:a>
			</s:iterator>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>