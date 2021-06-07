package com.hhp;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hhp.dto.ExcelStudentDTO;
import com.hhp.listener.ExcelStudentDTOListener;
import org.junit.Test;

public class ExcelReadTest {

    @Test
    public void simpleReadXlsx() {
        // 写法1：
        String fileName = "/Users/haipenghou/Documents/工作/simpleWrite.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener()).sheet().doRead();
    }

    @Test
    public void simpleReadXls() {
        // 写法1：
        String fileName = "/Users/haipenghou/Documents/工作/simpleWrite.xls";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener())
                .excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }
}
