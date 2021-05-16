<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h3>Редактируем запись</h3>

<div>
    <form method="post" action="${record.getId()}/edit">
        <input type="hidden" name="id" value="${record.getId()}">
        <label>Дата: ${record.getDate()}</label><br>
        <label>Текст: </label><label><input type="text" name="text" value="${record.getText()}"></label><br>
        <label>Тег: </label><label><input type="text" name="tag" value="${record.getTag()}"></label><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Отправить</button>
    </form>
</div>
<br><br>
<div>
    <form method="post" action="${record.getId()}/delete">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit"> Удалить запись</button>
    </form>
</div>

</body>
</html>