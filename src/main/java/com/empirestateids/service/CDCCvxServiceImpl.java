package com.empirestateids.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.empirestateids.common.IConstants;
import com.empirestateids.dao.CvxCatalogsMapper;
import com.empirestateids.dao.CvxCodesMapper;
import com.empirestateids.domain.AliasLanguageISOCode;
import com.empirestateids.domain.AliasTypeMnemonic;
import com.empirestateids.domain.CVXCatalog;
import com.empirestateids.domain.CVXCatalogConverter;
import com.empirestateids.domain.CVXConverter;
import com.empirestateids.domain.CVXParent;
import com.empirestateids.domain.CatalogDisplayName;
import com.empirestateids.domain.CatalogMnemonic;
import com.empirestateids.domain.CvxCatalogs;
import com.empirestateids.domain.CvxCodes;
import com.empirestateids.domain.OriginalCatalogMnemonic;
import com.empirestateids.domain.RowsCreatedOrUpdated;
import com.empirestateids.domain.SearchResult;
import com.empirestateids.utls.UtilityMethods;

/**
 *
 * @author Charles DiDonato
 *
 *
 */
@Component
@Service("CDCCvxService")
public class CDCCvxServiceImpl implements CDCCvxService {

	static Logger logger = LogManager.getLogger(CDCCvxServiceImpl.class);

	@Autowired
	private CvxCodesMapper cvxParentMapper;

//	@Autowired
//	private CvxAttributesMapper cvxAttrMapper;

	@Autowired
	private CvxCatalogsMapper cvxCatalogsMapper;

