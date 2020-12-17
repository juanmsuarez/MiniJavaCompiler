class A {
    dynamic void f1() {
        System.printSln("A.f1()");
    }

    static void f2() {
        System.printSln("A.f2()");
    }

    dynamic void f4() {
        System.printSln("A.f4()");
    }
}

class B extends A {
    dynamic void f1() {
        System.printSln("B.f1()");
        f4();
    }
    static void f3() {
        System.printSln("B.f3()");
        f2();
    }
}

class C extends B {
    C() {
        f1();
        System.println();
        f2();
        System.println();
        f3();
        System.println();
        f4();
        System.println();
    }

    static void f2() {
        System.printSln("C.f2()");
    }
    dynamic void f4() {
        System.printSln("C.f4()");
    }
}

class Main {
    static void main() {
        new C();
    }
}
