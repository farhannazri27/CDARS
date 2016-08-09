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
import com.onsemi.cdars.model.PcbFtp;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcbFtpDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PcbFtpDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public PcbFtpDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertPcbFtp(PcbFtp pcbFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_pcb_ftp (date_off, rms, process, status, support_item, created_date) VALUES (?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, pcbFtp.getDateOff());
            ps.setString(2, pcbFtp.getRms());
            ps.setString(3, pcbFtp.getProcess());
            ps.setString(4, pcbFtp.getStatus());
            ps.setString(5, pcbFtp.getSupportItem());
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

    public QueryResult updatePcbFtp(PcbFtp pcbFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_pcb_ftp SET date_off = ?, rms = ?, process = ?, status = ?, support_item = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, pcbFtp.getDateOff());
            ps.setString(2, pcbFtp.getRms());
            ps.setString(3, pcbFtp.getProcess());
            ps.setString(4, pcbFtp.getStatus());
            ps.setString(5, pcbFtp.getSupportItem());
            ps.setString(6, pcbFtp.getCreatedDate());
            ps.setString(7, pcbFtp.getId());
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

    public QueryResult deletePcbFtp(String pcbFtpId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_pcb_ftp WHERE id = '" + pcbFtpId + "'"
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

    public PcbFtp getPcbFtp(String pcbFtpId) {
        String sql = "SELECT * FROM cdars_pcb_ftp WHERE id = '" + pcbFtpId + "'";
        PcbFtp pcbFtp = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pcbFtp = new PcbFtp();
                pcbFtp.setId(rs.getString("id"));
                pcbFtp.setDateOff(rs.getString("date_off"));
                pcbFtp.setRms(rs.getString("rms"));
                pcbFtp.setProcess(rs.getString("process"));
                pcbFtp.setStatus(rs.getString("status"));
                pcbFtp.setSupportItem(rs.getString("support_item"));
                pcbFtp.setCreatedDate(rs.getString("created_date"));
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
        return pcbFtp;
    }

    public List<PcbFtp> getPcbFtpList() {
        String sql = "SELECT * FROM cdars_pcb_ftp ORDER BY id ASC";
        List<PcbFtp> pcbFtpList = new ArrayList<PcbFtp>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            PcbFtp pcbFtp;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pcbFtp = new PcbFtp();
                pcbFtp.setId(rs.getString("id"));
                pcbFtp.setDateOff(rs.getString("date_off"));
                pcbFtp.setRms(rs.getString("rms"));
                pcbFtp.setProcess(rs.getString("process"));
                pcbFtp.setStatus(rs.getString("status"));
                pcbFtp.setSupportItem(rs.getString("support_item"));
                pcbFtp.setCreatedDate(rs.getString("created_date"));
                pcbFtpList.add(pcbFtp);
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
        return pcbFtpList;
    }

    public Integer getCountExistingData(PcbFtp pcbFtp) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_pcb_ftp WHERE date_off = ? and rms = ? and process = ? "
                    + "and status = ? and support_item = ?"
            );
            ps.setString(1, pcbFtp.getDateOff());
            ps.setString(2, pcbFtp.getRms());
            ps.setString(3, pcbFtp.getProcess());
            ps.setString(4, pcbFtp.getStatus());
            ps.setString(5, pcbFtp.getSupportItem());

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
