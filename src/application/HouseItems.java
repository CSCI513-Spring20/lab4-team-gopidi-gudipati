package application;

public abstract class HouseItems {
	public abstract HouseEntity createItem(String item) ;
	public HouseEntity buildHouse(String type) {
		HouseEntity houseentity = createItem(type);
		return houseentity;
	}
}
