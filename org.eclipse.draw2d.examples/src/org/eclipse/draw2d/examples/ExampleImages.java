/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.draw2d.examples;

import org.eclipse.swt.graphics.Image;

/**
 * Created on :Sep 26, 2002
 * 
 * @author hudsonr
 * @since 2.0
 */
public final class ExampleImages {

	public static final Image GEORGE = new Image(null, ExampleImages.class.getResourceAsStream("images/george.gif")); //$NON-NLS-1$

	private ExampleImages() {
		throw new UnsupportedOperationException("Utility class should not be instantiated"); //$NON-NLS-1$
	}

}
