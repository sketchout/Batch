package com.work.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class DBUtil {
	private final static String	DRIVERNAME	= PropertiesUtil.getPorpertyValue("db.game.driverName");
	private final static String	DBURL		= PropertiesUtil.getPorpertyValue("db.game.driverURL");
	private final static String	DBUSER		= PropertiesUtil.getPorpertyValue("db.game.userid");
	private final static String	DBPWD		= PropertiesUtil.getPorpertyValue("db.game.passwd");
	
//	private final static String	WEB_DRIVERNAME	= PropertiesUtil.getPorpertyValue("db.web.driverName");
//	private final static String	WEB_DBURL			= PropertiesUtil.getPorpertyValue("db.web.driverURL");
//	private final static String	WEB_DBUSER		= PropertiesUtil.getPorpertyValue("db.web.userid");
//	private final static String	WEB_DBPWD			= PropertiesUtil.getPorpertyValue("db.web.passwd");

	static Logger				log			= Logger.getLogger(DBUtil.class);

	public static Connection openDB() {
		Connection conn = null;
		try {
//			log.info("DRIVERNAME..." +  DRIVERNAME);
//			log.info("DBURL..." + DBURL);
//			log.info("DBUSER..." + DBUSER );
			//log.info("DBPWD..." + DBPWD );
			
			Class.forName(DRIVERNAME).newInstance();
			
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
			
			log.info("openDB SUCCEED..." );
		} catch (Exception e) {
			
			log.error("openDB FAILED..." + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeDB(Connection conn) {
		try {
			if (!conn.isClosed())
				conn.close();
		} catch (Exception e) {
			log.error("closeDB FAILED.." + e.getMessage());
			
		}
	}
	
/*	public static Connection openWebDB() {
		Connection conn = null;
		try {
//			log.info("WEB_DRIVERNAME..." +  WEB_DRIVERNAME);
//			log.info("WEB_DBURL..." + WEB_DBURL);
//			log.info("WEB_DBUSER..." + WEB_DBUSER );
			//log.info("DBPWD..." + DBPWD );

			Class.forName(WEB_DRIVERNAME).newInstance();
			conn = DriverManager.getConnection(WEB_DBURL, WEB_DBUSER, WEB_DBPWD);
		} catch (Exception e) {
			log.error("openWebDB FAILED.." + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
*/
/*	public static void closeWebDB(Connection conn) {
		try {
			if (!conn.isClosed())
				conn.close();
		} catch (Exception e) {
			log.error("closeWebDB FAILED.." + e.getMessage());
		}
	}*/

	public static String getTodayDate(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		String rtnValue = "";

		sql.append(" SELECT	to_char(now(), 'yyyy-MM-dd') ");

		try {
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rtnValue = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("DBUTIL.getTodayDate", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rtnValue;
	}

	public static String getYesterDayDate(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		String rtnValue = "";

		sql.append(" SELECT	to_char((now() - '1 day'::interval), 'yyyy-MM-dd') ");

		try {
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rtnValue = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("DBUTIL.getYesterDayDate", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rtnValue;
	}

}