package org.gjt.sp.jedit.gui;

import java.awt.Graphics2D;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

public abstract class RotationType {

	static final int NONE = 0;
	static final int CW = 1;
	static final int CCW = 2;
	
	abstract int getRotationDirection();
	abstract void rotate(Graphics2D g2d,AffineTransform oldTransform,int x,int y,float height,float width,float ascent,GlyphVector glyphs);
	
	static RotationType newRotation(int code) {
		switch(code) {
			
		case NONE:
			return new NoRotation();
		case CW:
			return new ClockwiseRotation();
		case CCW:
			return new CounterClockwiseRotation();
		default:
			throw new IllegalArgumentException("Incorrect Rotation Type");
		}
	}
}
