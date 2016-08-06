package com.kibou.algorithm.sort;

import java.util.Arrays;

public class HeapSort extends Sort {

	/**
	 * ��ɸѡ������start֮�⣬start~end������󶥶ѵĶ��塣 ����֮��start~end��Ϊһ���󶥶ѡ�
	 * @param arr ����������
	 * @param start ��ʼָ��
	 * @param end ����ָ��
	 */
	public void heapAdjust(int[] arr, int start, int end) {
		int temp = arr[start];

		for (int i = 2 * start + 1; i <= end; i *= 2) {	// ���Һ��ӵĽڵ�ֱ�Ϊ2*i+1,2*i+2

			// ѡ������Һ��ӽ�С���±�
			if (i < end && arr[i] < arr[i + 1]) {
				i++;
			}
			if (temp >= arr[i]) {
				break; // �Ѿ�Ϊ�󶥶ѣ�=�����ȶ��ԡ�
			}
			arr[start] = arr[i]; // ���ӽڵ�����
			start = i; // ��һ��ɸѡ
		}

		arr[start] = temp; // ������ȷ��λ��
	}

	public void heapSort(int[] arr) {
		if (arr == null || arr.length == 0)
			return;

		// �����󶥶�
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