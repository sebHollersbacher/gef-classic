package org.eclipse.gef.examples.logicdesigner;
/*
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 2001, 2002 - All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;
import org.eclipse.draw2d.FigureUtilities;

public interface LogicColorConstants
	extends ColorConstants
{

public final static Color andGate = new Color(null, 220,70,80);
public final static Color orGate = new Color (null, 0, 134, 255);
public final static Color xorGate = new Color (null, 240,240,40);
public final static Color logicGreen = new Color(null, 123,174,148);
public final static Color logicHighlight = new Color(null,66,166,115);
public final static Color connectorGreen = new Color(null,0,69,40);
public final static Color logicPrimarySelectedColor = new Color(null, 10,36,106);
public final static Color logicSecondarySelectedColor =
	FigureUtilities.mixColors(logicPrimarySelectedColor, button);
public final static Color logicBackgroundBlue = new Color(null, 200, 200, 240);

}