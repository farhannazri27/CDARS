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
                    + "quantity, requested_by, requested_date, remarks, verified_date, receival_date, inventory_date, inventory_rack, inventory_shelf,"
                    + " inventory_by, status, received_date, flag, load_card_id, load_card_qty, program_card_id, program_card_qty) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
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
            ps.setString(19, whInventory.getReceivalDate());
            ps.setString(20, whInventory.getInventoryDate());
            ps.setString(21, whInventory.getInventoryRack());
            ps.setString(22, whInventory.getInventoryShelf());
            ps.setString(23, whInventory.getInventoryBy());
            ps.setString(24, whInventory.getStatus());
            ps.setString(25, whInventory.getFlag());
            ps.setString(26, whInventory.getLoadCard());
            ps.setString(27, whInventory.getLoadCardQty());
            ps.setString(28, whInventory.getProgramCard());
            ps.setString(29, whInventory.getProgramCardQty());
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

    public QueryResult insertWhInventoryEqptFromCsv(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_inventory (request_id, mp_no, mp_expiry_date, equipment_type, equipment_id, "
                    + "quantity, requested_by, requested_date, verified_date, receival_date, inventory_date, "
                    + "inventory_rack, inventory_shelf, inventory_by, status, received_date, flag,"
                    + "pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, load_card_qty, program_card_qty) "
                    + "VALUES (?,?,?,?,?,?,?,NOW(),NOW(),NOW(),NOW(),?,?,?,?,NOW(),?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getMpNo());
            ps.setString(3, whInventory.getMpExpiryDate());
            ps.setString(4, whInventory.getEquipmentType());
            ps.setString(5, whInventory.getEquipmentId());
            ps.setString(6, whInventory.getQuantity());
            ps.setString(7, whInventory.getRequestedBy());
            ps.setString(8, whInventory.getInventoryRack());
            ps.setString(9, whInventory.getInventoryShelf());
            ps.setString(10, whInventory.getInventoryBy());
            ps.setString(11, whInventory.getStatus());
            ps.setString(12, whInventory.getFlag());
            ps.setString(13, whInventory.getPcbAQty());
            ps.setString(14, whInventory.getPcbBQty());
            ps.setString(15, whInventory.getPcbCQty());
            ps.setString(16, whInventory.getPcbCtrQty());
            ps.setString(17, whInventory.getLoadCardQty());
            ps.setString(18, whInventory.getProgramCardQty());
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

    public QueryResult insertWhInventoryLcFromCsv(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_inventory (request_id, mp_no, mp_expiry_date, equipment_type, equipment_id, "
                    + "quantity, requested_by, requested_date, verified_date, receival_date, inventory_date, "
                    + "inventory_rack, inventory_shelf, inventory_by, status, received_date, flag,"
                    + "pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, load_card_qty, program_card_qty,load_card_id) "
                    + "VALUES (?,?,?,?,?,?,?,NOW(),NOW(),NOW(),NOW(),?,?,?,?,NOW(),?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getMpNo());
            ps.setString(3, whInventory.getMpExpiryDate());
            ps.setString(4, whInventory.getEquipmentType());
            ps.setString(5, whInventory.getEquipmentId());
            ps.setString(6, whInventory.getQuantity());
            ps.setString(7, whInventory.getRequestedBy());
            ps.setString(8, whInventory.getInventoryRack());
            ps.setString(9, whInventory.getInventoryShelf());
            ps.setString(10, whInventory.getInventoryBy());
            ps.setString(11, whInventory.getStatus());
            ps.setString(12, whInventory.getFlag());
            ps.setString(13, whInventory.getPcbAQty());
            ps.setString(14, whInventory.getPcbBQty());
            ps.setString(15, whInventory.getPcbCQty());
            ps.setString(16, whInventory.getPcbCtrQty());
            ps.setString(17, whInventory.getLoadCardQty());
            ps.setString(18, whInventory.getProgramCardQty());
            ps.setString(19, whInventory.getLoadCard());
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

    public QueryResult insertWhInventoryPcFromCsv(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_inventory (request_id, mp_no, mp_expiry_date, equipment_type, equipment_id, "
                    + "quantity, requested_by, requested_date, verified_date, receival_date, inventory_date, "
                    + "inventory_rack, inventory_shelf, inventory_by, status, received_date, flag,"
                    + "pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, load_card_qty, program_card_qty,program_card_id) "
                    + "VALUES (?,?,?,?,?,?,?,NOW(),NOW(),NOW(),NOW(),?,?,?,?,NOW(),?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getMpNo());
            ps.setString(3, whInventory.getMpExpiryDate());
            ps.setString(4, whInventory.getEquipmentType());
            ps.setString(5, whInventory.getEquipmentId());
            ps.setString(6, whInventory.getQuantity());
            ps.setString(7, whInventory.getRequestedBy());
            ps.setString(8, whInventory.getInventoryRack());
            ps.setString(9, whInventory.getInventoryShelf());
            ps.setString(10, whInventory.getInventoryBy());
            ps.setString(11, whInventory.getStatus());
            ps.setString(12, whInventory.getFlag());
            ps.setString(13, whInventory.getPcbAQty());
            ps.setString(14, whInventory.getPcbBQty());
            ps.setString(15, whInventory.getPcbCQty());
            ps.setString(16, whInventory.getPcbCtrQty());
            ps.setString(17, whInventory.getLoadCardQty());
            ps.setString(18, whInventory.getProgramCardQty());
            ps.setString(19, whInventory.getProgramCard());
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

    public QueryResult insertWhInventoryLcAndPcFromCsv(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_inventory (request_id, mp_no, mp_expiry_date, equipment_type, equipment_id, "
                    + "quantity, requested_by, requested_date, verified_date, receival_date, inventory_date, "
                    + "inventory_rack, inventory_shelf, inventory_by, status, received_date, flag,"
                    + "pcb_a_qty, pcb_b_qty, pcb_c_qty, pcb_ctr_qty, load_card_qty, program_card_qty, load_card_id, program_card_id) "
                    + "VALUES (?,?,?,?,?,?,?,NOW(),NOW(),NOW(),NOW(),?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getMpNo());
            ps.setString(3, whInventory.getMpExpiryDate());
            ps.setString(4, whInventory.getEquipmentType());
            ps.setString(5, whInventory.getEquipmentId());
            ps.setString(6, whInventory.getQuantity());
            ps.setString(7, whInventory.getRequestedBy());
            ps.setString(8, whInventory.getInventoryRack());
            ps.setString(9, whInventory.getInventoryShelf());
            ps.setString(10, whInventory.getInventoryBy());
            ps.setString(11, whInventory.getStatus());
            ps.setString(12, whInventory.getFlag());
            ps.setString(13, whInventory.getPcbAQty());
            ps.setString(14, whInventory.getPcbBQty());
            ps.setString(15, whInventory.getPcbCQty());
            ps.setString(16, whInventory.getPcbCtrQty());
            ps.setString(17, whInventory.getLoadCardQty());
            ps.setString(18, whInventory.getProgramCardQty());
            ps.setString(19, whInventory.getLoadCard());
            ps.setString(20, whInventory.getProgramCard());
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

    public QueryResult updateMpExpiryDateWhInventory(WhInventory whInventory) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_inventory SET request_id = ?, mp_no = ?, mp_expiry_date= ? WHERE id = ?"
            );
            ps.setString(1, whInventory.getRequestId());
            ps.setString(2, whInventory.getMpNo());
            ps.setString(3, whInventory.getMpExpiryDate());
            ps.setString(4, whInventory.getId());
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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

    public List<WhInventory> getWhInventoryLoadCardActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'Load Card' ORDER BY id DESC";
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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

    public List<WhInventory> getWhInventoryProgramCardActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'Program Card' ORDER BY id DESC";
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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

    public List<WhInventory> getWhInventoryLoadAndProgramCardActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'Load Card & Program Card' ORDER BY id DESC";
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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

    public List<WhInventory> getWhInventoryBibPartActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'BIB Parts' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAteDtsActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_DTS' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAteFetActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_FET' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAtePftActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_PFT' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAteTesecActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_TESEC' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAteTestActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_TESTFIXTURE' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAteEtsActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_ETS' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAteEsdActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_ESD' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryAteAccActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'ATE_SPAREPART_ACC' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryEqpGeneralActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'EQP_SPAREPART_GENERAL' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryEqpHastActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'EQP_SPAREPART_H3TRB_AC_HAST' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryEqpWfActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'EQP_SPAREPART_HTS_HTB_WF' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryEqpIolActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'EQP_SPAREPART_IOL' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryEqpPtcActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'EQP_SPAREPART_TC_PTC' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryEqpFolActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'EQP_SPAREPART_FOL' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryEqpBlrActiveList(String type) {
        String sql = "SELECT *, IF(equipment_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_wh_inventory WHERE flag = '0' AND equipment_type = 'EQP_SPAREPART_BLR' ORDER BY id DESC";
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

    public List<WhInventory> getWhInventoryListWithDateDisplay() {
        String sql = "SELECT *, "
                + "DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, "
                + "DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, "
                + "DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS view_inventory_date "
                + "FROM cdars_wh_inventory "
                + "WHERE flag = '0' "
                + "ORDER BY id DESC";
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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

    public List<WhInventory> getWhInventoryListWithDateDisplay2() {
        String sql = "SELECT id,request_id,"
                + "mp_no,mp_expiry_date,"
                + "equipment_type,equipment_id, quantity,"
                + "inventory_date,inventory_rack,inventory_shelf,"
                + "load_card_id,load_card_qty,program_card_id,program_card_qty,"
                + "DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, "
                + "DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS view_inventory_date "
                + "FROM cdars_wh_inventory "
                + "WHERE flag = '0' "
                + "ORDER BY id DESC";
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
//                whInventory.setPcbA(rs.getString("pcb_a"));
//                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
//                whInventory.setPcbB(rs.getString("pcb_b"));
//                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
//                whInventory.setPcbC(rs.getString("pcb_c"));
//                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
//                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
//                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
//                whInventory.setRequestedBy(rs.getString("requested_by"));
//                whInventory.setRequestedDate(rs.getString("requested_date"));
//                whInventory.setRemarks(rs.getString("remarks"));
//                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
//                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
//                whInventory.setInventoryBy(rs.getString("inventory_by"));
//                whInventory.setStatus(rs.getString("status"));
//                whInventory.setReceivedDate(rs.getString("received_date"));
//                whInventory.setFlag(rs.getString("flag"));

                //view date
                whInventory.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whInventory.setViewInventoryDate(rs.getString("view_inventory_date"));
//                whInventory.setViewVerifiedDate(rs.getString("view_verified_date"));
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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

    public Integer getCountMpExpiryDate() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_inventory WHERE flag = '0' AND (mp_expiry_date BETWEEN NOW() AND ADDDATE(DATE(NOW()),30))"
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

    public Integer getCountExpiredMp() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_inventory WHERE flag = '0' AND mp_expiry_date < NOW()"
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

    public List<WhInventory> getWhInventoryListMpExpire30Days() {
        String sql = "SELECT *, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, "
                + "DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS view_inventory_date FROM cdars_wh_inventory WHERE flag = '0' AND (mp_expiry_date BETWEEN NOW() "
                + "AND ADDDATE(DATE(NOW()),30)) ORDER BY equipment_type ASC, view_mp_expiry_date ASC ";
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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

    public List<WhInventory> getWhInventoryListMpHasExpired() {
        String sql = "SELECT *, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, DATE_FORMAT(verified_date,'%d %M %Y %h:%i %p') AS view_verified_date, "
                + "DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS view_inventory_date FROM cdars_wh_inventory WHERE flag = '0' AND mp_expiry_date < NOW() "
                + "ORDER BY equipment_type ASC, view_mp_expiry_date ASC ";
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
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
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
}
