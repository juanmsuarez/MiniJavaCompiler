class A {
    static void f() {

    }

    dynamic void g() {
        int a; a = 1;

        char c; c = 'a';
    }
}

class B extends A {
    dynamic void g() {
        boolean b; b = true;
    }
}

class Main {
    static void main() {
        int x, y;
        x = 1; y = 2;

        A a; a = new A();
        a.f();
        a.g();

        A b; b = new B();
        b.g();
    }
}
