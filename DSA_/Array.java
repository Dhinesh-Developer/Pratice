package DSA_;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Array {

    private static void program1(){
        int[] ar = {1,2,3,4,5};
        int largest = ar[0];
        for(int i=0;i<ar.length;i++){
            if(ar[i] > largest){
                largest = ar[i];
            }
        }
        System.out.println(largest);
    }

    private static void program2(){
        int[] ar = {1,2,3,4,5};
        int smallest = ar[0];
        int secondSmallest = Integer.MAX_VALUE;
        for(int i=0;i<ar.length;i++){
            if(ar[i] < smallest){
                secondSmallest = smallest;
                smallest = ar[i];
            }else if(ar[i] != smallest && ar[i]<secondSmallest){
                secondSmallest = ar[i];
            }
        }

        System.out.println(secondSmallest);
    }

    private static void program3(){
        int[] ar = {1,2,3,4,5};
        for(int i=ar.length-1;i>=0;i--){
            System.out.print(ar[i]+" ");
        }
        System.out.println();
    }

    private static void program4(){
        int[] ar = {1,2,3,4,5};
        int target = 4;
        int low = 0,high = ar.length-1;
        while(low <= high){
            int mid = (low+high)/2;
            if(ar[mid] == target){
                System.out.println("Target index :"+mid);
                break;
            }else if(ar[mid] > target){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
    }

    private static void program5(){
        int[] ar = {0,1,2,2,1,0,1,2,0,1,0};
        for(int i=0;i<ar.length-1;i++){
            for(int j=0;j<ar.length-i-1;j++){
                if(ar[j] > ar[j+1]){
                    int temp = ar[j];
                    ar[j] = ar[j+1];
                    ar[j+1] = temp;
                }
            }
        }

        for(int x : ar){
            System.out.print(x+" ");
        }
        System.out.println();
    }

    private static void program6(){
        int[] ar = {1,2,3,1,2};
        int low = 0;
        int high = ar.length-1;
        while(low <= high){
            if(ar[low] == ar[high]){
                low++;
                high--;
            }else{
                System.out.println(false);
                break;
            }
        }
        System.out.println(true);
    }

    private static void program7(){
        int[] ar = {1,2,3,1,2,3,4,5,4,3,2,1,32,324,1};
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<ar.length;i++){
            if(map.containsKey(ar[i])){
                map.put(ar[i],map.get(ar[i])+1);
            }else{
                map.put(ar[i],1);
            }
        }

        for(Map.Entry<Integer,Integer> x : map.entrySet()){
            System.out.print(x.getKey()+" ");
        }
        System.out.println();
    }

    private static void program8(){
        int[] ar = {1,2,1,2,3,4,5,6,4,5};
        HashMap<Integer,Integer> map = new LinkedHashMap<>();
        for(int i=0;i<ar.length;i++){
            if(map.containsKey(ar[i])){
                map.put(ar[i],map.get(ar[i])+1);
            }else{
                map.put(ar[i],1);
            }
        }

        for(Map.Entry<Integer,Integer> x : map.entrySet()){
            if(x.getValue()==1){
                System.out.print(x.getKey()+" ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {

        program1();//largest element in the array
        program2(); // second smallest element in the array
        program3(); // reverse the array
        program4(); // kth largest element in the array
        program5(); // sort 0's,1's,2's
        program6(); //check if the array is plaindrome
        program7(); // remove duplicates from the sorted array
        program8(); // find the element that appears once

    }
}
