package com.p.html.code.generator;

public class Main {

	public static void main(String[] args) {
		String href="https://www.w3schools.com/cssref/pr_pos_z-index.asp";
		String title="CSS z-index Property";
		String subtitle="Set the z-index for an image:";
		HtmlCodeGenerator.generateReferenceDiv(href,title,subtitle);
		
//		animatedPropertyFiles
		
		HtmlCodeGenerator.generateLinksHtmlFiles(animatedPropertyFiles, "C:\\Users\\premendra.kumar\\Desktop\\1");
		
		
//		HtmlCodeGenerator.generateGivenFiles(GIVEN_FILES,"C:\\Users\\premendra.kumar\\Desktop\\1");
		
		
//		List<String> files = HtmlCodeGenerator.getAllHtmlFiles(
//				"D:\\Prem\\CUST-INST\\apache-tomcat-8.5.56-windows-x86\\webapps\\my-pages\\other-sample-application\\Chart-Examples",
//				"../examples");
//		
//		for (String string : files) {
//			System.out.println(string);
//		}
//		System.out.println("\n\n\n");
		
		
//		HtmlCodeGenerator.generateLinksHtmlTextFromList(files);
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
	
	private static final String[] animatedPropertyFiles= {"background","background-color","background-position","background-size","border","border-bottom","border-bottom-color","border-bottom-left-radius","border-bottom-right-radius","border-bottom-width","border-color","border-left","border-left-color","border-left-width","border-right","border-right-color","border-right-width","border-spacing","border-top","border-top-color","border-top-left-radius","border-top-right-radius","border-top-width","bottom","box-shadow","clip","color","column-count","column-gap","column-rule","column-rule-color","column-rule-width","column-width","columns","filter","flex","flex-basis","flex-grow","flex-shrink","font","font-size","font-size-adjust","font-stretch","font-weight","grid","grid-area","grid-auto-columns","grid-auto-flow","grid-auto-rows","grid-column","grid-column-end","grid-column-gap","grid-column-start","grid-gap","grid-row","grid-row-end","grid-row-gap","grid-row-start","grid-template","grid-template-areas","grid-template-columns","grid-template-rows","height","left","letter-spacing","line-height","margin","margin-bottom","margin-left","margin-right","margin-top","max-height","max-width","min-height","min-width","object-position","opacity","order","outline","outline-color","outline-offset","outline-width","padding","padding-bottom","padding-left","padding-right","padding-top","perspective","perspective-origin","right","text-decoration-color","text-indent","text-shadow","top","transform","transform-origin","vertical-align","visibility","width","word-spacing","z-index"};
	
	private static final String[] GIVEN_FILES= {"32-CSS-break-before-Property.html","33-CSS-break-inside-Property.html","34-CSS-caption-side-Property.html","35-CSS-caret-color-Property.html","36-CSS-at-charset-Rule.html","37-CSS-clear-Property.html","38-CSS-clip-Property.html","39-CSS-clip-path-Property.html","40-CSS-color-Property.html","41-CSS-column-count-Property.html","42-CSS-column-fill-Property.html","43-CSS-column-gap-Property.html","44-CSS-column-rule-Property.html","45-CSS-column-rule-color-Property.html","46-CSS-column-rule-style-Property.html","47-CSS-column-rule-width-Property.html","48-CSS-column-span-Property.html","49-CSS-column-width-Property.html","50-CSS-columns-Property.html","51-CSS-content-Property.html","52-CSS-counter-increment-Property.html","53-CSS-counter-reset-Property.html","54-CSS-cursor-Property.html","55-CSS-direction-Property.html","56-CSS-display-Property.html","57-CSS-empty-cells-Property.html","58-CSS-filter-Property.html","59-CSS-flex-Property.html","60-CSS-flex-basis-Property.html","61-CSS-flex-direction-Property.html","62-CSS-flex-flow-Property.html","63-CSS-flex-grow-Property.html","64-CSS-flex-shrink-Property.html","65-CSS-flex-wrap-Property.html","66-CSS-float-Property.html","67-CSS-font-Property.html","68-CSS-at-font-face-Rule.html","69-CSS-font-family-Property.html","70-CSS-font-feature-settings-Property.html","71-CSS-font-kerning-Property.html","72-CSS-font-size-Property.html","73-CSS-font-size-adjust-Property.html","74-CSS-font-stretch-Property.html","75-CSS-font-style-Property.html","76-CSS-font-variant-Property.html","77-CSS-font-variant-caps-Property.html","78-CSS-font-weight-Property.html","79-CSS-grid-Property.html","80-CSS-grid-area-Property.html","81-CSS-grid-auto-columns-Property.html","82-CSS-grid-auto-flow-Property.html","83-CSS-grid-auto-rows-Property.html","84-CSS-grid-column-Property.html","85-CSS-grid-column-end-Property.html","86-CSS-grid-column-gap-Property.html","87-CSS-grid-column-start-Property.html","88-CSS-grid-gap-Property.html","89-CSS-grid-row-Property.html","90-CSS-grid-row-end-Property.html","91-CSS-grid-row-gap-Property.html","92-CSS-grid-row-start-Property.html","93-CSS-grid-template-Property.html","94-CSS-grid-template-areas-Property.html","95-CSS-grid-template-columns-Property.html","96-CSS-grid-template-rows-Property.html","97-CSS-hanging-punctuation-Property.html","98-CSS-height-Property.html","99-CSS-hyphens-Property.html","100-CSS-at-import-Rule.html","101-CSS-isolation-Property.html","102-CSS-justify-content-Property.html","103-CSS-at-keyframes-Rule.html","104-CSS-left-Property.html","105-CSS-letter-spacing-Property.html","106-CSS-line-height-Property.html","107-CSS-list-style-Property.html","108-CSS-list-style-image-Property.html","109-CSS-list-style-position-Property.html","110-CSS-list-style-type-Property.html","111-CSS-margin-Property.html","112-CSS-margin-bottom-Property.html","113-CSS-margin-left-Property.html","114-CSS-margin-right-Property.html","115-CSS-margin-top-Property.html","116-CSS-max-height-Property.html","117-CSS-max-width-Property.html","118-CSS-at-media-Rule.html","119-CSS-min-height-Property.html","120-CSS-min-width-Property.html","121-CSS-mix-blend-mode-Property.html","122-CSS-object-fit-Property.html","123-CSS-object-position-Property.html","124-CSS-opacity-Property.html","125-CSS-order-Property.html","126-CSS-outline-Property.html","127-CSS-outline-color-Property.html","128-CSS-outline-offset-Property.html","129-CSS-outline-style-Property.html","130-CSS-outline-width-Property.html","131-CSS-overflow-Property.html","132-CSS-overflow-x-Property.html","133-CSS-overflow-y-Property.html","134-CSS-padding-Property.html","135-CSS-padding-bottom-Property.html","136-CSS-padding-left-Property.html","137-CSS-padding-right-Property.html","138-CSS-padding-top-Property.html","139-CSS-page-break-after-Property.html","140-CSS-page-break-before-Property.html","141-CSS-page-break-inside-Property.html","142-CSS-perspective-Property.html","143-CSS-perspective-origin-Property.html","144-CSS-pointer-events-Property.html","145-CSS-position-Property.html","146-CSS-quotes-Property.html","147-CSS-resize-Property.html","148-CSS-right-Property.html","149-CSS-scroll-behavior-Property.html","150-CSS-tab-size-Property.html","151-CSS-table-layout-Property.html","152-CSS-text-align-Property.html","153-CSS-text-align-last-Property.html","154-CSS-text-decoration-Property.html","155-CSS-text-decoration-color-Property.html","156-CSS-text-decoration-line-Property.html","157-CSS-text-decoration-style-Property.html","158-CSS-text-indent-Property.html","159-CSS-text-justify-Property.html","160-CSS-text-overflow-Property.html","161-CSS-text-shadow-Property.html","162-CSS-text-transform-Property.html","163-CSS-top-Property.html","164-CSS-transform-Property.html","165-CSS-transform-origin-Property.html","166-CSS-transform-style-Property.html","167-CSS-transition-Property.html","168-CSS-transition-delay-Property.html","169-CSS-transition-duration-Property.html","170-CSS-transition-property-Property.html","171-CSS-transition-timing-function-Property.html","172-CSS-unicode-bidi-Property.html","173-CSS-user-select-Property.html","174-CSS-vertical-align-Property.html","175-CSS-visibility-Property.html","176-CSS-white-space-Property.html","177-CSS-width-Property.html","178-CSS-word-break-Property.html","179-CSS-word-spacing-Property.html","180-CSS-word-wrap-Property.html","181-CSS-writing-mode-Property.html","182-CSS-z-index-Property.html"};

}
