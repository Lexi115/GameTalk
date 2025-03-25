
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Utente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewProfile.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body class="bg-main" data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>
<main>
    <div class="container d-flex justify-content-center">
        <img class=" mt-5 md-3"
                 style="height: 350px"
                 src="${pageContext.request.contextPath}/images/error.png">
    </div>

    <div class="container d-flex justify-content-center">
        <h1 style="font-size: 100px"> Error 404 </h1>
    </div>

    <div class="container d-flex justify-content-center">
        <h2> Page not found </h2>
    </div>
</main>
</body>
</html>
