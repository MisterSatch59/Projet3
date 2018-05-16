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
		<s:text name="topos" />
	</h1>

	<s:iterator value="listTopos">
			
		<s:property value="titre" />
		<ul>
			<s:if test="description.titre!=''">
				<li>
					<s:property value="description.titre" /><br/>
					<s:iterator value="description.listParagraphes" var="parag">
						<s:property value="parag" /><br/>
					</s:iterator>
		  		</li>
			</s:if>
		</ul>
		<s:a action="infoTopo"><s:param name="titreTopo"><s:property value="titre" /></s:param><s:text name="spotInfo.voirTopo" /></s:a>
	</s:iterator>
	<s:if test="#session.utilisateur">
		<br/><br/>
		<s:a action="creerTopo"><s:text name="creerTopo" /></s:a>
	</s:if>

</body>
</html>