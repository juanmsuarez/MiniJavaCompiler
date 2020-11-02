class A {
	private int x;
	public int y;
}

class B extends A {
	public char x;
	static void f() {}
}

class C extends B {
	private char x;
	private char y;
	static int f() {}
}

