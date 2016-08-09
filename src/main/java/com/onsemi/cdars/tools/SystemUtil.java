package com.onsemi.cdars.tools;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;

public class SystemUtil {

    public static String nullToEmptyString(String string) {
        if (string == null) {
            return "";
        } else {
            return string;
        }
    }

    public static String[] nullToEmptyString(String[] string) {
        if (string == null) {
            return new String[0];
        } else {
            return string;
        }
    }

    public static String nullToZero(String string) {
        if (string == null) {
            return "0";
        } else {
            if (string.equals("on")) {
                return "1";
            } else {
                return string;
            }
        }
    }

    public static String emptyStringToNull(String string) {
        if (string == null) {
            return null;
        } else {
            if (string.equals("")) {
                return null;
            } else {
                return string;
            }
        }
    }

    public static String nullToNullString(String string) {
        if (string == null) {
            return "NULL";
        } else {
            return string;
        }
    }

    public static String getSelectedYear(String year) {
        if (year == null) {
            Integer selectedYear = Calendar.getInstance().get(Calendar.YEAR);// - 1;
            return selectedYear.toString();
        } else {
            return year;
        }
    }

    public static String idListToINString(String[] idList) {
        String in = "";
        for (int i = 0; i < idList.length; i++) {
            if (!idList[i].equals("")) {
                in += "'" + idList[i] + "'";
                if ((idList.length - 1) != i) {
                    in += ",";
                }
            }
        }
        return in;
    }

    public static String randomCalendarLabelColor() {
        Random r = new Random();
        int Low = 0;
        int High = 9;
        int R = r.nextInt(High - Low) + Low;
        String[] label = new String[10];
        label[0] = "label-success";
        label[1] = "label-danger";
        label[2] = "label-info";
        label[3] = "label-warning";
        label[4] = "label-primary";
        label[5] = "label-secondary";
        label[6] = "label-important";
        label[7] = "label-tertiary";
        label[8] = "label-inverse";
        label[9] = "label-default";
        return label[R];
    }

    public static String getCurrentYear() {
        Integer selectedYear = Calendar.getInstance().get(Calendar.YEAR);// - 1;
        return selectedYear.toString();
    }

    public static String amount(Object object) {
        return String.format("%,.2f", object);
    }

    public static String twoDecimal(Object object) {
        return String.format("%.2f", object);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static String serverUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        String base = url.substring(0, url.length() - uri.length()) + ctx;
        return base;
    }
    
     public static void delete(File file) {
        if (file.isDirectory()) {
            //directory is empty, then delete it
            if (file.list().length == 0) {
                file.delete();
                System.out.println("Directory is deleted : " + file.getAbsolutePath());
            } else {
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : " + file.getAbsolutePath());
                }
            }
        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public static Boolean forceDelete(String path) {
        System.gc();
        File file = new File(path);
        file.setWritable(true);
        return FileDeleteStrategy.FORCE.deleteQuietly(file);
    }

    public static void forceDeleteDirectory(String path) {
        System.gc();
        File file = new File(path);
        file.setWritable(true);
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException ex) {
            Logger.getLogger(SystemUtil.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }
}
