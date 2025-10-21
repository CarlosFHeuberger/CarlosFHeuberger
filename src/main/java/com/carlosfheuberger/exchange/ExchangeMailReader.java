package com.carlosfheuberger.exchange;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to read emails from an Exchange server folder.
 */
public class ExchangeMailReader {

    private final ExchangeService service;

    /**
     * Creates a new ExchangeMailReader with the specified credentials and server URL.
     *
     * @param serverUrl The URL of the Exchange server (e.g., https://outlook.office365.com/EWS/Exchange.asmx)
     * @param username  The username for authentication
     * @param password  The password for authentication
     * @throws Exception if connection fails
     */
    public ExchangeMailReader(String serverUrl, String username, String password) throws Exception {
        this.service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(username, password);
        service.setCredentials(credentials);
        service.setUrl(new URI(serverUrl));
    }

    /**
     * Reads emails from the Inbox folder.
     *
     * @param maxEmails Maximum number of emails to retrieve
     * @return List of EmailMessage objects
     * @throws Exception if reading fails
     */
    public List<EmailMessage> readInbox(int maxEmails) throws Exception {
        return readFolder(WellKnownFolderName.Inbox, maxEmails);
    }

    /**
     * Reads emails from a specified well-known folder.
     *
     * @param folderName The well-known folder name (e.g., Inbox, SentItems, DeletedItems)
     * @param maxEmails  Maximum number of emails to retrieve
     * @return List of EmailMessage objects
     * @throws Exception if reading fails
     */
    public List<EmailMessage> readFolder(WellKnownFolderName folderName, int maxEmails) throws Exception {
        List<EmailMessage> emails = new ArrayList<>();
        
        ItemView view = new ItemView(maxEmails);
        FindItemsResults<Item> findResults = service.findItems(folderName, view);
        
        for (Item item : findResults.getItems()) {
            if (item instanceof EmailMessage) {
                EmailMessage email = (EmailMessage) item;
                email.load(PropertySet.FirstClassProperties);
                emails.add(email);
            }
        }
        
        return emails;
    }

    /**
     * Reads a specific email by its ID.
     *
     * @param itemId The unique identifier of the email
     * @return The EmailMessage object
     * @throws Exception if reading fails
     */
    public EmailMessage readEmailById(ItemId itemId) throws Exception {
        EmailMessage email = EmailMessage.bind(service, itemId);
        email.load(PropertySet.FirstClassProperties);
        return email;
    }

    /**
     * Prints basic information about an email.
     *
     * @param email The email to print information about
     * @throws Exception if accessing properties fails
     */
    public static void printEmailInfo(EmailMessage email) throws Exception {
        System.out.println("Subject: " + email.getSubject());
        System.out.println("From: " + email.getFrom().getAddress());
        System.out.println("Date: " + email.getDateTimeReceived());
        System.out.println("Has Attachments: " + email.getHasAttachments());
        System.out.println("--------------------");
    }

    /**
     * Example usage of the ExchangeMailReader.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Example configuration - replace with your actual values
            String serverUrl = "https://outlook.office365.com/EWS/Exchange.asmx";
            String username = "your.email@example.com";
            String password = "your-password";

            ExchangeMailReader reader = new ExchangeMailReader(serverUrl, username, password);
            
            // Read up to 10 emails from inbox
            List<EmailMessage> emails = reader.readInbox(10);
            
            System.out.println("Found " + emails.size() + " emails:");
            for (EmailMessage email : emails) {
                printEmailInfo(email);
            }
            
        } catch (Exception e) {
            System.err.println("Error reading emails: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
