class Main {
	static void main() {
	
	}
}

class A {
	public int a1;
	private int a2;

	dynamic int f() {
		new A().a1 = 1;
		new A().a2 = 1;
		new A().f();
	}
}

class B extends A {
	public char a2;

	B(int x) {
		new C();
		new B().a1 = 1;
		new B(1).f();
		new B(1, 2).f();
		new A();
	}
}