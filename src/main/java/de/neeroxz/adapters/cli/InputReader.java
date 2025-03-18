package de.neeroxz.adapters.cli;

public interface InputReader
{
    String readLine(String prompt);

    int readInt(String prompt);

    double readValidDouble(String s, double v, double v1);
}

