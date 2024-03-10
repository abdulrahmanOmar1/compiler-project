package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SCANNER {
	
	public static String arrayTwoDi[][];
	public static String x1="=";
	public static String x2=">=";
	public static String x3="|=";
	public static String x4="||";
	public static String x5="<=";
	private static final Set<String> AllSymbols = new HashSet<>(Arrays.asList(">","*", ":", "<",".", ";", "=","<=", "-", ">=", ":=", "(", ")", "+", "|=", "/", ",", "="));
	private static final Set<String> Words = new HashSet<>(Arrays.asList("char","then", "loop",  "readchar", "end",
			"var","div", "readln", "writeint", "mod", "writechar", "until", "procedure", "end", "if","elseif",  "call", "while", "end", "writereal", "writeln", "exit","else", "module", "begin", "readint", "const",
			"readreal", "integer", "real","do"));
	public static int NumberOfTokenFunction(File f) {
		int NumberTO=0 , C = 0;
		try (
			BufferedReader R = new BufferedReader(new FileReader(f))) {
			String L;
			while ((L = R.readLine()) != null) {
//				System.out.print("Test -->"+C);
				C++;
				String[] arr = L.split("\\s+|(?<=[^a-zA-Z0-9])|(?=[^a-zA-Z0-9])");
				
//				for(int i=0 ; i<arr.length ; i++) {
//					System.out.print(arr[i]);
//					
//				}
//				
		        int ArrayLen = arr.length;
				for (int x = 0; x < ArrayLen; x++) {
		            if (!arr[x].isEmpty())
		            {
//        				System.out.print("Test:enter if");

		                if (x < ArrayLen-1 ) 
		                {
		                	
		                    String SYM = arr[x] + arr[x + 1];
		                    boolean bool=booleanTF(SYM);
		                    
		                    if (bool == true) {
//		        				System.out.print("bool is true");
		                        NumberTO++;
		                        x += 2;
//		        				System.out.print("Test1: x -->"+C+" , NumberTO--> "+NumberTO);
		                        if(x==ArrayLen) {
		                        	break;
//			        				System.out.print("Test: ----------");
		                        }else {
		                        	while(arr[x].isEmpty()){x++;} 
		                        	}

		                    } else if (IntTest(arr[x]) && ".".equals(arr[x + 1]) && IntTest(arr[x + 2]) ) 
		                    {
		                        x += 3;
		                        NumberTO++;
//		        				System.out.print("Test6: x -->"+C+" , NumberTO--> "+NumberTO);

		                        if(x==ArrayLen) {
//			        				System.out.print("Done------------------");
		                        	break;
		                        }
		                        else {
		                        	while(arr[x].isEmpty()){x++;} 
		                        	}

		                    } else if ("|".equals(arr[x]) && "=".equals(arr[x + 1]))
		                    {
		                        
		                        x += 2;
		                        NumberTO++;
//		        				System.out.print("Test5: x -->"+C+" , NumberTO--> "+NumberTO);

		                        if(x==ArrayLen) {
//			        				System.out.print("Done------------------");

		                        	break;
		                        }else {
		                        	while(arr[x].isEmpty()){x++;} 
		                        	}

		                    } else if (":".equals(arr[x]) && "=".equals(arr[x + 1])) {
		                        x += 2;
		                        NumberTO++;
//		        				System.out.print("Test4: x -->"+C+" , NumberTO--> "+NumberTO);
		                        if(x==ArrayLen) {
		                        	
//			        				System.out.print("Done------------------");

		                        	break;
		                        }else {
		                        	while(arr[x].isEmpty()){x++;}
		                        	}

		                    } else if (">".equals(arr[x]) && "=".equals(arr[x + 1])) {
		                    	
		                        x += 2;
		                        NumberTO++;
//		        				System.out.print("Test3: x -->"+C+" , NumberTO--> "+NumberTO);

		                        if(x==ArrayLen) {
//			        				System.out.print("Done------------------");

		                        	break;
		                        }else {
		                        	while(arr[x].isEmpty()){x++;}
		                        	}
		                        
		                        
		                    } else if ("=".equals(arr[x+1]) && "<".equals(arr[x])) {
		                        x += 2;
		                        NumberTO++;
//		        				System.out.print("Test2: x -->"+C+" , NumberTO--> "+NumberTO);
		                        if(x==ArrayLen)
		                        {
		                        	break;
		                        }else{
		                        	
		                        	while(arr[x].isEmpty()){x++;} 
		                        	}
		                    }
//		            		System.out.print("---------==-----------");

		                }
		                
		                     if (IntTest(arr[x])) {
		                        NumberTO++;
//		                		System.out.print("--->"+NumberTO);
		                    } else if (Words.contains(arr[x])) {
		                        NumberTO++;
//		                		System.out.print("--->"+NumberTO);

		                    } else if (AllSymbols.contains(arr[x]) ) {
		                        NumberTO++;
//		                		System.out.print("--->"+NumberTO);

		                    } else {
		                        NumberTO++;
//		                		System.out.print("--->"+NumberTO);

		                    }
		            }
		        }
		    }
		} catch (IOException ext) {
			ext.printStackTrace();
		}
		
		return NumberTO;
	}
	
	
	public static boolean booleanTF(String SYM) {
		if("|=".equals(SYM) || ">=".equals(SYM) || ":=".equals(SYM) || "<=".equals(SYM)) {
			return true ;
			
		}else {
			return false;
		}
	}	
	
	public static void GetFileData(File f) throws Exception {
		arrayTwoDi=new String[NumberOfTokenFunction(f)][3];
		int C = 0 ,k=0 , p=0 , num=0 , n=0;
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
			String line;
			while ((line = reader.readLine()) != null) {
				C++;
				String[] arr = line.split("\\s+|(?<=[^a-zA-Z0-9])|(?=[^a-zA-Z0-9])");
				
//				for(int i=0 ; i<arr.length ; i++) {
//					System.out.print(arr[i]);
//					
//				}
//	
		        int ArrayLen = arr.length;
				for ( n = 0; n < ArrayLen; n++) {
		            if (!arr[n].isEmpty()) {
		                if (n < ArrayLen-1 ) {
//							System.out.print(ArrayLen-1);

		                    String SYM = arr[n] + arr[n + 1];
		                    boolean bool=SSfunction(SYM);
		                    if (bool == true) {
		                    	FunctionAdd( arrayTwoDi,k,SYM,C,"SYMBOL");
		                        k++;
		                        n += 2;
		                        if(n==ArrayLen) {
//									System.out.print("Test 1");
		                        	break;
		                        }else {
		                        	while(arr[n].isEmpty()) {n++;}
		                        	}
		                    } else if ("<".equals(arr[n]) && "=".equals(arr[n + 1])) {
		                    	FunctionAdd(arrayTwoDi,k,"<=",C,"SYMBOL");
		                        k++;
		                        n += 2;
		                        if(n==ArrayLen) {
//									System.out.print("Test 2");

		                        	break;
		                        }else {
		                        	while(arr[n].isEmpty()) {n++;}
		                        	}

		                    } else if ("=".equals(arr[n + 1]) && ">".equals(arr[n]) ) {
		                    	FunctionAdd(arrayTwoDi,k,">=",C,"SYMBOL");
		                        k++;
		                        n += 2;
		                        if(n==ArrayLen) {
//									System.out.print("Test 3");

		                        	break;
		                        }else {
		                        	while(arr[n].isEmpty()) {n++;}
		                        	}

		                    } else if (":".equals(arr[n]) && "=".equals(arr[n + 1])) {
		                    	FunctionAdd(arrayTwoDi,k,":=",C,"SYMBOL");
		                        k++;
		                        n += 2;
		                        if(n==ArrayLen) {
//									System.out.print("Test 4");
		                        	break;
		                        }else {
		                        	while(arr[n].isEmpty()) {n++;}
		                        	}

		                    } else if ("|".equals(arr[n]) && "=".equals(arr[n + 1])) {
//								System.out.print("Test --");
		                    	FunctionAdd(arrayTwoDi,k,"|=",C,"SYMBOL");
		                        k++;
		                        n += 2;
		                        if(n==ArrayLen) {
//									System.out.print("Test 5");

		                        	break;
		                        }else {
		                        	while(arr[n].isEmpty()) {n++;}
		                        	}

		                    } else if (IntTest(arr[n]) && ".".equals(arr[n + 1]) && IntTest(arr[n + 2])) {
		                    	String v=arr[n] + arr[n + 1] + arr[n + 2];
		                    	FunctionAdd(arrayTwoDi,k,v,C,"REAL");
		                        k++;
		                        n += 3;
		                        if(n==ArrayLen) {
//									System.out.print("Test 6");

		                        	break;
		                        }else {
		                        	while(arr[n].isEmpty()) {n++;}
		                        	
		                        }

		                    }
		                    else if(IntTest(arr[n]) && ".".equals(arr[n + 1]) && !IntTest(arr[n + 2])) {
		                    	Worng("Worng : A set of integers with a (.) in a line "+C+"between them must make up the real value.");
		                    }
		                }
		                     if (AllSymbols.contains(arr[n])) {
			                    	String v=arr[n];
			                    	FunctionAdd(arrayTwoDi,k,v,C,"SYMBOL");
		                        k++;
		                    } else if (Words.contains(arr[n])) {
		                    	String v=arr[n];
		                    	FunctionAdd(arrayTwoDi,k,v,C,"RESERVED");
		                        k++;
		                    } else if (IntTest(arr[n])) {
		                    	String v=arr[n];
		                    	FunctionAdd(arrayTwoDi,k,v,C,"INTEGER");
		                        k++;
		                    } else if(booleanTest(arr[n])){
		                    	String v=arr[n];
		                    	FunctionAdd(arrayTwoDi,k,v,C,"NAME");
		                        k++;
		                    }else {
		                    	Worng("unidentified token  "+arr[n]+" in line : "+C);
		                    }
		            }
		        }
		    }
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void FunctionAdd(String[][] arr, int i, String v, int C, String type) {
		arr[i][0] = v;
		arr[i][1] = String.valueOf(C);
		arr[i][2] = type;
	}
	
	public static boolean SSfunction(String SYM) {
		if("<=".equals(SYM) ||":=".equals(SYM) ||  ">=".equals(SYM) || "!=".equals(SYM)) {
			return true ;
			
		}else {
			return false;
		}
	}

	
	private static boolean booleanTest(String word) {
//		System.out.print("Test :booleanTest called!");
		Pattern p = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9]*$");
		Matcher c = p.matcher(word);
//		System.out.print(c.matches());

		return c.matches();
	}
	
	public static void Worng(String msg) throws Exception {
		throw new Exception(msg);
	}

	private static boolean IntTest(String word) {
		try {
//			System.out.print("------------------in IntTest-------------------");
			Integer.parseInt(word);
			return true;
		} catch (NumberFormatException ess) {
			return false;
		}
	}
}
