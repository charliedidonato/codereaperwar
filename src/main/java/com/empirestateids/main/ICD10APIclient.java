package com.empirestateids.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.empirestateids.common.IConstants;
import com.empirestateids.domain.Icd10;
import com.empirestateids.domain.RowsCreatedOrUpdated;


public class ICD10APIclient {



	public static void main(String[] args) throws FileNotFoundException, Exception {

		ICD10APIclient api = new ICD10APIclient();
		//String token = api.getToken();
		List<Icd10> icd10Codes = new ArrayList<Icd10>();
		File inputFile = new File("c:/1/icd10cm_codes_2025.txt");
		FileReader fr = new FileReader(inputFile);
		BufferedReader buf = new BufferedReader(fr);
		String line = "";
		int lineNumber = 0;
		while ( (line=buf.readLine())!=null) {
			lineNumber++;
			if (line.length()<7) {
				continue;
			}

			String code = line.substring(0,7);
			if(code==null || code.length() < 3) {
				throw new Exception("ICD 10 File:"+inputFile.getName()+" is corrupted on line #:"+lineNumber);
			}
			code = code.trim();
			String descFile = line.substring(8);
			Icd10 icd10 = new Icd10();
			icd10.setCode(code);
			icd10.setDescription(descFile);
			icd10Codes.add(icd10);
		}

		for (int i = 0; i < icd10Codes.size(); i++) {
			Icd10 icd10Data = icd10Codes.get(i);
			System.out.println(icd10Data.toString());
		}


	}

	public RowsCreatedOrUpdated getIcd10Details(List<String> prunedIcd10CodeList,RowsCreatedOrUpdated data)
			throws Exception {
			String response = "";
			int retryCount = 100;
			for (int j = 0; j < prunedIcd10CodeList.size(); j++) {
				String icdCode = prunedIcd10CodeList.get(j);
				String token = getToken();
				// System.out.println("Token : \n" + token);
				try {
					response = getURI(token, IConstants.ICD_10_CODE_INDIVIDUAL + icdCode, "GET");
				} catch (FileNotFoundException fnfe) {
					// eat it
					continue;
				}catch(ConnectException ce) {
					System.out.println("Connect Exception:Loop Counter="+j+" Retrying....");
					Thread.sleep(1000 * 60);
					j = j - 1;
					continue;
				}
			}
			return data;
		}


	public static String padString(Integer i) {
		String secondThirdChars = i.toString();
		if (secondThirdChars.length() == 1) {
			secondThirdChars = "0" +secondThirdChars;
		}
		return secondThirdChars;
	}


	/**
	 *
	 * @return The parent foundation 3 character code
	 */
	public static List<String> getParentFoundationsList() {
		List<String> firstCharList =  Arrays.asList(IConstants.ICD10FIRSTCHAR);
		List<String> parentFoundationList = new ArrayList<String>();

		String threeCharCode = "";
		for (int i = 0; i < firstCharList.size(); i++) {
			String firstChar = firstCharList.get(i);
			String secondThirdChars = "";
			for (int j = 0; j < 100; j++) {
				secondThirdChars = padString(new Integer(j));
				threeCharCode = firstChar + secondThirdChars;
				parentFoundationList.add(threeCharCode);
			}

		}
		return(parentFoundationList);
	}


	// access ICD API
		private String getURI(String token, String uri, String httpAction) throws Exception {

			//System.out.println("Getting URI...:"+uri);

			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(httpAction);

			// HTTP header fields to set
			con.setRequestProperty("Authorization", "Bearer "+token);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Accept-Language", "en");
			con.setRequestProperty("API-Version", "v2");
			con.setConnectTimeout(0);
			con.setReadTimeout(0);
			// response
			int responseCode = con.getResponseCode();
			//System.out.println("URI Response Code : " + responseCode + "\n");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return response.toString();
		}

	/**
	 *
	 * @return The token string
	 * @throws Exception
	 */
	private String getToken() throws Exception {

		System.out.println("Getting token...");

		URL url = new URL(IConstants.ICD_TOKEN_ENDPOINT);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		// set parameters to post
		String urlParameters =
        		"client_id=" + URLEncoder.encode(IConstants.ICD_CODE_CLIENT_ID, "UTF-8") +
        		"&client_secret=" + URLEncoder.encode(IConstants.ICD_CODE_CLIENT_SECRET, "UTF-8") +
			"&scope=" + URLEncoder.encode(IConstants.ICD_CODE_SCOPE, "UTF-8") +
			"&grant_type=" + URLEncoder.encode(IConstants.ICD_CODE_GRANT_TYPE, "UTF-8");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		// response
		int responseCode = con.getResponseCode();
		System.out.println("Token Response Code : " + responseCode + "\n");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// parse JSON response
		JSONObject jsonObj = new JSONObject(response.toString());
		return jsonObj.getString("access_token");
	}

	/**
	 * Method to create 5th character of IICD11 with "." plus 0-9, A-Z
	 * @param prunedList - the pruned list of codes
	 * @param icdDotCodes - an empty list
	 * @return
	 */
	public static List<String> getICD11DotCodesFifthChar(List<String> prunedList,
			List<String> icdDotCodes){

		for (int i = 0; i < prunedList.size(); i++) {
			String icdPruneCode = prunedList.get(i);
			for (int j = 0; j < IConstants.fifthChar.size(); j++) {
				String code = icdPruneCode +"."+ IConstants.fifthChar.get(j);
				//System.out.println(code);
				icdDotCodes.add(code);
			}
		}

		return icdDotCodes;
	}

	/**
	 * Method to create 5th character of IICD11 with "." plus 0-9, A-Z
	 * @param prunedList - the pruned list of codes
	 * @param icdDotCodes - an empty list
	 * @return
	 */
	public static List<String> getICD10DotCodesSixthChar(List<String> prunedList,
			List<String> icdDotCodes){

		for (int i = 0; i < prunedList.size(); i++) {
			String icdPruneCode = prunedList.get(i);
			for (int j = 0; j < IConstants.sixthChar.size(); j++) {
				String code = icdPruneCode + IConstants.sixthChar.get(j);
				//System.out.println(code);
				icdDotCodes.add(code);
			}
		}

		return icdDotCodes;
	}

}

