/*******************************************************************************
 * Copyright (c) 2004, 2023 Elias Volanakis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.examples.shapes.model.commands;

import org.eclipse.gef.commands.Command;

import org.eclipse.gef.examples.shapes.model.Connection;
import org.eclipse.gef.examples.shapes.model.Shape;

/**
 * A command to reconnect a connection to a different start point or end point.
 * The command can be undone or redone.
 * <p>
 * This command is designed to be used together with a GraphicalNodeEditPolicy.
 * To use this command propertly, following steps are necessary:
 * </p>
 * <ol>
 * <li>Create a subclass of GraphicalNodeEditPolicy.</li>
 * <li>Override the <tt>getReconnectSourceCommand(...)</tt> method. Here you
 * need to obtain the Connection model element from the ReconnectRequest, create
 * a new ConnectionReconnectCommand, set the new connection <i>source</i> by
 * calling the <tt>setNewSource(Shape)</tt> method and return the command
 * instance.
 * <li>Override the <tt>getReconnectTargetCommand(...)</tt> method.</li> Here
 * again you need to obtain the Connection model element from the
 * ReconnectRequest, create a new ConnectionReconnectCommand, set the new
 * connection <i>target</i> by calling the <tt>setNewTarget(Shape)</tt> method
 * and return the command instance.</li>
 * </ol>
 * 
 * @see org.eclipse.gef.examples.shapes.parts.ShapeEditPart#createEditPolicies()
 *      for an example of the above procedure.
 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy
 * @see #setNewSource(Shape)
 * @see #setNewTarget(Shape)
 * @author Elias Volanakis
 */
public class ConnectionReconnectCommand extends Command {

	/** The connection instance to reconnect. */
	private Connection connection;
	/** The new source endpoint. */
	private Shape newSource;
	/** The new target endpoint. */
	private Shape newTarget;
	/** The original source endpoint. */
	private final Shape oldSource;
	/** The original target endpoint. */
	private final Shape oldTarget;

	/**
	 * Instantiate a command that can reconnect a Connection instance to a different
	 * source or target endpoint.
	 * 
	 * @param conn the connection instance to reconnect (non-null)
	 * @throws IllegalArgumentException if conn is null
	 */
	public ConnectionReconnectCommand(Connection conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		this.connection = conn;
		this.oldSource = conn.getSource();
		this.oldTarget = conn.getTarget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (newSource != null) {
			return checkSourceReconnection();
		} else if (newTarget != null) {
			return checkTargetReconnection();
		}
		return false;
	}

	/**
	 * Return true, if reconnecting the connection-instance to newSource is allowed.
	 */
	private boolean checkSourceReconnection() {
		// connection endpoints must be different Shapes
		if (newSource.equals(oldTarget)) {
			return false;
		}
		// return false if a newSource -> oldTarget connection exists already
		// and it is a different instance than the connection-field
		return newSource.getSourceConnections().stream()
				.noneMatch(conn -> conn.getTarget().equals(oldTarget) && !conn.equals(connection));
	}

	/**
	 * Return true, if reconnecting the connection-instance to newTarget is allowed.
	 */
	private boolean checkTargetReconnection() {
		// connection endpoints must be different Shapes
		if (newTarget.equals(oldSource)) {
			return false;
		}
		// return false if a oldSource -> newTarget connection exists already
		// and it is a different instance that the connection-field
		return newTarget.getTargetConnections().stream()
				.noneMatch(conn -> conn.getSource().equals(oldSource) && !conn.equals(connection));
	}

	/**
	 * Reconnect the connection to newSource (if setNewSource(...) was invoked
	 * before) or newTarget (if setNewTarget(...) was invoked before).
	 */
	@Override
	public void execute() {
		if (newSource != null) {
			connection.reconnect(newSource, oldTarget);
		} else if (newTarget != null) {
			connection.reconnect(oldSource, newTarget);
		} else {
			throw new IllegalStateException("Should not happen");
		}
	}

	/**
	 * Set a new source endpoint for this connection. When execute() is invoked, the
	 * source endpoint of the connection will be attached to the supplied Shape
	 * instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>target</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionSource a non-null Shape instance, to be used as a new source
	 *                         endpoint
	 * @throws IllegalArgumentException if connectionSource is null
	 */
	public void setNewSource(Shape connectionSource) {
		if (connectionSource == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection startpoint");
		newSource = connectionSource;
		newTarget = null;
	}

	/**
	 * Set a new target endpoint for this connection When execute() is invoked, the
	 * target endpoint of the connection will be attached to the supplied Shape
	 * instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>source</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionTarget a non-null Shape instance, to be used as a new target
	 *                         endpoint
	 * @throws IllegalArgumentException if connectionTarget is null
	 */
	public void setNewTarget(Shape connectionTarget) {
		if (connectionTarget == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection endpoint");
		newSource = null;
		newTarget = connectionTarget;
	}

	/**
	 * Reconnect the connection to its original source and target endpoints.
	 */
	@Override
	public void undo() {
		connection.reconnect(oldSource, oldTarget);
	}

}
