package indeksy_covid;

import java.util.Locale;

//import toolsdebug.Assert;
import tools.Assert;

/**
 * Oblicza indeks restrykcyjności wg wzoru
 * w BSG-WP-2020-032-v13.pdf strona 46
 * Tytuł dokumentu "Variation in government responses to COVID-19"
 * Adres internetowy dokumentu
 * https://www.bsg.ox.ac.uk/sites/default/files/2022-04/BSG-WP-2020-032-v13.pdf
 * 
 * @author Andrzej Bystrzycki
 */
public class IndeksPolityki
{
	// Tabela maksymalnych wartości pojedynczych indeksów,
	// patrz kolumna Max value tabela str. 45-46 w BSG-WP-2020-032-v13.pdf
	private static int[] Nj =
	{
		3, // C1
		3, // C2
		2, // C3
		4, // C4
		2, // C5
		3, // C6
		2, // C7
		4, // C8
		2, // E1
		2, // E2
		2, // H1
		3, // H2
		2, // H3
		4, // H6
		5, // H7
		3  // H8
	};
	
	// Tabela flag pojedynczych indeksów,
	// patrz kolumna Flag tabela str. 45-46 w BSG-WP-2020-032-v13.pdf
	private static int[] Fj =
	{
		1, // C1
		1, // C2
		1, // C3
		1, // C4
		1, // C5
		1, // C6
		1, // C7
		0, // C8
		1, // E1
		0, // E2
		1, // H1
		0, // H2
		0, // H3
		1, // H6
		1, // H7
		1  // H8
	};
	
	// Tabela z listą nazw pojedynczych indeksów,
	// patrz tabela str. 45-46 w BSG-WP-2020-032-v13.pdf
	private static String[] NazwyIndeksow =
	{
		"C1",
		"C2",
		"C3",
		"C4",
		"C5",
		"C6",
		"C7",
		"C8",
		"E1",
		"E2",
		"H1",
		"H2",
		"H3",
		"H6",
		"H7",
		"H8"
	};
	
	public static void main(String[] args)
	{
		/* oblicza indeksy polityki COVID
		 * z tabeli BSG-WP-2020-032-v13.pdf strona 46.
		 */
		double c1 = obliczIndeks("C1", "2", "1");
		//System.out.println("c1="+c1);
		// dla c2 'no data' interpretowane jako pusty łańcuch
		double c2 = obliczIndeks("C2", "", "");
		//System.out.println("c2="+c2);
		double c3 = obliczIndeks("C3", "2", "0");
		//System.out.println("c3="+c3);
		double c4 = obliczIndeks("C4", "2", "0");
		//System.out.println("c4="+c4);
		// dla c5 'null' interpretowane jako pusty łańcuch
		double c5 = obliczIndeks("C5", "0", "");
		//System.out.println("c5="+c5);
		double c6 = obliczIndeks("C6", "1", "0");
		//System.out.println("c6="+c6);
		double c7 = obliczIndeks("C7", "1", "1");
		//System.out.println("c7="+c7);		 
		//W pliku OxCGRT_latest.csv nie ma kolumny flagi
		// dla indeksu c8, więc drugi parametr = N/A;
		// wpisanie pustego łańcucha powoduje obliczenie
		// błędnej wartości 0
		double c8 = obliczIndeks("C8", "3", "N/A");
		//System.out.println("c8="+c8);
		double e1 = obliczIndeks("E1", "2", "0");
		//System.out.println("e1="+e1);
		double e2 = obliczIndeks("E2", "2", "N/A");
		//System.out.println("e2="+e2);
		double h1 = obliczIndeks("H1", "2", "0");
		//System.out.println("h1="+h1);
		double h2 = obliczIndeks("H2", "3", "N/A");
		//System.out.println("h2="+h2);
		double h3 = obliczIndeks("H3", "2", "N/A");
		//System.out.println("h3="+h3);
		double h6 = obliczIndeks("H6", "2", "0");
		//System.out.println("h1="+h6);
		double h7 = obliczIndeks("H7", "2", "1");
		//System.out.println("h1="+h7);
		double h8 = obliczIndeks("H8", "2", "1");
		//System.out.println("h1="+h8);
		
		// Indeksy zbiorcze jako średnia arytmetyczna indeksów pojedynczych.
		// Patrz tabela strona 44 w BSG-WP-2020-032-v13.pdf,
		// które indeksy wchodzą do zbiorczych indeksów.
		// Wzór na zbiorcze indeksy: wzór (1) strona 44.
		
		// Government response index GRI
		double GRI = (c1+c2+c3+c4+c5+c6+c7+c8+e1+e2+h1+h2+h3+h6+h7+h8)/16;
		String sGRI = String.format(new Locale("US"), "%6.2f", GRI).trim();
		System.out.println("GRI="+sGRI);
		// Containment and health index CHI
		double CHI = (c1+c2+c3+c4+c5+c6+c7+c8+h1+h2+h3+h6+h7+h8)/14;
		String sCHI = String.format(new Locale("US"), "%6.2f", CHI).trim();
		System.out.println("CHI="+sCHI);
		// Stringency index SI
		double SI = (c1+c2+c3+c4+c5+c6+c7+c8+h1) / 9;
		String sSI = String.format(new Locale("US"), "%6.2f", SI).trim();
		System.out.println("SI="+sSI);
		// Economic support index ESI
		double ESI = (e1+e2)/ 2;
		String sESI = String.format(new Locale("US"), "%6.2f", ESI).trim();
		System.out.println("ESI="+sESI);

		// W pliku OxCGRT_latest.csv nie ma kolumny flagi
		// dla indeksu c8, więc drugi parametr = N/A;
		// wpisanie pustego łańcucha powoduje obliczenie
		// błędnej wartości 0
		c8 = obliczIndeks("C8", "3", "");
		//System.out.println("c8="+c8);
	}
	
