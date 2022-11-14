package indeksy_covid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;

import indeksy_covid.IndeksPolityki;

/**
 * Oblicza sumaryczne pojedyncze indeksy polityki
 * dla wszystkich krajów
 * (pojedyncze - C1,C2 itd. a nie zbiorowe, np. stringency index)
 * Obliczone indeksy w podanym przedziale dat są zapisywane do pliku wyjściowego.
 * Każdy indeks jest jedną liczbą w podanym przedziale dat.
 * 
 * @author Andrzej Bystrzycki
 */
public class SumaryczneIndeksyPolityki
{
	//private static String KatalogWej = "c:\\rob\\pandemia_statystyka\\";
	//private static String KatalogWyj = "c:\\rob\\pandemia_statystyka\\";
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String Kodowanie = "Cp1250";
	//private static String NazwaPlikuWej = "test_indeks.csv";
	private static String NazwaPlikuWej = "OxCGRT_latest.csv";
	private static String NazwaPlikuWyj = "OxCGRTsuma_ind2021.csv";
	private static String NazwaPlikuKom = "kom_CGRTsuma_ind2021.txt";

	public static void main(String[] args)
	{
		obliczIndeks("20210101", "20211231");
	}

	public static void obliczIndeks(String _DataOd, String _DataDo)
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;
		String NazwaKraju = "";
		String NazwaKraju1 = "";
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
			
			while ((s = br.readLine()) != null)
			{
				if(!s.startsWith("CountryName"))
				{
					String[] tab = s.split(",",-1);
					String Data = tab[5];
					String Jurysdykcja = tab[4];
					NazwaKraju = tab[0];
							
					if(_DataOd.compareTo(Data) > 0 || _DataDo.compareTo(Data) < 0 || NazwaKraju.equals("") || !Jurysdykcja.equals("NAT_TOTAL")) continue;
					
					// NazwaKraju1 = nazwa kraju przed zmianą nazwy
					if (NazwaKraju1.compareTo(NazwaKraju) != 0 && !NazwaKraju1.equals(""))
					{
						w.write(NazwaKraju1+","
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC1).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC2).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC3).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC4).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC5).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC6).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC7).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksC8).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksE1).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksE2).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksH1).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksH2).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksH3).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksH6).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksH7).trim() + "," 
								+String.format(new Locale("US"), "%11.2f", SumaIndeksH8).trim() + 
								"," + LiczbaDni + "\r\n");
						
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
					double C2 = IndeksPolityki.obliczIndeks("C2", tab[8], tab[9]);
					//System.out.println("C2="+C2);
					double C3 = IndeksPolityki.obliczIndeks("C3", tab[10], tab[11]);
					//System.out.println("C3="+C3);
					double C4 = IndeksPolityki.obliczIndeks("C4", tab[12], tab[13]);
					//System.out.println("C4="+C4);
					double C5 = IndeksPolityki.obliczIndeks("C5", tab[14], tab[15]);
					//System.out.println("C5="+C5);
					double C6 = IndeksPolityki.obliczIndeks("C6", tab[16], tab[17]);
					//System.out.println("C6="+C6);
					double C7 = IndeksPolityki.obliczIndeks("C7", tab[18], tab[19]);
					//System.out.println("C7="+C7);
					double C8 = IndeksPolityki.obliczIndeks("C8", tab[20], "N/A");
					//System.out.println("C8="+C8);
					double E1 = IndeksPolityki.obliczIndeks("E1", tab[21], tab[22]);
					//System.out.println("E1="+E1);
					double E2 = IndeksPolityki.obliczIndeks("E2", tab[23], "N/A");
					//System.out.println("E2="+E2);
					double H1 = IndeksPolityki.obliczIndeks("H1", tab[26], tab[27]);
					//System.out.println("H1="+H1);
					double H2 = IndeksPolityki.obliczIndeks("H2", tab[28], "N/A");
					//System.out.println("H2="+H2);
					double H3 = IndeksPolityki.obliczIndeks("H3", tab[29], "N/A");
					//System.out.println("H3="+H3);
					double H6 = IndeksPolityki.obliczIndeks("H6", tab[32], tab[33]);
					//System.out.println("H6="+H6);
					double H7 = IndeksPolityki.obliczIndeks("H7", tab[34], tab[35]);
					//System.out.println("H7="+H7);
					double H8 = IndeksPolityki.obliczIndeks("H8", tab[36], tab[37]);
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
					NazwaKraju1 = NazwaKraju;
					
				}

			}

			// indeks dla ostatniego kraju
			if(!NazwaKraju.equals(""))
			{
				//double SredniIndeks = SumaIndeks / LiczbaDni;
				w.write(NazwaKraju1+","
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC1).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC2).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC3).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC4).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC5).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC6).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC7).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksC8).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksE1).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksE2).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksH1).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksH2).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksH3).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksH6).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksH7).trim() + "," 
						+String.format(new Locale("US"), "%11.2f", SumaIndeksH8).trim() + 
						"," + LiczbaDni + "\r\n");
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
	
}
