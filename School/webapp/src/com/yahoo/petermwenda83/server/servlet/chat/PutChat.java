/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.chat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.chat.Chat;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.chat.ChatDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 *
 */
public class PutChat extends HttpServlet{

	/**  
	 * 
	 */
	private static final long serialVersionUID = 886356988312421205L;
	private Cache schoolaccountCache;
	private static ChatDAO chatDAO;
	
	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		chatDAO = ChatDAO.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		HttpSession session = request.getSession(false);

		SchoolAccount school = new SchoolAccount();

		String schoolusername = "";
		
		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
		}


		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}
		if(school !=null){

			String message = request.getParameter("messageTextArea");
			String senderIds = request.getParameter("senderId");
			String receiverIds = request.getParameter("receiverId");
			
		
			Chat chat = new Chat();
			if(!StringUtils.isBlank(message) && !StringUtils.isBlank(senderIds) && !StringUtils.isBlank(receiverIds)){
				
				//System.out.println("message = " + message);
				//System.out.println("senderIds = " + senderIds);
				//System.out.println("receiverIds = " + receiverIds);
			
				chat.setSenderUuid(StringUtils.stripToEmpty(senderIds));
				chat.setReceiverUuid(StringUtils.stripToEmpty(receiverIds));
				chat.setMessage(StringUtils.stripToEmpty(message));
				chat.setMgsStatus("Sent");
				chat.setDateSent(new Date());
				chatDAO.putChat(chat); 
			}
			
		}
	}



	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
