package com.empirestateids.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empirestateids.common.IConstants;
import com.empirestateids.dao.RxNormCodesMapper;
import com.empirestateids.domain.RowsCreatedOrUpdated;
import com.empirestateids.domain.RxNormCodes;

/**
 *
 * @author Charles DiDonato
 *
 */
@Service("RxNormService")
public class RxNormServiceImpl implements RxNormService {

	static Logger logger = LogManager.getLogger(RxNormService.class);

	@Autowired
	private RxNormCodesMapper rxNormMapper;

	@Override
	public RowsCreatedOrUpdated insertOrUpdateRxNormCodes(String creatorUpdaterId)
			throws MalformedURLException, IOException, SQLIntegrityConstraintViolationException, Exception {
		URL url = new URL(IConstants.NLM_DOMAIN_REST+IConstants.NLM_DOMAIN_REST_ALL_NDC_EXT);
		HttpURLConnection httpUrlConDetail = null;
		RowsCreatedOrUpdated data = new RowsCreatedOrUpdated();

		String host = url.getHost();
		if (!host.startsWith(IConstants.NLM_DOMAIN_REST_HOST)) {
			logger.error("Illegal URL:HOST:" + url.getHost() + " PATH:" + url.getPath());
			throw new IllegalArgumentException("Incorrect URL into " + this.getClass().getMethods().toString()
					+ " URL should be to the NLM as follows:" + IConstants.NLM_DOMAIN_REST);
		}

		JSONArray codes = getAllNDCCodes(url);
		List<String> ndcCodes = getListFromJSONArray(codes);
		logger.error("NDC Codes:"+ndcCodes.toString());
		URL detaiUrl = null;
		for (int i = 0; i < ndcCodes.size(); i++) {
			String oneCode = ndcCodes.get(i);
			detaiUrl = new URL(IConstants.NLM_DOMAIN_REST + IConstants.NLM_DOMAIN_REST_NDC_DETAIL+oneCode);
			try {
				httpUrlConDetail = (HttpURLConnection) detaiUrl.openConnection();
				httpUrlConDetail.setDoOutput(true);
				httpUrlConDetail.setRequestMethod("GET");
				httpUrlConDetail.setRequestProperty("User-Agent", IConstants.CODEREAPER_USERAGENT);
			} catch (ConnectException ce) {
				logger.error("Connect Exception:Loop Counter=" + i + " Retrying....");
				Thread.sleep(1000 * 60);
				i = i - 1;
				continue;
			} catch (Exception ex) {
				logger.error("Exception:Loop Counter=" + i + " CODE:" + oneCode);
				throw ex;
			}

			int responseCodeDetail = httpUrlConDetail.getResponseCode();
			String inputLine = "";
			if (responseCodeDetail == HttpURLConnection.HTTP_OK) { // success
				BufferedReader inDetail = new BufferedReader(new InputStreamReader(httpUrlConDetail.getInputStream()));
				StringBuffer responseDetail = new StringBuffer();

				while ((inputLine = inDetail.readLine()) != null) {
					responseDetail.append(inputLine);
				}
				inDetail.close();

				JSONObject root = new JSONObject(responseDetail.toString());
				// print result
				// System.out.println("Detail Content length: "
				// +responseDetail.toString().length());
				JSONObject ndcInfoList = root.getJSONObject("ndcInfoList");
				JSONArray array = (JSONArray) ndcInfoList.get("ndcInfo");
				for (int j = 0; j < array.length(); j++) {
					Date currentDate = new Date();
					JSONObject oneObj = array.getJSONObject(j);
					String ndc11 = oneObj.getString("ndc11");
					String status = oneObj.getString("status");
					String rxcui = oneObj.getString("rxcui");
					String conceptName = oneObj.getString("conceptName");
					String conceptStatus = oneObj.getString("conceptStatus");
					String tty = oneObj.getString("tty");

					RxNormCodes one = new RxNormCodes();
					boolean keepDbValues = false;
					RxNormCodes allCols = rxNormMapper.selectIfExists(new Long(ndc11));
					if (allCols != null) {
						if (keepDbValues) {
							// UPDATE as there is a row with the same LONG NDC Code
							// KEEP Values from RxNormCodes returned object
							one.setRxCodePk(allCols.getRxCodePk());
							one.setNdc11Code(allCols.getNdc11Code());
							one.setNdc11CodeString(allCols.getNdc11CodeString());
							one.setConceptName(allCols.getConceptName());
							one.setConceptStatus(allCols.getConceptStatus());
							one.setTty(allCols.getTty());
							one.setStatus(allCols.getStatus());
							one.setTty(allCols.getTty());
							one.setRxcui(allCols.getRxcui());
							one.setUpdatedById(creatorUpdaterId);
							one.setUpdatedDate(new Date());
							one.setCreatedDate(allCols.getCreatedDate());
							one.setCreatedById(allCols.getCreatedById());
						} else {
							// UPDATE as there is a row with the same LONG NDC Code
							// Take Values from RxNormCodes API call
							one.setRxCodePk(allCols.getRxCodePk());
							one.setNdc11Code(allCols.getNdc11Code());
							one.setNdc11CodeString(allCols.getNdc11CodeString());
							one.setConceptName(conceptName);
							one.setConceptStatus(conceptStatus);
							one.setTty(tty);
							one.setStatus(status);
							one.setTty(tty);
							one.setRxcui(rxcui);
							one.setUpdatedById(creatorUpdaterId);
							one.setUpdatedDate(new Date());
							one.setCreatedDate(allCols.getCreatedDate());
							one.setCreatedById(allCols.getCreatedById());
						}

						int updateCnt = rxNormMapper.updateByPrimaryKey(one);
						data = data.addUpdated(data);
					} else {
						// INSERT with ndc11 Unique code
						one.setRxCodePk(0L);
						one.setNdc11Code(new Long(ndc11));
						one.setNdc11CodeString(ndc11);
						one.setConceptName(conceptName);
						one.setConceptStatus(conceptStatus);
						one.setTty(tty);
						one.setStatus(status);
						one.setConceptName(conceptName);
						one.setConceptStatus(conceptStatus);
						one.setTty(tty);
						one.setRxcui(rxcui);
						one.setCreatedDate(currentDate);
						one.setCreatedById(creatorUpdaterId);
						int insertCount = rxNormMapper.insert(one);
						data = data.addCreated(data);
					}

				} // end FOR LOOP j
				httpUrlConDetail.disconnect();
			} else {
				String message = "ERROR:Http Response Code:" + responseCodeDetail + "\n URL:" + detaiUrl.toURI();
				logger.error(message);
				throw new Exception(message);
			}
		}

		return data;
	}



