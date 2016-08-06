package com.kibou.algorithm.sort;

import java.util.Arrays;

public class QuickSort extends Sort{

	@Override
	public void ascSort(int[] arr) {
		quickSort(arr, 0, arr.length-1);
	}

	@Override
	public void descSort(int[] arr) {
		
	}
	
	//from: http://www.cnblogs.com/wxisme/p/5243631.html
	public int partition(int[] arr, int left, int right) {
		int pivot = arr[left];
		int pivotIndex = left;

		while (left < right) {
			while (left < right && arr[right] >= pivot) //从右往左找到第一个比支点小的
				right--;
			while (left < right && arr[left] <= pivot)//从左往右找到第一个比支点大的
				left++;
			swap(arr, left, right); // 把大的交换到右边，把小的交换到左边。
		}
		swap(arr, pivotIndex, left); // 最后把pivot交换到中间 - 
		//这里以left和pivotIndex,是因上述while大循环下,先从right开始找,如果上述先从left开始找,这里swap的时候就是pivotIndex和right了
		return left;
	}
	
	 /* 优化
	public static int partition(int[] arr, int left, int right) {
        int pivotKey = arr[left];
        
        while(left < right) {
            while(left < right && arr[right] >= pivotKey)
                right --;
            arr[left] = arr[right]; //把小的移动到左边
            while(left < right && arr[left] <= pivotKey)
                left ++;
            arr[right] = arr[left]; //把大的移动到右边
        }
        arr[left] = pivotKey; //最后把pivot赋值到中间
        return left;
    }*/

	public void quickSort(int[] arr, int left, int right) {
		if (left >= right)
			return;
		
		int pivotPos = partition(arr, left, right);
		//pivotPos位置上值 已确定
		quickSort(arr, left, pivotPos - 1);
		quickSort(arr, pivotPos + 1, right);
	}
	    
	public static void main(String[] args) {
		int[] raw = { 5, 11, 37, 17, 2, 1, 8, 44, 12, 34, 21, 19, 30, 4, 6 };
		System.out.println("Before sort : " + Arrays.toString(raw));
		
		new QuickSort().ascSort(raw);
		System.out.println("After sort : " + Arrays.toString(raw));
	}

}
