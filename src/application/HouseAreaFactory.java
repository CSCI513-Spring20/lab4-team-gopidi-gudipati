package application;

public class HouseAreaFactory extends HouseItems{

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
		else if(item.equals("Bed and Bath")) {
			return new HouseArea("Bed and Bath");	
		}
		else {
			return null;
		}
		
	}

	
}