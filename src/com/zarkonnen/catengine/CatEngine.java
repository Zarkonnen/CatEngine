package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.ScreenMode;
import javax.swing.JOptionPane;

public class CatEngine {
	public static void main(String[] args) {
		int choice = JOptionPane.showOptionDialog(null, "Choose an implementation.", "CatEngine!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, EngineFactory.defaultEngines(), EngineFactory.defaultEngines()[0]);
		if (choice == -1) { return; }
		Engine e = null;
		try {
			e = EngineFactory.make(EngineFactory.defaultEngines()[choice], "CatEngine!", "/com/zarkonnen/catengine/images/", 30);
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		e.run(new Game() {
			Pt cursor = new Pt(0, 0);
			int i = 0;
			@Override
			public void input(Input in) {
				cursor = in.cursor();
				i++;
				if (in.keyDown("1")) {
					for (ScreenMode m : in.modes()) {
						if (!m.fullscreen) {
							in.setMode(m);
							break;
						}
					}
				}
				if (in.keyDown("2")) {
					int sz = 0;
					ScreenMode best = null;
					for (ScreenMode m : in.modes()) {
						if (m.fullscreen && m.width * m.height > sz) {
							best = m;
							sz = m.width * m.height;
						}
					}
					if (best != null) {
						in.setMode(best);
					}
				}
				if (in.keyDown("3")) {
					if (in.modes().contains(new ScreenMode(800, 600, true))) {
						in.setMode(new ScreenMode(800, 600, true));
					}
				}
				if (in.keyDown("Q")) { in.quit(); return; }
				if (in.keyDown("C")) {
					in.setCursorVisible(!in.isCursorVisible());
				}
			}

			@Override
			public void render(Frame f) {
				ScreenMode sm = f.mode();
				f.rect(Clr.BLACK, 0, 0, sm.width, sm.height, 0);
				f.rect(Clr.RED, cursor.x, cursor.y, 10, 10, (Math.PI * 2 * i) / 100);
				f.blit("cat.jpg", new Clr(0, 255, 0, i % 255), (i * 3) % sm.width, (i * 10) % sm.height, 0, 0, (Math.PI * 2 * i) / 1000);
			}
		});
	}
}
