package model;

import java.util.Hashtable;

public class InstructionTables {
  public static Hashtable<String, Integer> rTable = getrFunctHashtable();
  public static Hashtable<String, Integer> iTable = getiOpCodHashtable();
  public static Hashtable<String, Integer> jTable = getjOpCodHashtable();

  public static Hashtable<String, Integer> getrFunctHashtable() {
    Hashtable<String, Integer> rInstructionHashtable = new Hashtable<>();
    rInstructionHashtable.put("sll", 0);
    rInstructionHashtable.put("srl", 2);
    rInstructionHashtable.put("jr", 8);
    rInstructionHashtable.put("mfhi", 16);
    rInstructionHashtable.put("mflo", 18);
    rInstructionHashtable.put("mult", 24);
    rInstructionHashtable.put("multu", 25);
    rInstructionHashtable.put("div", 26);
    rInstructionHashtable.put("divu", 27);
    rInstructionHashtable.put("add", 32);
    rInstructionHashtable.put("addu", 33);
    rInstructionHashtable.put("sub", 34);
    rInstructionHashtable.put("subu", 35);
    rInstructionHashtable.put("and", 36);
    rInstructionHashtable.put("or", 37);
    rInstructionHashtable.put("slt", 42);
    rInstructionHashtable.put("sltu", 43);
    rInstructionHashtable.put("mul", 2);

    return rInstructionHashtable;
  } // key: instruction, value: funct

  public static Hashtable<String, Integer> getiOpCodHashtable() {
    Hashtable<String, Integer> iInstructionHashtable = new Hashtable<>();
    iInstructionHashtable.put("beq", 4);
    iInstructionHashtable.put("bne", 5);
    iInstructionHashtable.put("addi", 8);
    iInstructionHashtable.put("addiu", 9);
    iInstructionHashtable.put("slti", 10);
    iInstructionHashtable.put("sltiu", 11);
    iInstructionHashtable.put("andi", 12);
    iInstructionHashtable.put("ori", 13);
    iInstructionHashtable.put("lui", 15);
    iInstructionHashtable.put("lw", 35);
    iInstructionHashtable.put("sw", 43);

    return iInstructionHashtable;
  } // key: instruction, value: opCode

  public static Hashtable<String, Integer> getjOpCodHashtable() {
    Hashtable<String, Integer> jInstructionHashtable = new Hashtable<>();
    jInstructionHashtable.put("j", 2);
    jInstructionHashtable.put("jal", 3);

    return jInstructionHashtable;
  } // key: instruction, value: opCode

}
