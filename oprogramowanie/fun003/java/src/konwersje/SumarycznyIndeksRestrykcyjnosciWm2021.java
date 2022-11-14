package konwersje;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * Oblicza sumaryczny indeks restrykcyjności na podstawie danych pojedynczych
 * indeksów wcześniej obliczonych. Plikiem wejściowym jest plik
 * zgony_wm_sz_ir2021suma_ind.csv. Patrz opis
 * zgony_wm_sz_ir2021suma_ind.ods.czytaj, jak otrzymano plik
 * zgony_wm_sz_ir2021suma_ind.csv.
 * 
 * Zapisuje dane w formacie 
 * NazwaKraju,ZmianaProc,SumIndeks
 * 
 * Wartość sumarycznego indeksu restrykcyjności przetestowałem
 * dla Barbadosu w pliku zgony_wm_sz_ir2020suma_ind.ods
 * obliczyłem wyrażenie =SUMA(G89:N89;T89) (OpenOffice)
 * 
 */
public class SumarycznyIndeksRestrykcyjnosciWm2021
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String Kodowanie = "utf-8";
	private static String NazwaPlikuWej = "zgony_wm_sz_ir2021suma_ind.csv";
	private static String NazwaPlikuWyj = "zgony_wm_sz_ir2021suma_ind1.csv";

	public static void main(String[] args)
	{
		zapiszDane();
	}

	public static void zapiszDane()
	{
		BufferedReader br = null;
		Writer w = null;
		String s = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			while ((s = br.readLine()) != null)
			{
				String[] tab = s.split(",", -1);
				String NazwaKraju = tab[0];
				String ZmianaProc = tab[4];
				double C1 = Double.parseDouble(tab[6]);
				double C2 = Double.parseDouble(tab[7]);
				double C3 = Double.parseDouble(tab[8]);
				double C4 = Double.parseDouble(tab[9]);
				double C5 = Double.parseDouble(tab[10]);
				double C6 = Double.parseDouble(tab[11]);
				double C7 = Double.parseDouble(tab[12]);
				double C8 = Double.parseDouble(tab[13]);
				double H6 = Double.parseDouble(tab[19]);
				double SI = C1 + C2 + C3 + C4 + C5 + C6 + C7 + C8 + H6;
				w.write(NazwaKraju + "," + ZmianaProc + "," + String.format(new Locale("US"), "%12.2f", SI).trim()
						+ "\r\n");
			}
		} catch (Exception e)
		{
			System.out.println(s);
			System.out.println("Wystąpił błąd");
			e.printStackTrace();
		} finally
		{
			try
			{
				if (w != null)
					w.close();
				if (br != null)
					br.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}
	}

}
