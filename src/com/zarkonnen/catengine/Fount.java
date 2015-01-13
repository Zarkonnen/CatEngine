package com.zarkonnen.catengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class Fount {
	public final String img;
	public final int lineHeight;
	public final Img[] imgs = new Img[256];
	public final HashMap<Integer, Img> extended = new HashMap<Integer, Img>();
	public final int height;
	public final int displayWidth;
	
	public static class Rect {
		int x, y, w, h;

		public Rect(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
	}

	public Fount(String img, HashMap<String, Rect> positions, int lineHeight) {
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
	}
	
	public static Fount fromResource(String img, String metrics) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Fount.class.getResourceAsStream(metrics), "UTF-8"));
			String l = null;
			HashMap<String, Rect> positions = new HashMap<String, Rect>();
			int maxH = 1;
			while ((l = br.readLine()) != null) {
				String[] rectDef = br.readLine().split(" ");
				positions.put(l, new Rect(
						Integer.parseInt(rectDef[0]),
						Integer.parseInt(rectDef[1]),
						Integer.parseInt(rectDef[2]),
						Integer.parseInt(rectDef[3])
				));
				maxH = Math.max(maxH, Integer.parseInt(rectDef[3]));
			}
			int lineHeight = maxH;// * 4 / 3;
			return new Fount(img, positions, lineHeight);
		} catch (Exception e) {
			return null;
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
	
	public int getWidth(char c) {
		int cNum = (int) c;
		if (cNum < 256) {
			return imgs[cNum] == null ? 0 : imgs[cNum].srcWidth;
		} else {
			return extended.containsKey(cNum) ? extended.get(cNum).srcWidth : 0;
		}
	}
}
