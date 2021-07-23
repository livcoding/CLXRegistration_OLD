<%-- 
    Document   : subjectComponentMasterList
    Created on : 5 Dec, 2018, 4:56:32 PM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>

<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class=""  >
                <th style="display:none"></th>  
                <th width="2px;" >Sl.No</th>
                <th width="15px;" >Subject Component Code</th>
                <th width="37px;" >Subject Component Description</th>
                <th width="15px;" >No. of Days per Week</th>
                <th width="15px;" >Seq ID</th>
                <th width="15px;" >De-active</th>
            </tr>
        </thead>  
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${subjectComponentList}" var="list">

                <tr id="${list.getIdStr()}">
                    <td style="display:none"> </td>
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list.subjectcomponentcode}</td>
                    <td>${list.subjectcomponentdesc}</td>
                    <td>${list.classperweekformula}</td>
                    <td>${list.seqid}</td>
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
    var link = val == 'N' ? 'subjectComponentMaster/add' : '';
            var cb = {
                add: {
                    link: link ,
                    name: "Add",
                    value: ""
                },
                edit: {
                    link: "subjectComponentMaster/edit",
                    name: "Edit",
                    value: ""
                },
                delete: {
                    link: "subjectComponentMaster/delete",
                    name: "",
                    value: ""
                },
                list: {
                    link: "subjectComponentMaster/list"
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
        $("#chk" + index).prop("checked", true);
        checkChild(value, index);
    }

    function checkChild(v, i) {
        $.ajax({
            url: "subjectComponentMaster/checkIfChildExist",
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
                    $("#d_img" + i).removeClass("hide");
                }
            }
        });
    }
</script>