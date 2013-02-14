package com.zarkonnen.catengine;

import static com.zarkonnen.catengine.util.Utils.*;

import com.zarkonnen.catengine.util.Rect;
import java.util.ArrayList;

public class Hooks {
	private ArrayList<Pair<Rect, Hook>> list = new ArrayList<Pair<Rect, Hook>>();
	
	public Hooks add(Rect r, Hook h) {
		if (r == null || h == null) { return this; }
		list.add(p(r, h));
		return this;
	}
	
	public void hit(Input in) {
		boolean hoverDone = false;
		boolean clickDone = false;
		for (int i = list.size() - 1; i >= 0; i--) {
			Pair<Rect, Hook> h = list.get(i);
			if (!hoverDone && h.b.type == Hook.Type.HOVER && h.a.contains(in.cursor())) {
				hoverDone = true;
				h.b.run(in, h.a.relative(in.cursor()));
			}
			if (!clickDone && h.b.type.ordinal() == in.clickButton() && h.a.contains(in.click())) {
				clickDone = true;
				h.b.run(in, h.a.relative(in.click()));
			}
			if (clickDone && hoverDone) { list.clear(); return; }
		}
		list.clear();
	}
}
