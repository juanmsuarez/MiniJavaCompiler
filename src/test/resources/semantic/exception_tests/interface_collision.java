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

class E implements A, B {}
class F implements A, C {}
class G implements A, D {}

interface H extends A, B {}
interface I extends A, C {}
interface J extends A, D {}