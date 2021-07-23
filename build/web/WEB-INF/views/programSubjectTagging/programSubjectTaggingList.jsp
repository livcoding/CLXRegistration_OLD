<%-- 
    Document   : programSubjectTaggingList
    Created on : 22 Dec, 2018, 12:18:08 PM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="semCode" name="semCode" onchange="getAcademicYear(); resetother()" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${regList}" var="reList">
                            <option value="${reList[0]}"><c:out value="${reList[1]} -- ${reList[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="acadYear" name="acadYear" data-validation="required" onchange="resetprogram()" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="program" name="program" onchange="getBranch()" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${progList}" var="proList">
                            <option value="${proList[2]}"><c:out value="${proList[0]} -- ${proList[1]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="branch" name="branch" onchange="getSemester()" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">STY No.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="semester" name="semester" onchange="getSection()"  data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="section" name="section" onchange="getBasket()" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Basket:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="basket" name="basket" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <div class="row col-lg-6 form-group" style="text-align: right">
                    <a href="javascript:loadData('1');getchildData('1')" class="btn btn-success ">Load Data</a>
                </div> 
                <div class="row col-lg-6 form-group" style="text-align: right">
                    <div style="margin-right: 15px;" class="form-group pull-right-container">
                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">Copy Data</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-12 container">
            <fieldset>
                <legend class="text-success">Teaching Scheme DataGrid</legend>
                <table id="itemList" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                    <thead>
                        <tr>
                            <th width="1px;"></th>
                            <th width="10px;">Subject Code</th>
                            <th width="25px;">Subject Desc</th>
                            <th width="5px;">Credit</th>
                            <th width="5px;">Subject Running</th>
                            <!--<th style="width:70px;height:30px;">Total CC Marks [L/T/P]</th>-->
                            <th width="15px;" style="display:none">Passing Marks [L/T/P]</th>
                            <!--<th width="5px;">PST Populated</th>-->
                            <!--<th width="5px;">Custom Finalized</th>-->
                            <th width="5px;">Active</th>
                            <!--<th style="width:70px;height:30px;">Passing / Total Marks</th>-->
                        </tr>
                    </thead>  
                    <tbody>
                    </tbody>
                </table>
            </fieldset>
        </div> 
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm " id="save" disabled="true" style="width:150px;margin-right:-25px"> Save</a>
            </div>
        </div>

        <br><br>
        <div class="col-lg-12 container"style="overflow-x: auto;overflow-y: auto">
            <fieldset>
                <legend class="text-success">Program Subject Tagging DataGrid</legend>
                <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                    <thead id="header">
                        <tr>
                            <th width="1px;"></th>
                            <th width="1px;">Sl No.</th>
                            <th width="10px;">Semester Code</th>
                            <th width="5px;" >Academic Year</th>
                            <th width="3px;" >STY No.</th>
                            <th width="10px;" >Program Code</th>
                            <th width="10px;" >Section Code</th>
                            <th width="10px;" >Basket Code</th>
                            <th width="10px;" >Subject Code</th>
                            <th width="10px;" >Subject Description</th>
                            <th width="5px;" >Subject Running</th>
                            <th width="5px;" >Credits</th>
                            <th width="10px;" >Department</th>
                            <th width="5px;" >PST Populated</th>
                            <th width="5px;" >Custom Finalized</th>
                            <th width="5px;" >Deactive</th>
                            <th width="5px;" >TT Hide</th>
                        </tr>
                    </thead>  
                    <tbody>
                    </tbody>
                </table>
            </fieldset>
        </div> 
    </div> 

    <!----------------------- Model Window Start --------------------->

    <div id="myModal" class="modal fade" role="dialog" style="width: 1000px;">
        <div class="modal-dialog" style="width: 900px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"><b>Program Subject Tagging PopUp</b></h4>
                </div>
                <div class="modal-body">
                    <div class="row col-lg-12 form-group">
                        <div class="row col-lg-6 form-group" style="border: 1px solid; margin-left: 15px;">
                            <div class="row col-lg-12 form-group"  style="background: #cccccc; width: 418px;"><label style="text-transform: capitalize; color: black;" class="col-sm-3 col-lg-8 col-xs-3 col-md-3 control-label"><b>COPY FROM</b></label></div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Reg Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="regCode1"  onchange="resetother1()"  name="regCode1" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${regList}" var="reList">
                                        <option value="${reList[0]}"><c:out value="${reList[1]} -- ${reList[2]}"/></option>
                                    </c:forEach>
                                </select> 
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Acad Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="acadYear1" name="acadYear1" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${acadList}" var="acaList">
                                        <option value="${acaList.id.academicyear}"><c:out value="${acaList.id.academicyear}"/></option>
                                    </c:forEach>
                                </select> 
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Prog. Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="program1" name="program1" onchange="getBranch1()"  data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${progList}" var="proList">
                                        <option value="${proList[2]}"><c:out value="${proList[0]} -- ${proList[1]}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Branch Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="branch1" name="branch1" onchange="getSemester1()"  data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">STY Number:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="semester1" name="semester1" onchange="getSection1()" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Section Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="section1" name="section1" onchange="getBasket1()" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Basket Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="basket1" name="basket1" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>

                        <div class="row col-lg-6 form-group" style="border: 1px solid;margin-left: 15px;">
                            <div class="row col-lg-12 form-group" style="background: #cccccc; width: 418px;"><label style="text-transform: capitalize; color: black;" class="col-sm-3 col-lg-8 col-xs-3 col-md-3 control-label"><b>COPY TO</b></label></div> 
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Reg Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="regCode2" name="regCode2" onchange="resetother2()"  data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${regList}" var="reList">
                                        <option value="${reList[0]}"><c:out value="${reList[1]} -- ${reList[2]}"/></option>
                                    </c:forEach>
                                </select> 
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Acad Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="acadYear2" name="acadYear2" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${acadList}" var="acaList">
                                        <option value="${acaList.id.academicyear}"><c:out value="${acaList.id.academicyear}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Prog. Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="program2" name="program2" onchange="getBranch2()" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${progList}" var="proList">
                                        <option value="${proList[2]}"><c:out value="${proList[0]} -- ${proList[1]}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Branch Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="branch2" name="branch2" onchange="getSemester2()" data-live-search="true">               
                                    <option value="">Select</option>
                                </select> 
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">STY Number:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="semester2" name="semester2" onchange="getSection2()" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Section Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="section2" name="section2"  data-live-search="true">               
                                    <option value="">Select</option>
                                </select> 
                            </div>
                            <!--                            <div class="row col-lg-12 form-group" style="margin-top: -20px;">
                                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Basket Code:
                                                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                                            <select class="form-control" id="basket2" name="basket2" data-live-search="true">               
                                                                <option value="">Select</option>
                                                            </select>
                                                        </div>-->
                            <div class="col-lg-12 form-group">              
                                <div style="margin-top: 1px;margin-right: 30px;" class="form-group pull-right">
                                    <a href="javascript:saveCopyData();" class="btn btn-success btn-sm btn-flat"> Copy & Save</a>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="modal-footer">
                </div>
            </div>
        </div>
    </div>

    <!--------------------Model window End-------------------------->

    <input type="hidden"   name="countval1" id="countval1" value=""/>
    <input type="hidden" name="totlaCountValue" id="totlaCountValue" value=""/>
