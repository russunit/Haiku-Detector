import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class DictionaryCreator {
	
	//sylWord.txt
	//mostCommonWords.txt
	//mostCommonWS.txt
	
	
	public String mCW;//mostcommonwords.txt
	public int mCWlength;
	public String mCWs[];
	public int mCWSyls[];
	
	public String sW;
	public int sWlength;
	public String sWs[];
	
	public int hSwl;
	public String sWords[];
	public int sSyls[];
	
	DictionaryCreator()
	{
		try 
		{
			mCW = fileToString("c:/testdocs/mostcommonwords.txt");
		}
		catch (IOException ioe) 
		{
			System.out.println("IO ERROR!");
		}
		
		mCWlength = countWords(mCW);
		mCWs = new String[mCWlength];
		mCWSyls = new int[mCWlength];
		
		mCWs = sepWords(mCW, mCWlength);
		
		try 
		{
			sW = fileToString("c:/testdocs/sylword.txt");
		}
		catch (IOException ioe) 
		{
			System.out.println("IO ERROR!");
		}
		
		sWlength = countWords(sW);
		sWs = new String[sWlength];
		sWs = sepWords(sW, sWlength);
		
		
		hSwl = sWlength/2;
		sWords = new String[hSwl];
		sSyls = new int[hSwl];
		
		int y = 0;
		int z = 0;
		for(int x = 0; x < sWlength; x++)
		{
			if(x%2==0)
			{
				sSyls[y] = Integer.parseInt(sWs[x]);
				y++;
			}
			else
			{
				sWords[z] = (sWs[x]);
				z++;
			}
		}//now i have 2 matching arrrays of words and syls.
		
		//test
		for(int x = 0; x<hSwl; x++)
		{
			System.out.println(sWords[x] + " " + sSyls[x]);
		}
		
		
		
		
		
		for(int x = 0; x < mCWlength; x++)
			for(int w = 0; w < hSwl; w++)
			{
				if(mCWs[x]==sWords[w])
				{
					mCWSyls[x]=sSyls[w];
					break;
				}
			}
		
		//test
		for(int x = 0; x<mCWlength; x++)
		{
			//System.out.println(mCWs[x] + " " + mCWSyls[x]);
		}
		
		
	}
	
	//string processors
	public int countWords(String inStr)
	{
		int nWords = 0;
		
		inStr = inStr.toLowerCase();//all lower case
		inStr = inStr + " ";
		//System.out.println("length = " + inStr.length());/////////////////////////////
		
		for(int x=1; x<inStr.length(); x++)
		{
			char aCh = inStr.charAt(x-1);
			//System.out.println("aCh = " + aCh);/////////////////////////////
			char bCh = inStr.charAt(x);
			//System.out.println("bCh = " +bCh+"\n");/////////////////////////////

			
			if(isaLetter(aCh))//ach is a letter
				if(!isaLetter(bCh))//bch is whitespace or punctuation or a symbol(the word ended)
					nWords++;
		}
		
		return nWords;//the number of words in the string.
	}
	public String[] sepWords(String inStr, int numW)
	{
		
		for(int x=0; x<inStr.length(); x++)//eliminate nonletters at beginning
		{
			if(!isaLetter(inStr.charAt(x)))
			{
				StringBuilder newStr = new StringBuilder(inStr);
				newStr.deleteCharAt(x);
				inStr = newStr.toString();
			}
			else
				break;
		}
		
		String[] splitStr = inStr.split("\\P{Alpha}+", numW);//delimiter=anything non-letter

		
		return splitStr;
	}
	
	//character recognition
	public boolean isaLetter(char c)
	{
		if(Character.isLetter(c))
			return true;
		else
			return false;
	}
	public boolean isaVowel(char c)
	{
		if(isaLetter(c))
			if(c=='a'||c=='e'||c=='i'||c=='o'||c=='u'||c=='y')
				return true;
			else
				return false;
		else
			return false;
	}
	public boolean isaConsonant(char c)
	{
		if(isaLetter(c))
			if(!isaVowel(c))
				return true;
			else
				return false;
		else
			return false;
	}
	
	public static String fileToString(String fileName) throws IOException 
	{ 
		@SuppressWarnings("resource")
		String entireFileText = new Scanner(new File(fileName))
		.useDelimiter("\\A").next();
			 
		System.out.println(entireFileText);
		return entireFileText;
	}

	public static void main(String[] args)
	{
		DictionaryCreator Dic = new DictionaryCreator();

	}

}
