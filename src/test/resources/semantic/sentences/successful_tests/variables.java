class Main {
	static void main() {
	
	}
}

class A {
	private int a1;
	public A a2;
	private static int a3;

	dynamic int a1(char a1, B x) {
		a1 = 'a';
		a3 = 1;
		a3 = 3;
		x = a2();
	}

	static B a2() {
		a3 = 1;		
	}
}

class B extends A {
	public char a1;
	private int b1;
	public B b2;

	static A b1(int b2) {
		b2 = 1;
	}

	dynamic B b2() {
		a1 = 'a';
		int b2;
		b1 = 1;
		b2 = 1;
		a2 = new A();
	}

	dynamic void b3(B par1) {
		par1 = this;
		int a1;
		a1 = 1;
	}	
}