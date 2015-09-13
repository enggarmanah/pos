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
                    <li><a href="#top"><%= StringsUtil.getString(lang, "menu_guide") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_pricing'"><%= StringsUtil.getString(lang, "menu_pricing") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_contact'"><%= StringsUtil.getString(lang, "menu_contact") %></a></li>
                </ul>
            </div>
            <div class="col-md-18 col-sm-18 hidden-md hidden-sm hidden-xs" id="templatemo-nav-bar">
                <ul class="nav navbar-right">
                    <li><a href="home?lang=<%= lang %>#templatemo_slideshow'"><%= StringsUtil.getString(lang, "menu_home") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_functionality'"><%= StringsUtil.getString(lang, "menu_functionality") %></a></li>
                    <li><a href="home?lang=<%= lang %>#templatemo_gallery'"><%= StringsUtil.getString(lang, "menu_gallery") %></a></li>
                    <li><a href="#top"><%= StringsUtil.getString(lang, "menu_guide") %></a></li>
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
            <h1><%= StringsUtil.getString(lang, "menu_guide") %></h1>
        </div>
        <div class="row">
            <div class="col-sm-5">	
                <img class="img-rounded" src="images/guide_registration.jpg" alt="image 4" />	
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "guide_register") %></h2>
                <p><%= StringsUtil.getString(lang, "guide_register_desc") %><br>
				<a href="#templatemo_guide_registration" class="btn btn-default margin_top"><%= StringsUtil.getString(lang, "read_more") %></a>
                </p>                
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-5">
                <img class="img-rounded" src="images/guide_user_access.jpg" alt="image 5" />
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "guide_access") %></h2>
                <p><%= StringsUtil.getString(lang, "guide_access_desc") %><br>
				<a href="#templatemo_guide_user_access" class="btn btn-default margin_top"><%= StringsUtil.getString(lang, "read_more") %></a>
				</p>
            </div>
        </div><!-- end of row -->
        <div class="row">
            <div class="col-sm-5">	
                <img class="img-rounded" src="images/guide_merchant_profile.jpg" alt="image 6" />	
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "guide_profile") %></h2>
                <p><%= StringsUtil.getString(lang, "guide_profile_desc") %><br>
				<a href="#templatemo_guide_merchant" class="btn btn-default margin_top"><%= StringsUtil.getString(lang, "read_more") %></a>
				</p>
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-5">
                <img class="img-rounded" src="images/guide_product.jpg" alt="image 7" />
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "guide_product") %></h2>
                <p><%= StringsUtil.getString(lang, "guide_product_desc") %><br>
				<a href="#templatemo_guide_product" class="btn btn-default margin_top"><%= StringsUtil.getString(lang, "read_more") %></a>
				</p>
            </div>
        </div><!-- end of row -->
        <div class="row">
            <div class="col-sm-5">	
                <img class="img-rounded" src="images/guide_others.jpg" alt="image 8" />	
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "guide_bills") %></h2>
                <p><%= StringsUtil.getString(lang, "guide_bills_desc") %><br>
				<a href="#templatemo_guide_others" class="btn btn-default margin_top"><%= StringsUtil.getString(lang, "read_more") %></a></p>
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-5">
                <img class="img-responsive" src="images/guide_printer.jpg" alt="image 9" />
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "guide_printer") %></h2>
                <p><%= StringsUtil.getString(lang, "guide_printer_desc") %><br>
				<a href="#templatemo_guide_printer" class="btn btn-default margin_top"><%= StringsUtil.getString(lang, "read_more") %></a>
				</p>
            </div>
        </div><!-- end of row -->
    </div>
</section> <!-- end of templatemo_functionality -->
<!-- solid line -->
<div class="container">
    <div class="solidline"></div>
</div>
<section id="templatemo_guide_registration" class="templatemo_guide_detail">
    <div id="gallery" class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_guide") %> - <%= StringsUtil.getString(lang, "guide_register") %></h1>
        </div>
        <div class="row-compact">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
				<h2><%= StringsUtil.getString(lang, "guide_register") %> </h2>
				<p><%= StringsUtil.getString(lang, "guide_register_info") %></p>
			</div>
        </div>
        <ul class="thumbs-guide row-compact">
			<li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_register_step_1") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/login_01.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_register_step_2") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/registration_01.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_register_step_3") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/registration_02.png" alt="image 3" />
            	</div>
            </li>
            <li class="ol-sm-2 hidden-xs"></li>
        </ul>
        <ul class="thumbs-guide row-compact">
			<li class="col-sm-1 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_register_step_4") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/registration_03.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_register_step_5") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/registration_04.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_register_step_6") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/login_02.png" alt="image 3" />
            	</div>
            </li>
            <li class="col-md-1 col-sm-1 hidden-xs"></li>
        </ul>
    </div>
</section> <!-- end of templatemo_guide_registration -->
<section id="templatemo_guide_user_access" class="templatemo_guide_detail">
    <div id="gallery" class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_guide") %> - <%= StringsUtil.getString(lang, "guide_access") %></h1>
        </div>
        <div class="row-compact">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
				<h2><%= StringsUtil.getString(lang, "guide_access") %></h2>
				<p><%= StringsUtil.getString(lang, "guide_access_info_1") %></p>
				<p><%= StringsUtil.getString(lang, "guide_access_info_2") %></p>
				<p><%= StringsUtil.getString(lang, "guide_access_info_3") %>
				   <br><br>
				</p>
			</div>
        </div>
        <ul class="thumbs-guide row-compact">
			<li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_access_step_1") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/login_02.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_access_step_2") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/user_access_01.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_access_step_3") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/user_access_02.png" alt="image 3" />
            	</div>
            </li>
            <li class="ol-sm-2 hidden-xs"></li>
        </ul>
    </div>
