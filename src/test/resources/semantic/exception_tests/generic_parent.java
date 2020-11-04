class Main { static void main() {} }

class A {}
class B<K> {}

class D extends A<T> {}
class E extends A<K> {}

class F extends B {}
class G extends B<T> {}
class H1 extends B<B> {}

class J<K> extends A<T> {}
class K<K> extends A<K> {}

class L<K> extends B {}
class N<K> extends B<T> {}
class O<K> extends Object<T> {}