	@Override
	public RowsCreatedOrUpdated insertOrUpdateCvxCodes(List<CVXCatalog> catalogs, RowsCreatedOrUpdated data,
			String creatorUpdatedId)
			throws MalformedURLException, IOException, SQLIntegrityConstraintViolationException, Exception {

		int deleteCnt = cvxParentMapper.deleteAll();
		logger.error("Deleted " + deleteCnt + " from cvx_parent.");
//		int deleteCount = cvxAttrMapper.deleteAll();
//		logger.error("Deleted " + deleteCount + " from cvx_attributes.");
		for (int i = 0; i < catalogs.size(); i++) {
			CVXCatalog oneCatalog = catalogs.get(i);
			UUID catalogUID = oneCatalog.getCatalogUID();
			// URL for getting the Details of a single vaccine catalog
			// "https://vaccinecodeset.cdc.gov/SymedicalCDCVCABProdRuntimeRestService/catalog/terms?maxResults=0&catalogIdentifier="
			// + the catalog ID
			String termURI = IConstants.CVX_CODE_CATALOG_TERM_URI + catalogUID;
			URL url = new URL(termURI);
			String json = null;
			try {
				json = getSingleCatalogOfVaccines(url);
			}catch (ConnectException ce) {
				logger.error("Connect Exception:"+ce.getMessage()+" Retrying....");
				i = i - 1;
				continue;
			}catch (Exception ex) {
				logger.error("Exception:"+ex.getMessage()+" Retrying....");
				i = i - 1;
				continue;
			}
//			ObjectMapper om = new ObjectMapper();

//			JsonNode jsonNode = om.readTree(json);

			// The entire list of SearchResults for a catalog
//			JsonNode nameNode = jsonNode.get("SearchResults");
			// the number of search results in the catalog
//			JsonNode countNode = jsonNode.get("TermSearchTotalCount");
//			int count = countNode.getIntValue();

			CVXParent cvxAllDataForCatalog = CVXConverter.fromJsonString(json);
			List<SearchResult> vaccines = cvxAllDataForCatalog.getSearchResults();
			for (int k = 0; k < vaccines.size(); k++) {

				SearchResult vac = vaccines.get(k);
				//logger.error("VACCINE:"+vac.toString());
				// the database model for the CVX_PARENT Table
				CvxCodes cvxCode = new CvxCodes();
				cvxCode.setCvxid(0L); // set key to 0 for AUTOINCREMENT
				cvxCode.setSearchhitdescription(vac.getSearchHitDescription());
				cvxCode.setSearchhitsourcecode(vac.getSearchHitSourceCode());
				Boolean isSearchAliasedBased = vac.getSearchHitIsAliasBased();
				if (isSearchAliasedBased != null) {
					cvxCode.setSearchhitisaliasbased(isSearchAliasedBased.toString());
				}

				UUID vacTermUUID = vac.getTermUID();
				if (vacTermUUID != null) {
					cvxCode.setTermuid(vacTermUUID.toString());
				}
				UUID vacCatUUID = vac.getTermCatalogUID();
				if (vacCatUUID != null) {
					cvxCode.setTermcataloguid(vacCatUUID.toString());
				}

				cvxCode.setTermsourcecode(vac.getTermSourceCode());
				cvxCode.setTermdescription(vac.getTermDescription());
				UUID aliasUID = vac.getAliasUID();
				if (aliasUID != null) {
					cvxCode.setAliasuid(aliasUID.toString());
				}

				cvxCode.setAliastypename(vac.getAliasTypeName().toValue());
				cvxCode.setAliaslanguagename(vac.getAliasLanguageName().toValue());
				Boolean isPreferred = vac.getIsPreferred();
				if (isPreferred != null) {
					cvxCode.setIspreferred(isPreferred.toString());
				}
				Boolean isRetired = vac.getIsRetired();
				if (isRetired != null) {
					cvxCode.setIsretired(isRetired.toString());
				}
				Boolean isLocalterm = vac.getIsLocalTerm();
				if (isLocalterm != null) {
					cvxCode.setIslocalterm(isLocalterm.toString());
				}
				UUID vacCatalogUID = vac.getCatalogUID();
				if (vacCatalogUID != null) {
					cvxCode.setCataloguid(vacCatalogUID.toString());
				}
				CatalogMnemonic cm = vac.getCatalogMnemonic();
				if (cm != null) {
					cvxCode.setCatalogmnemonic(cm.toValue());
				}
				OriginalCatalogMnemonic ocm = vac.getOriginalCatalogMnemonic();
				if (ocm != null) {
					cvxCode.setOriginalcatalogmnemonic(ocm.toValue());
				}
				CatalogDisplayName cmd = vac.getCatalogDisplayName();
				if (cmd != null) {
					cvxCode.setCatalogdisplayname(cmd.toValue());
				}

				Timestamp creationDate = UtilityMethods.getTimestampFormOffsetDateTime(vac.getCreationDate());
				if (creationDate != null) {
					cvxCode.setCreationdate(creationDate);
				}

				Timestamp creationDateLocal = UtilityMethods.getTimestampFormOffsetDateTime(vac.getCreationDateLocal());
				if (creationDateLocal != null) {
					cvxCode.setCreationdatelocal(creationDateLocal);
				}

				Timestamp modDate = UtilityMethods.getTimestampFormOffsetDateTime(vac.getModificationDate());
				if (modDate != null) {
					cvxCode.setModificationdate(modDate);
				}

				Timestamp modDateLocal = UtilityMethods.getTimestampFormOffsetDateTime(vac.getModificationDateLocal());
				if (modDateLocal != null) {
					cvxCode.setModificationdatelocal(modDateLocal);
				}

				Timestamp lastPublishDate = UtilityMethods.getTimestampFormOffsetDateTime(vac.getModificationDate());
				if (lastPublishDate != null) {
					cvxCode.setModificationdate(lastPublishDate);
				}

				Timestamp LastPublishDateLocal = UtilityMethods
						.getTimestampFormOffsetDateTime(vac.getModificationDateLocal());
				if (LastPublishDateLocal != null) {
					cvxCode.setModificationdatelocal(LastPublishDateLocal);
				}

				cvxCode.setIncidencecount(vac.getIncidenceCount());
				Boolean hasDigitalAttachment = vac.getHasDigitalAttachment();
				if (hasDigitalAttachment != null) {
					cvxCode.setHasdigitalattachment(hasDigitalAttachment.toString());
				}
				cvxCode.setPreferreddescription(vac.getPreferredDescription());
				cvxCode.setPreferredaliassourcecode(vac.getPreferredAliasSourceCode());
				cvxCode.setPreferredaliasdescription(vac.getPreferredAliasDescription());
				cvxCode.setPreferredaliastypename(vac.getPreferredAliasTypeName());
				cvxCode.setPreferredaliastypemnemonic(vac.getPreferredAliasTypeMnemonic());
				cvxCode.setPreferredaliaslanguageisocode(vac.getPreferredAliasLanguageISOCode());
				cvxCode.setPreferredaliasgroupmnemonic(vac.getPreferredAliasGroupMnemonic());
				cvxCode.setPreferredaliaslanguagename(vac.getPreferredAliasLanguageName());
				cvxCode.setAliassourcecode(vac.getAliasSourceCode());
				cvxCode.setAliasdescription(vac.getAliasDescription());

				AliasTypeMnemonic aliasTypeMnem = vac.getAliasTypeMnemonic();
				if (aliasTypeMnem != null) {
					cvxCode.setAliastypemnemonic(aliasTypeMnem.toValue());
				}

				cvxCode.setAliasgroupmnemonic(vac.getAliasGroupMnemonic());

				AliasLanguageISOCode aliso = vac.getAliasLanguageISOCode();
				if (aliso != null) {
					cvxCode.setAliaslanguageisocode(aliso.toValue());
				}

				Boolean profileMnemIsUsed = vac.getProfileDetail().getProfileMnemonicUsed();
				if (profileMnemIsUsed) {
					cvxCode.setProfilemnemonicused(profileMnemIsUsed.toString());
				}
				cvxCode.setProfilemnemonic(vac.getProfileDetail().getMnemonic());

				Boolean mnemSupplied = vac.getProfileDetail().getMnemonicSupplied();
				if (mnemSupplied != null) {
					cvxCode.setProfilemnemonicsupplied(mnemSupplied.toString());
				}
				Long profHitCnt = vac.getProfileDetail().getHitCount();
				if (profHitCnt != null) {
					cvxCode.setProfilehitcount(profHitCnt.toString());
				}

				Boolean profDetailFav = vac.getProfileDetail().getFavorite();
				if (profDetailFav != null) {
					cvxCode.setProfilefavorite(profDetailFav.toString());
				}

				int insertCnt = cvxParentMapper.insertSelective(cvxCode);

				//logger.error("Inserted " + insertCnt + " into cvx_parent.");
				//long cvxParentPK = cvxParentMapper.lastInsertId();
				data = data.addCreated(data);
//				AttributesXML xml = vac.getAttributesXML();
//				CvxAttributes attr = getCVXAttributes(xml);
//				//logger.error("Attribute from FILL:"+attr);
//				if (attr != null) {
//					attr.setAttributesid(0L); // set to 0 for AUTOINCREMENT
//					// set the FK for Table linkage between parent and child tables
//					attr.setCvxid(cvxParentPK);
//					logger.error("CATALOG:" + i + "  terms#:"+oneCatalog.getTermCount()+
//							"\n VACCINE:" + k + " CVX:" + cvxCode.toString()+"\n"+" Attributes:" + attr.toString());
//					//int insertCount = cvxAttrMapper.insertSelective(attr);
//
//					// logger.error("Inserted " + insertCount + " into cvx_attributes.");
//					data = data.addCreated(data);
//				}
			}
		}
		return data;
	}

//	@Override
//	public CvxAttributes setFields(CvxAttributes cvx, String name, String value)
//			throws IllegalArgumentException, Exception {
//
//		boolean isNotEmpty = false;
//		if (name == null) {
//			throw new IllegalArgumentException("Null nameAttr into getCVXAttributes()");
//		}
//
//		if (value == null) {
//			throw new IllegalArgumentException("Null nameAttr into getCVXAttributes()");
//		}
//
//		//logger.error("FILL FIELDS: NAME:"+name+ " VALUE:"+value);
//
//		value = blankIfNullTrim(value);
//
//		if (IConstants.MANULABELER.equalsIgnoreCase(name)) {
//			cvx.setManufacturerlabeler(value);
//		} else if (IConstants.PACKAGEDESCUOU.equalsIgnoreCase(name)) {
//			cvx.setPackagedescuou(value);
//		} else if (IConstants.UNCERTAINFORMULATIONCVX.equalsIgnoreCase(name)) {
//			cvx.setUncertainformulationcvx(value);
//		} else if (IConstants.NONVISDOC.equalsIgnoreCase(name)) {
//			cvx.setNonvisdoc(value);
//		} else if (IConstants.NOTES.equalsIgnoreCase(name)) {
//			cvx.setPackagedescuou(value);
//		} else if (IConstants.MARKETINGCAT.equalsIgnoreCase(name)) {
//			cvx.setMarketcategoryname(value);
//		} else if (IConstants.RETIREDDATE.equalsIgnoreCase(name)) {
//			cvx.setRetiireddate(value);
//		} else if (IConstants.MARKETINGSTARTDATE.equalsIgnoreCase(name)) {
//			cvx.setMarketingstartdate(value);
//		} else if (IConstants.NDCINNER.equalsIgnoreCase(name)) {
//			cvx.setNdcinnerid(value);
//		} else if (IConstants.PKGDESC.equalsIgnoreCase(name)) {
//			cvx.setPackagedesc(value);
//		} else if (IConstants.NONVACCINE.equalsIgnoreCase(name)) {
//			cvx.setNonvaccine(value);
//		} else if (IConstants.PRODUCTTYPE.equalsIgnoreCase(name)) {
//			cvx.setProducttype(value);
//		} else if (IConstants.USETYPE.equalsIgnoreCase(name)) {
//			cvx.setUsetype(value);
//		} else if (IConstants.OUTERPACKFORM.equalsIgnoreCase(name)) {
//			cvx.setOuterpackform(value);
//		} else if (IConstants.OUTERSTARTDATE.equalsIgnoreCase(name)) {
//			cvx.setOuterstartdate(value);
//		} else if (IConstants.OUTERENDDATE.equalsIgnoreCase(name)) {
//			cvx.setOuterenddate(value);
//		} else if (IConstants.LASTUPDATEDDATE.equalsIgnoreCase(name)) {
//			cvx.setLastupdateddate(value);
//		} else if (IConstants.USEUNITPACKFORM.equalsIgnoreCase(name)) {
//			cvx.setUseunitpackform(value);
//		} else if (IConstants.USEUNITSTARTDATE.equalsIgnoreCase(name)) {
//			cvx.setUseunitstartdate(value);
//		} else if (IConstants.APPNUMBER.equalsIgnoreCase(name)) {
//			cvx.setApplicationnumber(value);
//		} else if (IConstants.NDCOUTERID.equalsIgnoreCase(name)) {
//			cvx.setNdcouterid(value);
//		} else if (IConstants.GTIN.equalsIgnoreCase(name)) {
//			cvx.setGtin(value);
//		} else if (IConstants.CATEGORY.equalsIgnoreCase(name)) {
//			cvx.setCategory(value);
//		} else if (IConstants.EXCLUDE.equalsIgnoreCase(name)) {
//			cvx.setExclude(value);
//		} else if (IConstants.EXCLUDEREASON.equalsIgnoreCase(name)) {
//			cvx.setExcludereason(value);
//		} else if (IConstants.USEUNITENDDATE.equalsIgnoreCase(name)) {
//			cvx.setUseunitenddate(value);
//		} else if (IConstants.TAXONOMY.equalsIgnoreCase(name)) {
//			cvx.setTaxonomy(value);
//		} else if (IConstants.ROOTSOURCECODE.equalsIgnoreCase(name)) {
//			cvx.setRootsourcecode(value);
//		} else if (IConstants.LEVELS.equalsIgnoreCase(name)) {
//			cvx.setLevels(value);
//		} else if (IConstants.DEFAULTCATALOGMNE.equalsIgnoreCase(name)) {
//			cvx.setDefaultcatalogmne(value);
//		} else if (IConstants.DISPLAYNAME.equalsIgnoreCase(name)) {
//			cvx.setDisplayname(value);
//		} else if (IConstants.ALLOWEMPTYSEARCH.equalsIgnoreCase(name)) {
//			cvx.setAllowemptysearch(value);
//		} else if (IConstants.INFLUENZASEASON.equalsIgnoreCase(name)) {
//			cvx.setInfluenzaseason(value);
//		} else if (IConstants.SHOW.equalsIgnoreCase(name)) {
//			cvx.setShow(value);
//		} else if (IConstants.OUTERROUTE.equalsIgnoreCase(name)) {
//			cvx.setOuterroute(value);
//		} else if (IConstants.APPROXIMATETERMCOUNT.equalsIgnoreCase(name)) {
//			cvx.setApproximatetermcount(value);
//		} else if (IConstants.VISGDTI.equalsIgnoreCase(name)) {
//			cvx.setVisgdti(value);
//		} else if (IConstants.PDFURL.equalsIgnoreCase(name)) {
//			cvx.setPdfurl(value);
//		} else if (IConstants.HTMLURL.equalsIgnoreCase(name)) {
//			cvx.setHtmlurl(value);
//		} else if (IConstants.EDITIONSTATUS.equalsIgnoreCase(name)) {
//			cvx.setEditionstatus(value);
//		} else if (IConstants.EDITIONDATE.equalsIgnoreCase(name)) {
//			cvx.setEditiondate(value);
//		} else if (IConstants.ID.equalsIgnoreCase(name)) {
//			cvx.setId(value);
//		} else if (IConstants.UNCERTAINFORMULATION.equalsIgnoreCase(name)) {
//			cvx.setUncertainformulationcvx(value);
//		} else if (IConstants.NOTE.equalsIgnoreCase(name)) {
//			cvx.setNote(value);
//		} else if (IConstants.MARKETINGENDDATE.equalsIgnoreCase(name)) {
//			cvx.setMarketingstartdate(value);
//		} else if (IConstants.EFFDATE.equalsIgnoreCase(name)) {
//			cvx.setEffectivedate(value);
//		} else if (IConstants.VACCINESTATUS.equalsIgnoreCase(name)) {
//			cvx.setVaccinestatus(value);
//		} else if (IConstants.CPTCODEID.equalsIgnoreCase(name)) {
//			cvx.setCptcodeid(value);
//		} else if (IConstants.NOTES.equalsIgnoreCase(name)) {
//			cvx.setNotes(value);
//		} else if (IConstants.COMMENT.equalsIgnoreCase(name)) {
//			cvx.setComment(value);
//		} else {
//			throw new Exception("Unknown Name int fillFields:" + name);
//		}
//
//		return cvx;
//	}

