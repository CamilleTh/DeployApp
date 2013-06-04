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
							<li class="active"><a href="bdd2">2</a></li>
							<li><a href="bdd3">3</a></li>
							<li><a href="bdd4">4</a></li>							
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row main-features">
			<div class="span6">
				<div class="well">
					<h3>Console</h3>
					
					<h4> Exécution de l'expansion de la structure </h4>
					
					<c:forEach items="${ messages }" var="message" varStatus="boucle">
						<p>${ boucle.count }. ${ message }</p>
					</c:forEach>
				</div>
			</div>
			<div class="span6">
				<div class="well">
					<h3>Script SQL</h3>
					<pre>
ALTER TABLE `40853_intech`.`Personne` ADD COLUMN `Adresse_idAdresse` INT(11) NOT NULL  AFTER `adresse` , 
  ADD CONSTRAINT `fk_Personne_Adresse`
  FOREIGN KEY (`Adresse_idAdresse` )
  REFERENCES `40853_intech`.`Adresse` (`idAdresse` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_Personne_Adresse_idx` (`Adresse_idAdresse` ASC) ;

CREATE  TABLE IF NOT EXISTS `40853_intech`.`Adresse` (
  `idAdresse` INT(11) NOT NULL AUTO_INCREMENT ,
  `num` INT(5) NULL DEFAULT NULL ,
  `rue` VARCHAR(45) NULL DEFAULT NULL ,
  `code` INT(5) NULL DEFAULT NULL ,
  `ville` VARCHAR(45) NULL DEFAULT NULL ,
  `pays` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`idAdresse`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;
					</pre>
				</div>
			</div>
			<div class="span12">
				<div class="well">
					<h3>Model</h3>
					<img alt="2" src="images/2.png">
				</div>
			</div>
		</div>
	</div>
</body>
</html>

