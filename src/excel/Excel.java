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
    	
    	System.out.println("in read method");
    	
        File inputWorkbook = inputFile;
        Workbook w;
        List<String> list = new ArrayList<>();
        StringBuilder tempString; 
        
        
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines

            //for (int j = 0; j < sheet.getColumns(); j++) {
            //    for (int i = 0; i < sheet.getRows(); i++) {
            
            int i;
            
            System.out.println("Columns: " + sheet.getColumns());
            System.out.println("Rows: " + sheet.getRows());
            System.out.println("Cell (1,10): " + (String)sheet.getCell(1, 10).getContents());

            
            for (int j = 0; j < sheet.getRows(); j++) {
            	i=0;
            	tempString = new StringBuilder();
            	while(i < sheet.getColumns()) {
            		System.out.println("Reading cell: (" + i + ", " + j +")");
            		Cell cell = sheet.getCell(i, j);           
                    tempString.append((String)cell.getContents());
                    
                    System.out.println(tempString);
                    //if(cell.getContents()!="") {
                    //list.add((String)cell.getContents());
                    i++;
                    if (i < sheet.getColumns()) {
                    tempString.append('&');
                    }
                    else if (i == sheet.getColumns()) {
                    	list.add(tempString.toString());	
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

		String row[];
	
		
		for (int i = 0; i < list.size(); i++) {
			row = list.get(i).split("&");

			for (int j = 0; j < row.length; j++) {
				addLabel(excelSheet, j, i, row[j]);
			}
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