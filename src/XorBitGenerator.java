public class XorBitGenerator implements BitGenerator{
    @Override
    public int generate(StateVector state, StateVector taps) {
        int sum=state.scalarMult(taps);
        return sum%2;
    }
}
