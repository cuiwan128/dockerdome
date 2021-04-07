package com.demo.excel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class CustomCellWriteHandler implements CellWriteHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomCellWriteHandler.class);
    //标黄行宽集合
    private Set<Integer> yellowRowIndexs;

    //构造
    public CustomCellWriteHandler(Set<Integer> yellowRowIndexs) {
        this.yellowRowIndexs = yellowRowIndexs;
    }

    public CustomCellWriteHandler() {
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        LOGGER.info("beforeCellCreate~~~~");
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,         Head head, Integer relativeRowIndex, Boolean isHead) {
        LOGGER.info("afterCellCreate~~~~");
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        LOGGER.info("afterCellDataConverted~~~~");
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 这里可以对cell进行任何操作
        LOGGER.info("第{}行，第{}列写入完成。", cell.getRowIndex(), cell.getColumnIndex());
        if (CollectionUtils.isNotEmpty(yellowRowIndexs)) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();
            //字体
            Font cellFont = workbook.createFont();
            cellFont.setBold(true);
            cellStyle.setFont(cellFont);
            //标黄,要一起设置
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置前景填充样式
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());//前景填充色

            if (yellowRowIndexs.contains(cell.getRowIndex())) {
                cell.setCellStyle(cellStyle);
            }
        }
    }
}
