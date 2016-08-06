package com.kibou.algorithm.sort;

import java.util.Arrays;

public class SelectSort extends Sort{

	public void ascSort(int[] arr) {
		for(int i = 0 ; i < arr.length - 1;++i){//i代表轮数
			int maxIndex = 0;
			for(int j = 1 ; j < arr.length - i ;++j){
				if(arr[maxIndex] < arr[j]){
					maxIndex = j;
				}
			}
			//每次找到最大的和最后一位作交换
			swap(arr, maxIndex, arr.length - i - 1);
		}
		/*
		 * for(int i = 0 ; i < arr.length - 1;++i){
			int minIndex = i;
			for(int j = i+1 ; j < arr.length ;++j){
				if(arr[minIndex] > arr[j]){
					minIndex = j;
				}
			}
			swap(arr, minIndex, i);
		}
		 * */
	}
	
	@Override
	public void descSort(int[] arr) {
		
	}

	public static void main(String[] args) {
		int[] raw = { 5, 11, 3, 17, 2, 1, 8, 4, 12 };
		int[] raw2 = new int[raw.length];
		System.arraycopy(raw, 0, raw2, 0, raw.length);
		
		System.out.println("Before sort : " + Arrays.toString(raw));
		
		new SelectSort().ascSort(raw);
		
		System.out.println("After ascSort : " + Arrays.toString(raw));
		
//		new SelectSort().descSort(raw2);
//		System.out.println("After descSort : " + Arrays.toString(raw2));
	}

}
