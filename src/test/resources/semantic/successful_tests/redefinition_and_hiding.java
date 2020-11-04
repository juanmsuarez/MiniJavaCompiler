class A {
	private int x;
	public int y;

	static void main() {}

	static String f(int x, char y) {}
}

class B extends A {
	public char x;

	static String f(int y, char x) {}
}

class C extends B {
	private char x;
	private char y;
}

class D extends C {
	dynamic void h() {}
}

class E extends D {
	static String f(int a, char b) {}
}