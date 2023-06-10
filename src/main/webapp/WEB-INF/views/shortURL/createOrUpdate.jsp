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
            margin-bottom: 10px !important;
        }
        .btn {
            font-weight: bold !important;
        }
        .btn-outline-light {
            color: #4B6FFA !important;
            
        }
        #shortUrlFrontInput {
            background-color: #EAECEF;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 id="header">${pageKind}</h1>
        <form action="${pageKind}" method="POST">
            <!-- 원본 URL -->
            <div>
                <label class="form-label margin" for="originUrlInput">Destination</label>
                <input id="originUrlInput" type="text" class="form-control" placeholder="https://example.com/my-long-url" onchange="urlOnchange(this)" name="originUrl" value="${shortUrl.originUrl}">
                <div class="invalid-feedback" id="checkOriginUrl">
                    ${checkOriginUrl}
                </div>
            </div>

            <!-- 제목(option) -->
            <div>
                <label class="form-label margin" for="titleInput">Title<span id="option">(optional)</span></label>
                <input id="titleInput" type="text" class="form-control" name="title" value="${shortUrl.title}">
            </div>

            <!-- 짧은 URL -->
            <div>
                <h3>Short Link</h3>
                <label class="form-label margin" for="shortUrlBackInput">Domain🔒 / Custom back-half<span id="option">(optional)</span></label>
                <div class="input-group mb-3">
                    <input id="shortUrlFrontInput" type="text" class="form-control"  name="shortUrlFront" value="shortUrl.pj" readonly>
                    <span class="input-group-text">/</span>
                    <input id="shortUrlBackInput" type="text" class="form-control" name="shortUrlBack" value="${shortUrl.shortUrlBack}" onchange="urlOnchange(this)">
                    <div class="invalid-feedback" id="checkShortUrl">
                        ${checkShortUrl}
                    </div>
                </div>
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-outline-light" onclick="location.href='/shortUrl/main'" formmethod="post">Cancle</button>
                <input id="createUpdateButton" type="submit" class="btn btn-primary" value="${pageKind}">
            </div>
            <input type="hidden" name="pageKind" value="${pageKind}" id="pageKind">
            <input type="hidden" name="index" value="${shortUrl.index}" id="hiddenIndex">
            <input type="hidden" name="shortUrl" value="${shortUrl.shortUrl}" id="hiddenShortUrl">
            <input type="hidden" name="writer" value="${shortUrl.writer}" id="hiddenWriter">
        </form>
    </div>

    <script>
        // 필요 Elements
        let $pageKind = document.getElementById("pageKind");
        let $form = document.querySelector("form");
        let $header = document.getElementById("header");
        let $createUpdateButton = document.getElementById("createUpdateButton");

        let $checkShortUrl = document.getElementById("checkShortUrl");
        let $shortUrlBackInput = document.getElementById("shortUrlBackInput");

        let $checkOriginUrl = document.getElementById("checkOriginUrl");
        let $originUrlInput = document.getElementById("originUrlInput");

        let $titleInput = document.getElementById("titleInput");

        // Edit와 Create init setting
        if($pageKind.value == "c"){
            // create - form action 설정 / header = Create New 로 변경 / 버튼 이름 Create로 변경
            $form.action = "/shortUrl/create";
            $header.textContent = "Create New";
            $createUpdateButton.value = "Create";
        }else{
            // update - form action 설정 / header = Edit로 변경 / 버튼 이름 Edit로 변경
            $form.action = "/shortUrl/update";
            $header.textContent = "Edit"
            $createUpdateButton.value = "Edit";

            // 원본 url readOnly 설정
            const $originUrlInput = document.getElementById("originUrlInput");
            $originUrlInput.readOnly = true;
            $originUrlInput.style.background = "#EAECEF";

            // (option)글씨 숨기기
            [titleOp, urlOp] = document.querySelectorAll("#option");
            titleOp.style.display = "none";
            urlOp.style.display = "none";
            urlOp.set
        }
        // -------------------------------------------------------------------------
        function urlOnchange(obj){
            if(obj.value != ""){
                let $form = document.querySelector("form");
                let id = obj.id;

                if(id == "originUrlInput"){
                    $form.action = "/shortUrl/checkOriginUrl";
                }else if(id == "shortUrlBackInput"){
                    $form.action = "/shortUrl/checkShortUrl";
                }
                $form.submit();
            }else{
                if($pageKind.value == "u"){ // Edit일 경우만(Create일경우 실행 X)
                    // ShortenUrl이 비어있을 경우 - 필수 입력 표시
                    if($shortUrlBackInput.value == ""){
                        $checkShortUrl.textContent = "\n⚠️Back-half cannot be empty.\n";
                        $shortUrlBackInput.setAttribute("class", "form-control is-invalid");
                    }
                }
            }
        }

        // 원본 URL 중복 체크
        if($checkOriginUrl.textContent.includes("ALREADY")) {
            $originUrlInput.setAttribute("class", "form-control is-invalid");
            $originUrlInput.value = "";
            $checkOriginUrl.textContent = "\n⚠️Already exists\n"
        }else{
            $originUrlInput.setAttribute("class", "form-control");
        }
        // ---------------------------------------------------------------------------
        // 짧은 URL 중복체크
        if($checkShortUrl.textContent.includes('true')){
            $shortUrlBackInput.setAttribute("class", "form-control is-invalid");
            $shortUrlBackInput.value = "";
            $checkShortUrl.textContent = "\n⚠️Already exists\n";
        }else{
            $shortUrlBackInput.setAttribute("class", "form-control");
        }

        // hiddenIndex가 null일 때 0으로 초기 세팅 -- 안그럼 오류..
        $hiddenIndex = document.getElementById("hiddenIndex");
        if($hiddenIndex.value == "") $hiddenIndex.value = 0;

    </script>
</body>
</html>