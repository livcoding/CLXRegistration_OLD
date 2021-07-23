<%-- 
    Document   : coordinatorTypeMasterList
    Created on : Nov 8, 2019, 10:39:36 AM
    Author     : priyanka.tyagi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>

<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class=""  >
                <th style="display:none"></th>  
                <th width="2px;" >Sl.No.</th>
                <th width="37px;" >Coordinator Type</th>
                <th width="15px;" >Coordinator Type Code</th>
                <th width="37px;" >Coordinator Type Description</th>
                <th width="15px;" >Coordinator Type Based On</th>  
                <th width="15px;" >Student Limit</th>
                <!--<th width="15px;" >Seq ID</th>-->                
                <th width="15px;" >Deactive</th>
            </tr>
        </thead>  
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${coordinatorTypeList}" var="list">

                <tr id="${list.getIdStr()}">
                    <td style="display:none"> </td>
                    <td align="center">
                        <%=i%>
                    </td>
                    <c:if test="${list.coordinatortype=='A'}">
                        <td><c:out value="Student Advisors"/></td>
                    </c:if>
                    <c:if test="${list.coordinatortype=='E'}">
                        <td><c:out value="External Marks"/></td>
                    </c:if>
                    <c:if test="${list.coordinatortype=='C'}">
                        <td><c:out value="Subject"/></td>
                    </c:if>
                    <c:if test="${list.coordinatortype=='M'}">
                        <td><c:out value="Marks"/></td>
                    </c:if>
                    <c:if test="${list.coordinatortype=='T'}">
                        <td><c:out value="Attendance"/></td>
                    </c:if>
                    <c:if test="${list.coordinatortype=='G'}">
                        <td><c:out value="Grade"/></td>
                    </c:if>
                    <td>${list.coordinatortypecode}</td>
                    <td>${list.coordinatortypedesc}</td>
                    <c:if test="${list.groupflag=='F'}">
                        <td><c:out value="FST Based"/></td>
                    </c:if>
                    <c:if test="${list.groupflag=='S'}">
                        <td><c:out value="Student Based"/></td>
                    </c:if>
                    <td>${list.studentlimit}</td>
                    <!--<td>${list.seqid}</td>-->
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

    var cb = {
        add: {
            link: "coordinatorTypeMaster/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "coordinatorTypeMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "subjectComponentMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "coordinatorTypeMaster/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);

</script>
