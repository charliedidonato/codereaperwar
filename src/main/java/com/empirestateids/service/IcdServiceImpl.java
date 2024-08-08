package com.empirestateids.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.empirestateids.common.IConstants;
import com.empirestateids.dao.Icd10Mapper;
import com.empirestateids.dao.Icd11Mapper;
import com.empirestateids.domain.Icd10;
import com.empirestateids.domain.Icd11;
import com.empirestateids.domain.RowsCreatedOrUpdated;
import com.empirestateids.domain.UploadStatus;
import com.empirestateids.security.UserInfo;

/**
 *
 * @author Charles DiDonato
 *
 *
 */
@Component
@Service("IcdService")
public class IcdServiceImpl implements IcdService {

	static Logger logger = LogManager.getLogger(IcdServiceImpl.class);

	@Autowired
	private Icd10Mapper icd10Mapper;

	@Autowired
	private Icd11Mapper icd11Mapper;


	public UploadStatus uploadStoreAddendumIcd10File(String fileType, File targetFile,
			String versionId, byte[] cvsByteArray, UserInfo userInfo)
			throws FileNotFoundException, Exception {

		UploadStatus status = new UploadStatus();
		String errors = "File Details:";
		File inputFile = targetFile;
		if (!inputFile.canWrite()) {
			throw new Exception("Unable to write file to disk:"+inputFile.getAbsolutePath());
		}else {
			FileUtils.writeByteArrayToFile(inputFile, cvsByteArray);
		}

		List<Icd10> icd10Codes = new ArrayList<Icd10>();
		FileReader fr = new FileReader(inputFile);
		BufferedReader buf = new BufferedReader(fr);
		String line = "";
		int lineNumber = 0;
		while ( (line=buf.readLine())!=null) {
			lineNumber++;
			if (line.length() < 7) {
				continue;
			}
			if( (!line.startsWith("Add"))||
				(!line.startsWith("Rev"))||
			    (!line.startsWith("Del"))||
			    (!line.startsWith(" ")) ){
				continue;
			}
			String icdStatus = line.substring(0,13);
			if(icdStatus==null || icdStatus.length() < 3) {
				throw new Exception("ICD 10 AddendumFile:"+inputFile.getName()+" is corrupted on line #:"+lineNumber);
			}
			icdStatus = icdStatus.trim();
			icdStatus = icdStatus.replaceAll(":", "");
			System.out.println("ICD STATUS:"+icdStatus);

			String code = line.substring(13,20);
			if(code==null || code.length() < 3) {
				throw new Exception("ICD 10 Addendum File:"+inputFile.getName()+" is corrupted on line #:"+lineNumber);
			}
			code = code.trim();
			code = code.substring(0, 3)+"."+code.substring(3);
			String desc = "";
			if("ICD10_CM_ADDENDUM".equalsIgnoreCase(fileType)) {
				desc = line.substring(22);
			}else {  //assume PCS file addendum
				desc = line.substring(82);
			}
			desc = desc.trim();
			Icd10 dbIcd10 = icd10Mapper.selectByPrimaryKey(code);
			Icd10 icd10 = new Icd10();
			if (dbIcd10 != null) {
				icd10.setCode(code);
				icd10.setLateststatus(icdStatus);
				icd10.setDescription(desc);
				icd10.setUpdatedby(userInfo.getUsername());
				icd10.setUpdated(new Date());
				logger.error(icd10.toString());
				int updateCnt = icd10Mapper.updateByPrimaryKeySelective(icd10);
				icd10Codes.add(icd10);
			} else {
				//add it
				icd10.setCode(code);
				icd10.setLateststatus(icdStatus);
				icd10.setDescription(desc);
				icd10.setCreatedby(userInfo.getUsername());
				icd10.setCreated(new Date());
				int insertCount = icd10Mapper.insertSelective(icd10);
				logger.error(icd10.toString());
				icd10Codes.add(icd10);
			}


		}

		errors = errors + "Updated "+icd10Codes.size()+" ICD10 codes from Addendum File."+
				" Processed "+lineNumber+ " of rows from file:"+inputFile.getName();
		status.setRowCount(icd10Codes.size());
		status.setErrors(errors);

		return status;
	}


