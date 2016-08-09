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
import com.onsemi.cdars.model.IonicLimit;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IonicLimitDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicLimitDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IonicLimitDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertIonicLimit(IonicLimit ionicLimit) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_ionic_limit (name, value, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, ionicLimit.getName());
            ps.setString(2, ionicLimit.getValue());
            ps.setString(3, ionicLimit.getCreatedBy());
//            ps.setString(4, ionicLimit.getCreatedDate());
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

    public QueryResult updateIonicLimit(IonicLimit ionicLimit) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_ionic_limit SET name = ?, value = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, ionicLimit.getName());
            ps.setString(2, ionicLimit.getValue());
            ps.setString(3, ionicLimit.getModifiedBy());
            ps.setString(4, ionicLimit.getId());
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

    public QueryResult deleteIonicLimit(String ionicLimitId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_ionic_limit WHERE id = '" + ionicLimitId + "'"
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

    public IonicLimit getIonicLimit(String ionicLimitId) {
        String sql = "SELECT * FROM cdars_ionic_limit WHERE id = '" + ionicLimitId + "'";
        IonicLimit ionicLimit = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicLimit = new IonicLimit();
                ionicLimit.setId(rs.getString("id"));
                ionicLimit.setName(rs.getString("name"));
                ionicLimit.setValue(rs.getString("value"));
                ionicLimit.setCreatedBy(rs.getString("created_by"));
                ionicLimit.setCreatedDate(rs.getString("created_date"));
                ionicLimit.setModifiedBy(rs.getString("modified_by"));
                ionicLimit.setModifiedDate(rs.getString("modified_date"));
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
        return ionicLimit;
    }

    public List<IonicLimit> getIonicLimitList() {
        String sql = "SELECT * FROM cdars_ionic_limit ORDER BY id ASC";
        List<IonicLimit> ionicLimitList = new ArrayList<IonicLimit>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IonicLimit ionicLimit;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicLimit = new IonicLimit();
                ionicLimit.setId(rs.getString("id"));
                ionicLimit.setName(rs.getString("name"));
                ionicLimit.setValue(rs.getString("value"));
                ionicLimit.setCreatedBy(rs.getString("created_by"));
                ionicLimit.setCreatedDate(rs.getString("created_date"));
                ionicLimit.setModifiedBy(rs.getString("modified_by"));
                ionicLimit.setModifiedDate(rs.getString("modified_date"));
                ionicLimitList.add(ionicLimit);
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
        return ionicLimitList;
    }
}
