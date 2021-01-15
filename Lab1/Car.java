/* 
 * Klasa Car
 * Zawartoœæ:
 * Definicje metod klasy car, deklaracja w³aœciwosci klasy Car
 * 
 * Imiê i nazwisko : Kamil Osiñski
 * Nr albumu : 241387
 * Data utworzenia 12.10.2018
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

enum CarType {
	Unknown("Nieznany"),
	Sedan("sedan"),
	Hatchback("hatchback"),
	SUV("suv"),
	Coupe("coupe"),
	Combi("kombi");
	
	String TypeName;
	
	CarType(String type) {
		TypeName=type;
	}
}

class MyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyException(String msg) {
		super(msg);
	}
}

public class Car {
	
	public String CarMake;
	public String model;
	public int YearofProduction;
	public double MileageInKm;
	private CarType body;
	
	public Car(String make,String model, int year_of_prod, double mileage) throws MyException
	{
		setCarMake(make);
		setCarModel(model);
		setYearOfProd(year_of_prod);
		setMileageInKm(mileage);
	}
	
	public Car(String make, String model2, String string, String string2) {
	}

	public void setCarMake(String make) throws MyException {
		if((make==null )|| make.equals("")) {
			throw new MyException("Pole <<Marka>> musi zostac podane");
		}
		this.CarMake=make;
	}
	
	public String getCarMake() {
		return CarMake;
	}
	 
	public void setCarModel(String model) throws MyException {
		if((model==null )|| model.equals(""))
			throw new MyException("Pole <<model>> musi zostac podane");
			
		this.model=model;
	}
	
	public String getCarModel() {
		return model;
	}
	
	public void setYearOfProd(int year_of_prod) throws MyException {
		if ((year_of_prod<1920) || (year_of_prod>2018)) 
			throw new MyException("rok produkcji musi nalezec do przedzia³u od 1920roku do 2018");
		this.YearofProduction=year_of_prod;
	}
	
	public int getYearOfProd() {
		return YearofProduction;
	}
	 
	
	public void setMileageInKm(double kilometers) throws MyException{
		if(kilometers<0) 
			throw new MyException("To niemo¿liwe! Samochod nie moze miec ujemengo przebiegu");
		this.MileageInKm=kilometers;
	}
	
	public double getMileageInKm() {
		return MileageInKm;
	}
	
	public void setBody(String bodyType) throws MyException{
		if(bodyType==null || bodyType.equals("")) {
			this.body=CarType.Unknown;
			return;
		}
		for(CarType bodyType1 : CarType.values()){
			if(bodyType1.TypeName.equals(bodyType)) {
			this.body=bodyType1;
			return;
			}
		
		}
		throw new MyException("Nie ma takiego typu nadwozia");
		 
	}
	
	public CarType getBody() {
		return body;
	}
	
	
	public static void DataToFile(PrintWriter zapis, Car car) {
		zapis.println(car.CarMake +" "+ car.model+" "+ car.YearofProduction+" " + car.MileageInKm+" " + car.body);
		
	}
	
	public static void DataToFile(String text, Car car) throws MyException
	{
		try {
			PrintWriter file = new PrintWriter(text);
			DataToFile(file, car);
		}
		catch(IOException e) {
			
			throw new MyException("Nastapil blad przy zapisie");
		}
	} 
	public static Car DataFromFile(BufferedReader read) throws MyException {
		try {
			String lines = read.readLine();
			String[] text = lines.split(" ");
			Car car = new Car("0", "0", 2000 , 100000);
			car.setCarMake(text[0]);
			car.setCarModel(text[1]);
			int year=Integer.parseInt(text[2]);
			int mileage=Integer.parseInt(text[3]);
			car.setYearOfProd(year);
			car.setMileageInKm(mileage);
			car.setBody(text[4]);	
			return car;
		} catch(IOException e){
			throw new MyException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	 
	}	
	public static Car DataFromFile(String filename ) throws MyException {
		try {
		BufferedReader read = new BufferedReader(new FileReader(new File(filename)));
		return Car.DataFromFile(read);
		}
		catch(FileNotFoundException e)
		{
			throw new MyException("Nie znaleziono pliku o takiej nazwie");
		}
		catch(MyException e)
		{
			throw new MyException("Wystapil blad podczas odczytu pliku");
		}
	}
}
