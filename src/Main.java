import java.util.Comparator;
import java.util.LinkedList;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(new Comp());
//		t.insert(3, "3");
//		t.insert(2, "2");
//		t.insert(1, "1");
//		t.insert(4, "4");
//		t.insert(5, "1000");
//		t.insert(6, "6");
//		t.insert(7, "7");
//		t.insert(16, "16");
//		t.insert(15, "15");
//		t.insert(14, "14");
//		t.insert(13, "13");
//		t.insert(12, "12");
//		t.insert(11, "11");
//		t.insert(10, "10");
//		t.insert(8, "8");
//		t.insert(9, "9");
//		
////		System.out.println(t.getValue(100));
//		
////		t.delete(100);
//		t.delete(7);
//		t.delete(13);
//		System.out.println(t.getValue(8));
//		t.delete(15);
//		
//		System.out.println(t);
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 1000000; i++)
//		{
//			t.insert(i, String.valueOf(i));
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("build tree time : " + (end - start) + " ms");
//		
//		start = System.currentTimeMillis();
////		t.getValue(1000000);
//		t.delete(999999);
//		end = System.currentTimeMillis();
//		System.out.println((end - start) + " ms");
//		
		LinkedList<Integer> l = new LinkedList<Integer>();
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++)
		{
			l.add(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("build tree time : " + (end - start) + " ms");
		
		start = System.currentTimeMillis();
		l.get(9999999);
		end = System.currentTimeMillis();
		System.out.println((end - start) + " ms");
	}

}

class Comp implements Comparator<Integer>
{

	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		if (o1 < o2)
		{
			return -1;
		}
		else if (o1  > o2)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
}