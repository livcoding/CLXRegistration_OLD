<%-- 
    Document   : teachingScheme
    Created on : Feb 15, 2019, 5:01:31 PM
    Author     : mohit1.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">              
    <div class="col-lg-12" style="margin-top:10px;">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="acadYear" name="acadYear" data-validation="required" onchange="resetother()"data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${acadList}" var="acaList">
                            <option value="${acaList.id.academicyear}"><c:out value="${acaList.id.academicyear}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="program" name="program" onchange="getBranch()" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${progList}" var="proList">
                            <option value="${proList[2]}"><c:out value="${proList[0]} -- ${proList[1]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div> 
        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="branch" name="branch" onchange="getSemester();getSection();" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section/Batch:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="section" name="section" onchange="" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty Number:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="semester" name="semester" onchange="setLoadData();"  data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <div class="row col-lg-3 form-group" style="padding-top:5px;">
                    <!--<a href="javascript:loadData();" class="btn btn-success fa fa-search" style="padding:10px"></a>-->
                    </div> 
                <div class="col-sm-6 col-lg-3" style="padding-top:5px;"
                     <div style="margin-right: 15px;" class="form-group pull-right-container">
                        <button type="button" class="btn btn-info" data-toggle="modal" onclick="" data-target="#myModal">Copy Data</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" ></div>
        <div class="row col-lg-12 form-group" ></div>
        <div class="row col-lg-12 form-group" ></div>
        <div class="row col-lg-12 form-group" ></div>
        <div class="row col-lg-12 form-group" ></div>
        <div class="row col-lg-12 form-group" ></div>
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr style="background-color:#008080;color:white">
                    <th width="1px;"></th>
                    <th width="1px;">Sl No.</th>
                    <th width="10px;" >Program (Section)</th>
                    <th width="10px;" >Subject Code / Description</th>
                    <th width="10px;" >Basket Code</th>
                    <th width="5px;" >Credit</th>
                    <th width="5px;" >Subject Type</th>
                    <th width="10px;" >Department </th>
                    <th width="5px;" >Deactive</th>
                </tr>
            </thead>  
            <tbody>
            </tbody>
        </table>
    </div> 

    <!----------------------- Model Window Start --------------------->

    <div id="myModal" class="modal fade" role="dialog" style="width: 1000px;">
        <div class="modal-dialog" style="width: 900px;">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <button type="button" class="close" data-dismiss="modal" style="color:white">&times;</button>
                    <h4 class="modal-title"><b>Teaching Scheme PopUp</b></h4>
                </div>
                <div class="modal-body">
                    <div class="row col-lg-12 form-group">
                        <div class="row col-lg-6 form-group" style="border: 1px solid; margin-left: 15px;">
                            <div class="row col-lg-12 form-group"  style="background: #cccccc; width: 418px;margin-bottom:15px;"><label style="text-transform: capitalize; color: black;" class="col-sm-3 col-lg-8 col-xs-3 col-md-3 control-label"><b>COPY FROM</b></label></div>

                            <div class="row col-lg-12 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Acad Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="acadYear1" name="acadYear1" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${acadList}" var="acaList">
                                        <option value="${acaList.id.academicyear}"><c:out value="${acaList.id.academicyear}"/></option>
                                    </c:forEach> 
                                </select> 
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Prog. Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="program1" name="program1" onchange="getBranch1()"  data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${progList}" var="proList">
                                        <option value="${proList[2]}"><c:out value="${proList[0]} -- ${proList[1]}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Branch Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="branch1" name="branch1" onchange="getSemester1();getSection1()"  data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Section/Batch:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="section1" name="section1" onchange="" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">STY Number:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="semester1" name="semester1" onchange="getBasket1()" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Basket Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="basket1" name="basket1" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>

                        <div class="row col-lg-6 form-group" style="border: 1px solid; margin-left: 15px;">
                            <div class="row col-lg-12 form-group" style="background: #cccccc; width: 418px;margin-bottom:15px"><label style="text-transform: capitalize; color: black;" class="col-sm-3 col-lg-8 col-xs-3 col-md-3 control-label"><b>COPY TO</b></label></div> 
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Acad Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="acadYear2" name="acadYear2" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${acadList}" var="acaList">
                                        <option value="${acaList.id.academicyear}"><c:out value="${acaList.id.academicyear}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Prog. Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="program2" name="program2" onchange="getBranch2()" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${progList}" var="proList">
                                        <option value="${proList[2]}"><c:out value="${proList[0]} -- ${proList[1]}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Branch Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="branch2" name="branch2" onchange="getSemester2();getSection2()" data-live-search="true">               
                                    <option value="">Select</option>
                                </select> 
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Section Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="section2" name="section2" onchange="" data-live-search="true">               
                                    <option value="">Select</option>
                                </select> 
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">STY Number:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="semester2" name="semester2" onchange="getBasket2()" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Basket Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <select class="form-control" id="basket2" name="basket2" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="modal-footer">
                    <div class="col-lg-12 form-group">              
                        <div style="margin-top:-10px;" class="form-group pull-right">
                            <a href="javascript:saveCopyData();" class="btn btn-success btn-sm btn-flat"> Copy & Save</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--------------------Model window End-------------------------->

    <input type="hidden"   name="countval1" id="countval1" value=""/>
    <input type="hidden" name="totlaCountValue" id="totlaCountValue" value=""/>
