package edu.bme.mit.kpt7g6.NeuralNetwork.NNSolutionThree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixFormat;

import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.Layer;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.NeuralNetwork;
import edu.bme.mit.kpt7g6.NeuralNetwork.Utils.NNSolutionUtils;

public class NNSolutionThree {
	public static void main(String[] args) throws IOException{
		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));		
		NeuralNetwork network = NNSolutionUtils.buildNetworkFromParams(stdInReader);
		List<double[]> inputValues  = NNSolutionUtils.readInputValues(stdInReader);
		
		System.out.println(network.toString());
		
		RealMatrixFormat formatter = new RealMatrixFormat("", "", "", "", "\n", ",");
		for(double[] input : inputValues){
			network.setInputValues(input);
			for(Layer layer : network.getNonInputLayers()){
				RealMatrix pDerives = layer.computePartialDerivativeOfWeightsAndBiases(new ArrayRealVector(new double[]{1}));
				System.out.println(formatter.format(pDerives));
			}
		}
	}
	
}
