<#import "../parts/common.ftl" as c>
<@c.page "Edit user">
    <a href="/users">Users list</a>

    <h3>User editor</h3>
    <form method="post" action="/users">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="hidden" name="userId" value="${user.id}"/>

        Id: ${user.id}<br>
        Username: <label> <input type="text" value="${user.username}" name="username"/> </label><br>

        <label>
            <input type="checkbox" name="active" ${user.isActive()?string("checked", "")}>Active
        </label>

        <#list roles as r>
            <div>
                <label><input type="checkbox" name="${r}" ${user.roles?seq_contains(r)?string("checked", "")}>${r}
                </label>
            </div>
        </#list>

        <button type="submit">Save</button>
    </form>

</@c.page>