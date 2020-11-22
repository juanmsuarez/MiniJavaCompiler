package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class IfElseSentence extends Sentence {
    private Expression condition;
    private Sentence mainBody;
    private Sentence elseBody;

    public IfElseSentence(Lexeme lexeme) { // TODO: CONSULTA ver dónde se muestra el error (lexema)
        super(lexeme);
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public void setMainBody(Sentence mainBody) {
        this.mainBody = mainBody;
    }

    public void setElseBody(Sentence elseBody) {
        this.elseBody = elseBody;
    }

    @Override
    public String toString() {
        return "\nIfElseSentence{" +
                "condition=" + condition +
                ", mainBody=" + mainBody +
                ", elseBody=" + elseBody +
                '}';
    }
}
