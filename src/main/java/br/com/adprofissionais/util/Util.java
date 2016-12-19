package br.com.adprofissionais.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
 
public class Util {
 
      public static HttpSession getSession() {
    	  System.out.println("HttpSession getSession()");
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }
       
      public static HttpServletRequest getRequest() {
    	  System.out.println("HttpServletRequest getRequest()");
       return (HttpServletRequest) FacesContext.
          getCurrentInstance().
          getExternalContext().getRequest();
      }
 
      public static String getUserName()  {
    	  System.out.println("String getUserName()");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return  session.getAttribute("username").toString();
      }
       
      public static String getUserId()
      {
        HttpSession session = getSession();
        if ( session != null )
            return (String) session.getAttribute("userid");
        else
            return null;
      }
}