</form>

<script>
    $("#regCode1").chosen({width: "100%"});
    $("#regCode2").chosen({width: "100%"})
    $("#acadYear1").chosen({width: "100%"});
    $("#acadYear2").chosen({width: "100%"});
    $("#program1").chosen({width: "100%"});
    $("#program2").chosen({width: "100%"});
    $("#branch1").chosen({width: "100%"});
    $("#branch2").chosen({width: "100%"});
    $("#section1").chosen({width: "100%"});
    $("#section2").chosen({width: "100%"});
    $("#semester1").chosen({width: "100%"});
    $("#semester2").chosen({width: "100%"});
    $("#basket1").chosen({width: "100%"});
    // $("#basket2").chosen({width: "100%"});


    function resetother2() {
        $("#acadYear2").val('').trigger("chosen:updated");
        $("#program2").val('').trigger("chosen:updated");
        $("#program2").val('').trigger("chosen:updated");
        $("#branch2").empty();
        $("#branch2").val('').trigger("chosen:updated");
        $("#semester2").empty();
        $("#semester2").val('').trigger("chosen:updated");
        $("#section2").empty();
        $("#section2").val('').trigger("chosen:updated");
        //  $("#basket2").val('').trigger("chosen:updated");
    }
    function resetother1() {
        $("#acadYear1").val('').trigger("chosen:updated");
        $("#program1").val('').trigger("chosen:updated");
        $("#branch1").empty();
        $("#branch1").val('').trigger("chosen:updated");
        $("#semester1").empty();
        $("#semester1").val('').trigger("chosen:updated");
        $("#section1").empty();
        $("#section1").val('').trigger("chosen:updated");
        $("#basket1").empty();
        $("#basket1").val('').trigger("chosen:updated");
    }



    $('#itemList').DataTable({
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        "order": [],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }],
    });
    var cb = {
        add: {
            link: "",
            name: "",
            value: ""
        },
        edit: {
            link: "programSubjectTagging/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "programSubjectTagging/delete",
            name: "Delete",
            value: ""
        },
        list: {
            link: "programSubjectTagging/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);
    $("#semCode").chosen();
    $("#acadYear").chosen();
    $("#program").chosen();
    $("#branch").chosen();
    $("#section").chosen();
    $("#semester").chosen();
    $("#basket").chosen();
    function getAcademicYear() {
        $("#acadYear").empty();
        $.ajax({
            type: "POST",
            url: "programSubjectTagging/getAcademicYear",
            data: '&semCode=' + $("#semCode").val(),
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
                $.each(data.acadYearList, function (i, item)
                {
                    str1 += '<option value="' + item + '">' + item + '</option>'
                });
                $("#acadYear").append(str1);
                $("#acadYear").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }


    function resetother() {
        $("#acadYear").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#branch").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#section").val('').trigger("chosen:updated");
        $("#basket").val('').trigger("chosen:updated");
    }
    function resetprogram() {
        $("#program").val('').trigger("chosen:updated");
        $("#branch").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#section").val('').trigger("chosen:updated");
        $("#basket").val('').trigger("chosen:updated");
    }

    function resetother() {
        $("#acadYear").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('').trigger("chosen:updated");
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        $("#basket").empty();
        $("#basket").val('').trigger("chosen:updated");
    }
    function resetprogram() {
        $("#program").val('').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('').trigger("chosen:updated");
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        $("#basket").empty();
        $("#basket").val('').trigger("chosen:updated");
    }


    function callChkBox(index) {
        var value = $("#chk1" + index).val();
        if ($("#chk1" + index).prop('checked') == true) {
            $("#chk1" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk1" + index).prop("checked", true);
            checkChild(value, index);
        }
    }
    function checkChild(v, i) {
        $.ajax({
            url: "programSubjectTagging/checkIfChildExist",
            datatype: 'String',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "pk=" + v,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                if (data === "true") {
                    BootstrapDialog.alert("Child exists for this record!");
                    $("#chk1" + i).prop("checked", false);
                    $("#chk1" + i).attr("disabled", true);
                } else {
                    $("#del_img" + i).hide();
                    $("#d_img" + i).show();
                }
            }
        });
    }

    function getBranch() {
        if ($("#program").val() != '' && $("#semCode").val() != '' && $("#acadYear").val() != '') {
            $("#semester").empty();
            $("#semester").val('').trigger("chosen:updated");
            $("#section").empty();
            $("#section").val('').trigger("chosen:updated");
            $("#basket").empty();
            $("#basket").val('').trigger("chosen:updated");
            $.ajax({
                url: "programSubjectTagging/getBranch",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + $("#program").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#branch").empty();
                    if (data.branch[0] != '') {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.branch[0], function (i, item)
                        {
                            str1 += '<option value="' + item[4] + '">' + item[1] + '</option>'
                        });
                        $("#branch").append(str1);
                        $("#branch").trigger("chosen:updated");
                    } else {
                        $("#branch").empty();
                        $("#branch").val('').trigger("chosen:updated");
                        $("#semester").empty();
                        $("#semester").val('').trigger("chosen:updated");
                        $("#section").empty();
                        $("#section").val('').trigger("chosen:updated");
                        $("#basket").empty();
                        $("#basket").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("Branch Not Found For This Program,Please Select Another Program...");
                    }
                }
            });
        } else {
            $("#program").val('').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select Required Fields...!");
        }
    }

    function getBranch1() {
        if ($("#program1").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getBranch",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + $("#program1").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#branch1").empty();
                    if (data.branch[0] != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.branch[0], function (i, item)
                        {
                            str1 += '<option value="' + item[4] + '">' + item[1] + '</option>'
                        });
                        $("#branch1").append(str1);
                        $("#branch1").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Branch Not Found For This Program,Please Select Another Program...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select program first!");
        }
    }

    function getBranch2() {
        if ($("#program2").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getBranch",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + $("#program2").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#branch2").empty();
                    if (data.branch[0] != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.branch[0], function (i, item)
                        {
                            str1 += '<option value="' + item[4] + '">' + item[1] + '</option>'
                        });
                        $("#branch2").append(str1);
                        $("#branch2").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Branch Not Found For This Program,Please Select Another Program...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select program first!");
        }
    }

    function getSemester() {
        if ($("#program").val() != '' && $("#semCode").val() != '' && $("#acadYear").val() != '' && $("#branch").val() != '') {
            $("#semester").empty();
            $("#section").empty();
            $("#section").val('').trigger("chosen:updated");
            $("#basket").empty();
            $("#basket").val('').trigger("chosen:updated");
            $.ajax({
                url: "programSubjectTagging/getSemester",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "&regid=" + $("#semCode").val() + "&acadYear=" + $("#acadYear").val() + "&branch=" + $("#branch").val() + "&program=" + $("#program").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.semester != '') {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.semester, function (i, item)
                        {
                            debugger;
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#semester").append(str1);
                        $("#semester").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("STY Number Not Found For This Branch,Please Select Another Branch...!");
                        $("#semester").empty();
                        $("#semester").val('').trigger("chosen:updated");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select program first!");
        }
    }

    function getSemester1() {
        if ($("#program1").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getSemester",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "&regid=" + $("#regCode1").val() + "&acadYear=" + $("#acadYear1").val() + "&branch=" + $("#branch1").val() + "&program=" + $("#program1").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#semester1").empty();
                    if (data.semester != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.semester, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#semester1").append(str1);
                        $("#semester1").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("STY Number Not Found For This Branch,Please Select Another Branch...");
                        $("#semester1").empty();
                        $("#semester1").val('').trigger("chosen:updated");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select program first!");
        }
    }

    function getSemester2() {
        if ($("#program2").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getSemester",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "&regid=" + $("#regCode2").val() + "&acadYear=" + $("#acadYear2").val() + "&branch=" + $("#branch2").val() + "&program=" + $("#program2").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#semester2").empty();
                    if (data.semester != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.semester, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#semester2").append(str1);
                        $("#semester2").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("STY Number Not Found For This Branch,Please Select Another Branch...");
                        $("#semester2").empty();
                        $("#semester2").val('').trigger("chosen:updated");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select program first!");
        }
    }

    function getSection() {
        if ($("#acadYear").val() != '' && $("#branch").val() != '' && $("#program").val() != '' && $("#semester").val() != '') {
            $("#section").empty();
            $("#basket").empty();
            $("#basket").val('').trigger("chosen:updated");
            $.ajax({
                url: "programSubjectTagging/getSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear").val() + "&branch=" + $("#branch").val() + "&program=" + $("#program").val() + "&semester=" + $("#semester").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.section != '') {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.section, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
                        $("#section").append(str1);
                        $("#section").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section not found for this semester, Please select another semester...!");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic year,Branch,Program and Semester...!");
        }
    }

    function getSection1() {
        if ($("#acadYear1").val() != '' && $("#branch1").val() != '' && $("#program1").val() != '' && $("#semester1").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear1").val() + "&branch=" + $("#branch1").val() + "&program=" + $("#program1").val() + "&semester=" + $("#semester1").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#section1").empty();
                    if (data.section != null) {
                        var str1 = '';
                        str1 += '<option value="">Select</option>';
                        str1 += '<option value="All">All</option>';
                        $.each(data.section, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
                        $("#section1").append(str1);
                        $("#section1").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section not found for this semester, Please select another semester.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic year,Branch,Program and Semester !");
        }
    }

    function getSection2() {
        if ($("#acadYear2").val() != '' && $("#branch2").val() != '' && $("#program2").val() != '' && $("#semester2").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear2").val() + "&branch=" + $("#branch2").val() + "&program=" + $("#program2").val() + "&semester=" + $("#semester2").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#section2").empty();
                    if (data.section != null) {
                        var str1 = '';
                        str1 += '<option value="">Select</option>';
                        str1 += '<option value="All">All</option>';
                        $.each(data.section, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
                        $("#section2").append(str1);
                        $("#section2").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section not found for this semester, Please select another semester.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic year,Branch,Program and Semester !");
        }
    }

    function getBasket() {
        $("#basket").val('');
        if ($("#acadYear").val() != '' && $("#branch").val() != '' && $("#program").val() != '' && $("#semester").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getBasket",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear").val() + "&program=" + $("#program").val() + "&semester=" + $("#semester").val() + "&section=" + $("#section").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#basket").empty();
                    if (data.basket != '') {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.basket, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
                        $("#basket").append(str1);
                        $("#basket").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Basket not found for the selected Program,STY No. and Section..!");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic year,Branch,Program and Semester !");
        }
    }

    function getBasket1() {
        $("#basket1").val('');
        if ($("#acadYear1").val() != '' && $("#branch1").val() != '' && $("#program1").val() != '' && $("#semester1").val() != '') {
            $.ajax({
                url: "programSubjectTagging/getBasket",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear1").val() + "&program=" + $("#program1").val() + "&semester=" + $("#semester1").val() + "&section=" + $("#section1").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#basket1").empty();
                    if (data.basket != null) {
                        var str1 = '';
                        str1 += '<option value="">Select</option>';
                        if ($("#section1").val() == "All") {
                            str1 += '<option value="All">All</option>';
                        } else {
                            str1 += '<option value="All">All</option>';
                            $.each(data.basket, function (i, item)
                            {
                                str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                            });
                        }
                        $("#basket1").append(str1);
                        $("#basket1").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Basket not found for this section, please select another Section.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic year,Branch,Program and Semester !");
        }
    }
//
//    function getBasket2() {
//        if ($("#acadYear2").val() != '' && $("#branch2").val() != '' && $("#program2").val() != '' && $("#semester2").val() != '') {
//            $.ajax({
//                url: "programSubjectTagging/getBasket",
//                dataType: 'json',
//                async: false,
//                cache: false,
//                contentType: false,
//                processData: false,
//                data: "acadYear=" + $("#acadYear2").val() + "&program=" + $("#program2").val() + "&semester=" + $("#semester2").val() + "&section=" + $("#section2").val(),
//                error: (function (response) {
//                    alert('Server Error' + response);
//                }),
//                success: function (data) {
//                   // $("#basket2").empty();
//                    if (data.basket != null) {
//                        var str1 = '';
//                        var str1 = '<option value="">Select</option>';
//                        if ($("#section2").val() == "All") {
//                            str1 += '<option value="All">All</option>';
//                        } else {
//                            str1 += '<option value="All">All</option>';
//                            $.each(data.basket, function (i, item)
//                            {
//                                str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
//                            });
//                        }
//                        $("#basket2").append(str1);
//                        $("#basket2").trigger("chosen:updated");
//                    } else {
//                        BootstrapDialog.alert("Basket not found for this section, please select another Section.");
//                    }
//                }
//            });
//        } else {
//            BootstrapDialog.alert("Please Select Academic year,Branch,Program and Semester !");
//        }
//    }
    function loadData(show) {
        var programId = $("#program").val();
        var acadYear = $("#acadYear").val();
        var semester = $("#semester").val();
        var sectionId = $("#section").val();
        var basketId = $("#basket").val();
        var branchId = $("#branch").val();
        var regId = $("#semCode").val();
        $('#datatable').DataTable().clear().draw();
        var tablelist = $('#datatable').DataTable();
        if (regId != '' && regId != null) {
            if (acadYear != '' && acadYear != null) {
                if (programId != '' && programId != null) {
                    if (branchId != '' && branchId != null) {
                        if (semester != '' && semester != null) {
                            if (sectionId != '' && sectionId != null) {
                                if (basketId != '' && basketId != null) {
                                    $.ajax({
                                        url: "programSubjectTagging/loadProgramTaggingList",
                                        dataType: 'json',
                                        async: false,
                                        cache: false,
                                        contentType: false,
                                        processData: false,
                                        data: "programId=" + programId + "&acadYear=" + acadYear + "&semester=" + semester + "&sectionId=" + sectionId + "&basketId=" + basketId + "&regId=" + regId + "&branchId=" + branchId,
                                        error: (function (response) {
                                            alert('Server Error' + response);
                                        }),
                                        success: function (data) {

                                            if (data.programTaggingList != '') {
                                                $.each(data.programTaggingList, function (i, item)
                                                {
                                                    var str1 = '';
                                                    str1 += '<tr id="' + item[17] + '~@~' + item[18] + '~@~' + item[19] + '">';
                                                    str1 += '<td id="' + item[17] + '~@~' + item[18] + '~@~' + item[19] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[17] + '~@~' + item[18] + '~@~' + item[19] + '"/>';
                                                    str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                                                    str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                                                    str1 += '  <td>' + (i + 1) + '</td>';
                                                    str1 += '  <td>' + item[2] + '</td>';
                                                    str1 += '  <td>' + item[3] + '</td>';
                                                    str1 += '  <td>' + item[4] + '</td>';
                                                    str1 += '  <td>' + item[5] + '</td>';
                                                    str1 += '  <td>' + item[6] + '</td>';
                                                    str1 += '  <td>' + item[8] + '</td>';
                                                    str1 += '  <td>' + item[9] + '</td>';
                                                    str1 += '  <td>' + item[20] + '</td>';
                                                    str1 += '  <td>' + item[10] + '</td>';
                                                    str1 += '  <td>' + item[11] + '</td>';
                                                    str1 += '  <td>' + item[21] + '</td>';
                                                    str1 += '  <td>' + item[13] + '</td>';
                                                    str1 += '  <td>' + item[14] + '</td>';
                                                    str1 += '  <td>' + item[15] + '</td>';
                                                    str1 += '  <td>' + item[16] + '</td>';
                                                    str1 += '  </tr>';
                                                    tablelist.row.add($(str1)).draw();
                                                });
                                            } else {
                                                if (show == 1) {
                                                    BootstrapDialog.alert("No Tagged subject found in Program Subject Tagging...!");
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    BootstrapDialog.alert("Please Select Basket...!");
                                }
                            } else {
                                BootstrapDialog.alert("Please Select Section...!");
                            }
                        } else {
                            BootstrapDialog.alert("Please Select STY No. ...!");
                        }
                    } else {
                        BootstrapDialog.alert("Please Select Branch...!");
                    }
                } else {
                    BootstrapDialog.alert("Please Select Program...!");
                }
            } else {
                BootstrapDialog.alert("Please Select Academic Year...!");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code...!");
        }
    }
    function getchildData(show) {
        var programId = $("#program").val();
        var acadYear = $("#acadYear").val();
        var semester = $("#semester").val();
        var sectionId = $("#section").val();
        var basketId = $("#basket").val();
        var branchId = $("#branch").val();
        var regId = $("#semCode").val();
        $('#itemList').DataTable().clear().draw();
        var itemChildList = $('#itemList').DataTable();
        if (acadYear != '' && acadYear != null && programId != '' && programId != null && branchId != '' && branchId != null && semester != '' && semester != null && sectionId != '' && sectionId != null && basketId != '' && basketId != null) {
            $.ajax({
                url: "programSubjectTagging/add",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + programId + "&acadYear=" + acadYear + "&semester=" + semester + "&sectionId=" + sectionId + "&basketId=" + basketId + "&regId=" + regId + "&branchId=" + branchId,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    var count = new Array();
                    if (data.programTaggingChildList != '') {
                        $.each(data.programTaggingChildList, function (i, item)
                        {
                            var str1 = '';
                            var cou = 0;
                            var subtype = item[12];
                            str1 += ' <tr id="' + programId + '~@~' + acadYear + '~@~' + semester + '~@~' + sectionId + '~@~' + basketId + '~@~' + regId + '~@~' + branchId + '~@~' + item[2] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[10] + '">';
                            str1 += ' <td id="' + programId + '~@~' + acadYear + '~@~' + semester + '~@~' + sectionId + '~@~' + basketId + '~@~' + regId + '~@~' + branchId + '~@~' + item[2] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[10] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + programId + '~@~' + acadYear + '~@~' + semester + '~@~' + sectionId + '~@~' + basketId + '~@~' + regId + '~@~' + branchId + '~@~' + item[2] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[10] + '~@~' + item[37] + '~@~' + item[38] + '~@~' + item[39] + '"/>';
                            str1 += ' </td>';
                            str1 += ' <td>' + item[1] + '</td>'; //Subject Code
                            str1 += ' <td>' + item[3] + '</td>'; //Subject Desc 
                            str1 += ' <td>' + item[10] + '</td>'; // Credits
                            if (subtype.equals("C") || subtype.equals("P")) {
                                str1 += ' <td> <input type="checkbox" id="subjectRunning' + i + '" name="subjectRunning' + i + '"  value="Y" checked="true"/> </td>'; //Subject Running
                            } else {
                                str1 += ' <td> <input type="checkbox" id="subjectRunning' + i + '" name="subjectRunning' + i + '"  value="Y" /> </td>'; //Subject Running
                            }
//                        str1 += ' <td>' + item[17] + " / " + item[24] + " / " + item[31] + '</td>'; //Total CC Msrks LTP
                            str1 += ' <td style="display:none">' + item[18] + " / " + item[25] + " / " + item[32] + '</td>'; // Passing Marks LTP
//                        str1 += ' <td> <input type="checkbox" id="pstPopulated' + i + '"  name="pstPopulated' + i + '"  value="Y"/> </td>'; // PST Populated
//                        str1 += ' <td> <input type="checkbox" id="customFinalized' + i + '" name="customFinalized' + i + '"  value="Y"/> </td>'; // Custom Finalized
                            str1 += ' <td> <input type="checkbox" id="deactive' + i + '" name="deactive' + i + '"  value="Y" checked="true"/> </td>'; // Deactive
//                        str1 += ' <td>' + item[13] + '</td>'; // Passing Total
                            str1 += ' </tr>';
                            count.push(cou++);
                            itemChildList.row.add($(str1)).draw();
                        });
                        $("#countval1").val(count);
                        $("#save").removeAttr("disabled");
                    } else {
                        if (show == 1) {
                            BootstrapDialog.alert("No new subject found for tagging..(Teaching Schema is not define)");
                        }
                    }
                }
            });
        }
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
                                $.ajax({
                                    type: "POST",
                                    url: "programSubjectTagging/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadData(0);
                                            getchildData(0);
                                        } else if (data === 'Error') {
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
    function saveData() {
        var countval1 = $("#countval1").val().split(",");
        var checkedStatus = 0;
        for (var j = 0; j < countval1.length; j++) {
            if ($('#chk' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#totlaCountValue").val(countval1.length);
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select atleast one check box");
        } else {
            $("#ajaxform").submit();
        }
    }

    <%-------------------- Model Script Start --------------------------%>
    function saveCopyData() {
        var basket = $("#basket1").val();
        var section = $("#section2").val();
        alert(section + "" + basket);
        if (basket == '' || section == '' || basket == null || section == null) {
            BootstrapDialog.alert("Please Select Required Fields..");
        } else {
            $.ajax({
                type: "POST",
                url: "programSubjectTagging/copy",
                data: $("#ajaxform").serialize(),
                datatype: "String",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data === 'Success') {
                        toastr.success('Data Copied Successfully!', "Success!!");
                        $("#myModal").modal('hide');
                        $("#myModal").on('hidden.bs.modal', function (e) {
                            loadForm("programSubjectTagging/list");
                        })
                    } else if (data === 'Error') {
                        toastr.error(Error['error_code'], "Error!!");
                    } else {
                        BootstrapDialog.alert(data);
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        }
    }
    <%-------------------- Model Script End --------------------------%>

</script>

