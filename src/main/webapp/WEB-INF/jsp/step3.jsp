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
							<li class="active"><a href="bdd3">3</a></li>
							<li><a href="bdd4">4</a></li>

							<button type="button" id="flip" value="0" class="btn btn-primary">FLIP
								!</button>

						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row main-features">
			<div class="span6">
				<div class="well">
					<h3>Console</h3>

					<h4>Exécution de l'expansion des données</h4>


				</div>
			</div>
			<div class="span6">
				<div class="well">
					<h3>Data</h3>
					<div id="data">DATA...</div>
					<img id="loader" src="images/loader.gif">
				</div>
			</div>
			<div class="span12">
				<div class="well">
					<h3>Model</h3>
					<img alt="2" id="image" src="images/3a.png">
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

$(document).ready(function() {
	
	$("#loader").hide();
	
	$("#flip").click(function() {
		
		   var value = $(this).val();
		   console.log(value);
		   
		   if(value == 0) $("#flip").val("1");
		   if(value == 1) $("#flip").val("0");
		   
		   if(value == 0){
			   $('#image').attr('src','images/3a.png'); 
				$("#data").html("");
			   	$("#loader").show(); 
		   		$.ajax({
		   			type: 'GET',
		   			url : 'Flip1',
		   			success : function(data){
		   				console.log(data);
					   	$("#loader").hide(); 
		   				$("#data").html(data);

		   			},
		   			
		   		});
		   }
		   else{   
			   $('#image').attr('src','images/3b.png'); 
			  
				$("#data").html("");
			   	$("#loader").show(); 

			   $.ajax({
		   			type: 'GET',
		   			url : 'Flip2',
		   			success : function(data){
		   				console.log(data);
		   				$("#loader").hide(); 
		   				$("#data").html(data);
		   			}
		   		});
		   }   
			   
	});

}); 

</script>
</html>




