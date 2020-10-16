package com.study.usefulknowledge.UI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * 常用功能：
 * 1、选取文件，保存文件，选取器FileDialog(JFileChooser)    这里找了个FileDialog，也可以实现打开关闭文件窗口
 * 2、提示窗口JDialog、JOptionPane
 * 3、鼠标放在组件上面，提示说明
 * 4、观感器，皮肤
 * 5、字符串\组件字体
 * 6、组件边框
 * 7、图标、图片
 * 8、焦点，当窗口被激活时候重新设置，如未设置，则视为失去焦点时的焦点组件
 * 9、绑定键：键盘快捷键
 * 10、子窗口模态设置（打开窗口必须关闭）
 * 11、外部文件的拖拽
 * 12、系统托盘：关闭后变成小图标
 * 13、SplashScreen  闪现屏幕   用于开启程序前等待
 * 14、并行、多线程
 * 15、2d 画图
 * 16、界面fram的常规设置
 *
 */
public class ComponentTest2  {
    final static int WIDTH=500;
    final static int HEIGHT=500;
    private MenuBar bar;// 定义菜单栏
    private Menu fileMenu;// 定义"文件"和"子菜单"菜单
    private MenuItem openItem, saveItem, closeItem;// 定义条目“退出”和“子条目”菜单项
    private FileDialog openDia, saveDia;// 定义“打开、保存”对话框
    private File file;//定义文件
    ComponentTest2(){
        JFrame jFrame=new JFrame();
        JPanel jPanels=new JPanel();
/****  1 、**********************选取器*/
        TextArea ta = new TextArea();// 创建文本域
        bar = new MenuBar();// 创建菜单栏
        fileMenu = new Menu("文件");// 创建“文件”菜单
        openItem = new MenuItem("打开");// 创建“打开"菜单项
        saveItem = new MenuItem("保存");// 创建“保存"菜单项
        closeItem = new MenuItem("退出");// 创建“退出"菜单项
        fileMenu.add(openItem);// 将“打开”菜单项添加到“文件”菜单上
        fileMenu.add(saveItem);// 将“保存”菜单项添加到“文件”菜单上
        fileMenu.add(closeItem);// 将“退出”菜单项添加到“文件”菜单上
        bar.add(fileMenu);// 将文件添加到菜单栏上
        jFrame.setMenuBar(bar);// 将此窗体的菜单栏设置为指定的菜单栏。
        openDia = new FileDialog(jFrame, "打开", FileDialog.LOAD);
        saveDia = new FileDialog(jFrame, "保存", FileDialog.SAVE);
        //事件
        openItem.addActionListener(new ActionListener() {      // 打开菜单项监听
            public void actionPerformed(ActionEvent e) {
                openDia.setVisible(true);//显示打开文件对话框
                String dirpath = openDia.getDirectory();//获取打开文件路径并保存到字符串中。
                String fileName = openDia.getFile();//获取打开文件名称并保存到字符串中
                if (dirpath == null || fileName == null)//判断路径和文件是否为空
                    return;
                else
                    ta.setText(null);//文件不为空，清空原来文件内容。
                File  file = new File(dirpath, fileName);//创建新的路径和名称
                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(file));//尝试从文件中读东西
                    String line = null;//变量字符串初始化为空
                    while ((line = bufr.readLine()) != null) {
                        ta.append(line + "\r\n");//显示每一行内容
                    }
                    bufr.close();//关闭文件
                } catch (FileNotFoundException e1) {
                    // 抛出文件路径找不到异常
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // 抛出IO异常
                    e1.printStackTrace();
                }
            }
        });
        saveItem.addActionListener(new ActionListener() {   // 保存菜单项监听
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    saveDia.setVisible(true);//显示保存文件对话框
                    String dirpath = saveDia.getDirectory();//获取保存文件路径并保存到字符串中。
                    String fileName = saveDia.getFile();////获取打保存文件名称并保存到字符串中

                    if (dirpath == null || fileName == null)//判断路径和文件是否为空
                        return;//空操作
                    else
                        file=new File(dirpath,fileName);//文件不为空，新建一个路径和名称
                }
                try {
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(file));

                    String text = ta.getText();//获取文本内容
                    bufw.write(text);//将获取文本内容写入到字符输出流

                    bufw.close();//关闭文件
                } catch (IOException e1) {
                    //抛出IO异常
                    e1.printStackTrace();
                }
            }
        });
        closeItem.addActionListener(new ActionListener() {// 退出菜单项监听
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jFrame.addWindowListener(new WindowAdapter() {// 窗体关闭监听
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
/****  2 、**********************提示窗口JOptionPane*/
        JButton jButton=new JButton();
        jPanels.add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //单个选项yes
                JOptionPane.showMessageDialog(null, "提示消息", "标题",JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(null, "提示消息.", "标题",JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null, "提示消息.", "标题",JOptionPane.PLAIN_MESSAGE);
                //双选yes和no
                int n = JOptionPane.showConfirmDialog(null, "你高兴吗?", "标题",JOptionPane.YES_NO_OPTION);//i=0/1
                //自己定义按键名称
                Object[] options ={ "好啊！", "去一边！" };
                int m = JOptionPane.showOptionDialog(null, "我可以约你吗？", "标题",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                //下拉框选择
                Object[] obj2 ={ "足球", "篮球", "乒乓球" };
                String s = (String) JOptionPane.showInputDialog(null,"请选择你的爱好:\n", "爱好", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), obj2, "足球");
                //输入框形式的提示
                JOptionPane.showInputDialog(null,"请输入你的爱好：\n","title",JOptionPane.PLAIN_MESSAGE,new ImageIcon("icon.png"),null,"在这输入");
            }
        });
/****  3 、**********************鼠标放在组件上面，提示说明*/
        jButton.setToolTipText("这里是提示内容");
/****  4 、**********************观感器*/
    //注意：一般写在开头，不然有可能被java观感器覆盖
    //方法1、本机自带皮肤，可查看JLookAndFeelMenu
    //方法2、导入jar的皮肤
        try {
            UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel"); //自己下载的jar包，里面以LookAndFeel结尾的
            System.out.println(1);
        } catch (Exception e) {}
        SwingUtilities.updateComponentTreeUI(jFrame);
/****  5 、**********************字符串、组件字体*/
        //字符串
        JButton jButton1=new JButton("<html><b>第一个组件</b>"+"<font color=red>红色</font></html>");
        JTextField jTextField=new JTextField();
        jTextField.setText(String.format("%d",1.5));
        //组件
        UIManager.put("ComboBox.font",new Font("微软雅黑",0,12));
/****  6 、**********************组件边框*/
        jButton1.setBorder(BorderFactory.createLineBorder(Color.red,3));  //线框
        jButton1.setBorder(BorderFactory.createTitledBorder("分组框"));  //带标题的（左）
        //////
        TitledBorder tb = BorderFactory.createTitledBorder("标题");  //带标题（右）
        tb.setTitleJustification(TitledBorder.RIGHT);
        //////
        jButton1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  //
        jButton1.setBorder(BorderFactory.createEtchedBorder());  //蚀刻边框
        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());  //斜面边框(凸)
        jButton1.setBorder(BorderFactory.createLoweredBevelBorder());  //斜面边框(凹)
        jButton1.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.yellow));  //花色边框
        /////
        Border b1 = BorderFactory.createLineBorder(Color.blue, 2);  //组合边框
        Border b2 = BorderFactory.createEtchedBorder();
        jButton1.setBorder(BorderFactory.createCompoundBorder(b1, b2));
        /////
