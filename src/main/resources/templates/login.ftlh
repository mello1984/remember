<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page title="Login page">

    <h3>Login page</h3>
    <@l.login path="/login" isRegisterForm=false/>

</@c.page>