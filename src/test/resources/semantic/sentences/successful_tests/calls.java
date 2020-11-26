class Main {
	static void main() {
	
	}
}

class A {
	public A x;
	public int y; 
	
	dynamic A f() {
		f();	
		new A();
		static A.g();
		A.g();
		g();		

		x.x.f();
		x.x.g();
	}	

	static void g() {}
}

class B extends A {
		
}