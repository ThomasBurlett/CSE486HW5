/*
 * Stuart Wyse
 * CSE 486 - HW 5
 */

public class wyseshHW5 {

	public static void main(String[] args) {
	boolean [][] data1 = {
		{  true,  true,  true,  true,  true},
		{  true,  true, false,  true,  true},
		{  true,  true,  true, false,  true},
		{  true, false,  true,  true,  true},
		{ false,  true, false, false, false},
		{ false,  true,  true, false, false},
		{ false,  true, false,  true, false},
		{ false,  true, false, false, false},
	};
	boolean [][] data2 = {
		{ false, false,  true,  true,  true,  true},
		{ false, false,  true,  true, false,  true},
		{  true, false,  true, false,  true,  true},
		{  true,  true,  true, false, false,  true},
		{ false, false,  true,  true, false, false},
		{  true, false,  true,  true,  true, false},
		{  true, false, false, false,  true, false},
		{ false, false, false, false,  true, false},
	};
		System.out.println(GetAttribute(data1));		// should output 0
		System.out.println(GetAttribute(data2)); 		// should output 2
	}
	
	public static int GetAttribute(boolean[][] data) {
		double elementSize = data.length;
		double decisionSize = data[0].length;
		double totalYes = 0, totalNo = 0;
		for(int i = 0; i < elementSize; i++) {
			if(data[i][(int) (decisionSize - 1)] == true) {
				totalYes++;
			} else continue;
		}
		totalNo = elementSize - totalYes;
				
		// to store the # of 'true's for each decision
		int[] decisionTrue = new int[(int) decisionSize - 1];
		
		// initialize decisionTrue to all zeroes
		for(int i = 0; i< decisionTrue.length; i++) {
			decisionTrue[i] = 0;
		}
		
		// loop through the data and find how many 'true' there are for each decision
		for(int i = 0; i < decisionSize - 1; i++) {
			for(int j = 0; j < elementSize; j++) {
				if(data[j][i] == true) {
					decisionTrue[i]++;
				}
			}
		}
		
		int[] finalYes = new int[(int) decisionSize]; // holds # where decision was true and end result was true, for each decision
		int[] finalNo = new int[(int) decisionSize]; // holds # where decision was false and end result was true, for each decision

		// find # of decisions that were true and also had a true final outcome
		for(int i = 0; i < decisionSize - 1; i++) {
			for(int j = 0; j < elementSize; j++) {
				if(data[j][i] == true) {
					if(data[j][(int) decisionSize - 1] == true) {
						finalYes[i]++;
					}
				}
			}
		}
		
		// find # of decisions that were false and also had a true final outcome
		for(int i = 0; i < decisionSize - 1; i++) {
			for(int j = 0; j < elementSize; j++) {
				if(data[j][i] == false) {
					if(data[j][(int) decisionSize - 1] == true) {
						finalNo[i]++;
					}
				}
			}
		}

		// first part of IG equation (the parent)
		double yes = totalYes/elementSize;
		double no = totalNo/elementSize;
		double firstPart = (InformationGain(yes, no));

		// second part of IG equation
		double yes1 = 0.0, no1 = 0.0, yes2 = 0.0, no2 = 0.0;
		double half1 = 0.0, half2 = 0.0;
		
		// to store IG values of each decision
		double[] IGValues = new double[(int) decisionSize - 1];
				
		// fill in IGValues for each decision
		for(int i = 0; i < decisionSize - 1; i++) {
			yes1 = (double)finalYes[i] / (double)decisionTrue[i]; // how many true decisions had a true final result
			no1 = 1.0 - yes1;
			half1 = ((double)decisionTrue[i] / elementSize) * InformationGain(yes1, no1);
			
			yes2 = (double)finalNo[i] / (elementSize - (double)decisionTrue[i]); // how many false decisions had a true final result
			no2 = 1.0 - yes2;
			half2 = ((elementSize - (double)decisionTrue[i]) / elementSize) * InformationGain(yes2, no2);
			
			IGValues[i] = firstPart - (half1 + half2);
		}
		
		// getting the maximum value of the IGValues
		double max = IGValues[0];
		for (int i = 1; i < IGValues.length; i++) {
			if (IGValues[i] > max) {
				max = IGValues[i];
			}
		}
		
		// return the index of the max
		for(int i = 0; i < IGValues.length; i++) {
			if(IGValues[i] == max) {
				return i;
			}
		}
		return -1;
	}
	
	// calculates the information gain of 2 doubles 
	public static double InformationGain(double a, double b) {
		if(a == 0 || b == 0) {
			return 0;
		}
		double num1 = -(a * log2(a));
		double num2 = b * log2(b);
		
		return num1 - num2;
	}
	
	// return the log base 2 of a double
	public static double log2(double a) {
		return Math.log(a) / Math.log(2);
	}
}
