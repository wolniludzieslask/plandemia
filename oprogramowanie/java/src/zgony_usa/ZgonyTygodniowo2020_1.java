package zgony_usa;

import java.util.TreeMap;

import zgony_usa.ZgonyUSATygodniowo1.Zgony;

/**
 * Oblicza wzrost liczby zgonów w USA w roku 2020 
 * w stosunku do średniej z lat 2018 i 2019.
 * Różnica w stosunku do klasy ZgonyUSATygodniowo2020 jest taka,
 * że średnia z dwóch poprzednich lat jest obliczana po obliczeniu sumy
 * dla wszystkich tygodni.
 * W przypadku klasy ZgonyUSATygodniowo średnie za lata poprzednie obliczane są
 * dla każdego tygodnia, a na końcu obliczana jest suma i wykonywane zaokrąglenie.
*/
public class ZgonyTygodniowo2020_1
{
	private static TreeMap<String, Zgony> LiczbyZgonow1 = new TreeMap<String, Zgony>();
	private static TreeMap<String, Zgony> LiczbyZgonow2 = new TreeMap<String, Zgony>();
	private static TreeMap<String, Zgony> LiczbyZgonow3 = new TreeMap<String, Zgony>();
	//private static TreeMap<Integer, String> Naglowki1 = new TreeMap<Integer, String>();
	//private static TreeMap<Integer, String> Naglowki2 = new TreeMap<Integer, String>();
	//private static TreeMap<Integer, String> Naglowki3 = new TreeMap<Integer, String>();

	public static void main(String[] args)
	{
		ZgonyUSATygodniowo1.setNazwaPlikuWyj("zgony2020us.txt");
		ZgonyUSATygodniowo1.setNazwaPlikuKom("komunikaty2020us.txt");
		
		ZgonyUSATygodniowo1.czytajKodyStanow();
		ZgonyUSATygodniowo1.czytajIndeksRestrykcyjnosci();
		//ZgonyUSATygodniowo1.czytajPoziomSzczepien();
		
		ZgonyUSATygodniowo1.setLiczbyZgonow1(LiczbyZgonow1);
		ZgonyUSATygodniowo1.setLiczbyZgonow2(LiczbyZgonow2);
		ZgonyUSATygodniowo1.setLiczbyZgonow3(LiczbyZgonow3);

		/*
		 * dane za rok 2018
		 */
		ZgonyUSATygodniowo1.setRok_2(2018);
		ZgonyUSATygodniowo1.czytajLiczbyZgonow2(LiczbyZgonow3, "Weekly_Counts_of_Deaths_by_State_and_Select_Causes__2014-2019.csv", "2018");

		/*
		 * dane za rok 2019
		 */
		ZgonyUSATygodniowo1.setRok_1(2019);
		ZgonyUSATygodniowo1.czytajLiczbyZgonow2(LiczbyZgonow3, "Weekly_Counts_of_Deaths_by_State_and_Select_Causes__2014-2019.csv", "2019");

		/* dane za rok 2020 */
		ZgonyUSATygodniowo1.czytajLiczbyZgonow3(LiczbyZgonow3, "Weekly_Provisional_Counts_of_Deaths_by_State_and_Select_Causes__2020-2022.csv", "2020");

		// ostatni parametr false oznacza, żeby 53-go tygodnia nie sumować, true oznacza sumować
		ZgonyUSATygodniowo1.zapiszStatystyke("NazwaStanu,Srednia2018_2019,2020,ZmianaStan,ZmianaStanProc,PoziomSzczepien,IndeksRestr,LiczbaSumowanychTygodni,\r\n","2020",false);	
	}

}
