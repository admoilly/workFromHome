/*
 * Copyright (c) 29/7/19 9:51 PM
 * Amit Kumar (43466967)
 */

package com.ubs.gcmm.devops.DBExcelImporter;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


public class dumpQueryAsExcel {
    private dumpQueryAsExcel(String _driver, String _user, String _password, String _jdbc_url, String _sqlFile, String _outfile) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
            Calendar c = Calendar.getInstance();
            c.setTime(new java.util.Date());
            c.add(Calendar.DATE, -1);
            String output = sdf.format(c.getTime());
            Class.forName(_driver);
            Properties properties = new Properties();
            properties.setProperty("user", _user);
            properties.setProperty("password", _password);
            Connection conn = DriverManager.getConnection(_jdbc_url, properties);
            String SQL = readSQLFile(_sqlFile);
            ResultSet rst = conn.createStatement().executeQuery(SQL);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(output);
            XSSFCellStyle my_style = workbook.createCellStyle();
            XSSFFont my_font = workbook.createFont();
            my_font.setBold(true);
            my_style.setFont(my_font);
            int row_num = 0;
            int cell_num = 0;
            ResultSetMetaData resultSetMetaData = rst.getMetaData();
            Row row = sheet.createRow(row_num++);
            for (int col = 1; col <= resultSetMetaData.getColumnCount(); col++) {
                Cell cell = row.createCell(cell_num++);
                cell.setCellValue(resultSetMetaData.getColumnName(col));
                cell.setCellStyle(my_style);
            }
            while (rst.next()) {
                row = sheet.createRow(row_num++);
                cell_num = 0;
                for (int col = 1; col <= resultSetMetaData.getColumnCount(); col++) {
                    Cell cell = row.createCell(cell_num++);
                    cell.setCellValue(rst.getString(col));
                }
            }

            try {
                FileOutputStream out = new FileOutputStream(new File(String.format("%s_%s.xlsx", output, _outfile)));
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String driver = args[0];
        String user = args[1];
        String password = args[2];
        String jdbc_url = args[3];
        String sqlFile = args[4];
        String outFile = args[5];
        new dumpQueryAsExcel(driver, user, password, jdbc_url, sqlFile, outFile);
    }

    private String readSQLFile(String fileLocation) throws IOException {
        InputStream is = new FileInputStream(fileLocation);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        return sb.toString();
    }
}
