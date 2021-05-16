<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Вспомнить все</title>
</head>
<body>

<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="submit" value="Sign Out"/>
</form>

<h3>Список записей</h3>
<br><br>
<div>
    <form method="post">
        <label><input type="text" name="text" placeholder="Введите мысль"/></label>
        <label><input type="text" name="tag" placeholder="Введите тег"/></label>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Записать</button>
    </form>
</div>
<br><br>

<table border="1">
    <thead>
    <tr>
        <th>Дата</th>
        <th>Текст</th>
        <th>Тег</th>
        <th>Ссылка</th>
    </tr>
    </thead>

    <#if records?size==0>
        <tr>
            <td colspan="4">Записей нет...</td>
        </tr>

    <#else>
        <#list records as r>
            <tr>
                <td>${r.getDate()}</td>
                <td>${r.getText()}</td>
                <td>${r.getTag()}</td>
                <td><a href="/records/${r.getId()}">Редактировать</a></td>
            </tr>

        <#--            <p>${r}</p>-->
        </#list>
    </#if>
</table>


</body>
</html>