	/**
	 * Get all the Vaccine Catalogs at the CDC hosted CVX Vaccine API
	 *
	 * @param URL of the CDC CVX Vaccine API to get a list of ALL Catalogs
	 * @return List of Catalog Objects
	 * @throws Exception
	 */
	public List<CVXCatalog> getCVXCatalogs(URL url) throws IllegalArgumentException, Exception {

		List<CVXCatalog> catalogs = new ArrayList<CVXCatalog>();

		if (url == null) {
			throw new IllegalArgumentException("NULL URL into CDCCvxServiceImpl.getCVXCatalogs()");
		}

		String host = url.getHost();
		if (!host.startsWith(IConstants.CDC_CVX_DOMAIN_REST_HOST)) {
			logger.error("Illegal URL:HOST:" + host + " PATH:" + url.getPath());
			throw new IllegalArgumentException("Incorrect URL into " + this.getClass().getMethods().toString()
					+ " URL should be to the CDC CVX API as follows:" + IConstants.CVX_CODE_CATALOG_URI);
		}

		HttpURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		// HTTP header fields to set
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en");
		con.setRequestProperty("API-Version", "v2");
		con.setConnectTimeout(0);
		con.setReadTimeout(0);
		
		// response
		int responseCode = con.getResponseCode();
		if (responseCode == HttpsURLConnection.HTTP_OK) {

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONArray root = new JSONArray(response.toString());

			for (int i = 0; i < root.length(); i++) {
				JSONObject catalogId = (JSONObject) root.get(i);
				CVXCatalog oneCatalog = CVXCatalogConverter.fromJsonString(catalogId.toString());
				catalogs.add(oneCatalog);
			}

		} else {
			logger.error("BAD Response code:" + responseCode + " from URI:" + url.toString());
			throw new Exception("BAD Response code from URI. " + url.toString() + " Response Code:" + responseCode);
		}

		return catalogs;
	}

