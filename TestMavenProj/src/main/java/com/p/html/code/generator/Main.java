package com.p.html.code.generator;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> files = HtmlCodeGenerator.getAllHtmlFiles(
				"D:\\Prem\\CUST-INST\\apache-tomcat-8.5.56-windows-x86\\webapps\\my-pages\\other-sample-application\\Chart-Examples",
				"../examples");
		
		for (String string : files) {
			System.out.println(string);
		}
		System.out.println("\n\n\n");
		HtmlCodeGenerator.generateLinksHtmlTextFromList(files);
//		HtmlCodeGenerator.generateLinksHtmlFiles(LINKS_TEXT_ARRAY, "D:\\Prem\\CUST-INST\\apache-tomcat-8.5.56-windows-x86\\webapps\\my-pages\\other-sample-application\\Menu-Examples");
		// HtmlCodeGenerator.generateCanvasJsLinksHtmlText(CANVAS_JS_FILE_ARR);
		// HtmlCodeGenerator.canvasJsFiles("C:\\Users\\premendra.kumar\\Desktop\\DUMP\\charts\\canvasjs-3.0\\examples");
	}

	private static final String[] CANVAS_JS_FILE_ARR = { "01-overview/animated-chart.html",
			"01-overview/chart-from-json-data.html", "01-overview/chart-with-axis-scale-breaks.html",
			"01-overview/chart-with-crosshair.html", "01-overview/chart-with-custom-legend-chart.html",
			"01-overview/chart-with-image-overlay.html", "01-overview/chart-with-index-data-label.html",
			"01-overview/chart-with-inverted-reversed-axis.html", "01-overview/chart-with-logarithmic-axis.html",
			"01-overview/chart-with-secondary-axis.html", "01-overview/chart-with-zoom-pan.html",
			"01-overview/dynamic-chart.html", "01-overview/interactive-draggable-chart.html",
			"01-overview/multi-series-chart.html", "01-overview/null-data-chart.html",
			"01-overview/performance-demo.html", "02-line-spline-step-line-charts/dashed-line-chart.html",
			"02-line-spline-step-line-charts/dynamic-spline-chart.html",
			"02-line-spline-step-line-charts/line-chart-with-axis-scale-breaks.html",
			"02-line-spline-step-line-charts/line-chart-with-data-markers.html",
			"02-line-spline-step-line-charts/line-chart-with-logarithmic-axis.html",
			"02-line-spline-step-line-charts/line-chart-with-multiple-axis.html",
			"02-line-spline-step-line-charts/line-chart-with-zoom-pan.html",
			"02-line-spline-step-line-charts/line-chart.html",
			"02-line-spline-step-line-charts/multi-series-line-chart.html",
			"02-line-spline-step-line-charts/multi-series-spline-chart.html",
			"02-line-spline-step-line-charts/multi-series-step-line-chart.html",
			"02-line-spline-step-line-charts/spline-chart-with-legends.html",
			"02-line-spline-step-line-charts/spline-chart-with-secondary-axis.html",
			"02-line-spline-step-line-charts/spline-chart.html", "02-line-spline-step-line-charts/step-line-chart.html",
			"03-stacked-spline-range-area-charts/area-chart.html",
			"03-stacked-spline-range-area-charts/multi-series-area-chart.html",
			"03-stacked-spline-range-area-charts/multi-series-range-area-chart.html",
			"03-stacked-spline-range-area-charts/multi-series-spline-area-chart.html",
			"03-stacked-spline-range-area-charts/range-area-chart.html",
			"03-stacked-spline-range-area-charts/range-spline-area-chart.html",
			"03-stacked-spline-range-area-charts/spline-area-chart.html",
			"03-stacked-spline-range-area-charts/stacked-area-100-chart-with-date-time-axis.html",
			"03-stacked-spline-range-area-charts/stacked-area-100-chart.html",
			"03-stacked-spline-range-area-charts/stacked-area-chart.html",
			"03-stacked-spline-range-area-charts/step-area-chart.html",
			"04-column-stacked-range-waterfall-charts/bar-chart-with-axis-scale-break.html",
			"04-column-stacked-range-waterfall-charts/bar-chart.html",
			"04-column-stacked-range-waterfall-charts/column-chart-with-multiple-axis.html",
			"04-column-stacked-range-waterfall-charts/column-chart.html",
			"04-column-stacked-range-waterfall-charts/multi-series-bar-chart.html",
			"04-column-stacked-range-waterfall-charts/multi-series-range-column-chart.html",
			"04-column-stacked-range-waterfall-charts/multi-series-waterfall-chart.html",
			"04-column-stacked-range-waterfall-charts/range-bar-chart.html",
			"04-column-stacked-range-waterfall-charts/range-column-chart.html",
			"04-column-stacked-range-waterfall-charts/stacked-bar-100-chart.html",
			"04-column-stacked-range-waterfall-charts/stacked-bar-chart.html",
			"04-column-stacked-range-waterfall-charts/stacked-column-100-chart.html",
			"04-column-stacked-range-waterfall-charts/stacked-column-chart.html",
			"04-column-stacked-range-waterfall-charts/waterfall-chart-with-custom-color.html",
			"04-column-stacked-range-waterfall-charts/waterfall-chart.html",
			"05-pie-doughnut-funnel-pyramid-charts/doughnut-chart-with-custom-inner-radius.html",
			"05-pie-doughnut-funnel-pyramid-charts/doughnut-Chart.html",
			"05-pie-doughnut-funnel-pyramid-charts/funnel-chart-with-custom-neck.html",
			"05-pie-doughnut-funnel-pyramid-charts/funnel-chart.html",
			"05-pie-doughnut-funnel-pyramid-charts/inverted-reversed-funnel-chart.html",
			"05-pie-doughnut-funnel-pyramid-charts/pie-chart-with-custom-radius.html",
			"05-pie-doughnut-funnel-pyramid-charts/pie-chart-with-legends.html",
			"05-pie-doughnut-funnel-pyramid-charts/pie-chart.html",
			"05-pie-doughnut-funnel-pyramid-charts/pyramid-chart-where-area-represents-value.html",
			"05-pie-doughnut-funnel-pyramid-charts/pyramid-chart-with-index-label-placed-Inside.html",
			"05-pie-doughnut-funnel-pyramid-charts/pyramid-chart.html",
			"06-candlestick-ohlc-charts/candlestick-line-chart.html",
			"06-candlestick-ohlc-charts/multi-series-candlestick-chart.html",
			"06-candlestick-ohlc-charts/ohlc-chart-from-json-data.html",
			"06-candlestick-ohlc-charts/ohlc-stock-chart.html",
			"07-scatter-bubble-charts/bubble-chart-with-data-marker.html",
			"07-scatter-bubble-charts/bubble-chart-with-zoom-pan.html", "07-scatter-bubble-charts/bubble-chart.html",
			"07-scatter-bubble-charts/multi-series-scatter-point-chart.html",
			"07-scatter-bubble-charts/scatter-point-chart-with-custom-marker.html",
			"07-scatter-bubble-charts/scatter-point-chart.html",
			"08-box-and-whisker-charts/box-and-whisker-chart-with-custom-color.html",
			"08-box-and-whisker-charts/box-and-whisker-chart-with-outliers.html",
			"08-box-and-whisker-charts/box-and-whisker-chart.html", "09-combination-charts/column-line-area-chart.html",
			"09-combination-charts/error-bar-chart.html", "09-combination-charts/error-chart.html",
			"09-combination-charts/error-line-chart.html", "09-combination-charts/ohlc-line-chart.html",
			"09-combination-charts/pareto-chart-with-index-data-label.html", "09-combination-charts/pareto-chart.html",
			"09-combination-charts/range-area-line-chart.html", "10-dynamic-charts/dynamic-column-chart.html",
			"10-dynamic-charts/dynamic-line-chart.html", "10-dynamic-charts/dynamic-multi-series-chart.html",
			"11-integration/jquery-charts.html", "11-integration/jquery-line-chart-with-zoom-pan.html",
			"11-integration/jquery-resizable-chart.html", "11-integration/jquery-spline-area-chart-in-tab.html",
			"11-integration/jquery-spline-chart-with-image-export.html" };

	private static final String[] LINKS_TEXT_ARRAY = { "001_Icon-Bar", "002_Menu-Icon", "003_Accordion", "004_Tabs",
			"005_Vertical-Tabs", "006_Tab-Headers", "007_Full-Page-Tabs", "008_Hover-Tabs", "009_Top-Navigation",
			"010_Responsive-Topnav", "011_Navbar-with-Icons", "012_Search-Menu", "013_Search-Bar",
			"014_Fixed-Sidebar-Full-Height", "015_Fixed-Sidebar-Auto-Height",
			"016_Responsive-Sidebar-Always-show-sidenav", "016_Responsive-Sidebar-Sidenav-Full-width",
			"016_Responsive-Sidebar-Sidenav-Overlay-Example",
			"016_Responsive-Sidebar-Sidenav-Push-Content-with-opacity", "016_Responsive-Sidebar-Sidenav-Push-Content",
			"016_Responsive-Sidebar-Sidenav-Right-sided-navigation", "016_Responsive-Sidebar-Sidenav-without-Animation",
			"017_Fullscreen-Navigation-from-the-side", "017_Fullscreen-Navigation-from-the-top",
			"017_Fullscreen-Navigation-without-animation", "018_Off-Canvas-Menu-with-opacity", "018_Off-Canvas-Menu",
			"019_Hover-Sidenav-Buttons", "020_Sidebar-with-Icons", "021_Horizontal-Scroll-Menu", "022_Vertical-Menu",
			"023_Bottom-Navigation", "024_Responsive-Bottom-Nav", "025_Bottom-Border-Nav-Links",
			"026_Right-Aligned-Menu-Links", "027_Centered-Menu-Link", "028_Equal-Width-Menu-Links", "029_Fixed-Menu",
			"030_Slide-Down-Bar-on-Scroll", "031_Hide-Navbar-on-Scroll", "032_Shrink-Navbar-on-Scroll",
			"033_Sticky-Navbar", "034_Navbar-on-Image", "035_Hover-Dropdowns", "036_Click-Dropdowns",
			"037_Dropdown-in-Topnav", "038_Dropdown-in-Sidenav", "039_Resp-Navbar-Dropdown", "040_Subnavigation-Menu",
			"041_Dropup", "042_Mega-Menu", "043_Mobile-Menu", "044_Curtain-Menu", "045_Collapsed-Sidebar",
			"046_Collapsed-Sidepanel", "047_Pagination", "048_Breadcrumbs", "049_Button-Group",
			"050_Vertical-Button-Group", "051_Sticky-Social-Bar", "052_Pill-Navigation", "053_Responsive-Header" };

	private static final String[] LINKS_TEXT_ARRAY1 = { "Icon-Bar", "Menu-Icon", "Accordion", "Tabs", "Vertical-Tabs",
			"Tab-Headers", "Full-Page-Tabs", "Hover-Tabs", "Top-Navigation", "Responsive-Topnav", "Navbar-with-Icons",
			"Search-Menu", "Search-Bar", "Fixed-Sidebar", "Side-Navigation", "Responsive-Sidebar",
			"Fullscreen-Navigation", "Off-Canvas-Menu", "Hover-Sidenav-Buttons", "Sidebar-with-Icons",
			"Horizontal-Scroll-Menu", "Vertical-Menu", "Bottom-Navigation", "Responsive-Bottom-Nav",
			"Bottom-Border-Nav-Links", "Right-Aligned-Menu-Links", "Centered-Menu-Link", "Equal-Width-Menu-Links",
			"Fixed-Menu", "Slide-Down-Bar-on-Scroll", "Hide-Navbar-on-Scroll", "Shrink-Navbar-on-Scroll",
			"Sticky-Navbar", "Navbar-on-Image", "Hover-Dropdowns", "Click-Dropdowns", "Dropdown-in-Topnav",
			"Dropdown-in-Sidenav", "Resp-Navbar-Dropdown", "Subnavigation-Menu", "Dropup", "Mega-Menu", "Mobile-Menu",
			"Curtain-Menu", "Collapsed-Sidebar", "Collapsed-Sidepanel", "Pagination", "Breadcrumbs", "Button-Group",
			"Vertical-Button-Group", "Sticky-Social-Bar", "Pill-Navigation", "Responsive-Header" };

}
