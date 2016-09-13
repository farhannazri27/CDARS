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
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhShippingDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhShippingDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public WhShippingDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertWhShipping(WhShipping whShipping) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_shipping (request_id, box_id, mp_no, mp_expiry_date, hardware_barcode_1, date_scan_1, hardware_barcode_2, date_scan_2, shipping_date, status, remarks, flag, created_by, created_date, modified_by, modified_date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whShipping.getRequestId());
            ps.setString(2, whShipping.getBoxId());
            ps.setString(3, whShipping.getMpNo());
            ps.setString(4, whShipping.getMpExpiryDate());
            ps.setString(5, whShipping.getHardwareBarcode1());
            ps.setString(6, whShipping.getDateScan1());
            ps.setString(7, whShipping.getHardwareBarcode2());
            ps.setString(8, whShipping.getDateScan2());
            ps.setString(9, whShipping.getShippingDate());
            ps.setString(10, whShipping.getStatus());
            ps.setString(11, whShipping.getRemarks());
            ps.setString(12, whShipping.getFlag());
            ps.setString(13, whShipping.getCreatedBy());
            ps.setString(14, whShipping.getModifiedBy());
            ps.setString(15, whShipping.getModifiedDate());
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

    public QueryResult updateWhShipping(WhShipping whShipping) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_shipping SET request_id = ?, box_id = ?, mp_no = ?, mp_expiry_date = ?, hardware_barcode_1 = ?, date_scan_1 = ?, hardware_barcode_2 = ?, date_scan_2 = ?, shipping_date = ?, status = ?, remarks = ?, flag = ?, created_by = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, whShipping.getRequestId());
            ps.setString(2, whShipping.getBoxId());
            ps.setString(3, whShipping.getMpNo());
            ps.setString(4, whShipping.getMpExpiryDate());
            ps.setString(5, whShipping.getHardwareBarcode1());
            ps.setString(6, whShipping.getDateScan1());
            ps.setString(7, whShipping.getHardwareBarcode2());
            ps.setString(8, whShipping.getDateScan2());
            ps.setString(9, whShipping.getShippingDate());
            ps.setString(10, whShipping.getStatus());
            ps.setString(11, whShipping.getRemarks());
            ps.setString(12, whShipping.getFlag());
            ps.setString(13, whShipping.getCreatedBy());;
            ps.setString(14, whShipping.getModifiedBy());
            ps.setString(15, whShipping.getId());
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

    public QueryResult updateWhShippingStatus(WhShipping whShipping) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_shipping SET request_id = ?, status = ? WHERE id = ?"
            );
            ps.setString(1, whShipping.getRequestId());
            ps.setString(2, whShipping.getStatus());
            ps.setString(3, whShipping.getId());
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
    
     public QueryResult updateWhShippingFlag(WhShipping whShipping) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_shipping SET request_id = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whShipping.getRequestId());
            ps.setString(2, whShipping.getFlag());
            ps.setString(3, whShipping.getId());
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

    public QueryResult updateWhShippingMp(WhShipping whShipping) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_shipping SET mp_no = ?, mp_expiry_date = ?,  status = ?,  modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, whShipping.getMpNo());
            ps.setString(2, whShipping.getMpExpiryDate());
            ps.setString(3, whShipping.getStatus());
            ps.setString(4, whShipping.getModifiedBy());
            ps.setString(5, whShipping.getId());
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

    public QueryResult updateWhShippingTt(WhShipping whShipping) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_shipping SET hardware_barcode_1 = ?, date_scan_1 = NOW(), status = ?,  modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, whShipping.getHardwareBarcode1());
            ps.setString(2, whShipping.getStatus());
            ps.setString(3, whShipping.getModifiedBy());
            ps.setString(4, whShipping.getId());
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

    public QueryResult updateWhShippingBs(WhShipping whShipping) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_shipping SET hardware_barcode_2 = ?, date_scan_2 = NOW(), status = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, whShipping.getHardwareBarcode2());
            ps.setString(2, whShipping.getStatus());
            ps.setString(3, whShipping.getModifiedBy());
            ps.setString(4, whShipping.getId());
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

    public QueryResult deleteWhShipping(String whShippingId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_wh_shipping WHERE id = '" + whShippingId + "'"
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

    public WhShipping getWhShipping(String whShippingId) {
        String sql = "SELECT * FROM cdars_wh_shipping WHERE id = '" + whShippingId + "'";
        WhShipping whShipping = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whShipping = new WhShipping();
                whShipping.setId(rs.getString("id"));
                whShipping.setRequestId(rs.getString("request_id"));
                whShipping.setBoxId(rs.getString("box_id"));
                whShipping.setMpNo(rs.getString("mp_no"));
                whShipping.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whShipping.setHardwareBarcode1(rs.getString("hardware_barcode_1"));
                whShipping.setDateScan1(rs.getString("date_scan_1"));
                whShipping.setHardwareBarcode2(rs.getString("hardware_barcode_2"));
                whShipping.setDateScan2(rs.getString("date_scan_2"));
                whShipping.setShippingDate(rs.getString("shipping_date"));
                whShipping.setStatus(rs.getString("status"));
                whShipping.setRemarks(rs.getString("remarks"));
                whShipping.setFlag(rs.getString("flag"));
                whShipping.setCreatedBy(rs.getString("created_by"));
                whShipping.setCreatedDate(rs.getString("created_date"));
                whShipping.setModifiedBy(rs.getString("modified_by"));
                whShipping.setModifiedDate(rs.getString("modified_date"));
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
        return whShipping;
    }

    public WhShipping getWhShippingMergeWithRequest(String whShippingId) {
        String sql = "SELECT sh.*, DATE_FORMAT(sh.mp_expiry_date,'%Y-%m-%d') AS new_mp_expiry_date, re.equipment_type AS equipment_type, re.equipment_id AS equipment_id, re.quantity AS quantity, re.requested_by AS requested_by, re.requested_date, DATE_FORMAT(re.requested_date,'%d %M %Y') AS view_requested_date "
                + "FROM cdars_wh_shipping sh, cdars_wh_request re WHERE sh.request_id = re.id AND sh.id = '" + whShippingId + "'";
        WhShipping whShipping = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whShipping = new WhShipping();
                whShipping.setId(rs.getString("id"));
                whShipping.setRequestId(rs.getString("request_id"));
                whShipping.setBoxId(rs.getString("box_id"));
                whShipping.setMpNo(rs.getString("mp_no"));
                whShipping.setMpExpiryDate(rs.getString("new_mp_expiry_date"));
                whShipping.setHardwareBarcode1(rs.getString("hardware_barcode_1"));
                whShipping.setDateScan1(rs.getString("date_scan_1"));
                whShipping.setHardwareBarcode2(rs.getString("hardware_barcode_2"));
                whShipping.setDateScan2(rs.getString("date_scan_2"));
                whShipping.setShippingDate(rs.getString("shipping_date"));
                whShipping.setStatus(rs.getString("status"));
                whShipping.setRemarks(rs.getString("remarks"));
                whShipping.setFlag(rs.getString("flag"));
                whShipping.setCreatedBy(rs.getString("created_by"));
                whShipping.setCreatedDate(rs.getString("created_date"));
                whShipping.setModifiedBy(rs.getString("modified_by"));
                whShipping.setModifiedDate(rs.getString("modified_date"));

                //utk display data from table wh_request
                whShipping.setRequestEquipmentType(rs.getString("equipment_type"));
                whShipping.setRequestEquipmentId(rs.getString("equipment_id"));
                whShipping.setRequestQuantity(rs.getString("quantity"));
                whShipping.setRequestRequestedBy(rs.getString("requested_by"));
                whShipping.setRequestRequestedDate(rs.getString("requested_date"));
                whShipping.setRequestViewRequestedDate(rs.getString("view_requested_date"));
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
        return whShipping;
    }

    public List<WhShipping> getWhShippingList() {
        String sql = "SELECT * FROM cdars_wh_shipping ORDER BY id ASC";
        List<WhShipping> whShippingList = new ArrayList<WhShipping>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhShipping whShipping;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whShipping = new WhShipping();
                whShipping.setId(rs.getString("id"));
                whShipping.setRequestId(rs.getString("request_id"));
                whShipping.setBoxId(rs.getString("box_id"));
                whShipping.setMpNo(rs.getString("mp_no"));
                whShipping.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whShipping.setHardwareBarcode1(rs.getString("hardware_barcode_1"));
                whShipping.setDateScan1(rs.getString("date_scan_1"));
                whShipping.setHardwareBarcode2(rs.getString("hardware_barcode_2"));
                whShipping.setDateScan2(rs.getString("date_scan_2"));
                whShipping.setShippingDate(rs.getString("shipping_date"));
                whShipping.setStatus(rs.getString("status"));
                whShipping.setRemarks(rs.getString("remarks"));
                whShipping.setFlag(rs.getString("flag"));
                whShipping.setCreatedBy(rs.getString("created_by"));
                whShipping.setCreatedDate(rs.getString("created_date"));
                whShipping.setModifiedBy(rs.getString("modified_by"));
                whShipping.setModifiedDate(rs.getString("modified_date"));
                whShippingList.add(whShipping);
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
        return whShippingList;
    }

    public List<WhShipping> getWhShippingListMergeWithRequest() {
        String sql = "SELECT sh.*,re.equipment_type AS equipment_type, re.equipment_id AS equipment_id, re.quantity AS quantity, re.requested_by AS requested_by, re.requested_date, DATE_FORMAT(re.requested_date,'%d %M %Y') AS view_requested_date "
                + "FROM cdars_wh_shipping sh, cdars_wh_request re WHERE sh.request_id = re.id ORDER BY id DESC";
        List<WhShipping> whShippingList = new ArrayList<WhShipping>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhShipping whShipping;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whShipping = new WhShipping();
                whShipping.setId(rs.getString("id"));
                whShipping.setRequestId(rs.getString("request_id"));
                whShipping.setBoxId(rs.getString("box_id"));
                whShipping.setMpNo(rs.getString("mp_no"));
                whShipping.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whShipping.setHardwareBarcode1(rs.getString("hardware_barcode_1"));
                whShipping.setDateScan1(rs.getString("date_scan_1"));
                whShipping.setHardwareBarcode2(rs.getString("hardware_barcode_2"));
                whShipping.setDateScan2(rs.getString("date_scan_2"));
                whShipping.setShippingDate(rs.getString("shipping_date"));
                whShipping.setStatus(rs.getString("status"));
                whShipping.setRemarks(rs.getString("remarks"));
                whShipping.setFlag(rs.getString("flag"));
                whShipping.setCreatedBy(rs.getString("created_by"));
                whShipping.setCreatedDate(rs.getString("created_date"));
                whShipping.setModifiedBy(rs.getString("modified_by"));
                whShipping.setModifiedDate(rs.getString("modified_date"));

                //utk display data from table wh_request
                whShipping.setRequestEquipmentType(rs.getString("equipment_type"));
                whShipping.setRequestEquipmentId(rs.getString("equipment_id"));
                whShipping.setRequestQuantity(rs.getString("quantity"));
                whShipping.setRequestRequestedBy(rs.getString("requested_by"));
                whShipping.setRequestRequestedDate(rs.getString("requested_date"));
                whShipping.setRequestViewRequestedDate(rs.getString("view_requested_date"));

                whShippingList.add(whShipping);
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
        return whShippingList;
    }

    public WhShipping getWhShippingMergeWithRequestByMpNo(String mpNo) {
        String sql = "SELECT sh.*, DATE_FORMAT(sh.mp_expiry_date,'%Y-%m-%d') AS new_mp_expiry_date, re.equipment_type AS equipment_type, "
                + "re.equipment_id AS equipment_id, re.quantity AS quantity, re.requested_by AS requested_by, "
                + "re.requestor_email AS requestor_email, "
                + "re.requested_date, DATE_FORMAT(re.requested_date,'%d %M %Y %h:%i %p') AS view_requested_date "
                + "FROM cdars_wh_shipping sh, cdars_wh_request re WHERE sh.request_id = re.id AND sh.mp_no = '" + mpNo + "'";
        WhShipping whShipping = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whShipping = new WhShipping();
                whShipping.setId(rs.getString("id"));
                whShipping.setRequestId(rs.getString("request_id"));
                whShipping.setBoxId(rs.getString("box_id"));
                whShipping.setMpNo(rs.getString("mp_no"));
                whShipping.setMpExpiryDate(rs.getString("new_mp_expiry_date"));
                whShipping.setHardwareBarcode1(rs.getString("hardware_barcode_1"));
                whShipping.setDateScan1(rs.getString("date_scan_1"));
                whShipping.setHardwareBarcode2(rs.getString("hardware_barcode_2"));
                whShipping.setDateScan2(rs.getString("date_scan_2"));
                whShipping.setShippingDate(rs.getString("shipping_date"));
                whShipping.setStatus(rs.getString("status"));
                whShipping.setRemarks(rs.getString("remarks"));
                whShipping.setFlag(rs.getString("flag"));
                whShipping.setCreatedBy(rs.getString("created_by"));
                whShipping.setCreatedDate(rs.getString("created_date"));
                whShipping.setModifiedBy(rs.getString("modified_by"));
                whShipping.setModifiedDate(rs.getString("modified_date"));

                //utk display data from table wh_request
                whShipping.setRequestEquipmentType(rs.getString("equipment_type"));
                whShipping.setRequestEquipmentId(rs.getString("equipment_id"));
                whShipping.setRequestQuantity(rs.getString("quantity"));
                whShipping.setRequestRequestedBy(rs.getString("requested_by"));
                whShipping.setRequestRequestorEmail(rs.getString("requestor_email"));
                whShipping.setRequestRequestedDate(rs.getString("requested_date"));
                whShipping.setRequestViewRequestedDate(rs.getString("view_requested_date"));
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
        return whShipping;
    }

    public Integer getCountMpNo(String mpNo) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_wh_shipping WHERE mp_no = '" + mpNo + "'"
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
}
