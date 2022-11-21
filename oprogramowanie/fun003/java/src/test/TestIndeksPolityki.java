package test;

import indeksy_covid.IndeksPolityki;

/**
 * Oblicza indeksy polityki z opracowania
 * https://www.bsg.ox.ac.uk/research/publications/variation-government-responses-covid-19
 * do porównania z tabelą str 45-46
 * Adres internetowy dokumentu
 * https://www.bsg.ox.ac.uk/sites/default/files/2022-04/BSG-WP-2020-032-v13.pdf
 * 
 * @author Andrzej Bystrzycki
 *
 */
public class TestIndeksPolityki
{

	public static void main(String[] args)
	{
		double C1 = IndeksPolityki.obliczIndeks("C1", "2", "1");
		System.out.println("C1="+C1+ " wartość w tabeli=66.67");
		double C2 = IndeksPolityki.obliczIndeks("C2", "", "");
		System.out.println("C2="+C2+ " wartość w tabeli=0.00");
		double C3 = IndeksPolityki.obliczIndeks("C3", "2", "0");
		System.out.println("C3="+C3+ " wartość w tabeli=75.00");
		double C4 = IndeksPolityki.obliczIndeks("C4", "2", "0");
		System.out.println("C4="+C4+ " wartość w tabeli=37.50");
		double C5 = IndeksPolityki.obliczIndeks("C5", "0", "");
		System.out.println("C5="+C5+ " wartość w tabeli=0.00");
		double C6 = IndeksPolityki.obliczIndeks("C6", "1", "0");
		System.out.println("C6="+C6+ " wartość w tabeli=16.67");
		double C7 = IndeksPolityki.obliczIndeks("C7", "1", "1");
		System.out.println("C7="+C7+ " wartość w tabeli=50.00");
		double C8 = IndeksPolityki.obliczIndeks("C8", "3", "N/A");
		System.out.println("C8="+C8+ " wartość w tabeli=75.00");
		double E1 = IndeksPolityki.obliczIndeks("E1", "2", "0");
		System.out.println("E1="+E1+ " wartość w tabeli=75.00");
		double E2 = IndeksPolityki.obliczIndeks("E2", "2", "N/A");
		System.out.println("E2="+E2+ " wartość w tabeli=100.00");
		double H1 = IndeksPolityki.obliczIndeks("H1", "2", "0");
		System.out.println("H1="+H1+ " wartość w tabeli=75.00");
		double H2 = IndeksPolityki.obliczIndeks("H2", "3", "N/A");
		System.out.println("H2="+H2+ " wartość w tabeli=100.00");
		double H3 = IndeksPolityki.obliczIndeks("H3", "2", "N/A");
		System.out.println("H3="+H3+ " wartość w tabeli=100.00");
		double H6 = IndeksPolityki.obliczIndeks("H6", "2", "0");
		System.out.println("H6="+H6+ " wartość w tabeli=37.50");
		double H7 = IndeksPolityki.obliczIndeks("H7", "2", "1");
		System.out.println("H7="+H7+ " wartość w tabeli=40.00");
		double H8 = IndeksPolityki.obliczIndeks("H8", "2", "1");
		System.out.println("H8="+H8+ " wartość w tabeli=66.66");
		
		double GRI = (C1+C2+C3+C4+C5+C6+C7+C8+E1+E2+H1+H2+H3+H6+H7+H8)/16;
		System.out.println("GRI="+GRI+ " wartość w tabeli=57.18");
		double CHI = (C1+C2+C3+C4+C5+C6+C7+C8+H1+H2+H3+H6+H7+H8)/14;
		System.out.println("CHI="+CHI+ " wartość w tabeli=52.86");
		double SI = (C1+C2+C3+C4+C5+C6+C7+C8+H1)/ 9;
		System.out.println("SI="+SI+ " wartość w tabeli=43.98");
		double ESI = (E1+E2)/ 2;
		System.out.println("ESI="+ESI+ " wartość w tabeli=87.50");
	}

}
