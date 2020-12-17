class A {
    public B b;

    A() {
        b = new B();
    }
}

class B {
    public int x;

    B() {
        x = 1;
    }

    dynamic A f() {
        return new A();
    }
}

class Main {
    static B g() {
        return new B();
    }

    static void main() {
        A a; a = new A();
        System.printIln(a.b.f().b.f().b.x);

        a.b.x = 2;
        System.printIln(a.b.x);

        System.printIln(g().f().b.x);
    }
}
