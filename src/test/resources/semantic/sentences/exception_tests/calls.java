class Main {
	static void main() {
	
	}
}

class A {
	public A x;
	public int y; 
	
	dynamic A f() {
		f;
		f();		
		x;
		new A();
		new C();
		static A.g();
		A.g();
		g();
		h();		

		x.x.f();
		x.x.g();
		x.x.y; 
	}	

	static void g() {}
}

class B extends A {
		
}