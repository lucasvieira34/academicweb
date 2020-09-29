$(document).ready(function () {
    $(".personalizado").on("click", function () {
    
    	$('html, body').animate({scrollTop:0}, 'slow');

        var customElement = $("<div>", {
            "css"   : {
                "font-size"     : "40px",
                "text-align"    : "center",
                "margin-top"    : "5px",
            },
            "text"  : "Processando..."
        });

        $(".container-fluid").LoadingOverlay("show", {
            imageColor: "#FA8021",
            custom      : customElement
        });
        setTimeout(function () {
            $(".container-fluid").LoadingOverlay("hide");
        }, 8000000);

    });
});