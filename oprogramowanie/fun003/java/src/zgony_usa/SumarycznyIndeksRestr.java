package zgony_usa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.TreeMap;

import indeksy_covid.IndeksPolityki;
import utilities.BOMSkipper;

/**
 * Oblicza sumaryczne pojedyncze indeksy polityki
 * dla wszystkich stanów
 * (pojedyncze - C1,C2 itd. a nie zbiorowe, np. stringency index)
 * a potem sumaryczny indeks restrykcyjności
 * (C1+C2+C3+C4+C5+C6+C7+C8+H6)
 * 
 * @author Andrzej Bystrzycki
 */
public class SumarycznyIndeksRestr
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	//private static String KatalogWej = "c:\\rob\\zgony_usa\\";
	//private static String KatalogWyj = "c:\\rob\\zgony_usa\\";
	private static String Kodowanie = "Cp1250";
	//private static String NazwaPlikuWej = "test_indeks.csv";
	private static String NazwaPlikuWej = "OxCGRT_US_latest.csv";
	private static String NazwaPlikuWyj = "OxCGRT_US_suma_ind_restr2020.csv";
	private static String NazwaPlikuKom = "kom_CGRT_US_suma_ind_restr2020.txt";
	private static String NazwaPlikuStan = "stany1.txt";
	private static TreeMap<String, Stan> Stany = new TreeMap<String, Stan>();

	public static void main(String[] args)
	{
		czytajListeStanow();
		obliczIndeks("20200101", "20201231");
	}

	public static void czytajListeStanow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuStan), Kodowanie));
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

	public static void obliczIndeks(String _DataOd, String _DataDo)
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;
		String NazwaStanu = "";
		String NazwaStanu1 = "";
		int LiczbaDni = 0;
		double SumaIndeksC1 = 0.0;
		double SumaIndeksC2 = 0.0;
		double SumaIndeksC3 = 0.0;
		double SumaIndeksC4 = 0.0;
		double SumaIndeksC5 = 0.0;
		double SumaIndeksC6 = 0.0;
		double SumaIndeksC7 = 0.0;
		double SumaIndeksC8 = 0.0;
		double SumaIndeksE1 = 0.0;
		double SumaIndeksE2 = 0.0;
		double SumaIndeksH1 = 0.0;
		double SumaIndeksH2 = 0.0;
		double SumaIndeksH3 = 0.0;
		double SumaIndeksH6 = 0.0;
		double SumaIndeksH7 = 0.0;
		double SumaIndeksH8 = 0.0;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), Kodowanie);
			
			double IndeksRestr = 0.0;
			
			while ((s = br.readLine()) != null)
			{
				if(!s.startsWith("CountryName"))
				{
					//String[] tab = s.split(",",-1);
					// stackoverflow splitting a csv file with quotes as text-delimiter using
					String[] tab = s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
					String Data = tab[5];
					String Jurysdykcja = tab[4];
					NazwaStanu = tab[2];
							
					if(_DataOd.compareTo(Data) > 0 || _DataDo.compareTo(Data) < 0 || NazwaStanu.equals("") || !Jurysdykcja.equals("STATE_WIDE")) continue;
					
					// NazwaStanu1 = nazwa stanu przed zmianą nazwy
					if (NazwaStanu1.compareTo(NazwaStanu) != 0 && !NazwaStanu1.equals(""))
					{
						String Partia = "";						
						if (Stany.containsKey(NazwaStanu1))
							Partia = Stany.get(NazwaStanu1).Partia;
						
						IndeksRestr = SumaIndeksC1 + SumaIndeksC2 + SumaIndeksC3 + SumaIndeksC4 + SumaIndeksC5 + SumaIndeksC6 + SumaIndeksC7 + SumaIndeksC8 + SumaIndeksH6;
						w.write(NazwaStanu1+","
								+String.format(new Locale("US"), "%11.2f", IndeksRestr).trim() +
								"," + LiczbaDni + "," + Partia + "\r\n");
						
						SumaIndeksC1 = 0;
						SumaIndeksC2 = 0;
						SumaIndeksC3 = 0;
						SumaIndeksC4 = 0;
						SumaIndeksC5 = 0;
						SumaIndeksC6 = 0;
						SumaIndeksC7 = 0;
						SumaIndeksC8 = 0;
						SumaIndeksE1 = 0;
						SumaIndeksE2 = 0;
						SumaIndeksH1 = 0;
						SumaIndeksH2 = 0;
						SumaIndeksH3 = 0;
						SumaIndeksH6 = 0;
						SumaIndeksH7 = 0;
						SumaIndeksH8 = 0;
						LiczbaDni = 0;
					}
					
					double C1 = IndeksPolityki.obliczIndeks("C1", tab[6], tab[7]);
					//System.out.println("C1="+C1);
					double C2 = IndeksPolityki.obliczIndeks("C2", tab[9], tab[10]);
					//System.out.println("C2="+C2);
					double C3 = IndeksPolityki.obliczIndeks("C3", tab[12], tab[13]);
					//System.out.println("C3="+C3);
					double C4 = IndeksPolityki.obliczIndeks("C4", tab[15], tab[16]);
					//System.out.println("C4="+C4);
					double C5 = IndeksPolityki.obliczIndeks("C5", tab[18], tab[19]);
					//System.out.println("C5="+C5);
					double C6 = IndeksPolityki.obliczIndeks("C6", tab[21], tab[22]);
					//System.out.println("C6="+C6);
					double C7 = IndeksPolityki.obliczIndeks("C7", tab[24], tab[25]);
					//System.out.println("C7="+C7);
					double C8 = IndeksPolityki.obliczIndeks("C8", tab[27], "N/A");
					//System.out.println("C8="+C8);
					double E1 = IndeksPolityki.obliczIndeks("E1", tab[29], tab[30]);
					//System.out.println("E1="+E1);
					double E2 = IndeksPolityki.obliczIndeks("E2", tab[32], "N/A");
					//System.out.println("E2="+E2);
					double H1 = IndeksPolityki.obliczIndeks("H1", tab[38], tab[39]);
					//System.out.println("H1="+H1);
					double H2 = IndeksPolityki.obliczIndeks("H2", tab[41], "N/A");
					//System.out.println("H2="+H2);
					double H3 = IndeksPolityki.obliczIndeks("H3", tab[43], "N/A");
					//System.out.println("H3="+H3);
					double H6 = IndeksPolityki.obliczIndeks("H6", tab[49], tab[50]);
					//System.out.println("H6="+H6);
					double H7 = IndeksPolityki.obliczIndeks("H7", tab[52], tab[53]);
					//System.out.println("H7="+H7);
					double H8 = IndeksPolityki.obliczIndeks("H8", tab[55], tab[56]);
					//System.out.println("H8="+H8);
					
					double GRI = (C1+C2+C3+C4+C5+C6+C7+C8+E1+E2+H1+H2+H3+H6+H7+H8)/16;
					String sGRI = String.format(new Locale("US"), "%6.2f", GRI).trim();
					//System.out.println("GRI="+GRI);
					double CHI = (C1+C2+C3+C4+C5+C6+C7+C8+H1+H2+H3+H6+H7+H8)/14;
					String sCHI = String.format(new Locale("US"), "%6.2f", CHI).trim();
					//System.out.println("CHI="+CHI);
					//double SI = (C1+C2+C3+C4+C5+C6+C7+C8+H1)/ 9;
					//double SI = C1+C2+C3+C4+C5+C6+C7+C8+H1;
					double SI = C1+C2+C3+C4+C5+C6+C7+C8+H6;
					String sSI = String.format(new Locale("US"), "%6.2f", SI).trim();
					//System.out.println("SI="+SI);
					double ESI = (E1+E2)/ 2;
					String sESI = String.format(new Locale("US"), "%6.2f", ESI).trim();
					//System.out.println("ESI="+ESI);
					
					//SumaIndeks += SI;
					SumaIndeksC1 += C1;
					SumaIndeksC2 += C2;
					SumaIndeksC3 += C3;
					SumaIndeksC4 += C4;
					SumaIndeksC5 += C5;
					SumaIndeksC6 += C6;
					SumaIndeksC7 += C7;
					SumaIndeksC8 += C8;
					SumaIndeksE1 += E1;
					SumaIndeksE2 += E2;
					SumaIndeksH1 += H1;
					SumaIndeksH2 += H2;
					SumaIndeksH3 += H3;
					SumaIndeksH6 += H6;
					SumaIndeksH7 += H7;
					SumaIndeksH8 += H8;
					LiczbaDni++;
					NazwaStanu1 = NazwaStanu;
					
				}

			}

			// indeks dla ostatniego stanu
			if(!NazwaStanu.equals(""))
			{
				String Partia = "";				
				if (Stany.containsKey(NazwaStanu1))
					Partia = Stany.get(NazwaStanu1).Partia;
				
				w.write(NazwaStanu1+","
						+String.format(new Locale("US"), "%11.2f", IndeksRestr).trim() +
						"," + LiczbaDni + "," + Partia + "\r\n");
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
	
	static class Stan
	{
		String Nazwa;
		String Partia;
	}
}
