package org.gjt.sp.jedit.gui;

import java.awt.Graphics2D;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

public class NoRotation extends RotationType{

	@Override
	int getRotationDirection() {
		return NONE;
	}

	@Override
	void rotate(Graphics2D g2d, AffineTransform oldTransform, int x, int y, float height, float width, float ascent,
			GlyphVector glyphs) {
		g2d.drawGlyphVector(glyphs,x + 2,y + ascent);
		
	}

}
