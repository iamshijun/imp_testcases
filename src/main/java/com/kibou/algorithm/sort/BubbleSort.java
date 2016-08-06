package com.kibou.algorithm.sort;

import java.util.Arrays;

public class BubbleSort extends Sort{

	public static void main(String[] args) {
		int[] raw = { 5, 11, 3, 17, 2, 1, 8, 4, 12 };
		System.out.println("Before sort : " + Arrays.toString(raw));
		
		new BubbleSort().ascSort(raw);
		System.out.println("After sort : " + Arrays.toString(raw));
	}

	public void ascSort(int[] raw) {
		for (int i = 0; i < raw.length; ++i) {
			for(int j = 0;j < raw.length - 1 - i;++j){
				if(raw[j] > raw[j+1]){
					swap(raw,j,j+1);
				}
			}
		}
	}

	@Override
	public void descSort(int[] arr) {
		
	}
}
