/**
 * this Interface will be responsible for generating the new bit. is is an interface in case there
 * will be multiple tools for generating.
 */
public interface BitGenerator {
    public int generate(StateVector state, StateVector taps);
}
