package com.empirestateids.utls;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * A place for static methods that perform useful work
 *
 * @author Charlie D
 *
 */
public class UtilityMethods {


	static Logger logger = LogManager.getLogger(JavaEmail.class);

	// a date format for MySql
	public static final SimpleDateFormat mySqlFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	// array to prevent cross site scripting and sql injection
	private static int numberOfCharacters = 4096;
	private static String[] neutralizeList = new String[numberOfCharacters];
	static {
		// a list of character to prevent SQL injection and
		// cross site scripting
		neutralizeList['"'] = "&#34;";
		neutralizeList['%'] = "&#37";
		neutralizeList['&'] = "&#38;";
		neutralizeList['\''] = "&#39;";
		neutralizeList['('] = "&#40;";
		neutralizeList[')'] = "&#41;";
		neutralizeList['+'] = "&#43;";
		neutralizeList[';'] = "&#59;";
		neutralizeList['<'] = "&#60;";
		neutralizeList['>'] = "&#62;";
	}

	/**
	 * Captures the stack trace as a string, allowing it to be logged to other
	 * destinations than sysout.
	 */
	public static String getStackTrace(Throwable t) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter out = new PrintWriter(baos);
		t.printStackTrace(out);
		out.flush();
		return baos.toString();
	}

	/**
	 * Removes hazardous characters from an output string by replacing them with
	 * their HTML equivalent. This method should be called when getting form
	 * parameters so there is no chance of storing scripts in the database. Eg
	 * String someString =
	 * UtilityMethods.neutralize(request.getParameter("someParam"));
	 */
	public static String neutralize(String output) {
		if ((output == null) || (output.length() == 0)) {
			return output;
		}
		StringBuffer cleanOutput = null;
		String arrayVal = "";
		cleanOutput = new StringBuffer();
		for (int i = 0; i < output.length(); i++) {
			arrayVal = neutralizeList[output.charAt(i)];
			if (arrayVal == null) {
				cleanOutput.append(output.charAt(i));
			} else {
				cleanOutput.append(arrayVal);
			}
		}
		String cleanString = cleanOutput.toString();
		return (cleanString);
	}

	
	public static Timestamp getTimestampFormOffsetDateTime(OffsetDateTime offset) {

		Timestamp timestamp = Timestamp.valueOf(offset.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
		return timestamp;

	}
}
