package br.com.adprofissionais.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

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
		Logger logger = Logger.getLogger("adcon");
		  logger.info("iniciando aplicação");
		  logger.debug("debug here");

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
			logger.info("usuario salvo no banco com sucesso");
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
		System.out.println (TimeZone.getDefault());
    	String s = password;
		try {
	    	MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
	    	byte messageDigest[] = algorithm.digest(s.getBytes("UTF-8"));
	    	 
	    	StringBuilder hexString = new StringBuilder();
	    	for (byte b : messageDigest) {
	    	  hexString.append(String.format("%02X", 0xFF & b));
	    	}
	    	password = hexString.toString();
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
			System.out.println("Uname = "  + uname );
			result = uDAO.login(uname, password);
		       if (result) {
		            HttpSession session = Util.getSession();
		            session.setAttribute("username", uname);
		            session.setAttribute("data", new Date());
		            System.out.println("atribulo gravado =" + session.getAttribute("username"));
		            
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
    	System.out.println("entrei no logout");
        HttpSession session = Util.getSession();
        session.invalidate();
        return "/login.xhtml";
     }
    
    public String status() {
    	System.out.println("entrei no status" + uname);
    	HttpSession session = Util.getSession();
    	String username = (String) session.getAttribute("username");
    	if (session.getAttribute("username") != null) {
    		System.out.println("achei ");
    		UsuarioDAO udao = new UsuarioDAO();
    		try {
				return udao.nomeLogin(username);
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adcionarMensagemErro(e.getMessage());
	            session.invalidate();
	            return "/login.xhtml";
			}
    	} else {
        	JSFUtil.adcionarMensagemErro("Não Logado no sistema");
            session.invalidate();
            return "/login.xhtml";
    	}
    }
}
