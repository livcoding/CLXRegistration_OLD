<%-- 
    Document   : subjectComponentwiseStudentListReport
    Created on : 9 Jan, 2019, 2:15:06 PM
    Author     : deepak.gupta
--%>

<%@include file="../mainjstl.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <form method="POST" id="ajaxform" class="form-horizontal">

                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="regId" name="regId" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${registList}" var="reList">
                                    <option value="${reList.id.registrationid}~@~${reList.registrationcode}"><c:out value="${reList.registrationcode} / ${reList.registrationdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="subject" name="subject" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${subjectList}" var="subList">
                                    <option value="${subList.id.subjectid}~@~${subList.subjectcode}"><c:out value="${subList.subjectcode} / ${subList.subjectdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Component:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="subjectcomponent" name="subjectcomponent" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${subComponentList}" var="subComList">
                                    <option value="${subComList.id.subjectcomponentid}~@~${subComList.subjectcomponentcode}"><c:out value="${subComList.subjectcomponentcode} / ${subComList.subjectcomponentdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Section Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="section" name="section" onchange="getSubSection();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${sectionList}" var="secList">
                                    <option value="${secList.id.sectionid}~@~${secList.sectioncode}"><c:out value="${secList.sectioncode}  /  ${secList.sectiondesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Sub-Section Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="subsection" name="subsection" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat"> Download</a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset() {
        $("#regId").val('').trigger("chosen:updated");
        $("#subject").val('').trigger("chosen:updated");
        $("#subjectcomponent").val('').trigger("chosen:updated");
        $("#section").val('').trigger("chosen:updated");
        $("#subsection").val('').trigger("chosen:updated");
        $("#subsection").val('').empty();
    }
    $("#regId").chosen();
    $("#subject").chosen();
    $("#subjectcomponent").chosen();
    $("#section").chosen();
    $("#subsection").chosen();

    function getSubSection() {
        if ($("#section").val() != '') {
            $.ajax({
                url: "subjectComponentwiseStudentListReport/getSubSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "sectionId=" + $("#section").val() +"&regId=" + $("#regId").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subsection").empty();
                    if (data.subSectionList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.subSectionList[0], function (i, item)
                        {
                            str1 += '<option value="' + item[0] + "~@~" + item[1] + '">' + item[1] + " / " + item[2] + '</option>'
                        });
                        $("#subsection").append(str1);
                        $("#subsection").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Sub-Section Not Found, Please Select Another Section...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Section first!");
        }
    }

    function getReport() {
        var regId = $("#regId").val();
        var subject = $("#subject").val();
        var subjectcomponent = $("#subjectcomponent").val();
        var section = $("#section").val();
        var subsection = $("#subsection").val();
        var radioValue = $("input[name='export_To']:checked").val();
        if (regId != '' && subject != '' && subjectcomponent != '' && section != '' && subsection != '') {
            $.blockUI();
            window.location.assign('subjectComponentwiseStudentListReport/subjectComponentwiseStudentListReport?excelfilename=registrationReport&regId=' + regId + '&subject=' + subject + '&subjectcomponent=' + subjectcomponent + '&section=' + section + '&subsection=' + subsection + '&radioValue=' + radioValue + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>