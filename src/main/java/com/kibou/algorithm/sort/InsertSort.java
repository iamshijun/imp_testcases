package com.kibou.algorithm.sort;

import java.util.Arrays;

public class InsertSort extends Sort{

	@Override
	public void ascSort(int[] arr) {
		if(arr == null || arr.length <= 1)
			return;
		for(int i = 1 ; i < arr.length; ++i){
			int val = arr[i] , dest = 0; //val��ǰ����ֵ,
			
			for(int j = i - 1 ; j >= 0;--j){//�Ӻ���ǰ��val���
				if(arr[j] > val){ //��ǰ������ֵ���ڵ�ǰֵ,��ֵ�����ƶ�
					//�Ӻ���ǰ�� �ŵ�think about it.
					swap(arr, j, j+1); 
				}else{
					dest = j + 1;
					break;
				}
			}
			if(dest != i)  arr[dest] = val;
		}
	}

	@Override
	public void descSort(int[] arr) {
		
	}

	public static void main(String[] args) {
		int[] raw = { 5, 11, 3, 17, 2, 1, 8, 4, 12 };
		System.out.println("Before sort : " + Arrays.toString(raw));
		
		new InsertSort().ascSort(raw);
		System.out.println("After sort : " + Arrays.toString(raw));
	}
}
