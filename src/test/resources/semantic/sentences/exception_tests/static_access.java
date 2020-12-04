class Main {
	static void main() {
	
	}
}

class A {
	public int a1;
	private int a2;

	dynamic int f() {
		a1 = 1;	
		A.a1 = 1;
		static A.a2 = 2;
		static A.f();
		A.f();
		static A.g();
		A.g();
		static C.f();
		C.f();
	}
}

class B {
	static int f() {
		A.a1 = 1;
		static A.a2 = 2;
		static A.f();
		A.f();
		static C.f();
		C.f();
	}
}