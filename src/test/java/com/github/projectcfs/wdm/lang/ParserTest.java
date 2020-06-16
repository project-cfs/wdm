package com.github.projectcfs.wdm.lang;

import com.github.projectcfs.antlr.WdmParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest extends LangTest {

	@Test
	void shouldParse1() throws IOException {
		assertEquals("file", getParseTree(getSourcePath(1)));
	}

	@Test
	void shouldParse2() throws IOException {
		assertEqualsFormatted(
				getTree(2),
				getParseTree(getSourcePath(2))
		);
	}

	@Test
	void shouldParse3() throws IOException {
		assertEqualsFormatted(
				getTree(3),
				getParseTree(getSourcePath(3))
		);
	}

	@Test
	void shouldParse4() throws IOException {
		assertEqualsFormatted(
				getTree(4),
				getParseTree(getSourcePath(4))
		);
	}

	private String getParseTree(Path sourcePath) throws IOException {
		WdmParser wdmParser = new Parser().parse(sourcePath);
		return wdmParser.file().toStringTree(wdmParser);
	}

	private String getTree(int n) throws IOException {
		return Files.readString(getTreePath(n));
	}

	private void assertEqualsFormatted(String expected, String actual) {
		assertEquals(format(expected), format(actual));
	}

	private String format(String s) {
		return s.replaceAll("[ \n]*", "");
	}

}