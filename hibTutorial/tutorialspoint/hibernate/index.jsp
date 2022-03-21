<html>
	<head>
		<script type="text/javascript" src="http://127.0.0.1:8080/hibTutorial/a/jquery-1.3.2.min.js"></script>
	
		<style>
			#header {
				background-color:black;
				color:white;
				text-align:center;
				/*padding:5px;*/
			}
			#nav {
				line-height:20px;
				background-color:#eeeeee;
				/*height:300px;line-height:30px;padding:5px;*/
				width:200px;
				float:left;
				
				word-break: break-all;
				white-space: normal;
				font-size: 15;
			}
			
			.linkClassSelected{
				background-color:green;
			}
			#section {
				width:1050px;
				height:800px;
				
				/*padding:10px;float:left;*/	 	 
			}
			#footer {
				background-color:black;
				color:white;
				clear:both;
				text-align:center;
				padding:5px;	 	 
			}
			.titleClass:hover{
				color:red;
			}
		</style>
	</head>
	
	
	<body>
		
		<div id="header">
			<h1>Hibernate Tutorial</h1>
		</div>
	
	<table>
		<tr>
			<td>
				<div id="nav">
					<div class="linkClass">
						<a href="">Hibernate Tutorial</a>
					</div>
					
					<div class="linkClass">
						<a href="">Hibernate Tutorial</a>
					</div>
					
					<div class="linkClass">
						<a href="index.pdf">Hibernate Home</a>
					</div>
					
					<div class="linkClass">
						<a href="orm_overview.pdf">ORM Overview</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_overview.pdf">Hibernate Overview</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_architecture.pdf">Hibernate Architecture</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_environment.pdf">Hibernate Environment</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_configuration.pdf">Hibernate Configuration</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_sessions.pdf">Hibernate Sessions</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_persistent_classes.pdf">Hibernate Persistent Class</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_mapping_files.pdf">Hibernate Mapping Files</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_mapping_types.pdf">Hibernate Mapping Types</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_examples.pdf">Hibernate Examples</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_or_mappings.pdf">Hibernate O/R Mappings</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_annotations.pdf">Hibernate Annotations</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_query_language.pdf">Hibernate Query Language</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_criteria_queries.pdf">Hibernate Criteria Queries</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_native_sql.pdf">Hibernate Native SQL</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_caching.pdf">Hibernate Caching</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_batch_processing.pdf">Hibernate Batch Processing</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_interceptors.pdf">Hibernate Interceptors</a>
					</div>
					
					<div class="linkClass">
						<a href="">Hibernate Useful Resources</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_quick_guide.pdf">Hibernate Quick Guide</a>
					</div>
					
					<div class="linkClass">
						<a href="hibernate_useful_resources.pdf">Hibernate Useful Resources</a>
					</div>
					
					<div class="linkClass">
						<a href="">Selected Reading</a>
					</div>
					
					<div class="linkClass">
						<a href="">Developer's Best Practices</a>
					</div>
					
					<div class="linkClass">
						<a href="">Effective Resume Writing</a>
					</div>
					
					<div class="linkClass">
						<a href="">Computer Glossary</a>
					</div>
					
					<div class="linkClass">
						<a href="">Who is Who</a>
					</div>
					
				</div>
			</td>
			
			<td>	
				<div id="section">
					<embed id="pdfDisplaySection" src="" width="95%" height="100%" />
					<!--width="550" height="400" -->
				
				</div>
			</td>
		</tr>
	</table>
		
		<div id="footer">
			Hibernate Tutorial
		</div>
	
		<script>
			var anchorContainingDIVs=[];
			$("div").each(function() {			
				if($(this).hasClass('linkClass')==false){
					return;
				}
				
				anchorContainingDIVs.push(this);
				
				$(this).live('click',function (event) {	
				     var i=0;
					for(i=0;i<anchorContainingDIVs.length;i++){
						anchorContainingDIVs[i].removeClass('linkClassSelected');
					}
					
					///$(this).addClass("linkClassSelected");
				});
				
				//var obj=new Object();
				//var divKiID=$(this).attr('id');
				//obj.navForSectionId=divKiID;			
				//var spanKiID=sectionWala+divKiID.substr(navForSectionWala.length);
				
				//totalObject.push(obj);
				
				//$('#'+spanKiID).hide();
				
				$(this).find('a').live('click',function (event) {	
					
					event.preventDefault();
					showPDF(this);
					$(this).addClass("linkClassSelected");
					//var WeatherIcon = $(this).attr('href');
					//var parent = $('#'+section);
					//var newImage = "<embed id=\"GetWeatherIcon\" src=\"" + WeatherIcon + "\">";
					//var newElement = $(newImage);

					//parent.remove();
					//parent.append(newElement);
				});
		  });
		  
		  function showPDF(whichPDF){
			  var source=whichPDF.getAttribute("href");
			  var pdfElem=document.getElementById("pdfDisplaySection");
			  var section=document.getElementById("section");
			  var clone=pdfElem.cloneNode(true);
			  clone.setAttribute('src',source);
			  section.replaceChild(clone,pdfElem)
			}
		</script>
	
	</body>


</html>