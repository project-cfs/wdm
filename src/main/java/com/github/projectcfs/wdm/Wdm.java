package com.github.projectcfs.wdm;

import com.github.projectcfs.wdm.antlr.WdmParser;
import com.github.projectcfs.wdm.lang.Parser;
import com.github.projectcfs.wdm.lang.Transpiler;
import org.slf4j.helpers.MessageFormatter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Command(name = "wdm", mixinStandardHelpOptions = true, version = "wdm 0.1", description = "WDM language transpiler")
public class Wdm implements Callable<Integer> {

	@Parameters(description = "WDM source files to be transpiled")
	private List<Path> sources;

	@Option(names = {"-o", "--output"}, description = "Output file directory")
	private Path dest;

	@Option(names = {"-q", "--quiet"}, defaultValue = "false", description = "Suppress command output")
	private Boolean quiet;

	public static void main(String... args) {
		System.exit(new CommandLine(new Wdm()).execute(args));
	}

	@Override
	public Integer call() {
		try {
			if (sources == null) throw new IllegalStateException("No source files provided");
			for (var s : sources) {
				log("Parsing source file '{}'", s);
				WdmParser parser = new Parser().parse(s);
				log("Resolving destination path");
				Path destinationPath = resolveDestPath(s, dest);
				log("Transpiling into '{}'", destinationPath);
				new Transpiler(parser).transpile(destinationPath);
				log("Transpiled to output file '{}'", destinationPath);
			}
			return 0;
		} catch (Exception e) {
			String message = e.getClass().getName() + ": " + e.getMessage();
			System.err.println(Ansi.AUTO.string("@|bold,red " + message + "|@"));
			return 1;
		}
	}

	private Path resolveDestPath(Path source, Path dest) {
		String sourceName = source.getFileName().toString();
		String destName = sourceName.substring(0, sourceName.lastIndexOf('.')) + ".html";
		return dest != null ? dest.resolve(destName) : Paths.get(destName);
	}

	private void log(String message, Object... args) {
		if (!quiet) {
			System.out.println(MessageFormatter.arrayFormat(message, args).getMessage());
		}
	}

}
