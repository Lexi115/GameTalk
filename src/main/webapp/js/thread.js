function voteThread(id, vote){
    var currentVotes = parseInt($("#vote").html());

    $.ajax({
        url: 'voteThread',  // The URL to send the request to
        type: 'POST',              // HTTP method
        data: {                    // Data to send in the request
            threadId: id,
            vote: vote
        },
        success: function(response) {  // Callback function on success
            if (vote == 1){
                $("#upVoteThread").removeClass("btn-outline-success");
                $("#upVoteThread").addClass("btn-success popAnimation");
            }
            else if (vote == -1){
                $("#downVoteThread").removeClass("btn-outline-danger");
                $("#downVoteThread").addClass("btn-danger popAnimation");
            }
            $("#vote").html(currentVotes + vote);
            $("#upVoteThread").removeAttr("onclick");
            $("#downVoteThread").removeAttr("onclick");
        },
        error: function(xhr, status, error) {  // Callback function on error
            console.log('Error:', error);
        }
    });
}

function voteComment(id, vote){
    console.log("doesn't work for now: "+id+" "+vote);
}

function createComment(comment) {
    //create all elements
    let card = $("<div></div>",{class: "card bg-card mb-4 text-white"});
    let cardHeader = $("<div></div>",{class: "card-header row"});
    let username = $("<div></div>",{class: "fs-4 col-7 col-md-8 fw-bolder"});
    let votesdiv = $("<div></div>",{class: "col-5 col-md-4 fs-5 d-flex justify-content-end px-0"});
    let downvote = $("<button></button>", {class: "btn btn-outline-danger btn-sm fs-6 me-1 me-md-4"});
    let vote = $("<div></div>", {class: "d-flex align-items-center", id: "vote-"+comment.id});
    let upvote = $("<button></button>", {class: "btn btn-outline-success btn-sm fs-6 ms-1 ms-md-4"});
    let cardBody = $("<div></div>", {class: "card-body"});
    let body = $("<p></p>", {class: "card-text"});
    let cardFooter = $("<div></div>", {class: "card-footer"});

    //insert correct data
    username.html(comment.username);
    vote.html(comment.votes);
    body.html(comment.body);
    cardFooter.html(comment.creationDate);

    //insert icons
    downvote.html("<i class=' bi bi-caret-down-fill'></i>");
    upvote.html("<i class=' bi bi-caret-up-fill'></i>")

    //add eventListener
    downvote.on("click", function (){voteComment(comment.id,-1)});
    upvote.on("click", function (){voteComment(comment.id,1)});

    //form the element
    votesdiv.append(downvote);
    votesdiv.append(vote);
    votesdiv.append(upvote);

    cardHeader.append(username);
    cardHeader.append(votesdiv);

    cardBody.append(body);

    card.append(cardHeader);
    card.append(cardBody);
    card.append(cardFooter);

    return card;
}

var firstTime = true;

function getComment(id,page){

    $.ajax({
        url: 'getThreadComments',  // The URL to send the request to
        type: 'GET',              // HTTP method
        data: {                    // Data to send in the request
            threadId: id,
            page: page
        },
        success: function(response) {  // Callback function on success
            //console.log(response);
            $("#commentsArea").empty();

            let comments = response.comments;

            for (var i = 0; i < comments.length; i++) {
                $("#commentsArea").append(createComment(comments[i]));
            }

            if (response.totalPages > 1 && firstTime) {
                firstTime = false;
                paginationInit(response.threadId, response.totalPages);
            }
            else if(firstTime)
                $("#pagination").remove();
        },
        error: function(xhr, status, error) {  // Callback function on error
            console.log('Error:', error);
        }
    });
}

$("#commentsArea").ready(function () {getComment($("#thread").data("id"), 1)});

function nextPage(id, page, totalPages) {
    //remove old eventListener
    $("#nextPage").off("click");
    $("#prevPage").off("click");

    //unlock prev button
    $("#prevPage").removeClass("disabled");
    $("#prevPage").on("click", function (){prevPage(id,page-1,totalPages)});

    getComment(id,page);

    //lock next button if you load the last page otherwise set the jump to the next page
    if (page <= totalPages){
        $("#nextPage").addClass("disabled");
        $("#nextPage").off("click");
    }else
        $("#nextPage").on("click", function (){nextPage(id,page+1,totalPages)});
}

function prevPage(id, page, totalPages){
    //remove old eventListener
    $("#nextPage").off("click");
    $("#prevPage").off("click");

    //unlock next button
    $("#nextPage").removeClass("disabled");
    $("#nextPage").on("click", function (){nextPage(id,page+1,totalPages)});

    getComment(id,page);

    //lock prev button if you load the first page otherwise set the jump
    if (page == 1){
        $("#prevPage").addClass("disabled");
        $("#prevPage").off("click");
    }else
        $("#prevPage").on("click", function (){prevPage(id,page-1,totalPages)});
}

function paginationInit(id, totalPages) {
    $("#prevPage").addClass("disabled");
    $("#nextPage").on("click", function (){nextPage(id,2,totalPages)});
}
