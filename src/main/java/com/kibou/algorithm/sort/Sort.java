package com.kibou.algorithm.sort;

public abstract class Sort {

	//public void sort(T[] arr);
	public abstract void ascSort(int[] arr);
	
	public abstract void descSort(int[] arr);
	
	public void swapIfGraterThan(int[] arr,int index1,int index2){
		if(arr[index1] > arr[index2])
			swap(arr, index1, index2);
	}
	
	
	public void swap(int[] arr,int index1,int index2){
		if(index1 == index2) return;
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
}
