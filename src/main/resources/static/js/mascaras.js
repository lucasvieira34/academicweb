//$(document).ready(function () { 
//    var $data = $("#dataNascimento");
//    $data.mask("99/99/9999 ", {reverse: true});
//});

$(document).ready(function () { 
    var $cpf = $("#cpf");
    $cpf.mask('000.000.000-00', {reverse: true});
});

//var formattedDate = new Date($("#dataNascimento"));
//var d = formattedDate.getDate();
//var m =  formattedDate.getMonth();
//m += 1;  // JavaScript months are 0-11
//var y = formattedDate.getFullYear();
//
//$("#dataNascimento").val(d + "." + m + "." + y);