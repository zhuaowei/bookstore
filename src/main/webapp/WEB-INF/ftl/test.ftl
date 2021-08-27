<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Test</title>
    <script src="https://cdn.jsdelivr.net/npm/wangeditor@latest/dist/wangEditor.min.js"></script>

</head>
<body>
<div id="editor" style="width: 1080px; height: 720px">
    <p>欢迎使用 <b>wangEditor</b> 富文本编辑器</p>
</div>
<script type="text/javascript">
    const E = window.wangEditor;
    const editor = new E('#editor');
    // const editor = new E( document.getElementById('editor') );

    editor.create();
</script>

</body>

</html>