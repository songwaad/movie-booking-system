<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Cinemas"); 
    request.setAttribute("nav-cinemas", "active");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "movies.jsp"); 
    request.setAttribute("href-cinemas", "#"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/cinemas.css">
<script src="../js/cinemas.js"></script>

<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>

<div id="cinema-list" style="margin-top: 7%;"></div>

<%@ include file="components/bottom.jsp" %>