package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.ScreenMode;
import java.util.ArrayList;
import java.util.List;

public interface Input {
	public boolean keyDown(String key);
	public boolean keyPressed(String key);
	public String lastKeyPressed();
	public Pt cursor();
	public Pt click();
	public int clickButton();
	
	public ScreenMode mode();
	public Input setMode(ScreenMode mode);
	public ArrayList<ScreenMode> modes();
	
	public boolean isCursorVisible();
	public Input setCursorVisible(boolean visible);
	
	public void play(String sound, double pitch, double volume, double x, double y);
	public void preloadSounds(List<String> sound, Runnable preloadDone);
	
	public void playMusic(String music, double volume, MusicCallback startedCallback, MusicCallback doneCallback);
	public void stopMusic();
	public void preloadMusic(List<String> music, Runnable preloadDone);
	
	public void quit();
}
