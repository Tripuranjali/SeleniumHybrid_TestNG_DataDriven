package com.Utilities;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class IDGenerationUtils {
	
    public static String generateSouthAfricaID(String year, String month, String day, String gender, String citizen) {
        // Ensure year is in YY format (last two digits)
        String yy = year.substring(year.length() - 2);
        String mm = String.format("%02d", Integer.parseInt(month));
        String dd = String.format("%02d", Integer.parseInt(day));

        Random rand = new Random();
        
        // Gender-based sequence number (Males: 5000-9999, Females: 0000-4999)
        int sequenceNumber = gender.equalsIgnoreCase("Male") ? 5000 + rand.nextInt(5000) : rand.nextInt(5000);
        
        // Citizen Code: 'Y' (South African Citizen) → 0, 'N' (Permanent Resident) → 1
        int citizenCode = citizen.equalsIgnoreCase("Yes") ? 0 : 1;
        
        int raceClassification = 8; // Typically set to 8 in modern SA IDs

        String idWithoutChecksum = yy + mm + dd + String.format("%04d", sequenceNumber) + citizenCode + raceClassification;
        int checksum=0;
		try {
			checksum = calculateLuhnChecksum(idWithoutChecksum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return idWithoutChecksum+checksum;
    }

    // Luhn Algorithm for Checksum
    private static int calculateLuhnChecksum(String id) {
        int sum = 0;
        boolean alternate = false;

        for (int i = id.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(id.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum * 9) % 10;
    }
    
    private static final List<String> preferredIdentifiers = Arrays.asList(
            "TestCaseID", "ID" , "OrderID", "PolicyNumber","S.No","Country"
    );
    

    public static String getUniqueTestIdentifier(Map<String, String> testData) {
        if (testData == null || testData.isEmpty()) {
            return null; // Return null if testData is null or empty
        }

        // Try to find a preferred identifier
        return preferredIdentifiers.stream()
                .map(key -> testData.get(key)) // Get values from testData based on preferredIdentifiers keys
                .filter(value -> value != null && !value.trim().isEmpty()) // Remove null and empty values
                .findFirst() // Get the first valid identifier, if any
                // If no preferred identifier is found, pick the first available key-value pair
                .or(() -> testData.values().stream()
                        .filter(value -> value != null && !value.trim().isEmpty()) // Remove null and empty values
                        .findFirst())
                .orElse(null); // Return null if no identifier is found
    }
    
    private static String generateFallbackUniqueID() {
        Random random = new Random();
        int randomNum = random.nextInt(9000) + 1000;
        return Instant.now().toString().replaceAll("[:.TZ]", "_") + "_ID" + randomNum;
    }
    
    public static String extractUniqueIdentifier(Object[] parameters) {
        for (Object param : parameters) {
            if (param instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> paramMap = (Map<String, Object>) param;
                
                // Look for unique keys in the Map
                for (String key : Arrays.asList("TestID", "OrderID", "UserID", "TransactionID")) {
                    if (paramMap.containsKey(key)) {
                        return paramMap.get(key).toString().replaceAll("[^a-zA-Z0-9]", "_");
                    }
                }
            } else if (param instanceof List) {
                // Check if the List contains a unique identifier
                @SuppressWarnings("unchecked")
				List <String> paramList = (List<String>) param;
                for (Object item : paramList) {
                    if (item instanceof String && item.toString().contains("Country")) {  // Adjust prefix if needed
                        return item.toString().replaceAll("[^a-zA-Z0-9]", "_");
                    }
                }
            }
        }

        // Fallback unique ID if no identifier is found
        return generateFallbackUniqueID();
    }

    

} 
    
    
    


    


