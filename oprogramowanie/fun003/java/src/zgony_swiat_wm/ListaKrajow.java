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
 * Tworzy listę krajów z pliku z katalogu world_mortality.
 */
@SuppressWarnings("unused")
public class ListaKrajow
{
	private static String KatalogWej = "C:\\rob\\world_mortality\\";
	private static String KatalogWyj = "c:\\rob\\world_mortality\\";
	private static String KodowanieWe = "utf-8";
	//private static String KodowanieWe = "cp1252";
	private static String KodowanieWy = "utf-8";
	private static String NazwaPlikuWej = "world_mortality2022_10_03.csv";
	private static String NazwaPlikuWyj = "lista_krajow.txt";

	public static void main(String[] args)
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		TreeMap<String, String> ListaKrajow = new TreeMap<String, String>(); 

		try
		{				
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), KodowanieWe));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), KodowanieWy);
			while ((s = br.readLine()) != null)
			{
				if (!s.startsWith("iso3c"))
				{
					String[] stab = s.split(",");
					if(!ListaKrajow.containsKey(stab[1]))
					{
						ListaKrajow.put(stab[1], stab[1]);
						w.write(stab[1] + "\r\n");
					}
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
				if (w != null)
					w.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}

	}

}
