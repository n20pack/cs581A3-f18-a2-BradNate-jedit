package org.gjt.sp.jedit.gui;

import java.awt.Graphics2D;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

public class CounterClockwiseRotation extends RotationType{

	@Override
	int getRotationDirection() {
		return CCW;
	}

	@Override
	void rotate(Graphics2D g2d, AffineTransform oldTransform, int x, int y, float height, float width, float ascent,
			GlyphVector glyphs) {
		AffineTransform trans = new AffineTransform();
		trans.concatenate(oldTransform);
		trans.translate(x,y - 2);
		trans.rotate(Math.PI * 3 / 2,
			height / 2, width / 2);
		g2d.setTransform(trans);
		g2d.drawGlyphVector(glyphs,(height - width) / 2,
			(width - height) / 2
			+ ascent);
		
	}

}
