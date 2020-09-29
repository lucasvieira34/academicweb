$(document).ready(function () {
    $("#myTable tbody tr td").each(function () {
        if ($(this).text() > 5 && $(this).text() <= 10)
            $(this).addClass('verde');
        else if ($(this).text() > 0.1 && $(this).text() <= 5)
            $(this).addClass('vermelho');
    });
    
});