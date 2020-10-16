package com.study.usefulknowledge.UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 组件的设置流程：
 * 布局管理器方式：1、BorderLayout（边框布局）2、FlowLayout（流式布局）3、GridLayout（网格布局）4、绝对位置布局
 *                 5、CardLayout卡片布局
 * 注意：整体的布局一般使用BorderLayout（几个jpanel），方便，具体的每个jpanel建议使用GridBagLayout
 *
 * 实现方式：容器.setLayout(card);   card为布局方式对象
 * CardLayout卡片功能：实现多个控件操作一个显示框
 */
public class Cardlayout extends JFrame implements ActionListener {
    static int kk=0;
    JFrame jFrame;
    JButton nextbutton;
    JButton preButton;
//    JPanel cardPanel;
    JPanel cardPanel;
    JPanel controlpaPanel;
    CardLayout card;
    //定义构造函数
    public Cardlayout() {
        initUI();
        addComponentUI();
        addListenerUI();
        showFrameUI();
    }
    public void initUI() {
        jFrame=new JFrame();
        nextbutton=new JButton();
        preButton=new JButton();
        cardPanel=new JPanel();
        controlpaPanel=new JPanel();
        card=new CardLayout();
    }
    public void addComponentUI() {
        //循环，在cardPanel面板对象中添加五个按钮
        //因为cardPanel面板对象为卡片布局，因此只显示最先添加的组件
        for (int i = 0; i < 5; i++) {
            cardPanel.add(new JButton("按钮" + i));
        }
        //实例化按钮对象
        nextbutton = new JButton("下一张卡片");
        preButton = new JButton("上一张卡片");
        //为按钮对象注册监听器
        nextbutton.addActionListener(this);
        preButton.addActionListener(this);
        jFrame.setLayout(new BorderLayout());
//        controlpaPanel.setLayout(null);
        BorderLayout borderLayout=new BorderLayout();
        controlpaPanel.add(preButton);
        preButton.setPreferredSize(new Dimension(100,50));
//        preButton.setBounds(0,0,15,10);
        controlpaPanel.add(nextbutton,BorderLayout.EAST);
        //定义容器对象为当前窗体容器对象
        Container container=getContentPane();
//        container.add(cardPanel); //将label增加到窗口
        jFrame.add(cardPanel,BorderLayout.NORTH);
        cardPanel.setBounds(10,10,100,50);   //
         //将controlpaPanel面板放置在窗口边界布局的南边，
//        jFrame.add(controlpaPanel,BorderLayout.SOUTH);
        jFrame.add(controlpaPanel,BorderLayout.SOUTH);
        controlpaPanel.setBounds(10,70,150,200);
    }
    public void addListenerUI() {

    }
    public void showFrameUI() {
        jFrame.setTitle("卡片布局管理器");
        jFrame.setSize(300, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        //设置cardPanel面板对象为卡片布局
        cardPanel.setLayout(card);
    }
    //实现按钮的监听触发时的处理
    public void actionPerformed(ActionEvent e){
        //如果用户单击nextbutton，执行的语句
        if (e.getSource()==nextbutton){
            //切换cardPanel面板中当前组件之后的一个组件
            //若当前组件为最后添加的组件，则显示第一个组件，即卡片组件显示是循环的。
            card.next(cardPanel);
        }
        if (e.getSource()==preButton){
            //切换cardPanel面板中当前组件之前的一个组件
            //若当前组件为第一个添加的组件，则显示最后一个组件，即卡片组件显示是循环的。
            card.previous(cardPanel);
        }
    }
    public static void main(String[] args) {
//        cardlayout kapian=new cardlayout();
         //Create the JButton
        Object[] objects=new Object[2];
        objects[0]=1;
        objects[1]=2;
        JFrame jFrame=new JFrame("test事件");
        jFrame.setLayout(new FlowLayout());
        JButton b = new JButton("Button");
        JList myList=new JList(objects);
        jFrame.add(myList);
        myList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object o = myList.getSelectedValue(); //getSelectedItem()
                System.out.println(o.toString());
            }
        });
        jFrame.add(b);
        b.addActionListener(new HelloListener());

        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();  //系统对象获取工具
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        jFrame.setLocation(x,y);  //设置位置
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
//        jFrame.setContentPane(this);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);

    }
}
class HelloListener implements ActionListener {
    //The interface method to receive button clicks
    public void actionPerformed(ActionEvent e) {
        Cardlayout.kk++;
        System.out.println(Cardlayout.kk);
        JOptionPane.showMessageDialog(null, "请至少选中一列");//弹出警告窗口
        String addStr = JOptionPane.showInputDialog("请输入添加的数据！");//弹出输入窗口
    }
}

class CardLayoutTest extends JPanel{
    public CardLayoutTest(){
        JFrame jFrame=new JFrame();
        JLabel jLabel1=new JLabel("第一张");
        JLabel jLabel2=new JLabel("第二张");
        JPanel cardPanel=new JPanel();//显示panel
        CardLayout cardLayout=new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(jLabel1,"第一张");
        cardPanel.add(jLabel2,"第二张");

        JPanel controlPanel=new JPanel();//控制panel
        controlPanel.setLayout(new FlowLayout());
        JButton jButton1=new JButton("选取第二张");
        controlPanel.add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"第二张");//指定某一张
//                cardLayout.next(cardPanel);//下一张
//                cardLayout.previous(cardPanel);//前一张
            }
        });

        jFrame.setContentPane(this);
        setLayout(new BorderLayout());
        add(cardPanel,BorderLayout.NORTH);
        add(controlPanel,BorderLayout.CENTER);

        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();  //系统对象获取工具
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        jFrame.setLocation(x,y);  //设置位置
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
        jFrame.setContentPane(this);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);
    }

}