package szczepienia;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.TreeMap;

import utilities.BOMSkipper;

/**
 * Klasa służy do przepisania danych o procencie wyszczepienia
 * w poszczególnych krajach.
 * 
 * plik vaccinations.csv pochodzi z 
 * https://github.com/owid/covid-19-data/blob/master/public/data/vaccinations/vaccinations.csv
 */
public class SzczepieniaVac
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	//private static String KatalogWej = "c:\\rob\\pandemia_statystyka\\";
	//private static String KatalogWyj = "c:\\rob\\pandemia_statystyka\\";
	private static String Kodowanie = "UTF-8";
	//private static String NazwaPlikuWej = "test_indeks.csv";
	private static String NazwaPlikuWej = "vaccinations.csv";
	private static String NazwaPlikuWyj = "szczepienia_vac2021.txt";
	private static String NazwaPlikuKrajow = "lista_krajow.csv";
	private static String NazwaPlikuKom = "kom_szcz2021.txt";

	public static void main(String[] args)
	{
		szukajProcentZaszczepionych("2021-01-01", "2021-12-31");
	}
	
	public static void szukajProcentZaszczepionych(String DataOd, String DataDo)
	{
		BufferedReader br = null;
		BufferedReader brlist = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;
		String NazwaKraju = "";
		String NazwaKrajuPoprzedniego = "";
		String Naglowek = "";
		TreeMap<String, String> ListaKrajow = new TreeMap<String, String>(); 

		try
		{
			brlist = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuKrajow), Kodowanie));
			// Trzeba pominąć BOM, bo taki BOM jest byłby wczytywany do pierwszego łańcucha.
			BOMSkipper.skip(brlist);
			while ((s = brlist.readLine()) != null)
			{
				String[] stab = s.split(",");
				// stab[0] - nazwa angielska kraju, stab[2] - symbol kraju
				ListaKrajow.put(stab[0], stab[2]);
			}

			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), Kodowanie);
			
			int IndeksZaszczepionych = 0;
			String ProcentZaszepionych = "";
			String DataSzczepienia = "";
			
			while ((s = br.readLine()) != null)
			{
				String[] stab = s.split(",", -1);
				
				if(s.startsWith("location"))
				{					
					for(int i = 0; i < stab.length; i++)
						if(stab[i].equals("people_fully_vaccinated_per_hundred"))
						{
							IndeksZaszczepionych = i;
							Naglowek = s;
							break;
						}
					//w.write(Naglowek+"\r\n");
				}
				else
				{
					
					NazwaKraju = stab[0];
					if(stab.length < 2) continue;
					String Data = stab[2];
					
					if(DataOd.compareTo(Data) > 0 || DataDo.compareTo(Data) < 0 || NazwaKraju.equals("")) continue;					
					
					if(stab.length <= IndeksZaszczepionych) continue;
					
					if(stab.length < 16)
					{
						wkom.write("stab.length < 16 "+" len="+stab.length+ "\r\n");
						wkom.write(s+"\r\n");
						continue;
					}
					
					if (!NazwaKrajuPoprzedniego.equals(NazwaKraju) && !NazwaKrajuPoprzedniego.equals(""))
					{						
						String SymbolKraju = ListaKrajow.get(NazwaKrajuPoprzedniego);						
						w.write(NazwaKrajuPoprzedniego + "," + SymbolKraju + "," + ProcentZaszepionych + "," + DataSzczepienia+ "\r\n");
						
						ProcentZaszepionych = "";
					}
									
					//System.out.println(s);
					//System.out.println("DataOd="+DataOd+" Data="+Data+" 1="+DataOd.compareTo(Data));
					//System.out.println("DataDo="+DataOd+" Data="+Data+" 2="+DataDo.compareTo(Data));
					//System.out.println("3="+NazwaKraju.equals(""));
					
					// pomijamy kraje, których nie ma w mapie ListaKrajow
					//if(!ListaKrajow.containsKey(NazwaKraju)) continue;
					
					if(!stab[IndeksZaszczepionych].equals(""))
					{
						ProcentZaszepionych = stab[IndeksZaszczepionych];
						DataSzczepienia = Data;
					}
					//if(NazwaKraju.equals("Sweden"))
					//System.out.println(DataSzczepienia+" "+ProcentZaszepionych+" len="+stab.length);
					
					//if(NazwaKraju.equals("Sweden"))
						//w.write(s+"\r\n");
					
					NazwaKrajuPoprzedniego = NazwaKraju;
				}
			}
			
			// szczepienia dla ostatniego kraju
			if(!NazwaKrajuPoprzedniego.equals(""))
			{
				String SymbolKraju = ListaKrajow.get(NazwaKrajuPoprzedniego);
				w.write(NazwaKrajuPoprzedniego + "," + SymbolKraju + "," + ProcentZaszepionych + "," + DataSzczepienia+ "\r\n");
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
				if (brlist != null)
					brlist.close();
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
