package com.cn.letcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author hebiao
 * @version :Tree.java, v0.1 2019/7/26 15:35 hebiao Exp $$
 */
public class Tree {

    public class TreeNode {

        private int val;
        private TreeNode right;
        private TreeNode left;

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode(int x) {
            val = x;
        }

    }

    public TreeNode bstToGst(TreeNode treeNode) {

        bst(treeNode);
        return treeNode;

    }

    int sum = 0;

    private void bst(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        bst(treeNode.right);
        TreeNode right = treeNode.right;
        treeNode.val += sum;
        sum = right.val;
        bst(treeNode.left);

    }

    /**
     * 二叉树的中序遍历，递归解法
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ele = new ArrayList<>();
        recursive(root, ele);
        return ele;
    }

    private void recursive(TreeNode root, List<Integer> ele) {
        if (root == null) {
            return;
        }
        TreeNode lnode = root.left;
        recursive(lnode, ele);
        ele.add(root.val);
        TreeNode rnode = root.right;
        recursive(rnode, ele);
    }


    /**
     * 验证二叉树是否是二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        Integer lower = null;
        Integer upper = null;
        return recursiveSearch(root, lower, upper);

    }

    private boolean recursiveSearch(TreeNode root, Integer lower, Integer upper) {
        if (root == null) {
            return true;
        }
        Integer val = root.val;
        if (lower != null && val <= lower) {
            return false;
        }
        if (upper != null && val >= upper) {
            return false;
        }

        if (!(recursiveSearch(root.right, val, upper))) {
            return false;
        }
        if (!(recursiveSearch(root.left, lower, val))) {
            return false;
        }
        return true;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {

        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }

        return (p.val == q.val) && (isSameTree(p.left, p.left)) && (isSameTree(p.right, p.right));
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            list.add(new ArrayList<>());
            int length = queue.size();
            for (int i = 0; i < length; i++) {
                TreeNode node = queue.remove();
                if(level %2 ==0){
                    list.get(level).add(node.val);
                }
                else {
                    list.get(level).add(0,node.val);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
                if (node.left != null) {
                    queue.add(node.left);

                }
            }
            level++;
        }
        return list;
    }

    public List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<List<Integer>>();
        if (root == null) {
            return levels;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            levels.add(new ArrayList<Integer>());
            int level_length = queue.size();
            for (int i = 0; i < level_length; ++i) {
                TreeNode node = queue.remove();
                levels.get(level).add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            level++;
        }
        return levels;
    }

    public static void main(String[] args) {
        System.out.println(2 % 2);
    }


}
