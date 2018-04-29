<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
	</head>
	
	<body class="corps container">
		<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
		
		<h1><s:text name="infos.titre" /></h1>
		
		<h2><s:text name="infos.para1.titre" /></h2>
		<p>
			<s:text name="infos.para1.l1" /><br/>
			<s:text name="infos.para1.l2" /><br/>
			<s:text name="infos.para1.l3" />
		</p>
		<h2><s:text name="infos.para2.titre" />?</h2>
		<p>
			<s:text name="infos.para2.l1" /><br/>
			<s:text name="infos.para2.l2" /><br/>
			<s:text name="infos.para2.l3" />
		</p>
		<h2><s:text name="infos.para3.titre" /></h2>
		<ul>
			<li><s:text name="infos.para3.l1" /></li>
			<li><s:text name="infos.para3.l2" /></li>
			<li><s:text name="infos.para3.l3" /></li>
			<li><s:text name="infos.para3.l4" /></li>
			<li><s:text name="infos.para3.l5" /></li>
			<li><s:text name="infos.para3.l6" /></li>
		</ul>
		
	</body>
	
	<footer>
		<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
	</footer>
</html>