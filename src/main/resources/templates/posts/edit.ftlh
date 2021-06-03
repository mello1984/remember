<#import "../parts/common.ftl" as c>
<@c.page title = "Edit post">
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="post" enctype="multipart/form-data" action="/posts/${post.getId()}">
                <input type="hidden" name="_method" value="PATCH">
                <input type="hidden" name="id" value="${post.getId()}">
                <label class="m-1">${post.author.username}</label>
                <input type="text" name="text" class="form-control m-1" placeholder="Text" value="${post.text}"/>
                <input type="text" name="tag" class="form-control m-1" placeholder="Tag" value="${tags}"/>

                <#if post.filename??>
                    <div class="m-1"><img src="/img/${post.filename}" alt=""/></div>
                </#if>

                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Upload</span>
                    </div>
                    <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="inputGroupFile01">
                        <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    </div>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary m-1">Save</button>
            </form>
        </div>

    </div>

    <div>
        <form class="ml-1" method="post" action="${post.getId()}">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Delete</button>
        </form>
    </div>

</@c.page>