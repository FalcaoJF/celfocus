import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("This application needs an input file to run properly.");
            return;
        }
        Map<Integer, String> countryCodes = countryImporter();
        Map<String, Integer> answer = new HashMap<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            while((line = reader.readLine()) != null){
                if(line.length() < 4){
                    continue;
                } else if (line.length()<=6){//Short Number
                    if(line.charAt(0) == '0' || line.matches(".*\\s.*") || !line.matches("[0-9]+")){//Check if it starts with 0 or contains white spaces or contains non numerical chars.
                        continue;
                    }
                    answer.put("Portugal",answer.get("Portugal")!= null ? answer.get("Portugal")+1 : 1);
                } else if (line.length() >=9 && line.substring(0,2).matches("(^\\+?[1-9]{1,3})|((^[0]{2}))")){//Long Number
                    String digitsOnly = line.replaceAll("[\\D]","");
                    if(digitsOnly.length()<9) continue;
                    digitsOnly = "00".equals(digitsOnly.substring(0,2)) ? digitsOnly.substring(2): digitsOnly;
                    if(digitsOnly.length() >=9 && digitsOnly.length() <= 14){
                        final Integer countryCode1 = new Integer(digitsOnly.substring(0,1));
                        final Integer countryCode2 = new Integer(digitsOnly.substring(0,2));
                        final Integer countryCode3 = new Integer(digitsOnly.substring(0,3));
                       Optional<Integer> countryCode = countryCodes.keySet().stream().filter(code -> code.equals(countryCode1) || code.equals(countryCode2) || code.equals(countryCode3)).findFirst();
                       countryCode.ifPresent(integer -> answer.put(countryCodes.get(integer), answer.get(countryCodes.get(integer)) != null ? answer.get(countryCodes.get(integer)) + 1 : 1));
                    }
                }
            }
            System.out.println(sortByValue(answer));
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Integer, String> countryImporter() {
        Map<Integer, String> countryCodes = new HashMap<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("coutryCodes.txt"));
            while((line = reader.readLine()) != null){
                String[] country = line.split("-",2);
                Integer key = Integer.valueOf(country[1]);
                String value = country[0];
                countryCodes.put(key,value);
            }
        } catch (FileNotFoundException e) {
            System.out.println("coutryCodes.txt not found. It need to be placed in the same directory of the jar file.");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return countryCodes;
    }

    private static Map<String, Integer> sortByValue(final Map<String, Integer> map) {

        return map.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


}
