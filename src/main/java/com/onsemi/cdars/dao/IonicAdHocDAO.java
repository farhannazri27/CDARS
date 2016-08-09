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
import com.onsemi.cdars.model.IonicAdHoc;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IonicAdHocDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicAdHocDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IonicAdHocDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertIonicAdHoc(IonicAdHoc ionicAdHoc) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_ionic_ad_hoc (classification, source, category, hardware_id, pcard_id, pcard_qty, lcard_id, lcard_qty, rms, event, equipment_id, created_by, created_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, ionicAdHoc.getClassification());
            ps.setString(2, ionicAdHoc.getSource());
            ps.setString(3, ionicAdHoc.getCategory());
            ps.setString(4, ionicAdHoc.getHardwareId());
            ps.setString(5, ionicAdHoc.getPcardId());
            ps.setString(6, ionicAdHoc.getPcardQty());
            ps.setString(7, ionicAdHoc.getLcardId());
            ps.setString(8, ionicAdHoc.getLcardQty());
            ps.setString(9, ionicAdHoc.getRms());
            ps.setString(10, ionicAdHoc.getEvent());
            ps.setString(11, ionicAdHoc.getEquipmentId());
            ps.setString(12, ionicAdHoc.getCreatedBy());
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

    public QueryResult updateIonicAdHoc(IonicAdHoc ionicAdHoc) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_ionic_ad_hoc SET classification = ?, source = ?, category = ?, hardware_id = ?, pcard_id = ?, pcard_qty = ?, lcard_id = ?, lcard_qty = ?, rms = ?, event = ?, equipment_id = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, ionicAdHoc.getClassification());
            ps.setString(2, ionicAdHoc.getSource());
            ps.setString(3, ionicAdHoc.getCategory());
            ps.setString(4, ionicAdHoc.getHardwareId());
            ps.setString(5, ionicAdHoc.getPcardId());
            ps.setString(6, ionicAdHoc.getPcardQty());
            ps.setString(7, ionicAdHoc.getLcardId());
            ps.setString(8, ionicAdHoc.getLcardQty());
            ps.setString(9, ionicAdHoc.getRms());
            ps.setString(10, ionicAdHoc.getEvent());
            ps.setString(11, ionicAdHoc.getEquipmentId());
            ps.setString(12, ionicAdHoc.getModifiedBy());
            ps.setString(13, ionicAdHoc.getId());
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

    public QueryResult deleteIonicAdHoc(String ionicAdHocId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_ionic_ad_hoc WHERE id = '" + ionicAdHocId + "'"
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

    public IonicAdHoc getIonicAdHoc(String ionicAdHocId) {
        String sql = "SELECT * FROM cdars_ionic_ad_hoc WHERE id = '" + ionicAdHocId + "'";
        IonicAdHoc ionicAdHoc = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicAdHoc = new IonicAdHoc();
                ionicAdHoc.setId(rs.getString("id"));
                ionicAdHoc.setClassification(rs.getString("classification"));
                ionicAdHoc.setSource(rs.getString("source"));
                ionicAdHoc.setCategory(rs.getString("category"));
                ionicAdHoc.setHardwareId(rs.getString("hardware_id"));
                ionicAdHoc.setPcardId(rs.getString("pcard_id"));
                ionicAdHoc.setPcardQty(rs.getString("pcard_qty"));
                ionicAdHoc.setLcardId(rs.getString("lcard_id"));
                ionicAdHoc.setLcardQty(rs.getString("lcard_qty"));
                ionicAdHoc.setRms(rs.getString("rms"));
                ionicAdHoc.setEvent(rs.getString("event"));
                ionicAdHoc.setEquipmentId(rs.getString("equipment_id"));
                ionicAdHoc.setCreatedBy(rs.getString("created_by"));
                ionicAdHoc.setCreatedDate(rs.getString("created_date"));
                ionicAdHoc.setModifiedBy(rs.getString("modified_by"));
                ionicAdHoc.setModifiedDate(rs.getString("modified_date"));
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
        return ionicAdHoc;
    }

    public List<IonicAdHoc> getIonicAdHocList() {
        String sql = "SELECT * FROM cdars_ionic_ad_hoc ORDER BY id ASC";
        List<IonicAdHoc> ionicAdHocList = new ArrayList<IonicAdHoc>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IonicAdHoc ionicAdHoc;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicAdHoc = new IonicAdHoc();
                ionicAdHoc.setId(rs.getString("id"));
                ionicAdHoc.setClassification(rs.getString("classification"));
                ionicAdHoc.setSource(rs.getString("source"));
                ionicAdHoc.setCategory(rs.getString("category"));
                ionicAdHoc.setHardwareId(rs.getString("hardware_id"));
                ionicAdHoc.setPcardId(rs.getString("pcard_id"));
                ionicAdHoc.setPcardQty(rs.getString("pcard_qty"));
                ionicAdHoc.setLcardId(rs.getString("lcard_id"));
                ionicAdHoc.setLcardQty(rs.getString("lcard_qty"));
                ionicAdHoc.setRms(rs.getString("rms"));
                ionicAdHoc.setEvent(rs.getString("event"));
                ionicAdHoc.setEquipmentId(rs.getString("equipment_id"));
                ionicAdHoc.setCreatedBy(rs.getString("created_by"));
                ionicAdHoc.setCreatedDate(rs.getString("created_date"));
                ionicAdHoc.setModifiedBy(rs.getString("modified_by"));
                ionicAdHoc.setModifiedDate(rs.getString("modified_date"));
                ionicAdHocList.add(ionicAdHoc);
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
        return ionicAdHocList;
    }
}
