package elements.calculations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterfacialEnthalpyData {

    public static Double getElement(Element element1, Element element2) {
        Map<Element, Double> element1Map = data.get(element1);
        if (element1Map == null) {
            return null;
        }
        return element1Map.get(element2);
    }

    private static Map<Element, Map<Element, Double>> data = new HashMap<>();

    private static class HintDataRow {
        private String element;
        private Double[] values;

        private HintDataRow(String element, Double[] values) {
            this.element = element;
            this.values = values;
        }
    }

    private static class HintData {
        private List<String> elements;
        private List<HintDataRow> values;

        private HintData(List<String> elements, List<HintDataRow> values) {
            this.elements = elements;
            this.values = values;
        }
    }

    private static List<HintData> hintDataList = Arrays.asList(
            new HintData(
                    Arrays.asList("Ag", "Al", "Au", "As", "B", "Ba", "Be", "Bi", "C", "Ca", "Cd", "Cs", "Co", "Cr", "Cu", "Fe", "Ga", "Ge", "Hg", "In", "Hf", "Ir", "K", "La", "Li", "Mg", "Mn", "Mo", "N", "Na", "Nb", "Ni", "Os", "P", "Pb", "Pd", "Pt", "Pu", "Rb", "Re", "Rh", "Ru", "Sb", "Sc", "Si", "Sn", "Sr", "Ta", "Tc", "Th", "Ti", "Tl", "U", "V", "W", "Y", "Zn", "Zr"),
                    Arrays.asList(
                            new HintDataRow("Ag", new Double[]{0d, -24d, -22d, -40d, 21d, -75d, 31d, -1d, -207d, -84d, -12d, 16d, 84d, 120d, 10d, 123d, -25d, -28d, -7d, -11d, -48d, 61d, 20d, -100d, -59d, -39d, 57d, 150d, -509d, 2d, 66d, 68d, 112d, -87d, 3d, -28d, -3d, -23d, 16d, 152d, 38d, 94d, -19d, -108d, -21d, -16d, -75d, 60d, 97d, -92d, -6d, 5d, 1d, 73d, 172d, -103d, -20d, -78d}),
                            new HintDataRow("Au", new Double[]{-22d, -101d, 0d, -58d, -28d, -159d, -3d, -8d, -139d, -178d, -49d, -25d, 33d, -1d, -40d, 37d, -86d, 1d, 10d, 7d, -253d, 52d, -21d, -255d, -141d, -117d, -53d, 14d, -332d, 44d, -134d, 33d, 75d, -74d, -6d, 0d, 18d, -167d, -27d, 83d, 30d, 64d, -28d, -293d, -70d, -48d, -161d, -135d, 58d, -248d, -203d, -17d, -157d, -87d, 48d, -268d, -78d, -294d}),
                            new HintDataRow("Co", new Double[]{69d, -105d, 25d, -104d, -148d, 31d, -26d, 4d, -250d, 8d, -4d, 149d, 0d, -18d, 25d, -2d, -74d, -72d, 15d, -11d, -119d, -13d, 151d, -51d, 31d, 3d, -21d, -18d, -395d, 127d, -88d, -1d, 0d, -176d, 18d, -5d, -26d, -66d, 151d, 8d, -8d, -3d, -35d, -100d, -120d, -37d, 27d, -86d, -1d, -93d, -103d, 25d, -77d, -54d, -5d, -67d, -45d, -137d}),
                            new HintDataRow("Cr", new Double[]{98d, -74d, -1d, -111d, -182d, 141d, -41d, 33d, -349d, 114d, 34d, 248d, -18d, 0d, 51d, -6d, -38d, -63d, 44d, 32d, -32d, -65d, 0d, 52d, 127d, 75d, 9d, 1d, -556d, 221d, -26d, -27d, -39d, -229d, 50d, -53d, -87d, 6d, 249d, -16d, -50d, -44d, -0.19d, 2d, -119d, -5d, 136d, -24d, -33d, 7d, -27d, 67d, -9d, -8d, 4d, 35d, -8d, -41d}),
                            new HintDataRow("Cu", new Double[]{8d, -38d, -32d, -54d, -41d, -20d, -7d, 8d, -220d, -33d, -5d, 61d, 26d, 50d, 0d, 52d, -29d, -35d, 1d, -1d, -55d, 1d, 63d, -58d, -16d, -16d, 15d, 68d, -440d, 44d, 9d, 14d, 37d, -51d, 13d, -48d, -42d, 68d, 60d, 65d, -9d, 25d, -17d, -83d, -46d, -13d, -22d, 7d, 29d, -71d, -33d, 17d, -23d, 19d, 80d, -70d, -21d, -78d}),
                            new HintDataRow("Fe", new Double[]{103d, -79d, 28d, -94d, -161d, 104d, -25d, 39d, -292d, 77d, 32d, 219d, -2d, -6d, 53d, 0d, -43d, -53d, 48d, 28d, -71d, -32d, 221d, 14d, 96d, 54d, 1d, -7d, -458d, 195d, -57d, -6d, -15d, -193d, 54d, -16d, -47d, -22d, 221d, -1d, -20d, -17d, -9d, -39d, -110d, -5d, 99d, -54d, -11d, -33d, -62d, 66d, -38d, -28d, 0d, -4d, -13d, -85d}),
                            new HintDataRow("Hf", new Double[]{-53d, -215d, -257d, -359d, -407d, 169d, -209d, -159d, -807d, 133d, -109d, 290d, -168d, -44d, -81d, -98d, -185d, -278d, -122d, -112d, 0d, -293d, 277d, 53d, 127d, 29d, -57d, -17d, -1353d, 221d, 17d, -204d, -210d, -569d, -132d, -342d, -374d, -4d, 285d, -129d, -277d, -227d, -237d, 20d, -323d, -181d, 160d, 12d, -201d, 20d, 1d, -84d, -7d, -10d, -27d, 41d, -141d, -1d}),
                            new HintDataRow("Ir", new Double[]{62d, -163d, 48d, -122d, -177d, -43d, -38d, -3d, -229d, -73d, -30d, 122d, -15d, -78d, 1d, -38d, -122d, -93d, 4d, -41d, -253d, 0d, 123d, -158d, -36d, -57d, -80d, -86d, -332d, 95d, -207d, -7d, -2d, -178d, 11d, 24d, 1d, -167d, 123d, -13d, 3d, -2d, -46d, -227d, -158d, -63d, -47d, -203d, -6d, -216d, -227d, 9d, -184d, -146d, -62d, -180d, -88d, -281d}),
                            new HintDataRow("La", new Double[]{-143d, -231d, -328d, -411d, -333d, 57d, -184d, -276d, -836d, 32d, -192d, 180d, -93d, 93d, -115d, 25d, -233d, -343d, -222d, -205d, 72d, -230d, 158d, 0d, 30d, -41d, 14d, 156d, -1782d, 98d, 177d, -146d, -104d, -588d, -250d, -388d, -368d, 34d, 170d, 14d, -245d, -137d, -336d, 10d, -331d, -266d, 50d, 164d, -111d, 11d, 99d, -197d, 70d, 118d, 154d, 1d, -191d, 60d}),
                            new HintDataRow("Mn", new Double[]{46d, -106d, -39d, -151d, -184d, 81d, -54d, -31d, -370d, 58d, -18d, 182d, -21d, 8d, 15d, -1d, -80d, -108d, -12d, -25d, -41d, -66d, 180d, 8d, 71d, 28d, 0d, 18d, -616d, 152d, -13d, -33d, -32d, -254d, -15d, -82d, -101d, -13d, 182d, -2d, -60d, -40d, -76d, -28d, -147d, -60d, 76d, -14d, -31d, -24d, -30d, 3d, -18d, -3d, 23d, -5d, -50d, -52d}),
                            new HintDataRow("Mo", new Double[]{147d, -65d, 13d, -113d, -218d, 216d, -43d, 74d, -422d, 182d, 72d, 342d, -22d, 2d, 82d, -9d, -18d, -52d, 85d, 73d, -15d, -85d, 345d, 103d, 195d, 125d, 22d, 0d, -662d, 311d, -22d, -32d, -56d, -269d, 95d, -57d, -108d, 31d, 345d, -27d, -61d, -58d, 7d, 38d, -126d, 24d, 209d, -19d, -45d, 43d, -14d, 117d, 8d, 0d, -1d, 81d, 17d, -23d}),
                            new HintDataRow("Nb", new Double[]{66d, -121d, -124d, -223d, -325d, 244d, -134d, -12d, -632d, 207d, 9d, 364d, -111d, -32d, 12d, -70d, -77d, -147d, 7d, 13d, 15d, -216d, 360d, 120d, 206d, 111d, -17d, -23d, -1012d, 313d, 0d, -136d, -161d, -425d, 13d, -212d, -264d, 34d, 364d, -105d, -188d, -169d, -90d, 66d, -216d, -51d, 236d, 0d, -147d, 67d, 8d, 50d, 15d, -5d, -34d, 102d, -39d, 15d}),
                            new HintDataRow("Ni", new Double[]{56d, -118d, 25d, -109d, -145d, 1d, -27d, -8d, -233d, -22d, -18d, 122d, -1d, -27d, 14d, -6d, -88d, -80d, 3d, -27d, -145d, -5d, 123d, -81d, 3d, -20d, -33d, -27d, -366d, 100d, -107d, 0d, 5d, -170d, 5d, 0d, -17d, -88d, 123d, 8d, -3d, 2d, -45d, -130d, -126d, -50d, -3d, -105d, 2d, -122d, -126d, 9d, -98d, -69d, -11d, -97d, -60d, -165d}),
                            new HintDataRow("Os", new Double[]{111d, -115d, 68d, -92d, -168d, 44d, -20d, 47d, -246d, 13d, 22d, 197d, 0d, -46d, 46d, -17d, -70d, -55d, 52d, 15d, -178d, -2d, 201d, -70d, 44d, 11d, -38d, -55d, -362d, 173d, -152d, 6d, 0d, -173d, 62d, 31d, -2d, -100d, 200d, -4d, 8d, 0d, -2d, -141d, -126d, -13d, 39d, -147d, 1d, -131d, -161d, 65d, -121d, -97d, -38d, -93d, -37d, -202d}),
                            new HintDataRow("Pd", new Double[]{-29d, -230d, 0d, -187d, -170d, -185d, -50d, -118d, -232d, -208d, -129d, -23d, -7d, -65d, -62d, -19d, -205d, -171d, -97d, -151d, -301d, 24d, -26d, -270d, -165d, -161d, -100d, -59d, -377d, -50d, -208d, 0d, 32d, -202d, -105d, 0d, 8d, -224d, -24d, 26d, 8d, 25d, -144d, -319d, -206d, -163d, -188d, -206d, 17d, -306d, -260d, -110d, -222d, -151d, -26d, -286d, -171d, -339d}),
                            new HintDataRow("Pt", new Double[]{-3d, -229d, 17d, -172d, -194d, -152d, -61d, -75d, -228d, -182d, -102d, 28d, -33d, -110d, -56d, -59d, -192d, -151d, -63d, -119d, -340d, 2d, 26d, -267d, -138d, -144d, -128d, -114d, -332d, -3d, -267d, -22d, -2d, -199d, -62d, 8d, 0d, -247d, 27d, -18d, -7d, -4d, -110d, -333d, -206d, -134d, -157d, -263d, -13d, -320d, -304d, -69d, -257d, -198d, -82d, -289d, -157d, -377d}),
                            new HintDataRow("Pu", new Double[]{-25d, -187d, -187d, -309d, -334d, 105d, -147d, -140d, -726d, 80d, -94d, 206d, -88d, 8d, -45d, -29d, -163d, -238d, -103d, -102d, -4d, -183d, 204d, 24d, 83d, 14d, -17d, 35d, -1124d, 164d, 36d, -118d, -111d, -495d, -116d, -240d, -257d, 0d, 204d, -43d, -177d, -127d, -205d, -3d, -275d, -159d, 100d, 31d, -108d, -1d, 7d, -77d, 4d, 20d, 33d, 14d, -121d, -12d}),
                            new HintDataRow("Re", new Double[]{151d, -79d, 76d, -77d, -172d, 124d, -11d, 84d, -286d, 92d, 64d, 270d, 9d, -19d, 82d, -1d, -31d, -30d, 90d, 60d, -111d, -13d, 275d, 9d, 118d, 72d, -2d, -27d, -431d, 246d, -100d, 10d, -4d, -186d, 101d, 26d, -17d, -39d, 273d, 0d, 4d, -4d, 27d, -63d, -107d, 24d, 119d, -96d, 1d, -55d, -100d, 110d, -64d, -54d, -16d, -14d, 2d, -129d}),
                            new HintDataRow("Rh", new Double[]{37d, -169d, 27d, -139d, -170d, -63d, -39d, -36d, -242d, -90d, -53d, 90d, -9d, -58d, -11d, -23d, -135d, -112d, -23d, -66d, -232d, 2d, 90d, -162d, -55d, -71d, -70d, -59d, -372d, 64d, -176d, -4d, 8d, -189d, -22d, 7d, -6d, -157d, 90d, 4d, 0d, 5d, -73d, -221d, -165d, -87d, -67d, -173d, 1d, -210d, -204d, -22d, -167d, -123d, -36d, -181d, -102d, -261d}),
                            new HintDataRow("Ru", new Double[]{91d, -126d, 56d, -102d, -167d, 19d, -25d, 27d, -242d, -11d, 4d, 169d, -3d, -49d, 31d, -20d, -84d, -68d, 34d, -4d, -188d, -2d, 171d, -90d, 20d, -8d, -46d, -56d, -360d, 145d, -156d, 2d, 0d, -175d, 41d, 24d, -4d, -112d, 171d, -4d, 5d, 0d, -19d, -157d, -133d, -30d, 14d, -152d, -1d, -147d, -169d, 43d, -130d, -103d, -38d, -112d, -52d, -212d}),
                            new HintDataRow("Sc", new Double[]{-119d, -195d, -291d, -339d, -319d, 89d, -191d, -197d, -709d, 61d, -144d, 200d, -139d, 3d, -115d, -53d, -186d, -276d, -165d, -145d, 21d, -255d, 181d, 7d, 54d, -20d, -40d, 45d, -1435d, 125d, 76d, -180d, -162d, -502d, -176d, -352d, -354d, -3d, 192d, -72d, -257d, -185d, -256d, 0d, -286d, -201d, 82d, 67d, -162d, 0d, 33d, -132d, 14d, 33d, 39d, 3d, -154d, 16d}),
                            new HintDataRow("Ta", new Double[]{61d, -124d, -125d, -226d, -322d, 233d, -132d, -19d, -628d, 196d, 3d, 353d, -109d, -30d, 9d, -67d, -82d, -151d, 1d, 6d, 11d, -211d, 348d, 111d, 196d, 104d, -17d, -20d, -1009d, 302d, 0d, -133d, -156d, -423d, 5d, -210d, -260d, 30d, 352d, -100d, -185d, -164d, -96d, 59d, -217d, -58d, 224d, 0d, -142d, 60d, 6d, 42d, 12d, -4d, -30d, 93d, -44d, 10d}),
                            new HintDataRow("Tc", new Double[]{96d, -124d, 52d, -107d, -173d, 34d, -25d, 27d, -266d, 4d, 7d, 184d, -1d, -39d, 37d, -13d, -82d, -70d, 35d, -1d, -172d, -6d, 187d, -75d, 34d, 2d, -37d, -45d, -403d, 159d, -139d, 3d, 1d, -189d, 43d, 17d, -12d, -98d, 186d, 1d, 1d, -1d, -21d, -141d, -136d, -29d, 29d, -135d, 0d, -132d, -153d, 47d, -116d, -89d, -28d, -96d, -49d, -195d}),
                            new HintDataRow("Th", new Double[]{-143d, -244d, -386d, -443d, -436d, 101d, -251d, -242d, -1056d, 71d, -174d, 220d, -166d, 12d, -140d, -58d, -230d, -354d, -202d, -174d, 25d, -316d, 208d, 10d, 65d, -21d, -42d, 63d, -1695d, 148d, 95d, -218d, -195d, -703d, -213d, -440d, -445d, -1d, 213d, -81d, -317d, -225d, -323d, 0d, -372d, -248d, 94d, 85d, -195d, 0d, 41d, -156d, 19d, 45d, 57d, 5d, -189d, 19d}),
                            new HintDataRow("Ti", new Double[]{-6d, -162d, -180d, -274d, -333d, 170d, -158d, -93d, -656d, 138d, -58d, 280d, -126d, -33d, -40d, -74d, -130d, -204d, -66d, -59d, 1d, -228d, 271d, 64d, 135d, 50d, -36d, -15d, -1083d, 225d, 8d, -154d, -164d, -456d, -70d, -256d, -290d, -7d, 277d, -101d, -211d, -176d, -162d, 28d, -252d, -119d, 162d, 5d, -155d, 28d, 0d, -31d, -1d, -7d, -23d, 51d, -93d, -1d}),
                            new HintDataRow("U", new Double[]{1d, -186d, -186d, -311d, -370d, 153d, -161d, -109d, -778d, 123d, -70d, 267d, -110d, -13d, -34d, -53d, -151d, -231d, -76d, -74d, -7d, -215d, 267d, 53d, 126d, 44d, -25d, 9d, -1180d, 222d, 17d, -140d, -144d, -525d, -83d, -254d, -286d, 4d, 265d, -75d, -201d, -158d, -186d, 13d, -284d, -138d, 147d, 14d, -137d, 15d, -1d, -41d, 0d, 4d, 5d, 39d, -107d, -13d}),
                            new HintDataRow("V", new Double[]{63d, -101d, -69d, -170d, -238d, 163d, -84d, -10d, -471d, 135d, 5d, 269d, -58d, -8d, 21d, -29d, -67d, -113d, 7d, 4d, -8d, -130d, 267d, 69d, 141d, 74d, -3d, 0d, -762d, 231d, -4d, -75d, -88d, -317d, 9d, -132d, -167d, 16d, 269d, -48d, -113d, -95d, -70d, 25d, -166d, -44d, 157d, -4d, -80d, 28d, -6d, 36d, 3d, 0d, -3d, 54d, -34d, -13d}),
                            new HintDataRow("W", new Double[]{172d, -54d, 44d, -90d, -205d, 222d, -27d, 101d, -390d, 187d, 92d, 355d, -6d, 4d, 101d, 0d, -3d, -30d, 108d, 93d, -24d, -63d, 360d, 104d, 204d, 136d, 28d, -1d, -605d, 327d, -33d, -14d, -39d, -244d, 121d, -26d, -79d, 31d, 359d, -17d, -37d, -40d, 32d, 34d, -110d, 45d, 216d, -29d, -28d, 40d, -23d, 141d, 4d, -3d, 0d, 81d, 32d, -34d}),
                            new HintDataRow("Y", new Double[]{-136d, -221d, -318d, -389d, -332d, 68d, -190d, -249d, -797d, 42d, -177d, 189d, -112d, 59d, -117d, -6d, -219d, -322d, -204d, -185d, 51d, -242d, -185d, 1d, 38d, -35d, -8d, 114d, -35d, 108d, 139d, -162d, -128d, 108d, -225d, -379d, -367d, 18d, 179d, -20d, -253d, -158d, -310d, 4d, -318d, -245d, 61d, 127d, -132d, 5d, 73d, -175d, 47d, 85d, 111d, 0d, -180d, 42d}),
                            new HintDataRow("Zr", new Double[]{-87d, -240d, -303d, -398d, -440d, 163d, -239d, -196d, -867d, 127d, -138d, 283d, -197d, -58d, -110d, -118d, -212d, -314d, -155d, -141d, -1d, -331d, -141d, 45d, 117d, 13d, -74d, -27d, 13d, 208d, 17d, -237d, -241d, 208d, -167d, -391d, -421d, -13d, 277d, -153d, -316d, -260d, -275d, 16d, -355d, -213d, 154d, 12d, -232d, 15d, -1d, -115d, -13d, -17d, -39d, 34d, -166d, 0d})





                    )
            ),
            new HintData(
                    Arrays.asList("Ag","Au","Co","Cr","Cu","Fe","Hf","Ir","La","Mn","Mo","Nb","Ni","Os","Pd","Pt","Pu","Re","Rh","Ru","Sc","Ta","Tc","Th","Ti","U","V","W","Y","Zr"),
                    Arrays.asList(
                            new HintDataRow("Al", new Double[]{-24d, -100d, -124d, -87d, -47d, -91d, -189d, -156d, -155d, -127d, -64d, -116d, -139d, -113d, -216d, -207d, -165d, -77d, -167d, -126d, -170d, -120d, -121d, -155d, -163d, -155d, -113d, -52d, -161d, -208d}),
                            new HintDataRow("As", new Double[]{-44d, -64d, -149d, -156d, -75d, -131d, -364d, -144d, -322d, -213d, -133d, -252d, -155d, -110d, -215d, -191d, -305d, -90d, -169d, -124d, -344d, -255d, -128d, -315d, -320d, -290d, -224d, -104d, -330d, -397d}),
                            new HintDataRow("B", new Double[]{13d, -17d, -119d, -146d, -31d, -127d, -242d, -116d, -158d, -150d, -148d, -213d, -116d, -112d, -109d, -120d, -178d, -114d, -115d, -114d, -196d, -211d, -115d, -167d, -227d, -186d, -182d, -135d, -170d, -258d}),
                            new HintDataRow("Ba", new Double[]{-179d, -384d, 65d, 299d, -62d, 213d, 280d, -70d, 69d, 176d, 386d, 437d, 1d, 74d, -298d, -233d, 226d, 210d, -108d, 32d, 141d, 417d, 57d, 156d, 321d, 311d, 337d, 383d, 90d, 269d}),
                            new HintDataRow("Be", new Double[]{19d, -2d, -21d, -33d, -6d, -20d, -129d, -24d, -91d, -45d, -30d, -90d, -22d, -13d, -32d, -37d, -81d, -7d, -26d, -17d, -122d, -89d, -17d, -99d, -111d, -84d, -66d, -18d, -101d, -147d}),
                            new HintDataRow("Bi", new Double[]{-1d, -13d, 8d, 61d, 15d, 71d, -212d, -5d, -277d, -58d, 116d, -17d, -15d, 74d, -178d, -110d, -192d, 130d, -57d, 43d, -258d, -28d, 42d, -238d, -143d, -141d, -17d, 153d, -272d, -256d}),
                            new HintDataRow("C", new Double[]{-97d, -65d, -170d, -233d, -131d, -193d, -388d, -128d, -329d, -249d, -237d, -339d, -158d, -139d, -127d, -121d, -304d, -160d, -139d, -140d, -363d, -337d, -150d, -317d, -364d, -307d, -295d, -214d, -340d, -411d}),
                            new HintDataRow("Ca", new Double[]{-156d, -322d, 13d, 196d, -78d, 129d, 179d, -97d, 32d, 102d, 263d, 299d, -37d, 17d, -272d, -226d, 133d, 125d, -124d, -16d, 78d, 284d, 5d, 86d, 210d, 194d, 224d, 261d, 45d, 169d}),
                            new HintDataRow("Cd", new Double[]{-15d, -58d, -5d, 46d, -8d, 42d, -113d, -32d, -155d, -25d, 82d, 10d, -24d, 24d, -136d, -102d, -99d, 70d, -59d, 4d, -149d, 3d, 8d, -131d, -69d, -69d, 6d, 101d, -154d, -142d}),
                            new HintDataRow("Cs", new Double[]{59d, -91d, 353d, 628d, 275d, 522d, 625d, 214d, 291d, 480d, 730d, 820d, 284d, 365d, -40d, 45d, 661d, 514d, 168d, 320d, 416d, 794d, 346d, 506d, 677d, 807d, 685d, 720d, 329d, 613d}),
                            new HintDataRow("Ga", new Double[]{-28d, -95d, -96d, -50d, -40d, -55d, -179d, -128d, -173d, -106d, -20d, -82d, -114d, -76d, -213d, -192d, -161d, -33d, -148d, -93d, -178d, -88d, -88d, -163d, -145d, -141d, -83d, -4d, -175d, -203d}),
                            new HintDataRow("Ge", new Double[]{-27d, -51d, -89d, -77d, -44d, -64d, -244d, -95d, -231d, -132d, -53d, -144d, -99d, -57d, -170d, -145d, -209d, -31d, -118d, -72d, -242d, -148d, -72d, -222d, -207d, -191d, -129d, -30d, -235d, -272d}),
                            new HintDataRow("Hg", new Double[]{-9d, -30d, 22d, 65d, 1d, 69d, -137d, 5d, -193d, -17d, 104d, 8d, 4d, 62d, -110d, -69d, -114d, 106d, -27d, 40d, -185d, 1d, 42d, -161d, -84d, -80d, 10d, 129d, -191d, -173d}),
                            new HintDataRow("In", new Double[]{-14d, -65d, -18d, 50d, -2d, 43d, -128d, -52d, -178d, -39d, 95d, 16d, -42d, 20d, -186d, -141d, -122d, 76d, -86d, -5d, -164d, 7d, -1d, -149d, -78d, -83d, 6d, 118d, -175d, -160d}),
                            new HintDataRow("K", new Double[]{54d, -56d, 293d, 516d, 219d, 433d, 482d, 179d, 207d, 387d, 601d, 657d, 235d, 307d, -38d, 35d, 497d, 431d, 139d, 268d, 305d, 635d, 290d, 363d, 530d, 611d, 552d, 597d, 237d, 466d}),
                            new HintDataRow("Li", new Double[]{-69d, -166d, 30d, 131d, -24d, 94d, 108d, -27d, 19d, 76d, 169d, 185d, 3d, 34d, -123d, -96d, 87d, 94d, -43d, 16d, 45d, 176d, 27d, 49d, 130d, 125d, 144d, 169d, 26d, 100d}),
                            new HintDataRow("Mg", new Double[]{-48d, -145d, 3d, 98d, -25d, 68d, 29d, -58d, -31d, 37d, 137d, 121d, -25d, 12d, -162d, -138d, 16d, 75d, -76d, -9d, -20d, 113d, 2d, -16d, 58d, 46d, 93d, 144d, -29d, 13d}),
                            new HintDataRow("N", new Double[]{-277d, -181d, -320d, -439d, -304d, -361d, -752d, -222d, -720d, -487d, -439d, -634d, -297d, -246d, -247d, -212d, -548d, -288d, -256d, -250d, -755d, -632d, -272d, -593d, -699d, -543d, -560d, -394d, -731d, -797d}),
                            new HintDataRow("Na", new Double[]{4d, -78d, 180d, 329d, 99d, 277d, 273d, 102d, 92d, 235d, 390d, 408d, 140d, 194d, -53d, -3d, 258d, 281d, 72d, 166d, 150d, 393d, 180d, 167d, 313d, 330d, 343d, 391d, 110d, 257d}),
                            new HintDataRow("P", new Double[]{-77d, -66d, -213d, -274d, -129d, -229d, -488d, -177d, -401d, -305d, -270d, 407d, -206d, -175d, -197d, -188d, -395d, -186d, -194d, -181d, -445d, -405d, -190d, -403d, -452d, -395d, -354d, -239d, -416d, -521d}),
                            new HintDataRow("Pb", new Double[]{4d, -8d, 32d, 88d, 24d, 95d, -169d, 16d, -241d, -26d, 142d, 18d, 8d, 94d, -152d, -87d, -153d, 151d, -33d, 64d, -220d, 7d, 64d, -202d, -103d, -103d, 15d, 176d, -235d, -210d}),
                            new HintDataRow("Rb", new Double[]{49d, -83d, 323d, 571d, 239d, 477d, 551d, 197d, 247d, 432d, 665d, 736d, 259d, 336d, -39d, 40d, 569d, 472d, 154d, 294d, 359d, 713d, 318d, 426d, 601d, 698d, 617d, 658d, 281d, 537d}),
                            new HintDataRow("Sb", new Double[]{-27d, -40d, -62d, -34d, -30d, -16d, -295d, -66d, -319d, -132d, 10d, -125d, -79d, -3d, -204d, -151d, 258d, 39d, -109d, -29d, -316d, -133d, -31d, -291d, -233d, -220d, -113d, 46d, -319d, -337d}),
                            new HintDataRow("Si", new Double[]{-19d, -63d, -138d, -134d, -53d, -122d, -262d, -149d, -207d, -167d, -120d, -195d, -145d, -120d, -190d, -184d, -219d, -101d, -160d, -130d, -232d, -197d, -129d, -213d, -236d, -214d, -175d, -102d, -216d, -284d}),
                            new HintDataRow("Sn", new Double[]{-22d, -66d, -62d, -10d, -23d, -8d, -216d, -87d, -239d, -99d, 33d, -68d, -84d, -18d, -220d, -176d, -194d, 33d, -123d, -44d, -234d, -76d, -41d, -218d, -163d, -159d, -68d, 61d, -239d, -250d}),
                            new HintDataRow("Sr", new Double[]{-167d, -359d, 53d, 271d, -62d, 191d, 249d, -73d, 58d, 156d, 351d, 396d, -6d, 62d, -285d, -225d, 198d, 188d, -108d, 22d, 122d, 377d, 46d, 135d, 288d, 276d, 304d, 348d, 76d, 238d}),
                            new HintDataRow("Tl", new Double[]{6d, -23d, 40d, 110d, 30d, 106d, -103d, 12d, -182d, 6d, 162d, 67d, 14d, 88d, -144d, -86d, -97d, 148d, -30d, 60d, -158d, 57d, 63d, -142d, -43d, -49d, 56d, 189d, -175d, -139d}),
                            new HintDataRow("Zn", new Double[]{-19d, -73d, -49d, -9d, -25d, -14d, -117d, -75d, -122d, -55d, 161d, -35d, -63d, -33d, -143d, -126d, -101d, 2d, -90d, -47d, -128d, -40d, -42d, -113d, -88d, -84d, -36d, 28d, -125d, -137d})

                    )
            )
    );

    static {
        for (HintData hintData : hintDataList) {
            addHintData(hintData);
        }
    }

    private static void addHintData(HintData hintData) {
        for (HintDataRow hintDataRow : hintData.values) {
            Element element1 = ElementData.getElement(hintDataRow.element);

            Map<Element, Double> element1Map = new HashMap<>();
            for (int i = 0; i < hintDataRow.values.length; i++) {
                Element element2 = ElementData.getElement(hintData.elements.get(i));
                Double value = hintDataRow.values[i];
                element1Map.put(element2, value);
            }

            if (data.containsKey(element1)) {
                throw new RuntimeException();
            }
            data.put(element1, element1Map);
        }
    }

}
