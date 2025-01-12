/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.draw2d.test;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

import org.junit.Assert;
import org.junit.Test;

public class XYLayoutTest extends Assert {

	protected XYLayout layout;
	protected RectangleFigure figure;
	protected RectangleFigure contents;

	@Test
	public void testPreferredSize() {
		layout = new XYLayout();
		contents = new RectangleFigure();
		contents.setLayoutManager(layout);

		figure = new RectangleFigure();
		contents.add(figure, new Rectangle(0, 0, 100, -1));
		figure.setPreferredSize(100, 150);

		Dimension d = contents.getPreferredSize();

		assertEquals(100, d.width);
		assertEquals(150, d.height);
	}

}
