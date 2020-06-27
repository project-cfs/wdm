package com.github.projectcfs.wdm.lang.visitor;

import com.github.projectcfs.wdm.antlr.WdmParser;

public interface DirectiveVisitor {

	String visitDirective(AbstractDirectiveVisitor visitor, WdmParser.DirectiveContext directive);

	String visitWrapDirective(AbstractDirectiveVisitor visitor, WdmParser.WrapDirectiveContext wrapDirective);

}
