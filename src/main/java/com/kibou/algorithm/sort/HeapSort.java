package com.kibou.algorithm.sort;

import java.util.Arrays;

public class HeapSort extends Sort {

	/**
	 * 堆筛选，除了start之外，start~end均满足大顶堆的定义。 调整之后start~end称为一个大顶堆。
	 * @param arr 待调整数组
	 * @param start 起始指针
	 * @param end 结束指针
	 */
	public void heapAdjust(int[] arr, int start, int end) {
		int temp = arr[start];

		for (int i = 2 * start + 1; i <= end; i *= 2) {	// 左右孩子的节点分别为2*i+1,2*i+2

			// 选择出左右孩子较小的下标
			if (i < end && arr[i] < arr[i + 1]) {
				i++;
			}
			if (temp >= arr[i]) {
				break; // 已经为大顶堆，=保持稳定性。
			}
			arr[start] = arr[i]; // 将子节点上移
			start = i; // 下一轮筛选
		}

		arr[start] = temp; // 插入正确的位置
	}

	public void heapSort(int[] arr) {
		if (arr == null || arr.length == 0)
			return;

		// 建立大顶堆
		for (int i = arr.length / 2; i >= 0; i--) {
			heapAdjust(arr, i, arr.length - 1);
		}

		for (int i = arr.length - 1; i >= 0; i--) {
			swap(arr, 0, i);
			heapAdjust(arr, 0, i - 1);
		}

	}

	@Override
	public void ascSort(int[] arr) {
		heapSort(arr);
	}

	@Override
	public void descSort(int[] arr) {

	}

	public static void main(String[] args) {
		int[] raw = { 5, 11, 37, 17, 2, 1, 8, 44, 12, 34, 21, 19, 30, 4, 6 };
		System.out.println("Before sort : " + Arrays.toString(raw));
		
		new QuickSort().ascSort(raw);
		System.out.println("After sort : " + Arrays.toString(raw));
	}
}