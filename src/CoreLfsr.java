import java.util.Vector;

public interface CoreLfsr {

    Vector<Integer> getStream();

    StateVector getState();
    /**
     * making one step of the lfsr (shifting and generating new bit).
     * in each step the output bit is saved.
     */
    void step();

    /**
     * perform k steps.
     * @param k is the number of steps to perform.
     */
    void generate(int k);

    /**
     * after the initialization of the lfsr (initializing vector and taps) this method will
     * calculate the maximum periodicity of the current configuration.
     * @return int representing the maximum periodicity.
     */
    int findMaxPeriodicity() throws Exception;

    int findPeriodicityByBruteForce();

    /**
     * returning a string representing the stream of output bits.
     * @return String.
     */
    String toString();

}
