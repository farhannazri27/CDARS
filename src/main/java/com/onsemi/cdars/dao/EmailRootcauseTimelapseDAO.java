package com.onsemi.cdars.dao;

import com.onsemi.cdars.db.DB;
import com.onsemi.cdars.model.EmailRootcauseTimelapse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.cdars.model.EmailTimelapse;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailRootcauseTimelapseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailRootcauseTimelapseDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EmailRootcauseTimelapseDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEmailTimelapse(EmailRootcauseTimelapse emailRootcauseTimelapse) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_email_rootcause_timelapse (send_cc, user_oncid, user_name, email, flag, remarks) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailRootcauseTimelapse.getSendCc());
            ps.setString(2, emailRootcauseTimelapse.getUserOncid());
            ps.setString(3, emailRootcauseTimelapse.getUserName());
            ps.setString(4, emailRootcauseTimelapse.getEmail());
            ps.setString(5, emailRootcauseTimelapse.getFlag());
            ps.setString(6, emailRootcauseTimelapse.getRemarks());
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

    public QueryResult updateEmailTimelapse(EmailRootcauseTimelapse emailTimelapse) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_email_rootcause_timelapse SET send_cc = ?, user_oncid = ?, user_name = ?, email = ?, flag = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, emailTimelapse.getSendCc());
            ps.setString(2, emailTimelapse.getUserOncid());
            ps.setString(3, emailTimelapse.getUserName());
            ps.setString(4, emailTimelapse.getEmail());
            ps.setString(5, emailTimelapse.getFlag());
            ps.setString(6, emailTimelapse.getRemarks());
            ps.setString(7, emailTimelapse.getId());
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

    public QueryResult deleteEmailTimelapse(String emailTimelapseId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_email_rootcause_timelapse WHERE id = '" + emailTimelapseId + "'"
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

    public EmailRootcauseTimelapse getEmailTimelapse(String emailTimelapseId) {
        String sql = "SELECT * FROM cdars_email_rootcause_timelapse WHERE id = '" + emailTimelapseId + "'";
        EmailRootcauseTimelapse emailTimelapse = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailTimelapse = new EmailRootcauseTimelapse();
                emailTimelapse.setId(rs.getString("id"));
                emailTimelapse.setSendCc(rs.getString("send_cc"));
                emailTimelapse.setUserOncid(rs.getString("user_oncid"));
                emailTimelapse.setUserName(rs.getString("user_name"));
                emailTimelapse.setEmail(rs.getString("email"));
                emailTimelapse.setFlag(rs.getString("flag"));
                emailTimelapse.setRemarks(rs.getString("remarks"));
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
        return emailTimelapse;
    }

    public EmailRootcauseTimelapse getEmailTimelapseWithSelect(String emailTimelapseId, String sendCc) {
        String sql = "SELECT id, send_cc,IF(send_cc=\"" + sendCc + "\",\"selected=''\",\"\") AS selected FROM cdars_email_rootcause_timelapse WHERE id = '" + emailTimelapseId + "'";
        EmailRootcauseTimelapse emailTimelapse = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailTimelapse = new EmailRootcauseTimelapse();
                emailTimelapse.setId(rs.getString("id"));
                emailTimelapse.setSendCc(rs.getString("send_cc"));
                emailTimelapse.setSelect(rs.getString("selected"));
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
        return emailTimelapse;
    }

    public List<EmailRootcauseTimelapse> getEmailTimelapseList() {
        String sql = "SELECT * FROM cdars_email_rootcause_timelapse ORDER BY id ASC";
        List<EmailRootcauseTimelapse> emailTimelapseList = new ArrayList<EmailRootcauseTimelapse>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailRootcauseTimelapse emailTimelapse;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailTimelapse = new EmailRootcauseTimelapse();
                emailTimelapse.setId(rs.getString("id"));
                emailTimelapse.setSendCc(rs.getString("send_cc"));
                emailTimelapse.setUserOncid(rs.getString("user_oncid"));
                emailTimelapse.setUserName(rs.getString("user_name"));
                emailTimelapse.setEmail(rs.getString("email"));
                emailTimelapse.setFlag(rs.getString("flag"));
                emailTimelapse.setRemarks(rs.getString("remarks"));
                emailTimelapseList.add(emailTimelapse);
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
        return emailTimelapseList;
    }

    public List<EmailRootcauseTimelapse> getEmailTimelapseListForCc() {
        String sql = "SELECT * FROM cdars_email_rootcause_timelapse WHERE send_cc = 'Cc' ORDER BY id ASC";
        List<EmailRootcauseTimelapse> emailTimelapseList = new ArrayList<EmailRootcauseTimelapse>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailRootcauseTimelapse emailTimelapse;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailTimelapse = new EmailRootcauseTimelapse();
                emailTimelapse.setId(rs.getString("id"));
                emailTimelapse.setSendCc(rs.getString("send_cc"));
                emailTimelapse.setUserOncid(rs.getString("user_oncid"));
                emailTimelapse.setUserName(rs.getString("user_name"));
                emailTimelapse.setEmail(rs.getString("email"));
                emailTimelapse.setFlag(rs.getString("flag"));
                emailTimelapse.setRemarks(rs.getString("remarks"));
                emailTimelapseList.add(emailTimelapse);
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
        return emailTimelapseList;
    }

    public List<EmailRootcauseTimelapse> getEmailTimelapseListForDistList() {
        String sql = "SELECT * FROM cdars_email_rootcause_timelapse WHERE send_cc = 'Dist List' ORDER BY id ASC";
        List<EmailRootcauseTimelapse> emailTimelapseList = new ArrayList<EmailRootcauseTimelapse>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EmailRootcauseTimelapse emailTimelapse;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailTimelapse = new EmailRootcauseTimelapse();
                emailTimelapse.setId(rs.getString("id"));
                emailTimelapse.setSendCc(rs.getString("send_cc"));
                emailTimelapse.setUserOncid(rs.getString("user_oncid"));
                emailTimelapse.setUserName(rs.getString("user_name"));
                emailTimelapse.setEmail(rs.getString("email"));
                emailTimelapse.setFlag(rs.getString("flag"));
                emailTimelapse.setRemarks(rs.getString("remarks"));
                emailTimelapseList.add(emailTimelapse);
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
        return emailTimelapseList;
    }

    public Integer getCountName(String userOncid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_email_rootcause_timelapse WHERE user_oncid = '" + userOncid + "'"
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

    public Integer getCountNameWithDifferentID(String id, String userOncid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_email_rootcause_timelapse WHERE user_oncid = '" + userOncid + "' AND id != '" + id + "' "
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

    public Integer getCountCc() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_email_rootcause_timelapse WHERE send_cc = 'Cc'"
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
