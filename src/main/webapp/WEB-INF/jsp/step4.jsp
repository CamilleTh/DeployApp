<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"> </script>
<script type="text/javascript" src="js/dyn.js"></script>

<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<link href="css/global.css" rel="stylesheet">

</head>
<body>

	<div class="container">
		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="index">DatabaseMigration</a>
					<div class="navbar-content">
						<ul class="nav">
							<li><a href="bdd1">1</a></li>
							<li><a href="bdd2">2</a></li>
							<li><a href="bdd3">3</a></li>
							<li class="active"><a href="bdd4">4</a></li>
							
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row main-features">
			<div class="span6">
				<div class="well">
					<h3>Console</h3>
					
					<h4> Excécution du script de contraction</h4>		
				
				</div>
			</div>
			<div class="span6">
				<div class="well">
					<h3>Script SQL</h3>
					<pre>
ALTER TABLE `Personne` DROP `adresse`;					
					</pre>
				</div>
			</div>
			<div class="span12">
				<div class="well">
					<h3>Model</h3>
					<img alt="2" id="image" src="images/4.png">
				</div>
			</div>
		</div>
	</div>
</body>
</html>




