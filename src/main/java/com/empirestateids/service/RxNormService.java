package com.empirestateids.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;

import com.empirestateids.domain.RowsCreatedOrUpdated;



/**
 *
 * @author Charles DiDonato
 *
 */
public interface RxNormService {

	public RowsCreatedOrUpdated insertOrUpdateRxNormCodes(String creatorUpdatedId)
			throws MalformedURLException, IOException, Exception;

	public JSONArray getAllNDCCodes(URL url)
			throws MalformedURLException, IOException, Exception;

}
