<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="css/navbar.css">

<%
    String pageBase = "GameTalk_war_exploded";
    String pageToAnimate = "index.jsp";
    String[] pageNameSplited = request.getRequestURI().split("/");
    String pageName = pageNameSplited[pageNameSplited.length - 1];
    if (pageName.equalsIgnoreCase(pageToAnimate) || pageName.equalsIgnoreCase(pageBase)){
%>

<nav class="navbar navbar-expand-lg  navbar-dark shadow bg-nav animate-2 navDrop">
<%}else{%>
<nav class="navbar navbar-expand-lg  navbar-dark shadow bg-nav a-block-zone">
<%}%>
    <div class="container-fluid row p-0 m-0">
        <div class="col-md-3 col-6 order-0">
            <a href="index.jsp" class="text-decoration-none text-light fs-3 ps-3 animate-2 fadeIn a-delay-1">GameTalk</a>
        </div>

        <div class="col-md-6 col-12 order-2 order-md-1 mt-3 mt-md-0 fadeIn animate-2 a-delay-2">
            <form id="searchbarForm">
                <div class="input-group form-floating">
                    <input type="text" class="form-control" id="searchbar" aria-describedby="filter">
                    <a href="search.jsp" class="btn px-4 btn-secondary rounded-end text-center align-items-center justify-content-center d-inline-flex" type="button" id="filter">filtri</a>
                    <label for="searchbar">ricerca</label>
                </div>
            </form>
        </div>

        <div class="col-md-3 d-flex justify-content-end pe-3 fs-3 col-6 order-1 order-md-2 fadeIn animate-2 a-delay-3">
            <i class="bi bi-person-circle"></i>
        </div>
    </div>
</nav>

<!--
<form id="searchbarForm">
<div class="input-group form-floating">
<input type="text" class="form-control" id="searchbar" placeholder="name@example.com" aria-describedby="button-addon2">
<a href="search.jsp" class="btn btn-secondary" type="button" id="button-addon2">Filtri</a>
<label for="searchbar">ricerca</label>
</div>
</form>

<form id="searchbarForm" class="form-floating">
<input type="text" class="form-control" id="searchbar" placeholder="name@example.com" value="">
<label for="searchbar">ricerca</label>
</form>
-->