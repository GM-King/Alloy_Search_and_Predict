# Alloy_Search_and_Predict
High-entropy alloy predictive code described in publication "King, D. J. M., Middleburgh, S. C., McGregor, A. G., &amp; Cortie, M. B. (2016). Predicting the formation and stability of single phase high-entropy alloys. Acta Materialia, 104, 172-179."

This version is an early iteration of the website and is best run locally by right clicking the "CommandLineApplication.java" file in IntelliJ and selecting run using the latest Java Development Kit (JDK). See detailed steps below:

Steps for running this code.
1. Edit the input.txt in the root directory. 
2. "use_file_systems_txt: (true/false)" will read/not read systems.txt.
3. "target_name" will select which quantity to optimise for. These are found in RegularCalculation.java and the available values are delta, VEC, hmixSS, smix, nxst, tm, tMax, phi, hmixL, price, pTemp, eleAB.
4. "cutoff_PhiDelta" will not display any systems past the cutoff you set.
5. "elements" will set the system(s) you want to search through. * denotes any element, e.g. Al,Co,Cr,* will search through 4 element systems with Al-Co-Cr.
6. "only_output_best_result" is self explanatory.
7. "target_value" will set the value for which "target_name" is sorted for in the output. If you are after single phase formability then i suggest "target_name: phi", "target_value: 1000".
8. The remainder of the tags are for the concentration steps and are self explanatory. If you set them too fine e.g. you have a 5 element system and you are searching in steps of 0.01 the run will likely never finish or produce an output file terabytes in size.
9. Once you are happy with the inputs run the CommandLineApplication.java located in the src/main/java/elements/config folder.
10. Your output will be in the ../java-calculations-results/ folder named as the timestamp followed by the elements in line 6 of input.txt
