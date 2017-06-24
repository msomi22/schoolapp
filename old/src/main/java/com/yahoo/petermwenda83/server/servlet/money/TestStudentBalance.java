/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.util.Date;
import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;

/**
 * @author peter
 *
 */
public class TestStudentBalance {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private TermFeeDAO termFeeDAO;
	private SysConfigDAO sysConfigDAO;
	private StudentFeeDAO studentFeeDAO;
	private StudentOtherFeeDAO studentOtherFeeDAO;
	
	private final Date ADMISSION_DATE = new Date(new Long("1462609723000") );  
	private final String REG_TERM = "3";
	private final String STUDENT_UUID = "91318D8C-8150-49B4-A473-CF97F104F6B7";//June/916/Day
	private final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	private final int FINAL_YEAR = 2019;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.server.servlet.money.StudentBalance#findBalance(com.yahoo.petermwenda83.persistence.money.TermFeeDAO, com.yahoo.petermwenda83.persistence.exam.SysConfigDAO, com.yahoo.petermwenda83.persistence.money.StudentFeeDAO, com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO, java.util.Date, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	//@Ignore
	@Test
	public void testFindBalance() {
		termFeeDAO = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		sysConfigDAO = new SysConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentFeeDAO = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		studentOtherFeeDAO = new StudentOtherFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		StudentBalance studentBalance = new StudentBalance();
		System.out.println("Balance: " + studentBalance.findBalance(termFeeDAO, sysConfigDAO, studentFeeDAO,
				studentOtherFeeDAO, ADMISSION_DATE, REG_TERM, STUDENT_UUID, SCHOOL_UUID, FINAL_YEAR));
		
		
	}

}

/**     904=AEED7469-F7AF-41E6-B24C-13B1D74E1026,2
 *      905=CF64EBB2-BCC2-4EAA-B1C8-060EEE3284F3,2
 *      907=87D0EA99-7042-4867-A4B7-C31E7665DE5E,2
 *      908=6912F4C6-9370-440A-BA87-11A8426BA92C,3
 *      909=91318D8C-8150-49B4-A473-CF97F104F6B7,3
 *      
"4F218688-6DE5-4E69-8690-66FBA2F0DC9F";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"901";"JORAM";"NDUNGU";"MURIITHI";"MALE";"11/06/94";"L2374";"Nyeri";"1"
"A195BAF6-D6E7-43A5-B7C9-D6C627A42815";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"902";"PETER";"NJERU";"MWENDA";"MALE";"12/07/92";"A78892";"Tharaka Nithi";"1"
"8A1B5BDB-9589-4207-8345-A811FC18B2C9";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"903";"JOHN";"NGANGA";"MURIITHI";"MALE";"09/07/93";"A73893";"Nyeri";"1"
"AEED7469-F7AF-41E6-B24C-13B1D74E1026";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"904";"DENIS";"GIKUNDA";"MUTUMA";"MALE";"04/05/95";"A77388";"Meru";"2"
"CF64EBB2-BCC2-4EAA-B1C8-060EEE3284F3";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"905";"BETTY";"MWEBIA";"KANANA";"FEMALE";"06/08/96";"L98828";"Meru";"2"
"7BF6983F-CF8D-4897-8122-7CCD0F778CAF";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"906";"DORIS";"NDUNGU";"NDUTA";"FEMALE";"03/06/94";"L98282";"Muranga";"2"
"87D0EA99-7042-4867-A4B7-C31E7665DE5E";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"907";"JOYCE";"NJUGUNA";"NJOKI";"FEMALE";"07/07/94";"A77494";"Nakuru";"2"
"6912F4C6-9370-440A-BA87-11A8426BA92C";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"908";"DANIEL";"MIGWI";"NDUNG'U";"MALE";"02/23/94";"L83773";"Nyeri";"3"
"91318D8C-8150-49B4-A473-CF97F104F6B7";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"909";"DENNIS";"MUGAMBI";"MURIITHI";"MALE";"04/20/93";"A76733";"Tharaka Nithi";"3"
"DD9AF1EF-EEEC-49D3-A43F-802D7028BFD7";"E3CDC578-37BA-4CDB-B150-DAB0409270CD";"85C6F08E-902C-46C2-8746-8C50E7D11E2E";"4DA86139-6A72-4089-8858-6A3A613FDFE6";"910";"DANCAN";"KAMAU";"WANGUI";"MALE";"11/06/94";"L23772";"Nakuru";"3"
**/
