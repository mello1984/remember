<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<@c.page title="Posts page">
    <div class="mb-4"><h3>Список записей</h3></div>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new Post
    </a>

    <div class="collapse <#if textError?? || tagError??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <input type="text" name="text" placeholder="Text"
                           class="form-control ${(textError??)?string('is-invalid','')}"
                            <#if post??> value="${post.text}" </#if> />
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>

                <div class="form-group">
                    <input type="text" name="tag" placeholder="Tag"
                           class="form-control ${(tagError??)?string('is-invalid','')}"
                            <#if post??> value="${post.tag}" </#if>/>
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>

                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile"/>
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add new post</button>
                </div>
            </form>
        </div>
    </div>
    <br/><br/>


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
                <div class="card-footer text-muted">
                    ${p.author.username}
                    <#if p.author.id==userId> <a href="/posts/${p.id}">Edit</a> </#if>
                </div>
            </div>
        <#else>
            Posts not found
        </#list>
    </div>

</@c.page>