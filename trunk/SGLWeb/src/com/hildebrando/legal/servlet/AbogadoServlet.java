package com.hildebrando.legal.servlet;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbogadoServlet extends HttpServlet 
{
  public void service(HttpServletRequest req, HttpServletResponse res) throws IOException
  {
	   String requestURL = req.getRequestURL().toString();
	   String servletPath = req.getServletPath();
	   String serverPath = requestURL.substring(0,requestURL.indexOf(servletPath));
	   
	   String nuevoPath = serverPath.replace("SGLWeb", "Mantenimientos");
	   System.out.println("URL: " + nuevoPath);
	   
	   URL url = new URL(nuevoPath + "/modules/Abogado");
	   
	   res.sendRedirect(url.getPath());
  }
}