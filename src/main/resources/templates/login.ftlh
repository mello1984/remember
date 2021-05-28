<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page title="Login page">

    <@l.login path="/login" isRegisterForm=false/>
    <#if message??>${message}</#if>

</@c.page>