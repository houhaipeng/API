package com.hhp.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hhp.dto.ExcelStudentDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelStudentDTOListener extends AnalysisEventListener<ExcelStudentDTO> {

    @Override
    public void invoke(ExcelStudentDTO excelStudentDTO, AnalysisContext analysisContext) {
        log.info("解析到一条记录----->{}", excelStudentDTO);
        //调用dao层的save方法，即可导入数据库
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("all data has parsed!");
    }
}
