
package edu.bme.mit.kpt7g6.NeuralNetwork.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrixFormat;
import org.apache.commons.math3.linear.RealVectorFormat;

import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.LinearActivationFunction;
import edu.bme.mit.kpt7g6.NeuralNetwork.ActivationFunctions.ReLuActivationFunction;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.Layer;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.NeuralNetwork;

public class NNSolutionUtils {
	public static RealVectorFormat VECTOR_FORMATTER = new RealVectorFormat("", "", ",");
	public static RealMatrixFormat MATRIX_FORMATTER= new RealMatrixFormat("", "", "", "", "\n", ",");
	
	public static List<double[]> readInputValues(BufferedReader stdInReader) throws NumberFormatException, IOException{
		int noInputs = Integer.parseInt(stdInReader.readLine());
		List<double[]> inputValuesList = new  ArrayList<>(noInputs);
		
		for(int i = 0; i<noInputs; i++){
			inputValuesList.add(parseParamLine2DoubleArray(stdInReader.readLine()));
		}
		
		return inputValuesList;
	}
	
	public static NeuralNetwork buildNetworkFromParams(BufferedReader stdInReader) throws IOException{
		int[] neuronsInLayers = parseParamLine2IntArray(stdInReader.readLine());
		int noInputs = neuronsInLayers[0];

		NeuralNetwork network = new NeuralNetwork(noInputs,neuronsInLayers.length-1);
		
		for(int layerIndex = 1; layerIndex<neuronsInLayers.length; layerIndex++){
			int numberOfNeuronsInCurrentLayer = neuronsInLayers[layerIndex];
			Layer currentLayer = new Layer(numberOfNeuronsInCurrentLayer, network.getOutputLayer(), ReLuActivationFunction.INSTANCE);
			
			for(int neuronIndex = 0; neuronIndex<numberOfNeuronsInCurrentLayer; neuronIndex++){
				double[] parameters = parseParamLine2DoubleArray(stdInReader.readLine());
				currentLayer.setBiasOfNeuron(neuronIndex, parameters[parameters.length-1]); // The number at the end of the line is the bias
				currentLayer.setWeightsOfNeuron(neuronIndex, Arrays.copyOf(parameters, parameters.length-1)); // the others are weights
			}
			network.appendLayer(currentLayer);
		}
		network.getOutputLayer().setActivationFunction(LinearActivationFunction.INSTANCE);
		return network;
	}
	
	public static int[] parseParamLine2IntArray(String paramLine){
		String[] splitParam = paramLine.split(",");
		int[] res = new int[splitParam.length];
		
		for(int i = 0;i < res.length; i++){
			res[i] = Integer.parseInt(splitParam[i]);
		}
		return res;
	}
	
	public static double[] parseParamLine2DoubleArray(String paramLine){
		String[] splitParam = paramLine.split(",");
		double[] res = new double[splitParam.length];
		
		for(int i = 0;i < res.length; i++){
			res[i] = Double.parseDouble(splitParam[i]);
		}
		return res;	
	}
	
}
