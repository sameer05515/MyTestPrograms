// 
// Decompiled by Procyon v0.5.36
// 

package com.prem.vocab.build.proj;

public enum VocabBuildConstants implements XMLBasicMethodsRequired
{
    ROOT("ROOT", 0, "vocab-config", Type.NODE), 
    WORD_LIST("WORD_LIST", 1, "word-list", Type.NODE), 
    MY_WORD("MY_WORD", 2, "myword", Type.NODE);
    
    private String name;
    private Type type;
    
    private VocabBuildConstants(final String s, final int n, final String name, final Type type) {
        this.name = name;
        this.type = type;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Type getType() {
        return this.type;
    }
    
    public enum Examples implements XMLBasicMethodsRequired
    {
        node("node", 0, "examples", Type.NODE), 
        EXAMPLE("EXAMPLE", 1, "example", Type.NODE);
        
        private String name;
        private Type type;
        
        private Examples(final String s, final int n, final String name, final Type type) {
            this.name = name;
            this.type = type;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public Type getType() {
            return this.type;
        }
    }
    
    public enum Meanings implements XMLBasicMethodsRequired
    {
        node("node", 0, "meanings", Type.NODE), 
        MEANING("MEANING", 1, "meaning", Type.NODE);
        
        private String name;
        private Type type;
        
        private Meanings(final String s, final int n, final String name, final Type type) {
            this.name = name;
            this.type = type;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public Type getType() {
            return this.type;
        }
    }
    
    public enum Type
    {
        NODE("NODE", 0, "node"), 
        ATTRIBUTE("ATTRIBUTE", 1, "attribute"), 
        COMMENT("COMMENT", 2, "comment"), 
        CDATA("CDATA", 3, "cdata"), 
        SUB_NODE("SUB_NODE", 4, "subNode");
        
        private String name;
        
        private Type(final String s, final int n, final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public enum Word implements XMLBasicMethodsRequired
    {
        node("node", 0, "word", Type.NODE), 
        TYPE("TYPE", 1, "type", Type.ATTRIBUTE);
        
        private String name;
        private Type type;
        
        private Word(final String s, final int n, final String name, final Type type) {
            this.name = name;
            this.type = type;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public Type getType() {
            return this.type;
        }
    }
}
