<%-- 
    Document   : sis_RegistrationActivityMasterList
    Created on : Feb 14, 2019, 5:27:46 PM
    Author     : ankur.goyal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>

<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class=""  >
                <th Style="display:none;"></th>
                <th width="" >Sl.No.</th>
                <th width="" >Activity Name</th>
                <!--                <th width="" >Entry By</th>
                                <th width="" >Entry Date</th>
                                <th width="" >Updated By</th>
                                <th width="" >Updated Date</th>-->
                <th width="" >Fee Head</th>
                <th width="" >Deactive</th>
            </tr>
        </thead>
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${regList}" var="list">
                <tr id="${list[0]}">
<!--                    <td id="${list[0]}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list[0]}"/>
                        <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(<%=i%>);"/>
                        <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                    </td>-->
                    <th Style="display:none;"></th>
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list[2]}</td> <%-- Activity Name --%>
                <!--   <td>${list[5]}</td> <%-- Entry By --%>
                    <td>${list[6]}</td> <%-- Entry Date --%>
                    <td>${list[7]}</td> <%-- Updated By --%>
                    <td>${list[8]}</td> <%-- Updated Date --%>
                    <%-- Deactive --%>--->
                    <td>${list[10]}-(${list[9]})</td>
                    <c:if test="${list[4]=='Y'}">
                        <td><c:out value="YES"/></td>   
                    </c:if> 
                    <c:if test="${list[4]!='Y'}">
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
    var link = val == 'N' ? 'registrationActivityMaster/add' : '';
    var cb = {
        add: {
            link: link,
            name: "Add",
            value: ""
        },
        edit: {
            link: "registrationActivityMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "registrationActivityMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "registrationActivityMaster/list"
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
            url: "registrationActivityMaster/checkIfChildExist",
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



