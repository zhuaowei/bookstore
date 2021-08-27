<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>书城</title>
    <link rel="stylesheet" href="/resources/css/index.css">
    <link rel="stylesheet" href="/resources/css/bookstore.css">
<#--    <link rel="stylesheet" href="/resources/raty/lib/jquery.raty.css">-->
    <script src="/resources/jquery-3.6.0.js"></script>
    <script src="/resources/template-web.js"></script>
<#--    <script src="/resources/raty/lib/jquery.raty.js"></script>-->
    <script type="text/html" id="book-item">
        <li class="book-item" id="{{bookId}}">
            <a href="/detail?bookId={{bookId}}"><img class="cover" src="{{cover}}" alt="图书封面"></a>
            <div class="book-info">
                <p class="book-name">{{bookName}}</p>
                <p class="author">{{author}}</p>
                <p class="subTitle">{{subTitle}}</p>
                <br>
                <p>
                    <span class="stars" name="level" data-score="{{evaluationScore}}" title="gorgeous">综合评分：</span>
                    <span class="score">{{evaluationScore}}</span>
                    <span class="evaluation-quantity">{{evaluationQuantity}}条评论</span>
                </p>
            </div>
        </li>
    </script>
    <script>

        $(document).ready(function(){
            var current_page = 1;
            var pages = 1;
            var categoryId = $("#categoryId").attr("value");
            var sortMethod = $("#sort").attr("value");
            loadMore(true, true);
            function loadMore(isIndex) {
                categoryId = $("#categoryId").attr("value");
                sortMethod = $("#sort").attr("value");
                if (isIndex == true) {
                    current_page = 1;
                }
                $.ajax({
                    url : "/books",
                    type : "get",
                    data : {page : current_page, categoryId : categoryId, sort : sortMethod},
                    dataType : "json",
                    success : function (json) {
                        var records = json.records;
                        pages = json.pages;

                        // 删除子元素
                        $("#books").empty();
                        for (var i = 0; i < records.length; i++) {
                            var book = records[i];
                            // 生成图书列表元素html
                            var book_item = template("book-item", book);
                            // 添加到列表
                            $("#books").append(book_item);
                        }
                        $(".skip-value").val(current_page);
                        /* 星型组件 */
                        // $(".stars").raty({readOnly : true});
                    }
                });
            }
            $("#pre-page").click(function () {
                if (current_page > 1) {
                    current_page--;
                }
                loadMore(false);
            });

            $("#next-page").click(function () {
                if (current_page < pages) {
                    current_page++;
                }
                loadMore(false);
            });


            $(".category-bar .item").click(function () {
                $(".category-bar .item").removeClass("selected");
                $(this).addClass("selected");
                // 设置查询类别
                $("#categoryId").attr("value", $(this).attr("value"));
                console.log($("#categoryId").attr("value"));

                loadMore(true);
            });
            $(".sort-bar .item").click(function () {
                $(".sort-bar .item").removeClass("selected");
                $(this).addClass("selected");
                // 设置排序方式
                $("#sort").attr("value", $(this).attr("value"));
                console.log($("#sort").attr("value"));
                loadMore(true);
            });
        });
    </script>

</head>
<body>

    <div class="main-bookstore">

        <div class="container">
            <div class="header">
                <#-- 如果登录，显示用户 -->
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
            <div class="menu-bar">
                <ul class="category-bar" id="categoryId" value="-1">
                    <li class="item selected" value="-1"><a href="javascript:;">全部</a></li>
                    <li class="item" value="1"><a href="javascript:;">前端</a></li>
                    <li class="item" value="2"><a href="javascript:;">后端</a></li>
                    <li class="item" value="3"><a href="javascript:;">测试</a></li>
                    <li class="item" value="4"><a href="javascript:;">产品</a></li>
                </ul>
                <div style="clear: both"></div>
                <ul class="sort-bar" id="sort" value="evaluation_score">
                    <li class="item selected" value="evaluation_score"><a href="javascript:;">按评分排序</a></li>
                    <li class="item" value="evaluation_quantity"><a href="javascript:;">按人气排序</a></li>
                </ul>
            </div>
            <div class="book-list">
                <ul id="books">
                    <#-- 图书列表 -->

                </ul>
            </div>
            <div class="skip-bar">
                <div class="button-bar">
                    <input id="pre-page" type="submit" value="上一页">
                    <input id="next-page" type="submit" value=下一页>
                </div>
                <div class="skip">
                    <input class="skip-button" type="submit" value="跳转" >
                    到第
                    <input class="skip-value" type="text" value="1">
                    页
                </div>
<#--                <a href="javascript:;" id="test" value="test">测试</a>-->
            </div>
        </div>
    </div>
</body>
</html>