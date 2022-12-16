package indeksy_covid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.TreeMap;

import utilities.BOMSkipper;

/**
 * Tworzy listę krajów pochdzącą z pliku OxCGRT_latest.csv
 * Polskie nazwy i kody krajów są uzupełniane
 * z pliku lista_krajow.csv
 * Utworzona lista jest zapisywana do pliku.
 * Lista utworzona 28.05.2022 zawiera 187 krajów.
 * Dla porównania w projekcie world mortality (lista_krajow.csv) jest 120 krajów. 
 * 
 * @author Andrzej Bystrzycki
 *
 */
public class ListaKrajowCGRT
{
	private static String KatalogWej = "c:\\rob\\pandemia_statystyka\\";
	private static String KatalogWyj = "c:\\rob\\pandemia_statystyka\\";
	private static String Kodowanie = "utf-8";
	//private static String NazwaPlikuWej = "test_indeks.csv";
	private static String NazwaPlikuWej = "OxCGRT_latest.csv";
	private static String NazwaPlikuWyj = "lista_krajowCGRT.csv";
	private static String NazwaPlikuKom = "kom_listakrajowCGRT.txt";
	private static String NazwaPlikuKraj = "lista_krajow.csv";
	private static TreeMap<String, Kraj> Kraje = new TreeMap<String, Kraj>();
	private static TreeMap<String, String> KrajeCGRT = new TreeMap<String, String>();

	public static void main(String[] args)
	{
		czytajListeKrajow();
		zapiszListe();
	}

	public static void czytajListeKrajow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuKraj), Kodowanie));
			BOMSkipper.skip(br);
			while ((s = br.readLine()) != null)
			{
				Kraj KrajLok = new Kraj();
				String[] tab = s.split(",");

				KrajLok.Symbol = tab[2];
				KrajLok.Nazwa = tab[0];
				KrajLok.NazwaPolska = tab[1];
				
				//System.out.println(KrajLok.Symbol);
				//System.out.println(KrajLok.Nazwa);
				//System.out.println(KrajLok.NazwaPLiku);

				Kraje.put(KrajLok.Nazwa, KrajLok);
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

	private static void zapiszListe()
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;
		String NazwaKraju = "";

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), Kodowanie);
			
			while ((s = br.readLine()) != null)
			{
				if(!s.startsWith("CountryName"))
				{
					String[] tab = s.split(",",-1);
					NazwaKraju = tab[0];

					if(NazwaKraju.equals("")) continue;

					if(!KrajeCGRT.containsKey(NazwaKraju))
					{
						String NazwaPolskaKraju = "";
						String KodKraju = "";
						if(Kraje.containsKey(NazwaKraju))
						{
							Kraj KrajLok = Kraje.get(NazwaKraju);
							NazwaPolskaKraju = KrajLok.NazwaPolska;
							KodKraju = KrajLok.Symbol;
						}
						w.write(NazwaKraju+","+NazwaPolskaKraju+","+KodKraju+"\r\n");
						KrajeCGRT.put(NazwaKraju, NazwaKraju);
					}
					
				}

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
	
	static class Kraj
	{
		String Symbol;
		String Nazwa;
		String NazwaPolska;
	}

}