	@Override
	public UploadStatus uploadStoreIcd10File(String fileType, File targetFile,
			String versionId, byte[] cvsByteArray, UserInfo userInfo)
		throws FileNotFoundException, Exception {

		UploadStatus status = new UploadStatus();
		String errors = "File Details:";
		File inputFile = targetFile;
		if (!inputFile.canWrite()) {
			throw new Exception("Unable to write file to disk:"+inputFile.getAbsolutePath());
		}else {
			FileUtils.writeByteArrayToFile(inputFile, cvsByteArray);
		}
		List<Icd10> icd10Codes = new ArrayList<Icd10>();
		FileReader fr = new FileReader(inputFile);
		BufferedReader buf = new BufferedReader(fr);
		String line = "";
		int lineNumber = 0;
		while ( (line=buf.readLine())!=null) {
			lineNumber++;
			if (line.length() < 7) {
				continue;
			}

			String code = line.substring(0,8);
			if(code==null || code.length() < 3) {
				throw new Exception("ICD 10 File:"+inputFile.getName()+" is corrupted on line #:"+lineNumber);
			}
			code = code.trim();
			code = code.substring(0, 3)+"."+code.substring(3);
			String descFile = line.substring(8);
			Icd10 dbIcd10 = icd10Mapper.selectByPrimaryKey(code);
			Icd10 icd10 = new Icd10();
			if (dbIcd10 == null) {
				icd10.setCode(code);
				icd10.setDescription(descFile);
				icd10.setCreatedby(userInfo.getUsername());
				icd10.setCreated(new Date());
				int insertCount = icd10Mapper.insertSelective(icd10);
				icd10Codes.add(icd10);
			} else {
				icd10.setCode(code);
				icd10.setDescription(descFile);
				icd10.setUpdatedby(userInfo.getUsername());
				icd10.setUpdated(new Date());
				int updateCount = icd10Mapper.updateByPrimaryKeySelective(icd10);
				icd10Codes.add(icd10);
			}
		}

		errors = errors + " Inserted/Updated "+icd10Codes.size()+" ICD10 codes."+
				" Processed "+lineNumber+ " of rows from file:"+inputFile.getName();
		status.setRowCount(icd10Codes.size());
		status.setErrors(errors);
		return status;
	}

