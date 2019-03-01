package com.notificationlab.soochak;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



class Result {

    /*
     * Complete the 'bestSumAnyTreePath' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY parent
     *  2. INTEGER_ARRAY values
     */
    private static Integer[] maxSums;
    private static List<Integer> p;
    private static List<Integer> v;
    private static boolean[] isVisited;
    public static int bestSumAnyTreePath(List<Integer> parent, List<Integer> values) {
        maxSums = new Integer[values.size()];
        isVisited = new boolean[values.size()];
        p=parent;
        v=values;
        System.out.println(p);
        System.out.println(v);
//        return -1;
        max(0);
        return findMax();
     }

     static int max(Integer root){
         if(maxSums[root]!=null){
             return maxSums[root];
         }
         int currMValue = v.get(root);
         int rootValue = v.get(root);
         Integer maxLeft = null;
         Integer maxRight = null;
         Integer lChild = getChild(root);
         if(lChild!=null){
             maxLeft = max(lChild);
             isVisited[lChild] = true;
         }
         Integer rChild = getChild(root);
         if(rChild!=null){
             maxRight = max(rChild);
             isVisited[rChild] = true;
         }
         if(rootValue>currMValue){
             currMValue = rootValue;
         }
         if(maxLeft!=null && (maxLeft + rootValue > currMValue)){
             currMValue = maxLeft + rootValue;
         }
         if(maxRight!=null && (maxRight + rootValue > currMValue)){
             currMValue = maxRight + rootValue;
         }
         if(maxLeft!=null && maxRight!=null && (maxLeft + rootValue + maxRight > currMValue)){
             currMValue = maxLeft + rootValue + maxRight;
         }
         maxSums[root] = currMValue;
         return maxSums[root];
     }
     
     
    private static Integer getChild(Integer node){
        for(int i=0; i<p.size(); i++){
            if(!isVisited[i] && p.get(i).equals(node)){
                return i;
            }
        }
        return null;
    }

    private static int findMax(){
        Integer maxValue = Integer.MIN_VALUE;
        for(Integer v : maxSums){
            if(v !=null && v>maxValue){
                maxValue = v;
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws IOException {

        List<Integer> p = new ArrayList<Integer>();
        //[-1, 0, 1, 2, 0]
        p.add(-1);
//        p.add(0);
//        p.add(1);
//        p.add(2);
//        p.add(0);
        //[-2, 10, 10, -3, 10]
        List<Integer> v = new ArrayList<Integer>();
        v.add(-5);
//        v.add(10);
//        v.add(10);
//        v.add(-3);
//        v.add(10);
        int result = Result.bestSumAnyTreePath(p, v);

        System.out.println("RES :: "+result);
    }
}
