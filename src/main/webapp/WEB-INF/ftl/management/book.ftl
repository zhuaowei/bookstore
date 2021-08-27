<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Management</title>
    <link rel="stylesheet" href="/resources/css/book.css">
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/wangeditor@latest/dist/wangEditor.min.js"></script>
    <script src="/resources/template-web.js"></script>
    <script type="text/html" id="book-item">
        <li data-source="{{bookId}}">
            <div class="col-1">{{bookName}}</div>
            <div class="col-2">{{subTitle}}</div>
            <div class="col-3">{{author}}</div>
            <div class="col-4 operate">
                <button class="blue-button modify" data-source="{{bookId}}">修改</button>
                <button class="blue-button delete" data-source="{{bookId}}">删除</button>
            </div>
        </li>
    </script>
    <script type="text/javascript">
        $(function () {
            // 提示函数
            function notice(message) {
                $("#notice").text(message);
                $("#notice").fadeIn(200);
                setTimeout(function () {
                    $("#notice").fadeOut(1000);
                }, 3000)
            }
            /* TODO 定义一个函数，刷新图书列表（第一页） */
            function loadBooks(page) {
                $.get("/management/book/list", {
                    page: page,
                    /* 默认每页10条 */
                    limit: 10
                }, function (json) {
                    var books = json.records;

                    // 删除子元素
                    $("#book-list").empty();
                    for (var i = 0; i < books.length; i++) {
                        var book = books[i];
                        // 生成图书列表元素html
                        var book_item = template("book-item", book);
                        // 添加到列表
                        $("#book-list").append(book_item);

                        /* 修改当前页 */
                        $("#current-page").text(json.current);
                        $("#page-count").text(json.pages);
                    }

                    // 修改操作
                    $(".operate .modify").click(function () {
                        // 提交请求，编辑书籍
                        $.get("/management/book/modify", {
                            bookId : $(this).attr("data-source")
                        }, function (json) {
                            if (json.code == "0") {
                                /* 请求成功 */
                                // 填入数据
                                $("#categoryId").val(json.book.categoryId);
                                $("#bookName").val(json.book.bookName);
                                $("#subTitle").val(json.book.subTitle);
                                $("#author").val(json.book.author);
                                editor.txt.html(json.book.description);

                                /* 把操作写到按钮，提交时用 */
                                $("#submit-button").attr("optype", "update");
                                $("#dialog form").attr("data-source", json.book.bookId);
                                $("#dialog").fadeIn(500);
                            } else {
                                notice("获取图书失败");
                                return;
                            }
                        }, "json");
                    });

                    // 删除操作
                    $(".delete").click(function () {
                        $.post("/management/book/delete", {
                            bookId : $(this).attr("data-source")
                        }, function (json) {
                            if (json.code == "0") {
                                notice("删除成功");
                                loadBooks(parseInt($("#current-page").text()));
                            } else {
                                notice("删除失败");
                            }
                        }, "json");
                    });

                }, "json");
            }
            // 初始化editor
            const E = window.wangEditor;
            let editor = new E("#editor");
            /* 新版本设置图片上传路径和格式 */
            editor.config.uploadImgServer = "/management/book/upload";
            editor.config.uploadFileName = "img";
            editor.create();

            loadBooks(1);

            $(".pre").click(function () {
                var current_page = parseInt($("#current-page").text());
                if (current_page > 1) {
                    current_page--;
                }
                loadBooks(current_page);
            });
            $(".next").click(function () {
                var current_page = parseInt($("#current-page").text());
                var page_count = parseInt($("#page-count").text());
                if (current_page < page_count) {
                    current_page++;
                }
                loadBooks(current_page);
            });

            $("#close-button").click(function () {
                $("#dialog").fadeOut(500);
                document.getElementById("book-info").reset();
                editor.txt.html("");

            });
            $("#add-book").click(function () {
                document.getElementById("book-info").reset();
                editor.txt.html("");
                $("#dialog form").attr("data-source", "0");
                $("#dialog").fadeIn(500);
            });

            /* 提交图书信息 */
            $("#submit-button").click(function () {
                $("#submit-button").attr("optype", "create");
                let formatData = {
                    categoryId: $("#categoryId").val(),
                    bookName : $("#bookName").val(),
                    subTitle : $("#subTitle").val(),
                    author : $("#author").val(),
                    description : editor.txt.html()
                }
                if (formatData.categoryId == null) {
                    notice("请填写书籍类别");
                } else if (formatData.bookName == null) {
                    notice("请填写书名");
                }
                let optype = "create";
                if ($("#dialog form").attr("data-source") != "0"){
                    formatData.bookId = $("#dialog form").attr("data-source");
                    optype = "update";
                }
                console.log("method:" + optype);
                console.log(formatData);
                // 提交请求
                $.post("/management/book/" + optype, formatData, function (json) {
                    if (json.code == "0") {
                        /* 提交成功，关闭窗口，刷新图书列表 */
                        // TODO 刷新图书列表

                        $("#dialog").fadeOut(500);
                        notice("操作成功");
                        loadBooks(parseInt($("#current-page").text()));
                    } else {
                        notice("🙁 发生错误" + json.message);
                    }
                }, "json");
            });
        })
    </script>
</head>
<body>
    <div class="container">
        <header>
            <div id="icon">图书管理系统</div>
            <div id="logout">注销</div>
            <div id="member">
                <img  src="/resources/images/user.png" alt="user">
                <span>登录</span>
            </div>
        </header>
        <div class="main">
            <div class="side-bar">
                <div id="book-manage-btn">图书管理</div>
                <div id="evaluation-manage-btn">短评管理</div>
            </div>
            <div class="content">
                <div class="list-title">图书列表</div>
                <div class="book">
                    <div class="tool-bar">
                        <button id="add-book" class="blue-button">添加</button>
                    </div>
                    <div id="col">
                        <div class="col-1">书名</div>
                        <div class="col-2">子标题</div>
                        <div class="col-3">作者</div>
                        <div class="col-4">操作</div>
                    </div>
                    <ul id="book-list">

                    </ul>
                    <div class="page-skip">
                        <button class="blue-button pre" page="">上一页</button>
                        <button class="blue-button next" page="">下一页</button>
                        <p>第 <span id="current-page" >1</span> 页，共 <span id="page-count">1</span> 页</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 弹窗 -->
    <div id="dialog">
        <div class="tool-bar">
            <button class="blue-button" id="submit-button" optype="create">提交</button>
            <button class="blue-button" id="close-button">关闭</button>
        </div>
        <div class="clear"></div>
        <form id="book-info" action="/" method="post" data-source="0">
            <p><select id="categoryId" id="categoryId" >
                <option value="0">请选择书籍分类</option>
                <option value="1">前端</option>
                <option value="2">后端</option>
                <option value="3">产品</option>
                <option value="4">测试</option>
            </select>
            </p>
            <p><input id="bookName" type="text" placeholder="请输入书名"></p>
            <p><input id="subTitle" type="text" placeholder="请输入子标题"></p>
            <p><input id="author" type="text" placeholder="请输入作者"></p>
            <!-- 富文本编辑器 -->
            <p class="tips">tips: 必须包含至少一张图片，默认第一张图片作为图书封面</p>
            <div id="editor" name="content">

            </div>
        </form>

    </div>

    <div id="notice">注意：test</div>
</body>
</html>