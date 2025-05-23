<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.unisa.studenti.nc8.gametalk.storage.entities.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo Utente</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewProfile.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

    <%
        User user = (User) request.getAttribute("user");
    %>
</head>
<body class="bg-main" data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>

<main>

    <div class="container my-5">
        <div class="d-flex justify-content-center align-items-center flex-column mt-5">
            <h1 class="text-center display-3">
                <%= user != null ? user.getUsername() : "Username non disponibile" %><br>
                <strong>Ruolo:</strong>
                <%= user != null ? user.getRole().toString() : "Ruolo non disponibile" %>
            </h1>
        </div>



        <c:if test="${sessionScope.user.username ne user.username and sessionScope.user.role eq 'Moderator'}">
            <div class="text-center mt-4">
                <c:choose>
                    <c:when test="${user.banned}">
                        <form action="mod/banUser" method="post">
                            <input type="hidden" name="username" value="${user.username}" />
                            <input type="hidden" name="banned" value="false" />
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-person-check"></i> Sbanna Utente
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="mod/banUser" method="post">
                            <input type="hidden" name="username" value="${user.username}" />
                            <input type="hidden" name="banned" value="true" />
                            <button type="submit" class="btn btn-danger">
                                <i class="bi bi-person-x"></i> Banna Utente
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <div class="favourites mt-5">
            <h2 id="text" class="text-center" style="cursor: pointer;">THREADS</h2>
            <div class="row row-cols-1 row-cols-md-2 justify-content-center g-4 p-4" id="userThreads">
                <p>Caricamento thread...</p>
            </div>
        </div>
        <c:if test="${sessionScope.user.username eq user.username}">
            <div class="text-center mt-4">
                <form action="editProfile" method="get">
                    <input type="hidden" name="username" value="${user.username}" />
                    <button type="submit" class="btn">
                        <i class="bi"></i> Modifica il tuo profilo
                    </button>
                </form>

                <a href="${pageContext.request.contextPath}/logout" class="btn mt-1 p-0">Logout</a>
            </div>
        </c:if>
    </div>
</main>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {
        const username = "<%= user != null ? user.getUsername().replace("\"", "\\\"") : "" %>";
        const threadsContainer = $("#userThreads");

        if (username && username !== "Username non disponibile") {
            $.ajax({
                url: "getUserThreads",
                type: "GET",
                data: {
                    username: username,
                    page: 1
                },
                success: function (data) {
                    if (data.threads && data.threads.length > 0) {
                        threadsContainer.empty();
                        data.threads.forEach(thread => {
                            let threadHTML =
                                '<div class="col threadCol">' +
                                '<div class="card threadCard">' +
                                '<a href="${pageContext.request.contextPath}/thread?threadId=' + thread.id + '" class="stretched-link text-decoration-none">' +
                                '<div class="card-body threadCard-body">' +
                                '<h5 class="card-title threadCard-title">' + thread.title + '</h5>' +
                                '</div>' +
                                '</a>' +
                                '</div>' +
                                '</div>';
                            threadsContainer.append(threadHTML);
                        });
                    } else {
                        threadsContainer.html("<p>Nessun thread trovato.</p>");
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Errore nel caricamento dei thread:", error);
                    threadsContainer.html("<p>Errore nel recupero dei thread.</p>");
                }
            });
        } else {
            threadsContainer.html("<p>Username non disponibile.</p>");
        }
    });
</script>

<jsp:include page="footer.jsp"/>
</body>
</html>
