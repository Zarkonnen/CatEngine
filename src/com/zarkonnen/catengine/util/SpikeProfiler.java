package com.zarkonnen.catengine.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SpikeProfiler {
	public static boolean active = false;
	public static File outputDir = null;
	public static int fileIndex = 0;
	
	public static ArrayList<Entry> log = new ArrayList<Entry>(4096);
	
	public static String frameDone(String name, long tooManyMilliseconds) {
		if (!active) { return null; }
		
		String outID = null;
		
		if (!log.isEmpty()) {
			long nanoTimeTaken = log.get(log.size() - 1).nanoStamp - log.get(0).nanoStamp;
			System.out.println(name + " " + nanoTimeTaken / 1000000l);
			if (nanoTimeTaken > tooManyMilliseconds * 1000000l) {
				if (outputDir != null) {
					outputDir.mkdirs();
					outID = fileIndex++ + "_" + System.currentTimeMillis();
					File f = new File(outputDir,  outID + ".txt");
					try {
						PrintWriter pw = new PrintWriter(f);
						for (Entry e : log) {
							pw.println(e);
						}
						pw.flush();
						pw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Frame took " + nanoTimeTaken + " ns, which is more than " + tooManyMilliseconds * 1000000l);
					for (Entry e : log) {
						System.out.println(e);
					}

					System.out.println();
				}
			}
		}
		
		log.clear();
		return outID;
	}
	
	public static void start(String name) {
		if (!active) { return; }
		log.add(new Entry(name, true));
	}
	
	public static void end(String name) {
		if (!active) { return; }
		log.add(new Entry(name, false));
	}
	
	public static void endStart(String name) {
		if (!active) { return; }
		log.add(new Entry(log.get(log.size() - 1).text, false));
		log.add(new Entry(name, true));
	}
	
	public static void endStart(String end, String start) {
		if (!active) { return; }
		log.add(new Entry(end, false));
		log.add(new Entry(start, true));
	}
	
	public static final class Entry {
		String text;
		boolean start;
		long nanoStamp;
		
		public Entry(String def) {
			String[] bits = def.split(" ", 3);
			nanoStamp = Long.parseLong(bits[0]);
			start = bits[1].equals("start");
			text = bits[2];
		}

		public Entry(String text, boolean start) {
			this.text = text;
			this.start = start;
			nanoStamp = System.nanoTime();
		}
		
		@Override
		public String toString() {
			return nanoStamp + (start ? " start " : " end ") + text;
		}
	}
}
