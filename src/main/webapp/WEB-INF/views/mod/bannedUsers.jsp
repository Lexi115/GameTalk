
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Utenti bloccati</title>
    <link rel="stylesheet" href="../../../css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body class="bg-main" data-bs-theme="dark">
<jsp:include page="../../../navbar.jsp"/>
<main>
    <div class="container">
        <div class="row row-cols-1 row-cols-md-2 justify-content-center g-4 p-4" id="bannedUsers">
            <p>Caricamento utenti...</p>
        </div>
    </div>
</main>

<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script>

    const list = $("#bannedUsers");
    const contextPath = window.location.pathname.split('/')[1];
    const apiUrl = "/" + contextPath + "/mod/getBannedUsers";

    $(document).ready( function (){
        $.ajax({
            url: apiUrl,
            type: "GET",
            data:{
                page: 1,
            },
            success: function (data) {
                if(data.bannedUsers && data.bannedUsers.length > 0){
                    list.empty();
                    data.bannedUsers.forEach(user => {
                        let userHTML =
                            '<div class="card bg-dark text-light p-3">' +
                            '<div class="card-body">' +
                            '<h5 class="card-title">' + user.username + '</h5>' +
                            ' <a href="${pageContext.request.contextPath}/" style="text-decoration: underline; color: inherit;">Unban</a>'
                            '</div>' +
                            '</div>';

                        list.append(userHTML);
                    });
                } else {
                    list.html("<p>Nessun utente trovato</p>");
                }
            },
            error: function (xhr, status, error) {
                console.error("Errore nel caricamento dei thread:", error);
                list.html("<p>Errore nel recupero degli utenti.</p>");
            }
        })
    });

</script>

</body>
</html>
