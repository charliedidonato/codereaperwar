package com.empirestateids.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.empirestateids.dao.LoincMapper;
import com.empirestateids.domain.LoincWithBLOBs;
import com.empirestateids.domain.UploadStatus;
import com.empirestateids.security.UserInfo;
import com.empirestateids.utls.UtilityMethods;

/**
 * 
 * @author Charles DiDonato
 *
 */
@Service("CsvParserService")
public class CsvParserServiceImpl implements CsvParserService {
	
	static Logger logger = LogManager.getLogger(CsvParserService.class);
	
	@Autowired
	private LoincMapper loincMapper;

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public UploadStatus uploadLoincMasterFile(String fileType, String versionId, byte[] csvByteArray, UserInfo userInfo) {
		String failedRows = "";
		UploadStatus status = new UploadStatus();
		boolean isLoincFile = false;
		int totalInsert = 0;
		ICsvListReader listReader = null;
		CellProcessor[] processors = getProcessors("L");
		List <Object> resultList = new ArrayList<Object>();
		try {
		    if (fileType == null){
		    	throw new IllegalArgumentException("Unknown file type to Upload:"+fileType);
		    } else if ("L".equalsIgnoreCase(fileType)){
		    	//LOINC File - NO OP - just a sanity check
		    	isLoincFile = true;
		    }
		    else {
		    	throw new IllegalArgumentException("Unknown file type Upload Results:"+fileType);
		    } 
			
		    listReader = new CsvListReader(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvByteArray))), CsvPreference.STANDARD_PREFERENCE);
			listReader.getHeader(true);
			try {			
				while (listReader.read() != null) {
					resultList = listReader.executeProcessors(processors);
					LoincWithBLOBs record = getLoincs(resultList, fileType);
					int insertCnt = loincMapper.insertSelective(record);
					totalInsert = totalInsert + insertCnt;
					if (insertCnt!=1) {
						logger.error("NO INSERT:"+record.toString());
					}
				}			
			} catch (IOException ioe) {
				failedRows = failedRows + "Error at line: " + listReader.getLineNumber() + " " 
						+ ioe.getMessage() + "\n";
				logger.error("Exception:"+ioe.getMessage()+" Trace:"+UtilityMethods.getStackTrace(ioe));
				
			}
		}catch(Exception e) {
			logger.error("Exception:"+e.getMessage()+" Trace:"+UtilityMethods.getStackTrace(e));
		}
		status.setRowCount(totalInsert);
		status.setErrors(failedRows);
		return status;
    }	
    
	private static CellProcessor[] getProcessors(String fileType) {
		logger.error("FILETYPE:"+fileType);
		if ("L".equalsIgnoreCase(fileType)) {
			final CellProcessor[] processors = new CellProcessor[] {
					new NotNull(), // LOINC_NUM
					new NotNull(), // COMPONENT
					new Optional(new StringCellProcessor()), // PROPERTY
					new StringCellProcessor(), // TIME_ASPCT
					new Optional(), // SYSTEM
					new Optional(), // SCALE_TYP
					new Optional(), // METHOD_TYP
					new Optional(), // CLASS_NAME
					new Optional(), // VersionLastChanged
					new Optional(), // CHNG_TYPE
					new Optional(), // DefinitionDescription
					new Optional(), // STATUS
					new Optional(), // CONSUMER_NAME
					new Optional(), // CLASSTYPE
					new Optional(), // FORMULA
					new Optional(), // EXMPL_ANSWERS
					new Optional(), // SURVEY_QUEST_TEXT
					new Optional(), // SURVEY_QUEST_SRC
					new Optional(), // UNITSREQUIRED
					new Optional(), // RELATEDNAMES2
					new Optional(), // SHORTNAME
					new Optional(), // ORDER_OBS
					new Optional(), // HL7_FIELD_SUBFIELD_ID
					new Optional(), // EXTERNAL_COPYRIGHT_NOTICE
					new Optional(), // EXAMPLE_UNITS
					new NotNull(), // LONG_COMMON_NAME
					new Optional(), // EXAMPLE_UCUM_UNITS
					new Optional(), // STATUS_REASON
					new Optional(), // STATUS_TEXT
					new Optional(), // CHANGE_REASON_PUBLIC
					new Optional(), // COMMON_TEST_RANK
					new Optional(), // COMMON_ORDER_RANK
					new Optional(), // HL7_ATTACHMENT_STRUCTURE					
					new Optional(), // EXTERNAL_COPYRIGHT_LINK
					new Optional(), // PanelType
					new Optional(), // AskAtOrderEntry
					new Optional(), // AssociatedObservations
					new Optional(), // VersionFirstReleased
					new Optional(), // ValidHL7AttachmentRequest
					new Optional()  // DisplayName
			};
			return processors;
		} else {
			//danger will robinson
			return(null);
		}

	}
	
	/**
	 * A method to translate from CSV to a TrainerStat object
	 * @param objList
	 * @return
	 */
	public LoincWithBLOBs getLoincs(List<Object> objList, String fileType){
		
		if (objList==null){
			throw new IllegalArgumentException("Null Object list into getLoincs().");
		}
		
		if (fileType==null || 
			!"L".equalsIgnoreCase(fileType)) {
			throw new IllegalArgumentException("Null or Invalid File Type into getLoincs():"+fileType);
		}
		
		LoincWithBLOBs t = new LoincWithBLOBs();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		// field 1 Loinc Number	
		String loinc = (String) objList.get(0);
		t.setLoincNum(loinc);
		
		// field 2 Component (description)
		t.setComponent((String) objList.get(1));
		
	    //field 3 Property
		t.setProperty((String) objList.get(2));
		
		//field 4 TIME_ASPCT
		t.setTimeAspct((String) objList.get(3));
		
        //field 5 SYSTEM
		t.setSystem((String) objList.get(4));
			
		//field 6 SCALE_TYP
		t.setScaleTyp((String) objList.get(5));
		
		//field 7 METHOD_TYP
		t.setMethodTyp((String) objList.get(6));
		
		//field 8 CLASS_NAME
		t.setClassName((String) objList.get(7));
		
		//field 9 VersionLastChanged
		t.setVersionlastchanged((String) objList.get(8));
		
		//field 10 CHNG_TYPE
		t.setChngType((String) objList.get(9));
		
		//field 11 DefinitionDescription
		t.setDefinitiondescription((String) objList.get(10));
		
		//field 12 STATUS
		t.setStatus((String) objList.get(11));
		
		//field 13 CONSUMER_NAME
		t.setConsumerName((String) objList.get(12));
		
		//field 14 CLASSTYPE
		t.setClasstype(new Integer((String) objList.get(13)));
		
		//field 15 FORMULA
		t.setFormula((String) objList.get(14));
		
		//field 16 EXMPL_ANSWERS
		t.setExmplAnswers((String) objList.get(15));
		
		//field 17 SURVEY_QUEST_TEXT
		t.setSurveyQuestText((String) objList.get(16));
				
		//field 18 SURVEY_QUEST_SRC
		t.setSurveyQuestSrc((String) objList.get(17));
		
		//field 19 UNITSREQUIRED
		t.setUnitsrequired((String) objList.get(18));
		
		//field 20 RELATEDNAMES2
		t.setRelatednames2((String) objList.get(19));
		
		//field 21 SHORTNAME
		t.setShortname((String) objList.get(20));
		
		//field 22 ORDER_OBS
		t.setOrderObs((String) objList.get(21));
		
		//field 23 HL7_FIELD_SUBFIELD_ID
		t.setHl7FieldSubfieldId((String) objList.get(22));
		
		//field 24 EXTERNAL_COPYRIGHT_NOTICE
		t.setExternalCopyrightNotice((String) objList.get(23));		
		
		//field 25 EXAMPLE_UNITS
		t.setExampleUnits((String) objList.get(24));
		
		//field 26 LONG_COMMON_NAME
		t.setLongCommonName((String) objList.get(25));		

		//field 27 EXAMPLE_UCUM_UNITS
		t.setExampleUcumUnits((String) objList.get(26));		
		
		//field 28 STATUS_REASON
		t.setStatusReason((String) objList.get(27));
		
		//field 29 STATUS_TEXT
		t.setStatusText((String) objList.get(28));
		
		//field 30 CHANGE_REASON_PUBLIC
		t.setChangeReasonPublic((String) objList.get(29));
		
		//field 31 COMMON_TEST_RANK
		String commonTestRank = (String)objList.get(30);
		Integer temp = new Integer(-1);
		try {
			temp = new Integer(commonTestRank);
		}catch(NumberFormatException nfe) {
			temp = new Integer(-1);
		}
		t.setCommonTestRank(temp);	
		
		//field 32 COMMON_ORDER_RANK
		String commonOrderRank = (String)objList.get(31);
		temp = new Integer(-1);
		try {
			temp = new Integer(commonOrderRank);
		}catch(NumberFormatException nfe) {
			temp = new Integer(-1);
		}
		t.setCommonOrderRank(new Integer((String)objList.get(31)));
		
		//field 33 HL7_ATTACHMENT_STRUCTURE
		t.setHl7AttachmentStructure((String)objList.get(32));
		
		//field 34 EXTERNAL_COPYRIGHT_LINK
		t.setExternalCopyrightLink((String) objList.get(33));		

		//field 35 PanelType
		t.setPaneltype((String) objList.get(34));		
		
		//field 36 AskAtOrderEntry
		t.setAskatorderentry((String) objList.get(35));
		
		//field 37 AssociatedObservations
		t.setAssociatedobservations((String) objList.get(36));
		
		//field 38 VersionFirstReleased
		t.setVersionfirstreleased((String) objList.get(37));
		
		//field 39 ValidHL7AttachmentRequest
		t.setValidhl7attachmentrequest((String)objList.get(38));	
		
		//field 40 DisplayName
		t.setDisplayname((String)objList.get(39));
		
		return (t);
	}
    
}
