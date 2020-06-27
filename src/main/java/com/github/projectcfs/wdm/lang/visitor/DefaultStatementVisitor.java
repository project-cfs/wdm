package com.github.projectcfs.wdm.lang.visitor;

import com.github.projectcfs.wdm.antlr.WdmParser;

public class DefaultStatementVisitor implements StatementVisitor {

	@Override
	public String visitStatement(Visitor visitor, WdmParser.DirectiveContext directive, WdmParser.TextContext text) {
		return String.format(
				visitor.propertyLoader.get("template.statement"),
				visitor.visitDirectiveName(directive.directiveName()),
				visitor.visitText(text)
		);
	}

}
