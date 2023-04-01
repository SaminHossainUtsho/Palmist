package daily;
import java.awt.Color;import java.awt.Container;import java.awt.Font;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.io.BufferedWriter;import java.io.File;import java.io.FileNotFoundException;import java.io.FileWriter;import java.io.IOException;import java.text.SimpleDateFormat;import java.util.Date;import java.util.Scanner;import javax.swing.JButton;import javax.swing.JComboBox;import javax.swing.JFrame;import javax.swing.JLabel;import javax.swing.JOptionPane;import javax.swing.JScrollPane;import javax.swing.JTable;import javax.swing.JTextField;import javax.swing.table.DefaultTableModel;
public class Palmist  extends JFrame
{
        JTextField date;JLabel jb1,jb2,jb3,tot;JComboBox amount,lost;JTable table; JScrollPane scroll; JButton ab,db,clr; DefaultTableModel model;
       int i;  String col[] = new String[3]; 
       String total[] = {"1","0"} ,   row[] = {"Day","Date","Times"},spoiled[] = {"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
         Palmist()
         {
              Container con = this.getContentPane();con.setLayout(null);con.setBackground(Color.GRAY);                    
              Font font = new Font("Digital-7",Font.PLAIN,20);
              lost = new JComboBox(spoiled);lost.setBounds(115,30,160,36);lost.setEditable(true);lost.setFont(font);con.add(lost);
              Date dt = new Date();
              SimpleDateFormat time = new SimpleDateFormat("dd MM YYY");
              date = new JTextField();date.setBounds(115,90,160,35);date.setFont(font);date.setText(time.format(dt));date.setForeground(Color.yellow);date.setBackground(Color.DARK_GRAY);con.add(date); 
              amount = new JComboBox(total);amount.setBounds(115,150,160,35);amount.setFont(font);amount.setForeground(Color.CYAN);amount.setBackground(Color.WHITE); con.add(amount); 
              jb1 = new JLabel("Day          :");jb1.setBounds(10,0,200,100);jb1.setFont(font); jb1.setForeground(Color.white);con.add(jb1);
              jb2 = new JLabel("Date        :");jb2.setBounds(10,55,200,100);jb2.setFont(font); jb2.setForeground(Color.white);con.add(jb2);
              jb3 = new JLabel("Count  :");jb3.setBounds(10,115,200,100);jb3.setFont(font); jb3.setForeground(Color.white);con.add(jb3);
              ab = new JButton("ADD");ab.setBounds(310,50,70,30);con.add(ab);db = new JButton("WIPE"); db.setBounds(310,150,70,30); con.add(db);clr = new JButton("CLEAR"); clr.setBounds(310,100,70,30); con.add(clr);//view = new JRadioButton();view.setBounds(0,1,22,25); con.add(view);
              table = new JTable(); model = new DefaultTableModel();model.setColumnIdentifiers(row);table.setModel(model);table.setBackground(Color.BLACK);table.setForeground(Color.WHITE);table.setFont(font);table.setRowHeight(25);scroll = new JScrollPane(table); scroll.setBounds(0,195,456,385);con.add(scroll);
              File file = new File("E:/palmist.txt");
             try {
                  Scanner sc = new Scanner(file);
                  while(sc.hasNext())
                    {
                     col[0] = sc.next();  col[1] = sc.next();  col[2] = sc.next();    
                     model.addRow(col); 
                    }                 
                } catch (FileNotFoundException ex){ } 
              Font ft = new Font("Tahoma",Font.BOLD,20);
              tot = new JLabel();tot.setBounds(50,545,300,100);tot.setFont(ft);tot.setForeground(Color.YELLOW);con.add(tot);
              button_group bg = new button_group();
              bg.update_total();
              ab.addActionListener(bg);
              clr.addActionListener(bg);
              db.addActionListener(bg);
              lost.requestFocusInWindow();
         }
       class  button_group implements ActionListener
      {
                  public void actionPerformed(ActionEvent ae)
                      {
                            if(ae.getSource()==ab)
                            {
                                col[0] =  lost.getSelectedItem().toString().replace(' ','-');
                                col[1] = date.getText().replace(" ","/");
                                col[2] = (String) amount.getSelectedItem();
                                                     
                                model.addRow(col);
                                update_total();
                                try {
                                    FileWriter fw  = new FileWriter("E:/palmist.txt",true);
                                    try (BufferedWriter bw = new BufferedWriter(fw))
                                        {
                                                 bw.write(col[0]+" "+col[1]+" "+col[2]+"\n");
                                        } 
                                     } catch (IOException ex) { } 
                                }
                                
                            if(ae.getSource()==clr)
                            {
                                amount.setSelectedItem("");
                                lost.setSelectedItem("");
                                table.clearSelection();
                            }
                            if(ae.getSource()==db)
                            {
                            while(table.getSelectedRow()>=0)
                                {            
                                     model.removeRow(table.getSelectedRow());        
                                }
                             update_total();
                             update_file();
                            }
                      }
      private void update_file()
        {
          try {
              FileWriter fw  = new FileWriter("E:/palmist.txt",false);
              try (BufferedWriter bw = new BufferedWriter(fw))
                {
              for(i=0; i<table.getRowCount();i++)                                          
                         bw.write((table.getValueAt(i, 0))+" "+(table.getValueAt(i, 1))+" "+(table.getValueAt(i, 2))+"\n");
                }
              } catch (IOException ex){}
        } 
       private void update_total()
       {
            long  total=0;
            for(i=0; i<table.getRowCount();i++) 
            {
                  total +=  Integer.parseInt((String) table.getValueAt(i, 2));     
            }
            tot.setText("Total Count  "+total+" Days");
       }
       
     }
    public static void main(String[] args) throws Exception
    {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            {
                 if ("Nimbus".equals(info.getName())) 
                    {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());  break; 
                    }
            }
      Palmist  ob = new  Palmist();
      ob.setVisible(true);
      ob.setBounds(460,25,460,660);
      ob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ob.setResizable(false);
      ob.requestFocusInWindow();
    }   
}
