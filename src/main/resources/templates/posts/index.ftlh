<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<#import "../parts/posts.ftl" as p>
<@c.page title="Posts page">
    <div class="mb-4"><h3>Posts</h3></div>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new Post
    </a>

    <div class="collapse <#if textError?? || tagError??>show</#if>" id="collapseExample">
        <div class="form-group my-3">
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
                    <div class="form-control-file">
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

    <form method="get">
        <div class="form-group row ml-1">
            <div class="row">
                <div class="col-sm-2">
                    <label for="tag" class="col-form-label">Tag</label>
                </div>
                <div class="col-sm-6">
                    <input type="text" name="tag" class="form-control" id="tag" placeholder="${tag}"/>
                </div>
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-primary">Find</button>
                </div>
            </div>
        </div>
    </form>

    <div class="form-group-row ml-1">
        <div class="row">
            <div>
                <label class="col-sm-1 col-form-label" for="tags">Tags:</label>
            </div>
            <div class="col-sm-6" id="tags">
                <a class="btn btn btn-outline-secondary" href="/posts">Clear</a>
                <#list tags as t>
                    <a class="btn <#if t!=tag>btn-outline-secondary<#else>btn-secondary</#if>" href="/posts?tag=${t.tag}"
                       role="button">${t.tag}</a>
                </#list>
            </div>
        </div>
    </div>

    <div class="card-columns" id="message-list">
        <@p.posts posts true/>
    </div>

</@c.page>