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
import com.onsemi.cdars.model.MasterGroup;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MasterGroupDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(MasterGroupDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public MasterGroupDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertMasterGroup(MasterGroup masterGroup) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO cdars_master_group (group_master, hardware, type, remarks, created_by, created_date, flag) VALUES (?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, masterGroup.getGroupMaster());
			ps.setString(2, masterGroup.getHardware());
			ps.setString(3, masterGroup.getType());
			ps.setString(4, masterGroup.getRemarks());
			ps.setString(5, masterGroup.getCreatedBy());
			ps.setString(6, masterGroup.getFlag());
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

	public QueryResult updateMasterGroup(MasterGroup masterGroup) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE cdars_master_group SET group_master = ?, hardware = ?, type = ?, remarks = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
			);
			ps.setString(1, masterGroup.getGroupMaster());
			ps.setString(2, masterGroup.getHardware());
			ps.setString(3, masterGroup.getType());
			ps.setString(4, masterGroup.getRemarks());
			ps.setString(5, masterGroup.getCreatedBy());
			ps.setString(6, masterGroup.getCreatedDate());
			ps.setString(7, masterGroup.getFlag());
			ps.setString(8, masterGroup.getId());
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
        
        public QueryResult updateMasterGroupWithoutType(MasterGroup masterGroup) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE cdars_master_group SET group_master = ?, hardware = ?, remarks = ? WHERE id = ?"
			);
			ps.setString(1, masterGroup.getGroupMaster());
			ps.setString(2, masterGroup.getHardware());
			ps.setString(3, masterGroup.getRemarks());
			ps.setString(4, masterGroup.getId());
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

	public QueryResult deleteMasterGroup(String masterGroupId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM cdars_master_group WHERE id = '" + masterGroupId + "'"
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

	public MasterGroup getMasterGroup(String masterGroupId) {
		String sql = "SELECT * FROM cdars_master_group WHERE id = '" + masterGroupId + "'";
		MasterGroup masterGroup = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				masterGroup = new MasterGroup();
				masterGroup.setId(rs.getString("id"));
				masterGroup.setGroupMaster(rs.getString("group_master"));
				masterGroup.setHardware(rs.getString("hardware"));
				masterGroup.setType(rs.getString("type"));
				masterGroup.setRemarks(rs.getString("remarks"));
				masterGroup.setCreatedBy(rs.getString("created_by"));
				masterGroup.setCreatedDate(rs.getString("created_date"));
				masterGroup.setFlag(rs.getString("flag"));
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
		return masterGroup;
	}

	public List<MasterGroup> getMasterGroupList() {
		String sql = "SELECT * FROM cdars_master_group ORDER BY id ASC";
		List<MasterGroup> masterGroupList = new ArrayList<MasterGroup>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			MasterGroup masterGroup;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				masterGroup = new MasterGroup();
				masterGroup.setId(rs.getString("id"));
				masterGroup.setGroupMaster(rs.getString("group_master"));
				masterGroup.setHardware(rs.getString("hardware"));
				masterGroup.setType(rs.getString("type"));
				masterGroup.setRemarks(rs.getString("remarks"));
				masterGroup.setCreatedBy(rs.getString("created_by"));
				masterGroup.setCreatedDate(rs.getString("created_date"));
				masterGroup.setFlag(rs.getString("flag"));
				masterGroupList.add(masterGroup);
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
		return masterGroupList;
	}
}