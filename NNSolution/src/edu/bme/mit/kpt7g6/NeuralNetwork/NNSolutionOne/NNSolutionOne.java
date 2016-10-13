package edu.bme.mit.kpt7g6.NeuralNetwork.NNSolutionOne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.LinearActivationFunction;
import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.ReLuActivationFunction;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.Layer;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.NeuralNetwork;

public class NNSolutionOne {
	public static void main(String[]args) throws IOException{
		BufferedReader stdInreader = new BufferedReader(new InputStreamReader(System.in));
		
		String architechtureStr = stdInreader.readLine();
		String[] arhStrParams = architechtureStr.split(",");
		int noInputs = Integer.parseInt(arhStrParams[0]);
		int noOutput = Integer.parseInt(arhStrParams[arhStrParams.length-1]);
		
		int noHiddenLayers = arhStrParams.length - 1;
		NeuralNetwork network = new NeuralNetwork(noInputs, arhStrParams.length-1); 
		for(int i = 1; i<noHiddenLayers; i++){
			int noNeuronsInLayer = Integer.parseInt(arhStrParams[i]);
			Layer layer = new Layer(noNeuronsInLayer, network.getOutputLayer(), ReLuActivationFunction.INSTANCE);
			initLayerWeightsAndBias(layer, network.getOutputLayer().getNumerOfNuerons());
			network.appendLayerToNetwork(layer);
		}
		
		Layer outputLayer = new Layer(noOutput, network.getOutputLayer(), LinearActivationFunction.INSTANCE);
		initLayerWeightsAndBias(outputLayer,  network.getOutputLayer().getNumerOfNuerons());
		network.appendLayerToNetwork(outputLayer);
		
		System.out.println(architechtureStr);
		for(Layer l : network.getNonInputLayers()){
			System.out.println(l);
		}
	}
	
	public static void initLayerWeightsAndBias(Layer layer, int noWeights){
		for(int neuronIndex = 0; neuronIndex<layer.getNumerOfNuerons(); neuronIndex++){
			double[] weights = new double[noWeights];
			for(int wIndex = 0; wIndex < noWeights; wIndex++){
				weights[wIndex] = nextRandom();
			}
			layer.setWeightsOfNeuron(neuronIndex, weights);
		}
	}
	
	public static double nextRandom(){
		return new Random().nextGaussian()*0.1;
	}
}
