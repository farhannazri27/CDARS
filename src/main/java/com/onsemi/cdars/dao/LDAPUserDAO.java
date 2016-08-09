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
import com.onsemi.cdars.model.LDAPUser;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LDAPUserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LDAPUserDAO.class);
    //private final Connection conn;
    private final DataSource dataSource;

    public LDAPUserDAO() {
        DB db = new DB();
        //this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insert(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "INSERT INTO cdars_user_ldap (login_id, oncid, firstname, lastname, email, title, group_id, is_active, created_by, created_time) VALUES (?,?,?,?,?,?,?,'1',?,NOW())";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Long result = queryRunner.insert(
                    sql,
                    new ScalarHandler<Long>(),
                    user.getLoginId(),
                    user.getOncid(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getTitle(),
                    user.getGroupId(),
                    user.getCreatedBy()
            );
            queryResult.setResult(result.intValue());
            queryResult.setGeneratedKey(result.toString());
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult update(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE cdars_user_ldap SET oncid = ?, firstname = ?, lastname = ?, email = ?, title = ?, group_id = ?, is_active = ?, modified_by = ?, modified_time = NOW() WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    user.getOncid(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getTitle(),
                    user.getGroupId(),
                    user.getIsActive(),
                    user.getModifiedBy(),
                    user.getId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateGroup(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE cdars_user_ldap SET group_id = ?, modified_by = ?, modified_time = NOW() WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    user.getGroupId(),
                    user.getModifiedBy(),
                    user.getId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult delete(String id) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "DELETE FROM cdars_user_ldap WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    id
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public LDAPUser get(String id) {
        LDAPUser user = new LDAPUser();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name FROM cdars_user_ldap u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id) "
                + "WHERE u.id = ?";
        try {
            ResultSetHandler<LDAPUser> h = new ResultSetHandler<LDAPUser>() {
                @Override
                public LDAPUser handle(ResultSet rs) throws SQLException {
                    LDAPUser user = new LDAPUser();
                    while (rs.next()) {
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                    }
                    return user;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            user = run.query(sql, h, id);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return user;
    }
    
    public LDAPUser getByLoginId(String loginId) {
        LDAPUser user = new LDAPUser();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name FROM cdars_user_ldap u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id) "
                + "WHERE u.login_id = ?";
        try {
            ResultSetHandler<LDAPUser> h = new ResultSetHandler<LDAPUser>() {
                @Override
                public LDAPUser handle(ResultSet rs) throws SQLException {
                    LDAPUser user = new LDAPUser();
                    while (rs.next()) {
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                    }
                    return user;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            user = run.query(sql, h, loginId);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return user;
    }
    
    public List<LDAPUser> list() {
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name FROM cdars_user_ldap u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id)";
        try {
            ResultSetHandler<List<LDAPUser>> h = new ResultSetHandler<List<LDAPUser>>() {
                @Override
                public List<LDAPUser> handle(ResultSet rs) throws SQLException {
                    List<LDAPUser> list = new ArrayList<LDAPUser>();
                    while (rs.next()) {
                        LDAPUser user = new LDAPUser();
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                        list.add(user);
                    }
                    return list;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            list = run.query(sql, h);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return list;
    }
    
    public List<LDAPUser> listByGroupId(String groupId) {
        String filterGroupId = "";
        if (!groupId.equals("")) {
            filterGroupId = "WHERE u.group_id = '" + groupId + "' ";
        }
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name FROM cdars_user_ldap u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id) "
                + filterGroupId
                + "ORDER BY u.firstname";
        try {
            ResultSetHandler<List<LDAPUser>> h = new ResultSetHandler<List<LDAPUser>>() {
                @Override
                public List<LDAPUser> handle(ResultSet rs) throws SQLException {
                    List<LDAPUser> list = new ArrayList<LDAPUser>();
                    while (rs.next()) {
                        LDAPUser user = new LDAPUser();
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                        list.add(user);
                    }
                    return list;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            list = run.query(sql, h);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return list;
    }

    /*public LDAPUser getLDAPUser(String id) {
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name FROM cdars_user_ldap u "
                + "LEFT JOIN cdars_user_group ug ON (u.group_id = ug.id) "
                + "WHERE u.id = '" + id + "'";
        LDAPUser user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new LDAPUser();
                user.setId(rs.getString("id"));
                user.setLoginId(rs.getString("login_id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setGroupCode(rs.getString("group_code"));
                user.setGroupName(rs.getString("group_name"));
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
    }*/

    /*public LDAPUser getLDAPUserByLoginId(String loginId) {
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name, up.fullname, up.email FROM cdars_user_ldap u "
                + "LEFT JOIN cdars_user_ldap_group ug ON (u.group_id = ug.id) LEFT JOIN cdars_user_ldap_profile up ON (u.id = up.user_id) "
                + "WHERE u.login_id = '" + loginId + "' ORDER BY up.fullname";
        LDAPUser user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new LDAPUser(
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
    }*/

    /*public List<LDAPUser> getLDAPUserList(String groupId) {
        String filterGroupId = "";
        if (!groupId.equals("")) {
            //filterGroupId = "u.group_id = '" + groupId + "' AND";
            filterGroupId = "WHERE u.group_id = '" + groupId + "' ";
        }
        String sql = "SELECT u.*, ug.code AS group_code, ug.name AS group_name, up.fullname, up.email FROM cdars_user_ldap u "
                + "LEFT JOIN cdars_user_ldap_group ug ON (u.group_id = ug.id) "
                + "LEFT JOIN cdars_user_ldap_profile up ON (u.id = up.user_id) "
                + filterGroupId
                + "ORDER BY up.fullname";
        List<LDAPUser> userList = new ArrayList<LDAPUser>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LDAPUser user;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new LDAPUser(
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
    }*/

    /*public Integer getCountByGroupId(String groupId) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM cdars_user_ldap WHERE group_id = '" + groupId + "'";
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
    }*/
}
