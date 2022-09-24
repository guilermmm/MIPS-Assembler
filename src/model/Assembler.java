package model;

import java.io.*;
import java.util.*;

public class Assembler {

    static List<String> labels;

    public static void assemble(String filepath) throws IOException {
        System.out.println(InstructionTables.rTable);
        File assembly = new File(filepath);
        Scanner sc = new Scanner(assembly);
        List<List<String>> instructions = new ArrayList<>();
        labels = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            instructions.add(getLineInstructions(line));
            if (line.contains(":")) {
                labels.add(getLabels(line));
            }

        }

        List<String> binaryInstructions = new ArrayList<>();

        for (List<String> line : instructions) {
            String command = line.get(0);
            if (InstructionTables.rTable.containsKey(command)) {
                binaryInstructions.add(handleRInstruction(line, InstructionTables.rTable));
            } else if (InstructionTables.iTable.containsKey(command)) {
                binaryInstructions.add(handleIInstruction(line, InstructionTables.iTable));
            } else if (InstructionTables.jTable.containsKey(command)) {
                binaryInstructions.add(handleJInstruction(line, InstructionTables.jTable));
            } else {
                System.out.println("Invalid instruction: " + command);
            }
        }

        File binary = new File("src/resources/binary.bin");
        FileWriter fw = new FileWriter(binary);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String instruction : binaryInstructions) {
            bw.write(instruction);
            bw.newLine();
        }
        bw.close();
        fw.close();
        sc.close();

    }

    public static List<String> getLineInstructions(String line) {
        if (line.contains("("))
            line = line.replace("(", " ").replace(")", "");

        if (line.contains(":"))
            line = line.split(":")[1];

        List<String> instructions = List.of(line.trim().replace(",", "").split(" "));

        return instructions;
    }

    public static String getLabels(String line) {
        return line.split(":")[0].trim();
    }

    public static List<String> removeSymbol(List<String> list) {
        return removeSymbol(list, false);
    }

    public static List<String> removeSymbol(List<String> list, boolean isI) {
        List<String> newList = new ArrayList<>();
        for (String e : list) {
            if (e.contains("$"))
                newList.add(toBinary(Integer.parseInt(e.replace("$", "")), 5));
            int i = 1;
            for (String string : labels) {
                if (e.contains(string)) {
                    newList.add(toBinary(i, isI ? 16 : 26));
                }
                i++;
            }
            if (e.matches("[0-9]+")) {
                newList.add(toBinary(Integer.parseInt(e), 16));
            }
        }

        return newList;
    }

    public static String handleRInstruction(List<String> list, Hashtable<String, Integer> rInstructionHashtable) {
        String command = list.get(0);
        List<String> registers = removeSymbol(list);
        String opCode = "000000";
        String rs = "00000";
        String rt = "00000";
        String rd = "00000";
        String sa = "00000";
        String funct = toBinary(rInstructionHashtable.get(command));

        if (command.compareTo("mult") == 0) {
            opCode = "011100";
        }

        switch (command) {

            case "sll", "srl":
                rs = registers.get(1);
                rt = registers.get(0);
                sa = toBinary(Integer.parseInt(list.get(2)));
                break;

            case "jr":
                rs = registers.get(0);
                break;

            case "mfhi", "mflo":
                rd = registers.get(0);
                break;

            case "mult", "multu", "div", "divu":
                rs = registers.get(0);
                rt = registers.get(1);
                break;

            case "add", "addu", "sub", "subu", "and", "or", "slt", "sltu", "mul":
                rd = registers.get(0);
                rs = registers.get(1);
                rt = registers.get(2);
                break;

            default:
                break;
        }

        return opCode + rs + rt + rd + sa + funct;
    }

    public static String handleIInstruction(List<String> list, Hashtable<String, Integer> iInstructionHashtable) {
        String command = list.get(0);
        List<String> registers = removeSymbol(list, true);
        String opCode = toBinary(iInstructionHashtable.get(command));
        String rs = "00000";
        String rt = "00000";
        String immediate = "0000000000000000";

        switch (command) {

            case "beq", "bne":
                rs = registers.get(0);
                rt = registers.get(1);
                immediate = registers.get(2);

                break;

            case "addi", "addiu", "andi", "ori", "slti", "sltiu":
                rs = registers.get(1);
                rt = registers.get(0);
                immediate = registers.get(2);
                break;

            case "lui":
                rt = registers.get(0);
                immediate = registers.get(1);
                break;

            case "lw", "sw":
                rs = registers.get(2);
                rt = registers.get(0);
                immediate = registers.get(1);
                break;

            default:
                break;
        }

        return opCode + rs + rt + immediate;
    }

    public static String handleJInstruction(List<String> list, Hashtable<String, Integer> jInstructionHashtable) {
        String command = list.get(0);
        String address = removeSymbol(list).get(0);
        String opCode = toBinary(jInstructionHashtable.get(command));

        return opCode + address;
    }

    public static String toBinary(int number) {
        return toBinary(number, 6);
    }

    public static String toBinary(int number, int bits) {
        String binary = Integer.toBinaryString(number);
        int binaryLength = binary.length();

        if (binaryLength < bits) {
            for (int i = 0; i < bits - binaryLength; i++) {
                binary = "0" + binary;
            }
        }

        return binary;
    }
}
