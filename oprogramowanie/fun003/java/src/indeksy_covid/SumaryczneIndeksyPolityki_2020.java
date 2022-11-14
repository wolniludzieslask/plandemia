package indeksy_covid;

/*
 */
public class SumaryczneIndeksyPolityki_2020
{

	public static void main(String[] args)
	{
		SumaryczneIndeksyPolityki.setNazwaPlikuWyj("OxCGRTsuma_ind2020.csv");
		SumaryczneIndeksyPolityki.setNazwaPlikuKom("kom_CGRTsuma_ind2020.txt");
		SumaryczneIndeksyPolityki.obliczIndeks("20200101", "20201231");
	}

}
