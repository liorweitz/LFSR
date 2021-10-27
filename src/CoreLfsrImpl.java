/**
 * this class will represent the lfsr, the shifter. the stream will contain the output bits that will be taken from the state StateVector.
 * the bitGenerator will be responsible to generate the new bit.
 * the leftmost bit is the output bit and the rightmost is the new bit.
 * the matrix is the generator of movement, of the shifting and the generating of the new bit. it is for now redundant.
 */
import expression.Polynom;

import java.util.LinkedList;
import java.util.Vector;
public class CoreLfsrImpl implements CoreLfsr{
    private Vector<Integer> stream;
    private BitGenerator bitGenerator;
    private StateVector taps;
    private Polynom characteristicPol;
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
        characteristicPol=new Polynom(taps.getSize());
        generateCharacteristicPol(taps);
    }


    private void generateCharacteristicPol(StateVector taps) {
        characteristicPol.setCoef(0,1);
        for (int i=0;i<taps.getSize();i++){
            characteristicPol.setCoef(i+1,taps.getVal(i));
        }
    }

    private void setupMatrix(StateVector taps) {
        for (int i=0; i<taps.getSize()-1; i++){
            matrix.setVal(i,i+1, 1);
            matrix.setVal(taps.getSize()-1,i, taps.getVal(i));
        }
    }

    @Override
    public StateVector getState() {
        return state;
    }

    @Override
    public int step() {
        int newBit=bitGenerator.generate(state, taps);
        for (int i=1; i< state.getSize();i++){
            state.setVal(i,state.getVal(i));
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
    public int findMaxPeriodicity(){
        int allegedPeriod=1;
        while (!checkDivisionForAllegedPeriodicity(allegedPeriod) || checkDivisionOfFactorsOfPeriod(allegedPeriod)) {
            allegedPeriod++;
            System.out.println("checking period: " + allegedPeriod);
        }
        return allegedPeriod;
    }

    private boolean checkDivisionForAllegedPeriodicity(int allegedPeriod){
        Polynom periodPol=new Polynom(allegedPeriod);
        periodPol.setCoef(allegedPeriod,1);
        periodPol.setCoef(0,1);
        Polynom quotient=periodPol.divide(characteristicPol);
        if (quotient==null)
            return false;
        return true;
    }

    private boolean checkDivisionOfFactorsOfPeriod(int allegedPeriod) {
        LinkedList<Integer> primeFactors=findPrimeFactors(allegedPeriod);
        boolean check=false;
        for (int factor:primeFactors){
            check=checkDivisionForAllegedPeriodicity(factor);
            if (check)
                break;
        }
        return check;
    }

    private LinkedList<Integer> findPrimeFactors(int period) {
        LinkedList<Integer> factors=new LinkedList<>();
        if (period%2==0) {
            factors.push(2);
            period /= 2;
        }
        while (period%2==0)
            period/=2;
        for(int i=3; i<=Math.sqrt(period);i+=2){
            if (period%i==0){
                factors.push(i);
                period/=i;
            }
            while (period%i==0)
                period/=i;
        }
        if (period>2)
            factors.add(period);
        return factors;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("current state: " + state + "\n");
        sb.append("Characteristic Polynom " + characteristicPol + "\n");
        sb.append("current key Stream: " + stream);
        return sb.toString();
    }
}
