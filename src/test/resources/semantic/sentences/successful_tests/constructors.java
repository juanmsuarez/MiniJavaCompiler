class Main {
	static void main() {
	
	}
}

class A {
	public int a1;
	private int a2;

	dynamic int f() {
		new A().a1 = 1;
		new A().f();
	}
}

class B extends A {
	public char a2;

	B(int x) {
		new B(1).a1 = 1;
		new B(f()).f();
		new B(2).f();
		new A();
	}
}