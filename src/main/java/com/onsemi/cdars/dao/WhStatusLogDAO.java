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
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, re.mp_created_date)), ' mins, ', SECOND(TIMEDIFF(final_approved_date, re.mp_created_date)), ' secs') AS approve_to_mp_created "
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

    public WhStatusLog getTLReqToApproveAndApproveToMpCreatedForRetrievalShipment(String id) {
        String sql = "SELECT "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.requested_date, IFNULL(final_approved_date,NOW()))), ' secs') AS req_to_approve, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(final_approved_date, re.mp_created_date)), 24), ' hours, ', MINUTE(TIMEDIFF(final_approved_date, re.mp_created_date)), ' mins, ', SECOND(TIMEDIFF(final_approved_date, re.mp_created_date)), ' secs') AS approve_to_mp_created "
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
}
