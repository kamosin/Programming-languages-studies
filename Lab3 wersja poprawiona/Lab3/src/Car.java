/* 
 * Klasa Car
 * Zawartoœæ:
 * Definicje metod klasy car, deklaracja w³aœciwosci klasy Car
 * 
 * Imiê i nazwisko : Kamil Osiñski
 * Nr albumu : 241387
 * Data utworzenia 28.10.2018
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;


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
	
	@Override
	public String toString() {
		return TypeName;
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

public class Car implements Serializable, Comparable<Car>{
	 
	private static final long serialVersionUID = 1L;
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
	public Car(String make, String model2) {
	}

	public void setCarMake(String make) throws MyException {
		if((make==null )|| make.equals("")) {
			throw new MyException("Pole <<Marka>> musi zostaæ podane");
		}
		this.CarMake=make;
	}
	public String getCarMake() {
		return CarMake;
	}
	 
	public void setCarModel(String model) throws MyException {
		if((model==null )|| model.equals(""))
			throw new MyException("Pole <<model>> musi zostaæ podane");
			
		this.model=model;
	}
	
	public String getCarModel() {
		return model;
	}
	
	public void setYearOfProd(int year_of_prod) throws MyException {
		if ((year_of_prod<1920) || (year_of_prod>2018)) 
			throw new MyException("rok produkcji musi nale¿eæ do przedzia³u od 1920roku do 2018");
		this.YearofProduction=year_of_prod;
	}
	
	public void setYearOfProd(String year) throws MyException {
		if (year == null || year.equals("")){
			setYearOfProd(0);
			return;
		}
		try { 
			setYearOfProd(Integer.parseInt(year));
		} catch (NumberFormatException e) {
			throw new MyException("Rok produkcji musi byæ liczb¹ ca³kowit¹. ");
		}
	}
	
	
	
	
	public int getYearOfProd() {
		return YearofProduction;
	}
	 
	
	public void setMileageInKm(double kilometers) throws MyException{
		if(kilometers<0) 
			throw new MyException("To niemo¿liwe! Samochód nie mo¿e mieæ ujemengo przebiegu");
		this.MileageInKm=kilometers;
	}
	
	public void setMileageInKm(String mileage) throws MyException {
		if (mileage == null || mileage.equals("")){
			setMileageInKm(0);
			return;
		}
		try { 
			setMileageInKm(Double.parseDouble(mileage));
		} catch (NumberFormatException e) {
			throw new MyException("Rok urodzenia musi byæ liczb¹ ca³kowit¹.");
		}
	}
	
	
	
	public double getMileageInKm() {
		return MileageInKm;
	}
	
	public void setBody(CarType body){
		this.body = body;
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
	
	@Override
	public String toString() {  
		if(CarMake==null) {
			CarMake="";
			model="";
		}
		return CarMake + " " + model;
	}
	
	
	
	
	
	public static void DataToFile(PrintWriter zapis, Car car) {
		zapis.println(car.CarMake +"%"+ car.model+"%"+ car.YearofProduction+"%" + car.MileageInKm+"%" + car.body);
		
	}
	
	public static void DataToFile(String text, Car car) throws MyException
	{
		try {
			PrintWriter file = new PrintWriter(text);
			DataToFile(file, car);
			file.close();
		}
		catch(IOException e) {
			
			throw new MyException("Nast¹pi³ b³ad przy zapisie");
		}
	} 
	public static Car DataFromFile(BufferedReader read) throws MyException {
		try {
			String lines = read.readLine();
			String[] text = lines.split("%");
			Car car = new Car(text[0], text[1]);
			car.setCarMake(text[0]);
			car.setCarModel(text[1]);
			car.setYearOfProd(text[2]);
			car.setMileageInKm(text[3]);
			car.setBody(text[4]);	
			read.close();
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
			throw new MyException("Wyst¹pi³ b³ad podczas odczytu pliku");
		}
	}
	@Override
	public int compareTo(Car x) {
		int compareCarMake=CarMake.compareTo(x.getCarMake());
		if(compareCarMake!=0) {
			return compareCarMake;
		}
		int compareCarModel=model.compareTo(x.getCarModel());
		if(compareCarModel!=0) {
			return compareCarModel;
		}
		Integer y =YearofProduction;
		return y.compareTo(x.getYearOfProd());
		
	}
	
	@Override
	public boolean equals(Object o) {
		if(this==o)
			return true;
		if(o==null)
			return false;
		if(getClass()!=o.getClass())
			return false;
		
		Car other =(Car) o;
		if(other.getCarMake().equals(this.getCarMake()))
			if(other.getCarModel().equals(this.getCarModel()))
				if(other.getYearOfProd()==this.getYearOfProd())
					if(other.getMileageInKm()==this.getMileageInKm())
						if(other.getBody().equals(this.getBody()))
							return true;
		return false;
	}
	@Override
	public int hashCode() {
		
		return 829*getCarMake().hashCode()*getCarModel().hashCode()*getYearOfProd()*(int)getMileageInKm()*getBody().hashCode();
	}
	

}
