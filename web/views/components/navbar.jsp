<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav id="navbar">
        <div class="menu">
            <p class="logo">LOSTHER</p>
            <a href="<%= request.getAttribute("href-home")%>" class="<%= request.getAttribute("nav-home")%>">Home</a>
            <a href="<%= request.getAttribute("href-movies")%>" class="<%= request.getAttribute("nav-movies")%>">Movies</a>
            <a href="<%= request.getAttribute("href-cinemas")%>" class="<%= request.getAttribute("nav-cinemas")%>">Cinemas</a>
        </div>
        <div class="icon">
            <!-- <div><a href="#"><i class="fa-solid fa-magnifying-glass"></i></a></div> -->
            <div><a href="/Losther_JSP/views/myTicket.jsp" id="ticket-button"><i class="fa-solid fa-ticket"></i></a></div>
            <div><a href="#" id="login-button"><i class="fa-solid fa-right-to-bracket"></i></a></div>
            <div><a href="#" id="logout-button"><i class="fa-solid fa-power-off"></i></a></div>
        </div>
    </nav>