package com.myexample.handler;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.database.Cursor;
import android.util.Log;

public class CommonHelper {

	public enum DateFormatExp {
		DEFAULT_DATE_TO_STRING_FORMAT("EEE MMM dd HH:mm:ss zzz yyyy"),
		MMM_DD_COMMA_YYYY("MMM dd, yyyy");

		private String format;

		DateFormatExp(String format) {
			this.format = format;
		}

		String getFormat() {
			return format;
		}
	}
	
	public static Charset utf8 = Charset.forName("UTF-8");
	
	public static String removeNonUtf8CompliantCharacters( final String inString ) {
	    if (null == inString ) return null;
	   
	    byte[] byteArr = inString.getBytes();
	    for ( int i=0; i < byteArr.length; i++ ) {
	        byte ch= byteArr[i]; 
	        // remove any characters outside the valid UTF-8 range as well as all control characters
	        // except tabs and new lines
	        if ( !( (ch > 31 && ch < 253 ) || ch == '\t' || ch == '\n' || ch == '\r') ) {
	            byteArr[i]=' ';
	        }
	    }
	    return new String( byteArr );
	}

	private static final Long defaultBibleType = 1L;
	
	public static boolean isDefaultBible(Long bookType){
		return bookType.equals(defaultBibleType);
	}

	private static final String TAG = CommonHelper.class.getSimpleName();

	public static Integer gInt(Cursor cursor, String columeName) {
		Integer val = null;
		if (cursor.getColumnIndex(columeName) != -1) {
			val = cursor.getInt(cursor.getColumnIndex(columeName));
		}
		return val;
	}
	
	public static Long gLong(Cursor cursor, String columeName) {
		Long val = null;
		if (cursor.getColumnIndex(columeName) != -1) {
			val = cursor.getLong(cursor.getColumnIndex(columeName));
		}
		return val;
	}

	public static String gText(Cursor cursor, String columeName) {
		String val = null;
		if (cursor.getColumnIndex(columeName) != -1) {
			val = cursor.getString(cursor.getColumnIndex(columeName));
		}
		return val;
	}

	public static Boolean gBoolean(Cursor cursor, String columeName) {
		Boolean val = null;
		if (cursor.getColumnIndex(columeName) != -1) {
			val = cursor.getInt(cursor.getColumnIndex(columeName)) > 0;
		}
		return val;
	}

	public static String dateToString(Date date, DateFormatExp enumExpresion) {
		DateFormat df = new SimpleDateFormat(enumExpresion.getFormat());
		String strDate = df.format(date);
		return strDate;
	}

	public static String dateChangeFormatFromDefault(String strDate, DateFormatExp enumExpresion) {
		DateFormat df = new SimpleDateFormat(DateFormatExp.DEFAULT_DATE_TO_STRING_FORMAT.getFormat());
		Date reversedDate = null;
		String formatStr=StringUtils.EMPTY;
		try {
			reversedDate = df.parse(strDate);
		} catch (ParseException e) {
			Log.d(TAG, "Date conversion failed");
		}
		if(reversedDate!=null){
			df = new SimpleDateFormat(enumExpresion.getFormat());
			formatStr= df.format(reversedDate);
		}
		return formatStr;
	}
	

	//Hot fix: need reconsideration
	public static String getConcatedAuthorsNameInListView(String authorsNames) {
		String[] splitStrings = authorsNames.split(";");
		return getConcatedAuthorsName(Arrays.asList(splitStrings));
	}
	
	public static String getConcatedAuthorsName(List<String> authorsNames) {
		String author = null;
		Collection<String> authors = new ArrayList<String>();
		for (String _author : authorsNames) {
			if(!_author.contains(",")){
				author=_author;
				continue;
			}
			String tempAuthor = StringUtils.replaceChars(_author, ",", "");
			tempAuthor = StringUtils.reverseDelimited( tempAuthor, ' ' );
			authors.add(StringUtils.trimToEmpty(tempAuthor));
		}
		if (authors.size() > 1) {
			author = StringUtils.join(authors, ", ");
		} else {
			Iterator<String> iterator = authors.iterator();
			if (iterator.hasNext())
				author = iterator.next();
		}
		return author;
	}
}
