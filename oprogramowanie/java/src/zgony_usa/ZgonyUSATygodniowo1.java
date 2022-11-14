package zgony_usa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


/**
 * Oblicza wzrost liczby zgonów w USA w wybranym roku 
 * w stosunku do średniej z dwóch poprzednich lat.
 * Różnica w stosunku do klasy ZgonyUSATygodniowo jest taka,
 * że średnia z dwóch poprzednich lat jest obliczana po obliczeniu sumy
 * dla wszystkich tygodni.
 * Operacja zaokrąglenia jest wykonywana na liczbie, która ma być wyświetlona
 * na stronie internetowej.
 * W przypadku klasy ZgonyUSATygodniowo średnie za lata poprzednie obliczane są
 * dla każdego tygodnia, a na końcu obliczana jest suma i wykonywane zaokrąglenie.
*/
public class ZgonyUSATygodniowo1
{
	private static String KatalogWej = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	private static String KatalogWyj = "C:\\rob\\pandemia_strona\\oprogramowanie\\fun003\\rob\\";
	//private static String KatalogWej = "c:\\rob\\zgony_usa\\";
	//private static String KatalogWyj = "c:\\rob\\zgony_usa\\";
	private static String Kodowanie = "Cp1250";
	private static String NazwaPlikuStan = "state_codes.txt";
	private static String NazwaPlikuWyj = "zgony4.txt";
	private static String NazwaPlikuKom = "komunikaty.txt";
	private static String NazwaPlikuIndeks = "indeks.txt";
	private static String NazwaPlikuSzczepien = "szczepienia_vacUS2021_12_31.txt";
	private static TreeMap<String, Stan> Stany = new TreeMap<String, Stan>();
	private static TreeMap<String, IndeksRestr> Indeksy = new TreeMap<String, IndeksRestr>();
	private static TreeMap<String, PoziomSzczepien> Szczepienia = new TreeMap<String, PoziomSzczepien>();
	private static TreeMap<String, Zgony> LiczbyZgonow1 = new TreeMap<String, Zgony>();
	private static TreeMap<String, Zgony> LiczbyZgonow2 = new TreeMap<String, Zgony>();
	private static TreeMap<String, Zgony> LiczbyZgonow3 = new TreeMap<String, Zgony>();
	private static int Rok_1;
	private static int Rok_2;

	public static void main(String[] args)
	{
		ZgonyUSATygodniowo1.setNazwaPlikuWyj("zgony2020us.txt");
		ZgonyUSATygodniowo1.setNazwaPlikuKom("komunikaty2020us.txt");
		
		ZgonyUSATygodniowo1.czytajKodyStanow();
		ZgonyUSATygodniowo1.czytajIndeksRestrykcyjnosci();
		
		ZgonyUSATygodniowo1.setLiczbyZgonow1(LiczbyZgonow1);
		ZgonyUSATygodniowo1.setLiczbyZgonow2(LiczbyZgonow2);
		ZgonyUSATygodniowo1.setLiczbyZgonow3(LiczbyZgonow3);

		/*
		 * dane za rok 2018
		 */
		ZgonyUSATygodniowo1.setRok_2(2018);
		ZgonyUSATygodniowo1.czytajLiczbyZgonow2(LiczbyZgonow3, "Weekly_Counts_of_Deaths_by_State_and_Select_Causes__2014-2019.csv", "2018");

		/*
		 * dane za rok 2019
		 */
		ZgonyUSATygodniowo1.setRok_1(2019);
		ZgonyUSATygodniowo1.czytajLiczbyZgonow2(LiczbyZgonow3, "Weekly_Counts_of_Deaths_by_State_and_Select_Causes__2014-2019.csv", "2019");

		/* dane za rok 2020 */
		ZgonyUSATygodniowo1.czytajLiczbyZgonow3(LiczbyZgonow3, "Weekly_Provisional_Counts_of_Deaths_by_State_and_Select_Causes__2020-2022_1.csv", "2020");

		// ostatni parametr false oznacza, żeby 53-go tygodnia nie sumować, true oznacza sumować
		ZgonyUSATygodniowo1.zapiszStatystyke("NazwaStanu,Srednia2018_2019,2020,ZmianaStan,ZmianaStanProc,PoziomSzczepien,IndeksRestr,LiczbaSumowanychTygodni\r\n","2020",false);	
	}

