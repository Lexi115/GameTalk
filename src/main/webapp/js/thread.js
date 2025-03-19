var USERLOGGED = false;
function voteThread(id, vote){
    var currentVotes = parseInt($("#vote").html());

    $.ajax({
        url: 'voteThread',
        type: 'POST',
        data: {
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
    var currentVotes = parseInt($("#vote-"+id).html());
    var threadId = $("#thread").data("id");
    $.ajax({
        url: 'voteComment',
        type: 'POST',
        data: {
            commentId: id,
            threadId: threadId,
            vote: vote
        },
        success: function(response) {  // Callback function on success
            if (vote == 1){
                $("#upVote-"+id).removeClass("btn-outline-success");
                $("#upVote-"+id).addClass("btn-success popAnimation");
            }
            else if (vote == -1){
                $("#downVote-"+id).removeClass("btn-outline-danger");
                $("#downVote-"+id).addClass("btn-danger popAnimation");
            }
            $("#vote-"+id).html(currentVotes + vote);
            $("#upVote-"+id).removeAttr("onclick");
            $("#downVote-"+id).removeAttr("onclick");
        },
        error: function(xhr, status, error) {  // Callback function on error
            console.log('Error:', error);
        }
    });
}

function createComment(comment) {
    //create all elements
    let card = $("<div></div>",{class: "card bg-card mb-4 text-white"});
    let cardHeader = $("<div></div>",{class: "card-header row"});
    let username = $("<div></div>",{class: "fs-4 col-7 col-md-8 fw-bolder"});
    let votesdiv = $("<div></div>",{class: "col-5 col-md-4 fs-5 d-flex justify-content-end px-0"});
    let vote = $("<div></div>", {class: "d-flex align-items-center "+ (USERLOGGED)?"me-4":"", id: "vote-"+comment.id});
    let cardBody = $("<div></div>", {class: "card-body"});
    let body = $("<p></p>", {class: "card-text"});
    let cardFooter = $("<div></div>", {class: "card-footer"});
    let downvote = null;
    let upvote = null;

    //insert correct data
    username.html(comment.author);
    vote.html(comment.votes);
    body.html(comment.body);
    cardFooter.html(comment.creationDate);

    //insert buttons
    if (USERLOGGED){
        downvote = $("<button></button>", {class: "btn btn-outline-danger btn-sm fs-6 me-1 me-md-4", id:"downVote-"+comment.id});
        upvote = $("<button></button>", {class: "btn btn-outline-success btn-sm fs-6 ms-1 ms-md-4", id: "upVote-"+comment.id});
        downvote.html("<i class=' bi bi-caret-down-fill'></i>");
        upvote.html("<i class=' bi bi-caret-up-fill'></i>");

        //add eventListener
        downvote.on("click", function (){voteComment(comment.id,-1)});
        upvote.on("click", function (){voteComment(comment.id,1)});
    }


    //form the element
    if(USERLOGGED)
        votesdiv.append(downvote);
    votesdiv.append(vote);
    if (USERLOGGED)
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
        url: 'getThreadComments',
        type: 'GET',
        data: {
            threadId: id,
            page: page
        },
        success: function(response) {  // Callback function on success
            //console.log(response);
            $("#commentsArea").empty();

            if (response.totalPages<=1)
                $("#pagination").addClass("d-none");
            else
                $("#pagination").removeClass("d-none");

            $("#prevPage").addClass("disabled");
            $("#prevPage").off("click");
            $("#nextPage").addClass("disabled");
            $("#nextPage").off("click");

            if (page > 1){
                $("#prevPage").removeClass("disabled");
                $("#prevPage").on("click", function (){getComment(id,page-1)});
            }
            if (page < response.totalPages){
                $("#nextPage").removeClass("disabled");
                $("#nextPage").on("click", function (){getComment(id,page+1)});
            }

            $("#pageNumber").html(page);

            let comments = response.comments;

            for (var i = 0; i < comments.length; i++) {
                $("#commentsArea").append(createComment(comments[i]));
            }
        },
        error: function(xhr, status, error) {  // Callback function on error
            console.log('Error:', error);
        }
    });
}

$("#commentsArea").ready(function () {getComment($("#thread").data("id"), 1)});


function newComment(){
    let body = $("#comment").val();
    let id = $("#thread").data("id");

    $.ajax({
        url: 'addComment',
        type: 'POST',
        data: {
            threadId: id,
            body: body
        },
        success: function(response) {  // Callback function on success
            if (response.status == "OK"){
                getComment(id,1);
            }
        },
        error: function(xhr, status, error) {  // Callback function on error
            console.log('Error:', error);
        }
    });
}