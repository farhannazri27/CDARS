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
import com.onsemi.cdars.model.PcbLimit;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcbLimitDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PcbLimitDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public PcbLimitDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertPcbLimit(PcbLimit pcbLimit) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_pcb_limit (pcb_type, quantity, remarks, created_by, created_date) VALUES (?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, pcbLimit.getPcbType());
            ps.setString(2, pcbLimit.getQuantity());
            ps.setString(3, pcbLimit.getRemarks());
            ps.setString(4, pcbLimit.getCreatedBy());
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

    public QueryResult updatePcbLimit(PcbLimit pcbLimit) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_pcb_limit SET pcb_type = ?, quantity = ?, remarks = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, pcbLimit.getPcbType());
            ps.setString(2, pcbLimit.getQuantity());
            ps.setString(3, pcbLimit.getRemarks());
            ps.setString(4, pcbLimit.getModifiedBy());
            ps.setString(5, pcbLimit.getId());
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

    public QueryResult deletePcbLimit(String pcbLimitId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_pcb_limit WHERE id = '" + pcbLimitId + "'"
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

    public PcbLimit getPcbLimit(String pcbLimitId) {
        String sql = "SELECT * FROM cdars_pcb_limit WHERE id = '" + pcbLimitId + "'";
        PcbLimit pcbLimit = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pcbLimit = new PcbLimit();
                pcbLimit.setId(rs.getString("id"));
                pcbLimit.setPcbType(rs.getString("pcb_type"));
                pcbLimit.setQuantity(rs.getString("quantity"));
                pcbLimit.setRemarks(rs.getString("remarks"));
                pcbLimit.setCreatedBy(rs.getString("created_by"));
                pcbLimit.setCreatedDate(rs.getString("created_date"));
                pcbLimit.setModifiedBy(rs.getString("modified_by"));
                pcbLimit.setModifiedDate(rs.getString("modified_date"));
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
        return pcbLimit;
    }
    
    public PcbLimit getPcbLimitByType(String pcbType) {
        String sql = "SELECT * FROM cdars_pcb_limit WHERE pcb_type = '" + pcbType + "'";
        PcbLimit pcbLimit = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pcbLimit = new PcbLimit();
                pcbLimit.setId(rs.getString("id"));
                pcbLimit.setPcbType(rs.getString("pcb_type"));
                pcbLimit.setQuantity(rs.getString("quantity"));
                pcbLimit.setRemarks(rs.getString("remarks"));
                pcbLimit.setCreatedBy(rs.getString("created_by"));
                pcbLimit.setCreatedDate(rs.getString("created_date"));
                pcbLimit.setModifiedBy(rs.getString("modified_by"));
                pcbLimit.setModifiedDate(rs.getString("modified_date"));
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
        return pcbLimit;
    }

    public List<PcbLimit> getPcbLimitList() {
        String sql = "SELECT * FROM cdars_pcb_limit ORDER BY id ASC";
        List<PcbLimit> pcbLimitList = new ArrayList<PcbLimit>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            PcbLimit pcbLimit;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pcbLimit = new PcbLimit();
                pcbLimit.setId(rs.getString("id"));
                pcbLimit.setPcbType(rs.getString("pcb_type"));
                pcbLimit.setQuantity(rs.getString("quantity"));
                pcbLimit.setRemarks(rs.getString("remarks"));
                pcbLimit.setCreatedBy(rs.getString("created_by"));
                pcbLimit.setCreatedDate(rs.getString("created_date"));
                pcbLimit.setModifiedBy(rs.getString("modified_by"));
                pcbLimit.setModifiedDate(rs.getString("modified_date"));
                pcbLimitList.add(pcbLimit);
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
        return pcbLimitList;
    }

    public List<PcbLimit> getPcbLimitList2(String type) {
        String sql = "SELECT *, IF(pcb_type=\"" + type + "\",\"selected=''\",\"\") AS selected FROM cdars_pcb_limit ORDER BY id ASC";
        List<PcbLimit> pcbLimitList = new ArrayList<PcbLimit>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            PcbLimit pcbLimit;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pcbLimit = new PcbLimit();
                pcbLimit.setId(rs.getString("id"));
                pcbLimit.setPcbType(rs.getString("pcb_type"));
                pcbLimit.setQuantity(rs.getString("quantity"));
                pcbLimit.setRemarks(rs.getString("remarks"));
                pcbLimit.setCreatedBy(rs.getString("created_by"));
                pcbLimit.setCreatedDate(rs.getString("created_date"));
                pcbLimit.setModifiedBy(rs.getString("modified_by"));
                pcbLimit.setModifiedDate(rs.getString("modified_date"));
                pcbLimit.setSelected(rs.getString("selected"));
                pcbLimitList.add(pcbLimit);
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
        return pcbLimitList;
    }

    public Integer getCountPcbTypeDifferentId(String id, String pcbType) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_pcb_limit WHERE id != '" + id + "' AND pcb_type = '" + pcbType + "' "
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

    public Integer getCountPcbType(String pcbType) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_pcb_limit WHERE pcb_type = '" + pcbType + "' "
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
