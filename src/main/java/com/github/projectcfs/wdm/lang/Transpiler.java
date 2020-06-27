package com.github.projectcfs.wdm.lang;

import com.github.projectcfs.wdm.antlr.WdmParser;
import com.github.projectcfs.wdm.lang.visitor.AbstractDirectiveVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Transpiler {

	private final WdmParser parser;

	public Transpiler(WdmParser parser) {
		this.parser = parser;
	}

	public String transpile() {
		return new AbstractDirectiveVisitor(parser).visit();
	}

	public void transpile(Path destinationPath) throws IOException {
		Files.writeString(destinationPath, transpile());
	}

}
