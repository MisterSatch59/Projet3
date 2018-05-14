<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<s:form action="creerUtilisateur">
		<legend>
			<s:text name="creerUtilisateur.title" />
		</legend>
		<s:textfield id = "pseudo" name="pseudo" key="pseudo" requiredLabel="true" maxlength="30" />
		
		<s:password id = "mdp" name="mdp" key="mdp" requiredLabel="true" />
		<s:password id = "mdp2" name="mdp2" key="confirmation" requiredLabel="true" />
		
		<s:textfield id = "email" name="email" key="email" requiredLabel="true" type="email" />
	
		<s:submit key="valider" name=""/>
	</s:form>
	
</body>
</html>