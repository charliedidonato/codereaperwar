package com.empirestateids.utls;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.json.simple.parser.ContainerFactory;

import net.minidev.json.JSONValue;
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

	public static Map parseJSON2(String jsonText)  {
	  JSONParser parser = new JSONParser();
	  ContainerFactory containerFactory = new ContainerFactory(){
	    public List creatArrayContainer() {
	      return new LinkedList();
	    }

	    public Map createObjectContainer() {
	      return new LinkedHashMap();
	    }

	  };
	  Map json = null;
	  try{
	    json = (Map)parser.parse(jsonText, containerFactory);
	    Iterator iter = json.entrySet().iterator();
	    logger.debug("==iterate result==");
	    while(iter.hasNext()){
	      Map.Entry entry = (Map.Entry)iter.next();
	      logger.debug(entry.getKey() + "=>" + entry.getValue());
	    }

	    logger.debug("==toJSONString()==");
	    logger.debug(JSONValue.toJSONString(json));

	  }
	  catch(ParseException pe){
	    logger.error("Exception:"+pe.getMessage()+ "Trace:"
	    	+ UtilityMethods.getStackTrace(pe));
	  }
	  return json;
	}

	public static Timestamp getTimestampFormOffsetDateTime(OffsetDateTime offset) {

		Timestamp timestamp = Timestamp.valueOf(offset.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
		return timestamp;

	}
}
