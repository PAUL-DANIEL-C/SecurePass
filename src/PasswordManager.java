import java.util.HashMap;
import java.util.Map;

public class PasswordManager {
    private Map<String, String> passwordMap = new HashMap<>();
    private static final String FILE_PATH = "passwords.enc";
    private String masterKey;

    public boolean loadPasswords(String masterPassword) {
        this.masterKey = masterPassword;
        String encryptedData = FileHandler.readFile(FILE_PATH);
        if (encryptedData == null) return true;
        
        try {
            String decryptedData = EncryptionUtil.decrypt(encryptedData, masterPassword);
            String[] entries = decryptedData.split("\n");
            for (String entry : entries) {
                String[] parts = entry.split(":");
                if (parts.length == 2) passwordMap.put(parts[0], parts[1]);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addPassword(String site, String password) {
        passwordMap.put(site, password);
    }

    public String getPassword(String site) {
        return passwordMap.getOrDefault(site, "No password stored.");
    }

    public void savePasswords() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : passwordMap.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        String encryptedData = EncryptionUtil.encrypt(sb.toString(), masterKey);
        FileHandler.writeFile(FILE_PATH, encryptedData);
    }
}
