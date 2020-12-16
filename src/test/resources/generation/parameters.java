class B {
    public int x;

    B() {
        x = 1;
    }
}
class A {
    static void f(int i, char c, B b) {
        System.printIln(i);
        System.printCln(c);
        System.printCln(b.x);
    }

    dynamic void g(String s, boolean x) {
        System.printSln(s);
        System.printBln(x);
    }
}

class Main {
    static void main() {
        new A().f();

        new A().g();
    }
}
