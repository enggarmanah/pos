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
                    <li><a href="#templatemo_slideshow"><%= StringsUtil.getString(lang, "menu_home") %></a></li>
                    <li><a href="#templatemo_functionality"><%= StringsUtil.getString(lang, "menu_functionality") %></a></li>
                    <li><a href="#templatemo_gallery"><%= StringsUtil.getString(lang, "menu_gallery") %></a></li>
                    <li><a onClick="javascript:window.location.href='guide?lang=<%= lang %>'" href="#"><%= StringsUtil.getString(lang, "menu_guide") %></a></li>
                    <li><a href="#templatemo_pricing"><%= StringsUtil.getString(lang, "menu_pricing") %></a></li>
                    <li><a href="#templatemo_contact"><%= StringsUtil.getString(lang, "menu_contact") %></a></li>
                </ul>
            </div>
            <div class="col-md-18 col-sm-18 hidden-md hidden-sm hidden-xs" id="templatemo-nav-bar">
                <ul class="nav navbar-right">
                    <li><a href="#templatemo_slideshow"><%= StringsUtil.getString(lang, "menu_home") %></a></li>
                    <li><a href="#templatemo_functionality"><%= StringsUtil.getString(lang, "menu_functionality") %></a></li>
                    <li><a href="#templatemo_gallery"><%= StringsUtil.getString(lang, "menu_gallery") %></a></li>
                    <li><a onClick="javascript:window.location.href='guide?lang=<%= lang %>'" href="#"><%= StringsUtil.getString(lang, "menu_guide") %></a></li>
                    <li><a href="#templatemo_pricing"><%= StringsUtil.getString(lang, "menu_pricing") %></a></li>
                    <li><a href="#templatemo_contact"><%= StringsUtil.getString(lang, "menu_contact") %></a></li>
                </ul>
            </div>
        </div>
    </div>
</header><!-- end of templatemo_header -->
<section id="templatemo_slideshow">
    <div class="container">
        <div id="main-slider" class="flexslider">
        <ul class="slides">
            <li class="row">
                <img src="images/home_smartphone.jpg" alt="slider image 1" />
                <div class="flex-caption col-lg-5 col-lg-push-1 hidden-xs">
                    <h3>Android POS App</h3> 
                    <p><%= StringsUtil.getString(lang, "slideshow_1") %></p>
                </div>
                <div class="flex-button"><a target="_blank" href="https://www.youtube.com/watch?v=QQEMGK3UlUI"><%= StringsUtil.getString(lang, "view_demo") %></a></div>
            </li>
            <li class="row">
                <img src="images/templatemo_banner_image_3.jpg" alt="slider image 3" />
                <div class="flex-caption col-lg-5 col-lg-push-1 hidden-xs">
                    <h3>Cloud Solutions</h3> 
                    <p><%= StringsUtil.getString(lang, "slideshow_2") %></p> 
                </div>
                <div class="flex-button"><a target="_blank" href="https://www.youtube.com/watch?v=QQEMGK3UlUI"><%= StringsUtil.getString(lang, "view_demo") %></a></div>
            </li>
            <li class="row">
                <img src="images/templatemo_banner_image_2.jpg" alt="slider image 2" />
                <div class="flex-caption col-lg-5 col-lg-push-1 hidden-xs">
                    <h3>Technology That Works For You</h3> 
                    <p><%= StringsUtil.getString(lang, "slideshow_3") %></p> 
                </div>
                <div class="flex-button"><a target="_blank" href="https://www.youtube.com/watch?v=QQEMGK3UlUI"><%= StringsUtil.getString(lang, "view_demo") %></a></div>
            </li>
            
        </ul>
        </div><!-- end of main-slider -->
    </div>	
</section><!-- end of templatemo_slideshow -->
<section id="templatemo_language">
	<div class="container">
		<div class="row">
			<div class="col-md-24 col-xs-24">
				<a href="/home?lang=en"><img src="images/flag_united_kingdom.png" alt="Language English"/></a>
				<a href="/home?lang=id"><img src="images/flag_indonesia.png" alt="Language Indonesian"/></a> 
        	</div>
		</div>
	</div>
