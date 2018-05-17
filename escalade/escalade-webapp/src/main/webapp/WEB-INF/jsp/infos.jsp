<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/_include/head.jsp"%>
	</head>
	
	<body class="container">
		<%@ include file="/WEB-INF/jsp/_include/header.jsp"%>
		
		<h1><s:text name="infos.titre" /></h1>
		
		<div class="jumbotron container-fluid">
			<h2><s:text name="infos.para1.titre" /></h2>
			
			<div class="thumbnail col-md-7">
				<img src="img/info1.jpg" alt= "Image" />
			</div>
			<div class="col-md-5">
				<p>
					<s:text name="infos.para1.l1" />
				</p>
			</div>
			<div class="col-md-12">
				<p>
					<s:text name="infos.para1.l2" />
				</p>
				<p>
					<s:text name="infos.para1.l3" />
				</p>
			</div>
		</div>
		
		<div class="jumbotron container-fluid">
			<h2><s:text name="infos.para2.titre" />?</h2>
		
			<div class="col-md-12">
				<p>
					<s:text name="infos.para2.l1" />
				</p>
			</div>
			<div class="col-md-5">
				<p>
					<s:text name="infos.para2.l2" />
				</p>
			</div>
			<div class="thumbnail col-md-7">
				<img src="img/info2.jpg" alt= "Image" />
			</div>
			<div class="col-md-12">
				<p>
					<s:text name="infos.para2.l3" />
				</p>
			</div>
		</div>
		<div class="jumbotron container-fluid">
			<h2><s:text name="infos.para3.titre" /></h2>
			<div class="thumbnail col-md-7">
				<img src="img/info3.jpg" alt= "Image" />
			</div>
			<div class="col-md-5">
				<ul>
					<li><s:text name="infos.para3.l1" /></li>
					<li><s:text name="infos.para3.l2" /></li>
					<li><s:text name="infos.para3.l3" /></li>
					<li><s:text name="infos.para3.l4" /></li>
					<li><s:text name="infos.para3.l5" /></li>
					<li><s:text name="infos.para3.l6" /></li>
				</ul>
			</div>
		</div>
		
		
		<!-- jQuery -->
		<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<!-- Javascript de Bootstrap -->
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
			integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
			crossorigin="anonymous">
		</script>
		
		<%@ include file="/WEB-INF/jsp/_include/footer.jsp"%>
	</body>
	
</html>