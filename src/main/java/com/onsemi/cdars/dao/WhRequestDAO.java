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
                    + "quantity, rack, shelf, requested_by, requestor_email, requested_date, remarks, remarks_log, created_by, "
                    + "created_date, status, flag, retrieval_reason, sfpkid, sfpkidB, sfpkidC, sfpkidCtr, load_card_id, load_card_qty, program_card_id, program_card_qty,sfpkidLc,sfpkidPc) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
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
            ps.setString(22, whRequest.getRemarksLog());
            ps.setString(23, whRequest.getCreatedBy());
            ps.setString(24, whRequest.getStatus());
            ps.setString(25, whRequest.getFlag());
            ps.setString(26, whRequest.getRetrievalReason());
            ps.setString(27, whRequest.getSfpkid());
            ps.setString(28, whRequest.getSfpkidB());
            ps.setString(29, whRequest.getSfpkidC());
            ps.setString(30, whRequest.getSfpkidCtr());
            ps.setString(31, whRequest.getLoadCard());
            ps.setString(32, whRequest.getLoadCardQty());
            ps.setString(33, whRequest.getProgramCard());
            ps.setString(34, whRequest.getProgramCardQty());
            ps.setString(35, whRequest.getSfpkidLc());
            ps.setString(36, whRequest.getSfpkidPc());
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

    public QueryResult insertWhRequestForEqptCsv(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_request (request_type, equipment_type, equipment_id, "
                    + "mp_no, mp_expiry_date,mp_created_date, "
                    + "quantity, rack, shelf, requested_by, requestor_email, requested_date, remarks, remarks_log, created_by, "
                    + "created_date, status, flag, pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, "
                    + "sfpkidB, sfpkidC, sfpkidCtr, load_card_qty, program_card_qty,sfpkidLc,sfpkidPc) "
                    + "VALUES (?,?,?,?,?,NOW(),?,?,?,?,?,NOW(),?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRequest.getRequestType());
            ps.setString(2, whRequest.getEquipmentType());
            ps.setString(3, whRequest.getEquipmentId());
            ps.setString(4, whRequest.getMpNo());
            ps.setString(5, whRequest.getMpExpiryDate());
            ps.setString(6, whRequest.getQuantity());
            ps.setString(7, whRequest.getRack());
            ps.setString(8, whRequest.getShelf());
            ps.setString(9, whRequest.getRequestedBy());
            ps.setString(10, whRequest.getRequestorEmail());
            ps.setString(11, whRequest.getRemarks());
            ps.setString(12, whRequest.getRemarksLog());
            ps.setString(13, whRequest.getCreatedBy());
            ps.setString(14, whRequest.getStatus());
            ps.setString(15, whRequest.getFlag());
            ps.setString(16, whRequest.getPcbAQty());
            ps.setString(17, whRequest.getPcbBQty());
            ps.setString(18, whRequest.getPcbCQty());
            ps.setString(19, whRequest.getPcbCtrQty());
            ps.setString(20, whRequest.getSfpkidB());
            ps.setString(21, whRequest.getSfpkidC());
            ps.setString(22, whRequest.getSfpkidCtr());
            ps.setString(23, whRequest.getLoadCardQty());
            ps.setString(24, whRequest.getProgramCardQty());
            ps.setString(25, whRequest.getSfpkidLc());
            ps.setString(26, whRequest.getSfpkidPc());
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

    public QueryResult insertWhRequestForLcCsv(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_request (request_type, equipment_type, equipment_id, "
                    + "mp_no, mp_expiry_date,mp_created_date, "
                    + "quantity, rack, shelf, requested_by, requestor_email, requested_date, remarks, remarks_log, created_by, "
                    + "created_date, status, flag, pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, "
                    + "sfpkidB, sfpkidC, sfpkidCtr, load_card_qty, program_card_qty,sfpkidLc,sfpkidPc, load_card_id,sfpkid) "
                    + "VALUES (?,?,?,?,?,NOW(),?,?,?,?,?,NOW(),?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRequest.getRequestType());
            ps.setString(2, whRequest.getEquipmentType());
            ps.setString(3, whRequest.getEquipmentId());
            ps.setString(4, whRequest.getMpNo());
            ps.setString(5, whRequest.getMpExpiryDate());
            ps.setString(6, whRequest.getQuantity());
            ps.setString(7, whRequest.getRack());
            ps.setString(8, whRequest.getShelf());
            ps.setString(9, whRequest.getRequestedBy());
            ps.setString(10, whRequest.getRequestorEmail());
            ps.setString(11, whRequest.getRemarks());
            ps.setString(12, whRequest.getRemarksLog());
            ps.setString(13, whRequest.getCreatedBy());
            ps.setString(14, whRequest.getStatus());
            ps.setString(15, whRequest.getFlag());
            ps.setString(16, whRequest.getPcbAQty());
            ps.setString(17, whRequest.getPcbBQty());
            ps.setString(18, whRequest.getPcbCQty());
            ps.setString(19, whRequest.getPcbCtrQty());
            ps.setString(20, whRequest.getSfpkidB());
            ps.setString(21, whRequest.getSfpkidC());
            ps.setString(22, whRequest.getSfpkidCtr());
            ps.setString(23, whRequest.getLoadCardQty());
            ps.setString(24, whRequest.getProgramCardQty());
            ps.setString(25, whRequest.getSfpkidLc());
            ps.setString(26, whRequest.getSfpkidPc());
            ps.setString(27, whRequest.getLoadCard());
            ps.setString(28, whRequest.getSfpkid());
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

    public QueryResult insertWhRequestForPcCsv(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_request (request_type, equipment_type, equipment_id, "
                    + "mp_no, mp_expiry_date,mp_created_date, "
                    + "quantity, rack, shelf, requested_by, requestor_email, requested_date, remarks, remarks_log, created_by, "
                    + "created_date, status, flag, pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, "
                    + "sfpkidB, sfpkidC, sfpkidCtr, load_card_qty, program_card_qty,sfpkidLc,sfpkidPc, program_card_id,sfpkid) "
                    + "VALUES (?,?,?,?,?,NOW(),?,?,?,?,?,NOW(),?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRequest.getRequestType());
            ps.setString(2, whRequest.getEquipmentType());
            ps.setString(3, whRequest.getEquipmentId());
            ps.setString(4, whRequest.getMpNo());
            ps.setString(5, whRequest.getMpExpiryDate());
            ps.setString(6, whRequest.getQuantity());
            ps.setString(7, whRequest.getRack());
            ps.setString(8, whRequest.getShelf());
            ps.setString(9, whRequest.getRequestedBy());
            ps.setString(10, whRequest.getRequestorEmail());
            ps.setString(11, whRequest.getRemarks());
            ps.setString(12, whRequest.getRemarksLog());
            ps.setString(13, whRequest.getCreatedBy());
            ps.setString(14, whRequest.getStatus());
            ps.setString(15, whRequest.getFlag());
            ps.setString(16, whRequest.getPcbAQty());
            ps.setString(17, whRequest.getPcbBQty());
            ps.setString(18, whRequest.getPcbCQty());
            ps.setString(19, whRequest.getPcbCtrQty());
            ps.setString(20, whRequest.getSfpkidB());
            ps.setString(21, whRequest.getSfpkidC());
            ps.setString(22, whRequest.getSfpkidCtr());
            ps.setString(23, whRequest.getLoadCardQty());
            ps.setString(24, whRequest.getProgramCardQty());
            ps.setString(25, whRequest.getSfpkidLc());
            ps.setString(26, whRequest.getSfpkidPc());
            ps.setString(27, whRequest.getProgramCard());
            ps.setString(28, whRequest.getSfpkid());
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

    public QueryResult insertWhRequestForLcPcCsv(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_request (request_type, equipment_type, equipment_id, "
                    + "mp_no, mp_expiry_date,mp_created_date, "
                    + "quantity, rack, shelf, requested_by, requestor_email, requested_date, remarks, remarks_log, created_by, "
                    + "created_date, status, flag, pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, "
                    + "sfpkidB, sfpkidC, sfpkidCtr, load_card_qty, program_card_qty,sfpkidLc,sfpkidPc, load_card_id, program_card_id,sfpkid) "
                    + "VALUES (?,?,?,?,?,NOW(),?,?,?,?,?,NOW(),?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRequest.getRequestType());
            ps.setString(2, whRequest.getEquipmentType());
            ps.setString(3, whRequest.getEquipmentId());
            ps.setString(4, whRequest.getMpNo());
            ps.setString(5, whRequest.getMpExpiryDate());
            ps.setString(6, whRequest.getQuantity());
            ps.setString(7, whRequest.getRack());
            ps.setString(8, whRequest.getShelf());
            ps.setString(9, whRequest.getRequestedBy());
            ps.setString(10, whRequest.getRequestorEmail());
            ps.setString(11, whRequest.getRemarks());
            ps.setString(12, whRequest.getRemarksLog());
            ps.setString(13, whRequest.getCreatedBy());
            ps.setString(14, whRequest.getStatus());
            ps.setString(15, whRequest.getFlag());
            ps.setString(16, whRequest.getPcbAQty());
            ps.setString(17, whRequest.getPcbBQty());
            ps.setString(18, whRequest.getPcbCQty());
            ps.setString(19, whRequest.getPcbCtrQty());
            ps.setString(20, whRequest.getSfpkidB());
            ps.setString(21, whRequest.getSfpkidC());
            ps.setString(22, whRequest.getSfpkidCtr());
            ps.setString(23, whRequest.getLoadCardQty());
            ps.setString(24, whRequest.getProgramCardQty());
            ps.setString(25, whRequest.getSfpkidLc());
            ps.setString(26, whRequest.getSfpkidPc());
            ps.setString(27, whRequest.getLoadCard());
            ps.setString(28, whRequest.getProgramCard());
            ps.setString(29, whRequest.getSfpkid());
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
                    + "mp_expiry_date = ?, quantity = ?, rack = ?, shelf = ?, remarks = ?, remarks_log = ?,  modified_by = ?, modified_date = NOW(), "
                    + "flag = ?, retrieval_reason = ?, load_card_id = ?, load_card_qty = ?, program_card_id = ?, program_card_qty = ? "
                    + "WHERE id = ?"
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
            ps.setString(20, whRequest.getRemarksLog());
            ps.setString(21, whRequest.getModifiedBy());
            ps.setString(22, whRequest.getFlag());
            ps.setString(23, whRequest.getRetrievalReason());
            ps.setString(24, whRequest.getLoadCard());
            ps.setString(25, whRequest.getLoadCardQty());
            ps.setString(26, whRequest.getProgramCard());
            ps.setString(27, whRequest.getProgramCardQty());
            ps.setString(28, whRequest.getId());
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
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS mp_expiry_date_view FROM cdars_wh_request WHERE id = '" + whRequestId + "'";
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
                whRequest.setMpExpiryDateView(rs.getString("mp_expiry_date_view"));
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
                whRequest.setRemarksLog(rs.getString("remarks_log"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
                whRequest.setSfpkid(rs.getString("sfpkid"));
                whRequest.setSfpkidB(rs.getString("sfpkidB"));
                whRequest.setSfpkidC(rs.getString("sfpkidC"));
                whRequest.setSfpkidCtr(rs.getString("sfpkidCtr"));
                //phase 2
                whRequest.setLoadCard(rs.getString("load_card_id"));
                whRequest.setLoadCardQty(rs.getString("load_card_qty"));
                whRequest.setProgramCard(rs.getString("program_card_id"));
                whRequest.setProgramCardQty(rs.getString("program_card_qty"));
                whRequest.setSfpkidLc(rs.getString("sfpkidLc"));
                whRequest.setSfpkidPc(rs.getString("sfpkidPc"));
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
                whRequest.setRemarksLog(rs.getString("remarks_log"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
                whRequest.setSfpkid(rs.getString("sfpkid"));
                whRequest.setSfpkidB(rs.getString("sfpkidB"));
                whRequest.setSfpkidC(rs.getString("sfpkidC"));
                whRequest.setSfpkidCtr(rs.getString("sfpkidCtr"));
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
//        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view FROM cdars_wh_request WHERE request_type = 'Ship' "
//                + "AND (status = 'Waiting for Approval' or status = 'Not Approved') "
//                + "ORDER BY id DESC"; //original 3/11/16
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view FROM cdars_wh_request WHERE request_type = 'Ship' "
                + "AND (status = 'Pending Approval' or status = 'Not Approved') " //as requested 2/11/16
                + "ORDER BY id DESC";
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
                whRequest.setRemarksLog(rs.getString("remarks_log"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
                whRequest.setSfpkid(rs.getString("sfpkid"));
                whRequest.setSfpkidB(rs.getString("sfpkidB"));
                whRequest.setSfpkidC(rs.getString("sfpkidC"));
                whRequest.setSfpkidCtr(rs.getString("sfpkidCtr"));
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

    public List<WhRequest> getWhRequestListWithoutRetrievalAndStatusApprovedBasedMasterGroupId(String eqptType) {
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view FROM cdars_wh_request WHERE request_type = 'Ship' "
                + "AND (status = 'Pending Approval' or status = 'Not Approved') "
                + "AND equipment_type IN (" + eqptType + ") "
                + "ORDER BY id DESC";
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
                whRequest.setRemarksLog(rs.getString("remarks_log"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
                whRequest.setSfpkid(rs.getString("sfpkid"));
                whRequest.setSfpkidB(rs.getString("sfpkidB"));
                whRequest.setSfpkidC(rs.getString("sfpkidC"));
                whRequest.setSfpkidCtr(rs.getString("sfpkidCtr"));
                whRequest.setLoadCard(rs.getString("load_card_id"));
                whRequest.setLoadCardQty(rs.getString("load_card_qty"));
                whRequest.setProgramCard(rs.getString("program_card_id"));
                whRequest.setProgramCardQty(rs.getString("program_card_qty"));
                whRequest.setSfpkidLc(rs.getString("sfpkidLc"));
                whRequest.setSfpkidPc(rs.getString("sfpkidPc"));
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
                whRequest.setRemarksLog(rs.getString("remarks_log"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
                whRequest.setSfpkid(rs.getString("sfpkid"));
                whRequest.setSfpkidB(rs.getString("sfpkidB"));
                whRequest.setSfpkidC(rs.getString("sfpkidC"));
                whRequest.setSfpkidCtr(rs.getString("sfpkidCtr"));
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
                    "UPDATE cdars_wh_request SET final_approved_status = ?, final_approved_by = ?, final_approved_date = NOW(), remarks_approver = ?, status = ? ,flag = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getFinalApprovedStatus());
            ps.setString(2, whRequest.getFinalApprovedBy());
            ps.setString(3, whRequest.getRemarksApprover());
            ps.setString(4, whRequest.getStatus());
            ps.setString(5, whRequest.getFlag());
            ps.setString(6, whRequest.getId());
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
                    //                    "SELECT count(*) AS count FROM cdars_wh_request WHERE status = 'Waiting for Approval' AND NOW() > ADDDATE(DATE(requested_date),3)" //original 3/11/16
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE status = 'Pending Approval' AND NOW() > ADDDATE(DATE(requested_date),3)" //as requested 2/11/16
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

    public QueryResult updateWhRequestStatus(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getId());
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

    public QueryResult updateWhRequestStatusWithSfpkidAll(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkid = ?, sfpkidB = ?, sfpkidC = ?, sfpkidCtr = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkid());
            ps.setString(4, whRequest.getSfpkidB());
            ps.setString(5, whRequest.getSfpkidC());
            ps.setString(6, whRequest.getSfpkidCtr());
            ps.setString(7, whRequest.getId());
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

    public QueryResult updateWhRequestStatusWithSfpkid(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkid = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkid());
            ps.setString(4, whRequest.getId());
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

    public QueryResult updateWhRequestStatusWithSfpkidLc(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkidLc = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkidLc());
            ps.setString(4, whRequest.getId());
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

    public QueryResult updateWhRequestStatusWithSfpkidPc(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkidPc = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkidPc());
            ps.setString(4, whRequest.getId());
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

    public QueryResult updateWhRequestStatusWithSfpkidLcAndSpkidPc(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkidLc = ?, sfpkidPc = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkidLc());
            ps.setString(4, whRequest.getSfpkidPc());
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

    public QueryResult updateWhRequestStatusWithSfpkidB(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkidB = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkidB());
            ps.setString(4, whRequest.getId());
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

    public QueryResult updateWhRequestStatusWithSfpkidC(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkidC = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkidC());
            ps.setString(4, whRequest.getId());
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

    public QueryResult updateWhRequestStatusWithSfpkidCtr(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ?, sfpkidCtr = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getSfpkidCtr());
            ps.setString(4, whRequest.getId());
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

    public QueryResult updateWhRequestStatusAndMpNo(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET mp_no = ?, mp_expiry_date = ?, modified_by = ?, modified_date = NOW(), status = ?, mp_created_date = NOW() WHERE id = ?"
            );
            ps.setString(1, whRequest.getMpNo());
            ps.setString(2, whRequest.getMpExpiryDate());
            ps.setString(3, whRequest.getModifiedBy());
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

    public QueryResult updateWhRequestMpNoMpExpiryDate(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET mp_no = ?, mp_expiry_date = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getMpNo());
            ps.setString(2, whRequest.getMpExpiryDate());
            ps.setString(3, whRequest.getId());
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

    public QueryResult updateWhRequestSfpkid(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET sfpkid = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getSfpkid());
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

    public QueryResult updateWhRequestSfpkidLc(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET sfpkidLc = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getSfpkidLc());
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

    public QueryResult updateWhRequestSfpkidPc(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET sfpkidPc = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getSfpkidPc());
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

    public QueryResult updateWhRequestSfpkidLcAndPc(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET sfpkidLc = ?,sfpkidPc = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getSfpkidLc());
            ps.setString(2, whRequest.getSfpkidPc());
            ps.setString(3, whRequest.getId());
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

    public Integer getCountRequestId(String requestId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE id = '" + requestId + "'"
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

    public Integer getCountRequestEqpTypeAndEqpIdandMpNo(String eqptType, String eqptId, String mpNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE equipment_type = '" + eqptType + "'"
                    + " AND equipment_id = '" + eqptId + "' AND mp_no = '" + mpNo + "'"
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

    public Integer getCountRequestEqpTypeAndLcAndPcandMpNo(String eqptType, String lcId, String pcId, String mpNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE equipment_type = '" + eqptType + "'"
                    + " AND load_card_id = '" + lcId + "' AND program_card_id = '" + pcId + "' AND mp_no = '" + mpNo + "'"
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

    public Integer getCountRequestEqpTypeAndLcandMpNo(String eqptType, String lcId, String mpNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE equipment_type = '" + eqptType + "'"
                    + " AND load_card_id = '" + lcId + "' AND mp_no = '" + mpNo + "'"
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

    public Integer getCountRequestEqpTypeAndPcandMpNo(String eqptType, String pcId, String mpNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE equipment_type = '" + eqptType + "'"
                    + " AND program_card_id = '" + pcId + "' AND mp_no = '" + mpNo + "'"
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

    public Integer getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(String equipmentId, String mpNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE request_type = 'Retrieve' AND equipment_id = '" + equipmentId + "' AND mp_no = '" + mpNo + "' AND status <> 'Cancelled'"
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

    public Integer getCountWaitingFlag0() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    //                    "SELECT count(*) AS count FROM cdars_wh_request WHERE status = 'Waiting for Approval' AND flag = '0'" //original 3/11/16
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE status = 'Pending Approval' AND flag = '0'" //as requested 2/11/16
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

    public Integer getCountApprovedFlag0() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE status = 'Approved' AND flag = '0'"
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

    public Integer getCountNotApprovedFlag0() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE status = 'Not Approved' AND flag = '0'"
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

    public List<WhRequest> getQuery(String query) {
//        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view ,DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS mp_expiry_date_view "
//                + "FROM cdars_wh_request "
//                + "WHERE " + query + " AND status <> 'Requested for Retrieval' ORDER BY id DESC";
        String sql = "SELECT re.*,DATE_FORMAT(re.requested_date,'%d %M %Y %h:%i %p') AS requested_date_view ,DATE_FORMAT(re.mp_expiry_date,'%d %M %Y') AS mp_expiry_date_view, "
                + "DATE_FORMAT(sh.shipping_date,'%d %M %Y %h:%i %p') AS shipping_date, DATE_FORMAT(ret.shipping_date,'%d %M %Y %h:%i %p') AS received_date "
                + "FROM cdars_wh_request re LEFT JOIN cdars_wh_shipping sh ON sh.request_id = re.id "
                + "LEFT JOIN cdars_wh_retrieval ret ON ret.request_id = re.id "
                + "WHERE " + query + " ORDER BY re.id DESC";
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
                whRequest.setMpExpiryDateView(rs.getString("mp_expiry_date_view"));
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
                whRequest.setRemarksLog(rs.getString("remarks_log"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
                whRequest.setShippingDate(rs.getString("shipping_date"));
                whRequest.setReceivedDate(rs.getString("received_date"));
                whRequest.setLoadCard(rs.getString("load_card_id"));
                whRequest.setLoadCardQty(rs.getString("load_card_qty"));
                whRequest.setProgramCard(rs.getString("program_card_id"));
                whRequest.setProgramCardQty(rs.getString("program_card_qty"));
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

    public List<WhRequest> getWhRequestListStatus() {
//        String sql = "SELECT status,id FROM cdars_wh_request WHERE status <> 'Requested for Retrieval' GROUP BY status ORDER BY status";
        String sql = "SELECT status,id FROM cdars_wh_request GROUP BY status ORDER BY status";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setStatus(rs.getString("status"));
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

    public List<WhRequest> getWhRequestListRequestedBy() {
        String sql = "SELECT requested_by,id FROM cdars_wh_request GROUP BY requested_by ORDER BY requested_by";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
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

    public List<WhRequest> getWhRequestListEquipmentId() {
//        String sql = "SELECT equipment_id,id FROM cdars_wh_request GROUP BY equipment_id ORDER BY equipment_id";
        String sql = "SELECT equipment_id,"
                + "id,"
                + "IF(equipment_id LIKE '%SINGLE%' OR equipment_id LIKE '%PAIR%',IF(load_card_id is null,program_card_id,load_card_id),equipment_id) as equipment_id2 "
                + "FROM cdars_wh_request "
                + "GROUP BY equipment_id2 "
                + "ORDER BY equipment_id2";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setEquipmentId2(rs.getString("equipment_id2"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
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

    public List<WhRequest> getWhRequestListRetrievalReason() {
        String sql = "SELECT retrieval_reason,id FROM cdars_wh_request GROUP BY retrieval_reason ORDER BY retrieval_reason";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
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

}
