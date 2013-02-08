package com.zarkonnen.catengine;

import com.zarkonnen.catengine.Engine;
import com.zarkonnen.catengine.Frame;
import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.Rect;
import com.zarkonnen.catengine.util.ScreenMode;
import java.io.InputStream;
import java.util.ArrayList;
import org.newdawn.slick.*;

public class SlickEngine extends BasicGame implements Engine {
	public SlickEngine(String title, String loadBase, Integer fps) {
		super(title);
		this.loadBase = loadBase;
		this.fps = fps;
	}
	
	int fps;
	String loadBase;
	AppGameContainer agc;
	Game g;
	boolean fullscreen;
	boolean cursorVisible = true;

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(fps);
		gc.setVSync(true);
		gc.setShowFPS(false);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		g.input(new MyInput(gc));
	}

	@Override
	public void render(GameContainer gc, Graphics grphcs) throws SlickException {
		g.render(new MyFrame(gc, grphcs));
	}

	@Override
	public void run(com.zarkonnen.catengine.Game g) {
		this.g = g;
		try {
			agc = new AppGameContainer(this);
			agc.setDisplayMode(800, 600, false);
			agc.start();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}		
	}
	
	private class MyInput implements com.zarkonnen.catengine.Input {
		GameContainer gc;
		public MyInput(GameContainer gc) {
			this.gc = gc;
		}

		@Override
		public boolean keyDown(String key) {
			try {
				return gc.getInput().isKeyDown(org.newdawn.slick.Input.class.getField("KEY_" + key).getInt(null));
			} catch (Exception e) {
				return false;
			}
		}

		@Override
		public Pt cursor() {
			return new Pt(gc.getInput().getMouseX(), gc.getInput().getMouseY());
		}

		@Override
		public Pt click() {
			for (int i = 3; i > 0; i--) {
				if (gc.getInput().isMouseButtonDown(i)) {
					return cursor();
				}
			}
			return null;
		}

		@Override
		public int clickButton() {
			for (int i = 3; i > 0; i--) {
				if (gc.getInput().isMouseButtonDown(i)) {
					return i;
				}
			}
			return 0;
		}

		@Override
		public ScreenMode mode() {
			return new ScreenMode(gc.getWidth(), gc.getHeight(), fullscreen);
		}

		@Override
		public Input setMode(ScreenMode mode) {
			try {
				gc.setMouseGrabbed(false);
				agc.setDisplayMode(mode.width, mode.height, mode.fullscreen);
				fullscreen = mode.fullscreen;
				setCursorVisible(cursorVisible);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this;
		}

		@Override
		public ArrayList<ScreenMode> modes() {
			ArrayList<ScreenMode> sm =  new ArrayList<ScreenMode>();
			sm.add(new ScreenMode(800, 600, false));
			sm.add(new ScreenMode(640, 480, true));
			sm.add(new ScreenMode(800, 600, true));
			sm.add(new ScreenMode(gc.getScreenWidth(), gc.getScreenHeight(), true));
			return sm;
		}

		@Override
		public void quit() {
			gc.exit();
		}

		@Override
		public boolean isCursorVisible() {
			return cursorVisible;
		}

		@Override
		public com.zarkonnen.catengine.Input setCursorVisible(boolean visible) {
			cursorVisible = visible;
			gc.setMouseGrabbed(!visible);
			return this;
		}
	}

	private class MyFrame implements Frame {
		private MyFrame(GameContainer gc, Graphics grphcs) {
			this.gc = gc;
			this.g = grphcs;
		}
		
		GameContainer gc;
		Graphics g;
		
		
		@Override
		public ScreenMode mode() {
			return new ScreenMode(gc.getWidth(), gc.getHeight(), fullscreen);
		}

		@Override
		public Rect rect(Clr c, double x, double y, double width, double height, double angle) {
			g.setColor(new Color(c.r, c.g, c.b, c.a));
			if (angle == 0) {
				g.fillRect((float) x, (float) y, (float) width, (float) height);
			} else {
				g.translate((float) x, (float) y);
				g.rotate(0, 0, (float)  (angle * 180 / Math.PI));
				g.fillRect(0, 0, (float) width, (float) height);
				g.rotate(0, 0, (float) - (angle * 180 / Math.PI));
				g.translate((float) -x, (float) -y);
			}
			return new Rect(x, y, width, height);
		}

		@Override
		public Rect blit(String img, Clr tint, double x, double y, double width, double height, double angle) {
			Image image = getImage(img);
			if (image == null) { return null; }
			g.translate((float) x, (float) y);
			if (angle != 0) { g.rotate(0, 0, (float) (angle * 180 / Math.PI)); }
			if (tint != null && tint.a != 255) {
				if (width == 0 && height == 0) {
					g.drawImage(image, 0, 0);
				} else {
					g.drawImage(image, (float) x, (float) y, (float) (x + width), (float) (y + height), 0f, 0f, image.getWidth(), image.getHeight());
				}
			}
			if (width == 0 && height == 0) {
				g.drawImage(image, 0, 0, tint == null ? null : new Color(tint.r, tint.g, tint.b, tint.a));
			} else {
				g.drawImage(image, (float) x, (float) y, (float) (x + width), (float) (y + height), 0f, 0f, image.getWidth(), image.getHeight(), tint == null ? null : new Color(tint.r, tint.g, tint.b, tint.a));
			}
			if (angle != 0) { g.rotate(0, 0, (float) - (angle * 180 / Math.PI)); }
			g.translate((float) -x, (float) -y);
			return new Rect(x, y, width == 0 ? image.getWidth() : width, height == 0 ? image.getHeight() : height);
		}

		private Image getImage(String name) {
			InputStream is = SlickEngine.class.getResourceAsStream(loadBase + name);
			if (is == null) {
				is = SlickEngine.class.getResourceAsStream(loadBase + name + ".png");
			}
			if (is == null) {
				is = SlickEngine.class.getResourceAsStream(loadBase + name + ".jpg");
			}
			if (is == null) {
				return null;
			}
			try {
				return new Image(is, name, false);
			} catch (SlickException e) {
				return null;
			}
		}
	}

}
