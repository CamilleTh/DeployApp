<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! SNAPSHOT 0.0.6</h1>
    
        <ul>
	        <li> 
	        	<a href="<%=  request.getContextPath()  %>/Migrate?V=1">Migration V1</a>
	        </li>
	         <li> 
	        	<a href="<%=  request.getContextPath()  %>/Migrate?V=2">Migration V2</a>
	        </li>
	      	<li> 
	        	<a href="<%=  request.getContextPath()  %>/Migrate?V=3">Migration V3</a>
	        </li>     
	         <li> 
	        	<a href="<%=  request.getContextPath()  %>/bdd1">Test Migration 1</a>
	        </li>
	         <li> 
	        	<a href="<%=  request.getContextPath()  %>/bdd2">Test Migration 2</a>
	        </li>
	         <li> 
	        	<a href="<%=  request.getContextPath()  %>/bdd3">Test Migration 3</a>
	        </li>
</ul>
        
    </body>
</html>
