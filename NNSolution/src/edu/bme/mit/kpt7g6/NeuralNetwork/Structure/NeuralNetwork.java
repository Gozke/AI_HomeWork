package edu.bme.mit.kpt7g6.NeuralNetwork.Structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import edu.bme.mit.kpt7g6.NeuralNetwork.Utils.CompleteInput;

public class NeuralNetwork {
	private InputLayer inputLayer;
	
	/**
	 * This list stores every other layer excluding the input layer, but including the output layer of the neural network.
	 * <br> The output layer is always the last layer
	 */
	private List<Layer> layers;
	
	public NeuralNetwork(int numberOfInputs, int numberOfOtherLayers){
		inputLayer = new InputLayer(numberOfInputs);
		layers = new ArrayList<>(numberOfOtherLayers);
	}
	
	public RealVector calculateOutput(){
		// We ask the last layer (ie. the output layer) to calculate it's output. That will start the avalanche.
		return getOutputLayer().getOutput();
	}
	
	public RealVector calculateOuputFor(RealVector input){
		setInputVector(input);
		return calculateOutput();
	}

	public void performLearning(List<CompleteInput> learningSamples, double braveness){
		for(CompleteInput sample : learningSamples){
			setInputVector(sample.getInputVector());
			RealVector actualOutput = calculateOutput();
			RealVector errorVector = sample.getExpectedOutput().subtract(actualOutput);
			
			Map<Layer, RealMatrix> updatedWeights = new HashMap<>();
			Map<Layer, RealVector> updatedBias = new HashMap<>();
			for (Layer layer : getNonInputLayers()) {
				RealMatrix pDerives = layer.computePartialDerivativeOfWeightsAndBiases(errorVector);
				RealMatrix derivativeOfWeights = pDerives.getSubMatrix(0, pDerives.getRowDimension()-1, 0, pDerives.getColumnDimension()-2);
				RealVector derivativeOfBiases = pDerives.getColumnVector(pDerives.getColumnDimension()-1);

				updatedWeights.put(layer, layer.getWeightsMatrix().add(derivativeOfWeights.scalarMultiply(2*braveness)));
				updatedBias.put(layer, layer.getBiases().add(derivativeOfBiases.mapMultiply(2*braveness)));
			}
			for(Layer layer : getNonInputLayers()){
				layer.updateWeightsAndBiases(updatedWeights.get(layer), updatedBias.get(layer));
			}
		}
	}
	
	public void setInputValues(double[] inputValues){
		inputLayer.setInputValues(inputValues);
	}
	
	public void setInputVector(RealVector inputV){
		inputLayer.setInputValues(inputV.toArray());
	}
	
	public void appendLayer(Layer layerToAppend){
		getOutputLayer().setNextLayer(layerToAppend);
		layers.add(layerToAppend);
	}
	

	/**
	 * Returns the nTh layer of the network. The input layer cannot be accessed with this method.
	 * 
	 * @param layerIndex the 1 based index of the layer to get.
	 * @return the nth layer of the neural network. 
	 */
	public Layer getNthLayer(int layerIndex){
		return layers.get(layerIndex-1);
	}
	
	public List<Layer> getNonInputLayers(){
		return layers;
	}
	
	/**
	 * Returns the output layer.
	 * @return
	 */
	public Ilayer getOutputLayer(){
		return layers.isEmpty() ? inputLayer :  layers.get(layers.size()-1);
	}
	
	public int getNumberOfInputs(){
		return inputLayer.getNumerOfNuerons();
	}
	
	@Override
	public String toString() {
		StringBuilder strRep = new StringBuilder();
		strRep.append(inputLayer.getNumerOfNuerons());
		for(Layer l : layers){
			strRep.append(",");
			strRep.append(l.numberOfNeurons);
		}
		return strRep.toString();
	}
}
