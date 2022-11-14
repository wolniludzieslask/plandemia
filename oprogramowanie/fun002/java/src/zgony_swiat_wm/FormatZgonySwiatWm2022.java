package zgony_swiat_wm;

public class FormatZgonySwiatWm2022
{
	public static void main(String[] args)
	{
		FormatZgonySwiatWm.czytajListeKrajow();
		FormatZgonySwiatWm.setNazwaPlikuKom("kom_zgony_wm2022.txt");
		FormatZgonySwiatWm.setNazwaPlikuWyj("zgony_wm2022.txt");
		FormatZgonySwiatWm.czytajZgony("2018,2019,2022",false);
		FormatZgonySwiatWm.setNazwaPlikuKom("kom_zgony_wm2022_1.txt");
		FormatZgonySwiatWm.zapiszLiczbeZgonow("NazwaKraju,Mies./tyg.,NrOkresu,Zgony2018,Zgony2019,Zgony2021");
	}


}
