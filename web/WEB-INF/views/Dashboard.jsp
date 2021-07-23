<%-- 
Document   : Dashboard
Created on : Sep 1, 2018, 3:53:50 PM
Author     : mohit1.kumar
--%>
<%@ include file="mainjstl.jsp" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>CampusLynx | CLXRegistration</title>
        <link rel = "icon" href ="resources/img/CLX.png" type = "image/x-icon"> 
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.7 -->

        <link rel='stylesheet' href='resources/fullcalender/fullcalender.css' />
        <link rel="stylesheet" href="resources/bootstrap/bootstrap.css">
        <link rel="stylesheet" href="resources/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="resources/dateTimePicker/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!--<link rel="stylesheet" href="resources/fontAwesome/font-awesome.min.css">-->
        <!--<link rel="stylesheet" href="resources/icons/ionicons.min.css">-->
        <!--<link rel="stylesheet" href="resources/morris.js/morris.css">-->
        <link rel="stylesheet" href="resources/adminLTE/AdminLTE.min.css">
        <link rel="stylesheet" href="resources/skins/_all-skins.min.css">
        <link rel="stylesheet" href="resources/jquery/jquery-jvectormap.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/4.4.1/css/ionicons.min.css">
        <link rel="stylesheet" href="resources/css/pace-theme-minimal.min.css">
        <link rel="stylesheet" href="resources/datatables/dataTables.bootstrap.css" >
        <link rel="stylesheet" type="text/css" href="resources/datatables/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="resources/datatables/TableTools/css/dataTables.tableTools.min.css">  
        <link rel="stylesheet" href="resources/datatables/ColVis/dataTables.colVis.css">
        <link rel="stylesheet" href="resources/datatables/ColReorder/dataTables.colReorder.css">     
        <link rel="stylesheet" href="resources/datatables/extensions/button/css/buttons.dataTables.min.css" type="text/css">
        <link rel="stylesheet" href="resources/datatables/extensions/Responsive/css/dataTables.responsive.css" type="text/css">
        <!--<link rel="stylesheet" href="resources/css/chosen.css" type="text/css">-->
        <!--<link rel="stylesheet" href="resources/css/chosen.min.css" type="text/css">-->
        <link rel="stylesheet" href="resources/chosen-js/chosen.min.css" type="text/css">
        <link rel="stylesheet" href="resources/css/bootstrap-dialog.min.css" type="text/css">
        <link rel="stylesheet" href="resources/css/bootstrap-modal.min.css" type="text/css">
        <link rel="stylesheet" href="resources/css/toastr.min.css" type="text/css">
        <link rel="stylesheet" href="resources/bootstrap/bootstrap-duallistbox.css" type="text/css">
        <link rel="stylesheet" href="resources/bootstrap/dashboard.css" type="text/css">
        <style>
            .skin-blue .sidebar-menu>li:hover>a,
            .skin-blue .sidebar-menu>li.active>a,
            .skin-blue .sidebar-menu>li.menu-open>a {
                color: #fff;
                background: #039be5;
            }

            @media(max-width:768px){
                .formheading{
                    margin-top:100px;
                }
            }

            @media(min-width:768px){
                .formheading{
                    margin-top:50px;
                }
            }
            .slimScrollBar {
                background: none repeat scroll 0 0 #cccccc !important;
                border-radius: 0;
                display: none;
                width: 10px!important;
                z-index: 99;
                opacity:0.7!important;
            }
            .blockDiv {
                position: absolute;
                top: 0px;
                left: 0px;
                background-color: #FFF;
                width: 0px;
                height: 0px;
                z-index: 9999;
            }

            .modal {
                position: fixed;
                top: 50%;
                left: 50%;
                width: 800px;
                margin: -250px 0 0 -400px;
                overflow: auto;
                -moz-background-clip: padding-box;
                background-clip: padding-box;
            }




            @media print {
                .col-lg-1, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6,
                .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-10, .col-lg-11, .col-lg-12 {
                    float: left;              
                }
                .col-lg-12 {
                    width: 100%;
                }
                .col-lg-11 {
                    width: 91.66666666666666%;
                }
                .col-lg-10 {
                    width: 83.33333333333334%;
                }
                .col-lg-9 {
                    width: 75%;
                }
                .col-lg-8 {
                    width: 66.66666666666666%;
                }
                .col-lg-7 {
                    width: 58.333333333333336%;
                }
                .col-lg-6 {
                    width: 50%;
                }
                .col-lg-5 {
                    width: 41.66666666666667%;
                }
                .col-lg-4 {
                    width: 33.33333333333333%;
                }
                .col-lg-3 {
                    width: 25%;
                }
                .col-lg-2 {
                    width: 16.666666666666664%;
                }
                @media print {
                    .col-lg-1, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6,
                    .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-10, .col-lg-11, .col-lg-12 {
                        float: left;              
                    }

                    .col-lg-12 {
                        width: 100%;
                    }

                    .col-lg-11 {
                        width: 91.66666666666666%;
                    }
                    .col-lg-10 {
                        width: 83.33333333333334%;
                    }

                    .col-lg-9 {
                        width: 75%;

                    }


                    .col-lg-1 {
                        width: 8.333333333333332%;
                    }           


                    .col-lg-8 {
                        width: 66.66666666666666%;
                    }

                    .col-lg-7 {
                        width: 58.333333333333336%;
                    }

                    .col-lg-6 {
                        width: 50%;
                    }

                    .col-lg-5 {
                        width: 41.66666666666667%;
                    }

                    .col-lg-4 {
                        width: 33.33333333333333%;
                    }

                    .col-lg-3 {
                        width: 25%;
                    }

                    .col-lg-2 {
                        width: 16.666666666666664%;
                    }

                    .col-lg-1 {
                        width: 8.333333333333332%;

                    }           

                }
                @media (min-width: 768px) and (max-width: 991px) {
                    .hide-on-sm {
                        display: none !important;
                    }
                }
                table.dataTable th
                {
                    /*                white-space: nowrap;*/
                }
                fieldset.scheduler-border {
                    border: 1px groove black !important;
                    padding: 0 1.4em 1.4em 1.4em !important;
                    margin: 0 0 1.5em 0 !important;
                    -webkit-box-shadow:  0px 0px 0px 0px #000;
                    box-shadow:  0px 0px 0px 0px #000;
                }
                legend.scheduler-border {
                    width:inherit; /* Or auto */
                    padding:0 10px; /* To give a bit of padding on the left and right */
                    border-bottom:none;
                }
                /* jQuery Countdown styles 2.0.0. */
                .is-countdown {
                    border: 0px solid #ccc;
                    background-color: #eee;
                }
                .countdown-rtl {
                    direction: rtl;
                }
                .countdown-holding span {
                    color: #888;
                }
                .countdown-row {
                    clear: both;
                    width: 100%;
                    padding: 0px 2px;
                    text-align: center;
                }
                .countdown-show1 .countdown-section {
                    width: 98%;
                }
                .countdown-show2 .countdown-section {
                    width: 48%;
                }
                .countdown-show3 .countdown-section {
                    width: 32.5%;
                }
                .countdown-show4 .countdown-section {
                    width: 24.5%;
                }
                .countdown-show5 .countdown-section {
                    width: 19.5%;
                }
                .countdown-show6 .countdown-section {
                    width: 16.25%;
                }
                .countdown-show7 .countdown-section {
                    width: 14%;
                }
                .countdown-section {
                    display: block;
                    float: left;
                    font-size: 75%;
                    text-align: center;
                }
                .countdown-amount {
                    font-size: 200%;
                }
                .countdown-period {
                    display: block;
                }
                .countdown-descr {
                    display: block;
                    width: 100%;
                }

                @media print
                {   
                    .no-print, .no-print *
                    {
                        display: none !important;
                    }
                }
            </style>

            <!-- Google Font --> 
            <link rel="stylesheet"
                  href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
        </head>
        <body class="hold-transition skin-blue sidebar-mini" onload="noBack();">
            <%@ page language="java" import="java.util.*" %> 
            <%@ page import = "java.util.ResourceBundle" %>
            <% ResourceBundle resource = ResourceBundle.getBundle("campuslynx");
                String oauth_server_url = resource.getString("oauth_server_url");%>
            <input type="text" id="oauth_server_url" hidden="true" value="<%=oauth_server_url%>"/>
            <div class="wrapper">
                <header class="main-header" style="position:fixed;top:0px;width:100%;">
                    <!-- Logo -->
                    <a href="#" class="logo">
                        <input type="hidden" id="cc" name="cc" value="${cc}" readonly="true"/>
                        <!-- mini logo for sidebar mini 50x50 pixels -->
                        <span class="logo-mini"><i><b>C</b>LX</i></span>
                        <!-- logo for regular state and mobile devices -->
                        <span class="logo-lg"><i>
                                <img src="resources/img/CLX.png" style="height:30px;width:30px"/>
                                CLX<b>Registration</b></i></span>
                    </a>

                    <!-- Header Navbar: style can be found in header.less -->
                    <nav class="navbar navbar-static-top">
                        <!-- Sidebar toggle button-->
                        <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                            <span class="sr-only">Toggle navigation</span>
                        </a>
                        <!-- Navbar Right Menu -->
                        <div class="navbar-custom-menu">
                            <ul class="nav navbar-nav">
                                <!-- Messages: style can be found in dropdown.less-->

                                <!--                                <li class="">
                                                                    <a href="javascript:hello();" class="" data-toggle="">
                                                                        <i class="fa fa-phone"></i>
                                                                    </a>
                                                                </li>-->

                                <!--                                <li class="dropdown messages-menu">
                                                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                                        <i class="fa fa-envelope-o"></i>
                                                                        <span class="label label-success">4</span>
                                                                    </a>
                                                                    <ul class="dropdown-menu">
                                
                                                                    </ul>
                                                                </li>-->
                                <!-- Notifications: style can be found in dropdown.less -->
                                <!--                                <li class="dropdown notifications-menu">
                                                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                                        <i class="fa fa-bell-o"></i>
                                                                        <span class="label label-warning">10</span>
                                                                    </a>
                                                                    <ul class="dropdown-menu">
                                                                        <li class="header">You have 10 notifications</li>
                                                                        <li>
                                                                             inner menu: contains the actual data 
                                                                            <ul class="menu">
                                
                                                                            </ul>
                                                                        </li>
                                
                                                                    </ul>
                                                                </li>-->
                                <!-- Tasks: style can be found in dropdown.less -->
                                <!--                                <li class="dropdown tasks-menu">
                                                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                                        <i class="fa fa-flag-o"></i>
                                                                        <span class="label label-danger">9</span>
                                                                    </a>
                                                                    <ul class="dropdown-menu">
                                                                        <li class="header">You have 9 tasks</li>
                                                                        <li>
                                                                             inner menu: contains the actual data 
                                                                            <ul class="menu">
                                                                                <li> Task item 
                                
                                                                                </li>
                                                                                 end task item 
                                
                                                                            </ul>
                                                                        </li>
                                
                                                                    </ul>
                                                                </li>--> 
                                <li style="margin-right:15px;">
                                    <a href="javascript:changeModule();" data-toggle="tooltip" title="Change Module" data-placement="bottom" style="padding:4px"> 
                                        <img src="resources/img/home-icon.png" style="filter: invert(0.9);" height="40px" width="40px"/>
                                    </a>
                                </li> 
                                <!-- User Account: style can be found in dropdown.less -->
                                <li class="dropdown user user-menu">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        <img alt="noimg" src="data:image/jpeg;base64,${userPhoto}" onerror=this.src="resources/img/Profile.png" class="user-image" style="height:30px;width:30px" alt="User Image"/>
                                        <span class="hidden-xs"> ${userName}</span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <!-- User image -->
                                        <li class="user-header">
                                            <img alt="noimg" src="data:image/jpeg;base64,${userPhoto}" onerror=this.src="resources/img/Profile.png" class="img-circle" alt="User Image"/>

                                            <p>
                                                ${userName}
                                                <small></small>
                                            </p>
                                        </li>

                                        <li class="user-footer">
                                            <div class="pull-left">
                                                <!--<a href="#" class="btn btn-linkedin btn-flat">Profile</a>-->
                                            </div>
                                            <div class="pull-right">
                                                <a href="javascript:logOut();" class="btn btn-google btn-flat">Sign Out</a>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <!-- Control Sidebar Toggle Button -->
                                <li>
                                    <a href="javascript:logOut();" data-toggle="">Sign Out</a>
                                </li>
                            </ul>
                        </div>

                    </nav>
                </header>
                <!-- Left side column. contains the logo and sidebar -->
                <aside class="main-sidebar" style="background-image:url('resources/img/rightsbackground.jpg')">
                    <!-- sidebar: style can be found in sidebar.less -->
                    <section class="sidebar">
                        <!-- Sidebar user panel -->
                        <!--                        <div class="user-panel">
                                                    <div class="pull-left image">
                                                        <img src="resources/img/Koala.jpg" class="img-circle" alt="User Image">
                                                    </div>
                                                    <div class="pull-left info">
                                                        <p>Mohit kumar</p>
                                                        <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                                                    </div>
                                                </div>-->
                        <!-- search form -->
                        <form action="#" method="get" class="sidebar-form">
                            <!--                            <div class="input-group">
                                                            <input type="text" name="q" class="form-control" placeholder="Search...">
                                                            <span class="input-group-btn">
                                                                <button type="submit" name="search" id="search-btn" class="btn btn-flat">
                                                                    <i class="fa fa-search"></i>
                                                                </button>
                                                            </span>
                                                        </div>-->
                        </form>
                        <!-- /.search form -->
                        <!-- sidebar menu: : style can be found in sidebar.less -->
                        <%--<c:forEach items="${menu}" var="menu">--%>
                        <c:forEach items="${rightsid}" var="entry">
                            <ul class="sidebar-menu" data-widget="tree">
                                <!--<li class="header">MAIN NAVIGATION</li>-->
                                <li class=" treeview">
                                    <a href="#">
                                        <i class=""></i> <span>${entry.key}</span>
                                        <span class="pull-right-container">
                                            <i class="fa fa-angle-left pull-right"></i>
                                        </span>
                                    </a>

                                    <ul class="treeview-menu" style="background-color:transparent">
                                        <!--<li class="active"><a href="javascript:loadLink('coumtrymaster/getCountryList')"><i class="fa fa-angle-right"></i> List Country Master  </a></li>-->
                                        <%--<c:forEach items="${rightsid}" var="submenu">
                                            <!--<li class="active"><a href="javascript:loadLink('${submenu[2]}')"><i class="fa fa-angle-right"></i> ${submenu[1]} </a></li>-->
                                            </c:forEach>--%>
                                        <c:forEach items="${entry.value}" var="item" >
                                            <li class="active"><a href="javascript:loadLink('${item[2]}','${item[5]}')"><i class="fa fa-angle-right"></i> ${item[1]} </a></li>  
                                            </c:forEach>
                                    </ul>
                                </li>
                            </ul>
                        </c:forEach>
                    </section>
                    <!-- /.sidebar -->
                </aside>

                <!-- Content Wrapper. Contains page content -->
                <div class="formheading">
                    <div class="content-wrapper" >
                        <!-- Content Header (Page header) -->
                        <section class="content-header"> 
                            <h1><b><i id="formTitle"></i></b></h1>
                            <ol class="breadcrumb">
                                <li data-toggle="tooltip" title="Change Institute"><i class=""><a href="loadPopup"><b>${institute}</b></a></i></li>
                                <!--<li class="active">Dashboard</li>-->
                            </ol>
                        </section>


                        <!-- From Content -->
                        <%-- here goes the all the jsps --%>
                        <section class="content hide" id="contentcontent" >
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="box" style="box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                                        <div class="box-header with-border bg-navy">
                                            <!--                                    <i class="fa fa-bars fa-2x" style="color:white"></i>-->
                                            <h3 class="box-title header_name" id="box-title"></h3>
                                            <div class="box-tools pull-right">                  
                                                <button onclick="javascript:closePage();" class="btn btn-box-tool"><i class="fa fa-close fa-2x" style="color:white"></i></button>
                                            </div>
                                        </div><!-- /.box-header -->
                                        <div class="box-body">
                                            <!-- <div   id="formcontent" style="overflow-x: auto;overflow-y: auto" > -->

                                            <div id="formcontent" >
                                            </div><!-- /.row -->
                                        </div><!-- ./box-body -->
                                    </div><!-- /.box -->
                                </div><!-- /.col -->
                            </div>
                        </section>
                        <!-- Main content -->
                        <section class="content">
                            <!-- Info boxes -->
                            <div class="row">
                                <div class="col-lg-12"  id="contentarea" style="overflow-x: auto;overflow-y: auto">
                                    <!-- Default box -->
                                    <input type="hidden" id="notiFlag" value="1"/>
                                    <div class="row" id="notificationArea">

                                        <div  id="notificationDetailArea"></div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-12" >
                                            <div class="box" style="margin-top:20px">
                                                <div class="box-header with-border">
                                                    <h3 class="box-title"><i class="fa fa-calendar" aria-hidden="true"></i> Calendar</h3>
                                                    <div class="box-tools pull-right">
                                                        <button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
                                                        <button data-widget="remove" class="btn btn-box-tool"><i class="fa fa-times"></i></button>
                                                    </div>
                                                </div>                              
                                                <!-- THE CALENDAR -->
                                                <div class="box-body">
                                                    <div style="border:1px solid lightblue"  id="calendar"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /.row -->
                        </section>
                        <!-- /.content -->
                    </div>
                    <!-- /.content-wrapper -->

                    <footer class="main-footer">
                        <div class="pull-right hidden-xs">
                            <b>Version</b> 1.0.0
                        </div>
                        <strong>Copyright &copy; 2014-2016 <a target="_new" href="http://jilit.co.in">Jilit</a>.</strong> All rights reserved.
                    </footer>

                    <!-- Control Sidebar -->
                    <!--<aside class="control-sidebar control-sidebar-dark">-->
                    <!-- Create the tabs -->
                    <!--                    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
                                            <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
                                            <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
                                        </ul>-->
                    <!-- Tab panes -->

                    <!--</aside>-->
                    <!-- /.control-sidebar -->
                    <!-- Add the sidebar's background. This div must be placed
                         immediately after the control sidebar -->
                    <div class="control-sidebar-bg"></div>
                </div>
            </div>
        <!-- ./wrapper -->

        <script src="resources/jquery/jquery.min.js"></script>
        <script src='resources/js/moment.min.js'></script>
        <script src='resources/fullcalender/fullcalender.js'></script>
        <script src="resources/js/pace.js"></script>
        <script src="resources/bootstrap/bootstrap.min.js"></script>
        <script src='resources/dateTimePicker/bootstrap-datetimepicker.min.js'></script>
        <script src="resources/js/raphael.min.js"></script>
        <script src="resources/morris/morris.min.js"></script>
        <script src="resources/js/fastclick.js"></script>
        <script src="resources/adminLTE/adminlte.min.js"></script>
        <script src="resources/jquery/jquery.sparkline.min.js"></script>
        <script src="resources/jquery/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="resources/jquery/jquery-jvectormap-world-mill-en.js"></script>
        <!--SlimScroll--> 
        <script src="resources/jquery/jquery.slimscroll.min.js"></script>
        <!--ChartJS--> 
        <script src="resources/js/Chart.js"></script>
        <script src="resources/bootstrap/bootstrap-maxlength.js"></script>
        <script src="resources/datatables/jquery.dataTables.min.js"></script>
        <script  src="resources/datatables/dataTables.bootstrap.min.js"></script>
        <script type="text/javascript" src="resources/datatables/extensions/button/buttons.colVis.min.js"></script>
        <script type="text/javascript" src="resources/datatables/extensions/button/buttons.print.min.js"></script>
        <!--<script type="text/javascript" src="resources/datatables/otherHtml5Buttons/pdfmake.min.js"></script>-->
        <script type="text/javascript" src="resources/datatables/extensions/button/buttons.html5.min.js"></script>
        <script type="text/javascript" src="resources/datatables/extensions/button/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="resources/datatables/otherHtml5Buttons/jszip.min.js"></script>
        <script type="text/javascript" src="resources/datatables/otherHtml5Buttons/vfs_fonts.js"></script>
        <script type="text/javascript" src="resources/datatables/js/dataTables.bootstrap.js"></script> 
        <script type="text/javascript" src="resources/datatables/responsive/dataTables.responsive.js"></script>
        <!--<script type="text/javascript" src="resources/datatables/extensions/Responsive/js/responsive.bootstrap.min.js"></script>-->
        <script src="resources/jquery/jquery.blockUI.js"></script>
        <script src="resources/jquery/jquery.form-validator.js"></script>
        <script src="resources/jquery/jquery.multi-select.js"></script>
        <script src="resources/jquery/jquery.quicksearch.js"></script>
        <script src="resources/js/app.min.js"></script>
        <!--<script src="resources/js/chosen.jquery.js"></script>-->
        <!--<script src="resources/js/chosen.jquery.min.js"></script>-->
        <script src="resources/chosen-js/chosen.jquery.min.js"></script>
        <script src="resources/js/toastr.min.js"></script>
        <!--<script src="resources/js/bootstrap-modal.min.js"></script>-->
        <script src="resources/js/bootstrap-dialog.min.js"></script>
        <script src="resources/buttonJs/commonButton.js"></script>
        <script src="resources/js/dynamicTable.js"></script>
        <script src="resources/js/jquery.bootstrap.wizard.js"></script>
        <!--<script src="resources/wizard/wizard.js"></script>-->
        <script src="resources/bootstrap/jquery.bootstrap-duallistbox.js"></script>
        <script>
                                                    function getValue() {
                                                        var v = $("#cc").val();
                                                        var val = v.split('');
                                                        return val[3];
                                                    }
                                                    var calendar = $('#calendar').fullCalendar({});

                                                    $(document).ready(function () {
                                                        $('[data-toggle="tooltip"]').tooltip();
                                                    });

                                                    $(window).on('keydown', function (event)
                                                    {
                                                        if (event.keyCode == 123)
                                                        {
                                                            return false;
                                                        } else if (event.ctrlKey && event.shiftKey && event.keyCode == 73)
                                                        {
                                                            return false;  //Prevent from ctrl+shift+i
                                                        }
                                                    });
                                                    $(document).on("contextmenu", function (e)
                                                    {
                                                        e.preventDefault();
                                                    });
                                                    //browser back button stop code
                                                    window.history.forward();
                                                    function noBack()
                                                    {
                                                        window.history.forward();
                                                    }

                                                    function loadLink(link, pageName) {
                                                        $(".header_name").html("");
                                                        $("#formcontent").html("");
                                                        $("#contentcontent").addClass('hide');
                                                        $("#formTitle").html(pageName);
                                                        loadForm(link);
                                                    }
                                                    function loadForm(link)
                                                    {
                                                        Pace.start();
                                                        block_screen();
                                                        $(".header_name").html("");
                                                        $("#formcontent").html("");
                                                        $("#contentcontent").addClass('hide');
                                                        $("#contentarea").html('<div class="text-center row" style="margin-top:30px;"><i class="fa fa-refresh fa-spin fa-3x"></></div>');
                                                        $("#contentarea").load(link, function () {
                                                            Pace.stop();
                                                            unblock_screen();
                                                        });
                                                        var body = $("html, body");
                                                        body.stop().animate({scrollTop: 0}, 500, 'swing');
                                                    }
                                                    function block_screen() {
                                                        $('<div id="screenBlock"></div>').appendTo('body');
                                                        $('#screenBlock').css({opacity: 0, width: $(document).width(), height: $(document).height()});
                                                        $('#screenBlock').addClass('blockDiv');
                                                        $('#screenBlock').animate({opacity: 0.0}, 200);
                                                    }
                                                    function unblock_screen() {
                                                        $('#screenBlock').animate({opacity: 0}, 200, function () {
                                                            $('#screenBlock').remove();
                                                        });
                                                    }
                                                    function closePage() {
                                                        $("#formcontent").html("");
                                                        $("#contentcontent").addClass('hide');
                                                    }
                                                    function blank() {
                                                        $("#dashboard").addClass('hide');
                                                    }
                                                    function logOut() {
                                                        var oauth_server_url = document.getElementById("oauth_server_url").value.trim();
                                                        window.location.href = oauth_server_url + "/OAuthServer/logout.do?";
                                                    }
                                                    function changeModule() {
                                                        var oauth_server_url = document.getElementById("oauth_server_url").value.trim();
                                                        window.location.href = oauth_server_url + "/index";
                                                    }

                                                    toastr.options = {
                                                        "closeButton": true,
                                                        "debug": false,
                                                        "newestOnTop": false,
                                                        "progressBar": false,
                                                        "positionClass": "toast-top-right",
                                                        "preventDuplicates": false,
                                                        "onclick": null,
                                                        "showDuration": "300",
                                                        "hideDuration": "1000",
                                                        "timeOut": "5000",
                                                        "extendedTimeOut": "1000",
                                                        "showEasing": "swing",
                                                        "hideEasing": "linear",
                                                        "showMethod": "fadeIn",
                                                        "hideMethod": "fadeOut"
                                                    }
                                                    function hello() {
                                                        loadForm('complainMaster/add');
                                                    }
                                                    $(document).ajaxError(function myErrorHandler(event, xhr, ajaxOptions, thrownError) {
                                                        if (xhr.responseText != 'undefined' && xhr.responseText.includes("OAuthServer/logout.do")) {
                                                            logOut();
                                                        }
                                                    });
        </script>
    </body>
</html>

