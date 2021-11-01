import expression.Polynom;
import java.util.LinkedList;
import java.util.Vector;
/**
 * this class will represent the lfsr, the shifter. the stream will contain the output bits that will be taken from the state StateVector.
 * the bitGenerator will be responsible to generate the new bit.
 * the rightmost bit is the output bit and the leftmost bit is the new bit.
 * the matrix is the generator of movement, of the shifting and the generating of the new bit. it is for now redundant.
 * taps bits correspond from left to right to a1,a2,...,an.
 */
public class CoreLfsrImpl implements CoreLfsr{
    private final Vector<Integer> stream;
    private final BitGenerator bitGenerator;
    private final StateVector taps;
    private Polynom characteristicPol;
    private final StateVector state;

    /**
     * constructor. the seed is the initializing vector. the state StateVector will be referenced to it.
     * @param seed is initial state.
     * @param taps represent from left to right a1,a2,...,an.
     * @param bitGenerator the action taken with the taps.
     */
    public CoreLfsrImpl(StateVector seed, StateVector taps, BitGenerator bitGenerator){
        this.state=seed;
        this.taps=taps;
        stream= new Vector<>();
        this.bitGenerator=bitGenerator;
        generateCharacteristicPol(taps);
    }

    public CoreLfsrImpl(CoreLfsrImpl other){
        this.state=new StateVectorImpl(other.state.getArray());
        this.bitGenerator=other.bitGenerator;
        this.stream= new Vector<>();
        this.taps=new StateVectorImpl(other.taps.getArray());
        generateCharacteristicPol(other.taps);
    }


    private void generateCharacteristicPol(StateVector taps) {
        characteristicPol=new Polynom(taps.getSize());
        characteristicPol.setCoef(0,1);
        for (int i=0;i<taps.getSize();i++){
            characteristicPol.setCoef(i+1,taps.getVal(i));
        }
        characteristicPol=characteristicPol.decreaseToLeading();
    }

//    private void setupMatrix(StateVector taps) {
//        for (int i=0; i<taps.getSize()-1; i++){
//            matrix.setVal(i,i+1, 1);
//            matrix.setVal(taps.getSize()-1,i, taps.getVal(i));
//        }
//    }

    @Override
    public StateVector getState() {
        return state;
    }

    public Vector<Integer> getStream(){
        return stream;
    }

    @Override
    public void step() {
        int newBit=bitGenerator.generate(state, taps);
        for (int i=state.getSize()-1; i>=1;i--){
            state.setVal(i,state.getVal(i-1));
        }
        state.setVal(0,newBit);
        stream.add(state.getVal(state.getSize()-1));
    }

    @Override
    public void generate(int k) {
        for (int i=1;i<=k;i++){
            step();
        }
    }

    @Override
    public int findMaxPeriodicity(){
        int allegedPeriod=1;
        while (!(checkDivisionForAllegedPeriodicity(allegedPeriod))) {
            allegedPeriod++;
            System.out.println("checking period: " + allegedPeriod);
        }
        return allegedPeriod;
    }

    public int findPeriodicityByBruteForce(){
        ReoccuringStateVectorFinder table=new ReoccuringStateVectorFinder(state.getSize());
        CoreLfsrImpl clone=new CoreLfsrImpl(this);
        int stepCounter=0;
        clone.generate(clone.getState().getSize()-1);
        while (table.insert(state,stepCounter)){
            clone.step();
            stepCounter++;
        }
        return table.getPeriod();
    }

    /**
     * checks if the alleged period is divisible by the characteristic polynom.
     * @param allegedPeriod the suspected period.
     * @return true if divisible, false else.
     */
    private boolean checkDivisionForAllegedPeriodicity(int allegedPeriod){
        Polynom periodPol=new Polynom(allegedPeriod);
        periodPol.setCoef(allegedPeriod,1);
        periodPol.setCoef(0,1);
        Polynom quotient=periodPol.divide(characteristicPol);
        return quotient != null;
    }

    /**
     * checks if the factors of alleged period are divisible by characteristic polynom. it is redundant to use it
     * by findMaxPeriodicity but maybe later version will have a "im feeling lucky" find max period.
     *
     * @param allegedPeriod is suspected period
     * @return false if no factor is divisible, true else.
     */
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
        int tempPeriod=period;
        LinkedList<Integer> factors=new LinkedList<>();
        if (tempPeriod%2==0) {
            factors.push(2);
            tempPeriod /= 2;
        }
        while (tempPeriod%2==0)
            tempPeriod/=2;
        for(int i=3; i<=Math.sqrt(tempPeriod);i+=2){
            if (tempPeriod%i==0){
                factors.push(i);
                tempPeriod/=i;
            }
            while (tempPeriod%i==0)
                tempPeriod/=i;
        }
        if (tempPeriod>2 & tempPeriod!=period)
            factors.add(period);
        return factors;
    }

    @Override
    public String toString(){
        return "current state: " + state + "\n" +
                "Characteristic Polynom " + characteristicPol + "\n" +
                "current key Stream: " + stream;
    }
}
