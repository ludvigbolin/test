package Labb3;

class Stack {
	
	private int platsStack;
	private String array[];
	

	
	Stack(int storlek) { //Skapa stacken, eftersom det är en array som agerar stack så måste den ha ett maxvärde
	
		array = new String[storlek];
		platsStack = 0;
	}

	
	public void printStack() { //debug-syfte
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


	public void push(String x) { //Push, som en stack ska
	
		platsStack++;
		array[platsStack] = x;
	}

	
	public String pop() { //Pop, som en stack ska
	
		return array[platsStack--];
		
	}

	public String peek() { //Peek, som en stack ska
	
		if (!isEmpty())
			return array[platsStack];

		return null;
	}

	public Boolean isEmpty() { //isEmpty, som en stack ska
		
		if (array[platsStack] == null) {
			return true;
			
		} else {
			return false;
			
		}
		
	}
	
}
