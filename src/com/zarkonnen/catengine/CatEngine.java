package com.zarkonnen.catengine;

import com.zarkonnen.catengine.java2d.J2DEngine;
import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.ScreenMode;

public class CatEngine {
	public static void main(String[] args) {
		Engine e = new J2DEngine("Example", "/com/zarkonnen/catengine/images/", 30);
		e.start();
		int i = 0;
		while (true) {
			i++;
			Frame f = e.next();
			if (f.keyDown("1")) {
				for (ScreenMode m : f.modes()) {
					if (!m.fullscreen) {
						f.setMode(m);
						break;
					}
				}
			}
			if (f.keyDown("2")) {
				int sz = 0;
				ScreenMode best = null;
				for (ScreenMode m : f.modes()) {
					if (m.fullscreen && m.width * m.height > sz) {
						best = m;
						sz = m.width * m.height;
					}
				}
				if (best != null) {
					f.setMode(best);
				}
			}
			if (f.keyDown("3")) {
				if (f.modes().contains(new ScreenMode(800, 600, true))) {
					f.setMode(new ScreenMode(800, 600, true));
				}
			}
			if (f.keyDown("Q")) { f.quit(); return; }
			ScreenMode sm = f.mode();
			f.rect(Clr.BLACK, 0, 0, sm.width, sm.height, 0).
					rect(f.clickButton() == 0 ? Clr.RED : Clr.WHITE, f.cursor().x, f.cursor().y, 10, 10, (Math.PI * 2 * i) / 100).
					blit("cat.jpg", new Clr(0, 255, 0, i % 255), (i * 3) % sm.width, (i * 10) % sm.height, 0, 0, (Math.PI * 2 * i) / 1000);
		}
	}
}
