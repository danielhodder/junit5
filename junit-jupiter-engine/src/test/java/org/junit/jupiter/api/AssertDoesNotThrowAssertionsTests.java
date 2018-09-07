/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.api;

import static org.junit.jupiter.api.AssertionTestUtils.assertMessageEquals;
import static org.junit.jupiter.api.AssertionTestUtils.expectAssertionFailedError;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.FutureTask;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.opentest4j.AssertionFailedError;

/**
 * Unit tests for JUnit Jupiter {@link Assertions}.
 *
 * @since 5.2
 */
class AssertDoesNotThrowAssertionsTests {

	private static final Executable nix = () -> {
	};

	private static final ThrowingSupplier<String> something = () -> "enigma";

	@Test
	void assertDoesNotThrowWithFutureMethodReference() {
		FutureTask<String> future = new FutureTask<>(() -> {
			return "foo";
		});
		future.run();

		String result;

		// Current compiler's type inference
		result = assertDoesNotThrow(future::get);
		assertEquals("foo", result);

		// Explicitly as an Executable
		assertDoesNotThrow((Executable) future::get);

		// Explicitly as a ThrowingSupplier
		result = assertDoesNotThrow((ThrowingSupplier<String>) future::get);
		assertEquals("foo", result);
	}

	// --- executable ----------------------------------------------------------

	@Test
	void assertDoesNotThrowAnythingWithExecutable() {
		assertDoesNotThrow(nix);
	}

	@Test
	void assertDoesNotThrowAnythingWithExecutableAndMessage() {
		assertDoesNotThrow(nix, "message");
	}

	@Test
	void assertDoesNotThrowAnythingWithExecutableAndMessageSupplier() {
		assertDoesNotThrow(nix, () -> "message");
	}

	@Test
	void assertDoesNotThrowWithExecutableThatThrowsACheckedException() {
		try {
			assertDoesNotThrow((Executable) () -> {
				throw new IOException();
			});
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "Unexpected exception thrown: " + IOException.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithExecutableThatThrowsARuntimeException() {
		try {
			assertDoesNotThrow((Executable) () -> {
				throw new IllegalStateException();
			});
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "Unexpected exception thrown: " + IllegalStateException.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithExecutableThatThrowsAnError() {
		try {
			assertDoesNotThrow((Executable) AssertionTestUtils::recurseIndefinitely);
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "Unexpected exception thrown: " + StackOverflowError.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithExecutableThatThrowsAnExceptionWithMessageString() {
		try {
			assertDoesNotThrow((Executable) () -> {
				throw new IllegalStateException();
			}, "Custom message");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex,
				"Custom message ==> Unexpected exception thrown: " + IllegalStateException.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithExecutableThatThrowsAnExceptionWithMessageSupplier() {
		try {
			assertDoesNotThrow((Executable) () -> {
				throw new IllegalStateException();
			}, () -> "Custom message");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex,
				"Custom message ==> Unexpected exception thrown: " + IllegalStateException.class.getName());
		}
	}

	// --- supplier ------------------------------------------------------------

	@Test
	void assertDoesNotThrowAnythingWithSupplier() {
		assertEquals("enigma", assertDoesNotThrow(something));
	}

	@Test
	void assertDoesNotThrowAnythingWithSupplierAndMessage() {
		assertEquals("enigma", assertDoesNotThrow(something, "message"));
	}

	@Test
	void assertDoesNotThrowAnythingWithSupplierAndMessageSupplier() {
		assertEquals("enigma", assertDoesNotThrow(something, () -> "message"));
	}

	@Test
	void assertDoesNotThrowWithSupplierThatThrowsACheckedException() {
		try {
			assertDoesNotThrow((ThrowingSupplier<?>) () -> {
				throw new IOException();
			});
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "Unexpected exception thrown: " + IOException.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithSupplierThatThrowsARuntimeException() {
		try {
			assertDoesNotThrow((ThrowingSupplier<?>) () -> {
				throw new IllegalStateException();
			});
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "Unexpected exception thrown: " + IllegalStateException.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithSupplierThatThrowsAnError() {
		try {
			assertDoesNotThrow((ThrowingSupplier<?>) () -> {
				throw new StackOverflowError();
			});
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "Unexpected exception thrown: " + StackOverflowError.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithSupplierThatThrowsAnExceptionWithMessageString() {
		try {
			assertDoesNotThrow((ThrowingSupplier<?>) () -> {
				throw new IllegalStateException();
			}, "Custom message");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex,
				"Custom message ==> Unexpected exception thrown: " + IllegalStateException.class.getName());
		}
	}

	@Test
	void assertDoesNotThrowWithSupplierThatThrowsAnExceptionWithMessageSupplier() {
		try {
			assertDoesNotThrow((ThrowingSupplier<?>) () -> {
				throw new IllegalStateException();
			}, () -> "Custom message");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex,
				"Custom message ==> Unexpected exception thrown: " + IllegalStateException.class.getName());
		}
	}

}
