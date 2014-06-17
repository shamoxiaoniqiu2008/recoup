	function fetchRingCount() {
		var strUrl=window.location.href;
		  arrUrl=strUrl.split("/");
			// 再取。之前的字符
			var strPage1=arrUrl[arrUrl.length-1];
			var arrUrl1=strPage1.split(".");
			var strPage=arrUrl[arrUrl.length-2]+"/"+arrUrl1[0];
			jQuery.get("/"+arrUrl[3]+"/RingServlet.Servlet",function(result){
//				alert(result.ringCount);
//			if(result.ringCount!=result.lastCount)
			if(true)
			{
				try
				{
					jQuery("#searchForm\\:ringTxt").html(result.ringCount);
					jQuery("#searchForm\\:searchButton").click();
				}
				catch(err)
				{
					
				}

			}
		  },"json");
	}
	setInterval(function(){
//		alert("time is running");
		fetchRingCount();
	},6000);
