package indeksy_covid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * Oblicza sredni indeks restrykcyjności
 * dla wszystkich krajów.
 * Nazwy krajów w języku angielskim
 * (jak w pliku OxCGRTsuma_ind2020.csv)
 * 
 * @author Andrzej Bystrzycki
 *
 */
public class SredniIndeksRestrykcyjnosci
{
	private static String KatalogWej = "c:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "c:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String Kodowanie = "utf-8";
	private static String NazwaPlikuWej = "OxCGRTsuma_ind2020.csv";
	private static String NazwaPlikuWyj = "sredni_indeks_restr2020.txt";
	private static String NazwaPlikuKom = "kom_sredni_indeks_restr2020.txt";

	public static void main(String[] args)
	{
		obliczZapiszSredniIndeksRestr();
	}

	private static void obliczZapiszSredniIndeksRestr()
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;
		
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), Kodowanie);
			
			while ((s = br.readLine()) != null)
			{
				String[] tab = s.split(",", -1);
				String NazwaKraju = tab[0];
				double C1 = Double.parseDouble(tab[1]);
				double C2 = Double.parseDouble(tab[2]);
				double C3 = Double.parseDouble(tab[3]);
				double C4 = Double.parseDouble(tab[4]);
				double C5 = Double.parseDouble(tab[5]);
				double C6 = Double.parseDouble(tab[6]);
				double C7 = Double.parseDouble(tab[7]);
				double C8 = Double.parseDouble(tab[8]);
				double H6 = Double.parseDouble(tab[14]);
				int LiczbaDni = Integer.parseInt(tab[17]);
				double SredniIndeksRestr = (C1 + C2 + C3 + C4 + C5 + C6 + C7 + C8 + H6) / 9 / LiczbaDni;
				w.write(NazwaKraju + "," + String.format(new Locale("US"), "%12.2f", SredniIndeksRestr).trim()
						+ "\r\n");
				
			}
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd " + s);
				e.printStackTrace();
			} finally
			{
				try
				{
					if (br != null)
						br.close();
					if (w != null)
						w.close();
					if (wkom != null)
						wkom.close();
				} catch (Exception e)
				{
					System.out.println("Wystąpił błąd finally");
				}
			}
		
	}

	public static String getNazwaPlikuWej()
	{
		return NazwaPlikuWej;
	}

	public static void setNazwaPlikuWej(String nazwaPlikuWej)
	{
		NazwaPlikuWej = nazwaPlikuWej;
	}

	public static String getNazwaPlikuWyj()
	{
		return NazwaPlikuWyj;
	}

	public static void setNazwaPlikuWyj(String nazwaPlikuWyj)
	{
		NazwaPlikuWyj = nazwaPlikuWyj;
	}

	public static String getNazwaPlikuKom()
	{
		return NazwaPlikuKom;
	}

	public static void setNazwaPlikuKom(String nazwaPlikuKom)
	{
		NazwaPlikuKom = nazwaPlikuKom;
	}
}
