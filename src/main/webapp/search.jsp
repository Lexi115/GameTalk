<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>GameTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/search.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark">
    <jsp:include page="navbar.jsp"/>
    <main>
        <div class="container mt-5 mb-3 animate-2 a-delay-1 fadeIn">
            <div class="bg-card-light card p-3">
                <div class="card-body">
                    <form id="searchForm" action="searchThread" method="get">
                        <div class="row my-2">
                            <div class="p-0 form-floating input-group">
                                <input type="text" class="form-control bg-card" id="searchbar" name="query" aria-describedby="search" placeholder="">
                                <button class="btn btn-secondary rounded-end px-4" type="submit" id="search"><i class="bi bi-search"></i></button>
                                <label for="searchbar" class="label-bg-none z-5">ricerca</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 p-0 form-floating">
                                <select name="category" class="form-select bg-card" id="category" data-default-value="${category}">
                                    <option value="">Any</option>
                                    <option value="General">General</option>
                                    <option value="Welcome">Welcome</option>
                                    <option value="Help">Help</option>
                                    <option value="Bugs">Bugs</option>
                                    <option value="Guides">Guides</option>
                                    <option value="Memes">Memes</option>
                                    <option value="Announcements">Announcements</option>
                                </select>
                                <label for="category" class="label-bg-none">category</label>
                            </div>
                            <div class="col-md-3 p-0 form-floating">
                                <select name="order" class="form-select bg-card" id="order" data-default-value="${order}">
                                    <option selected value="Best">Best</option>
                                    <option value="Newest">Newest</option>
                                    <option value="Oldest">Oldest</option>
                                </select>
                                <label for="order" class="label-bg-none">order</label>
                            </div>
                            <div class="col-md-3 p-0 form-floating">
                                <input name="dateFrom" type="date" class="form-control bg-card" id="dateFrom" value="${dateFrom}">
                                <label for="dateFrom" class="label-bg-none">select dateFrom</label>
                            </div>
                            <div class="col-md-3 p-0 form-floating">
                                <input name="dateTo" type="date" class="form-control bg-card" id="dateTo" value="${dateTo}">
                                <label for="dateTo" class="label-bg-none">select dateTo</label>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="container mt-5" id="threadsContainer">
            <c:forEach var="thread" items="${requestScope.threads}">
            <div class="row">
                <div class="card bg-card mb-3" id="thread-${thread.id}">
                    <div class="card-header row">
                        <div class="fs-4 col-7 col-md-8 fw-bolder">${thread.title}</div>
                        <div class="col-5 col-md-4 fs-5 d-flex justify-content-end px-0">
                            <div class="d-flex align-items-center">${thread.votes}</div>
                        </div>
                    </div>
                    <a class="text-decoration-none" href="thread?threadId=${thread.id}">
                        <div class="card-body text-white">
                            <p class="card-text text-truncate-3">${thread.body}</p>
                        </div>
                        <div class="card-footer text-body-secondary row">
                            <div class="col-6 text-start ps-4">${thread.username}</div>
                            <div class="col-6 text-end pe-4">${thread.creationDate}</div>
                        </div>
                    </a>
                </div>
            </div>
            </c:forEach>
        </div>
        <div id="pagination">
            <nav>
                <ul class="pagination pagination-lg justify-content-center">
                    <c:if test="${page == 1}">
                        <li class="page-item disabled">
                    </c:if>
                    <c:if test="${page != 1}">
                        <li class="page-item">
                    </c:if>
                        <c:set var="result" value="${fn:substringAfter(pageContext.request.queryString, '&')}" />
                        <a class="page-link bg-card" href="searchThread?page=${page - 1}&${result}">Previous</a>
                    </li>
                        <li class="page-item">
                            <span class="page-link">${page}</span>
                        </li>
                    <c:if test="${page >= maxPages}">
                        <li class="page-item disabled">
                    </c:if>
                    <c:if test="${page < maxPages}">
                        <li class="page-item">
                    </c:if>
                        <a class="page-link bg-card" href="searchThread?page=${page + 1}&${result}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
    <script>
        $("#category").ready(function (){$("#category").val($("#category").data("defaultValue"));});
        $("#order").ready(function (){$("#order").val($("#order").data("defaultValue"));});
    </script>
</body>
</html>

<!--
<ul class="list-group mb-5" id="threadsList">
    <li class="list-group-item">
        <a href="#" class="text-decoration-none text-dark">
            <div class="row p-2 mt-2">
                <div class="col-md-4">
                    <img src="..." class="img-fluid rounded-start" alt="...">
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                        <p class="card-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                    </div>
                </div>
            </div>
        </a>
    </li>
</ul>
-->