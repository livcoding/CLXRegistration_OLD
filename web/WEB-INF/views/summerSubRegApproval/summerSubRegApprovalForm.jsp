<%-- 
    Document   : SummerSubjectRagistrationApprovalForm
    Created on : Feb 5, 2019, 12:16:31 PM
    Author     : malkeet.singh
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
                    <div  style="text-align:right">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Institute:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label></div>
                    <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                        <select class="form-control" id="multiinstituteid" multiple="true" name="multiinstituteid" data-validation="required"data-live-search="true" onchange="getSemesterCode(2);">               
                            <c:forEach items="${institutelist}" var="institutelist">
                                <c:if test="${maininstituteid==institutelist[0]}">
                                    <option value="${institutelist[0]}" selected="true"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                </c:if>
                                <c:if test="${maininstituteid!=institutelist[0]}">
                                    <option value="${institutelist[0]}"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <div  style="text-align:right">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label></div>
                    <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                        <select class="form-control" id="regCode" name="regCode" onchange="setRegDesc(this.value);" data-validation="required" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div>  
                <div class="row col-lg-6 form-group">
                    <div class="col-sm-10 col-lg-10 col-xs-10 col-md-10">
                        <input type="text" name="regDesc" id="regDesc" value="" class="form-control" readonly="true"/>   
                    </div>
                    <div class="col-sm-2 col-lg-2 col-xs-2 col-md-2">                                    
                        <a href="javascript:cleartable();loadPendingForApproveData();" class="btn btn-success fa fa-search"></a>
                    </div>
                </div>  
            </div>
            <div class="col-md-12">
                <div class="box-body" >
                    <div id="rootwizard" class="col-lg-12">
                        <div class="navbar">
                            <div class="navbar-inner">
                                <div class="container col-lg-12" >
                                    <ul class="nav nav-tabs" style="background: #cccccc; width: 100%;">
                                        <li onclick="getTab1Data();" class="active"><a href="#tab1"  data-toggle="tab"><b>Pending For Approve</b></a></li>
                                        <li onclick="getTab2Data();"><a href="#tab2" data-toggle="tab"><b>Approved</b></a></li>                    
                                        <li onclick="getTab3Data();"><a href="#tab3" data-toggle="tab"><b>Canceled</b></a></li>                    
                                        <li onclick="getTab4Data();"><a href="#tab4" data-toggle="tab"><b>Subject Wise Report</b></a></li>                    
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab1">
                                <%@include file="pendingForApprove.jsp"%>            
                            </div>
                            <div class="tab-pane" id="tab2">
                                <%@include file="approved.jsp"%>
                            </div>
                            <div class="tab-pane" id="tab3">
                                <%@include file="cancelled.jsp"%>
                            </div>
                            <div class="tab-pane" id="tab4">
                                <%@include file="subjectWiseReport.jsp"%>
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
    $("#multiinstituteid").chosen();
    $("#regCode").chosen();
    $(document).ready(function () {
        getSemesterCode();
    });

    function getSemesterCode() {
        $("#regCode").empty().trigger("chosen:updated");
        var instids = $('#multiinstituteid').val();
        $.ajax({
            type: "POST",
            url: "summerSubjectRagistrationApproval/getSemesterCode",
            data: '&instituteids=' + instids,
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.semestercodelist, function (i, item)
                {
                    str1 += '<option value="' + item[2] + '~@~' + item[4] + '">' + item[1] + ' -- ' + item[3] + '</option>'
                });
                $("#regCode").append(str1);
                $("#regCode").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function cleartable() {
        $("#total").val("");
        $('#itemListChoice').DataTable().clear().draw();
    }
    function getTab1Data()
    {
        loadPendingForApproveData();
    }
    function getTab2Data()
    {
        if ($("#styType").val() != '') {
            loadApprovedData();
        } else {
            BootstrapDialog.alert("Please Click On Search Button First..");
        }
    }
    function getTab3Data()
    {
        if ($("#styType").val() != '') {
            loadCanceledData();
        } else {
            BootstrapDialog.alert("Please Click On Search Button First..");
        }
    }
    function getTab4Data()
    {
        if ($("#styType").val() != '') {
            loadReportData();
        } else {
            BootstrapDialog.alert("Please Click On Search Button First..");
        }
    }


    function setFormIdAndUrl(id, url, tabNo) {
        $("#form_id").val(id);
        $("#url").val(url);
        $("#ajaxform" + tabNo).submit();
    }

    function setRegDesc(id) {
        $("#regDesc").val(id.split('~@~')[1]);
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
                                $.blockUI();
                                var formData = new FormData();
                                var url = $("#url").val();
                                var form = document.getElementById($("#form_id").val());
                                formData = new FormData(form);
                                formData.append('styType', $('#styType').val());
                                formData.append('regCode', $('#regCode').val().split('~@~')[0]);
                                $.ajax({
                                    type: "POST",
                                    url: url,
                                    data: formData,
                                    dataType: "json",
                                    async: true,
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
                                            loadPendingForApproveData();
//                                            loadForm("supplementarySubjectRagistrationApproval/list");
                                        } else if (data.status === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data.status);
                                        }
                                        setTimeout($.unblockUI, 2000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");

                                        setTimeout($.unblockUI, 2000);
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);
</script>