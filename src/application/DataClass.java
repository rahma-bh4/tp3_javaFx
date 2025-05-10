package application;

import java.util.ArrayList;
import java.util.List;

public class DataClass {
	private List<Person> importList;
	private List<Person> exportList;
	
	public DataClass() {
		importList = new ArrayList<Person>();
		// Make sure we're using firstName and lastName here (not prenom, nom)
		importList.add(new Person("Sami", "BenAli", "sami@example.com"));
		importList.add(new Person("Alia", "BenSalah", "alia@example.com"));
		importList.add(new Person("Ali", "BenSalah", "ali@example.com"));
		
		// Initialize exportList to avoid NullPointerException in setExportList
		exportList = new ArrayList<Person>();
	}
	
	public List<Person> getImportList() {
		return importList;
	}
	
	public List<Person> getExportList() {
		return exportList;
	}
	
	public void setExportList(List<Person> exportList) {
		// Clear the existing list first
		this.exportList.clear();
		// Then add all items from the parameter list
		this.exportList.addAll(exportList);
		
		// Print exported items for debugging
		System.out.println("Exported list:");
		for(Person p: this.exportList) {
			System.out.println(p);
		}
	}
}