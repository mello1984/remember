<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page title="Registration page">

    <h3>Add new user</h3>
    <@l.login path="/registration" isRegisterForm=true/>
    <#if userExistsMessage??>${userExistsMessage}</#if>

</@c.page>