package zgony_usa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

/**
 * Tworzy listę stanów w USA na podstawie pliku ze strony
 * https://data.cdc.gov/NCHS/Weekly-Counts-of-Deaths-by-State-and-Select-Causes/3yf8-kanr
 * i zapisuje te stany do pliku.
 * 
 * @author Andrzej Bystrzycki
 *
 */
public class ListaStanow
{
	private static String KatalogWej = "c:\\rob\\zgony_usa\\";
	private static String KatalogWyj = "c:\\rob\\zgony_usa\\";
	private static String Kodowanie = "Cp1250";
	private static String NazwaPlikuWej = "Weekly_Counts_of_Deaths_by_State_and_Select_Causes__2014-2019.csv";
	private static String NazwaPlikuWyj = "stany1.txt";
	//private static String NazwaPlikuKom = "komunikaty.txt";

	public static void main(String[] args)
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		TreeMap<String, String> Stany = new TreeMap<String, String>();

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				if(!s.startsWith("Jurisdiction of Occurrence"))
				{
					String[] stab = s.split(",");
					Stany.put(stab[0], stab[0]);
				}
			}
			
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			for (Map.Entry<String, String> StanLok : Stany.entrySet())
			{
				w.write(StanLok.getKey() + "\r\n");
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
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}

	}

}
