/*******************************************************************************
 * Copyright (c) 2011 Fabian Steeg. All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p/>
 * Contributors: Fabian Steeg - initial tests
 *******************************************************************************/
package org.eclipse.zest.tests;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IFigureProvider;
import org.eclipse.zest.core.viewers.IGraphContentProvider;
import org.eclipse.zest.core.widgets.CGraphNode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link IFigureProvider} class.
 * 
 * @author Fabian Steeg (fsteeg)
 * 
 */
public class IFigureProviderTests extends Assert {

	private GraphViewer viewer;
	private Shell shell;

	/**
	 * Set up the shell and viewer to use in the tests.
	 */
	@Before
	public void setUp() {
		shell = new Shell();
		viewer = new GraphViewer(shell, SWT.NONE);
	}

	/**
	 * Test with IGraphContentProvider that provides destinations only.
	 */
	@Test
	public void testWithDestinationProvider() {
		testWith(new DestinationContentProvider());
	}

	/**
	 * Test with IGraphContentProvider that provides sources only.
	 */
	@Test
	public void testWithSourceProvider() {
		testWith(new SourceContentProvider());
	}

	/**
	 * Test with IGraphContentProvider that provides destinations and sources.
	 */
	@Test
	public void testWithFullProvider() {
		testWith(new FullContentProvider());
	}

	private void testWith(IGraphContentProvider contentProvider) {
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new CustomLabelProvider());
		viewer.setInput(new Object());
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < viewer.getGraphControl().getNodes().size(); i++) {
			CGraphNode n = (CGraphNode) viewer.getGraphControl().getNodes().get(i);
			buffer.append(((Label) n.getNodeFigure().getChildren().get(0)).getText());
		}
		String string = buffer.toString();
		assertTrue("Label 1 should be in figure labels", string.indexOf("1") >= 0);
		assertTrue("Label 2 should be in figure labels", string.indexOf("2") >= 0);
		assertTrue("Label 3 should be in figure labels", string.indexOf("3") >= 0);
	}

	private class DestinationContentProvider implements IGraphContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		}

		@Override
		public Object getDestination(Object r) {
			if (r.equals("1to2"))
				return "2";
			if (r.equals("2to3"))
				return "3";
			if (r.equals("3to1"))
				return "1";
			return null;
		}

		@Override
		public Object[] getElements(Object arg0) {
			return new String[] { "1to2", "2to3", "3to1" };
		}

		@Override
		public Object getSource(Object r) {
			return null;
		}

	}

	private class SourceContentProvider implements IGraphContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		}

		@Override
		public Object getDestination(Object r) {
			return null;
		}

		@Override
		public Object[] getElements(Object arg0) {
			return new String[] { "1to2", "2to3", "3to1" };
		}

		@Override
		public Object getSource(Object r) {
			if (r.equals("1to2"))
				return "1";
			if (r.equals("2to3"))
				return "2";
			if (r.equals("3to1"))
				return "3";
			return null;
		}

	}

	private class FullContentProvider implements IGraphContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		}

		@Override
		public Object getDestination(Object r) {
			if (r.equals("1to2"))
				return "2";
			if (r.equals("2to3"))
				return "3";
			if (r.equals("3to1"))
				return "1";
			return null;
		}

		@Override
		public Object[] getElements(Object arg0) {
			return new String[] { "1to2", "2to3", "3to1" };
		}

		@Override
		public Object getSource(Object r) {
			if (r.equals("1to2"))
				return "1";
			if (r.equals("2to3"))
				return "2";
			if (r.equals("3to1"))
				return "3";
			return null;
		}

	}

	private class CustomLabelProvider extends LabelProvider implements IFigureProvider {
		@Override
		public String getText(Object node) {
			return node.toString();
		}

		@Override
		public IFigure getFigure(Object node) {
			Ellipse e = new Ellipse();
			e.setSize(40, 40);
			e.setLayoutManager(new BorderLayout());
			e.add(new Label(node.toString()), BorderLayout.CENTER);
			return e;
		}
	}

}
