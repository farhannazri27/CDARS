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
import com.onsemi.cdars.model.HardwareRequest;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardwareRequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareRequestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public HardwareRequestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertHardwareRequest(HardwareRequest hardwareRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_hardware_request (forecast_readout_start, event_code, rms, process, status, created_date) VALUES (?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, hardwareRequest.getForecastReadoutStart());
            ps.setString(2, hardwareRequest.getEventCode());
            ps.setString(3, hardwareRequest.getRms());
            ps.setString(4, hardwareRequest.getProcess());
            ps.setString(5, hardwareRequest.getStatus());
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

    public QueryResult updateHardwareRequest(HardwareRequest hardwareRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_hardware_request SET forecast_readout_start = ?, event_code = ?, rms = ?, process = ?, status = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, hardwareRequest.getForecastReadoutStart());
            ps.setString(2, hardwareRequest.getEventCode());
            ps.setString(3, hardwareRequest.getRms());
            ps.setString(4, hardwareRequest.getProcess());
            ps.setString(5, hardwareRequest.getStatus());
            ps.setString(6, hardwareRequest.getModifiedBy());
            ps.setString(7, hardwareRequest.getId());
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

    public QueryResult deleteHardwareRequest(String hardwareRequestId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_hardware_request WHERE id = '" + hardwareRequestId + "'"
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

    public HardwareRequest getHardwareRequest(String hardwareRequestId) {
        String sql = "SELECT *,DATE_FORMAT(forecast_readout_start,'%d %M %Y') AS forecast_date_view FROM cdars_hardware_request WHERE id = '" + hardwareRequestId + "'";
        HardwareRequest hardwareRequest = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwareRequest = new HardwareRequest();
                hardwareRequest.setId(rs.getString("id"));
                hardwareRequest.setForecastReadoutStart(rs.getString("forecast_readout_start"));
                hardwareRequest.setForecastDateView(rs.getString("forecast_date_view"));
                hardwareRequest.setEventCode(rs.getString("event_code"));
                hardwareRequest.setRms(rs.getString("rms"));
                hardwareRequest.setProcess(rs.getString("process"));
                hardwareRequest.setStatus(rs.getString("status"));
                hardwareRequest.setCreatedDate(rs.getString("created_date"));
                hardwareRequest.setModifiedBy(rs.getString("modified_by"));
                hardwareRequest.setModifiedDate(rs.getString("modified_date"));
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
        return hardwareRequest;
    }

    public List<HardwareRequest> getHardwareRequestList() {
        String sql = "SELECT *, DATE_FORMAT(forecast_readout_start,'%d %M %Y') AS forecast_date_view FROM cdars_hardware_request ORDER BY id ASC";
        List<HardwareRequest> hardwareRequestList = new ArrayList<HardwareRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            HardwareRequest hardwareRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardwareRequest = new HardwareRequest();
                hardwareRequest.setId(rs.getString("id"));
                hardwareRequest.setForecastReadoutStart(rs.getString("forecast_readout_start"));
                hardwareRequest.setEventCode(rs.getString("event_code"));
                hardwareRequest.setRms(rs.getString("rms"));
                hardwareRequest.setProcess(rs.getString("process"));
                hardwareRequest.setStatus(rs.getString("status"));
                hardwareRequest.setCreatedDate(rs.getString("created_date"));
                hardwareRequest.setModifiedBy(rs.getString("modified_by"));
                hardwareRequest.setModifiedDate(rs.getString("modified_date"));
                hardwareRequest.setForecastDateView(rs.getString("forecast_date_view"));
                hardwareRequestList.add(hardwareRequest);
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
        return hardwareRequestList;
    }

    public Integer getCountExistingData(HardwareRequest hardwareRequest) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_hardware_request WHERE forecast_readout_start = ? and event_code = ? and rms = ? "
                    + "and process = ? "
            );
            ps.setString(1, hardwareRequest.getForecastReadoutStart());
            ps.setString(2, hardwareRequest.getEventCode());
            ps.setString(3, hardwareRequest.getRms());
            ps.setString(4, hardwareRequest.getProcess());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }
}
