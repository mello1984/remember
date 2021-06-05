<#include "security.ftl">
<#macro post p showAuthor>
    <div class="card m-1">
        <div class="card-body">
            <#if p.filename??>
                <img src="/img/${p.filename}" class="card-img-top">
            </#if>
            <span>${p.text}</span><br/>
            <#list p.tags as t>
                <a class="btn btn-outline-secondary" href="/posts?tag=${t.tag}"
                   role="button">${t.tag}</a>
            </#list>
        </div>
        <div class="card-footer text-muted container">
            <div class="row">
                <#if showAuthor><a class="col align-self-center"
                                   href="/user-posts/${p.author.id}">${p.author.username}</a></#if>

                <#if !p.myPost>
                    <#if p.liked>
                        <a class="col align-self-center text-right" href="/posts/${p.id}/like">
                            <i class="fas fa-heart"></i> ${p.likes}</a>
                    <#else>
                        <a class="col align-self-center text-right" href="/posts/${p.id}/like">
                            <i class="far fa-heart"></i> ${p.likes}</a>
                    </#if>
                <#else>
                    <i class="col align-self-center text-center fas fa-heart"> ${p.likes}</i>
                </#if>

                <#if p.myPost>
                    <a class="col align-self-center text-right" href="/posts/${p.id}"><i class="fas fa-pen"></i></a>
                </#if>

            </div>
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

