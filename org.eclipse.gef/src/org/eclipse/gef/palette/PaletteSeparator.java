package org.eclipse.gef.palette;

import org.eclipse.gef.ui.palette.PaletteMessages;

/**
 * A separator for the palette
 * <br><br>
 * Separators can also be used as markers.  Palettes that expect external code to add
 * entries to it can use such markers to indicate where those new entries should be added.
 * For this to happen, a separator must be uniquely identified.  Unless a separator
 * is not a marker, it is recommended that it be given a unique ID.  If a separator is not
 * a marker, <code>NOT_A_MARKER</code> can be used as the ID.
 * 
 * @author Pratik Shah
 */
public class PaletteSeparator extends PaletteEntry {

private String id;

public static final Object PALETTE_TYPE_SEPARATOR = "$Palette Separator";//$NON-NLS-1$

public PaletteSeparator() {
	this ("");
}

/**
 * Constructor
 * 
 * @param	id	This Separator's unique ID
 */
public PaletteSeparator(String id) {
	super(PaletteMessages.NEW_SEPARATOR_LABEL, PaletteMessages.NEW_SEPARATOR_DESC);
	setId(id);
}

/**
 * Returns the id
 * @return String
 */
public String getId() {
	return id;
}

/**
 * Sets the id
 * @param id The new id to be set
 */
public void setId(String id) {
	this.id = id;
}

}
