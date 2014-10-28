package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Rect;
import static com.zarkonnen.catengine.util.Utils.*;
import com.zarkonnen.catengine.util.Utils.Pair;
import java.util.ArrayList;

public class Hooks {
	public ArrayList<Pair<Rect, Hook>> list = new ArrayList<Pair<Rect, Hook>>();
	
	public Hooks add(Rect r, Hook h) {
		if (r == null || h == null) { return this; }
		list.add(p(r, h));
		return this;
	}
	
	public void hit(Input in) {
		boolean hoverDone = false;
		boolean downDone = false;
		boolean clickDone = false;
		for (int i = list.size() - 1; i >= 0; i--) {
			Pair<Rect, Hook> h = list.get(i);
			if (!hoverDone && h.b.ofType(Hook.Type.HOVER) && h.a.contains(in.cursor())) {
				hoverDone = true;
				h.b.run(in, h.a.relative(in.cursor()), Hook.Type.HOVER);
			}
			// qqDPS Fixing things up for now to deal with lack of downButton.
			if (!downDone && h.b.ofType(Hook.Type.MOUSE_1_DOWN) && h.a.contains(in.mouseDown())) {
				downDone = true;
				h.b.run(in, h.a.relative(in.mouseDown()), Hook.Type.values()[in.clickButton()]);
			}
			if (!clickDone && (in.clickButton() + 3) < Hook.Type.values().length && h.b.ofType(Hook.Type.values()[in.clickButton() + 3]) && h.a.contains(in.clicked())) {
				clickDone = true;
				h.b.run(in, h.a.relative(in.clicked()), Hook.Type.values()[in.clickButton() + 3]);
			}
			if (clickDone && downDone && hoverDone) { list.clear(); return; }
		}
		list.clear();
	}
}
