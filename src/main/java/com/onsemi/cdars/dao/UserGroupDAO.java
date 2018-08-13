package com.onsemi.cdars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.cdars.db.DB;
import com.onsemi.cdars.model.UserGroup;
import com.onsemi.cdars.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserGroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupDAO.class);
    private final Connection conn;

    public UserGroupDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
    }

    public QueryResult insertGroup(UserGroup userGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_user_group (code, name, created_by, created_time,master_group_id) VALUES (?,?,?,NOW(),'0')", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, userGroup.getCode());
            ps.setString(2, userGroup.getName());
            ps.setString(3, userGroup.getCreatedBy());
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

    public QueryResult updateGroup(UserGroup userGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_user_group SET code = ?, name = ?, modified_by = ?, modified_time = NOW() WHERE id = ?"
            );
            ps.setString(1, userGroup.getCode());
            ps.setString(2, userGroup.getName());
            ps.setString(3, userGroup.getModifiedBy());
            ps.setString(4, userGroup.getId());
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

    public QueryResult deleteGroup(String groupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM cdars_user_group WHERE id = '" + groupId + "'"
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

    public UserGroup getGroup(String groupId) {
        String sql = "SELECT * FROM cdars_user_group WHERE id = '" + groupId + "'";
        UserGroup userGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userGroup = new UserGroup(
                        rs.getString("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("master_group_id"),
                        rs.getString("created_by"),
                        rs.getString("created_time"),
                        rs.getString("modified_by"),
                        rs.getString("modified_time")
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
        return userGroup;
    }

    public List<UserGroup> getGroupList(String groupId) {
        String sql = "SELECT id, code, name, master_group_id, IF(id=\"" + groupId + "\",\"selected=''\",\"\") AS selected FROM cdars_user_group ORDER BY name";
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserGroup userGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userGroup = new UserGroup(
                        rs.getString("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("master_group_id"),
                        rs.getString("selected")
                );
                userGroupList.add(userGroup);
            }
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
        return userGroupList;
    }

    public QueryResult updateMasterGroupId(UserGroup userGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_user_group SET master_group_id = ?, modified_by = ?, modified_time = NOW() WHERE id = ?"
            );
            ps.setString(1, userGroup.getMasterGroupId());
            ps.setString(2, userGroup.getModifiedBy());
            ps.setString(3, userGroup.getId());
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

    public List<UserGroup> getGroupListforMaster() {
        String sql = "SELECT id, code, name, master_group_id FROM cdars_user_group ORDER BY name ASC";
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserGroup userGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userGroup = new UserGroup();
                userGroup.setId(rs.getString("id"));
                userGroup.setCode(rs.getString("code"));
                userGroup.setMasterGroupId(rs.getString("master_group_id"));
                userGroup.setName(rs.getString("name"));
                userGroupList.add(userGroup);
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
        return userGroupList;
    }

    public List<UserGroup> getGroupListbyMasterGroupId(String masterGroupId) {
        String sql = "SELECT id, code, name, master_group_id FROM cdars_user_group WHERE master_group_id = '" + masterGroupId + "' ORDER BY name ASC";
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UserGroup userGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userGroup = new UserGroup();
                userGroup.setId(rs.getString("id"));
                userGroup.setCode(rs.getString("code"));
                userGroup.setMasterGroupId(rs.getString("master_group_id"));
                userGroup.setName(rs.getString("name"));
                userGroupList.add(userGroup);
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
        return userGroupList;
    }
}
