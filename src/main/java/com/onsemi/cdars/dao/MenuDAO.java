package com.onsemi.cdars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.cdars.db.DB;
import com.onsemi.cdars.model.Menu;

public class MenuDAO {

    private Connection conn = null;

    public MenuDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
    }

    public MenuDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Menu> getMenuAccess(String parentCode, String currentPath, String groupId) {
        String sql = "SELECT m.parent_code, m.code, m.name, m.url_path, m.target, m.icon, m.label_key, "
                + "IF(url_path = \"" + currentPath + "\", \"class=\\\"active\\\"\", \"\") AS class_active FROM cdars_menu m, cdars_menu_access ma "
                + "WHERE ma.group_id = '" + groupId + "' AND m.id = ma.menu_id AND m.parent_code='" + parentCode + "' "
                + "AND m.is_active = '1' AND m.is_enabled = '1' ORDER BY m.sequence";
        //System.out.println(sql);
        List<Menu> menuList = new ArrayList<Menu>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Menu menu;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                menu = new Menu(
                        rs.getString("parent_code"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("url_path"),
                        rs.getString("target"),
                        rs.getString("icon"),
                        rs.getString("label_key"),
                        rs.getString("class_active")
                );
                menuList.add(menu);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return menuList;
    }
    
    public List<Menu> getMenuList(String parentCode) {
        String sql = "SELECT id, parent_code, code, name, icon, label_key FROM cdars_menu m WHERE m.parent_code='" + parentCode + "' ORDER BY m.sequence ASC";
        List<Menu> menuList = new ArrayList<Menu>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Menu menu;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                menu = new Menu();
                menu.setId(rs.getString("id"));
                menu.setParentCode(rs.getString("parent_code"));
                menu.setCode(rs.getString("code"));
                menu.setName(rs.getString("name"));
                menu.setIcon(rs.getString("icon"));
                menu.setLabelKey(rs.getString("label_key"));
                menuList.add(menu);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return menuList;
    }
    
    public Boolean menuExist(String path) {
        String sql = "SELECT id FROM cdars_menu m WHERE m.url_path='" + path + "'";
        Boolean exist = false;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                exist = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exist;
    }
}
