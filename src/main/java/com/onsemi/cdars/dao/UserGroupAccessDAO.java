package com.onsemi.cdars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.cdars.db.DB;
import com.onsemi.cdars.model.UserGroupAccess;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserGroupAccessDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupAccessDAO.class);
    private final Connection conn;

    public UserGroupAccessDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
    }
    
    public List<UserGroupAccess> getGroupAccess(String groupId) {
        String sql = "SELECT * FROM cdars_menu_access WHERE group_id = '" + groupId + "'";
        List<UserGroupAccess> userGroupAccessList = new ArrayList<UserGroupAccess>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserGroupAccess userGroupAccess;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userGroupAccess = new UserGroupAccess(
                        rs.getString("id"),
                        rs.getString("group_id"),
                        rs.getString("menu_id")
                );
                userGroupAccessList.add(userGroupAccess);
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
        return userGroupAccessList;
    }

    public List<UserGroupAccess> getUserGroupAccess(String groupId) {
        String sql = "SELECT m.id AS menu_id, m.parent_code, m.code, m.name, uga.id, uga.group_id, IF (m.id = uga.menu_id, \"checked=\\\"\\\"\", \"\") AS selected "
                + "FROM cdars_menu m LEFT JOIN cdars_menu_access uga ON uga.group_id = '" + groupId + "' AND m.id = uga.menu_id ORDER BY m.code";
        List<UserGroupAccess> userGroupAccessList = new ArrayList<UserGroupAccess>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserGroupAccess userGroupAccess;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userGroupAccess = new UserGroupAccess(
                        rs.getString("id"),
                        rs.getString("group_id"),
                        rs.getString("menu_id"),
                        rs.getString("parent_code"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("selected")
                );
                userGroupAccessList.add(userGroupAccess);
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
        return userGroupAccessList;
    }

    public QueryResult addAccess(String groupId, String menuId) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "INSERT into cdars_menu_access (group_id, menu_id) "
                + "SELECT * FROM (SELECT ? AS group_id, ? AS menu_id) AS tmp "
                + "WHERE NOT EXISTS (SELECT id FROM cdars_menu_access WHERE group_id = ? AND menu_id = ?) "
                + "LIMIT 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, groupId);
            ps.setString(2, menuId);
            ps.setString(3, groupId);
            ps.setString(4, menuId);
            queryResult.setResult(ps.executeUpdate());
            ps.close();
            queryResult.setResult(1);
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

    public QueryResult removeAccess(String groupId, String[] groupAccess) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String idList = "";
        try {
            for (String access : groupAccess) {
                String findId = "SELECT id FROM cdars_menu_access WHERE group_id = ? AND menu_id = ?";
                PreparedStatement ps = conn.prepareStatement(findId);
                ps.setString(1, groupId);
                ps.setString(2, access);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    idList += rs.getString("id") + ",";
                }
                rs.close();
                ps.close();
            }
            if (!idList.equals("")) {
                idList = idList.substring(0, idList.length() - 1);
            }
            String delete = "DELETE FROM cdars_menu_access WHERE id NOT IN (" + idList + ") AND group_id ='" + groupId + "'";
            if (idList.equals("")) {
                delete = "DELETE FROM cdars_menu_access WHERE group_id = '" + groupId + "'";
            }
            PreparedStatement ps = conn.prepareStatement(delete);
            queryResult.setResult(ps.executeUpdate());
            ps.close();
            queryResult.setResult(1);
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
    
    public QueryResult removeAccessByGroupId(String groupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_menu_access WHERE group_id = '" + groupId + "'"
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
}
