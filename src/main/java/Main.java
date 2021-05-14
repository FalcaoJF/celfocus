import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Map<Integer, String> countryCodes = countryImporter();
        Map<String, Integer> answer = new HashMap<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Rodrigo\\repos\\vipyou\\prova\\src\\com\\company\\coutryCodes.txt"));
            while((line = reader.readLine()) != null){
                if(line.length() < 4){
                    continue;
                } else if (line.length()<=6){//Short Number
                    if(line.charAt(0) == '0' || line.matches(".*\\s.*") || !line.matches("[0-9]+")){//Check if it starts with 0 or contains white spaces or contains non numerical chars.
                        continue;
                    }
                    answer.put("Portugal",answer.get("Portugal")!= null ? answer.get("Portugal")+1 : 1);
                } else if (line.length() >=9 && line.substring(0,2).matches("(^\\+?[1-9]{1,3})|((^[0]{2}))")){//Long Number
                    String digitsOnly = line.replaceAll("[^\\D]","");
                    digitsOnly = "00".equals(digitsOnly.substring(0,1)) ? digitsOnly.substring(2): digitsOnly;
                    if(digitsOnly.length() >=9 && digitsOnly.length() <= 14){
                        final Integer countryCode1 = new Integer(digitsOnly.substring(0,0));
                        final Integer countryCode2 = new Integer(digitsOnly.substring(0,1));
                        final Integer countryCode3 = new Integer(digitsOnly.substring(0,2));

                       Optional<Integer> countryCode = countryCodes.keySet().stream().filter(code -> code.equals(countryCode1) || code.equals(countryCode2) || code.equals(countryCode3)).findFirst();
                       if(countryCode.isPresent()){
                           answer.put(countryCodes.get(countryCode.get()),answer.get(countryCodes.get(countryCode.get())!= null ? countryCodes.get(countryCode.get())+1 : 1));
                       }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("coutryCodes.txt not found. It need to be placed in the same directory of the jar file.");
        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static Map<Integer, String> countryImporter() {
        Map<Integer, String> countryCodes = new HashMap<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Rodrigo\\repos\\vipyou\\prova\\src\\com\\company\\coutryCodes.txt"));
            while((line = reader.readLine()) != null){
                String[] country = line.split("-",2);
                Integer key = Integer.valueOf(country[1]);
                String value = country[0];
                countryCodes.put(key,value);
            }
            System.out.println(countryCodes);

        } catch (FileNotFoundException e) {
            System.out.println("coutryCodes.txt not found. It need to be placed in the same directory of the jar file.");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return countryCodes;
    }


}
