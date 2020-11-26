class Main {
	static void main() {
	
	}
}

class A {
	public int z;
	
	dynamic void f(char y) {
		int x;
		
		{
			int z;
		}

		{
			int z;
		}

		if (true) int z;
		else int z;

		while (false) int z;

		{
			int z;
			{
				int k;
			}
		}

		int k;
		k = 1;				
	}
}

class B extends A {
	dynamic void g() {
		A a;
		B b;
		Object o;
	}	
}