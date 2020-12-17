class A {
    static void f() {

    }

    dynamic void g() {
        if (true) {
            int a;
            a = 1;
            System.printIln(a);
        }

        int x; x = 0;
        while (x < 2) {
            int a;
            a = 2;

            if (x < 1) {
                int y; y = 3;
                System.printIln(y);
            }

            System.printIln(a);
            x = x + 1;

        }

        int a, y; a = 4; y = 5;
        System.printIln(a);
        System.printIln(y);

        char c; c = 'a';
        System.printCln(c);
    }
}

class B extends A {
    static void f() {
        if (false) {
            int a, b, c;
            a = 1; b = 2; c = 3;
        }
    }

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
        b.f();
        b.g();
    }
}
