package com.github.projectcfs.wdm.lang;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranspilerTest extends LangTest {

	@Test
	void shouldTranspile1() throws IOException {
		assertEquals(
				getTranspile(getTranspilePath(1)),
				new Transpiler(new Parser().parse(getSourcePath(1))).transpile()
		);
	}

	@Test
	void shouldTranspile2() throws IOException {
		assertEquals(
				getTranspile(getTranspilePath(2)),
				new Transpiler(new Parser().parse(getSourcePath(2))).transpile()
		);
	}

	@Test
	void shouldTranspile3() throws IOException {
		assertEquals(
				getTranspile(getTranspilePath(3)),
				new Transpiler(new Parser().parse(getSourcePath(3))).transpile()
		);
	}

	@Test
	void shouldTranspile4() throws IOException {
		assertEquals(
				getTranspile(getTranspilePath(4)),
				new Transpiler(new Parser().parse(getSourcePath(4))).transpile()
		);
	}

	private String getTranspile(Path transpilePath) throws IOException {
		return Files.readString(transpilePath);
	}

}