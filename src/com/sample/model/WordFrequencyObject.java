package com.sample.model;

import com.sample.repository.WordFrequency;

public class WordFrequencyObject implements WordFrequency {

	private String word;
	private int frequency;

	// Constructor
	public WordFrequencyObject(String word, int frequency) {
		this.word = word;
		this.frequency = frequency;
	}

	@Override
	public String getWord() {
		return word;
	}

	@Override
	public int getFrequency() {
		// TODO Auto-generated method stub
		return frequency;
	}

}
