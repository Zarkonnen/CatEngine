package com.zarkonnen.catengine;

import java.util.HashMap;

public final class Fount {
	public final String img;
	public final int width;
	public final int height;
	public final int displayWidth;
	public final int lineHeight;
	public final String alphabet;
	public final int imgSize;
	public final Img[] imgs = new Img[256];
	public final HashMap<Integer, Img> extended = new HashMap<Integer, Img>();

	public Fount(String img, int width, int height, int displayWidth, int lineHeight, String alphabet) {
		this.img = img;
		this.width = width;
		this.height = height;
		this.displayWidth = displayWidth;
		this.lineHeight = height;
		this.alphabet = alphabet;
		
		int sz = 64;
		int cols;
		int rows;
		while (true) {
			cols = sz / width;
			rows = alphabet.length() / cols + (alphabet.length() % cols > 0 ? 1 : 0);
			if (rows * height <= sz) {
				break;
			} else {
				sz *= 2;
			}
		}
		
		imgSize = sz;
		char[] bits = alphabet.toCharArray();
		int col = 0;
		int row = 0;
		for (char c : bits) {
			int cNum = (int) c;
			Img cImg = new Img(img, col * width, row * height, width, height, false);
			if (cNum < 256) {
				imgs[cNum] = cImg;
			} else {
				extended.put(cNum, cImg);
			}
			col++;
			if (col == cols) {
				row++;
				col = 0;
			}
		}
	}
	
	public Img get(char c) {
		int cNum = (int) c;
		if (cNum < 256) {
			return imgs[cNum];
		} else {
			return extended.get(cNum);
		}
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 79 * hash + this.img.hashCode();
		hash = 79 * hash + this.width;
		hash = 79 * hash + this.height;
		hash = 79 * hash + this.displayWidth;
		hash = 79 * hash + this.lineHeight;
		hash = 79 * hash + this.alphabet.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Fount)) { return false; }
		return
				((Fount) o2).displayWidth == displayWidth &&
				((Fount) o2).lineHeight   == lineHeight   &&
				((Fount) o2).width        == width        &&
				((Fount) o2).height       == height       &&
				((Fount) o2).img.equals(img)              &&
				((Fount) o2).alphabet.equals(alphabet);
	}
}
