/**
 * this class will contain the information of the different aspects of the lfsr:
 * 1) the current state.
 * 2)
 */
public interface StateVector {
    int getVal(int loc);
    int getSize();
    int getOutputbit();
    int[] getArray();

    void setVal(int loc, int val);

    int scalarMult(StateVector other);
    StateVector bitWiseMult(StateVector other);
    StateVector matrixMult(Matrix m);

    int toInt();
}
