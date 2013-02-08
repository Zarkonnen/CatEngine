package com.zarkonnen.catengine.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class FontGen {
	public static void main(String[] args)throws Exception {
		if (args.length != 7) {
			System.out.println("Params: font size imgWidth imgHeight targetDirectory symbolsFilePath pretty");
		}
		
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(args[5]), "UTF-8"));
		char[] chars = r.readLine().toCharArray();
		r.close();
		File targetDir = new File(args[4]);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		for (char c : chars) {
			BufferedImage img = new BufferedImage(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Transparency.TRANSLUCENT);
			Graphics2D g = img.createGraphics();
			if (args[6].equals("true") || args[6].equals("1")) {
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font(args[0], Font.PLAIN, Integer.parseInt(args[1])));
			g.drawString(new String(new char[] {c}), 0, g.getFontMetrics().getMaxAscent());
			ImageIO.write(img, "PNG", new File(targetDir, Character.codePointAt(new char[] {c}, 0) + ".png"));
		}
	}
}
