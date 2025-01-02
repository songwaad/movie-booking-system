<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Movies"); 
    request.setAttribute("nav-movies", "active");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "movies.jsp"); 
    request.setAttribute("href-cinemas", "cinemas.jsp"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/movieDetails.css">
<script src="../js/movieDetails.js"></script>

<!-- Movie -->
<div class="container" id="movie-details"></div>


<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>

<%@ include file="components/bottom.jsp" %>