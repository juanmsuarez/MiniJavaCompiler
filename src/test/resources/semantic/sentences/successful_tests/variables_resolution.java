class Main {
	static void main() {
	
	}
}

class A {
	private static int a1;
	public static char a2;

	dynamic int a1(char a1) {
		a1 = 'a';

		int a2;
		a2 = 3;
	}

	static B a2() {
		a2 = 'a';	
	}
}

class B extends A {
	dynamic int b1() {
		a2 = 'a';
	}	
}

class C extends A {
	public int a1;

	dynamic int c1() {
		a1 = 1;
	}	
}