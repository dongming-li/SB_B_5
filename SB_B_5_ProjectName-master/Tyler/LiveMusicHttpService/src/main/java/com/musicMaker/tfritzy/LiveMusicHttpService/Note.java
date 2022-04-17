package com.musicMaker.tfritzy.LiveMusicHttpService;

public class Note {
	private float frequency;
	private float creator;
	private float name;
	
	public Note(float frequency, float creator, float name) {
		super();
		this.frequency = frequency;
		this.creator = creator;
		this.name = name;
	}
	
	public float getFrequency() {
		return frequency;
	}
	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}
	public float getCreator() {
		return creator;
	}
	public void setCreator(float creator) {
		this.creator = creator;
	}
	public float getName() {
		return name;
	}
	public void setName(float name) {
		this.name = name;
	}
}
