// recursion, no pass large judge
public class Solution {
    

    public int largestRectangleArea(int[] height) {
        // Start typing your Java solution below
        // DO NOT write main() function
        int len = height.length;
        if (len == 0){
            return 0;
        }
        
        return findMax(height, 0, len-1);
    }
    
    public int findMax(int[] height, int start, int end){
        if (start > end){
            return 0;
        }
        int minHeight = height[start];
        int minIndex = start;
        for(int i = start+1; i <= end; i++){
            if ( height[i] < minHeight){
                minHeight = height[i];
                minIndex = i;
            }
        }
        return Math.max(minHeight * (end-start+1), Math.max(findMax(height, start, minIndex-1), findMax(height, minIndex+1, end)));
    }
}

