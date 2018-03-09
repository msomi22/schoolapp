/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "APITeacherSubject")  //only needed if we also want to generate XML 
public class APITeacherSubject{
	
	private APISubjectClasss apiSubjectClasss;
	
	public APITeacherSubject(){
		
	}

	/**
	 * 
	 */
	public APITeacherSubject(ApiResponse response,APISubjectClasss apiSubjectClasss) {
		this.apiSubjectClasss = apiSubjectClasss;
	
	}


	/**
	 * @return the apiSubjectClasss
	 */
	public APISubjectClasss getApiSubjectClasss() {
		return apiSubjectClasss;
	}

	/**
	 * @param apiSubjectClasss the apiSubjectClasss to set
	 */
	public void setApiSubjectClasss(APISubjectClasss apiSubjectClasss) {
		this.apiSubjectClasss = apiSubjectClasss;
	}

	@Override
	public String toString() {
		return "APITeacherSubject [apiSubjectClasss=" + apiSubjectClasss + "]";
	}

	
	

}
