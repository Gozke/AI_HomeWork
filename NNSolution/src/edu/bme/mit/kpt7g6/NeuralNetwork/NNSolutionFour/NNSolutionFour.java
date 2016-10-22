package edu.bme.mit.kpt7g6.NeuralNetwork.NNSolutionFour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.Layer;
import edu.bme.mit.kpt7g6.NeuralNetwork.Structure.NeuralNetwork;
import edu.bme.mit.kpt7g6.NeuralNetwork.Utils.CompleteInput;
import edu.bme.mit.kpt7g6.NeuralNetwork.Utils.NNSolutionUtils;

public class NNSolutionFour {
	public static void main(String[] args) throws IOException {
		BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));
		double[] teachingParameters = NNSolutionUtils.parseParamLine2DoubleArray(stdInReader.readLine());
		int numberOfEpochs = (int) teachingParameters[0];
		double breavnessFactor = teachingParameters[1];
		double teachingSampleRatio = teachingParameters[2];
		
		NeuralNetwork network = NNSolutionUtils.buildNetworkFromParams(stdInReader);
		List<double[]> inputValues = NNSolutionUtils.readInputValues(stdInReader);
		int numberOfTeachingSamples = (int) Math.floor(inputValues.size() * teachingSampleRatio);
		List<CompleteInput> trainerInputs = inputValues.subList(0, numberOfTeachingSamples).stream()
				.map(new CompleteInput.ArrayToInputMapper(network.getNumberOfInputs()))
				.collect(Collectors.toList());
		List<CompleteInput> validatingInputs = inputValues.subList(numberOfTeachingSamples, inputValues.size()).stream()
				.map(new CompleteInput.ArrayToInputMapper(network.getNumberOfInputs()))
				.collect(Collectors.toList());
		
		for (int epochIndex = 0; epochIndex < numberOfEpochs; epochIndex++) {
			network.performLearning(trainerInputs, breavnessFactor);
			
			double squaredMeanError = performValidation(network, validatingInputs);
			System.out.format("%.6f\n", squaredMeanError);
		}
		
		System.out.println(network.toString());
		for(Layer l : network.getNonInputLayers()){
			System.out.println(l);
		}
		
	}	
	
	private static double performValidation(NeuralNetwork network, List<CompleteInput> validatingSamples){
		RealVector errorAccumulator = new ArrayRealVector(network.getOutputLayer().getNumerOfNuerons());
		for(CompleteInput sample : validatingSamples){
			RealVector actualOutput = network.calculateOuputFor(sample.getInputVector());
			RealVector errorSquared = sample.getExpectedOutput().subtract(actualOutput).mapToSelf(new Power(2)); 
			errorAccumulator = errorAccumulator.add(errorSquared);
		}
		errorAccumulator.mapDivideToSelf(validatingSamples.size());
		double entryAvarage = 0;
		for(int i = 0; i<errorAccumulator.getDimension(); i++){
			entryAvarage+= errorAccumulator.getEntry(i);
		}
		return entryAvarage / errorAccumulator.getDimension();
	}

}
