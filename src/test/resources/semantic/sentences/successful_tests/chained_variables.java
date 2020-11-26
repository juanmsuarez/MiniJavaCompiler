class Main {
	static void main() {
	
	}
}

class A {
	public int a1;
	public A a2;
	public static int a3;

	dynamic int a1(char a1, B x) {
		this.a1 = 1;
		this.a2 = this;
	}

	static B a2() {
		new B().a1 = 'a';
		new B().b2.b2.b2 = new B();
		new B().b1 = 1;	
	}
}

class B extends A {
	public char a1;
	public int b1;
	public B b2;

	static A b1(int b2) {
		b2 = 1;
	}

	dynamic B b2() {
		b2.b2.a2.a2 = new A();	
		this.a1 = 'a';
		this.b1 = 1;
	}

	dynamic void b3(B par1) {
		new A().a2.a2.a1 = 1;
		new A().a1 = 1;
		new A().a1 = 1;
		par1.a2.a2 = new A();
	}	
}