package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.generation.PredefinedBlocks;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.*;

public class PredefinedEntities { // TODO: controlar uso de system y object (new, herencia...)
    public static final Class OBJECT = new Class("Object") {{
        add(new Constructor("Object"));
    }};

    public static final Class SYSTEM = new Class("System", "Object") {{
        add(new Constructor("System"));

        add(new Method(Form.STATIC, new IntType(), "read") {{
            setBlock(new PredefinedBlocks.SystemRead());
        }});

        add(new Method(Form.STATIC, new VoidType(), "printB", new Parameter(new BooleanType(), "b")) {{
            setBlock(new PredefinedBlocks.SystemPrintB());
        }});
        add(new Method(Form.STATIC, new VoidType(), "printC", new Parameter(new CharType(), "c")) {{
            setBlock(new PredefinedBlocks.SystemPrintC());
        }});
        add(new Method(Form.STATIC, new VoidType(), "printI", new Parameter(new IntType(), "i")) {{
            setBlock(new PredefinedBlocks.SystemPrintI());
        }});
        add(new Method(Form.STATIC, new VoidType(), "printS", new Parameter(new StringType(), "s")) {{
            setBlock(new PredefinedBlocks.SystemPrintS());
        }});

        add(new Method(Form.STATIC, new VoidType(), "println") {{
            setBlock(new PredefinedBlocks.SystemPrintln());
        }});

        add(new Method(Form.STATIC, new VoidType(), "printBln", new Parameter(new BooleanType(), "b")) {{
            setBlock(new PredefinedBlocks.SystemPrintBln());
        }});
        add(new Method(Form.STATIC, new VoidType(), "printCln", new Parameter(new CharType(), "c")) {{
            setBlock(new PredefinedBlocks.SystemPrintCln());
        }});
        add(new Method(Form.STATIC, new VoidType(), "printIln", new Parameter(new IntType(), "i")) {{
            setBlock(new PredefinedBlocks.SystemPrintIln());
        }});
        add(new Method(Form.STATIC, new VoidType(), "printSln", new Parameter(new StringType(), "s")) {{
            setBlock(new PredefinedBlocks.SystemPrintSln());
        }});
    }};

    public static final Method MAIN = new Method(Form.STATIC, new VoidType(), "main");
}
