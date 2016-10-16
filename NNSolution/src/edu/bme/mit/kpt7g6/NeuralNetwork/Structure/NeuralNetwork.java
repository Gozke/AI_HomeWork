package edu.bme.mit.kpt7g6.NeuralNetwork.Structure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealVector;

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
	
	public RealVector calculateOutputForInputValues(double[] inputValues){
		inputLayer.setInputValues(inputValues);
		// We ask the last layer (ie. the output layer) to calculate it's output. That will start the avalanche.
		return getOutputLayer().getOutput();
	}
	
	public void setInputValues(double[] inputValues){
		inputLayer.setInputValues(inputValues);
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
