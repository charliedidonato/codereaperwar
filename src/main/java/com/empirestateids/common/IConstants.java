/**
 *
 */
package com.empirestateids.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author DiDonato
 *
 */
public interface IConstants {


	public static final String ATLAS_PROD_URL_STRING = "https://empirestateids.com";
	public static final String ATLAS_PROD_URL_STRING_REST = "https://empirestateids.com/codereaper/REST/";

	public static final String ATLAS_PROD_CONTEXT_URL = "/codereaper/";

	public static final String LKUP_REF_GROUP_LIST = "GroupList";
	public static final String LKUP_GENDER = "Gender";
	public static final String LKUP_AUTHORITY = "Authority";

	public static final String CODEREAPER_USERAGENT = "Empire State IDS codereaper application";

	//National Library of Medicine constants for parsing and loading of RxNorm codes
	public static final String NLM_DOMAIN_REST_HOST = "rxnav.nlm.nih.gov";
	public static final String NLM_DOMAIN_REST = "https://rxnav.nlm.nih.gov/REST/";
	//universe of status = active - obsolete - remapped - quantified - notcurrent
	public static final String NLM_DOMAIN_REST_ALL_NDC_EXT = "allNDCstatus.json?status=active";
	public static final String NLM_DOMAIN_REST_NDC_DETAIL = "relatedndc.json?relation=product&ndc=";
	public static final String FILE_UPLOAD_LOC = "/codereaper/client/upload/";



	//ICD constants for parsing and loading via ICD REST service (ICD10)
	public static final String ICD_TOKEN_ENDPOINT = "https://icdaccessmanagement.who.int/connect/token";
	public static final String ICD_CODE_CLIENT_ID = "9be8d76b-a589-4c5b-8459-3590ed9ad97c_8fe97b9b-2b9e-430a-b68f-8c25d6ca86c8";
	public static final String ICD_CODE_CLIENT_SECRET = "3qZ20rDjTiVKq1SHFZT1579h6vynQsYimRCwIGwb1K0=";
	public static final String ICD_CODE_SCOPE = "icdapi_access";
	public static final String ICD_CODE_GRANT_TYPE = "client_credentials";

	//public static final String ICD_CODE_11_ENTITIES = "https://id.who.int/icd/entity?releaseId=2019-04";
	public static final String ICD_CODE_11_SINGLE_ENTITY = "https://id.who.int/icd/release/11/2024-01/mms";
	public static final String ICD_CODE_11_SINGLE_ENTITY_MMS = "https://id.who.int/icd/release/11/2024-01/mms/codeinfo/";
	public static final String ICD_CODE_11_SINGLE_ENTITY_MMS_TRAILER  = "?flexiblemode=false&convertToTerminalCodes=false";
	public static final String[] IDC11FirstChar = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static final String[] IDC11SecondChar = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static final String[] IDC11ThirdChar = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	public static final String[] IDC11FourthChar = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
			"D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	// notice the first entry in the array is empty string. This is for to generate
	// a 4 character ICD11 without any trailing .<char1><char2>
	public static final String[] IDC11FifthChar = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
			"B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };

	// same reason for empty string for first entry in sixth character
	public static final String[] IDC11SixthChar = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static final List<String> firstChar = Arrays.asList(IDC11FirstChar);
	public static final List<String> secondChar = Arrays.asList(IDC11SecondChar);
	public static final List<String> thirdChar = Arrays.asList(IDC11ThirdChar);
	public static final List<String> fourthChar = Arrays.asList(IDC11FourthChar);
	public static final List<String> fifthChar = Arrays.asList(IDC11FifthChar);
	public static final List<String> sixthChar = Arrays.asList(IDC11SixthChar);

