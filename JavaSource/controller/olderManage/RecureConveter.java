package controller.olderManage;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import service.olderManage.PensionRecureDetailExtend;

@SuppressWarnings("serial")
public class RecureConveter implements Converter,Serializable {
	private List<PensionRecureDetailExtend> list;
	@Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {

                for (PensionRecureDetailExtend p : list) {
                    if (p.getName().contentEquals(submittedValue)) {
                        return p;
                    }
                }

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }

        return null;
    }
	@Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((PensionRecureDetailExtend) value).getName());
        }
    }
	public void setList(List<PensionRecureDetailExtend> list) {
		this.list = list;
	}
	public List<PensionRecureDetailExtend> getList() {
		return list;
	}
}
                    
