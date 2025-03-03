
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Utenti bloccati</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body class="bg-main" data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>
<main>
    <div class="container">
        <div class="d-flex m-4" >
            <ul id="lista">
                <li>Testo 1 <button onclick="removeItem(this)">Rimuovi</button></li>
                <li>Testo 2 <button onclick="removeItem(this)">Rimuovi</button></li>
                <li>Testo 3 <button onclick="removeItem(this)">Rimuovi</button></li>
                <li>Testo 4 <button onclick="removeItem(this)">Rimuovi</button></li>
            </ul>
        </div>
    </div>
</main>
</body>
</html>
