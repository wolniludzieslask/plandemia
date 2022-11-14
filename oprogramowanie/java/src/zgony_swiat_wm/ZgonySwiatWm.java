package zgony_swiat_wm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * Sumuje liczby zgonów dla każdego kraju.
 * Plikiem wejściowym jest plik otrzymany z klasy FormatZgonySwiatWm.
 * Plikiem wyściowym jest plik w formacie
 * NazwaKraju,ŚredniaZgonów2018_2019,Zgony2020,Zmiana,ZmianaProcentowa,LiczbaOkresów,RodzajOkresu 
 * Rodzaj okresu to T - tygodniowo, M - miesięcznie
 */
public class ZgonySwiatWm
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	//private static String KatalogWej = "C:\\rob\\world_mortality\\";
	//private static String KatalogWyj = "C:\\rob\\world_mortality\\";
	private static String KodowanieWej = "utf-8";
	private static String KodowanieWyj = "utf-8";
	//private static String NazwaPlikuKraj = "lista_krajow.csv";
	private static String NazwaPlikuWej = "zgony_wm2020.txt";
	private static String NazwaPlikuWyj = "zgony_wm2020suma.txt";
	private static String NazwaPlikuKom = "kom_zgony_wm2020suma.txt";

	public static void main(String[] args)
	{
		zapiszStatystyke(3);
	}

	public static void zapiszStatystyke(int _LiczbaLat)
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;

		try
		{
			String NazwaKrajuPoprz = "";
			String MiesTygPoprz = "";
			int[] SumaZgonow = new int[_LiczbaLat];
			int[] LiczbaZgonow = new int[_LiczbaLat];
			int LiczbaOkresow = 0;
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), KodowanieWej));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), KodowanieWyj);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), KodowanieWyj);
			
			while ((s = br.readLine()) != null)
			{
				if(s.startsWith("NazwaKraju")) continue;
				String[] stab = s.split(",");
				
				String NazwaKraju = stab[0];
				String MiesTyg = stab[1];
				
				for(int i=0; i < LiczbaZgonow.length; i++)
					LiczbaZgonow[i] = Integer.parseInt(stab[3+i]);
				
				
				if(!NazwaKrajuPoprz.equals(NazwaKraju) && !NazwaKrajuPoprz.equals(""))
				{
					int Suma = 0;
					for(int j=0; j < SumaZgonow.length - 1; j++)
						Suma += SumaZgonow[j];
					double SredniaDouble = Suma / (double)(LiczbaZgonow.length - 1);
					int Srednia = (int) Math.round(SredniaDouble);
					double Zmiana = 0.0;
					double ZmianaProc = 0.0;
					if (Srednia != 0)
					{
						Zmiana = SumaZgonow[SumaZgonow.length - 1] - Srednia;
						ZmianaProc = ((double) Zmiana) / Srednia * 100.0;
					}
					w.write(NazwaKrajuPoprz + "," + Srednia + "," + SumaZgonow[LiczbaZgonow.length - 1] + ","
							+ String.format(new Locale("US"), "%5.0f", Zmiana).trim() + ","
							+ String.format(new Locale("US"), "%6.2f", ZmianaProc).trim() + ","
							+ LiczbaOkresow + "," + MiesTygPoprz
							+ "\r\n");
					
					for(int i = 0; i < SumaZgonow.length; i++)
						SumaZgonow[i] = 0;
					LiczbaOkresow = 0;
					
				}
				
				for(int i=0; i < LiczbaZgonow.length; i++)
					SumaZgonow[i] += LiczbaZgonow[i];
				LiczbaOkresow++;
				
				NazwaKrajuPoprz = NazwaKraju;
				MiesTygPoprz = MiesTyg;
			}

			// Suma dla ostatniego kraju
			if(!NazwaKrajuPoprz.equals(""))
			{
				int Suma = 0;
				for(int j=0; j < SumaZgonow.length - 1; j++)
					Suma += SumaZgonow[j];
				double SredniaDouble = Suma / (double)(LiczbaZgonow.length - 1);
				int Srednia = (int) Math.round(SredniaDouble);
				double Zmiana = 0.0;
				double ZmianaProc = 0.0;
				if (Srednia != 0)
				{
					Zmiana = SumaZgonow[SumaZgonow.length - 1] - Srednia;
					ZmianaProc = ((double) Zmiana) / Srednia * 100.0;
				}
				w.write(NazwaKrajuPoprz + "," + Srednia + "," + SumaZgonow[LiczbaZgonow.length - 1] + ","
						+ String.format(new Locale("US"), "%5.0f", Zmiana).trim() + ","
						+ String.format(new Locale("US"), "%6.2f", ZmianaProc).trim() + ","
						+ LiczbaOkresow + "," + MiesTygPoprz
						+ "\r\n");
				
				for(int i = 0; i < SumaZgonow.length; i++)
					SumaZgonow[i] = 0;
				
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
				if(br != null)
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
}
