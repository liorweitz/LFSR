package expression;

/**
 * this class is intended for the use of the lfsr. it is used with modulu2 polynomials
 * and the input is of leading order polynomials.
 * the zero polynom will be represented by array of size 1 and coeficcient of zero.
 */
public class Polynom {
    private double[] poly;

    public Polynom (int maximumPower){
        poly=new double[maximumPower+1];
        initialize();
    }

    public Polynom(double [] other){
        poly=other;
    }

    private void initialize() {
        for (int i=0;i<poly.length-1;i++){
            poly[i]=0;
        }
    }

    public void setCoef(int power, double coef){
        poly[power]=coef;
    }

    /**
     *
     * @param other
     * @return the result of substraction modulu 2
     */
    public Polynom subtract(Polynom other){
        int thisPower=this.poly.length-1, otherPower=other.poly.length-1;
        int maxPower=(int)Math.max(thisPower, otherPower);
        Polynom newPol = new Polynom(maxPower);
        for (int i=maxPower; i>=0; i--){
            if (i<=thisPower & i<=otherPower){
                newPol.setCoef(i, Math.abs(this.poly[i]-other.poly[i]));
            }
            else if (i<=thisPower & i>otherPower){
                newPol.setCoef(i, this.poly[i]);
            }
            else {
                newPol.setCoef(i, other.poly[i]);
            }
        }
        return newPol.decreaseToLeading();
    }

    /**
     *
     * @return a polynom of leading order from the origin polynom which might contain zeroes.
     */
    private Polynom decreaseToLeading() {
        int leadingPower;
        for (leadingPower=this.poly.length-1; leadingPower>=0; leadingPower--){
            if (poly[leadingPower]==1 || leadingPower==0)
                break;
        }
        double[] newArr=new double[leadingPower+1];
        System.arraycopy(this.poly,0,newArr,0,leadingPower+1);
        return new Polynom(newArr);
    }

    /**
     * long division modulu 2. if remainder is detected (at some step the divisor leading power will
     * be greater than the dividend leading power) atithmaticEcxeption will be thrown and catched. if catched,
     * the periodicity must be decreased and checked.
     * @param divisor
     * @return  quotient
     * @throws Exception
     */
    public Polynom divide(Polynom divisor) throws Exception {
        Polynom dividend=new Polynom(this.poly);
        int maxPower=Math.max(dividend.poly.length, divisor.poly.length);
        Polynom quotient=new Polynom(maxPower);
        while (!(dividend.poly.length==1 && dividend.poly[0]==0)){
            int quotientIndex=findNextQuotient(dividend,divisor);
            quotient.setCoef(quotientIndex,1);
            Polynom temp=(divisor.multiplyByMono(quotientIndex));
            dividend=dividend.subtract(temp);
        }
        return quotient.decreaseToLeading();
    }

    /**
     * this method is for the use of the long division. it is used only to shift the powers according to the
     * qoutient last found term.
     * @param quotientIndex
     * @return polynom of equal or higher order.
     */
    private Polynom multiplyByMono(int quotientIndex) {
        Polynom temp=new Polynom(this.poly.length-1+quotientIndex);
        for (int i=this.poly.length-1;i>=0;i--){
            if (this.poly[i]==1){
                temp.poly[i+quotientIndex]=1;
            }
        }
        return temp;
    }

    /**
     *
     * @param dividend
     * @param divisor
     * @return index of the quotient. it is found from the subtraction of the leading powers.
     * @throws Exception
     */
    public int findNextQuotient(Polynom dividend, Polynom divisor) throws Exception {
        if (divisor.poly.length>this.poly.length){
            throw new Exception(new ArithmeticException(""));
        }
        else{
            return dividend.poly.length-divisor.poly.length;
        }
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        for (int i=this.poly.length-1;i>=0; i--){
            sb.append(poly[i]);
            sb.append("X^");
            sb.append(i);
            sb.append(" ");
            sb.append("+");
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length()-2);
        return sb.toString();
    }
}
