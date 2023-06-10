<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
    <div class="container" style="margin-top: 50px;">
        <h1 class="text-center">👻Shorten URL👻</h1>
        <form action="search" method="get" class="input-group mb-3" style="width: 70% !important; margin: 50px auto;">
            <input type="text" class="form-control" placeholder="shortUrl.py/example" name="shortenUrl" onchange="searchOnchange(this)" required>
            <input type="submit" class="btn btn-outline-secondary" value="검색" onclick="window.location.reload()">
        </form>
        <div class="text-center" style="margin-top: 30%;">
            <button class="btn btn-secondary" onclick="location.href='/shortUrl/main'">목록</button>
        </div>
    </div>

    <script>
        function searchOnchange(obj){
            $form = document.querySelector("form");
            $form.action = obj.value;
            $form.target = "_blank";
        }
    </script>
</body>
</html>