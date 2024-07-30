package com.empirestateids.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.empirestateids.domain.CVXCatalog;
import com.empirestateids.domain.RowsCreatedOrUpdated;



/**
 *
 * @author Charles DiDonato Empire State IDS
 *
 */
public interface CDCCvxService {

	public RowsCreatedOrUpdated insertOrUpdateCvxCodes(List<CVXCatalog> catalogs, RowsCreatedOrUpdated data, String creatorUpdatedId)
	    throws MalformedURLException, IOException, SQLIntegrityConstraintViolationException, Exception;

	public List<CVXCatalog> getCVXCatalogs(URL url) throws IllegalArgumentException,Exception;

	public RowsCreatedOrUpdated storeCVXCatalogs(List<CVXCatalog> catList,String creatorUpdated)
			throws IllegalArgumentException,SQLException, Exception;

	public String getSingleCatalogOfVaccines(URL url) throws Exception;

//	public CvxAttributes setFields(CvxAttributes cvx, String name,String value)
//			throws IllegalArgumentException, Exception;

//	public CvxAttributes getCVXAttributes(AttributesXML xml)
//			throws IllegalArgumentException, Exception;

}
