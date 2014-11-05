package com.hildebrando.legal.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Util {
	
	private static ResourceBundle resourceMensaje    = ResourceBundle.getBundle("legal");
	
	public static String getMessage(String mensajeId) {
		String mensaje = resourceMensaje.getString(mensajeId);
		return ("".equals(mensaje))?mensajeId:mensaje;
	}
	public static String getMessage(String mensajeId, String[] values) {
		String mensaje = resourceMensaje.getString(mensajeId);
		mensaje = ("".equals(mensaje))?mensajeId:mensaje;
		
		Object arguments[] = new Object[values.length];
		for (int i = 0; i < arguments.length; i++) {
			arguments[i] = values[i];
		}
		return MessageFormat.format(mensaje, arguments);
	}
	public static String getRelativeURL(String key) {
		return resourceMensaje.getString(key);
	}
	public static String generarSangriaJerarquica(int nivel) {
		String relleno = "";
		for (int i = 1; i < nivel; i++) {
			relleno += "--";
		}
		relleno += "> ";
		return relleno;
	}
	public static String rellenarCadenaDerecha(char caracter, int cantidad, Object objeto) {
		String cadena = ""+objeto;
		for (int i = cantidad - cadena.length(); i > 0; i--) {
			cadena = cadena+caracter;
		}
		return cadena.substring(0,cantidad);
	}
	public static String rellenarCadenaIzquierda(char caracter, int cantidad, Object objeto) {
		String cadena = ""+objeto;
		for (int i = cantidad - cadena.length(); i > 0; i--) {
			cadena = caracter+cadena;
		}
		return cadena.substring(0,cantidad);
	}
}
