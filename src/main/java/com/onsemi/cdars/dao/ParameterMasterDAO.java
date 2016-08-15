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
import com.onsemi.cdars.model.ParameterMaster;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterMasterDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterMasterDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ParameterMasterDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertParameterMaster(ParameterMaster parameterMaster) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_parameter_master (code, name, remarks, created_by, created_date) VALUES (?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, parameterMaster.getCode());
            ps.setString(2, parameterMaster.getName());
            ps.setString(3, parameterMaster.getRemarks());
            ps.setString(4, parameterMaster.getCreatedBy());
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

    public QueryResult updateParameterMaster(ParameterMaster parameterMaster) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_parameter_master SET code = ?, name = ?, remarks = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, parameterMaster.getCode());
            ps.setString(2, parameterMaster.getName());
            ps.setString(3, parameterMaster.getRemarks());
            ps.setString(4, parameterMaster.getModifiedBy());
            ps.setString(5, parameterMaster.getId());
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

    public QueryResult deleteParameterMaster(String parameterMasterId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_parameter_master WHERE id = '" + parameterMasterId + "'"
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

    public ParameterMaster getParameterMaster(String parameterMasterId) {
        String sql = "SELECT * FROM cdars_parameter_master WHERE id = '" + parameterMasterId + "'";
        ParameterMaster parameterMaster = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                parameterMaster = new ParameterMaster();
                parameterMaster.setId(rs.getString("id"));
                parameterMaster.setCode(rs.getString("code"));
                parameterMaster.setName(rs.getString("name"));
                parameterMaster.setRemarks(rs.getString("remarks"));
                parameterMaster.setCreatedBy(rs.getString("created_by"));
                parameterMaster.setCreatedDate(rs.getString("created_date"));
                parameterMaster.setModifiedBy(rs.getString("modified_by"));
                parameterMaster.setModifiedDate(rs.getString("modified_date"));
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
        return parameterMaster;
    }

    public List<ParameterMaster> getParameterMasterList() {
        String sql = "SELECT * FROM cdars_parameter_master ORDER BY id ASC";
        List<ParameterMaster> parameterMasterList = new ArrayList<ParameterMaster>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ParameterMaster parameterMaster;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                parameterMaster = new ParameterMaster();
                parameterMaster.setId(rs.getString("id"));
                parameterMaster.setCode(rs.getString("code"));
                parameterMaster.setName(rs.getString("name"));
                parameterMaster.setRemarks(rs.getString("remarks"));
                parameterMaster.setCreatedBy(rs.getString("created_by"));
                parameterMaster.setCreatedDate(rs.getString("created_date"));
                parameterMaster.setModifiedBy(rs.getString("modified_by"));
                parameterMaster.setModifiedDate(rs.getString("modified_date"));
                parameterMasterList.add(parameterMaster);
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
        return parameterMasterList;
    }

    public String getNextMasterCode() {
        String sql = "SELECT LPAD (MAX(code)+1,3,'0') AS code FROM cdars_parameter_master";
        String code = "";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                code = rs.getString("code");
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
        return code;
    }

    public String getMasterCodeandName(String masterCode) {
        String sql = "SELECT s.code AS code, s.name AS name FROM cdars_parameter_master s"
                + " WHERE code = '" + masterCode + "'";
        String name = "";
        String code = "";
        String codeName = "";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                code = rs.getNString("code");
                codeName = code + " - " + name;
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
        return codeName;
    }
    
     public String getMasterCode(String masterCode) {
        String sql = "SELECT s.code AS code FROM cdars_parameter_master s"
                + " WHERE code = '" + masterCode + "'";
        String code = "";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                code = rs.getNString("code");
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
        return code;
    }
}
