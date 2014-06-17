var currentCount = 0;
var item = new Array();
var itemText = new Array();
var focusImg = "zh3.png";
var focusPageIoc = "../images/common/"+focusImg;
var unfocusPageIoc = "../images/common/zh4.png";
jQuery(document).ready(function(){		
		 function GetById(id) {
		        return document.getElementById(id);
		    }
		 var meuuParent = GetById("topTags");
		 var menu = GetById("topTags").getElementsByTagName("ul")[0]; //顶部菜单容器
		 var tags = menu.getElementsByTagName("li"); //顶部菜单
		 var ck = jQuery(".menu-li");
		 var frameBox = GetById("main-content");
		 var iframes = frameBox.getElementsByTagName("iframe");

		 
		 //alert(ck.length);
		 //ck[0].onclick = function() { alert('ddddddd') }  
		 
		 
		 //点击左侧菜单增加新标签
		    for (var i = 0; ck.length > i; i++) {
		        ck[i].onclick = function() {
		          clearMenu();
		          this.style.background = 'url(../images/f1_03.gif)';
		          this.style.backgroundRepeat="no-repeat";
		            //循环取得当前索引
		            for (var j = 0; ck.length > j; j++) {
		            	if (this == ck[j]) {
		                    if (GetById("p" + j) == null) {
		                    	var url = this.getElementsByTagName("div")[0].innerHTML;
		                        openNew(j, this.innerHTML, url, true); //设置标签显示文字
		                        
		                    }
		                    clearStyle();
		                    GetById("p" + j).style.background = 'url('+focusPageIoc+')';
		                    GetById("p" + j).style.color = '#000000';
		                    GetById("f" + j).className = "mainFrame";
				            
		                    
		                   // clearContent();
		                    /* GetById("c" + j).style.display = "block"; */
		                }
		            }
		            return false; 
		        }
		    }
		 
		    //增加或删除标签
		    function openNew(id, name, url, isClose) {
		    	
		        var tagMenu = document.createElement("li");
		        tagMenu.id = "p" + id;
		        nameTemp = name.split("<")[0];
		     //   alert(name)
		        nameTemp = name.split(">")[1];
		        nameTemp = nameTemp.split("<")[0];
		     //   alert();
		        tagMenu.title = nameTemp;
		       if(isClose) {
		    	   tagMenu.innerHTML = nameTemp==""?"":nameTemp.substr(0,4) + "&nbsp;" + "<img title='关闭' style='margin-bottom: 2px;' src='../images/common/zh5.png'/>";
		       } else {
		    	   tagMenu.innerHTML = nameTemp==""?"":nameTemp.substr(0,4) + "&nbsp;" + "<img  style='margin-bottom: 3px;display:none;' src='../images/zh5.png'/>";  
		       }

		        var frame = document.createElement("iframe");
		        frame.id = "f" + id; 
		        frame.src = url;
		        frame.className ="mainFrame";
		        frame.setAttribute("frameborder","0px");
		        var maxCount = document.getElementById("hideForm:maxShowCount").innerHTML;
            	if(item.length>maxCount)
            	{
            		frameBox.removeChild(itemText.splice(1,1)[0]);
            		menu.removeChild(item.splice(1,1)[0]);
            	}

		        frameBox.appendChild(frame);
		        menu.appendChild(tagMenu);
		        item.push(tagMenu);
		        itemText.push(frame);
		        
		        //标签点击事件
		        tagMenu.onclick = function(evt) {
		            clearMenu();
		            ck[id].style.background = 'url(../images/f1_03.gif)';
		            ck[id].style.backgroundRepeat="no-repeat";
		            
					clearStyle();
		            tagMenu.style.background = 'url('+focusPageIoc+')';
		            tagMenu.style.color = '#000000';
		            frame.className ="mainFrame";
		           // clearContent();
		            //GetById("c" + id).style.display = "block";
		        };
		        
		        
		      //标签内关闭图片点击事件
		        tagMenu.getElementsByTagName("img")[0].onclick = function(evt) {
		            evt = (evt) ? evt: ((window.event) ? window.event: null);
		            if (evt.stopPropagation) {
		                evt.stopPropagation();
		            } //取消opera和Safari冒泡行为;
		            this.parentNode.parentNode.removeChild(tagMenu); //删除当前标签
		            frameBox.removeChild(frame);
		            deleteItem(tagMenu,frame);
		            menu.style.width = (menu.clientWidth - 90) + 'px';
		           
		            
		            
		            
		            var bgimg = tagMenu.style.background;
		            if(bgimg.indexOf(focusImg) > 0) {
		                if (tags.length - 1 >= 0) {
		                    clearStyle();
		                    tags[tags.length - 1].style.background = 'url('+focusPageIoc+')';
		                    
		                    iframes[tags.length - 1].className ="mainFrame";
		                    
		                   // clearContent();
		                    var cc = tags[tags.length - 1].id.split("p");
		                   // GetById("c" + cc[1]).style.display = "block";
		                    clearMenu();
		                    ck[cc[1]].style.background = 'url(../images/f1_03.gif)';
		                    
		                    ck[cc[1]].style.backgroundRepeat="no-repeat";
		                    
		                } else {
		                   // clearContent();
		                    clearMenu();
		                }
		            }
		        }
		        
		        
		        //标签内关闭图片点击事件
		        if(isClose) {
			        tagMenu.ondblclick = function(evt) {
			            evt = (evt) ? evt: ((window.event) ? window.event: null);
			            if (evt.stopPropagation) {
			                evt.stopPropagation()
			            } //取消opera和Safari冒泡行为;
			            this.parentNode.removeChild(tagMenu); //删除当前标签
			            frameBox.removeChild(frame);
			            deleteItem(tagMenu,frame);
			            menu.style.width = (menu.clientWidth - 90) + 'px';
			      
			            
			            var bgimg = tagMenu.style.background;
			            if(bgimg.indexOf(focusImg) > 0) {
			                if (tags.length - 1 >= 0) {
			                    clearStyle();
			                    tags[tags.length - 1].style.background = 'url('+focusPageIoc+')';
			                    
			                    iframes[tags.length - 1].className ="mainFrame";
			                    
			                   // clearContent();
			                    var cc = tags[tags.length - 1].id.split("p");
			                   // GetById("c" + cc[1]).style.display = "block";
			                    clearMenu();
			                    ck[cc[1]].style.background = 'url(../images/f1_03.gif)';
			                    ck[cc[1]].style.backgroundRepeat="no-repeat";
			                } else {
			                   // clearContent();
			                    clearMenu();
			                }
			            }
			        }
		        }
		        
		       // if(menu.clientWidth >= meuuParent.clientWidth) {
		       // alert(meuuParent.style.width);
		      //  alert( meuuParent.offsetWidth);
		      //  alert( meuuParent.parentNode.offsetWidth);
		        	meuuParent.style.width =  meuuParent.offsetWidth + "px";
		        	//jQuery("#topTags").css("width", (jQuery("#main-content").width()-30)+"px");
		        //	alert(meuuParent.style.width);
		        	menu.style.width = menu.clientWidth + 120 + 'px';
		        	//alert("换行了");
		        	
		     //   }


		        

		        
		    } 
		    //清除菜单样式
		    function clearMenu() {
		        for (i = 0; ck.length > i; i++) {
		            ck[i].style.background = 'url(../image/tabbg01.gif)';
		        }
		    }
		    
		  //清除标签样式
		    function clearStyle() {
		        for (i = 0; tags.length > i; i++) {
		            var dTag = menu.getElementsByTagName("li")[i];
		            var dFrame = frameBox.getElementsByTagName("iframe")[i];
		            dFrame.className = "minFrame";
		            
		            dTag.style.background = 'url('+unfocusPageIoc+')';
		            dTag.style.color = '#59814C';
		        }
		    }
		    
		  
		    openNew(0, '<img>工作台', 'desktop.jsf',false);
		    
		    clearStyle();
            GetById("p" + 0).style.background = 'url('+focusPageIoc+')';
            GetById("p" + 0).style.color = '#000000';
            GetById("f" + 0).className = "mainFrame";
            
            

            
	});
	
	function showTag(messageUrl) {
		jQuery(".menu-li").each(function(){
			if(messageUrl.indexOf(jQuery(this).find("div").html()) >= 0) {
				jQuery(this).click();
				jQuery(".mainFrame").attr("src",messageUrl+"&aaa="+new Date());
			}
		});
    	
    }
	
	function showMapTag(messageUrl) {
		if(messageUrl != "") {
			jQuery(".menu-li").each(function(){
				if(messageUrl.indexOf(jQuery(this).find("div").html()) >= 0) {
					jQuery(this).click();
				}
			});
		}
    }
	
    function toLeft() {
    	var menu = document.getElementById("topTags").getElementsByTagName("ul")[0]; //顶部菜单容器
    	var meuuParent = document.getElementById("topTags");
    	
    	// alert(menu.clientWidth);
    	//alert(menu.clientWidth +","+ meuuParent.clientWidth);
    	
	   	 if(menu.clientWidth < meuuParent.clientWidth) {
	   		// alert("dddddd");
	   		 return;
	   	 }
	   	 
    	marginLeft = menu.style.marginLeft;
    	if(typeof(marginLeft) == "undefined" || marginLeft == "undefined" || marginLeft == "") {
    		marginLeft = "0px";
    	}
    	marginLeft = parseInt(marginLeft);
    	//alert(menu.clientWidth+","+meuuParent.clientWidth);
    	
    	if((menu.clientWidth + marginLeft) <=  meuuParent.clientWidth) {
    		return;
    	}
    	
    	menu.style.marginLeft=(marginLeft-90) + "px";
    	//alert(menu.style.marginLeft);
	}
	
	function toRight() {
		var menu = document.getElementById("topTags").getElementsByTagName("ul")[0]; //顶部菜单容器
		marginLeft = menu.style.marginLeft;
		if(typeof(marginLeft) == "undefined" || marginLeft == "undefined" || marginLeft == "") {
    		marginLeft = "0px";
    	}
    	marginLeft = parseInt(marginLeft);
    	
    	if(marginLeft>=0) {
    		return;
    	}
    	
    	menu.style.marginLeft=(marginLeft+90) + "px";
    	//alert(menu.style.marginLeft);
	}
	
	function deleteItem(i,t){
		for(var j = 0;j<item.length;j++){
			if(i == item[j]){
				item.splice(j, 1);
				itemText.splice(j, 1);
				return;
			}
		}
			
	}