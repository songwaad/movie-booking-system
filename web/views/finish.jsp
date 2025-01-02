<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    request.setAttribute("pageTitle", "Finish"); 
    request.setAttribute("nav-movies", "");
    request.setAttribute("href-home", "../index.jsp"); 
    request.setAttribute("href-movies", "movies.jsp"); 
    request.setAttribute("href-cinemas", "cinemas.jsp"); 
%>

<%@ include file="components/top.jsp" %>
<link rel="stylesheet" href="../css/progreesbar.css">
<link rel="stylesheet" href="../css/finish.css">
<script src="../js/finish.js"></script>
<script src="../js/progressBar.js"></script>
<script>currentStep = 4;</script>

<%@ include file="components/progressbar.jsp" %>
<%@ include file="components/navbar.jsp" %>
<%@ include file="components/login-modal.jsp" %>


<div class="container">
    <div class="success-animation">
        <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52"><circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none" /><path class="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8" /></svg>
    </div>
    <p>Thank you for your booking!</p>
    <div class="button">
        <a href="myTicket.jsp">My Tickets</a>
    </div>
    
</div>


<%@ include file="components/bottom.jsp" %>