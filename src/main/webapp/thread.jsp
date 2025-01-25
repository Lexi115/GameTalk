<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>GameTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark">
<jsp:include page="navbar.jsp"/>
<main>
    <div class="container-fluid mt-4 row" data-bs-theme="light">
        <div class="col-1 d-flex align-items-end flex-column fs-3">
            <button class="btn btn-success"><i class="bi bi-caret-up-fill"></i></button>
            <div>255</div>
            <button class="btn btn-outline-danger"><i class="bi bi-caret-down-fill"></i></button>
        </div>
        <div id="thread" class="card col-10">
            <div class="card-header row">
                <div class="col-6 text-start ps-4">username</div>
                <div class="col-6 text-end pe-4">date</div>
            </div>
            <div class="card-body">
                <h5 class="card-title">Special title treatment</h5>
                <img class="img-fluid" src="images/solo-leveling.jpg">
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam corporis delectus eveniet in iusto molestiae mollitia nesciunt quis, recusandae sit tempora totam ullam velit veniam vero? Consequatur nostrum perferendis possimus!</p>
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam corporis delectus eveniet in iusto molestiae mollitia nesciunt quis, recusandae sit tempora totam ullam velit veniam vero? Consequatur nostrum perferendis possimus!</p>
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam corporis delectus eveniet in iusto molestiae mollitia nesciunt quis, recusandae sit tempora totam ullam velit veniam vero? Consequatur nostrum perferendis possimus!</p>
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam corporis delectus eveniet in iusto molestiae mollitia nesciunt quis, recusandae sit tempora totam ullam velit veniam vero? Consequatur nostrum perferendis possimus!</p>
            </div>
        </div>
    </div>
    <div class=" offset-1 fs-3">Comments</div>
    <div class="container " id="comments" data-bs-theme="light">
        <%for (int i = 0; i < 10; i++) {%>
        <div class="row mb-4">
            <div class="col-1 d-flex justify-content-center align-items-end flex-column fs-3">
                <button class="btn btn-success"><i class="bi bi-caret-up-fill"></i></button>
                <div>255</div>
                <button class="btn btn-outline-danger"><i class="bi bi-caret-down-fill"></i></button>
            </div>
            <div class="col-11 card">
                <h5 class="card-header">Featured</h5>
                <div class="card-body">
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias eum incidunt inventore neque nulla perspiciatis quas quidem repellat sit vitae. Architecto deleniti excepturi harum ipsa ipsum, nemo omnis recusandae temporibus.</p>
                </div>
                <div class="card-footer">date</div>
            </div>
        </div>
        <%}%>
    </div>
</main>
<jsp:include page="footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
</html>