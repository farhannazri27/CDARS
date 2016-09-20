package com.onsemi.cdars.dao;

import com.onsemi.cdars.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WhStatusLogDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(WhStatusLogDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public WhStatusLogDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertWhStatusLog(WhStatusLog whStatusLog) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO cdars_wh_status_log (ref_id, module, status, status_date, created_by, flag) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, whStatusLog.getRefId());
			ps.setString(2, whStatusLog.getModule());
			ps.setString(3, whStatusLog.getStatus());
			ps.setString(4, whStatusLog.getStatusDate());
			ps.setString(5, whStatusLog.getCreatedBy());
			ps.setString(6, whStatusLog.getFlag());
			queryResult.setResult(ps.executeUpdate());
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			queryResult.setErrorMessage(e.getMessage());
			LOGGER.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return queryResult;
	}

	public QueryResult updateWhStatusLog(WhStatusLog whStatusLog) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE cdars_wh_status_log SET ref_id = ?, module = ?, status = ?, status_date = ?, created_by = ?, flag = ? WHERE id = ?"
			);
			ps.setString(1, whStatusLog.getRefId());
			ps.setString(2, whStatusLog.getModule());
			ps.setString(3, whStatusLog.getStatus());
			ps.setString(4, whStatusLog.getStatusDate());
			ps.setString(5, whStatusLog.getCreatedBy());
			ps.setString(6, whStatusLog.getFlag());
			ps.setString(7, whStatusLog.getId());
			queryResult.setResult(ps.executeUpdate());
			ps.close();
		} catch (SQLException e) {
			queryResult.setErrorMessage(e.getMessage());
			LOGGER.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return queryResult;
	}

	public QueryResult deleteWhStatusLog(String whStatusLogId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM cdars_wh_status_log WHERE id = '" + whStatusLogId + "'"
			);
			queryResult.setResult(ps.executeUpdate());
			ps.close();
		} catch (SQLException e) {
			queryResult.setErrorMessage(e.getMessage());
			LOGGER.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return queryResult;
	}

	public WhStatusLog getWhStatusLog(String whStatusLogId) {
		String sql = "SELECT * FROM cdars_wh_status_log WHERE id = '" + whStatusLogId + "'";
		WhStatusLog whStatusLog = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				whStatusLog = new WhStatusLog();
				whStatusLog.setId(rs.getString("id"));
				whStatusLog.setRefId(rs.getString("ref_id"));
				whStatusLog.setModule(rs.getString("module"));
				whStatusLog.setStatus(rs.getString("status"));
				whStatusLog.setStatusDate(rs.getString("status_date"));
				whStatusLog.setCreatedBy(rs.getString("created_by"));
				whStatusLog.setFlag(rs.getString("flag"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return whStatusLog;
	}

	public List<WhStatusLog> getWhStatusLogList() {
		String sql = "SELECT * FROM cdars_wh_status_log ORDER BY id ASC";
		List<WhStatusLog> whStatusLogList = new ArrayList<WhStatusLog>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			WhStatusLog whStatusLog;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				whStatusLog = new WhStatusLog();
				whStatusLog.setId(rs.getString("id"));
				whStatusLog.setRefId(rs.getString("ref_id"));
				whStatusLog.setModule(rs.getString("module"));
				whStatusLog.setStatus(rs.getString("status"));
				whStatusLog.setStatusDate(rs.getString("status_date"));
				whStatusLog.setCreatedBy(rs.getString("created_by"));
				whStatusLog.setFlag(rs.getString("flag"));
				whStatusLogList.add(whStatusLog);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return whStatusLogList;
	}
}