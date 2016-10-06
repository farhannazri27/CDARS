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
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhRequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhRequestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public WhRequestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertWhRequest(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_request (inventory_id, request_type, equipment_type, pcb_type, equipment_id, "
                    + "pcb_a, pcb_a_qty, pcb_b, pcb_b_qty, pcb_c, pcb_c_qty, pcb_ctr, pcb_ctr_qty, mp_no, mp_expiry_date, "
                    + "quantity, rack, shelf, requested_by, requestor_email, requested_date, remarks, created_by, "
                    + "created_date, status, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRequest.getInventoryId());
            ps.setString(2, whRequest.getRequestType());
            ps.setString(3, whRequest.getEquipmentType());
            ps.setString(4, whRequest.getPcbType());
            ps.setString(5, whRequest.getEquipmentId());
            ps.setString(6, whRequest.getPcbA());
            ps.setString(7, whRequest.getPcbAQty());
            ps.setString(8, whRequest.getPcbB());
            ps.setString(9, whRequest.getPcbBQty());
            ps.setString(10, whRequest.getPcbC());
            ps.setString(11, whRequest.getPcbCQty());
            ps.setString(12, whRequest.getPcbCtr());
            ps.setString(13, whRequest.getPcbCtrQty());
            ps.setString(14, whRequest.getMpNo());
            ps.setString(15, whRequest.getMpExpiryDate());
            ps.setString(16, whRequest.getQuantity());
            ps.setString(17, whRequest.getRack());
            ps.setString(18, whRequest.getShelf());
            ps.setString(19, whRequest.getRequestedBy());
            ps.setString(20, whRequest.getRequestorEmail());
            ps.setString(21, whRequest.getRemarks());
            ps.setString(22, whRequest.getCreatedBy());
            ps.setString(23, whRequest.getStatus());
            ps.setString(24, whRequest.getFlag());
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

    public QueryResult updateWhRequest(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET inventory_id = ?, request_type = ?, equipment_type = ?, pcb_type = ?, equipment_id = ?, "
                    + "pcb_a = ?, pcb_a_qty = ?, pcb_b = ?, pcb_b_qty = ?, pcb_c = ?, pcb_c_qty = ?, pcb_ctr = ?, pcb_ctr_qty = ?, mp_no = ?, "
                    + "mp_expiry_date = ?, quantity = ?, rack = ?, shelf = ?, remarks = ?, modified_by = ?, modified_date = NOW(), flag = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getInventoryId());
            ps.setString(2, whRequest.getRequestType());
            ps.setString(3, whRequest.getEquipmentType());
            ps.setString(4, whRequest.getPcbType());
            ps.setString(5, whRequest.getEquipmentId());
            ps.setString(6, whRequest.getPcbA());
            ps.setString(7, whRequest.getPcbAQty());
            ps.setString(8, whRequest.getPcbB());
            ps.setString(9, whRequest.getPcbBQty());
            ps.setString(10, whRequest.getPcbC());
            ps.setString(11, whRequest.getPcbCQty());
            ps.setString(12, whRequest.getPcbCtr());
            ps.setString(13, whRequest.getPcbCtrQty());
            ps.setString(14, whRequest.getMpNo());
            ps.setString(15, whRequest.getMpExpiryDate());
            ps.setString(16, whRequest.getQuantity());
            ps.setString(17, whRequest.getRack());
            ps.setString(18, whRequest.getShelf());
            ps.setString(19, whRequest.getRemarks());
            ps.setString(20, whRequest.getModifiedBy());
            ps.setString(21, whRequest.getFlag());
            ps.setString(22, whRequest.getId());
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

    public QueryResult deleteWhRequest(String whRequestId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_wh_request WHERE id = '" + whRequestId + "'"
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

    public WhRequest getWhRequest(String whRequestId) {
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view FROM cdars_wh_request WHERE id = '" + whRequestId + "'";
        WhRequest whRequest = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setInventoryId(rs.getString("inventory_id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setPcbType(rs.getString("pcb_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setPcbA(rs.getString("pcb_a"));
                whRequest.setPcbAQty(rs.getString("pcb_a_qty"));
                whRequest.setPcbB(rs.getString("pcb_b"));
                whRequest.setPcbBQty(rs.getString("pcb_b_qty"));
                whRequest.setPcbC(rs.getString("pcb_c"));
                whRequest.setPcbCQty(rs.getString("pcb_c_qty"));
                whRequest.setPcbCtr(rs.getString("pcb_ctr"));
                whRequest.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRequest.setMpNo(rs.getString("mp_no"));
                whRequest.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRequest.setRack(rs.getString("rack"));
                whRequest.setShelf(rs.getString("shelf"));
                whRequest.setQuantity(rs.getString("quantity"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
                whRequest.setRequestorEmail(rs.getString("requestor_email"));
                whRequest.setRequestedDate(rs.getString("requested_date"));
                whRequest.setRequestedDateView(rs.getString("requested_date_view"));
                whRequest.setFinalApprovedStatus(rs.getString("final_approved_status"));
                whRequest.setFinalApprovedBy(rs.getString("final_approved_by"));
                whRequest.setFinalApprovedDate(rs.getString("final_approved_date"));
                whRequest.setRemarks(rs.getString("remarks"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
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
        return whRequest;
    }

    public List<WhRequest> getWhRequestList() {
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view FROM cdars_wh_request ORDER BY id DESC";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setInventoryId(rs.getString("inventory_id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setPcbType(rs.getString("pcb_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setPcbA(rs.getString("pcb_a"));
                whRequest.setPcbAQty(rs.getString("pcb_a_qty"));
                whRequest.setPcbB(rs.getString("pcb_b"));
                whRequest.setPcbBQty(rs.getString("pcb_b_qty"));
                whRequest.setPcbC(rs.getString("pcb_c"));
                whRequest.setPcbCQty(rs.getString("pcb_c_qty"));
                whRequest.setPcbCtr(rs.getString("pcb_ctr"));
                whRequest.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRequest.setMpNo(rs.getString("mp_no"));
                whRequest.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRequest.setRack(rs.getString("rack"));
                whRequest.setShelf(rs.getString("shelf"));
                whRequest.setQuantity(rs.getString("quantity"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
                whRequest.setRequestorEmail(rs.getString("requestor_email"));
                whRequest.setRequestedDate(rs.getString("requested_date"));
                whRequest.setRequestedDateView(rs.getString("requested_date_view"));
                whRequest.setFinalApprovedStatus(rs.getString("final_approved_status"));
                whRequest.setFinalApprovedBy(rs.getString("final_approved_by"));
                whRequest.setFinalApprovedDate(rs.getString("final_approved_date"));
                whRequest.setRemarks(rs.getString("remarks"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequestList.add(whRequest);
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
        return whRequestList;
    }

    public List<WhRequest> getWhRequestListWithoutRetrievalAndStatusApproved() {
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view FROM cdars_wh_request WHERE request_type = 'Ship' AND status <> 'Approved' ORDER BY id DESC";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setInventoryId(rs.getString("inventory_id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setPcbType(rs.getString("pcb_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setPcbA(rs.getString("pcb_a"));
                whRequest.setPcbAQty(rs.getString("pcb_a_qty"));
                whRequest.setPcbB(rs.getString("pcb_b"));
                whRequest.setPcbBQty(rs.getString("pcb_b_qty"));
                whRequest.setPcbC(rs.getString("pcb_c"));
                whRequest.setPcbCQty(rs.getString("pcb_c_qty"));
                whRequest.setPcbCtr(rs.getString("pcb_ctr"));
                whRequest.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRequest.setMpNo(rs.getString("mp_no"));
                whRequest.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRequest.setRack(rs.getString("rack"));
                whRequest.setShelf(rs.getString("shelf"));
                whRequest.setQuantity(rs.getString("quantity"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
                whRequest.setRequestorEmail(rs.getString("requestor_email"));
                whRequest.setRequestedDate(rs.getString("requested_date"));
                whRequest.setRequestedDateView(rs.getString("requested_date_view"));
                whRequest.setFinalApprovedStatus(rs.getString("final_approved_status"));
                whRequest.setFinalApprovedBy(rs.getString("final_approved_by"));
                whRequest.setFinalApprovedDate(rs.getString("final_approved_date"));
                whRequest.setRemarks(rs.getString("remarks"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequestList.add(whRequest);
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
        return whRequestList;
    }

    public List<WhRequest> getWhRequestListForShipping() {
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view FROM cdars_wh_request WHERE status = \"Approved\" ORDER BY id DESC";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setInventoryId(rs.getString("inventory_id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setPcbType(rs.getString("pcb_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setPcbA(rs.getString("pcb_a"));
                whRequest.setPcbAQty(rs.getString("pcb_a_qty"));
                whRequest.setPcbB(rs.getString("pcb_b"));
                whRequest.setPcbBQty(rs.getString("pcb_b_qty"));
                whRequest.setPcbC(rs.getString("pcb_c"));
                whRequest.setPcbCQty(rs.getString("pcb_c_qty"));
                whRequest.setPcbCtr(rs.getString("pcb_ctr"));
                whRequest.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRequest.setMpNo(rs.getString("mp_no"));
                whRequest.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRequest.setRack(rs.getString("rack"));
                whRequest.setShelf(rs.getString("shelf"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
                whRequest.setRequestorEmail(rs.getString("requestor_email"));
                whRequest.setRequestedDate(rs.getString("requested_date"));
                whRequest.setRequestedDateView(rs.getString("requested_date_view"));
                whRequest.setFinalApprovedBy(rs.getString("final_approved_by"));
                whRequest.setFinalApprovedDate(rs.getString("final_approved_date"));
                whRequest.setRemarks(rs.getString("remarks"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequestList.add(whRequest);
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
        return whRequestList;
    }

    public QueryResult updateWhRequestForApproval(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET final_approved_status = ?, final_approved_by = ?, final_approved_date = NOW(), remarks_approver = ?, status = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getFinalApprovedStatus());
            ps.setString(2, whRequest.getFinalApprovedBy());
            ps.setString(3, whRequest.getRemarksApprover());
            ps.setString(4, whRequest.getStatus());
            ps.setString(5, whRequest.getId());
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

    public Integer getCountNowDateMoreDateRequested3Days() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE status = 'Waiting for Approval' AND NOW() > ADDDATE(DATE(requested_date),3)"
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

    public QueryResult updateWhRequestFlag1(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET flag = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getFlag());
            ps.setString(2, whRequest.getId());
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

    public Integer getCountFlag0ForShip(String equipmentId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE request_type = 'Ship' AND equipment_id = '" + equipmentId + "' AND flag = '0' "
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
    
    public Integer getCountFlag0ForRetrieve(String equipmentId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE request_type = 'Retrieve' AND equipment_id = '" + equipmentId + "' AND flag = '0' "
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
