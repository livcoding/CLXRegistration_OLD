<%-- 
    Document   : newSubSectionAllocationList
    Created on : 15 Jan, 2019, 10:11:53 AM
    Author     : deepak.gupta
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="row" id="dashboard">
    <div class="col-md-12">
        <div class="box-body" >
            <div id="rootwizard" class="col-lg-12">
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="container col-lg-12" >
                            <ul class="nav nav-tabs" style="background: #cccccc; width: 457px;">
                                <li class="active"><a href="#tab1"  data-toggle="tab"><b>Student Master</b></a></li>
                                <li><a href="#tab2" data-toggle="tab"><b>Student Registration</b></a></li>                    
                                <li><a href="#tab3" data-toggle="tab"><b>Student Choice Detail</b></a></li>                    
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="tab-content">
                    <div class="tab-pane active" id="tab1">
                        <%@include file="studentMaster.jsp"%>            
                    </div>
                    <div class="tab-pane" id="tab2">
                        <%@include file="studentRegistration.jsp"%>
                    </div>
                    <div class="tab-pane" id="tab3">
                        <%@include file="studentChoiceDetail.jsp"%>
                    </div>

                </div>
                <input type="hidden" id='form_id' class="">   
                <input type="hidden" class="" id='url'>
            </div>
        </div>
    </div>
</div>
<script>

    function setFormIdAndUrl(id, url, tabNo) {
        $("#form_id").val(id);
        $("#url").val(url);
        $("#ajaxform" + tabNo).submit();
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
                                            loadForm("newSubSectionAllocation/list");
                                        } else if (data.status === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
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