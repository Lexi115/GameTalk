<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>GameTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/thread.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>
<main>
    <div class="container mt-4">
        <div id="thread" class="card bg-card" data-id="${thread.id}">
            <div class="card-header row px-0 mx-0">
                <div class="col-6 text-start ps-4">${thread.username}</div>
                <div class="col-6 text-end pe-4">${thread.creationDate}</div>
            </div>
            <div class="card-body">
                ${thread.body}
            </div>
            <div class="card-footer d-flex justify-content-end fs-5">
                <button id="downVoteThread" class="btn btn-outline-danger fs-6 me-4 btn-vote" onclick="voteThread(${thread.id},-1)"><i class=" bi bi-caret-down-fill"></i></button>
                <div class="d-flex align-items-center" id="vote">${thread.votes}</div>
                <button id="upVoteThread" class="btn btn-outline-success fs-6 ms-4 btn-vote" onclick="voteThread(${thread.id},1)"><i class="bi bi-caret-up-fill"></i></button>
            </div>
        </div>
    </div>
    <div class="container mt-4" id="comments" data-bs-theme="light">
        <form action="#" method="post">
            <div class="row g-0">
                <div class="col-md-11">
                    <textarea type="text" name="comment" id="comment" class="form-control"></textarea>
                </div>
                <div class="col-md-1 d-flex">
                    <button class="btn btn-lg btn-secondary w-100"><i class="bi bi-caret-right-fill"></i></button>
                </div>
            </div>
        </form>
        <div class="fs-3 mb-3">Comments</div>
        <div class="container" id="commentsArea">

        </div>
        <nav id="pagination" data-page="1">
            <ul class="pagination pagination-lg justify-content-center ">
                <li class="page-item">
                    <a id="prevPage" class="page-link disabled bg-card" href="#commentsArea">Previous</a>
                </li>
                <li class="page-item">
                    <a id="nextPage" class="page-link bg-card" href="#commentsArea">Next</a>
                </li>
            </ul>
        </nav>
    </div>
</main>
<jsp:include page="footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
<script src="js/thread.js"></script>
</body>
</html>
</html>