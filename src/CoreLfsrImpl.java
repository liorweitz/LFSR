import bits.functions.Function;

/**
 * this class will represent the lfsr, the shifter. the stream will contain the output bits that will be taken from the state StateVector.
 * the Function will be the operation that will act upon the taps bits to generate the new bit.
 * the matrix is the generator of movement, of the shifting and the generating of the new bit. it is for now redundant.
 */
import java.util.Vector;
public class CoreLfsrImpl implements CoreLfsr{
    private Vector<Integer> stream;
    private Function func;
    private StateVector taps;
    private StateVector state;
    private Matrix matrix;

    public CoreLfsrImpl(StateVector seed, StateVector taps, Function func){
        this.state=seed;
        this.taps=taps;
        stream=new Vector<Integer>();
        this.func=func;
        matrix=new MatrixImpl(seed.getSize(), seed.getSize());
        setupMatrix(taps);
    }

    private void setupMatrix(StateVector taps) {
        for (int i=0; i<taps.getSize()-1; i++){
            matrix.setVal(i,i+1, 1);
            matrix.setVal(taps.getSize()-1,i, taps.getVal(i));
        }
    }

    @Override
    public int step() {
        int newBit=generateNewBit();
        for (int i=1; i< state.getSize();i++){
            state.setVal(i,state.getVal(i=1));
        }
        state.setVal(0,newBit);
//        stream.add(state.getVal)
        return 1;
    }

    private int generateNewBit() {
        StateVector temp=state.bitWiseMult(taps);
        int newBit=temp.getVal(0);
        for (int i=1; i< temp.getSize();i++){
            newBit=func.act(newBit,temp.getVal(i));
        }
        return newBit;
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
