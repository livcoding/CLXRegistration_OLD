<%-- 
    Document   : sis_StudentRegActivitiesList
    Created on : Feb 16, 2019, 11:00:07 AM
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
                <th width="" >Activity Name</th>
                <th width="" >Activity By (Code / Name)</th>
                <th width="" >From Date</th>
                <th width="" >To Date</th>
                <th width="" >Deactive</th>
            </tr>
        </thead>  
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${sraList}" var="list">
                <tr id="${list[0]}~@~${list[1]}~@~${list[2]}~@~${list[3]}">
<!--                    <td id="${list[0]}~@~${list[1]}~@~${list[2]}~@~${list[3]}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list[0]}~@~${list[1]}~@~${list[2]}~@~${list[3]}"/>
                        <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(<%=i%>);"/>
                        <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                    </td>-->
                <td style="display:none"> </td> 
                    <td align="center">
                        <%=i%>
                    </td>
                    <%-- ${list[0]}--> Instituteid --%>
                    <%-- ${list[1]} --> Activityid --%>
                    <%-- ${list[2]} --> Staffid    --%>
                    <%-- ${list[3]} --> Staff Type --%>
                    <td>${list[7]}</td> <%-- Activity Name --%>
                    <td>${list[9]} / ${list[10]}</td> <%-- Activity By--%>
                    <td>${list[4]}</td> <%-- From Date --%>
                    <td>${list[5]}</td> <%-- To Date --%>
                    <c:if test="${list[6]=='Y'}">
                        <td><c:out value="YES"/></td>   
                    </c:if> 
                    <c:if test="${list[6]!='Y'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>  <%-- Deactive --%>
                </tr>
                <% i++;%>
            </c:forEach>   
        </tbody>
    </table>
</div> 

<script>

    var cb = {
        add: {
            link: "registrationActivityGrants/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "registrationActivityGrants/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "registrationActivityGrants/delete",
            name: "",
            value: ""
        },
        list: {
            link: "registrationActivityGrants/list"
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
            $("#del_img" + index).hide();
            $("#d_img" + index).show();
        }
    }

</script>
