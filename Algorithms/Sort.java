import java.util.Arrays;

public class Sort {

    int N;
    int[] recycle;

    //sorts an array of ints using heapsort
    public void heapSort(int[] to_sort) {
	Heap myHeap = new Heap(to_sort);
	for (int i=N-1;i>-1;i--) {
	    to_sort[i] = myHeap.deleteMax();
	}
	//System.out.println("heap "+Arrays.toString(to_sort));
    }

 
    //sorts an array of ints using selection sort
    public void selectionSort(int[] to_sort) {
	for (int i=0;i<N;i++){

	    int smallest_pos = i;

	    for (int j=i+1;j<to_sort.length;j++) {

		if (to_sort[j]<to_sort[smallest_pos]) {

		    smallest_pos=j;
		}
	    }

	    int val = to_sort[smallest_pos];
	    to_sort[smallest_pos] = to_sort[i];
	    to_sort[i] = val;
	}
	//	System.out.println("selection "+Arrays.toString(to_sort));
    }

    //sorts an array of ints using quicksort
    public void quickSort(int[] to_sort) {
	int lo = 0;
	int hi = to_sort.length - 1;
	//	System.out.println("quick "+Arrays.toString(to_sort));
    }

    public void quickSortHelper(int[] to_sort,int lo,int hi) {
	if (hi==lo) return;
	int x = partition(to_sort,lo,hi);
	quickSortHelper(to_sort,lo,x);
	quickSortHelper(to_sort,x+1,hi);
    }

    public int partition(int[] to_sort,int lo, int hi) {
	int x = to_sort[hi];
	int i = lo-1;
	for (int j=lo;j<hi;j++) {
	    if(to_sort[j]<=x) {
		int temp = j;
		to_sort[j] = to_sort[i+1];
		to_sort[i+1] = temp;
		i++;
	    }
	}
	to_sort[hi] = to_sort[i+1];
	to_sort[i+1] = x;
	return(i+1);
    }

    //sorts an array of ints using mergesort
    public void mergeSort(int[] to_sort) {

	int lo = 0;
	int hi = to_sort.length - 1;
	mergeSorthelper(to_sort,lo,hi);
	//	System.out.println("merge "+Arrays.toString(to_sort));
    }


    public void mergeSorthelper(int[] to_sort,int lo, int hi) {
	if (hi==lo) return;
	int mid = (hi+lo)/2;
	mergeSorthelper(to_sort,lo,mid);
	mergeSorthelper(to_sort,mid+1,hi);
	
    }

    public void merge(int[] to_sort, int lo, int mid, int hi){
	int p1 = lo;
	int p2 = mid+1;
	for (int i = lo;i<hi+1;i++) {
	    if (to_sort[p1]>=to_sort[p2]|p1==mid+1) {
		recycle[i] =to_sort[p2];
		p2++;
	    }
	    else if (to_sort[p1]<to_sort[p2]|p2==hi+1) {
		recycle[i] = to_sort[p1];
		p1++;
	    }
	}
	for (int i=lo;i<hi+1;i++){
	    to_sort[i] = recycle[i];
	}
    }

    //sorts an array of ints using insertion sort
    public void insertionSort(int[] to_sort) {
	
	for(int i = 1; i < N; i++) {

	    int val = to_sort[i];
	    int j = i - 1;

	    while(j >= 0 && val < to_sort[j]) {
		to_sort[j + 1] = to_sort[j];
		j--;
	    }

	    to_sort[j+1] = val;

	}
	//	System.out.println("insertion "+Arrays.toString(to_sort));
    }


    public void test() {

	int[] my_array = new int[N];


	long total_time = 0;
	int num_iters = 10;

	//test insertionsort
	//sort multiple times to reduce noise
	for(int i = 0; i < num_iters; i++) { 
	    Generate.randomData(my_array); //fill my_array with unsorted data
	    long[] time_loop = new long[5];
	    for (int j=0;j<5;j++) {
		long time = getMethod(j,my_array);
		time_loop[j]=time;
		System.out.println("Method"+j+" takes time: "+(time/1000000000.0)/(num_iters * 1.0) + " ");
	    }
	}
	    

    }

    public long getMethod(int i, int[] to_sort) {
	long start_time;
	long end_time;
	if (i==0) {
	    start_time = System.nanoTime();
	    heapSort(to_sort); 
	    end_time = System.nanoTime();}
	else if (i==1) {
	    start_time = System.nanoTime();
	    selectionSort(to_sort);
	    end_time = System.nanoTime();
	}
	else if (i==2) {
	    start_time = System.nanoTime();
	    quickSort(to_sort);
	    end_time = System.nanoTime();
	}
	else if (i==3) {
	    start_time = System.nanoTime();
	    mergeSort(to_sort);
	    end_time = System.nanoTime();
	}
	else {
	    start_time = System.nanoTime();
	    insertionSort(to_sort);
	    end_time = System.nanoTime();
	}
	long time = end_time - start_time;
	return(time);
    }
	    
	    

    //input: number of elements to sort
    public static void main(String[] args) {

	int num_items = Integer.parseInt(args[0]);
	

	Sort s = new Sort(num_items);
	int[] recycle = new int[num_items];
	s.test();

    }

    public Sort(int num_elts) {
	N = num_elts;

    }

}