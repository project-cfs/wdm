package com.github.projectcfs.wdm.lang;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class LangTest {

	public Path getSourcePath(int n) {
		return Paths.get("src", "test", "resources", "source", n + ".wdml");
	}

	public Path getTranspilePath(int n) {
		return Paths.get("src", "test", "resources", "transpile", n + ".html");
	}

}
