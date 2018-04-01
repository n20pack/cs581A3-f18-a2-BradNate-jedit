/*
 * StructureMatcher.java - Abstract interface for bracket matching, etc.
 * :tabSize=4:indentSize=4:noTabs=false:
 * :folding=explicit:collapseFolds=1:
 *
 * Copyright (C) 2003 Slava Pestov
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.gjt.sp.jedit.textarea;

//{{{ Imports
import java.awt.*;
import org.gjt.sp.jedit.TextUtilities;
//}}}

/**
 * An interface for matching parts of a source file's stucture. The default
 * implementation matches brackets. The XML plugin provides an implementation
 * for matching XML tags.
 *
 * @author Slava Pestov
 * @version $Id$
 * @since jEdit 4.2pre3
 */
public interface StructureMatcher
{
	//{{{ getMatch() method
	/**
	 * Returns the element matching the one at the given text area's
	 * caret position, or null.
	 * @since jEdit 4.2pre3
	 */
	Match getMatch(TextArea textArea);
	//}}}

	//{{{ selectMatch() method
	/**
	 * Selects from the caret to the matching structure element (if there is
	 * one, otherwise the behavior of this method is undefined).
	 * @since jEdit 4.2pre3
	 */
	void selectMatch(TextArea textArea);
	//}}}

	//{{{ BracketMatcher class
	static class BracketMatcher implements StructureMatcher
	{
		public Match getMatch(TextArea textArea)
		{
			int offset = textArea.getCaretPosition()
				- textArea.getLineStartOffset(
				textArea.getCaretLine());

			if(offset != 0)
			{
				int bracketOffset = TextUtilities.findMatchingBracket(
					textArea.getBuffer(),
					textArea.getCaretLine(),
					offset - 1);
				if(bracketOffset != -1)
				{
					int bracketLine = textArea
						.getLineOfOffset(
						bracketOffset);
					return new Match(this,
						bracketLine,
						bracketOffset,
						bracketLine,
						bracketOffset + 1);
				}
			}

			return null;
		}

		public void selectMatch(TextArea textArea)
		{
			textArea.selectToMatchingBracket();
		}
	} //}}}

	//{{{ Match class
	/**
	 * A structure match, denoted by a start and end position.
	 * @since jEdit 4.2pre3
	 */
	public static class Match
	{
		public StructureMatcher matcher;
		public int startLine;
		public int start;
		public int endLine;
		public int end;

		public Match() {}

		public Match(StructureMatcher matcher)
		{
			this.matcher = matcher;
		}

		public Match(StructureMatcher matcher, int startLine,
			int start, int endLine, int end)
		{
			this(matcher);
			this.startLine = startLine;
			this.start = start;
			this.endLine = endLine;
			this.end = end;
		}
	} //}}}

	//{{{ Highlight class
	/**
	 * Paints the structure match highlight.
	 */
	static class Highlight extends TextAreaExtension
	{
		Highlight(TextArea textArea)
		{
			this.textArea = textArea;
		}

		public void paintValidLine(Graphics2D gfx, int screenLine,
			int physicalLine, int start, int end, int y)
		{
			if(!textArea.getPainter().isStructureHighlightEnabled())
				return;

			Match match = textArea.getStructureMatch();
			if(match != null)
			{
				textArea.paintHighlight(gfx,screenLine,
					start,end,y,match);
			}
		}

		private TextArea textArea;
	} //}}}
}
