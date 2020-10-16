package com.study.usefulknowledge.UI.通讯系统;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class help {
    static final int WIDTH=700;
    static final int HEIGHT=400;
    JTree tree;
    DefaultMutableTreeNode root;
    DefaultMutableTreeNode node1;
    DefaultMutableTreeNode node2;
    DefaultMutableTreeNode node3;
    DefaultMutableTreeNode node4;
    static JTextArea text;
    help(){
        JFrame frame=new JFrame();
        frame.setTitle("通讯录系统帮助文档");
        frame.setSize(WIDTH,HEIGHT);
        root=new DefaultMutableTreeNode("通讯录系统帮助文档");
        node1=new DefaultMutableTreeNode("如何操作同学通讯系统");
        node2=new DefaultMutableTreeNode("如何操作同事通讯系统");
        node3=new DefaultMutableTreeNode("如何操作朋友通讯系统");
        node4=new DefaultMutableTreeNode("如何操作查询系统");
        root.add(node1);
        root.add(node2);
        root.add(node3);
        root.add(node4);
        DefaultMutableTreeNode leaftnode=new DefaultMutableTreeNode("如何使用同学信息模块");
        node1.add(leaftnode);
        leaftnode=new DefaultMutableTreeNode("如何使用同学通讯模块");
        node1.add(leaftnode);
        leaftnode=new DefaultMutableTreeNode("如何使用同事信息模块");
        node2.add(leaftnode);
        leaftnode=new DefaultMutableTreeNode("如何使用同事通讯模块");
        node2.add(leaftnode);
        leaftnode=new DefaultMutableTreeNode("如何使用朋友信息模块");
        node3.add(leaftnode);
        leaftnode=new DefaultMutableTreeNode("如何使用朋友通讯模块");
        node3.add(leaftnode);
        tree=new JTree(root);
        JScrollPane scrollPane =new JScrollPane(tree);
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JSplitPane vSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        vSplitPane.setLeftComponent(p1);
        vSplitPane.setRightComponent(p2);
        vSplitPane.setPreferredSize(new Dimension(100,200));
        vSplitPane.setDividerLocation(200);
        vSplitPane.setDividerSize(3);
        vSplitPane.setOneTouchExpandable(true);
        vSplitPane.setContinuousLayout(true);
        frame.setContentPane(vSplitPane);
        p1.add(scrollPane);
        frame.setVisible(true);
        tree.addMouseListener(new MouseHandler());
        text=new JTextArea();
        p2.add(text);
    }
}

class MouseHandler extends MouseAdapter{
    public void mousePressed(MouseEvent e ){
        String nodeName;
        try{
            JTree tree =(JTree)e.getSource();
            int rowLocation=tree.getRowForLocation(e.getX(),e.getY());
            TreePath treePath=tree.getPathForRow(rowLocation);
            TreeNode treenode=(TreeNode)treePath.getLastPathComponent();
            nodeName=treenode.toString();
        }catch (Exception e1 ){

        }
    }
}
