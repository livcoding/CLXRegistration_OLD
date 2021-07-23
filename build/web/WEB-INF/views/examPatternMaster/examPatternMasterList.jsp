<%-- 
    Document   : ExamPatterMasterAdd
    Created on : 8 AUG: 2019 
    Author     : Malkeet Singh
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>

<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class=""  >
                <th style="display:none"> </th>
                <th>Sl.No.</th>
                <th>Pattern Code</th>
                <th>Pattern Description</th>
                <th>MIN Attendance</th>
                <th>Deactive</th>
            </tr>
        </thead> 
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${examPatternList}" var="list">
                <tr id="${list.id.instituteid}':'${list.id.patternid}">
                    <td style="display:none"> </td>
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list.patterncode}</td>
                    <td>${list.patternname}</td>
                    <td>${list.minattendance}</td>
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
    var link = val == 'N' ? 'examPatternMaster/add' : '';
    var cb = {
        add: {
            link: link,
            name: "Add",
            value: ""
        },
        edit: {
            link: "examPatternMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "examPatternMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "examPatternMaster/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);



</script>



