package com.work.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.work.util.DBUtil;

public class BatchRealtime {

	static Logger log = Logger.getLogger(BatchRealtime.class);
	
	public static void main(String[] args) {
		
		BatchRealtime br = new BatchRealtime();
		
		log.info("--[[ start ]]]--");
		
		String dayStr = "20161215"; 
		
		String hourStr = null;
		String minStr = null;
		
		int runResult = 0;
		
		for ( int i =0 ; i < 24; i++ ) {
			
			hourStr = "";
			
			//if ( i == 2 ) break;
			
			if ( i < 10 )hourStr += "0" + Integer.toString(i);
			else hourStr += Integer.toString(i);
			
			for( int j=0; j<60; j+= 5) {
				
				minStr = "";
				if ( j < 10 )minStr += "0" + Integer.toString(j);
				else minStr += Integer.toString(j);
				
				log.info(dayStr+hourStr+minStr);
				runResult = br.runBatch(dayStr+hourStr+minStr);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
		}
	}
	
	int runBatch(String timeStr) {
		int runResult =0 ;
		Connection conn = DBUtil.openDB();
		if ( conn == null ) {
			log.error("connection failed...");
			return -1;
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select grapestats.fn_create_realtime_stat('"+timeStr+"',true)" );
//		sql.append("select 1;");
		
		log.info("--[[" + sql.toString() + "]]--");
		
		try {
			pstmt = conn.prepareStatement( sql.toString() );
			rs = pstmt.executeQuery();

			while (rs.next()) {
				runResult = rs.getInt(1);
			}

			//System.out.println("ccu= " + ccu);
		} catch (SQLException e) {
			
			e.printStackTrace();
			log.error("procGather", e);
			
		} finally {
			
			try {
				
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

				DBUtil.closeDB(conn);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return runResult;
	}
}
