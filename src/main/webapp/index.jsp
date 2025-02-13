<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>GameTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/index.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body data-bs-theme="dark">
    <jsp:include page="navbar.jsp"/>
    <main>
        <div class="container">
            <div id="gameCarousel" class="carousel slide pt-4 pb-4" data-bs-ride="carousel" data-bs-interval="5000">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#gameCarousel" data-bs-slide-to="0" class="active bg-white"
                            aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#gameCarousel" class=
                            "bg-white" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#gameCarousel" class=
                            "bg-white" data-bs-slide-to="2" aria-label="Slide 3"></button>
                    <button type="button" data-bs-target="#gameCarousel" class=
                            "bg-white" data-bs-slide-to="3" aria-label="Slide 4"></button>
                    <button type="button" data-bs-target="#gameCarousel" class=
                            "bg-white" data-bs-slide-to="4" aria-label="Slide 5"></button>
                    <button type="button" data-bs-target="#gameCarousel" class=
                            "bg-white" data-bs-slide-to="5" aria-label="Slide 6"></button>
                    <button type="button" data-bs-target="#gameCarousel" class=
                            "bg-white" data-bs-slide-to="6" aria-label="Slide 7"></button>
                    <button type="button" data-bs-target="#gameCarousel" class=
                            "bg-white" data-bs-slide-to="7" aria-label="Slide 8"></button>

                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="images/slide1.png" class="d-block w-100" alt="Slide 1">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/slide2.png" class="d-block w-100" alt="Slide 2">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/slide3.png" class="d-block w-100" alt="Slide 3">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/slide4.png" class="d-block w-100" alt="Slide 4">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/slide5.png" class="d-block w-100" alt="Slide 5">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/slide6.png" class="d-block w-100" alt="Slide 6">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/slide7.png" class="d-block w-100" alt="Slide 7">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/slide8.png" class="d-block w-100" alt="Slide 8">
                        <div class="carousel-caption d-none d-md-block">
                        </div>
                    </div>

                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#gameCarousel" data-bs-slide="prev">
                    <i class="bi bi-chevron-left fs-3 text-white"></i>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#gameCarousel"
                        data-bs-slide="next">
                    <i class="bi bi-chevron-right fs-3 text-white"></i>
                </button>
            </div>
            <div id="categories" class="row row-cols-2 row-cols-md-4 justify-content-center g-4 ps-3 pe-3">
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">Welcome</h3>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">General</h3>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">Help</h3>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">Guides</h3>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">Bugs</h3>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">Memes</h3>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">News</h3>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col categoryCol">
                    <div class="card categoryCard text-center justify-content-center">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body categoryCard-body justify-content-center">
                                <h3 class="card-title categoryCard-title m-0">Other</h3>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <div id="threads" class="row row-cols-1 row-cols-md-2 justify-content-center mt-4 g-4 p-3">
                <div class="col threadCol order-0 order-md-0">
                    <h3 class="threadTop mb-2">IMPORTANTE</h3>
                    <div class="card threadCard">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body threadCard-body">
                                <h5 class="card-title threadCard-title">Importante 1</h5>
                                <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col threadCol order-3 order-md-1">
                    <h3 class="threadTop mb-2">NOVITA'</h3>
                    <div class="card threadCard">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body threadCard-body ">
                                <h5 class="card-title threadCard-title">Novità 1</h5>
                                <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col threadCol order-1 order-md-2">
                    <div class="card threadCard">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body threadCard-body ">
                                <h5 class="card-title threadCard-title">Importante 2</h5>
                                <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col threadCol order-4 order-md-3">
                    <div class="card threadCard">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body threadCard-body ">
                                <h5 class="card-title threadCard-title">Novità 2</h5>
                                <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col threadCol order-2 order-md-4">
                    <div class="card threadCard">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body threadCard-body ">
                                <h5 class="card-title threadCard-title">Importante 3</h5>
                                <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col threadCol order-5 order-md-5">
                    <div class="card threadCard">
                        <a href="#" class="stretched-link text-decoration-none">
                            <div class="card-body threadCard-body ">
                                <h5 class="card-title threadCard-title">Novità 3</h5>
                                <p class="card-text threadCard-text text-truncate-3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam aspernatur consequatur deleniti eos esse fugiat harum id incidunt laborum minus necessitatibus, nobis odit omnis possimus quibusdam, reiciendis, similique tempora. Non?</p>
                                <p class="card-text threadCard-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

