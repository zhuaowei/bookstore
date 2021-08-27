<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>detail</title>

    <link rel="stylesheet" href="/resources/css/detail.css">
    <script src="/resources/jquery-3.6.0.js"></script>
    <script src="/resources/jquery-3.6.0.min.js"></script>

<#--    <script src="/resources/raty/lib/jquery.raty.js"></script>-->
    <script>

        $(function () {
            /* 星型组件 */
            // $(".stars").raty({
            //     readOnly : true
            // });
            // $("#evaluation-star").raty();
            <#if readState??>
                /* 如果存在阅读状态，给相应的添加阅读状态 */
            $("*[read-state='${readState.readState}']").addClass("highlight");
            </#if>

            <#if !loginMember??>
            /* 如果不存在阅读状态，给相应的添加阅读状态 */
            $("*[read-state], #add-evaluation, .enjoy-button").click(function () {
                alert("请登录后操作");
            });
            </#if>
            <#if loginMember??>
            /* 如果存在阅读状态，给相应的添加阅读状态 */
            $("*[read-state]").click(function () {
                var readState = $(this).attr("read-state");
                console.log(readState);
                $.post("/update_read_state", {
                    memberId : ${loginMember.memberId},
                    bookId : ${book.bookId},
                    readState : readState
                }, function (json) {
                    if (json.code == "0") {
                        /* 如果获取状态成功 */
                        $("*[read-state]").removeClass("highlight");
                        $("*[read-state='" + readState + "']").addClass("highlight");
                    }
                }, "json");
            });

            /* 写评论 */
            $("#add-evaluation").click(function () {
                // let score = $("#evaluation-star").raty("score");
                let score = $("#evaluation-star input").val();
                let content = $("#evaluation").val();
                // alert(score + ":" + content);
                if (score == null || $.trim(content) == "" || score < 1 || score > 5) {
                    return;
                } else {
                    $.post("/evaluate", {
                        bookId: ${book.bookId},
                        memberId: ${loginMember.memberId},
                        score: score,
                        content: content
                    }, function (json) {
                        if (json.code == "0") {
                            window.location.reload();
                        }
                    }, "json");
                }

            });

            /* 点赞功能 */
            $(".enjoy-button").click(function () {
                let evaluationId = $(this).attr("data-source-enjoy");
                $.post("/enjoy", {
                    evaluationId : evaluationId
                }, function (json) {
                    if (json.code == "0") {
                        $("*[data-source-enjoy='" + json.evaluation.evaluationId + "'] label").text(json.evaluation.enjoy);
                    }
                }, "json");
            })
            </#if>
        })
    </script>
</head>
<body>
    <div class="container">
        <!-- 顶部状态栏 -->
        <div class="top-bar">
            <!-- 图标 -->
            <img src="/resources/images/book.png">
            <#if loginMember??>
                <div class="member">
                    <a href="javascript:;">
                        <!-- 用户图片 -->
                        <img src="/resources/images/user.png" alt="">
                        <label for="">${loginMember.nickname}</label>
                    </a>
                </div>
            <#else>
                <div class="member">
                    <a href="/login.html">
                        <!-- 用户图片 -->
                        <img src="/resources/images/user.png" alt="">
                        <label for="">请登录</label>
                    </a>
                </div>
            </#if>

        </div>
        <!-- 图书信息 -->
        <div class="book">
            <a href=""><img class="cover" src="${book.cover}" alt="图书封面" /></a>
            <div class="book-info">
                <p class="book-name">${book.bookName} 作者：${book.author}</p>
                <p class="subTitle">${book.subTitle}</p>
                <div style="clear: both"></div>
                <p>
                    <span class="stars" name="level" data-score="${book.evaluationScore}" title="gorgeous"></span>
                    <span class="score">评分：${book.evaluationScore}</span>
                    <span class="evaluation-quantity">${book.evaluationQuantity}条评论</span>
                </p>
                <div style="clear: both"></div>
                <div class="button">
                    <input type="submit" read-state="1" value="想看">
                    <input type="submit" read-state="2" value="看过">
                </div>
            </div>
            <div style="clear: both"></div>
            <div id="description">
                ${book.description}
            </div>
        </div>
        <!-- 图书评论 -->
        <div class="comment">
            <!-- 工具栏 写评论 -->
            <div class="tool-bar">
                <div id="evaluation-star" data-score="" title="gorgeous">给书打分：<input type="number" min="1" max="5" placeholder="1 - 5"></div>
                <input type="text" id="evaluation" placeholder="写个评论吧" >
                <input type="submit" name="" id="add-evaluation" value="写评论">
            </div>
            <div class="evaluation-list">
                <ul>
                    <#list evaluationList as evaluation>
                        <li class="evaluation">
                            <div class="member">
                                <span class="evaluation-time">${evaluation.createTime?string("yyyy-MM-dd")}</span>
                                <span>${evaluation.member.nickname}</span>
                                <span class="stars" name="level" data-score="${evaluation.score}" title="gorgeous"></span>
                                <span class="score">评分：${evaluation.score}</span>
                            </div>
                            <div class="content">
                                <span class="eval-content">${evaluation.content}</span>
                                <button data-source-enjoy="${evaluation.evaluationId}" type="submit" class="enjoy-button" value="${evaluation.enjoy}">
                                    <img src="/resources/images/like.png" alt="点赞">
                                    <label>${evaluation.enjoy}</label>
                                </button>
                            </div>
                        </li>
                    </#list>

                </ul>
            </div>
        </div>
    </div>
</body>
</html>