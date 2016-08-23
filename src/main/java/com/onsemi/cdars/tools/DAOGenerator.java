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

public class DAOGenerator {

    public static void main(String[] args) {
        String table = "cdars_wh_mp_list";
        String sql = "SELECT * FROM " + table + " LIMIT 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdars", "root", "root");
            if (conn != null) {
                String className = className(table) + "DAO";
                String modelName = modelName(table);
                String modelClass = className(table);
                System.out.println("ClassName: " + className + " | Model Name: " + modelName);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();
                String packageName = "package com.onsemi.cdars.dao;";
                String imports = "import com.onsemi.cdars.db.DB;\n"
                        + "import java.sql.Connection;\n"
                        + "import java.sql.PreparedStatement;\n"
                        + "import java.sql.ResultSet;\n"
                        + "import java.sql.SQLException;\n"
                        + "import java.sql.Statement;\n"
                        + "import java.util.ArrayList;\n"
                        + "import java.util.List;\n"
                        + "import javax.sql.DataSource;\n"
                        + "import com.onsemi.cdars.model." + modelClass + ";\n"
                        + "import com.onsemi.cdars.tools.QueryResult;\n"
                        + "import org.slf4j.Logger;\n"
                        + "import org.slf4j.LoggerFactory;\n";
                //+ "import org.springframework.beans.factory.annotation.Autowired;\n"
                //+ "import org.springframework.stereotype.Repository;\n"
                //+ "import org.springframework.web.context.support.SpringBeanAutowiringSupport;";

                //ORIGINAL CODE - start
//                String classFileContent = packageName + "\n\n" + imports + "\n\n"
//                        //+ "@Repository\n"
//                        //+ "public class " + className + " extends SpringBeanAutowiringSupport {\n\n"
//                        + "public class " + className + " {\n\n"
//                        + "\tprivate static final Logger LOGGER = LoggerFactory.getLogger(" + className + ".class);\n"
//                        + "\tprivate Connection conn = null;\n\n";
//                        //+ "\t@Autowired\n"
//                        //+ "\tprivate DataSource dataSource;\n\n";

//                //Constructor
//                classFileContent += "\tpublic " + className + "(DataSource dataSource) {\n"
//                        + "\t\ttry {\n"
//                        + "\t\t\tthis.conn = dataSource.getConnection();\n"
//                        + "\t\t} catch (SQLException e) {\n"
//                        + "\t\t\tLOGGER.error(e.getMessage());\n"
//                        + "\t\t}\n"
//                        + "\t}\n"
//                        + "\n";
                //+ "\tpublic " + className + "(Connection conn) {\n"
                //+ "\t\tthis.conn = conn;\n"
                //+ "\t}\n\n";    
                //ORIGINAL CODE - end
                
                 //MODIFIED CODE - start
                String classFileContent = packageName + "\n\n" + imports + "\n\n"
                        //+ "@Repository\n"
                        //+ "public class " + className + " extends SpringBeanAutowiringSupport {\n\n"
                        + "public class " + className + " {\n\n"
                        + "\tprivate static final Logger LOGGER = LoggerFactory.getLogger(" + className + ".class);\n"
                        + "\tprivate final Connection conn;\n"
                        + "\tprivate final DataSource dataSource;\n\n";
                
                 //Constructor
                classFileContent += "\tpublic " + className + "() {\n"
                        + "\t\t\tDB db = new DB();\n"
                        + "\t\t\tthis.conn = db.getConnection();\n"
                        + "\t\t\tthis.dataSource = db.getDataSource();\n"
                        + "\t\t}\n"
                        + "\n";             
                 //MODIFIED CODE - end
                 
                //Insert
                classFileContent += "\tpublic QueryResult insert" + modelClass + "(" + modelClass + " " + modelName + ") {\n"
                        + "\t\tQueryResult queryResult = new QueryResult();\n"
                        + "\t\ttry {\n"
                        + "\t\t\tPreparedStatement ps = conn.prepareStatement(\n"
                        + "\t\t\t\t\"INSERT INTO " + table + " (";
                for (int i = 1; i <= count; i++) {
                    if (i == 1) {
                    } else if (i == count) {
                        classFileContent += metaData.getColumnLabel(i);
                    } else {
                        classFileContent += metaData.getColumnLabel(i) + ", ";
                    }
                }
                classFileContent += ") VALUES (";
                for (int i = 1; i <= count; i++) {
                    if (i == 1) {
                    } else if (i == count) {
                        classFileContent += "?";
                    } else {
                        classFileContent += "?,";
                    }
                }
                classFileContent += ")\", Statement.RETURN_GENERATED_KEYS\n"
                        + "\t\t\t);\n";
                for (int i = 1; i <= count; i++) {
                    if (i == 1) {
                    } else {
                        classFileContent += "\t\t\tps.setString(" + (i - 1) + ", " + modelName + ".get" + capitalizeAll(metaData.getColumnLabel(i)) + "());\n";
                    }
                }
                classFileContent += "\t\t\tqueryResult.setResult(ps.executeUpdate());\n"
                        + "\t\t\tResultSet rs = ps.getGeneratedKeys();\n"
                        + "\t\t\tif (rs.next()) {\n"
                        + "\t\t\t\tqueryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));\n"
                        + "\t\t\t}\n"
                        + "\t\t\trs.close();\n"
                        + "\t\t\tps.close();\n"
                        + "\t\t} catch (SQLException e) {\n"
                        + "\t\t\tqueryResult.setErrorMessage(e.getMessage());\n"
                        + "\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t} finally {\n"
                        + "\t\t\tif (conn != null) {\n"
                        + "\t\t\t\ttry {\n"
                        + "\t\t\t\t\tconn.close();\n"
                        + "\t\t\t\t} catch (SQLException e) {\n"
                        + "\t\t\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t\t\t}\n"
                        + "\t\t\t}\n"
                        + "\t\t}\n"
                        + "\t\treturn queryResult;\n"
                        + "\t}\n\n";
                //Update
                classFileContent += "\tpublic QueryResult update" + modelClass + "(" + modelClass + " " + modelName + ") {\n"
                        + "\t\tQueryResult queryResult = new QueryResult();\n"
                        + "\t\ttry {\n"
                        + "\t\t\tPreparedStatement ps = conn.prepareStatement(\n"
                        + "\t\t\t\t\"UPDATE " + table + " SET ";
                for (int i = 1; i <= count; i++) {
                    if (i == 1) {
                    } else if (i == count) {
                        classFileContent += metaData.getColumnLabel(i) + " = ?";
                    } else {
                        classFileContent += metaData.getColumnLabel(i) + " = ?, ";
                    }
                }
                classFileContent += " WHERE id = ?\"\n"
                        + "\t\t\t);\n";
                for (int i = 1; i <= count; i++) {
                    if (i == 1) {
                    } else {
                        classFileContent += "\t\t\tps.setString(" + (i - 1) + ", " + modelName + ".get" + capitalizeAll(metaData.getColumnLabel(i)) + "());\n";
                    }
                }
                classFileContent += "\t\t\tps.setString(" + (count) + ", " + modelName + ".getId());\n";
                classFileContent += "\t\t\tqueryResult.setResult(ps.executeUpdate());\n"
                        + "\t\t\tps.close();\n"
                        + "\t\t} catch (SQLException e) {\n"
                        + "\t\t\tqueryResult.setErrorMessage(e.getMessage());\n"
                        + "\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t} finally {\n"
                        + "\t\t\tif (conn != null) {\n"
                        + "\t\t\t\ttry {\n"
                        + "\t\t\t\t\tconn.close();\n"
                        + "\t\t\t\t} catch (SQLException e) {\n"
                        + "\t\t\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t\t\t}\n"
                        + "\t\t\t}\n"
                        + "\t\t}\n"
                        + "\t\treturn queryResult;\n"
                        + "\t}\n\n";
                //Delete
                classFileContent += "\tpublic QueryResult delete" + modelClass + "(String " + modelName + "Id) {\n"
                        + "\t\tQueryResult queryResult = new QueryResult();\n"
                        + "\t\ttry {\n"
                        + "\t\t\tPreparedStatement ps = conn.prepareStatement(\n"
                        + "\t\t\t\t\"DELETE FROM " + table + " WHERE id = '\" + " + modelName + "Id + \"'\"\n"
                        + "\t\t\t);\n"
                        + "\t\t\tqueryResult.setResult(ps.executeUpdate());\n"
                        + "\t\t\tps.close();\n"
                        + "\t\t} catch (SQLException e) {\n"
                        + "\t\t\tqueryResult.setErrorMessage(e.getMessage());\n"
                        + "\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t} finally {\n"
                        + "\t\t\tif (conn != null) {\n"
                        + "\t\t\t\ttry {\n"
                        + "\t\t\t\t\tconn.close();\n"
                        + "\t\t\t\t} catch (SQLException e) {\n"
                        + "\t\t\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t\t\t}\n"
                        + "\t\t\t}\n"
                        + "\t\t}\n"
                        + "\t\treturn queryResult;\n"
                        + "\t}\n\n";
                //Get
                classFileContent += "\tpublic " + modelClass + " get" + modelClass + "(String " + modelName + "Id) {\n"
                        + "\t\tString sql = \"SELECT * FROM " + table + " WHERE id = '\" + " + modelName + "Id + \"'\";\n"
                        + "\t\t" + modelClass + " " + modelName + " = null;\n"
                        + "\t\ttry {\n"
                        + "\t\t\tPreparedStatement ps = conn.prepareStatement(sql);\n"
                        + "\t\t\tResultSet rs = ps.executeQuery();\n"
                        + "\t\t\twhile (rs.next()) {\n"
                        + "\t\t\t\t" + modelName + " = new " + modelClass + "();\n";
                for (int i = 1; i <= count; i++) {
                    classFileContent += "\t\t\t\t" + modelName + ".set" + capitalizeAll(metaData.getColumnLabel(i)) + "(rs.getString(\"" + metaData.getColumnLabel(i) + "\"));\n";
                }
                classFileContent += "\t\t\t}\n"
                        + "\t\t\trs.close();\n"
                        + "\t\t\tps.close();\n"
                        + "\t\t} catch (SQLException e) {\n"
                        + "\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t} finally {\n"
                        + "\t\t\tif (conn != null) {\n"
                        + "\t\t\t\ttry {\n"
                        + "\t\t\t\t\tconn.close();\n"
                        + "\t\t\t\t} catch (SQLException e) {\n"
                        + "\t\t\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t\t\t}\n"
                        + "\t\t\t}\n"
                        + "\t\t}\n"
                        + "\t\treturn " + modelName + ";\n"
                        + "\t}\n\n";
                //List
                classFileContent += "\tpublic List<" + modelClass + "> get" + modelClass + "List() {\n"
                        + "\t\tString sql = \"SELECT * FROM " + table + " ORDER BY id ASC\";\n"
                        + "\t\tList<" + modelClass + "> " + modelName + "List = new ArrayList<" + modelClass + ">();\n"
                        + "\t\ttry {\n"
                        + "\t\t\tPreparedStatement ps = conn.prepareStatement(sql);\n"
                        + "\t\t\t" + modelClass + " " + modelName + ";\n"
                        + "\t\t\tResultSet rs = ps.executeQuery();\n"
                        + "\t\t\twhile (rs.next()) {\n"
                        + "\t\t\t\t" + modelName + " = new " + modelClass + "();\n";
                for (int i = 1; i <= count; i++) {
                    classFileContent += "\t\t\t\t" + modelName + ".set" + capitalizeAll(metaData.getColumnLabel(i)) + "(rs.getString(\"" + metaData.getColumnLabel(i) + "\"));\n";
                }
                classFileContent += "\t\t\t\t" + modelName + "List.add(" + modelName + ");\n"
                        + "\t\t\t}\n"
                        + "\t\t\trs.close();\n"
                        + "\t\t\tps.close();\n"
                        + "\t\t} catch (SQLException e) {\n"
                        + "\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t} finally {\n"
                        + "\t\t\tif (conn != null) {\n"
                        + "\t\t\t\ttry {\n"
                        + "\t\t\t\t\tconn.close();\n"
                        + "\t\t\t\t} catch (SQLException e) {\n"
                        + "\t\t\t\t\tLOGGER.error(e.getMessage());\n"
                        + "\t\t\t\t}\n"
                        + "\t\t\t}\n"
                        + "\t\t}\n"
                        + "\t\treturn " + modelName + "List;\n"
                        + "\t}\n";
                classFileContent += "}";
                String fileLocation = "D:\\New\\CDARS\\src\\main\\java\\com\\onsemi\\cdars\\dao\\";
                FileUtils.writeStringToFile(new File(fileLocation + className + ".java"), classFileContent);
                rs.close();
                ps.close();
                conn.close();
            } else {
                System.out.println("Connection is NULL!");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DAOGenerator.class.getName()).log(Level.SEVERE, null, ex);
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

    private static String modelName(String string) {
        String[] a = string.split("\\_");
        String b = "";
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
            } else if (i == 1) {
                b += a[i];
            } else {
                b += StringUtils.capitalize(a[i]);
            }
        }
        return b;
    }
}
