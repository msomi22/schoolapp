
/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package ke.co.qubintel.school.server.bean.staff;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * A staff Has A position , Either a Principal ,Deputy, Hod, Teacher , ...
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class AcessLevel extends StorableBean{
	
	private String  description;
	private String  acessId;
	    
	public AcessLevel() {
		description = "";
		acessId = "";
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * @return the acessId
	 */
	public String getAcessId() {
		return acessId;
	}

	/**
	 * @param acessId the acessId to set
	 */
	public void setAcessId(String acessId) {
		this.acessId = acessId;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("AcessLevel");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",description=");
		builder.append(description);
		builder.append(",acessId=");
		builder.append(acessId);
		return builder.toString(); 
		}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -920169356050037976L;
}
