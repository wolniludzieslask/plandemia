package zgony_swiat_wm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;


/*
 * Sumuje liczby zgonów dla każdego kraju.
 * Plikiem wejściowym jest plik otrzymany z klasy FormatZgonySwiatWm2020_2021.
 * Plikiem wyściowym jest plik w formacie
 * NazwaKraju,SumaZgonów2018_2019,SumaZgonów2020_2021,Zmiana,ZmianaProcentowa,LiczbaOkresów,RodzajOkresu 
 * Rodzaj okresu to T - tygodniowo, M - miesięcznie
 * Patrz plik barbados2020_2021tabela_test.JPG w katalogu testy.
 */
public class ZgonySwiatSumyWm
{
	private static String KatalogWej = "C:\\rob\\world_mortality\\";
	private static String KatalogWyj = "C:\\rob\\world_mortality\\";
	private static String KodowanieWej = "utf-8";
	private static String KodowanieWyj = "utf-8";
	private static String NazwaPlikuWej = "zgony_wm2020_2021.txt";
	private static String NazwaPlikuWyj = "zgony2020_2021suma.txt";
	private static String NazwaPlikuKom = "komunikaty2020_2021suma.txt";

	public static void main(String[] args)
	{
		zapiszStatystyke(2);
	}

	/*
	 * Tworzy się sumy zgonów z tej samej liczby lat np. dla lat 2018, 2019, 2020 i 2021
	 * tworzy się dwie sumy zgonów: pierwsza dla lat 2018 i 2019, druga dla lat 2020 i 2021.
	 * Parametr _LiczbaLat = 2 oznacza, że w przypadku obydwu sum sumuje się zgony
	 * dla dwóch lat.
	 *
	 * Liczba okresów dla danego kraju jest określona przez liczbę okresów
	 * dla ostatniego roku na liście lat. Patrz klasa FormatZgonySwiatWm,
	 * metoda czytajZgony, fragment kodu
	 * 				if(ListaLat.lastKey().equals(Rok))
					{
						ListaZgonow.add(ZgonyLok);
					}
					else
					{
						MapaZgonow.put(Klucz, ZgonyLok);
					}	
		Chodzi o ostatni rok na liście lat w parametrze metody czytajZgony
		klasy FormatZgonySwiatWm. Zgony dla ostatniego roku są zapisywane 
		na listę ListaZgonow. Natomiat zgony z poprzednich lat są
		zapisywane do mapy. Liczba okresów jest więc określona przez
		pętlę for (Zgony ZgonyLok: ListaZgonow) metody zapiszLiczbeZgonow
		w klasie FormatZgonySwiatWm.
	 */
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
			int[] SumaZgonow1 = new int[_LiczbaLat];
			int[] SumaZgonow2 = new int[_LiczbaLat];
			int[] LiczbaZgonow1 = new int[_LiczbaLat];
			int[] LiczbaZgonow2 = new int[_LiczbaLat];
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
				
				for(int i=0; i < LiczbaZgonow1.length; i++)
				{
					int LiczbaZgonowLok1 = Integer.parseInt(stab[3+i]);
					if(LiczbaZgonowLok1 == 0)
						wkom.write(s + "\r\n");
					LiczbaZgonow1[i] = LiczbaZgonowLok1;
				}
				for(int i=0; i < LiczbaZgonow2.length; i++)
				{
					int LiczbaZgonowLok2 = Integer.parseInt(stab[3+LiczbaZgonow1.length+i]);
					if(LiczbaZgonowLok2 == 0)
						wkom.write(s + "\r\n");
					LiczbaZgonow2[i] = LiczbaZgonowLok2;
				}
								
				if(!NazwaKrajuPoprz.equals(NazwaKraju) && !NazwaKrajuPoprz.equals(""))
				{
					int Suma1 = 0;
					for(int j=0; j < SumaZgonow1.length; j++)
						Suma1 += SumaZgonow1[j];
					int Suma2 = 0;
					for(int j=0; j < SumaZgonow2.length; j++)
						Suma2 += SumaZgonow2[j];
					double Zmiana = 0.0;
					double ZmianaProc = 0.0;
					if(Suma1 != 0)
					{
						Zmiana = Suma2 - Suma1;
						ZmianaProc = ((double) Zmiana) / Suma1 * 100.0;
					}
					w.write(NazwaKrajuPoprz + "," + Suma1 + "," + Suma2 + ","
							+ String.format(new Locale("US"), "%5.0f", Zmiana).trim() + ","
							+ String.format(new Locale("US"), "%6.2f", ZmianaProc).trim() + ","
							+ LiczbaOkresow * _LiczbaLat + "," + MiesTygPoprz
							+ "\r\n");
					
					for(int i = 0; i < SumaZgonow1.length; i++)
						SumaZgonow1[i] = 0;
					for(int i = 0; i < SumaZgonow2.length; i++)
						SumaZgonow2[i] = 0;
					LiczbaOkresow = 0;
					
				}
				
				for(int i=0; i < SumaZgonow1.length; i++)
					SumaZgonow1[i] += LiczbaZgonow1[i];
				for(int i=0; i < SumaZgonow2.length; i++)
					SumaZgonow2[i] += LiczbaZgonow2[i];
				
				LiczbaOkresow++;
				
				NazwaKrajuPoprz = NazwaKraju;
				MiesTygPoprz = MiesTyg;
			}

			// Suma dla ostatniego kraju
			if(!NazwaKrajuPoprz.equals(""))
			{
				int Suma1 = 0;
				for(int j=0; j < SumaZgonow1.length; j++)
					Suma1 += SumaZgonow1[j];
				int Suma2 = 0;
				for(int j=0; j < SumaZgonow2.length; j++)
					Suma2 += SumaZgonow2[j];
				double Zmiana = 0.0;
				double ZmianaProc = 0.0;
				if(Suma1 != 0)
				{
					Zmiana = Suma2 - Suma1;
					ZmianaProc = ((double) Zmiana) / Suma1 * 100.0;
				}
				w.write(NazwaKrajuPoprz + "," + Suma1 + "," + Suma2 + ","
						+ String.format(new Locale("US"), "%5.0f", Zmiana).trim() + ","
						+ String.format(new Locale("US"), "%6.2f", ZmianaProc).trim() + ","
						+ LiczbaOkresow * _LiczbaLat + "," + MiesTygPoprz
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
