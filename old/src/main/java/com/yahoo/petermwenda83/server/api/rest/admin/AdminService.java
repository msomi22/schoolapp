/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import com.google.gson.Gson;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.account.Miscellanous;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.staff.AcessLevel;
import com.yahoo.petermwenda83.bean.subject.Category;
import com.yahoo.petermwenda83.bean.subject.SubCategory;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.admin.ApiAccount;

/**
 * @author peter
 *
 */
public class AdminService {

	private static AccountDAO accountDAO;
	private static EmailValidator emailValidator;

	static {
		accountDAO = AccountDAO.getInstance();
		emailValidator = EmailValidator.getInstance();
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


		if(StringUtils.isBlank(apiAccount.getName()) && apiAccount.getName().length() < 5) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School name.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getName(), "1") !=null) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school name is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMotto()) && apiAccount.getMotto().length() < 5) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School motto.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getUsername()) && apiAccount.getUsername().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School username.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getUsername(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school username is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getPassword())&& apiAccount.getPassword().length() < 4) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School password.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMobile()) && !StringUtils.isNumeric(apiAccount.getMobile()) && 
				apiAccount.getMobile().length() !=9) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School mobile number.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getMobile(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school mobile is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getEmail()) && !emailValidator.isValid(apiAccount.getEmail())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School email address.");
			return apiResponse;

		}else if(accountDAO.getAccount(apiAccount.getEmail(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school email is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getAddress()) && apiAccount.getAddress().length() < 2) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School postal address.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getTown()) && apiAccount.getTown().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid town.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsBoarding!.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsMixed())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsMixed!.");
			return apiResponse;

		}else {



			Account account = new Account();
			account = apiAccount;

			account.setIsActive("1"); 

			if(accountDAO.putAccount(account)) {

				boolean pop = pupulateDefaluts(account.getUuid()); 

				if(pop) {
					apiResponse.setMessage("success");
					apiResponse.setDescription("Account registered successfully");
					return apiResponse;
				}else {
					apiResponse.setMessage("success");
					apiResponse.setDescription("Account registered successfully, but some default data NOT set!!!");
					return apiResponse;
				}





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
	private boolean pupulateDefaluts(String accountId) {
		//TODO

		/**
		 * 1. AcessLevel
		 * 2. Category
		 * 3. classRoom
		 * 4. GradingSystem
		 * 5. Miscellaneous (Miscellan^ous) 'e'
		 * 6. subCategory
		 * 7. Subject
		 * 8. 
		 * 
		 */

		String[] accessIds = {"C3915245-00EE-4EF4-9898-ACE59683DD60","615F04C1-00BF-499C-AC7A-B46B69243AAA",
				"0DE968C9-7309-C481-58F7-AB6CDB1011EH","1CC7F06E-9938-4850-81FB-9CC249C7CFA2",
				"BDF7F33D-1936-43F3-B14B-8FC3EA3A1265","64553348-3229-4869-A13D-CADFC1D3AF46",
		"0DE968C9-7309-C481-58F7-AB6CDB1011EF"};
		String[] access = {"Principal","Deputy Principal","CM","HOD","Teacher","Secretary","Bursar"};

		for(int count=0;count<accessIds.length;count++) {
			AcessLevel acessLevel = new AcessLevel();
			acessLevel.setUuid(accessIds[count]); 
			acessLevel.setAccountId(accountId);
			acessLevel.setDescription(access[count]); 
			//TODO , put
		}


		String[] categoryIds = {"3F0330CD-47F9-42B4-B736-0E11CBB4988A","44B7A7B3-4DAE-44A9-86FB-70FE1A6D31C1",
				"BCD7AFBC-B5B0-45CE-806C-64051F4C6D1F","6DAAC70F-C6A5-4DD7-85AE-B928946132EA",
				"B8C59DA7-1013-4879-B40A-630F0E647497","55DD5463-6ECB-48A3-B6E7-03548A9E37FE"};

		String[] categorys = {"Languages","Sciences","Humanities","Technicals","Mathematics","General"};

		int[] maxNo = {2,3,3,4,2,0};

		for(int count=0;count<categoryIds.length;count++) {
			Category category = new Category();
			category.setUuid(categoryIds[count]);
			category.setAccountId(accountId);
			category.setMaxNo(String.valueOf(maxNo[count]));
			category.setDescription(categorys[count]);
			//TODO put
		}

		String[] classRoomIds = {"C143978A-E021-4015-BC67-5A00D6C910D1","3E22E428-3155-42F5-B73E-66553ED501C9",
				"A4BFC2BD-262F-4207-99C8-057D6ADF80C7","14E56350-08DA-45CC-97D9-C225AF74A7AD"};

		String[] classes = {"FORM 1","FORM 2","FORM 3","FORM 4"};

		for(int count=0;count<classRoomIds.length;count++) {
			ClassRoom classRoom = new ClassRoom();
			classRoom.setUuid(classRoomIds[count]);
			classRoom.setAccountId(accountId);
			classRoom.setDescription(classes[count]); 

			//TODO put
		}


		String[] gradingSystemIds = {"1EABC062-DC76-42FC-A817-52D89C8CDAE9","C8593353-1810-46DF-897C-1173537B78CC",
				"ED2F088A-0D1F-45F5-88DC-CB9552FD44F5","323556CF-3695-449E-86F4-9515CC58A267",
				"CDBCEC15-3EE0-43EF-B384-F4014E83F89D","4F819276-09F7-4B53-99E9-65FADF75F7D2",
				"FB769B43-B092-42E4-9936-A0716F28B25D","A058D7E4-0233-4BE7-990A-D3CB229164B5",
				"1C9DD8CF-14B2-4BF2-AACC-6B6CBEB98998","3C212576-FEB0-47B3-A3F8-71C623D38064",
				"129BAE9E-BBE9-4FBA-B67E-BEE11FF3C1C6","0E97899E-99F2-468F-86A5-9E59F7A224DC"};

		String gcateId = "55DD5463-6ECB-48A3-B6E7-03548A9E37FE";

		int[] lowerLimits = {83,75,66,56,54,48,42,40,35,31,26,1};
		int[] upperLimits = {100,82,74,65,55,53,47,41,39,34,30,25};
		int[] points = {12,11,10,9,8,7,6,5,4,3,2,1};
		String[] desc = {"A","A-","B+","B","B-","C+","C","C-","D+","D","D-","E"};

		for(int count=0;count<gradingSystemIds.length;count++) {
			GradingSystem gradingSystem = new GradingSystem();
			gradingSystem.setUuid(gradingSystemIds[count]);
			gradingSystem.setAccountId(accountId);
			gradingSystem.setCategoryId(gcateId);
			gradingSystem.setLowerLimit(lowerLimits[count]);
			gradingSystem.setUpperLimit(upperLimits[count]);
			gradingSystem.setPoints(points[count]);
			gradingSystem.setDescription(desc[count]);
			//TODO put

		}




		String[] miscellanousIds = {"6A017FB8-5E19-4441-A3DA-B3EB780E81A1","7B6C4D4E-DE72-4F81-A166-6AF01C5B11D5","5B0F3957-0B88-45C9-8772-7F7A94E16DBF"};
		String[] keys = {"CLOSING_DATE","OPENING_DATE","HEAD_TEACHER_REMARKS"};
		String[] values = {"Tue 03 April 2016","Wed 07 May 2016","for the fantastic term it has been awesome to see you grow and develop hope you have a wonderful holiday .For your performance all we can say is ..."};
		

		for(int count=0;count<miscellanousIds.length;count++) {
			Miscellanous miscellanous = new Miscellanous();
			miscellanous.setUuid(miscellanousIds[count]);
			miscellanous.setAccountId(accountId);
			miscellanous.setKey(keys[count]);
			miscellanous.setValue(values[count]);
		}

		String[] uuids = {"45207ABB-C547-43B6-A1FD-E9359C0F8DDF","D6C95E77-6B48-416C-AD3F-2752EB20EF23",
				"B517BB4D-3E7F-4879-AAEB-297B43C0FD1F","FB824121-1003-44AF-B283-F163A5FC5E8F",
				"4BB37A08-D180-47DF-8401-B3162F84E23F","D71F66A7-DCDA-4D54-BA42-612596B30E52",
				"12039F0B-A39F-4399-B2CA-19A3D10F0A4D","82B17C63-6BBA-4B5C-B387-43DD1E74B2B1",
				"7DBC3E02-DB92-4A34-A506-D2ED184F02A9","B393510A-2D03-44D1-8A0C-73FBEC1BEDD7",
				"E96657DE-DFEB-4073-AA93-4F83155DCD09","FE1F19D5-C88D-411E-9543-40CE59979BEC","E8246333-CD6C-48CE-8E12-16FDCD87C903"};
		String[] catIds = {"3F0330CD-47F9-42B4-B736-0E11CBB4988A","3F0330CD-47F9-42B4-B736-0E11CBB4988A",
				"B8C59DA7-1013-4879-B40A-630F0E647497",
				"44B7A7B3-4DAE-44A9-86FB-70FE1A6D31C1","44B7A7B3-4DAE-44A9-86FB-70FE1A6D31C1","44B7A7B3-4DAE-44A9-86FB-70FE1A6D31C1",
				"BCD7AFBC-B5B0-45CE-806C-64051F4C6D1F","BCD7AFBC-B5B0-45CE-806C-64051F4C6D1F","BCD7AFBC-B5B0-45CE-806C-64051F4C6D1F",
				"6DAAC70F-C6A5-4DD7-85AE-B928946132EA","6DAAC70F-C6A5-4DD7-85AE-B928946132EA","6DAAC70F-C6A5-4DD7-85AE-B928946132EA","6DAAC70F-C6A5-4DD7-85AE-B928946132EA"};
		String[] subIds = {"D0F7EC32-EA25-7D32-8708-2CC132446","66027e51-b1ad-4b10-8250-63af64d23323","4f59580d-1a16-4669-9ed5-4b89615d6903",
				"552c0a24-6038-440f-add5-2dadfb9a23bd","44f23b3c-e066-4b45-931c-0e8073d3a93a","de0c86be-9bcb-4d3b-8098-b06687536c1f",
				"c9caf109-c27d-4062-9b9f-ac4268629e27","f098e943-26fd-4dc0-b6a0-2d02477004a4","0e5dc1c6-f62f-4a36-a1ec-064173332694",
				"e1729cc2-524a-4069-b4a4-be5aec8473fe","b9bbd718-b32f-4466-ab34-42f544ff900e","C1F28FF4-1A18-4552-822A-7A4767643643","F1972BF2-C788-4F41-94FE-FBA1869C92BC"};

		for(int count=0;count<uuids.length;count++) {

			SubCategory subCategory = new SubCategory();
			subCategory.setUuid(uuids[count]);
			subCategory.setAccountId(accountId);
			subCategory.setCategoryId(catIds[count]);
			subCategory.setSubjectId(subIds[count]);
		}


		

		String[] subjectIds = {"D0F7EC32-EA25-7D32-8708-2CC132446","66027e51-b1ad-4b10-8250-63af64d23323",
				"4f59580d-1a16-4669-9ed5-4b89615d6903","552c0a24-6038-440f-add5-2dadfb9a23bd",
				"44f23b3c-e066-4b45-931c-0e8073d3a93a","de0c86be-9bcb-4d3b-8098-b06687536c1f",
				"c9caf109-c27d-4062-9b9f-ac4268629e27","f098e943-26fd-4dc0-b6a0-2d02477004a4",
				"0e5dc1c6-f62f-4a36-a1ec-064173332694","e1729cc2-524a-4069-b4a4-be5aec8473fe",
				"b9bbd718-b32f-4466-ab34-42f544ff900e","C1F28FF4-1A18-4552-822A-7A4767643643","F1972BF2-C788-4F41-94FE-FBA1869C92BC"};
		String[] subcatIds = {"3F0330CD-47F9-42B4-B736-0E11CBB4988A","3F0330CD-47F9-42B4-B736-0E11CBB4988A",
				"B8C59DA7-1013-4879-B40A-630F0E647497","44B7A7B3-4DAE-44A9-86FB-70FE1A6D31C1",
				"44B7A7B3-4DAE-44A9-86FB-70FE1A6D31C1","44B7A7B3-4DAE-44A9-86FB-70FE1A6D31C1",
				"BCD7AFBC-B5B0-45CE-806C-64051F4C6D1F","BCD7AFBC-B5B0-45CE-806C-64051F4C6D1F",
				"BCD7AFBC-B5B0-45CE-806C-64051F4C6D1F","6DAAC70F-C6A5-4DD7-85AE-B928946132EA",
				"6DAAC70F-C6A5-4DD7-85AE-B928946132EA","6DAAC70F-C6A5-4DD7-85AE-B928946132EA","6DAAC70F-C6A5-4DD7-85AE-B928946132EA"};
		String[] subCodes = {"ENG","KIS","MAT","CHE","PHY","BIO","HIS","CRE","GEO","B/S","AGR","HSC","COM"};
		String[] numCodes = {"100","101","102","103","104","105","106","107","108","109","110","111","112"};
		String[] subDesc = {"English","Kiswahili","Mathematics","Chemistry","Physics","Biology","History",
				"Christian Religion","Geography","Business","Agriculture","Home Science","Computer Studies"};

		for(int count=0;count<subjectIds.length;count++) {
			Subject subject = new Subject();
			subject.setUuid(subjectIds[count]);
			subject.setAccountId(accountId);
			subject.setCategoryId(subcatIds[count]);
			subject.setCode(subCodes[count]);
			subject.setNumericCode(numCodes[count]);
			subject.setDescription(subDesc[count]);
		}


		return false;
	}

	/**
	 * 
	 * @param apiAccount
	 * @return
	 */
	public Object updateAccount(ApiAccount apiAccount) {

		ApiResponse apiResponse = new ApiResponse(); 


		if(StringUtils.isBlank(apiAccount.getName()) && apiAccount.getName().length() < 5) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School name.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getName(),apiAccount.getAccountId())) {  
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school name is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMotto()) && apiAccount.getMotto().length() < 5) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School motto.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getUsername()) && apiAccount.getUsername().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School username.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getUsername(),apiAccount.getAccountId())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school username is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getPassword())&& apiAccount.getPassword().length() < 4) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School password.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getMobile()) && !StringUtils.isNumeric(apiAccount.getMobile()) && 
				apiAccount.getMobile().length() !=9) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School mobile number.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getMobile(),apiAccount.getAccountId())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school mobile is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getEmail()) && !emailValidator.isValid(apiAccount.getEmail())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School email address.");
			return apiResponse;

		}else if(hasDuplicate(apiAccount.getEmail(),apiAccount.getAccountId())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school email is in use.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getAddress()) && apiAccount.getAddress().length() < 2) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School postal address.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getTown()) && apiAccount.getTown().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid town.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsBoarding!.");
			return apiResponse;

		}else if(StringUtils.isBlank(apiAccount.getIsMixed())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsMixed!.");
			return apiResponse;

		}else if(accountDAO.getAccountById(apiAccount.getAccountId()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;

		}else {



			Account account = accountDAO.getAccountById(apiAccount.getAccountId());
			account = apiAccount;

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
	 * @param data
	 */
	public Object putData(String data) {

		ApiResponse apiResponse = new ApiResponse(); 

		ApiAccData apiAccData = new ApiAccData();
		Gson gson = new Gson();
		apiAccData = gson.fromJson(data, ApiAccData.class);



		AccData accData = new AccData();
		accData.setAddDate(apiAccData.getAddDate());
		accData.setPitch(apiAccData.getPitch());
		accData.setRoll(apiAccData.getRoll());
		accData.setYaw(apiAccData.getYaw());

		//System.out.println(accData); 


		if(accountDAO.putAccData(accData)) {
			apiResponse.setMessage("success");
			apiResponse.setDescription("Data saved successfully.");

		}else {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Data NOT saved.");
		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public List<ApiAccData> getAccData(){
		List<AccData> list = new  ArrayList<>(); 

		if(accountDAO.getAccData() != null) {
			list = accountDAO.getAccData();

		}

		List<ApiAccData> apiAccDataList =  new  ArrayList<>(); 

		list.forEach(data -> {

			ApiAccData apiAccData = new ApiAccData();
			apiAccData.setUuid(data.getUuid());
			apiAccData.setAddDate(data.getAddDate());
			apiAccData.setPitch(data.getPitch());
			apiAccData.setRoll(data.getRoll());
			apiAccData.setYaw(data.getYaw());

			apiAccDataList.add(apiAccData);


		});

		return apiAccDataList;
	}


	/**
	 * to detect duplicate value
	 * 
	 * @param value
	 * @param accountid
	 * @return
	 */
	private boolean hasDuplicate(String value, String accountId) { 
		Account account = new Account();
		if(accountDAO.getAccount(value, "1") == null) {
			return true;
		}else {
			account = accountDAO.getAccount(value, "1");
			if(StringUtils.equals(account.getAccountId(), accountId)) {
				return true;
			}else {
				return false;
			}

		}
	}



}
