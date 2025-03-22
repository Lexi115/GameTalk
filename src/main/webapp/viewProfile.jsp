<%@ page import="it.unisa.studenti.nc8.gametalk.storage.entities.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Utente</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/viewProfile.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>

<%
    User user = (User) request.getAttribute("user");
    String username = (user != null) ? user.getUsername() : "Username non disponibile";
    String role = (user != null) ? user.getRole().toString() : "Ruolo non disponibile";
%>

<body class="bg-main" data-bs-theme="dark">
    <jsp:include page="navbar.jsp"/>
    <main>
        <div class="container">
            <div class="d-flex m-4">
                <div class="descrizione m-5" style="margin-top: 160px; font-size: 200px">
                    <h1>
                        <%= username %><br>
                        <strong>Ruolo:</strong> <%= role %>
                    </h1>
                </div>
            </div>


            <div class="favourites m-5">
                <h2 id = "text" style="text-align: center; cursor: pointer" onclick="change()">I TUOI THREAD</h2>
                <div class=" row row-cols-1 row-cols-md-2 justify-content-center g-4 p-4">
                    <div class="col threadCol">
                        <div class="card threadCard">
                            <a href="#" class="stretched-link text-decoration-none">
                                <div class="card-body threadCard-body">
                                    <h5 class="card-title threadCard-title">Thread 1</h5>
                                    <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                    <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                                </div>
                            </a>
                        </div>
                    </div>

                    <div class="col threadCol">
                        <div class="card threadCard">
                            <a href="#" class="stretched-link text-decoration-none">
                                <div class="card-body threadCard-body">
                                    <h5 class="card-title threadCard-title">Thread 2</h5>
                                    <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                    <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                                </div>
                            </a>
                        </div>
                    </div>

                    <div class="col threadCol">
                        <div class="card threadCard">
                            <a href="#" class="stretched-link text-decoration-none">
                                <div class="card-body threadCard-body">
                                    <h5 class="card-title threadCard-title">Thread 3</h5>
                                    <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                    <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                                </div>
                            </a>
                        </div>
                    </div>

                    <div class="col threadCol">
                        <div class="card threadCard">
                            <a href="#" class="stretched-link text-decoration-none">
                                <div class="card-body threadCard-body">
                                    <h5 class="card-title threadCard-title">Thread 4</h5>
                                    <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                    <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                                </div>
                            </a>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </main>
</body>
</html>
