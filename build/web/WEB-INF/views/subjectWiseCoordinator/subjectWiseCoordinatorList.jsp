<%-- 
    Document   : subjectWiseCoordinatorList
    Created on : 18 Oct, 2019, 4:56:32 PM
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
                <th width="2px;" >S.No.</th>
                <th width="15px;" >Subject Code / Description</th>
                <th width="40px;" >Coordinator Code / Name</th>
                <th width="15px;" >Coordinator Type</th>
                <th width="10px;" >Chief Coordinator</th>
                <th width="10px;" >Status</th>
                <th width="10px;" >Change Status</th>
                
            </tr>
        </thead> 
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${data}" var="list" >
                <tr>
                    <th style="display:none"></th> 
                    <td><%=i%></td> 
                    <td>${list[2]} / ${list[3]}</td>
                    <td>${list[5]} / ${list[6]}</td>
                    <c:if test="${list[7]=='E'}">
                        <td>Employee</td>
                    </c:if>
                    <c:if test="${list[7]=='A'}">
                        <td>Admin</td>
                    </c:if>
                    <c:if test="${list[7]!='A' && list[7]!='E'}">
                        <td>Staff</td>
                    </c:if>
                      <c:if test="${list[9]!='Y'}">
                        <td><c:out value="NO"/></td>    
                    </c:if>
                    <c:if test="${list[9]=='Y'}">
                        <td><c:out value="YES"/></td>    
                    </c:if>                          
                    <c:if test="${list[8]=='Y'}">
                        <td>Deactive</td>
                    </c:if>
                    <c:if test="${list[8]!='Y'}">
                        <td>Active</td>
                    </c:if>
                    <c:if test="${list[8]=='Y'}">
                        <td><a class="btn btn-info" href="javascript:ActiveDeactive('${list[1]}~@~${list[4]}~@~${list[7]}~@~${list[8]}');">Active</a></td>
                    </c:if>
                    <c:if test="${list[8]!='Y'}">
                        <td><a class="btn btn-info" href="javascript:ActiveDeactive('${list[1]}~@~${list[4]}~@~${list[7]}~@~${list[8]}');">Deactive</a></td>
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
            link: "subjectWiseCoordinator/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "",
            name: "",
            value: ""
        },
        delete: {
            link: "subjectComponentMaster/delete",
            name: "",
            value: ""
        },
        list: {
            link: "subjectWiseCoordinator/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }

    function ActiveDeactive(ids) {
        var subid = ids.split('~@~')[0];
        var stafid = ids.split('~@~')[1];
        var stafftype = ids.split('~@~')[2];
        var deactive = ids.split('~@~')[3];

        $.ajax({
            type: "POST",
            url: "subjectWiseCoordinator/update",
            data: '&subid=' + subid + '&stafid=' + stafid + '&stafftype=' + stafftype + '&deactive=' + deactive,
            datatype: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data === 'Success') {
                    toastr.success(Success['update_success'], "Success!!");
                    loadForm("subjectWiseCoordinator/list");
                } else if (data === 'Error') {
                    toastr.error('Form Submission Failed.', "Error!!");
                } else {
                    BootstrapDialog.alert(data);
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }



    commonButtonMethod(cb);

    //    function callChkBox(index) {
    //        var value = $("#chk" + index).val();
    //        $("#chk" + index).prop("checked", true);
    //        checkChild(value, index);
    //    }


</script>