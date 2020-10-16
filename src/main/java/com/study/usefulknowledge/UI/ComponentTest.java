package com.study.usefulknowledge.UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;

/**
 * 常用组件：
 * 1、   JScrollPane滚动条的面板，当内容大于JScrollPane时，自动显示滚动条
 * 2、   JSplitPane带有分隔符的面板
 * 3、   JTabbedPane分页的面板
 * 4、   JList列表框
 * 5、   JComboBox下拉表
 * 6、   JProgrssBar进度条、JSlider滑块
 * 7、   菜单条Menubar--菜单JMenu--菜单项JMenuItem，
 *       快捷键、右键菜单
 * 8、   工具条JToolBar
 * 9、   表格JTable
 * 10、  树目录JTree
 * 11、
 *
 *
 */
public class ComponentTest implements ListSelectionListener{
    final static int WIDTH=500;
    final static int HEIGHT=500;
    static String[] s={"s1","s2","s3"};
    JComboBox nameinput;
    public ComponentTest (){
        JFrame jFrame=new JFrame();
/****      1、 **********************JScrollPane滚动条的面板*/
        JPanel jPanel1=new JPanel();
        jPanel1.setSize(new Dimension(100,200));
        JScrollPane jScrollPane=new JScrollPane();
        jScrollPane.setSize(new Dimension(200,200));
        jScrollPane.add(jPanel1);
/*****     2、 *********************JSplitPane分隔符面板*/
        JPanel jPanel2=new JPanel();
        JLabel p1=new JLabel();
        JLabel p2=new JLabel();
        JSplitPane vSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        vSplitPane.setLeftComponent(p1);
        vSplitPane.setRightComponent(p2);
        vSplitPane.setPreferredSize(new Dimension(100,200));
        vSplitPane.setDividerLocation(200);//设置分隔条的位置
        vSplitPane.setDividerSize(3);
        vSplitPane.setOneTouchExpandable(true);
        vSplitPane.setContinuousLayout(true);
        jPanel2.add(vSplitPane);
/*****    3 、 *********************JTabbedPane分页的面板*/
        JTabbedPane jPanel3=new JTabbedPane();
        JPanel  panel1=new JPanel();
        JPanel  panel2=new JPanel();
        JPanel  panel3=new JPanel();
        JPanel  panel4=new JPanel();
        JPanel  panel5=new JPanel();
        panel1.setLayout(new BorderLayout());
        jPanel3.addTab("panel1",panel1);
        jPanel3.setEnabledAt(0,true);
        jPanel3.setTitleAt(0,"基本信息");
        jPanel3.addTab("panel2",panel2);
        jPanel3.setEnabledAt(1,true);
        jPanel3.setTitleAt(1,"照片");
        jPanel3.addTab("panel3",panel3);
        jPanel3.setEnabledAt(2,true);
        jPanel3.setTitleAt(2,"兴趣爱好");
        jPanel3.addTab("panel4",panel4);
        jPanel3.setEnabledAt(3,true);
        jPanel3.setTitleAt(3,"日常习惯");
        jPanel3.addTab("panel5",panel5);
        jPanel3.setEnabledAt(4,true);
        jPanel3.setTitleAt(4,"评价");
        jPanel3.setPreferredSize(new Dimension(500,200));
        jPanel3.setTabPlacement(JTabbedPane.TOP);
        jPanel3.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

/******       4、  ********************JList列表框*/
        JTabbedPane jPanel4=new JTabbedPane();
        DefaultListModel model=new DataModel(1);//对DefaultListModel进行操作，每个对象对应一个模型
        JList jList=new JList(model);
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index;
                if(e.getSource()==jList){  //确定某个对象的双击事件
                    if(e.getClickCount()==2){
                        index=jList.locationToIndex(e.getPoint());//获取位置，然后操作，方便增删改查
                        model.removeElement(index);
                    }
                }
            }
        });
        jPanel4.add(jList);
