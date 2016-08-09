package com.onsemi.cdars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.cdars.db.DB;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
    private final Connection conn;

    public UserDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
    }

    public QueryResult insertUser(User user) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_user (login_id, password, group_id, is_active, created_by, created_time) VALUES (?,?,?,'1',?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, user.getLoginId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getGroupId());
            ps.setString(4, user.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            if (queryResult.getResult() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                }
                ps.close();
                ps = conn.prepareStatement(
                        "INSERT INTO cdars_user_profile (user_id, fullname, email) VALUES (?,?,?)"
                );
                ps.setString(1, queryResult.getGeneratedKey());
                ps.setString(2, user.getFullname());
                ps.setString(3, user.getEmail());
                ps.executeUpdate();
                ps.close();
            }
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

    public QueryResult updateUser(User user) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_user SET group_id = ?, is_active = ?, modified_by = ?, modified_time = NOW() WHERE id = ?"
            );
            ps.setString(1, user.getGroupId());
            ps.setString(2, user.getIsActive());
            ps.setString(3, user.getModifiedBy());
            ps.setString(4, user.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
            ps = conn.prepareStatement(
                    "UPDATE cdars_user_profile SET fullname = ?, email = ?, modified_by = ?, modified_time = NOW() WHERE user_id = ?"
            );
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getModifiedBy());
            ps.setString(4, user.getId());
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
    
    public QueryResult updateGroup(String userId, String groupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_user SET group_id = ? WHERE id = ?"
            );
            ps.setString(1, groupId);
            ps.setString(2, userId);
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

    public QueryResult updatePassword(User user) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_user SET password = ?, modified_by = ?, modified_time = NOW() WHERE id = ?"
            );
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getModifiedBy());
            ps.setString(3, user.getId());
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

    public QueryResult deleteUser(String userId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_user WHERE id = '" + userId + "'"
            );
            queryResult.setResult(ps.executeUpdate());
            ps.close();
            if (queryResult.getResult() == 1) {
                ps = conn.prepareStatement(
                        "DELETE FROM cdars_user_profile WHERE user_id = '" + userId + "'"
                );
                queryResult.setResult(ps.executeUpdate());
                ps.close();
            }
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

    public User getUser(String userId) {
        /*String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM cdars_user u, cdars_user_group ug, cdars_user_profile up "
         + "WHERE u.id = '" + userId + "' AND u.group_id = ug.id AND u.id = up.user_id ORDER BY up.fullname";*/
        String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM cdars_user u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id) "
                + "LEFT JOIN cdars_user_profile up ON (u.id = up.user_id) "
                + "WHERE u.id = '" + userId + "' ORDER BY up.fullname";
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("login_id"),
                        rs.getString("password"),
                        rs.getString("group_id"),
                        rs.getString("is_active"),
                        rs.getString("created_by"),
                        rs.getString("created_time"),
                        rs.getString("modified_by"),
                        rs.getString("modified_time"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getString("fullname"),
                        rs.getString("email")
                );
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
        return user;
    }

    public User getUserByLoginId(String loginId) {
        /*String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM cdars_user u, cdars_user_group ug, cdars_user_profile up "
         + "WHERE u.login_id = '" + loginId + "' AND u.group_id = ug.id AND u.id = up.user_id ORDER BY up.fullname";*/
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name, up.fullname, up.email FROM cdars_user u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id) LEFT JOIN cdars_user_profile up ON (u.id = up.user_id) "
                + "WHERE u.login_id = '" + loginId + "' ORDER BY up.fullname";
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("login_id"),
                        rs.getString("password"),
                        rs.getString("group_id"),
                        rs.getString("is_active"),
                        rs.getString("created_by"),
                        rs.getString("created_time"),
                        rs.getString("modified_by"),
                        rs.getString("modified_time"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getString("fullname"),
                        rs.getString("email")
                );
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
        return user;
    }

    public List<User> getUserList(String groupId) {
        String filterGroupId = "";
        if (!groupId.equals("")) {
            //filterGroupId = "u.group_id = '" + groupId + "' AND";
            filterGroupId = "WHERE u.group_id = '" + groupId + "' ";
        }
        /*String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM cdars_user u, cdars_user_group ug, cdars_user_profile up "
         + "WHERE " + filterGroupId + " u.group_id = ug.id AND u.id = up.user_id ORDER BY up.fullname";*/
        String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM cdars_user u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id) "
                + "LEFT JOIN cdars_user_profile up ON (u.id = up.user_id) "
                + filterGroupId
                + "ORDER BY up.fullname";
        List<User> userList = new ArrayList<User>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            User user;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("login_id"),
                        rs.getString("password"),
                        rs.getString("group_id"),
                        rs.getString("is_active"),
                        rs.getString("created_by"),
                        rs.getString("created_time"),
                        rs.getString("modified_by"),
                        rs.getString("modified_time"),
                        rs.getString("group_code"),
                        rs.getString("group_name"),
                        rs.getString("fullname"),
                        rs.getString("email")
                );
                userList.add(user);
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
        return userList;
    }

    public Integer getCountByGroupId(String groupId) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM cdars_user WHERE group_id = '" + groupId + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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
