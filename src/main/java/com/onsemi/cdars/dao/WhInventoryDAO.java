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
                    "INSERT INTO cdars_wh_inventory (inventory_id, mp_no, mp_expiry_date, equipment_type, equipment_id, quantity, requested_by, requested_date, remarks, verified_date, inventory_date, invetory_rack, inventory_slot, inventory_by, status, received_date, flag, location) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whInventory.getInventoryId());
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
            ps.setString(12, whInventory.getInvetoryRack());
            ps.setString(13, whInventory.getInventorySlot());
            ps.setString(14, whInventory.getInventoryBy());
            ps.setString(15, whInventory.getStatus());
            ps.setString(16, whInventory.getFlag());
            ps.setString(17, whInventory.getLocation());
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
                    "UPDATE cdars_wh_inventory SET inventory_id = ?, mp_no = ?, mp_expiry_date= ?, equipment_type = ?, equipment_id = ?, quantity = ?, requested_by = ?, requested_date = ?, remarks = ?, verified_date = ?, inventory_date = ?, invetory_rack = ?, inventory_slot = ?, inventory_by = ?, status = ?, received_date = ?, flag = ?, location = ? WHERE id = ?"
            );
            ps.setString(1, whInventory.getInventoryId());
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
            ps.setString(12, whInventory.getInvetoryRack());
            ps.setString(13, whInventory.getInventorySlot());
            ps.setString(14, whInventory.getInventoryBy());
            ps.setString(15, whInventory.getStatus());
            ps.setString(16, whInventory.getReceivedDate());
            ps.setString(17, whInventory.getFlag());
            ps.setString(18, whInventory.getLocation());
            ps.setString(19, whInventory.getId());
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
                    "UPDATE cdars_wh_inventory SET inventory_id = ?, inventory_date = ?, invetory_rack = ?, inventory_slot = ?, inventory_by = ?,  received_date = NOW(), flag = ? WHERE id = ?"
            );
            ps.setString(1, whInventory.getInventoryId());
            ps.setString(2, whInventory.getInventoryDate());
            ps.setString(3, whInventory.getInvetoryRack());
            ps.setString(4, whInventory.getInventorySlot());
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
                whInventory.setInventoryId(rs.getString("inventory_id"));
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
                whInventory.setInvetoryRack(rs.getString("invetory_rack"));
                whInventory.setInventorySlot(rs.getString("inventory_slot"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setLocation(rs.getString("location"));
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
        String sql = "SELECT *, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, DATE_FORMAT(verified_date,'%d %M %Y') AS view_verified_date, DATE_FORMAT(inventory_date,'%d %M %Y') AS view_inventory_date FROM cdars_wh_inventory WHERE id = '" + whInventoryId + "'";
        WhInventory whInventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setInventoryId(rs.getString("inventory_id"));
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
                whInventory.setInvetoryRack(rs.getString("invetory_rack"));
                whInventory.setInventorySlot(rs.getString("inventory_slot"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setLocation(rs.getString("location"));

                //view date
                whInventory.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whInventory.setviewInventoryDate(rs.getString("view_inventory_date"));
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
                whInventory.setInventoryId(rs.getString("inventory_id"));
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
                whInventory.setInvetoryRack(rs.getString("invetory_rack"));
                whInventory.setInventorySlot(rs.getString("inventory_slot"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setLocation(rs.getString("location"));
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
        String sql = "SELECT *, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS view_mp_expiry_date, DATE_FORMAT(verified_date,'%d %M %Y') AS view_verified_date, "
                + "DATE_FORMAT(inventory_date,'%d %M %Y') AS view_inventory_date FROM cdars_wh_inventory "
                + "WHERE flag = '0' ORDER BY id DESC";
        List<WhInventory> whInventoryList = new ArrayList<WhInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            WhInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setInventoryId(rs.getString("inventory_id"));
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
                whInventory.setInvetoryRack(rs.getString("invetory_rack"));
                whInventory.setInventorySlot(rs.getString("inventory_slot"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setLocation(rs.getString("location"));

                //view date
                whInventory.setViewMpExpiryDate(rs.getString("view_mp_expiry_date"));
                whInventory.setviewInventoryDate(rs.getString("view_inventory_date"));
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

    public Integer getCountInventoryiIdSlotRack(String inventoryId, String slot, String rack) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_wh_inventory WHERE inventory_id = '" + inventoryId + "' AND inventory_slot = '" + slot + "' AND invetory_rack = '" + rack + "'"
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

    public Integer getCountInventoryiId(String inventoryId) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_wh_inventory WHERE inventory_id = '" + inventoryId + "'"
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

    public String getId(String inventoryId) {
//        QueryResult queryResult = new QueryResult();
        String id = "";
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id AS id FROM cdars_wh_inventory WHERE inventory_id = '" + inventoryId + "'"
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
