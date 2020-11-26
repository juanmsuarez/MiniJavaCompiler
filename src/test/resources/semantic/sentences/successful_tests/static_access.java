class Main {
	static void main() {
	
	}
}

class A {
	public static int a1;
	public static int a2;

	static int f() {
		a1 = 1;	
		A.a1 = 1;
		static A.a2 = 2;
		static A.a2 = 2;
		static A.f();
	}

	dynamic void g() {
		a1 = 1;
		A.a1 = 1;
		static A.a2 = 2;
		static A.a2 = 2;
		static A.f();
	}
}

class B {
	static int f() {
		A.a1 = 1;
		static A.a2 = 2;
		static A.a2 = 2;
		static A.f();
	}

	dynamic void g() {
		A.a1 = 1;
		static A.a2 = 2;
		static A.a2 = 2;
		static A.f();
	}
}