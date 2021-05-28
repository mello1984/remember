<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<@c.page title="Posts page">
    <div class="mb-4"><h3>My posts</h3></div>

    <div class="card-columns" id="message-list">
        <#list posts as p>

            <div class="card m-2">
                <div class="m-1">
                    <#if p.filename??>
                        <img src="/img/${p.filename}" class="card-img-top">
                    </#if>
                    <div class="card-body">
                        <span>${p.text}</span><br/>
                        <i>#${p.tag}</i>
                    </div>
                </div>
            </div>

        <#else>
            Posts not found
        </#list>
    </div>

</@c.page>