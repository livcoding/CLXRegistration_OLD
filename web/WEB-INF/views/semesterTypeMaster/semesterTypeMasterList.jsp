<%-- 
    Document   : subjectTypeMasterList
    Created on : Dec 12, 2018, 10:22:35 AM
    Author     : ankur.goyal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>

<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class=""  >
                <th style="display:none"></th>
                <th width="" >Sl.No</th>
                <th width="" >Semester Type Code</th>
                <th width="" >Semester Type Description</th>
                <th width="" >Seq ID</th>
                <th width="" >Grade Improvement</th>
                <th width="" >Deactive</th>
            </tr>
        </thead> 
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${semesterList}" var="list">
                <c:if test="${list[2] == 'REG' ||list[2] == 'RWJ' ||list[2] == 'SUP'||list[2] == 'SAP'||list[2] == 'GIP'}">
                    <tr id="${list[0]}:${list[1]}">
                        <td style="display:none"> </td>
                        <td align="center">
                            <%=i%>
                        </td>
                        <td>${list[2]}</td>
                        <td>${list[3]}</td>
                        <td>${list[4]}</td>
                        <c:if test="${list[5]=='Y'}">
                            <td><c:out value="Yes"/></td>    
                        </c:if>
                        <c:if test="${list[5]=='N'}">
                            <td><c:out value="No"/></td>    
                        </c:if>
                        <c:if test="${list[6]=='Y'}">
                            <td><c:out value="Yes"/></td>    
                        </c:if>
                        <c:if test="${list[6]!='Y'}">
                            <td><c:out value="No"/></td>    
                        </c:if>
                    </tr>
                    <% i++;%>
                </c:if>
            </c:forEach>   
        </tbody>
    </table>
</div> 

<script>
    var val = getValue();
    var link = val == 'N' ? 'semesterTypeMaster/add' : '';
    var cb = {
        add: {
            link: link,
            name: "Add",
            value: ""
        },
        edit: {
            link: "semesterTypeMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "semesterTypeMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "semesterTypeMaster/list"
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
            url: "semesterTypeMaster/checkIfChildExist",
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



