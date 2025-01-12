/*******************************************************************************
 * Copyright (c) 2008, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.draw2d.test;
import org.eclipse.draw2d.geometry.Vector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Vector's tests
 * 
 * @author aboyko
 * 
 */
public class VectorTest extends Assert {

	@Test
	public void test_getLength() {
		testLengthValues(3, 4, 5);
		testLengthValues(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Test
	public void test_getOrthoComplement() {
		Vector a = new Vector(3, -5);
		assertTrue(a.getOrthogonalComplement().equals(new Vector(5, 3)));
	}

	@Test
	public void test_getDotProduct() {
		Vector a = new Vector(3, 2);
		Vector b = new Vector(2, -2);
		assertTrue(a.getDotProduct(b) == 2);
	}

	@Test
	public void test_getAngle() {
		Vector a = new Vector(24.03809869102058, -6.868028197434448);
		Vector b = new Vector(-24.038098691020593, 6.868028197434448);
		assertTrue(a.getAngle(b) == 180.0);
	}

	private void testLengthValues(int x, int y, double expectedLength) {
		Vector Vector = new Vector(x, y);
		assertEquals(expectedLength, Vector.getLength(), 0);
	}
}
