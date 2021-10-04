import bits.Bit;
import bits.functions.Function;

import java.util.Vector;
public class CoreLfsrImpl implements CoreLfsr{
    private Vector<Integer> stream;
    private Function func;
    private Vector taps;
    private Vector state;

    @Override
    public int step() {
        return null;
    }

    @Override
    public Vector<Integer> generate(int k) {
        return null;
    }

    @Override
    public String toString(){
        return stream.toString();
    }
}
