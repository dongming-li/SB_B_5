package com.musicMaker.tfritzy.LiveMusicHttpService;

import java.util.ArrayList;

public class Notes {
	private boolean c;
	private boolean d;
	private boolean e;
	private boolean g;
	private boolean a;
	
	public Notes() {
		c = false;
		d = false;
		e = false;
		g = false;
		a = false;
	}
	
	public boolean isC() {
		return c;
	}
	public void setC(boolean c) {
		this.c = c;
	}
	public boolean isD() {
		return d;
	}
	public void setD(boolean d) {
		this.d = d;
	}
	public boolean isE() {
		return e;
	}
	public void setE(boolean e) {
		this.e = e;
	}
	public boolean isG() {
		return g;
	}
	public void setG(boolean g) {
		this.g = g;
	}
	public boolean isA() {
		return a;
	}
	public void setA(boolean a) {
		this.a = a;
	}
	
	@Override
	public String toString() {
		return "" + isC() + isD() + isE() + isG() + isA();
		
	
	}
	
	
	
	
	
}
