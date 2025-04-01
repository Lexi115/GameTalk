function importImg(){
    let ui = $.summernote.ui;
    console.log("import");
    var btn = ui.button({
        contents: '<i class="bi bi-image"/>',
        tooltip: 'inserisci un immagine',
        click: function () {
            let imageUrl = prompt("Enter the image URL:");
            if (imageUrl) {
                $('#summernote').summernote('insertImage', imageUrl);
            }
        }
    });
    return btn.render();
}

$(document).ready(function() {
    $('#summernote').summernote({
        placeholder: 'write here...',
        height: 120,
        toolbar: [
            ['style', ['style']],
            ['fontstyle', ['fontname', 'fontsize']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['forecolor']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link','inImg']],
            ['view', ['fullscreen', 'codeview', 'help']]
        ],
        buttons:{
            inImg: importImg
        }
    });
});
