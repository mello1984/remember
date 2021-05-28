<#import "../parts/common.ftl" as c>
<#include "../parts/security.ftl">
<@c.page "users list">
    <table class="table" id="user_table">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Username</th>
            <th scope="col">Active</th>
            <th scope="col">Roles</th>
            <th scope="col">Link</th>
        </tr>
        </thead>
        <#list users as u>
            <tr>
                <td>${u.getId()}</td>
                <td>${u.getUsername()}</td>
                <td><#if u.active>active<#else>not active</#if></td>
                <td><#list u.getRoles() as role>${role}<#sep>, </#list></td>
                <td><a href="/users/${u.getId()}">edit</a></td>
            </tr>
        <#else>
            <td colspan="4">Список пользоватедуй пуст</td>
        </#list>

    </table>


</@c.page>