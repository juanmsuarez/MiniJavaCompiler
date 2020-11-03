class B<K> {}
class Integer {}

class A<K> {
	public K x1;
	private T x2;

	private B<B> y;
	private static B<A> z;

	private static K x;
	static K f() {}
	static void f(K x) {}

	dynamic void f1(K p1, T p2, B p3, B<K> p4, B<B> p5) {}
	dynamic void f2(Integer<B> p1, Integer<Integer> p2) {}
	dynamic void f3(K<Integer> p1, B<Integer> p2) {}

	dynamic K g1() {}
	dynamic T g2() {}
	dynamic B g3() {}
	dynamic B<K> g4() {}
	dynamic B<B> g5() {}	
	dynamic B<A> g6() {}	
	dynamic B<Integer> g7() {} 	
}