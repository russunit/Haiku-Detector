import java.io.*;
//import java.util.*;
import java.util.Scanner;

public class wordList 
{
	
	public String inString;
	public int numWords;
	
	public  String allWords[];
	public int wordSyls[];
	
	wordList(){}
	
	//setters
	public void setInString(String s)
	{inString = s;}
	public void setNumWords(int i)
	{numWords = i;}
	public void setAllWords(String[] s)
	{
		allWords = new String[numWords];
		for(int x=0; x<numWords; x++)
			allWords[x] = s[x];
	}
	public void setWordSyls()
	{
		wordSyls = new int[numWords];
		for(int x=0; x<numWords; x++)
		{
			wordSyls[x] = countSyls(allWords[x]);
		}
		
	}
	
	//getters
	public String getWord(int i)
	{
		return allWords[i];
	}
	public int getWordSyls(int i)
	{
		return wordSyls[i];
	}
	public int getNumWords()
	{return numWords;}
	public String getInString()
	{return inString;}
	
	//string loaders
	public static String fileToString(String fileName) throws IOException 
	{ 
		@SuppressWarnings("resource")
		String entireFileText = new Scanner(new File(fileName))
		.useDelimiter("\\A").next();
			 
		System.out.println(entireFileText);
		return entireFileText;
	}
		  
	
	public String formToString()//not done
	{
		String outStr = "";
		return outStr;
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
		
		numWords = nWords;
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
	public int countSyls(String inStr)
	{
		
		//a rule-based system that i created; accurate >95%, usually >98%.
		
		int numSyls = 0;
		inStr = inStr + " ";
		
		for(int x=1; x<inStr.length(); x++)
		{
			char aCh = inStr.charAt(x-1);
			char bCh = inStr.charAt(x);
			
			
			if(aCh=='i')
				if(bCh=='u')
					numSyls++;//syllable detected (-iu-)
			
			if(aCh=='u')
				if(bCh=='a')
				{
					numSyls++;//syllable detected (-ua-)
					if(x >= 3)
					{
						char zCh = inStr.charAt(x-2);//char before ach
						if(zCh=='q'||zCh=='g')
							numSyls--;//false syllable (gua, qua)

					}
				}
			
			if(aCh=='y')
				if(bCh=='i')
					numSyls++;//syllable detected (-yi-)
			
			if(aCh=='i')
				if(bCh=='a')
					numSyls++;//syllable detected (-ia-)


			
			if(x >= 3)
			{
				char zCh = inStr.charAt(x-2);//char before ach

				if(isaVowel(aCh)&&isaVowel(bCh)&&isaVowel(zCh))
					numSyls++;//syllable detected (3 vowels in a row)
				
				if(zCh=='i')
					if(aCh=='a')
						if(!isaLetter(bCh)||bCh=='s')
							numSyls++;//syllable detected (-ia)	or (-ias)
			}
			
			if( x >= 5)
			{
				char zCh = inStr.charAt(x-2);//char before ach
				char yCh = inStr.charAt(x-3);//char before zch
				char xCh = inStr.charAt(x-4);//char before ych
				char wCh = inStr.charAt(x-5);//char before xch

				
				if(!isaLetter(bCh))
					if(aCh=='s'&&zCh=='s')
						if(yCh=='e'&&(xCh=='l'||xCh=='n'))
							if(wCh=='e')// false syllable (-eless / -eness )
								numSyls--;
				
				if(!isaLetter(bCh))
					if(aCh=='y'&&zCh=='l')
						if(yCh=='e'&&isaConsonant(xCh))
							if(isaVowel(wCh))// false syllable (-vowel-consonant-ely )
								numSyls--;
			}
			
				
			
			if(isaVowel(aCh))
				if(!isaVowel(bCh))//syllable detected
				{
					numSyls++;
					
					if(x>=3)
						if((aCh=='e'&&bCh=='s')&&!isaLetter(inStr.charAt(x+1))&&isaVowel(inStr.charAt(x-3)))//-es case, false syllable
						{
							numSyls--;

							char zCh = inStr.charAt(x-2);//char before ach
							if(zCh=='c'||zCh=='g'||zCh=='h'||zCh=='j'||zCh=='s'||zCh=='x'||isaVowel(zCh))//soft consonant before ach
								numSyls++;

						}
					
					if((aCh=='e'&&bCh=='d')&&!isaLetter(inStr.charAt(x+1)))//-ed case, false syllable detected
					{
						numSyls--;
						
						if(x>=3)
						{
							char zCh = inStr.charAt(x-2);//char before ach
							if(zCh=='d'||zCh=='t'||isaVowel(zCh))//d or t or vowel before ach
							{
								numSyls++;

							}
						}
						
						
					}
					
					if(isaLetter(aCh)&&!isaLetter(bCh))//end of word
					{
						
						if(aCh=='e'&&!isaLetter(bCh))//-e case, false syllable detected
						{
							numSyls--;
							
							if(x>=3)
							{
								char zCh = inStr.charAt(x-2);//char before ach
								
								if(zCh=='l')//a vowel before zCh
								{
									numSyls++;
									if(inStr.length()>=4)
									{
										char yCh = inStr.charAt(x-3);//char before zch
										if(isaVowel(yCh))//vowel before le, not syllable
											numSyls--;
									}
								}
							}
								
						}
					}
					
				}
		}
		
		//Of the 500 most commonly used words, 6 were exceptions to the rules.
		//They are corrected here...

		if(inStr.contains("beauty")&&inStr.length()==7)
			numSyls = 2;
		
		if(inStr.contains("idea")&&inStr.length()==5)
			numSyls = 3;
		if(inStr.contains("science")&&inStr.length()==8)
			numSyls = 2;
		if(inStr.contains("special")&&inStr.length()==8)
			numSyls = 2;
		if(inStr.contains("area")&&inStr.length()==5)
			numSyls = 3;
		if(inStr.contains("hundred")&&inStr.length()==8)
			numSyls = 2;
		
		
		if(numSyls==0)//zero syllable word case
			numSyls++;
		
		return numSyls;
	}
	public String HaikuCollector()//not done
	{
		String Haikus = "";

		return Haikus;
	}
	public String DetectHaikus(String inStr)//not done
	{
		String allHaikus = "";
		
		return allHaikus;
	}


	
	//main tester
	public static void main(String[] args)
	{
		//Scanner console = new Scanner(System.in);
		
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

		String sentence = "";
		String fName ="";
		int sWords;
		String divSentence[];
		
		
		wordList hWords = new wordList();
		
		//input string
		
		boolean guard1 = true;
		while(guard1)
		{
			guard1 = false;
			System.out.println("STRING: ");
			try 
			{
				sentence = buff.readLine();
			}
			catch (IOException ioe) 
			{
			System.out.println("IO ERROR!");
			guard1 = true;
		    }
		}
		
		//process string
		
		sentence = sentence.replaceAll("0", "");
		sentence = sentence.replaceAll("1", "");
		sentence = sentence.replaceAll("2", "");
		sentence = sentence.replaceAll("3", "");
		sentence = sentence.replaceAll("4", "");
		sentence = sentence.replaceAll("5", "");
		sentence = sentence.replaceAll("6", "");
		sentence = sentence.replaceAll("7", "");
		sentence = sentence.replaceAll("8", "");
		sentence = sentence.replaceAll("9", "");
		sentence = sentence.replaceAll("\'", "");
		sentence = sentence.replaceAll("\"", "");
		sentence = sentence.replaceAll("\n", " ");


		sentence = sentence.toLowerCase();

		sentence = sentence.replaceAll("\\P{Alpha}+", " ");
		
		sentence.toLowerCase();
		hWords.setInString(sentence);
		sWords = hWords.countWords(sentence);
		
		//output string
		
		System.out.println("\nWORDS: ");
		System.out.println(sWords);
		
		
		divSentence = hWords.sepWords(sentence, sWords);
		System.out.println("\nSEPARATED WORDS: ");
		
		hWords.setAllWords(divSentence);
		hWords.setWordSyls();


		for(int x=0; x<sWords; x++)
		{
			//int n = hWords.countSyls(hWords.getWord(x));
			System.out.print(hWords.getWordSyls(x) + " ");
			System.out.println(hWords.getWord(x) + " ");
			//if(x%20 == 0)
			//	System.out.println();
		}
		
////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

		
		//input file
		guard1 = true;
		while(guard1)
		{
			guard1 = false;
			System.out.println("\nFILE NAME: ");
			try 
			{
				fName = buff.readLine();
				sentence = fileToString(fName);
			}
			catch (IOException ioe) 
			{
			System.out.println("IO ERROR!");
			guard1 = true;
		    }
		}
		
		//process file
		
		sentence = sentence.replaceAll("0", "");
		sentence = sentence.replaceAll("1", "");
		sentence = sentence.replaceAll("2", "");
		sentence = sentence.replaceAll("3", "");
		sentence = sentence.replaceAll("4", "");
		sentence = sentence.replaceAll("5", "");
		sentence = sentence.replaceAll("6", "");
		sentence = sentence.replaceAll("7", "");
		sentence = sentence.replaceAll("8", "");
		sentence = sentence.replaceAll("9", "");
		
		
		sentence = sentence.replaceAll("\'", "");
		sentence = sentence.replaceAll("\"", "");
		sentence = sentence.replaceAll("\n", " ");


		sentence = sentence.toLowerCase();

		sentence = sentence.replaceAll("\\P{Alpha}+", " ");////////////////////////////////////////
		
		sentence.toLowerCase();
		hWords.setInString(sentence);
		sWords = hWords.countWords(sentence);
		
		//output file
		
		System.out.println("\nWORDS: ");
		System.out.println(sWords);
		
		
		divSentence = hWords.sepWords(sentence, sWords);
		System.out.println("\nSEPARATED WORDS: ");
		
		hWords.setAllWords(divSentence);
		hWords.setWordSyls();


		for(int x=0; x<sWords; x++)
		{
			//int n = hWords.countSyls(hWords.getWord(x));
			System.out.print(hWords.getWordSyls(x) + " ");
			System.out.print(hWords.getWord(x) + " ");
			//if(x%20 == 0)
				System.out.println();
		}
		
		System.out.println("\nWORDS: ");
		System.out.println(sWords);
		
		

	}
	
	
}
