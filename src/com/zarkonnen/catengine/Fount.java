package com.zarkonnen.catengine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Fount {
	public final String img;
	public final int lineHeight;
	public final Img[] imgs = new Img[256];
	public final HashMap<Integer, Img> extended = new HashMap<Integer, Img>();
	public final int height;
	public final int displayWidth;
	public final int letterSpacing;
	public final int letterOffset;
	public final ArrayList<Fount> subFounts = new ArrayList<Fount>();
	
	public static class Rect {
		int x, y, w, h;

		public Rect(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
	}

	public Fount(String img, HashMap<String, Rect> positions, int lineHeight, int letterSpacing, int letterXOffset) {
		this.img = img;
		this.lineHeight = lineHeight;
		
		int maxH = 1;
		int maxW = 1;
		for (Map.Entry<String, Rect> e : positions.entrySet()) {
			int cNum = (int) e.getKey().charAt(0);
			Img cImg = new Img(img, e.getValue().x, e.getValue().y, e.getValue().w, e.getValue().h, false);
			maxH = Math.max(e.getValue().h, maxH);
			maxW = Math.max(e.getValue().w, maxW);
			if (cNum < 256) {
				imgs[cNum] = cImg;
			} else {
				extended.put(cNum, cImg);
			}
		}
		height = maxH;
		displayWidth = maxW;
		this.letterSpacing = letterSpacing;
		this.letterOffset = letterXOffset;
	}
	
	public Fount withSubFount(Fount sub) {
		subFounts.add(sub);
		return this;
	}
	
	public static Fount fromResource(String img, String metrics) {
		return fromStream(img, Fount.class.getResourceAsStream(metrics));
	}
	
	public static Fount fromStream(String img, InputStream stream) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String l = null;
			HashMap<String, Rect> positions = new HashMap<String, Rect>();
			int maxH = 1;
			int ls = 0;
			int lio = 0;
			while ((l = br.readLine()) != null) {
				if (l.startsWith("letterSpacing ")) {
					ls = Integer.parseInt(l.split(" ")[1]);
				} else if (l.startsWith("letterXOffset ")) {
					lio = Integer.parseInt(l.split(" ")[1]);
				} else {
					String[] rectDef = br.readLine().split(" ");
					positions.put(l, new Rect(
							Integer.parseInt(rectDef[0]),
							Integer.parseInt(rectDef[1]),
							Integer.parseInt(rectDef[2]),
							Integer.parseInt(rectDef[3])
					));
					maxH = Math.max(maxH, Integer.parseInt(rectDef[3]));
				}
			}
			int lineHeight = maxH;// * 4 / 3;
			return new Fount(img, positions, lineHeight, ls, lio);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Img get(char c) {
		int cNum = (int) c;
		if (cNum < 256 && imgs[cNum] != null) {
			return imgs[cNum];
		} else if (extended.containsKey(cNum)) {
			return extended.get(cNum);
		} else {
			for (Fount sub : subFounts) {
				Img subFountImg = sub.get(c);
				if (subFountImg != null) {
					return subFountImg;
				}
			}
		}
		return null;
	}
	
	public int getWidth(char c) {
		int cNum = (int) c;
		if (cNum < 256 && imgs[cNum] != null) {
			return imgs[cNum] == null ? 0 : imgs[cNum].srcWidth;
		} else if (extended.containsKey(cNum)) {
			return extended.get(cNum).srcWidth;
		} else {
			for (Fount sub : subFounts) {
				int subFountW = sub.getWidth(c);
				if (subFountW != 0) {
					return subFountW;
				}
			}
		}
		return 0;
	}
}
