	function fetchMessageCount() {
	//	alert("dddddd")
		var strUrl=window.location.href;
		  arrUrl=strUrl.split("/");
			// 再取。之前的字符
			var strPage1=arrUrl[arrUrl.length-1];
			var arrUrl1=strPage1.split(".");
			var strPage=arrUrl[arrUrl.length-2]+"/"+arrUrl1[0];
			//alert(arrUrl[3])
		jQuery.get("/"+arrUrl[3]+"/MessageServlet.Servlet",function(result){
			//alert(result.messageCount);
			jQuery("#messageTxt").html(result.messageCount);
			jQuery("#curUser").html(result.curUser);
			if(result.messageCount > 0) {
				 flash_title();
			} else {
				if(typeof(messageWarning) != "undefined") {
					clearTimeout(messageWarning);
					setDefaultTitle();
				 }
			}
		  },"json");
	}

/*
	fetchMessageCount();
	setInterval(function(){
		fetchMessageCount();
	},6000);
**/
	
	function fetchMessageCounts() {
		var strUrl=window.location.href;
		  arrUrl=strUrl.split("/");
			// 再取。之前的字符
			var strPage1=arrUrl[arrUrl.length-1];
			var arrUrl1=strPage1.split(".");
			var strPage=arrUrl[arrUrl.length-2]+"/"+arrUrl1[0];
		//jQuery.post( "/"+arrUrl[3]+"/MessageServlet",{'birthday':birthday,'ethnicityName':ethnicityName},function(result){
		 jQuery.post("/"+arrUrl[3]+"/MessageServlet.Servlet",{},function(result){
			 
			// alert(result);
		     t = "<table width='100%' border='1'>";
				
			 t += "<tr>";
			 t += "<td>编号</td>";
			 t += "<td>类别</td>";
			 t += "<td>名称</td>";
			 t += "<td>内容</td>";
			// t += "<td>处理人</td>";
			 //t += "<td>处理</td>";
			 t += "<td>删除</td>";
			 t += "</tr>";
				 
				
			 jQuery.each(result, function (i, o) {
	            // alert("text:" + o.id + ", value:"  + o.messagetypeId+ ", value:"  + o.deptId+ ", value:"  + o.userId+ ", value:"  + o.isprocessor);
				// alert(o.messagTypeName);
	             t += "<tr>";
				 t += "<td>";
				 t += (typeof(o.id) == "undefined")?"&nbsp;":o.id;
				 t += "</td>";

				 t += "<td>";
				 t += (typeof(o.messagTypeName) == "undefined")?"&nbsp;":o.messagTypeName;
				 t += "</td>";
				 
				 t += "<td>";
				 t += (typeof(o.messagename) == "undefined")?"&nbsp;":"<a href=\"javascript:messageDialog.hide();showTag(\'"+ o.messageUrl +"\')\" >"+o.messagename+"</a>";
				 t += "</td>";
				 
				 t += "<td>";
				 t += (typeof(o.notes) == "undefined")?"&nbsp;":"<a href=\"javascript:messageDialog.hide();showTag(\'"+ o.messageUrl +"\')\" >"+o.notes+"</a>";
				 t += "</td>";
				 
				// t += "<td>";
				// t += (typeof(o.userId) == "undefined")?"&nbsp;":o.userId;
				// t += "</td>";
				 
				// t += "<td>";
				// t += (typeof(o.isprocessor) == "undefined")?"未完成":o.isprocessor==1?'完成':'未完成';
				// t += "</td>";
				 
				 
				 t += "<td  style=\"cursor: pointer; \" " ;
				 t += (typeof(o.id) == "undefined")?"":" onclick=\"javascript:delMessage(\'"+ o.id +"\',this)\" " ;
				// t += (typeof(o.id) == "undefined")?"&nbsp;":"<a href=\"javascript:delMessage(\'"+ o.id +"\',this)\" >删除</a>";
				 t += " >";
				 t += (typeof(o.id) == "undefined")?"&nbsp;":"删除";
				 t += "</td>";
				 
				 t += "</tr>";
				 
	        });
			 t += "</table>";
			 
			 jQuery("#messageForm").html(t);
			 
			 
		  },'json'); 
		
		
	}

	function delMessage(id, obj) {
		if(confirm("确定要删除吗？")){
			var strUrl=window.location.href;
			arrUrl=strUrl.split("/");
			var strPage1=arrUrl[arrUrl.length-1];
			var arrUrl1=strPage1.split(".");
			//var strPage=arrUrl[arrUrl.length-2]+"/"+arrUrl1[0];
			//jQuery.post( "/"+arrUrl[3]+"/MessageServlet",{'birthday':birthday,'ethnicityName':ethnicityName},function(result){
			 jQuery.post("/"+arrUrl[3]+"/MessageServlet.Servlet",{'del':'del','msgId':id},function(result){
				 //alert(result.sucess);
				 if(result.sucess == 'true') {
					// alert("删除成功");
					// alert(obj.innerHTML);
					 obj.parentNode.parentNode.removeChild(obj.parentNode);
					// alert("删除成功");
				 }
				 jsMessageInfos(result.message);
				// alert(result.sucess);
			  },'json'); 
		}
		
	}
	 documentTitle = document.title;
	 step = true;
	 function flash_title()
	 {
		 if(typeof(messageWarning) != "undefined") {
			clearTimeout(messageWarning);
		 }
		 if (step) {
			 document.title=documentTitle;
			 document.getElementById("messagePic").style.display="";
			 step = false;
		} else {
			document.title='★新消息★';
			document.getElementById("messagePic").style.display="none";
			step = true;
		}
		messageWarning = setTimeout("flash_title()",1000);
	 }
	
	 function setDefaultTitle() {
		 document.title=documentTitle;
		 document.getElementById("messagePic").style.display="";
	 }
	 
	 
		function exportExcel(excelName, contentExcel) {

			document.getElementById("excelName").value=excelName;
			document.getElementById("contentExcel").value=contentExcel;
			
			
			var strUrl=window.location.href;
		    arrUrl=strUrl.split("/");
			// 再取。之前的字符
			var strPage1=arrUrl[arrUrl.length-1];
			var arrUrl1=strPage1.split(".");
			var strPage=arrUrl[arrUrl.length-2]+"/"+arrUrl1[0];
			//document.getElementById("exportExcelFrame").src="/"+arrUrl[3]+"/ExcelServlet.Servlet";
			document.exportExcelForm.action = "/"+arrUrl[3]+"/ExcelServlet.Servlet";
			document.exportExcelForm.submit();

		}
		