	public UploadStatus uploadStoreAddendumIcd10File(String fileType, String versionId, byte[] cvsByteArray, UserInfo userInfo)
			throws FileNotFoundException, Exception {
		UploadStatus status = new UploadStatus();



		return status;
	}


//	@Override
//	//@Transactional(propagation = Propagation.REQUIRED)
//	public UploadStatus insertOrUpdateIcd10Codes(RowsCreatedOrUpdated data, String creatorUpdatedId)
//			throws MalformedURLException, IOException, SQLIntegrityConstraintViolationException, Exception {
//
//		String errors = new String("API Details:");
//		UploadStatus status = new UploadStatus();
//		List<String> dbCodeList = icd10Mapper.selectAllIcd10Codes();
//		logger.error("DB Code List Size:" + dbCodeList.size());
//		List<String> prunedUrlList = new ArrayList<String>();
//		String token = getToken();
//		logger.error("Token:" + token);
//		for (int j = 0; j < dbCodeList.size(); j++) {
//			String code = dbCodeList.get(j);
//			try {
//				String response = getURI(token, IConstants.ICD_10_CODE_INDIVIDUAL + code, "GET");
//				// System.out.println("Single foundation:"+code+"\n" + response);
//				JSONObject jsonObj = new JSONObject(response.toString());
//				try {
//					JSONArray children = jsonObj.getJSONArray("child");
//					for (int k = 0; k < children.length(); k++) {
//						String uri = children.getString(k);
//						uri = uri.replaceAll("http", "https");
//						// The API returned 200..........add that URL to the list of the children URLS.
//						prunedUrlList.add(uri);
//					}
//				} catch (Exception ex) {
//
//					continue;
//				}
//			} catch (FileNotFoundException fnfe) {
//				// 404 from API........
//				errors = errors + " code:" + code + " NF";
//				continue;
//			} catch (ConnectException ce) {
//				logger.error("ConnectException:" + ce.getMessage());
//				j = j - 1;
//				Thread.sleep(60*1000);
//				continue;
//			} catch(JSONException jse) {
//				//no hit from code..........continue on
//				continue;
//			}
//		}
//		// now get the code details for each of the pruned URIs in the List
//		logger.error("URI List size:" + prunedUrlList.size());
//		// System.out.println("URIs:"+prunedUrlList.toString());
//		for (int i = 0; i < prunedUrlList.size(); i++) {
//			String uri = prunedUrlList.get(i);
//			String code = "";
//			String response = "";
//			try {
//				response = getURI(token, uri, "GET");
//			} catch (ConnectException ce) {
//				logger.error("ConnectException:" + ce.getMessage());
//				i = i - 1;
//				Thread.sleep(60*1000);
//				continue;
//			}
//			JSONObject codeData = new JSONObject(response);
//			String label = "";
//			code = codeData.getString("code");
//			String classKind = codeData.getString("classKind");
//			JSONObject title = codeData.getJSONObject("title");
//			try {
//				JSONArray inclusion = codeData.getJSONArray("inclusion");
//				JSONObject inclusionFirst = (JSONObject) inclusion.get(0);
//				JSONObject labelObj = inclusionFirst.getJSONObject("label");
//				label = labelObj.getString("@value");
//			} catch (Exception e) {
//				// eat it
//			}
//			String desc = title.getString("@value");
//			Icd10 icd = new Icd10();
//
//			Icd10 codeDb = icd10Mapper.selectByPrimaryKey(code);
//			if (codeDb == null) { // insert
//				icd.setCode(code);
//				icd.setDescription(desc);
//				Date now = new Date();
//				icd.setCreated(now);
//				icd.setCreatedby(creatorUpdatedId);
//				int insertCnt = icd10Mapper.insertSelective(icd);
//				data = data.addCreated(data);
//			} else {
//				// update
//				Date now = new Date();
//				icd.setUpdated(now);
//				icd.setUpdatedby(creatorUpdatedId);
//				int updateCnt = icd10Mapper.updateByPrimaryKeySelective(icd);
//				data = data.addUpdated(data);
//			}
//			logger.error(prunedUrlList.toString());
//		}
//
//		status.setRowCount(data.getRowsCreated() + data.getRowsUpdated());
//		status.setErrors(errors);
//		return status;
//	}


//	@Override
//	//@Transactional(propagation = Propagation.REQUIRED)
//	public RowsCreatedOrUpdated insertOrUpdateIcd11Codes(RowsCreatedOrUpdated data,	String creatorUpdatedId)
//			throws MalformedURLException, IOException, SQLIntegrityConstraintViolationException, Exception {
//
//		int rowsDeleted = icd11Mapper.deleteAll();
//		logger.error("Deleted "+rowsDeleted+ " row(s) from ICD11.");
//		String token = getToken();
//		logger.error("Token:" + token);
//		List<String> icd11CodeList = new ArrayList<String>();
//		StringBuffer code = new StringBuffer();
//		for (int i = 0; i < IConstants.firstChar.size(); i++) {
//			String first = IConstants.firstChar.get(i);
//			for (int j = 0; j < IConstants.secondChar.size(); j++) {
//				String second = IConstants.secondChar.get(j);
//				for (int k = 0; k < IConstants.thirdChar.size(); k++) {
//					String third = IConstants.thirdChar.get(k);
//					for (int m = 0; m < IConstants.fourthChar.size(); m++) {
//						String fourth = IConstants.fourthChar.get(m);
//						code.append(first + second + third + fourth);
//						icd11CodeList.add(code.toString());
//						code = new StringBuffer();
//					} //for int = m
//				} //for int = k
//			} //for int = j
//		} //for int = i
//
//		List<String> prunedIcd11CodeList1 = new ArrayList<String>();
//		List<String> prunedIcd11CodeList2 = new ArrayList<String>();
//		for (int i = 0; i < icd11CodeList.size(); i++) {
//			String icd = icd11CodeList.get(i);
//			if (icd.startsWith("1")) {
//				Matcher matcher1 = IConstants.firstChapter.matcher(icd);
//				if (matcher1.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("2")) {
//				Matcher matcher2 = IConstants.secondChapter.matcher(icd);
//				if (matcher2.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("3")) {
//				Matcher matcher3 = IConstants.thirdChapter.matcher(icd);
//				if (matcher3.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("4")) {
//				Matcher matcher4 = IConstants.fourthChapter.matcher(icd);
//				if (matcher4.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("5")) {
//				Matcher matcher5 = IConstants.fifthChapter.matcher(icd);
//				if (matcher5.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("6")) {
//				Matcher matcher6 = IConstants.sixthChapter.matcher(icd);
//				if (matcher6.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("7")) {
//				Matcher matcher7 = IConstants.seventhChapter.matcher(icd);
//				if (matcher7.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("8")) {
//				Matcher matcher8 = IConstants.eighthChapter.matcher(icd);
//				if (matcher8.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("9")) {
//				Matcher matcher9 = IConstants.ninethChapter.matcher(icd);
//				if (matcher9.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("A")) {
//				Matcher matcher10 = IConstants.tenthChapter.matcher(icd);
//				if (matcher10.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("B")) {
//				Matcher matcher11 = IConstants.eleventhChapter.matcher(icd);
//				if (matcher11.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("C")) {
//				Matcher matcher12 = IConstants.twelthChapter.matcher(icd);
//				if (matcher12.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("D")) {
//				Matcher matcher13 = IConstants.thirteenthChapter.matcher(icd);
//				if (matcher13.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("E")) {
//				Matcher matcher14 = IConstants.fourteenthChapter.matcher(icd);
//				if (matcher14.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("F")) {
//				Matcher matcher15 = IConstants.fifteenthChapter.matcher(icd);
//				if (matcher15.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("G")) {
//				Matcher matcher16 = IConstants.sixteenthChapter.matcher(icd);
//				if (matcher16.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("H")) {
//				Matcher matcher17 = IConstants.seventeenthChapter.matcher(icd);
//				if (matcher17.matches()) {
//					prunedIcd11CodeList1.add(icd);
//				}
//			} else if (icd.startsWith("J")) {
//				Matcher matcher18 = IConstants.eighteenthChapter.matcher(icd);
//				if (matcher18.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("K")) {
//				Matcher matcher19 = IConstants.nineteenthChapter.matcher(icd);
//				if (matcher19.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("L")) {
//				Matcher matcher20 = IConstants.twentienthChapter.matcher(icd);
//				if (matcher20.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("M")) {
//				Matcher matcher21 = IConstants.twentyfirstChapter.matcher(icd);
//				if (matcher21.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("N")) {
//				Matcher matcher22 = IConstants.twentysecondChapter.matcher(icd);
//				if (matcher22.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("P")) {
//				Matcher matcher23 = IConstants.twentythirdChapter.matcher(icd);
//				if (matcher23.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("Q")) {
//				Matcher matcher24 = IConstants.twentyfourthChapter.matcher(icd);
//				if (matcher24.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("R")) {
//				Matcher matcher25 = IConstants.twentyfifthChapter.matcher(icd);
//				if (matcher25.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("S")) {
//				Matcher matcher26 = IConstants.twentysixthChapter.matcher(icd);
//				if (matcher26.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("V")) {
//				Matcher matcher27 = IConstants.twentyseventhChapter.matcher(icd);
//				if (matcher27.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else if (icd.startsWith("X")) {
//				Matcher matcher28 = IConstants.twentyeighthChapter.matcher(icd);
//				if (matcher28.matches()) {
//					prunedIcd11CodeList2.add(icd);
//				}
//			} else {
//				continue;
//			}
//
//		}
//
//
//		logger.error("List1 Size:" + prunedIcd11CodeList1.size());
//
//		data = getIcd11Details(prunedIcd11CodeList1,data);
//
//
//		return data;
//	}

