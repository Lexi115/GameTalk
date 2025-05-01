<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>GameTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/thread.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>
<main>
    <div class="container mt-4">
        <div id="thread" class="card bg-card" data-id="${thread.id}" data-presonalvote="${personalVote}">
            <div class="card-header row px-0 mx-0">
                <div class="col-6 text-start ps-3">${thread.title}</div>
                <div class="col-6 text-end pe-4">${thread.creationDate}</div>
            </div>
            <div class="card-body">
                ${thread.body}
            </div>

            <div class="card-footer fs-5">
                <div class="row">
                    <div class="col-6">
                        <a href="${pageContext.request.contextPath}/profile?username=${thread.username}"
                           class="btn p-0">
                            <b>${thread.username}</b>
                        </a>
                    </div>
                    <div class="col-6 d-flex justify-content-end">
                        <c:if test="${not empty sessionScope.user}">
                            <c:if test="${sessionScope.isModerator or thread.username == sessionScope.user.username}">
                                <a class="btn btn-primary" href="editThread?id=${thread.id}"><i class="bi bi-pencil"></i></a>
                                <form action="removeThread" method="post">
                                    <input type="hidden" name="threadId" value="${thread.id}">
                                    <button type="submit" class="btn btn-danger fs-6 me-4"><i class="bi bi-trash"></i></button>
                                </form>
                            </c:if>
                            <button id="downVoteThread" class="btn btn-outline-danger fs-6 me-4 btn-vote"><i class=" bi bi-caret-down-fill"></i></button>
                            <div class="d-flex align-items-center" id="vote">${thread.votes}</div>
                            <button id="upVoteThread" class="btn btn-outline-success fs-6 ms-4 btn-vote"><i class="bi bi-caret-up-fill"></i></button>
                        </c:if>
                        <c:if test="${empty sessionScope.user}">
                            <div class="d-flex align-items-center me-5" id="vote">${thread.votes}</div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container mt-4" id="comments" data-bs-theme="light">
        <c:if test="${not empty sessionScope.user}">
            <div class="row g-0">
                <div class="col-md-11">
                    <textarea name="comment" id="comment" class="form-control"></textarea>
                </div>
                <div class="col-md-1 d-flex">
                    <button onclick="newComment()" class="btn btn-lg btn-secondary w-100"><i class="bi bi-caret-right-fill"></i></button>
                </div>
            </div>
        </c:if>

        <div class="fs-3 mb-3">Comments</div>
        <div class="container" id="commentsArea">

        </div>
        <nav id="pagination" data-bs-theme="dark">
            <ul class="pagination pagination-lg justify-content-center ">
                <li class="page-item">
                    <a id="prevPage" class="page-link disabled bg-card" href="#commentsArea">Previous</a>
                </li>
                <li class="page-item">
                    <span class="page-link" id="pageNumber">1</span>
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
<script src="${pageContext.request.contextPath}/js/thread.js"></script>
<c:if test="${not empty sessionScope.user}">
    <script>USERLOGGED = true; USERNAME = "${sessionScope.user.username}"; MODERATOR = (${sessionScope.isModerator})?true :false</script>
</c:if>
</body>
</html>
</html>