class B<K> {}
class Integer {}

class A<K> {
	public K x1;
	private T x2;

	private static B<B> y;
	private static B<A> z;

	static void f1(K p1, T p2, B p3, B<K> p4, B<B> p5) {}
	static void f2(Integer<B> p1, Integer<Integer> p2) {}
	static void f3(K<Integer> p1, B<Integer> p2) {}

	static K g1() {}
	dynamic T g2() {}
	static B g3() {}
	dynamic B<K> g4() {}
	dynamic B<B> g5() {}	
	static B<A> g6() {}	
	dynamic B<Integer> g7() {} 	
}