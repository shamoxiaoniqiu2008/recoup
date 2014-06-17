	function showDesktop(){
		require.config({
		    paths:{
		        echarts:'../echarts/echarts',
		        'echarts/chart/bar' : '../echarts/echarts',
		        'echarts/chart/line' : '../echarts/echarts',
		        'echarts/chart/pie' : '../echarts/echarts',
		    }
		});
		var strUrl=window.location.href;
		arrUrl=strUrl.split("/");
		// 再取。之前的字符
		var strPage1=arrUrl[arrUrl.length-1];
		var arrUrl1=strPage1.split(".");
		var messageList;
		var nurseList;
		var olderFlowList;
		jQuery.post("/"+arrUrl[3]+"/EchartsServlet.Servlet",function(data){
			var resultList = eval(data);
			jQuery.each(resultList, function (i, o) {
				nurseList = eval(o.nurseList);
				showNurse(nurseList);
				showTab();
			 });
		});
		
	}
	
	function showTab(){
		var strUrl=window.location.href;
		arrUrl=strUrl.split("/");
		jQuery.post("/"+arrUrl[3]+"/EchartsServlet.Servlet",{"tab":"tab"},function(data){
			var resultList = eval(data);
			jQuery.each(resultList, function (i, o) {
				messageList = eval(o.messageListStr);
				olderFlowList = eval(o.olderFlowList);
				showMessage(messageList);
				showOlderFlow(olderFlowList);
			 });
		});
	}

	
	function showOlderFlow(olderFlowList){
		var seriesData=new Array();
		jQuery.each(olderFlowList, function (i, o) {
			seriesData[i] = o;
		});
		if(seriesData.length > 0){
			require(
					[
					 'echarts',
					 'echarts/chart/bar',
					 'echarts/chart/line',
					 'echarts/chart/pie'
					 ],
					 function(ec) {
						var myChart = ec.init(document.getElementById('olderFlowFrame'));
						var option = {
								tooltip : {
									trigger: 'axis'
								},
								toolbox: {
									show : true,
									feature : {
										mark : false,
										dataView : {readOnly: true},
										restore : false,
										saveAsImage : true
									}
								},
								calculable : true,
								xAxis : [
								         {
								        	 type : 'category',
								        	 data : ['起始老人数','新入住老人数','新出院老人数','结束老人数']
								         }
								         ],
								         yAxis : [
								                  {
								                	  type : 'value',
								                	  splitArea : {show : true}
								                  }
								                  ],
								                  series : [
								                            {
								                            	name:'老人数',
								                            	type:'bar',
								                            	data:seriesData
								                            },
								                            
								                            ]
						};
						myChart.setOption(option);
					}
			);
		}else{
			jQuery("#nurseFrame").html("当前没有老人流动情况！");
		}
		
	}
	
	function addCloseButton(){
		var closeButton = document.createElement('input');
		closeButton.type="button";
		closeButton.addClass="commonbtn";
		closeButton.value="关闭";
		closeButton.id="closeButton";
		closeButton.style.position="absolute";
		closeButton.style.right="5px";
		closeButton.style.top="5px";
		closeButton.onclick=function(){
			jQuery('#closeButton').remove();
			jQuery('#feeFrame').hide();
		};
		var feeChart = document.getElementById('feeFrame');
		feeChart.appendChild(closeButton);
	}
	
	
	
	function showNurse(nurseList){
		if(nurseList.length > 0){
			t = "<table ><tr>";
			jQuery.each(nurseList, function (i, o) {
				if( i != 0 && i % 4 == 0){
					t += "</tr><tr>";
				}
				t += "<td onclick=\"showFeeFrame('"+o.olderId+"')\" style=\"padding:10px 10px 10px 10px ;text-align:center;\"><img src=\"/pension"+o.photoUrl+"\"/>";
				t += "<h3>"+o.olderName+"</h3></td>";
			});
			t += "</tr></table>";
			jQuery("#nurseFrame").html(t);
		}else{
			jQuery("#nurseFrame").html("当前您没有护理老人！");
		}

	}
	
	function showFeeFrame(olderId){
		jQuery.post("/"+arrUrl[3]+"/EchartsServlet.Servlet",{"olderId":olderId},function(data){
			var feeList = eval(data);
			var xAxisData =new Array();
			var index = 1;
			xAxisData[0]= "'总费用'";
			var totalFee = 0;
			jQuery.each(feeList, function (i, o) {
				var obj1 = new Object();
				obj1 = o.itemName;
				xAxisData[index]= "'" + obj1 + "'";
				totalFee += o.totalFee;
				index ++ ;
			});
			var seriesData1 =new Array();
			var index1 = 1;
			seriesData1[0]= 0;
			var tempTotalFee = totalFee;
			jQuery.each(feeList, function (i, o) {
				var obj1 = new Object();
				obj1 = tempTotalFee - o.totalFee;
				tempTotalFee -= o.totalFee;
				seriesData1[index1]= obj1;
				index1 ++ ;
			});
			var seriesData2 =new Array();
			var index2 = 1;
			seriesData2[0]= totalFee;
			jQuery.each(feeList, function (i, o) {
				var obj1 = new Object();
				obj1 = o.totalFee;
				seriesData2[index2]= obj1;
				index2 ++ ;
			});
			require(
			        [
			            'echarts',
			            'echarts/chart/bar',
			            'echarts/chart/line',
			            'echarts/chart/pie'
			        ],
			        function(ec) {
			        	jQuery("#feeFrame").show();
			        	var feeChart = ec.init(document.getElementById('feeFrame'));
			        	var option = {
			        			title: {
			        				text: '老人缴费情况',
			        			},
			        			tooltip : {
			        				trigger: 'axis',
			        				axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			        					type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        				},
			        				formatter: function(params) {
			        					var tar;
			        					if (params[1][2] != '-') {
			        						tar = params[1];
			        					}
			        					else {
			        						tar = params[2];
			        					}
			        					return tar[1] + '<br/>' + tar[0] + ' : ' + tar[2];
			        				}
			        			},
			        			toolbox: {
			        				show : false,
			        				feature : {
			        					mark : false,
			        					dataView : {readOnly: true},
			        					restore : false,
			        					saveAsImage : true
			        				}
			        			},
			        			xAxis : [
			        			         {
			        			        	 type : 'category',
			        			        	 splitLine: {show:false},
			        			        	 data : xAxisData
			        			         }
			        			         ],
			        			         yAxis : [
			        			                  {
			        			                	  type : 'value',
			        			                	  splitArea: {show : true}
			        			                  }
			        			                  ],
			        			                  series : [
			        			                            {
			        			                            	name:'辅助',
			        			                            	type:'bar',
			        			                            	stack: '总量',
			        			                            	itemStyle:{
			        			                            		normal:{
			        			                            			borderColor:'rgba(0,0,0,0)',
			        			                            			color:'rgba(0,0,0,0)'
			        			                            		},
			        			                            		emphasis:{
			        			                            			borderColor:'rgba(0,0,0,0)',
			        			                            			color:'rgba(0,0,0,0)'
			        			                            		}
			        			                            	},
			        			                            	data:seriesData1
			        			                            },
			        			                            {
			        			                            	name:'',
			        			                            	type:'bar',
			        			                            	stack: '总量',
			        			                            	itemStyle : { normal: {label : {show: true, position: 'inside'}}},
			        			                            	data:seriesData2
			        			                            }
			        			                            ]
			        	};
			        	feeChart.setOption(option);	  
			        	addCloseButton();
			        });
		});
		
	}
	
	function showMessage(messageList) {
		var legendData=new Array();
		 jQuery.each(messageList, function (i, o) {
			 if(o.type == 1){
				 legendData[i] = o.messageName;
			 }
		 });
		 var seriesDataInner =new Array();
		 var index1 = 0;
		 jQuery.each(messageList, function (i, o) {
			 if(o.type == 1){
				 var obj = new Object();
				 obj = {value:parseInt(o.messageNum),name:o.messageName,messageUrl:o.messageUrl};
				 seriesDataInner[index1]= obj;
				 index1 ++ ;
			 }
		 });
		 var seriesDataOuter =new Array();
		 var index2 = 0;
		 jQuery.each(messageList, function (i, o) {
			 if(o.type == 2){
				 var obj = new Object();
				 obj = {messageUrl:o.messageUrl,value:parseInt(o.messageNum),name:o.messageName};
				 seriesDataOuter[index2]= obj;
				 index2 ++;
			 }
		 });
		if(seriesDataOuter.length > 0){
			
		

			require(
			        [
			            'echarts',
			            'echarts/chart/bar',
			            'echarts/chart/line',
			            'echarts/chart/pie'
			        ],
			        function(ec) {
			            var myChart = ec.init(document.getElementById('messageFrame'));
			            var option = {
			            	    tooltip : {
			            	        trigger: 'item',
			            	        formatter: "{b}  "
			            	    },
			            	    legend: {
			            	        orient : 'vertical',
			            	        x : 'left',
			            	        data:legendData
			            	    },
			            	    toolbox: {
			            	        show : true,
			            	        feature : {
			            	            mark : false,
			            	            dataView : {readOnly: true},
			            	            restore : false,
			            	            saveAsImage : true
			            	        }
			            	    },
			            	    calculable : false,
			            	    series : [
			            	        {
			            	            name:'消息类型',
			            	            type:'pie',
			            	            selectedMode: 'single',
			            	            radius : [0, 70],
			            	            itemStyle : {
			            	                normal : {
			            	                    label : {
			            	                        position : 'inner'
			            	                    },
			            	                    labelLine : {
			            	                        show : false
			            	                    }
			            	                }
			            	            },
			            	            data:seriesDataInner
			            	        },
			            	        {
			            	            name:'消息',
			            	            type:'pie',
			            	            radius : [100, 140],
			            	            data:seriesDataOuter
			            	        }
			            	    ]
			            	};
			            	                              
			            myChart.setOption(option);
			            myChart.on("click", function (data) {
			            	jQuery.each(seriesDataOuter, function (i, o) {
			   				 if(o.name == data.name){
			   					parent.showTag( o.messageUrl );
			   				 }
			   			 });
			            });
			            	                    
			        }
			    );
			}else{
				jQuery("#messageFrame").html("当前您没有代办消息！");
			}
}
	
	
	