	public RowsCreatedOrUpdated getIcd11Details(List<String> prunedIcd11CodeList,RowsCreatedOrUpdated data)
			throws Exception {
			String response = "";
			int retryCount = 100;
			for (int j = 0; j < prunedIcd11CodeList.size(); j++) {
				String icdCode = prunedIcd11CodeList.get(j);
				String token = getToken();
				// System.out.println("Token : \n" + token);
				try {
					response = getURI(token, IConstants.ICD_CODE_11_SINGLE_ENTITY_MMS + icdCode
							+ IConstants.ICD_CODE_11_SINGLE_ENTITY_MMS_TRAILER, "GET");
				} catch (FileNotFoundException fnfe) {
					// eat it
					continue;
				}catch(ConnectException ce) {
					logger.error("Connect Exception:Loop Counter="+j+" Retrying....");
					Thread.sleep(1000 * 60);
					j = j - 1;
					continue;
				}
				JSONObject root = new JSONObject(response);
				String stemId = root.getString("stemId");
				stemId = stemId.replaceAll("http", "https");
				String details = getURI(token, stemId, "GET");
				JSONObject codeDetail = new JSONObject(details);
				JSONObject title = codeDetail.getJSONObject("title");
				String value = title.getString("@value");
				String classKind = codeDetail.getString("classKind");
				String source = "";
				JSONObject longDef = null;
				JSONObject definition = null;
				String def = "-";
				try {
					source = codeDetail.getString("source");
					if(source!=null) {
						source = URLEncoder.encode(source,"UTF-8");
					}
				}catch (JSONException je) {
					//eat it
				}
				try {
					definition = codeDetail.getJSONObject("definition");
				}catch (JSONException je) {
					//eat it
				}
				if(definition!=null) {
					def = definition.getString("@value");
				}

				Icd11 icd11 = new Icd11();
				icd11.setCode(icdCode);
				if(def!=null) {
					icd11.setDefinition(def);
				}

				icd11.setTitle(value);
				icd11.setSource(source);
				icd11.setClasskind(classKind);
				logger.error(icd11);
				icd11Mapper.insertSelective(icd11);
				data.addCreated(data);
			}

			return data;
		}

	/**
	 *
	 * @return The parent foundation 3 character code
	 */
	public List<String> getParentFoundationsList() {
		List<String> firstCharList = Arrays.asList(IConstants.ICD10FIRSTCHAR);
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
		return (parentFoundationList);
	}

	public static String padString(Integer i) {
		String secondThirdChars = i.toString();
		if (secondThirdChars.length() == 1) {
			secondThirdChars = "0" + secondThirdChars;
		}
		return secondThirdChars;
	}

	public String getURI(String token, String uri, String httpAction) throws Exception {

		// System.out.println("Getting URI...:"+uri);

		URL url = new URL(uri);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod(httpAction);
		con.setConnectTimeout(0);
		con.setReadTimeout(0);

		// HTTP header fields to set
		con.setRequestProperty("Authorization", "Bearer " + token);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en");
		con.setRequestProperty("API-Version", "v2");

		// response
		int responseCode = con.getResponseCode();
		// System.out.println("URI Response Code : " + responseCode + "\n");
		StringBuffer response = new StringBuffer("");
		if (responseCode==200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
		}
		return response.toString();
	}

	/**
	 *
	 * @return The token string
	 * @throws Exception
	 */
	public String getToken() throws Exception {

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

}
