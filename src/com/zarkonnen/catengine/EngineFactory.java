package com.zarkonnen.catengine;

import java.lang.reflect.InvocationTargetException;

/** I'm sorry! */
public class EngineFactory {
	public static String[] defaultEngines() {
		return new String[] {"Slick", "Java2D"};
	}
	
	public static Engine make(String impl, String title, String loadBase, int fps) throws InstantiationException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class c = null;
		try { c = Class.forName(impl); } catch (Exception e) {}
		if (c == null) { try { c = Class.forName("com.zarkonnen.catengine." + impl); } catch (Exception e) {} }
		if (c == null) { try { c = Class.forName("com.zarkonnen.catengine." + impl.toLowerCase() + "." + impl); } catch (Exception e) {} }
		if (c == null) { try { c = Class.forName("com.zarkonnen.catengine." + impl + "Engine"); } catch (Exception e) {} }
		if (c == null) { try { c = Class.forName("com.zarkonnen.catengine." + impl.toLowerCase() + "." + impl + "Engine"); } catch (Exception e) {} }
		if (c == null) { return null; }
		return (Engine) c.getConstructor(String.class, String.class, Integer.class).newInstance(title, loadBase, fps);
	}
}
