<%-- 
    Document   : instituteCompanyPopup
    Created on : Nov 5, 2018, 12:53:09 PM
    Author     : mohit1.kumar
--%>
<%--<%@ include file="mainjstl.jsp" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>CampusLynx | CLXRegistration</title>
        <link rel = "icon" href ="resources/img/CLX.png" type = "image/x-icon"> 
        <link rel="stylesheet" href="resources/bootstrap/bootstrap.css">
        <link rel="stylesheet" href="resources/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/chosen.min.css" type="text/css">
        <link rel="stylesheet" href="resources/css/bootstrap-dialog.min.css" type="text/css">
        <link rel="stylesheet" href="resources/css/bootstrap-modal.min.css" type="text/css">
        <style>
            .maintest{
                background-color:white;
                position:fixed;
                right:0px;
                height:100%;
            }
            .hiddenlogo{
                text-align:center;
                margin-bottom:10px;
                margin-left:-1000000px;
            }
            @media(max-width:768px){
                .hiddenlogo{
                    margin-left:0px;
                }
                .maintest{
                    width:100%;
                    text-align: center;
                }
            }
            #institute_chosen{
                text-align:left;
            }
            .modal-header, h4, .close {
                background-color: rgb(60, 141, 188);/*#5cb85c*/
                color:white !important;
                text-align: center;
                font-size: 30px;
            }

            .modal {
                position: fixed;
                width: 800px;
                margin:auto;
                margin-top:-200px;
                overflow: auto;
                -moz-background-clip: padding-box;
                background-clip: padding-box;
            } 
        </style>
    </head>
    <body>
        <div style="background-image:url('resources/img/dark-material-bg.jpg');
             background-position: center;width:100%;height:100%;background-size: cover;overflow:hidden">
            <div style="width:67%;float:left">
                <div class="row" style="padding:12%">
                    <div style="margin-bottom:20px">
                        <img src="resources/img/campusLynx.png" style="width:130px;height:60px"/>   
                    </div>
                    <div style="color:white;font-size:42px;">Welcome to the CampusLynx!
                    </div>
                    <div>
                        <marquee direction="up" style="height:150px;margin-top:20px;margin-bottom:20px;color:white">Hello ......Admin,Welcome to <b>ITER</b></marquee>
                    </div>
                    <div style="color:white;font-size:42px;">
                        <img src="resources/img/jilitLogo.png" />
                    </div>
                </div>
            </div>
            <div class="maintest" >
                <div style="padding:60px;">
                    <form method="POST" id="ajaxform" class="form-horizontal" action="loadDashboard">
                        <div class="hiddenlogo" >
                            <img src="resources/img/campusLynx.png" style="width:130px;height:60px;margin:auto;"/>
                        </div>
                        <div class="row" style="font-size:21px;margin-bottom:55px">Select Institute</div>
                        <div class="row" >
                            <select class="form-control" id="institute" name="institute" data-validation-help="please select some value" data-validation="required" data-live-search="true">
                                <option value="">Select</option>
                                <c:forEach items="${instData}" var="instList">
                                    <option value="${instList[0]}@${instList[1]}"><c:out value="${instList[1]} / ${instList[2]}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="row" style="margin-top:35px;">
                            <a href="javascript:callSubmit();" class="btn btn-md inline" style="font-size:15px;margin-top:7px;background-color:#039be5;width:300px;color:white;box-shadow:1px 1px 1px 1px lightgray">SUBMIT</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="resources/jquery/jquery.min.js"></script>
        <script src="resources/bootstrap/bootstrap.min.js"></script>
        <script src="resources/js/bootstrap-dialog.min.js"></script>
        <script src="resources/js/chosen.jquery.min.js"></script>
    </body>

    <script>
        $(window).on('keydown', function (event)
        {
            if (event.keyCode == 123)
            {
                return false;
            } else if (event.ctrlKey && event.shiftKey && event.keyCode == 73)
            {
                return false;  //Prevent from ctrl+shift+i
            }
        });
        $(document).on("contextmenu", function (e)
        {
            e.preventDefault();
        });
        $("#company").chosen({width: "300px"});
        $("#institute").chosen({width: "300px"});
        $(document).ready(function () {
            $("#myModal").modal("show");
        });
        function callSubmit() {
            if ($("#institute").val() != '' && $("#company").val() != '') {
                $("#ajaxform").submit();
            } else {
                BootstrapDialog.alert("Please select the required field!!");
            }
        }
    </script>
</html>