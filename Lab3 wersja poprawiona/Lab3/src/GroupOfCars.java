import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/*
 *   Plik: GroupOfCars.java
 *
 *   Opis: Zarz¹dzanie kolekcjami klasy Car.java
 *   Autor: Kamil Osiñski
 *   Nr indeksu: 241387
 *   Data: 25.11.2018 r.
 */


enum GroupType {
	
	VECTOR("Lista   (klasa Vector)"),
	ARRAY_LIST("Lista   (klasa ArrayList)"),
	LINKED_LIST("Lista   (klasa LinkedList)"),
	HASH_SET("Zbiór   (klasa HashSet)"),
	TREE_SET("Zbiór   (klasa TreeSet)");

	String typeName;

	private GroupType(String type_name) {
		typeName = type_name;
	}

	@Override
	public String toString() {
		return typeName;
	}


	public static GroupType find(String type_name){
		for(GroupType type : values()){
			if (type.typeName.equals(type_name)){
				return type;
			}
		}
		return null;
	}
	public Collection<Car> createCollection() throws MyException {
		switch (this) {
		case VECTOR:      return new Vector<Car>();
		case ARRAY_LIST:  return new ArrayList<Car>();
		case HASH_SET:    return new HashSet<Car>();
		case LINKED_LIST: return new LinkedList<Car>();
		case TREE_SET:    return new TreeSet<Car>();
		default:          throw new MyException("Podany typ kolekcji nie zosta³ zaimplementowany.");
		}
	}
}



public class GroupOfCars implements Iterable<Car>, Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private GroupType type;
	private Collection<Car> collection;
	
	public GroupOfCars(GroupType type, String name) throws MyException {
		setName(name);
		if (type==null){
			throw new MyException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}


	public GroupOfCars(String type_name, String name) throws MyException {
		setName(name);
		GroupType type = GroupType.find(type_name);
		if (type==null){
			throw new MyException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}
	
	public void setName(String n) throws MyException {
		if(n==null || n.equals("")) {
			throw new MyException ("B³êdna nazwa");
		}
		else {
			this.name=n;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setGroupType(GroupType t) throws MyException{
		if(t==null) {
			throw new MyException("Nieokreœlony typ kolekcji");
		}
		if(this.type==t)
			return;
		
		Collection<Car> oldCollection = collection;
		collection = t.createCollection();
		this.type = t;
		for (Car car : oldCollection)
			collection.add(car);
		
	}
 
	
	public void setGroupType(String type_name) throws MyException {
		for(GroupType t : GroupType.values()){
			if (t.toString().equals(type_name)) {
				setGroupType(t);
				return;
			}
		}
		throw new MyException("Nie ma takiego typu kolekcji.");
	}
	public GroupType getGroupType() {
		return type;
	}
	
	
	public boolean add(Car c) {
		return collection.add(c);
	}
	
	@Override
	public Iterator<Car> iterator() {
		return collection.iterator();
	}
	
	@Override
	public String toString() {
		return name + "  [" + type + "]";
	}

	public int size() {
		return collection.size();
	}
	
	
	public static void printToFile(PrintWriter writer, GroupOfCars group) {
		writer.println(group.getName());
		writer.println(group.getGroupType());
		for (Car car : group.collection)
			Car.DataToFile(writer, car);
	}


	public static void printToFile(String file_name, GroupOfCars group) throws MyException {
		try (PrintWriter writer = new PrintWriter(file_name)) {
			printToFile(writer, group);
		} catch (FileNotFoundException e){
			throw new MyException("Nie odnaleziono pliku " + file_name);
		}
	}


	public static GroupOfCars readFromFile(BufferedReader reader) throws MyException{
		try {
			String group_name = reader.readLine();
			String type_name = reader.readLine();
			GroupOfCars groupOfPeople = new GroupOfCars(type_name, group_name);

			Car car;
			while((car = Car.DataFromFile(reader)) != null)
				groupOfPeople.collection.add(car);
			return groupOfPeople;
		} catch(IOException e){
			throw new MyException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}


	public static GroupOfCars readFromFile(String file_name) throws MyException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return GroupOfCars.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new MyException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new MyException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}
	}
	
	public void sortName() throws MyException {
		if (type==GroupType.HASH_SET|| type==GroupType.TREE_SET ){
			throw new MyException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		
		Collections.sort((List<Car>)collection, new Comparator<Car>() {

			@Override
			public int compare(Car c1, Car c2) {
				
				return c1.getCarMake().compareTo(c2.getCarMake());
			}
		});
	}
	
	public void sortYearOfProd() throws MyException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new MyException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystany
		// obiekt klasy anonimowej (klasa bez nazwy), która implementuje
		// interfejs Comparator i zawiera tylko jedn¹ metodê compare.
		Collections.sort((List<Car>) collection, new Comparator<Car>() {

			@Override
			public int compare(Car c1, Car c2) {
				if (c1.getYearOfProd() < c2.getYearOfProd())
					return -1;
				if (c1.getYearOfProd() > c2.getYearOfProd())
					return 1;
				return 0;
			}

		});
	
		}
	
	
	public void sortMileage() throws MyException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new MyException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystany
		// obiekt klasy anonimowej (klasa bez nazwy), która implementuje
		// interfejs Comparator i zawiera tylko jedn¹ metodê compare.
		Collections.sort((List<Car>) collection, new Comparator<Car>() {

			@Override
			public int compare(Car c1, Car c2) {
				if (c1.getMileageInKm() < c2.getMileageInKm())
					return -1;
				if (c1.getMileageInKm() > c2.getMileageInKm())
					return 1;
				return 0;
			}

		});
	
		}
	
	public void sortBodyType() throws MyException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new MyException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		// Przy sortowaniu jako komparator zostanie wykorzystany
		// obiekt klasy anonimowej (klasa bez nazwy), która implementuje
		// interfejs Comparator i zawiera tylko jedn¹ metodê compare.
		Collections.sort((List<Car>) collection, new Comparator<Car>() {

			@Override
			public int compare(Car c1, Car c2) {
				return c1.getBody().toString().compareTo(c2.getBody().toString());
			}

		});
	}
	
}