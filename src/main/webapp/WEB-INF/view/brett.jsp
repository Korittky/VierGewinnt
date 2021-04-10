<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vier Gewinnt</title>
</head>
<body>


<table cellspacing="0" cellpadding="0">
    <c:if test="${not 'leer'.equals(naechsteFarbe)}">
        <tr >
        <c:forEach begin="0" end="${brett.get(0).size()-1}" var="i">
            <th><button ${'leer'.equals(brett.get(0).get(i))?'':'disabled="disabled"'} type="button" onClick="location.href='/wirf/${i}'">V</button></th>
        </c:forEach>
        </tr>
    </c:if>

    <c:forEach items="${brett}" var="reihe">
        <tr>
            <c:forEach begin="0" end="${reihe.size()-1}" var="i">
                <td><img width="100" height="100" src="/${reihe.get(i)}.png"></td>
            </c:forEach>
        </tr>
    </c:forEach>
    <tr>
    <c:forEach begin="0" end="${brett.get(0).size()-1}" var="i">
        <th>${score.get(i)}</th>
    </c:forEach>
    </tr>
</table>

<hr/>
Z&uuml;ge: ${zuege}
<hr/>

N&auml;chste Farbe:
<img width="100" height="100" src="/${naechsteFarbe}.png">

<hr/>

<c:if test="${'leer'.equals(naechsteFarbe)}">
    Spieler ${gewonnen} hat gewonnen!
    <a href="/init">Neues Spiel</a>
</c:if>

</body>
</html>