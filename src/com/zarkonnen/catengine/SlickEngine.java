package com.zarkonnen.catengine;

import com.zarkonnen.catengine.Engine;
import com.zarkonnen.catengine.Frame;
import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.Rect;
import com.zarkonnen.catengine.util.ScreenMode;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.renderer.Renderer;

public class SlickEngine extends BasicGame implements Engine {
	public SlickEngine(String title, String loadBase, String soundLoadBase, Integer fps) {
		super(title);
		this.loadBase = loadBase;
		this.soundLoadBase = soundLoadBase;
		this.fps = fps;
	}
	
	int fps;
	Music currentMusic;
	String loadBase;
	String soundLoadBase;
	MyAppGameContainer agc;
	Game g;
	boolean fullscreen;
	boolean cursorVisible = true;
	final HashMap<String, SoftReference<Image>> images = new HashMap<String, SoftReference<Image>>();
	final HashMap<String, SoftReference<Music>> musics = new HashMap<String, SoftReference<Music>>();
	final HashMap<String, ArrayList<SoftReference<Sound>>> sounds = new HashMap<String, ArrayList<SoftReference<Sound>>>();
	
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
	public void setup(com.zarkonnen.catengine.Game g) {
		this.g = g;
		try {
			agc = new MyAppGameContainer(this);
			agc.setDisplayMode(800, 600, false);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}	
	}
	
	class MyAppGameContainer extends AppGameContainer {
		public MyAppGameContainer(org.newdawn.slick.Game game) throws SlickException {
			super(game, 800, 600, false);
			setup();
			getDelta();
		}

		public void runUntil(Condition c) throws SlickException {
			getDelta();
			while (!c.satisfied() && running()) {
				gameLoop();
			}

			if (forceExit) {
				System.exit(0);
			}
		}
	}

	@Override
	public void runUntil(Condition u) {
		try {
			agc.runUntil(u);
		} catch (SlickException e) {
			e.printStackTrace();
			agc.destroy();
			System.exit(1);
		}
	}
	
	@Override
	public void destroy() {
		agc.destroy();
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
		public boolean keyPressed(String key) {
			try {
				return gc.getInput().isKeyPressed(org.newdawn.slick.Input.class.getField("KEY_" + key).getInt(null));
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
			for (int i = 3; i >= 0; i--) {
				if (gc.getInput().isMouseButtonDown(i)) {
					return cursor();
				}
			}
			return null;
		}

		@Override
		public int clickButton() {
			for (int i = 3; i >= 0; i--) {
				if (gc.getInput().isMouseButtonDown(i)) {
					return i + 1;
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

		@Override
		public void play(String sound, double pitch, double volume, double x, double y) {
			try {
				getSound(sound).playAt((float) pitch, (float) volume, (float) x, (float) y, 0);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		private Sound getSound(String sound) throws SlickException {
			if (!sound.contains(".")) { sound += ".ogg"; }
			if (!sounds.containsKey(sound)) {
				sounds.put(sound, new ArrayList<SoftReference<Sound>>());
			}
			ArrayList<SoftReference<Sound>> l = sounds.get(sound);
			for (SoftReference<Sound> entry : l) {
				Sound snd = entry.get();
				if (snd != null && !snd.playing()) {
					return snd;
				}
			}
			
			for (int i = 0; i < l.size(); i++) {
				Sound snd = l.get(i).get();
				if (snd == null) {
					snd = new Sound(SlickEngine.class.getResource(soundLoadBase + sound));
					l.set(i, new SoftReference<Sound>(snd));
					return snd;
				}
			}
			
			Sound snd = new Sound(SlickEngine.class.getResource(soundLoadBase + sound));
			l.add(new SoftReference<Sound>(snd));
			return snd;
		}
		
		private Music getMusic(String music) throws SlickException {
			synchronized (musics) {
				if (!music.contains(".")) { music += ".ogg"; }
				if (musics.containsKey(music)) {
					SoftReference<Music> sr = musics.get(music);
					Music m = sr.get();
					if (m != null) {
						return m;
					}
				}
				Music m = new Music(SlickEngine.class.getResource(soundLoadBase + music));
				musics.put(music, new SoftReference<Music>(m));
				return m;
			}
		}

		@Override
		public void playMusic(final String music, final double volume, final MusicDone callback) {
			new Thread("MusicStarter") {
				@Override
				public void run() {
					try {
						synchronized (musics) {
							stopMusic();
							currentMusic = getMusic(music);
							currentMusic.play(1.0f, (float) volume);
							currentMusic.addListener(new MusicListener() {
								@Override
								public void musicEnded(Music m) {
									if (m == currentMusic && callback != null) {
										callback.run(music, volume);
									}
								}

								@Override
								public void musicSwapped(Music oldM, Music newM) {
									// Ignore.
								}
							});
						}
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}

		@Override
		public void stopMusic() {
			synchronized (musics) {
				if (currentMusic != null && currentMusic.playing()) {
					currentMusic.stop();
					currentMusic = null;
				}
			}
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
		public int fps() {
			return gc.getFPS();
		}
		
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
			g.setColor(Color.white);
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
					g.drawImage(image, 0f, 0f, (float) (width), (float) (height), 0f, 0f, image.getWidth(), image.getHeight());
				}
			}
			if (width == 0 && height == 0) {
				g.drawImage(image, 0, 0, tint == null ? null : new Color(tint.r, tint.g, tint.b, tint.a));
			} else {
				if (tint == null) {
					g.drawImage(image, 0f, 0f, (float) (width), (float) (height), 0f, 0f, image.getWidth(), image.getHeight());
				} else {
					g.drawImage(image, 0f, 0f, (float) (width), (float) (height), 0f, 0f, image.getWidth(), image.getHeight(), new Color(tint.r, tint.g, tint.b, tint.a));
				}
			}
			g.setColor(Color.white);
			if (angle != 0) { g.rotate(0, 0, (float) - (angle * 180 / Math.PI)); }
			g.translate((float) -x, (float) -y);
			return new Rect(x, y, width == 0 ? image.getWidth() : width, height == 0 ? image.getHeight() : height);
		}

		private Image getImage(String name) {
			if (images.containsKey(name)) {
				Image img = images.get(name).get();
				if (img != null) { return img; }
			}
			Image img = loadImage(name);
			images.put(name, new SoftReference<Image>(img));
			return img;
		}
		
		private Image loadImage(String name) {
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
