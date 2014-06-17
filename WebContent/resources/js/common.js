var keyDown = false;
imeRuning = false;
function checkNulls(formId,calssId,isMessage) {
	//alert("'dddddddddddddddd");
	//alert(jQuery("#" + formId + " ." + calssId))
		name = "";
		key = "";
		jQuery("#" + formId + " ." + calssId).each(function() {
			//alert(jQuery(this).attr("for"));
			idStr = "#"+formId+"\\:"+jQuery(this).attr("for")
			vals = jQuery(idStr).val();
			vals =  trim(vals);   //vals.replace(/(^\s*)|(\s*$)/g, "");
			if(vals == "") {
				name = jQuery(this).html();
				key = idStr;
				return false;
			}
		});
	
		if(name != "") {
			if(!keyDown){
				 if(isMessage){
					jsMessageInfoFun(name+"不能为空");
				 }
				keyDown = false;
			}
			jQuery(idStr).focus();
			
			return false;
		} else {
			return true;
		}
	
	return false;
	}
	 function trim(s) {
	    return s.replace(/^\s+|\s+$/g, "");
	}; 
	function checkIsNotNull(id){
		var txt=document.getElementById(id).value;
		if (txt==null||trim(txt)=="") {
			return false;
		}
		else{
			return true;
		}
	}
	
	
	function jsMessageInfoFun(messageInfo) {
		jQuery(function()
		{	
			try {
				 msg = new Object();
				 msg["severity"] = 'info';
				 msg["summary"] = messageInfo;
				// msg["detail"]= messageInfo;
				PF('message').show([msg]);
				/*jQuery.gritter.add(
						{
							title:messageInfo,
							text:messageInfo,image:'../../javax.faces.resource/growl/assets/error.png.jsf?ln=primefaces&amp;v=2.2.1',sticky:false
						});*/
			} catch(err) {
			}
			
		});
	}
	
	function jsMessageInfos(messageInfo) {
		jQuery(function()
		{	
			jQuery.gritter.add(
			{
				title:messageInfo,
				text:messageInfo,image:'../javax.faces.resource/growl/assets/error.png.jsf?ln=primefaces&amp;v=2.2.1',sticky:false}
			);
		});
	}
	
	function jumpTo(id) {
		document.getElementById(id).focus();
	}
	
	
	document.onkeydown = function (evt) {
		keyDown = true;
	}
	
	document.onmousedown = function (evt) {
		keyDown = false;
	}
	
	function Click() 
	{ 
	    var evt = GetEvent(); 
	    alert(evt); 
	}

	// 返回 event 对象 
	function GetEvent() 
	{ 
	    if(document.all) // IE 
	    { 
	        return window.event; 
	    }

	    func = GetEvent.caller; // 返回调用本函数的函数

	    while(func != null) 
	    { 
	        // Firefox 中一个隐含的对象 arguments，第一个参数为 event 对象 
	        var arg0 = func.arguments[0]; 
	        // alert('参数长度：' + func.arguments.length); 
	        if(arg0) 
	        { 
	            if((arg0.constructor == Event || arg0.constructor == MouseEvent) ||(typeof(arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) 
	            { 
	                return arg0; 
	            } 
	        }

	        func = func.caller; 
	    }

	    return null; 
	}
	
	
	function GetPopPos(e) {
//		var h = e.offsetHeight;
		var h=0;
		var vec = new Array(1);
		vec[0] = jQuery(e).offset().top + h;
		vec[1] = jQuery(e).offset().left;
		return vec;
	};

	function DisplayResult(obj){	
		
		/*var node = document.getElementById("replaceDiv");
		if (node) {
			node.parentNode.removeChild(node);
		}*/
		//alert('dddddddddddddddddddd')
		var vec = GetPopPos(obj);
		document.getElementById("inputDiv").style.top=vec[0]+"px";
		document.getElementById("inputDiv").style.left=vec[1]+"px";
		/*t = "<div id='replaceDiv' style='position:absolute; z-index:999999;left:" 
			+vec[1] + "px;top:"+vec[0]+ "px'>dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd</div>";
		 jQuery("body").append(t);*/
		
		
	}
	
	jQuery(document).ready(function(){ 
		jQuery("body").append("<div id=\"CentLingPmsBar\" style=\"display:none;" +
				"z-index:2999; position: absolute; left:0px; top:0px;  \"> <div id=\"barimg\" " +
				"style=\" width:300px;height: 60px;position: absolute;z-index: 90000;color:red;font-weight:bold; \">" +
				"<br><img src=\"../../images/progressbar.gif\" /> </div>" +
				" </div>");
	});
	function cleanBar() {
			jQuery("#barimg").html("");
	}
	
	
	function clickChageHeight(obj,classSmall,classLarge){
		if(obj.className==classSmall){
			obj.className=classLarge;
		}else{
			obj.scrollTop=0;
			obj.className=classSmall;
		}
	}
