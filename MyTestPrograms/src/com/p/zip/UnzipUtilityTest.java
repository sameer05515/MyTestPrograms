package com.p.zip;

/**
  
* A console application that tests the UnzipUtility class
 *
 * @author www.codejava.net
 *
 */
public class UnzipUtilityTest {
    public static void main(String[] args) {
        String zipFilePath = "C:\\Prem\\data\\others\\self\\comics\\Hawaldar Bahadur Complete\\HB_Bachchon Ke Chor.cbr";
        String destDirectory = "C:\\Prem\\data\\others\\self\\comics\\Hawaldar Bahadur Complete\\HB_Bachchon Ke Chor";
        UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(zipFilePath, destDirectory);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
    }
}