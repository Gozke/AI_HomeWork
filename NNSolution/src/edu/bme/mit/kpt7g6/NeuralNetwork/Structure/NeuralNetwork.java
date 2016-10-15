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
	
	public void appendLayer(Layer layerToAppend){
		layers.add(layerToAppend);
	}
	
	/**
	 * Returns a non null list of the hidden layers. If there are no hidden layers an empty list is returned.
	 * 
	 * @return List of the hidden layer or an empty list. (never {@code null})
	 */
	public List<Layer> getHiddenLayers(){
		return layers.size() > 1 ? layers.subList(0, layers.size()-2) : new  ArrayList<>(0);
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
}
