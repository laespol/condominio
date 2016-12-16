package br.com.adprofissionais.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

@ManagedBean(name = "MBTeste")
@ViewScoped
public class TesteBean {

	private String nome;
	private Date data;
	private Double input2 = new Double(0);
    private int number3;
    

	public int getNumber3() {
		return number3;
	}

	public void setNumber3(int number3) {
		this.number3 = number3;
	}

	public Double getInput2() {
		return input2;
	}

	public void setInput2(Double input2) {
		this.input2 = input2;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void teste() {

		System.out.println(nome);
		System.out.println(data);

		setData(new Date());
		System.out.println(data);

	}

	public void teste1() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		System.out.println(nome);
		System.out.println(data);

		setData(new Date());
		System.out.println(data);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado", null));

	}

	public void onDateSelect(SelectEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		System.out.println(nome);
		System.out.println(data);
		System.out.println(event);

		setNome("Feio");

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
	}

	public void handleKeyEvent() {
		nome = nome.toUpperCase();

		setNome("Feio");

	}



}
