package com.sift.loan.service;

import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sift.loan.bean.FileBean;

@Service("importService")
public class ImportServiceImpl implements  ImportService{
    public void Import(FileBean fileBean) {
        ByteArrayInputStream bis = new ByteArrayInputStream(fileBean.getFileData().getBytes());
        Workbook workbook;
        try {
            if (fileBean.getFileData().getOriginalFilename().endsWith("xls")) {
                workbook = new HSSFWorkbook(bis);
            } else if (fileBean.getFileData().getOriginalFilename().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(bis);
            } else {
                throw new IllegalArgumentException("Received file does not have a standard excel extension.");
            }

            for (Row row : workbook.getSheetAt(0)) {
               if (row.getRowNum() == 0) {
                  Iterator<Cell> cellIterator = row.cellIterator();
                  while (cellIterator.hasNext()) {
                      Cell cell = cellIterator.next();
                      //go from cell to cell and do create sql based on the content
                  }
               }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}