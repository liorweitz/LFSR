/**
 * this class will represent the lfsr, the shifter. the stream will contain the output bits that will be taken from the state StateVector.
 * the bitGenerator will be responsible to generate the new bit.
 * the matrix is the generator of movement, of the shifting and the generating of the new bit. it is for now redundant.
 */
import java.util.Vector;
public class CoreLfsrImpl implements CoreLfsr{
    private Vector<Integer> stream;
    private BitGenerator bitGenerator;
    private StateVector taps;
    private StateVector state;
    private Matrix matrix;

    /**
     * constructor. the seed is the initializing vector. the state StateVector will be referenced to it.
     * @param seed
     * @param taps
     * @param bitGenerator
     */
    public CoreLfsrImpl(StateVector seed, StateVector taps, BitGenerator bitGenerator){
        this.state=seed;
        this.taps=taps;
        stream=new Vector<Integer>();
        this.bitGenerator=bitGenerator;
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
        int newBit=bitGenerator.generate(state, taps);
        for (int i=1; i< state.getSize();i++){
            state.setVal(i,state.getVal(i=1));
        }
        state.setVal(0,newBit);
//        stream.add(state.getVal)
        return 1;
    }

    @Override
    public Vector<Integer> generate(int k) {
        return null;
    }

    @Override
    public int findMaxPeriodicity() {
        return 0;
    }

    @Override
    public String toString(){
        return stream.toString();
    }
}
