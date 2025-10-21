## Hi there 👋

<!--
**CarlosFHeuberger/CarlosFHeuberger** is a ✨ _special_ ✨ repository because its `README.md` (this file) appears on your GitHub profile.

Here are some ideas to get you started:

- 🔭 I’m currently working on ...
- 🌱 I’m currently learning ...
- 👯 I’m looking to collaborate on ...
- 🤔 I’m looking for help with ...
- 💬 Ask me about ...
- 📫 How to reach me: ...
- 😄 Pronouns: ...
- ⚡ Fun fact: ...
-->

---

# Exchange Mail Reader

A Java application to read emails from Microsoft Exchange server folders using the Exchange Web Services (EWS) Java API.

## Features

- Connect to Exchange Server (on-premises or Office 365)
- Read emails from Inbox and other well-known folders
- Retrieve email properties (subject, sender, date, attachments info)
- Configurable through properties file or programmatically
- Support for Exchange 2010 SP2 and later versions

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Access to an Exchange server (on-premises or Office 365)
- Valid Exchange credentials

## Building the Project

```bash
mvn clean compile
```

## Usage

### Option 1: Using Configuration File

1. Copy the example configuration file:
   ```bash
   cp src/main/resources/exchange.properties.example src/main/resources/exchange.properties
   ```

2. Edit `src/main/resources/exchange.properties` with your Exchange server details:
   ```properties
   exchange.server.url=https://outlook.office365.com/EWS/Exchange.asmx
   exchange.username=your.email@example.com
   exchange.password=your-password
   exchange.max.emails=10
   ```

### Option 2: Programmatic Usage

```java
import com.carlosfheuberger.exchange.ExchangeMailReader;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        try {
            String serverUrl = "https://outlook.office365.com/EWS/Exchange.asmx";
            String username = "your.email@example.com";
            String password = "your-password";

            ExchangeMailReader reader = new ExchangeMailReader(serverUrl, username, password);
            
            // Read up to 10 emails from inbox
            List<EmailMessage> emails = reader.readInbox(10);
            
            System.out.println("Found " + emails.size() + " emails:");
            for (EmailMessage email : emails) {
                ExchangeMailReader.printEmailInfo(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Running the Example

```bash
# Run the main example class
mvn exec:java -Dexec.mainClass="com.carlosfheuberger.exchange.ExchangeMailReader"
```

## API Overview

### ExchangeMailReader

Main class for reading emails from Exchange server.

**Constructor:**
- `ExchangeMailReader(String serverUrl, String username, String password)`

**Methods:**
- `readInbox(int maxEmails)` - Read emails from the Inbox folder
- `readFolder(WellKnownFolderName folderName, int maxEmails)` - Read emails from a specific folder
- `readEmailById(ItemId itemId)` - Read a specific email by ID
- `printEmailInfo(EmailMessage email)` - Print basic email information

### ExchangeConfig

Configuration helper class for loading Exchange settings.

**Constructors:**
- `ExchangeConfig(String propertiesFilePath)` - Load from properties file
- `ExchangeConfig(String serverUrl, String username, String password, int maxEmails)` - Create with explicit values

## Exchange Server URLs

### Office 365 / Exchange Online
```
https://outlook.office365.com/EWS/Exchange.asmx
```

### On-Premises Exchange Server
```
https://your-exchange-server.com/EWS/Exchange.asmx
```

## Security Notes

- **Never commit your actual credentials to version control**
- Keep your `exchange.properties` file secure and exclude it from git (it's in `.gitignore`)
- Consider using environment variables or secure credential stores for production use
- Use App Passwords or OAuth 2.0 when possible instead of plain passwords

## Troubleshooting

### Connection Issues
- Verify the Exchange server URL is correct
- Check if EWS is enabled on your Exchange server
- Ensure your credentials are correct
- Check network connectivity and firewall rules

### Authentication Issues
- For Office 365, you may need to use App Passwords if MFA is enabled
- Modern authentication may require OAuth 2.0 tokens instead of basic authentication

## Dependencies

- [EWS Java API](https://github.com/OfficeDev/ews-java-api) - Microsoft Exchange Web Services Java API

## License

This project is open source and available under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
