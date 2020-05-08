public class TriangleHeap
{
    
    private Triangle[] heap;
    private int size;

    // Inits a new TriangleHeap, which is implimented as a maxheap
    public TriangleHeap(int maxSize)
    {
        size = 0;
        heap = new Triangle[maxSize];
    }

    // Adds a new element to the heap
    public void Add(Triangle t)
    {
        heap[size] = t;
        size++;

        heapifyUp(size - 1);
    }

    // Pops element off of heap
    public Triangle Pop()
    {
        Triangle t = new Triangle(heap[0]);

        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);

        return t;
    }

    // Returns the size of the heap
    public int GetSize()
    {
        return size;
    }

    // Returns the left child's index given index i
    private int leftChild(int i)
    {
        return (i * 2) + 1;
    }

    // Returns the right child's index given index i
    private int rightChild(int i)
    {
        return (i * 2) + 2;
    }

    // Returns the parent's index given index i
    private int parent(int i)
    {
        return (i - 1) / 2;
    }

    // Swaps elements at a and b in heap
    private void swap(int a, int b)
    {
        Triangle tmp = new Triangle(heap[a]);

        heap[a] = heap[b];
        heap[b] = tmp;
    }

    // Recursively heapifys up the tree
    private void heapifyUp(int i)
    {
        if (i > 0 && heap[parent(i)].LessThan(heap[i]))
		{
			swap(i, parent(i));
			heapifyUp(parent(i));
		}
    }

    // Recursively heapifys down the tree
    private void heapifyDown(int i)
    {
		int left = leftChild(i);
		int right = rightChild(i);

		int largest = i;

        if (left < size && heap[left].GreaterThan(heap[i])) largest = left;
        if (right < size && heap[right].GreaterThan(heap[largest])) largest = right;

		if (largest != i)
		{
			swap(i, largest);
            heapifyDown(largest);
        }
    }
}