package elements.calculations;

import elements.concentrations.CalculationResult;
import elements.concentrations.CalculationResultFactory;
import java.util.ArrayList;
import elements.structure.ResultingStructure;

public class RegularCalculation {

    private final CalculationResultFactory calculationResultFactory;

    public RegularCalculation(CalculationResultFactory calculationResultFactory) {
        this.calculationResultFactory = calculationResultFactory;
    }

    public CalculationResult calculate(Element[] elements,
                                       Double[] concentrations) {
        double tm = 0;
        double conTotS = 0;
        double chem = 0;
        double elas = 0;
        double struct = 0;
        double price = 0;
        double bM = 0;
        double pTemp = 0;
        double tMax = 0;
        double FOM = 0;
        double rd = 0;
        double hmixAm = 0;
        double hMixSSfrac = 0;
        double hmixSS = 0;
        double hmixL = 0;
        double phi = 0;
        Double[] hfix;
        double nxst = 0;
        boolean isMajor = true;
        double concL = 0;
        double radL = 0;
        double radS = 1;
        double hmixInt = 0;
        double wL = 0;
        double wS = 0;
        double gamma = 0;
        int minorAdd = 0;
        boolean useHfix = isHfixAvailable(elements);
        ArrayList<Double> hMixSum = new ArrayList<Double>();
        double[][] hStruct1 =
                {{0, -1, 7, 5, -3, 0, -2, -11},
                        {-1, 0, 16, 32, 21, 32, 37, 36},
                        {34, 12, 0, -4, -15, -14, -18, -30},
                        {-21, -17, -4, 0, 9, 30, 46, 54},
                        {70, 52, 43, 25, 0, -3, -12, -28},
                        {-19, -17, -5, -3, -8, -1, 0, 0},
                        {-19, -17, -5, -3, -8, -1, 0, 0},
                        {-55, -47, -30, -22, -21, -8, 0, 0}};

        double[][] hStruct2 =
                {{1, 2, 3, 4, 5, 5.5, 6, 7, 8, 8.5, 9, 10, 11, 12},
                        {-2.4, -2.4, -2.4, -2.5, 10.0, 15.0, 13.0, -5.0, -10.5, -11.0, -8.0, -1.0, -1.0, -1.0},
                        {-2.0, -2.0, -2.0, -1.5, 9, 14.0, 11.0, -3.0, -9.5, -11.0, -9.0,-2.0, -2.0, -2.0},
                        {2.2, 2.2, 2.2, 2, -9.5, -14.5, -12.0, 4.0, 10.0, 11.0, 8.5, 1.5, 1.5, 1.5}};

        for (int l = 0; l < elements.length; l++) {
            price = price + elements[l].price*concentrations[l];
            bM = bM + elements[l].k*concentrations[l];
            if (concentrations[l] > concL) {
                concL = concentrations[l];
            }
            if (elements[l].radii > radL) {
                radL = elements[l].radii;
            }
            if (elements[l].radii < radS) {
                radS = elements[l].radii;
            }
        }

        for (int k = 0; k < elements.length; k++) {
            if (((concentrations[k] / concL) < 0.34)) {
                minorAdd += 1;
            }
        }

        String eleAB = "";


        for (int m = 0; m < elements.length; m++) {
            hMixSum.add(0.0);
            for (int n = m + 1; n < elements.length; n++) {
                double sumOfConcentrations = concentrations[m] + concentrations[n];
                double proportionOfConcentrationM = concentrations[m] / sumOfConcentrations;
                double proportionOfConcentrationN = concentrations[n] / sumOfConcentrations;
                double conRat = 0.0;
                if (proportionOfConcentrationM > proportionOfConcentrationN) {
                    conRat = proportionOfConcentrationN;
                } else {
                    conRat = proportionOfConcentrationM;
                }
                Double Hint = 0.0;
                double phiA = 0.0;
                double phiB = 0.0;
                double nwsA = 0.0;
                double nwsB = 0.0;
                double vA = 0.0;
                double vB = 0.0;
                double cA = 0.0;
                double cB = 0.0;

                if (InterfacialEnthalpyData.getElement(elements[m], elements[n]) != null && proportionOfConcentrationN*elements[n].v > proportionOfConcentrationM*elements[m].v
                        || (InterfacialEnthalpyData.getElement(elements[m], elements[n]) != null
                        && proportionOfConcentrationM == proportionOfConcentrationN && elements[n].v > elements[m].v)) {
                    Hint = InterfacialEnthalpyData.getElement(elements[m], elements[n]);
                    phiA = elements[m].phi;
                    phiB = elements[n].phi;
                    nwsA = elements[m].nws;
                    nwsB = elements[n].nws;
                    vA = Math.pow(elements[m].v, (0.666666666));
                    vB = Math.pow(elements[n].v, (0.666666666));
                    cA = proportionOfConcentrationM;
                    cB = 1 - cA;
                } else if (InterfacialEnthalpyData.getElement(elements[m], elements[n]) == null
                        || proportionOfConcentrationM*elements[m].v > proportionOfConcentrationN*elements[n].v
                        || (InterfacialEnthalpyData.getElement(elements[n], elements[m]) != null
                        && proportionOfConcentrationM*elements[m].v == proportionOfConcentrationN*elements[n].v && elements[m].v > elements[n].v)) {
                    Hint = InterfacialEnthalpyData.getElement(elements[n], elements[m]);
                    phiA = elements[n].phi;
                    phiB = elements[m].phi;
                    nwsA = elements[n].nws;
                    nwsB = elements[m].nws;
                    vA = Math.pow(elements[n].v, (0.66666666666));
                    vB = Math.pow(elements[m].v, (0.66666666666));
                    cA = proportionOfConcentrationN;
                    cB = 1 - cA;
                }

                if (Hint == null) {
                    hmixInt = 4.0
                            * HvalData.hval[elements[m].c1][elements[n].c1]
                            * proportionOfConcentrationM
                            * proportionOfConcentrationN;
                } else {
                    double alpha = 1.5 * (vA) / ((1 / Math.pow(nwsA, (1.0/3.0)) + (1 / Math.pow(nwsB, (1.0/3.0)))));
                    double dvA = alpha * ((phiA - phiB) / nwsA);
                    double csA = (cA * vA) / (cA * vA + cB * vB);
                    double csB = 1 - csA;
                    double fAB = csB * (1 + 8 * Math.pow((csA * csB), 2));
                    vA = Math.pow(vA, 1.5) + fAB * (dvA / (cA / conRat));
                    vA = Math.pow(Math.abs(vA), 2.0/3.0);
                    double fBA = csA * (1 + 8 * Math.pow((csA * csB), 2));
                    vB = Math.pow(vB, 1.5) + fBA * ((-1 * (nwsA / nwsB) * dvA) / (cB / conRat));
                    vB = Math.pow(Math.abs(vB), 2.0/3.0);
                    csA = (cA * vA) / (cA * vA + cB * vB);
                    csB = 1 - csA;
                    fAB = csB * (1 + 8 * Math.pow((csA * csB), 2));
                    hmixInt = cA * fAB * Hint;
                }

                if (Math.abs(hmixInt) > Math.abs(hmixL)) {
                    /*if (hmixInt > 0) {
                        hmixInt = hmixInt*2;
                    }*/
                    eleAB = elements[m].getSymbol() + elements[n].getSymbol();
                    hmixL = hmixInt;
                }
            }
        }
        double hmixLargest = 0.0;
        hmixLargest = hmixL;
        if (elements.length % 2.0 == 0){
            hmixL = (hmixL/2.0)*elements.length;
        }
        else {
            hmixL = (hmixL/2.0)*(elements.length-1);
        }



        for (int m = 0; m < elements.length; m++) {
            tm += concentrations[m] * elements[m].mp;
            if (elements[m].mp > tMax){
                tMax = elements[m].mp;
            }
            rd += concentrations[m] * elements[m].radii;
            nxst += concentrations[m] * elements[m].nxst;

            for (int n = m + 1; n < elements.length; n++) {
                if (InterfacialEnthalpyData.getElement(elements[m], elements[n]) != null && InterfacialEnthalpyData.getElement(elements[n], elements[m]) != null){
                    double sumOfConcentrations = 0;
                    double proportionOfConcentrationM = 0;
                    double proportionOfConcentrationN = 0;
                    sumOfConcentrations = concentrations[m] + concentrations[n];
                    proportionOfConcentrationM = concentrations[m] / sumOfConcentrations;
                    proportionOfConcentrationN = concentrations[n] / sumOfConcentrations;
                    hfix = ElementPairData.getValues(elements[m], elements[n]);
                    if (hfix == null) {
                        hfix = ElementPairData.getValues(elements[n], elements[m]);
                    } if (useHfix) {
                        for (int k = 0; k < 4; k++) {
                            hmixAm += 4.0
                                    * hfix[k]
                                    * Math.pow(proportionOfConcentrationM - proportionOfConcentrationN, k)
                                    * concentrations[m]
                                    * concentrations[n];
                        }
                    } else {
                        hmixAm += 4.0
                                * HvalData.hval[elements[m].c1][elements[n].c1]
                                * concentrations[m]
                                * concentrations[n];
                    }

                    double csA = (proportionOfConcentrationM * Math.pow(elements[m].v, 2.0/3.0) / (proportionOfConcentrationM * Math.pow(elements[m].v, 2.0/3.0)
                            + proportionOfConcentrationN * Math.pow(elements[n].v, 2.0/3.0)));
                    double csB = 1 - csA;
                    hMixSSfrac = proportionOfConcentrationM * proportionOfConcentrationN * (csB * InterfacialEnthalpyData.getElement(elements[m], elements[n])
                            + csA * InterfacialEnthalpyData.getElement(elements[n], elements[m]));

                    double[] hElas = {0, 0};
                    for (int l = 0; l < 2; l++) {
                        int o = 0;
                        int p = 0;
                        if (l == 0) {
                            o = m;
                            p = n;
                        } else {
                            o = n;
                            p = m;
                        }
                        double phiA = elements[o].phi;
                        double phiB = elements[p].phi;
                        double nwsA = elements[o].nws;
                        double nwsB = elements[p].nws;
                        double vA = elements[o].v;
                        double vB = elements[p].v;
                        double kA = elements[o].k;
                        double gB = elements[p].g;
                        double wA = vA + (1.5 * (Math.pow(vA,2.0/3.0) / ((1 / Math.pow(nwsA, 1.0/3.0)) + (1 / Math.pow(nwsB, 1.0/3.0))))) * ((phiA - phiB) / nwsA);
                        wA = wA * Math.pow(0.01, 3);
                        double wB = vB + (1.5 * (Math.pow(vA, 2.0/3.0) / ((1 / Math.pow(nwsA, 1.0/3.0)) + (1 / Math.pow(nwsB, 1.0/3.0))))) * ((phiA - phiB) / nwsB);
                        wB = wB * Math.pow(0.01, 3);
                        hElas[l] = ((2 * (kA * Math.pow(10, 10)) * (gB * Math.pow(10, 10)) * Math.pow((wA - wB), 2)) / (4 * (gB * Math.pow(10, 10)) * wA + 3 * (kA * Math.pow(10, 10)) * wB)) / 1000;
                    }
                    double Helas = proportionOfConcentrationM * proportionOfConcentrationN * (proportionOfConcentrationN * hElas[0] + proportionOfConcentrationM * hElas[1]);
                    double hStructL = 100.0;
                    double hStructU = 100.0;
                    double hStructA = 100.0;
                    double hStructB = 100.0;
                    double zAvg = proportionOfConcentrationM * elements[m].vec + proportionOfConcentrationN * elements[n].vec;
                    //System.out.println(zAvg);
                    for (int q = 0; q<14;q++) {
                        if ( zAvg <= hStruct2[0][q]) {
                            zAvg = hStruct2[0][q] - zAvg;
                            //System.out.println(zAvg);
                            for (int r = 1; r < 4; r++) {
                                if (hStruct2[r][q] < hStructU) {
                                    hStructU = hStruct2[r][q];
                                }
                            }
                            //System.out.println(hStructU);
                            q -= 1;
                            if (q < 1) {
                                hStructL = -1*hStructU;
                            } else {
                                zAvg = zAvg/(hStruct2[0][q+1]-hStruct2[0][q]);
                                for (int r = 1; r < 4; r++) {
                                    if (hStruct2[r][q] < hStructL) {
                                        hStructL = hStruct2[r][q];
                                    }
                                }
                                //System.out.println(hStructL);
                            }
                            hStructL = hStructU + (hStructL - hStructU) * zAvg;
                            //System.out.println(hStructL);
                            break;
                        }
                    }
                    for (int q = 0; q < 14; q++) {
                        if (elements[n].vec <= hStruct2[0][q]) {
                            for (int r = 1; r < 4; r++) {
                                if ( hStruct2[r][q] < hStructA) {
                                    hStructA = hStruct2[r][q];
                                }
                            }
                            break;
                        }
                    }
                    for (int q = 0; q < 14; q++) {
                        if (elements[m].vec <= hStruct2[0][q]){
                            for (int r = 1; r < 4; r++){
                                if (hStruct2[r][q] < hStructB){
                                    hStructB = hStruct2[r][q];
                                }
                            }
                            break;
                        }
                    }
                    if (hStructA > hStructB) {
                        hStructA = hStructA + (hStructB-hStructA)*proportionOfConcentrationN;
                    }else{
                        hStructA = hStructB + (hStructA - hStructB)*proportionOfConcentrationM;
                    }
                    hStructL = hStructL - hStructA;
                    //System.out.println(hStructL);
                    /*for (int q = 0; q < 10; q++) {
                        if (q != 9 && zAvg < hStruct2[0][q + 1] && zAvg >= hStruct2[0][q] || q == 9) {
                            zAvg = hStruct2[0][q+1] - zAvg ;   // Find remainder of VEC to interpolate value.
                            System.out.println(zAvg);
                            for (int r = 1; r < 4; r++) {
                                if (hStruct2[r][q] < hStructL) {
                                    hStructL = hStruct2[r][q];
                                }

                                if (q < 9 && (hStruct2[r][q + 1] < hStructU)) {
                                    hStructU = hStruct2[r][q + 1];
                                }

                            }
                            System.out.println(hStructL);
                            System.out.println(hStructU);
                            hStructL = hStructL + (hStructU - hStructL) * zAvg; // Add weighted value.
                            System.out.println(hStructL);
                            break;
                        }
                    }
                    for (int q = 0; q < 10; q++) {
                        if (elements[m].vec == hStruct2[0][q]) {
                            for (int r = 1; r < 4; r++) {
                                if (hStruct2[r][q] < hStructA) {
                                    hStructA = hStruct2[r][q];
                                }
                            }
                        }
                        if (elements[n].vec == hStruct2[0][q]) {
                            for (int r = 1; r < 4; r++) {
                                if (hStruct2[r][q] < hStructB) {
                                    hStructB = hStruct2[r][q];
                                }
                            }
                        }
                    }
                    //System.out.println(hStructL);
                    //System.out.println((hStructA + hStructB)/2);
                    hStructL = hStructL - ((hStructA + hStructB)*proportionOfConcentrationN);*/
                    conTotS = 0.0;
                    for (int o = 0; o < elements.length; o ++){
                        if (o != m){
                            conTotS = conTotS + concentrations[o];
                        }
                    }
                    hMixSSfrac = hMixSSfrac + Helas + hStructL;
                    //hmixSS = hmixSS + hMixSSfrac*conTotS;
                    hMixSum.set(m, hMixSum.get(m) + hMixSSfrac*(concentrations[n]/conTotS));
                    conTotS = 0.0;
                    for (int o = 0; o < elements.length; o ++){
                        if (o != n){
                            conTotS = conTotS + concentrations[o];
                        }
                    }
                    hMixSum.set(n, hMixSum.get(n) + hMixSSfrac*(concentrations[m]/conTotS));
                    /*chem = chem + hMixSSfrac*(1.0/conTotS);
                    elas = elas + Helas*(1.0/conTotS);
                    struct = struct + hStructL*(1.0/conTotS);
                    */
                } else{
                    hmixSS = 1;
                    hmixL = 1000;
                }
            }
        }

        for (int i = 0; i < hMixSum.size(); i++) {
            hmixSS = hmixSS + hMixSum.get(i)*concentrations[i];
        }

        double r = -8.3144621;
        double smix = 0;
        double delta = 0;
        double vec = 0;
        for (int i = 0; i < elements.length; i++) {
            smix += r * concentrations[i] * Math.log(concentrations[i]);
            delta += concentrations[i] * Math.pow(1 - (elements[i].radii / rd), 2);
            vec += concentrations[i] * elements[i].vec;
        }
        if (hmixL == 0) {
            hmixL = 0.00001;
        } else {
            phi = (hmixSS * 1000 - tm * smix) / (-1*Math.abs((hmixL * 1000)));
        }

        pTemp = -1*(1*-Math.abs(hmixL) - hmixSS)/(smix/1000);
        delta = roundToFiveDecimalPlaces(Math.sqrt(delta) * 100);
        vec = roundToFiveDecimalPlaces(vec);

        ResultingStructure structure;
        if (phi < 1.0 || delta > 6.6) {
            structure = ResultingStructure.INTERMEDIATE;
        } else if (vec >= 8) {
            structure = ResultingStructure.FCC;
        } else if (vec <= 6.87) {
            structure = ResultingStructure.BCC;
        } else {
            structure = ResultingStructure.FCC_PLUS_BCC;
        }

        CalculationResult result = calculationResultFactory.newInstance(
                elements,
                concentrations,
                structure
        );
        result.setValue("delta", delta);
        //result.setValue("VEC", vec);
        result.setValue("hmixSS", hmixSS);
        //result.setValue("chem", chem);
        //result.setValue("elas", elas);
        //result.setValue("struct", struct);
        result.setValue("smix", smix);
        result.setValue("tm", tm);
        result.setValue("tMax", tMax);
        result.setValue("phi", phi);
        result.setValue("hmixL", hmixLargest);
        result.setValue("price", price);
        result.setValue("pTemp", pTemp);
        result.setValue("eleAB", eleAB);
        return result;
    }


    private boolean isHfixAvailable(Element[] elements) {
        for (int i = 0; i < elements.length; i++) {
            for (int j = i + 1; j < elements.length; j++) {
                if (ElementPairData.getValues(elements[i], elements[j]) == null) {
                    if ((ElementPairData.getValues(elements[j], elements[i])) == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static double roundToFiveDecimalPlaces(double value) {
        return Math.round(value * 100000) / 100000.0;
    }

}