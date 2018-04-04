package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import excel.Excel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.write.WriteException;

/**
 * 
 * "ExcelShuffer"
 *	version 1.3
 * 
 * @author agung udijana
 * 
 * 2 April 2018
 * 
 * For :
 * Hogeschool van Amsterdam
 * Department : Centraal Stem Bureau
 *  
 *  
 * This standalone Windows tool randomly shuffles the contents of a simple Excel spreadsheet
 * consisting of one worksheet and enables the user to save the shuffled list 
 * to new Excel sheet.
 * 
 * -= NEW 4 April 2018 :  Now also possible to have multiple columns with data per row =-
 * 
 */


public final class ExcelShuffler extends Application {

	@Override
	public void start(final Stage stage) {
		stage.setTitle("Shuffle Excel file");
		Image image = new Image("http://icons.iconarchive.com/icons/benjigarner/softdimension/128/Excel-icon.png");
		stage.getIcons().add(image);

		final FileChooser fileChooser = new FileChooser();
		final Button openButton = new Button("Open Excel file, shuffle and save!");
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel 97-2003 files (*.xls)", "*.xls");
		fileChooser.getExtensionFilters().add(extFilter);

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(stage);
				List<String> list = new ArrayList<>();
				if (file != null) {
					try {
						list = openFile(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
				// call the shuffle method
				shuffle(list);
			
				System.out.println("Shuffled list :");
				System.out.println("--------------");
				System.out.println(list);
			
				File shuffledFile = fileChooser.showSaveDialog(stage);
				
				if (shuffledFile != null) {
					try {
						saveFile(shuffledFile, list);
					} catch (WriteException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		};
		

		openButton.setOnAction(event);
		
		
		final GridPane inputGridPane = new GridPane();

		GridPane.setConstraints(openButton, 0, 0);
		inputGridPane.setHgap(12);
		inputGridPane.setVgap(12);
		inputGridPane.getChildren().addAll(openButton);

		final Pane rootGroup = new VBox(200);
		rootGroup.getChildren().addAll(inputGridPane);
		rootGroup.setPadding(new Insets(50, 50, 50, 50));

		stage.setScene(new Scene(rootGroup));
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	
	/**
	 * 
	 * Opens an Excel file according to the Excel 97-2003 file format (extension .xls)
	 * Any other file will give an error
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private List<String> openFile(File file) throws IOException {
		
		System.out.println("Opening file...");
		System.out.println();
		
		Excel excel = new Excel();
		System.out.println("Calling read method");
		List<String> list = excel.read(file);

		System.out.println("Unshuffled list:");
		System.out.println("------------------");
		System.out.println(list);
		System.out.println();

		return list;
	}

	
	/**
	 * This method uses the shuffle method from the standard Java Collections class :
	 * more info : https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html
	 *  
	 * @param list
	 */
	private void shuffle(List<String> list) {
		System.out.println("shuffle");
		Collections.shuffle(list);
	}

	/**
	 * 
	 * This will save the shuffled file to an Excel file according to the Excel 97-2003 file format
	 * (extension .xls)
	 * 
	 * @param shuffledFile
	 * @param list
	 * @throws WriteException
	 * @throws IOException
	 */
	private void saveFile(File shuffledFile, List<String> list) throws WriteException, IOException {
		
		System.out.println();
		System.out.println("Saving..");
		System.out.println();
		
		Excel excel = new Excel();
		
		excel.write(shuffledFile, list);
	}

}