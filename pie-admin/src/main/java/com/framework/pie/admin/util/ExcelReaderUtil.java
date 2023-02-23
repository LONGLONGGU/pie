package com.framework.pie.admin.util;

import com.framework.pie.admin.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderUtil.class);
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param fileType    文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     *
     * @param filePath 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static Workbook getWorkBook(String filePath) {
        Workbook workbook = null;
        FileInputStream inputStream = null;
        try {
            // 获取Excel后缀名
            String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
            // 获取Excel文件
            File excelFile = new File(filePath);
            if (!excelFile.exists()) {
                logger.warn("指定的Excel文件不存在！");
                return null;
            }
            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

////            // 读取excel中的数据
//            List<SysUser> resultDataList = parseExcel(workbook);
//            return resultDataList;
        } catch (Exception e) {
            logger.warn("解析Excel失败，文件名：" + filePath + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warn("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
        return  workbook;
    }
    /**
     * 将单元格内容转换为字符串
     *
     * @param cell
     * @return
     */
    public static String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();
                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     * <p>
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static SysUser convertRowToData(Row row) {
        SysUser resultData = new SysUser();
        Cell cell;
        int cellNum = 0;
        // 获取部门
        cell = row.getCell(cellNum++);
        String deptName = convertCellValueToString(cell);
        resultData.setDeptName(deptName);
        // 获取账号
        cell = row.getCell(cellNum++);
        String name = convertCellValueToString(cell);
        resultData.setName(name);

        // 获取密码
        cell = row.getCell(cellNum++);
        String password = convertCellValueToString(cell);
        resultData.setPassword(password);

        //获取角色
        cell = row.getCell(cellNum++);
        String role = convertCellValueToString(cell);

        //获取电话号码
        cell = row.getCell(cellNum++);
        String mobile = convertCellValueToString(cell);
        resultData.setMobile(mobile);

        return resultData;
    }

}
