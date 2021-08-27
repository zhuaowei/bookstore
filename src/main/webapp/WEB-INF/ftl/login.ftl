<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录注册</title>
</head>
<link rel="stylesheet" href="/resources/css/login.css">
<script src="/resources/jquery-3.6.0.js" ></script>
<script>
    $(function () {
        $("#login-option").click(function () {
            $(".form").removeClass("display");
            $("#login").addClass("display");
            reloadVerifyCode();
        });
        $("#register-option").click(function () {
            $(".form").removeClass("display");
            $("#register").addClass("display");
            reloadVerifyCode();
        });
        $(".form .imgVerifyCode").click(function () {
            reloadVerifyCode();
        });
        function reloadVerifyCode() {
            $(".display .imgVerifyCode").attr("src", "/verify_code?ts=" + new Date());
        }
        $(".form form").submit(function () {
            return false;
        });
        $("#login-button").click(function () {
            // username = $(".username").val();
            // password = $(".password").val();
            // vc = $(".vc").val();
            $.ajax({
                url : "/login",
                type : "post",
                data : $("#login form").serialize(),
                dataType : "json",
                success : function (json) {
                    if (json.code == "0") {
                        window.location = "/?ts=" + new Date();
                    } else {
                        alert(json.message);
                        reloadVerifyCode();
                    }
                }
            })
        });
        $("#register-button").click(function () {
            $.ajax({
                url : "/register",
                type : "post",
                data : $("#register form").serialize(),
                dataType : "json",
                success : function (json) {
                    if (json.code == "0") {
                        window.location = "/?ts=" + new Date();
                    } else {
                        alert(json.message);
                        reloadVerifyCode();
                    }
                }
            })
        });
    })
</script>
<style type="text/css">
    .form {
        display: none;
    }

    .display {
        display: block;
    }
</style>
<body>
    <div class="container">
        <div class="content">
            <div class="button">
                <input id="login-option" type="submit" value="登录">
                <input id="register-option" type="submit" value="注册">
            </div>
            <div id="login" class="form display">
                <form action="/login" method="post">
                    <input name="username" class="input-text" type="text" placeholder="username">
                    <br>
                    <input name="password" class="input-text" type="password" placeholder="password">
                    <br>
                    <div class="verify">
                        <input name="vc" type="text" placeholder="验证码">
                        <img class="imgVerifyCode" src="/verify_code" alt="验证码">
                    </div>
                    <br>
                    <input id="login-button" type="submit" value="登录">
                </form>
            </div>
            <div id="register" class="form">
                <form action="/register" method="post">
                    <input name="username" class="input-text" type="text" placeholder="username">
                    <br>
                    <input name="password" class="input-text" type="password" placeholder="password">
                    <br>
                    <input name="nickname" class="input-text" type="text" placeholder="昵称">
                    <br>
                    <div class="verify">
                        <input name="vc" type="text" placeholder="验证码">
                        <img class="imgVerifyCode" src="/verify_code" alt="验证码">
                    </div>
                    <br>
                    <input id="register-button" type="submit" value="注册">
                </form>
            </div>
        </div>
    </div>
</body>
</html>