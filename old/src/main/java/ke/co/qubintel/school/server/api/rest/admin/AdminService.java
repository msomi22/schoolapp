/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.google.gson.Gson;

import ke.co.qubintel.school.server.api.rest.bean.ApiResponse;
import ke.co.qubintel.school.server.api.rest.bean.Response;
import ke.co.qubintel.school.server.api.rest.bean.admin.ApiAccount;
import ke.co.qubintel.school.server.bean.account.Account;
import ke.co.qubintel.school.server.bean.account.ApiCredential;
import ke.co.qubintel.school.server.bean.account.Miscellanous;
import ke.co.qubintel.school.server.bean.classroom.ClassRoom;
import ke.co.qubintel.school.server.bean.classroom.Stream;
import ke.co.qubintel.school.server.bean.exam.Exam;
import ke.co.qubintel.school.server.bean.exam.GradingSystem;
import ke.co.qubintel.school.server.bean.exam.SysConfig;
import ke.co.qubintel.school.server.bean.house.House;
import ke.co.qubintel.school.server.bean.money.TermFee;
import ke.co.qubintel.school.server.bean.staff.AcessLevel;
import ke.co.qubintel.school.server.bean.subject.Category;
import ke.co.qubintel.school.server.bean.subject.SubCategory;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.account.AccountDAO;
import ke.co.qubintel.school.server.persistence.account.ApiCredentialDAO;
import ke.co.qubintel.school.server.persistence.account.MiscellanousDAO;
import ke.co.qubintel.school.server.persistence.classroom.ClassDAO;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.exam.ExamDAO;
import ke.co.qubintel.school.server.persistence.exam.GradingSystemDAO;
import ke.co.qubintel.school.server.persistence.exam.SysConfigDAO;
import ke.co.qubintel.school.server.persistence.house.HouseDAO;
import ke.co.qubintel.school.server.persistence.money.TermFeeDAO;
import ke.co.qubintel.school.server.persistence.staff.AcessLevelDAO;
import ke.co.qubintel.school.server.persistence.subject.CategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubCategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;
import ke.co.qubintel.school.server.servlet.reports.ReportUtil;
import ke.co.qubintel.school.server.servlet.util.SYS_COSTANTS;

/**
 * @author peter
 *
 */
public class AdminService {

	private static AccountDAO accountDAO;
	private static EmailValidator emailValidator;

	private static AcessLevelDAO acessLevelDAO;
	private static ClassDAO classDAO;
	private static CategoryDAO categoryDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static MiscellanousDAO miscellanousDAO;
	private static SubCategoryDAO subCategoryDAO;
	private static SubjectDAO subjectDAO;

	private static StreamDAO streamDAO;
	private static ExamDAO examDAO;
	private static SysConfigDAO sysConfigDAO;
	private static ApiCredentialDAO apiCredentialDAO;
	private static TermFeeDAO termFeeDAO;
	
	private static HouseDAO houseDAO;

