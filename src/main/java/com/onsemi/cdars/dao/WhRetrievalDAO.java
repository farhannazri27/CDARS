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
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhRetrievalDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhRetrievalDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public WhRetrievalDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertWhRetrieval(WhRetrieval whRetrieval) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_retrieval (request_id, hardware_type, hardware_id, "
                    + "pcb_a, pcb_a_qty, pcb_b, pcb_b_qty, pcb_c, pcb_c_qty, pcb_ctr, pcb_ctr_qty,"
                    + "hardware_qty, mp_no, mp_expiry_date, location, shelf, rack, requested_by, "
                    + "requested_date, remarks, status, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRetrieval.getRequestId());
            ps.setString(2, whRetrieval.getHardwareType());
            ps.setString(3, whRetrieval.getHardwareId());
            ps.setString(4, whRetrieval.getPcbA());
            ps.setString(5, whRetrieval.getPcbAQty());
            ps.setString(6, whRetrieval.getPcbB());
            ps.setString(7, whRetrieval.getPcbBQty());
            ps.setString(8, whRetrieval.getPcbC());
            ps.setString(9, whRetrieval.getPcbCQty());
            ps.setString(10, whRetrieval.getPcbCtr());
            ps.setString(11, whRetrieval.getPcbCtrQty());
            ps.setString(12, whRetrieval.getHardwareQty());
            ps.setString(13, whRetrieval.getMpNo());
            ps.setString(14, whRetrieval.getMpExpiryDate());
            ps.setString(15, whRetrieval.getLocation());
            ps.setString(16, whRetrieval.getShelf());
            ps.setString(17, whRetrieval.getRack());
            ps.setString(18, whRetrieval.getRequestedBy());
            ps.setString(19, whRetrieval.getRequestedDate());
            ps.setString(20, whRetrieval.getRemarks());
            ps.setString(21, whRetrieval.getStatus());
            ps.setString(22, whRetrieval.getFlag());
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

    public QueryResult updateWhRetrieval(WhRetrieval whRetrieval) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_retrieval SET request_id = ?, hardware_type = ?, hardware_id = ?, hardware_qty = ?, mp_no = ?, mp_expiry_date = ?, location = ?, shelf = ?, rack = ?, requested_by = ?, requested_date = ?, remarks = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whRetrieval.getRequestId());
            ps.setString(2, whRetrieval.getHardwareType());
            ps.setString(3, whRetrieval.getHardwareId());
            ps.setString(4, whRetrieval.getHardwareQty());
            ps.setString(5, whRetrieval.getMpNo());
            ps.setString(6, whRetrieval.getMpExpiryDate());
            ps.setString(7, whRetrieval.getLocation());
            ps.setString(8, whRetrieval.getShelf());
            ps.setString(9, whRetrieval.getRack());
            ps.setString(10, whRetrieval.getRequestedBy());
            ps.setString(11, whRetrieval.getRequestedDate());
            ps.setString(12, whRetrieval.getRemarks());
            ps.setString(13, whRetrieval.getStatus());
            ps.setString(14, whRetrieval.getFlag());
            ps.setString(15, whRetrieval.getId());
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

    public QueryResult updateTt(WhRetrieval whRetrieval) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_retrieval SET tt_verification = ?, tt_verified_by = ?, tt_verified_date = NOW(), status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whRetrieval.getTtVerification());
            ps.setString(2, whRetrieval.getTtVerifiedBy());
            ps.setString(3, whRetrieval.getStatus());
            ps.setString(4, whRetrieval.getFlag());
            ps.setString(5, whRetrieval.getId());
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

    public QueryResult updateWhRetrievalBarcode(WhRetrieval whRetrieval) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_retrieval SET barcode_verification = ?, barcode_verified_by = ?, barcode_verified_date = NOW(), status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whRetrieval.getBarcodeVerification());
            ps.setString(2, whRetrieval.getBarcodeVerifiedBy());
            ps.setString(3, whRetrieval.getStatus());
            ps.setString(4, whRetrieval.getFlag());
            ps.setString(5, whRetrieval.getId());
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

    public QueryResult updateWhRetrievalFromCsv(WhRetrieval whRetrieval) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_retrieval SET request_id = ?,  mp_no = ?, verified_by = ?, verified_date = ?, shipping_by = ?, shipping_date = ?, received_date = NOW(), status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whRetrieval.getRequestId());
            ps.setString(2, whRetrieval.getMpNo());
            ps.setString(3, whRetrieval.getVerifiedBy());
            ps.setString(4, whRetrieval.getVerifiedDate());
            ps.setString(5, whRetrieval.getShippingBy());
            ps.setString(6, whRetrieval.getShippingDate());
            ps.setString(7, whRetrieval.getStatus());
            ps.setString(8, whRetrieval.getFlag());
            ps.setString(9, whRetrieval.getId());
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

    public QueryResult deleteWhRetrieval(String whRetrievalId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_wh_retrieval WHERE id = '" + whRetrievalId + "'"
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

    public WhRetrieval getWhRetrieval(String whRetrievalId) {
        String sql = "SELECT * FROM cdars_wh_retrieval WHERE id = '" + whRetrievalId + "'";
        WhRetrieval whRetrieval = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRetrieval = new WhRetrieval();
                whRetrieval.setId(rs.getString("id"));
                whRetrieval.setRequestId(rs.getString("request_id"));
                whRetrieval.setHardwareType(rs.getString("hardware_type"));
                whRetrieval.setHardwareId(rs.getString("hardware_id"));
                whRetrieval.setPcbA(rs.getString("pcb_a"));
                whRetrieval.setPcbAQty(rs.getString("pcb_a_qty"));
                whRetrieval.setPcbB(rs.getString("pcb_b"));
                whRetrieval.setPcbBQty(rs.getString("pcb_b_qty"));
                whRetrieval.setPcbC(rs.getString("pcb_c"));
                whRetrieval.setPcbCQty(rs.getString("pcb_c_qty"));
                whRetrieval.setPcbCtr(rs.getString("pcb_ctr"));
                whRetrieval.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRetrieval.setHardwareQty(rs.getString("hardware_qty"));
                whRetrieval.setMpNo(rs.getString("mp_no"));
                whRetrieval.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRetrieval.setLocation(rs.getString("location"));
                whRetrieval.setShelf(rs.getString("shelf"));
                whRetrieval.setRack(rs.getString("rack"));
                whRetrieval.setRequestedBy(rs.getString("requested_by"));
                whRetrieval.setRequestedDate(rs.getString("requested_date"));
                whRetrieval.setVerifiedBy(rs.getString("verified_by"));
                whRetrieval.setVerifiedDate(rs.getString("verified_date"));
                whRetrieval.setShippingBy(rs.getString("shipping_by"));
                whRetrieval.setShippingDate(rs.getString("shipping_date"));
                whRetrieval.setReceivedDate(rs.getString("received_date"));
                whRetrieval.setRemarks(rs.getString("remarks"));
                whRetrieval.setStatus(rs.getString("status"));
                whRetrieval.setFlag(rs.getString("flag"));
                whRetrieval.setTtVerification(rs.getString("tt_verification"));
                whRetrieval.setTtVerifiedBy(rs.getString("tt_verified_by"));
                whRetrieval.setTtVerifiedDate(rs.getString("tt_verified_date"));
                whRetrieval.setBarcodeVerification(rs.getString("barcode_verification"));
                whRetrieval.setBarcodeVerifiedBy(rs.getString("barcode_verified_by"));
                whRetrieval.setBarcodeVerifiedDate(rs.getString("barcode_verified_date"));

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
        return whRetrieval;
    }

    public WhRetrieval getWhRetrievalWithDateDisplay(String whRetrievalId) {
        String sql = "SELECT *, "
                + "DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS view_requested_date, "
                + "DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, "
                + "DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, "
                + "DATE_FORMAT(shipping_date,'%d %M %Y %h:%i %p') AS view_shipping_date, "
                + "DATE_FORMAT(received_date,'%d %M %Y %h:%i %p') AS view_received_date, "
                + "DATE_FORMAT(tt_verified_date,'%d %M %Y %h:%i %p') AS view_tt_verified_date, "
                + "DATE_FORMAT(barcode_verified_date,'%d %M %Y %h:%i %p') AS view_tt_barcode_verified_date "
                + "FROM cdars_wh_retrieval WHERE id = '" + whRetrievalId + "'";
        WhRetrieval whRetrieval = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRetrieval = new WhRetrieval();
                whRetrieval.setId(rs.getString("id"));
                whRetrieval.setRequestId(rs.getString("request_id"));
                whRetrieval.setHardwareType(rs.getString("hardware_type"));
                whRetrieval.setHardwareId(rs.getString("hardware_id"));
                whRetrieval.setPcbA(rs.getString("pcb_a"));
                whRetrieval.setPcbAQty(rs.getString("pcb_a_qty"));
                whRetrieval.setPcbB(rs.getString("pcb_b"));
                whRetrieval.setPcbBQty(rs.getString("pcb_b_qty"));
                whRetrieval.setPcbC(rs.getString("pcb_c"));
                whRetrieval.setPcbCQty(rs.getString("pcb_c_qty"));
                whRetrieval.setPcbCtr(rs.getString("pcb_ctr"));
                whRetrieval.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRetrieval.setHardwareQty(rs.getString("hardware_qty"));
                whRetrieval.setMpNo(rs.getString("mp_no"));
                whRetrieval.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRetrieval.setLocation(rs.getString("location"));
                whRetrieval.setShelf(rs.getString("shelf"));
                whRetrieval.setRack(rs.getString("rack"));
                whRetrieval.setRequestedBy(rs.getString("requested_by"));
                whRetrieval.setRequestedDate(rs.getString("requested_date"));
                whRetrieval.setVerifiedBy(rs.getString("verified_by"));
                whRetrieval.setVerifiedDate(rs.getString("verified_date"));
                whRetrieval.setShippingBy(rs.getString("shipping_by"));
                whRetrieval.setShippingDate(rs.getString("shipping_date"));
                whRetrieval.setReceivedDate(rs.getString("received_date"));
                whRetrieval.setRemarks(rs.getString("remarks"));
                whRetrieval.setStatus(rs.getString("status"));
                whRetrieval.setFlag(rs.getString("flag"));
                whRetrieval.setTtVerification(rs.getString("tt_verification"));
                whRetrieval.setTtVerifiedBy(rs.getString("tt_verified_by"));
                whRetrieval.setTtVerifiedDate(rs.getString("tt_verified_date"));
                whRetrieval.setBarcodeVerification(rs.getString("barcode_verification"));
                whRetrieval.setBarcodeVerifiedBy(rs.getString("barcode_verified_by"));
                whRetrieval.setBarcodeVerifiedDate(rs.getString("barcode_verified_date"));

                //date view
                whRetrieval.setViewRequestedDate(rs.getString("view_requested_date"));
                whRetrieval.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whRetrieval.setViewVerifiedDate(rs.getString("view_verified_date"));
                whRetrieval.setViewShippingDate(rs.getString("view_shipping_date"));
                whRetrieval.setViewReceivedDate(rs.getString("view_received_date"));
                whRetrieval.setViewTtVerifiedDate(rs.getString("view_tt_verified_date"));
                whRetrieval.setViewBarcodeVerifiedDate(rs.getString("view_tt_barcode_verified_date"));
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
        return whRetrieval;
    }

    public List<WhRetrieval> getWhRetrievalList() {
        String sql = "SELECT * FROM cdars_wh_retrieval ORDER BY id DESC";
        List<WhRetrieval> whRetrievalList = new ArrayList<WhRetrieval>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRetrieval whRetrieval;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRetrieval = new WhRetrieval();
                whRetrieval.setId(rs.getString("id"));
                whRetrieval.setRequestId(rs.getString("request_id"));
                whRetrieval.setHardwareType(rs.getString("hardware_type"));
                whRetrieval.setHardwareId(rs.getString("hardware_id"));
                whRetrieval.setPcbA(rs.getString("pcb_a"));
                whRetrieval.setPcbAQty(rs.getString("pcb_a_qty"));
                whRetrieval.setPcbB(rs.getString("pcb_b"));
                whRetrieval.setPcbBQty(rs.getString("pcb_b_qty"));
                whRetrieval.setPcbC(rs.getString("pcb_c"));
                whRetrieval.setPcbCQty(rs.getString("pcb_c_qty"));
                whRetrieval.setPcbCtr(rs.getString("pcb_ctr"));
                whRetrieval.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRetrieval.setHardwareQty(rs.getString("hardware_qty"));
                whRetrieval.setMpNo(rs.getString("mp_no"));
                whRetrieval.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRetrieval.setLocation(rs.getString("location"));
                whRetrieval.setShelf(rs.getString("shelf"));
                whRetrieval.setRack(rs.getString("rack"));
                whRetrieval.setRequestedBy(rs.getString("requested_by"));
                whRetrieval.setRequestedDate(rs.getString("requested_date"));
                whRetrieval.setVerifiedBy(rs.getString("verified_by"));
                whRetrieval.setVerifiedDate(rs.getString("verified_date"));
                whRetrieval.setShippingBy(rs.getString("shipping_by"));
                whRetrieval.setShippingDate(rs.getString("shipping_date"));
                whRetrieval.setReceivedDate(rs.getString("received_date"));
                whRetrieval.setRemarks(rs.getString("remarks"));
                whRetrieval.setStatus(rs.getString("status"));
                whRetrieval.setFlag(rs.getString("flag"));
                whRetrieval.setTtVerification(rs.getString("tt_verification"));
                whRetrieval.setTtVerifiedBy(rs.getString("tt_verified_by"));
                whRetrieval.setTtVerifiedDate(rs.getString("tt_verified_date"));
                whRetrieval.setBarcodeVerification(rs.getString("barcode_verification"));
                whRetrieval.setBarcodeVerifiedBy(rs.getString("barcode_verified_by"));
                whRetrieval.setBarcodeVerifiedDate(rs.getString("barcode_verified_date"));
                whRetrievalList.add(whRetrieval);
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
        return whRetrievalList;
    }

    public List<WhRetrieval> getWhRetrievalListWithDateDisplay() {
        String sql = "SELECT *,"
                + "DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS view_requested_date, "
                + "DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, "
                + "DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, "
                + "DATE_FORMAT(shipping_date,'%d %M %Y %h:%i %p') AS view_shipping_date, "
                + "DATE_FORMAT(received_date,'%d %M %Y %h:%i %p') AS view_received_date, "
                + "DATE_FORMAT(tt_verified_date,'%d %M %Y %h:%i %p') AS view_tt_verified_date, "
                + "DATE_FORMAT(barcode_verified_date,'%d %M %Y %h:%i %p') AS view_tt_barcode_verified_date "
                + "FROM cdars_wh_retrieval ORDER BY id DESC";
        List<WhRetrieval> whRetrievalList = new ArrayList<WhRetrieval>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRetrieval whRetrieval;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRetrieval = new WhRetrieval();
                whRetrieval.setId(rs.getString("id"));
                whRetrieval.setRequestId(rs.getString("request_id"));
                whRetrieval.setHardwareType(rs.getString("hardware_type"));
                whRetrieval.setHardwareId(rs.getString("hardware_id"));
                whRetrieval.setPcbA(rs.getString("pcb_a"));
                whRetrieval.setPcbAQty(rs.getString("pcb_a_qty"));
                whRetrieval.setPcbB(rs.getString("pcb_b"));
                whRetrieval.setPcbBQty(rs.getString("pcb_b_qty"));
                whRetrieval.setPcbC(rs.getString("pcb_c"));
                whRetrieval.setPcbCQty(rs.getString("pcb_c_qty"));
                whRetrieval.setPcbCtr(rs.getString("pcb_ctr"));
                whRetrieval.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRetrieval.setHardwareQty(rs.getString("hardware_qty"));
                whRetrieval.setMpNo(rs.getString("mp_no"));
                whRetrieval.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRetrieval.setLocation(rs.getString("location"));
                whRetrieval.setShelf(rs.getString("shelf"));
                whRetrieval.setRack(rs.getString("rack"));
                whRetrieval.setRequestedBy(rs.getString("requested_by"));
                whRetrieval.setRequestedDate(rs.getString("requested_date"));
                whRetrieval.setVerifiedBy(rs.getString("verified_by"));
                whRetrieval.setVerifiedDate(rs.getString("verified_date"));
                whRetrieval.setShippingBy(rs.getString("shipping_by"));
                whRetrieval.setShippingDate(rs.getString("shipping_date"));
                whRetrieval.setReceivedDate(rs.getString("received_date"));
                whRetrieval.setRemarks(rs.getString("remarks"));
                whRetrieval.setStatus(rs.getString("status"));
                whRetrieval.setFlag(rs.getString("flag"));
                whRetrieval.setTtVerification(rs.getString("tt_verification"));
                whRetrieval.setTtVerifiedBy(rs.getString("tt_verified_by"));
                whRetrieval.setTtVerifiedDate(rs.getString("tt_verified_date"));
                whRetrieval.setBarcodeVerification(rs.getString("barcode_verification"));
                whRetrieval.setBarcodeVerifiedBy(rs.getString("barcode_verified_by"));
                whRetrieval.setBarcodeVerifiedDate(rs.getString("barcode_verified_date"));

                //date view
                whRetrieval.setViewRequestedDate(rs.getString("view_requested_date"));
                whRetrieval.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whRetrieval.setViewVerifiedDate(rs.getString("view_verified_date"));
                whRetrieval.setViewShippingDate(rs.getString("view_shipping_date"));
                whRetrieval.setViewReceivedDate(rs.getString("view_received_date"));
                whRetrieval.setViewTtVerifiedDate(rs.getString("view_tt_verified_date"));
                whRetrieval.setViewBarcodeVerifiedDate(rs.getString("view_tt_barcode_verified_date"));
                whRetrievalList.add(whRetrieval);
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
        return whRetrievalList;
    }

    public List<WhRetrieval> getWhRetrievalListWithDateDisplayWithoutStatusClosed() {
        String sql = "SELECT *,"
                + "DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS view_requested_date, "
                + "DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, "
                + "DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, "
                + "DATE_FORMAT(shipping_date,'%d %M %Y %h:%i %p') AS view_shipping_date, "
                + "DATE_FORMAT(received_date,'%d %M %Y %h:%i %p') AS view_received_date, "
                + "DATE_FORMAT(tt_verified_date,'%d %M %Y %h:%i %p') AS view_tt_verified_date, "
                + "DATE_FORMAT(barcode_verified_date,'%d %M %Y %h:%i %p') AS view_tt_barcode_verified_date "
                + "FROM cdars_wh_retrieval WHERE status <> 'Closed' ORDER BY id DESC";
        List<WhRetrieval> whRetrievalList = new ArrayList<WhRetrieval>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRetrieval whRetrieval;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRetrieval = new WhRetrieval();
                whRetrieval.setId(rs.getString("id"));
                whRetrieval.setRequestId(rs.getString("request_id"));
                whRetrieval.setHardwareType(rs.getString("hardware_type"));
                whRetrieval.setHardwareId(rs.getString("hardware_id"));
                whRetrieval.setPcbA(rs.getString("pcb_a"));
                whRetrieval.setPcbAQty(rs.getString("pcb_a_qty"));
                whRetrieval.setPcbB(rs.getString("pcb_b"));
                whRetrieval.setPcbBQty(rs.getString("pcb_b_qty"));
                whRetrieval.setPcbC(rs.getString("pcb_c"));
                whRetrieval.setPcbCQty(rs.getString("pcb_c_qty"));
                whRetrieval.setPcbCtr(rs.getString("pcb_ctr"));
                whRetrieval.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRetrieval.setHardwareQty(rs.getString("hardware_qty"));
                whRetrieval.setMpNo(rs.getString("mp_no"));
                whRetrieval.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRetrieval.setLocation(rs.getString("location"));
                whRetrieval.setShelf(rs.getString("shelf"));
                whRetrieval.setRack(rs.getString("rack"));
                whRetrieval.setRequestedBy(rs.getString("requested_by"));
                whRetrieval.setRequestedDate(rs.getString("requested_date"));
                whRetrieval.setVerifiedBy(rs.getString("verified_by"));
                whRetrieval.setVerifiedDate(rs.getString("verified_date"));
                whRetrieval.setShippingBy(rs.getString("shipping_by"));
                whRetrieval.setShippingDate(rs.getString("shipping_date"));
                whRetrieval.setReceivedDate(rs.getString("received_date"));
                whRetrieval.setRemarks(rs.getString("remarks"));
                whRetrieval.setStatus(rs.getString("status"));
                whRetrieval.setFlag(rs.getString("flag"));
                whRetrieval.setTtVerification(rs.getString("tt_verification"));
                whRetrieval.setTtVerifiedBy(rs.getString("tt_verified_by"));
                whRetrieval.setTtVerifiedDate(rs.getString("tt_verified_date"));
                whRetrieval.setBarcodeVerification(rs.getString("barcode_verification"));
                whRetrieval.setBarcodeVerifiedBy(rs.getString("barcode_verified_by"));
                whRetrieval.setBarcodeVerifiedDate(rs.getString("barcode_verified_date"));

                //date view
                whRetrieval.setViewRequestedDate(rs.getString("view_requested_date"));
                whRetrieval.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whRetrieval.setViewVerifiedDate(rs.getString("view_verified_date"));
                whRetrieval.setViewShippingDate(rs.getString("view_shipping_date"));
                whRetrieval.setViewReceivedDate(rs.getString("view_received_date"));
                whRetrieval.setViewTtVerifiedDate(rs.getString("view_tt_verified_date"));
                whRetrieval.setViewBarcodeVerifiedDate(rs.getString("view_tt_barcode_verified_date"));
                whRetrievalList.add(whRetrieval);
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
        return whRetrievalList;
    }

    public Integer getCountRequestIdAndMpNo(String requestId, String mpNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_wh_retrieval WHERE request_id = '" + requestId + "' AND mp_no = '" + mpNo + "' "
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

    public String getId(String requestId, String mpNo) {
        String id = "";
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id AS id FROM cdars_wh_retrieval WHERE request_id = '" + requestId + "' AND mp_no = '" + mpNo + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getString("id");
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
        return id;
    }
}