	/**
	 * Oblicza indeks restrykcyjności wg wzoru
	 * w BSG-WP-2020-032-v13.pdf
	 * str. 46 wzór (2)
	 * 
	 * Lista (tabela) indeksów str. 31 w BSG-WP-2020-032-v13.pdf
	 * Wartośći indeksów do testowania: tabela str. 46-47
	 * w BSG-WP-2020-032-v13.pdf
	 * 
	 * @param _NazwaIndeksu nazwa (symbol) indeksu np. C1
	 * @param Svjt wartość indeksu np. "2" (typ String)
	 * @param Sfjt wartość flagi np. "1" (typ String)
	 * @return wartość indeksu - liczba w przedziale 0 - 100
	 */
	public static double obliczIndeks(String _NazwaIndeksu, String Svjt, String Sfjt)
	{
		int vjt = 0;
		int fjt = 0;
		int NrIndeksu = -1;
		
		for(int i=0; i < NazwyIndeksow.length; i++)
			if(_NazwaIndeksu.equals(NazwyIndeksow[i]))
			{
				NrIndeksu = i;
				break;
			}
		
		double indeks = 0.0;
		
		String Sindeks = "";

		if(NrIndeksu == -1)
		{
			indeks = -1;
			Sindeks = String.format(new Locale("US"), "%7.2f", indeks).trim();
			Assert.msg("Indicator=" +_NazwaIndeksu + " vj,t=" + vjt + " fj,t=" + fjt + " Nj=" + Nj[NrIndeksu] + " Fj=" + Fj[NrIndeksu] + " Ij,t=" + Sindeks);
			return indeks;
		}
				
		if(Sfjt.equals(""))
		{
			indeks = 0.0;
			Sindeks = String.format(new Locale("US"), "%7.2f", indeks).trim();
			Assert.msg("Indicator=" +_NazwaIndeksu + " vj,t=" + Svjt + " fj,t=" + Sfjt + " Nj=" + Nj[NrIndeksu] + " Fj=" + Fj[NrIndeksu] + " Ij,t=" + Sindeks);
			return indeks;
		}
		
		// zamiana łańcucha Sfjt na liczbę całkowitą
		// np. flaga C8, H2, H3
		if(Sfjt.equals("N/A") && Fj[NrIndeksu] == 0) 
			fjt = 0;
		else
		{
			if(!Sfjt.equals(""))
				fjt = Integer.parseInt(Sfjt);			
		}

		// zamiana łańcucha Svjt na liczbę typu double
		if(!Svjt.equals(""))
			vjt = (int) Double.parseDouble(Svjt);	
		
		// obliczenie indeksu wg wzoru (2) str 46 w BSG-WP-2020-032-v13.pdf
		indeks = 100 * (vjt - 0.5 * (Fj[NrIndeksu] - fjt)) / Nj[NrIndeksu];
		
		Sindeks = String.format(new Locale("US"), "%7.2f", indeks).trim();
		Assert.msg("Indicator=" +_NazwaIndeksu + " vj,t=" + Svjt + " fj,t=" + Sfjt + " Nj=" + Nj[NrIndeksu] + " Fj=" + Fj[NrIndeksu] + " Ij,t=" + Sindeks);
		
		return indeks;
	}

}
