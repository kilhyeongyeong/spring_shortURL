<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <style>
        .container{
            width: 50%;
            margin-top: 50px;
        }
        .container>form>div {
            margin: 30px 0;
        }
        .margin{
            margin-bottom: 10px;
        }
        .btn {
            font-weight: bold !important;
        }
        .btn-outline-light {
            color: #4B6FFA !important;
            
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Edit</h1>
        <form action="#" method="post">
            <div>
                <p class="margin">Destination</p>
                <input type="text" class="form-control" onchange="originUrlOnchange(this)" name="originUrl" value="${shortUrl.originUrl}">
            </div>
            <div>
                <p class="margin">Title(optional)</p>
                <input type="text" class="form-control" name="title" value="${shortUrl.title}">
            </div>
            <div>
                <h3>Short Link</h3>
                <div class="input-group">
                    <p class="margin">Domain🔒</p>
                    <p class="margin">/</p>
                    <p class="margin">Custom back-half(optional)</p>
                </div>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" disabled value="shortUrl.pj">
                    <span class="input-group-text">/</span>
                    <input type="text" class="form-control" name="shortUrl" value="${shortUrl.shortUrl}" onchange="shortUrlOnchage(this)" id="shortUrlInput">
                </div>
                <div>
                    <p id="checkShortUrl">${checkShortUrl}</p>
                </div>
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-outline-light" onclick="location.href='/shortUrl/main'" formmethod="post">Cancle</button>
                <input type="submit" class="btn btn-primary" value="Edit">
            </div>
            <input type="hidden" name="index" value="${shortUrl.index}">
        </form>
    </div>

    <script>
        function originUrlOnchange(obj){
            location.href = "/shortUrl/create/calculation?originUrl=" + decodeURI(obj.value);
        }

        function shortUrlOnchage(obj){
            location.href = "/shortUrl/create/checkShortUrl?shortUrl=" + obj.value;
        }

        let $checkShortUrl = document.getElementById("checkShortUrl");
        let $shortUrlInput = document.getElementById("shortUrlInput");
        console.dir($checkShortUrl)
        if($checkShortUrl.textContent == 'true'){
            $checkShortUrl.textContent = "already exist..!";
            $shortUrlInput.value = '';
        }else{
            $checkShortUrl.textContent = "";
            $checkShortUrl.setAttribute("display", "none");
        }

    </script>
</body>
</html>