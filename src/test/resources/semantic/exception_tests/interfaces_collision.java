class Main { static void main() {} }

interface A {
	static void f();
}
interface B {
	dynamic void f();	
}
interface C {
	static void f();
}
interface D extends B {
}

class E implements A, B {
	static void f() {}
}
class F implements A, C {
	static void f() {}
}
class G implements A, D {
	static void f() {}
}

interface H extends A, B {}
interface I extends A, C {}
interface J extends A, D {}