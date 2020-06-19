grammar Wdm;

@header {
    package com.github.projectcfs.antlr;
}

file
    : statement*
    ;

statement
    : directive text?
    | wrapDirective
    ;

directive
    : Backslash directiveName
    ;

wrapDirective
    : directive OCurlyBrace text? CCurlyBrace
    ;

directiveName
    : Word
    ;

text
    : richText+
    ;

richText
    : wrapDirective
    | Word+
    ;

EmptyLine
    : Nl (Nl | EOF) -> skip
    ;

Backslash
    : '\\'
    ;

OCurlyBrace
    : '{'
    ;

CCurlyBrace
    : '}'
    ;

Word
    : ([a-zA-Z0-9\-!$%^&*()_+|~=`[\]:";'<>?,./])+
    | '\n'
    ;

Nl
    : [\n]
    ;

Ws
   : [ \t]+ -> skip
   ;
