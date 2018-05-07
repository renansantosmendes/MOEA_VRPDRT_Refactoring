/* Copyright 2009-2018 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.moeaframework.algorithm;

import org.moeaframework.core.AlgorithmMOEA;

/**
 * An exception that originated from an algorithm during initialization.
 */
public class AlgorithmInitializationException extends AlgorithmException {

	private static final long serialVersionUID = -4341813616427565989L;

	/**
	 * Constructs an algorithm initialization exception originating from the 
	 * specified algorithm with the given message and cause.
	 * 
	 * @param algorithm the algorithm responsible for this exception
	 * @param message the message describing this exception
	 * @param cause the cause of this exception
	 */
	public AlgorithmInitializationException(AlgorithmMOEA algorithm,
			String message, Throwable cause) {
		super(algorithm, message, cause);
	}

	/**
	 * Constructs an algorithm initialization exception originating from the 
	 * specified algorithm with the given message.
	 * 
	 * @param algorithm the algorithm responsible for this exception
	 * @param message the message describing this exception
	 */
	public AlgorithmInitializationException(AlgorithmMOEA algorithm, 
			String message) {
		super(algorithm, message);
	}

	/**
	 * Constructs an algorithm initialization exception originating from the 
	 * specified algorithm with the given cause.
	 * 
	 * @param algorithm the algorithm responsible for this exception
	 * @param cause the cause of this exception
	 */
	public AlgorithmInitializationException(AlgorithmMOEA algorithm, 
			Throwable cause) {
		super(algorithm, cause);
	}

	/**
	 * Constructs an algorithm initialization exception originating from the 
	 * specified algorithm.
	 * 
	 * @param algorithm the algorithm responsible for this exception
	 */
	public AlgorithmInitializationException(AlgorithmMOEA algorithm) {
		super(algorithm);
	}

}
