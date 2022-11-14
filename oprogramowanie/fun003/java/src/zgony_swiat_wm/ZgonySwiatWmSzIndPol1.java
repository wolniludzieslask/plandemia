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
 * Łączy w jednym pliku dane o zgonach, szczepieniach i indeksach polityki
 * dla poszczególnych krajów.
 * 
 * Nazwy krajów polskie z pliku lista_krajowCGRT.csv
 * 
 * Różnica w stosunku do klasy ZgonySwiatWmSzIndPol, 
 * to dodanie instrukcji continue, jak w poniższym kodzie
 * w metodzie zapiszStatystyke():
				if (IndeksRestr.containsKey(NazwaAngielskaKraju))
				{
					IndeksRestrykcyjnosci = IndeksRestr.get(NazwaAngielskaKraju);
					if(NazwaKraju.equals("Peru"))
						System.out.println("peru IndeksRestr.containsKey");
				}
				// Jeśli nie ma indeksu rekstrykcyjności, to pomijamy
				// Jedyna różnica w stosunku do klasy ZgonySwiatWmSzIndPol
				// to dodanie poniższej instrukcji continue
				else
					continue;
					
 * @author Andrzej Bystrzycki
 */
public class ZgonySwiatWmSzIndPol1
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	//private static String KatalogWej = "C:\\rob\\world_mortality\\";
	//private static String KatalogWyj = "C:\\rob\\world_mortality\\";
	private static String KodowanieWej = "utf-8";
	private static String KodowanieWyj = "utf-8";
	//private static String NazwaPlikuWej = "zgony_wm2021suma.csv";
	private static String NazwaPlikuWej = "zgony_wm2021suma.txt";
	private static String NazwaPlikuWyj = "zgony_wm_sz_ir2021suma_ind.txt";
	private static String NazwaPlikuKom = "kom_zgony_wm_sz_ir2021suma_ind.txt";
	private static String NazwaPlikuSzczepien = "szczepienia_vac2021.txt";
	private static String NazwaPlikuIndeksuRestr = "OxCGRTsuma_ind2021.csv";
	private static String NazwaPlikuKraj = "lista_krajowCGRT.csv";
	private static TreeMap<String, String> Szczepienia = new TreeMap<String, String>();
	private static TreeMap<String, String> IndeksRestr = new TreeMap<String, String>();
	private static TreeMap<String, Kraj> Kraje = new TreeMap<String, Kraj>();

	public static void main(String[] args)
	{
		czytajListeKrajow();
		czytajPoziomSzczepien();
		czytajIndeksyPolityki();
		zapiszStatystyke();
	}

	public static void czytajListeKrajow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuKraj), KodowanieWej));
			BOMSkipper.skip(br);
			while ((s = br.readLine()) != null)
			{
				Kraj KrajLok = new Kraj();
				String[] tab = s.split(",",-1);

				KrajLok.Symbol = tab[2];
				KrajLok.Nazwa = tab[0];
				KrajLok.NazwaPolska = tab[1];

				// System.out.println(KrajLok.Symbol);
				// System.out.println(KrajLok.Nazwa);
				// System.out.println(KrajLok.NazwaPLiku);

				Kraje.put(KrajLok.NazwaPolska, KrajLok);
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

	public static void czytajPoziomSzczepien()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuSzczepien), KodowanieWej));
			while ((s = br.readLine()) != null)
			{
				String[] tab = s.split(",");

				String PoziomSzczepien = tab[2];

				Szczepienia.put(tab[0], PoziomSzczepien);
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

	public static void czytajIndeksyPolityki()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuIndeksuRestr), KodowanieWej));
			while ((s = br.readLine()) != null)
			{
				//String[] tab = s.split(",");
				//String[] tab = s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
				int indeks = s.indexOf(",");
				int OstIndeks = s.lastIndexOf(",");
				
				String NazwaKraju = s.substring(0, indeks);
				String IndeksRestrLok = s.substring(indeks+1,OstIndeks);

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

	public static void zapiszStatystyke()
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), KodowanieWej));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), KodowanieWyj);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), KodowanieWyj);

			while ((s = br.readLine()) != null)
			{
				if (s.startsWith("NazwaKraju"))
					continue;
				// stackoverflow splitting a csv file with quotes as text-delimiter using
				String[] stab = s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");;

				// NazwaKraju bez cudzysłowów
				//String NazwaKraju = stab[0].substring(1, stab[0].length()-1);
				// zakładam, że nie ma cudzysłowu
				String NazwaKraju = stab[0];
				String MiesTyg = stab[6];

				String LiczbaZgonowPoprz = stab[1];
				String LiczbaZgonow = stab[2];
				String Zmiana = stab[3];
				String ZmianaProc = stab[4];
				String LiczbaOkresow = stab[5];
				String PoziomSzczepien = "";
				String NazwaAngielskaKraju = "";
				String IndeksRestrykcyjnosci = ",,,,,,,,,,,,,,,";
				
				if (Kraje.containsKey(NazwaKraju))
					NazwaAngielskaKraju = Kraje.get(NazwaKraju).Nazwa;
				if (Szczepienia.containsKey(NazwaAngielskaKraju))
					PoziomSzczepien = Szczepienia.get(NazwaAngielskaKraju);
				
				if (IndeksRestr.containsKey(NazwaAngielskaKraju))
				{
					IndeksRestrykcyjnosci = IndeksRestr.get(NazwaAngielskaKraju);
					if(NazwaKraju.equals("Peru"))
						System.out.println("peru IndeksRestr.containsKey");
				}
				// Jeśli nie ma indeksu rekstrykcyjności, to pomijamy
				// Jedyna różnica w stosunku do klasy ZgonySwiatWmSzIndPol
				// to dodanie poniższej instrukcji continue
				else
					continue;

				if(NazwaKraju.equals("Peru"))
					System.out.println("NazwaKraju="+NazwaKraju+ "< NazwaAngielskaKraju="+NazwaAngielskaKraju+"< IndeksRestrykcyjnosci="+IndeksRestrykcyjnosci);
				
				w.write(NazwaKraju + "," + LiczbaZgonowPoprz + "," + LiczbaZgonow + "," + Zmiana + "," + ZmianaProc
						+ "," + PoziomSzczepien + "," + IndeksRestrykcyjnosci + "," + LiczbaOkresow + "," + MiesTyg
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

	public static String getNazwaPlikuIndeksuRestr()
	{
		return NazwaPlikuIndeksuRestr;
	}

	public static void setNazwaPlikuIndeksuRestr(String nazwaPlikuIndeksuRestr)
	{
		NazwaPlikuIndeksuRestr = nazwaPlikuIndeksuRestr;
	}

	static class Kraj
	{
		String Symbol;
		String Nazwa;
		String NazwaPolska;
	}
}
