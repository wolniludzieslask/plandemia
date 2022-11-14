package zgony_swiat_wm;

/* Przepisuje liczby zgonów z pliku world_mortality.csv
 * (projekt https://github.com/akarlinsky/world_mortality)
 * do pliku w formacie
 * NazwaKraju,RodzajOkresu,NumerOkresu,Zgony2018,Zgony2019,Zgony2021
 * Patrz pliki peru2021.JPG, barbados2021.JPG w katalogu testy.
*/
public class FormatZgonySwiatWm2021
{
	public static void main(String[] args)
	{
		FormatZgonySwiatWm.czytajListeKrajow();
		FormatZgonySwiatWm.setNazwaPlikuKom("kom_zgony_wm2021.txt");
		FormatZgonySwiatWm.setNazwaPlikuWyj("zgony_wm2021.txt");
		FormatZgonySwiatWm.czytajZgony("2018,2019,2021",false);
		FormatZgonySwiatWm.setNazwaPlikuKom("kom_zgony_wm2021_1.txt");
		FormatZgonySwiatWm.zapiszLiczbeZgonow("NazwaKraju,Mies./tyg.,NrOkresu,Zgony2018,Zgony2019,Zgony2021");
	}


}
