package com.github.projectcfs.wdm.lang.visitor;

import com.github.projectcfs.wdm.antlr.WdmBaseVisitor;
import com.github.projectcfs.wdm.antlr.WdmParser;
import org.antlr.v4.runtime.misc.Interval;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractDirectiveVisitor extends WdmBaseVisitor<String> implements DirectiveVisitor {

	private static final Path TEMPLATE_PATH = Paths.get("src", "main", "resources", "file-template.txt");
	private static final String STYLE_TEMPLATE = "<link href='%s' rel='stylesheet'>";

	private final WdmParser parser;
	private final Map<String, DirectiveVisitor> directiveVisitorMap;
	private final DirectiveVisitor defaultDirectiveVisitor;

	public AbstractDirectiveVisitor(WdmParser parser) {
		this(parser, Collections.emptyMap());
	}

	public AbstractDirectiveVisitor(WdmParser parser, Map<String, DirectiveVisitor> directiveVisitorMap) {
		this.parser = parser;
		this.directiveVisitorMap = directiveVisitorMap;
		this.defaultDirectiveVisitor = new DefaultDirectiveVisitor();
	}

	@Override
	public String visitDirective(AbstractDirectiveVisitor visitor, WdmParser.DirectiveContext directive) {
		return defaultDirectiveVisitor.visitDirective(this, directive);
	}

	@Override
	public String visitWrapDirective(AbstractDirectiveVisitor visitor, WdmParser.WrapDirectiveContext wrapDirective) {
		return defaultDirectiveVisitor.visitWrapDirective(this, wrapDirective);
	}

	public String visit() {
		return visitFile(parser.file());
	}

	public String visitFile(WdmParser.FileContext file) {
		file.children = file.children == null ? Collections.emptyList() : file.children;

		String body = file.children
				.stream()
				.map(statement -> (WdmParser.StatementContext) statement)
				.filter(statement -> statement.directive() != null)
				.map(this::visitStatement)
				.collect(Collectors.joining("\n"));

		String styles = file.children
				.stream()
				.map(statement -> (WdmParser.StatementContext) statement)
				.filter(statement -> statement.wrapDirective() != null)
				.map(WdmParser.StatementContext::wrapDirective)
				.filter(wrapDirective -> visitDirective(this, wrapDirective.directive()).equals("style"))
				.map(this::visitStyleStatement)
				.map(line -> line.concat("\n"))
				.collect(Collectors.joining());


		try {
			return String.format(
					Files.readString(TEMPLATE_PATH),
					styles,
					body
			);
		} catch (IOException e) {
			throw new RuntimeException("Unable to locate template file.", e);
		}
	}

	public String visitStatement(WdmParser.StatementContext statement) {
		if (statement.directive() != null) {
			return String.format(
					"<div class='%s'>%s</div>\n",
					visitDirective(this, statement.directive()),
					statement.text() != null ? visitText(statement.text()) : ""
			);
		}
		if (statement.wrapDirective() != null) {
			return visitWrapDirective(this, statement.wrapDirective());
		}

		throw new IllegalArgumentException("syntax error");
	}

	public String visitDirectiveName(WdmParser.DirectiveNameContext directiveName) {
		return directiveName.Word().getText();
	}

	public String visitText(WdmParser.TextContext text) {
		return text.children
				.stream()
				.map(richText -> visitRichText((WdmParser.RichTextContext) richText))
				.collect(Collectors.joining());
	}

	public String visitRichText(WdmParser.RichTextContext richText) {
		if (richText.wrapDirective() != null) {
			return visitWrapDirective(this, richText.wrapDirective());
		} else {
			return content(richText);
		}
	}

	private String visitStyleStatement(WdmParser.WrapDirectiveContext wrapDirective) {
		return String.format(STYLE_TEMPLATE, visitText(wrapDirective.text()));
	}

	/**
	 * Guide: https://stackoverflow.com/a/26533266/8662097
	 */
	public String content(WdmParser.RichTextContext str) {
		if (str.start == null || str.stop == null || str.start.getStartIndex() < 0 || str.stop.getStopIndex() < 0)
			return str.getText();

		return str.start.getInputStream().getText(Interval.of(str.start.getStartIndex(), str.stop.getStopIndex()));
	}

}
