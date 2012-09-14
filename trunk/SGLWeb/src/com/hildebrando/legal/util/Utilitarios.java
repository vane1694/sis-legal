package com.hildebrando.legal.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Utilitarios {

	private static String NUMEROS = "0123456789";

	private static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	private static String ESPECIALES = "Ò—@";

	public static String getRuc() {
		return MAYUSCULAS + MINUSCULAS;
	}

	public static String getContrasenia() {
		return getContrasenia(20);
	}

	public static String getContrasenia(int length) {
		return getContrasenia(NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES,
				length);
	}

	private static String getContrasenia(String key, int length) {
		String pswd = "";

		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}

	public static Timestamp getFechaActual() {

		Date fecha = new Date();
		Timestamp time = new Timestamp(fecha.getTime());
		return time;

	}

	public static String formatoFecha(Date fecha) {

		String fechaActualizacion = "";
		String horaActualizacion = "";

		Calendar calFechaAct = Calendar.getInstance();
		calFechaAct.setTimeInMillis(fecha.getTime());
		fechaActualizacion = calFechaAct.get(Calendar.DATE) + "/"
				+ calFechaAct.get(Calendar.MONTH) + "/"
				+ calFechaAct.get(Calendar.YEAR);
		horaActualizacion = calFechaAct.get(Calendar.HOUR) + ":"
				+ calFechaAct.get(Calendar.MINUTE) + ":"
				+ calFechaAct.get(Calendar.SECOND);

		return fechaActualizacion + " " + horaActualizacion;
	}

	public static boolean validaVersionExcel(String palabra) {

		boolean paso;
		int can = 0;

		if (palabra.length() == 5) {
			for (int i = 0; i < palabra.length(); i++) {
				if (palabra.substring(i, i + 1).equals(".")) {
					if (i % 2 != 0) {
						can++;
					}
				}
			}
		}

		if (can == 2)
			paso = true;// si es version
		else
			paso = false;// no es version

		return paso;
	}

	public static boolean validaCaracteresEspeciales(String email) {

		Pattern p = Pattern.compile("[^A-Za-z0-9\\.\\·ÈÌÛ˙¡…Õ”⁄\\/\\@_\\-~#]+");

		boolean caracteresIlegales = false;

		if (email.contains(" ")) {

			StringTokenizer st = new StringTokenizer(email, " ");
			while (st.hasMoreElements()) {
				Matcher m = p.matcher(st.nextToken());
				StringBuffer sb = new StringBuffer();
				boolean resultado = m.find();
				// boolean caracteresIlegales = false;
				while (resultado) {
					caracteresIlegales = true;
					m.appendReplacement(sb, "");
					resultado = m.find();
				}

			}

		} else {

			Matcher m = p.matcher(email);
			StringBuffer sb = new StringBuffer();
			boolean resultado = m.find();

			while (resultado) {
				caracteresIlegales = true;
				m.appendReplacement(sb, "");
				resultado = m.find();
			}
		}

		if (caracteresIlegales)
			return true;
		else
			return false;
	}

	public static boolean validaTildes(String palabra) {

		boolean paso = true;

		if (palabra.contains("·"))
			return paso;
		else if (palabra.contains("È"))
			return paso;
		else if (palabra.contains("Ì"))
			return paso;
		else if (palabra.contains("Û"))
			return paso;
		else if (palabra.contains("˙"))
			return paso;
		else if (palabra.contains("¡"))
			return paso;
		else if (palabra.contains("…"))
			return paso;
		else if (palabra.contains("Õ"))
			return paso;
		else if (palabra.contains("”"))
			return paso;
		else if (palabra.contains("⁄"))
			return paso;
		else
			paso = false;

		return paso;

	}

	public static String quitaComaFinal(String palabra) {
		return palabra.substring(0, palabra.length() - 2);
	}

	public static String quitarSlash(String palabra) {
		return palabra.substring(0, palabra.length() - 1);
	}

	public static boolean validaNumero(String num) {

		boolean paso;

		try {

			double numero = Double.parseDouble(num.trim());
			paso = true;

		} catch (Exception e) {
			paso = false;
		}
		return paso;

	}

	public static boolean compararCadenas(String fijo, String prmt) {

		if (fijo.toUpperCase().toString().startsWith((prmt).toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean compararNumeros(String fijo, String prmt) {

		if (fijo.toString().startsWith((prmt).toString())) {
			return true;
		} else {
			return false;
		}
	}

	public static byte[] returnArray(String ruta) {
		File file = new File(ruta);
		// System.out.println(file.exists() + "!!");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {

			FileInputStream fis = new FileInputStream(file);
			byte[] buf = new byte[1024];

			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		byte[] bytes = bos.toByteArray();

		return bytes;
	}

	public static void mensaje(String titulo, String mensaje) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(titulo, mensaje));
	}

	public static void mensajeInfo(String titutlo, String mensaje) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "InformaciÛn: ", mensaje));
	}

	public static void mensajeError(String titutlo, String mensaje) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_WARN, "AtenciÛn: ", mensaje));
	}

	public static void putObjectInSession(String value, Object var) {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = (HttpSession) context.getSession(true);
		session.setAttribute(value, var);
	}

	public static Object getObjectInSession(String value) {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession sessionhttp = (HttpSession) context.getSession(true);
		return (Object) sessionhttp.getAttribute(value);
	}

	public static void removeObjectInSession(String value) {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		session.removeAttribute(value);
		session.invalidate();
	}

	public static String remove1(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";
		// Cadena de caracteres ASCII que reemplazar·n los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}// for i
		return output;
	}

	public static String quitarTilde(String palabra) {
		String tustring = palabra;
		tustring = tustring.replace('·', 'a');
		tustring = tustring.replace('¡', 'A');
		tustring = tustring.replace('È', 'e');
		tustring = tustring.replace('…', 'E');
		tustring = tustring.replace('Ì', 'i');
		tustring = tustring.replace('Õ', 'I');
		tustring = tustring.replace('Û', 'o');
		tustring = tustring.replace('”', 'O');
		tustring = tustring.replace('˙', 'u');
		tustring = tustring.replace('⁄', 'U');

		return tustring.replaceAll(" ", "");
	}

	public static boolean validateEmail(String email) {

		Pattern p = Pattern
				.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");// me
																												// gusta
																												// esta

		Matcher m = p.matcher(email);
		return m.matches();
	}

	/*
	 * jc.ortiz: 14-04-2012 funciÛn para eliminar espacios en blanco desde la
	 * izquierda.
	 */
	public static String elmiminarEspaciosAlaIzquierda(String palabra) {
		String token = palabra != null ? palabra.trim() : "";
		int count = token.length();
		for (int i = 0; i < count; i++) {
			if (token.charAt(i) != ' ') {
				return token.substring(i);
			}
		}
		return "";

	}

	private String CLOBToString(Clob cl) throws IOException, SQLException {
		if (cl == null)
			return "";
		StringBuffer strOut = new StringBuffer();
		String aux;
		BufferedReader br = new BufferedReader(cl.getCharacterStream());
		while ((aux = br.readLine()) != null) {
			strOut.append(aux);
		}
		return strOut.toString();
	}

}
