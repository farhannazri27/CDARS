package com.onsemi.cdars.dao;

import com.onsemi.cdars.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.model.WhUslReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhUslReportDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhUslReportDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public WhUslReportDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public Integer getCountTotalShipDec16() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countDec16 FROM cdars_wh_request re, cdars_wh_shipping sh WHERE "
                    + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Ship' AND sh.request_id = re.id"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countDec16");
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

    public Integer getCountTotalRetrieveDec16() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countDec16 FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE "
                    + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Retrieve' AND rt.request_id = re.id "
                    + "AND rt.flag != '5'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countDec16");
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

    public Integer getCountTotalShipCurrentYearWithCurrentMonth(String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countJan FROM cdars_wh_request re, cdars_wh_shipping sh WHERE "
                    + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' AND sh.request_id = re.id"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countJan");
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

    public Integer getCountTotalShipByYearAndByMonth(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countJan FROM cdars_wh_request re, cdars_wh_shipping sh WHERE "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' AND sh.request_id = re.id"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countJan");
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

    public Integer getCountTotalShipByYearAndByMonthAndByEqptType(String month, String year, String eqptType) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countJan FROM cdars_wh_request re, cdars_wh_shipping sh "
                    + "WHERE YEAR(re.requested_date) = '" + year + "' "
                    + "AND MONTH(re.requested_date) = '" + month + "' "
                    + "AND re.request_type = 'Ship' "
                    + "AND re.equipment_type LIKE '" + eqptType + "' "
                    + "AND sh.request_id = re.id"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countJan");
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

    public Integer getCountTotalRetrieveCurrentYearWithCurrentMonth(String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countJan FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE "
                    + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' AND rt.request_id = re.id "
                    + "AND rt.flag != '5'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countJan");
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

    public Integer getCountTotalRetrieveByYearAndByMonth(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countJan FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' AND rt.request_id = re.id "
                    + "AND rt.flag != '5'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countJan");
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

    public Integer getCountTotalRetrieveByYearAndByMonthAndByEqptType(String month, String year, String eqptType) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countJan FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE "
                    + "YEAR(re.requested_date) = '" + year + "' AND "
                    + "MONTH(re.requested_date) = '" + month + "' AND "
                    + "re.request_type = 'Retrieve' AND "
                    + "re.equipment_type LIKE '" + eqptType + "' AND "
                    + "rt.request_id = re.id "
                    + "AND rt.flag != '5'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countJan");
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

    public Integer getCountShipFailDec16() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFailDec16 FROM(SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                    + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Ship') AS total "
                    + "WHERE total.totalHourTake > 120"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFailDec16");
//                LOGGER.info("totalFailDec16..........." + count);
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

    public Integer getCountShipFailCurrentYearWithCustomMonth(String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFail FROM(SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                    + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS total "
                    + "WHERE total.totalHourTake > 120"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFail");
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

    public Integer getCountShipFailByYearAndByMonth(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFail FROM(SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS total "
                    + "WHERE total.totalHourTake > 120"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFail");
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

    public Integer getCountShipFailByYearAndByMonthAndByEqptType(String month, String year, String eqptType) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFail FROM(SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_shipping sh "
                    + "WHERE re.id = sh.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND "
                    + "MONTH(re.requested_date) = '" + month + "' AND "
                    + "re.request_type = 'Ship' AND "
                    + "re.equipment_type LIKE '" + eqptType + "') AS total "
                    + "WHERE total.totalHourTake > 120"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFail");
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

    public Integer getCountRetrieveFailDec16() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFailDec16 FROM (SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                    + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Retrieve' AND rt.flag != '5') AS total WHERE total.totalHourTake > 96"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFailDec16");
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

    public Integer getCountRetrieveFailCurrentYearWithCustomMonth(String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFail FROM (SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                    + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' AND rt.flag != '5') AS total "
                    + "WHERE total.totalHourTake > 96"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFail");
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

    public Integer getCountRetrieveFailByYearAndByMonth(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFail FROM (SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' AND rt.flag != '5') AS total "
                    + "WHERE total.totalHourTake > 96"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFail");
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

    public Integer getCountRetrieveFailByYearAndByMonthAndByEqptType(String month, String year, String eqptType) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFail FROM (SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) as totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_retrieval rt "
                    + "WHERE re.id = rt.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND "
                    + "MONTH(re.requested_date) = '" + month + "' AND "
                    + "re.request_type = 'Retrieve' AND "
                    + "re.equipment_type LIKE '" + eqptType + "' AND "
                    + "rt.flag != '5') AS total "
                    + "WHERE total.totalHourTake > 96"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFail");
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

    public List<WhUslReport> GetListOfFailedShipItemDec16() { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW()))+ "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Ship' "
                + "AND test.idTest = re.id AND test.totalHourTake > 120 ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedShipItemCurrentYearCustomMonth(String month) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' "
                + "AND test.idTest = re.id AND test.totalHourTake > 120 ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedShipItemByYearAndByMonth(String month, String year) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' "
                + "AND test.idTest = re.id AND test.totalHourTake > 120 ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTable(String month, String year) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake, ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti ON re.id = ti.request_id , cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' AND ti.request_id is null "
                + "AND test.idTest = re.id AND test.totalHourTake > 120 " //                + "ORDER BY re.equipment_type"
                ;
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public Integer GetCountOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTable(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count "
                    + "FROM (SELECT re.id AS idTest, "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS test, "
                    + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti ON re.id = ti.request_id , cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' AND ti.request_id is null "
                    + "AND test.idTest = re.id AND test.totalHourTake > 120 "
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

    public Integer GetCountOfFailedRetrieveItemByYearAndByMonthLeftJoinTimelapseTable(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count "
                    + "FROM (SELECT re.id AS idTest, "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                    + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve') AS test, "
                    + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti on re.id = ti.request_id, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                    + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' AND ti.request_id is null "
                    + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5'"
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

    public List<WhUslReport> GetListOfFailedShipItemInnerJoinTimelapseTable() { //timelapse notification ship
        String sql = "SELECT re.id AS reqId,DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view,re.equipment_type AS eqptType, re.equipment_id AS eqptId, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, re.mp_no AS mpNo,"
                + "ti.category, ti.ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), "
                + "IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_timelapse ti, cdars_wh_request re, cdars_wh_shipping sh "
                + "WHERE re.id = ti.request_id AND sh.request_id = re.id ORDER BY ti.id DESC,re.equipment_type ";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setCategory(rs.getString("category"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
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

    public List<WhUslReport> GetListOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTableForExcel(String month, String year) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake, ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti ON re.id = ti.request_id , cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' "
                //                + "AND ti.request_id is null "      //disabled to displayed all even without ca and rc
                + "AND test.idTest = re.id AND test.totalHourTake > 120 "
                + "ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public WhUslReport GetListOfFailedShipItemByYearAndByMonthLeftJoinTimelapseTableWithReqId(String reqId) {
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake, ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                //                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship') AS test, "
                + "re.id = '" + reqId + "' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti ON re.id = ti.request_id , cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                //                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Ship' AND ti.request_id is null "
                + "re.id = '" + reqId + "' AND re.request_type = 'Ship' AND ti.request_id is null "
                + "AND test.idTest = re.id AND test.totalHourTake > 120 " //                + "ORDER BY re.equipment_type"
                ;
        WhUslReport whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public WhUslReport GetListOfFailedShipItemByInnerJoinTimelapseTableWithReqId(String reqId) {
        String sql = "SELECT re.id AS reqId,DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view,re.equipment_type AS eqptType, re.equipment_id AS eqptId, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, re.mp_no AS mpNo,"
                + "ti.category, ti.ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.id AS tiId, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), "
                + "IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_timelapse ti, cdars_wh_request re, cdars_wh_shipping sh "
                + "WHERE re.id = ti.request_id AND sh.request_id = re.id AND re.id = '" + reqId + "' ORDER BY ti.id DESC,re.equipment_type";
        WhUslReport whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setTiId(rs.getString("tiId"));
                whStatusLog.setCategory(rs.getString("category"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public List<WhUslReport> GetListOfFailedShipItemByYearAndByMonthAndByEqptType(String month, String year, String eqptType) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Ship' "
                + "AND test.idTest = re.id AND test.totalHourTake > 120 ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedShipItemByYearAndByMonthAndByEqptTypeWithTimelapseTable(String month, String year, String eqptType) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) AS reqToappr, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) AS mpCreateToTTscan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) AS ttScanToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) AS bcScanToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW())) AS shipToInv, "
                + "test.totalHourTake AS totalHourTake,ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(re.final_approved_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.mp_created_date,NOW()), IFNULL(sh.date_scan_1,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_1,NOW()), IFNULL(sh.date_scan_2,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.date_scan_2,NOW()), IFNULL(sh.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipping_date,NOW()), IFNULL(sh.inventory_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Ship') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti ON re.id = ti.request_id , cdars_wh_shipping sh WHERE re.id = sh.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Ship' "
                + "AND test.idTest = re.id AND test.totalHourTake > 120 ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToApp(rs.getString("reqToappr"));
                whStatusLog.setMpCreateToTtScan(rs.getString("mpCreateToTTscan"));
                whStatusLog.setTtScanToBcScan(rs.getString("ttScanToBcScan"));
                whStatusLog.setBcScanToShip(rs.getString("bcScanToShip"));
                whStatusLog.setShipToInv(rs.getString("shipToInv"));
                whStatusLog.setTotalHourTakeShip(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemDec16() { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '2016' AND MONTH(re.requested_date) = '12' AND re.request_type = 'Retrieve' "
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemCurrentYearCustomMonth(String month) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = YEAR(CURRENT_DATE) AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' "
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemByYearAndByMonth(String month, String year) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' "
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemByYearAndByMonthLeftJoinTimelapseTable(String month, String year) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard,DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake,ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti on re.id = ti.request_id, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve' AND ti.request_id is null "
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemInnerJoinTimelapseTable() { //timelapse notification ship
        String sql = "SELECT re.id AS reqID, re.mp_no AS mpNo, DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view, re.equipment_type AS eqptType, "
                + "re.equipment_id AS eqptId, re.load_card_id as loadCard, re.program_card_id AS programCard, "
                + "ti.category, ti.ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_timelapse ti, cdars_wh_request re, cdars_wh_retrieval rt "
                + "WHERE re.id = ti.request_id AND rt.request_id = re.id "
                + "ORDER BY ti.id DESC, re.equipment_type ";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setCategory(rs.getString("category"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemByYearAndByMonthLeftJoinTimelapseTableForExcel(String month, String year) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake,ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti on re.id = ti.request_id, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.request_type = 'Retrieve'"
                //                + " AND ti.request_id is null " // disabled bcoz wanna show all timelapse
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemByYearAndByMonthAndByEqptType(String month, String year, String eqptType) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Retrieve' "
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
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

    public List<WhUslReport> GetListOfFailedRetrieveItemByYearAndByMonthAndByEqptTypeWithTimelapseTable(String month, String year, String eqptType) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake,ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate  "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti on re.id = ti.request_id, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Retrieve' "
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' ORDER BY re.equipment_type";
        List<WhUslReport> whStatusLogList = new ArrayList<WhUslReport>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhUslReport whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public WhUslReport GetListOfFailedRetrieveItemWithTimelapseTableByReqId(String reqId) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.equipment_type AS eqptType, re.equipment_id AS eqptId, re.mp_no AS mpNo, "
                + "re.load_card_id AS loadCard, re.program_card_id AS programCard,DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "test.totalHourTake AS totalHourTake,ti.ca AS ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.created_date AS createdDate  "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_request re, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                //                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Retrieve') AS test, "
                + "re.id = '" + reqId + "' AND re.request_type = 'Retrieve') AS test, "
                + "cdars_wh_request re LEFT JOIN cdars_wh_timelapse ti on re.id = ti.request_id, cdars_wh_retrieval rt WHERE re.id = rt.request_id AND "
                //                + "YEAR(re.requested_date) = '" + year + "' AND MONTH(re.requested_date) = '" + month + "' AND re.equipment_type LIKE '" + eqptType + "' AND re.request_type = 'Retrieve' "
                + "re.id = '" + reqId + "' AND re.request_type = 'Retrieve' "
                + "AND test.idTest = re.id AND test.totalHourTake > 96 AND rt.flag != '5' " //                + "ORDER BY re.equipment_type"
                ;
        WhUslReport whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCreatedDate(rs.getString("createdDate"));
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

    public WhUslReport GetListOfFailedRetrieveItemByInnerJoinTimelapseTableWithReqId(String reqId) {
        String sql = "SELECT re.id AS reqID, re.mp_no AS mpNo, DATE_FORMAT(re.requested_date,'%d %M %Y') AS requested_date_view, re.equipment_type AS eqptType, "
                + "re.equipment_id AS eqptId, re.load_card_id as loadCard, re.program_card_id AS programCard, "
                + "ti.category, ti.ca, ti.root_cause AS rootCause, ti.created_by AS createdBy, ti.id AS tiId, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) AS reqToVerify, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) AS VerifyToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) AS shipToBcScan, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW())) AS bcScanToTtScan, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.requested_date,NOW()), IFNULL(rt.verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.verified_date,NOW()), IFNULL( rt.shipping_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL( rt.shipping_date,NOW()), IFNULL(rt.barcode_verified_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(rt.barcode_verified_date,NOW()), IFNULL(rt.tt_verified_date,NOW()))) AS totalHourTake "
                + "FROM cdars_wh_timelapse ti, cdars_wh_request re, cdars_wh_retrieval rt "
                + "WHERE re.id = ti.request_id AND rt.request_id = re.id AND re.id = '" + reqId + "' ";
        WhUslReport whStatusLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new WhUslReport();
                whStatusLog.setRequestId(rs.getString("reqId"));
                whStatusLog.setEqptType(rs.getString("eqptType"));
                whStatusLog.setEqptId(rs.getString("eqptId"));
                whStatusLog.setMpNo(rs.getString("mpNo"));
                whStatusLog.setRequestDate(rs.getString("requested_date_view"));
                whStatusLog.setLoadCard(rs.getString("loadCard"));
                whStatusLog.setProgramCard(rs.getString("programCard"));
                whStatusLog.setReqToVer(rs.getString("reqToVerify"));
                whStatusLog.setVerToShip(rs.getString("VerifyToShip"));
                whStatusLog.setShipToBcScan(rs.getString("shipToBcScan"));
                whStatusLog.setBcScanToTtScan(rs.getString("bcScanToTtScan"));
                whStatusLog.setTotalHourTakeRetrieve(rs.getString("totalHourTake"));
                whStatusLog.setTiId(rs.getString("tiId"));
                whStatusLog.setCa(rs.getString("ca"));
                whStatusLog.setRootCause(rs.getString("rootCause"));
                whStatusLog.setCreatedBy(rs.getString("createdBy"));
                whStatusLog.setCategory(rs.getString("category"));
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
