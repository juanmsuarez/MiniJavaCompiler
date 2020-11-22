class Main { static void main() {} }


interface A extends B, C {
	static void h();
}
interface B {
	static void f(int x);
}
interface C {
	static void g(A a);
}
interface D extends C {
	
}
interface E {
	static void g(A a);
}

class F implements C, E {
	static void g(A a) {

	}
}
class G implements D, E {
	static void g(A a) {

	}
}
class H implements A, B {
	static void g(A a) {

	}

	static void h() {

	}

	static void f(int y) {

	}
}