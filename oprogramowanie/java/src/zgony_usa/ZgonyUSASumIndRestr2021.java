package zgony_usa;

/**
 * Łączy w jednym pliku dane o zgonach, szczepieniach i indeksach polityki
 * dla poszczególnych stanów w roku 2021.
 * 
 * @author Andrzej Bystrzycki
 */
public class ZgonyUSASumIndRestr2021
{

	public static void main(String[] args)
	{
		ZgonyUSASumIndRestr.setNazwaPlikuWej("zgony2021us.txt");
		ZgonyUSASumIndRestr.setNazwaPlikuWyj("zgony_us_sir2021.txt");
		ZgonyUSASumIndRestr.setNazwaPlikuSzczepien("szczepienia_vacUS2021_12_31.txt");
		ZgonyUSASumIndRestr.setNazwaPlikuKom("kom_zgony_us_ir2021.txt");
		ZgonyUSASumIndRestr.setNazwaPlikuIndeksuRestr("OxCGRT_US_suma_ind_restr2021.csv");
		ZgonyUSASumIndRestr.czytajListeStanow();
		ZgonyUSASumIndRestr.czytajIndeksRestrykcyjnosci();
		ZgonyUSASumIndRestr.czytajPoziomSzczepien();
		ZgonyUSASumIndRestr.zapiszStatystyke();
	}

}
