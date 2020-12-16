class A {
    static void f() {

    }

    dynamic void g() {
        int a; a = 1;
        System.printIln(a);

        char c; c = 'a';
        System.printCln(c);
    }
}

class B extends A {
    dynamic void g() {
        boolean b; b = true;
        System.printBln(b);
    }
}

class Main {
    static void main() {
        int x, y;
        x = 1; y = 2;
        System.printIln(x);
        System.printIln(y);

        A a; a = new A();
        a.f();
        a.g();

        A b; b = new B();
        b.g();
    }
}