	static {
		accountDAO = AccountDAO.getInstance();
		emailValidator = EmailValidator.getInstance();

		acessLevelDAO = AcessLevelDAO.getInstance();
		classDAO = ClassDAO.getInstance();
		categoryDAO = CategoryDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		miscellanousDAO = MiscellanousDAO.getInstance();
		subCategoryDAO = SubCategoryDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();

		streamDAO = StreamDAO.getInstance();
		examDAO = ExamDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		apiCredentialDAO = ApiCredentialDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		
		houseDAO = HouseDAO.getInstance();
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Object getAccountById(String uuid) {

		ApiResponse apiResponse = new ApiResponse(); 

		if(accountDAO.getAccountById(uuid) == null) {

			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Account not found!"); 
			return apiResponse;

		}

		return accountDAO.getAccountById(uuid);
	}

	/**
	 * 
	 * @return
	 */
	public List<ApiAccount> getAccounts() {

		Gson gson = new Gson();

		List<ApiAccount> accounts = new ArrayList<>();

		accountDAO.getAccounts().forEach(account -> {

			String tmpAccount =  gson.toJson(account);
			ApiAccount apiAccount = gson.fromJson(tmpAccount, ApiAccount.class);
			accounts.add(apiAccount); 

		});

		return accounts;
	}


	/**
	 * 
	 * @param apiAccount
	 * @return
	 */
	public Object newAccount(ApiAccount apiAccount) {

		ApiResponse apiResponse = new ApiResponse(); 

		String accountId = "b83e9b89-0d52-4191-a6bf-acf501267e2e1";
		accountId = "";


		if(accountDAO.getAccountById(accountId) != null) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account already added."); 
			return apiResponse;

		}else if(!isActiveValid(apiAccount.getIsActive())) {  
			apiResponse.setMessage("error");
			apiResponse.setDescription("Isactive can either be 1 or 0");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getName()) || apiAccount.getName().length() < 5) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School name.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getName(), "1") !=null) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school name is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMotto()) || apiAccount.getMotto().length() < 5) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School motto.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getUsername()) || apiAccount.getUsername().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School username.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getUsername(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school username is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getPassword()) || apiAccount.getPassword().length() < 4) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School password.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMobile()) || !StringUtils.isNumeric(apiAccount.getMobile()) && 
				apiAccount.getMobile().length() !=9) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School mobile number.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getMobile(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school mobile is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getEmail()) || !emailValidator.isValid(apiAccount.getEmail())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School email address.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getEmail(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school email is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getAddress()) || apiAccount.getAddress().length() < 2) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School postal address.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getTown()) || apiAccount.getTown().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid town.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsBoarding()) || !validBoarding(apiAccount.getIsBoarding())) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsBoarding!.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsMixed()) || !validMixed(apiAccount.getIsMixed())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsMixed!.");
			return apiResponse;

		}else {



			Account account = new Account();
			accountId = account.getUuid();
			account.setIsActive("1");
			account.setName(apiAccount.getName());
			account.setMotto(apiAccount.getMotto());
			account.setWebsite(apiAccount.getWebsite());
			account.setLogo(apiAccount.getLogo());
			account.setSignature(apiAccount.getSignature());
			account.setUsername(apiAccount.getUsername());
			account.setPassword(apiAccount.getPassword());
			account.setMobile(apiAccount.getMobile());
			account.setEmail(apiAccount.getEmail());
			account.setAddress(apiAccount.getAddress());
			account.setTown(apiAccount.getTown());
			account.setIsBoarding(apiAccount.getIsBoarding());
			account.setIsMixed(apiAccount.getIsMixed()); 
			account.setLastUpdated(new Date().toString());
			account.setUuid(accountId);

			if(accountDAO.putAccount(account)) {

				Object object = populateDefaluts(accountId); 

				return object;


			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Something went wrong while registering the account.");
			}

		}

		return apiResponse;

	}


	/**
	 * 
	 * @param uuid
	 * @return
	 */
	private Object populateDefaluts(String accountId) {

		Response response = new Response();  

		String resposne = "Account registered successfully,";
		
		/*****************************************************************************************/
		String[] access = {"Principal","Deputy Principal","CM","HOD","Teacher","Secretary","Bursar",SYS_COSTANTS.SYS_ACCESS_LEVEL}; 
		int[] a_Ids= {100,200,300,400,500,600,700,800}; 

		for(int count=0;count<access.length;count++) {
			AcessLevel acessLevel = new AcessLevel();
			acessLevel.setUuid(acessLevel.getUuid());
			acessLevel.setAccountId(accountId);
			acessLevel.setDescription(access[count]); 
			acessLevel.setAcessId(String.valueOf(a_Ids[count]));  
			acessLevelDAO.putAcessLevel(acessLevel); 
		}
		/*****************************************************************************************/
		String[] classes = {"FORM 1","FORM 2","FORM 3","FORM 4"};
		int[] examSubNo = {11,11,7,7};

		for(int count=0;count<classes.length;count++) {
			ClassRoom classRoom = new ClassRoom();
			classRoom.setUuid(classRoom.getUuid());
			classRoom.setAccountId(accountId);
			classRoom.setDescription(classes[count]); 
			classRoom.setDescription(examSubNo[count]+"");  
			classDAO.putClassRoom(classRoom);
		}
		/*****************************************************************************************/
		String[] classIds = {"FORM 1","FORM 2",
				"FORM 3","FORM 4",
				"FORM 1","FORM 2", "FORM 4"};

		String[] streams = {"FORM 1 N","FORM 2 N","FORM 3 N","FORM 4 N","FORM 1 S","FORM 2 S", SYS_COSTANTS.SYS_DEFAULT_STREAM}; 

		for(int count=0;count<classIds.length;count++) {
			Stream stream = new Stream();
			stream.setUuid(stream.getUuid());
			stream.setAccountId(accountId);
			stream.setClassRoomId(classDAO.getClassRoomByDesc(accountId, classIds[count]).getUuid());
			stream.setDescription(streams[count]); 
			streamDAO.putStream(stream);
		}

		/*****************************************************************************************/

		String[] categorys = {"Languages","Sciences","Humanities","Technicals","Mathematics","General"};

		int[] maxNo = {2,3,3,4,2,0};

		for(int count=0;count<categorys.length;count++) {
			Category category = new Category();
			category.setUuid(category.getUuid());
			category.setAccountId(accountId);
			category.setMaxNo(maxNo[count]); 
			category.setDescription(categorys[count]);
			categoryDAO.putCategory(category);
		}

		/*****************************************************************************************/
		int[] lowerLimits = {83,75,66,56,54,48,42,40,35,31,26,1};
		int[] upperLimits = {100,82,74,65,55,53,47,41,39,34,30,25};
		int[] points = {12,11,10,9,8,7,6,5,4,3,2,1};
		String[] desc = {"A","A-","B+","B","B-","C+","C","C-","D+","D","D-","E"};

		for(int count=0;count<desc.length;count++) {
			GradingSystem gradingSystem = new GradingSystem();
			gradingSystem.setUuid(gradingSystem.getUuid());
			gradingSystem.setAccountId(accountId);
			gradingSystem.setCategoryId(ReportUtil.getGeneralId(accountId));
			gradingSystem.setLowerLimit(lowerLimits[count]);
			gradingSystem.setUpperLimit(upperLimits[count]);
			gradingSystem.setPoints(points[count]);
			gradingSystem.setDescription(desc[count]);
			gradingSystemDAO.putGradingSystem(gradingSystem);
		}

		/*****************************************************************************************/
		String[] keys = {"CLOSING_DATE","OPENING_DATE","HEAD_TEACHER_REMARKS"};
		String[] values = {"Tue 03 April 2016","Wed 07 May 2016","for the fantastic term it has been awesome to see you grow and develop hope you have a wonderful holiday .For your performance all we can say is ..."};


		for(int count=0;count<keys.length;count++) {
			Miscellanous miscellanous = new Miscellanous();
			miscellanous.setUuid(miscellanous.getUuid());
			miscellanous.setAccountId(accountId);
			miscellanous.setKey(keys[count]);
			miscellanous.setValue(values[count]);
			miscellanousDAO.putMiscellanous(miscellanous);
		}
		
		/*****************************************************************************************/
		String[] subcatIds = {"Languages","Languages",
				"Mathematics","Sciences",
				"Sciences","Sciences",
				"Humanities","Humanities",
				"Humanities","Technicals",
				"Technicals","Technicals","Technicals"};
		String[] subCodes = {"ENG","KIS","MAT","CHE","PHY","BIO","HIS","CRE","GEO","B/S","AGR","HSC","COM"};
		String[] numCodes = {"100","101","102","103","104","105","106","107","108","109","110","111","112"};
		String[] subDesc = {"English","Kiswahili","Mathematics","Chemistry","Physics","Biology","History",
				"Christian Religion","Geography","Business","Agriculture","Home Science","Computer Studies"};
		
		for(int count=0;count<subCodes.length;count++) {
			Subject subject = new Subject();
			subject.setUuid(subject.getUuid());
			subject.setAccountId(accountId);
			subject.setCategoryId(categoryDAO.getCategory(accountId, subcatIds[count]).getUuid());
			subject.setCode(subCodes[count]);
			subject.setNumericCode(numCodes[count]);
			subject.setDescription(subDesc[count]);
			subjectDAO.putSubject(subject);
		}
		
		/*****************************************************************************************/
		for(int count=0;count<subCodes.length;count++) {
			SubCategory subCategory = new SubCategory();
			subCategory.setUuid(subCategory.getUuid());
			subCategory.setAccountId(accountId);
			Subject subject = subjectDAO.getSubjectByCode(accountId, subCodes[count]);
			subCategory.setCategoryId(subject.getCategoryId());
			subCategory.setSubjectId(subject.getUuid());
			subCategoryDAO.putSubCategory(subCategory);
		}
		
		/*****************************************************************************************/
		String[] examCodes = {"P1","P2","P3","C1","C2","ET","P123"};
		String[] examDesc = {"Paper 1","Paper 2","Paper 3","Cat 1","Cat 2","End Term","P123"};
		int[] examOutof = {60,80,40,30,30,70,0}; 

		for(int count=0;count<examCodes.length;count++) {
			Exam exam = new Exam();
			exam.setUuid(exam.getUuid());
			exam.setAccountId(accountId);
			exam.setCode(examCodes[count]);
			exam.setDescription(examDesc[count]);
			exam.setOutOf(examOutof[count]);  
			examDAO.putExam(exam);
		}

		/*****************************************************************************************/
		SysConfig systemConfig = new SysConfig();
		systemConfig.setAccountId(accountId);
		systemConfig.setCansendSMS("0");
		systemConfig.setTerm("1");
		systemConfig.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));  
		sysConfigDAO.putSysConfig(systemConfig);
		
		/*****************************************************************************************/
		SysConfig config = sysConfigDAO.getSysConfig(accountId);
		if(termFeeDAO.getFee(accountId, config.getTerm(), config.getYear()) == null) {
			TermFee termFee = new TermFee();
			termFee.setAccountId(accountId);
			termFee.setBoaderAmount(12000);
			termFee.setDayAmount(8000);
			termFee.setTerm(config.getTerm());
			termFee.setYear(config.getYear());
			termFeeDAO.putFee(termFee, accountId, config.getTerm(), config.getYear());

		}

		/*****************************************************************************************/
		String[] apiCats = {"SMS_API","SYSTEM_API","MPESA_API"};

		for(int count=0;count<apiCats.length;count++) {
			ApiCredential apiCredential = new ApiCredential();
			apiCredential.setUuid(apiCredential.getUuid());
			apiCredential.setAccountId(accountId);
			apiCredential.setApiType(apiCats[count]); 
			apiCredential.setApiKey(RandomStringUtils.randomAlphabetic(20)); 
			apiCredential.setApisecret(RandomStringUtils.randomAlphabetic(20)); 
			apiCredentialDAO.putApiCredential(apiCredential); 
		}

        
		String[] houses = {"Mt. Kenya","Mt. Longonot"}; 
        for(int count=0;count<houses.length;count++) {
        	House house = new House();
        	house.setAccountId(accountId);
        	house.setHouseName(houses[count]);  
        	houseDAO.putHouse(house);
        }




		response.setDescription(resposne);
		response.setMessage("success");
		
		return response;
	}

	/**
	 * 
	 * @param apiAccount
	 * @return
	 */
	public Object updateAccount(ApiAccount apiAccount) {

		ApiResponse apiResponse = new ApiResponse(); 


		if(!isActiveValid(apiAccount.getIsActive())) {  
			apiResponse.setMessage("error");
			apiResponse.setDescription("Isactive can either be 1 or 0");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getName()) || apiAccount.getName().length() < 5) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School name.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getName())) {   
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school name is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMotto()) || apiAccount.getMotto().length() < 5) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School motto.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getUsername()) || apiAccount.getUsername().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School username.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getUsername())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school username is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getPassword()) || apiAccount.getPassword().length() < 4) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School password.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMobile()) || !StringUtils.isNumeric(apiAccount.getMobile()) && 
				apiAccount.getMobile().length() !=9) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School mobile number.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getMobile())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school mobile is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getEmail()) || !emailValidator.isValid(apiAccount.getEmail())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School email address.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getEmail())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school email is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getAddress()) || apiAccount.getAddress().length() < 2) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School postal address.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getTown()) || apiAccount.getTown().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid town.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsBoarding()) || !validBoarding(apiAccount.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsBoarding!.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsMixed()) || !validMixed(apiAccount.getIsMixed())) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsMixed!.");
			return apiResponse;

		}else if(accountDAO.getAccountById(apiAccount.getUuid()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;

		}else {



			Account account = accountDAO.getAccountById(apiAccount.getUuid());
			account.setIsActive(apiAccount.getIsActive()); 
			account.setName(apiAccount.getName());
			account.setMotto(apiAccount.getMotto());
			account.setWebsite(apiAccount.getWebsite());
			account.setLogo(apiAccount.getLogo());
			account.setSignature(apiAccount.getSignature());
			account.setUsername(apiAccount.getUsername());
			account.setPassword(apiAccount.getPassword());
			account.setMobile(apiAccount.getMobile());
			account.setEmail(apiAccount.getEmail());
			account.setAddress(apiAccount.getAddress());
			account.setTown(apiAccount.getTown());
			account.setIsBoarding(apiAccount.getIsBoarding());
			account.setIsMixed(apiAccount.getIsMixed()); 
			account.setLastUpdated(new Date().toString());


			if(accountDAO.updateAccount(account)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Account updated successfully");

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Something went wrong while updating the account.");
			}

		}

		return apiResponse;

	}

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getAccount(String accountId) {

		ApiResponse apiResponse = new ApiResponse(); 

		if(accountDAO.getAccountById(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;

		}else {
			ApiAccount apiAccount = new ApiAccount();

			try {
				BeanUtils.copyProperties(apiAccount, accountDAO.getAccountById(accountId));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			return apiAccount;
		}

	}
	/**
	 * 
	 * @return
	 */
	public Object getAccountList() {

		ApiResponse apiResponse = new ApiResponse(); 

		if(accountDAO.getAccounts() == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("No account found!");
			return apiResponse;

		}else {
			List<ApiAccount> ListapiAccount = new ArrayList<>();
			accountDAO.getAccounts().forEach(account -> {
				ApiAccount apiAccount = new ApiAccount();

				try {
					BeanUtils.copyProperties(apiAccount, account); 
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				ListapiAccount.add(apiAccount);
			});

			return ListapiAccount; 

		}
	}


	/**
	 * 
	 * @param query
	 * @return
	 */
	public Object getAccountInfo(String query) {
		Response response = new Response(); 
		
		if(accountDAO.getAccount(query, "1") != null) {
			/*response.setMessage("success");
			response.setDescription("Account found!");
			return response;*/
			return accountDAO.getAccount(query, "1"); 
		}else {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;
		}

	}


	/**
	 * 
	 * @param isActive
	 * @return
	 */
	private boolean isActiveValid(String isActive) {
		String[] allowed = {"0","1"};
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);

		if(allowedList.contains(isActive)) {
			return true;
		}else {
			return false;
		}
	}



	/**
	 * 
	 * @param isMixed
	 * @return
	 */
	private boolean validMixed(String isMixed) {
		String[] allowed = {"0","1"};
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);

		if(allowedList.contains(isMixed)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 
	 * @param isBoarding
	 * @return
	 */
	private boolean validBoarding(String isBoarding) {
		String[] allowed = {"0","1","2"}; 
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);

		if(allowedList.contains(isBoarding)) {
			return true;
		}else {
			return false;
		}

	}



	/**
	 * to detect duplicate value
	 * 
	 * @param value
	 * @return
	 */
	private boolean hasDuplicate(String value) { 
		List<Account> accountList = new ArrayList<>();
		//if not account with such a key, return true and proceed

		if(accountDAO.findAccountDuplicate(value) == null) { 
			return false;
		}else {
			accountList = accountDAO.findAccountDuplicate(value); 
			//System.out.println("size: " + accountList.size() + " key: " + value ); 
			//if only one account has such a key, return true and proceed
			if(accountList.size() == 1) {
				return false;

				//if you reach here, there are more than one accounts sharing the provided key, return false.
			}else if(accountList.size() > 1) {

				return true;

			}else if(accountList.size() == 0) {
				return false;

			}else {
				return false;

			}
		}
	}


}
