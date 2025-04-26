package adapters.cli;

public interface IInputReader
{
    String readLine(String prompt);

    int readInt(String prompt);

    double readValidDouble(String s, double v, double v1);
}

