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
import com.onsemi.cdars.model.EmailConfig;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailConfigDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfigDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EmailConfigDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEmailConfig(EmailConfig emailConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_email_config (task_pdetails_code, user_oncid, user_name, email, flag, remarks) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailConfig.getTaskPdetailsCode());
            ps.setString(2, emailConfig.getUserOncid());
            ps.setString(3, emailConfig.getUserName());
            ps.setString(4, emailConfig.getEmail());
            ps.setString(5, emailConfig.getFlag());
            ps.setString(6, emailConfig.getRemarks());
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

    public QueryResult updateEmailConfig(EmailConfig emailConfig) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_email_config SET task_pdetails_code = ?, user_oncid = ?, user_name = ?, email = ?, flag = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, emailConfig.getTaskPdetailsCode());
            ps.setString(2, emailConfig.getUserOncid());
            ps.setString(3, emailConfig.getUserName());
            ps.setString(4, emailConfig.getEmail());
            ps.setString(5, emailConfig.getFlag());
            ps.setString(6, emailConfig.getRemarks());
            ps.setString(7, emailConfig.getId());
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

    public QueryResult deleteEmailConfig(String emailConfigId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_email_config WHERE id = '" + emailConfigId + "'"
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

    public EmailConfig getEmailConfig(String emailConfigId) {
        String sql = "SELECT * FROM cdars_email_config WHERE id = '" + emailConfigId + "'";
        EmailConfig emailConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailConfig = new EmailConfig();
                emailConfig.setId(rs.getString("id"));
                emailConfig.setTaskPdetailsCode(rs.getString("task_pdetails_code"));
                emailConfig.setUserOncid(rs.getString("user_oncid"));
                emailConfig.setUserName(rs.getString("user_name"));
                emailConfig.setEmail(rs.getString("email"));
                emailConfig.setFlag(rs.getString("flag"));
                emailConfig.setRemarks(rs.getString("remarks"));
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
        return emailConfig;
    }

    public List<EmailConfig> getEmailConfigList() {
        String sql = "SELECT * FROM cdars_email_config ORDER BY id ASC";
        List<EmailConfig> emailConfigList = new ArrayList<EmailConfig>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailConfig emailConfig;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailConfig = new EmailConfig();
                emailConfig.setId(rs.getString("id"));
                emailConfig.setTaskPdetailsCode(rs.getString("task_pdetails_code"));
                emailConfig.setUserOncid(rs.getString("user_oncid"));
                emailConfig.setUserName(rs.getString("user_name"));
                emailConfig.setEmail(rs.getString("email"));
                emailConfig.setFlag(rs.getString("flag"));
                emailConfig.setRemarks(rs.getString("remarks"));
                emailConfigList.add(emailConfig);
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
        return emailConfigList;
    }
    
    public Integer getCountTask(String job) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_email_config WHERE task_pdetails_code = '" + job + "'"
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
    public EmailConfig getEmailConfigByTask(String task) {
        String sql = "SELECT * FROM cdars_email_config WHERE task_pdetails_code = '" + task + "'";
        EmailConfig emailConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailConfig = new EmailConfig();
                emailConfig.setId(rs.getString("id"));
                emailConfig.setTaskPdetailsCode(rs.getString("task_pdetails_code"));
                emailConfig.setUserOncid(rs.getString("user_oncid"));
                emailConfig.setUserName(rs.getString("user_name"));
                emailConfig.setEmail(rs.getString("email"));
                emailConfig.setFlag(rs.getString("flag"));
                emailConfig.setRemarks(rs.getString("remarks"));
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
        return emailConfig;
    }
}
