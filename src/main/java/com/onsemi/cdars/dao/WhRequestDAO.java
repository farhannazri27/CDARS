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
                    "INSERT INTO cdars_wh_request (request_type, equipment_type, equipment_id, quantity, type, requested_by, requested_date, remarks, created_by, created_date, status, flag) VALUES (?,?,?,?,?,?,NOW(),?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRequest.getRequestType());
            ps.setString(2, whRequest.getEquipmentType());
            ps.setString(3, whRequest.getEquipmentId());
            ps.setString(4, whRequest.getQuantity());
            ps.setString(5, whRequest.getType());
            ps.setString(6, whRequest.getRequestedBy());
            ps.setString(7, whRequest.getRemarks());
            ps.setString(8, whRequest.getCreatedBy());
            ps.setString(9, whRequest.getStatus());
            ps.setString(10, whRequest.getFlag());
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
                    "UPDATE cdars_wh_request SET request_type = ?, equipment_type = ?, equipment_id = ?, quantity = ?, type = ?, remarks = ?, modified_by = ?, modified_date = NOW(), flag = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getRequestType());
            ps.setString(2, whRequest.getEquipmentType());
            ps.setString(3, whRequest.getEquipmentId());
            ps.setString(4, whRequest.getQuantity());
            ps.setString(5, whRequest.getType());
            ps.setString(6, whRequest.getRemarks());
            ps.setString(7, whRequest.getModifiedBy());
            ps.setString(8, whRequest.getFlag());
            ps.setString(9, whRequest.getId());
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
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y') AS requested_date_view FROM cdars_wh_request WHERE id = '" + whRequestId + "'";
        WhRequest whRequest = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setType(rs.getString("type"));
                whRequest.setQuantity(rs.getString("quantity"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
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
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y') AS requested_date_view FROM cdars_wh_request ORDER BY id DESC";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setType(rs.getString("type"));
                whRequest.setQuantity(rs.getString("quantity"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
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
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y') AS requested_date_view FROM cdars_wh_request WHERE status = \"Approved\" ORDER BY id DESC";
        List<WhRequest> whRequestList = new ArrayList<WhRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhRequest whRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
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
}
