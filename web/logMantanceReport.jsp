<%--
    Document   : logMantanceReport
    Created on : Jul 11, 2018, 3:05:16 PM
    Author     : ashutosh1.kumar
--%>
<%@ include file="include.jsp" %>
<div style="padding-left:0%;margin:0;border:0px;margin-left:0px; background:#F1A26E" class="headercss">
    <img  src="Image/CampusLynxHeader.jpg" alt="" width=100%" height="80" style="text-align:center"/>
</div>
<div class="container">
    <center> <h2><span>Generate Report</span></h2> </center>

    <div class="container" style="border:1px solid;margin-top:50px; background:#FFFFF0; width:50%;" >
        <form  class="form-horizontal " role="form" >
            <div class="form-group pull-right" style="margin-right:10%;">
                <button type="button" class="btn btn-success" onclick="generateExcel();"><i class="fa fa-file-excel-o" aria-hidden="true">Download</i></button>
            </div>
        </form>
    </div>
</div>
<script>
    function generateExcel() {
        alert("updload");
        window.location.assign('report/getReport?level=one&basedon=C&basedonid=CMP20131200000000001&basedoncode=BENU&employeetypeids=BUIDEMPT1612A0000001&employeetypes=E&stafftype=C&includedeactive=Y&exportto=P&orderby=oscode&instituteid=BUINTA0000002');

    }

</script>
