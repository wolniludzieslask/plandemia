package zgony_swiat_wm;

/*
 * Sumuje liczby zgonów dla każdego kraju.
 * Plikiem wejściowym jest plik otrzymany z klasy FormatZgonySwiatWm2021.
 * Plikiem wyściowym jest plik w formacie
 * NazwaKraju,ŚredniaZgonów2018_2019,Zgony2021,Zmiana,ZmianaProcentowa,LiczbaOkresów,RodzajOkresu 
 * Rodzaj okresu to T - tygodniowo, M - miesięcznie
 */
public class ZgonySwiatWm2021
{

	public static void main(String[] args)
	{
		ZgonySwiatWm.setNazwaPlikuWej("zgony_wm2021.txt");
		ZgonySwiatWm.setNazwaPlikuWyj("zgony_wm2021suma.txt");
		ZgonySwiatWm.setNazwaPlikuKom("kom_zgony_wm2021suma.txt");
		ZgonySwiatWm.zapiszStatystyke(3);
	}

}
