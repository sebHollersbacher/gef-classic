/*******************************************************************************
 * Copyright 2005-2007, CHISEL Group, University of Victoria, Victoria, BC,
 * Canada. All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Chisel Group, University of Victoria
 ******************************************************************************/
package org.eclipse.zest.examples.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphContainer;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

public class Showcase {

	private static Image image1;
	private static Image classImage;

	public static void populateContainer(GraphContainer c, Graph g, int numb) {
		GraphNode a = new GraphNode(c, ZestStyles.NODES_HIDE_TEXT, "Node", classImage);
		for (int i = 0; i < numb; i++) {
			GraphNode b = new GraphNode(c, ZestStyles.NODES_HIDE_TEXT, "Node", classImage);
			new GraphConnection(g, SWT.NONE, a, b);
			for (int j = 0; j < numb; j++) {
				GraphNode d = new GraphNode(c, ZestStyles.NODES_HIDE_TEXT, "Node", classImage);
				new GraphConnection(g, SWT.NONE, b, d);
			}
		}
		c.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display d = new Display();

		image1 = new Image(Display.getDefault(), Showcase.class.getResourceAsStream("package_obj.gif"));
		classImage = new Image(Display.getDefault(), Showcase.class.getResourceAsStream("class_obj.gif"));

		Shell shell = new Shell(d);
		shell.setText("Showcase");
		shell.setLayout(new FillLayout());
		shell.setSize(1000, 500);

		Graph g = new Graph(shell, SWT.NONE);
		GraphContainer a = new GraphContainer(g, SWT.NONE, "Container 1", image1);
		GraphContainer b = new GraphContainer(g, SWT.NONE, "Container 2", image1);
		GraphContainer c = new GraphContainer(g, SWT.NONE, "Container 3", image1);
		new GraphConnection(g, SWT.NONE, a, c);
		new GraphConnection(g, SWT.NONE, b, c);
		populateContainer(a, g, 1);
		populateContainer(b, g, 2);
		populateContainer(c, g, 3);

		g.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		shell.open();
		while (!shell.isDisposed()) {
			while (!d.readAndDispatch()) {
				d.sleep();
			}
		}
		image1.dispose();
	}
}
