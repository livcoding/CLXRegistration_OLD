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
                <th width="" >Subject Type</th>
                <th width="" >Subject Type Description</th>
                <th width="" >Seq ID</th>
                <th width="" >Deactive</th>
            </tr>
        </thead> 
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${subjectList}" var="list">
                <c:if test="${list.subjecttype == 'P' ||list.subjecttype == 'S' ||list.subjecttype == 'E'||list.subjecttype == 'I'||list.subjecttype == 'O'||list.subjecttype == 'C'}">

                    <tr id="${list.getIdStr()}">
    <!--                    <td id="${list.getIdStr()}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list.getIdStr()}"/>
                            <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(<%=i%>);"/>
                            <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                        </td>-->
                        <td style="display:none"> </td>
                        <td align="center">
                            <%=i%>
                        </td>
                        <td>${list.subjecttype}</td>
                        <td>${list.subjecttypedesc}</td>
                        <td>${list.seqid}</td>
                        <c:if test="${list.deactive=='Y'}">
                            <td><c:out value="YES"/></td>   
                        </c:if> 
                        <c:if test="${list.deactive!='Y'}">
                            <td><c:out value="NO"/></td>    
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
    var link = val == 'N' ? 'subjectTypeMaster/add' : '';
    var cb = {
        add: {
            link: link,
            name: "Add",
            value: ""
        },
        edit: {
            link: "subjectTypeMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "subjectTypeMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "subjectTypeMaster/list"
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
            url: "subjectTypeMaster/checkIfChildExist",
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