</section> <!-- end of templatemo_guide_user_access -->
<section id="templatemo_guide_merchant" class="templatemo_guide_detail">
    <div id="gallery" class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_guide") %> - <%= StringsUtil.getString(lang, "guide_profile") %></h1>
        </div>
        <div class="row-compact">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
				<h2><%= StringsUtil.getString(lang, "guide_profile") %></h2>
				<p><%= StringsUtil.getString(lang, "guide_profile_info_1") %></p>
				<p><%= StringsUtil.getString(lang, "guide_profile_info_2") %></p>
				<p><%= StringsUtil.getString(lang, "guide_profile_info_3") %></p>
				<p><%= StringsUtil.getString(lang, "guide_profile_info_4") %></p>
				<p><%= StringsUtil.getString(lang, "guide_profile_info_5") %>
					<br><br>
				</p>
			</div>
        </div>
        <ul class="thumbs-guide row-compact">
			<li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_profile_step_1") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/merchant_01.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_profile_step_2") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/merchant_02.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_profile_step_3") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/merchant_03.png" alt="image 3" />
            	</div>
            </li>
            <li class="ol-sm-2 hidden-xs"></li>
        </ul>
    </div>
</section> <!-- end of templatemo_guide_merchant -->
<section id="templatemo_guide_product" class="templatemo_guide_detail">
    <div id="gallery" class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_guide") %> - <%= StringsUtil.getString(lang, "guide_product") %></h1>
        </div>
        <div class="row-compact">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
				<h2><%= StringsUtil.getString(lang, "guide_product") %></h2>
				<p><%= StringsUtil.getString(lang, "guide_product_info_1") %></p>
				<p><%= StringsUtil.getString(lang, "guide_product_info_2") %></p>
				<p><%= StringsUtil.getString(lang, "guide_product_info_3") %></p>
				<p><%= StringsUtil.getString(lang, "guide_product_info_4") %></p>
				<p><%= StringsUtil.getString(lang, "guide_product_info_5") %></p>
				<p><%= StringsUtil.getString(lang, "guide_product_info_6") %></p>
				<p><%= StringsUtil.getString(lang, "guide_product_info_7") %>
					<br><br>
				</p>
			</div>
        </div>
        <ul class="thumbs-guide row-compact">
			<li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_product_step_1") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/product_group_01.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_product_step_2") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/product_01.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_product_step_3") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/product_02.png" alt="image 3" />
            	</div>
            </li>
            <li class="ol-sm-2 hidden-xs"></li>
        </ul>
    </div>
</section> <!-- end of templatemo_guide_user_access -->
<section id="templatemo_guide_others" class="templatemo_guide_detail">
    <div id="gallery" class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_guide") %> - <%= StringsUtil.getString(lang, "guide_bills") %></h1>
        </div>
        <div class="row-compact">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
				<h2><%= StringsUtil.getString(lang, "guide_bills") %></h2>
				<p><%= StringsUtil.getString(lang, "guide_bills_info_1") %></p>
				<p><%= StringsUtil.getString(lang, "guide_bills_info_2") %></p>
                <p><%= StringsUtil.getString(lang, "guide_bills_info_3") %></p>
				<p><%= StringsUtil.getString(lang, "guide_bills_info_4") %></p>
				<p><%= StringsUtil.getString(lang, "guide_bills_info_5") %>
				   <br><br>
				</p>
			</div>
        </div>
        <ul class="thumbs-guide row-compact">
			<li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_bills_step_1") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/bills_01.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_bills_step_2") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/bills_02.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_bills_step_3") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/inventory_01.png" alt="image 3" />
            	</div>
            </li>
            <li class="ol-sm-2 hidden-xs"></li>
        </ul>
        <ul class="thumbs-guide row-compact">
			<li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_bills_step_4") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/inventory_02.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_bills_step_5") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/cashflow_01.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_bills_step_6") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/cashflow_02.png" alt="image 3" />
            	</div>
            </li>
            <li class="ol-sm-2 hidden-xs"></li>
        </ul>
    </div>
</section> <!-- end of templatemo_guide_others -->
<section id="templatemo_guide_printer" class="templatemo_guide_detail">
    <div id="gallery" class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_guide") %> - <%= StringsUtil.getString(lang, "guide_printer") %></h1>
        </div>
        <div class="row-compact">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
				<h2><%= StringsUtil.getString(lang, "guide_printer") %></h2>
				<p>
					<%= StringsUtil.getString(lang, "guide_printer_info_1") %>
				   <br><br>
				</p>
			</div>
        </div>
        <ul class="thumbs-guide row-compact">
			<li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_printer_step_1") %>
				</div>
            	<div>
                    <img src="images/screen<%= img %>/printer_01.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_printer_step_2") %>
				</div>
                <div>
                    <img src="images/screen<%= img %>/printer_02.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            	<div class="col-sm-6 guide-info">
					<%= StringsUtil.getString(lang, "guide_printer_step_3") %>
				</div>
            	<div>
            		<img src="images/screen<%= img %>/printer_03.png" alt="image 3" />
            	</div>
            </li>
            <li class="ol-sm-2 hidden-xs"></li>
        </ul>
    </div>
</section> <!-- end of templatemo_guide_user_access -->
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