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
        <div class="container card py-3 mt-5 mb-3 animate-2 a-delay-1 fadeIn">
            <div class="row">
                <div class="col-10 offset-1 bg-dark">
                    <form id="searchForm">
                        <div class="row my-2">
                            <div class="p-0 form-floating input-group">
                                <input type="text" class="form-control" id="searchbar" aria-describedby="search">
                                <button class="btn btn-secondary rounded-end px-4 " type="button" id="search"><i class="bi bi-search"></i></button>
                                <label for="searchbar">ricerca</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-3 p-0 form-floating">
                                <select name="category" class="form-select" id="category">
                                    <option selected>null</option>
                                    <option value="1">One</option>
                                    <option value="2">Two</option>
                                    <option value="3">Three</option>
                                </select>
                                <label for="category">category</label>
                            </div>
                            <div class="col-3 p-0 form-floating">
                                <select name="order" class="form-select" id="order">
                                    <option selected>null</option>
                                    <option value="1">One</option>
                                    <option value="2">Two</option>
                                    <option value="3">Three</option>
                                </select>
                                <label for="order">order</label>
                            </div>
                            <div class="col-3 p-0 form-floating">
                                <input name="dateFrom" type="date" class="form-control" id="dateFrom">
                                <label for="dateFrom">select dateFrom</label>
                            </div>
                            <div class="col-3 p-0 form-floating">
                                <input name="dateTo" type="date" class="form-control" id="dateTo">
                                <label for="dateTo">select dateTo</label>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="container mt-5" id="threadsContainer" data-bs-theme="light">
            <%
                for (int i = 0; i < 10; i++) {
            %>
            <a class="text-decoration-none" href="#">
                <div class="card mb-3">
                    <div class="card-header">
                        <span class="fs-4 fw-bolder">title</span>
                    </div>
                    <div class="card-body">
                        <p class="card-text text-truncate-3">With supporting text below as a natural lead-in to additional content.</p>
                    </div>
                    <div class="card-footer text-body-secondary row">
                        <div class="col-6 text-start ps-4">date</div>
                        <div class="col-6 text-end pe-4">up votes- down votes</div>
                    </div>
                </div>
            </a>
            <%}%>
        </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
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