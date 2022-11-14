package zgony_usa;

/**
 * Oblicza sumaryczny indeks restrykcyjności
 * (C1+C2+C3+C4+C5+C6+C7+C8+H6)
 * dla wszystkich stanów za rok 2021.
 * 
 * @author Andrzej Bystrzycki
 *
 */		
public class SumarycznyIndeksRestr2021
{

	public static void main(String[] args)
	{
		SumarycznyIndeksRestr.setNazwaPlikuWyj("OxCGRT_US_suma_ind_restr2021.csv");
		SumarycznyIndeksRestr.setNazwaPlikuKom("kom_CGRT_US_suma_ind_restr2021.txt");
		SumarycznyIndeksRestr.czytajListeStanow();
		SumarycznyIndeksRestr.obliczIndeks("20210101", "20211231");
	}

}
