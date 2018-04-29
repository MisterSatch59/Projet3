<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
	</head>
	
	<body class="corps container">
		<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
		
		<img src="img/logoBleu.png" alt= "test" />
		<img src="img/PhotoAccueil.jpg" alt= "Accueil" />
		
		<h2><s:text name="index.welcome" /></h2>
		
		<p>
			<s:text name="index.para.l1" /><br/>
			<s:text name="index.para.l2" /><br/>
			<s:text name="index.para.l3" /><br/>
			<s:text name="index.para.l4" /><br/>
			<s:text name="index.para.l5" /><br/><br/>
			<s:text name="index.para.l6" />
		</p>
	</body>
	
	<footer>
		<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
	</footer>
</html>
