package excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Excel {
	
    public List<String> read(File inputFile) throws IOException  {
        File inputWorkbook = inputFile;
        Workbook w;
        List<String> list = new ArrayList<>();
        
        
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    
                    if(cell.getContents()!="") {
                    list.add((String)cell.getContents());
                    }
              }
            }
        } catch (BiffException e) {
            e.printStackTrace();
            System.out.println("Exiting");
            /* TO DO for later version :
      		Show an error message to the user and enable to try again
      		*/
            System.exit(0);
        }
        
        
        return list;
        
    } 
    
    public void write(File file, List<String> list) throws IOException, WriteException {

		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Shuffled", 0);
		WritableSheet excelSheet = workbook.getSheet(0);

		for (int i = 0; i < list.size(); i++) {
			addLabel(excelSheet, 0, i, list.get(i));
		}

		workbook.write();
		workbook.close();
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s);
		sheet.addCell(label);
	}
    
}