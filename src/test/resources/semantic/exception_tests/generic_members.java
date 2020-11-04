class Main { static void main() {} }

class B<K> {}
class Integer {}

class A<K> {
	private T x2;
	private static K y1;
	private static B<K> y2;

	private B<B> z;

	static K h1() {}
	static void h2(K x) {}

	dynamic void f1(T p2, B p3, B<B> p5) {}
	dynamic void f2(Integer<B> p1, Integer<Integer> p2) {}
	dynamic void f3(K<Integer> p1) {}

	dynamic T g2() {}
	dynamic B g3() {}
	dynamic B<B> g5() {}	
	dynamic B<A> g6() {}	
}