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
			while (left < right && arr[right] >= pivot) //���������ҵ���һ����֧��С��
				right--;
			while (left < right && arr[left] <= pivot)//���������ҵ���һ����֧����
				left++;
			swap(arr, left, right); // �Ѵ�Ľ������ұߣ���С�Ľ�������ߡ�
		}
		swap(arr, pivotIndex, left); // ����pivot�������м� - 
		//������left��pivotIndex,��������while��ѭ����,�ȴ�right��ʼ��,��������ȴ�left��ʼ��,����swap��ʱ�����pivotIndex��right��
		return left;
	}
	
	 /* �Ż�
	public static int partition(int[] arr, int left, int right) {
        int pivotKey = arr[left];
        
        while(left < right) {
            while(left < right && arr[right] >= pivotKey)
                right --;
            arr[left] = arr[right]; //��С���ƶ������
            while(left < right && arr[left] <= pivotKey)
                left ++;
            arr[right] = arr[left]; //�Ѵ���ƶ����ұ�
        }
        arr[left] = pivotKey; //����pivot��ֵ���м�
        return left;
    }*/

	public void quickSort(int[] arr, int left, int right) {
		if (left >= right)
			return;
		
		int pivotPos = partition(arr, left, right);
		//pivotPosλ����ֵ ��ȷ��
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
