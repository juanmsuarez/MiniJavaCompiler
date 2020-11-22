package com.minijava.compiler.semantic.sentences.asts;

public interface ChainedAccess { // TODO: abstract class or interface? prob interface
    void setChainedAccess(ChainedAccess nextChainedAccess);
}
