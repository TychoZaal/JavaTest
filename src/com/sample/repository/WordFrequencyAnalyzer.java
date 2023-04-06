package com.sample.repository;

public interface WordFrequencyAnalyzer {
	int calculateHighestFrequency(String text);

	int calculateFrequencyForWord(String text, String word);

	WordFrequency[] calculateMostFrequentNWords(String text, int n);
}
