<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<@c.page title="Index page">

    <h3>Hello, ${name}!</h3>
    <div class="mb-1">This is simple project...</div>

    <div class="mt-4">
        User: ${user}<br/>
        Name: ${name}<br/>
    </div>

</@c.page>