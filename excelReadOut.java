import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;

public class excelReadOut {

    HSSFWorkbook wb;

    public excelReadOut() {
        wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("BakaData");
        FileOutputStream out = null;

        try {
            out = new FileOutputStream("E:\\python\\test.xls");
            wb.write(out);
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    public void insertData(String sheetName)
    {
        Row row1 = wb.getSheet(sheetName).createRow(1);
        Cell cell1_1 = row1.createCell(1);
        cell1_1.setCellValue(123);
    }


    public static void main(String[] args){
        excelReadOut test = new excelReadOut();
        test.insertData("BakaData");
    }
}
