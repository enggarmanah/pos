<%@page import="com.app.posweb.server.ServerUtil"%>
<%@page import="com.app.posweb.server.StringsUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
	StringsUtil.loadProperties();
	String lang = "id".equals(request.getParameter("lang")) ? request.getParameter("lang") : "";
	String img = "id".equals(request.getParameter("lang")) ? "/" + request.getParameter("lang") : ""; 
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
<title>TokoKu - Android POS App</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<!-- 
Tamarillo Template 
http://www.templatemo.com/preview/templatemo_399_tamarillo 
-->
<meta name="author" content="" />
<!-- favicons -->
<link rel="shortcut icon" href="images/ic_launcher.png">
<!-- bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet" />
<!-- fancybox CSS -->
<link href="css/jquery.lightbox.css" rel="stylesheet" />
<!-- flex slider CSS -->
<link href="css/flexslider.css" rel="stylesheet" />
<!-- custom styles for this template -->
<link href="css/templatemo_style.css" rel="stylesheet" />
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->
</head>
<body>
<header>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-6 no-padding">
                <a href="#" id="templatemo_logo"><img src="images/tokoku.png" alt="tamarillo" class="logo"></a>
            </div>
            <div class="col-md-3 hidden-xs"></div>
            <div class="col-xs-3 col-xs-offset-20 visible-md visible-sm visible-xs">
                <a href="#" id="mobile_menu"><span class="glyphicon glyphicon-align-justify"></span></a>
            </div>
            <div class="col-xs-24 visible-md visible-sm visible-xs" id="mobile_menu_list">
                <ul>
                    <li><a href="home?lang=<%= lang %>#templatemo_slideshow'"><%= StringsUtil.getString(lang, "menu_home") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_functionality'"><%= StringsUtil.getString(lang, "menu_functionality") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_gallery'"><%= StringsUtil.getString(lang, "menu_gallery") %></a></li>
                    <li><a href="guide?lang=<%= lang %>'"><%= StringsUtil.getString(lang, "menu_guide") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_pricing'"><%= StringsUtil.getString(lang, "menu_pricing") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_contact'"><%= StringsUtil.getString(lang, "menu_contact") %></a></li>
                </ul>
            </div>
            <div class="col-md-18 col-sm-18 hidden-md hidden-sm hidden-xs" id="templatemo-nav-bar">
                <ul class="nav navbar-right">
                    <li><a href="home?lang=<%= lang %>#templatemo_slideshow'"><%= StringsUtil.getString(lang, "menu_home") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_functionality'"><%= StringsUtil.getString(lang, "menu_functionality") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_gallery'"><%= StringsUtil.getString(lang, "menu_gallery") %></a></li>
                    <li><a href="guide?lang=<%= lang %>'"><%= StringsUtil.getString(lang, "menu_guide") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_pricing'"><%= StringsUtil.getString(lang, "menu_pricing") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_contact'"><%= StringsUtil.getString(lang, "menu_contact") %></a></li>
                </ul>
            </div>
        </div>
    </div>
</header><!-- end of templatemo_header -->
<div id="top" class="container">
    <div class="solidline"></div>
</div><!-- solid line -->
<div class="container">
    <div class="solidline"></div>
</div><!-- solid line -->
<section id="templatemo_guide">
    <div class="container">
        <div class="row">
            <br><br>
            <h2 class="col-centered"><%= StringsUtil.getString(lang, "feedback_title") %></h2>
            <br><br>
            <h4 class="col-centered"><%= StringsUtil.getString(lang, "feedback_message") %></h4>
            <br><br>
        </div><!-- end of row -->
    </div>
</section> <!-- end of templatemo_functionality -->
<section id="templatemo_contact">
    <div class="container">
        <div class="row" id="templatemo_contact_gray_wap">
            <div class="col-md-24 col-sm-24">
                <h1 class="margin_top"><%= StringsUtil.getString(lang, "menu_contact") %></h1>
            </div>
            <!-- <div class="col-md-24 col-sm-24">
                <div id="map-canvas"></div>
            </div> -->
            <div class="col-md-9 col-sm-18 col-sm-push-3 col-xs-24">
                <p>
                    <span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp; TokoKu Marketing Office, <br />
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Jl. Merak 4 Gonilan Solo 57162, Indonesia<br />
                    <span class="glyphicon glyphicon-phone-alt"></span>&nbsp;&nbsp; Tel: +62 856-4745-3111<br />
                    <span class="glyphicon glyphicon-envelope"></span>&nbsp;&nbsp; Email: app.tokoku@gmail.com<br />
                    <span class="glyphicon glyphicon-globe"></span>&nbsp;&nbsp; Website: www.pos-tokoku.com
                </p>
                <ul class="icon">
                    <li><a href="#"><img src="images/templatemo_icon_1.png" alt="Skype" /></a></li>
                    <li><a href="#"><img src="images/templatemo_icon_2.png" alt="Google+" /></a></li>
                    <li><a href="#"><img src="images/templatemo_icon_3.png" alt="Instagram" /></a></li>
                    <li><a href="#"><img src="images/templatemo_icon_4.png" alt="RSS" /></a></li>
                </ul>
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-9  col-sm-18 col-sm-push-3">
                <form id="contact-form" action="#" method="get">
                    <label>
                        <span><%= StringsUtil.getString(lang, "contact_name") %></span>
                        <input name="name" type="text" id="name" maxlength="40" />
                  </label>
                    <label>
                        <span><%= StringsUtil.getString(lang, "contact_email") %></span>
                        <input name="email" type="text" id="email" maxlength="40" />
                  </label>
                    <label>
                        <span><%= StringsUtil.getString(lang, "contact_subject") %></span>
                        <input name="subject" type="text" id="subject" maxlength="100" />
                  </label>
                    <label>
                        <span><%= StringsUtil.getString(lang, "contact_message") %></span>
                        <textarea name="message" id="message"></textarea>
                    </label>
                    <label>
                        <button name="submit" type="submit"><%= StringsUtil.getString(lang, "contact_send") %></button>
                    </label>
                </form>
            </div>	
        </div> <!-- end of row -->
        <div class="row" id="templatemo_footer">
            <div class="col-md-24" >
                <p>Copyright &copy; 2015 TokoKu</p>
            </div>
        </div><!-- end of templatemo_footer -->
    </div>	
    <a href="#" class="scrollup"><i class="fa fa-angle-up active"></i></a>
</section><!-- end of templatemo_contact -->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false"></script>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.scrollto.min.js"></script>
<script src="js/jquery.easing.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.lightbox.min.js"></script>
<script src="js/jquery.flexslider.js"></script>
<script src="js/jquery.singlePageNav.min.js"></script>
<script src="js/templatemo_script.js"></script>
<!-- templatemo 399 tamarillo -->
</body>
</html>