class A {
    A(int x) {
        System.printSln("A()");
        System.printIln(x);
    }

    dynamic void f() {
        System.printSln("A.f()");
    }
}

class B extends A {
    B(char c) {
        System.printSln("B()");
        System.printCln(c);
    }

    dynamic void f() {
        System.printSln("B.f()");
    }
}

class C {
    C(char a, char b, char c) {
        System.printSln("C()");
        System.printCln(a);
        System.printCln(b);
        System.printCln(c);
    }

    dynamic void f() {
        System.printSln("C.f()");
    }
}

class D extends C {
    dynamic void f() {
        System.printSln("D.f()");
    }
}

class Main {
    static void main() {
        new A(2).f();
        new B('a').f();
        new C('a', 'b', 'c').f();
        new D().f();
    }
}