/*
		
		t += "<table style='background-color: #FFFFFF;border-collapse:collapse;line-height:25px;border:2px solid #90b9cd'; >";
		

			
			var colsize = 0;
			for(var i=0;i<rows.length;i++)
			{
				var cols = rows[i].split("\t");
				colsize = cols.length;
				if(i == this.rowselect){
					t += "<tr name='"+ i +"' "
						+ "id = 'IMEtr" + i +"' " 
						+ "style='font-weight:bold;cursor:pointer;border:1px solid #90b9cd;background-color:" + this.selectcolor + "'>";
				}else{
					if(i%2 == 0){
						t += "<tr name='"+ i +"' "
						  + "id = 'IMEtr" + i +"' " 
						  + "style='cursor:pointer;border:1px solid #90b9cd;background-color:" + _this.intervalColor+"' >";
					}else{
						t += "<tr name='"+ i +"' "
						  + "id = 'IMEtr" + i +"' " 
						  + "style='cursor:pointer;border:1px solid #90b9cd;'>";
					}
				}
				if(i == 0){
					//表头
					t += "<tr style='color: #2E6E9E; font-weight: bold;border:1px solid #90b9cd;background:url(../../images/tktdbj1.gif) repeat;text-align:center;'>";
				}
				
				for(var j=0;j<cols.length;j++)
				{
					if(cols[j].indexOf("卍卐") >= 0){
						continue;
					}
					if(cols[j].indexOf("卐卍") >= 0){
						
						var subcols = cols[j].split("卐卍");
						
						t += "<td style='white-space:normal !important;border:1px solid #90b9cd;padding-left:3px;padding-right:3px;width:"+subcols[1]+"'>" + subcols[0] + "</td>";
					}else{
						
						t += "<td style='border:1px solid #90b9cd;padding-left:3px;padding-right:3px;'>" + cols[j] + "</td>";
					}
					
				}
				t += "</tr>";
			}
			
			
			 t += "<tr style='color: #2E6E9E; font-weight: bold;border:1px solid #90b9cd;background:url(../../images/tktdbj1.gif) repeat;text-align:center;'>";
			 t += "<td colspan=" + colsize+">";
			 t += "总&nbsp;";
			 t += "<span style='color:red'>"+_this.totalPage+"</span>"; 
			 t += "&nbsp;页&nbsp;&nbsp;当前";
			 t += "<span style='color:red'>" + _this.curpage +"</span>";
			 t += "&nbsp;页&nbsp;" ;
			 t +=  "<span style='color:red'>" + "◀" +"</span>";
			 t +=  "&nbsp;";
			 t +=  "<span style='color:red'>"+ "▶" +"</span>";
			 t +=  "&nbsp;翻页"; 
			 t += "</td></tr>";
			
		}
		

		
	     t += "</table>";
	     t += "</div>";

	     $("body").append(t);
	     
	     
	     _this.adjustPos(vec, '#IMEdiv1');
	     
	     for(var i=1;i<rows.length;i++){
	    	 $('#IMEtr' + i).mouseenter(_this.onMouseOver);
	    	 $('#IMEtr' + i).mouseleave(_this.onMouseOut);
	    	 $('#IMEtr' + i).mousedown(_this.onMouseClick);
	     };

	};*/
	
	