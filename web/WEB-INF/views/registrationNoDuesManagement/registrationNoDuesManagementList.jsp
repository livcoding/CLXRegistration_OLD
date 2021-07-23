<%-- 
    Document   : registrationNoDuesManagementList
    Created on : Feb 12, 2019, 4:02:08 PM
    Author     : ashutosh1.kumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="row" id="dashboard">
    <div class="col-md-12">
        <div class="box" style="box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
            <div class="box-header with-border bg-navy">
                <h3 class="box-title header_name" id="box-title"></h3>
                <div class="box-tools pull-right">                  
                    <button onclick="javascript:blank();" class="btn btn-box-tool"><i class="fa fa-close fa-2x" style="color:white"></i></button>
                </div>
            </div>
            <div class="box-body" >
                <div class="row col-lg-12 form-group" >
                    <div class="row col-lg-6 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-4 col-lg-4 col-xs-4 col-md-4 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                            <select class="form-control" id="regCode" name="regCode" onchange="getAcademicYear(this.value);" data-validation="required" data-live-search="true">               
                                <option value="">Select</option>
                                <c:forEach items="${data}" var="reList">
                                    <option value="${reList[0]}"><c:out value="${reList[1]}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>  
                    <div class="row col-lg-6 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-4 col-lg-4 col-xs-4 col-md-4 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                            <select class="form-control" id="acadYear" name="acadYear" onchange="emptyRegistrationNo();getProgramCode();" data-validation="required" data-live-search="true">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>  
                </div>
                <div class="row col-lg-12 form-group" >
                    <div class="row col-lg-6 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-4 col-lg-4 col-xs-4 col-md-4 control-label">Program Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                            <select class="form-control" id="programid" name="programid"  data-validation="required" data-live-search="true">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>  
                    <div class="row col-lg-6 form-group">
                        <div class="col-sm-2 col-lg-1 col-xs-2 col-md-2">                                    
                            <a href="javascript:getEnrollmentNo(),loadStudentWiseNoDuesData();loadAcdWiseNoDuesData();" class="btn btn-success btn-sm">Load Students</a>&nbsp; &nbsp; 
                        </div>
                    </div>  
                </div>
                <div class="col-md-12">
                    <div class="box-body" >
                        <div id="rootwizard" class="col-lg-12">
                            <div class="navbar">
                                <div class="navbar-inner">
                                    <div class="container col-lg-12" >
                                        <ul class="nav nav-tabs" style="background: #cccccc; width: 350px;">
                                            <li onclick="getTab1Data();" class="active"><a href="#tab1"  data-toggle="tab"><b>Student Wise No Dues</b></a></li>
                                            <li onclick="getTab2Data();"><a href="#tab2" data-toggle="tab"><b>Academic Year Wise</b></a></li>                   
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab1">
                                    <%@include file="studentWiseNoDues.jsp"%>            
                                </div>
                                <div class="tab-pane" id="tab2">
                                    <%@include file="academicYearWise.jsp"%>
                                </div>

                            </div>
                            <input type="hidden" id='form_id' class="">   
                            <input type="hidden" class="" id='url'>
                            <input type="hidden" class="styType" id="styType" value=""/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#regCode").chosen();
    $("#acadYear").chosen();
    $("#programid").chosen({width: '100%'});
    function getTab1Data()
    {
        var regCode = $("#regCode").val();
        var acadYear = $("#acadYear").val();
        if (regCode != '' && acadYear != '') {
            loadStudentWiseNoDuesData();
            getEnrollmentNo();
        } else {
            BootstrapDialog.alert("Please Select Registration  Code And Academic Year first!");
        }
    }
    function getTab2Data()
    {
        var regCode = $("#regCode").val();
        var acadYear = $("#acadYear").val();
        if (regCode != '' && acadYear != '') {
            loadAcdWiseNoDuesData();
        } else {
            BootstrapDialog.alert("Please Select Registration  Code And Academic Year first!");
        }
    }


    function setFormIdAndUrl(id, url, tabNo) {
        $("#form_id").val(id);
        $("#url").val(url);
        $("#ajaxform" + tabNo).submit();
    }

    function getAcademicYear(id) {
        $("#programid").empty();
        $("#programid").trigger("chosen:updated");
        $("#acadYear").empty();
        if (id != '') {
            $.ajax({
                url: "registrationNoDuesManagement/getAcademicYear",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regCode=" + id,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.acadyear != null) {

                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.acadyear, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#acadYear").append(str1);
                        $("#acadYear").trigger("chosen:updated");

                    } else {
                        BootstrapDialog.alert("Academic Year Not Found, Please Select Another Semester Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }
    function getProgramCode() {
        var reg = $("#regCode").val();
        var acad = $("#acadYear").val();
        if (reg != '' && acad != '') {
            $("#programid").empty();
            $.ajax({
                type: "POST",
                url: "registrationNoDuesManagement/getProgramCode",
                data: '&regCode=' + reg + '&acadYear=' + acad,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    str1 += '<option value="All">ALL</option>';
                    $.each(data.progList, function (i, item)
                    {
                        debugger;
                        str1 += '<option value="' + item[0] + '">' + item[1] + ' -- ' + item[2] + '</option>'
                    });
                    $("#programid").append(str1);
                    $("#programid").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code or Academic Year..!");
        }
    }
    function emptyRegistrationNo()
    {
        $("#regNo").empty();
        $("#regNo").trigger("chosen:updated");
    }


    setTimeout(
            function () {
                $.validate(
                        {
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                var formData = new FormData();
                                var url = $("#url").val();
                                var form = document.getElementById($("#form_id").val());
                                formData = new FormData(form);
                                formData.append('acadYear', $('#acadYear').val());
                                formData.append('regCode', $('#regCode').val());
                                $.ajax({
                                    type: "POST",
                                    url: url,
                                    data: formData,
                                    dataType: "json",
                                    async: false,
                                    processData: false,
                                    contentType: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data.status === 'Success') {
                                            toastr.success(Success['save_success'], "Success!!");
//                                            getEnrollmentNo();
//                                            loadStudentWiseNoDuesData();
//                                            loadAcdWiseNoDuesData();
//                                          loadForm("registrationNoDuesManagement/list");
                                        } else if (data.status === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data.status);
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
</script>
