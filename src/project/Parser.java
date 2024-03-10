package project;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Parser {

	// Name : abdulrahman omar
	// ID :1201811

	private static final Set<String> followStat = new HashSet<>(Arrays.asList("end", "until", "else", "elseif", ";"));
	public static String Labels[] = new String[2];
	private static final Set<String> startvar = new HashSet<>(Arrays.asList("var"));
	static int C = 0;
	public static final Set<String> constFollow = new HashSet<>(Arrays.asList("begin", "procedure", "var"));
	private static final Set<String> varFollw = new HashSet<>(Arrays.asList("procedure", "begin"));
	public static final Set<String> constFirst = new HashSet<>(Arrays.asList("const"));
	private static final Set<String> LIST = new HashSet<>(Arrays.asList("if", "exit", "while", "call", "writeln",
			"writeint", "readint", "loop", "readln", "name", "readreal", "writechar", "writereal", "readchar"));
	public static String arr2[][];

	public static void main(String[] args) throws Exception {

		Scanner s = new Scanner(System.in);
		System.out.println("Hello ! \nPlease enter file Path: ");
		String Path = s.nextLine();
		File f = new File(Path.trim());

		SCANNER.GetFileData(f);
		arr2 = SCANNER.arrayTwoDi;
//		System.out.print("Test :---- read is Done ------");
		module_Heading();
		declarFuction();
		procedure_Dec();
		block();

		if (!Labels[0].equals(arr2[C][0])) {
			Worng("Error :the Modula name at the beginning of the file does not match the modula "
					+ "name at the end of the file, please make sure the file is correct");
		}
		NAMEfunction();
		if (C == arr2.length) {
			Worng("Error : You must put . at the end of the file you attached ");
		}

		if (arr2[C][0].equals(".")) {
			C++;
//				System.out.print("Test in main(): "+C);
		} else {
			Worng(" Error : You missed . on line: " + arr2[C][1] + "   found " + arr2[C][0]);
		}

		if (C < arr2.length) {
			Worng("Error : There is an additional code after . check the file ");
		}
		System.out.println("Done :) , there is nothing wrong!");

	}

	public static void block() throws Exception {
		if (arr2[C][0].equals("begin")) {
//			System.out.print("Test : begin");
			C++;
		} else {
			Worng("Error : There is a missing begin in the line ->   " + arr2[C][1] + " , please check the file.");
		}
		stmtList();

		if (arr2[C][0].equals("end")) {
//			System.out.print("Test : end");
			C++;
		} else {
			Worng("Error : There is a missing end in the line ->" + arr2[C][1] + " , please check the file.");
		}
	}

	public static void procedure_Dec() throws Exception {
		procedure_Heading();
		declarFuction();
		block();
//		System.out.print("Test: in function --> procedureDec() ");
		if (!Labels[1].equals(arr2[C][0])) {
			Worng("Error :procedure name mismatch both at the beginning and the end of the procedure");
		}
//		System.out.print("\n");
//		int x=0;
		NAMEfunction();
		if (arr2[C][0].equals(";")) {
//			 x++;
			C++;
		} else {
			Worng("Error There is a missing ;  in the line -> " + arr2[C][1] + " , please check the file.");
		}
//			System.out.print(x);

	}

	public static void stmtList() throws Exception {
//		System.out.print("Test : -------------1-------------");
		statemnt();
//		System.out.print("Test : ------------2--------------");
		while (arr2[C][0].equals(";")) {
			C++;
			statemnt();
		}

	}

	public static void declarFuction() throws Exception {
		if (constFirst.contains(arr2[C][0])) {
			if (arr2[C][0].equals("const")) {
				C++;
			}
//			System.out.print("Test in declaration(): before consatntList() Test -----");
			consatntList();
		} else if (constFollow.contains(arr2[C][0])) {
//			System.out.print("Test in declaration(): else if -> Test -----");
		}

		if (startvar.contains(arr2[C][0])) {
			if (arr2[C][0].equals("var")) {
				C++;
			}
			FunctionVARlist();
		} else if (varFollw.contains(arr2[C][0])) {
//			System.out.print("Test in declaration(): else if -> Test -----");
		}

	}

	public static void NAMEfunction() throws Exception {
//		int i = 0;
//		String[] StringArray = arr2[C][0].toString();
		char[] CharArray = arr2[C][0].toCharArray();

		if (LATTER(CharArray[0])) {
			int x = 1;
			while (x < CharArray.length) {
				if (Digtest(CharArray[x]) || LATTER(CharArray[x])) {
//					System.out.print("Test :Done");
				} else {
					Worng("Error : Name has to be a line of letters and numbers in line: " + arr2[C][1]
							+ " , please check the file. ");
				}
				x++;
			}
		} else {
			Worng("Error :The name must begin with a letter in a line:  " + arr2[C][1] + " , please check the file. ");
		}

		C++;
//		System.out.print("Test in function Lables " + C +" >>");

	}

	public static void term() throws Exception {
		factor();
		while (arr2[C][0].equals("div") || arr2[C][0].equals("/") || arr2[C][0].equals("mod")
				|| arr2[C][0].equals("*")) {
			if (arr2[C][0].equals("*")) {
				C++;
			} else if (arr2[C][0].equals("/")) {
				C++;
			} else if (arr2[C][0].equals("mod")) {
				C++;
			} else if (arr2[C][0].equals("div")) {
				C++;
			} else {
				Worng("Error :It's supposed to be (+) or (-) or (div) or (mod)  at line: " + arr2[C][1]
						+ " But it does not exist , and exists  " + arr2[C][0]);
			}
			factor();
		}
//		System.out.print("---------out while");

	}

	public static void elseifFunction() throws Exception {
		while (arr2[C][0].equals("elseif")) {
			C++;
			nameOrValue();
			realtionalOperation();
			nameOrValue();

			if (arr2[C][0].equals("then")) {
//				System.out.print("Test :-----------------");
				C++;
			} else {
				Worng("Error : There is a missing then in the line ->" + arr2[C][1] + " ,please check the file.");
			}
			stmtList();
		}

	}

	public static void realtionalOperation() throws Exception {
		if (arr2[C][0].equals(">=")) {
//			System.out.print("Test --- >=");
			C++;
		} else if (arr2[C][0].equals("<")) {
//			System.out.print("Test --- <");
			C++;
		} else if (arr2[C][0].equals("|=")) {
//			System.out.print("Test --- |=");
			C++;
		} else if (arr2[C][0].equals(">")) {
//			System.out.print("Test --- >");
			C++;
		} else if (arr2[C][0].equals("<=")) {
//			System.out.print("Test --- <=");
			C++;
		} else if (arr2[C][0].equals("=")) {
//			System.out.print("Test --- =");
			C++;
		} else {
			Worng("Error :It's supposed to be (=) || (|=) || (<) || (<=) || (>) || (>=) at line: " + arr2[C][1]
					+ " But it does not exist , and exists  " + arr2[C][0]);
		}
	}

	public static void nameOrValue() throws Exception {
		if (arr2[C][2].equals("NAME")) {
			NAMEfunction();
		} else if (arr2[C][2].equals("REAL")) {
			intgerValue();
		} else if (arr2[C][2].equals("INTEGER")) {
			intgerValue();
		} else {
			Worng("Error :It's supposed to be (NAME) or (INTEGER) or (REAL) at line: " + arr2[C][1]
					+ " But it does not exist , and exists  " + arr2[C][0]);
		}
	}

	public static void ListW() throws Exception {
		writeItem();
		while (arr2[C][0].equals(",")) {
			C++;
//			System.out.print("Test :"+C);
			writeItem();
		}
	}

	public static void writeItem() throws Exception {
		if (arr2[C][2].equals("NAME")) {
			NAMEfunction();
//			System.out.print("---------");
		} else if (arr2[C][2].equals("REAL")) {
			intgerValue();
		} else if (arr2[C][2].equals("INTEGER")) {
			intgerValue();
//			System.out.print("---------");

		} else {
			Worng("Error : There are not valid write item in line: " + arr2[C][1] + " ,please check the file.");
		}
	}

	public static void assignment() throws Exception {
		if (arr2[C][2].equals("NAME")) {
			NAMEfunction();
		}
		if (arr2[C][0].equals(":=")) {
//			System.out.print("---------");
			C++;
		} else {
			Worng("Error : There is a missing := in the line ->" + arr2[C][1] + " ,please check the file.");
		}
		exp();
	}

	public static void exp() throws Exception {
		term();
		while (arr2[C][0].equals("-") || arr2[C][0].equals("+")) {

			if (arr2[C][0].equals("-")) {
//				System.out.print("---------> -");

				C++;
			} else if (arr2[C][0].equals("+")) {
//				System.out.print("---------> +");

				C++;
			} else {
//				System.out.print("---------Error");
				Worng("Error :It's supposed to be (+) or (-) at line: " + arr2[C][1]
						+ " But it does not exist , and exists  " + arr2[C][0]);
			}
			term();
		}
	}

	public static void intgerValue() throws Exception {
		if (arr2[C][2].equals("INTEGER")) {

			char[] CharArray = arr2[C][0].toCharArray();
			int x = 0;
			while (x < CharArray.length) {
				if (Digtest(CharArray[x])) {
//					System.out.print("Test: INTEGER  is True");
				} else {
					Worng("Error : The value of an integer must be a set of digits only " + arr2[C][1]
							+ " ,please check the file.");
				}
				x++;
			}
			C++;
		} else if (arr2[C][2].equals("REAL")) {
//				char[] StringArray = arr2[C][0].toString();
			char[] CharArray = arr2[C][0].toCharArray();

			int x = 0;
			while (x < CharArray.length) {
				if (Digtest(CharArray[x]) || CharArray[x] == '.') {
//						System.out.print("Test in REAL is True ");
				} else {
					Worng("Error : In line " + arr2[C][1]
							+ ", there has to be a series of integers with a . in between them");
				}
				x++;
			}
			C++;
		}
	}

	public static void factor() throws Exception {
		if (arr2[C][0].equals("(")) {
			C++;
//			System.out.print("---> " + C);

			exp();

			if (arr2[C][0].equals(")")) {
				C++;

			} else {
				Worng("Error : There is a missing ) in the line ->" + arr2[C][1] + " ,please check the file.");
			}

		} else if (arr2[C][2].equals("NAME")) {
			NAMEfunction();
		} else if (arr2[C][2].equals("INTEGER") || arr2[C][1].equals("REAL")) {
			intgerValue();
		} else {
			Worng("Error :It's supposed to be (intger) or (real) or ( ( )  at line: " + arr2[C][1]
					+ " But it does not exist , and exists  " + arr2[C][0]);
		}
	}

	public static void procedure_Heading() throws Exception {

		if (arr2[C][0].equals("procedure")) {
//			System.out.print("Test:procedur-->True");
			C++;
		} else {
			Worng("Error :There is procedure keyword missing in the line in line: " + arr2[C][1]
					+ ",please check the file.");
		}

		Labels[1] = arr2[C][0];
		NAMEfunction();
		if (arr2[C][0].equals(";")) {
			C++;
		} else {
			Worng("Error :There is ; missing in the line in line: " + arr2[C][1] + ",please check the file.");
		}
	}

	public static void FunctionVARlist() throws Exception {
		while (arr2[C][2].equals("NAME")) {
			VARfunc();
			if (arr2[C][0].equals(";")) {
//				System.out.print("Test in     :  Test -----");
				C++;
			} else {
				Worng("Error : There is a missing ; in the line  " + arr2[C][1] + ",please check the file.");
			}
		}
	}

	public static void VARfunc() throws Exception {
		nameList();
		if (arr2[C][0].equals(":")) {
//			System.out.print("Test in     :  Test -----");
			C++;
		} else {
			Worng("Error : There is a missing : in the line  " + arr2[C][1] + "please check the file");
		}
		TypeOfData();
	}

	public static void nameList() throws Exception {
		if (arr2[C][2].equals("NAME")) {
			NAMEfunction();
//			System.out.print("Test in    :  Test -----");
		}
		while (arr2[C][0].equals(",")) {
//			System.out.print("Test in     :  Test -----");
			C++;
			NAMEfunction();
		}
	}

	public static void TypeOfData() throws Exception {
		if (arr2[C][0].equals("real")) {
//			System.out.print("Test: in line +"arr2[C][1]"+is real");
			C++;
		} else if (arr2[C][0].equals("integer")) {
//			System.out.print("Test: in line +"arr2[C][1]"+is integer");
			C++;
		} else if (arr2[C][0].equals("char")) {
//			System.out.print("Test: in line +"arr2[C][1]"+is char");
			C++;
		} else {
			Worng("Error : invalid data type on line  " + arr2[C][1] + " ,please check the file.");
		}
	}

	public static void consatntList() throws Exception {
		while (arr2[C][2].equals("NAME")) {
			NAMEfunction();
			if (arr2[C][0].equals("=")) {
//				System.out.print("Test: "+arr2[C][0] );
				C++;
			} else {
				Worng("Error :There is a missing = in the line: " + arr2[C][1] + " ,please check the file.");
			}

			intgerValue();
			if (arr2[C][0].equals(";")) {
//				System.out.print("Test: "+arr2[C][0] );
				C++;
			} else {
				Worng("Error :There is a missing ; in the line: " + arr2[C][1] + "please check the file.");
			}
		}
	}

	public static void module_Heading() throws Exception {

		if (arr2[C][0].equals("module")) {
//			System.out.print("Test in Startmodule() :module is exists---------  ");
			C++;
		} else {
			Worng("Error:  module keyword is missing in this line: " + arr2[C][1]
					+ " , please make sure the file is correct");
		}

		Labels[0] = arr2[C][0];
		NAMEfunction();
//		System.out.print("Test in Startmodule():"+ Labels[0] +" ---------  ");
		if (arr2[C][0].equals(";")) {
			C++;
//			System.out.print("Test in function Startmodule() :"+ C +" ---------  ");
		} else {
			Worng("Error : There is a missing semicolon in the line " + arr2[C][1] + ", please check the file");
		}
	}

	public static void elseP() throws Exception {
		if (arr2[C][0].equals("else")) {
//			System.out.print("----else---");
			C++;
			stmtList();
		} else if (arr2[C][0].equals("end")) {
//			System.out.print("-----------------");
		} else {
			Worng("Error : There are  not expected token for else in line " + arr2[C][1] + " ,please check the file.");
		}
	}

	public static boolean LATTER(char c) {
		return Character.isLetter(c);
	}

	public static boolean Digtest(char c) {
		return Character.isDigit(c);
	}

	public static void Worng(String msg) throws Exception {
		throw new Exception(msg);
	}

	public static void statemnt() throws Exception {
		if (arr2[C][2].equals("NAME") || LIST.contains(arr2[C][0])) {
			if (arr2[C][2].equals("NAME")) {
				assignment();
			} else if (arr2[C][0].equals("readint")) {
				C++;
				if (arr2[C][0].equals("(")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {

					Worng("Error : There is a missing ( in the line ->" + arr2[C][1] + " ,please check the file.");
				}

				nameList();
				if (arr2[C][0].equals(")")) {
//					System.out.print("Test on STAT :" + C);

					C++;
				} else {

					Worng("Error : There is a missing ) in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("readreal")) {
				C++;
				if (arr2[C][0].equals("(")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {

					Worng("Error : There is a missing ( in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				nameList();
				if (arr2[C][0].equals(")")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ) in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("readchar")) {
				C++;
				if (arr2[C][0].equals("(")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ( in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				nameList();
				if (arr2[C][0].equals(")")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ) in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("readln")) {
				C++;

			} else if (arr2[C][0].equals("writeint")) {
//				System.out.print("Test on STAT :" + C);
				C++;
				if (arr2[C][0].equals("(")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ( in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				ListW();
				if (arr2[C][0].equals(")")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ) in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("writereal")) {
				C++;
				if (arr2[C][0].equals("(")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ( in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				ListW();
				if (arr2[C][0].equals(")")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ) in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("writechar")) {
				C++;
				if (arr2[C][0].equals("(")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ( in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				ListW();
				if (arr2[C][0].equals(")")) {
//					System.out.print("Test on STAT :" + C);
					C++;
				} else {
					Worng("Error : There is a missing ) in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("writeln")) {
//				System.out.print("Test on STAT :" + C);
				C++;

			} else if (arr2[C][0].equals("if")) {
//				System.out.print("Test on STAT :" + C);
				C++;
				nameOrValue();
				realtionalOperation();
				nameOrValue();
				if (arr2[C][0].equals("then")) {
//					System.out.print("Test on STAT : -------------------------" + C);
					C++;
				} else {
					Worng("Error : There is a missing then in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				stmtList();
				elseifFunction();
				elseP();
				if (arr2[C][0].equals("end")) {

					C++;
				} else {
					Worng("Error : There is a missing end in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("while")) {
				C++;
				nameOrValue();
				realtionalOperation();
				nameOrValue();
				if (arr2[C][0].equals("do")) {

					C++;
				} else {
					Worng("Error : There is a missing do in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				stmtList();
				if (arr2[C][0].equals("end")) {

					C++;
				} else {
					Worng("Error : There is a missing end in the line ->" + arr2[C][1] + " ,please check the file.");
				}
			} else if (arr2[C][0].equals("loop")) {
				C++;
				stmtList();
				if (arr2[C][0].equals("until")) {

					C++;
				} else {
					Worng("Error : There is a missing until in the line ->" + arr2[C][1] + " ,please check the file.");
				}
				nameOrValue();
				realtionalOperation();
				nameOrValue();
			} else if (arr2[C][0].equals("exit")) {
				C++;
			} else if (arr2[C][0].equals("call")) {
				C++;
				if (!Labels[1].equals(arr2[C][0])) {
					Worng("Error :procedure name called unknown .");
				}
				NAMEfunction();
			}
		} else if (followStat.contains(arr2[C][0])) {
//			System.out.print(----------------------------------------------);
		} else {
			Worng("Error : There are invalid statment on line: " + arr2[C][1] + " ,please check the file.");
		}
	}
}
