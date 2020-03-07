
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
package com.yahoo.petermwenda83.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.exam.CatOne;
import com.yahoo.petermwenda83.bean.exam.CatTwo;
import com.yahoo.petermwenda83.bean.exam.Common;
import com.yahoo.petermwenda83.bean.exam.EndTerm;
import com.yahoo.petermwenda83.bean.exam.PaperOne;
import com.yahoo.petermwenda83.bean.exam.PaperThree;
import com.yahoo.petermwenda83.bean.exam.PaperTwo;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.persistence.GenericDAO;
//import com.yahoo.petermwenda83.server.servlet.upload.ExcelUtil;

/**
 * Persistence implementation for {@link SchoolExamEngineDAO}
 * 
 * Copyright (c) FasTech Solutions Ltd., Dec 02, 2015
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamEgineDAO extends GenericDAO implements SchoolExamEngineDAO {

	private static ExamEgineDAO examEgineDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static ExamEgineDAO getInstance() {

		if (examEgineDAO == null) {
			examEgineDAO = new ExamEgineDAO();
		}
		return examEgineDAO;
	}

	/**
	 * 
	 */
	public ExamEgineDAO() {
		super();
	}

	/**
	 * 
	 */
	public ExamEgineDAO(String databaseName, String Host, String databaseUsername, String databasePassword,
			int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#getCatOne(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public Common getCommon(String schoolAccountUuid, String classRoomUuid, String studentUuid, String subjectUuid,
			String Term, String Year) {
		Common common = null;
		ResultSet rset = null;
		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Perfomance"
						+ " WHERE SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ? "
						+ " AND SubjectUuid = ? AND Term = ? AND Year = ?;");

		) {

			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, studentUuid);
			pstmt.setString(4, subjectUuid);
			pstmt.setString(5, Term);
			pstmt.setString(6, Year);
			rset = pstmt.executeQuery();
			while (rset.next()) {

				common = beanProcessor.toBean(rset, Common.class);
			}

		} catch (SQLException e) {
			logger.error("SQL Exception when getting Common: " + common);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return common;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#hasCatOne(com.yahoo.petermwenda83.bean.exam.CatOne)
	 */
	@Override
	public boolean Checker(String schoolAccountUuid, String classRoomUuid, String studentUuid, String subjectUuid,
			String Term, String Year) {
		boolean studentexist = false;
		String school = "";
		String classroom = "";
		String student = "";
		String subject = "";
		String term = "";
		String year = "";
		//ExcelUtil.sleep(1); 
		ResultSet rset = null;
		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT SchoolAccountUuid,classRoomUuid,StudentUuid,SubjectUuid,Term,Year FROM Perfomance "
								+ "WHERE SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ?  AND SubjectUuid = ? AND Term = ? AND Year = ?;");) {

			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, studentUuid);
			pstmt.setString(4, subjectUuid);
			pstmt.setString(5, Term);
			pstmt.setString(6, Year);
			rset = pstmt.executeQuery();
			//ExcelUtil.sleep(1); 
			if (rset.next()) {
				school = rset.getString("SchoolAccountUuid");
				classroom = rset.getString("classRoomUuid");
				student = rset.getString("StudentUuid");
				subject = rset.getString("SubjectUuid");
				term = rset.getString("Term");
				year = rset.getString("Year");

				studentexist = (school != schoolAccountUuid && classroom != classRoomUuid && student != studentUuid
						&& subject != subjectUuid && term != Term && year != Year) ? true : false;

			}

		} catch (SQLException e) {
			logger.error("SQL Exception while getting score for  Perfomance: ");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));

		}

		return studentexist;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#putCatOne(com.yahoo.petermwenda83.bean.exam.CatOne)
	 */
	@Override
	public boolean putScore(Perfomance perfomance, String schoolAccountUuid, String classRoomUuid, String studentUuid,
			String subjectUuid, String Term, String Year) {
		boolean success = true;
		//ExcelUtil.sleep(1);
		if (!Checker(schoolAccountUuid, classRoomUuid, studentUuid, subjectUuid, Term, Year)) {
			
			try (Connection conn = dbutils.getConnection();

					PreparedStatement pstmtCatOne = conn.prepareStatement("INSERT INTO Perfomance"
							+ "(SchoolAccountUuid,StudentUuid,SubjectUuid,classRoomUuid,ClassesUuid,CatOne,TeacherUuid,Term,Year) VALUES (?,?,?,?,?,?,?,?,?);");

					PreparedStatement pstmtCatTwo = conn.prepareStatement("INSERT INTO Perfomance"
							+ "(SchoolAccountUuid,StudentUuid,SubjectUuid,classRoomUuid,ClassesUuid,CatTwo,TeacherUuid,Term,Year) VALUES (?,?,?,?,?,?,?,?,?);");

					PreparedStatement pstmtEndTerm = conn.prepareStatement("INSERT INTO Perfomance"
							+ "(SchoolAccountUuid,StudentUuid,SubjectUuid,classRoomUuid,ClassesUuid,EndTerm,TeacherUuid,Term,Year) VALUES (?,?,?,?,?,?,?,?,?);");

					PreparedStatement pstmtPaperOne = conn.prepareStatement("INSERT INTO Perfomance"
							+ "(SchoolAccountUuid,StudentUuid,SubjectUuid,classRoomUuid,ClassesUuid,PaperOne,TeacherUuid,Term,Year) VALUES (?,?,?,?,?,?,?,?,?);");

					PreparedStatement pstmtPaperTwo = conn.prepareStatement("INSERT INTO Perfomance"
							+ "(SchoolAccountUuid,StudentUuid,SubjectUuid,classRoomUuid,ClassesUuid,PaperTwo,TeacherUuid,Term,Year) VALUES (?,?,?,?,?,?,?,?,?);");

					PreparedStatement pstmtPaperThree = conn.prepareStatement("INSERT INTO Perfomance"
							+ "(SchoolAccountUuid,StudentUuid,SubjectUuid,classRoomUuid,ClassesUuid,PaperThree,TeacherUuid,Term,Year) VALUES (?,?,?,?,?,?,?,?,?);");) {

				if (perfomance instanceof CatOne) {
					pstmtCatOne.setString(1, schoolAccountUuid);
					pstmtCatOne.setString(2, studentUuid);
					pstmtCatOne.setString(3, subjectUuid);
					pstmtCatOne.setString(4, classRoomUuid);
					pstmtCatOne.setString(5, perfomance.getClassesUuid());
					pstmtCatOne.setDouble(6, perfomance.getCatOne());
					pstmtCatOne.setString(7, perfomance.getTeacherUuid());
					pstmtCatOne.setString(8, Term);
					pstmtCatOne.setString(9, Year);
					pstmtCatOne.executeUpdate();
				} else if (perfomance instanceof CatTwo) {
					pstmtCatTwo.setString(1, schoolAccountUuid);
					pstmtCatTwo.setString(2, studentUuid);
					pstmtCatTwo.setString(3, subjectUuid);
					pstmtCatTwo.setString(4, classRoomUuid);
					pstmtCatTwo.setString(5, perfomance.getClassesUuid());
					pstmtCatTwo.setDouble(6, perfomance.getCatTwo());
					pstmtCatTwo.setString(7, perfomance.getTeacherUuid());
					pstmtCatTwo.setString(8, Term);
					pstmtCatTwo.setString(9, Year);
					pstmtCatTwo.executeUpdate();
				} else if (perfomance instanceof EndTerm) {
					pstmtEndTerm.setString(1, schoolAccountUuid);
					pstmtEndTerm.setString(2, studentUuid);
					pstmtEndTerm.setString(3, subjectUuid);
					pstmtEndTerm.setString(4, classRoomUuid);
					pstmtEndTerm.setString(5, perfomance.getClassesUuid());
					pstmtEndTerm.setDouble(6, perfomance.getEndTerm());
					pstmtEndTerm.setString(7, perfomance.getTeacherUuid());
					pstmtEndTerm.setString(8, Term);
					pstmtEndTerm.setString(9, Year);
					pstmtEndTerm.executeUpdate();
				} else if (perfomance instanceof PaperOne) {
					pstmtPaperOne.setString(1, schoolAccountUuid);
					pstmtPaperOne.setString(2, studentUuid);
					pstmtPaperOne.setString(3, subjectUuid);
					pstmtPaperOne.setString(4, classRoomUuid);
					pstmtPaperOne.setString(5, perfomance.getClassesUuid());
					pstmtPaperOne.setDouble(6, perfomance.getPaperOne());
					pstmtPaperOne.setString(7, perfomance.getTeacherUuid());
					pstmtPaperOne.setString(8, Term);
					pstmtPaperOne.setString(9, Year);
					pstmtPaperOne.executeUpdate();
				} else if (perfomance instanceof PaperTwo) {
					pstmtPaperTwo.setString(1, schoolAccountUuid);
					pstmtPaperTwo.setString(2, studentUuid);
					pstmtPaperTwo.setString(3, subjectUuid);
					pstmtPaperTwo.setString(4, classRoomUuid);
					pstmtPaperTwo.setString(5, perfomance.getClassesUuid());
					pstmtPaperTwo.setDouble(6, perfomance.getPaperTwo());
					pstmtPaperTwo.setString(7, perfomance.getTeacherUuid());
					pstmtPaperTwo.setString(8, Term);
					pstmtPaperTwo.setString(9, Year);
					pstmtPaperTwo.executeUpdate();
				} else if (perfomance instanceof PaperThree) {
					pstmtPaperThree.setString(1, schoolAccountUuid);
					pstmtPaperThree.setString(2, studentUuid);
					pstmtPaperThree.setString(3, subjectUuid);
					pstmtPaperThree.setString(4, classRoomUuid);
					pstmtPaperThree.setString(5, perfomance.getClassesUuid());
					pstmtPaperThree.setDouble(6, perfomance.getPaperThree());
					pstmtPaperThree.setString(7, perfomance.getTeacherUuid());
					pstmtPaperThree.setString(8, Term);
					pstmtPaperThree.setString(9, Year);
					pstmtPaperThree.executeUpdate();
				}

			} catch (SQLException e) {
				logger.error("SQL Exception trying to put Perfomance" + perfomance);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			}

		} else {

			try (Connection conn = dbutils.getConnection();
					PreparedStatement pstmtCatOne = conn
							.prepareStatement("UPDATE Perfomance SET CatOne =?,TeacherUuid =?"
									+ "WHERE Term =? AND Year =? AND SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ? AND SubjectUuid = ?  ; ");

					PreparedStatement pstmtCatTwo = conn
							.prepareStatement("UPDATE Perfomance SET CatTwo =?,TeacherUuid =?"
									+ "WHERE Term =? AND Year =? AND SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ? AND SubjectUuid = ?  ; ");

					PreparedStatement pstmtEndTerm = conn
							.prepareStatement("UPDATE Perfomance SET EndTerm =?,TeacherUuid =?"
									+ "WHERE Term =? AND Year =? AND SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ? AND SubjectUuid = ?  ; ");

					PreparedStatement pstmtPaperOne = conn
							.prepareStatement("UPDATE Perfomance SET PaperOne =?,TeacherUuid =?"
									+ "WHERE Term =? AND Year =? AND SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ? AND SubjectUuid = ?  ; ");

					PreparedStatement pstmtPaperTwo = conn
							.prepareStatement("UPDATE Perfomance SET PaperTwo =?,TeacherUuid =?"
									+ "WHERE Term =? AND Year =? AND SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ? AND SubjectUuid = ?  ; ");

					PreparedStatement pstmtPaperThree = conn
							.prepareStatement("UPDATE Perfomance SET PaperThree =?,TeacherUuid =?"
									+ "WHERE Term =? AND Year =? AND SchoolAccountUuid = ? AND classRoomUuid = ? AND StudentUuid = ? AND SubjectUuid = ?  ; ");

			) {
				if (perfomance instanceof CatOne) {

					pstmtCatOne.setDouble(1, perfomance.getCatOne());
					pstmtCatOne.setString(2, perfomance.getTeacherUuid());
					pstmtCatOne.setString(3, Term);
					pstmtCatOne.setString(4, Year);
					pstmtCatOne.setString(5, schoolAccountUuid);
					pstmtCatOne.setString(6, classRoomUuid);
					pstmtCatOne.setString(7, studentUuid);
					pstmtCatOne.setString(8, subjectUuid);
					pstmtCatOne.executeUpdate();
				} else if (perfomance instanceof CatTwo) {

					pstmtCatTwo.setDouble(1, perfomance.getCatTwo());
					pstmtCatTwo.setString(2, perfomance.getTeacherUuid());
					pstmtCatTwo.setString(3, Term);
					pstmtCatTwo.setString(4, Year);
					pstmtCatTwo.setString(5, schoolAccountUuid);
					pstmtCatTwo.setString(6, classRoomUuid);
					pstmtCatTwo.setString(7, studentUuid);
					pstmtCatTwo.setString(8, subjectUuid);
					pstmtCatTwo.executeUpdate();
				} else if (perfomance instanceof EndTerm) {

					pstmtEndTerm.setDouble(1, perfomance.getEndTerm());
					pstmtEndTerm.setString(2, perfomance.getTeacherUuid());
					pstmtEndTerm.setString(3, Term);
					pstmtEndTerm.setString(4, Year);
					pstmtEndTerm.setString(5, schoolAccountUuid);
					pstmtEndTerm.setString(6, classRoomUuid);
					pstmtEndTerm.setString(7, studentUuid);
					pstmtEndTerm.setString(8, subjectUuid);
					pstmtEndTerm.executeUpdate();
				} else if (perfomance instanceof PaperOne) {

					pstmtPaperOne.setDouble(1, perfomance.getPaperOne());
					pstmtPaperOne.setString(2, perfomance.getTeacherUuid());
					pstmtPaperOne.setString(3, Term);
					pstmtPaperOne.setString(4, Year);
					pstmtPaperOne.setString(5, schoolAccountUuid);
					pstmtPaperOne.setString(6, classRoomUuid);
					pstmtPaperOne.setString(7, studentUuid);
					pstmtPaperOne.setString(8, subjectUuid);
					pstmtPaperOne.executeUpdate();
				}

				else if (perfomance instanceof PaperTwo) {

					pstmtPaperTwo.setDouble(1, perfomance.getPaperTwo());
					pstmtPaperTwo.setString(2, perfomance.getTeacherUuid());
					pstmtPaperTwo.setString(3, Term);
					pstmtPaperTwo.setString(4, Year);
					pstmtPaperTwo.setString(5, schoolAccountUuid);
					pstmtPaperTwo.setString(6, classRoomUuid);
					pstmtPaperTwo.setString(7, studentUuid);
					pstmtPaperTwo.setString(8, subjectUuid);
					pstmtPaperTwo.executeUpdate();
				}

				else if (perfomance instanceof PaperThree) {

					pstmtPaperThree.setDouble(1, perfomance.getPaperThree());
					pstmtPaperThree.setString(2, perfomance.getTeacherUuid());
					pstmtPaperThree.setString(3, Term);
					pstmtPaperThree.setString(4, Year);
					pstmtPaperThree.setString(5, schoolAccountUuid);
					pstmtPaperThree.setString(6, classRoomUuid);
					pstmtPaperThree.setString(7, studentUuid);
					pstmtPaperThree.setString(8, subjectUuid);
					pstmtPaperThree.executeUpdate();
				}

			} catch (SQLException e) {
				logger.error("SQL Exception trying to update Perfomance" + perfomance);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;
			}
		}
		// System.out.println(perfomance);
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#getcatoneList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<CatOne> getcatoneList(String schoolAccountUuid, String classRoomUuid, String Term, String Year) {
		List<CatOne> list = new ArrayList<>();

		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT SchoolAccountUuid,TeacherUuid,StudentUuid,SubjectUuid,"
								+ "classRoomUuid,CatOne,Term,Year FROM Perfomance WHERE SchoolAccountUuid = ?"
								+ " AND classRoomUuid = ? AND Term = ? AND Year = ?;");) {
			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, Term);
			pstmt.setString(4, Year);
			try (ResultSet rset = pstmt.executeQuery();) {

				list = beanProcessor.toBeanList(rset, CatOne.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting List  of CatOne for school" + schoolAccountUuid + " and classroom"
					+ classRoomUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#getcatwoList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<CatTwo> getcatwoList(String schoolAccountUuid, String classRoomUuid, String Term, String Year) {
		List<CatTwo> list = new ArrayList<>();

		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT SchoolAccountUuid,TeacherUuid,StudentUuid,SubjectUuid,"
								+ "classRoomUuid,CatTwo,Term,Year FROM Perfomance WHERE SchoolAccountUuid = ?"
								+ " AND classRoomUuid = ? AND Term = ? AND Year = ?;");) {
			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, Term);
			pstmt.setString(4, Year);
			try (ResultSet rset = pstmt.executeQuery();) {

				list = beanProcessor.toBeanList(rset, CatTwo.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting List CatTwo for school" + schoolAccountUuid + " and classroom"
					+ classRoomUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#getendtermList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<EndTerm> getendtermList(String schoolAccountUuid, String classRoomUuid, String Term, String Year) {
		List<EndTerm> list = new ArrayList<>();

		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT SchoolAccountUuid,TeacherUuid,StudentUuid,SubjectUuid,"
								+ "classRoomUuid,EndTerm,Term,Year FROM Perfomance WHERE SchoolAccountUuid = ?"
								+ " AND classRoomUuid = ? AND Term = ? AND Year = ?;");) {
			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, Term);
			pstmt.setString(4, Year);
			try (ResultSet rset = pstmt.executeQuery();) {

				list = beanProcessor.toBeanList(rset, EndTerm.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting EndTerm List for school" + schoolAccountUuid + " and classroom"
					+ classRoomUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#getPaperOneList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<PaperOne> getPaperOneList(String schoolAccountUuid, String classRoomUuid, String Term, String Year) {
		List<PaperOne> list = new ArrayList<>();

		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT SchoolAccountUuid,TeacherUuid,StudentUuid,SubjectUuid,"
								+ "classRoomUuid,PaperOne,Term,Year FROM Perfomance WHERE SchoolAccountUuid = ?"
								+ " AND classRoomUuid = ? AND Term = ? AND Year = ?;");) {
			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, Term);
			pstmt.setString(4, Year);
			try (ResultSet rset = pstmt.executeQuery();) {

				list = beanProcessor.toBeanList(rset, PaperOne.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting PaperOne List for school" + schoolAccountUuid + " and classroom"
					+ classRoomUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#getPaperTwoList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<PaperTwo> getPaperTwoList(String schoolAccountUuid, String classRoomUuid, String Term, String Year) {
		List<PaperTwo> list = new ArrayList<>();

		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT SchoolAccountUuid,TeacherUuid,StudentUuid,SubjectUuid,"
								+ "classRoomUuid,PaperTwo,Term,Year FROM Perfomance WHERE SchoolAccountUuid = ? "
								+ "AND classRoomUuid = ? AND Term = ? AND Year = ?;");) {
			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, Term);
			pstmt.setString(4, Year);
			try (ResultSet rset = pstmt.executeQuery();) {

				list = beanProcessor.toBeanList(rset, PaperTwo.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting PaperTwo List for school" + schoolAccountUuid + " and classroom"
					+ classRoomUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.exam.SchoolExamEngineDAO#getpaperThreeList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<PaperThree> getpaperThreeList(String schoolAccountUuid, String classRoomUuid, String Term,
			String Year) {
		List<PaperThree> list = new ArrayList<>();

		try (Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT SchoolAccountUuid,TeacherUuid,StudentUuid,SubjectUuid,"
								+ "classRoomUuid,PaperThree,Term,Year FROM Perfomance WHERE SchoolAccountUuid = ? "
								+ "AND classRoomUuid = ? AND Term = ? AND Year = ?;");) {
			pstmt.setString(1, schoolAccountUuid);
			pstmt.setString(2, classRoomUuid);
			pstmt.setString(3, Term);
			pstmt.setString(4, Year);
			try (ResultSet rset = pstmt.executeQuery();) {

				list = beanProcessor.toBeanList(rset, PaperThree.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting PaperThree List for school" + schoolAccountUuid + " and classroom"
					+ classRoomUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

}
