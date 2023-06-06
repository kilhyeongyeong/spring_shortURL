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
            margin-top: 50px;
        }

        h1{
            margin: 20px 0px;
        }

        #short {
            font: bold;
            color: orange;
        }

        #buttons {
            height: 90% !important;
        }

        #secondDiv {
            border-bottom: 3px dotted lightgray;
        }

        /* table에서 각 요소 text 내용을 script에서 가져다 쓰기 위해 
        3번째 요소 부터 보이지 않도록 처리 */
        tr>td>p:nth-child(n+3) {
            display: none;
        }

        /* 원본 URL이 너무 길 경우 ... 처리 */
        #secondDivRight>p{
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space:nowrap;
        }

        /* a 태그 밑줄 + 파란색 없애기 */
        a:link {
            color:black;
            text-decoration: none;
        }

        a:visited {
            color:black;
            text-decoration: none;
        }

        a:hover {
            color:black;
            text-decoration: none;
        }

        a:active {
            color:black;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="col-md-6">👻Short URL Project</h1>
        <div class="row g-3" id="secondDiv">
            <div class="col-md-4" id="secondDivLeft">
                <h3 id="title">Title</h3>
                <p>날짜 by writer</p>
            </div>
            <div class="col-md-4" id="secondDivRight">
                <h3>ShortUrl</h3>
                <p><a href="#">originUrl</a></p>
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end col-md-4" id="buttons">
                <form action="/shortUrl/delete" method="post">
                    <input type="hidden" name="index" value="" id="deleteButton">
                    <input type="submit" value="Delete" class="btn btn-outline-danger btn-sm">
                </form>
                <button class="btn btn-outline-primary btn-sm" type="button" onclick="editButtonOnclick(this)">Edit</button>
                <button class="btn btn-primary btn-sm" type="button" onclick="location.href='/shortUrl/createPage'">Create new</button>
            </div>
        </div>
        
        <div>
            <table class="table table-hover" id="shortUrlList">
                <c:forEach var="list" items="${shortUrlList}">
                    <tr onclick="changeColorRowAndSecondDivSetting(this)">
                        <td>
                            <p>${list.title}</p>
                            <p id="short">${list.shortUrl}</p>
                            <p>${list.index}</p>
                            <p>${list.lastUpdateDate}</p>
                            <p>${list.originUrl}</p>
                            <p>${list.writer}</p>
                            <p>${list.count}</p>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <script>
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
            const [$title, $shortUrl, $index, $lastUpdateDate, $originUrl, $writer, $count] = obj.getElementsByTagName("p");

            // 제목
            document.querySelector("#secondDivLeft>h3").textContent = $title.textContent;
            // 날짜 + 작성자
            document.querySelector("#secondDivLeft>p").textContent = $lastUpdateDate.textContent + " by " + $writer.textContent;
            // 짧은 Url
            document.querySelector("#secondDivRight>h3").textContent = $shortUrl.textContent;
            // URL 원본 TextSetting + href설정 + 클릭시 새창으로 열기
            document.querySelector("#secondDivRight a").textContent = decodeURI($originUrl.textContent);
            document.querySelector("#secondDivRight a").href = decodeURI($originUrl.textContent);
            document.querySelector("#secondDivRight a").target = "_blank";

            // Delete시 넘겨줄 파라미터 세팅
            document.getElementById("deleteButton").value = $index.textContent;
        }

        function editButtonOnclick(obj){
            let [$title, $shortUrl] = document.querySelectorAll("h3");
            location.href = "/shortUrl/update?shortUrl=" + $shortUrl.textContent;
        }

    </script>
</body>
</html>