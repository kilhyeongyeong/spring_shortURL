<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>shortenURLProject</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <style>

        #secondDiv {
            border-bottom: 3px dotted lightgray;
        }

        .row {
            margin: auto 0 !important;
        }

        /* 원본 URL이 너무 길 경우 ... 처리 */
        #secondDivRight a {
            width: 100%;
            display: block;
            overflow: hidden !important;
            text-overflow: ellipsis !important;
            white-space:nowrap !important;
        }

        /* 드레그 허용 */
        .draggable {
            -webkit-user-select:all;
            -moz-user-select:all;
            -ms-user-select:all;
            user-select:all
        }

        /* a 태그 밑줄 + 파란색 없애기 */
        a:link {
            color:black;
        }

        a:visited {
            color:black;
        }

        a:active {
            color:black;
        }
    </style>
</head>
<body>
    <div class="container" style="margin-top: 50px;">
        <h1 class="col-md-6" style="margin: 20px 0px;">👻Shorten URL Project</h1>
        <div class="d-flex justify-content-end" id="buttons" style="margin: 20px auto;">
            <button class="btn btn-outline-danger me-1" type="button" onclick="deleteButtonOnclick(this)" id="deleteButton" >Delete</button>
            <button class="btn btn-outline-primary me-1" type="button" onclick="editButtonOnclick(this)">Edit</button>
            <button class="btn btn-primary" type="button" onclick="location.href='/shortUrl/createPage'">Create new</button>
        </div>
        <div class="row" id="secondDiv">
            <div class="col-md-6" id="secondDivLeft">
                <h3 id="title">Title</h3>
                <p>날짜 by writer</p>
            </div>
            <div class="col-md-6" id="secondDivRight" style="padding: 0">
                <div class="row">
                    <h3 id="shortUrl" style="padding: 0; width:auto"><a href="#" onclick="increaseCount(this)">ShortUrl</a></h3>
                </div>
                <p><a href="#">originUrl</a></p>
            </div>
            
        </div>
        
        <div>
            <table class="table table-hover" id="shortUrlList">
                <c:forEach var="list" items="${shortUrlList}">
                    <tr onclick="changeColorRowAndSecondDivSetting(this)">
                        <td>
                            <div class="row">
                                <p class="col-11">${list.title}</p>
                                <p id="count" class="col-1">${list.count}</p>
                            </div>
                            <div class="row">
                                <p class="col" style="color: orange; font: bold;">${list.shortUrl}</p>
                            </div>
                            <div class="row" style="display: none;">
                                <p class="col">${list.index}</p>
                            </div>
                            <div class="row" style="display: none;">
                                <p class="col">${list.lastUpdateDate}</p>
                            </div>
                            <div class="row" style="display: none;">
                                <p class="col">${list.originUrl}</p>
                            </div>
                            <div class="row" style="display: none;">
                                <p class="col">${list.writer}</p>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="text-center">
            <button class="btn btn-secondary" onclick="location.href='/shortUrl/searchPage'">Search Page</button>
        </div>
    </div>

    <script>

        const $td = document.getElementById("shortUrlList").getElementsByTagName("td");

        for(let url of $td){
            let title = url.firstElementChild.textContent;
            if(title == "") {
                url.firstElementChild.textContent = url.firstElementChild.nextElementSibling.textContent;
            }
        }

        let count;

        function changeColorRowAndSecondDivSetting(obj){

            // 리스트 클릭시 클릭한 행 색상 변경
            let $shortUrlList = document.getElementById("shortUrlList");
            let $tr = $shortUrlList.getElementsByTagName("tr");

            for(let i=0; i<$tr.length; i++){
                $tr[i].style.background = "white";
            }
            obj.style.backgroundColor = "rgb(235,235,235)";
            //---------------------------------------------------------------------------------------------------------------
            // 리스트 클릭시 세부정보 띄우기
            const [$title,$count,$shortUrl,$index, $lastUpdateDate, $originUrl, $writer] = obj.firstElementChild.getElementsByTagName("p");

            // 제목
            document.querySelector("#secondDivLeft h3").textContent = $title.textContent;
            // 날짜 + 작성자
            let date = new Date($lastUpdateDate.textContent);
            let dateFormat = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getMilliseconds();
            document.querySelector("#secondDivLeft>p").textContent = dateFormat + " by " + $writer.textContent;
            // 짧은 Url
            document.querySelector("#secondDivRight h3>a").textContent = $shortUrl.textContent;
            // URL 원본 TextSetting + href설정 + 클릭시 새창으로 열기
            $a = document.querySelector("#secondDivRight>p>a");
            $a.textContent = decodeURI($originUrl.textContent);
            $a.href = decodeURI($originUrl.textContent);
            $a.target = "_blank";
            // ShortenUrl href설정 + 클릭시 새 창으로 리타이렉트
            const $aShort = document.querySelector("#secondDivRight h3>a");
            $aShort.href = "/shortUrl/" + $aShort.textContent;
            $aShort.target = "_blank";

            count = $count;
        }

        function editButtonOnclick(obj){
            let $shortUrl = document.getElementById("shortUrl");
            location.href = "/shortUrl/updatePage?shortUrl=" + $shortUrl.textContent;
        }

        function deleteButtonOnclick(obj){
            let $shortUrl = document.getElementById("shortUrl");
            location.href = "/shortUrl/delete?shortUrl=" + $shortUrl.textContent;
        }

        function increaseCount(obj){
            count.textContent = Number(count.textContent) + 1;
        }

    </script>
</body>
</html>