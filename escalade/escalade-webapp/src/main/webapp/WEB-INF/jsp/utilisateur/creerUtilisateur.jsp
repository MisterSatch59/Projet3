<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
	
	<h1><s:text name="creerUtilisateur.title" /></h1>
	
	<div class="row">
		<div class="jumbotron col-lg-offset-2 col-lg-8 col-md-12 marge">
			<s:form action="creerUtilisateur" class="formClassiq" method="POST" enctype="multipart/form-data">
				<s:textfield id = "pseudo" name="pseudo" key="pseudo" requiredLabel="true" maxlength="30" class=" form-control"/>
				
				<s:password id = "mdp" name="mdp" key="mdp" requiredLabel="true" class="form-control miniMarge"/>
				<s:password id = "mdp2" name="mdp2" key="confirmation" requiredLabel="true" class="form-control miniMarge"/>
				
				<s:textfield id = "email" name="email" key="email" requiredLabel="true" type="email" class="form-control miniMarge"/>
				
				<s:file name="myFile" key="avatar" accept="image/png" />
				
				<s:submit class="col-sm-offset-3 col-sm-6 col-xs-12 marge btn btn-default btn-custom" key="valider" name=""/>
			</s:form>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
</body>
</html>