	/**
	 * this method makes a REST call to get all of the NDC codes
	 *
	 * @param url
	 * @param userAgent
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSONArray getAllNDCCodes(URL url) throws MalformedURLException, IOException, Exception {

		JSONArray data = null;
		URL urlObj = url;
		HttpURLConnection httpUrlCon = (HttpURLConnection) urlObj.openConnection();
		httpUrlCon.setDoOutput(true);
		httpUrlCon.setRequestMethod("GET");
		httpUrlCon.setRequestProperty("User-Agent", IConstants.CODEREAPER_USERAGENT);

		int responseCode = httpUrlCon.getResponseCode();
		logger.info("getAllNDCCodes():Server response:" + responseCode+ "\n URI:"+url.toString());

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlCon.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject obj = new JSONObject(response.toString());
			logger.error(obj.toString());
			JSONObject codesArray = (JSONObject) obj.get("ndcList");
			data = (JSONArray) codesArray.get("ndc");
			for (int i = 0; i < data.length(); i++) {
				RxNormCodes oneCode = new RxNormCodes();

			}

		} else {
			String message = "GET request error: Response code:" + responseCode + " URL" + url.toURI();
			logger.error(message);
			throw new Exception(message);
		}

		return data;
	}

	/**
	 * Method to get ALL of the NDC codes regardless of status
	 *
	 * @param codes
	 * @return
	 */
	public static List<String> getCodeListFromJSONArray(JSONArray codes) {
		List<String> ndcCodes = new ArrayList<String>();

		if (codes != null) {
			int len = codes.length();
			for (int i = 0; i < len; i++) {
				ndcCodes.add(codes.get(i).toString());
			}
		}

		return ndcCodes;
	}

//	public static JSONObject getNDCCodeDetails(String ndcCode, String url, String userAgent)
//			throws MalformedURLException, IOException, ConnectException, Exception {
//		JSONObject property = new JSONObject();
//		HttpURLConnection httpUrlCon = null;
//
//		URL urlObj = new URL(url + ndcCode);
//		httpUrlCon = (HttpURLConnection) urlObj.openConnection();
//		httpUrlCon.setDoOutput(true);
//		httpUrlCon.setRequestMethod("GET");
//		httpUrlCon.setRequestProperty("User-Agent", userAgent);
//
//		int responseCode = httpUrlCon.getResponseCode();
//		// System.out.println("Server returned response code " + responseCode);
//
//		if (responseCode == HttpURLConnection.HTTP_OK) { // success
//			BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlCon.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//			in.close();
//
//			// print result
//			System.out.println("Content length: " + response.toString().length());
//			property = new JSONObject(response.toString());
//
//		} else {
//			System.out.println("GET request did not work.");
//			System.out.println(url + ndcCode);
//		}
//
//		return property;
//	}

	public static JSONArray getAllNDCCodes(String url)
			throws MalformedURLException, IOException, Exception {

		JSONArray data = null;
		URL urlObj = new URL(url);
		HttpURLConnection httpUrlCon = (HttpURLConnection) urlObj.openConnection();
		httpUrlCon.setDoOutput(true);
		httpUrlCon.setConnectTimeout(0);
		httpUrlCon.setReadTimeout(0);
		httpUrlCon.setRequestMethod("GET");
		httpUrlCon.setRequestProperty("User-Agent", IConstants.CODEREAPER_USERAGENT);

		int responseCode = httpUrlCon.getResponseCode();
		//logger.debug("getAllNDCCodes():Server returned response code " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(
					new InputStreamReader(httpUrlCon.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();


			// print result
			//logger.debug("Content length: " +response.toString().length());
			JSONObject obj  = new JSONObject(response.toString());
			//logger.error(obj);
			JSONObject codesArray = (JSONObject) obj.get("ndcList");

			data = (JSONArray) codesArray.get("ndc");

		} else {
			System.out.println("GET request did not work.");
		}

		return data;
	}

	/**
	 * Method to get ALL of the NDC codes regardless of status
	 * @param codes
	 * @return
	 */
	public static List<String> getListFromJSONArray(JSONArray codes) {
		List<String> ndcCodes = new ArrayList<String>();

		if (codes != null) {
		   int len = codes.length();
		   for (int i=0;i<len;i++){
			   ndcCodes.add(codes.get(i).toString());
		   }
		}

		return ndcCodes;
	}
}
