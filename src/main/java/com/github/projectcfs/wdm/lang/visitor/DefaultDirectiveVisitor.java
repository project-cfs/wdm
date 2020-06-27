package com.github.projectcfs.wdm.lang.visitor;

import com.github.projectcfs.wdm.antlr.WdmParser;

public class DefaultDirectiveVisitor implements DirectiveVisitor {

	@Override
	public String visitDirective(AbstractDirectiveVisitor visitor, WdmParser.DirectiveContext directive) {
		return visitor.visitDirectiveName(directive.directiveName());
	}

	@Override
	public String visitWrapDirective(AbstractDirectiveVisitor visitor, WdmParser.WrapDirectiveContext wrapDirective) {
		return String.format(
				"<div class='%s'>%s</div>",
				visitDirective(visitor, wrapDirective.directive()),
				visitor.visitText(wrapDirective.text())
		);
	}

}
