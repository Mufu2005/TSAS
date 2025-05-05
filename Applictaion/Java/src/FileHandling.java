import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileHandling {

    String name, uid;

    // ---------------------readFileLogIn--------------------------//
    public Boolean readFileLogIn(String name, String passs) {
        try {
            File file = new File("Txt files\\LogIn.txt");
            Scanner read = new Scanner(file);
            // read.useDelimiter("|");

            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String user = parts[0].trim();
                    String pass = parts[1].trim();
                    if (hashing(name).equals(user) && hashing(passs).equals(pass)) {
                        return true;
                    }

                } else {
                }
            }
            read.close();
        } catch (FileNotFoundException e) {
            return false;
        }

        return false;
    }

    // ---------------------readUidHoldersFromFile--------------------------//
    public String readUidHolderFromFile(String scanUid) {
        try {
            File file = new File("Txt files\\Workers.txt");
            Scanner read = new Scanner(file);
            read.useDelimiter("\\|");

            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 2 && parts[1].equals(scanUid)) {
                    this.name = parts[0].trim();
                    this.uid = parts[1].trim();

                } else {
                }
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return "error";
        }
        return "Name: " + name + ", UID: " + uid;
    }

    // ---------------------writeFileSignIn--------------------------//
    public void writeFileSignIn(String user, String pass) {

        try {
            FileWriter write = new FileWriter("Txt files\\LogIn.txt", true);
            write.write(System.lineSeparator() + hashing(user) + "|" + hashing(pass));
            write.close();
        } catch (IOException e) {
            System.out.println("file no found");
        }
    }

    // ---------------------writeUidHoldersToFile--------------------------//
    public void writeUidHoldersToFile(String name, String uid) {

        try {
            FileWriter write = new FileWriter("Txt files\\Workers.txt", true);
            write.write(name + "|" + uid + System.lineSeparator());
            write.close();
            JOptionPane.showMessageDialog(null, "Employe " + name + " with uid " + uid + " added succesfully",
                    "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file no found");
        }
    }

    // ---------------------WriteToExcel--------------------------//
    public void writeToExcel(String name, String uid) throws IOException {

        String path = "Excel files\\Attendence.xlsx";
        FileOutputStream outputStream = null;
        FileInputStream fis = null;
        File file = new File(path);
        XSSFWorkbook book = null;
        XSSFSheet sheet;

        if (file.exists()) {
            fis = new FileInputStream(path);
            book = new XSSFWorkbook(fis);
            fis.close();
        } else {
            book = new XSSFWorkbook();
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
        String date = now.format(formatter);
        DateTimeFormatter formatte = DateTimeFormatter.ofPattern("HH:mm");
        String time = now.format(formatte);

        if (book.getSheet(date) == null) {
            sheet = book.createSheet(date);
            System.out.println("sheet created");
        } else {
            sheet = book.getSheet(date);
            System.out.println("sheet exists");
        }

        int lastRownum = sheet.getLastRowNum();
        XSSFRow row = sheet.createRow(lastRownum + 1);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(time);
        cell = row.createCell(1);
        cell.setCellValue(name);
        cell = row.createCell(2);
        cell.setCellValue(uid);

        outputStream = new FileOutputStream(path);
        book.write(outputStream);
        outputStream.close();
        book.close();

    }

    // ---------------------Hashing--------------------------//
    public String hashing(String message){
        BigInteger intConverter = null;
        try{
        MessageDigest hasher = MessageDigest.getInstance("MD5");
        byte[] hashedMessage = hasher.digest(message.getBytes());
        intConverter = new BigInteger(1,hashedMessage);
    }
    catch(NoSuchAlgorithmException e){
        e.printStackTrace();
    }
    return intConverter.toString(16);

}
}
