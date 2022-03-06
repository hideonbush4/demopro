package com.example.demo.utils;

import java.util.Arrays;

public class SortUtils {

    public static void main(String[] args) {
        int a[] = {2, 4, 1, 5, 7, 9, 11, 24, 70, 12};
        quickSort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    //冒泡排序（顺序）
    public static int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, i, j);
                }
            }
        }
        return arr;
    }

    //冒泡排序（逆序）
    public static int[] bubbleSort2(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] > arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
        return arr;
    }

    //选择排序
    public static int[] selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
        return arr;
    }

    //插入排序
    public static int[] insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int preIndex = i - 1;
            int current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex++] = current;
        }
        return arr;
    }

    //希尔排序
    public static int[] shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < arr.length; i++) {
                int preIndex = i - gap;
                int current = arr[i];
                while (preIndex >= 0 && arr[preIndex] > current) {
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex = preIndex - gap;
                }
                arr[preIndex + gap] = current;
            }
        }
        return arr;
    }

    public static int[] shellSort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < arr.length; i++) {
                int preIndex = i;
                int current = arr[i];
                while (preIndex > 0 && arr[preIndex - gap] < current) {
                    arr[preIndex] = arr[preIndex - gap];
                    preIndex -= gap;
                }
                arr[preIndex] = current;
            }
        }
        return arr;
    }

    //归并排序
    public static int[] mergeSort(int[] arr) {
        if (arr.length < 2) {
            return arr;
        }
        int middle = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, arr.length);
        return merge(mergeSort(left), mergeSort(right));
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        while (left.length > 0 && right.length > 0) {
            if (left[i] <= right[i]) {
                result[i] = left[i++];
                left = Arrays.copyOfRange(left, 1, left.length);
            } else {
                result[i] = right[i++];
                right = Arrays.copyOfRange(right, 1, right.length);
            }
        }
        while (left.length > 0) {
            result[i++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }
        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }
        return result;
    }

    //快速排序
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot;
            pivot = findPivot(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    private static int findPivot(int[] arr, int low, int high) {
        int pivotkey = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= pivotkey) {
                high--;
            }
            swap(arr, low, high);
            while (low < high && arr[low] <= pivotkey) {
                low++;
            }
            swap(arr, low, high);
        }
        return low;
    }

}

//堆排序
class HeapSort {
    //test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice" : "fucking fucked!");
        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        heapSort(arr);
        printArray(arr);
    }

    //堆排序
    //堆是在数组中进行伸缩的，
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //先创建堆，将元素插入堆中
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        int size = arr.length;
        //将0位置的元素和树的最后一个元素的位置进行交换
        swap(arr, 0, --size);
        while (size > 0) { //只要树还有元素，就将树中最后一个元素的位置和树根元素进行交换
            //将交换后的堆顶元素进行调整
            heapify(arr, 0, size);
            //将最后一个元素和堆顶元素记性交换
            swap(arr, 0, --size);

        }

    }

    //建立大根堆的过程，时间复杂度为O(logn)
    public static void heapInsert(int[] arr, int index) {
        //将元素插入堆，如果插入元素位置的元素大于父节点位置的元素，那就将这两个位置的元素位置互换
        //最终的结果是创建一个大根堆
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    //交换数组中两个元素的位置
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //当大根堆中有个位置的元素被换掉，就需要进行相应的调整
    //0-heapsize-1的范围上已经形成了堆，再往右就越界
    public static void heapify(int[] arr, int index, int size) {
        //得到左孩子节点的索引
        int left = 2 * index + 1;
        //当左子树的下标没有超过数组长度的时候，即左孩子下标没有越界，如果left已经越界了，那就表示其父节点已经是叶子节点了
        while (left < size) {
            //只有右孩子不越界，并且右孩子的值要比左孩子的值要大，才会作为largest值出现
            int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;//找出左右节点中值比较大的节点
            //左孩子和右孩子最大值和父节点index之间谁大，谁就是largest下标
            largest = arr[largest] > arr[index] ? largest : index;
            //如果父节点的值比两个子节点的值都大的话，就退出
            if (largest == index) {
                break;
            }
            //如果父节点的值小于两个子节点中比较大的值的节点的时候，就将父节点和子节点中值较大的进行交换
            swap(arr, largest, index);
            //这是一个值变小一直向下沉的操作
            index = largest;
            //索引交换了之后，接着向下做调整操作
            left = index * 2 + 1;
        }
    }

    //test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random()) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    //test
    public static int[] copyArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    //test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    //test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 != null && arr2 == null) || (arr1 == null && arr2 != null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;

    }

    //test
    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
