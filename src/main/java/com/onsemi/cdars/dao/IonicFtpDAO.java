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
import com.onsemi.cdars.model.IonicFtp;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IonicFtpDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicFtpDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IonicFtpDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertIonicFtp(IonicFtp ionicFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_ionic_ftp (event_code, rms, intervals, current_status, date_off, equip_id, lcode, hardware_final, support_item, created_date) VALUES (?,?,?,?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, ionicFtp.getEventCode());
            ps.setString(2, ionicFtp.getRms());
            ps.setString(3, ionicFtp.getIntervals());
            ps.setString(4, ionicFtp.getCurrentStatus());
            ps.setString(5, ionicFtp.getDateOff());
            ps.setString(6, ionicFtp.getEquipId());
            ps.setString(7, ionicFtp.getLcode());
            ps.setString(8, ionicFtp.getHardwareFinal());
            ps.setString(9, ionicFtp.getSupportItem());
//			ps.setString(10, ionicFtp.getCreatedDate());
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

//    public QueryResult createStoreProcedure(String event, String rms,
//            String intervals, String status, String date, String equip,
//            String lcode, String hardware, String support_item) {
//        QueryResult queryResult = new QueryResult();
//        try {
//            PreparedStatement ps = conn.prepareStatement(
//                      "DROP PROCEDURE IF EXISTS select_or_insert_ionic_ftp $$\n"
//                    + "delimiter $$\n"
//                    + "CREATE PROCEDURE select_or_insert_ionic_ftp(\n"
//                    + "IN '" + event + "' VARCHAR(50),\n"
//                    + "IN '" + rms + "' VARCHAR(50),\n"
//                    + "IN '" + intervals + "' VARCHAR(50),\n"
//                    + "IN '" + status + "' VARCHAR(100),\n"
//                    + "IN '" + equip + "' VARCHAR(100),\n"
//                    + "IN '" + lcode + "' VARCHAR(50),\n"
//                    + "IN '" + hardware + "' VARCHAR(100),\n"
//                    + "IN '" + support_item + "' VARCHAR(100)) \n"
//                    + "BEGIN \n"
//                    + "IF NOT EXISTS (\n"
//                    + "SELECT * FROM cdars_ionic_ftp \n"
//                    + "WHERE event_code = '" + event + "' and rms = '" + rms + "' and intervals = '" + intervals + "' and current_status = '" + status + "' and \n"
//                    + "equip_id = '" + equip + "' and lcode = '" + lcode + "' and hardware_final = '" + hardware + "' \n"
//                    + "support_item = '" + support_item + "') THEN \n"
//                    + "INSERT INTO cdars_ionic_ftp (event_code, rms, intervals, current_status, date_off, equip_id,\n"
//                    + "lcode, hardware_final, support_item, created_date) VALUES ('" + event + "','" + rms + "','" + intervals + "','" + status + "',"
//                    + "'" + date + "','" + equip + "','" + lcode + "','" + hardware + "','" + support_item + "', NOW());\n"
//                    + "END IF;\n"
//                    + "END $$\n"
//                    + "delimiter ;"
//            );
//            queryResult.setResult(ps.executeUpdate());
//            ps.close();
//            LOGGER.info("masuk procedure");
//        } catch (SQLException e) {
//            queryResult.setErrorMessage(e.getMessage());
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return queryResult;
//    }
    public QueryResult InsertAndcallStoreProcedure(IonicFtp ionicFtp) {
        QueryResult queryResult = new QueryResult();
        try {

            String callProcedure = "{call select_or_insert_ionic_ftp(?,?,?,?,?,?,?,?,?)}";

            PreparedStatement ps = conn.prepareStatement(callProcedure, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, ionicFtp.getEventCode());
            ps.setString(2, ionicFtp.getRms());
            ps.setString(3, ionicFtp.getIntervals());
            ps.setString(4, ionicFtp.getCurrentStatus());
            ps.setString(5, ionicFtp.getDateOff());
            ps.setString(6, ionicFtp.getEquipId());
            ps.setString(7, ionicFtp.getLcode());
            ps.setString(8, ionicFtp.getHardwareFinal());
            ps.setString(9, ionicFtp.getSupportItem());
//			ps.setString(10, ionicFtp.getCreatedDate());
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
            }
            rs.close();
            ps.close();
            LOGGER.info("berjaya save");
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

    public QueryResult updateIonicFtp(IonicFtp ionicFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_ionic_ftp SET event_code = ?, rms = ?, intervals = ?, current_status = ?, date_off = ?, equip_id = ?, lcode = ?, hardware_final = ?, support_item = ?, WHERE id = ?"
            );
            ps.setString(1, ionicFtp.getEventCode());
            ps.setString(2, ionicFtp.getRms());
            ps.setString(3, ionicFtp.getIntervals());
            ps.setString(4, ionicFtp.getCurrentStatus());
            ps.setString(5, ionicFtp.getDateOff());
            ps.setString(6, ionicFtp.getEquipId());
            ps.setString(7, ionicFtp.getLcode());
            ps.setString(8, ionicFtp.getHardwareFinal());
            ps.setString(9, ionicFtp.getSupportItem());
//			ps.setString(10, ionicFtp.getCreatedDate());
            ps.setString(10, ionicFtp.getId());
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

    public Integer getCountExistingData(IonicFtp ionicFtp) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_ionic_ftp WHERE event_code = ? and rms = ? and intervals = ? "
                    + "and current_status = ? and equip_id = ? and lcode = ? and hardware_final = ? and support_item = ?"
            );
            ps.setString(1, ionicFtp.getEventCode());
            ps.setString(2, ionicFtp.getRms());
            ps.setString(3, ionicFtp.getIntervals());
            ps.setString(4, ionicFtp.getCurrentStatus());
            ps.setString(5, ionicFtp.getEquipId());
            ps.setString(6, ionicFtp.getLcode());
            ps.setString(7, ionicFtp.getHardwareFinal());
            ps.setString(8, ionicFtp.getSupportItem());

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

    public QueryResult deleteIonicFtp(String ionicFtpId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_ionic_ftp WHERE id = '" + ionicFtpId + "'"
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

    public IonicFtp getIonicFtp(String ionicFtpId) {
        String sql = "SELECT * FROM cdars_ionic_ftp WHERE id = '" + ionicFtpId + "'";
        IonicFtp ionicFtp = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicFtp = new IonicFtp();
                ionicFtp.setId(rs.getString("id"));
                ionicFtp.setEventCode(rs.getString("event_code"));
                ionicFtp.setRms(rs.getString("rms"));
                ionicFtp.setIntervals(rs.getString("intervals"));
                ionicFtp.setCurrentStatus(rs.getString("current_status"));
                ionicFtp.setDateOff(rs.getString("date_off"));
                ionicFtp.setEquipId(rs.getString("equip_id"));
                ionicFtp.setLcode(rs.getString("lcode"));
                ionicFtp.setHardwareFinal(rs.getString("hardware_final"));
                ionicFtp.setSupportItem(rs.getString("support_item"));
                ionicFtp.setCreatedDate(rs.getString("created_date"));
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
        return ionicFtp;
    }

    public List<IonicFtp> getIonicFtpList() {
        String sql = "SELECT * FROM cdars_ionic_ftp ORDER BY id ASC";
        List<IonicFtp> ionicFtpList = new ArrayList<IonicFtp>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IonicFtp ionicFtp;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicFtp = new IonicFtp();
                ionicFtp.setId(rs.getString("id"));
                ionicFtp.setEventCode(rs.getString("event_code"));
                ionicFtp.setRms(rs.getString("rms"));
                ionicFtp.setIntervals(rs.getString("intervals"));
                ionicFtp.setCurrentStatus(rs.getString("current_status"));
                ionicFtp.setDateOff(rs.getString("date_off"));
                ionicFtp.setEquipId(rs.getString("equip_id"));
                ionicFtp.setLcode(rs.getString("lcode"));
                ionicFtp.setHardwareFinal(rs.getString("hardware_final"));
                ionicFtp.setSupportItem(rs.getString("support_item"));
                ionicFtp.setCreatedDate(rs.getString("created_date"));
                ionicFtpList.add(ionicFtp);
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
        return ionicFtpList;
    }
}
