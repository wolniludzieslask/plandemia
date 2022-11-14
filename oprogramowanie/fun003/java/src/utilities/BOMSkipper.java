package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;


public class BOMSkipper
{
    /**
     * a BOM as it appears internally
     */
    private static final char BOM = 0xfeff;

    
    public static void main(String[] args)
	{
		try
		{
			BufferedReader input = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("c:\\slownik_frp\\hasabom.txt"), "UTF-8"));
			// pomija BOM
			BOMSkipper.skip(input);
			// czyta tylko tekst (pierwsza linia)
			String s = input.readLine();
			System.out.println(s);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

    // Wykonanne w oparciu o dyskusję
    // pod adresem http://stackoverflow.com/questions/1835430/byte-order-mark-screws-up-file-reading-in-java#1835529
    public static void skip(Reader reader) throws IOException
    {
        reader.mark(1);
        char[] possibleBOM = new char[1];
        reader.read(possibleBOM);

        // Obojętnie, jakie kodowanie, Unicode reader z BOM
        // zaczyna się od takiego znaku (stała BOM)
        // Patrz klasa papwn_v_0_9_3.testBOMutf8 projektu abc
        // fragment po test reader
        
        // Jeśli to nie jest BOM
        // to czytaj od początku pliku
        if (possibleBOM[0] != BOM)
        {
            reader.reset();
        }
    }
    
    
    // Patrz klasa papwn_v_0_9_3.UnicodeUtil projektu abc
    // Stamtąd skopiowałem metodę
    public static String getBOM(String enc) throws UnsupportedEncodingException 
	{  
		if ("UTF-8".equals(enc)) 
		{  
			byte[] bom = new byte[3];  
			bom[0] = (byte) 0xEF;  
			bom[1] = (byte) 0xBB;  
			bom[2] = (byte) 0xBF;  
			return new String(bom, enc);  
		} 
		else if ("UTF-16BE".equals(enc)) 
		{  
			byte[] bom = new byte[2];  
			bom[0] = (byte) 0xFE;  
			bom[1] = (byte) 0xFF;  
			return new String(bom, enc);  
		} 
		else if ("UTF-16LE".equals(enc)) 
		{  
			byte[] bom = new byte[2];  
			bom[0] = (byte) 0xFF;  
			bom[1] = (byte) 0xFE;  
			return new String(bom, enc);  
		} 
		else if ("UTF-32BE".equals(enc)) 
		{  
			byte[] bom = new byte[4];  
			bom[0] = (byte) 0x00;  
			bom[1] = (byte) 0x00;  
			bom[2] = (byte) 0xFE;  
			bom[3] = (byte) 0xFF;  
			return new String(bom, enc);  
		} else if ("UTF-32LE".equals(enc)) 
		{  
			byte[] bom = new byte[4];  
			bom[0] = (byte) 0x00;  
			bom[1] = (byte) 0x00;  
			bom[2] = (byte) 0xFF;  
			bom[3] = (byte) 0xFE;  
			return new String(bom, enc);  
		} else 
		{  
			return null;  
		}  
  
	}  
    
}

