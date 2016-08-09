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

public class ControllerGenerator {

    public static void main(String[] args) {
        String table = "cdars_wh_request";
        String sql = "SELECT * FROM " + table + " LIMIT 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdars", "root", "root");
            if (conn != null) {
                String className = className(table) + "Controller";
                String modelName = modelName(table);
                String modelClass = className(table);
                System.out.println("ClassName: " + className + " | Model Name: " + modelName);
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();
                String packageName = "package com.onsemi.cdars.controller;";
                String imports = "import java.io.UnsupportedEncodingException;\n"
                        + "import java.net.URLEncoder;\n"
                        + "import java.util.List;\n"
                        + "import java.util.Locale;\n"
                        + "import javax.servlet.http.HttpServletRequest;\n"
                        + "import com.onsemi.cdars.dao." + modelClass + "DAO;\n"
                        + "import com.onsemi.cdars.model." + modelClass + ";\n"
                        + "import com.onsemi.cdars.model.UserSession;\n"
                        + "import com.onsemi.cdars.tools.QueryResult;\n"
                        + "import javax.servlet.ServletContext;\n"
                        + "import org.slf4j.Logger;\n"
                        + "import org.slf4j.LoggerFactory;\n"
                        + "import org.springframework.beans.factory.annotation.Autowired;\n"
                        + "import org.springframework.context.MessageSource;\n"
                        + "import org.springframework.stereotype.Controller;\n"
                        + "import org.springframework.ui.Model;\n"
                        + "import org.springframework.web.bind.annotation.ModelAttribute;\n"
                        + "import org.springframework.web.bind.annotation.PathVariable;\n"
                        + "import org.springframework.web.bind.annotation.RequestMapping;\n"
                        + "import org.springframework.web.bind.annotation.RequestMethod;\n"
                        + "import org.springframework.web.bind.annotation.RequestParam;\n"
                        + "import org.springframework.web.bind.annotation.SessionAttributes;\n"
                        + "import org.springframework.web.servlet.ModelAndView;\n"
                        + "import org.springframework.web.servlet.mvc.support.RedirectAttributes;";
                String classFileContent = packageName + "\n\n" + imports + "\n\n"
                        + "@Controller\n"
                        + "@RequestMapping(value = \"/" + modelName + "\")\n"
                        + "@SessionAttributes({\"userSession\"})"
                        + "public class " + className + " {\n\n"
                        + "\tprivate static final Logger LOGGER = LoggerFactory.getLogger(" + className + ".class);\n"
                        + "\tString[] args = {};\n\n"
                        + "\t@Autowired\n"
                        + "\tprivate MessageSource messageSource;\n\n"
                        + "\t@Autowired\n"
                        + "\tServletContext servletContext;\n\n";
                //List
                classFileContent += "\t@RequestMapping(value = \"\", method = RequestMethod.GET)\n"
                        + "\tpublic String " + modelName + "(\n"
                        + "\t\t\tModel model\n"
                        + "\t) {\n"
                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO();\n"
                        + "\t\tList<" + modelClass + "> " + modelName + "List = " + modelName + "DAO.get" + modelClass + "List();\n"
                        + "\t\tmodel.addAttribute(\"" + modelName + "List\", " + modelName + "List);\n"
                        + "\t\treturn \"" + modelName + "/" + modelName + "\";\n"
                        + "\t}\n\n";
                //Add
                classFileContent += "\t@RequestMapping(value = \"/add\", method = RequestMethod.GET)\n"
                        + "\tpublic String add(Model model) {\n"
                        + "\t\treturn \"" + modelName + "/add\";\n"
                        + "\t}\n\n";
                //Save
                classFileContent += "\t@RequestMapping(value = \"/save\", method = RequestMethod.POST)\n"
                        + "\tpublic String save(\n"
                        + "\t\t\tModel model,\n"
                        + "\t\t\tLocale locale,\n"
                        + "\t\t\tRedirectAttributes redirectAttrs,\n"
                        + "\t\t\t@ModelAttribute UserSession userSession,\n";
                for (int i = 1; i <= count; i++) {
                    if (i == 1) {
                    } else if (i == count) {
                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + "\n";
                    } else {
                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + ",\n";
                    }
                }
                classFileContent += "\t) {\n"
                        + "\t\t" + modelClass + " " + modelName + " = new " + modelClass + "();\n";
                for (int i = 1; i <= count; i++) {
                    if (i == 1) {
                    } else {
                        classFileContent += "\t\t" + modelName + ".set" + capitalizeAll(metaData.getColumnLabel(i)) + "(" + capitalize(metaData.getColumnLabel(i)) + ");\n";
                    }
                }
                classFileContent += "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO();\n"
                        + "\t\tQueryResult queryResult = " + modelName + "DAO.insert" + modelClass + "(" + modelName + ");\n"
                        + "\t\targs = new String[1];\n"
                        + "\t\targs[0] = " + capitalize(metaData.getColumnLabel(2)) + " + \" - \" + " + capitalize(metaData.getColumnLabel(3)) + ";\n"
                        + "\t\tif (queryResult.getGeneratedKey().equals(\"0\")) {\n"
                        + "\t\t\tmodel.addAttribute(\"error\", messageSource.getMessage(\"general.label.save.error\", args, locale));\n"
                        + "\t\t\tmodel.addAttribute(\"" + modelName + "\", " + modelName + ");\n"
                        + "\t\t\treturn \"" + modelName + "/add\";\n"
                        + "\t\t} else {\n"
                        + "\t\t\tredirectAttrs.addFlashAttribute(\"success\", messageSource.getMessage(\"general.label.save.success\", args, locale));\n"
                        + "\t\t\treturn \"redirect:/" + modelName + "/edit/\" + queryResult.getGeneratedKey();\n"
                        + "\t\t}\n"
                        + "\t}\n\n";
                //Edit
                classFileContent += "\t@RequestMapping(value = \"/edit/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
                        + "\tpublic String edit(\n"
                        + "\t\t\tModel model,\n"
                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
                        + "\t) {\n"
                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO();\n"
                        + "\t\t" + modelClass + " " + modelName + " = " + modelName + "DAO.get" + modelClass + "(" + modelName + "Id);\n"
                        + "\t\tmodel.addAttribute(\"" + modelName + "\", " + modelName + ");\n"
                        + "\t\treturn \"" + modelName + "/edit\";\n"
                        + "\t}\n\n";
                //Update
                classFileContent += "\t@RequestMapping(value = \"/update\", method = RequestMethod.POST)\n"
                        + "\tpublic String update(\n"
                        + "\t\t\tModel model,\n"
                        + "\t\t\tLocale locale,\n"
                        + "\t\t\tRedirectAttributes redirectAttrs,\n"
                        + "\t\t\t@ModelAttribute UserSession userSession,\n";
                for (int i = 1; i <= count; i++) {
                    if (i == count) {
                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + "\n";
                    } else {
                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + ",\n";
                    }
                }
                classFileContent += "\t) {\n"
                        + "\t\t" + modelClass + " " + modelName + " = new " + modelClass + "();\n";
                for (int i = 1; i <= count; i++) {
                    classFileContent += "\t\t" + modelName + ".set" + capitalizeAll(metaData.getColumnLabel(i)) + "(" + capitalize(metaData.getColumnLabel(i)) + ");\n";
                }
                classFileContent += "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO();\n"
                        + "\t\tQueryResult queryResult = " + modelName + "DAO.update" + modelClass + "(" + modelName + ");\n"
                        + "\t\targs = new String[1];\n"
                        + "\t\targs[0] = " + capitalize(metaData.getColumnLabel(2)) + " + \" - \" + " + capitalize(metaData.getColumnLabel(3)) + ";\n"
                        + "\t\tif (queryResult.getResult() == 1) {\n"
                        + "\t\t\tredirectAttrs.addFlashAttribute(\"success\", messageSource.getMessage(\"general.label.update.success\", args, locale));\n"
                        + "\t\t} else {\n"
                        + "\t\t\tredirectAttrs.addFlashAttribute(\"error\", messageSource.getMessage(\"general.label.update.error\", args, locale));\n"
                        + "\t\t}\n"
                        + "\t\treturn \"redirect:/" + modelName + "/edit/\" + id;\n"
                        + "\t}\n\n";
                //Delete
                classFileContent += "\t@RequestMapping(value = \"/delete/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
                        + "\tpublic String delete(\n"
                        + "\t\t\tModel model,\n"
                        + "\t\t\tLocale locale,\n"
                        + "\t\t\tRedirectAttributes redirectAttrs,\n"
                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
                        + "\t) {\n"
                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO();\n"
                        + "\t\t" + modelClass + " " + modelName + " = " + modelName + "DAO.get" + modelClass + "(" + modelName + "Id);\n"
                        + "\t\t" + modelName + "DAO = new " + modelClass + "DAO();\n"
                        + "\t\tQueryResult queryResult = " + modelName + "DAO.delete" + modelClass + "(" + modelName + "Id);\n"
                        + "\t\targs = new String[1];\n"
                        + "\t\targs[0] = " + modelName + ".get" + capitalizeAll(metaData.getColumnLabel(2)) + "() + \" - \" + " + modelName + ".get" + capitalizeAll(metaData.getColumnLabel(3)) + "();\n"
                        + "\t\tif (queryResult.getResult() == 1) {\n"
                        + "\t\t\tredirectAttrs.addFlashAttribute(\"success\", messageSource.getMessage(\"general.label.delete.success\", args, locale));\n"
                        + "\t\t} else {\n"
                        + "\t\t\tredirectAttrs.addFlashAttribute(\"error\", messageSource.getMessage(\"general.label.delete.error\", args, locale));\n"
                        + "\t\t}\n"
                        + "\t\treturn \"redirect:/" + modelName + "\";\n"
                        + "\t}\n\n";
                //View
                classFileContent += "\t@RequestMapping(value = \"/view/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
                        + "\tpublic String view(\n"
                        + "\t\t\tModel model, \n"
                        + "\t\t\tHttpServletRequest request, \n"
                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
                        + "\t) throws UnsupportedEncodingException {\n"
                        + "\t\tString pdfUrl = URLEncoder.encode(request.getContextPath() + \"/" + modelName + "/view" + modelClass + "Pdf/\" + " + modelName + "Id, \"UTF-8\");\n"
                        + "\t\tString backUrl = servletContext.getContextPath() + \"/" + modelName + "\";\n"
                        + "\t\tmodel.addAttribute(\"pdfUrl\", pdfUrl);\n"
                        + "\t\tmodel.addAttribute(\"backUrl\", backUrl);\n"
                        + "\t\tmodel.addAttribute(\"pageTitle\", \"general.label." + modelName + "\");\n"
                        + "\t\treturn \"pdf/viewer\";\n"
                        + "\t}\n\n";
                //ViewPdf
                classFileContent += "\t@RequestMapping(value = \"/view" + modelClass + "Pdf/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
                        + "\tpublic ModelAndView view" + modelClass + "Pdf(\n"
                        + "\t\t\tModel model, \n"
                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
                        + "\t) {\n"
                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO();\n"
                        + "\t\t" + modelClass + " " + modelName + " = " + modelName + "DAO.get" + modelClass + "(" + modelName + "Id);\n"
                        + "\t\treturn new ModelAndView(\"" + modelName + "Pdf\", \"" + modelName + "\", " + modelName + ");\n"
                        + "\t}\n";
                classFileContent += "}";
                System.out.println(classFileContent);
                String fileLocation = "D:\\New\\CDARS\\src\\main\\java\\com\\onsemi\\cdars\\controller\\";
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
    //ORIGINAL CODE - Start
//    public static void main(String[] args) {
//        String table = "cdars_ionic_limit";
//        String sql = "SELECT * FROM " + table + " LIMIT 1";
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = null;
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdars", "root", "root");
//            if (conn != null) {
//                String className = className(table) + "Controller";
//                String modelName = modelName(table);
//                String modelClass = className(table);
//                System.out.println("ClassName: " + className + " | Model Name: " + modelName);
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery();
//                ResultSetMetaData metaData = rs.getMetaData();
//                int count = metaData.getColumnCount();
//                String packageName = "package com.onsemi.cdars.controller;";
//                String imports = "import java.io.UnsupportedEncodingException;\n"
//                        + "import java.net.URLEncoder;\n"
//                        + "import java.util.List;\n"
//                        + "import java.util.Locale;\n"
//                        + "import javax.servlet.http.HttpServletRequest;\n"
//                        + "import com.onsemi.cdars.dao." + modelClass + "DAO;\n"
//                        + "import com.onsemi.cdars.model." + modelClass + ";\n"
//                        + "import com.onsemi.cdars.model.UserSession;\n"
//                        + "import com.onsemi.cdars.tools.QueryResult;\n"
//                        + "import org.slf4j.Logger;\n"
//                        + "import org.slf4j.LoggerFactory;\n"
//                        + "import org.springframework.beans.factory.annotation.Autowired;\n"
//                        + "import org.springframework.context.MessageSource;\n"
//                        + "import org.springframework.stereotype.Controller;\n"
//                        + "import org.springframework.ui.Model;\n"
//                        + "import org.springframework.web.bind.annotation.ModelAttribute;\n"
//                        + "import org.springframework.web.bind.annotation.PathVariable;\n"
//                        + "import org.springframework.web.bind.annotation.RequestMapping;\n"
//                        + "import org.springframework.web.bind.annotation.RequestMethod;\n"
//                        + "import org.springframework.web.bind.annotation.RequestParam;\n"
//                        + "import org.springframework.web.bind.annotation.SessionAttributes;\n"
//                        + "import org.springframework.web.servlet.ModelAndView;\n"
//                        + "import org.springframework.web.servlet.mvc.support.RedirectAttributes;";
//                String classFileContent = packageName + "\n\n" + imports + "\n\n"
//                        + "@Controller\n"
//                        + "@RequestMapping(value = \"/" + modelName + "\")\n"
//                        + "@SessionAttributes({\"userSession\"})"
//                        + "public class " + className + " {\n\n"
//                        + "\tprivate static final Logger LOGGER = LoggerFactory.getLogger(" + className + ".class);\n"
//                        + "\tString[] args = {};\n\n"
//                        + "\t@Autowired\n"
//                        + "\tprivate MessageSource messageSource;\n\n"
//                        + "\t@Autowired\n"
//                        + "\tprivate DataSource dataSource;\n\n";
//                //List
//                classFileContent += "\t@RequestMapping(value = \"list\", method = RequestMethod.GET)\n"
//                        + "\tpublic String " + modelName + "(\n"
//                        + "\t\t\tModel model\n"
//                        + "\t) {\n"
//                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO(dataSource);\n"
//                        + "\t\tList<" + modelClass + "> " + modelName + "List = " + modelName + "DAO.get" + modelClass + "List();\n"
//                        + "\t\tmodel.addAttribute(\"" + modelName + "List\", " + modelName + "List);\n"
//                        + "\t\treturn \"" + modelName + "/" + modelName + "\";\n"
//                        + "\t}\n\n";
//                //Add
//                classFileContent += "\t@RequestMapping(value = \"/add\", method = RequestMethod.GET)\n"
//                        + "\tpublic String add(Model model) {\n"
//                        + "\t\treturn \"" + modelName + "/add\";\n"
//                        + "\t}\n\n";
//                //Save
//                classFileContent += "\t@RequestMapping(value = \"/save\", method = RequestMethod.POST)\n"
//                        + "\tpublic String save(\n"
//                        + "\t\t\tModel model,\n"
//                        + "\t\t\tLocale locale,\n"
//                        + "\t\t\tRedirectAttributes redirectAttrs,\n"
//                        + "\t\t\t@ModelAttribute UserSession userSession,\n";
//                for (int i = 1; i <= count; i++) {
//                    if (i == 1) {
//                    } else if (i == count) {
//                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + "\n";
//                    } else {
//                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + ",\n";
//                    }
//                }
//                classFileContent += "\t) {\n"
//                        + "\t\t" + modelClass + " " + modelName + " = new " + modelClass + "();\n";
//                for (int i = 1; i <= count; i++) {
//                    if (i == 1) {
//                    } else {
//                        classFileContent += "\t\t" + modelName + ".set" + capitalizeAll(metaData.getColumnLabel(i)) + "(" + capitalize(metaData.getColumnLabel(i)) + ");\n";
//                    }
//                }
//                classFileContent += "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO(dataSource);\n"
//                        + "\t\tQueryResult queryResult = " + modelName + "DAO.insert" + modelClass + "(" + modelName + ");\n"
//                        + "\t\targs = new String[1];\n"
//                        + "\t\targs[0] = " + capitalize(metaData.getColumnLabel(2)) + " + \" - \" + " + capitalize(metaData.getColumnLabel(3)) + ";\n"
//                        + "\t\tif (queryResult.getGeneratedKey().equals(\"0\")) {\n"
//                        + "\t\t\tmodel.addAttribute(\"error\", messageSource.getMessage(\"general.label.save.error\", args, locale));\n"
//                        + "\t\t\tmodel.addAttribute(\"" + modelName + "\", " + modelName + ");\n"
//                        + "\t\t\treturn \"" + modelName + "/add\";\n"
//                        + "\t\t} else {\n"
//                        + "\t\t\tredirectAttrs.addFlashAttribute(\"success\", messageSource.getMessage(\"general.label.save.success\", args, locale));\n"
//                        + "\t\t\treturn \"redirect:/" + modelName + "/edit/\" + queryResult.getGeneratedKey();\n"
//                        + "\t\t}\n"
//                        + "\t}\n\n";
//                //Edit
//                classFileContent += "\t@RequestMapping(value = \"/edit/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
//                        + "\tpublic String edit(\n"
//                        + "\t\t\tModel model,\n"
//                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
//                        + "\t) {\n"
//                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO(dataSource);\n"
//                        + "\t\t" + modelClass + " " + modelName + " = " + modelName + "DAO.get" + modelClass + "(" + modelName + "Id);\n"
//                        + "\t\tmodel.addAttribute(\"" + modelName + "\", " + modelName + ");\n"
//                        + "\t\treturn \"" + modelName + "/edit\";\n"
//                        + "\t}\n\n";
//                //Update
//                classFileContent += "\t@RequestMapping(value = \"/update\", method = RequestMethod.POST)\n"
//                        + "\tpublic String update(\n"
//                        + "\t\t\tModel model,\n"
//                        + "\t\t\tLocale locale,\n"
//                        + "\t\t\tRedirectAttributes redirectAttrs,\n"
//                        + "\t\t\t@ModelAttribute UserSession userSession,\n";
//                for (int i = 1; i <= count; i++) {
//                    if (i == count) {
//                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + "\n";
//                    } else {
//                        classFileContent += "\t\t\t@RequestParam(required = false) String " + capitalize(metaData.getColumnLabel(i)) + ",\n";
//                    }
//                }
//                classFileContent += "\t) {\n"
//                        + "\t\t" + modelClass + " " + modelName + " = new " + modelClass + "();\n";
//                for (int i = 1; i <= count; i++) {
//                    classFileContent += "\t\t" + modelName + ".set" + capitalizeAll(metaData.getColumnLabel(i)) + "(" + capitalize(metaData.getColumnLabel(i)) + ");\n";
//                }
//                classFileContent += "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO(dataSource);\n"
//                        + "\t\tQueryResult queryResult = " + modelName + "DAO.update" + modelClass + "(" + modelName + ");\n"
//                        + "\t\targs = new String[1];\n"
//                        + "\t\targs[0] = " + capitalize(metaData.getColumnLabel(2)) + " + \" - \" + " + capitalize(metaData.getColumnLabel(3)) + ";\n"
//                        + "\t\tif (queryResult.getResult() == 1) {\n"
//                        + "\t\t\tredirectAttrs.addFlashAttribute(\"success\", messageSource.getMessage(\"general.label.update.success\", args, locale));\n"
//                        + "\t\t} else {\n"
//                        + "\t\t\tredirectAttrs.addFlashAttribute(\"error\", messageSource.getMessage(\"general.label.update.error\", args, locale));\n"
//                        + "\t\t}\n"
//                        + "\t\treturn \"redirect:/" + modelName + "/edit/\" + id;\n"
//                        + "\t}\n\n";
//                //Delete
//                classFileContent += "\t@RequestMapping(value = \"/delete/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
//                        + "\tpublic String delete(\n"
//                        + "\t\t\tModel model,\n"
//                        + "\t\t\tLocale locale,\n"
//                        + "\t\t\tRedirectAttributes redirectAttrs,\n"
//                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
//                        + "\t) {\n"
//                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO(dataSource);\n"
//                        + "\t\t" + modelClass + " " + modelName + " = " + modelName+ "DAO.get" + modelClass + "(" + modelName + "Id);\n"
//                        + "\t\t" + modelName + "DAO = new " + modelClass + "DAO(dataSource);\n"
//                        + "\t\tQueryResult queryResult = " + modelName + "DAO.delete" + modelClass + "(" + modelName + "Id);\n"
//                        + "\t\targs = new String[1];\n"
//                        + "\t\targs[0] = " + modelName + ".get" + capitalizeAll(metaData.getColumnLabel(2)) + "() + \" - \" + " + modelName + ".get" + capitalizeAll(metaData.getColumnLabel(3)) + "();\n"
//                        + "\t\tif (queryResult.getResult() == 1) {\n"
//                        + "\t\t\tredirectAttrs.addFlashAttribute(\"success\", messageSource.getMessage(\"general.label.delete.success\", args, locale));\n"
//                        + "\t\t} else {\n"
//                        + "\t\t\tredirectAttrs.addFlashAttribute(\"error\", messageSource.getMessage(\"general.label.delete.error\", args, locale));\n"
//                        + "\t\t}\n"
//                        + "\t\treturn \"redirect:/" + modelName + "\";\n"
//                        + "\t}\n\n";
//                //View
//                classFileContent += "\t@RequestMapping(value = \"/view/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
//                        + "\tpublic String view(\n"
//                        + "\t\t\tModel model, \n"
//                        + "\t\t\tHttpServletRequest request, \n"
//                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
//                        + "\t) throws UnsupportedEncodingException {\n"
//                        + "\t\tString pdfUrl = URLEncoder.encode(request.getContextPath() + \"/" + modelName + "/view" + modelClass + "Pdf/\" + " + modelName + "Id, \"UTF-8\");\n"
//                        + "\t\tmodel.addAttribute(\"pdfUrl\", pdfUrl);\n"
//                        + "\t\tmodel.addAttribute(\"pageTitle\", \"general.label." + modelName + "\");\n"
//                        + "\t\treturn \"pdf/viewer\";\n"
//                        + "\t}\n\n";
//                //ViewPdf
//                classFileContent += "\t@RequestMapping(value = \"/view" + modelClass + "Pdf/{" + modelName + "Id}\", method = RequestMethod.GET)\n"
//                        + "\tpublic ModelAndView view" + modelClass + "Pdf(\n"
//                        + "\t\t\tModel model, \n"
//                        + "\t\t\t@PathVariable(\"" + modelName + "Id\") String " + modelName + "Id\n"
//                        + "\t) {\n"
//                        + "\t\t" + modelClass + "DAO " + modelName + "DAO = new " + modelClass + "DAO(dataSource);\n"
//                        + "\t\t" + modelClass + " " + modelName + " = " + modelName + "DAO.get" + modelClass + "(" + modelName + "Id);\n"
//                        + "\t\treturn new ModelAndView(\"" + modelName + "Pdf\", \"" + modelName + "\", " + modelName + ");\n"
//                        + "\t}\n";
//                classFileContent += "}";
//                System.out.println(classFileContent);
//                String fileLocation = "D:\\New\\CDARS\\src\\main\\java\\com\\onsemi\\cdars\\controller\\";
//                FileUtils.writeStringToFile(new File(fileLocation + className + ".java"), classFileContent);
//                rs.close();
//                ps.close();
//                conn.close();
//            } else {
//                System.out.println("Connection is NULL!");
//            }
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DAOGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DAOGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private static String capitalize(String string) {
//        String[] a = string.split("\\_");
//        String b = "";
//        for (int i = 0; i < a.length; i++) {
//            if (i == 0) {
//                b += a[i];
//            } else {
//                b += StringUtils.capitalize(a[i]);
//            }
//        }
//        return b;
//    }
//
//    private static String capitalizeAll(String string) {
//        String[] a = string.split("\\_");
//        String b = "";
//        for (int i = 0; i < a.length; i++) {
//            b += StringUtils.capitalize(a[i]);
//        }
//        return b;
//    }
//
//    private static String className(String string) {
//        String[] a = string.split("\\_");
//        String b = "";
//        for (int i = 0; i < a.length; i++) {
//            if (i == 0) {
//            } else {
//                b += StringUtils.capitalize(a[i]);
//            }
//        }
//        return b;
//    }
//
//    private static String modelName(String string) {
//        String[] a = string.split("\\_");
//        String b = "";
//        for (int i = 0; i < a.length; i++) {
//            if (i == 0) {
//            } else if (i == 1) {
//                b += a[i];
//            }else {
//                b += StringUtils.capitalize(a[i]);
//            }
//        }
//        return b;
//    }

    //ORIGINAL CODE - End
}
