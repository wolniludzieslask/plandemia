package zgony_usa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.TreeMap;

import utilities.BOMSkipper;

/**
 * Łączy w jednym pliku dane o zgonach i indeksach polityki
 * dla poszczególnych stanów w roku 2020.
 * 
 * @author Andrzej Bystrzycki
 */
public class ZgonyUSASumIndRestr
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	//private static String KatalogWej = "C:\\rob\\zgony_usa\\";
	//private static String KatalogWyj = "C:\\rob\\zgony_usa\\";
	private static String KodowanieWej = "utf-8";
	private static String KodowanieWyj = "utf-8";
	private static String NazwaPlikuWej = "zgony2020us.txt";
	private static String NazwaPlikuWyj = "zgony_us_sir2020.txt";
	private static String NazwaPlikuKom = "kom_zgony_us_sir2020.txt";
	private static String NazwaPlikuSzczepien = "szczepienia_vac2020.txt";
	private static String NazwaPlikuIndeksuRestr = "OxCGRT_US_suma_ind_restr2020.csv";
	private static String NazwaPlikuStan = "stany1.txt";
	private static TreeMap<String, String> Szczepienia = new TreeMap<String, String>();
	private static TreeMap<String, String> IndeksRestr = new TreeMap<String, String>();
	private static TreeMap<String, Stan> Stany = new TreeMap<String, Stan>();

	public static void main(String[] args)
	{
		czytajListeStanow();
		czytajIndeksRestrykcyjnosci();
		// nie czytam pliku szczepień, bo to jest rok 2020
		zapiszStatystyke();
	}

	public static String getNazwaPlikuSzczepien()
	{
		return NazwaPlikuSzczepien;
	}

	public static void setNazwaPlikuSzczepien(String nazwaPlikuSzczepien)
	{
		NazwaPlikuSzczepien = nazwaPlikuSzczepien;
	}

	public static void czytajListeStanow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuStan), KodowanieWej));
			BOMSkipper.skip(br);
			while ((s = br.readLine()) != null)
			{
				Stan StanLok = new Stan();
				String[] tab = s.split(",",-1);

				StanLok.Nazwa = tab[0];
				if(tab.length > 1)
					StanLok.Partia = tab[1];

				// System.out.println(KrajLok.Symbol);
				// System.out.println(KrajLok.Nazwa);
				// System.out.println(KrajLok.NazwaPLiku);

				Stany.put(StanLok.Nazwa, StanLok);
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

	public static void czytajIndeksRestrykcyjnosci()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuIndeksuRestr), KodowanieWej));
			while ((s = br.readLine()) != null)
			{
				String[] tab = s.split(",");
				
				String NazwaStanu = tab[0];
				String IndeksRestrLok = tab[1];

				IndeksRestr.put(NazwaStanu, IndeksRestrLok);
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
				if (s.startsWith("NazwaStanu"))
					continue;
				
				// stany pomijane
				if (s.startsWith("Puerto Rico"))
					continue;
				
				String[] stab = s.split(",",-1);;

				String NazwaStanu = stab[0];
				//String MiesTyg = stab[6];


				String LiczbaZgonowPoprz = stab[1];
				String LiczbaZgonow = stab[2];
				String Zmiana = stab[3];
				String ZmianaProc = stab[4];
				String LiczbaOkresow = stab[7];
				String PoziomSzczepien = "";
				String IndeksRestrykcyjnosci = ",,";
				String Partia = "";
				
				if (Stany.containsKey(NazwaStanu))
					Partia = Stany.get(NazwaStanu).Partia;
				
				if (Szczepienia.containsKey(NazwaStanu))
					PoziomSzczepien = Szczepienia.get(NazwaStanu);
				
				if (IndeksRestr.containsKey(NazwaStanu))
				{
					IndeksRestrykcyjnosci = IndeksRestr.get(NazwaStanu);
					if(NazwaStanu.equals("Alaska"))
						System.out.println("alaska IndeksRestr.containsKey");
				}
				else
				{
					if (s.startsWith("District of Columbia"))
						IndeksRestrykcyjnosci = IndeksRestr.get("Washington DC");					
				}

				if(NazwaStanu.equals("Alaska"))
				{
					System.out.println("NazwaStanu="+NazwaStanu+" IndeksRestrykcyjnosci="+IndeksRestrykcyjnosci);
					System.out.println("NazwaStanu="+NazwaStanu+" LiczbaOkresow="+LiczbaOkresow);
				}
				
				w.write(NazwaStanu + "," + LiczbaZgonowPoprz + "," + LiczbaZgonow + "," + Zmiana + "," + ZmianaProc
						+ "," + PoziomSzczepien + "," + IndeksRestrykcyjnosci + "," + LiczbaOkresow + "," + Partia 
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

	static class Stan
	{
		String Nazwa;
		String Partia;
	}
}
