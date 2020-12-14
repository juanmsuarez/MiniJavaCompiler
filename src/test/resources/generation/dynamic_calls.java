class A {
    static void f1() {
        System.printSln("A.f1()");
    }

    static void f2() {
        System.printSln("A.f2()");
    }
}

class B extends A {
    static void f1() {
        System.printSln("B.f1()");
    }
    static void f3() {
        System.printSln("B.f3()");
    }
}

class C extends B {
    static void f2() {
        System.printSln("C.f2()");
    }
    static void f4() {
        System.printSln("C.f4()");
    }
}

class D {
    static void f() {
        System.printSln("D.f()");
    }
}

class Main {
    static void main() {
        A.f1();
        A.f2();
        B.f1();
        B.f2();
        C.f2();
        D.f();

        new B().f1();
        new C().f3();
        new D().f();
    }
}
