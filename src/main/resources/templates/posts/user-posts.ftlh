<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<#import "../parts/posts.ftl" as p>
<@c.page title="Posts page">

    <div class="card-columns" id="message-list">
        <@p.posts posts false/>
    </div>

</@c.page>