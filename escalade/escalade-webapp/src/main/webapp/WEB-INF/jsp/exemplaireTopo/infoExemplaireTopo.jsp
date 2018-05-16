<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
</head>

<body class="corps container">
	<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>

	<h1>
		<s:text name="exemplaireTitre" /> : <s:property value="exemplaire.topo.titre" />
	</h1>
	
	<s:a action="infoTopo"><s:param name="titreTopo" value="exemplaire.topo.titre"/><s:text name="accesInfoTopo" /></s:a>
	
	<br/>
	<h3><s:text name="periodePretPrevues" /> :</h3>
	
	<s:iterator value="listEmprunt">
		<s:property value="emprunteur.pseudo" /> - <s:property value="emprunteur.mail" /> - <s:property value="dateDebut" /> - <s:property value="dateFin" /> - 
		<s:a action="supprimerEmpruntPreteur"><s:param name="empruntId" value="id"/><s:param name="exemplaireId" value="%{exemplaire.id}"/><s:text name="supprimerEmprunt" /></s:a>
		<br/>
	</s:iterator>

</body>
</html>