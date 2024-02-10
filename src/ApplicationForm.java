import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


class ApplicationForm extends JFrame implements ActionListener{

    private JLabel bankName;
    private JLabel name, Mobile, Email, AccType, InitialDeposite, WarningMsg, password;
    private JTextField nameInput, MobileInput, EmailInput, InitialDepositeInput, passwordInput;
    private JComboBox<String> AccountType;
    private JButton Submit, clear;

    ApplicationForm()
    {
        bankName = new JLabel("Bank of Chittoor");
        bankName.setForeground(Color.WHITE);
        bankName.setFont(new Font("Monotype Corsiva", Font.ITALIC, 55));
        bankName.setBounds(300, 20, 1000, 80);  //(x, y, width, height)
        add(bankName);

        
        // Name Entry Sections
        name = new JLabel("Name: ");
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.PLAIN, 28));
        name.setBounds(580, 150, 200, 30);
        add(name);

        nameInput = new JTextField();
        nameInput.setBounds(665, 150, 325, 30);
        add(nameInput);


        // Mobile NUmber Entry Section
        Mobile = new JLabel("Mobile No.: ");
        Mobile.setForeground(Color.WHITE);
        Mobile.setFont(new Font("Arial", Font.PLAIN, 28));
        Mobile.setBounds(521, 200, 200, 30);
        add(Mobile);

        MobileInput = new JTextField();
        MobileInput.setBounds(665, 200, 325, 30);
        add(MobileInput);


        // Email Entry Section
        Email = new JLabel("Email: ");
        Email.setForeground(Color.WHITE);
        Email.setFont(new Font("Arial", Font.PLAIN, 28));
        Email.setBounds(584, 250, 200, 30);
        add(Email);

        EmailInput = new JTextField();
        EmailInput.setBounds(665, 250 ,325, 30);
        add(EmailInput);


        // Account Type selection section
        AccType = new JLabel("Account Type: ");
        AccType.setForeground(Color.WHITE);
        AccType.setFont(new Font("Arial", Font.PLAIN, 28));
        AccType.setBounds(483, 300, 200, 30);
        add(AccType);
        // C to select Account Type
        String[] genderOptions = {"Saving", "Current"};
        AccountType = new JComboBox<>(genderOptions);
        AccountType.setBounds(665, 300, 140, 30);
        AccountType.setFont(new Font("Arial", Font.PLAIN, 20));
        AccountType.setForeground(Color.BLACK);
        add(AccountType);

        // Initial Deposite section 
        InitialDeposite = new JLabel("Initial Deposite: ");
        InitialDeposite.setForeground(Color.WHITE);
        InitialDeposite.setFont(new Font("Arial", Font.PLAIN, 28));
        InitialDeposite.setBounds(470, 350, 200, 30);
        add(InitialDeposite);

        InitialDepositeInput = new JTextField();
        InitialDepositeInput.setBounds(665, 350, 325, 30);
        add(InitialDepositeInput);

        // Section to create password for further Login
        password = new JLabel("Create Password");
        password.setForeground(Color.WHITE);
        password.setFont(new Font("Arial", Font.PLAIN, 28));
        password.setBounds(470, 400, 200, 30);
        add(password);

        passwordInput = new JTextField();
        passwordInput.setBounds(665, 400, 325, 30);
        add(passwordInput);

        // Button to submit all the data to open a new Bank account
        Submit = new JButton("Submit");
        Submit.setForeground(Color.WHITE);
        Submit.setBackground(Color.RED);
        Submit.setBorder(null);
        Submit.setBounds(550, 500, 150, 40);
        Submit.addActionListener(this); 
        add(Submit);

        // Button to clear all the fields
        clear = new JButton("Clear All");
        clear.setForeground(Color.WHITE);
        clear.setBackground(Color.RED);
        clear.setBorder(null);
        clear.setBounds(720, 500, 150, 40);
        clear.addActionListener(this); 
        add(clear);

        // Warning Message
        WarningMsg = new JLabel();
        WarningMsg.setFont(new Font("Monotype Corsiva", Font.ITALIC, 16));
        WarningMsg.setForeground(Color.RED);
        WarningMsg.setBounds(200, 250, 500, 300);
        add(WarningMsg);

        // Setting up the background Image
        ImageIcon backgroundImage = new ImageIcon("//media/ragnar/ca023da0-2328-4858-8f08-a69753e22717/Projects/L-T_BankingSoftware/src/Data/Images/Login_BackGround2.jpg");
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
            if(ae.getSource() == Submit) {
                String Name_ = nameInput.getText().toString();
                String MobileNo_ = MobileInput.getText().toString();
                String Email_ = EmailInput.getText().toString();
                String InitialDeposite_ =InitialDepositeInput.getText().toString();
                String Password = passwordInput.getText().toString();
                nameInput.setText("");
                MobileInput .setText(""); 
                EmailInput.setText("");
                InitialDepositeInput.setText("");
                WarningMsg.setText("");
                passwordInput.setText("");
                
                String AccType_ = (String) AccountType.getSelectedItem();

                String Account_No = "00100501227879";
                if(Name_.isBlank() || MobileNo_.isBlank() || Email_.isBlank() || InitialDeposite_.isBlank() ) {
                    WarningMsg.setText("None of the fields should be empty");
                }
                else if(Integer.parseInt(InitialDeposite_) < 1000 ) {
                    WarningMsg.setText("Initial deposite must be greater than 1000");
                }
                WarningMsg.setText(Name_+"\t"+MobileNo_+"\t"+Email_+"\t"+InitialDeposite_+"\t"+Password+"\t"+Account_No+"\t"+AccType_);
                // File excelFile = new File("/media/ragnar/ca023da0-2328-4858-8f08-a69753e22717/Projects/L-T_BankingSoftware/src/Data/UserDetail.xlsx");
                try(XSSFWorkbook workbk = new XSSFWorkbook()) {
                
                XSSFSheet sheet = workbk.getSheetAt(0);
                int rowIndex = 5;

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        if (cell.getCellType() != CellType.BLANK) {
                            rowIndex++;
                        }
                    }
                }
                Row headerRow = sheet.createRow(rowIndex);
                headerRow.createCell(0).setCellValue(Name_);
                headerRow.createCell(1).setCellValue(Account_No);
                headerRow.createCell(2).setCellValue(MobileNo_);
                headerRow.createCell(3).setCellValue(Email_);
                headerRow.createCell(4).setCellValue(Password);
                headerRow.createCell(5).setCellValue(AccType_);
                headerRow.createCell(6).setCellValue(InitialDeposite_);
                headerRow.createCell(7).setCellValue(InitialDeposite_);

                try (FileOutputStream fileOut = new FileOutputStream("/media/ragnar/ca023da0-2328-4858-8f08-a69753e22717/Projects/L-T_BankingSoftware/src/Data/UserDetail.xlsx")) {
                    workbk.write(fileOut);
                }
               } catch (IOException e) {
                    System.out.println(e);
               }

                
            }
            else if (ae.getSource() == clear) {
                nameInput.setText("");
                MobileInput .setText(""); 
                EmailInput.setText("");
                InitialDepositeInput.setText("");
                passwordInput.setText("");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        new ApplicationForm();
    }
}