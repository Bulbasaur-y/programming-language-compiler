import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * i choose option(1) in the pdf file to do the scanner
 */
class Scanner {
    //store all tokens 
    private final List<Core> tokens = new ArrayList<>();
    //the values of ID and CONST
    private final List<Object> values = new ArrayList<>();
     
    private final HashMap<String,Core> tokenMap = new HashMap<>();
     
    private final HashMap<Character,Core> signMap = new HashMap<>();
     
    private int tokenIndex=0;

    // Constructor should open the file and find the first token
    Scanner(String filename) {

        Core[] cores = Core.class.getEnumConstants();
        for(Core core : cores){
            tokenMap.put(core.toString().toLowerCase(),core);
        }

        signMap.put(';',Core.SEMICOLON);
        signMap.put('(',Core.LPAREN);
        signMap.put(')',Core.RPAREN);
        signMap.put(',',Core.COMMA);
        signMap.put('=',Core.ASSIGN);
        signMap.put('!',Core.NEGATION);
        signMap.put('<',Core.LESS);
        signMap.put('+',Core.ADD);
        signMap.put('-',Core.SUB);
        signMap.put('*',Core.MULT);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //start from position 0
                dealLine(line,0);
                 
                //if error, break
                if(tokens.size() > 0 && tokens.get(0) == Core.ERROR){
                    break;
                }
            }
            tokens.add(Core.EOF);
            values.add(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void dealLine(String line,int index){
         
        if(index >= line.length()){
            return;
        }

        char first = line.charAt(index);
        if(first == ' '){
            
            dealLine(line,index+1);
        }
        else {
            if(first == '\t'){
                
                dealLine(line,index+1);
            }
            else if(first>= 'a' && first<='z' || first >='A' && first <= 'Z'){
                 //deal with ID
                int endIndex = dealString(line,index);
                String substring = line.substring(index,endIndex);
                if(tokenMap.containsKey(substring)){
                    
                    Core core = tokenMap.get(substring);
                    tokens.add(core);
                    values.add(null);
                }
                else {
                     
                    tokens.add(Core.ID);
                    values.add(substring);
                }
                
                dealLine(line,endIndex);
            }
            else if(first>= '0' && first <='9'){
                //deal with CONST
               int endIndex = dealConst(line,index);
               String constS = line.substring(index,endIndex);
               int constN = 0;
               try {
                   constN = Integer.valueOf(constS);
               }
               catch (Exception e){
                   
                   System.out.println("Error:" + constS + " the constant is not an int");
                   
                   if(tokens.size() >0){
                       tokens.set(0,Core.ERROR);
                   }
                   else {
                       tokens.add(Core.ERROR);
                   }

                   dealLine(line,line.length());
               }

               if(constN >= 0 && constN <= 1023){
                   tokens.add(Core.CONST);
                   values.add(constN);
               }
               else {
                    
                   System.out.println("Error:" + constN + " const not in range");
                   if(tokens.size() >0){
                       tokens.set(0,Core.ERROR);
                   }
                   else {
                       tokens.add(Core.ERROR);
                   }
                   dealLine(line,line.length());
               }
                dealLine(line,endIndex);
            }
            else if(signMap.containsKey(first)) {
                Core core = signMap.get(first);
                // tokens which has 2 chars
                if(core == Core.LESS){
                    if(index+1 <line.length() && line.charAt(index+1) == '='){
                        core = Core.LESSEQUAL;
                        index = index + 1;
                    }
                }
                else if(core == Core.ASSIGN){
                    if(index+1 <line.length() && line.charAt(index+1) == '='){
                        core = Core.EQUAL;
                        index = index + 1;
                    }
                }
                tokens.add(core);
                values.add(null);
                dealLine(line,index + 1);
            }
            else {
                //invalid symbol
                System.out.println("Error: Invaild symbol '" + line.charAt(index) + "'");
                if(tokens.size() >0){
                    tokens.set(0,Core.ERROR);
                }
                else {
                    tokens.add(Core.ERROR);
                }
                dealLine(line,line.length());
            }
        }
    }

    
    public int dealString(String line,int index){
        while (index <line.length() && (line.charAt(index)>= '0' && line.charAt(index) <='9'
        || line.charAt(index)>= 'a' && line.charAt(index)<='z' || line.charAt(index) >='A' && line.charAt(index) <= 'Z')){
            index++;
        }
        return index;
    }

    
    public int dealConst(String line,int index){
        while (index <line.length() && (line.charAt(index)>= '0' && line.charAt(index) <='9')){
            index++;
        }
        return index;
    }
     
        
    // nextToken should advance the scanner to the next token
    public void nextToken() {
        tokenIndex++;
    }

    // currentToken should return the current token
    public Core currentToken() {
        return tokens.get(tokenIndex);
    }

    // If the current token is ID, return the string value of the identifier
    // Otherwise, return value does not matter
    public String getID() {
        if(currentToken() == Core.ID){
            return (String) values.get(tokenIndex);
        }
        else {
            return "value does not matter";
        }
    }

    // If the current token is CONST, return the numerical value of the constant
    // Otherwise, return value does not matter
    public int getCONST() {
        if(currentToken() == Core.CONST){
            return (int) values.get(tokenIndex);
        }
        else {
            return 0;
        }
    }
}