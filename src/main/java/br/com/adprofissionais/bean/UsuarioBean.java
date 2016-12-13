package br.com.adprofissionais.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import br.com.adprofissionais.dao.UsuarioDAO;
import br.com.adprofissionais.domain.Usuario;
import br.com.adprofissionais.util.JSFUtil;
import br.com.adprofissionais.util.Util;

@ManagedBean(name = "MBUsuario")
@ViewScoped
public class UsuarioBean {

	private Usuario usuario;
	private ArrayList<Usuario> itens;
	private ArrayList<Usuario> itensFiltrados;
    private String password;
    private String uname;

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ArrayList<Usuario> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Usuario> itens) {
		this.itens = itens;
	}

	public ArrayList<Usuario> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Usuario> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public void prepararPesquisa() {
		try {
			UsuarioDAO udao = new UsuarioDAO();
			itens = udao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		System.out.println("entrei no salvar");

		System.out.println(usuario.getSenha());
		String s = usuario.getSenha();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(s.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			usuario.setSenha(hexString.toString());
			System.out.println("SHA-256 " + usuario.getSenha());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			JSFUtil.adcionarMensagemErro(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			JSFUtil.adcionarMensagemErro(e.getMessage());
		}
		UsuarioDAO udao = new UsuarioDAO();
		try {
			udao.salvar(usuario);
			itens = udao.listar();
			JSFUtil.adcionarMensagemSucesso("Usuario Salvo com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adcionarMensagemErro(e.getMessage());
		}
	}

	public void prepararNovo() {
		usuario = new Usuario();
	}

	public void excluir() {
		if (usuario.getStatus().equals("A")) {
			UsuarioDAO udao = new UsuarioDAO();
			try {
				udao.excluir(usuario);
				itens = udao.listar();
				JSFUtil.adcionarMensagemSucesso("Usuario Excluido com Sucesso");
			} catch (SQLException ex) {
				ex.printStackTrace();
				JSFUtil.adcionarMensagemErro(ex.getMessage());
			}
		} else {
			JSFUtil.adcionarMensagemErro("Usuario inativo não pode ser excluido");
		}

	}

	public void editar() {
		if (usuario.getStatus().equals("A")) {
			String s = usuario.getSenha();
			try {
				MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
				byte messageDigest[] = algorithm.digest(s.getBytes("UTF-8"));

				StringBuilder hexString = new StringBuilder();
				for (byte b : messageDigest) {
					hexString.append(String.format("%02X", 0xFF & b));
				}
				usuario.setSenha(hexString.toString());
				System.out.println("SHA-256 " + usuario.getSenha());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				JSFUtil.adcionarMensagemErro(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				JSFUtil.adcionarMensagemErro(e.getMessage());
			}
			UsuarioDAO udao = new UsuarioDAO();
			try {
				udao.editar(usuario);
				itens = udao.listar();
				JSFUtil.adcionarMensagemSucesso("Usuario Alterado com Sucesso");
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adcionarMensagemErro(e.getMessage());
			}

		} else

		{
			JSFUtil.adcionarMensagemErro("Usuario inativo não pode ser alterado");
		}
	}
	
    public String loginProject() {
    	System.out.println(uname + " " + password);
    	String s = password;
		try {
	    	MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
	    	byte messageDigest[] = algorithm.digest(s.getBytes("UTF-8"));
	    	 
	    	StringBuilder hexString = new StringBuilder();
	    	for (byte b : messageDigest) {
	    	  hexString.append(String.format("%02X", 0xFF & b));
	    	}
	    	password = hexString.toString();
	    	System.out.println("SHA-256 " + password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			JSFUtil.adcionarMensagemErro(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			JSFUtil.adcionarMensagemErro(e.getMessage());
		}
		
		UsuarioDAO uDAO = new UsuarioDAO();
    	
        boolean result;
		try {
			result = uDAO.login(uname, password);
		       if (result) {
		            HttpSession session = Util.getSession();
		            session.setAttribute("username", uname);
		            return "/pages/principal";
		        } else { 
		        	JSFUtil.adcionarMensagemErro("Usuario Inválido");
		            return "login";
		        }
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adcionarMensagemErro(e.getMessage());
			  return "login";
		}

    }
    
    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "login";
     }
}
