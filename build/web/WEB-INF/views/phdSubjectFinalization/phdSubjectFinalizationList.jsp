<%-- 
    Document   : phdSubjectFinalizationList
    Created on : Mar 5, 2019, 2:22:13 PM
    Author     : ankit.kumar
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
                        <label style="text-transform: capitalize;text-align:right" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                            <select class="form-control" id="registrationid" name="registrationid" onchange="setRegDesc(this.value);javascript:getPendingForApprovalData();getApprovalData();getCancelledData();getSubjectReportData();" data-validation="required" data-live-search="true">               
                                <option value="">Select</option>
                                <c:forEach items="${semester}" var="reList">
                                    <option value="${reList[0]}~@~${reList[2]}"><c:out value="${reList[1]}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>  
                    <div class="row col-lg-6 form-group">
                        <div class="col-sm-10 col-lg-10 col-xs-10 col-md-10">
                            <input type="text" name="regDesc" id="regDesc" value="" class="form-control" readonly="true"/>   
                        </div>
                        <div class="col-sm-2 col-lg-2 col-xs-2 col-md-2">                                    
                            <!--<a href="javascript:getPendingForApprovalData();getApprovalData();getCancelledData();getSubjectReportData();" class="btn btn-success fa fa-search"></a>-->
                        </div>
                    </div>  
                </div>
                <div class="col-md-12">
                    <div class="box-body" >
                        <div id="rootwizard" class="col-lg-12">
                            <div class="navbar">
                                <div class="navbar-inner">
                                    <div class="container col-lg-12" >
                                        <ul class="nav nav-tabs" style="background: #cccccc; width: 525px;">
                                            <li onclick="getTab1Data();"><a href="#tab1"  data-toggle="tab"><b>Pending For Approve</b></a></li>
                                            <li onclick="getTab2Data();"><a href="#tab2" data-toggle="tab"><b>Approved</b></a></li>                    
                                            <li onclick="getTab3Data();"><a href="#tab3" data-toggle="tab"><b>Canceled</b></a></li>                    
                                            <li onclick="getTab4Data();" class="active"><a href="#tab4" data-toggle="tab"><b>Subject Wise Report</b></a></li>                    
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-content">
                                <div class="tab-pane " id="tab1">
                                    <%@include file="phdSubjectFinalizationPendingForApproved.jsp"%>            
                                </div>
                                <div class="tab-pane" id="tab2">
                                    <%@include file="phdSubjectFinalizationApproved.jsp"%>
                                </div>
                                <div class="tab-pane" id="tab3">
                                    <%@include file="phdSubjectFinalizationCancelled.jsp"%>
                                </div>
                                <div class="tab-pane active" id="tab4">
                                    <%@include file="subjectWiseReport.jsp"%>
                                </div>
                            </div>
                            <input type="hidden" id='form_id' class="">   
                            <input type="hidden" class="" id='url'>
                            <input type="hidden" class="" id='status' value="">
                            <input type="hidden" class="styType" id="styType" value=""/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#registrationid").chosen();
    function getTab1Data()
    {
        if ($("#registrationid").val() != '') {
            getPendingForApprovalData();
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }
    function getTab2Data()
    {
        if ($("#registrationid").val() != '') {
            getApprovalData();
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }
    function getTab3Data()
    {
        if ($("#registrationid").val() != '') {
            getCancelledData();
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }
    function getTab4Data()
    {
        if ($("#registrationid").val() != '') {
            getSubjectReportData();
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }


    function setFormIdAndUrl(id, url, tabNo, status) {
        $("#form_id").val(id);
        $("#url").val(url);
        $("#status").val('');
        $("#status").val(status);
        $("#ajaxform" + tabNo).submit();
    }

    function setRegDesc(id) {
        $("#regDesc").val('');
        var ids = id.split("~@~");
        $("#regDesc").val(ids[1]);
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
                                formData.append('regCode', $('#registrationid').val());
                                formData.append('status', $("#status").val());
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
//                                            loadForm("phdSubjectFinalization/list");
                                            getPendingForApprovalData();
                                            getApprovalData();
                                            getCancelledData();
                                            getSubjectReportData();
                                        } else if (data.status === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data.status.split('~@~')[0] + "<br>" + data.status.split('~@~')[1]);
                                        }
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!" + response.toString());

                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);


</script>