</form>
<style>
    #button_wrapper div:nth-child(2) {display:none;}    
</style>
<script>
    $("#regCode1").chosen({width: "55%"});
    $("#regCode2").chosen({width: "55%"});
    $("#acadYear1").chosen({width: "55%"});
    $("#acadYear2").chosen({width: "55%"});
    $("#program1").chosen({width: "55%"});
    $("#program2").chosen({width: "55%"});
    $("#branch1").chosen({width: "55%"});
    $("#branch2").chosen({width: "55%"});
    $("#section1").chosen({width: "55%"});
    $("#section2").chosen({width: "55%"});
    $("#semester1").chosen({width: "55%"});
    $("#semester2").chosen({width: "55%"});
    $("#basket1").chosen({width: "55%"});
    $("#basket2").chosen({width: "55%"});



    function resetother() {
        $("#program").val('').trigger("chosen:updated");
        $("#section").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#branch").val('').trigger("chosen:updated");
    }

    var count = 1;
    function setLoadData() {
        closePage();
        count = count + 1;
        setData();
        loadData();
        if (count % 2 != 0) {
                setLoadData();
        }
    }
    function setData() {
        var section = $("#section").val();
        if (section != '') {
            $('#datatable').DataTable().destroy();
            var cb = {
                add: {
                    link: "teachingSch/add",
                    name: "Add",
                    value: "special",
                    data: $("#acadYear").val() + '~@~' + $("#program").val() + '~@~' + $("#section").val() + '~@~' + $("#semester").val()
                },
                edit: {
                    link: "teachingSch/edit",
                    name: "Edit",
                    value: ""
                },
                delete: {
                    link: "teachingSch/delete",
                    name: "Delete",
                    value: ""
                },
                list: {
                    link: "teachingSch/list"
                },
                expression: {
                    link: "",
                    name: "",
                    value: ""
                }
            }
            commonButtonMethod(cb);
        }
    }

    $("#semCode").chosen();
    $("#acadYear").chosen();
    $("#program").chosen();
    $("#branch").chosen();
    $("#section").chosen();
    $("#semester").chosen();
    $("#basket").chosen();
    $(document).ready(function () {
        $('#datatable').DataTable().clear().draw();
    });
    function callChkBox(index) {
        var value = $("#chk1" + index).val();
        if ($("#chk1" + index).prop('checked') == true) {
            $("#chk1" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk1" + index).prop("checked", true);
            $("#del_img" + index).hide();
            $("#d_img" + index).show();
        }
    }

    function getBranch() {
        $('#datatable').DataTable().clear().draw();
        if ($("#acadYear").val() != '') {
            if ($("#program").val() != '') {
                $.ajax({
                    url: "teachingSch/getBranch",
                    dataType: 'json',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: "programId=" + $("#program").val(),
                    error: (function (response) {
                        BootstrapDialog.alert('Server Error' + response);
                    }),
                    success: function (data) {
                        $("#branch").empty();
                        if (data.branch[0] != null) {
                            var str1 = '';
                            var str1 = '<option value="">Select</option>';
                            $.each(data.branch[0], function (i, item)
                            {
                                str1 += '<option value="' + item[4] + '">' + item[1] + '</option>'
                            });
                            $("#branch").append(str1);
                            $("#branch").trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Branch Not Found For This Program,Please Select Another Program...");
                        }
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select program first!");
            }
        } else {
            BootstrapDialog.alert("Please Select Academic Year first!");
            $("#program").val('').trigger("chosen:updated");
        }
    }

    function getBranch1() {
        if ($("#program1").val() != '') {
            $.ajax({
                url: "teachingSch/getBranch",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + $("#program1").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
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
                url: "teachingSch/getBranch",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + $("#program2").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
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
        $('#datatable').DataTable().clear().draw();
        if ($("#program").val() != '') {
            $.ajax({
                url: "teachingSch/getSemester",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "&branchId=" + $("#branch").val() + "&academicYear=" + $("#acadYear").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#semester").empty();
                    if (data.semester[0] != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        for (var i = parseInt(data.semester[0][4]); i <= parseInt(data.semester[0][5]); i++)
                        {
                            str1 += '<option value="' + i + '">' + i + '</option>'
                        }
                        $("#semester").append(str1);
                        $("#semester").trigger("chosen:updated");
                    } else {
                        $("#branch").empty();
                        $("#branch").val('').trigger("chosen:updated");
                        $("#semester").empty();
                        $("#semester").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("Sty Number Not Found For This Branch,Please Select Another Branch...");
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
                url: "teachingSch/getSemester",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "&branchId=" + $("#branch1").val() + "&academicYear=" + $("#acadYear1").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#semester1").empty();
                    if (data.semester[0] != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        for (var i = parseInt(data.semester[0][4]); i <= parseInt(data.semester[0][5]); i++)
                        {
                            str1 += '<option value="' + i + '">' + i + '</option>'
                        }
                        $("#semester1").append(str1);
                        $("#semester1").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Semester Not Found For This Branch,Please Select Another Branch...");
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
                url: "teachingSch/getSemester",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "&branchId=" + $("#branch2").val() + "&academicYear=" + $("#acadYear2").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#semester2").empty();
                    if (data.semester[0] != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        for (var i = parseInt(data.semester[0][4]); i <= parseInt(data.semester[0][5]); i++)
                        {
                            str1 += '<option value="' + i + '">' + i + '</option>'
                        }
                        $("#semester2").append(str1);
                        $("#semester2").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Semester Not Found For This Branch,Please Select Another Branch...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select program first!");
        }
    }

    function getSection() {
        $('#datatable').DataTable().clear().draw();
        if ($("#acadYear").val() != '') {
            if ($("#program").val() != '') {
                $.ajax({
                    url: "teachingSch/getSection",
                    dataType: 'json',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: "acadYear=" + $("#acadYear").val() + "&branch=" + $("#branch").val() + "&program=" + $("#program").val(),
                    error: (function (response) {
                        BootstrapDialog.alert('Server Error' + response);
                    }),
                    success: function (data) {
                        $("#section").empty();
                        if (data.section != null) {
                            var str1 = '';
                            var str1 = '<option value="">Select</option>';
                            $.each(data.section, function (i, item)
                            {
                                str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                            });
                            $("#section").append(str1);
                            $("#section").trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Section not found for this semester, Please select another semester.");
                        }
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Program first.. !");
            }
        } else {
            BootstrapDialog.alert("Please Select Academic Year first... !");
        }
    }

    function getSection1() {
        if ($("#acadYear1").val() != '' && $("#branch1").val() != '' && $("#program1").val() != '') {
            $.ajax({
                url: "teachingSch/getSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear1").val() + "&branch=" + $("#branch1").val() + "&program=" + $("#program1").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#section1").empty();
                    if (data.section != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
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
        if ($("#acadYear2").val() != '' && $("#branch2").val() != '' && $("#program2").val() != '') {
            $.ajax({
                url: "teachingSch/getSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear2").val() + "&branch=" + $("#branch2").val() + "&program=" + $("#program2").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#section2").empty();
                    if (data.section != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
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

    function getBasket1() {
        if ($("#acadYear1").val() != '' && $("#branch1").val() != '' && $("#program1").val() != '' && $("#semester1").val() != '') {
            $.ajax({
                url: "teachingSch/getBasket",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear1").val() + "&program=" + $("#program1").val() + "&semester=" + $("#semester1").val() + "&section=" + $("#section1").val() +"&reqfor=copyfrom",
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#basket1").empty();
                    if (data.basket != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.basket, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
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

    function getBasket2() {
        if ($("#acadYear2").val() != '' && $("#branch2").val() != '' && $("#program2").val() != '' && $("#semester2").val() != '') {
            $.ajax({
                url: "teachingSch/getBasket",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear2").val() + "&program=" + $("#program2").val() + "&semester=" + $("#semester2").val() + "&section=" + $("#section2").val() +"&reqfor=copyto", 
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#basket2").empty();
                    if (data.basket != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.basket, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
                        $("#basket2").append(str1);
                        $("#basket2").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Basket not found for this section, please select another Section.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic year,Branch,Program and Semester !");
        }
    }

    function loadData() {
        var programId = $("#program").val();
        var acadYear = $("#acadYear").val();
        var semester = $("#semester").val();
        var sectionId = $("#section").val();
        var basketId = $("#basket").val();
        var branchId = $("#branch").val();

        $('#datatable').DataTable().clear().draw();
        if ($("#acadYear").val() != '' && $("#program").val() != '' && $("#branch").val() != '' && $("#section").val() != '') {
            $.blockUI();
            $.ajax({
                url: "teachingSch/loadTeachSchList",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + programId + "&acadYear=" + acadYear + "&semester=" + semester + "&sectionId=" + sectionId + "&branchId=" + branchId,
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                    setTimeout($.unblockUI, 2000);
                }),
                success: function (data) {
                    if (data.loadTeachSchData != null) {
                        $.each(data.loadTeachSchData, function (i, item)
                        {
                            var status = '';
                            if (item[4] == 'Y') {
                                status = "YES";
                            } else {
                                status = "NO";
                            }
                            var str1 = '';
                            str1 += '<tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[16] + '~@~' + item[18] + '">';
                            str1 += '<td id="' + item[0] + '~@~' + item[1] + '~@~' + item[16] + '~@~' + item[18] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[16] + '"/>';
                            str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                            str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                            str1 += '  <td>' + (i + 1) + '</td>';
                            str1 += '  <td>' + item[10] + '(' + item[11] + ')</td>';
                            str1 += '  <td>' + item[13] + ' / ' + item[19] + '</td>';
                            str1 += '  <td>' + item[12] + '</td>';
                            str1 += '  <td>' + item[5] + '</td>';
                            str1 += '  <td>' + item[17] + '</td>';
                            str1 += '  <td>' + item[14] + '</td>';
                            str1 += '  <td>' + status + '</td>';
                            str1 += '  </tr>';
                            tablelist.row.add($(str1)).draw();
                        });
                    } else {
                        BootstrapDialog.alert("No Data Found !!");
                    }
                    setTimeout($.unblockUI, 2000);
                }
            });
        } else {
            BootstrapDialog.alert("Please select the  Required Fields.");
            $("#semester").val('').trigger("chosen:updated");
            $.unblockUI();
        }
    }

    <%-------------------- Model Script Start --------------------------%>
    function saveCopyData() {
        if ($("#basket1").val() == '') {
            BootstrapDialog.alert("Please Select Required Fields..");
        } else if ($("#basket2").val() == '') {
            BootstrapDialog.alert("Please Select Required Fields..");
        } else {
            $.ajax({
                type: "POST",
                url: "teachingSch/copy",
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
                    } else if (data === 'Error') {
                        toastr.error(Error['error_code'], "Error!!");
                    } else {
                        BootstrapDialog.alert(data);
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...', "Warning!!");
                }
            });
        }
    }
    <%-------------------- Model Script End --------------------------%>

</script>

