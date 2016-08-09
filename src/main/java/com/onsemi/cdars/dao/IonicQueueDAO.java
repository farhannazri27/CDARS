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
import com.onsemi.cdars.model.IonicQueue;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IonicQueueDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicQueueDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IonicQueueDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertIonicQueue(IonicQueue ionicQueue) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_ionic_queue (ionic_ftp_id, pcb_ftp_id, ionic_ad_hoc_id, classification, source, event_name_code, rms, intervals, current_status, date_off, equipment_id, lcode, hardware_final, final_support_item, status, created_by, created_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, ionicQueue.getIonicFtpId());
            ps.setString(2, ionicQueue.getPcbFtpId());
            ps.setString(3, ionicQueue.getIonicAdHocId());
            ps.setString(4, ionicQueue.getClassification());
            ps.setString(5, ionicQueue.getSource());
            ps.setString(6, ionicQueue.getEventNameCode());
            ps.setString(7, ionicQueue.getRms());
            ps.setString(8, ionicQueue.getIntervals());
            ps.setString(9, ionicQueue.getCurrentStatus());
            ps.setString(10, ionicQueue.getDateOff());
            ps.setString(11, ionicQueue.getEquipmentId());
            ps.setString(12, ionicQueue.getLcode());
            ps.setString(13, ionicQueue.getHardwareFinal());
            ps.setString(14, ionicQueue.getFinalSupportItem());
            ps.setString(15, ionicQueue.getStatus());
            ps.setString(16, ionicQueue.getCreatedBy());
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

    public QueryResult updateIonicQueue(IonicQueue ionicQueue) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_ionic_queue SET ionic_ftp_id = ?, ionic_ad_hoc_id = ?, classification = ?, source = ?, event_name_code = ?, rms = ?, intervals = ?, current_status = ?, date_off = ?, equipment_id = ?, lcode = ?, hardware_final = ?, final_support_item = ?, status = ?,  modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, ionicQueue.getIonicFtpId());
            ps.setString(2, ionicQueue.getIonicAdHocId());
            ps.setString(3, ionicQueue.getClassification());
            ps.setString(4, ionicQueue.getSource());
            ps.setString(5, ionicQueue.getEventNameCode());
            ps.setString(6, ionicQueue.getRms());
            ps.setString(7, ionicQueue.getIntervals());
            ps.setString(8, ionicQueue.getCurrentStatus());
            ps.setString(9, ionicQueue.getDateOff());
            ps.setString(10, ionicQueue.getEquipmentId());
            ps.setString(11, ionicQueue.getLcode());
            ps.setString(12, ionicQueue.getHardwareFinal());
            ps.setString(13, ionicQueue.getFinalSupportItem());
            ps.setString(14, ionicQueue.getStatus());;
            ps.setString(15, ionicQueue.getModifiedBy());
            ps.setString(16, ionicQueue.getId());
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

    public QueryResult deleteIonicQueue(String ionicQueueId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_ionic_queue WHERE id = '" + ionicQueueId + "'"
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

    public IonicQueue getIonicQueue(String ionicQueueId) {
        String sql = "SELECT * FROM cdars_ionic_queue WHERE id = '" + ionicQueueId + "'";
        IonicQueue ionicQueue = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicQueue = new IonicQueue();
                ionicQueue.setId(rs.getString("id"));
                ionicQueue.setIonicFtpId(rs.getString("ionic_ftp_id"));
                ionicQueue.setIonicAdHocId(rs.getString("ionic_ad_hoc_id"));
                ionicQueue.setClassification(rs.getString("classification"));
                ionicQueue.setSource(rs.getString("source"));
                ionicQueue.setEventNameCode(rs.getString("event_name_code"));
                ionicQueue.setRms(rs.getString("rms"));
                ionicQueue.setIntervals(rs.getString("intervals"));
                ionicQueue.setCurrentStatus(rs.getString("current_status"));
                ionicQueue.setDateOff(rs.getString("date_off"));
                ionicQueue.setEquipmentId(rs.getString("equipment_id"));
                ionicQueue.setLcode(rs.getString("lcode"));
                ionicQueue.setHardwareFinal(rs.getString("hardware_final"));
                ionicQueue.setFinalSupportItem(rs.getString("final_support_item"));
                ionicQueue.setStatus(rs.getString("status"));
                ionicQueue.setCreatedBy(rs.getString("created_by"));
                ionicQueue.setCreatedDate(rs.getString("created_date"));
                ionicQueue.setModifiedBy(rs.getString("modified_by"));
                ionicQueue.setModifiedDate(rs.getString("modified_date"));
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
        return ionicQueue;
    }

    public List<IonicQueue> getIonicQueueList() {
        String sql = "SELECT * FROM cdars_ionic_queue ORDER BY id ASC";
        List<IonicQueue> ionicQueueList = new ArrayList<IonicQueue>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IonicQueue ionicQueue;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicQueue = new IonicQueue();
                ionicQueue.setId(rs.getString("id"));
                ionicQueue.setIonicFtpId(rs.getString("ionic_ftp_id"));
                ionicQueue.setIonicAdHocId(rs.getString("ionic_ad_hoc_id"));
                ionicQueue.setClassification(rs.getString("classification"));
                ionicQueue.setSource(rs.getString("source"));
                ionicQueue.setEventNameCode(rs.getString("event_name_code"));
                ionicQueue.setRms(rs.getString("rms"));
                ionicQueue.setIntervals(rs.getString("intervals"));
                ionicQueue.setCurrentStatus(rs.getString("current_status"));
                ionicQueue.setDateOff(rs.getString("date_off"));
                ionicQueue.setEquipmentId(rs.getString("equipment_id"));
                ionicQueue.setLcode(rs.getString("lcode"));
                ionicQueue.setHardwareFinal(rs.getString("hardware_final"));
                ionicQueue.setFinalSupportItem(rs.getString("final_support_item"));
                ionicQueue.setStatus(rs.getString("status"));
                ionicQueue.setCreatedBy(rs.getString("created_by"));
                ionicQueue.setCreatedDate(rs.getString("created_date"));
                ionicQueue.setModifiedBy(rs.getString("modified_by"));
                ionicQueue.setModifiedDate(rs.getString("modified_date"));
                ionicQueueList.add(ionicQueue);
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
        return ionicQueueList;
    }
}
