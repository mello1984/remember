<#import "../parts/common.ftl" as c>
<#import "../parts/login.ftl" as l>
<@c.page title="Records page">
    <@l.logout/>

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
            <th>Автор</th>
            <th>Ссылка</th>
        </tr>
        </thead>
            <#list records as r>
                <tr>
                    <td>${r.getDate()}</td>
                    <td>${r.getText()}</td>
                    <td>${r.getTag()}</td>
                    <td>${r.getAuthor().getUsername()}</td>
                    <td><a href="/records/${r.getId()}">Редактировать</a></td>
                </tr>
                <#else>
                <tr> <td colspan="5"> Записей нет</td></tr>

            </#list>
    </table>

</@c.page>