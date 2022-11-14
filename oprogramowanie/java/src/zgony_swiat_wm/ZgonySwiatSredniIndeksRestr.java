package zgony_swiat_wm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.TreeMap;

import utilities.BOMSkipper;

/**
 * Zapisuje w jednym pliku wzrost liczby zgonów i średni indeks restrykcyjności
 * dla każdego kraju.
 * 
 * @author Andrzej Bystrzycki
 *
 */
public class ZgonySwiatSredniIndeksRestr
{
	private static String KatalogWej = "c:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "c:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String Kodowanie = "utf-8";
	//private static String NazwaPlikuWej = "zgony_wm2020suma.txt";
	private static String NazwaPlikuWej = "zgony_wm_sz_ir2020suma_ind2.csv";	
	private static String NazwaPlikuWyj = "zgony_sredni_indeks_restr2020.txt";
	private static String NazwaPlikuIndeksuRestr = "sredni_indeks_restr2020.txt";
	private static String NazwaPlikuKom = "kom_zgony_sredni_indeks_restr2020.txt";
	private static String NazwaPlikuKraj = "lista_krajowCGRT.csv";
	private static TreeMap<String, String> Kraje = new TreeMap<String, String>();
	private static TreeMap<String, String> IndeksRestr = new TreeMap<String, String>();

	public static void main(String[] args)
	{
		czytajListeKrajow();
		czytajIndeksyRestr();
		zapiszZgonySredniIndeks();
	}

	public static void czytajListeKrajow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuKraj), Kodowanie));
			BOMSkipper.skip(br);
			while ((s = br.readLine()) != null)
			{
				String[] tab = s.split(",",-1);

				String NazwaAngielska = tab[0];
				String NazwaPolska = tab[1];

				// System.out.println(KrajLok.NazwaPolska);
				// System.out.println(KrajLok.NazwaPLiku);

				Kraje.put(NazwaPolska, NazwaAngielska);
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

	public static void czytajIndeksyRestr()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuIndeksuRestr), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				int indeks = s.indexOf(",",-1);
				
				String NazwaKraju = s.substring(0, indeks);
				String IndeksRestrLok = s.substring(indeks+1);

				IndeksRestr.put(NazwaKraju, IndeksRestrLok);
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
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}
	}

	private static void zapiszZgonySredniIndeks()
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
				String NazwaPolskaKraju = tab[0];
				String ZmianaProc = tab[1];
				String NazwaAngielskaKraju = "";
				String IndeksRestrykcyjnosci = "";
				
				if (Kraje.containsKey(NazwaPolskaKraju))
					NazwaAngielskaKraju = Kraje.get(NazwaPolskaKraju);
				if (IndeksRestr.containsKey(NazwaAngielskaKraju))
				{
					IndeksRestrykcyjnosci = IndeksRestr.get(NazwaAngielskaKraju);
					//if(NazwaKraju.equals("Peru"))
						//System.out.println("peru IndeksRestr.containsKey");
				}
				w.write(NazwaPolskaKraju + "," + ZmianaProc + "," +
						IndeksRestrykcyjnosci + "\r\n");
				
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
}
