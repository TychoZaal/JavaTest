package com.sample;

public class OrdinaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordFrequencyAnalyzerObject obj = new WordFrequencyAnalyzerObject();

		System.out.println(obj.calculateHighestFrequency("ordina workingtalent ordina"));
		System.out.println(obj.calculateFrequencyForWord("ordina workingtalent ordina workingtalent workingtalent",
				"workingtalent"));
		obj.calculateMostFrequentNWords("hoi hoi hoi doei doei dag dag bye", 5);
	}
}
