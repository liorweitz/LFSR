package bits.functions;

import bits.Bit;

public class XOr implements Function{

    @Override
    public int act(int b1, int b2) {
        if (b1!=b2){
            return 1;
        }
        else{
            return 0;
        }
    }
}
