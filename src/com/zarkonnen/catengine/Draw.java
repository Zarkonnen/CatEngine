package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.Rect;
import java.util.HashMap;
import java.util.Map;

public class Draw {
	Frame f;
	Hooks hs = new Hooks();
	
	public Hooks getHooks() { return hs; }
	
	public Draw(Frame f) {
		this.f = f;
	}
	
	public Draw(Frame f, Hooks hs) {
		this.f = f;
		this.hs = hs;
	}
	
	public Draw blit(String img, double x, double y) {
		f.blit(img, null, x, y, 0, 0, 0);
		return this;
	}
	
	public Draw blit(String img, double x, double y, double angle) {
		f.blit(img, null, x, y, 0, 0, angle);
		return this;
	}
	
	public Draw blit(String img, Clr c, double x, double y) {
		f.blit(img, c, x, y, 0, 0, 0);
		return this;
	}
	
	public Draw blit(String img, double x, double y, double width, double height) {
		f.blit(img, null, x, y, width, height, 0);
		return this;
	}
	
	public Draw blit(String img, Clr c, double x, double y, double width, double height) {
		f.blit(img, c, x, y, width, height, 0);
		return this;
	}
	
	public Draw blit(String img, Clr c, double x, double y, double angle) {
		f.blit(img, c, x, y, 0, 0, angle);
		return this;
	}
	
	public Draw blit(String img, Clr c, double x, double y, double width, double height, double angle) {
		f.blit(img, c, x, y, width, height, angle);
		return this;
	}
	
	public Draw rect(Clr c, double x, double y, double width, double height) {
		f.rect(c, x, y, width, height, 0);
		return this;
	}
	
	public Draw rect(Clr c, double x, double y, double width, double height, double angle) {
		f.rect(c, x, y, width, height, angle);
		return this;
	}
	
	public Draw blit(String img, double x, double y, Hook hook) {
		hs.add(f.blit(img, null, x, y, 0, 0, 0), hook);
		return this;
	}
	
	public Draw blit(String img, Clr c, double x, double y, Hook hook) {
		hs.add(f.blit(img, c, x, y, 0, 0, 0), hook);
		return this;
	}
	
	public Draw blit(String img, double x, double y, double width, double height, Hook hook) {
		hs.add(f.blit(img, null, x, y, width, height, 0), hook);
		return this;
	}
	
	public Draw blit(String img, Clr c, double x, double y, double width, double height, Hook hook) {
		hs.add(f.blit(img, c, x, y, width, height, 0), hook);
		return this;
	}
	
	public Draw rect(Clr c, double x, double y, double width, double height, Hook hook) {
		hs.add(f.rect(c, x, y, width, height, 0), hook);
		return this;
	}
	
	public Draw text(String text, Fount f, double x, double y) {
		return text(text, f, x, y, 10000, 10000, true, new HashMap<String, Hook>());
	}
	
	public Draw text(String text, Fount f, double x, double y, int maxWidth) {
		return text(text, f, x, y, maxWidth, 10000, true, new HashMap<String, Hook>());
	}
	
	public Draw text(String text, Fount f, double x, double y, Map<String, Hook> hooks) {
		return text(text, f, x, y, 10000, 10000, true, hooks);
	}
	
	public Draw text(String text, Fount f, double x, double y, int maxWidth, Map<String, Hook> hooks) {
		return text(text, f, x, y, maxWidth, 10000, true, hooks);
	}
	
	public Pt textSize(String text, Fount f) {
		return textSize(text, f, 10000, 10000, true);
	}
	
	public Rect textSize(String text, Fount f, double x, double y) {
		Pt ts = textSize(text, f);
		return new Rect(x, y, ts.x, ts.y);
	}
	
	public Rect textSize(String text, Fount f, double x, double y, int maxWidth) {
		Pt ts = textSize(text, f, maxWidth, 10000, true);
		return new Rect(x, y, ts.x, ts.y);
	}
	
	public Draw hook(double x, double y, double width, double height, Hook h) {
		hs.add(new Rect(x, y, width, height), h);
		return this;
	}
	
