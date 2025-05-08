<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>GameTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-bs5.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/treadEditor.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark">
    <jsp:include page="navbar.jsp"/>
    <main>
        <div class="container mt-3">
            <div class="card bg-card ">
                <div class="card-header">Crea un Thread</div>
                <div class="card-body">
                    <div class="row d-flex justify-content-around py-2 px-4">
                        <div class="col-md-5 form-floating">
                            <input class="form-control" type="text" name="title" id="title" placeholder="">
                            <label for="title" class="label-bg-none ms-2">Titolo</label>
                        </div>
                        <div class="col-md-5 form-floating">
                            <select class="form-control" name="category" id="category">
                                <option value="General" selected>General</option>
                                <option value="Welcome">Welcome</option>
                                <option value="Help">Help</option>
                                <option value="Bugs">Bugs</option>
                                <option value="Guides">Guides</option>
                                <option value="Memes">Memes</option>
                                <option value="Announcements">Announcements</option>
                            </select>
                            <label for="category" class="label-bg-none ms-2">Categoria</label>
                        </div>
                    </div>
                    <div id="summernote" class="col-12"></div>
                </div>
                <div class="card-footer d-flex justify-content-end">
                    <button id="sendButton" class="btn btn-success" type="button">Crea</button>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-bs5.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/threadEditor.js"></script>
    <script>
        $("#sendButton").on("click", function() {
            var title = $("#title").val();
            var category = $("#category").val();
            var body = $("#summernote").summernote('code');
            $.post("addThread",
                {
                    title:title,
                    category:category,
                    body: body
                },
            function (response){
                window.location.href = "thread?threadId="+response.threadId;
            });
        });
    </script>
</body>
</html>