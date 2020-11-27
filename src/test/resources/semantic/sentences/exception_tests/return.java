class Main {
	static void main() {
	
	}
}

class Test {
	Test() {
		return 1;
		return f();
		return null;		
		return;
	}

	dynamic void f() {
		return 1;
		return true;
		return new Object();
		return f();
	}

	dynamic Object g() {
		return 1 + 2;
		return;
		return 'a';
		return new Object();
		return null;
		return new Test();
	}	

	dynamic int h() {
		return 1;
		return true;
		return new Object();
		return;	
	}
}