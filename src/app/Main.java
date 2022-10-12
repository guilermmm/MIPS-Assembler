package app;

import java.io.IOException;

import model.Assembler;

public class Main {
  public static void main(String[] args) throws IOException {
    Assembler.assemble("src/resources/test.asm");
  }
}
