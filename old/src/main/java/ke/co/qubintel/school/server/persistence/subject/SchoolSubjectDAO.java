/**
 * Account Management System
 * This software belong to Peter Mwenda's and Miwgi Ndungu's Company
 * copywrite peter&MigwiSoftwares.co.ltd
 */
package ke.co.qubintel.school.server.persistence.subject;

import java.util.List;

import ke.co.qubintel.school.server.bean.subject.Subject;



/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolSubjectDAO {

	public  Subject getSubjectById(String accountId,String uuid);
	
	public  Subject getSubject(String accountId,String query);
	
	public boolean putSubject(Subject subject);
	
	public boolean updateSubject(Subject subject);
	
	public boolean deleteSubject(String accountId,String uuid);
	
	public List<Subject> getSubjects(String accountId);
	
	
	

}
