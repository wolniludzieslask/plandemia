package konwersje;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.TreeMap;

/*
 * Klasa dodaje numery wierszy w każdej linii wejściowego pliku.
 * Przepisywane są tylko te wiersze, których nazwa kraju
 * znajduje się w pliku NazwaPlikuKraj.
 * Chodzi o to, że jeśli dane o zgonach nie są kompletne w ostatnim roku
 * (w tym wypadku chodzi o rok 2021)
 * to z tego powodu nie uwzględniam tego kraju w pozostałych zestawieniach.  
 */
public class DodanieNumerowWierszy2
{
	private static String KatalogWej = "c:\\rob\\world_mortality\\";
	private static String KatalogWyj = "c:\\rob\\world_mortality\\";
	private static String Kodowanie = "utf-8";
	private static String NazwaPlikuWej = "zgony_wm2020_od03_do08suma.in";
	private static String NazwaPlikuWyj = "zgony_wm2020_od03_do08suma.csv";
	private static String NazwaPlikuKraj = "zgony2020_2021suma.csv";
	private static TreeMap<String, String> ListaKrajow = new TreeMap<String, String>(); 

	public static void main(String[] args)
	{
		czytajListeKrajow();
		PrzepiszTotalTest();
	}

	/* Wczytuje listę krajów do mapy ListaKrajow z pliku NazwaPlikuKraj */
	public static void czytajListeKrajow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuKraj), Kodowanie));
			// Trzeba pominąć BOM, bo taki BOM byłby wczytywany do pierwszego łańcucha.
			while ((s = br.readLine()) != null)
			{
				String[] tab = s.split(",");
				String NazwaKraju = tab[0].substring(1, tab[0].length()-1);
				ListaKrajow.put(NazwaKraju, NazwaKraju);
			}

		} catch (Exception e)
		{
			System.out.println("czytajKodyKrajow() Wystąpił błąd " + s);
			e.printStackTrace();
		} finally
		{
			try
			{
				if (br != null)
					br.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}
	}
	
	/* dodaje numery wierszy w każdej linii wejściowego pliku */
	public static void PrzepiszTotalTest()
	{
		BufferedReader br = null;
		Writer w = null;
		String s = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			int NumerWiersza = 0;
			while ((s = br.readLine()) != null)
			{
				String[] tab = s.split(",");
				String NazwaKraju = tab[0].substring(1, tab[0].length()-1);
				if(ListaKrajow.containsKey(NazwaKraju))
				{
					NumerWiersza++;
					w.write(""+NumerWiersza+","+s+"\r\n");
				}
			}
		} catch (Exception e)
		{
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
