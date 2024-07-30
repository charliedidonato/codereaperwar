package com.empirestateids.service;

import com.empirestateids.domain.UploadStatus;
import com.empirestateids.security.UserInfo;

/**
 * 
 * @author Charles DiDonato
 *
 */
public interface CsvParserService {

	public UploadStatus uploadLoincMasterFile(String fileType, String versionId, byte[] csvByteArray, UserInfo userInfo);
	

}
