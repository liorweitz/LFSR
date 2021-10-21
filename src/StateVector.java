/**
 * this class will contain the information of the different aspects of the lfsr:
 * 1) the current state.
 * 2)
 */
public interface StateVector {
    public int getVal(int loc);
    public int getSize();
    public int getOutputbit();

    public void setVal(int loc, int val);

    public int scalarMult(StateVector other);
    public StateVector bitWiseMult(StateVector other);
    public StateVector matrixMult(Matrix m);
}