	/**
	 *
	 */
	@Override
	public String getSingleCatalogOfVaccines(URL url) throws ConnectException, Exception {

		String rawJSON = "";

		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		// HTTP header fields to set
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en");
		con.setRequestProperty("API-Version", "v2");
		con.setConnectTimeout(0);
		con.setReadTimeout(0);

		// response
		int responseCode = con.getResponseCode();
		if (responseCode == HttpsURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			rawJSON = response.toString();
			// The @ and # symbol cannot be a name of a variable.
			// so change them throughout the returned JSON string before processing
			rawJSON = rawJSON.replaceAll("@name", "nameAttr");
			rawJSON = rawJSON.replaceAll("#text", "textAttr");

		} else {
			logger.error("BAD Response code:" + responseCode + " from URI:" + url.toString());
			throw new Exception("BAD Response code from URI. " + url.toString());
		}

		return rawJSON;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public RowsCreatedOrUpdated storeCVXCatalogs(List<CVXCatalog> catList, String creatorUpdated)
			throws IllegalArgumentException, SQLException, Exception {
		RowsCreatedOrUpdated data = new RowsCreatedOrUpdated();
		int deleteCount = cvxCatalogsMapper.deleteAll();
		logger.error("Deleted " + deleteCount + " from cvx_catalogs.");
		for (int i = 0; i < catList.size(); i++) {
			CVXCatalog oneCat = catList.get(i);
			CvxCatalogs dbCat = new CvxCatalogs();
			dbCat.setCatalogname(oneCat.getCatalogName());
			UUID catalogUUID = oneCat.getCatalogUID();
			dbCat.setCataloguid(catalogUUID.toString());
			dbCat.setCatalogversion(oneCat.getCatalogVersion());
			dbCat.setMnemonic(oneCat.getMnemonic());
			dbCat.setTermcount(oneCat.getTermCount());
			dbCat.setLastupdatedate(UtilityMethods.getTimestampFormOffsetDateTime(oneCat.getLastUpdatedDate()));
			dbCat.setLastpublisheddate(UtilityMethods.getTimestampFormOffsetDateTime(oneCat.getLastPublishedDate()));
			dbCat.setCreateddate(new Date());
			dbCat.setCreatedby(creatorUpdated);
			int insertCount = cvxCatalogsMapper.insert(dbCat);
			data.addCreated(data);

		}

		return data;
	}

//	@Override
//	public CvxAttributes getCVXAttributes(AttributesXML xml) throws IllegalArgumentException, Exception {
//
//		CvxAttributes one = new CvxAttributes();
//
//		if (xml == null) {
//			return null;
//		}
//
//		Attributes attrs = xml.getAttributes();
//		if (attrs != null) {
//			AttributeUnion attrUnion = attrs.getAttribute();
//			if (attrUnion != null && attrUnion.attributeElementArrayValue != null) {
//				List<AttributeElement> attrList = attrUnion.attributeElementArrayValue;
//				Iterator<AttributeElement> itrAttributeElem = attrList.iterator();
//				while (itrAttributeElem.hasNext()) {
//					Object obj = (Object) itrAttributeElem.next();
//					LinkedHashMap map = (LinkedHashMap) obj;
//					String nameAttr = blankIfNullTrim((String) map.get("nameAttr"));
//					String textAttr = blankIfNullTrim((String) map.get("textAttr"));
//					//logger.error("NAME:"+nameAttr+" TEXT:"+textAttr);
//					one = setFields(one, nameAttr, textAttr);
//				}
//			} else if (attrUnion != null && attrUnion.attributeElementValue != null) {
//				String nameAttr = blankIfNullTrim(attrUnion.attributeElementValue.getNameAttr());
//				String textAttr = blankIfNullTrim(attrUnion.attributeElementValue.getTextAttr());
//				//logger.error("NAME:"+nameAttr+" TEXT:"+textAttr);
//				one = setFields(one, nameAttr, textAttr);
//			} else {
//				throw new Exception("ERROR in getCVXAttributes(AttributesXML xml).");
//			}
//		}else {
//			return null;
//		}
//
//		return one;
//	}

	public static String blankIfNullTrim(String s) {
		if (s==null) {
			return "";
		}
		else {
			return s.trim();
		}
	}

}
