<%-- 
    Document   : subjectMasterList
    Created on : 17 Nov, 2018, 10:29:19 AM
    Author     : Nazar.Mohammad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>

<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th style="display:none"></th>
                <th width="2px;" >Sl.No</th>
                <th width="30px;" >Subject Code</th>
                <th width="30px;" >Subject Desc.</th>
                <th style="width:200px;">Subject Pattern</th>
                <th width="30px;" >Subject Type</th>
                <th width="20px;" >Short Name</th>
                <th width="20px;" >Deactive</th>
            </tr>
        </thead> 
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${subjectList}" var="list" >
                <tr id="${list.getIdStr()}">
<!--                    <td id="${list.getIdStr()}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list.getIdStr()}"/>
                        <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(<%=i%>);"/>
                        <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                    </td>-->
                    <td style="display:none"> </td>
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list.subjectcode}</td>
                    <td>${list.subjectdesc}</td>
                    <td style="width:200px;">${list.ex_patternmaster.patternname}</td>
                    <c:if test="${list.subjectflag=='G'}">
                        <td>General</td>
                    </c:if>
                    <c:if test="${list.subjectflag=='W'}">
                        <td>Workshop</td>
                    </c:if>
                    <c:if test="${list.subjectflag=='S'}">
                        <td>Sport</td>
                    </c:if>
                    <c:if test="${list.subjectflag=='A'}">
                        <td>Audit</td>
                    </c:if>
                    <c:if test="${list.subjectflag=='N'}">
                        <td>Major Project Subject</td>
                    </c:if>
                    <c:if test="${list.subjectflag=='M'}">
                        <td>Minor Project Subject</td>
                    </c:if>
                    <c:if test="${list.subjectflag==null || list.subjectflag==''}">
                        <td>--</td>
                    </c:if>
                    <c:if test="${list.deactive=='Y'}">
                        <td><c:out value="YES"/></td>   
                    </c:if> 
                    <c:if test="${list.deactive!='Y'}">
                        <td><c:out value="NO"/></td>    
                    </c:if> 
                </tr>
                <% i++;%>
            </c:forEach>   
        </tbody>
    </table>

</div> 

<script>
    var val = getValue();
    var link = val == 'N' ? 'subjectMaster/add' : '';
    var cb = {
        add: {
            link: link ,
            name: "Add",
            value: ""
        },
        edit: {
            link: "subjectMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "subjectMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "subjectMaster/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }

    }

    commonButtonMethod(cb);

    function callChkBox(index) {
        var value = $("#chk" + index).val();
        if ($("#chk" + index).prop('checked') == true) {
            $("#chk" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk" + index).prop("checked", true);
            checkChild(value, index);
        }
    }

    function checkChild(v, i) {
        $.ajax({
            url: "subjectMaster/checkIfChildExist",
            datatype: 'String',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "pk=" + v,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                if (data === "true") {
                    BootstrapDialog.alert("Child exists for this record!");
                    $("#chk" + i).prop("checked", false);
                    $("#chk" + i).attr("disabled", true);
                } else {
                    $("#del_img" + i).hide();
                    $("#d_img" + i).show();
                }
            }
        });
    }

</script>