/****      5、 **********************JComboBox下拉表*/
        JTabbedPane jPanel5=new JTabbedPane();
        DefaultComboBoxModel comboBoxModel=new DefaultComboBoxModel();
        nameinput=new JComboBox(comboBoxModel);
        nameinput.setEditable(true);
        ComboBoxEditor editor=nameinput.getEditor();//初始化值
        nameinput.configureEditor(editor,s);
        nameinput.addItem("1");//有自身的增删改查
        nameinput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {//选择值之后的操作
                String namestring=(String)nameinput.getSelectedItem();//
            }
        });
        jPanel5.add(nameinput);
/******        6 、 ********************JProgrssBar进度条、JSlider滑块*/
        JTabbedPane jPanel6=new JTabbedPane();
        JProgressBar jProgressBar=new JProgressBar();         //进度条
        jProgressBar.setOrientation(JProgressBar.HORIZONTAL);
        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);
        jProgressBar.setValue(0);
        jProgressBar.setStringPainted(true);  //设置百分比进度
        jProgressBar.setPreferredSize(new Dimension(100,30));
        JButton jButton=new JButton();
        jPanel6.add(jProgressBar,BorderLayout.NORTH);
        jPanel6.add(jButton,BorderLayout.CENTER);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 10; i++) {
                            jProgressBar.setValue(i * 10);
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
        //滑块
        JSlider jSlider=new JSlider(0,100,0);
        jSlider.setMajorTickSpacing(20);//主刻度
        jSlider.setMinorTickSpacing(5);//次刻度
        jSlider.setPaintTicks(true);//显示刻度
        jSlider.setPaintLabels(true);//显示刻度数字
        jSlider.setSnapToTicks(true);//让滑块滑到附近整数刻度
        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((JSlider)e.getSource()==jSlider){   //滑动滑块得到值
                    System.out.println(jSlider.getValue());
                }
            }
        });
/******        7 、 ********************菜单条Menubar--菜单JMenu--菜单项JMenuItem*/
        JMenuBar jMenuBar=new JMenuBar();
        JMenu jMenu1=new JMenu("文件[F]");
        jMenu1.setMnemonic('F');//设置JMenu的快捷键，使用时alt+键
        JMenu jMenu2=new JMenu("帮助");
        JMenuItem jMenuItem1_1=new JMenuItem("打开[O]",'O');//设置JMenuItem的快捷键，使用时alt+键
        JMenuItem jMenuItem1_2=new JMenuItem("保存",'S');
        JMenuItem jMenuItem1_3=new JMenuItem("退出");
        JMenuItem jMenuItem2_1=new JMenuItem("帮助1",'H');
        JMenuItem jMenuItem2_2=new JMenuItem("帮助2");
        jMenuItem2_2.setEnabled(false);// 设置为不可用
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        jMenu1.add(jMenuItem1_1);
        jMenu1.addSeparator();//分割线
        jMenu1.add(jMenuItem1_2);
        jMenu1.add(jMenuItem1_3);
        jMenu2.add(jMenuItem2_1);
        jMenu2.add(jMenuItem2_2);
        jFrame.setJMenuBar(jMenuBar);//添加菜单条
        jMenuItem1_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //可以参考ComponentTest的选取器
            }
        });
        //对jpanel3添加右击菜单功能
        JPopupMenu jPopupMenu=new JPopupMenu();
        jPopupMenu.add(jMenuItem1_1);
        jPopupMenu.add(jMenuItem1_2);
        jPanel3.setComponentPopupMenu(jPopupMenu);
        //单选菜单项、复选菜单项
        JRadioButtonMenuItem jRadioButtonMenuItem;
        JCheckBoxMenuItem jCheckBoxMenuItem;
/******        8、 ********************工具条JToolBar*/
        //类似与一个jpanel使用
