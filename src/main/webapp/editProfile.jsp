<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="it.unisa.studenti.nc8.gametalk.storage.entities.user.User" %>
<%@ page session="true" %>
<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifica Profilo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>
<div class="container">
    <h2>Modifica Profilo</h2>
    <p>Stai modificando il profilo di <strong><%= user.getUsername() %></strong></p>

    <form id="editProfileForm" action="editProfile" method="post">
        <input type="hidden" name="username" value="${user.username}">

        <label for="password">Nuova Password:</label>
        <input type="password" id="password" name="password" required>

        <label for="confirmPassword">Conferma Password:</label>
        <input type="password" id="confirmPassword" required>

        <p id="passwordError" style="color: red; display: none;">
            La password deve contenere almeno:
            <br>- 8 carattermi minimi
            <br>- Una lettera maiuscola
            <br>- Una lettera minuscola
            <br>- Un numero
            <br>- Un carattere speciale (!@#$%^&*)
        </p>

        <p id="confirmError" style="color: red; display: none;">
            Le password non coincidono.
        </p>

        <button type="submit" id="submitButton" disabled>Aggiorna Password</button>
    </form>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const passwordInput = document.getElementById("password");
            const confirmPasswordInput = document.getElementById("confirmPassword");
            const passwordError = document.getElementById("passwordError");
            const confirmError = document.getElementById("confirmError");
            const submitButton = document.getElementById("submitButton");

            function validatePassword() {
                const password = passwordInput.value;
                const confirmPassword = confirmPasswordInput.value;

                const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

                if (!passwordRegex.test(password)) {
                    passwordError.style.display = "block";
                    submitButton.disabled = true;
                } else {
                    passwordError.style.display = "none";
                }

                if (confirmPassword && password !== confirmPassword) {
                    confirmError.style.display = "block";
                    submitButton.disabled = true;
                } else {
                    confirmError.style.display = "none";
                }

                if (passwordRegex.test(password) && password === confirmPassword) {
                    submitButton.disabled = false;
                }
            }

            passwordInput.addEventListener("input", validatePassword);
            confirmPasswordInput.addEventListener("input", validatePassword);
        });
    </script>


    <a href="profile?username=<%= user.getUsername() %>">Torna al profilo</a>
</div>

<script>
    document.querySelector("form").addEventListener("submit", function(event) {
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("confirmPassword").value;
        if (password !== confirmPassword) {
            alert("Le password non coincidono!");
            event.preventDefault();
        }
    });
</script>
<jsp:include page="footer.jsp"/>
</body>
</html>
