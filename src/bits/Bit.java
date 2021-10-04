package bits;

public class Bit {
    private int bit;

    public Bit(int bit){
        this.bit=bit;
    }

    public int getVal(){
        return bit;
    }

    public boolean isOne(){
        if (bit==1){
            return true;
        }
        else{
            return false;
        }
    }
}
