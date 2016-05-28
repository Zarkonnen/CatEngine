package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.Rect;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
	
	public Frame frame() { return f; }
	
	public void shift(double dx, double dy) { f.shift(dx, dy); }
	public void scale(double xScale, double yScale) { f.scale(xScale, yScale); }
	public void rotate(double angle) { f.rotate(angle); }
	public void resetTransforms() { f.resetTransforms(); }
	
	public Pt cursor() {
		return f.cursor();
	}
	
	public Draw blit(Img img, double x, double y) {
		f.blit(img, null, 1, x, y, 0, 0, 0);
		return this;
	}
	
	public Draw blit(Img img, double x, double y, double angle) {
		f.blit(img, null, 1, x, y, 0, 0, angle);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double x, double y) {
		f.blit(img, c, 1, x, y, 0, 0, 0);
		return this;
	}
	
	public Draw blit(Img img, double x, double y, double width, double height) {
		f.blit(img, null, 1, x, y, width, height, 0);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double x, double y, double width, double height) {
		f.blit(img, c, 1, x, y, width, height, 0);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double x, double y, double angle) {
		f.blit(img, c, 1, x, y, 0, 0, angle);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double x, double y, double width, double height, double angle) {
		f.blit(img, c, 1, x, y, width, height, angle);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double alpha, double x, double y, double width, double height, double angle) {
		f.blit(img, c, alpha, x, y, width, height, angle);
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
	
	public Draw blit(Img img, double x, double y, Hook hook) {
		blit(img, x, y);
		hs.add(new Rect(x, y, img.srcWidth, img.srcHeight), hook);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double x, double y, Hook hook) {
		blit(img, c, x, y);
		hs.add(new Rect(x, y, img.srcWidth, img.srcHeight), hook);
		return this;
	}
	
	public Draw blit(Img img, double x, double y, double width, double height, Hook hook) {
		blit(img, x, y, width, height);
		hs.add(new Rect(x, y, width, height), hook);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double x, double y, double width, double height, Hook hook) {
		blit(img, c, x, y, width, height);
		hs.add(new Rect(x, y, width, height), hook);
		return this;
	}
	
	public Draw blit(Img img, Clr c, double alpha, double x, double y, double width, double height, Hook hook) {
		blit(img, c, alpha, x, y, width, height);
		hs.add(new Rect(x, y, width, height), hook);
		return this;
	}
	
	public Draw rect(Clr c, double x, double y, double width, double height, Hook hook) {
		rect(c, x, y, width, height);
		hs.add(new Rect(x, y, width, height), hook);
		return this;
	}
	
	public void addSymbols(HashMap<String, Img> symbols) {
		this.symbols.putAll(symbols);
	}
	
	public Draw text(String text, Fount f, double x, double y) {
		return text(text, f, x, y, 10000, 10000, 0, true);
	}
	
	public Draw text(String text, Fount f, double x, double y, int maxWidth) {
		return text(text, f, x, y, maxWidth, 10000, 0, true);
	}
	
	public Pt textSize(String text, Fount f) {
		return textSize(text, f, 10000, 10000, 0, true);
	}
	
	public Rect textSize(String text, Fount f, double x, double y) {
		Pt ts = textSize(text, f);
		return new Rect(x, y, ts.x, ts.y);
	}
	
	public Rect textSize(String text, Fount f, double x, double y, int maxWidth) {
		Pt ts = textSize(text, f, maxWidth, 10000, 0, true);
		return new Rect(x, y, ts.x, ts.y);
	}
	
	public Draw hook(double x, double y, double width, double height, Hook h) {
		hs.add(new Rect(x, y, width, height), h);
		return this;
	}
	
	static HashMap<String, Clr> knownColors = new HashMap<String, Clr>();
	
	HashMap<String, Img> symbols = new HashMap<String, Img>();
	
	static final Pattern IS_ALPHA_CLR = Pattern.compile("[0-9a-fA-F]{10}");

	public Draw text(String text, Fount fount, double x, double y, int maxWidth, int maxHeight, int lineHeightDelta, boolean allowCommands) {
		doText(true, text, fount, x, y, maxWidth, maxHeight, lineHeightDelta, allowCommands);
		return this;
	}
	
	public Pt textSize(String text, Fount fount, int maxWidth, int maxHeight, int lineHeightDelta, boolean allowCommands) {
		return doText(false, text, fount, 0, 0, maxWidth, maxHeight, lineHeightDelta, allowCommands);
	}
	
	public Pt doText(boolean doRender, String text, Fount fount, double x, double y, int maxWidth, int maxHeight, int lineHeightDelta, boolean allowCommands) {
		int rows = maxHeight / fount.lineHeight;
		int xOffset = 0;
		int row = 0;
		int textIndex = 0;
		Clr bgC = null;
		Clr tintC = null;
		double textAlpha = 1.0;
		Clr defaultTintC = null;
		int biggestWidth = 0;
		
		while (textIndex < text.length()) {
			// Look ahead
			int nextSpaceIndex = textIndex;
			int nextWordWidth = 0;
			int nextLetterWidth = 0;
			boolean inSquareBrackets = false;
			boolean inCurlyBrackets = false;
			while (nextSpaceIndex < text.length()) {
				char c = text.charAt(nextSpaceIndex);
				if (c == ' ' || c == '\n') {
					break;
				}
				if (c == '[') {
					inSquareBrackets = true;
				}
				if (c == ']') {
					inSquareBrackets = false;
				}
				if (c == '{') {
					inCurlyBrackets = true;
				}
				if (c == '}') {
					inCurlyBrackets = false;
				}
				if (!inCurlyBrackets && !inSquareBrackets) {
					nextWordWidth += fount.getWidth(c) + fount.letterSpacing;
					if (nextLetterWidth == 0) {
						nextLetterWidth = fount.getWidth(c) + fount.letterSpacing;
					}
				}
				nextSpaceIndex++;
			}
			boolean justIncrementedRow = false;
			if (xOffset != 0 && xOffset + nextWordWidth > maxWidth && (xOffset + nextLetterWidth > maxWidth || nextWordWidth < maxWidth)) {
				biggestWidth = Math.max(biggestWidth, xOffset);
				xOffset = 0;
				row++;
				justIncrementedRow = true;
			}
			if (row >= rows) {
				break;
			}
			if (text.charAt(textIndex) == '\\' && allowCommands) {
				textIndex++;
			} else {
				if (text.charAt(textIndex) == '\n') {
					if (!justIncrementedRow) {
						biggestWidth = Math.max(biggestWidth, xOffset);
						row++;
					}
					xOffset = 0;
					textIndex++;
					continue;
				}
				if (text.charAt(textIndex) == '{' && allowCommands) {
					int n2 = textIndex + 1;
					while (text.charAt(n2) != '}') { n2++; }
					String nameS = text.substring(textIndex + 1, n2);
					String sym = null;
					Clr symC = null;
					if (nameS.startsWith("[") && nameS.contains("]")) {
						String tintN = nameS.substring(1, nameS.indexOf("]"));
						if (!tintN.isEmpty()) {
							symC = knownColors.get(tintN);
							if (symC == null) {
								symC = Clr.fromHex(tintN);
								if (symC != null) {
									knownColors.put(tintN, symC);
								}
							}
							if (symC == null) {
								symC = Clr.getNamedColor(tintN);
								if (symC != null) {
									knownColors.put(tintN, symC);
								}
							}
							if (symC != null) {
								sym = nameS.substring(nameS.indexOf("]") + 1);
							}
						}
					}
					
					if (sym == null) { sym = nameS; }
					Img symImg = symbols.get(sym);
					if (symImg == null) {
						symImg = new Img(sym);
						symbols.put(sym, symImg);
					}
					if (doRender) {
						blit(symImg, symC, x + xOffset, y + row * (fount.lineHeight + lineHeightDelta));
					}
					textIndex = n2 + 1;
					xOffset += symImg.srcWidth;
					continue;
				}
				if (text.charAt(textIndex) == '[' && allowCommands) {
					int n2 = textIndex + 1;
					while (text.charAt(n2) != ']') { n2++; }
					String tintN = text.substring(textIndex + 1, n2);
					
					boolean def = false;
					boolean bg = false;
					if (tintN.startsWith("bg=")) {
						bg = true;
						tintN = tintN.substring(3);
					}
					if (tintN.startsWith("default=")) {
						def = true;
						tintN = tintN.substring("default=".length());
					}
					double newAlpha = 1.0;
					Clr newC = null;
					if (tintN.length() == 10 && IS_ALPHA_CLR.matcher(tintN).matches()) {
						newAlpha = Integer.parseInt(tintN.substring(8, 10), 16) / 255.0;
						tintN = tintN.substring(0, 8);
					}
					if (!tintN.isEmpty()) {
						newC = knownColors.get(tintN);
						if (newC == null) {
							newC = Clr.fromHex(tintN);
							if (newC != null) {
								knownColors.put(tintN, newC);
							}
						}
						if (newC == null) {
							newC = Clr.getNamedColor(tintN);
							if (newC != null) {
								knownColors.put(tintN, newC);
							}
						}
					} else if (!bg) {
						newC = defaultTintC;
					}
					if (bg) {
						bgC = newC;
					} else if (def) {
						defaultTintC = newC;
					} else {
						tintC = newC;
						textAlpha = newAlpha;
					}
					textIndex = n2 + 1;
					continue;
				}
			}
			char currentChar = text.charAt(textIndex);
			int charWidth = fount.getWidth(currentChar) + fount.letterSpacing;
			if (doRender) {
				if (bgC != null) {
					f.rect(bgC, x + xOffset + fount.letterOffset, y + row * (fount.lineHeight + lineHeightDelta + fount.letterOffset) + fount.letterOffset, charWidth, fount.height, 0);
				}
				blit(fount.get(currentChar), tintC, textAlpha, x + xOffset + fount.letterOffset, y + row * (fount.lineHeight + lineHeightDelta + fount.letterOffset) + fount.letterOffset, 0, 0, 0);
			}
			xOffset += charWidth;
			textIndex++;
		}
		if (doRender) {
			return null;
		} else {
			return new Pt(Math.max(biggestWidth, xOffset), (fount.lineHeight + lineHeightDelta) * (row + 1));
		}
	}
}
