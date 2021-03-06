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
                    "INSERT INTO cdars_wh_status_log (request_id, module, status, status_date, created_by, flag) VALUES (?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whStatusLog.getRequestId());
            ps.setString(2, whStatusLog.getModule());
            ps.setString(3, whStatusLog.getStatus());
            ps.setString(4, whStatusLog.getCreatedBy());
            ps.setString(5, whStatusLog.getFlag());
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
                    "UPDATE cdars_wh_status_log SET request_id = ?, module = ?, status = ?, status_date = ?, created_by = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whStatusLog.getRequestId());
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
                whStatusLog.setRequestId(rs.getString("request_id"));
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
                whStatusLog.setRequestId(rs.getString("request_id"));
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

    public List<WhStatusLog> getWhStatusLogList(String id) {
        String sql = "SELECT lo.* FROM cdars_wh_status_log lo, cdars_wh_request re WHERE re.id = lo.request_id AND re.id = '" + id + "' ORDER BY lo.status_date DESC";
        List<WhStatusLog> whStatusLogList = new ArrayList<WhStatusLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhStatusLog whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setId(rs.getString("id"));
                whStatusLog.setRequestId(rs.getString("request_id"));
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

    public List<WhStatusLog> getWhStatusLogListForRetrieveAndShip(String id) {
        String sql = "SELECT lo.* FROM cdars_wh_status_log lo, "
                + "(SELECT re.id AS id FROM cdars_wh_request re, (SELECT inv.request_id AS ii FROM cdars_wh_request res, cdars_wh_inventory inv WHERE res.inventory_id = inv.id AND res.id = '" + id + "') AS invid "
                + "WHERE re.id = invid.ii) AS test WHERE test.id = lo.request_id ORDER BY lo.status_date DESC";
        List<WhStatusLog> whStatusLogList = new ArrayList<WhStatusLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhStatusLog whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setId(rs.getString("id"));
                whStatusLog.setRequestId(rs.getString("request_id"));
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

    public Integer getCountList(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM "
                    + "(SELECT lo.* FROM cdars_wh_status_log lo, cdars_wh_request re WHERE re.id = lo.request_id AND re.id = '" + id + "' ORDER BY lo.status_date DESC) AS Item"
            );
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

    public Integer getCountRetrieveAndShipList(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM "
                    + "(SELECT lo.* FROM cdars_wh_status_log lo, "
                    + "(SELECT re.id AS id FROM cdars_wh_request re, (SELECT inv.request_id AS ii FROM cdars_wh_request res, cdars_wh_inventory inv WHERE res.inventory_id = inv.id AND res.id = '" + id + "') AS invid "
                    + "WHERE re.id = invid.ii) AS test WHERE test.id = lo.request_id ORDER BY lo.status_date DESC) AS Item"
            );
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

    public WhStatusLog getCountList2(String id) {
        String sql = "SELECT COUNT(*) AS count FROM "
                + "(SELECT lo.* FROM cdars_wh_status_log lo, cdars_wh_request re WHERE re.id = lo.request_id AND re.id = '" + id + "' ORDER BY lo.status_date DESC) AS Item";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setCount(rs.getString("count"));
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

    public WhStatusLog getCountRetrieveAndShipList2(String id) {
        String sql = "SELECT COUNT(*) AS count FROM "
                + "(SELECT lo.* FROM cdars_wh_status_log lo, "
                + "(SELECT re.id AS id FROM cdars_wh_request re, (SELECT inv.request_id AS ii FROM cdars_wh_request res, cdars_wh_inventory inv WHERE res.inventory_id = inv.id AND res.id = '" + id + "') AS invid "
                + "WHERE re.id = invid.ii) AS test WHERE test.id = lo.request_id ORDER BY lo.status_date DESC) AS Item";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setCount(rs.getString("count"));
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

    public WhStatusLog getTLReqToApproveAndApproveToMpCreated(String id) {
        String sql = "SELECT "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' secs') AS req_to_approve, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date,  IFNULL(re.mp_created_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' secs') AS approve_to_mp_created "
                + "FROM  cdars_wh_request re "
                + "WHERE re.id = '" + id + "'";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setRequestToApprove(rs.getString("req_to_approve"));
                whStatusLog.setApproveToMPCreated(rs.getString("approve_to_mp_created"));
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

    public List<WhStatusLog> getTLReqToApproveAndApproveToMpCreatedList() { //timelapse notification ship
        String sql = "SELECT re.id AS request_id, "
                + "re.equipment_type AS equipment_type, re.equipment_id AS equipment_id, re.mp_no AS mp_no, re.status AS status, "
                + "HOUR(TIMEDIFF(re.requested_date,re.final_approved_date)) AS req_to_approve_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.requested_date, re.final_approved_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.requested_date, final_approved_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.requested_date, final_approved_date)), ' mins, ', SECOND(TIMEDIFF(re.requested_date, final_approved_date)), ' secs') AS req_to_approve, "
                + "HOUR(TIMEDIFF(re.requested_date,IFNULL(re.final_approved_date,NOW()))) AS req_to_approve_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.requested_date, IFNULL(re.final_approved_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' secs') AS req_to_approve_temp, "
                + "HOUR(TIMEDIFF(final_approved_date,re.mp_created_date)) AS approve_to_mp_created_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, re.mp_created_date)), ' mins, ', SECOND(TIMEDIFF(final_approved_date, re.mp_created_date)), ' secs') AS approve_to_mp_created, "
                + "HOUR(TIMEDIFF(final_approved_date,IFNULL(re.mp_created_date,NOW()))) AS approve_to_mp_created_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date,  IFNULL(re.mp_created_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' secs') AS approve_to_mp_created_temp "
                + "FROM cdars_wh_request re WHERE re.request_type = 'Ship' "
                + "AND flag = '0' "
                + "ORDER BY re.equipment_type ASC ";
        List<WhStatusLog> whStatusLogList = new ArrayList<WhStatusLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhStatusLog whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setRequestId(rs.getString("request_id"));
                whStatusLog.setEquipmentType(rs.getString("equipment_type"));
                whStatusLog.setEquipmentId(rs.getString("equipment_id"));
                whStatusLog.setMpNo(rs.getString("mp_no"));
                whStatusLog.setStatus(rs.getString("status"));
                whStatusLog.setRequestToApprove(rs.getString("req_to_approve"));
                whStatusLog.setApproveToMPCreated(rs.getString("approve_to_mp_created"));
                whStatusLog.setRequestToApproveTemp(rs.getString("req_to_approve_temp"));
                whStatusLog.setApproveToMPCreatedTemp(rs.getString("approve_to_mp_created_temp"));
                whStatusLog.setRequestToApprove24(rs.getString("req_to_approve_24"));
                whStatusLog.setApproveToMPCreated24(rs.getString("approve_to_mp_created_24"));
                whStatusLog.setRequestToApproveTemp24(rs.getString("req_to_approve_temp_24"));
                whStatusLog.setApproveToMPCreatedTemp24(rs.getString("approve_to_mp_created_temp_24"));
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

    public List<WhStatusLog> getTLMpCreatedToFinalInventoryDateList() { //timelapse notification ship
        String sql = "SELECT re.id AS request_id,"
                + "re.equipment_type AS equipment_type, re.equipment_id AS equipment_id, re.mp_no AS mp_no, re.status AS status, "
                + "HOUR(TIMEDIFF(shi.mp_created_date,shi.date_scan_1)) AS mp_created_to_tt_scan_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)), ' mins, ', SECOND(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)), ' secs') AS mp_created_to_tt_scan, "
                + "HOUR(TIMEDIFF(shi.mp_created_date,IFNULL(shi.date_scan_1,NOW()))) AS mp_created_to_tt_scan_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), ' secs') AS mp_created_to_tt_scan_temp, "
                + "HOUR(TIMEDIFF(shi.date_scan_1,shi.date_scan_2)) AS tt_scan_to_bs_scan_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_1, shi.date_scan_2)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_1, shi.date_scan_2)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_1,shi.date_scan_2)), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_1,shi.date_scan_2)), ' secs') AS tt_scan_to_bs_scan, "
                + "HOUR(TIMEDIFF(shi.date_scan_1,IFNULL(shi.date_scan_2,NOW()))) AS tt_scan_to_bs_scan_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), ' secs') AS tt_scan_to_bs_scan_temp, "
                + "HOUR(TIMEDIFF(shi.date_scan_2,shi.shipping_date)) AS bs_scan_to_ship_date_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_2, shi.shipping_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_2, shi.shipping_date)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_2,shi.shipping_date)), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_2,shi.shipping_date)), ' secs') AS bs_scan_to_ship_date, "
                + "HOUR(TIMEDIFF(shi.date_scan_2,IFNULL(shi.shipping_date,NOW()))) AS bs_scan_to_ship_date_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), ' secs') AS bs_scan_to_ship_date_temp, "
                + "HOUR(TIMEDIFF(shi.shipping_date,shi.inventory_date)) AS ship_date_to_inv_date_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.shipping_date, shi.inventory_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.shipping_date, shi.inventory_date)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.shipping_date, shi.inventory_date)), ' mins, ', SECOND(TIMEDIFF(shi.shipping_date, shi.inventory_date)), ' secs') AS ship_date_to_inv_date, "
                + "HOUR(TIMEDIFF(shi.shipping_date,IFNULL(shi.inventory_date,NOW()))) AS ship_date_to_inv_date_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), ' secs') AS ship_date_to_inv_date_temp "
                + "FROM cdars_wh_shipping shi, cdars_wh_request re "
                + "WHERE shi.request_id = re.id ORDER BY re.equipment_type ASC ";
        List<WhStatusLog> whStatusLogList = new ArrayList<WhStatusLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhStatusLog whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setRequestId(rs.getString("request_id"));
                whStatusLog.setEquipmentType(rs.getString("equipment_type"));
                whStatusLog.setEquipmentId(rs.getString("equipment_id"));
                whStatusLog.setMpNo(rs.getString("mp_no"));
                whStatusLog.setStatus(rs.getString("status"));
                whStatusLog.setMpCreatedToTtScan(rs.getString("mp_created_to_tt_scan"));
                whStatusLog.setTtScanToBsScan(rs.getString("tt_scan_to_bs_scan"));
                whStatusLog.setBsScanToShip(rs.getString("bs_scan_to_ship_date"));
                whStatusLog.setShipToInventory(rs.getString("ship_date_to_inv_date"));
                whStatusLog.setMpCreatedToTtScanTemp(rs.getString("mp_created_to_tt_scan_temp"));
                whStatusLog.setTtScanToBsScanTemp(rs.getString("tt_scan_to_bs_scan_temp"));
                whStatusLog.setBsScanToShipTemp(rs.getString("bs_scan_to_ship_date_temp"));
                whStatusLog.setShipToInventoryTemp(rs.getString("ship_date_to_inv_date_temp"));
                whStatusLog.setMpCreatedToTtScan24(rs.getString("mp_created_to_tt_scan_24"));
                whStatusLog.setTtScanToBsScan24(rs.getString("tt_scan_to_bs_scan_24"));
                whStatusLog.setBsScanToShip24(rs.getString("bs_scan_to_ship_date_24"));
                whStatusLog.setShipToInventory24(rs.getString("ship_date_to_inv_date_24"));
                whStatusLog.setMpCreatedToTtScanTemp24(rs.getString("mp_created_to_tt_scan_temp_24"));
                whStatusLog.setTtScanToBsScanTemp24(rs.getString("tt_scan_to_bs_scan_temp_24"));
                whStatusLog.setBsScanToShipTemp24(rs.getString("bs_scan_to_ship_date_temp_24"));
                whStatusLog.setShipToInventoryTemp24(rs.getString("ship_date_to_inv_date_temp_24"));
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

    public List<WhStatusLog> getTLReqToInventoryList() { //timelapse notification ship
        String sql = "SELECT re.id AS request_id, re.load_card_id AS load_card, re.program_card_id AS program_card, "
                + "re.equipment_type AS equipment_type, re.equipment_id AS equipment_id, re.mp_no AS mp_no, re.status AS status, "
                + "HOUR(TIMEDIFF(re.requested_date,re.final_approved_date)) AS req_to_approve_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.requested_date, re.final_approved_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.requested_date, final_approved_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.requested_date, final_approved_date)), ' mins, ', SECOND(TIMEDIFF(re.requested_date, final_approved_date)), ' secs') AS req_to_approve, "
                + "HOUR(TIMEDIFF(re.requested_date,IFNULL(re.final_approved_date,NOW()))) AS req_to_approve_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.requested_date, IFNULL(re.final_approved_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' secs') AS req_to_approve_temp, "
                + "HOUR(TIMEDIFF(final_approved_date,re.mp_created_date)) AS approve_to_mp_created_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, re.mp_created_date)), ' mins, ', SECOND(TIMEDIFF(final_approved_date, re.mp_created_date)), ' secs') AS approve_to_mp_created, "
                + "HOUR(TIMEDIFF(final_approved_date,IFNULL(re.mp_created_date,NOW()))) AS approve_to_mp_created_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date,  IFNULL(re.mp_created_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' secs') AS approve_to_mp_created_temp, "
                + "HOUR(TIMEDIFF(shi.mp_created_date,shi.date_scan_1)) AS mp_created_to_tt_scan_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)), ' mins, ', SECOND(TIMEDIFF(shi.mp_created_date, shi.date_scan_1)), ' secs') AS mp_created_to_tt_scan, "
                + "HOUR(TIMEDIFF(shi.mp_created_date,IFNULL(shi.date_scan_1,NOW()))) AS mp_created_to_tt_scan_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), ' secs') AS mp_created_to_tt_scan_temp, "
                + "HOUR(TIMEDIFF(shi.date_scan_1,shi.date_scan_2)) AS tt_scan_to_bs_scan_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_1, shi.date_scan_2)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_1, shi.date_scan_2)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_1,shi.date_scan_2)), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_1,shi.date_scan_2)), ' secs') AS tt_scan_to_bs_scan, "
                + "HOUR(TIMEDIFF(shi.date_scan_1,IFNULL(shi.date_scan_2,NOW()))) AS tt_scan_to_bs_scan_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), ' secs') AS tt_scan_to_bs_scan_temp, "
                + "HOUR(TIMEDIFF(shi.date_scan_2,shi.shipping_date)) AS bs_scan_to_ship_date_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_2, shi.shipping_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_2, shi.shipping_date)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_2,shi.shipping_date)), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_2,shi.shipping_date)), ' secs') AS bs_scan_to_ship_date, "
                + "HOUR(TIMEDIFF(shi.date_scan_2,IFNULL(shi.shipping_date,NOW()))) AS bs_scan_to_ship_date_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), ' secs') AS bs_scan_to_ship_date_temp, "
                + "HOUR(TIMEDIFF(shi.shipping_date,shi.inventory_date)) AS ship_date_to_inv_date_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.shipping_date, shi.inventory_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.shipping_date, shi.inventory_date)), 24), ' hours, ', MINUTE(TIMEDIFF(shi.shipping_date, shi.inventory_date)), ' mins, ', SECOND(TIMEDIFF(shi.shipping_date, shi.inventory_date)), ' secs') AS ship_date_to_inv_date, "
                + "HOUR(TIMEDIFF(shi.shipping_date,IFNULL(shi.inventory_date,NOW()))) AS ship_date_to_inv_date_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), ' secs') AS ship_date_to_inv_date_temp "
                + "FROM cdars_wh_request re LEFT JOIN cdars_wh_shipping shi ON re.id = shi.request_id "
                + "WHERE re.request_type = 'Ship' "
                + "AND re.flag = '0' AND re.status NOT IN ('In SF Inventory','Requested for Retrieval') "
                + "ORDER BY re.equipment_type ASC ";
        List<WhStatusLog> whStatusLogList = new ArrayList<WhStatusLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhStatusLog whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setRequestId(rs.getString("request_id"));
                whStatusLog.setEquipmentType(rs.getString("equipment_type"));
                whStatusLog.setEquipmentId(rs.getString("equipment_id"));
                whStatusLog.setMpNo(rs.getString("mp_no"));
                whStatusLog.setStatus(rs.getString("status"));
                whStatusLog.setLoadCard(rs.getString("load_card"));
                whStatusLog.setProgramCard(rs.getString("program_card"));
                whStatusLog.setRequestToApprove(rs.getString("req_to_approve"));
                whStatusLog.setApproveToMPCreated(rs.getString("approve_to_mp_created"));
                whStatusLog.setRequestToApproveTemp(rs.getString("req_to_approve_temp"));
                whStatusLog.setApproveToMPCreatedTemp(rs.getString("approve_to_mp_created_temp"));
                whStatusLog.setRequestToApprove24(rs.getString("req_to_approve_24"));
                whStatusLog.setApproveToMPCreated24(rs.getString("approve_to_mp_created_24"));
                whStatusLog.setRequestToApproveTemp24(rs.getString("req_to_approve_temp_24"));
                whStatusLog.setApproveToMPCreatedTemp24(rs.getString("approve_to_mp_created_temp_24"));
                whStatusLog.setMpCreatedToTtScan(rs.getString("mp_created_to_tt_scan"));
                whStatusLog.setTtScanToBsScan(rs.getString("tt_scan_to_bs_scan"));
                whStatusLog.setBsScanToShip(rs.getString("bs_scan_to_ship_date"));
                whStatusLog.setShipToInventory(rs.getString("ship_date_to_inv_date"));
                whStatusLog.setMpCreatedToTtScanTemp(rs.getString("mp_created_to_tt_scan_temp"));
                whStatusLog.setTtScanToBsScanTemp(rs.getString("tt_scan_to_bs_scan_temp"));
                whStatusLog.setBsScanToShipTemp(rs.getString("bs_scan_to_ship_date_temp"));
                whStatusLog.setShipToInventoryTemp(rs.getString("ship_date_to_inv_date_temp"));
                whStatusLog.setMpCreatedToTtScan24(rs.getString("mp_created_to_tt_scan_24"));
                whStatusLog.setTtScanToBsScan24(rs.getString("tt_scan_to_bs_scan_24"));
                whStatusLog.setBsScanToShip24(rs.getString("bs_scan_to_ship_date_24"));
                whStatusLog.setShipToInventory24(rs.getString("ship_date_to_inv_date_24"));
                whStatusLog.setMpCreatedToTtScanTemp24(rs.getString("mp_created_to_tt_scan_temp_24"));
                whStatusLog.setTtScanToBsScanTemp24(rs.getString("tt_scan_to_bs_scan_temp_24"));
                whStatusLog.setBsScanToShipTemp24(rs.getString("bs_scan_to_ship_date_temp_24"));
                whStatusLog.setShipToInventoryTemp24(rs.getString("ship_date_to_inv_date_temp_24"));
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

    public WhStatusLog getTLMpCreatedToFinalInventoryDate(String id) {
        String sql = "SELECT "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(mp_created_date, IFNULL(date_scan_1,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(mp_created_date, IFNULL(date_scan_1,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(mp_created_date, IFNULL(date_scan_1,NOW()))), ' mins, ', SECOND(TIMEDIFF(mp_created_date, IFNULL(date_scan_1,NOW()))), ' secs') AS mp_created_to_tt_scan, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(date_scan_1, IFNULL(date_scan_2,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(date_scan_1, IFNULL(date_scan_2,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(date_scan_1, IFNULL(date_scan_2,NOW()))), ' mins, ', SECOND(TIMEDIFF(date_scan_1, IFNULL(date_scan_2,NOW()))), ' secs') AS tt_scan_to_bs_scan, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(date_scan_2, IFNULL(shipping_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(date_scan_2, IFNULL(shipping_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(date_scan_2, IFNULL(shipping_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(date_scan_2, IFNULL(shipping_date,NOW()))), ' secs') AS bs_scan_to_ship_date, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shipping_date, IFNULL(inventory_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shipping_date, IFNULL(inventory_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shipping_date, IFNULL(inventory_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shipping_date, IFNULL(inventory_date,NOW()))), ' secs') AS ship_date_to_inv_date "
                + "FROM  cdars_wh_shipping "
                + "WHERE request_id = '" + id + "'";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setMpCreatedToTtScan(rs.getString("mp_created_to_tt_scan"));
                whStatusLog.setTtScanToBsScan(rs.getString("tt_scan_to_bs_scan"));
                whStatusLog.setBsScanToShip(rs.getString("bs_scan_to_ship_date"));
                whStatusLog.setShipToInventory(rs.getString("ship_date_to_inv_date"));

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

    public WhStatusLog getTLRetrieveRequestToClose(String id) {
        String sql = "SELECT "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))), ' secs') AS req_received_date, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))), ' secs') AS received_date_to_ship_date, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))), ' secs') AS ship_date_to_bs_scan, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))), ' secs') AS bs_scan_to_tt_scan "
                + "FROM  cdars_wh_retrieval "
                + "WHERE request_id = '" + id + "'";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setRequestToVerifiedDate(rs.getString("req_received_date"));
                whStatusLog.setVerifiedDatetoShipDate(rs.getString("received_date_to_ship_date"));
                whStatusLog.setShipDateToBsScan(rs.getString("ship_date_to_bs_scan"));
                whStatusLog.setBsScanToTtScan(rs.getString("bs_scan_to_tt_scan"));

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

    public List<WhStatusLog> getTLRetrieveRequestToCloseList() { //timelapse notification for retrieve
        String sql = "SELECT "
                + "request_id,"
                + "hardware_type, hardware_id, mp_no,load_card_id, program_card_id, "
                + "HOUR(TIMEDIFF(requested_date,verified_date)) AS req_received_date_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(requested_date, verified_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(requested_date, verified_date)), 24), ' hours, ', MINUTE(TIMEDIFF(requested_date, verified_date)), ' mins, ', SECOND(TIMEDIFF(requested_date, verified_date)), ' secs') AS req_received_date, "
                + "HOUR(TIMEDIFF(requested_date,IFNULL(verified_date,NOW()))) AS req_received_date_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(requested_date, IFNULL(verified_date,NOW()))), ' secs') AS req_received_date_temp, "
                + "HOUR(TIMEDIFF(verified_date,shipping_date)) AS received_date_to_ship_date_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(verified_date,shipping_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(verified_date,shipping_date)), 24), ' hours, ', MINUTE(TIMEDIFF(verified_date,shipping_date)), ' mins, ', SECOND(TIMEDIFF(verified_date, shipping_date)), ' secs') AS received_date_to_ship_date, "
                + "HOUR(TIMEDIFF(verified_date,IFNULL(shipping_date,NOW()))) AS received_date_to_ship_date_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(verified_date, IFNULL(shipping_date,NOW()))), ' secs') AS received_date_to_ship_date_temp, "
                + "HOUR(TIMEDIFF(shipping_date,barcode_verified_date)) AS ship_date_to_bs_scan_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shipping_date,barcode_verified_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shipping_date, barcode_verified_date)), 24), ' hours, ', MINUTE(TIMEDIFF(shipping_date, barcode_verified_date)), ' mins, ', SECOND(TIMEDIFF(shipping_date,barcode_verified_date)), ' secs') AS ship_date_to_bs_scan, "
                + "HOUR(TIMEDIFF(shipping_date,IFNULL(barcode_verified_date,NOW()))) AS ship_date_to_bs_scan_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shipping_date, IFNULL(barcode_verified_date,NOW()))), ' secs') AS ship_date_to_bs_scan_temp,"
                + "HOUR(TIMEDIFF(barcode_verified_date,tt_verified_date)) AS bs_scan_to_tt_scan_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(barcode_verified_date,tt_verified_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(barcode_verified_date,tt_verified_date)), 24), ' hours, ', MINUTE(TIMEDIFF(barcode_verified_date,tt_verified_date)), ' mins, ', SECOND(TIMEDIFF(barcode_verified_date,tt_verified_date)), ' secs') AS bs_scan_to_tt_scan, "
                + "HOUR(TIMEDIFF(barcode_verified_date,IFNULL(tt_verified_date,NOW()))) AS bs_scan_to_tt_scan_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(barcode_verified_date, IFNULL(tt_verified_date,NOW()))), ' secs') AS bs_scan_to_tt_scan_temp "
                + "FROM cdars_wh_retrieval "
                + "WHERE flag = '0' "
                + "ORDER BY hardware_type ASC";
        List<WhStatusLog> whStatusLogList = new ArrayList<WhStatusLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhStatusLog whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setEquipmentId(rs.getString("hardware_id"));
                whStatusLog.setEquipmentType(rs.getString("hardware_type"));
                whStatusLog.setMpNo(rs.getString("mp_no"));
                whStatusLog.setLoadCard(rs.getString("load_card_id"));
                whStatusLog.setProgramCard(rs.getString("program_card_id"));
                whStatusLog.setRequestToVerifiedDate(rs.getString("req_received_date"));
                whStatusLog.setVerifiedDatetoShipDate(rs.getString("received_date_to_ship_date"));
                whStatusLog.setShipDateToBsScan(rs.getString("ship_date_to_bs_scan"));
                whStatusLog.setBsScanToTtScan(rs.getString("bs_scan_to_tt_scan"));
                whStatusLog.setRequestToVerifiedDateTemp(rs.getString("req_received_date_temp"));
                whStatusLog.setVerifiedDatetoShipDateTemp(rs.getString("received_date_to_ship_date_temp"));
                whStatusLog.setShipDateToBsScanTemp(rs.getString("ship_date_to_bs_scan_temp"));
                whStatusLog.setBsScanToTtScanTemp(rs.getString("bs_scan_to_tt_scan_temp"));
                whStatusLog.setRequestToVerifiedDate24(rs.getString("req_received_date_24"));
                whStatusLog.setVerifiedDatetoShipDate24(rs.getString("received_date_to_ship_date_24"));
                whStatusLog.setShipDateToBsScan24(rs.getString("ship_date_to_bs_scan_24"));
                whStatusLog.setBsScanToTtScan24(rs.getString("bs_scan_to_tt_scan_24"));
                whStatusLog.setRequestToVerifiedDateTemp24(rs.getString("req_received_date_temp_24"));
                whStatusLog.setVerifiedDatetoShipDateTemp24(rs.getString("received_date_to_ship_date_temp_24"));
                whStatusLog.setShipDateToBsScanTemp24(rs.getString("ship_date_to_bs_scan_temp_24"));
                whStatusLog.setBsScanToTtScanTemp24(rs.getString("bs_scan_to_tt_scan_temp_24"));
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

    public WhStatusLog getTLReqToApproveAndApproveToMpCreatedForRetrievalShipment(String id) {
        String sql = "SELECT "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' secs') AS req_to_approve, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(final_approved_date, IFNULL(re.mp_created_date,NOW()))), ' secs') AS approve_to_mp_created "
                + "FROM  cdars_wh_request re, (SELECT DISTINCT Item.request_id AS request_id FROM "
                + "(SELECT lo.* FROM cdars_wh_status_log lo, "
                + "(SELECT re.id AS id FROM cdars_wh_request re, (SELECT inv.request_id AS ii FROM cdars_wh_request res, cdars_wh_inventory inv WHERE res.inventory_id = inv.id AND res.id = '" + id + "') AS invid "
                + "WHERE re.id = invid.ii) AS test WHERE test.id = lo.request_id ORDER BY lo.status_date DESC) AS Item ) AS testtt "
                + "WHERE re.id = testtt.request_id";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setRequestToApprove(rs.getString("req_to_approve"));
                whStatusLog.setApproveToMPCreated(rs.getString("approve_to_mp_created"));
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

    public WhStatusLog getTLMpCreatedToFinalInventoryDateForRetrievalShipment(String id) {
        String sql = "SELECT "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.mp_created_date, IFNULL(shi.date_scan_1,NOW()))), ' secs') AS mp_created_to_tt_scan, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_1, IFNULL(shi.date_scan_2,NOW()))), ' secs') AS tt_scan_to_bs_scan, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.date_scan_2, IFNULL(shi.shipping_date,NOW()))), ' secs') AS bs_scan_to_ship_date, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(shi.shipping_date, IFNULL(shi.inventory_date,NOW()))), ' secs') AS ship_date_to_inv_date "
                + "FROM  cdars_wh_shipping shi,(SELECT DISTINCT Item.request_id AS request_id FROM "
                + "(SELECT lo.* FROM cdars_wh_status_log lo, "
                + "(SELECT re.id AS id FROM cdars_wh_request re, (SELECT inv.request_id AS ii FROM cdars_wh_request res, cdars_wh_inventory inv WHERE res.inventory_id = inv.id AND res.id = '" + id + "') AS invid "
                + "WHERE re.id = invid.ii) AS test WHERE test.id = lo.request_id ORDER BY lo.status_date DESC) AS Item ) AS testtt "
                + "WHERE shi.request_id = testtt.request_id";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setMpCreatedToTtScan(rs.getString("mp_created_to_tt_scan"));
                whStatusLog.setTtScanToBsScan(rs.getString("tt_scan_to_bs_scan"));
                whStatusLog.setBsScanToShip(rs.getString("bs_scan_to_ship_date"));
                whStatusLog.setShipToInventory(rs.getString("ship_date_to_inv_date"));

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

    public WhStatusLog getCountReqIdAtShip(String id) {
        String sql = "SELECT COUNT(*) AS count FROM "
                + "cdars_wh_shipping "
                + "WHERE request_id = '" + id + "'";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setCount(rs.getString("count"));
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

    public WhStatusLog getCountReqIdAtRetrieval(String id) {
        String sql = "SELECT COUNT(*) AS count FROM "
                + "cdars_wh_retrieval "
                + "WHERE request_id = '" + id + "'";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setCount(rs.getString("count"));
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

    public WhStatusLog getCountShipRequestIdAtRetrieval(String id) {
        String sql = "SELECT count(*) AS count "
                + "FROM cdars_wh_request re, "
                + "(SELECT DISTINCT Item.request_id AS request_id FROM (SELECT lo.* FROM cdars_wh_status_log lo, "
                + "(SELECT re.id AS id FROM cdars_wh_request re, "
                + "(SELECT inv.request_id AS ii FROM cdars_wh_request res, cdars_wh_inventory inv WHERE res.inventory_id = inv.id AND res.id = '" + id + "') AS invid "
                + "WHERE re.id = invid.ii) AS test WHERE test.id = lo.request_id "
                + "ORDER BY lo.status_date DESC) AS Item ) AS testtt "
                + "WHERE re.id = testtt.request_id";
        WhStatusLog whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhStatusLog();
                whStatusLog.setCount(rs.getString("count"));
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
}
