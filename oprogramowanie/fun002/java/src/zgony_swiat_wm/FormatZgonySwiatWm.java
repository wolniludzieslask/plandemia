package zgony_swiat_wm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import utilities.BOMSkipper;

/* Przepisuje liczby zgonów z pliku world_mortality.csv
 * (projekt https://github.com/akarlinsky/world_mortality)
 * do pliku w formacie
 * NazwaKraju,RodzajOkresu,NumerOkresu,Zgony2018,Zgony2019,Zgony2020
 * Patrz pliki peru2020.JPG, barbados2020.JPG w katalogu testy.
 */
public class FormatZgonySwiatWm
{
	private static String KatalogWej = "C:\\rob\\world_mortality\\";
	private static String KatalogWyj = "C:\\rob\\world_mortality\\";
	private static String KodowanieWej = "cp1250";
	private static String KodowanieWyj = "utf-8";
	private static String NazwaPlikuKraj = "lista_krajow.csv";
	private static String NazwaPlikuWej = "world_mortality.csv";
	private static String NazwaPlikuWyj = "zgony_wm2020.txt";
	private static String NazwaPlikuKom = "kom_zgony_wm2020.txt";
	private static TreeMap<String, Kraj> Kraje = new TreeMap<String, Kraj>();
	private static ArrayList<Zgony> ListaZgonow = new ArrayList<Zgony>();
	private static TreeMap<String, Zgony> MapaZgonow = new TreeMap<String, Zgony>();
	private static TreeMap<String, String> ListaLat = new TreeMap<String, String>(); 

	public static void main(String[] args)
	{
		czytajListeKrajow();
		czytajZgony("2018,2019,2020",false);
		setNazwaPlikuKom("kom_zgony_2wm2020_1.txt");
		zapiszLiczbeZgonow("NazwaKraju,RodzajOkresu,NumerOkresu,Zgony2018,Zgony2019,Zgony2020");
	}

