class MySystem extends System {
	static void printCln(char c) {
		printS("MySystem ");
		System.printCln(c);
	}
}

class MyObject extends Object {
	static void f() {
		System.printSln("MyObject.f");
	}
}

class A {
	static void main() {
		System.printB(true);
		System.printB(false);

		System.printC('a');
		System.printC('\n');
		System.printC('\\');

		System.printI(0);
		System.printI(3);

		System.printS("asd");
		System.printS("");
		System.printS("a  b  c");
		System.printS("bcd\nbcd");

		System.println();

		System.printBln(true);
		System.printCln('a');
		System.printIln(0);
		System.printSln("asd");

		System.printI(System.read());

		MySystem.printCln('a');
		MyObject.f();
	}
}
