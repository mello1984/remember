<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<#import "../parts/posts.ftl" as p>
<@c.page title="Tags page">

    <h3> Tags:</h3>

    <#list tags as t>
        <div class="form-group row">
            <label class="col-sm-3 col-form-label"> ${t.tag} <i>(${t.posts?size} post(s))</i> </label>
            <#if t.posts?size==0 && isAdmin>
                <form method="post">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <input type="hidden" name="id" value="${t.id}">

                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </#if>
        </div>
    </#list>

</@c.page>