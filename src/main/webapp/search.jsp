<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html class="h-100">
<head>
    <title>GameTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/search.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark" class="d-flex flex-column h-100 bg-main">
    <div id="bg-balls">
        <span id="bg-ball1"></span>
        <span id="bg-ball2"></span>
    </div>
    <jsp:include page="navbar.jsp"/>
    <main class="flex-shrink-0">
        <div class="container card py-3 mt-5">
            <div class="row">
                <div class="col-10 offset-1 bg-dark">
                    <form id="searchForm">
                        <div class="row my-2 form-floating">
                            <input type="text" class="form-control" id="searchbar" placeholder="name@example.com" value="">
                            <label for="searchbar">ricerca</label>
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
    </main>
    <jsp:include page="footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>