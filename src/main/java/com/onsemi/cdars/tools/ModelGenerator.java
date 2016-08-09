package com.onsemi.cdars.tools;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class ModelGenerator {

    public static void main(String[] args) {
        String table = "cdars_wh_shipping";
        String sql = "SELECT * FROM " + table + " LIMIT 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdars", "root", "root");
            if (conn != null) {
                String className = className(table);
                System.out.println("ClassName: " + className);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();
                String packageName = "package com.onsemi.cdars.model;";
                String classFileContent = packageName + "\n\npublic class " + className + " {\n\n";
                for (int i = 1; i <= count; i++) {
                    classFileContent += "\tprivate String " + capitalize(metaData.getColumnLabel(i)) + ";\n";
                }
                classFileContent += "\n";
                for (int i = 1; i <= count; i++) {
                    classFileContent += "\tpublic String get" + capitalizeAll(metaData.getColumnLabel(i)) + "() {\n"
                            + "\t\treturn " + capitalize(metaData.getColumnLabel(i)) + ";\n"
                            + "\t}\n\n"
                            + "\tpublic void set" + capitalizeAll(metaData.getColumnLabel(i)) + "(String " + capitalize(metaData.getColumnLabel(i)) + ") {\n"
                            + "\t\tthis." + capitalize(metaData.getColumnLabel(i)) + " = " + capitalize(metaData.getColumnLabel(i)) + ";\n"
                            + "\t}\n\n";
                }
                classFileContent += "}";
                String fileLocation = "D:\\New\\CDARS\\src\\main\\java\\com\\onsemi\\cdars\\model\\";
                FileUtils.writeStringToFile(new File(fileLocation + className + ".java"), classFileContent);
                rs.close();
                ps.close();
                conn.close();
            } else {
                System.out.println("Connection is NULL!");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String capitalize(String string) {
        String[] a = string.split("\\_");
        String b = "";
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                b += a[i];
            } else {
                b += StringUtils.capitalize(a[i]);
            }
        }
        return b;
    }

    private static String capitalizeAll(String string) {
        String[] a = string.split("\\_");
        String b = "";
        for (int i = 0; i < a.length; i++) {
            b += StringUtils.capitalize(a[i]);
        }
        return b;
    }
    
    private static String className(String string) {
        String[] a = string.split("\\_");
        String b = "";
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
            } else {
                b += StringUtils.capitalize(a[i]);
            }
        }
        return b;
    }
}
