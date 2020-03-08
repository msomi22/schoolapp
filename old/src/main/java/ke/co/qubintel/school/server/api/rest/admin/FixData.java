package ke.co.qubintel.school.server.api.rest.admin;

import ke.co.qubintel.school.server.bean.subject.SubCategory;
import ke.co.qubintel.school.server.bean.subject.Subject;
import ke.co.qubintel.school.server.persistence.subject.CategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubCategoryDAO;
import ke.co.qubintel.school.server.persistence.subject.SubjectDAO;

public class FixData {
	
	private static SubCategoryDAO subCategoryDAO;
	private static SubjectDAO subjectDAO;
	//private static CategoryDAO categoryDAO;
	
	static {
		subCategoryDAO = new SubCategoryDAO("schooldb", "localhost", "school", "AllaManO1", 5432);
		subjectDAO = new SubjectDAO("schooldb", "localhost", "school", "AllaManO1", 5432);
		//categoryDAO = new CategoryDAO();
	}

	public FixData() {
		
	}

	public static void main(String[] args) {
		
		String accountId = "22bf25b1-23f4-4ed0-a9f4-46a5d7f7d65d";
		
		String[] subCodes = {"ENG","KIS","MAT","CHE","PHY","BIO","HIS","CRE","GEO","B/S","AGR","HSC","COM"};
		
		
		for(int count=0;count<subCodes.length;count++) {
			SubCategory subCategory = new SubCategory();
			subCategory.setUuid(subCategory.getUuid());
			subCategory.setAccountId(accountId);
			Subject subject = subjectDAO.getSubjectByCode(accountId, subCodes[count]);
			subCategory.setCategoryId(subject.getCategoryId());
			subCategory.setSubjectId(subject.getUuid());
			subCategoryDAO.putSubCategory(subCategory);
		}

	}

}
