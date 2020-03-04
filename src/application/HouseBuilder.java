package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


/**
 * 
 * All items in the house (rooms, floors, furniture are House Entities) 
 */
interface HouseEntity {
    public void listHouseSpecs(int level);
    public int countContents();
    public void add(HouseEntity houseentity);
}

 
/**
 * 
 * Composite pattern:  Leaf node
 */
class Furniture implements Serializable, HouseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String blockName;
	public Furniture(String blockName){
		this.blockName = blockName;
	}
	
 	@Override
	public void listHouseSpecs(int level) {
		StringBuffer sb = new StringBuffer();
		for(int j = 0; j < level; j++)
			sb.append("   ");			
		System.out.println(sb.toString() + blockName);		
	}

	@Override
	public int countContents() {
		return 1;
	}

	@Override
	public void add(HouseEntity houseentity) {
		// TODO Auto-generated method stub
		
	}    
}

/**
 * 
 * Composite Pattern: Composite Class
 * HouseArea is a floor (upstairs, downstairs), the house itself, or a room
 */
class HouseArea implements Serializable, HouseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// List of children
	private List<HouseEntity> childGroup = new ArrayList<HouseEntity>();
	String blockName;
	public HouseArea(String blockName){
		this.blockName = blockName;
	}
	
	public void add(HouseEntity group) {
		childGroup.add(group);
	}
	
	public void remove(HouseEntity group) {
		childGroup.remove(group);
	}
	
	@Override
	public void listHouseSpecs(int level) {

		// First display the current group
		StringBuffer sb = new StringBuffer();
		for(int j = 0; j < level; j++)
			sb.append("   ");
		System.out.println(sb.toString() + blockName);
		
		// Now delegate the task of display to any children
		for(HouseEntity group: childGroup){
			group.listHouseSpecs(level+1);
		}	
	}

	@Override
	public int countContents() {
		int contents = 0;
		for(HouseEntity child: childGroup){
			contents += child.countContents();
		}
		return contents +1;
	}
}


/**
 * 
 * This is the main application.  Note that while it is a JavaFX application it doesn't
 * actually "show" the main scene.  We just need the application for the fileChooser.
 */
public class HouseBuilder extends Application{
	
	
	HouseFactory area = new HouseAreaFactory();
	HouseEntity house = area.createItem("House");
	FurnitureAreaFactory farea= new FurnitureAreaFactory();
	public void buildHouse(){
		    HouseEntity kitchen = area.createItem("Kitchen");
		    HouseEntity hall = area.createItem("Hall");
		    HouseEntity bnb = area.createItem("BedBath");
		    
		    HouseEntity chairs = farea.createItem("Chairs");
		    HouseEntity organiser = farea.createItem("Organiser");
		    HouseEntity dining = farea.createItem("Dining");
		    HouseEntity bed = farea.createItem("Bed");
		    
		    house.add(kitchen);
		    house.add(hall);
		    house.add(bnb);
		    
		    hall.add(chairs);
		    kitchen.add(dining);
		    bnb.add(bed);
		    bnb.add(organiser);
		    
	}
	
	/**
	 * Save using serialization
	 * @param fileName
	 */
	public void save(String fileName){
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( new FileOutputStream(fileName));
			oos.writeObject(house);  //serializing employee
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	
	public void countHouseContents(){
		System.out.println("House includes: " + house.countContents() + " areas and/or furniture items.");
	}
	
	public void printHouseSpecs(){
		house.listHouseSpecs(0);
	}
	
	public HouseArea getHouse(){
		return (HouseArea) house;
	}
	
	
	/**
	 * Restore from serialized form
	 * @param fileName
	 */
	public void restore(String fileName){
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream( new FileInputStream(fileName));
			house = (HouseArea) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public String getFileName(Stage primaryStage){
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setInitialDirectory(new File("/Users/abhinavreddygopidi/Downloads"));  // This is optional
		 fileChooser.setTitle("Serialization File");
		 File file = fileChooser.showOpenDialog(primaryStage);
		 return file.getAbsolutePath();
	}
	
	 public static void main(String[] args) {
		 launch(args);      
	 }

	@Override
	public void start(Stage primaryStage) throws Exception {
		  HouseBuilder houseBuilder = new HouseBuilder();
	      houseBuilder.buildHouse();
	      houseBuilder.save("/Users/abhinavreddygopidi/Downloads/myHouse.ser");
	      String filename = houseBuilder.getFileName(primaryStage);
	      houseBuilder.restore(filename);
	      houseBuilder.printHouseSpecs();
	      houseBuilder.countHouseContents();		
	}      	       
}

