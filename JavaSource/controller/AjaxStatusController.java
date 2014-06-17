package controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 * ajax status的controller
 * @author Ade
 *
 */

@ManagedBean(name = "ajaxStatusController")
@ViewScoped
public class AjaxStatusController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public void procssAjax(){
		try {
	        Thread.sleep(2000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		System.out.println("处理ajax请求");
	}

}
