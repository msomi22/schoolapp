/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.safaricom;

import java.io.UnsupportedEncodingException;

import org.javalite.http.Http;
import org.javalite.http.Post;

import ke.co.qubintel.school.server.api.rest.util.JsonFromObj;

/**
 * @author peter
 *
 */
public class JavaLite {

	/**
	 * 
	 */
	public JavaLite() {
		
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {/* 
		
		String username = "demo";
		String password = "12345678";
		
		String authEncoded = SafaricomService.getAuthBase64(username,password);
		
		SubClass subclass = new SubClass();
		subclass.setAccountId("E3CDC578-37BA-4CDB-B150-DAB0409270CD");
		subclass.setStreamId("D3733507-C113-4795-91ED-D3CD8039EA03");
		subclass.setSubjectId("F1972BF2-C788-4F41-94FE-FBA1869C92BC");
		subclass.setTeacherId("5498156A-FE83-43F4-9592-737HDHJ877S"); 
		
	//	String query = JsonFromObj.getJsonStringFromObject(subclass); 
		
		byte[] content = query.getBytes("UTF-8"); 
		
		String url = "http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-737HDHJ877S/subjects";
		
		Post post = Http.post(url, content)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + authEncoded); 
		
		System.out.println(post.text());
		
	*/}

}
