package application;

public class HouseAreaFactory extends HouseFactory{

	@Override
	public HouseEntity createItem(String item) {
		if(item.equals("House")) {
			return new HouseArea("House");	
		}
		else if(item.equals("Kitchen")) {
			return new HouseArea("Kitchen");	
		}
		else if(item.equals("Hall")) {
			return new HouseArea("Hall");	
		}
		else if(item.equals("BedBath")) {
			return new HouseArea("BedBath");	
		}
		else {
			return null;
		}
		
	}

	
}