package com.bbva.common.listener.SpringInit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prueba {

	public static void main(String[] args) {
		

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		try {
			String date = format.format(new Date());
			Date date2 = format.parse(date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private Date parseDate(String date, String format) throws ParseException
	{
	    SimpleDateFormat formatter = new SimpleDateFormat(format);
	    return formatter.parse(date);
	}

}
