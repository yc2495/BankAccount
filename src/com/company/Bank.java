package com.company;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Bank {
    private ArrayList<BankAccount> allAccounts;
    private ArrayList<Customer> allCustomers;

    public Bank(){
        allCustomers = new ArrayList<Customer>();
        allAccounts = new ArrayList<BankAccount>();
    }

    public void doBanking(){
        var inputReader = new Scanner(System.in);
        while (true){
            printMenu();
            var userChoice = inputReader.nextInt();
            switch (userChoice){
                case 1:
                    System.exit(0);
                case 2:
                    addCustomer(inputReader);
                    break;
                case 3:
                    System.out.print("What is the customer ID of the customer to select:");
                    var idToFind = inputReader.nextInt();
                    var customer = getCustomer(idToFind);
                    if (customer.isPresent()){
                        doCustomerMenu(inputReader, customer.get());
                    }
                    else
                        System.out.println("No such customer exists at this bank");
                    break;
            }
        }
    }
    private void doCustomerMenu(Scanner inputReader, Customer customer) {
        while(true) {
            printCustomerMenu();
            //ask the user which choice they want and read that in
            System.out.print("Enter selection");
            var choice = inputReader.nextInt();
            //create a switch statement to select among the choices
            switch (choice){
                case 1:
                    BankAccount newAccount = addAccountToCustomer(inputReader, customer);
                    allAccounts.add(newAccount);
                    break;
                case 2:
                    closeAccount(inputReader, customer);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("PLease choose one of the choices in the list");

            }
        }
    }

    private void closeAccount(Scanner inputReader, Customer customer) {
        //ask the user for the account number to close
        System.out.println("Enter id of account to close");
        var closeID = inputReader.nextInt();
        //call close account on the customer
        var closedAccount = customer.closeAccount(closeID);
        //if successful remove the account for allAccounts as well
        if (closedAccount.isPresent()){
            System.out.println("Closeing account ...");
            allAccounts.remove(closedAccount.get());
        }
    }

    private BankAccount addAccountToCustomer(Scanner inputReader, Customer customer) {
        //ask the user how much the initial balance should be
        System.out.println("How much is the initial balance for the new account");
        var initialDeposit = inputReader.nextDouble();
        //call open account in the customer
        var newAccount = customer.openAccount(initialDeposit);
        System.out.println("created account with Id"+ newAccount.getAccountID());
        //return the newly created account
        return newAccount;
    }


    private void printCustomerMenu(){
        System.out.println("################################");
        System.out.println("Please select what to do with this customer");
        System.out.println("   [1] open account");
        System.out.println("   [2] close account");
        System.out.println("   [3] return to main menu");
        System.out.println("################################");
    }
    private Optional<Customer> getCustomer(int CustomerID){
        for(var currentCustomer : allCustomers){
            if(currentCustomer.getID()==CustomerID )
                return Optional.of(currentCustomer);
        }
        return Optional.empty();
    }

    private void addCustomer(Scanner inputReader) {
        inputReader.nextLine();//eats \n from previous to nextInt
        System.out.print("Enter the new Customer's name:");
        var newCustomerName = inputReader.nextLine();
        System.out.print("Enter the new Customer's Tax Id (SSN):");
        var newCustomerTaxId = inputReader.nextInt();
        var newCustomer = new Customer(newCustomerName, newCustomerTaxId);
        allCustomers.add(newCustomer);
        System.out.println("Success! Created new Customer with Name: "+
                newCustomer.getName() + " and taxID: "+ newCustomer.getID());
    }

    private void printMenu() {
        System.out.println("===========================================");
        System.out.println("What do you want to do next:");
        System.out.println("    [1] Exit the program");
        System.out.println("    [2] Add a new Customer");
        System.out.println("    [3] Select customer");
        System.out.println("Type the number of options you want:");
    }
}
