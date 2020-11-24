package com.h3c.redis;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
@A_ROOT
public class MainTest {
    public static void main(String[] args) throws NoSuchMethodException {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(MainTest.class);
//        enhancer.setCallback(new MyIntercepter());
//        enhancer.5, 194, 72, 80, 143, 55, 209, 18, 554, 103, 55, 166, 124});

//        List<Integer> list=new ArrayList<>();
//        System.out.println(list.toString());



//                AnnotationAttributes a =AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_A.class,false,true);
//                AnnotationAttributes b =AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_B.class,false,true);
//                AnnotationAttributes c =AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_C.class,false,true);
//                AnnotationAttributes root =AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_ROOT.class,false,true);
//                System.out.println("x");
//        System.out.println(A_C.class.getName());
//        System.out.println(A_C.class.getDeclaredMethod("c1").getName());

        int a[]={0,1,2,3,4};
        int b=0;
        System.out.println(a[b=2]);
    }

}
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface  A_C
{
    @AliasFor(value = "c2")
    String c1() default "A_C_1";

    @AliasFor(value = "c1")
    String c2() default "A_C_1";

    String c3() default "A_C_3";

    @AliasFor(value = "c5")
    String c4() default "A_C_4";
    @AliasFor(value = "c4")
    String c5() default "A_C_4";

    String c6() default "A_C_6";
}

@A_C
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface  A_B
{
    @AliasFor(value = "c2",annotation =  A_C.class)
    String b1() default "A_B_1_2";
    @AliasFor(value = "c2",annotation =  A_C.class)
    String b2() default "A_B_1_2";
    @AliasFor(value = "c2",annotation =  A_C.class)
    String b3() default "A_B_1_2";

    String b4() default "A_B_4";


}



@A_B
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface  A_A
{
    @AliasFor(value = "c1",annotation =  A_C.class)
    String a1() default "A_A_1";

    String a2() default "A_A_2";
}



@A_A
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@interface  A_ROOT
{

}


@A_ROOT
class AnnotationUtilTest {
    public static void main(String[] args) {
        AnnotationAttributes a = AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_A.class,false,true);
        AnnotationAttributes b =AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_B.class,false,true);
        AnnotationAttributes c =AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_C.class,false,true);
        AnnotationAttributes root =AnnotatedElementUtils.findMergedAnnotationAttributes(AnnotationUtilTest.class,A_ROOT.class,false,true);
        System.out.println("x");
    }
}
class Solution1 {
    Map<TreeNode, TreeNode> parent;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        parent = new HashMap();
        dfs(root, null);

        Queue<TreeNode> queue = new LinkedList();
        queue.add(null);
        queue.add(target);

        Set<TreeNode> seen = new HashSet();
        seen.add(target);
        seen.add(null);

        int dist = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                if (dist == K) {
                    List<Integer> ans = new ArrayList();
                    for (TreeNode n: queue)
                        ans.add(n.val);
                    return ans;
                }
                queue.offer(null);
                dist++;
            } else {
                if (!seen.contains(node.left)) {
                    seen.add(node.left);
                    queue.offer(node.left);
                }
                if (!seen.contains(node.right)) {
                    seen.add(node.right);
                    queue.offer(node.right);
                }
                TreeNode par = parent.get(node);
                if (!seen.contains(par)) {
                    seen.add(par);
                    queue.offer(par);
                }
            }
        }

        return new ArrayList<Integer>();
    }

    public void dfs(TreeNode node, TreeNode par) {
        if (node != null) {
            parent.put(node, par);
            dfs(node.left, node);
            dfs(node.right, node);
        }
    }
}
class Solution {

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> result = new ArrayList<>();
        preOrder(root, new ArrayList<>(), target, K, result);
        return result;
    }

    private void preOrder(TreeNode node, List<TreeNode> ancestors, TreeNode target, int K, List<Integer> result) {
        if (node == null)
            return;
        List<Integer> tmp = new ArrayList<>();
        if (node == target) {
            for (int i = 0; i < ancestors.size(); i++) {
                if (K >= i + 1) {
                    getKNode(ancestors.get(ancestors.size() - 1 - i), K - 1 - i, tmp, target);
                    result.addAll(tmp);
                    tmp.clear();
                }
            }
            getKNode(node, K, tmp, target);
            result.addAll(tmp);
        }
        ancestors.add(node);
        preOrder(node.left, ancestors, target, K, result);
        preOrder(node.right, ancestors, target, K, result);
        ancestors.remove(ancestors.size() - 1);
    }

    private void getKNode(TreeNode node, int K, List<Integer> result, TreeNode exclude) {
        if (node == null)
            return;
        if (K == 0) {
            if (node != exclude)
                result.add(node.val);
            return;
        }
        getKNode(node.left, K - 1, result, exclude);
        getKNode(node.right, K - 1, result, exclude);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class Parent {
    void f() {
        System.out.println("parent f");
    }
}

class Child extends Parent {
    void f() {
        System.out.println("child f");
    }
}

class MyIntercepter implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        methodProxy.invokeSuper(o, objects);
        return null;
    }
}

//class Solution {
//    public boolean checkInclusion(String s1, String s2) {
//        StringBuilder sb = new StringBuilder(s2);
//        return s1.contains(sb.reverse().toString());
//    }
//}
//class Solution {
//    public int maxSubArray(int[] nums) {
//
//    }
//}
//class Solution {
//    public int maxProduct(int[] nums) {
//        int index = 0;
//        while (index < nums.length) {
//            int neAbsMin = Integer.MIN_VALUE;
//            int neAbsMax = 0;
//            int poAbsMin = Integer.MAX_VALUE;
//            int poAbsMax = 0;
//            int pro = 1;
//            for (int i = index; i < nums.length; i++) {
//                if(nums[i]==0){
//                    index++;
//                    break;
//                }
//
//            }
//
//        }
//
//    }
//}
//class Solution {
//    public int rob(int[] nums) {
//        if (nums == null || nums.length == 0)
//            return 0;
//        if (nums.length == 1)
//            return nums[0];
//        int[] flags = new int[nums.length];
//        Arrays.fill(flags, -1);
//        flags[nums.length - 1] = nums[nums.length - 1];
//        return rob(0, nums, flags);
//    }
//
//    private int rob(int index, int[] nums, int[] flags) {
//        if (index == nums.length - 1)
//            return nums[nums.length - 1];
//
//        int n1, n2;
//        if (index + 2 < nums.length && flags[index + 2] != -1) {
//            n1 = nums[index] + flags[index + 2];
//        } else if (index + 2 < nums.length) {
//            flags[index + 2] = rob(index + 2, nums, flags);
//            n1 = nums[index] + flags[index + 2];
//        } else {
//            n1 = nums[index];
//        }
//        if (index + 3 < nums.length && flags[index + 3] != -1) {
//            n2 = nums[index + 1] + flags[index + 3];
//        } else if (index + 3 < nums.length) {
//            flags[index + 3] = rob(index + 3, nums, flags);
//            n2 = nums[index + 1] + flags[index + 3];
//        } else {
//            n2 = nums[index + 1];
//        }
//
//        return Math.max(n1, n2);
//    }
//}