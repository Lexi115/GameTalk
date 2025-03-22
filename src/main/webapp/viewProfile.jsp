<%@ page import="it.unisa.studenti.nc8.gametalk.storage.entities.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo Utente</title>

    <!-- Link per gli stili CSS -->
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/viewProfile.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

    <%
        User user = (User) request.getAttribute("user");
        String username = (user != null) ? user.getUsername() : "Username non disponibile";
        String role = (user != null) ? user.getRole().toString() : "Ruolo non disponibile";
    %>
</head>
<body class="bg-main" data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>

<main>
    <div class="container my-5">
        <!-- Sezione del profilo utente -->
        <div class="d-flex justify-content-center align-items-center flex-column mt-5">
            <h1 class="text-center display-3">
                <%= user != null ? user.getUsername() : "Username non disponibile" %><br>
                <strong>Ruolo:</strong>
                <%= user != null ? user.getRole().toString() : "Ruolo non disponibile" %>
            </h1>
        </div>

        <!-- Sezione dei thread dell'utente -->
        <div class="favourites mt-5">
            <h2 id="text" class="text-center" style="cursor: pointer;">I TUOI THREAD</h2>
            <div class="row row-cols-1 row-cols-md-2 justify-content-center g-4 p-4" id="userThreads">
                <p>Caricamento thread...</p>
            </div>
        </div>
    </div>
</main>

<!-- Script JS per caricare i thread -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const username = "<%= user != null ? user.getUsername() : "" %>";
        const threadsContainer = document.getElementById("userThreads");

        // Verifica se il nome utente è disponibile
        if (username && username !== "Username non disponibile") {
            fetch(`/getUserThreads?username=${username}`)
                .then(response => response.json())
                .then(data => {
                    if (data.threads && data.threads.length > 0) {
                        threadsContainer.innerHTML = "";
                        data.threads.forEach(thread => {
                            const threadHTML = `
                                    <div class="col threadCol">
                                        <div class="card threadCard">
                                            <a href="thread.jsp?id=${thread.id}" class="stretched-link text-decoration-none">
                                                <div class="card-body threadCard-body">
                                                    <h5 class="card-title threadCard-title">${thread.title}</h5>
                                                    <p class="card-text threadCard-text text-truncate">${thread.content}</p>
                                                    <p class="card-text threadCard-text"><small class="text-body-secondary">Ultimo aggiornamento: ${thread.lastUpdated}</small></p>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                `;
                            threadsContainer.innerHTML += threadHTML;
                        });
                    } else {
                        threadsContainer.innerHTML = "<p>Nessun thread trovato.</p>";
                    }
                })
                .catch(error => {
                    console.error("Errore nel caricamento dei thread:", error);
                    threadsContainer.innerHTML = "<p>Errore nel recupero dei thread.</p>";
                });
        } else {
            threadsContainer.innerHTML = "<p>Username non disponibile.</p>";
        }
    });
</script>

</body>
</html>
