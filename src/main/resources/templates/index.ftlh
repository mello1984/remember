<#import "parts/common.ftl" as c>
<@c.page title="Index page">

    <#if name??>
        Hello, ${name}!
    <#else>
        Hello, unknown!
    </#if>

    <br><br>
    <a href="/records">Records</a>
    <br><br>
    <a href="/users">Users</a>

</@c.page>