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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editProfile.css">
</head>
<body data-bs-theme="dark">

<jsp:include page="navbar.jsp"/>

<div class="container edit-profile-card p-4 rounded-4 mt-4">
    <h2 class="mb-3">Modifica Profilo</h2>
    <p>Stai modificando il profilo di <strong><%= user.getUsername() %></strong></p>

    <form id="editProfileForm" action="editProfile" method="post" novalidate>
        <input type="hidden" name="username" value="${user.username}">

        <div class="mb-3">
            <label for="password" class="form-label">Nuova Password:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Conferma Password:</label>
            <input type="password" id="confirmPassword" class="form-control" required>
        </div>

        <div id="passwordError" class="text-danger mb-2" style="display: none;">
            La password deve contenere almeno:
            <ul class="mb-0">
                <li>Minimo 8 caratteri</li>
                <li>1 lettera maiuscola</li>
                <li>1 lettera minuscola</li>
                <li>1 numero</li>
                <li>1 carattere speciale (!@#$%^&*)</li>
            </ul>
        </div>

        <div id="confirmError" class="text-danger mb-3" style="display: none;">
            Le password non coincidono.
        </div>

        <button type="submit" id="submitButton" class="btn btn-primary" style="background-color: #22236D; border: none"
                disabled>Aggiorna
            Password</button>
    </form>

    <a class="btn mt-4" href="profile?username=<%= user.getUsername() %>">← Torna al profilo</a>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const password = document.getElementById("password");
        const confirmPassword = document.getElementById("confirmPassword");
        const passwordError = document.getElementById("passwordError");
        const confirmError = document.getElementById("confirmError");
        const submitButton = document.getElementById("submitButton");

        function validatePasswordRules(pw) {
            return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/.test(pw);
        }

        function updateValidation() {
            const pw = password.value;
            const cpw = confirmPassword.value;

            const pwValid = validatePasswordRules(pw);
            const match = pw === cpw;

            passwordError.style.display = pwValid ? "none" : "block";
            confirmError.style.display = (pwValid && !match) ? "block" : "none";

            submitButton.disabled = !(pwValid && match);
        }

        password.addEventListener("input", updateValidation);
        confirmPassword.addEventListener("input", updateValidation);
    });
</script>

<jsp:include page="footer.jsp"/>
</body>
</html>
