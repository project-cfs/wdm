package com.github.projectcfs.wdm.lang;

import com.github.projectcfs.antlr.WdmParser;
import org.antlr.v4.runtime.misc.Interval;

import java.util.stream.Collectors;

public class Visitor extends com.github.projectcfs.antlr.WdmBaseVisitor<String> {

	private final WdmParser parser;

	public Visitor(WdmParser parser) {
		this.parser = parser;
	}

	public String visit() {
		return visitFile(parser.file());
	}

	public String visitFile(WdmParser.FileContext file) {
		if (file.children == null) return "";

		return file.children
				.stream()
				.map(statement -> visitStatement((WdmParser.StatementContext) statement))
				.collect(Collectors.joining("\n"));
	}

	public String visitStatement(WdmParser.StatementContext statement) {
		return String.format(
				"<div class='%s'>%s</div>\n",
				visitDirective(statement.directive()),
				visitText(statement.text())
		);
	}

	public String visitDirective(WdmParser.DirectiveContext directive) {
		return visitDirectiveName(directive.directiveName());
	}

	public String visitWrapDirective(WdmParser.WrapDirectiveContext wrapDirective) {
		return String.format(
				"<div class='%s'>%s</div>",
				visitDirective(wrapDirective.directive()),
				visitText(wrapDirective.text())
		);
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
			return visitWrapDirective(richText.wrapDirective());
		} else {
			return content(richText);
		}
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