</section>
<section id="templatemo_about">
    <div class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "about_tokoku") %></h1>
        </div>
        <div class="row">
            <div class="col-md-1"></div>	
            <div class="col-md-3 col-sm-3 col-xs-24">
                <img src="images/cloud.png" alt="image 1"/>
            </div>
            <div class="col-md-1"></div>	
            <div class="col-md-18">
                <h2><%= StringsUtil.getString(lang, "about_1_title") %></h2>
                <p><%= StringsUtil.getString(lang, "about_1_desc") %></p>
            </div>
        </div><!-- end of row --><div class="clear"></div>
        <div class="row">
            <div class="col-md-1"></div>	
            <div class="col-md-3 col-sm-3 col-xs-24">
                <img src="images/shield.png" alt="image 3"/>
            </div>
            <div class="col-md-1"></div>	
            <div class="col-md-18">
                <h2><%= StringsUtil.getString(lang, "about_2_title") %></h2>
                <p><%= StringsUtil.getString(lang, "about_2_desc") %></p>
            </div>
        </div><!-- end of row -->
        <div class="row">
            <div class="col-md-1"></div>	
            <div class="col-md-3 col-sm-3 col-xs-24">
                <img src="images/location.png" alt="image 3"/>
            </div>
            <div class="col-md-1"></div>	
            <div class="col-md-18">
                <h2><%= StringsUtil.getString(lang, "about_3_title") %></h2>
                <p><%= StringsUtil.getString(lang, "about_3_desc") %></p>
            </div>
        </div><!-- end of row -->
        <div class="row">
            <div class="col-md-1"></div>	
            <div class="col-md-3 col-sm-3 col-xs-24">
            <img src="images/printer.png" alt="image 2"/>
            </div>
            <div class="col-md-1"></div>	
            <div class="col-md-18">
                <h2><%= StringsUtil.getString(lang, "about_4_title") %></h2>
                <p><%= StringsUtil.getString(lang, "about_4_desc") %></p>
            </div>
        </div><!-- end of row -->
        <div class="clear"></div>
        <div class="row">
            <div class="col-md-1"></div>	
            <div class="col-md-3 col-sm-3 col-xs-24">
                <img src="images/mobile_graps.png" alt="image 3"/>
            </div>
            <div class="col-md-1"></div>	
            <div class="col-md-18">
                <h2><%= StringsUtil.getString(lang, "about_5_title") %></h2>
                <p><%= StringsUtil.getString(lang, "about_5_desc") %></p>
            </div>
        </div><!-- end of row -->
    </div> 
</section><!-- end of templatemo_about -->
<div class="container">
    <div class="solidline"></div>
</div><!-- solid line -->
<section id="templatemo_functionality">
    <div class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_functionality") %></h1>
        </div>
        <div class="row">
            <div class="col-sm-5">	
                <img class="img-rounded" src="images/func_cashier.jpg" alt="image 4" />	
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "func_cashier") %></h2>
                <p><%= StringsUtil.getString(lang, "func_cashier_desc") %></p>
            </div>	
            <div class="col-sm-1"></div>
            <div class="col-sm-5">
                <img class="img-rounded" src="images/func_table.jpg" alt="image 5" />
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "func_waitress") %></h2>
                <p><%= StringsUtil.getString(lang, "func_waitress_desc") %></p>
            </div>
        </div><!-- end of row -->
        <div class="row">
            <div class="col-sm-5">	
                <img class="img-rounded" src="images/func_bills.jpg" alt="image 6" />	
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "func_bills") %></h2>
                <p><%= StringsUtil.getString(lang, "func_bills_desc") %></p>
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-5">
                <img class="img-rounded" src="images/func_cashflow.jpg" alt="image 7" />
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "func_cashflow") %></h2>
                <p><%= StringsUtil.getString(lang, "func_cashflow_desc") %></p>
            </div>
        </div><!-- end of row -->
        <div class="row">
            <div class="col-sm-5">	
                <img class="img-rounded" src="images/func_inventory.jpg" alt="image 8" />	
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "func_inventory") %></h2>
                <p><%= StringsUtil.getString(lang, "func_inventory_desc") %></p>
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-5">
                <img class="img-responsive" src="images/templatemo_image_9.jpg" alt="image 9" />
            </div>
            <div class="col-sm-6">
                <h2><%= StringsUtil.getString(lang, "func_report") %></h2>
                <p><%= StringsUtil.getString(lang, "func_report_desc") %></p>
            </div>
        </div><!-- end of row -->
    </div>
</section> <!-- end of templatemo_functionality -->
<!-- solid line -->
<div class="container">
    <div class="solidline"></div>
