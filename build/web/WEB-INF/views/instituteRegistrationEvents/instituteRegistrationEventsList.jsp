<%-- 
    Document   : instituteRegistrationEventsList
    Created on : Jan 15, 2019, 12:04:50 PM
    Author     : ashutosh1.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<script>
    var cb = {
        add: {
            link: "instituteRegistrationEvents/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "instituteRegistrationEvents/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "instituteRegistrationEvents/delete",
            name: "Delete",
            value: ""
        },
        list: {
            link: "instituteRegistrationEvents/list"
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
        $("#del_img" + i).hide();
        $("#d_img" + i).show();
    }


</script>


<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th ></th>
                <th>Sl.No.</th>
                <th>Semester Code</th>
                <th>Semester Desc.</th>
                <th>Pre-registration Allowed</th>
                <th>Attendance Entry Allowed</th>
                <th>Marks Entry Allowed</th>
                <th>Grade Entry Allowed</th>
                <th>SRS Allowed</th>
                <th>Supplementary Request Allowed</th>
                <th>Hostel Allocation Allowed</th>
            </tr>
        </thead> 
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${data}" var="list">
                <tr id="${list[0]}:${list[1]}">
                    <td id="${list[0]}:${list[1]}"><input type="checkbox" class="hidden" id="chk<%=i%>" value="${list[0]}:${list[1]}"/>
                        <img id="del_img<%=i%>" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(<%=i%>);"/>
                        <img id="d_img<%=i%>" style="display: none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(<%=i%>);"/>
                    </td>
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list[2]}</td>
                    <td>${list[3]}</td>
                    <c:if test="${list[4]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[4]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[5]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[5]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[6]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[6]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[7]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[7]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[8]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[8]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[9]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[9]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[10]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>
                    <c:if test="${list[10]=='N'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                </tr>
                <% i++;%>
            </c:forEach>  
        </tbody>
    </table>
</div> 

