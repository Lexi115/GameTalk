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
    <div class="container mt-4">
        <div id="thread" class="card bg-card">
            <div class="card-header row px-0 mx-0">
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
            <div class="card-footer d-flex justify-content-end fs-5">
                <button class="btn btn-outline-danger fs-6 me-4"><i class=" bi bi-caret-down-fill"></i></button>
                <div class="d-flex align-items-center" id="votes">255</div>
                <button class="btn btn-success fs-6 ms-4"><i class="bi bi-caret-up-fill"></i></button>
            </div>
        </div>
    </div>
    <div class="container mt-4" id="comments" data-bs-theme="light">
        <div class="fs-3 mb-3">Comments</div>
        <%for (int i = 0; i < 10; i++) {%>
        <div class="card bg-card mb-4 text-white">
            <div class="card-header row">
                <div class="fs-4 col-7 col-md-8 fw-bolder">Featured</div>
                <div class="col-5 col-md-4 fs-5 d-flex justify-content-end px-0">
                    <button class="btn btn-outline-danger btn-sm fs-6 me-1 me-md-4"><i class=" bi bi-caret-down-fill"></i></button>
                    <div class="d-flex align-items-center" id="votes">255</div>
                    <button class="btn btn-success btn-sm fs-6 ms-1 ms-md-4"><i class="bi bi-caret-up-fill"></i></button>
                </div>
            </div>
            <div class="card-body">
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias eum incidunt inventore neque nulla perspiciatis quas quidem repellat sit vitae. Architecto deleniti excepturi harum ipsa ipsum, nemo omnis recusandae temporibus.</p>
            </div>
            <div class="card-footer">date</div>
        </div>
        <%}%>
    </div>
</main>
<jsp:include page="footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
</html>