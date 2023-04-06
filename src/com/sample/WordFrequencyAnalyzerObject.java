package com.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class WordFrequencyAnalyzerObject implements WordFrequencyAnalyzer {

	@Override
	public int calculateHighestFrequency(String text) {

		// Return 0 when the string is empty or null
		if (text == null || text.isEmpty())
			return 0;

		// Fill a frequency HashMap
		HashMap<String, Integer> wordFrequency = generateHashMap(text);

		// Return the highest value(frequency) in the HashMap
		return Collections.max(wordFrequency.values());
	}

	@Override
	public int calculateFrequencyForWord(String text, String word) {

		// Return 0 if either text or word is empty/null
		if (text == null || text.isEmpty() || word == null || word.isEmpty())
			return 0;

		// To account for case insensitivity
		word = word.toLowerCase();

		// Fill a frequency HashMap
		HashMap<String, Integer> wordFrequency = generateHashMap(text);

		// If the word does not exist in text, return 0
		if (!wordFrequency.containsKey(word))
			return 0;

		// Else return the frequency
		return wordFrequency.get(word);
	}

	@Override
	public WordFrequency[] calculateMostFrequentNWords(String text, int n) {
		if (text == null || text.isEmpty() || n == 0)
			return null;

		// Generate a list of WordFrequency objects
		// TODO: PriorityQueue can be used as an alternative
		List<WordFrequencyObject> wordFrequencyObjects = generateWordFrequencyList(text);

		// Create custom sorting method using the frequency
		Collections.sort(wordFrequencyObjects, new Comparator<WordFrequencyObject>() {
			public int compare(WordFrequencyObject o1, WordFrequencyObject o2) {
				return o2.getFrequency() - o1.getFrequency();
			}
		});

		List<WordFrequencyObject> sortedList = new ArrayList<WordFrequencyObject>();

		for (int i = 0; i < wordFrequencyObjects.size(); i++) {
			// If the next object in the list has the SAME frequency, sort all duplicates
			// alphabetically and add them to the sorted list
			if (i + 1 < wordFrequencyObjects.size()
					&& wordFrequencyObjects.get(i).getFrequency() == wordFrequencyObjects.get(i + 1).getFrequency()) {
				List<WordFrequencyObject> sortedDuplicates = new ArrayList<WordFrequencyObject>();
				int lastFrequency = wordFrequencyObjects.get(i).getFrequency();

				// Repeat add duplicates to this temporary list until a non-duplicate appears
				while (i < wordFrequencyObjects.size() && wordFrequencyObjects.get(i).getFrequency() == lastFrequency) {
					sortedDuplicates.add(wordFrequencyObjects.get(i));
					i++;
				}

				// Create custom sorting method using the alphabetical order of words
				Collections.sort(sortedDuplicates, new Comparator<WordFrequencyObject>() {
					@Override
					public int compare(final WordFrequencyObject object1, final WordFrequencyObject object2) {
						return object1.getWord().compareTo(object2.getWord());
					}
				});

				// Counteract the i++ from the while loop
				i--;

				// Add all sorted duplicates to the final sorted list
				sortedList.addAll(sortedDuplicates);
			}
			// If the frequency is not duplicate, add it to the sorted list and move to the
			// next
			else {
				sortedList.add(wordFrequencyObjects.get(i));
			}
		}

		// Get the first n elements of the list, or all items if there are less than n
		sortedList = sortedList.subList(0, Math.min(sortedList.size(), n));

		WordFrequency[] wordFrequencyArray = new WordFrequency[sortedList.size()];

		for (int i = 0; i < wordFrequencyArray.length; i++) {
			wordFrequencyArray[i] = sortedList.get(i);
		}

		return wordFrequencyArray;
	}

	// Method for generating a frequency HashMap.
	// TODO: Collections.frequency can be used as an alternative, should figure out
	// which one is more efficient
	private HashMap<String, Integer> generateHashMap(String text) {
		// Split up string into words when encountering a non-alphabetic character
		// String should be case insensitive
		String[] words = text.toLowerCase().split("[^\\p{L}0-9']+");

		// Return 0 when only non-alphabetic characters are present in the text
		if (words.length <= 0)
			return new HashMap<String, Integer>();

		HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();

		// Fill the HashMap with the frequencies
		for (int i = 0; i < words.length; i++) {
			if (wordFrequency.containsKey(words[i])) {
				wordFrequency.put(words[i], wordFrequency.get(words[i]) + 1);
			} else {
				wordFrequency.put(words[i], 1);
			}
		}

		return wordFrequency;
	}

	private List<WordFrequencyObject> generateWordFrequencyList(String text) {

		// Split up string into words when encountering a non-alphabetic character
		// String should be case insensitive
		String[] words = text.toLowerCase().split("[^\\p{L}0-9']+");

		// Return 0 when only non-alphabetic characters are present in the text
		if (words.length <= 0)
			return new ArrayList<WordFrequencyObject>();

		HashMap<String, WordFrequencyObject> wordFrequency = new HashMap<String, WordFrequencyObject>();

		// Fill the HashMap with the frequencies
		for (int i = 0; i < words.length; i++) {
			if (wordFrequency.containsKey(words[i])) {

				// Create temporary object, mainly done for readability
				WordFrequencyObject temp = new WordFrequencyObject(words[i],
						wordFrequency.get(words[i]).getFrequency() + 1);

				wordFrequency.put(words[i], temp);
			} else {
				wordFrequency.put(words[i], new WordFrequencyObject(words[i], 1));
			}
		}

		return new ArrayList<WordFrequencyObject>(wordFrequency.values());
	}

}
