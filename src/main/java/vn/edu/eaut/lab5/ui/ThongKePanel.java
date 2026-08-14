package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ThongKePanel extends JPanel {
    private final JTextField txtFrom = new JTextField(LocalDate.now().withDayOfMonth(1).toString(),10);
    private final JTextField txtTo = new JTextField(LocalDate.now().toString(),10);
    private final JLabel lblDoanhThu = new JLabel("Doanh thu: -");
    private final JTextArea result = new JTextArea(12,60);
    private final ThongKeBUS bus = new ThongKeBUS();

    public ThongKePanel() {
        setLayout(new BorderLayout(8,8));
        JPanel top=new JPanel();
        top.add(new JLabel("Tu ngay (yyyy-MM-dd):"));top.add(txtFrom);
        top.add(new JLabel("Den ngay:"));top.add(txtTo);

        JButton doanhThu=new JButton("Tinh doanh thu");
        JButton maxHd=new JButton("Hoa don cao nhat");
        JButton bestSp=new JButton("San pham ban chay");
        top.add(doanhThu);top.add(maxHd);top.add(bestSp);

        result.setEditable(false);
        lblDoanhThu.setFont(lblDoanhThu.getFont().deriveFont(Font.BOLD,16f));

        JPanel center=new JPanel(new BorderLayout());
        center.add(lblDoanhThu,BorderLayout.NORTH);
        center.add(new JScrollPane(result),BorderLayout.CENTER);

        add(top,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);

        doanhThu.addActionListener(e->loadRevenue());
        maxHd.addActionListener(e->loadText("Hoa don cao nhat"));
        bestSp.addActionListener(e->loadText("San pham ban chay"));
    }

    private void loadRevenue() {
        LocalDate from,to;
        try{
            from=LocalDate.parse(txtFrom.getText().trim());
            to=LocalDate.parse(txtTo.getText().trim());
        }catch(Exception ex){
            MessageUtil.error(this,new IllegalArgumentException("Ngay phai theo dinh dang yyyy-MM-dd."));
            return;
        }

        lblDoanhThu.setText("Dang tinh...");
        new SwingWorker<BigDecimal,Void>(){
            protected BigDecimal doInBackground() throws Exception{
                return bus.tinhDoanhThu(from,to);
            }
            protected void done(){
                try{lblDoanhThu.setText("Doanh thu: "+get()+" VND");}
                catch(Exception e){MessageUtil.error(ThongKePanel.this,e);}
            }
        }.execute();
    }

    private void loadText(String type) {
        result.setText("Dang tai...");
        new SwingWorker<String,Void>(){
            protected String doInBackground() throws Exception{
                return type.startsWith("Hoa") ? bus.hoaDonCaoNhat() : bus.sanPhamBanChay();
            }
            protected void done(){
                try{result.setText(type+":\n"+get());}
                catch(Exception e){MessageUtil.error(ThongKePanel.this,e);}
            }
        }.execute();
    }
}
