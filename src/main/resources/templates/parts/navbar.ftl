<#include "security.ftl">
<#import "login.ftl" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Remember me</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <#if known>
                <li class="nav-item">
                    <a class="nav-link" href="/posts">Posts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/posts/user-posts">My posts</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/users">Users</a>
                    </li>
                </#if>
            </#if>
        </ul>
        <div class="navbar-text mr-4">
            <#if known> ${name}
            <#else>
                <a href="/login" class="btn btn-outline-secondary" role="button" aria-pressed="true">Log in</a>
            </#if>
        </div>
        <#if known>
            <div><@l.logout/></div>
        </#if>
    </div>
</nav>