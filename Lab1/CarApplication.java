/* 
 *Klasa CarApplication
 *Zawartoœæ:
 *Metody klasy CarApplication odpowiedzialne za funkcjonowanie programu (Dodanie danych do nowego samochodu, usuniêcie danych, modyfikacja danych, zapis i odczyt z plików, pêtla glowna programu,
 *wyswietlenie aktualnych danych samochodu;
 *Funkcja main
 * 
 * Imiê i nazwisko : Kamil Osiñski
 * Nr albumu : 241387
 * Data utworzenia 12.10.2018
 */

public class CarApplication {

	private static final String Info =
			"Program Car \n" +
			"Autor: Kamil Osinski \n" +
			"Nr Albumu: 241387\n"+
			"Data 08.10.2018 \n";
	
	
	private static final String MENU = 
			"Menu Programu \n" +
			"1. Wyœwietl dane wszystkich samochodów.\n"+
			"2. Dodaj nowe dane samochodu \n"+
			"3. Usun dane samochodu \n" +
			"4. Modyfikuj dane samochodu \n"+
			"5. Wczytaj dane z pliku \n"+
			"6. Zapisz dane na pliku \n"+
			"7. Wyjdz z programu. \n";
	
	private static final String change_data = 
			"   Co zmienic?     \n" + 
	        "1 - Marka           \n" + 
			"2 - Model       \n" + 
	        "3 - Rok Produkcji  \n" + 
			"4 - Przebieg   \n" +
	        "6 - Typ nadwozia \n" +
			"7 - Powrot do menu glownego \n";
	
	public int deleteCarData(Car[] base, int licznik) throws IndexOutOfBoundsException {
		
		int pointer=-1;
		try {
		pointer=Interface.enterInt("Dane ktorego samochodu chcesz usunac?")-1;
		base[pointer]=base[licznik];
		
		}
		catch(IndexOutOfBoundsException e) {
			System.err.println("indeks poza obszarami tablicy");
			return licznik;
		}
		
		base[licznik]=null;
		System.out.print("licznik="+licznik);
		licznik--;
		System.out.print(" a teraz licznik="+licznik);
		System.out.println("Dane samochodu usunieto");
		return licznik;
		 
	}
 
	public void showData(Car[] data, int licznik) {
		for(int i=0;i<licznik;i++) {
			System.out.println("Samochod numer: " +(i+1));
			ShowCar(data[i]);
			System.out.println("\n");
		}
	}
 
	public static void main(String[] args) {
		CarApplication application = new CarApplication();
		  
		application.Loop();
	}  
	  
	public void Loop() {
		Interface.printMessage(Info);
		Car[] bazasamochodow = new Car[10];
		int licznik=0;
		while(true) {
			Interface.clearConsole();
			switch(Interface.enterInt(MENU)) {
			case 1:
				if(licznik>0)
				showData(bazasamochodow, licznik);
				else
					System.out.println("Brak samochodow w bazie");
				
				break;
			case 2:
				if(licznik<=9) {
					try {
					bazasamochodow[licznik]=new Car(Interface.enterString("Podaj marke samochodu\n"), Interface.enterString("Podaj model samochodu\n"), Interface.enterInt("Podaj rok produkcji\n"), Interface.enterDouble("Podaj przebieg samochodu\n"));
					
					bazasamochodow[licznik].setBody(Interface.enterString("podaj typ nadwozia: sedan, hatchback, suv, coupe, kombi"));
					}
					catch(MyException e)
					{
						Interface.printErrorMessage(e.getMessage());
						break;
					}
				licznik++;
				}
				else
				{ 
					System.out.println("Przykro mi, nie ma miejsca na wiecej samochodow");
				}
				break;
			case 3:
				if(licznik>0) {
				showData(bazasamochodow, licznik);
				
				licznik=deleteCarData(bazasamochodow, licznik);
				System.out.println("licznik="+ licznik);
				}
				else
				{
					System.out.println("Brak samochodow w bazie");
				}
				break;
			case 4:
				if(licznik>0)
				changeCarData(bazasamochodow, licznik);
				else
					System.out.println("Brak samochodow w bazie");
				break;
			case 5:
				String file_name=Interface.enterString("Podaj nazwe pliku do wczytania");
				try {
					if(licznik<=9)
					bazasamochodow[licznik]=Car.DataFromFile(file_name);
				} catch (MyException e1) {
					e1.printStackTrace();
				}
				catch(IndexOutOfBoundsException e){
					System.out.println("indeks poza tablica");
				}
				break; 
			case 6:
				if(licznik>0) {
					showData(bazasamochodow, licznik);
				}
				else {
					System.out.println("Brak samochodow w bazie");
				}
				int number=Interface.enterInt("Dane ktorego samochodu chcesz zapisaæ")-1;
				String filename=Interface.enterString("Podaj nazwe pliku, na jakim chcesz zapisac dane: ");
				try {
					Car.DataToFile(filename, bazasamochodow[number]);
				} catch (MyException e) {
					e.printStackTrace();
					break;
				}
				System.out.println("Pomyœlnie zapisano dane");
				break;
				 
			case 7:
				Interface.printInfoMessage("Program zamknieto");
			System.exit(0);
			}
		}
	}
	
	
	private static DialogWithUser Interface = new DialogWithUser();
	
	static void ShowCar(Car car) {
		
		System.out.println("Marka: " +car.getCarMake());
		System.out.println("Model: "+car.getCarModel());
		System.out.println("Rok produkcji: "+car.getYearOfProd());
		System.out.println("Przebieg: "+car.getMileageInKm());
		System.out.println("Typ nadwozia: "+car.getBody());
	
	}
	
	void changeCarData(Car[] base, int licznik) {
		int pointer=-1;
		while (true) {
			Interface.clearConsole();
			for(int i=0;i<licznik;i++) {
				System.out.println("Samochód numer "+(i+1));
				ShowCar(base[i]);
				System.out.println("\n");
			}
			
			pointer=Interface.enterInt("Dane ktorego samochodu chcesz zmodyfikowac?");
			pointer=pointer-1;
			if((pointer<=licznik) || (pointer<0)) {
			
			try {		
				switch (Interface.enterInt(change_data)) {
				case 1:
					System.out.println(pointer);
					base[pointer].setCarMake(Interface.enterString("Podaj markê samochodu: "));
					break;
				case 2:
					base[pointer].setCarModel(Interface.enterString("Podaj model samochodu: "));
					break;
				case 3:
					base[pointer].setYearOfProd(Interface.enterInt("Podaj rok produkcji "));
					break;
				case 4:
					base[pointer].setMileageInKm(Interface.enterDouble("Podaj przebieg samochodu"));
					break;
				case 6:
					base[pointer].setBody(Interface.enterString("Podaj typ nadwozia "));
					break;
				case 7:
					return;
				}
			} catch (MyException e) {     
				Interface.printErrorMessage(e.getMessage());
			}
			catch (IndexOutOfBoundsException m) {
				System.err.println("indeks poza obszarami tablicy");
			}
			}
			else {
				System.out.println("Indeks poza granicami tablicy");
				return;
			}
		}
	}
	
	
}