/******        9、 ********************表格JTable*/
//表格可以直接创建，但是一般利用表格模型设置表格的属性
        JPanel jPanel9=new JPanel();
        Object[][] p={
                {"小张",new Integer(91),new Integer(1992)},
                {"小明",new Integer(92),new Integer(1992)},
                {"小王",new Integer(93),new Integer(1992)},
                {"小李",new Integer(94),new Integer(1992)}
        };
        String[] n={"姓名","工号","出生年月"};
        DefaultTableModel defaultTableModel=new DefaultTableModel(p,n);
        JTable jTable=new JTable(defaultTableModel){
            //设置第一列为不可编辑
            public boolean isCellEditable(int rowindex,int columnindex){
                if (columnindex==0)
                    return false;
                return true;
            }
            //在表格里能加其它组件
            public TableCellRenderer getCellRenderer(int row, int column) {
                if(row==1&&column==2){
                    return new TableCellRenderer() {
                        JTable subTable=new JTable(new DefaultTableModel(4,4));
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            return subTable;
                        }
                    };
                }else
                    return super.getCellRenderer(row,column);
            }
        };
        jTable.setRowHeight(1,jTable.getRowHeight()*4);//设置表里其他组件的大小
        jTable.setPreferredScrollableViewportSize(new Dimension(400,80));
        jTable.setCellSelectionEnabled(true);//设置表格以单元选取，不设置则是按照每行选取
        JScrollPane jScrollPane1=new JScrollPane(jTable);
        JButton jButton9=new JButton("num9");
        jPanel9.add(jScrollPane1,BorderLayout.NORTH);
        jPanel9.add(jButton9,BorderLayout.CENTER);
        //通过（按钮、右键）对表格的选取、更擅改查
        jButton9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=jTable.getSelectedRow();//获取选择的单行
                int[] rows=jTable.getSelectedRows();//获取选择的行s
                if(e.getActionCommand().equals("num9")){
//                    defaultTableModel.addRow(new Vector());//增加行
                    defaultTableModel.removeRow(row);//删除选中行
//                    defaultTableModel.getValueAt(row,1);//获取值
//                    defaultTableModel.setRowCount(rowCount);//设置删除行的数量
                }
                jTable.revalidate();//更新表格
            }
        });
        //监听表格的值：通过自身值的改变，监听，同时可以设置单元格之间的关联
        jTable.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("tableCellEditor".equals(evt.getPropertyName()))
                {
                    Object oldValue;
                    Object newValue;
                    if (jTable.isEditing()){
                        int row=jTable.getSelectedRow();
                        int column=jTable.getSelectedColumn();
                        oldValue = jTable.getModel().getValueAt(row, column);
                        System.out.println(oldValue);
                    }
                    else{
                        int row=jTable.getSelectedRow();
                        int column=jTable.getSelectedColumn();
                        newValue = jTable.getModel().getValueAt(row, column);
                        System.out.println(newValue);
                    }
                }
            }
        });
        //设置行的背景颜色DefaultTableCellRenderer
        DefaultTableCellRenderer defaultTableCellRenderer=new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if(row%2==0){
                    setBackground(Color.white);
                }else if(row%2==1){
                    setBackground(new Color(206,231,255));
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        for(int i=0;i<3;i++){
            jTable.getColumn(n[i]).setCellRenderer(defaultTableCellRenderer);
        }
/******        10、 ********************树目录JTree*/
//每个目录点，都可以设置为一个DefaultMutableTreeNode
        DefaultMutableTreeNode defaultMutableTreeNode;





        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();  //系统对象获取工具
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        jFrame.setLocation(x,y);  //设置位置
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
        jFrame.setContentPane(jScrollPane);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);
    }
    public static void main(String[] args){

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Object[] index=nameinput.getSelectedObjects();
    }

    class DataModel extends DefaultListModel{
        DataModel(int flag){
            if(flag==1){
                for(int i=0;i<ComponentTest.s.length;i++)
                    addElement(ComponentTest.s[i]);
            }
        }
    }
}
