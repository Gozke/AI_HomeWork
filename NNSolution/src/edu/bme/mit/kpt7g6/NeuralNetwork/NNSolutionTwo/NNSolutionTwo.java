package edu.bme.mit.kpt7g6.NeuralNetwork.NNSolutionTwo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.ActivationFunction;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.Ilayer;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.Layer;

public class NNSolutionTwo {
	public static void main(String[] args) throws IOException{
		BufferedReader stdInreader = new BufferedReader(new InputStreamReader(System.in));
		
		String architechtureStr = stdInreader.readLine();
		String[] arhStrParams = architechtureStr.split(",");
		int noInputs = Integer.parseInt(arhStrParams[0]);
		int noOutput = Integer.parseInt(arhStrParams[arhStrParams.length-1]);

		
	}
	
	public static Layer crateLayer(List<double[]> weights, List<Double> biasvalues, Ilayer prevLayer, ActivationFunction f){
		
		return null;
	}
}
