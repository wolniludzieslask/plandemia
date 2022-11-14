package zgony_swiat_wm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;



/**
 * Z listy krajów z roku 2020 przepisywane są tylko kraje,
 * które występują na liście w roku 2021.
 * Tworzone są dwa nowe pliki dla roku 2020 i 2021 z tą samą listą krajów.
 */
@SuppressWarnings("unused")
public class ZgonyDopasowanieKrajow2020_2021
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String Kodowanie = "utf-8";
	private static String Kodowanie1 = "utf-8";
	private static String NazwaPlikuWej = "zgony_wm_sz_ir2021suma_ind1.csv";
	private static String NazwaPlikuWej1 = "zgony_wm_sz_ir2020suma_ind1.csv";
	private static String NazwaPlikuWyj = "zgony_wm_sz_ir2020suma_ind2.csv";
	private static String NazwaPlikuWyj1 = "zgony_wm_sz_ir2021suma_ind2.csv";
	private static String NazwaPlikuKom = "kom_zgony_wm_sz_ir2021suma_ind.txt";

	public static void main(String[] args)
	{
		BufferedReader br = null;
		BufferedReader br1 = null;
		String s = null;
		Writer w = null;
		Writer w1 = null;
		TreeMap<String, String> Kraje = new TreeMap<String, String>(); 
		Writer wkom = null;

		try
		{		
			// czytanie pliku C:\\rob\\world_mortality\\fun002\\rob\\zgony_wm_sz_ir2021suma_ind1.csv
			// (z roku 2021)
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				Kraj KrajLok = new Kraj();
				String[] tab = s.split(",");

				int indeks = s.indexOf(",");
				String Nazwa = tab[0].substring(1, tab[0].length()-1);
				String Linia = s.substring(indeks);
				
				//System.out.println(Nazwa);
				//System.out.println(Linia);

				Kraje.put(Nazwa, Linia);
			}
			
			// czytanie pliku C:\\rob\\world_mortality\\fun002\\rob\\zgony_wm_sz_ir2020suma_ind1.csv
			// (z roku 2020)
			br1 = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuWej1), Kodowanie1));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie1);
			w1 = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj1), Kodowanie1);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), Kodowanie1);
			while ((s = br1.readLine()) != null)
			{
				String[] tab = s.split(",");

				int indeks = s.indexOf(",");
				String Nazwa = tab[0].substring(1, tab[0].length()-1);
				String Linia = s.substring(indeks);

				// 
				if(Kraje.containsKey(Nazwa))
				{
					// zapis dla roku 2020
					w.write(Nazwa + Linia + "\r\n");
					String Linia1 = Kraje.get(Nazwa);
					// zapis dla roku 2021
					w1.write(Nazwa + Linia1 + "\r\n");
				}
				// jeśli nie ma kraju w pliku C:\\rob\\world_mortality\\fun002\\rob\\zgony_wm_sz_ir2021suma_ind1.csv
				// to zapis do pliku komunikatów
				else 
				{
					wkom.write("Brak kraju w roku 2021\r\n" + s + "\r\n");
					//System.out.println(s);
				}
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
				if (br != null)
					br.close();
				if (br1 != null)
					br1.close();
				if (w != null)
					w.close();
				if (w1 != null)
					w1.close();
				if (wkom != null)
					wkom.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}

	}

	static class Kraj
	{
		String Linia;
	}
}