	public static final Pattern firstChapter = Pattern.compile("1+[A-H]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern secondChapter = Pattern.compile("2+[A-F]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern thirdChapter = Pattern.compile("3+[A-C]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern fourthChapter = Pattern.compile("4+[A-B]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern fifthChapter = Pattern.compile("5+[A-D]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern sixthChapter = Pattern.compile("6+[A-E]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern seventhChapter = Pattern.compile("7+[A-B]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern eighthChapter = Pattern.compile("8+[A-E]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern ninethChapter = Pattern.compile("9+[A-E]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern tenthChapter = Pattern.compile("A+[A-C]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern eleventhChapter = Pattern.compile("B+[A-E]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern twelthChapter = Pattern.compile("C+[A-B]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern thirteenthChapter = Pattern.compile("D+[A-E]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern fourteenthChapter = Pattern.compile("E+[A-M]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern fifteenthChapter = Pattern.compile("F+[A-C]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern sixteenthChapter = Pattern.compile("G+[A-C]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern seventeenthChapter = Pattern.compile("H+A+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern eighteenthChapter = Pattern.compile("J+[A-B]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern nineteenthChapter = Pattern.compile("K+[A-D]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentienthChapter = Pattern.compile("L+[A-D]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentyfirstChapter = Pattern.compile("M+[A-H]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentysecondChapter = Pattern.compile("N+[A-F]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentythirdChapter = Pattern.compile("P+[A-L]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentyfourthChapter = Pattern.compile("Q+[A-F]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentyfifthChapter = Pattern.compile("R+[A]+[\\d]+[\\d,A-Z]", Pattern.CASE_INSENSITIVE);
	public static final Pattern twentysixthChapter = Pattern.compile("S+[A-T]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentyseventhChapter = Pattern.compile("V+[A-W]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern twentyeighthChapter = Pattern.compile("X+[A-Y]+[\\d]+[\\d,A-Z]",
			Pattern.CASE_INSENSITIVE);

	//public static final String ICD_CODE_ENTITY_URI_SEARCH_PARENT_ANCESTOR = "https://id.who.int/icd/entity/2029519782?include=ancestor%2Cdescendant";
	public static final String ICD_10_CODE_INDIVIDUAL = "https://id.who.int/icd/release/10/2019/";

	/************* MAKE THIS a PROPERTY from FILE ******************/
	public static final File ICD_10_CM_CODE_FILE = new File("C:/1/icd10cm_codes_2025.txt");
	public static final File ICD_10_CM_CODE_ADDENDUM_FILE = new File("C:/1/icd10cm_codes_addenda_2025.txt");
	public static final File ICD_10_PCS_CODE_FILE = new File("C:/1/icd10pcs_codes_2025.txt");
	public static final File ICD_10_PCS_CODE_ADDENDUM_FILE = new File("C:/1/order_addenda_2025.txt");

	//first character of ICD 10 codes
	public static final String[] ICD10FIRSTCHAR = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","V","W","X","Y","Z"};
	public static final String[] IDC10_4567Chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F", "G", "H","I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };



//	public static final String ICD_CODE_FOUNDATION_URI = "https://id.who.int/icd/contexts/contextForFoundationEntity.json";
//	public static final String ICD_CODE_FOUNDATION_REFERENCE_URI = "https://id.who.int/icd/schema/foundationReference";


	//Vaccine constants for parsing and loading via CDC Vaccine REST service (CVX Codes)
	public static final String CVX_CODE_CATALOG_URI = "https://vaccinecodeset.cdc.gov/SymedicalCDCVCABProdRuntimeRestService/catalog";
	public static final String CVX_CODE_CATALOG_TERM_URI = "https://vaccinecodeset.cdc.gov/SymedicalCDCVCABProdRuntimeRestService/catalog/terms?maxResults=0&catalogIdentifier=";
	public static final String CDC_CVX_DOMAIN_REST_HOST = "vaccinecodeset.cdc.gov";
	public static final String MANULABELER = "Manufacturer Labeler";
	public static final String MARKETINGCAT = "MarketingCategoryName";
	public static final String MARKETINGSTARTDATE = "MarketingStartDate";
	public static final String NDCINNER = "NDCInnerID";
	public static final String PKGDESC = "PackageDescription";
	public static final String PRODUCTTYPE = "ProductType";
	public static final String USETYPE = "UseType";
	public static final String RETIREDDATE = "Retired Date";
	public static final String USEUNITPACKFORM = "UseUnitPackForm";
	public static final String USEUNITSTARTDATE = "UseUnitstartDate";
	public static final String APPNUMBER = "ApplicationNumber";
	public static final String GTIN = "GTIN";
	public static final String COMMENT = "Comment";
	public static final String NONVACCINE = "Nonvaccine";
	public static final String EXCLUDE = "Exclude";
	public static final String EXCLUDEREASON = "ExcludeReason";
	public static final String USEUNITENDDATE = "UseUnitEndDate";
	public static final String TAXONOMY = "Taxonomy";
	public static final String ROOTSOURCECODE = "RootSourceCode";
	public static final String LEVELS = "Levels";
	public static final String DEFAULTCATALOGMNE = "DefaultCatalogMne";
	public static final String DISPLAYNAME = "DisplayName";
	public static final String ALLOWEMPTYSEARCH = "AllowEmptySearch";
	public static final String SHOW = "Show";
	public static final String APPROXIMATETERMCOUNT = "ApproximateTermCount";
	public static final String VISGDTI = "VIS GDTI document code";
	public static final String PDFURL = "PDF URL";
	public static final String HTMLURL = "HTML URL";
	public static final String EDITIONSTATUS = "Edition Status";
	public static final String EDITIONDATE = "Edition Date";
	public static final String ID = "ID";
	public static final String NOTE = "Note";
	public static final String NONVISDOC = "NonVIS Document";
	public static final String LASTUPDATEDDATE = "Last Updated Date";
	public static final String NOTES = "Notes";
	public static final String NDCOUTERID = "NDCOuterID";
	public static final String VACCINESTATUS = "Vaccine Status";
	public static final String UNCERTAINFORMULATION = "Uncertain Formulation";
	public static final String EFFDATE = "Effective Date";
	public static final String CATEGORY = "Category";
	public static final String OUTERPACKFORM = "OuterPackForm";
	public static final String MARKETINGENDDATE = "MarketingEndDate";
	public static final String CPTCODEID = "CPT_code_ID";
	public static final String OUTERROUTE = "OuterRoute";
	public static final String OUTERSTARTDATE = "OuterStartDate";
	public static final String OUTERENDDATE = "OuterEndDate";
	public static final String PACKAGEDESCUOU = "PackageDescriptionUoU";
	public static final String INFLUENZASEASON = "InfluenzaSeason";
	public static final String UNCERTAINFORMULATIONCVX = "Uncertain Formulation CVX";

	// groups
	public static final int GROUP_NAME_ADMINISTRATOR=102;
	public static final int GROUP_NAME_REGISTERED_USER=103;
	public static final int GROUP_NAME_WNPF=107;

	// article perms
	public static final String BR_ARTICLE_CREATE = "BR_ARTICLE_CREATE";
	public static final String BR_ARTICLE_MANAGE = "BR_ARTICLE_MANAGE";
	public static final String BR_ARTICLE_UPDATE = "BR_ARTICLE_UPDATE";

	// ckeditor perms
	public static final String BR_CKEDITOR_ALL = "BR_CKEDITOR_ALL";
	public static final String BR_CKFINDER_ALL = "BR_CKFINDER_ALL";

	// gallery perms
	public static final String BR_GALLERY_CREATE = "BR_GALLERY_CREATE";
	public static final String BR_GALLERY_MANAGE = "BR_GALLERY_MANAGE";
	public static final String BR_GALLERY_UPDATE = "BR_GALLERY_UPDATE";

	// group perms
	public static final String BR_GROUP_CREATE = "BR_GROUP_CREATE";
	public static final String BR_GROUP_MANAGE = "BR_GROUP_MANAGE";
	public static final String BR_GROUP_UPDATE = "BR_GROUP_UPDATE";
	public static final String BR_GROUP_VIEW = "BR_GROUP_VIEW";
	// user reg perms
	public static final String BR_USER_REG_CREATE = "BR_USER_REG_CREATE";
	public static final String BR_USER_REG_MANAGE = "BR_USER_REG_MANAGE";
	public static final String BR_USER_REG_UPDATE = "BR_USER_REG_UPDATE";
	public static final String BR_USER_REG_VIEW = "BR_USER_REG_VIEW";

	// user reg perms
	public static final String BR_PROFILE_CREATE = "BR_PROFILE_CREATE";
	public static final String BR_PROFILE_MANAGE = "BR_PROFILE_MANAGE";
	public static final String BR_PROFILE_UPDATE = "BR_PROFILE_UPDATE";
	public static final String BR_PROFILE_VIEW = "BR_PROFILE_VIEW";

	// security roles
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_FEDERATION = "ROLE_FEDERATION";
	public static final String ROLE_MEMBER = "ROLE_MEMBER";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_PREFERRED_USER = "ROLE_ORG_ATLAS";

	// security company/org belong to
	public static final String ORG_ATLAS = "ORG_ATLAS";
	public static final String ORG_WNPF = "ORG_WNPF";

	// context path
	public static final String APP_CONTEXT = "application.context";
	public static final String APP_CONTEXT_PATH = "application.context.path";

	// id seq
	public static final String IPOWERLIFTID = "ipowerliftid";
	public static final String ARTICLEID = "articleid";
	public static final String CONTACTID = "contactid";
	public static final String GROUPID = "groupid";


	//resource bundle file
	public static final String APP_RESRC_BUNDLE_FILE = "/bundles/application-resources.properties";

	// application-resources.properties
	public static final String ATLAS_ADVERTISEMENTS = "atlas.advertisements";
	public static final String ATLAS_ANALYTICS = "atlas.analytics";
	public static final String ATLAS_LOCAL_EMAIL = "atlas.local.email";

	public static final String STAGE_LOCAL = "local";
	public static final String STAGE_PROD = "prod";
	public static final String STAGE_EVAL = "eval";
	public static final String STAGE_DEV = "dev";
	public static final String STAGE_NOT_SPECIFIED = "notspecified";

	//article html file encoding
	public static final String ARTICLE_ENCODING = "UTF-8";

	//resource files locs
	public static final String APP_RESRC_LOC = "app.resrc.loc";
	public static final String APP_RESRC_MAPPER = "app.resrc.mapper";
	public static final String APP_RESRC_ARTICLES_LOC = "app.resrc.articles.loc";

	//file upload location
	//public static final String FILE_UPLOAD_LOC = "/mavenresources/files/";


	//Profile Picture Location
	public static final String PROFILE_PICTURE_FILE_UPLOAD_LOC = "/mavenresources/images/profiles/";

	//Advertisement Image Location
	public static final String AD_IMAGE_UPLOAD_LOC = "/mavenresources/images/advertisements/";
	public static final String AD_IMAGE_UPLOAD_LOC_REL = "/mavenresources/images/advertisements/";

	public static final String DATE_PATTERN =
	          "(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";

	public static final short TRUE = 1;
	public static final short FALSE = 0;

	public static final int EMAIL_CLASS_TYPE_USER = 1;


	//article source
	public static final int SOURCE_ATLAS=1;


	 //mail constants

    //togaquikpics.com
    //public static final String SMTP_MAILHOST = "email-smtp.us-west-2.amazonaws.com";
    //public static final String SMTP_USER = "AKIAWNN5MWMODJES2SRG";
    //public static final String SMTP_PASS = "BO3OA0pGBQZjUlQSnJZlTz6e4YqDIUqf4foPupR3NyUO";
    //public static final String DEFAULT_ATLAS_FROM_EMAIL = "payment@togaquikpics.com";

    //aihorsehandicapping.com - set up for EC2 server
	public static final String SMTP_USER = "AKIAWNN5MWMOLADTIEEZ";
	public static final String SMTP_PASS = "BBVR8liBYz/ZI7Vry7hZaZtNGYLYL5Mjdfn2Coqlw5x3";

    public static final String SMTP_MAILHOST = "email-smtp.us-west-2.amazonaws.com";
    public static final Integer SMTP_PORT = 587; //SSL port
    public static final String DEFAULT_ATLAS_FROM_EMAIL = "support@empirestateids.com";




    public static final int EMAIL_PRIORITY_HIGH = 1;
    public static final int EMAIL_PRIORITY_NORMAL = 3;
    public static final int EMAIL_PRIORITY_LOW = 5;

    public static final int TEMPORARY_PASSWORD_LENGTH = 8;

//    public static final String LIVESTREAM_CHANNEL = "ipowerlift";
//    public static final String CHANNEL_URI_LIST =
//    	"listclips.json?id=750857b5-b33f-479d-92bf-ed3e6b5fc29e";


//	int[] WEIGHT_CLASS_ARRAY_MENS = {80,100,114,123,132,148,165,181,198,220,242,275,999};
//	int[] WEIGHT_CLASS_ARRAY_WOMENS = {97,105,114,123,132,148,165,181,198,999};
//
//	int[] LIFT_TYPE_ARRAY_POWERLIFTING = {IConstants.LIFTTYPE_SQUAT, IConstants.LIFTTYPE_BENCHPRESS, IConstants.LIFTTYPE_DEADLIFT, IConstants.LIFTTYPE_TOTAL};
//	int[] LIFT_TYPE_ARRAY_SQUAT_ONLY = {IConstants.LIFTTYPE_SQUAT};
//	int[] LIFT_TYPE_ARRAY_BENCHPRESS_ONLY = {IConstants.LIFTTYPE_BENCHPRESS};
//	int[] LIFT_TYPE_ARRAY_DEADLIFT_ONLY = {IConstants.LIFTTYPE_DEADLIFT};
//	int[] LIFT_TYPE_ARRAY_POWERCURL = {IConstants.LIFTTYPE_POWERCURL};

//    public static final int MENS = 2;
//	public static final int WOMENS = 1;
//
//	public static final int RECORD_CLASS_TYPE_CURRENT = 1;
//	public static final int RECORD_CLASS_TYPE_PERSONAL = 2;
//	public static final int RECORD_CLASS_TYPE_HISTORIC = 3;
//
//	public static final int LIFTTYPE_SQUAT = 1;
//	public static final int LIFTTYPE_BENCHPRESS = 2;
//	public static final int LIFTTYPE_DEADLIFT = 3;
//	public static final int LIFTTYPE_TOTAL = 4;
//	public static final int LIFTTYPE_POWERCURL = 5;
//
//	public static final int MEETTYPE_SQUAT_ONLY = 4;
//	public static final int MEETTYPE_BENCHPRESS_ONLY = 3;
//	public static final int MEETTYPE_DEADLIFT_ONLY = 2;
//	public static final int MEETTYPE_POWERLIFTING = 1;
//	public static final int MEETTYPE_POWERCURL = 5;
//	public static final int MEETTYPE_IRONMAN = 6;
//
//	public static final int LEVEL_NATIONAL = 1;
//	public static final int LEVEL_WORLD = 2;
//	public static final int LEVEL_STATE = 3;
//	public static final int LEVEL_TRAINING = 4;
//
//	public static final int SUPER_HEAVY_WEIGHT = 999;

//	public static final byte LIFTER_REG_MEMEBR_CODE_SUBMITED = 1;
//	public static final byte LIFTER_REG_MEMEBR_CODE_APPROVED = 2;
//
//	public static final byte LIFTER_PROFILE_ACTIVE = 1;
//	public static final byte LIFTER_PROFILE_INACTIVE = 2;
//
//	public static final byte LIFTER_PRIMARY_PIC = 1;
//	public static final byte LIFTER_NON_PRIMARY_PIC = 2;
//
//	public static final int LIFTER_MAXIMUM_PICTURES_ALLOWED = 50;
//	public static final int LIFTER_MAXIMUM_VIDEOS_ALLOWED = 30;

	public static final String LIFTER_SPORT = "PowerLifting";

	public static final int GUEST_USERID = 1000000;

	public static final byte LIFTER_PROFILE_PICTUTRES_IACCEPT  = 1;
	public static final byte LIFTER_PROFILE_PICTUTRES_IDECLINE = 2;

	public static final byte LIFTER_PROFILE_REGISTER_MIN_AGE  = 18;

	public static final int DEFAULT_IMG_WIDTH = 800;
	public static final int DEFAULT_IMG_HEIGHT = 600;
	public static final int DEFAULT_THUMB_MAX_WIDTH = 100;
	public static final int DEFAULT_THUMB_MAX_HEIGHT = 75;
	public static final float DEFAULT_IMG_QUALITY = 0.8f;
	public static final float ENHANCED_IMG_QUALITY = 1.0f;
	public static final float DEFAULT_THUMB_IMG_QUALITY = 1.0f;

	public static final String INITIAL_PROFILE_LISTING = "Initial";

	public static final String DIRECTORY_THUMBNAIL = "thumb";
	public static final String DIRECTORY_ORIGINAL_IMAGE = "original";

	public static final String FILENAME_PROFILE_PIC = "profilepic_";

	//Sort order Constants
	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";

	//Upload Results File constants
	public static final String TRAINER_FILE = "T";
	public static final String HORSE_FILE = "H";
	public static final String JOCKEY_FILE = "J";
	public static final String POSTPOSITION_FILE = "P";
	public static final String RACEINFO_FILE = "R";
	public static final String WORKOUT_FILE = "W";
	public static final String HORSECOMMENTS_FILE = "C";
	public static final String HORSERESULTS_FILE = "HR";

	public static final int WIN_POINTS = 10;
	public static final int PLACE_POINTS = 6;
	public static final int SHOW_POINTS = 3;

	// Superuser for free racecards
	public static final int FREECARDUSERID = 1000030;

	//Form Names for Race Card Scenarios
	public static final String DIRT_DEFAULT_FORM = "1 DIRT SAME DISTANCE";
	public static final String DIRT_SAME_TRACK_FORM = "2 DIRT SAME TRACK";
	public static final String DIRT_WET_FORM = "3 DIRT WET";
	public static final String TURF_DEFAULT_FORM = "1 TURF LIFETIME";
	public static final String TURF_SAME_TRACK_FORM = "2 TURF SAME TRACK";
	public static final String TURF_WET_FORM = "3 OFF TURF WET";
	public static final String MAIDEN_DEFAULT_FORM = "1 MAIDEN SAME DISTANCE";
	public static final String MAIDEN_TURF_FORM = "2 MAIDEN TURF";
	public static final String MAIDEN_SAME_TRACK_FORM = "3 MAIDEN SAME TRACK";
	public static final String MAIDEN_WET_FORM = "4 MAIDEN WET";

	// *******************  FIX ME MAKE THESE STSTEM PROPERTIES*****************/
	// PayPal Constants
	public static final String NONE_SELECTED = "NONE";
	public static final String DEV_CLIENT_ID = "AY-PDrrZ-MMYCRVx0z7JB9i9GEPht4PE9_34fbCjO5QybVvpZ1tCfIS83xJCpaelidH0n0Ke_T1Sb_kP";
	public static final String DEV_CLIENT_SECRET = "EHQly4fpnNVfa8tn5ErWL2b3oXCaqeL5nEp0jxljD9aaoENl9le1wj2JrdM-bP_lvIj-teyoLy36JjD7";

	public static final String PROD_CLIENT_ID = "AaIpEh0-UZdWjmt7rTShDtBcjKXjMBf3tvqR7ephPqtGcG6-dSZP5ciQOq5LIb39UPaWSOibKdcXuyxC";
	public static final String PROD_CLIENT_SECRET = "ENnjbqO9TkMniJqxCRZ7FBa8HqtiPAwpmgUyAELuRmrBvL5yUOVMdtsFUIZNdhJGpGtzvgx06Pa5_DMC";

	public static final String PROD_HOST_ENDPOINT = "api.paypal.com";
	public static final String DEV_HOST_ENDPOINT = "api.sandbox.paypal.com";
	public static final String PAYPAL_ORDER_URL = "/v2/checkout/orders";

	public static final String PAYPAL_ORDER_DETAILS_URL = "/v2/checkout/orders";

	public static final String PAYPAL_CONFIRM_URL_PREFIX = "/v2/checkout/orders/";  //put this before order id (prepend)
	public static final String PAYPAL_CONFIRM_URL_SUFFIX = "/confirm-payment-source"; //put this after order id (append)

	public static final String PAYPAL_AUTHORIZE_URL_PREFIX = "/v2/checkout/orders/";  //put this before order id (prepend)
	public static final String PAYPAL_AUTHORIZE_URL_SUFFIX = "/authorize"; //put this after order id (append)

	public static final String PAYPAL_CAPTURE_URL_PREFIX = "/v2/checkout/orders/";  //put this before order id (prepend)
	public static final String PAYPAL_CAPTURE_URL_SUFFIX = "/capture"; //put this after order id (append)


	public static final String PAYPAL_AUTHORIZE_MSG = "Authorized by Customer";
	public static final String PAYPAL_APPROVE_MSG = "Completed";
	public static final Integer PRODUCT_STANDARD_RACE_CARD = 1;  //Standard Race Card Qty 1

	public static final String PAYPAL_CONFIRM_URL = "https://www.aihorsehandicapping.com/pegasus/paypalPayment/authorizePayment?";

}
