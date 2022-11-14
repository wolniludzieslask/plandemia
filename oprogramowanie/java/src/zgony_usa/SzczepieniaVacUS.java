package zgony_usa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.TreeMap;

//import utilities.BOMSkipper;

/**
 * Klasa służy do przepisania danych o procencie wyszczepienia
 * w poszczególnych stanach.
 */
public class SzczepieniaVacUS
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	//private static String KatalogWej = "c:\\rob\\zgony_usa\\";
	//private static String KatalogWyj = "c:\\rob\\zgony_usa\\";
	private static String Kodowanie = "UTF-8";
	//private static String NazwaPlikuWej = "test_indeks.csv";
	private static String NazwaPlikuWej = "us_state_vaccinations.csv";
	private static String NazwaPlikuWyj = "szczepienia_vacUS2021_12_31.txt";
	private static String NazwaPlikuStanow = "stany1.txt";
	private static String NazwaPlikuKom = "kom_szczUS2021_12_31.txt";

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
		String NazwaStanu = "";
		String NazwaStanuPoprzedniego = "";
		String Naglowek = "";
		TreeMap<String, String> ListaStanow = new TreeMap<String, String>(); 

		try
		{
			brlist = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuStanow), Kodowanie));
			// Trzeba pominąć BOM, bo taki BOM jest byłby wczytywany do pierwszego łańcucha.
			//BOMSkipper.skip(brlist);
			while ((s = brlist.readLine()) != null)
			{
				String[] stab = s.split(",");
				// stab[0] - nazwa stanu
				ListaStanow.put(stab[0], stab[0]);
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
				
				if(s.startsWith("date"))
				{					
					for(int i = 0; i < stab.length; i++)
						if(stab[i].equals("people_fully_vaccinated_per_hundred"))
						{
							IndeksZaszczepionych = i;
							System.out.println("IndeksZaszczepionych="+IndeksZaszczepionych);
							Naglowek = s;
							break;
						}
					wkom.write(Naglowek+"\r\n");
				}
				else
				{
					
					NazwaStanu = stab[1];
					if(stab.length < 2) continue;
					String Data = stab[0];
					
					if(DataOd.compareTo(Data) > 0 || DataDo.compareTo(Data) < 0 || NazwaStanu.equals("")) continue;					
					
					if(stab.length <= IndeksZaszczepionych) continue;
					
					if(stab.length < 16)
					{
						wkom.write("stab.length < 16 "+" len="+stab.length+ "\r\n");
						wkom.write(s+"\r\n");
						continue;
					}
					
					if (!NazwaStanuPoprzedniego.equals(NazwaStanu) && !NazwaStanuPoprzedniego.equals(""))
					{						
						String SymbolStanu = ListaStanow.get(NazwaStanuPoprzedniego);						
						w.write(NazwaStanuPoprzedniego + "," + SymbolStanu + "," + ProcentZaszepionych + "," + DataSzczepienia+ "\r\n");
						
						ProcentZaszepionych = "";
					}
									
					//System.out.println(s);
					//System.out.println("DataOd="+DataOd+" Data="+Data+" 1="+DataOd.compareTo(Data));
					//System.out.println("DataDo="+DataOd+" Data="+Data+" 2="+DataDo.compareTo(Data));
					//System.out.println("3="+NazwaStanu.equals(""));
					
					if(!stab[IndeksZaszczepionych].equals(""))
					{
						ProcentZaszepionych = stab[IndeksZaszczepionych];
						DataSzczepienia = Data;
					}
					//if(NazwaStanu.equals("Alska"))
						//w.write(s+"\r\n");
					
					NazwaStanuPoprzedniego = NazwaStanu;
				}
			}
			
			// szczepienia dla ostatniego stanu
			if(!NazwaStanuPoprzedniego.equals(""))
			{
				String SymbolStanu = ListaStanow.get(NazwaStanuPoprzedniego);
				w.write(NazwaStanuPoprzedniego + "," + SymbolStanu + "," + ProcentZaszepionych + "," + DataSzczepienia+ "\r\n");
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
