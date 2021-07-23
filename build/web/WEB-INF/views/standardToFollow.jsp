<%-- 
    Document   : standardToFollow
    Created on : Sep 8, 2018, 10:03:18 AM
    Author     : mohit1.kumar
--%>
<%@ include file="mainjstl.jsp" %>
<!--views/registration.jsp [Registration page to create and save new employee in database]-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%--   List page  code Statrs here--%>

<script>
    var cb = {
        add: {
            link: "link name",
            name: "Add",
            value: ""
        },
        edit: {
            link: "link name",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "link name",
            name: "Delete",
            value: ""
        },
        list: {
            link: "link name"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }

    }

    commonButtonMethod(cb);
</script>


<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="">
                <th width="10px;" >Sl.No</th>
                <th width="30px;" >Country Name</th>
                <th width="15px;" >Country Code</th>
                <th width="15px;" >Seq Id</th>
                <th width="15px;" >Deactive</th>
            </tr>
        </thead>  
        <tfoot>
            <tr id="filterrow" class="no-print">
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>

            </tr>
        </tfoot>
        <tbody>
            <% int i = 1;%>
            <c:forEach items="${list}" var="list">

                <tr id="${list.countryid}">
                    <c:set var="nrows" value="1"/>
                    <td align="center">
                        <%=i%>
                    </td>
                    <td>${list.countryname}</td>
                    <td>${list.countrycode}</td>
                    <td> ${list.seqid}</td>
                    <td>${list.deactive}</td>
                </tr>
                <% i++;%>
                <%--<c:set var="nrows" value="1"/>--%>
            </c:forEach>   
        </tbody>
    </table>
</div> 


<%--  List page code ends here       --%>



<%--  Add/Edit page code Starts here       --%>


<form method="POST" modelAttribute="" id="ajaxform" class="form-horizontal">
    <!--<form id="ajaxform" class="form-horizontal">-->

    <div class="row col-lg-12 form-group"> <br/></div>
    <div class="row col-lg-12 form-group" id="divContainer">                 
        <div style="height: 12px"><br/></div>

        <div class="row col-lg-12 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Name<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <input type="text" data-validation-help="Please give us some more information"  data-validation="length" data-validation-length="min2" id="name" maxlength="50" placeholder="Enter Name" path="name" class="form-control has-help-txt"/>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Joining Date<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <input type="text" data-validation-help="Please give us some more information"  data-validation="required" data-validation-length="" id="joiningDate" maxlength="" placeholder="Enter Joining Date" path="joiningDate" class="form-control has-help-txt"/>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Salary<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <input type="text" data-validation-help="Please give us some more information"  data-validation="required" data-validation-length="" id="salary" maxlength="" placeholder="Enter Salary" path="salary" class="form-control has-help-txt"/>
            </div>
        </div>
        
        <%--  for dropdown --%>
        
        <div class="row col-lg-12 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Country Code:
                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <select class="form-control" id="countryCode" name="countryCode" data-validation="required" data-live-search="true">
                    <option value="">Select</option>
                    <c:forEach items="${countryList}" var="cntryList">
                        <option value="${cntryList.countryid}"><c:out value="${cntryList.countrycode} / ${cntryList.countryname}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
        
        <div class="row col-lg-12 form-group">
            <label  class="col-sm-2 col-lg-3 col-xs-2 col-md-2 control-label">SSN
                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <input type="text" data-validation-help="Please give us some more information"  data-validation="required" data-validation-length="" id="ssn" maxlength="" placeholder="Enter ssn" path="ssn" class="form-control has-help-txt"/>
            </div>
        </div>
        
        <%-- for datepicker --%>
        
        <div class="row col-lg-12 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Date:
                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <div class='input-group date' id='datetimepicker1'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>          
            </div>
        </div>

        <!--        for dual list box -->

        <div class="row col-lg-12 form-group" >
            <select multiple="multiple" size="10" name="duallistbox">
                <option value="option1">Option 1</option>
                <option value="option2">Option 2</option>
                <option value="option3" selected="selected">Option 3</option>
                <option value="option4">Option 4</option>
                <option value="option5">Option 5</option>
                <option value="option6" selected="selected">Option 6</option>
                <option value="option7">Option 7</option>
                <option value="option8">Option 8</option>
                <option value="option9">Option 9</option>
                <option value="option0">Option 10</option>
            </select>  
        </div>

        <%-- for buttons --%>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:callSubmit();" class="btn btn-success btn-sm btn-flat"> Submit</a>
                <button class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>

        <!--ends-->

        <!-- for tabs -->
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
            <li><a data-toggle="tab" href="#menu1">Menu 1</a></li>
            <li><a data-toggle="tab" href="#menu2">Menu 2</a></li>
            <li><a data-toggle="tab" href="#menu3">Menu 3</a></li>
        </ul>

        <div class="tab-content">
            <div id="home" class="tab-pane fade in active">
                <h3>HOME</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
            </div>
            <div id="menu1" class="tab-pane fade">
                <h3>Menu 1</h3>
                <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
            </div>
            <div id="menu2" class="tab-pane fade">
                <h3>Menu 2</h3>
                <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
            </div>
            <div id="menu3" class="tab-pane fade">
                <h3>Menu 3</h3>
                <p>Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
            </div>
        </div>

        <!--ends-->
    </div>
</form>
      
<script>
//    $("#id").Chosen();

    setTimeout(
            function () {

                $.validate(
                        {
//                            modules: 'file, date',
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                $.ajax({
                                    type: "POST",
                                    url: "new",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadForm("list");
                                        } else {

                                            toastr.error(Error['error_code'], "Error!!");
                                        }
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);


    function callSubmit() {
        $("#ajaxform").submit();
    }
    $(function () {
        $('#datetimepicker1').datetimepicker();
    });
    
    //for grid without add/edit button
      table1 = $('#itemListChoice').DataTable({
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
   
   
</script>

<%-- Add/Edit page ends here  --%>



 <%-- code for excel button only this has to add before the table 
    
<div id="specialCase_Button" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    
    and  following should be added in script 
    
      var btns = [];
    table1 = $('#itemListChoice').DataTable({
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
    btns.push({
        extend: 'excel',
        text: '<i class=\"fa fa-lg fa-file-excel-o\"></i>',
        exportOptions: {
            modifier: {
                page: 'all'
            }
        }
    });
    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(table1, {
                    buttons: btns
                });
                $('#specialCase_Button').append(table1.buttons(0, null).container());
            }, 100);

    --%>

