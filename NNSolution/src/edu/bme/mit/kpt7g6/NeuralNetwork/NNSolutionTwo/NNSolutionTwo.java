package edu.bme.mit.kpt7g6.NeuralNetwork.NNSolutionTwo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.math3.linear.RealVector;

import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.NeuralNetwork;
import edu.bme.mit.kpt7g6.NeuralNetwork.Utils.NNSolutionUtils;

public class NNSolutionTwo {
	public static void main(String[] args) throws IOException{
		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));
		
		NeuralNetwork network = NNSolutionUtils.buildNetworkFromParams(stdInReader);
		List<double[]> inputValues  = NNSolutionUtils.readInputValues(stdInReader);
		
		System.out.println(inputValues.size());
		for(double[] input : inputValues){
			network.setInputValues(input);
			RealVector outputVector = network.calculateOutput();
			System.out.println(NNSolutionUtils.VECTOR_FORMATTER.format(outputVector));
		}

	}
	
}
