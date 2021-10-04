import bits.Bit;

import java.util.Vector;

public interface CoreLfsr {
    public int step();
    public Vector<Integer> generate(int k);
    public String toString();

}
