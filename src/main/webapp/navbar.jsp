<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <a href="index.jsp">
                    <img class="m-2 fs-3 ps-3 animate-2 fadeIn a-delay-1"
                         style="height: 25px"
                         alt="logo"
                         src="images/logo.svg">
                </a>
            </div>

            <div class="col-md-6 col-12 order-2 order-md-1 mt-3 mt-md-0 fadeIn animate-2 a-delay-2">
                <form id="searchbarForm">
                    <div class="input-group form-floating">
                        <input type="text" class="form-control bg-card" id="searchbar" aria-describedby="filter" placeholder="Ricerca">
                        <a href="search.jsp" class="btn px-4 btn-secondary rounded-end text-center align-items-center justify-content-center d-inline-flex" type="button" id="filter">filtri</a>
                        <label for="searchbar" class="label-bg-none z-5">ricerca</label>
                    </div>
                </form>
            </div>

            <div class="col-md-3 d-flex justify-content-end align-items-center pe-3 fs-3 col-6 order-1 order-md-2 fadeIn animate-2 a-delay-3">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <a href="user.jsp">
                        <i  class="bi bi-person-circle" style="cursor: pointer;"></i>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <h5 class="text" style="text-decoration : underline; cursor: pointer;"
                            onclick="toggleLoginOverlay()">Login
                        </h5>
                        <h5 class="text">/</h5>
                        <h5 class="text" style="text-decoration : underline; cursor: pointer"
                            onclick="toggleSignupOverlay()">SignUp</h5>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>

    <div id="loginOverlay" class="overlay">
        <div class="overlay-content">
            <div class="overlay-header">
                <h2>Accedi</h2>
                <span class="close-btn" onclick="toggleLoginOverlay()">&times</span>
            </div>
            <form id="loginForm">
                <div class="mb-3">
                    <label for="usernameLogin" class="form-label">Username</label>
                    <input type ="text" class="form-control" id="usernameLogin" name="username" required>
                </div>
                <div class = "mb-3">
                    <label for="passwordLogin" class="form-label">Password</label>
                    <input type="password" class="form-control" id="passwordLogin" name="password" required>
                </div>
                <div id="loginError" class="text-danger mb-2"></div>
                <button type="submit" class="btn btn-primary w-100">Login</button>
            </form>
        </div>
    </div>

    <div id="signupOverlay" class="overlay">
        <div class="overlay-content">
            <div class="overlay-header">
                <h2>Iscriviti</h2>
                <span class="close-btn" onclick="toggleSignupOverlay()">&times</span>
            </div>
            <form id="signupForm">
                <div class="mb-3">
                    <label for="usernameSignup" class="form-label">Username</label>
                    <input type ="text" class="form-control" id="usernameSignup" name="username" required>
                </div>
                <div class = "mb-3">
                    <label for="passwordSignup" class="form-label">Password</label>
                    <input type="password" class="form-control" id="passwordSignup" name="password" required>
                </div>
                <div id="signupError" class="text-danger mb-2"></div>
                <button type="submit" class="btn btn-primary w-100">Sign-up</button>
            </form>
        </div>
    </div>




    <script>
        function toggleLoginOverlay() {
            let overlay = document.getElementById("loginOverlay");
            overlay.style.display = (overlay.style.display === "flex") ? "none" : "flex";

            if (overlay.style.display === "flex") {
                document.getElementById("loginError").textContent = "";
            }
        }

        function toggleSignupOverlay() {
            let overlay = document.getElementById("signupOverlay");
            overlay.style.display = (overlay.style.display === "flex") ? "none" : "flex";

            if (overlay.style.display === "flex") {
                document.getElementById("signupError").textContent = "";
            }
        }

        function showError(message, elementId) {
            document.getElementById(elementId).textContent = message;
        }

        document.addEventListener("DOMContentLoaded", function () {
            document.getElementById("loginForm").addEventListener("submit", function (event) {
                event.preventDefault();
                let formData = new FormData(this);

                fetch("login", {
                    method: "POST",
                    body: formData
                })
                    .then(response => {
                        if (!response.ok) throw new Error("Login fallito");
                        window.location.reload();
                    })
                    .catch(error => showError(error.message, "loginError"));
            });

            document.getElementById("signupForm").addEventListener("submit", function (event) {
                event.preventDefault();
                let formData = new FormData(this);

                fetch("signup", {
                    method: "POST",
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.error) {
                            showError(data.error, "signupError");
                        } else {
                            showError("Registrazione completata! Effettua il login.", "signupError");
                        }
                    })
                    .catch(error => showError("Errore di registrazione.", "signupError"));
            });
        });

    </script>




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