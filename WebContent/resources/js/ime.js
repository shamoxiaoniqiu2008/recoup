imeRuning = false;



/**
 * 输入法处理类
 */
IMEHandler = function(inputid, imeid, lockedit, multiret) {
	$ = jQuery;
	var _this = this;
	// this.inputobj = document.getElementById(inputid);//输入框对象。
	this.inputobj = null;
	this.result;// 结果字符串
	this.selfid = imeid; // 组件ID

	this.lastenterstr = null;
	// if(this.inputobj != null){
	// this.lastenterstr = this.inputobj.value; //最后一次确定值。
	// }else{
	// this.lastenterstr =""; //最后一次确定值。
	// }
	this.enterflag = false;

	this.rowselect = 1;// 当前选中行 1起始
	this.curpage = 1;  // 当期页从1开始
	this.totalPage = 0; //总页数
	
	
	this.selectcolor = "#FBEC88";// 选中行颜色
	this.titlecolor = "#3ab8ea"; // 表头颜色
	this.intervalColor = "#E0E0E0"; //间隔色
	
	this.totalrow = 0;// 最大列数
	this.lock = lockedit; // 锁定编辑
	this.multiresult = multiret; // 多结果。
	this.focus = true;
	this.enable = true;
	
	
	this.resultcol = 0; // 参数 返回结果的列

	this.seq = 0; // 同步变量；
	this.lastSeq = this.seq;
	this.error = false;
	this.bHotKey = false;
	
	
	//this.delayTime = 200; //查询延迟时间
	//this.timer = null;
	
	/**
	 * 显示候选列表
	 */
	this.show = function show(e) {

		if(_this.enable == "true"){
			var posvec = this.GetPopPos(this.inputobj);
			this.DisplayResult(posvec);
		}
	};
	/**
	 * 隐藏候选列表
	 */
	this.hide = function hide(e) {

		var node = document.getElementById("IMEdiv1");

		if (node) {
			node.parentNode.removeChild(node);
		}

	};
	/**
	 * 根据文本框取得div弹出的位置
	 * 
	 * @returns {Array}top left
	 */
	this.GetPopPos = function GetPopPos(e) {
		var h = e.offsetHeight;
		var vec = new Array(1);
		vec[0] = jQuery(e).offset().top + h;
		vec[1] = jQuery(e).offset().left;
		return vec;
	};

	/**
	 * 分析返回结果中附带参数，并将其从result 中移除
	 */
	this.parseResult = function(e) {
		error = false;
		
		var paramstr = "";
		if (this.result.indexOf("\n") < 0) {
			// 无结果。
			paramstr = this.result;
			if(paramstr == ""){
				error = true;
			}
			this.result = "";
		} else {
			paramstr = this.result.substring(0, this.result.indexOf("\n"));
			this.result = this.result.substring(this.result.indexOf("\n") + 1,
					this.result.length + 1);

		}
		var params = paramstr.split("\t");
		
		this.enable = params[0];
		this.resultcol = params[1];
		this.lock = params[2];
		this.multiresult = params[3];
		this.totalPage = params[4];
		
	};
	/**
	 * 显示结果
	 * @param vec 位置 top left
	 */
	this.DisplayResult = function DisplayResult(vec){
		this.hide();
		if(this.result == null)
			return;
//		if(this.result.indexOf("\n")<0)
//			return;
	
		var rows = this.result.split("\n");

		
		t = "<div id='IMEdiv1' style='white-space:nowrap;background-color:#FCFDFD;z-index:999999;position:absolute;left:" 
				+vec[1] + "px;top:"+vec[0]+ "px'>";
		
		t += "<table style='background-color: #FFFFFF;border-collapse:collapse;line-height:25px;border:2px solid #90b9cd'; >";
		

		
		_this.totalrow = rows.length;

		if(error)
		{
			t += "<tr style='color: #2E6E9E; font-weight: bold;border:1px solid #90b9cd;background:url(../../images/tktdbj1.gif) repeat;text-align:center;'>";
			t += "<td style='text-align:left;color:red'>错误！！</td>";
			t+= "</tr>";
			
			t+= "<tr>";
			t+= "<td style='color:red'>" +"系统错误！！请联系技术支持！" +"</td>";
			t+= "</tr>";
		}else if(_this.totalPage == 0){
			
			t += "<tr style='color: #2E6E9E; font-weight: bold;border:1px solid #90b9cd;background:url(../../images/tktdbj1.gif) repeat;text-align:center;'>";
			t += "<td style='text-align:left'>提示</td>";
			t+= "</tr>";
			
			t+= "<tr>";
			t+= "<td style='color:blue'>" +"找不到匹配的输入结果" +"</td>";
			t+= "</tr>";

		}else{
			
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

	};
	
	this.adjustPos = function(vec,id){
		
	     //修正位置
	     var h = jQuery(id).height();
	     var w = jQuery(id).width();
	     var winH = jQuery(window).height();
	     var winW = jQuery(window).width();
	     var x = 0;
	     var y = 0;
	
	     //上下
	    y = vec[0];
	    //上边比较大。。并且下面显示不开的时候
	    if(winH - vec[0] < h 
	     &&vec[0] > winH/2){
		    y = vec[0] - h - jQuery(_this.inputobj).height();
	    }
	    jQuery(id).css('top',y + 'px');
	
	    //左右
	    x = vec[1];	    	 
       //右边显示不开的时候。往左挪。。。。确定最后一列能够显示。。。
	    if(winW - vec[1] < w){
	    	x = x - (w - (winW - vec[1]));
	    	if(x <0){
	    		x = 0;//确保首列能够显示。
	    	}
	    }
	    
	    
       jQuery(id).css('left',x + 'px');

		
	};
	
	
	/**
	 * 鼠标点击
	 */
	this.onMouseClick = function(e) {
		var ev = window.event || e;
		var src = ev.srcElement || ev.target;
		var jsrc = $(src).parent();
		_this.rowselect = jsrc.attr("name");
		_this.EnterPress();

	};
	/**
	 * 鼠标移除选中行。
	 */
	this.onMouseOut = function(e) {
		var ev = window.event || e;
		var src = ev.srcElement || ev.target;
		var jsrc = $(src).parent();

		if (jsrc.attr("name") != _this.rowselect) {

			if(jsrc.attr("name") %2 == 0){
				jsrc.css("background-color", _this.intervalColor);
			}else{
				jsrc.css("background-color", "");
			}

		}

	};
	/**
	 * 鼠标已入选中行
	 */
	this.onMouseOver = function(e) {
		var ev = window.event || e;
		var src = ev.srcElement || ev.target;
		var jsrc = $(src).parent();

		jsrc.css("background-color", _this.selectcolor);
	};
	
	
	
	/**
	 * 上一页
	 */
	this.pageUp = function(e){

		if(_this.totalPage <= 1)
			return;
		
		if(_this.curpage>1){
			_this.curpage--;
		}else{
			_this.curpage = _this.totalPage;
		}
		
		_this.ajaxPost(e);
		
	};
	
	/**
	 * 下一页
	 */
	this.pageDown = function(e){
		
		if(_this.totalPage <= 1)
			return;
		
		//_this.curpage++;
		if(_this.curpage < _this.totalPage){
			_this.curpage++;
		}else{
			_this.curpage  = 1;
		}
		_this.ajaxPost(e);
	};
	
	
	/**
	 * 上移选中行
	 */
	this.ArrowUp = function(e) {
		if (this.rowselect > 1) {
			this.rowselect--;
		} else {
			this.rowselect = _this.totalrow - 1;
		}
		this.show();
	};
	/**
	 * 下移选中行
	 */
	this.ArrowDown = function(e) {
		if (this.rowselect < _this.totalrow - 1) {
			this.rowselect++;
		} else {
			this.rowselect = 1;
		}
		this.show();
	};

	/**
	 * 取得选中内容
	 */
	this.getRowItem = function(row, col) {

		var rows = this.result.split("\n");
		for ( var i = 0; i < rows.length; i++) {
			if (i == row) {
				var cols = rows[i].split("\t");
				for ( var j = 0; j < cols.length; j++) {
					if (col == j) {
						// "卍卐" 去掉隐藏标记
						if (cols[j].indexOf("卍卐") >= 0) {
							cols[j] = cols[j].substring(0, cols[j]
									.indexOf("卍卐"));
						}
						if (cols[j].indexOf("卐卍") >= 0) {
							cols[j] = cols[j].substring(0, cols[j]
									.indexOf("卐卍"));
						}
						return cols[j];
					}
				}

			}
		}

	};

	/**
	 * enter 按下 确认选中行
	 */
	this.EnterPress = function(e) {

		if (this.result != "" && this.result != null) {
			// 填写返回结果
			_this.inputobj.value = _this.getRowItem(_this.rowselect,
					_this.resultcol);
			_this.lastenterstr = _this.inputobj.value;

			// 多结果
			if (_this.multiresult != "null") {
				var iterms = _this.multiresult.split(",");
				for ( var i = 0; i < iterms.length; i++) {
					var tmps = iterms[i].split(";");
					var inputid = tmps[0];
					var col = tmps[1];

					var input = document.getElementById(inputid);
					if (input != null) {
						var retval = this.getRowItem(this.rowselect, col);
						input.value = retval;
					}
				}
			}



		}
		// 清理
		this.result = "";
		_this.enterflag = true;
		this.hide();
		this.rowselect = 1;
		
		
		_this.validate();
		
		

		

	};

	
	
	
	/**
	 * 选中控制
	 * 
	 * @param e选中事件
	 */
	this.OnKeyDown = function(e) {
		
		_this.seq++;
		_this.bHotKey = false;
		var ev  = _this.getEvent(e);
		_this.inputobj = _this.getSrcElement(ev);
		
		if (ev.keyCode == 13 
		 || ev.keyCode == 9) {

//			if(_this.timer != null){
//				clearTimeout(_this.timer);
//			}
			// enter
			_this.EnterPress();
			// 转换为tab

			ev.keycode = 9;
		} else if (ev.keyCode == 38) {
			// up
			_this.ArrowUp();
		} else if (ev.keyCode == 40) {
			// down
			_this.ArrowDown();
		} else if (ev.keyCode == 27) {

			_this.result = "";
			_this.hide();
			_this.rowselect = 1;

		}else if(ev.keyCode == 37){
			//left
			_this.pageUp(e);
		}else if(ev.keyCode == 39){
			//right
			_this.pageDown(e);
		}else if(ev.keyCode <= 123 && ev.keyCode >= 112){
			_this.bHotKey = true;
			_this.OnBlur(e);
		}

	};

	this.ajaxPost = function(e) {
		imeRuning = true;
		_this.rowselect = 1;


		var form = null;

		form = $(_this.inputobj).closest("form");
		if (form.length == 0) {

			var iterms = $(_this.inputobj).attr("id").split(":");
			var formId = iterms[0];
			form = $("#" + formId);
		}

		if (form.length == 0)
			return;

		var url = form.attr("action");
		var forms = form.serialize();

		_this.lastSeq = _this.seq;

		var postdata = forms + "&ajaxreq=true" 
				             + "&id=" + _this.selfid
						     + "&content=" + _this.inputobj.value
						     + "&curpage=" + _this.curpage;

		$.post(url, postdata,

		function(result) {
			imeRuning = false;
			if (_this.lastSeq == _this.seq) {
				_this.result = result;
				_this.parseResult();
				_this.show();

			}

		});
	};
	
	/**
	 * 输入框有字符输入。
	 */
	this.OnKeyUp = function(e) {

		if(!_this.bHotKey){
			var ev  = _this.getEvent(e);
			if (ev.keyCode == 13 
			 || ev.keyCode == 38 
			 || ev.keyCode == 40
			 || ev.keyCode == 9 
			 || ev.keyCode == 27
			 || ev.keyCode == 37
			 || ev.keyCode == 39)
				return;
			
			_this.seq++;
			_this.curpage = 1;
			_this.totalPage = 0;
			
//			if(_this.timer != null){
//				clearTimeout(_this.timer);
//			}
//			_this.timer = setTimeout(_this.ajaxPost,_this.delayTime);
			
			_this.ajaxPost();
		}
	};


	
	
	
	/**
	 * 失去焦点隐藏
	 * 
	 * @param obj
	 *            事件对象
	 */
	this.OnBlur = function(e) {

		
//		if(_this.timer != null){
//			clearTimeout(_this.timer);
//		}
		
		
		
		_this.seq++;
		_this.curpage = 1;
		_this.totalPage = 0;
		
		_this.validate();
		_this.hide();
		_this.result = "";
		_this.enterflag = true;

	};

	
	this.getEvent = function(e){
		if((e == null && window.event != null)
		||(e != null && window.event == null)){
			var	ev = window.event || e;
			return ev;
		}else{
			if(e.target.localName == "input"||e.target.localName == "textarea"){
				return e;
			}else{
				return window.event;
			}
		}
		return null;
	};
	
	this.getSrcElement = function(ev){
		var src = ev.srcElement || ev.target;
		return src;
	};
	
	this.OnDbclick = function(e) {
		_this.curpage = 1;
		_this.totalPage = 0;
		
		_this.ajaxPost();
	};
	
	this.OnFocus = function(e) {

		_this.seq++;
		var ev  = _this.getEvent(e);
		_this.inputobj = _this.getSrcElement(ev);
		$('[id$="' + _this.inputobj.id + '"]').attr("autocomplete","off");

		if (_this.inputobj.value) {
			_this.lastenterstr = _this.inputobj.value; // 最后一次确定值。
		}else if(_this.inputobj.value == ''){
			_this.lastenterstr = '';
		}
		
//		_this.curpage = 1;
//		_this.totalPage = 0;
//		
//		_this.ajaxPost();
	};

	/**
	 * lock验证。
	 */
	this.validate = function(e) {
		if (_this.lock != "true")
			return;
		if(_this.enable != "true"){
			return;
		}

		var y = _this.inputobj.value;
		if ((!_this.enterflag || _this.lastenterstr != y) && y != "") {
			_this.inputobj.value = _this.lastenterstr;

		}
		if (y == "") {
			// 清空赋值框
			if (_this.multiresult != "null") {
				var iterms = _this.multiresult.split(",");
				for ( var i = 0; i < iterms.length; i++) {
					var tmps = iterms[i].split(";");
					var inputid = tmps[0];
					var input = document.getElementById(inputid);
					if (input != null) {
						input.value = "";
					}
				}
			}
		}
		_this.lastenterstr = _this.inputobj.value;
	};
	/**
	 * 类事件绑定
	 */

	//$('[id$="' + inputid + '"]').off();
	$('[id$="' + inputid + '"]').off("focus");
	//$('[id$="' + inputid + '"]').off("blur");
	$('[id$="' + inputid + '"]').off("keydown");
	$('[id$="' + inputid + '"]').off("keyup");
	$('[id$="' + inputid + '"]').off("dblclick");
	
	$('[id$="' + inputid + '"]').on("focus", this.OnFocus);
	$('[id$="' + inputid + '"]').on("blur", this.OnBlur);
	$('[id$="' + inputid + '"]').on("keydown", this.OnKeyDown);
	$('[id$="' + inputid + '"]').on("keyup", this.OnKeyUp);
	$('[id$="' + inputid + '"]').on("dblclick", this.OnDbclick);

	
};

/*jQuery(document).ready(function(){ 
	jQuery("body").append("<div id=\"CentLingPmsBar\" style=\"display:none;" +
			"z-index:2999; position: absolute; left:0px; top:0px;  \"> <div id=\"barimg\" " +
			"style=\" width:300px;height: 60px;position: absolute;z-index: 90000;color:red;font-weight:bold; \">" +
			"<br><img src=\"../../images/progressbar.gif\" />处理中... </div>" +
			" </div>");

	imeRuning = false;
	
});*/  
