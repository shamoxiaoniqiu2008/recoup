package controller.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import service.system.TimerProperties;
import service.system.TimerService;
import util.PmsException;

public class TimerController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  transient  TimerService timerService;
	private List<TimerProperties> timerPropertiesList;
	private TimerProperties oneTimerProperty;
	
	@PostConstruct
	public void  init() {
		fetchTimer();
	}
	
	public void fetchTimer() {
		try {
			timerPropertiesList = timerService.fetchTimerType();
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actionTimer() {
		if(oneTimerProperty != null && oneTimerProperty.getKey() != null) {
			String open;
			if(oneTimerProperty.getOpen().equals("1")) {
				open = "0";
			} else {
				open = "1";
			}
			try {
				timerService.putUpTimerType(oneTimerProperty.getKey()+".open", open);
				oneTimerProperty.setOpen(open);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"设置成功", ""));
			} catch (PmsException e) {
				// TODO Auto-generated catch block
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								e.getMessage(), ""));
				e.printStackTrace();
			}
	
		}
	}
	public void setTimerService(TimerService timerService) {
		this.timerService = timerService;
	}
	public TimerService getTimerService() {
		return timerService;
	}

	public void setTimerPropertiesList(List<TimerProperties> timerPropertiesList) {
		this.timerPropertiesList = timerPropertiesList;
	}

	public List<TimerProperties> getTimerPropertiesList() {
		return timerPropertiesList;
	}

	public void setOneTimerProperty(TimerProperties oneTimerProperty) {
		this.oneTimerProperty = oneTimerProperty;
	}

	public TimerProperties getOneTimerProperty() {
		return oneTimerProperty;
	}
}
