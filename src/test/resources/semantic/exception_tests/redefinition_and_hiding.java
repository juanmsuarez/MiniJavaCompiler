class A {
	private int x;
	public int y;

	static void main() {}

	static String f(int x, char y) {}
}

class B extends A {
	public char x;

	dynamic void main() {}
	static String f(int y, char x) {}
}

class C extends B {
	private char x;
	private char y;

	static int main() {}
	static String f(int y, String x) {}
}

class D extends C {
	static void main(int x) {}
}

class E extends D {
	dynamic void main(int x) {}
}