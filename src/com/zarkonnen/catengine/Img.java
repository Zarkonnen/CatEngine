package com.zarkonnen.catengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Img implements Serializable {
	public final String src;
	public final int srcX;
	public final int srcY;
	public final int srcWidth;
	public final int srcHeight;
	public final boolean flipped;

	public Img(String src, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipped) {
		this.src = src;
		this.srcX = srcX;
		this.srcY = srcY;
		this.srcWidth = srcWidth;
		this.srcHeight = srcHeight;
		this.flipped = flipped;
	}
	
	public Img flip() {
		return new Img(src, srcX, srcY, srcWidth, srcHeight, !flipped);
	}
	
	private static int readInt(BufferedReader r) throws IOException {
		return Integer.parseInt(r.readLine());
	}
	
	public static HashMap<String, Img> loadMap(InputStream in) throws UnsupportedEncodingException, IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		HashMap<String, Img> m = new HashMap<String, Img>();
		int n = readInt(r);
		for (int i = 0; i < n; i++) {
			m.put(r.readLine(), new Img(r.readLine(), readInt(r), readInt(r), readInt(r), readInt(r), false));
		}
		return m;
	}
}
