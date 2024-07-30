package com.empirestateids.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.empirestateids.domain.UploadStatus;
import com.empirestateids.security.UserInfo;



/**
 *
 * @author Charles DiDonato Empire State IDS
 *
 */
public interface IcdService {

//  will have to do this from an INPUT file
//	public UploadStatus insertOrUpdateIcd10Codes(RowsCreatedOrUpdated data, String creatorUpdatedId)
//	    throws MalformedURLException, IOException, SQLIntegrityConstraintViolationException, Exception;

//  will have to do this from an INPUT file
//	public RowsCreatedOrUpdated insertOrUpdateIcd11Codes(RowsCreatedOrUpdated data, String creatorUpdatedId)
//		    throws MalformedURLException, IOException, SQLIntegrityConstraintViolationException, Exception;

	public List<String> getParentFoundationsList();

	public String getURI(String token, String uri, String httpAction) throws Exception;

	public UploadStatus uploadStoreIcd10File(String fileType, File targetFile,
			String versionId, byte[] cvsByteArray, UserInfo userInfo)
			throws FileNotFoundException, Exception;

	public UploadStatus uploadStoreAddendumIcd10File(String fileType, File targetFile,
			String versionId, byte[] cvsByteArray, UserInfo userInfo)
			throws FileNotFoundException, Exception;

	public String getToken() throws Exception;


}
