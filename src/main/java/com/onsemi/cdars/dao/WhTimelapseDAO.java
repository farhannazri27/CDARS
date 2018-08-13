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
import com.onsemi.cdars.model.WhTimelapse;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhTimelapseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhTimelapseDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public WhTimelapseDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertWhTimelapse(WhTimelapse whTimelapse) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_timelapse (request_id, ca, root_cause, created_by, created_date, category) VALUES (?,?,?,?,NOW(), ?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whTimelapse.getRequestId());
            ps.setString(2, whTimelapse.getCa());
            ps.setString(3, whTimelapse.getRootCause());
            ps.setString(4, whTimelapse.getCreatedBy());
            ps.setString(5, whTimelapse.getCategory());
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

    public QueryResult updateWhTimelapse(WhTimelapse whTimelapse) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_timelapse SET request_id = ?, ca = ?, root_cause = ?, created_by = ?, category = ? WHERE id = ?"
            );
            ps.setString(1, whTimelapse.getRequestId());
            ps.setString(2, whTimelapse.getCa());
            ps.setString(3, whTimelapse.getRootCause());
            ps.setString(4, whTimelapse.getCreatedBy());
            ps.setString(5, whTimelapse.getCategory());
            ps.setString(6, whTimelapse.getId());
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

    public QueryResult deleteWhTimelapse(String whTimelapseId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_wh_timelapse WHERE id = '" + whTimelapseId + "'"
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

    public WhTimelapse getWhTimelapse(String whTimelapseId) {
        String sql = "SELECT * FROM cdars_wh_timelapse WHERE id = '" + whTimelapseId + "'";
        WhTimelapse whTimelapse = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whTimelapse = new WhTimelapse();
                whTimelapse.setId(rs.getString("id"));
                whTimelapse.setRequestId(rs.getString("request_id"));
                whTimelapse.setCa(rs.getString("ca"));
                whTimelapse.setRootCause(rs.getString("root_cause"));
                whTimelapse.setCreatedBy(rs.getString("created_by"));
                whTimelapse.setCreatedDate(rs.getString("created_date"));
                whTimelapse.setCategory(rs.getString("category"));
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
        return whTimelapse;
    }

    public List<WhTimelapse> getWhTimelapseList() {
        String sql = "SELECT * FROM cdars_wh_timelapse ORDER BY id ASC";
        List<WhTimelapse> whTimelapseList = new ArrayList<WhTimelapse>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhTimelapse whTimelapse;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whTimelapse = new WhTimelapse();
                whTimelapse.setId(rs.getString("id"));
                whTimelapse.setRequestId(rs.getString("request_id"));
                whTimelapse.setCa(rs.getString("ca"));
                whTimelapse.setRootCause(rs.getString("root_cause"));
                whTimelapse.setCreatedBy(rs.getString("created_by"));
                whTimelapse.setCreatedDate(rs.getString("created_date"));
                whTimelapse.setCategory(rs.getString("category"));
                whTimelapseList.add(whTimelapse);
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
        return whTimelapseList;
    }
}
