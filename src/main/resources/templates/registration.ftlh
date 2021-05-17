<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page title="Add new user">

    <h3>Add new user</h3>

    <@l.login path="/registration"/>
    <#if userExistsMessage??>${userExistsMessage}</#if>
</@c.page>