	public Draw text(String text, Fount fount, double x, double y, int maxWidth, int maxHeight, boolean allowCommands, Map<String, Hook> hooksForStrings) {
		for (Map.Entry<String, Hook> sub : hooksForStrings.entrySet()) {
			text = text.replace(sub.getKey(), "[!" + sub.getKey() + "]" + sub.getKey() + "[!]");
		}
		int cols = maxWidth / fount.displayWidth;
		int rows = maxHeight / fount.height;
		int c = 0;
		int r = 0;
		int n = 0;
		char[] cs = text.toCharArray();
		Clr bgC = null;
		Clr tintC = null;
		
		String hookText = null;
		double hookX = 0, hookY = 0;
		
		while (n < cs.length) {
			/*if (c >= cols) {
				c = 0;
				r++;
			}*/
			// Look ahead
			int nextSpace = n;
			int realNextSpace = n;
			boolean inSquareBrackets = false;
			boolean inCurlyBrackets = false;
			while (nextSpace < cs.length) {
				if (cs[nextSpace] == ' ' || cs[nextSpace] == '\n') {
					break;
				}
				if (cs[nextSpace] == '[') {
					inSquareBrackets = true;
				}
				if (cs[nextSpace] == ']') {
					inSquareBrackets = false;
				}
				if (cs[nextSpace] == '{') {
					inCurlyBrackets = true;
					realNextSpace += 2;
				}
				if (cs[nextSpace] == '}') {
					inCurlyBrackets = false;
				}
				if (!inCurlyBrackets && !inSquareBrackets) {
					realNextSpace++;
				}
				nextSpace++;
			}
			boolean justIncrementedRow = false;
			if (realNextSpace - n + c >= cols && realNextSpace != cs.length) {
				c = 0;
				r++;
				justIncrementedRow = true;
			}
			if (r >= rows) {
				return this;
			}
			if (cs[n] == '\\' && allowCommands) {
				n++;
			} else {
				if (cs[n] == '\n') {
					c = 0;
					if (!justIncrementedRow) { r++; }
					n++;
					continue;
				}
				if (cs[n] == '{' && allowCommands) {
					int n2 = n + 1;
					while (cs[n2] != '}') { n2++; }
					char[] name = new char[n2 - n - 1];
					System.arraycopy(cs, n + 1, name, 0, name.length);
					String nameS = new String(name);
					String sym = null;
					Clr symC = null;
					if (nameS.startsWith("[") && nameS.contains("]")) {
						String tintN = nameS.substring(1, nameS.indexOf("]"));
						if (!tintN.isEmpty()) {
							if (tintN.matches("[a-fA-F0-9]{6}")) {
								try {
									symC = new Clr(
											Integer.parseInt(tintN.substring(0, 2), 16),
											Integer.parseInt(tintN.substring(2, 4), 16),
											Integer.parseInt(tintN.substring(4, 6), 16));
								} catch (Exception e) {
									// Ignore
									e.printStackTrace();
								}
							}
							if (tintN.matches("[a-fA-F0-9]{8}")) {
								try {
									symC = new Clr(
											Integer.parseInt(tintN.substring(0, 2), 16),
											Integer.parseInt(tintN.substring(2, 4), 16),
											Integer.parseInt(tintN.substring(4, 6), 16),
											Integer.parseInt(tintN.substring(6, 8), 16));
								} catch (Exception e) {
									// Ignore
									e.printStackTrace();
								}
							}
							if (symC == null) {
								try {
									symC = Clr.getNamedColor(tintN);
								} catch (Exception e) {
									// Ignore
									e.printStackTrace();
								}
							}
							if (symC != null) {
								sym = nameS.substring(nameS.indexOf("]") + 1);
							}
						}
					}
					
					if (sym == null) { sym = nameS; }
					int overhang = 0; // qqDPS
					Rect imgSz = f.blit(sym, symC, x + (c) * fount.displayWidth, y + r * fount.height + overhang, 0, 0, 0);
					n = n2 + 1;
					if (imgSz != null) {
						hs.add(imgSz, hooksForStrings.get("{" + sym + "}"));
						c += (imgSz.width / fount.displayWidth) + (imgSz.width % fount.displayWidth == 0 ? 0 : 1);
					}
					continue;
				}
				if (cs[n] == '[' && allowCommands) {
					int n2 = n + 1;
					while (cs[n2] != ']') { n2++; }
					char[] name = new char[n2 - n - 1];
					System.arraycopy(cs, n + 1, name, 0, name.length);
					String tintN = new String(name);
					
					if (tintN.startsWith("!")) {
						if (tintN.equals("!")) {
							if (hookText != null) {
								hs.add(new Rect(hookX, hookY, x + (c) * fount.displayWidth - hookX, y + r * fount.height + fount.height - hookY), hooksForStrings.get(hookText));
								hookText = null;
							}
						} else {
							hookText = tintN.substring(1);
							hookX = x + (c) * fount.displayWidth;
							hookY = y + r * fount.height;
						}
						
						n = n2 + 1;
						continue;
					}
					
					boolean bg = false;
					if (tintN.startsWith("bg=")) {
						bg = true;
						tintN = tintN.substring(3);
					}
					Clr newC = null;
					if (!tintN.isEmpty()) {
						if (tintN.matches("[a-fA-F0-9]{6}")) {
							try {
								newC = new Clr(
										Integer.parseInt(tintN.substring(0, 2), 16),
										Integer.parseInt(tintN.substring(2, 4), 16),
										Integer.parseInt(tintN.substring(4, 6), 16));
							} catch (Exception e) {
								// Ignore
								e.printStackTrace();
							}
						}
						if (tintN.matches("[a-fA-F0-9]{8}")) {
							try {
								newC = new Clr(
										Integer.parseInt(tintN.substring(0, 2), 16),
										Integer.parseInt(tintN.substring(2, 4), 16),
										Integer.parseInt(tintN.substring(4, 6), 16),
										Integer.parseInt(tintN.substring(6, 8), 16));
							} catch (Exception e) {
								// Ignore
								e.printStackTrace();
							}
						}
						if (newC == null) {
							newC = Clr.getNamedColor(tintN);
						}
					}
					if (bg) {
						bgC = newC;
					} else {
						tintC = newC;
					}
					n = n2 + 1;
					continue;
				}
			}
			int codePt = Character.codePointAt(cs, n);
			if (bgC != null) {
				f.rect(bgC, x + c * fount.displayWidth - (n == 0 ? -2 : 1), y + r * fount.height - 2,
					fount.displayWidth, fount.height, 0);
			}
			f.blit(
					fount.loadBase + codePt,
					tintC,
					x + c * fount.displayWidth, y + r * fount.height,
					0,
					0,
					0);
			c++;
			n++;
		}
		return this;
	}
	
