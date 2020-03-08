
package ke.co.qubintel.school.server.servlet.util.email;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestEmailUtil {
	
	final String[] EMAILS = {"mwendapeter72@gmail.com","mwendapeter72@gmail.com"};
	final String[] EMAILS2 = {"mwendapeter72gmail.com","mwendapeter72gmail.com"};
	final String[] EMAILS3 = {"hfgsghjfgsghfgghjfg","hgfsfghjufgdfghjk"};
	
	final String EMAIL ="mwendapeter72@gmail.com";
	final String EMAIL2 ="mwendapeter72gmail.com";
	final String EMAIL3 ="hhggggujjjiiuugyghghghj";
	
	
	
	final String FROM ="charles.thurura@alandick.co.ke";
	
	final String TO = "mwendapeter72@gmail.com,mwendapeter72@gmail.com";  
	final String[] recipientList = TO.split(",");  
	
	final String TO2 ="mwendapeter72@gmail.com";
	
	final String  CC ="mwendapeter72@gmail.com";
	final String[] recCCList = CC.split(","); 
	
	final String CC2 ="mwendapeter72@gmail.com";
	
	final String BCC = "mwendapeter72@gmail.com";
	final String[] recBCCList = BCC.split(","); 
	
	
	final String BCC2 = "mwendapeter72@gmail.com";
	
	final String SUBJECT ="emil test";
	final String BODY ="hello...!";
	
	final String OUT_E_SERVER ="10.20.200.1";
	final int OUT_E_PORT = 25;
		

	/**
	 * Commons method for {@link com.alandick.server.servlet.util.email.EmailUtil#run()}.
	 */
	@Ignore
	@Test
	public void testSendEmail() {
		EmailUtil util = new EmailUtil(FROM, recipientList, recCCList, recBCCList, SUBJECT, BODY, OUT_E_SERVER, OUT_E_PORT,
				"smtpUsername", "smtpPasswd");
		util.run();
	}
	/**
	 * Commons method for {@link com.alandick.server.servlet.util.email.EmailUtil#run()}.
	 */
	//@Ignore
	@Test
	public void testSendEmail2() {
		EmailUtil util = new EmailUtil(FROM, TO2, SUBJECT, BODY, OUT_E_SERVER, OUT_E_PORT,
				"charles.thurura@alandick.co.ke", "adc123");
		util.run();
		
	}


}
