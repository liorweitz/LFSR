import java.util.Vector;

public interface CoreLfsr {

    public StateVector getState();
    /**
     * making one step of the lfsr (shifting and generating new bit).
     * in each step the output bit is saved.
     * @return output bit
     */
    public int step();

    /**
     * perform k steps.
     * @param k is the number of steps to perform.
     * @return a vector containing the output bits.
     */
    public Vector<Integer> generate(int k);

    /**
     * after the initialization of the lfsr (initializing vector and taps) this method will
     * calculate the maximum periodicity of the current configuration.
     * @return int representing the maximum periodicity.
     */
    public int findMaxPeriodicity() throws Exception;

    /**
     * returning a string representing the stream of output bits.
     * @return
     */
    public String toString();

}
