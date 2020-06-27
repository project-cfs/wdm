package com.github.projectcfs.wdm.lang;

import com.github.projectcfs.wdm.antlr.WdmParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest extends LangTest {

	@Test
	void shouldParse1() throws IOException {
		assertNoErrors(1);
	}

	@Test
	void shouldParse2() throws IOException {
		assertNoErrors(2);
	}

	@Test
	void shouldParse3() throws IOException {
		assertNoErrors(3);
	}

	@Test
	void shouldParse4() throws IOException {
		assertNoErrors(4);
	}

	private void assertNoErrors(int i) throws IOException {
		assertEquals(0, getParser(getSourcePath(i)).getNumberOfSyntaxErrors());
	}

	private WdmParser getParser(Path sourcePath) throws IOException {
		return new Parser().parse(sourcePath);
	}

}