	public Pt textSize(String text, Fount fount, int maxWidth, int maxHeight, boolean allowCommands) {
		int widthReached = 0;
		int heightReached = 0;
		
		int cols = maxWidth / fount.displayWidth;
		int rows = maxHeight / fount.height;
		int c = 0;
		int r = 0;
		int n = 0;
		char[] cs = text.toCharArray();
		while (n < cs.length) {
			/*if (c >= cols) {
				c = 0;
				r++;
			}*/
			// Look ahead
			int nextSpace = n;
			int realNextSpace = n;
			boolean inSquareBrackets = false;
			boolean inCurlyBrackets = false;
			while (nextSpace < cs.length) {
				if (cs[nextSpace] == ' ' || cs[nextSpace] == '\n') {
					break;
				}
				if (cs[nextSpace] == '[') {
					inSquareBrackets = true;
				}
				if (cs[nextSpace] == ']') {
					inSquareBrackets = false;
				}
				if (cs[nextSpace] == '{') {
					inCurlyBrackets = true;
					realNextSpace += 2;
				}
				if (cs[nextSpace] == '}') {
					inCurlyBrackets = false;
				}
				if (!inCurlyBrackets && !inSquareBrackets) {
					realNextSpace++;
				}
				nextSpace++;
			}
			boolean justIncrementedRow = false;
			if (realNextSpace - n + c >= cols && realNextSpace != cs.length) {
				c = 0;
				r++;
				justIncrementedRow = true;
			}
			if (r >= rows) {
				return new Pt(widthReached + 1, heightReached + 1);
			}
			if (cs[n] == '\\' && allowCommands) {
				n++;
			} else {
				if (cs[n] == '\n') {
					c = 0;
					if (!justIncrementedRow) { r++; }
					n++;
					continue;
				}
				if (cs[n] == '{' && allowCommands) {
					int n2 = n + 1;
					while (cs[n2] != '}') { n2++; }
					char[] name = new char[n2 - n - 1];
					n = n2 + 1;
					c += 1; // qqDPS
					continue;
				}
				if (cs[n] == '[' && allowCommands) {
					int n2 = n + 1;
					while (cs[n2] != ']') { n2++; }
					n = n2 + 1;
					continue;
				}
			}
			int val = (int) cs[n];
			widthReached = Math.max(widthReached, c * fount.displayWidth + fount.displayWidth);
			heightReached = Math.max(heightReached, r * fount.height + fount.height);
			c++;
			n++;
		}
		return new Pt(widthReached + 1, heightReached + 1);
	}
}
