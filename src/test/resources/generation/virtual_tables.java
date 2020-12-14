class A {
    static void f1() { }

    static void f2() {}

    dynamic void f3() {}

    dynamic void f4() {}
}

class B extends A {
    static void f1() {}

    dynamic void f3() {}

    dynamic void f5() {}
}

class C extends B {
    static void f2() {}

    dynamic void f4() {}

    dynamic void f5() {}

    dynamic void f6() {}
}

class D {}

class E {
    dynamic void f() {}
}

class Main {
	static void main() {
	}
}