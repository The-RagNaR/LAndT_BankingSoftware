import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TransactionSection extends JFrame implements ActionListener{

    private JLabel bankName, accountNo, money_, alertMsg;
    private JTextArea emailId, sendMoney;
    private JButton transferMoney, goBack;
    String accNumber;
    int balance, rowNum;
    
    TransactionSection(String accNumber,String balance, int rowNum) {

        this.accNumber = accNumber;
        this.balance = Integer.parseInt(balance);
        this.rowNum = rowNum;

        bankName = new JLabel("Bank of Chittoor");
        bankName.setForeground(Color.WHITE);
        bankName.setFont(new Font("Monotype Corsiva", Font.ITALIC, 55));
        bankName.setBounds(300, 20, 1000, 80);  //(x, y, width, height)
        add(bankName);


        accountNo = new JLabel("Receiver Email Id: ");
        accountNo.setForeground(Color.WHITE);
        accountNo.setFont(new Font("Arial", Font.PLAIN, 28));
        accountNo.setBounds(350, 150, 400, 30);
        add(accountNo);


        emailId = new JTextArea();
        emailId.setBounds(580, 150, 390, 30);
        emailId.setForeground(Color.black);
        emailId.setFont(new Font("Arial", Font.PLAIN, 23));
        add(emailId);


        money_ = new JLabel("Money: ");
        money_.setForeground(Color.WHITE);
        money_.setFont(new Font("Arial", Font.PLAIN, 28));
        money_.setBounds(482, 200, 200, 30);
        add(money_);

        sendMoney = new JTextArea();
        sendMoney.setForeground(Color.black);
        sendMoney.setFont(new Font("Arial", Font.PLAIN, 23));
        sendMoney.setBounds(580, 200, 150, 30);
        add(sendMoney);

        alertMsg = new JLabel();
        alertMsg.setFont(new Font("Monotype Corsiva", Font.ITALIC, 16));
        alertMsg.setForeground(Color.RED);
        alertMsg.setBounds(570, 250,450, 30);
        add(alertMsg);

        transferMoney = new JButton();
        transferMoney = new JButton("Pay");
        transferMoney.setForeground(Color.WHITE);
        transferMoney.setBackground(Color.red);
        transferMoney.setBorder(null);
        transferMoney.addActionListener(this);
        transferMoney.setBounds(610, 300, 80, 40);
        add(transferMoney);

        goBack = new JButton("Back");
        goBack.setForeground(Color.WHITE);
        goBack.setBackground(Color.red);
        goBack.setBorder(null);
        goBack.addActionListener(this);
        goBack.setBounds(605, 350, 90, 40);
        add(goBack);


        ImageIcon backgroundImage = new ImageIcon("/media/ragnar/ca023da0-2328-4858-8f08-a69753e22717/Projects/L-T_BankingSoftware/src/Data/Images/Login_BackGround2.jpg");
        Image background = backgroundImage.getImage().getScaledInstance(1000, 650, Image.SCALE_DEFAULT);
        ImageIcon backgrouIcon2 =new ImageIcon(background);
        JLabel imagBack = new JLabel(backgrouIcon2);
        imagBack.setBounds(0, 0, 1000, 650);
        add(imagBack);


        setLayout(null);
        setSize(1000, 650);
        setLocation(380, 150);
        // setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {

        try {
            
            if (ae.getSource() == transferMoney) {

                String userId = emailId.getText().toString();
                String Amount = sendMoney.getText().toString();
                int payAmount = Integer.parseInt(Amount);

                if (payAmount < 0) {
                    alertMsg.setText("Amount can't be less than 0.");
                }
                else if (payAmount > 100000) {
                    alertMsg.setText("Amount is too big.");
                }
                else if (userId.isBlank() || payAmount ==0) {
                    alertMsg.setText("None of the fields can be empty");
                }
                else {
                    executeTransfer(payAmount, userId);
                }
            }
            else if (ae.getSource() == goBack) {
                getCellValue();
                
            }
        } catch(NumberFormatException ne) {
            alertMsg.setText(ne.getMessage());

        } catch (Exception e) {
            alertMsg.setText(e.getMessage());
        }
        
        
    }


    public void executeTransfer(int amount, String userId) {
        XSSFWorkbook workbk = null;
        XSSFSheet sheet = null;
        File excelFile = new File("/media/ragnar/ca023da0-2328-4858-8f08-a69753e22717/Projects/L-T_BankingSoftware/src/Data/UserDetail.xlsx");
    
        try {
            FileInputStream excel = new FileInputStream(excelFile);
            workbk = new XSSFWorkbook(excel);
            sheet = workbk.getSheet("Sheet1");
               
            boolean receiverFound = false;
    
            for (Row receiverRow : sheet) {
                String cell = receiverRow.getCell(3).toString();
                // Searching for the Receiver User ID
                if(balance <= amount){
                    alertMsg.setText("Insufficent Balance !!!");

                }
                else {
                    if (cell.equals(userId)) {
                        receiverFound = true;
                        Row senderRow = sheet.getRow(rowNum);
    
                        // setting new balance for both sender and receiver
                        int senderAmount = balance - amount;
                        String receiverBalance = receiverRow.getCell(6).toString();
                        int receiverAmount = Integer.parseInt(receiverBalance.substring(0, receiverBalance.length()-2)) + amount;
    
                        // setting last Transaction for sender and receiver
                        String senderLastTrans = "-"+String.valueOf(amount);
                        String receiverLastTrans = "+"+String.valueOf(amount);
    
                        String senderName = senderRow.getCell(0).getStringCellValue();
                        String receiverName = receiverRow.getCell(0).getStringCellValue();
    
                        String TransactionTime = " on "+LocalDate.now().toString()+" at "+LocalTime.now().toString().substring(0, 8);
    
                        String receiverTrans = "Received "+String.valueOf(amount)+" from "+senderName;
                        String senderTrans = "Sent "+String.valueOf(amount)+" to "+receiverName;
    
                        // writing the new balance for sender and receiver
                        receiverRow.getCell(6).setCellValue(String.valueOf(receiverAmount));
                        senderRow.getCell(6).setCellValue(String.valueOf(senderAmount));
    
                        // writng the last trasaction 
                        receiverRow.getCell(8).setCellValue(receiverLastTrans);
                        senderRow.getCell(8).setCellValue(senderLastTrans);
    
                        // writing the time of transaction to both sender and receiver rows
                        receiverRow.getCell(9).setCellValue(TransactionTime);
                        senderRow.getCell(9).setCellValue(TransactionTime);
    
                        // writing the transaction detail to bother sender and receiver rows
                        receiverRow.getCell(10).setCellValue(receiverTrans);
                        senderRow.getCell(10).setCellValue(senderTrans);
        
                        try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                            alertMsg.setText("Writing file");
                            workbk.write(fileOut);
                            alertMsg.setText("Writing file successful");
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(this, "Error writing to Excel file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
        
                        alertMsg.setText("Sent " + amount + " to " + receiverName);
                        
                        break;
                    }
                }
            }
    
            if (!receiverFound) {
                alertMsg.setText("Receiver not found");
            }
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (workbk != null) {
                    workbk.close();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    

    public void getCellValue(){
        XSSFWorkbook workbook;
        XSSFSheet sheet;
        File excelFile = new File("/media/ragnar/ca023da0-2328-4858-8f08-a69753e22717/Projects/L-T_BankingSoftware/src/Data/UserDetail.xlsx");

        try{
            
            workbook = new XSSFWorkbook(excelFile);
            sheet = workbook.getSheet("Sheet1");
            Row row = sheet.getRow(rowNum);


            String name_ = row.getCell(0).toString();
            String accNum = row.getCell(1).toString();
            String phoneNo = row.getCell(2).toString();
            String emailId = row.getCell(3).toString();
            String accType = row.getCell(5).toString();
            String balance = row.getCell(6).toString();
            String lastTrans = row.getCell(8).toString();
            String lastTransTime = row.getCell(9).toString();
            String lastTransDetails = row.getCell(10).toString();
            String Password_ = row.getCell(4).toString();

            workbook.close();
            String finalBalance = balance.substring(0, balance.length()-2);
            new AccountDetail(name_, accNum, phoneNo, emailId, accType, finalBalance, lastTrans, lastTransTime, lastTransDetails, rowNum, Password_);
            dispose();

        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}