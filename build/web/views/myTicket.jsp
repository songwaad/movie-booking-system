<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "My Tickets"); 
    request.setAttribute("nav-movies", "");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "movies.jsp"); 
    request.setAttribute("href-cinemas", "cinemas.jsp"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/myTicket.css">
<script src="../js/myTicket.js"></script>

<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>

<div class="container">
    <div class="flexbox" id="ticket-list">
    </div>
</div>


<%@ include file="components/bottom.jsp" %>


