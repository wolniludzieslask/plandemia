package zgony_swiat_wm;

public class ZgonySwiatWmSzIndPol2020_1
{

	public static void main(String[] args)
	{
		//ZgonySwiatWmSzIndPol.setNazwaPlikuWej("zgony_wm2020suma2.csv");
		ZgonySwiatWmSzIndPol1.setNazwaPlikuWej("zgony_wm2020suma.txt");
		ZgonySwiatWmSzIndPol1.setNazwaPlikuWyj("zgony_wm_sz_ir2020suma_ind.txt");
		ZgonySwiatWmSzIndPol1.setNazwaPlikuKom("kom_zgony_wm_sz_ir2020suma_ind.txt");
		ZgonySwiatWmSzIndPol1.czytajListeKrajow();
		// nie czytam poziomu szczepień, bo zestawienie jest za 2020 rok
		ZgonySwiatWmSzIndPol1.setNazwaPlikuIndeksuRestr("OxCGRTsuma_ind2020.csv");
		ZgonySwiatWmSzIndPol1.czytajIndeksyPolityki();
		ZgonySwiatWmSzIndPol1.zapiszStatystyke();
	}

}
