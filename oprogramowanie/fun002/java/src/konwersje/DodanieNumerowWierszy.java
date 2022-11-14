package konwersje;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/* Klasa dodaje numery wierszy w każdej linii wejściowego pliku.*/
public class DodanieNumerowWierszy
{
	private static String KatalogWej = "c:\\rob\\world_mortality\\";
	private static String KatalogWyj = "c:\\rob\\world_mortality\\";
	private static String Kodowanie = "utf-8";
	private static String NazwaPlikuWej = "zgony_wm2021zmiana_od2020suma.in";
	private static String NazwaPlikuWyj = "zgony_wm2021zmiana_od2020suma1.csv";

	public static void main(String[] args)
	{
		dodajNumeryWierszy();
	}

	public static void dodajNumeryWierszy()
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
				NumerWiersza++;
				w.write(""+NumerWiersza+","+s+"\r\n");
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
