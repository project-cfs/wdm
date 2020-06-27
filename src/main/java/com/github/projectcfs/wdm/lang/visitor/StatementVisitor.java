package com.github.projectcfs.wdm.lang.visitor;

import com.github.projectcfs.wdm.antlr.WdmParser;

public interface StatementVisitor {

	String visitStatement(Visitor visitor, WdmParser.DirectiveContext directive, WdmParser.TextContext text);

}