/****  7 、**********************图标、图片*/
        JLabel label = new JLabel(new ImageIcon("F:\\chatServer\\images\\1.jpg"));
/****  8 、**********************焦点，当窗口被激活时候重新设置，如未设置，则视为失去焦点时的焦点组件*/
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                jButton1.requestFocusInWindow();
            }
        });
/****  9 、**********************绑定键*/
        JButton jButton2=new JButton("绑定键");
        jButton2.getInputMap(2).put(KeyStroke.getKeyStroke("B"),"Open");//对jButton9设置快捷键B
        jButton2.getActionMap().put("Open",new buttonAction("绑定键"));//对快捷键实现事件要重新写，
        // 不能认为之前写了按键的事件就不写，快捷键和Action事件计算机认为是两个监听方式
/****  10 、**********************子窗口模态设置（打开窗口必须关闭）*/
        JFrame jFrame1=new JFrame();
        JDialog jd=new JDialog(jFrame1, JDialog.ModalityType.APPLICATION_MODAL);
        //APPLICATION_MODAL    阻塞同一 Java 应用程序中的所有顶层窗口  （常用）
        //DOCUMENT_MODAL    阻塞对同一文档中所有顶层窗口的输入
        //MODELESS        不阻塞任何顶层窗口
        //TOOLKIT_MODAL   阻塞从同一工具包运行所有顶层窗口
        jd.setVisible(true);

/****  11 、**********************外部文件的拖拽*/
        JTextField  field = new JTextField();
        field.setBounds(50, 50, 300, 30);
        field.setTransferHandler(new TransferHandler()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor); //获取数据的路径
                    String filepath = o.toString();

                    if (filepath.startsWith("[")) {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]")) {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }
                    System.out.println(filepath);
                    field.setText(filepath);
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (int i = 0; i < flavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                        return true;
                    }
                }
                return false;
            }
        });
/****  12 、**********************系统托盘：关闭后变成小图标*/
        JFrame jFrame2=new JFrame();      //将jFrame2设置为系统托盘功能
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (SystemTray.isSupported()) { // 判断系统是否支持托盘功能.
            // 创建托盘右击弹出菜单
            PopupMenu popupMenu = new PopupMenu();
            //创建弹出菜单中的退出项
            MenuItem itemExit = new MenuItem("out");
            itemExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            popupMenu.add(itemExit);
            //创建托盘图标
            ImageIcon icon = new ImageIcon("F:\\chatServer\\images\\2.JPG"); // 创建图片对象
            TrayIcon trayIcon = new TrayIcon(icon.getImage(), "测试系统托盘", popupMenu);//设置托盘的图标、提示、右键
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jFrame2.setVisible(true);
                }
            });
            //把托盘图标添加到系统托盘
            //这个可以点击关闭之后再放到托盘里面，在此是打开程序直接显示托盘图标了
            try {
                SystemTray.getSystemTray().add(trayIcon);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
/****  13 、**********************   SplashScreen  闪现屏幕   用于开启程序前等待*/

/****  14 、**********************   并行、多线程*/
//     查看   TestSwingWorker
/****  15 、**********************   2d 画图*/
        //查看 paint
//     查看  DrawingExample 的Graphics2D



/****  16 、**********************   fram的常规设置*/
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();  //系统对象获取工具
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        jFrame.setLocation(x,y);  //设置位置
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
        jFrame.setContentPane(jPanels);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);
    }
}
class buttonAction extends AbstractAction{
    buttonAction(String componentName){  //比如jButton9的num9
        super(componentName);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("测试快捷键");
    }
}
