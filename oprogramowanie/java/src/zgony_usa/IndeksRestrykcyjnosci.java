package zgony_usa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * 
 * Jakaś stara wersja moich badań nad indeksami.
 * Oblicza średni indeks StringencyIndex w podanym przedziale dat
 * dla poszczególnych stanów.
 * 
 * @author Andrzej Bystrzycki
 *
 */
public class IndeksRestrykcyjnosci
{
	public static String getNazwaPlikuWyj()
	{
		return NazwaPlikuWyj;
	}

	public static void setNazwaPlikuWyj(String nazwaPlikuWyj)
	{
		NazwaPlikuWyj = nazwaPlikuWyj;
	}

	private static String KatalogWej = "c:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "c:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String Kodowanie = "Cp1250";
	//private static String NazwaPlikuWej = "test_indeks.csv";
	private static String NazwaPlikuWej = "OxCGRT_US_latest.csv";
	private static String NazwaPlikuWyj = "indeks2021.txt";
	private static String NazwaPlikuKom = "kom_indeks2021.txt";
	//private static String NazwaPlikuKom = "komunikaty.txt";
	//private static int iIndeksRestr = 68;
	// zmiana struktury pliku wejściowego
	// indeks pola StringencyIndex
	private static int iIndeksRestr = 76;

	public static void main(String[] args)
	{
		obliczSredniIndeks("20210101", "20211231");
	}
	
	public static void obliczSredniIndeks(String DataOd, String DataDo)
	{
		BufferedReader br = null;
		String s = null;
		Writer w = null;
		Writer wkom = null;
		String NazwaStanu = "";
		String NazwaStanu1 = "";
		int LiczbaDni = 0;
		double IndeksRes = 0.0;
		double SumaIndeks = 0.0;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), Kodowanie));
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), Kodowanie);
			while ((s = br.readLine()) != null)
			{
				if(!s.startsWith("CountryName"))
				{					
					// stackverflow splitting a csv file with quotes as text-delimiter using
					String[] stab = s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
					//String[] stab = s.split(",");
					NazwaStanu = stab[2];
					String Data = stab[5];
					
					//System.out.println(s);
					//System.out.println("1="+DataOd.compareTo(Data));
					//System.out.println("2="+DataDo.compareTo(Data));
					//System.out.println("3="+NazwaStanu.equals(""));
					
					if(DataOd.compareTo(Data) > 0 || DataDo.compareTo(Data) < 0 || NazwaStanu.equals("")) continue;
					
					// NazwaStanu1 = nazwa stanu przed zmianą nazwy
					if (NazwaStanu1.compareTo(NazwaStanu) != 0 && !NazwaStanu1.equals(""))
					{
						double SredniIndeks = SumaIndeks / LiczbaDni;
						w.write(NazwaStanu1+","+String.format(new Locale("US"), "%7.2f", SredniIndeks).trim() + "\r\n");
						
						SumaIndeks = 0.0;
						LiczbaDni = 0;
					}
					
					//System.out.println("stab[68]="+stab[68]);
					IndeksRes = 0.0;
					if(stab.length < 69)
					{
						wkom.write("<69\r\n"+s+"\r\n");
						//for(int i=0; i < stab.length; i++)
							//wkom.write("["+i+"] "+ stab[i]+"\r\n");
						continue;
					}
					if(!stab[iIndeksRestr].equals(""))
					{	
						IndeksRes = Double.parseDouble(stab[iIndeksRestr]);
					}
					else
					{
						wkom.write("pusty\r\n"+s+"\r\n");
						//for(int i=0; i < stab.length; i++)
							//wkom.write("["+i+"] "+ stab[i]+"\r\n");
						continue;
					}
					SumaIndeks += IndeksRes;
					LiczbaDni++;
					//System.out.println("LiczbaDni="+LiczbaDni);
				
					NazwaStanu1 = NazwaStanu;
				}
			}
			
			// indeks dla ostatniego kraju
			if(!NazwaStanu.equals(""))
			{
				double SredniIndeks = SumaIndeks / LiczbaDni;
				w.write(NazwaStanu1+","+String.format(new Locale("US"), "%7.2f", SredniIndeks).trim() + "\r\n");				
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
