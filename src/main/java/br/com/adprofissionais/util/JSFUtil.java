package br.com.adprofissionais.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JSFUtil {

	public static void adcionarMensagemSucesso(String mensagem) {

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);

		FacesContext contexto = FacesContext.getCurrentInstance();

		contexto.addMessage(null, msg);

	}

	public static void adcionarMensagemErro(String mensagem) {

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);

		FacesContext contexto = FacesContext.getCurrentInstance();

		contexto.addMessage(null, msg);

	}

}
