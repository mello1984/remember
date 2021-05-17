<#import "../parts/common.ftl" as c>
<@c.page "users list">
    <h3>User list</h3>

    <table border="1">
        <thead>
        <tr>
            <th>Id</th>
            <th>Username</th>
            <th>Roles</th>
            <th>Link</th>
        </tr>
        </thead>
        <#list userlist as u>
            <tr>
                <td>${u.getId()}</td>
                <td>${u.getUsername()}</td>
                <td><#list u.getRoles() as role>${role}<#sep>, </#list></td>
                <td><a href="/users/${u.getId()}">edit</a></td>
            </tr>
        <#else>
            <td colspan="4">Список пользоватедуй пуст</td>
        </#list>

    </table>


</@c.page>