package com.example.jetdevs.fileUploader.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.jetdevs.fileUploader.domain.File;
import com.example.jetdevs.fileUploader.domain.FileRecord;

public class ExcelHelper {

	public static boolean checkExcelFormat(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		} else {
			return false;
		}
	}

	// convert Excel to list of records

	public static List<FileRecord> convertExcelToListOfRecord(InputStream inputStream, File file) {
		List<FileRecord> listRecord = new ArrayList<>();
		try {
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workBook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();
				FileRecord excelRecords = new FileRecord();

				int cellIndex = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIndex) {
					case 0:
						excelRecords.setProductName(currentCell.getStringCellValue());
						break;

					case 1:
						excelRecords.setProductDesc(currentCell.getStringCellValue());
						break;

					case 2:
						excelRecords.setProductPrice(currentCell.getNumericCellValue());
						break;

					default:
						break;
					}
					cellIndex++;
				}
				excelRecords.setFile(file);
				listRecord.add(excelRecords);
			}
			workBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRecord;
	}
}
