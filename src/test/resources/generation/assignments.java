class A {
    public B b;

    A() {
        b = new B();
    }

    dynamic String f() {
        return "A.f";
    }
}

class B extends A {
    public int x;

    dynamic String f() {
        return "B.f";
    }
}

class Main {
    static void main() {
        A x;

        x = new A();
        System.printSln(x.f());

        x.b.x = 3 * 2 + 6;
        System.printIln(x.b.x);

        x.b.x += 6 + 5 * 3;
        System.printIln(x.b.x);

        x.b.x -= 4;
        System.printIln(x.b.x);

        x = new B();
        System.printSln(x.f());
    }
}
