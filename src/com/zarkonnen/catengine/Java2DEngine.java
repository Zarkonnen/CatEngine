package com.zarkonnen.catengine;

import com.zarkonnen.catengine.Engine;
import com.zarkonnen.catengine.Frame;
import com.zarkonnen.catengine.Game;
import com.zarkonnen.catengine.Input;
import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.Rect;
import com.zarkonnen.catengine.util.ScreenMode;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Java2DEngine implements Engine, KeyListener, MouseListener, MouseMotionListener {
	Game g;
	String winTitle;
	final String loadBase;
	final String soundLoadBase;
	boolean fullscreen = false;
	JFrame gameFrame;
	Canvas canvas;
	InputFrame inputFrame;
	GraphicsConfiguration config;
	HashMap<String, SoftReference<BufferedImage>> images = new HashMap<String, SoftReference<BufferedImage>>();
	DisplayMode originalMode;
	int msPerFrame;
	long lastFrame;
	boolean quitted = false;
	boolean cursorVisible = true;
	LinkedList<Long> frameIntervalWindow = new LinkedList<Long>();

	public Java2DEngine(String winTitle, String loadBase, String soundLoadBase, Integer frameRate) {
		this.winTitle = winTitle;
		this.loadBase = loadBase;
		this.soundLoadBase = soundLoadBase;
		this.msPerFrame = 1000 / frameRate;
	}

	private MyFrame next() {
		if (lastFrame != 0) {
			canvas.getBufferStrategy().show();
			long nextFrame = lastFrame + msPerFrame * 1000000;
			long timeLeft = nextFrame - System.nanoTime();
			if (timeLeft > 5000000) { // 5 ms!
				long timeToSleep = timeLeft / 1000000 - 1;
				try { Thread.sleep(timeToSleep); } catch (Exception e) {}
			}
			while (System.nanoTime() < nextFrame) {}
			frameIntervalWindow.add(System.nanoTime() - lastFrame);
			if (frameIntervalWindow.size() > 3) {
				frameIntervalWindow.removeFirst();
			}
		}
		lastFrame = System.nanoTime();
		InputFrame f;
		synchronized (this) {
			f = inputFrame;
			inputFrame = new InputFrame();
			inputFrame.mouse = f.mouse;
			inputFrame.keyDowns = new HashSet<String>(f.keyDowns);
		}
		return new MyFrame(f);
	}

	@Override
	public void setup(Game g) {
		this.g = g;
		inputFrame = new InputFrame();
		gameFrame = new JFrame(winTitle);
		gameFrame.setIgnoreRepaint(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		gameFrame.add(canvas);
		gameFrame.setSize(800, 600);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
		canvas.createBufferStrategy(2);
		gameFrame.addKeyListener(this);
		gameFrame.addMouseListener(this);
		gameFrame.addMouseMotionListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.requestFocus();
		config = canvas.getGraphicsConfiguration();
		originalMode = config.getDevice().getDisplayMode();
	}

	@Override
	public void destroy() {
		gameFrame.dispose(); quitted = true;
	}
	
	@Override
	public void runUntil(Condition u) {
		while (!quitted && !u.satisfied()) {
			MyFrame f = next();
			g.input(f);
			if (quitted) { return; }
			g.render(f);
		}
	}

	static class InputFrame {
		Point mouse = new Point(0, 0);
		Point click = null;
		int button;
		HashSet<String> keyDowns = new HashSet<String>();
		HashSet<String> keyPresseds = new HashSet<String>();
	}

	class MyFrame implements Frame, Input {
		InputFrame input;
		Graphics2D g;

		public MyFrame(InputFrame input) {
			this.input = input;
			g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
			//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}
		
		@Override
		public int fps() {
			if (frameIntervalWindow.isEmpty()) { return 0; }
			long sum = 0;
			for (long l : frameIntervalWindow) {
				sum += l;
			}
			long avg = sum / frameIntervalWindow.size();
			return (int) (1000000000l / avg);
		}
		
		@Override
		public boolean keyDown(String key) {
			return input.keyDowns.contains(key);
		}
		
		@Override
		public boolean keyPressed(String key) {
			return input.keyPresseds.contains(key);
		}

		@Override
		public Pt cursor() {
			return new Pt(input.mouse.x, input.mouse.y);
		}
		
		@Override
		public Pt click() {
			return input.click == null ? null : new Pt(input.click.x, input.click.y);
		}

		@Override
		public int clickButton() {
			return input.button;
		}

		@Override
		public ScreenMode mode() {
			return new ScreenMode(gameFrame.getWidth(), gameFrame.getHeight(), fullscreen);
		}

		@Override
		public Input setMode(ScreenMode mode) {
			gameFrame.setCursor(null);
			canvas.setCursor(null);
			config.getDevice().setFullScreenWindow(null);
			if (mode.fullscreen) {
				if (!fullscreen) {
					gameFrame.dispose();
					gameFrame.setUndecorated(true);
					gameFrame.setVisible(true);
					config = gameFrame.getGraphicsConfiguration();
				}
				gameFrame.setSize(mode.width, mode.height);
				config.getDevice().setFullScreenWindow(gameFrame);
				setDisplayMode(mode);
				fullscreen = true;
			} else {
				if (fullscreen) {
					gameFrame.dispose();
					gameFrame.setUndecorated(false);
					gameFrame.setVisible(true);
					config = gameFrame.getGraphicsConfiguration();
				}
				gameFrame.setSize(mode.width, mode.height);
				fullscreen = false;
			}
			setCursorVisible(cursorVisible);
			return this;
		}
		
		void setDisplayMode(ScreenMode mode) {
			int bestDepth = 0;
			DisplayMode bestDM = null;
			for (DisplayMode dm : config.getDevice().getDisplayModes()) {
				if (dm.getWidth() == mode.width && dm.getHeight() == mode.height && dm.getBitDepth() > bestDepth) {
					bestDepth = dm.getBitDepth();
					bestDM = dm;
				}
			}
			if (bestDM != null) {
				try {
					config.getDevice().setDisplayMode(bestDM);
					return;
				} catch (Exception e) {}
			}
			for (DisplayMode dm : config.getDevice().getDisplayModes()) {
				if (dm.getWidth() == mode.width && dm.getHeight() == mode.height) {
					try {
						config.getDevice().setDisplayMode(dm);
						return;
					} catch (Exception e) {}
				}
			}
		}

		@Override
		public ArrayList<ScreenMode> modes() {
			ArrayList<ScreenMode> ms = new ArrayList<ScreenMode>();
			ms.add(new ScreenMode(800, 600, false));
			if (config.getDevice().isFullScreenSupported()) {
				for (DisplayMode dm : config.getDevice().getDisplayModes()) {
					ScreenMode sm = new ScreenMode(dm.getWidth(), dm.getHeight(), true);
					if (!ms.contains(sm)) {
						ms.add(sm);
					}
				}
			}
			return ms;
		}

		@Override
		public Rect rect(Clr c, double x, double y, double width, double height, double angle) {
			g.setColor(new Color(c.r, c.g, c.b, c.a));
			if (angle == 0) {
				g.fill(new Rectangle2D.Double(x, y, width, height));
			} else {
				g.translate(x, y);
				g.rotate(angle);
				g.fill(new Rectangle2D.Double(0, 0, width, height));
				g.rotate(-angle);
				g.translate(-x, -y);
			}
			return new Rect(x, y, width, height);
		}

		@Override
		public Rect blit(String img, Clr tint, double x, double y, double width, double height, double angle) {
			BufferedImage image = getImage(img, tint);
			if (image == null) { return null; }
			g.translate(x, y);
			if (angle != 0) { g.rotate(angle); }
			if (width == 0 && height == 0) {
				g.drawImage(image, 0, 0, null);
			} else {
				g.drawImage(image, 0, 0, (int) width, (int) height, null);
			}
			if (angle != 0) { g.rotate(-angle); }
			g.translate(-x, -y);
			return new Rect(x, y, width == 0 ? image.getWidth() : width, height == 0 ? image.getHeight() : height);
		}

		@Override
		public void quit() {
			destroy();
		}

		@Override
		public boolean isCursorVisible() {
			return cursorVisible;
		}

		@Override
		public Input setCursorVisible(boolean visible) {
			cursorVisible = visible;
			if (visible) {
				gameFrame.setCursor(null);
				canvas.setCursor(null);
			} else {
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
				gameFrame.setCursor(blankCursor);
				canvas.setCursor(blankCursor);
			}
			return this;
		}

		@Override
		public void play(String sound, double pitch, double volume, double x, double y) {
			// qqDPS TODO
		}

		@Override
		public void playMusic(String music, double volume, MusicDone callback) {
			// qqDPS TODO
		}

		@Override
		public void stopMusic() {
			// qqDPS TODO
		}
	}
	
	BufferedImage getImage(String name, Clr tint) {
		String key = name + (tint == null ? "" : tint.toString());
		if (images.containsKey(key)) {
			SoftReference<BufferedImage> ref = images.get(key);
			BufferedImage img = ref.get();
			if (img != null) { return img; }
		}
		BufferedImage img = null;
		if (tint != null && images.containsKey(name)) {
			SoftReference<BufferedImage> ref = images.get(name);
			img = ref.get();
		}
		if (img == null) {
			img = readImage(name);
		}
		if (img == null) {
			return null;
		}
		if (tint != null) {
			img = tint(img, new Color(tint.r, tint.g, tint.b, tint.a));
		}
		images.put(key, new SoftReference<BufferedImage>(img));
		return img;
	}
	
	BufferedImage readImage(String name) {
		InputStream is = Java2DEngine.class.getResourceAsStream(loadBase + name);
		if (is == null) {
			is = Java2DEngine.class.getResourceAsStream(loadBase + name + ".png");
		}
		if (is == null) {
			is = Java2DEngine.class.getResourceAsStream(loadBase + name + ".jpg");
		}
		if (is == null) {
			return null;
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(is);
		} catch (Exception e) {
			return null;
		}
		if (img == null) {
			return null;
		}
		try {
			BufferedImage fixedImg = config.createCompatibleImage(img.getWidth(), img.getHeight(),
					Transparency.TRANSLUCENT);
			Graphics2D fig = fixedImg.createGraphics();
			fig.drawImage(img, 0, 0, null);
			fig.dispose();
			fixedImg.flush();
			return fixedImg;
		}
		catch (Exception e) { return null; }
	}
	
	BufferedImage createImage(int width, int height, int transparency) {
		BufferedImage img = config.createCompatibleImage(width, height, transparency);
		return img;
	}
	
	BufferedImage tint(BufferedImage src, Color tint) {
		BufferedImage src2 = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		src2.createGraphics().drawImage(src, 0, 0, null);
		src = src2;
		final int w = src.getWidth();
		final int h = src.getHeight();
		final BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);//createImage(w, h, src.getTransparency());
		final WritableRaster ar = src.getAlphaRaster();
		final int a = tint.getAlpha();
		final int r = tint.getRed() * a;
		final int g = tint.getGreen() * a;
		final int b = tint.getBlue() * a;
		final int na = 255 - a;

		for (int y = 0; y < h; y++) { for (int x = 0; x < w; x++) {
			// Need to extract alpha value from alpha raster because getRGB is broken.
			Color c = new Color(src.getRGB(x, y));
			c = new Color(
					(c.getRed() * na + (r * c.getRed()) / 256) / 256,
					(c.getGreen() * na + (g * c.getGreen()) / 256) / 256,
					(c.getBlue() * na + (b * c.getBlue()) / 256) / 256,
					ar.getSample(x, y, 0)
			);
			dst.setRGB(x, y, c.getRGB());
		}}
		final BufferedImage dst2 = createImage(w, h, src.getTransparency());
		dst2.createGraphics().drawImage(dst, 0, 0, null);
		return dst2;
	}

	@Override
	public synchronized void keyPressed(KeyEvent ke) {
		inputFrame.keyDowns.add(KeyEvent.getKeyText(ke.getKeyCode()));
		inputFrame.keyPresseds.add(KeyEvent.getKeyText(ke.getKeyCode()));
	}
	
	@Override
	public synchronized void mouseMoved(MouseEvent me) {
		inputFrame.mouse = me.getPoint();
	}

	@Override
	public synchronized void mousePressed(MouseEvent me) {
		inputFrame.click = me.getPoint();
		inputFrame.button = me.getButton();
	}
	
	@Override
	public void keyTyped(KeyEvent ke) {}

	@Override
	public void keyReleased(KeyEvent ke) {
		inputFrame.keyDowns.remove(KeyEvent.getKeyText(ke.getKeyCode()));
	}

	@Override
	public void mouseClicked(MouseEvent me) {}

	@Override
	public void mouseReleased(MouseEvent me) {}

	@Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent me) {}

	@Override
	public void mouseDragged(MouseEvent me) {
		inputFrame.mouse = me.getPoint();
	}
}