</div>
<section id="templatemo_gallery">
    <div id="gallery" class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_gallery") %></h1>
        </div>
        <ul class="thumbs row">
            <li class="col-sm-1 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Merchant Login" 
                        href="images/screen<%= img %>/login_01.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/login_01.png" alt="image 1" />	
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="User Login" 
                        href="images/screen<%= img %>/login_02.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/login_02.png" alt="image 2" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            <div>
                <a 
                    class="overlay" 
                    data-lightbox="gallery" 
                    data-caption="Registration" 
                    href="images/screen<%= img %>/registration_01.png" >
                    <span class="glyphicon glyphicon-zoom-in"></span>
                </a>
                <img src="images/screen<%= img %>/registration_01.png" alt="image 3" />
            </div>
            </li>
            <li class="col-md-1 col-sm-1 hidden-xs"></li>
            
        </ul>
        <ul class="thumbs row">
            <li class="col-sm-1 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Main Menu" 
                        href="images/screen<%= img %>/menu_01.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/menu_01.png" alt="image 4" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Transaction Report for Indonesia (IDR)" 
                        href="images/screen<%= img %>/report_trans_01.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/report_trans_01.png" alt="image 5" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs">
            </li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Transaction Report for Spain (Euro)" 
                        href="images/screen<%= img %>/report_trans_02.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/report_trans_02.png" alt="image 7" />
                </div>
            </li>
            <li class="col-md-1"></li>
        </ul>
        <ul class="thumbs row">
            <li class="col-sm-1 hidden-xs">
            </li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Cashier, Transaction Items" 
                        href="images/screen<%= img %>/cashier_01.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/cashier_01.png" alt="image 7" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Cashier, Product Purchase Quantity" 
                        href="images/screen<%= img %>/cashier_02.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/cashier_02.png" alt="image 8" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
	            <div>
	                <a 
	                    class="overlay" 
	                    data-lightbox="gallery" 
	                    data-caption="Transaction Payment" 
	                    href="images/screen<%= img %>/cashier_04.png">
	                    <span class="glyphicon glyphicon-zoom-in"></span>
	                </a>
	                <img src="images/screen<%= img %>/cashier_04.png" alt="image 9" />
	            </div>
            </li>
        </ul>
        <ul class="thumbs row">
            <li class="col-sm-1 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Bills. Red indicates pastdue bils. Orange indicates unpaid bills." 
                        href="images/screen<%= img %>/report_bills_01.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/report_bills_01.png" alt="image 7" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Cashflow Report" 
                        href="images/screen<%= img %>/report_cashflow_02.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/report_cashflow_02.png" alt="image 8" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            <div>
                <a 
                    class="overlay" 
                    data-lightbox="gallery" 
                    data-caption="Inventory Report" 
                    href="images/screen<%= img %>/report_inventory_02.png">
                    <span class="glyphicon glyphicon-zoom-in"></span>
                </a>
                <img src="images/screen<%= img %>/report_inventory_02.png" alt="image 9" />
            </div>
            </li>
        </ul>
        <ul class="thumbs row">
            <li class="col-sm-1 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Favorite Customer Listing" 
                        href="images/screen<%= img %>/favorite_customer_01.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/favorite_customer_01.png" alt="image 7" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs"></li>
            <li class="item-thumbs col-sm-6 col-xs-8">
                <div>
                    <a 
                        class="overlay" 
                        data-lightbox="gallery" 
                        data-caption="Favorite Customer Details" 
                        href="images/screen<%= img %>/favorite_customer_02.png">
                        <span class="glyphicon glyphicon-zoom-in"></span>
                    </a>
                    <img src="images/screen<%= img %>/favorite_customer_02.png" alt="image 8" />
                </div>
            </li>
            <li class="col-sm-2 hidden-xs">
          	</li>
            <li class="item-thumbs col-sm-6 col-xs-8">
            <div>
                <a 
                    class="overlay" 
                    data-lightbox="gallery" 
                    data-caption="User Access Rights" 
                    href="images/screen<%= img %>/user_access_03.png">
                    <span class="glyphicon glyphicon-zoom-in"></span>
                </a>
                <img src="images/screen<%= img %>/user_access_03.png" alt="image 9" />
            </div>
            </li>
        </ul>
    </div>
</section> <!-- end of templatemo_gallery -->
<!-- solid line -->
<div class="container">
    <div class="solidline"></div>
</div>
<section id="templatemo_pricing">
    <div class="container">
        <div class="row">
            <h1><%= StringsUtil.getString(lang, "menu_pricing") %></h1>
        </div>
        <div class="row">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
                <h2><%= StringsUtil.getString(lang, "pricing_free_app") %></h2>
                <p><%= StringsUtil.getString(lang, "pricing_free_app_desc") %><br>
                <a href="https://play.google.com/store/apps/details?id=com.tokoku.pos" class="btn btn-default margin_top">Download</a>
                </p>
            </div>
        </div><!-- end of row -->
        <div class="row">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
                <h2><%= StringsUtil.getString(lang, "pricing_free_cloud") %></h2>
                <p><%= StringsUtil.getString(lang, "pricing_free_cloud_desc") %></p>
            </div>
        </div><!-- end of row -->
        <div class="row">
        	<div class="col-md-1"></div>
            <div class="col-sm-22">
                <h2><%= StringsUtil.getString(lang, "pricing_printer") %></h2>
                <p><%= StringsUtil.getString(lang, "pricing_printer_desc") %></p>
            </div>
        </div><!-- end of row -->
    </div>
</section> <!-- end of templatemo_pricing -->
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
                <form id="contact-form" action="/feedback" method="POST">
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
                    	<input name="lang" type="hidden" value="<%= lang %>"/>
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