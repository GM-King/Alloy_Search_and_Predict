package elements.calculations;

public class Element implements Comparable<Element> {

    public final String symbol;
    public final double mp;
    public final double mm;
    public final double radii;
    public final int c1;
    public final int c2;
    public final double nxst;
    public final double vec;
    public final double phi;
    public final double nws;
    public final double v;
    public final double k;
    public final double g;
    public final double price;

    public Element(String symbol,
                   double mp,
                   double mm,
                   int x,
                   int y,
                   double nxst,
                   double vec,
                   double phi,
                   double nws,
                   double v,
                   double k,
                   double g,
                   double price) {
        this.symbol = symbol;
        this.mp = mp;
        this.mm = mm;
        this.c1 = x;
        this.c2 = y;
        this.radii = HvalData.hval[x][y];
        this.nxst = nxst;
        this.vec = vec;
        this.phi = phi;
        this.nws = nws;
        this.v = v;
        this.k = k;
        this.g = g;
        this.price = price;
    }



    public String getSymbol() {
        return symbol;
    }

    public double getMp() {
        return mp;
    }

    public double getMm() {
        return mm;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }

    public double getRadii() {
        return radii;
    }

    public double getNxst() {
        return nxst;
    }

    public double getVec() {
        return vec;
    }

    public double getPhi() {
        return phi;
    }

    public double getNws() {
        return nws;
    }

    public double getV() {
        return v;
    }

    public double getK(){
        return k;
    }

    public double getG(){
        return g;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public int compareTo(Element otherElement) {
        return symbol.compareTo(otherElement.symbol);
    }
}