	public static void czytajListeKrajow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWyj + NazwaPlikuKraj), KodowanieWyj));
			// Trzeba pominąć BOM, bo taki BOM byłby wczytywany do pierwszego łańcucha.
			BOMSkipper.skip(br);
			while ((s = br.readLine()) != null)
			{
				Kraj KrajLok = new Kraj();
				String[] tab = s.split(",");

				KrajLok.Symbol = tab[2];
				KrajLok.Nazwa = tab[0];
				KrajLok.NazwaPolska = tab[1];
				
				//System.out.println(KrajLok.Symbol);
				//System.out.println(KrajLok.Nazwa);
				//System.out.println(KrajLok.NazwaPLiku);

				Kraje.put(KrajLok.Nazwa, KrajLok);
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
	
	/* Wczytuje liczby zgonów z pliku  do mapy MapaZgonow
	 * oraz listy ListaZgonow.
	 * Parametr _ListaLat - lista lat oddzielonych przecinkami, np. "2018,2019,2020"
	 * Parametr _Sumuj53 - true - sumować zgony z 53-go okresu, false - nie sumować
	 */
	public static void czytajZgony(String _ListaLat, boolean _Sumuj53)
	{
		BufferedReader br = null;
		String s = null;
		Writer wkom = null;

		try
		{
			int IndeksPocz = 0;
			// tworzenie mapy lat
			while(true)
			{
				int IndeksKon = _ListaLat.indexOf(",", IndeksPocz);
				if(IndeksKon > 0)
				{
					String Rok = _ListaLat.substring(IndeksPocz, IndeksKon);
					//System.out.println("Rok="+Rok);
					ListaLat.put(Rok, Rok);
				}
				else
				{
					String Rok = _ListaLat.substring(IndeksPocz, _ListaLat.length());
					//System.out.println("Rok="+Rok);
					ListaLat.put(Rok, Rok);
					break;
				}
				IndeksPocz = IndeksKon + 1;
			}
						
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), KodowanieWej));
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), KodowanieWyj);
			String NazwaKrajuAngPoprz = "";
			String NazwaKraju = "";
			String SymbolKraju = "";
			while ((s = br.readLine()) != null)
			{
				if(s.startsWith("iso3c")) continue;
				
				String[] stab = s.split(",");
				String Rok = stab[2];
				int NumerOkresu = Integer.parseInt(stab[3]);
				if(!ListaLat.containsKey(Rok)) continue;
				if(!_Sumuj53 && NumerOkresu == 53) continue;
				
				String NazwaKrajuAng = stab[1];
				if(!NazwaKrajuAng.equals(NazwaKrajuAngPoprz))
				{
					if(Kraje.containsKey(NazwaKrajuAng))
					{
						SymbolKraju = Kraje.get(NazwaKrajuAng).Symbol;
						NazwaKraju = Kraje.get(NazwaKrajuAng).NazwaPolska;
					}
					else
					{
						wkom.write("Brak kodu dla kraju " + NazwaKrajuAng + "\r\n");
						System.out.println("Brak kodu dla kraju " + NazwaKrajuAng);
						NazwaKraju = "xxxxxxx";
						SymbolKraju = "YY";
					}
				}
				
				int LiczbaZgonow = (int) Math.round(Double.parseDouble(stab[5]));
				String Klucz = SymbolKraju + "-" + Rok + String.format("%02d", NumerOkresu);
				Zgony ZgonyLok = new Zgony();
				ZgonyLok.LiczbaZgonow = LiczbaZgonow;
				ZgonyLok.SymbolKraju = SymbolKraju;
				ZgonyLok.NazwaKraju = NazwaKraju; 
				ZgonyLok.NumerOkresu = NumerOkresu;
				
				// stab[4] = monthly lub weekly
				if(stab[4].equals("monthly"))
				{
					ZgonyLok.MiesTyg = "M";
				}
				else if(stab[4].equals("weekly"))
				{
					ZgonyLok.MiesTyg = "T";
				}
				else
					ZgonyLok.MiesTyg = stab[4];
				
				if(ListaLat.lastKey().equals(Rok))
				{
					ListaZgonow.add(ZgonyLok);
				}
				else
				{
					MapaZgonow.put(Klucz, ZgonyLok);
				}	
				
				NazwaKrajuAngPoprz = NazwaKrajuAng;
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
				if (wkom != null)
					wkom.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}
		
	}

	/* Wczytuje liczby zgonów z pliku do mapy MapaZgonow
	 * oraz listy ListaZgonow.
	 * Zgony są wczytywane dla tygodni o numerach w przedziale od _NrTygOd, od _NrTygDo
	 * i miesięcy o numerach w przedziale od _NrMiesOd, do _NrMiesDo.
	 * Parametr _ListaLat - lista lat oddzielonych przecinkami, np. "2018,2019,2020"
	 */
	public static void czytajZgony(String _ListaLat, int _NrTygOd, int _NrTygDo, int _NrMiesOd, int _NrMiesDo)
	{
		BufferedReader br = null;
		String s = null;
		Writer wkom = null;

		try
		{
			int IndeksPocz = 0;
			// tworzenie mapy lat
			while(true)
			{
				int IndeksKon = _ListaLat.indexOf(",", IndeksPocz);
				if(IndeksKon > 0)
				{
					String Rok = _ListaLat.substring(IndeksPocz, IndeksKon);
					//System.out.println("Rok="+Rok);
					ListaLat.put(Rok, Rok);
				}
				else
				{
					String Rok = _ListaLat.substring(IndeksPocz, _ListaLat.length());
					//System.out.println("Rok="+Rok);
					ListaLat.put(Rok, Rok);
					break;
				}
				IndeksPocz = IndeksKon + 1;
			}
						
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuWej), KodowanieWej));
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), KodowanieWyj);
			String NazwaKrajuAngPoprz = "";
			String NazwaKraju = "";
			String SymbolKraju = "";
			while ((s = br.readLine()) != null)
			{
				if(s.startsWith("iso3c")) continue;
				
				String[] stab = s.split(",");
				String Rok = stab[2];
				int NumerOkresu = Integer.parseInt(stab[3]);
				if(!ListaLat.containsKey(Rok)) continue;
				
				if(stab[4].equals("monthly") && (NumerOkresu < _NrMiesOd || NumerOkresu > _NrMiesDo)) continue;
				
				if(stab[4].equals("weekly") && (NumerOkresu < _NrTygOd || NumerOkresu > _NrTygDo)) continue;

				String NazwaKrajuAng = stab[1];
				if(!NazwaKrajuAng.equals(NazwaKrajuAngPoprz))
				{
					if(Kraje.containsKey(NazwaKrajuAng))
					{
						SymbolKraju = Kraje.get(NazwaKrajuAng).Symbol;
						NazwaKraju = Kraje.get(NazwaKrajuAng).NazwaPolska;
					}
					else
					{
						wkom.write("Brak kodu dla kraju " + NazwaKrajuAng + "\r\n");
						System.out.println("Brak kodu dla kraju " + NazwaKrajuAng);
						NazwaKraju = "xxxxxxx";
						SymbolKraju = "YY";
					}
				}
				
				int LiczbaZgonow = (int) Math.round(Double.parseDouble(stab[5]));
				String Klucz = SymbolKraju + "-" + Rok + String.format("%02d", NumerOkresu);
				Zgony ZgonyLok = new Zgony();
				ZgonyLok.LiczbaZgonow = LiczbaZgonow;
				ZgonyLok.SymbolKraju = SymbolKraju;
				ZgonyLok.NazwaKraju = NazwaKraju; 
				ZgonyLok.NumerOkresu = NumerOkresu;
				
				// stab[4] = monthly lub weekly
				if(stab[4].equals("monthly"))
				{
					ZgonyLok.MiesTyg = "M";
				}
				else if(stab[4].equals("weekly"))
				{
					ZgonyLok.MiesTyg = "T";
				}
				else
					ZgonyLok.MiesTyg = stab[4];
				
				if(ListaLat.lastKey().equals(Rok))
				{
					ListaZgonow.add(ZgonyLok);
				}
				else
				{
					MapaZgonow.put(Klucz, ZgonyLok);
				}	
				
				NazwaKrajuAngPoprz = NazwaKrajuAng;
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
				if (wkom != null)
					wkom.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}
		
	}

	/* Zapisuje liczby zgonów w formacie
	 NazwaKraju,RodzajOkresu,NumerOkresu,Zgony2018,Zgony2019,Zgony2020 
	 parametr _Naglowek jest pierwszą linią zapisywaną w pliku wyjściowym */
	public static void zapiszLiczbeZgonow(String _Naglowek)
	{
		String s = null;
		Writer w = null;
		Writer wkom = null;

		try
		{
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), KodowanieWyj);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom), KodowanieWyj);
			
			w.write(_Naglowek + "\r\n");

			//String NazwaKrajuPoprz = "";
			//String SymbolKraju = "";
			
			for (Zgony ZgonyLok: ListaZgonow)
			{

				for (Map.Entry<String, String> LataLok : ListaLat.entrySet())
				{
					if(ListaLat.lastKey().equals(LataLok.getKey())) break;
					
					String Klucz = ZgonyLok.SymbolKraju + "-" + LataLok.getKey() + String.format("%02d", ZgonyLok.NumerOkresu);
					
					Zgony ZgonyLokM = null;
					if(MapaZgonow.containsKey(Klucz))
					{
						ZgonyLokM = MapaZgonow.get(Klucz);
					}
					if(ZgonyLokM == null) continue;
					if(ListaLat.firstKey().equals(LataLok.getKey()))
					{						
						w.write(ZgonyLokM.NazwaKraju + "," + ZgonyLokM.MiesTyg + "," + ZgonyLokM.NumerOkresu + "," + ZgonyLokM.LiczbaZgonow);
					}
					else
					{
						w.write("," + ZgonyLokM.LiczbaZgonow);
					}
				}	
				w.write("," + ZgonyLok.LiczbaZgonow + "\r\n");
							
				//NazwaKrajuPoprz = NazwaKraju;
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

	static class Kraj
	{
		String Symbol;
		String Nazwa;
		String NazwaPolska;
	}
	
	static class Zgony
	{
		String SymbolKraju;
		String NazwaKraju;
		int NumerOkresu;
		String Rok;
		int LiczbaZgonow;
		String MiesTyg;
	}

}
