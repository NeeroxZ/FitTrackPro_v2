package adapters;

import adapters.cli.IInputReader;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FakeInputReader implements IInputReader
{

    private final Queue<String> inputs = new LinkedList<>();
    private final Queue<Integer> ints = new LinkedList<>();

    public void feed(String... values) {
        inputs.addAll(List.of(values));
    }

    public void feedInts(Integer... values) {
        ints.addAll(List.of(values));
    }

    @Override
    public String readLine(String prompt) {
        return inputs.isEmpty() ? "" : inputs.poll();
    }

    @Override
    public int readInt(String prompt) {
        return ints.isEmpty() ? 0 : ints.poll();
    }

    @Override
    public double readValidDouble(String prompt, double min, double max) {
        return 0.0;
    }
}
