<#include "security.ftl">
<#macro post p showAuthor>
    <div class="card m-1">
        <div class="card-body">
            <#if p.filename??>
                <img src="/img/${p.filename}" class="card-img-top">
            </#if>
            <span>${p.text}</span><br/>
            <i>#${p.tag}</i>
        </div>
        <div class="card-footer text-muted">
            <#if showAuthor><a href="/posts/user-posts/${p.author.id}">${p.author.username}</a></#if>
            <#if p.author.id==userId> <a href="/posts/${p.id}">Edit</a> </#if>
        </div>
    </div>
</#macro>

<#macro posts posts showAuthor>
    <#list posts as p>
        <@post p showAuthor/>
    <#else>
        Posts not found
    </#list>
</#macro>

