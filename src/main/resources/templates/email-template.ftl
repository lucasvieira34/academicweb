<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Thymeleaf HTML Template Example</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 48px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">

    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="border-collapse: collapse;">
        <tr>
            <td align="center" bgcolor="#ef6c00" style="padding: 40px 0 30px 0;">
                <img src="cid:logo.jpg" style="display: block;" />
            </td>
        </tr>
        <tr>
            <td bgcolor="#eaeaea" style="padding: 40px 30px 40px 30px;">
                <p>Olá ${name},</p>
                <p>A sua nota de ${disciplina} acaba ser alterada!</p>
                <p>Bons estudos!</p>
            </td>
        </tr>
        <tr>
            <td bgcolor="#ef6c00" style="padding: 30px 30px 30px 30px;">
                <p>Academic Web</p>
                <p>15/07/2020</p>
            </td>
        </tr>
    </table>

</body>
</html>