package Main;

import java.util.ArrayList;
import java.util.List;

public class FileData {
	public static Integer numberOfItems;
	public static Integer binMaxCapacity;
	public static List<Integer> items;
	
	public FileData(List<Integer> items, Integer binMaxCapacity, Integer numberOfItems) {
		FileData.items = new ArrayList<Integer>(items);
		FileData.binMaxCapacity = binMaxCapacity;
		FileData.numberOfItems = numberOfItems;
	}
	
	public Integer getNumberOfItems() {
		return numberOfItems;
	}
	
	public Integer getBinCapacity() {
		return binMaxCapacity;
	}

	public List<Integer> getItems() {
		return items;
	}

}
