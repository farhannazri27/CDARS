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
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhInventoryDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhInventoryDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public WhInventoryDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertWhInventory(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_inventory (request_id, mp_no, mp_expiry_date, equipment_type, equipment_id, "
                    + "pcb_a, pcb_a_qty, pcb_b, pcb_b_qty, pcb_c, pcb_c_qty, pcb_ctr, pcb_ctr_qty, "
                    + "quantity, requested_by, requested_date, remarks, verified_date, inventory_date, inventory_rack, inventory_shelf,"
                    + " inventory_by, status, received_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getMpNo());
            ps.setString(3, whInventory.getMpExpiryDate());
            ps.setString(4, whInventory.getEquipmentType());
            ps.setString(5, whInventory.getEquipmentId());
            ps.setString(6, whInventory.getPcbA());
            ps.setString(7, whInventory.getPcbAQty());
            ps.setString(8, whInventory.getPcbB());
            ps.setString(9, whInventory.getPcbBQty());
            ps.setString(10, whInventory.getPcbC());
            ps.setString(11, whInventory.getPcbCQty());
            ps.setString(12, whInventory.getPcbCtr());
            ps.setString(13, whInventory.getPcbCtrQty());
            ps.setString(14, whInventory.getQuantity());
            ps.setString(15, whInventory.getRequestedBy());
            ps.setString(16, whInventory.getRequestedDate());
            ps.setString(17, whInventory.getRemarks());
            ps.setString(18, whInventory.getVerifiedDate());
            ps.setString(19, whInventory.getInventoryDate());
            ps.setString(20, whInventory.getInventoryRack());
            ps.setString(21, whInventory.getInventoryShelf());
            ps.setString(22, whInventory.getInventoryBy());
            ps.setString(23, whInventory.getStatus());
            ps.setString(24, whInventory.getFlag());
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

    public QueryResult updateWhInventory(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_inventory SET request_id = ?, mp_no = ?, mp_expiry_date= ?, equipment_type = ?, equipment_id = ?, quantity = ?, requested_by = ?, requested_date = ?, remarks = ?, verified_date = ?, inventory_date = ?, inventory_location = ?, inventory_by = ?, status = ?, received_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getMpNo());
            ps.setString(3, whInventory.getMpExpiryDate());
            ps.setString(4, whInventory.getEquipmentType());
            ps.setString(5, whInventory.getEquipmentId());
            ps.setString(6, whInventory.getQuantity());
            ps.setString(7, whInventory.getRequestedBy());
            ps.setString(8, whInventory.getRequestedDate());
            ps.setString(9, whInventory.getRemarks());
            ps.setString(10, whInventory.getVerifiedDate());
            ps.setString(11, whInventory.getInventoryDate());
            ps.setString(12, whInventory.getInventoryLocation());
            ps.setString(13, whInventory.getInventoryBy());
            ps.setString(14, whInventory.getStatus());
            ps.setString(15, whInventory.getReceivedDate());
            ps.setString(16, whInventory.getFlag());
            ps.setString(17, whInventory.getId());
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

    public QueryResult updateWhInventoryLocation(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_inventory SET request_id = ?, inventory_date = ?, inventory_rack = ?, inventory_shelf = ?, inventory_by = ?,  received_date = NOW(), flag = ? WHERE id = ?"
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getInventoryDate());
            ps.setString(3, whInventory.getInventoryRack());
            ps.setString(4, whInventory.getInventoryShelf());
            ps.setString(5, whInventory.getInventoryBy());
            ps.setString(6, whInventory.getFlag());
            ps.setString(7, whInventory.getId());
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

    public QueryResult updateWhInventoryFlag(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_inventory SET request_id = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getFlag());
            ps.setString(3, whInventory.getId());
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

    public QueryResult deleteWhInventory(String whInventoryId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_wh_inventory WHERE id = '" + whInventoryId + "'"
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

    public WhInventory getWhInventory(String whInventoryId) {
        String sql = "SELECT * FROM cdars_wh_inventory WHERE id = '" + whInventoryId + "'";
        WhInventory whInventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
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
        return whInventory;
    }

    public WhInventory getWhInventoryActive(String whInventoryId) {
        String sql = "SELECT * FROM cdars_wh_inventory WHERE id = '" + whInventoryId + "' AND flag = '0'";
        WhInventory whInventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
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
        return whInventory;
    }

    public WhInventory getWhInventoryForWithDateDisplay(String whInventoryId) {
        String sql = "SELECT *, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS view_inventory_date FROM cdars_wh_inventory WHERE id = '" + whInventoryId + "'";
        WhInventory whInventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));

                //view date
                whInventory.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whInventory.setViewInventoryDate(rs.getString("view_inventory_date"));
                whInventory.setViewVerifiedDate(rs.getString("view_verified_date"));
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
        return whInventory;
    }

    public WhInventory getWhInventoryByRequestId(String requestId) {
        String sql = "SELECT * FROM cdars_wh_inventory WHERE request_id = '" + requestId + "'";
        WhInventory whInventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
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
        return whInventory;
    }

    public List<WhInventory> getWhInventoryList() {
        String sql = "SELECT * FROM cdars_wh_inventory ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public List<WhInventory> getWhInventoryActiveList() {
        String sql = "SELECT * FROM cdars_wh_inventory WHERE flag = '0' ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public List<WhInventory> getWhInventoryMbActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'Motherboard' ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setSelected(rs.getString("selected"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public List<WhInventory> getWhInventoryStencilActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'Stencil' ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setSelected(rs.getString("selected"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public List<WhInventory> getWhInventoryTrayActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'Tray' ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setSelected(rs.getString("selected"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public List<WhInventory> getWhInventoryPCBActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'PCB' ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setSelected(rs.getString("selected"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public List<WhInventory> getWhInventoryListWithDateDisplay() {
        String sql = "SELECT *, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, "
                + "DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS view_inventory_date FROM cdars_wh_inventory "
                + "WHERE flag = '0' ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));

                //view date
                whInventory.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whInventory.setViewInventoryDate(rs.getString("view_inventory_date"));
                whInventory.setViewVerifiedDate(rs.getString("view_verified_date"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public Integer getCountInventoryiIdAndLocation(String requestId, String rack, String shelf) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_wh_inventory WHERE request_id = '" + requestId + "' AND inventory_rack = '" + rack + "' AND inventory_shelf = '" + shelf + "'"
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

    public Integer getCountInventoryiId(String requestId) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_wh_inventory WHERE request_id = '" + requestId + "'"
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

    public String getId(String requestId) {
//        QueryResult queryResult = new QueryResult();
        String id = "";
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id AS id FROM cdars_wh_inventory WHERE request_id = '" + requestId + "'"
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
