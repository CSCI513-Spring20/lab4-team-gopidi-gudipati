package application;

public class FurnitureAreaFactory extends HouseFactory{

	@Override
	public HouseEntity createItem(String item) {
		if(item.equals("Chairs")) {
			return new Furniture("Chairs");	
		}
		else if(item.equals("Bed")) {
			return new Furniture("Bed");	
		}
		else if(item.equals("Dining")) {
			return new Furniture("Dining");	
		}
		else if(item.equals("Organiser")) {
			return new Furniture("Organiser");	
		}
		else {
			return null;
		}
		
	}

	
}