	public static void czytajKodyStanow()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuStan), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				Stan StanLok = new Stan();
				String[] tab = s.split(",");

				StanLok.Symbol = tab[1];
				StanLok.Nazwa = tab[0];

				Stany.put(StanLok.Nazwa, StanLok);
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
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuIndeks), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				IndeksRestr IndeksLok = new IndeksRestr();
				String[] tab = s.split(",");

				IndeksLok.Indeks = Double.parseDouble(tab[1]);
				IndeksLok.Nazwa = tab[0];

				Indeksy.put(IndeksLok.Nazwa, IndeksLok);
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


	public static void czytajPoziomSzczepien()
	{
		BufferedReader br = null;
		String s = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + NazwaPlikuSzczepien), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				PoziomSzczepien SzczepieniaLok = new PoziomSzczepien();
				String[] tab = s.split(",", -1);

				if(!tab[2].equals(""))
					SzczepieniaLok.Poziom = Double.parseDouble(tab[2]);
				else
					SzczepieniaLok.Poziom = 0.0;
				SzczepieniaLok.Nazwa = tab[0];

				Szczepienia.put(SzczepieniaLok.Nazwa, SzczepieniaLok);
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

	@SuppressWarnings("unused")
	private static void czytajLiczbyZgonow(TreeMap<String, Zgony> LiczbyZgonow, String _NazwaPlikuWej,
			int LiczbaTygodniRok)
	{
		BufferedReader br = null;
		String s;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + _NazwaPlikuWej), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				if (!s.startsWith("freq"))
				{
					String[] tab = s.split(",");
					String[] tab1 = tab[0].split(";");
					// licznik zaczyna się od liczby odpowiadającej kolumnie 20xx-W01
					// tab[0] = W;T;NR;AD
					// tab[1] = 20yy-W99
					for (int i = 1; i < LiczbaTygodniRok + 2; i++)
					{
						Zgony ZgonyLok = new Zgony();
						if (tab[i].endsWith("p"))
							ZgonyLok.LiczbaZgonow = Integer.parseInt(tab[i].substring(0, tab[i].length() - 1).trim());
						else
						{
							if (!tab[i].startsWith(":"))
								ZgonyLok.LiczbaZgonow = Integer.parseInt(tab[i].trim());
							else
								ZgonyLok.LiczbaZgonow = 0;
						}

						// i-1, bo dla i = 2 jest to pierwszy tydzień, czyli W01
						LiczbyZgonow.put(tab1[3] + "-" + String.format("%02d", i), ZgonyLok);
					}
				} else
				{
					String[] tab = s.split(",");

				}
			}
		} catch (Exception e)
		{
			System.out.println("Wystąpił błąd");
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

	public static void czytajLiczbyZgonow1(TreeMap<String, Zgony> LiczbyZgonow, TreeMap<Integer, String> Naglowki,
			String _NazwaPlikuWej, int IndeksPocz, int IndeksKonc)
	{
		BufferedReader br = null;
		String s;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + _NazwaPlikuWej), Kodowanie));
			while ((s = br.readLine()) != null)
			{
				if (!s.startsWith("freq"))
				{
					String[] tab = s.split(",");
					String[] tab1 = tab[0].split(";");
					// licznik zaczyna się od liczby odpowiadającej kolumnie 20xx-W01
					// tab[0] = W;T;NR;AD
					// tab[1] = 20yy-W99
					for (int i = IndeksPocz; i <= IndeksKonc; i++)
					{
						Zgony ZgonyLok = new Zgony();
						if (tab[i].endsWith("p"))
							ZgonyLok.LiczbaZgonow = Integer.parseInt(tab[i].substring(0, tab[i].length() - 1).trim());
						else
						{
							if (!tab[i].startsWith(":"))
								ZgonyLok.LiczbaZgonow = Integer.parseInt(tab[i].trim());
							else
								ZgonyLok.LiczbaZgonow = 0;
						}

						// i-1, bo dla i = 2 jest to pierwszy tydzień, czyli W01
						LiczbyZgonow.put(tab1[3] + "-" + Naglowki.get(i), ZgonyLok);
					}
				} else
				{
					String[] tab = s.split(",");
					for (int i = IndeksPocz; i <= IndeksKonc; i++)
					{
						Naglowki.put(i, tab[i].substring(5).trim());
					}

				}
			}
		} catch (Exception e)
		{
			System.out.println("Wystąpił błąd");
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

	/*
	 * Czytanie liczby zgonów w formacie, jak w pliku
	 * Weekly_Counts_of_Deaths_by_State_and_Select_Causes__2014-2019.csv
	 */
	public static void czytajLiczbyZgonow2(TreeMap<String, Zgony> LiczbyZgonow, String _NazwaPlikuWej, String _Rok)
	{
		BufferedReader br = null;
		String s = null;
		Writer wkom = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + _NazwaPlikuWej), Kodowanie));
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom, true), Kodowanie);

			String SymbolStanu = "xx";
			String NazwaStanu = "xx";

			while ((s = br.readLine()) != null)
			{
				if (!s.startsWith("Jurisdiction"))
				{
					// stackverflow splitting a csv file with quotes as text-delimiter using
					String[] tab = s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

					String Rok = tab[1];
					// System.out.println(Rok);

					// bierzemy wiersze w określonym roku
					if (Rok.equals(_Rok))
					{
						Zgony ZgonyLok = new Zgony();
						ZgonyLok.LiczbaZgonow = Integer.parseInt(tab[4]);

						// wyszukanie symbolu stanu, jeśli się zmienił
						String NazwaStanuRob = tab[0];
						if (!NazwaStanuRob.equals(NazwaStanu))
						{
							SymbolStanu = null;
							if (Stany.containsKey(NazwaStanuRob))
							{
								SymbolStanu = Stany.get(NazwaStanuRob).Symbol;
							} else
							{
								//System.out.println(
										//"czytajLiczbyZgonow2 Brak nazwy stanu w tablicy stanów: " + NazwaStanuRob);
								wkom.write("czytajLiczbyZgonow2 Brak nazwy stanu w tablicy stanów: " + NazwaStanuRob + "\r\n");
								continue;
							}

						}

						int NrTygodnia = Integer.parseInt(tab[2]);
						// Klucz = symbol kraju-rrrrW99 (rrrr to rok, 99 to numer tygodnia)
						String Klucz = SymbolStanu + "-" + Rok + String.format("%02d", NrTygodnia);

						// System.out.println("Klucz=" + Klucz);

						LiczbyZgonow.put(Klucz, ZgonyLok);

						// if(NazwaStanuRob.equals("Alaska"))
						// System.out.println(s);
					}
				}
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
				if (wkom != null)
					wkom.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}

	}

	/*
	 * Czytanie liczby zgonów w formacie, jak w pliku
	 * Weekly_Provisional_Counts_of_Deaths_by_State_and_Select_Causes__2020-2022.csv
	 */
	public static void czytajLiczbyZgonow3(TreeMap<String, Zgony> LiczbyZgonow, String _NazwaPlikuWej, String _Rok)
	{
		BufferedReader br = null;
		String s = null;
		Writer wkom = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(KatalogWej + _NazwaPlikuWej), Kodowanie));
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom, true), Kodowanie);

			String SymbolStanu = "xx";
			String NazwaStanu = "xx";

			while ((s = br.readLine()) != null)
			{
				if (!s.startsWith("Data As Of"))
				{
					// stackverflow splitting a csv file with quotes as text-delimiter using
					// nowy format z przecinkami jako separatorem pól
					String[] tab = s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
					// stary format ze średnikami jako separatorem
					//String[] tab = s.split(";(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);

					String Rok = tab[2];
					// System.out.println(Rok);

					// bierzemy wiersze w określonym roku
					if (Rok.equals(_Rok))
					{
						Zgony ZgonyLok = new Zgony();
						String sLiczbaZgonow = tab[5].replaceAll(",", "");
						ZgonyLok.LiczbaZgonow = Integer.parseInt(sLiczbaZgonow);

						// wyszukanie symbolu stanu, jeśli się zmienił
						String NazwaStanuRob = tab[1];
						if (!NazwaStanuRob.equals(NazwaStanu))
						{
							SymbolStanu = null;
							if (Stany.containsKey(NazwaStanuRob))
							{
								SymbolStanu = Stany.get(NazwaStanuRob).Symbol;
							} else
							{
								//System.out.println(
										//"czytajLiczbyZgonow3 Brak nazwy stanu w tablicy stanów: " + NazwaStanuRob);
								wkom.write("czytajLiczbyZgonow3 Brak nazwy stanu w tablicy stanów: " + NazwaStanuRob + "\r\n");
								continue;
							}

						}

						int NrTygodnia = Integer.parseInt(tab[3]);
						// Klucz = symbol kraju-rrrrW99 (rrrr to rok, 99 to numer tygodnia)
						String Klucz = SymbolStanu + "-" + Rok + String.format("%02d", NrTygodnia);

						// System.out.println("Klucz=" + Klucz);

						LiczbyZgonow.put(Klucz, ZgonyLok);

						// if(NazwaStanuRob.equals("Alaska"))
						// System.out.println(s);
					}
				}
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
				if (wkom != null)
					wkom.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}

	}

	@SuppressWarnings("unused")
	private static void zapiszStatystykeTest(TreeMap<String, Zgony> LiczbyZgonow)
	{
		Writer w = null;

		try
		{
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			for (Map.Entry<String, Zgony> Zgon1 : LiczbyZgonow.entrySet())
			{
				String Klucz = Zgon1.getKey();
				int LiczbaZgonow = Zgon1.getValue().LiczbaZgonow;
				w.write(Klucz + "," + LiczbaZgonow + "\r\n");
			}
		} catch (Exception e)
		{
			System.out.println("Wystąpił błąd");
			e.printStackTrace();
		} finally
		{
			try
			{
				if (w != null)
					w.close();
			} catch (Exception e)
			{
				System.out.println("Wystąpił błąd finally");
			}
		}

	}

	@SuppressWarnings("unused")
	public static void zapiszStatystyke(String _Naglowek, String _Rok, boolean _Pomin53)
	{
		Writer w = null;
		Writer wkom = null;

		try
		{
			w = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuWyj), Kodowanie);
			wkom = new OutputStreamWriter(new FileOutputStream(KatalogWyj + NazwaPlikuKom, true), Kodowanie);
			String SymbolStanu = "";
			String SymbolStanu1 = "";
			String NazwaStanu = "";
			int SumaZgonowStan1 = 0;
			int SumaZgonowStan2 = 0;
			int SumaZgonowStan3 = 0;
			int SredniaStan = 0;
			double ZmianaStan = 0.0;
			double ZmianaStanProc = 0.0;
			int LiczbaSumowanychTygodni = 0;

			w.write(_Naglowek);

			for (Map.Entry<String, Zgony> Zgon1 : LiczbyZgonow3.entrySet())
			{

				String Klucz = Zgon1.getKey();
				int x = Klucz.indexOf("-");
				SymbolStanu = Klucz.substring(0, x);
				String Rok = Klucz.substring(x + 1, x + 5);

				// jeśli nie są to wybrane lata, to pomijamy
				if (_Rok.indexOf(Rok) < 0)
					continue;

				String Tydzien = Klucz.substring(x + 6);

				// if(Tydzien.equals("99")) continue;

				//System.out.println(Zgon1.getKey() + " " + Zgon1.getValue().LiczbaZgonow);

				// w.write("test SymbolKraju1="+SymbolKraju1+" test
				// SymbolKraju="+SymbolKraju+"\r\n");

				if (SymbolStanu1.compareTo(SymbolStanu) != 0)
				{
					{
						int Srednia = 0;
						if (SymbolStanu1.compareTo("") != 0)
						{
							if (SumaZgonowStan2 != 0)
							{
								double SredniaDouble = (SumaZgonowStan1 + SumaZgonowStan2) / 2.0;
								// 703.5 -> 704
								Srednia = (int) Math.round(SredniaDouble);
								ZmianaStan = SumaZgonowStan3 - Srednia;
								ZmianaStanProc = ((double) ZmianaStan) / SumaZgonowStan2 * 100.0;
							} else
								ZmianaStanProc = -9999.0;

							double IndeksRLoc = 0.0;
							if(Indeksy.containsKey(NazwaStanu))
							{
								IndeksRLoc = Indeksy.get(NazwaStanu).Indeks;
							}
							
							double PoziomSzcz = 0.0;
							if(Szczepienia.containsKey(NazwaStanu))
							{
								PoziomSzcz = Szczepienia.get(NazwaStanu).Poziom;
							}
							
							w.write(NazwaStanu + "," + Srednia + "," + SumaZgonowStan3 + ","
									+ String.format(new Locale("US"), "%5.0f", ZmianaStan).trim() + ","
									+ String.format(new Locale("US"), "%7.2f", ZmianaStanProc).trim() + ","
									+ String.format(new Locale("US"), "%6.2f", PoziomSzcz).trim() + "," +
									String.format(new Locale("US"), "%6.2f", IndeksRLoc).trim() + "," +
									+ LiczbaSumowanychTygodni + "\r\n");
						}

						/*
						 * if (Stany.containsKey(SymbolKraju)) { NazwaKraju =
						 * Stany.get(SymbolKraju).Nazwa; } else { NazwaKraju = SymbolKraju;
						 * w.write("Brak kodu w tablicy kodow: " + SymbolKraju + "\r\n"); }
						 */

						// wyszukanie symbolu stanu
						String SymbolStanuRob = SymbolStanu; //???
						String NazwaStanuRob = "";
						for (Map.Entry<String, Stan> StanyLoc : Stany.entrySet())
						{
							String SymbolStanuLoc = StanyLoc.getValue().Symbol;
							// System.out.println("SymbolKrajuRob="+SymbolKrajuRob+" SymbolStanu="+SymbolStanu);
							if (SymbolStanuRob.equals(SymbolStanuLoc))
							{
								NazwaStanuRob = StanyLoc.getValue().Nazwa;
								break;
							}
						}

						if (!NazwaStanuRob.equals(""))
							NazwaStanu = NazwaStanuRob;
						else
						{
							System.out
									.println("zapiszStatystyke Brak symbolu w tablicy stanów: " + SymbolStanuRob);
							continue;
						}

						//w.write(NazwaStanu + "\r\n");

						SumaZgonowStan1 = 0;
						SumaZgonowStan2 = 0;
						SumaZgonowStan3 = 0;
						LiczbaSumowanychTygodni = 0;
					}

				}

				int LiczbaZgonow3 = Zgon1.getValue().LiczbaZgonow;

				// if(SymbolKraju.equals("AM"))
				// System.out.println(Klucz+" LiczbaZgonow3="+LiczbaZgonow3);

				int LiczbaZgonow1 = 0;

				// pierwszy rok na liście, np rok 2020 na liście "2020,2021"
				String PierwszyRok = _Rok.substring(0, 4);
				int RokInt = Integer.parseInt(PierwszyRok);

				// rok - 2
				// int Rok_2 = RokInt - 2;
				String Klucz_2 = Klucz.substring(0, 3) + String.format("%d", Rok_2) + Klucz.substring(7);

				if (LiczbyZgonow3.containsKey(Klucz_2))
				{
					LiczbaZgonow1 = LiczbyZgonow3.get(Klucz_2).LiczbaZgonow;
				} else
					LiczbaZgonow1 = 0;

				// rok - 1
				// int Rok_1 = RokInt - 1;
				String Klucz_1 = Klucz.substring(0, 3) + String.format("%d", Rok_1) + Klucz.substring(7);
				int LiczbaZgonow2 = 0;
				if (LiczbyZgonow3.containsKey(Klucz_1))
				{
					LiczbaZgonow2 = LiczbyZgonow3.get(Klucz_1).LiczbaZgonow;
				} else
					LiczbaZgonow2 = 0;

				// test
				//w.write(Klucz + " " + Klucz_1 +" " + Klucz_2 + ",LiczbaZgonow3=" +
				 //LiczbaZgonow3 + ",LiczbaZgonow1=" + LiczbaZgonow1 + ",LiczbaZgonow2=" +
				 //LiczbaZgonow2 + "\r\n");

				double SredniaDouble = (LiczbaZgonow1 + LiczbaZgonow2) / 2.0; // 2.0, żeby nie obcinał części ułamkowej
																				// np. 679 + 728 = 1407 / 2 =
				// 703.5 -> 704
				int Srednia = (int) Math.round(SredniaDouble);

				double Zmiana = 0.0;
				double ZmianaProc = 0.0;
				if (Srednia != 0)
				{
					Zmiana = LiczbaZgonow3 - Srednia;
					ZmianaProc = ((double) Zmiana) / Srednia * 100.0;
				}

				// linia z liczbą zgonów dla poszczególnych tygodni
				//w.write(Klucz + "," + Srednia + "," + LiczbaZgonow3 + ","
				 //+ String.format(new Locale("US"), "%5.0f", Zmiana).trim() + ","
				 //+ String.format(new Locale("US"), "%7.2f", ZmianaProc).trim() + "\r\n");

				// Sumy obliczamy tylko, gdy wszystkie 3 liczby zgonów są różne od zera
				// lub dla 53-go tygodnia
				if (!_Pomin53)
				{
					// if (SymbolKraju.equals("LI") || (LiczbaZgonow1 != 0 && LiczbaZgonow2 != 0 &&
					// LiczbaZgonow3 != 0))
					if (LiczbaZgonow1 != 0 && LiczbaZgonow2 != 0 && LiczbaZgonow3 != 0)
					{
						// System.out.println(Tydzien);
						SumaZgonowStan1 += LiczbaZgonow1;
						SumaZgonowStan2 += LiczbaZgonow2;
						SumaZgonowStan3 += LiczbaZgonow3;
						LiczbaSumowanychTygodni += 1;
					} else
					{
						wkom.write("Liczba zgonow 0. Klucz=" + Klucz + "\r\n");
					}
				} else if (Tydzien.equals("53") || (LiczbaZgonow1 != 0 && LiczbaZgonow2 != 0 && LiczbaZgonow3 != 0))
				{
					// System.out.println(Tydzien);
					SumaZgonowStan1 += LiczbaZgonow3;
					SumaZgonowStan2 += LiczbaZgonow2;
					SumaZgonowStan3 += LiczbaZgonow3;
					LiczbaSumowanychTygodni += 1;
				}

				SymbolStanu1 = SymbolStanu;

			}

			// Podsumowanie dla ostatniego stanu
			if (SymbolStanu1.compareTo("") != 0)
			{
				//System.out.println("1 SymbolStanu1="+SymbolStanu1);
				
				// wyszukanie symbolu stanu
				String SymbolStanuRob = SymbolStanu; //???
				String NazwaStanuRob = "";
				for (Map.Entry<String, Stan> StanyLoc : Stany.entrySet())
				{
					String SymbolStanuLoc = StanyLoc.getValue().Symbol;
					// System.out.println("SymbolKrajuRob="+SymbolKrajuRob+" SymbolStanu="+SymbolStanu);
					if (SymbolStanuRob.equals(SymbolStanuLoc))
					{
						NazwaStanuRob = StanyLoc.getValue().Nazwa;
						break;
					}
				}

				if (!NazwaStanuRob.equals(""))
					NazwaStanu = NazwaStanuRob;
				else
				{
					System.out
							.println("zapiszStatystyke (ostatni stan) Brak symbolu w tablicy stanów: " + SymbolStanuRob);
				}
				
				//System.out.println("2 NazwaStanu="+NazwaStanu);
				//System.out.println("3 SumaZgonowStan1="+SumaZgonowStan1+" SumaZgonowStan2="+SumaZgonowStan2+
					//	" SumaZgonowStan3=" + SumaZgonowStan3);
				
				int Srednia = 0;
				if (SumaZgonowStan2 != 0)
				{
					double SredniaDouble = (SumaZgonowStan1 + SumaZgonowStan2) / 2.0;
					// 703.5 -> 704
					Srednia = (int) Math.round(SredniaDouble);
					ZmianaStan = SumaZgonowStan3 - Srednia;
					ZmianaStanProc = ((double) ZmianaStan) / Srednia * 100.0;
				} else
					ZmianaStanProc = -9999.0;
				
				double IndeksRLoc = 0.0;
				if(Indeksy.containsKey(NazwaStanu))
				{
					IndeksRLoc = Indeksy.get(NazwaStanu).Indeks;
				}
				
				double PoziomSzcz = 0.0;
				if(Szczepienia.containsKey(NazwaStanu))
				{
					PoziomSzcz = Szczepienia.get(NazwaStanu).Poziom;
				}

				//w.write(_Naglowek);
				w.write(NazwaStanu + "," + Srednia + "," + SumaZgonowStan3 + ","
						+ String.format(new Locale("US"), "%5.0f", ZmianaStan).trim() + ","
						+ String.format(new Locale("US"), "%6.2f", ZmianaStanProc).trim() + ","
						+ String.format(new Locale("US"), "%6.2f", PoziomSzcz).trim() + "," +
						String.format(new Locale("US"), "%6.2f", IndeksRLoc).trim() + "," +
						+ LiczbaSumowanychTygodni + "\r\n");
			}

			/*if (!Stany.containsKey(SymbolStanu))
			{
				wkom.write("Brak kodu w tablicy kodow: " + SymbolStanu + "\r\n");
			}*/

		} catch (Exception e)
		{
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

	public static String getNazwaPlikuIndeks()
	{
		return NazwaPlikuIndeks;
	}

	public static void setNazwaPlikuIndeks(String nazwaPlikuIndeks)
	{
		NazwaPlikuIndeks = nazwaPlikuIndeks;
	}

	public static String getNazwaPlikuKom()
	{
		return NazwaPlikuKom;
	}

	public static void setNazwaPlikuKom(String nazwaPlikuKom)
	{
		NazwaPlikuKom = nazwaPlikuKom;
	}

	public static int getRok_1()
	{
		return Rok_1;
	}

	public static void setRok_1(int rok_1)
	{
		Rok_1 = rok_1;
	}

	public static int getRok_2()
	{
		return Rok_2;
	}

	public static void setRok_2(int rok_2)
	{
		Rok_2 = rok_2;
	}

	public static void setKatalogWej(String katalogWej)
	{
		KatalogWej = katalogWej;
	}

	public static void setKatalogWyj(String katalogWyj)
	{
		KatalogWyj = katalogWyj;
	}

	public static void setKodowanie(String kodowanie)
	{
		Kodowanie = kodowanie;
	}

	public static void setNazwaPlikuWyj(String nazwaPlikuWyj)
	{
		NazwaPlikuWyj = nazwaPlikuWyj;
	}

	static class Stan
	{
		String Symbol;
		String Nazwa;
	}

	static class IndeksRestr
	{
		String Nazwa;
		double Indeks;
	}

	static class PoziomSzczepien
	{
		String Nazwa;
		double Poziom;
	}

	static class Zgony
	{
		int LiczbaZgonow;
	}

	public static TreeMap<String, Zgony> getLiczbyZgonow1()
	{
		return LiczbyZgonow1;
	}

	public static void setLiczbyZgonow1(TreeMap<String, Zgony> liczbyZgonow1)
	{
		LiczbyZgonow1 = liczbyZgonow1;
	}

	public static TreeMap<String, Zgony> getLiczbyZgonow2()
	{
		return LiczbyZgonow2;
	}

	public static void setLiczbyZgonow2(TreeMap<String, Zgony> liczbyZgonow2)
	{
		LiczbyZgonow2 = liczbyZgonow2;
	}

	public static TreeMap<String, Zgony> getLiczbyZgonow3()
	{
		return LiczbyZgonow3;
	}

	public static void setLiczbyZgonow3(TreeMap<String, Zgony> liczbyZgonow3)
	{
		LiczbyZgonow3 = liczbyZgonow3;
	}
}
