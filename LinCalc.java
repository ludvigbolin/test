package Labb3;

import java.util.Scanner;

//import lincalc.LinCalcJohn;

//Av Ludvig Bolin, IT1 2019

public class LinCalc {
	private static Stack stack; //Instaniering av stack-klassen
	

    public static void printArray(String[] array){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            sb.append(", ");
        }
        // Replace the last ", " with "]"
        sb.replace(sb.length() - 2, sb.length(), "]");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String expression;

        double result;

        System.out.print("Enter expression: ");
        expression = in.nextLine();
        do {
            result = evaluate(expression);
            System.out.println("Result was: " + result);
            System.out.print("Enter expression: ");
        } while (!"".equals(expression = in.nextLine()));
    }

    
  public static double calc(String[] lexedPostfix) {

	 // double facit = LinCalcJohn.calc(lexedPostfix);
	  
	  String[] operator = {"+", "-", "*", "/"};
	  Stack postfixStack = new Stack(lexedPostfix.length * 2); //ny stack till calc-del
	  
	  
	  for(int i = 0; i < lexedPostfix.length; i++) {
		 if (stringMemberOf(lexedPostfix[i], operator)) { //är det en operator?
			 
			 double operand2 = Double.valueOf(postfixStack.pop()); //spara undan de två tidigare operanderna, som double
			 double operand1 = Double.valueOf(postfixStack.pop());
			 
			 if (lexedPostfix[i].contentEquals("+")) { //gör lämplig uträkning, addition, subtraktion osv.
				  double summa = operand1 + operand2; 
				  String result = String.valueOf(summa); //gör om till Strings igen
				  postfixStack.push(result);
			  }
			  
			  if (lexedPostfix[i].contentEquals("-")) {
				  double differens = operand1 - operand2;
				  String result = String.valueOf(differens);
				  postfixStack.push(result);
			  }
			  
			  if (lexedPostfix[i].contentEquals("*")) {
				  double produkt = operand1 * operand2;
				  String result = String.valueOf(produkt);
				  postfixStack.push(result);
			  }
			  
			  if (lexedPostfix[i].contentEquals("/")) {
				  double kvot = operand1 / operand2;
				  String result = String.valueOf(kvot);
				  postfixStack.push(result);
			  }
		 } else {
			 postfixStack.push(lexedPostfix[i]);
		 }
	  }
	  
	  
	  //System.out.println("facit: " + facit);
	  System.out.println("mitt:  " + postfixStack.peek());
	  
	  double resultat = Double.valueOf(postfixStack.pop()); //omvandla till double från string ur stacken
       
	  return resultat;
    }
	
  
  public static String[] toPostfix(String[] inputData) {
   // 	String[] facit = LinCalcJohn.toPostfix(inputData);
        
        
        String[] operator = {"+", "-", "*", "/", "(", ")"};
        stack = new Stack(inputData.length);
        String[] postFixUttryck = new String[inputData.length];
        
        int platsUttryck = 0;
        int i = 0;
      
        for(i = 0; i < inputData.length; i++) {
       
        	if(stringMemberOf(inputData[i], operator)) { //är det en operator?										
 
        		if (stack.isEmpty()) {
        			stack.push(inputData[i]);
        				
        		} 
        		
        		else if (inputData[i].contentEquals(")")) { //om det kommer en slutparentes
        			
        			while (!stack.peek().contentEquals("(")) {
        				
        				postFixUttryck[platsUttryck] = stack.pop();
            			platsUttryck++;
            			
        				}
        			if (stack.peek().contentEquals("(")) {
        				stack.pop();
        			}
        		}
        		else if (inputData[i].contentEquals("(")) {
        			stack.push(inputData[i]);
        		}
        		else if (prio(stack.peek()) < prio(inputData[i])) { //om stacken har lägre prio än arrayn
        			
        			stack.push(inputData[i]);
        			
        		} 
        		else { //om stacken har högre eller samma prio
        			
        			while(!stack.isEmpty() && prio(stack.peek()) >= prio(inputData[i])) { //popa tills stacken har lägre prio
        				
        				postFixUttryck[platsUttryck] = stack.pop();
        				platsUttryck++;
        			}
        			
        			stack.push(inputData[i]); //därefter pusha ny operator
        			
        			}
        	
        	} else {
        		postFixUttryck[platsUttryck] = inputData[i];
        		platsUttryck++;
        	}
        	
        	if(i == (inputData.length - 1)) { //när sista tecknet har granskats
        		
        		while(!stack.isEmpty()) {
        			
        			postFixUttryck[platsUttryck] = stack.pop();
        			platsUttryck++;
        		}
    		}
        }
        
       
       String[] postFix = bortMedNulls(postFixUttryck);
        
    //   printArray(facit);
       printArray(postFix);
        
      return postFix;
    }
	
  public static double evaluate(String expression) {
        String[] lexedInfix = lex(expression);
        String[] lexedPostfix = toPostfix(lexedInfix);
        return calc(lexedPostfix);
        //return LinCalcJohn.calc(lexedPostfix);
    }
    
    
    public static String[] lex(String expr) {
   // String[] facit = LinCalcJohn.lex(expr);
   // printArray(facit);
    String operator = "+-*/()";
    String operand = "1234567890.";
    
    char[] exprCharArray = expr.toCharArray(); //omvandla till Char Array
    
    
    String uttryck[] = new String[exprCharArray.length*2];
    String tal = "";
    int e = 0;
    int i = 0;
    for(i = 0; i < exprCharArray.length; i++) { //går igenom hela Arrayn
    	
    	
    	if(memberOf(exprCharArray[i], "~") && memberOf(exprCharArray[i+1], "(")) { //om ~( uppstår
    		//~( blir -1, *, (
    		uttryck[e] = "-1";
    		e++;
    		uttryck[e] = "*";
    		e++;
    		uttryck[e] = "(";
    		e++;
    		i += 2; //eftersom ~( är 2 tecken ökar pekaren med 2
    		
    	}
    	
    	if (memberOf(exprCharArray[i], "~")) {
    		tal += "-";
    		i++;
    	}
    	
    	if(memberOf(exprCharArray[i], operand)) {
    	//lägg till alla tidigare i ett uttryck
    		tal += exprCharArray[i];
    	} 
    	
    	else if (tal != ""){
    		
    		uttryck[e] = tal;
    		tal = "";
    		e++;
    		
    	}
    	
    	if (memberOf(exprCharArray[i], operator)) {
    		tal += exprCharArray[i];
    		
    		uttryck[e] = tal;
    		tal = "";
    		e++;
    			
    	}	
    	
    	if ((i+1) == exprCharArray.length && tal != "") { //när sista tecknet har granskats är uttrycket klart, 
    													  //om inte uttrycket slutar med ), därav: tal != ""
    		uttryck[e] = tal;
    	}	
    }
    
   String[] klartUttryck = bortMedNulls(uttryck);
    
    printArray(klartUttryck);
    return klartUttryck;
    }
    
    
    //metoder nedan
    
    public static String[] bortMedNulls(String[] array) {  //ska ta bort nulls och ersätta list användandet
    	int antalNulls = 0; //räkna nulls för att kunna ta bort de från Arrayn
    	for(int i1 = 0; i1 < array.length; i1++) {
        	if(array[i1] == null) {
        		antalNulls++;
        	}
        }
    	
    	String[] klartUttryck = new String[array.length - antalNulls];
    	
    	for(int i2 = 0; i2 < klartUttryck.length; i2++) {
        	klartUttryck[i2] = array[i2]; //kopierar uttrycket utan nulls
        }
    	
    	return klartUttryck;
    }
	
    public static boolean memberOf(char tecken, String texttyp) { //jämför chars, krävs vid lex
    	char[] StringIChars = texttyp.toCharArray();
    	
    	for (int i = 0; i < StringIChars.length; i++) {
    		if(StringIChars[i] == tecken) {
    			return true;
    		}

    	}
    	return false;
	
}
    
 public static int prio(String operator) { //vilken prioritet har operatorn?
	 if(operator.equals("-")) {
		 return 1;
	 }
	 if(operator.equals("+")){
		 return 1;
	 }
	 if(operator.equals("*")){
		 return 2;
	 }
	 if(operator.equals("/")){
		 return 2;
	 }
	 if(operator.equals("(")){
		 return 0;
	 }
	 return -1;
 }
 
    public static boolean stringMemberOf(String tecken, String[] texttyp) { //jämför Strings, krävs vid postfix
    	
    	for (int i = 0; i < texttyp.length; i++) {
    		if(texttyp[i].contentEquals(tecken)) {
    			return true;
    		}

    	}
    	return false;
	
}
    
    
}
