package com.github.projectcfs.wdm.lang;

import com.github.projectcfs.wdm.antlr.WdmLexer;
import com.github.projectcfs.wdm.antlr.WdmParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.antlr.v4.runtime.CharStreams.fromStream;

public class Parser {

	public WdmParser parse(@NotNull String source) {
		try {
			return new WdmParser(
					new CommonTokenStream(
							new WdmLexer(
									fromStream(new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)))
							)
					)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public WdmParser parse(@NotNull Path sourcePath) throws IOException {
		return parse(Files.readString(sourcePath));
	}

}
